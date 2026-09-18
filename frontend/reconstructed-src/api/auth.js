import { ApiError, api, request } from "./client.js";
import { createSession } from "../session/session.js";

export async function login({ userName, password, embyInfoId }, remember) {
  let response;
  try {
    response = await request("/embyUser/login", {
      method: "POST",
      body: { userName, password, embyInfoId },
      acceptCodes: [200, 201, 471],
    });
  } catch (error) {
    if (error instanceof ApiError && error.code === 471) {
      const data = error.data && typeof error.data === "object" ? error.data : {};
      return {
        needServerSelect: true,
        servers: Array.isArray(data.servers) ? data.servers : [],
        msg: error.message,
      };
    }
    throw error;
  }

  if (response.code === 471) {
    return {
      needServerSelect: true,
      servers: response.data?.servers || [],
      msg: response.message,
    };
  }
  return { session: createSession(response.data || {}, remember) };
}

export function logout() {
  return api("/embyUser/logout", {
    method: "POST",
    body: new URLSearchParams(),
  });
}

