/**
 * Source-level reconstruction of the session helpers embedded in the Mist
 * production bundle (the `foam-web-session` storage contract).
 *
 * The deployed app still uses the recovered production bundle. This module is
 * the first source replacement and intentionally keeps the original storage
 * keys and field semantics so it can be wired into a Vite build later.
 */

export const SESSION_KEY = "foam-web-session";

function readStorage(storage) {
  try {
    const value = storage.getItem(SESSION_KEY);
    return value ? JSON.parse(value) : null;
  } catch {
    return null;
  }
}

export function getSession() {
  if (typeof window === "undefined") return null;
  return readStorage(window.localStorage) || readStorage(window.sessionStorage);
}

export function saveSession(session) {
  if (typeof window === "undefined") return;
  const target = session.remember ? window.localStorage : window.sessionStorage;
  const other = session.remember ? window.sessionStorage : window.localStorage;
  target.setItem(SESSION_KEY, JSON.stringify(session));
  other.removeItem(SESSION_KEY);
}

export function clearSession() {
  if (typeof window === "undefined") return;
  window.localStorage.removeItem(SESSION_KEY);
  window.sessionStorage.removeItem(SESSION_KEY);
}

export function updateSession(userPatch) {
  const current = getSession();
  if (!current) return null;
  const user = { ...current.user, ...userPatch };
  if (Number(user.hostLineType) === 1) user.expirationDate = null;
  const next = { ...current, user };
  saveSession(next);
  return next;
}

export function isAdmin(user) {
  return Number(user?.isAdmin || 0) === 1 || user?.isAdmin === true;
}

export function isPrimaryAdmin(user) {
  return Number(user?.isPrimaryAdmin || 0) === 1 || user?.isPrimaryAdmin === true;
}

export function hasMenuPermission(user, menu) {
  if (!isAdmin(user)) return false;
  if (isPrimaryAdmin(user)) return true;
  return Array.isArray(user?.menuPermissions) && user.menuPermissions.includes(menu);
}

export function isDistributor(user) {
  return (
    isAdmin(user) ||
    Number(user?.isDistributor || 0) === 1 ||
    user?.isDistributor === true
  );
}

export function displayName(user) {
  return user?.embyUserName || user?.userName || user?.email || "Mist User";
}

function cleanString(value) {
  const text = String(value ?? "").trim();
  const lower = text.toLowerCase();
  return !text || lower === "null" || lower === "undefined" ? "" : text;
}

export function avatarUrl(user, fallback = "") {
  return (
    cleanString(user?.avatar) ||
    cleanString(user?.avatarUrl) ||
    fallback
  );
}

export function createSession(user, remember) {
  return {
    token: `embyuser-${user.id || user.embyUserId || user.userId || user.embyUserName || "foam"}`,
    remember,
    user,
  };
}
