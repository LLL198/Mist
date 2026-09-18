package com.una.embyhub.config.handler;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.una.embyhub.config.common.enums.AdminMenuKey;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;

@Component
public class AdminMenuPolicyRegistry {
   private static final Map<String, Set<String>> CONTROLLER_DEFAULTS = new HashMap<>();
   private static final Map<String, AdminMenuPolicyRegistry.Policy> METHOD_RULES = new HashMap<>();

   public AdminMenuPolicyRegistry.Policy resolve(HandlerMethod handlerMethod) {
      String controller = handlerMethod.getBeanType().getSimpleName();
      String methodKey = methodKey(controller, handlerMethod.getMethod().getName());
      AdminMenuPolicyRegistry.Policy explicit = METHOD_RULES.get(methodKey);
      if (explicit != null) {
         return explicit;
      } else if (!this.requiresAdminPermission(handlerMethod)) {
         return AdminMenuPolicyRegistry.Policy.notEnforced();
      } else {
         Set<String> inherited = CONTROLLER_DEFAULTS.get(controller);
         return inherited == null ? new AdminMenuPolicyRegistry.Policy(true, false, Set.of()) : new AdminMenuPolicyRegistry.Policy(true, false, inherited);
      }
   }

   private boolean requiresAdminPermission(HandlerMethod handlerMethod) {
      SaCheckPermission methodPermission = AnnotatedElementUtils.findMergedAnnotation(handlerMethod.getMethod(), SaCheckPermission.class);
      SaCheckPermission classPermission = AnnotatedElementUtils.findMergedAnnotation(handlerMethod.getBeanType(), SaCheckPermission.class);
      return this.containsAdmin(methodPermission) || this.containsAdmin(classPermission);
   }

   private boolean containsAdmin(SaCheckPermission permission) {
      return permission != null && Arrays.asList(permission.value()).contains("admin");
   }

   private static void registerPointsBotRules() {
      registerMethods("PointsBotAdminController", AdminMenuKey.POINTS_BOT_RED_PACKETS, "selectRedPacket", "listRedPacketClaims", "cancelRedPacket");
      registerMethods("PointsBotAdminController", AdminMenuKey.POINTS_BOT_FOAM_BAGS, "selectFoamBag", "getFoamBagConfig", "updateFoamBagConfig");
      registerMethods("PointsBotAdminController", AdminMenuKey.GAMES, "updateGame", "topUpHellDiceVault");
      registerMethod("PointsBotAdminController", "listGames", AdminMenuKey.GAMES, AdminMenuKey.NOTIFY_CHANNELS);
      registerMethods("PointsBotAdminController", AdminMenuKey.POINTS_BOT_USERS, "selectUser", "adjustUserPoints", "getUserStats");
      registerMethods("PointsBotAdminController", AdminMenuKey.POINTS_BOT_LEDGERS, "selectLedger", "getLedgerStats", "translateReason", "getReasonMappings");
      registerMethods("PointsBotAdminController", AdminMenuKey.POINTS_BOT_LOTTERIES, "selectLottery", "getLotteryStats");
      registerMethods("PointsBotAdminController", AdminMenuKey.POINTS_BOT_LOTTERY_ENTRIES, "selectLotteryEntry", "getLotteryEntryStats");
      registerMethods(
         "PointsBotAdminController",
         AdminMenuKey.POINTS_BOT_REDEEM_CONFIGS,
         "selectRedeemConfig",
         "insertRedeemConfig",
         "updateRedeemConfig",
         "deleteRedeemConfig",
         "getRedeemConfigStats"
      );
      registerMethods(
         "PointsBotAdminController",
         AdminMenuKey.POINTS_BOT_LEVELS,
         "selectLevelConfig",
         "insertLevelConfig",
         "updateLevelConfig",
         "deleteLevelConfig",
         "getLevelConfigStats"
      );
      registerMethods(
         "PointsBotAdminController",
         AdminMenuKey.POINTS_BOT_PRIZES,
         "selectPrizeConfig",
         "insertPrizeConfig",
         "updatePrizeConfig",
         "deletePrizeConfig",
         "getPrizeConfigStats"
      );
      registerMethod(
         "PointsBotAdminController",
         "reload",
         AdminMenuKey.GAMES,
         AdminMenuKey.POINTS_BOT_REDEEM_CONFIGS,
         AdminMenuKey.POINTS_BOT_LEVELS,
         AdminMenuKey.POINTS_BOT_PRIZES,
         AdminMenuKey.POINTS_BOT_USERS,
         AdminMenuKey.POINTS_BOT_LEDGERS,
         AdminMenuKey.POINTS_BOT_LOTTERIES,
         AdminMenuKey.POINTS_BOT_LOTTERY_ENTRIES,
         AdminMenuKey.POINTS_BOT_RED_PACKETS,
         AdminMenuKey.POINTS_BOT_FOAM_BAGS,
         AdminMenuKey.NOTIFY_CHANNELS
      );
   }

