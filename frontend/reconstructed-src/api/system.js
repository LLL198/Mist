import { api } from "./client.js";

export function isSystemConfigEnabled(configKey) {
  return api("/systemConfig/isEnabled", {
    method: "POST",
    body: { configKey },
  });
}

export function isUserInitialized() {
  return api("/embyUser/userExist", {
    method: "POST",
    body: {},
  });
}

export function isRegistrationEnabled() {
  return api("/embyUser/enableRegistration", {
    method: "POST",
    body: {},
  });
}

export function getPaymentAccountConfig() {
  return api("/paymentAccount/config", { method: "GET" });
}

export function getLoginTransitionCurrent() {
  return api("/loginTransition/current", { method: "GET" });
}

