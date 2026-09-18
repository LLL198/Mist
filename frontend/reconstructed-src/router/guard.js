import {
  defaultAdminRoute,
  ROUTES,
} from "./routes.js";
import {
  getSession,
  hasMenuPermission,
  isAdmin,
  isDistributor,
  isPrimaryAdmin,
} from "../session/session.js";

export function routeGuard(to, session = getSession()) {
  const meta = to?.meta || {};
  if (!meta.public && !session) {
    return {
      path: "/login",
      query: { redirect: to?.fullPath || to?.path || "/" },
    };
  }
  if ((meta.admin && !isAdmin(session?.user)) || (meta.primaryAdmin && !isPrimaryAdmin(session?.user))) {
    return "/";
  }
  if (
    isAdmin(session?.user) &&
    !isPrimaryAdmin(session?.user) &&
    !meta.public &&
    to?.name !== "profile" &&
    (!meta.adminMenu || !hasMenuPermission(session?.user, meta.adminMenu))
  ) {
    return defaultAdminRoute(session?.user);
  }
  if (meta.distributor && !isDistributor(session?.user)) return "/";
  if (to?.name === "login" && session) return defaultAdminRoute(session.user);
  return true;
}

export function routeByName(name) {
  return ROUTES.find((item) => item.name === name) || null;
}