   private static void registerController(String controller, AdminMenuKey... menuKeys) {
      CONTROLLER_DEFAULTS.put(controller, keys(menuKeys));
   }

   private static void registerMethods(String controller, AdminMenuKey menuKey, String... methods) {
      for (String method : methods) {
         registerMethod(controller, method, menuKey);
      }
   }

   private static void registerMethod(String controller, String method, AdminMenuKey... menuKeys) {
      METHOD_RULES.put(methodKey(controller, method), new AdminMenuPolicyRegistry.Policy(true, false, keys(menuKeys)));
   }

   private static void registerSharedMethod(String controller, String method, AdminMenuKey... menuKeys) {
      METHOD_RULES.put(methodKey(controller, method), new AdminMenuPolicyRegistry.Policy(true, true, keys(menuKeys)));
   }

   private static Set<String> keys(AdminMenuKey... menuKeys) {
      LinkedHashSet<String> keys = new LinkedHashSet<>();

      for (AdminMenuKey menuKey : menuKeys) {
         keys.add(menuKey.getKey());
      }

      return Set.copyOf(keys);
   }

   private static String methodKey(String controller, String method) {
      return controller + "#" + method;
   }

   static {
      registerController("CardSecurityManagementController", AdminMenuKey.CARDS);
      registerController("EmbyBlockKeywordController", AdminMenuKey.EMBY_BLOCK_KEYWORDS);
      registerController("EmbyBossMigrationController", AdminMenuKey.MIGRATION);
      registerController("EmbyController", AdminMenuKey.SETTINGS);
      registerController("EmbyDeviceController", AdminMenuKey.SETTINGS);
      registerController("EmbyInfoController", AdminMenuKey.SERVERS);
      registerController("EmbyIpLocationsController", AdminMenuKey.PLAYBACK_SUMMARY);
      registerController("EmbyLibraryAccessController", AdminMenuKey.LIBRARY_ACCESS);
      registerController("EmbyNotifyDataController", AdminMenuKey.SETTINGS);
      registerController("EmbyUserController", AdminMenuKey.USERS);
      registerController("EmbyUserMultiCreateController", AdminMenuKey.USERS);
      registerController("EmbyUserRegisterRecordController", AdminMenuKey.USER_REGISTER_RECORDS);
      registerController("EmbyUserRenewRecordController", AdminMenuKey.USER_RENEW_RECORDS);
      registerController("FoamDataMigrationController", AdminMenuKey.MIGRATION);
      registerController("HostLineController", AdminMenuKey.SERVERS);
      registerController("InvitationCodeController", AdminMenuKey.INVITATIONS);
      registerController("LoginTransitionController", AdminMenuKey.LOGIN_TRANSITION);
      registerController("MoviePilotController", AdminMenuKey.REQUEST_SUBSCRIBE);
      registerController("NotifyChannelController", AdminMenuKey.NOTIFY_CHANNELS);
      registerController("NotifyTemplateController", AdminMenuKey.NOTIFY_TEMPLATES);
      registerController("NullbrController", AdminMenuKey.SETTINGS);
      registerController("PlayRecordsController", AdminMenuKey.PLAYBACK_RECORDS);
      registerController("RequestListController", AdminMenuKey.REQUEST_RECORDS);
      registerController("RequestPackagesCardSecurityManagementController", AdminMenuKey.SETTINGS);
      registerController("RequestPackagesController", AdminMenuKey.SETTINGS);
      registerController("RoseController", AdminMenuKey.USERS);
      registerController("ScheduledTaskController", AdminMenuKey.TASKS);
      registerController("SimultaneousPlaybackRecordController", AdminMenuKey.SIMULTANEOUS_PLAYBACK);
      registerController("SpringBootLogController", AdminMenuKey.REALTIME_LOGS);
      registerController("SupportTicketController", AdminMenuKey.TICKETS);
      registerController("SysNoticeController", AdminMenuKey.NOTICES);
      registerController("SystemConfigController", AdminMenuKey.SETTINGS);
      registerController("SystemToolController", AdminMenuKey.SERVERS);
      registerController("TelegramBindingReviewController", AdminMenuKey.TELEGRAM_BINDING_REVIEWS);
      registerController("TmdbDailyReleaseController", AdminMenuKey.TMDB_DAILY_RELEASE);
      registerController("TmdbTrackController", AdminMenuKey.REQUEST_SUBSCRIBE);
      registerController("UserAnalysisController", AdminMenuKey.USER_ANALYSIS);
      registerController("WeChatIpController", AdminMenuKey.SETTINGS);
      registerController("WechatBotController", AdminMenuKey.SETTINGS);
      registerController("PlaybackReportingController", AdminMenuKey.PLAYBACK_RECORDS);
      registerController("FoamThemeConfigController", AdminMenuKey.SETTINGS);
      registerController("MoviePtDownloadController", AdminMenuKey.REQUEST_SUBSCRIBE);
      registerController("MoviePtSearchController", AdminMenuKey.REQUEST_SUBSCRIBE);
      registerController("MoviePtSiteController", AdminMenuKey.REQUEST_SUBSCRIBE);
      registerController("MoviePtSubscribeController", AdminMenuKey.REQUEST_SUBSCRIBE);
      registerController("MovieQbittorrentController", AdminMenuKey.REQUEST_SUBSCRIBE);
      registerController("MovieScrapePathConfigController", AdminMenuKey.REQUEST_SUBSCRIBE);
      registerMethod("EmbyUserController", "select", AdminMenuKey.USERS, AdminMenuKey.SETTINGS);
      registerMethod("EmbyUserController", "syncUserBetweenServers", AdminMenuKey.USER_SYNC);
      registerMethod("EmbyInfoController", "select", AdminMenuKey.values());
      registerMethod("EmbyController", "getNowPlaying", AdminMenuKey.NOW_PLAYING);
      registerSharedMethod("DashboardController", "popularMovies", AdminMenuKey.DASHBOARD);
      registerSharedMethod("FoamEmbyController", "libraryCounts", AdminMenuKey.DASHBOARD, AdminMenuKey.LIBRARY);
      registerSharedMethod("FoamEmbyController", "latestLibrary", AdminMenuKey.DASHBOARD);
      registerSharedMethod("FoamEmbyController", "libraryPage", AdminMenuKey.LIBRARY);
      registerSharedMethod("FoamEmbyController", "itemDetail", AdminMenuKey.LIBRARY, AdminMenuKey.REQUESTS);
      registerSharedMethod("FoamEmbyController", "seasonList", AdminMenuKey.LIBRARY, AdminMenuKey.REQUESTS);
      registerSharedMethod("FoamEmbyController", "episodeList", AdminMenuKey.LIBRARY, AdminMenuKey.REQUESTS);
      registerSharedMethod("RequestListController", "select", AdminMenuKey.DASHBOARD, AdminMenuKey.REQUESTS, AdminMenuKey.REQUEST_RECORDS);
      registerSharedMethod("RequestListController", "todayCount", AdminMenuKey.DASHBOARD, AdminMenuKey.REQUESTS, AdminMenuKey.REQUEST_RECORDS);
      registerSharedMethod("RequestListController", "insertRequestList", AdminMenuKey.REQUESTS);
      registerSharedMethod("RequestListController", "updateRequestList", AdminMenuKey.REQUESTS);
      registerSharedMethod("RequestListController", "deleteByRequestListId", AdminMenuKey.REQUESTS);
      registerSharedMethod("SupportTicketController", "select", AdminMenuKey.TICKETS);
      registerSharedMethod("SupportTicketController", "submit", AdminMenuKey.TICKETS);
      registerSharedMethod("SupportTicketController", "detail", AdminMenuKey.TICKETS);
      registerSharedMethod("SupportTicketController", "reply", AdminMenuKey.TICKETS);
      registerMethod("DistributionController", "saveProduct", AdminMenuKey.DISTRIBUTION_ADMIN_PRODUCTS);
      registerMethod("DistributionController", "deleteProduct", AdminMenuKey.DISTRIBUTION_ADMIN_PRODUCTS);
      registerMethod("DistributionController", "reviewApplication", AdminMenuKey.DISTRIBUTION_ADMIN_REVIEWS);
      registerMethod("DistributionController", "reviewCustomExchange", AdminMenuKey.DISTRIBUTION_ADMIN_CUSTOM_REVIEWS);
      registerMethod("DistributionController", "setDistributor", AdminMenuKey.USERS);
      registerSharedMethod("DistributionController", "listProducts", AdminMenuKey.DISTRIBUTION_PRODUCTS, AdminMenuKey.DISTRIBUTION_ADMIN_PRODUCTS);
      registerSharedMethod("DistributionController", "myApplications", AdminMenuKey.DISTRIBUTION_APPLICATION, AdminMenuKey.DISTRIBUTION_ADMIN_REVIEWS);
      registerSharedMethod("DistributionController", "statistics", AdminMenuKey.DISTRIBUTION_APPLICATION, AdminMenuKey.DISTRIBUTION_ADMIN_REVIEWS);
      registerSharedMethod(
         "DistributionController", "queryCustomExchange", AdminMenuKey.DISTRIBUTION_EXCHANGE_HISTORY, AdminMenuKey.DISTRIBUTION_ADMIN_CUSTOM_REVIEWS
      );
      registerSharedMethod("DistributionController", "queryPointsRecords", AdminMenuKey.DISTRIBUTION_EXCHANGE_HISTORY);
      registerSharedMethod("DistributionController", "getMyPoints", AdminMenuKey.DISTRIBUTION_PRODUCTS);
      registerSharedMethod("DistributionController", "exchange", AdminMenuKey.DISTRIBUTION_PRODUCTS);
      registerSharedMethod("DistributionController", "submitApplication", AdminMenuKey.DISTRIBUTION_APPLICATION);
      registerSharedMethod("DistributionController", "deleteApplication", AdminMenuKey.DISTRIBUTION_APPLICATION);
      registerSharedMethod("DistributionController", "getDashboard", AdminMenuKey.DISTRIBUTION_DASHBOARD);
      registerSharedMethod("EmbyUserController", "selectDistributor", AdminMenuKey.DISTRIBUTION_INVITE_RECORDS);
      registerSharedMethod("EmbyUserController", "serverUserStatsDistributor", AdminMenuKey.DISTRIBUTION_INVITE_RECORDS);
      registerMethod("CardSecurityManagementController", "select", AdminMenuKey.CARDS, AdminMenuKey.DISTRIBUTION_CARDS);
      registerMethod("CardSecurityManagementController", "cardSecurityManagementListStatus", AdminMenuKey.CARDS, AdminMenuKey.DISTRIBUTION_CARDS);
      registerSharedMethod("CardSecurityManagementController", "selectDistributor", AdminMenuKey.DISTRIBUTION_CARDS);
      registerSharedMethod("CardSecurityManagementController", "cardSecurityManagementListStatusDistributor", AdminMenuKey.DISTRIBUTION_CARDS);
      registerMethod("FoamEmbyController", "mediaFolders", AdminMenuKey.LIBRARY_ACCESS, AdminMenuKey.COVER_DESIGNER);
      registerMethod("FoamEmbyController", "libraryItems", AdminMenuKey.COVER_DESIGNER);
      registerMethod("FoamEmbyController", "replaceItemCover", AdminMenuKey.COVER_DESIGNER);
      registerMethod("FoamEmbyController", "speedTestMediaWithAdminCredential", AdminMenuKey.SERVERS);
      registerPointsBotRules();
      registerMethod("ScheduledTaskController", "listAll", AdminMenuKey.TASKS);
      registerMethod("SysNoticeController", "select", AdminMenuKey.NOTICES);
   }

   public static record Policy(boolean enforced, boolean allowNonAdministrator, Set<String> requiredMenuKeys) {
      private static AdminMenuPolicyRegistry.Policy notEnforced() {
         return new AdminMenuPolicyRegistry.Policy(false, false, Set.of());
      }
   }
}
