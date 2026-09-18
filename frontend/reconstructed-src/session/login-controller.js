import { ApiError } from "../api/client.js";
import { login } from "../api/auth.js";
import { getLoginTransitionCurrent } from "../api/system.js";
import { saveSession } from "./session.js";

const RESERVED_NAMES = ["admin", "root"];
const MIN_USERNAME_LENGTH = 6;

export function hasRepeatedOrSequentialTriple(value) {
  for (let index = 0; index < value.length - 2; index += 1) {
    const first = value.charCodeAt(index);
    const second = value.charCodeAt(index + 1);
    const third = value.charCodeAt(index + 2);
    if (
      (first === second && first === third) ||
      (first + 1 === second && first + 2 === third) ||
      (first - 1 === second && first - 2 === third)
    ) {
      return true;
    }
  }
  return false;
}

export function validateUsername(value) {
  const text = String(value ?? "").trim();
  if (!text) return "用户名不能为空";
  if (text.length > 64) return "用户名长度不能超过 64 个字符";
  if (/\s/.test(text)) return "用户名不能包含空格";
  if (RESERVED_NAMES.includes(text.toLowerCase())) return "用户名无法使用";
  return true;
}

export function validateRegisterUsername(value, { minLength = false } = {}) {
  const result = validateUsername(value);
  if (result !== true) return result;
  return minLength && String(value ?? "").trim().length < MIN_USERNAME_LENGTH
    ? `用户名长度不能少于 ${MIN_USERNAME_LENGTH} 位`
    : true;
}

export function validatePassword(value) {
  const text = String(value ?? "");
  if (text.length < 6 || text.length > 30) return "密码长度必须在 6-30 位之间";
  return hasRepeatedOrSequentialTriple(text)
    ? "密码不能包含 3 个及以上连续或重复字符"
    : true;
}

export function validateEmail(value) {
  const text = String(value ?? "").trim();
  return text
    ? /^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(text) || "请输入有效邮箱"
    : "邮箱不能为空";
}

export function validateMobile(value) {
  const text = String(value ?? "").trim();
  return text
    ? /^[+\d][\d\s-]{5,19}$/.test(text) || "请输入有效手机号"
    : "手机不能为空";
}

export function normalizeRedirect(query) {
  const redirect = query?.redirect;
  return typeof redirect === "string" && redirect.startsWith("/")
    ? redirect
    : "/";
}

export function loginErrorMessage(error) {
  if (error instanceof ApiError) {
    if (error.code === 471) return "请选择要登录的服务器。";
    return error.message || "登录失败";
  }
  return error instanceof Error ? error.message : "登录失败";
}

export async function submitLogin(credentials) {
  const result = await login(credentials, credentials.remember);
  if (result.session) saveSession(result.session);
  return result;
}

export async function completeLogin(result, router, query, onTransition = null) {
  if (!result?.session) return result;
  const transition = await getLoginTransitionCurrent().catch(() => null);
  await router.replace(normalizeRedirect(query));
  if (transition?.enabled && transition.rule && onTransition) {
    await onTransition(transition.rule, transition.grantedDays);
  }
  return result;
}
