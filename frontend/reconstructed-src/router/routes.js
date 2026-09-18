/**
 * Route contract recovered from the production router in index-*.js.
 * `componentChunk` is kept as evidence/lookup metadata until each view is
 * replaced by a source-level Vue component.
 */

const route = (path, name, meta = {}, componentChunk = null, redirect = null) => ({
  path,
  name,
  meta,
  componentChunk,
  redirect,
});

export const ROUTES = [
  route("/login", "login", { public: true }),
  route("/", "dashboard", {}, "DashboardView-hl-mFBfV.js"),
  route("/library", "library", {}, "LibraryView-4rnANsgh.js"),
  route("/requests", "requests", {}, "RequestCenterView-DU3joHZ1.js"),
  route("/tickets", "tickets", {}, "TicketCenterView-D9Ce1Lxx.js"),
  route("/profile", "profile", {}, "ProfileView-DK55tQJV.js"),
  route("/user-analysis", "user-analysis", { adminMenu: "user-analysis" }, "UserAnalysisView--cL6AFvq.js"),
  route("/request-records", "request-records", { adminMenu: "request-records" }, "RequestRecordsView-CNhtYU1Q.js"),
  route("/renew", "renew", {}, "RenewView-DFv9RofM.js"),
  route("/my/foam-bags", "my-foam-bags", {}, "FoamBagView-f99Hy7qa.js"),
  route("/my/points/ledger", "my-points-ledger", {}, "PointsPortalView-C_-uGzCd.js"),
  route("/my/points/exchange", "my-points-exchange", {}, "PointsPortalView-C_-uGzCd.js"),
  route("/my/points/records", "my-points-records", {}, "PointsPortalView-C_-uGzCd.js"),
  route("/register-invite", "register-invite", { public: true }, "RegisterInviteView-Ei0d8axJ.js"),
  route("/users", "users", { admin: true, adminMenu: "users" }, "UsersView-Ob8Z7i7x.js"),
  route("/login-transition", "login-transition", { admin: true, adminMenu: "login-transition" }, "LoginTransitionView-Digw6oQ6.js"),
  route("/telegram-binding-reviews", "telegram-binding-reviews", { admin: true, adminMenu: "telegram-binding-reviews" }, "TelegramBindingReviewView-CZnnnRwo.js"),
  route("/user-renew-records", "user-renew-records", { admin: true, adminMenu: "user-renew-records" }, "UserRenewRecordsView-D_zhi0bI.js"),
  route("/cards", "cards", { admin: true, adminMenu: "cards" }, "CardManagementView-B6by84gn.js"),
  route("/invitations", "invitations", { admin: true, adminMenu: "invitations" }, "InvitationManagementView-DjkvePdi.js"),
  route("/user-register-records", "user-register-records", { admin: true, adminMenu: "user-register-records" }, "UserRegisterRecordsView-Ds6qIdn4.js"),
  route("/user-sync", "user-sync", { admin: true, adminMenu: "user-sync" }, "UserSyncView-V7QHg7ga.js"),
  route("/distribution", "distribution", { distributor: true, adminMenu: "distribution-dashboard" }, "DistributionView-DARFHZ1_.js"),
  route("/distribution/application", "distribution-application", { distributor: true, adminMenu: "distribution-application" }, "DistributionView-DARFHZ1_.js"),
  route("/distribution/products", "distribution-products", { distributor: true, adminMenu: "distribution-products" }, "DistributionView-DARFHZ1_.js"),
  route("/distribution/invite-records", "distribution-invite-records", { distributor: true, adminMenu: "distribution-invite-records" }, "DistributionView-DARFHZ1_.js"),
  route("/distribution/exchange-history", "distribution-exchange-history", { distributor: true, adminMenu: "distribution-exchange-history" }, "DistributionView-DARFHZ1_.js"),
  route("/distribution/cards", "distribution-cards", { distributor: true, adminMenu: "distribution-cards" }, "CardManagementView-B6by84gn.js"),
  route("/distribution/admin/reviews", "distribution-admin-reviews", { admin: true, adminMenu: "distribution-admin-reviews" }, "DistributionView-DARFHZ1_.js"),
  route("/distribution/admin/custom-reviews", "distribution-admin-custom-reviews", { admin: true, adminMenu: "distribution-admin-custom-reviews" }, "DistributionView-DARFHZ1_.js"),
  route("/distribution/admin/products", "distribution-admin-products", { admin: true, adminMenu: "distribution-admin-products" }, "DistributionView-DARFHZ1_.js"),
  route("/games", "games", { admin: true, adminMenu: "games" }, "GameManagementView-1VpH9drK.js"),
  route("/points-bot/config", "points-bot-config", { admin: true, adminMenu: "points-bot-redeem-configs" }, null, "/points-bot/redeem-configs"),
  route("/points-bot/foam-bags", "points-bot-foam-bags", { admin: true, adminMenu: "points-bot-foam-bags", foamBagAdmin: true }, "FoamBagView-f99Hy7qa.js"),
  route("/points-bot/red-packets", "points-bot-red-packets", { admin: true, adminMenu: "points-bot-red-packets" }, "RedPacketView-BBfRZwhm.js"),
  route("/points-bot/redeem-configs", "points-bot-redeem-configs", { admin: true, adminMenu: "points-bot-redeem-configs", pointsBotPage: "redeem" }, "PointsBotView-BgSmVxHF.js"),
  route("/points-bot/levels", "points-bot-levels", { admin: true, adminMenu: "points-bot-levels", pointsBotPage: "levels" }, "PointsBotView-BgSmVxHF.js"),
  route("/points-bot/prizes", "points-bot-prizes", { admin: true, adminMenu: "points-bot-prizes", pointsBotPage: "prizes" }, "PointsBotView-BgSmVxHF.js"),
  route("/points-bot/users", "points-bot-users", { admin: true, adminMenu: "points-bot-users", pointsBotPage: "users" }, "PointsBotView-BgSmVxHF.js"),
  route("/points-bot/ledgers", "points-bot-ledgers", { admin: true, adminMenu: "points-bot-ledgers", pointsBotPage: "ledgers" }, "PointsBotView-BgSmVxHF.js"),
  route("/points-bot/lotteries", "points-bot-lotteries", { admin: true, adminMenu: "points-bot-lotteries", pointsBotPage: "lotteries" }, "PointsBotView-BgSmVxHF.js"),
  route("/points-bot/lottery-entries", "points-bot-lottery-entries", { admin: true, adminMenu: "points-bot-lottery-entries", pointsBotPage: "entries" }, "PointsBotView-BgSmVxHF.js"),
  route("/playback-records", "playback-records", { admin: true, adminMenu: "playback-records" }, "PlaybackRecordsView-Di9yiIDi.js"),
  route("/playback-summary", "playback-summary", { admin: true, adminMenu: "playback-summary" }, "PlaybackSummaryView-BvOz6XFf.js"),
  route("/simultaneous-playback", "simultaneous-playback", { admin: true, adminMenu: "simultaneous-playback" }, "SimultaneousPlaybackView-BOb2cWpm.js"),
  route("/request-subscribe", "request-subscribe", { admin: true, adminMenu: "request-subscribe" }, "RequestSubscribeView-HvdiI5NQ.js"),
  route("/cover-designer", "cover-designer", { admin: true, adminMenu: "cover-designer" }, "CoverDesignerView-DjZ7Hiso.js"),
  route("/library-access", "library-access", { admin: true, adminMenu: "library-access" }, "LibraryAccessView-BJNhLM7_.js"),
  route("/tmdb-daily-release", "tmdb-daily-release", { admin: true, adminMenu: "tmdb-daily-release" }, "TmdbDailyReleaseView-Pa7CqUoF.js"),
  route("/now-playing", "now-playing", { admin: true, adminMenu: "now-playing" }, "NowPlayingView-CqYlWqFJ.js"),
  route("/servers", "servers", { admin: true, adminMenu: "servers" }, "ServersView-BFXJPBbk.js"),
  route("/notices", "notices", { admin: true, adminMenu: "notices" }, "NoticeManagementView-BtkYqCDz.js"),
  route("/emby-block-keywords", "emby-block-keywords", { admin: true, adminMenu: "emby-block-keywords" }, "EmbyBlockKeywordView-Bb1W7RxG.js"),
  route("/notify-channels", "notify-channels", { admin: true, adminMenu: "notify-channels" }, "NotifyChannelView-D22Cx6LW.js"),
  route("/notify-templates", "notify-templates", { admin: true, adminMenu: "notify-templates" }, "NotifyTemplateView-DWVeBYV_.js"),
  route("/realtime-logs", "realtime-logs", { admin: true, adminMenu: "realtime-logs" }, "RealtimeLogsView-6g_rr09y.js"),
  route("/migration", "migration", { admin: true, adminMenu: "migration" }, "FoamMigrationView-ohqEdnXL.js"),
  route("/tasks", "tasks", { admin: true, adminMenu: "tasks" }, "TaskManagementView-B7uMu26j.js"),
  route("/payment-management", "payment-management", { admin: true, primaryAdmin: true }, "PaymentManagementView-DnO6G5Gy.js"),
  route("/settings", "settings", { admin: true, adminMenu: "settings" }, "SettingsView-BTXSgF7v.js"),
  route("/:pathMatch(.*)*", null, {}, null, "/"),
];

export function defaultAdminRoute(user) {
  if (!isAdminUser(user) || isPrimaryAdminUser(user)) return "/";
  for (const menu of Array.isArray(user?.menuPermissions) ? user.menuPermissions : []) {
    const match = ROUTES.find(
      (item) => item.meta.adminMenu === menu && !item.redirect && !item.path.includes(":"),
    );
    if (match) return match.path;
  }
  return "/profile";
}

function isAdminUser(user) {
  return Number(user?.isAdmin || 0) === 1 || user?.isAdmin === true;
}

function isPrimaryAdminUser(user) {
  return Number(user?.isPrimaryAdmin || 0) === 1 || user?.isPrimaryAdmin === true;
}
