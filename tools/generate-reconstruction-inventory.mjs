import fs from "node:fs";
import path from "node:path";
import { fileURLToPath } from "node:url";

const scriptDir = path.dirname(fileURLToPath(import.meta.url));
const root = path.resolve(scriptDir, "..");
const backendJava = path.join(root, "backend", "src", "main", "java");
const migrations = path.join(root, "backend", "src", "main", "resources", "db", "migration");
const frontendDist = path.join(root, "frontend", "dist");
const frontendRecoveredSource = path.join(root, "frontend", "reconstructed-src");

function walk(dir) {
  if (!fs.existsSync(dir)) return [];
  const result = [];
  for (const entry of fs.readdirSync(dir, { withFileTypes: true })) {
    const full = path.join(dir, entry.name);
    if (entry.isDirectory()) result.push(...walk(full));
    else result.push(full);
  }
  return result;
}

function rel(file) {
  return path.relative(root, file).replaceAll(path.sep, "/");
}

const javaFiles = walk(backendJava).filter((file) => file.endsWith(".java"));
const migrationFiles = walk(migrations).filter((file) => file.endsWith(".sql"));
const frontendFiles = walk(frontendDist);
const frontendJs = frontendFiles.filter((file) => file.endsWith(".js"));
const frontendCss = frontendFiles.filter((file) => file.endsWith(".css"));
const frontendReconstructedSourceFiles = walk(frontendRecoveredSource);
const normalizedJavaFiles = javaFiles.map((file) => file.replaceAll(path.sep, "/"));

const javaByModule = {};
for (const file of javaFiles) {
  const relative = path.relative(backendJava, file).replaceAll(path.sep, "/");
  const parts = relative.split("/");
  const module = parts[3] || "root";
  javaByModule[module] = (javaByModule[module] || 0) + 1;
}

const indexBundle = frontendJs.find((file) => path.basename(file).startsWith("index-"));
const bundleText = indexBundle ? fs.readFileSync(indexBundle, "utf8") : "";
const routes = [...bundleText.matchAll(/path:"([^"]+)"/g)].map((match) => match[1]);
const apiPaths = [...bundleText.matchAll(/R\("([^"]+)"/g)].map((match) => match[1]);
const unique = (values) => [...new Set(values)].sort((a, b) => a.localeCompare(b));

const inventory = {
  generatedAt: new Date().toISOString(),
  provenance: {
    backendClasses: "build/app-expanded-2/BOOT-INF/classes",
    backendDependencies: "build/app-expanded-2/BOOT-INF/lib",
    frontendBundle: "frontend/dist",
    sourceMapsFound: false,
  },
  backend: {
    javaSourceFiles: javaFiles.length,
    controllers: normalizedJavaFiles.filter((file) => file.includes("/controller/")).length,
    services: normalizedJavaFiles.filter((file) => file.includes("/service/")).length,
    mappers: normalizedJavaFiles.filter((file) => file.includes("/mapper/")).length,
    jobs: normalizedJavaFiles.filter((file) => file.includes("/job/")).length,
    configurationClasses: normalizedJavaFiles.filter((file) => file.includes("/config/")).length,
    flywayMigrations: migrationFiles.length,
    modules: javaByModule,
  },
  frontend: {
    files: frontendFiles.length,
    javascriptChunks: frontendJs.length,
    stylesheets: frontendCss.length,
    reconstructedSourceFiles: frontendReconstructedSourceFiles.length,
    reconstructedSourcePaths: frontendReconstructedSourceFiles.map(rel).sort(),
    routes: unique(routes),
    apiCallPaths: unique(apiPaths),
  },
  deployment: {
    oracleUrl: "http://127.0.0.1:8081",
    reconstructedUrl: "http://127.0.0.1:8082",
    backendImage: "mist-api-reconstructed:local",
    frontendImage: "mist-web-reconstructed:local",
    fallbackClassCount: 0,
  },
};

const output = JSON.stringify(inventory, null, 2) + "\n";
if (process.argv.includes("--write")) {
  const outputDir = path.join(root, "docs", "reconstruction");
  fs.mkdirSync(outputDir, { recursive: true });
  fs.writeFileSync(path.join(outputDir, "inventory.json"), output, "utf8");
}
process.stdout.write(output);
