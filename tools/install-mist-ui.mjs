import fs from 'node:fs/promises';
import path from 'node:path';
import { fileURLToPath } from 'node:url';
const root = path.resolve(path.dirname(fileURLToPath(import.meta.url)), '..');
const dist = path.join(root, 'frontend/dist');
const indexPath = path.join(dist, 'index.html');
let html = await fs.readFile(indexPath, 'utf8');
await fs.mkdir(path.join(dist, 'mist-ui'), { recursive: true });
for (const file of ['mist-editorial.css', 'mist-editorial.js']) {
  await fs.copyFile(path.join(root, 'frontend/ui', file), path.join(dist, 'mist-ui', file));
}
await fs.cp(path.join(root, 'frontend/ui/vendor'), path.join(dist, 'mist-ui/vendor'), { recursive: true });
await fs.cp(path.join(root, 'frontend/ui/media'), path.join(dist, 'mist-ui/media'), { recursive: true });
html = html.replace(/\s*<!-- mist-ui:start -->[\s\S]*?<!-- mist-ui:end -->/g, '');
html = html.replace('</head>', `  <!-- mist-ui:start -->
    <link rel="stylesheet" href="/mist-ui/mist-editorial.css?v=20260921-9">
    <script type="module" src="/mist-ui/mist-editorial.js?v=20260921-9"></script>
    <!-- mist-ui:end -->
  </head>`);
await fs.writeFile(indexPath, html);
console.log('Mist UI installed: styles, presentation module, and HTML entry.');
