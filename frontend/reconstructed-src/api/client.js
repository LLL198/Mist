/**
 * Behavior-compatible HTTP client reconstructed from index-*.js.
 *
 * The original bundle uses the `/api` prefix, cookie credentials, the
 * `{code,msg,data}` response envelope.
 */

export class ApiError extends Error {
  constructor(message, { code, status, data } = {}) {
    super(message);
    this.name = "ApiError";
    this.code = code;
    this.status = status;
    this.data = data;
  }
}

export const API_BASE = "/api".replace(/\/+$/, "");

export function buildUrl(path, query) {
  const url = `${API_BASE}${path.startsWith("/") ? path : `/${path}`}`;
  const params = new URLSearchParams();
  Object.entries(query || {}).forEach(([key, value]) => {
    if (value != null && value !== "") params.set(key, String(value));
  });
  const encoded = params.toString();
  return encoded ? `${url}?${encoded}` : url;
}

export function serializeBody(body, headers) {
  if (body == null) return undefined;
  if (
    body instanceof FormData ||
    body instanceof URLSearchParams ||
    body instanceof Blob ||
    typeof body === "string"
  ) {
    return body;
  }
  if (!headers.has("Content-Type")) {
    headers.set("Content-Type", "application/json;charset=UTF-8");
  }
  return JSON.stringify(body);
}

export async function parseResponse(response) {
  const text = await response.text();
  if (!text) return null;
  try {
    return JSON.parse(text);
  } catch {
    return text;
  }
}

function isObject(value) {
  return value && typeof value === "object" && !Array.isArray(value);
}

export async function request(path, options = {}) {
  const headers = new Headers(options.headers);
  const {
    query,
    acceptCodes = [200, 201],
    body,
    ...fetchOptions
  } = options;
  const response = await fetch(buildUrl(path, query), {
    ...fetchOptions,
    body: serializeBody(body, headers),
    headers,
    credentials: "include",
  });
  const parsed = await parseResponse(response);
  const envelope = isObject(parsed) ? parsed : null;
  const codeValue = envelope?.code ?? envelope?.status;
  const code = Number(codeValue ?? response.status);

  if (response.ok && (codeValue == null || acceptCodes.includes(code))) {
    return {
      code,
      message: envelope?.msg || envelope?.message || response.statusText,
      data: envelope && "data" in envelope ? envelope.data : parsed,
      raw: parsed,
    };
  }

  const message =
    envelope?.msg ||
    envelope?.message ||
    (typeof parsed === "string" ? parsed : "") ||
    `请求失败 (${response.status})`;
  throw new ApiError(message, {
    code,
    status: response.status,
    data: envelope?.data ?? parsed,
  });
}

export async function api(path, options = {}) {
  return (await request(path, options)).data;
}
