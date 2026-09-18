import assert from "node:assert/strict";

import { request } from "../frontend/reconstructed-src/api/client.js";
import { login } from "../frontend/reconstructed-src/api/auth.js";
import { routeGuard } from "../frontend/reconstructed-src/router/guard.js";
import {
  clearSession,
  getSession,
  saveSession,
} from "../frontend/reconstructed-src/session/session.js";

class MemoryStorage {
  #values = new Map();
  getItem(key) {
    return this.#values.get(key) ?? null;
  }
  setItem(key, value) {
    this.#values.set(key, String(value));
  }
  removeItem(key) {
    this.#values.delete(key);
  }
}

globalThis.window = {
  localStorage: new MemoryStorage(),
  sessionStorage: new MemoryStorage(),
  location: {
    pathname: "/login",
    replace(path) {
      this.pathname = path;
    },
  },
};

let calls = [];
globalThis.fetch = async (url, options) => {
  calls.push({ url: String(url), options });
  const parsedUrl = String(url);
  if (parsedUrl.endsWith("/embyUser/login")) {
    return new Response(
      JSON.stringify({ code: 471, msg: "多服务器", data: { servers: [{ id: "s1" }] } }),
      { status: 200, headers: { "content-type": "application/json" } },
    );
  }
  return new Response(
    JSON.stringify({ code: 200, msg: "ok", data: { valid: true } }),
    { status: 200, headers: { "content-type": "application/json" } },
  );
};

clearSession();
const session = { token: "embyuser-test", remember: false, user: { id: 1 } };
saveSession(session);
assert.deepEqual(getSession(), session);
clearSession();
assert.equal(getSession(), null);

const serverSelection = await login(
  { userName: "u", password: "p", embyInfoId: undefined },
  true,
);
assert.equal(serverSelection.needServerSelect, true);
assert.equal(serverSelection.servers[0].id, "s1");

const loginRedirect = routeGuard({ path: "/", fullPath: "/?x=1", meta: {} }, null);
assert.deepEqual(loginRedirect, { path: "/login", query: { redirect: "/?x=1" } });

console.log(`PASS reconstructed frontend core (${calls.length} mocked HTTP calls)`);
