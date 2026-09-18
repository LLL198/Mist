package com.una.embyhub.config.common.enums;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public enum AdminMenuKey {
   DASHBOARD("dashboard", "仪表盘", "开始", "/"),
   LIBRARY("library", "媒体库", "开始", "/library"),
   REQUESTS("requests", "求片中心", "开始", "/requests"),
   TICKETS("tickets", "工单中心", "开始", "/tickets"),
   REQUEST_RECORDS("request-records", "求片管理", "用户", "/request-records"),
   REQUEST_SUBSCRIBE("request-subscribe", "求片订阅", "用户", "/request-subscribe"),
   USERS("users", "用户管理", "用户", "/users"),
   TELEGRAM_BINDING_REVIEWS("telegram-binding-reviews", "TG绑定审批", "用户", "/telegram-binding-reviews"),
   USER_ANALYSIS("user-analysis", "用户分析", "用户", "/user-analysis"),
   USER_RENEW_RECORDS("user-renew-records", "续费记录", "用户", "/user-renew-records"),
   CARDS("cards", "卡密管理", "用户", "/cards"),
   INVITATIONS("invitations", "邀请管理", "用户", "/invitations"),
   USER_REGISTER_RECORDS("user-register-records", "注册记录", "用户", "/user-register-records"),
   USER_SYNC("user-sync", "同步用户", "用户", "/user-sync"),
   DISTRIBUTION_DASHBOARD("distribution-dashboard", "分销总览", "分销", "/distribution"),
   DISTRIBUTION_APPLICATION("distribution-application", "我要进货", "分销", "/distribution/application"),
   DISTRIBUTION_PRODUCTS("distribution-products", "积分商城", "分销", "/distribution/products"),
   DISTRIBUTION_INVITE_RECORDS("distribution-invite-records", "注册用户", "分销", "/distribution/invite-records"),
   DISTRIBUTION_EXCHANGE_HISTORY("distribution-exchange-history", "兑换历史", "分销", "/distribution/exchange-history"),
   DISTRIBUTION_CARDS("distribution-cards", "卡密管理", "分销", "/distribution/cards"),
   DISTRIBUTION_ADMIN_REVIEWS("distribution-admin-reviews", "进货审批", "分销", "/distribution/admin/reviews"),
   DISTRIBUTION_ADMIN_CUSTOM_REVIEWS("distribution-admin-custom-reviews", "兑换审批", "分销", "/distribution/admin/custom-reviews"),
   DISTRIBUTION_ADMIN_PRODUCTS("distribution-admin-products", "商品管理", "分销", "/distribution/admin/products"),
   GAMES("games", "游戏管理", "游戏", "/games"),
   POINTS_BOT_REDEEM_CONFIGS("points-bot-redeem-configs", "兑换配置", "娱乐", "/points-bot/redeem-configs"),
   POINTS_BOT_LEVELS("points-bot-levels", "等级配置", "娱乐", "/points-bot/levels"),
   POINTS_BOT_PRIZES("points-bot-prizes", "奖品配置", "娱乐", "/points-bot/prizes"),
   POINTS_BOT_USERS("points-bot-users", "用户积分", "娱乐", "/points-bot/users"),
   POINTS_BOT_LEDGERS("points-bot-ledgers", "积分流水", "娱乐", "/points-bot/ledgers"),
   POINTS_BOT_LOTTERIES("points-bot-lotteries", "抽奖记录", "娱乐", "/points-bot/lotteries"),
   POINTS_BOT_LOTTERY_ENTRIES("points-bot-lottery-entries", "参与记录", "娱乐", "/points-bot/lottery-entries"),
   POINTS_BOT_RED_PACKETS("points-bot-red-packets", "红包记录", "娱乐", "/points-bot/red-packets"),
   POINTS_BOT_FOAM_BAGS("points-bot-foam-bags", "雾袋管理", "娱乐", "/points-bot/foam-bags"),
   NOW_PLAYING("now-playing", "正在播放", "播放", "/now-playing"),
   PLAYBACK_SUMMARY("playback-summary", "播放汇总", "播放", "/playback-summary"),
   PLAYBACK_RECORDS("playback-records", "播放记录", "播放", "/playback-records"),
   SIMULTANEOUS_PLAYBACK("simultaneous-playback", "同播检测", "播放", "/simultaneous-playback"),
   SERVERS("servers", "服务器", "系统", "/servers"),
   LIBRARY_ACCESS("library-access", "媒体库分级", "系统", "/library-access"),
   COVER_DESIGNER("cover-designer", "媒体封面", "系统", "/cover-designer"),
   TMDB_DAILY_RELEASE("tmdb-daily-release", "追新日历", "系统", "/tmdb-daily-release"),
   NOTICES("notices", "系统公告", "系统", "/notices"),
   EMBY_BLOCK_KEYWORDS("emby-block-keywords", "访问拦截", "系统", "/emby-block-keywords"),
   NOTIFY_CHANNELS("notify-channels", "通知渠道", "系统", "/notify-channels"),
   NOTIFY_TEMPLATES("notify-templates", "通知模板", "系统", "/notify-templates"),
   REALTIME_LOGS("realtime-logs", "实时日志", "系统", "/realtime-logs"),
   MIGRATION("migration", "数据库迁移", "系统", "/migration"),
   TASKS("tasks", "任务管理", "系统", "/tasks"),
   LOGIN_TRANSITION("login-transition", "登录转场", "系统", "/login-transition"),
   SETTINGS("settings", "设置", "系统", "/settings");

   private static final Map<String, AdminMenuKey> BY_KEY;
   private final String key;
   private final String title;
   private final String section;
   private final String route;

   private AdminMenuKey(String key, String title, String section, String route) {
      this.key = key;
      this.title = title;
      this.section = section;
      this.route = route;
   }

   public String getKey() {
      return this.key;
   }

   public String getTitle() {
      return this.title;
   }

   public String getSection() {
      return this.section;
   }

   public String getRoute() {
      return this.route;
   }

   public String permissionCode() {
      return "admin-menu:" + this.key;
   }

   public static Optional<AdminMenuKey> fromKey(String key) {
      return Optional.ofNullable(BY_KEY.get(key));
   }

   public static List<String> allKeys() {
      return Arrays.stream(values()).map(AdminMenuKey::getKey).toList();
   }

   static {
      Map<String, AdminMenuKey> values = new LinkedHashMap<>();

      for (AdminMenuKey item : values()) {
         values.put(item.key, item);
      }

      BY_KEY = Map.copyOf(values);
   }
}
