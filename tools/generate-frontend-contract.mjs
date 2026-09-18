import fs from "node:fs";
import path from "node:path";
import { fileURLToPath } from "node:url";

const root = path.resolve(path.dirname(fileURLToPath(import.meta.url)), "..");
const sourceRoot = path.resolve(root, process.env.MIST_FRONTEND_SOURCE ?? process.env.FOAM_FRONTEND_SOURCE ?? "frontend/dist");
const outputRoot = path.resolve(root, process.env.MIST_FRONTEND_OUTPUT ?? process.env.FOAM_FRONTEND_OUTPUT ?? "frontend/recovered");
const indexFile = fs.readdirSync(path.join(sourceRoot, "assets")).find((name) => /^index-.*\.js$/.test(name));
if (!indexFile) throw new Error(`Frontend index bundle not found under ${sourceRoot}`);

const source = fs.readFileSync(path.join(sourceRoot, "assets", indexFile), "utf8");
const unique = (values) => [...new Set(values)].sort((a, b) => a.localeCompare(b));

function findBalancedEnd(text, start, open, close) {
  let depth = 0;
  let quote = null;
  let escaped = false;
  for (let index = start; index < text.length; index += 1) {
    const character = text[index];
    if (quote) {
      if (escaped) {
        escaped = false;
      } else if (character === "\\") {
        escaped = true;
      } else if (character === quote) {
        quote = null;
      }
      continue;
    }
    if (character === '"' || character === "'" || character === "`") {
      quote = character;
      continue;
    }
    if (character === open) depth += 1;
    if (character === close && --depth === 0) return index;
  }
  return -1;
}

const routesStart = source.indexOf("routes:[");
if (routesStart < 0) throw new Error("Vue router route array not found in frontend index bundle");
const routeArrayStart = routesStart + "routes:".length;
const routeArrayEnd = findBalancedEnd(source, routeArrayStart, "[", "]");
if (routeArrayEnd < 0) throw new Error("Unterminated Vue router route array");
const routeArray = source.slice(routeArrayStart + 1, routeArrayEnd);
const routeObjects = [];
for (let index = 0; index < routeArray.length; index += 1) {
  if (routeArray[index] !== "{") continue;
  const end = findBalancedEnd(routeArray, index, "{", "}");
  if (end < 0) throw new Error("Unterminated Vue route object");
  routeObjects.push(routeArray.slice(index, end + 1));
  index = end;
}
const routes = routeObjects.map((segment) => {
  const pathValue = segment.match(/\bpath:"([^"]+)"/)?.[1] ?? null;
  const name = segment.match(/\bname:"([^"]+)"/)?.[1] ?? null;
  const chunk = segment.match(/import\("\.\/([^\"]+\.js)"\)/)?.[1] ?? null;
  const redirect = segment.match(/\bredirect:"([^"]+)"/)?.[1] ?? null;
  const adminMenu = segment.match(/\badminMenu:"([^"]+)"/)?.[1] ?? null;
  const publicRoute = /\bpublic:!0/.test(segment);
  const admin = /\badmin:!0/.test(segment);
  const primaryAdmin = /\bprimaryAdmin:!0/.test(segment);
  const distributor = /\bdistributor:!0/.test(segment);
  return {
    path: pathValue,
    name,
    chunk,
    redirect,
    adminMenu,
    public: publicRoute,
    admin,
    primaryAdmin,
    distributor,
  };
});

const apiCalls = [];
for (const match of source.matchAll(/R\("([^"]+)"/g)) {
  const before = source.slice(Math.max(0, match.index - 1200), match.index);
  const functions = [...before.matchAll(/(?:async\s+)?function\s+([A-Za-z0-9_$]+)\s*\(/g)];
  const functionName = functions.at(-1)?.[1] ?? null;
  const statementEnd = source.indexOf("}", match.index);
  const statement = source.slice(match.index, statementEnd < 0 ? match.index + 800 : statementEnd);
  const method = statement.match(/method:"(GET|POST|PUT|DELETE|PATCH)"/)?.[1] ?? "GET";
  apiCalls.push({ path: match[1], method, functionName });
}

const groupedApiCalls = Object.values(
  apiCalls.reduce((groups, item) => {
    const key = `${item.method} ${item.path}`;
    groups[key] ??= { method: item.method, path: item.path, functions: [] };
    if (item.functionName && !groups[key].functions.includes(item.functionName)) {
      groups[key].functions.push(item.functionName);
    }
    return groups;
  }, {}),
).sort((a, b) => `${a.method} ${a.path}`.localeCompare(`${b.method} ${b.path}`));

const contract = {
  generatedAt: new Date().toISOString(),
  source: path.relative(root, path.join(sourceRoot, "assets", indexFile)).replaceAll(path.sep, "/"),
  routeCount: routes.length,
  apiCallCount: groupedApiCalls.length,
  routes,
  apiCalls: groupedApiCalls,
  note: "Generated from the production Vue bundle because no source map was available. Function names are bundle symbols, not original source names.",
};

fs.mkdirSync(outputRoot, { recursive: true });
fs.writeFileSync(path.join(outputRoot, "CONTRACT.json"), JSON.stringify(contract, null, 2) + "\n", "utf8");
console.log(`Generated ${contract.routeCount} routes and ${contract.apiCallCount} API calls in frontend/recovered/CONTRACT.json`);
