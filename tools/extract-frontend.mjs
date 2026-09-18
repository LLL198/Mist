import fs from "node:fs";
import path from "node:path";
import { fileURLToPath } from "node:url";

const root = path.resolve(path.dirname(fileURLToPath(import.meta.url)), "..");
const source = path.resolve(root, process.env.MIST_FRONTEND_SOURCE ?? process.env.FOAM_FRONTEND_SOURCE ?? "frontend/dist");
const output = path.resolve(root, process.env.MIST_FRONTEND_OUTPUT ?? process.env.FOAM_FRONTEND_OUTPUT ?? "frontend/recovered");

function walk(dir) {
  if (!fs.existsSync(dir)) return [];
  const files = [];
  for (const entry of fs.readdirSync(dir, { withFileTypes: true })) {
    const full = path.join(dir, entry.name);
    if (entry.isDirectory()) files.push(...walk(full));
    else files.push(full);
  }
  return files;
}

function relative(file) {
  return path.relative(root, file).replaceAll(path.sep, "/");
}

if (!fs.existsSync(source)) {
  throw new Error(`Frontend bundle not found: ${source}`);
}

if (fs.existsSync(output)) {
  fs.rmSync(output, { recursive: true, force: true });
}
fs.cpSync(source, output, { recursive: true });

const files = walk(output);
const javascriptFiles = files.filter((file) => file.endsWith(".js"));
const stylesheetFiles = files.filter((file) => file.endsWith(".css"));
const indexBundle = javascriptFiles.find((file) => path.basename(file).startsWith("index-"));
const indexText = indexBundle ? fs.readFileSync(indexBundle, "utf8") : "";
const unique = (values) => [...new Set(values)].sort((a, b) => a.localeCompare(b));
const routes = unique([...indexText.matchAll(/path:"([^"]+)"/g)].map((match) => match[1]));
const apiCallPaths = unique([...indexText.matchAll(/R\("([^"]+)"/g)].map((match) => match[1]));
const pageChunks = javascriptFiles
  .map((file) => path.basename(file))
  .filter((name) => /(?:View|Shell|Login|Register|Portal|Management|Records|Designer|Mesh|disclaimer)/i.test(name))
  .sort();

const manifest = {
  generatedAt: new Date().toISOString(),
  source: relative(source),
  output: relative(output),
  sourceMapsFound: files.some((file) => file.endsWith(".map")),
  files: files.length,
  javascriptChunks: javascriptFiles.length,
  stylesheets: stylesheetFiles.length,
  routes,
  apiCallPaths,
  pageChunks,
  note: "This directory is a readable bundle extraction. It preserves compiled Vue behavior but is not the original .vue source tree.",
};
fs.writeFileSync(path.join(output, "EXTRACTION.json"), JSON.stringify(manifest, null, 2) + "\n", "utf8");
fs.writeFileSync(
  path.join(output, "README.md"),
  `# Mist 前端恢复 bundle\n\n该目录由 tools/extract-frontend.mjs 从 ${relative(source)} 生成。\n\n- 页面 chunk、CSS、图片、字体和入口文件均按原路径复制。\n- JavaScript/CSS 文件可再用 Prettier 格式化，便于阅读和逐页迁移。\n- EXTRACTION.json 保存路由、接口调用和页面 chunk 清单。\n- 没有 source map，因此这里是编译产物恢复，不是原始 Vue SFC。\n`,
  "utf8",
);

console.log(`Extracted ${files.length} frontend files to ${relative(output)}`);
console.log(`JavaScript chunks: ${javascriptFiles.length}; routes: ${routes.length}; API paths: ${apiCallPaths.length}`);
