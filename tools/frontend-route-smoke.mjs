import fs from "node:fs";

const oldBase = process.env.OLD_BASE ?? "http://127.0.0.1:8081";
const newBase = process.env.NEW_BASE ?? "http://127.0.0.1:8082";
const inventory = JSON.parse(fs.readFileSync("docs/reconstruction/inventory.json", "utf8"));

async function check(base, route) {
  const response = await fetch(`${base}${route}`, { redirect: "manual" });
  const body = await response.text();
  return { status: response.status, html: body.includes("<html") || body.includes("<!doctype") };
}

let failures = 0;
for (const route of inventory.frontend.routes) {
  const [oldResult, newResult] = await Promise.all([check(oldBase, route), check(newBase, route)]);
  const same = oldResult.status === newResult.status && oldResult.html === newResult.html && newResult.status === 200;
  console.log(`${same ? "PASS" : "FAIL"} ${route}: old=${oldResult.status}, new=${newResult.status}`);
  if (!same) failures += 1;
}

if (failures) process.exitCode = 1;
