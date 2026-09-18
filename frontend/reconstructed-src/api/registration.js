import { api } from "./client.js";

export function registerByCard({ cardPassword, embyUserName, password = "", remarks = "" }) {
  return api("/embyUser/insertUserCard", {
    method: "POST",
    body: { cardPassword, embyUserName, password, remarks },
  });
}

function registrationBody(form) {
  return {
    embyUserName: form.embyUserName,
    embyUserPassword: form.embyUserPassword,
    remarks: form.remarks,
    email: form.email,
    mobile: form.mobile,
    gender: form.gender,
    birthday: form.birthday,
    interests: form.interests,
  };
}

export function registerUser(form) {
  return api("/embyUser/registeredUser", {
    method: "POST",
    body: registrationBody(form),
  });
}

export function registerUserWithInvitation(form) {
  return api("/embyUser/registeredUser", {
    method: "POST",
    body: registrationBody(form),
  });
}

export function getInvitationStatus() {
  return api("/invitationCode/status", {
    method: "POST",
    body: {},
  });
}

export function registerByInvitation(form) {
  return api("/embyUser/registeredByInvitation", {
    method: "POST",
    body: form,
  });
}

export function createPaymentOrder({ packageId, paymentType, buyerName }) {
  return api("/paymentAccount/orders", {
    method: "POST",
    body: { packageId, paymentType, buyerName },
  });
}

export function queryPaymentOrder(orderNo, lookupCode) {
  return api("/paymentAccount/orders/query", {
    method: "POST",
    body: { orderNo, lookupCode },
  });
}

