package com.una.embyhub.config.common.constants;

import com.una.embyhub.model.dto.response.notifytemplate.NotifyTemplateVariableResponse;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public enum NotifyTemplateVariableEnum {
   NAME(
      "name",
      "资源名称或通知标题",
      templates("media_photo_detail", "media_photo_message", "media_text_message"),
      messageTypes(NotifyMessageType.PHOTO_DETAIL, NotifyMessageType.PHOTO_MESSAGE, NotifyMessageType.TEXT)
   ),
   OVERVIEW(
      "overview",
      "通知正文或资源简介",
      templates("media_photo_detail", "media_photo_message", "media_text_message"),
      messageTypes(NotifyMessageType.PHOTO_DETAIL, NotifyMessageType.PHOTO_MESSAGE, NotifyMessageType.TEXT)
   ),
   TV_INFO(
      "tvInfo",
      "剧集季/集原始信息",
      templates("media_photo_detail", "media_photo_message"),
      messageTypes(NotifyMessageType.PHOTO_DETAIL, NotifyMessageType.PHOTO_MESSAGE)
   ),
   TV_INFO_BLOCK(
      "tvInfoBlock",
      "剧集季/集信息（已带换行，空内容自动忽略）",
      templates("media_photo_detail", "media_photo_message"),
      messageTypes(NotifyMessageType.PHOTO_DETAIL, NotifyMessageType.PHOTO_MESSAGE)
   ),
   DISPLAY_TITLE(
      "displayTitle",
      "原始分辨率或版本信息",
      templates("media_photo_detail", "media_photo_message"),
      messageTypes(NotifyMessageType.PHOTO_DETAIL, NotifyMessageType.PHOTO_MESSAGE)
   ),
   DISPLAY_TITLE_BLOCK(
      "displayTitleBlock",
      "分辨率信息块（已带换行）",
      templates("media_photo_detail", "media_photo_message"),
      messageTypes(NotifyMessageType.PHOTO_DETAIL, NotifyMessageType.PHOTO_MESSAGE)
   ),
   GENRES(
      "genres",
      "类型/标签原始文本（以 # 分隔）",
      templates("media_photo_detail", "media_photo_message"),
      messageTypes(NotifyMessageType.PHOTO_DETAIL, NotifyMessageType.PHOTO_MESSAGE)
   ),
   GENRES_BLOCK(
      "genresBlock",
      "类型/标签信息块（已带换行）",
      templates("media_photo_detail", "media_photo_message"),
      messageTypes(NotifyMessageType.PHOTO_DETAIL, NotifyMessageType.PHOTO_MESSAGE)
   ),
   TYPE(
      "type",
      "媒体原始类型（Movie/Episode 等）",
      templates("media_photo_detail", "media_photo_message"),
      messageTypes(NotifyMessageType.PHOTO_DETAIL, NotifyMessageType.PHOTO_MESSAGE)
   ),
   TYPE_TAG(
      "typeTag",
      "媒体类型标签（#电影/#剧集）",
      templates("media_photo_detail", "media_photo_message"),
      messageTypes(NotifyMessageType.PHOTO_DETAIL, NotifyMessageType.PHOTO_MESSAGE)
   ),
   SIZE(
      "size",
      "原始文件大小（字符串形式）",
      templates("media_photo_detail", "media_photo_message"),
      messageTypes(NotifyMessageType.PHOTO_DETAIL, NotifyMessageType.PHOTO_MESSAGE)
   ),
   SIZE_BLOCK(
      "sizeBlock",
      "文件大小信息块（自动处理空值和换行）",
      templates("media_photo_detail", "media_photo_message"),
      messageTypes(NotifyMessageType.PHOTO_DETAIL, NotifyMessageType.PHOTO_MESSAGE)
   ),
   BACKDROP_PATH(
      "backdropPath",
      "背景图/横幅图地址",
      templates("media_photo_detail", "media_photo_message"),
      messageTypes(NotifyMessageType.PHOTO_DETAIL, NotifyMessageType.PHOTO_MESSAGE)
   ),
   BACKDROP_IMAGE_BLOCK(
      "backdropImageBlock",
      "钉钉可识别的 Markdown 图片片段",
      templates("media_photo_detail", "media_photo_message"),
      messageTypes(NotifyMessageType.PHOTO_DETAIL, NotifyMessageType.PHOTO_MESSAGE)
   ),
   SERVER_URL(
      "serverUrl",
      "服务器名称（兼容历史变量，不再输出地址）",
      templates(
         "media_photo_detail",
         "media_photo_message",
         "wechat_playback_start",
         "wechat_playback_stop",
         "wechat_playback_pause",
         "auth_failed",
         "auth_success",
         "user_expiration",
         "user_disabled",
         "user_deleted",
         "simultaneous_playback",
         "request_submitted",
         "request_completed",
         "card_register_success",
         "card_renew_success",
         "invitation_register_success"
      ),
      messageTypes(NotifyMessageType.PHOTO_DETAIL, NotifyMessageType.PHOTO_MESSAGE, NotifyMessageType.TEXT)
   ),
   SERVER_NAME(
      "serverName",
      "服务器名称",
      templates(
         "media_photo_detail",
         "media_photo_message",
         "wechat_playback_start",
         "wechat_playback_stop",
         "wechat_playback_pause",
         "auth_failed",
         "auth_success",
         "user_expiration",
         "user_disabled",
         "user_deleted",
         "simultaneous_playback",
         "request_submitted",
         "request_completed",
         "card_register_success",
         "card_renew_success",
         "invitation_register_success"
      ),
      messageTypes(NotifyMessageType.PHOTO_DETAIL, NotifyMessageType.PHOTO_MESSAGE, NotifyMessageType.TEXT)
   ),
   SERVER_URL_BLOCK(
      "serverUrlBlock",
      "服务器名称信息块（已带换行）",
      templates(
         "media_photo_detail",
         "media_photo_message",
         "wechat_playback_start",
         "wechat_playback_stop",
         "wechat_playback_pause",
         "auth_failed",
         "auth_success",
         "user_expiration",
         "user_disabled",
         "user_deleted",
         "simultaneous_playback",
         "request_submitted",
         "request_completed",
         "card_register_success",
         "card_renew_success",
         "invitation_register_success"
      ),
      messageTypes(NotifyMessageType.PHOTO_DETAIL, NotifyMessageType.PHOTO_MESSAGE, NotifyMessageType.TEXT)
   ),
   IMG_URL(
      "imgUrl",
      "海报图地址",
      templates("media_photo_detail", "media_photo_message", "media_text_message"),
      messageTypes(NotifyMessageType.PHOTO_DETAIL, NotifyMessageType.PHOTO_MESSAGE, NotifyMessageType.TEXT)
   ),
   TMDB_URL(
      "tmdbUrl",
      "TMDB 详情链接",
      templates("media_photo_detail", "media_photo_message", "media_text_message"),
      messageTypes(NotifyMessageType.PHOTO_DETAIL, NotifyMessageType.PHOTO_MESSAGE, NotifyMessageType.TEXT)
   ),
   PARSE_MODE(
      "parseMode",
      "消息解析模式（Markdown、HTML 等）",
      templates("media_photo_detail", "media_photo_message", "media_text_message"),
      messageTypes(NotifyMessageType.PHOTO_DETAIL, NotifyMessageType.PHOTO_MESSAGE, NotifyMessageType.TEXT)
   ),
   PRODUCTION_YEAR(
      "productionYear",
      "出品年份",
      templates("media_photo_detail", "media_photo_message"),
      messageTypes(NotifyMessageType.PHOTO_DETAIL, NotifyMessageType.PHOTO_MESSAGE)
   ),
   SERIES_NAME(
      "seriesName",
      "剧集名称",
      templates("media_photo_detail", "media_photo_message"),
      messageTypes(NotifyMessageType.PHOTO_DETAIL, NotifyMessageType.PHOTO_MESSAGE)
   ),
   SEASON_NUMBER(
      "seasonNumber",
      "季编号（数字）",
      templates("media_photo_detail", "media_photo_message"),
      messageTypes(NotifyMessageType.PHOTO_DETAIL, NotifyMessageType.PHOTO_MESSAGE)
   ),
   EPISODE_NUMBER(
      "episodeNumber",
      "集编号（数字）",
      templates("media_photo_detail", "media_photo_message"),
      messageTypes(NotifyMessageType.PHOTO_DETAIL, NotifyMessageType.PHOTO_MESSAGE)
   ),
   EPISODE_NAME(
      "episodeName",
      "单集标题",
      templates("media_photo_detail", "media_photo_message"),
      messageTypes(NotifyMessageType.PHOTO_DETAIL, NotifyMessageType.PHOTO_MESSAGE)
   ),
   DOUBLE_LINE_BREAK(
      "doubleLineBreak",
      "两个换行符（\\n\\n）",
      templates("media_photo_detail", "media_photo_message", "media_text_message"),
      messageTypes(NotifyMessageType.PHOTO_DETAIL, NotifyMessageType.PHOTO_MESSAGE, NotifyMessageType.TEXT)
   ),
   LINE_BREAK(
      "lineBreak",
      "单个换行符（\\n）",
      templates("media_photo_detail", "media_photo_message", "media_text_message"),
      messageTypes(NotifyMessageType.PHOTO_DETAIL, NotifyMessageType.PHOTO_MESSAGE, NotifyMessageType.TEXT)
   ),
   SONG_TITLE_HTML("songTitleHtml", "歌曲模板：已转义的曲名", templates("media_photo_detail_song"), messageTypes(NotifyMessageType.PHOTO_DETAIL)),
   SONG_ALBUM_HTML("songAlbumHtml", "歌曲模板：已转义的专辑/剧集信息", templates("media_photo_detail_song"), messageTypes(NotifyMessageType.PHOTO_DETAIL)),
   SONG_TRACK_HTML("songTrackHtml", "歌曲模板：已转义的曲序/集数", templates("media_photo_detail_song"), messageTypes(NotifyMessageType.PHOTO_DETAIL)),
   SONG_MEDIA_TYPE_HTML("songMediaTypeHtml", "歌曲模板：已转义的媒体类型", templates("media_photo_detail_song"), messageTypes(NotifyMessageType.PHOTO_DETAIL)),
   SONG_YEAR_HTML("songYearHtml", "歌曲模板：已转义的发行年份", templates("media_photo_detail_song"), messageTypes(NotifyMessageType.PHOTO_DETAIL)),
   SONG_GENRE_HTML("songGenreHtml", "歌曲模板：已转义的曲风标签", templates("media_photo_detail_song"), messageTypes(NotifyMessageType.PHOTO_DETAIL)),
   SONG_QUALITY_HTML("songQualityHtml", "歌曲模板：已转义的画面版本", templates("media_photo_detail_song"), messageTypes(NotifyMessageType.PHOTO_DETAIL)),
   SONG_QUALITY_LINE_HTML("songQualityLineHtml", "歌曲模板：已转义的画面版本整行，有值才显示", templates("media_photo_detail_song"), messageTypes(NotifyMessageType.PHOTO_DETAIL)),
   SONG_AUDIO_HTML("songAudioHtml", "歌曲模板：已转义的音质信息", templates("media_photo_detail_song"), messageTypes(NotifyMessageType.PHOTO_DETAIL)),
   SONG_AUDIO_LINE_HTML("songAudioLineHtml", "歌曲模板：已转义的音质整行，有值才显示", templates("media_photo_detail_song"), messageTypes(NotifyMessageType.PHOTO_DETAIL)),
   SONG_SUBTITLE_HTML("songSubtitleHtml", "歌曲模板：已转义的字幕信息", templates("media_photo_detail_song"), messageTypes(NotifyMessageType.PHOTO_DETAIL)),
   SONG_SUBTITLE_LINE_HTML("songSubtitleLineHtml", "歌曲模板：已转义的字幕整行，有值才显示", templates("media_photo_detail_song"), messageTypes(NotifyMessageType.PHOTO_DETAIL)),
   SONG_SIZE_HTML("songSizeHtml", "歌曲模板：已转义的文件大小", templates("media_photo_detail_song"), messageTypes(NotifyMessageType.PHOTO_DETAIL)),
   SONG_RUNTIME_HTML("songRuntimeHtml", "歌曲模板：已转义的播放时长", templates("media_photo_detail_song"), messageTypes(NotifyMessageType.PHOTO_DETAIL)),
   SONG_SERVER_HTML("songServerHtml", "歌曲模板：已转义的发布站点", templates("media_photo_detail_song"), messageTypes(NotifyMessageType.PHOTO_DETAIL)),
   SONG_NOTE_HTML("songNoteHtml", "歌曲模板：已转义的创作手记/简介", templates("media_photo_detail_song"), messageTypes(NotifyMessageType.PHOTO_DETAIL)),
   PLAY_USER("playUser", "播放用户", templates("wechat_playback_start", "wechat_playback_stop"), messageTypes(NotifyMessageType.PHOTO_MESSAGE)),
   PLAY_TITLE("playTitle", "播放标题", templates("wechat_playback_start", "wechat_playback_stop"), messageTypes(NotifyMessageType.PHOTO_MESSAGE)),
   USER_LOCATION("userLocation", "用户归属地", templates("wechat_playback_start", "wechat_playback_stop"), messageTypes(NotifyMessageType.PHOTO_MESSAGE)),
   PLAY_TIME("playTime", "播放时间", templates("wechat_playback_start", "wechat_playback_stop"), messageTypes(NotifyMessageType.PHOTO_MESSAGE)),
   PLAY_POSITION("playPosition", "播放位置", templates("wechat_playback_start", "wechat_playback_stop"), messageTypes(NotifyMessageType.PHOTO_MESSAGE)),
   CLIENT_INFO(
      "clientInfo",
      "客户端信息",
      templates("wechat_playback_start", "wechat_playback_stop", "wechat_playback_pause", "auth_failed", "auth_success"),
      messageTypes(NotifyMessageType.PHOTO_MESSAGE, NotifyMessageType.TEXT)
   ),
   USER_NAME(
      "userName",
      "用户名称；卡密注册、邀请码注册、卡密续费群聊通知中会脱敏",
      templates(
         "auth_failed",
         "auth_success",
         "user_expiration",
         "user_disabled",
         "user_deleted",
         "simultaneous_playback",
         "request_submitted",
         "card_register_success",
         "card_renew_success",
         "invitation_register_success"
      ),
      messageTypes(NotifyMessageType.TEXT, NotifyMessageType.PHOTO_DETAIL)
   ),
   REGISTER_CODE("registerCode", "前几位真实、后续方块打码的注册码/授权码", templates("card_register_success", "card_renew_success"), messageTypes(NotifyMessageType.TEXT)),
   INVITATION_CODE("invitationCode", "前几位真实、后续方块打码的邀请码", templates("invitation_register_success"), messageTypes(NotifyMessageType.TEXT)),
   REGISTER_DAYS(
      "registerDays", "注册成功后的有效期，例如 30天 或 永久", templates("card_register_success", "invitation_register_success"), messageTypes(NotifyMessageType.TEXT)
   ),
   RENEW_DAYS("renewDays", "卡密续费增加的有效期，例如 30天 或 永久", templates("card_renew_success"), messageTypes(NotifyMessageType.TEXT)),
   LOGIN_TIME("loginTime", "登录时间", templates("auth_failed", "auth_success"), messageTypes(NotifyMessageType.TEXT)),
   IP_ADDRESS("ipAddress", "IP地址", templates("auth_failed", "auth_success"), messageTypes(NotifyMessageType.TEXT)),
   DEVICE("device", "设备名称", templates("auth_failed", "auth_success"), messageTypes(NotifyMessageType.TEXT)),
   EXPIRATION_DATE("expirationDate", "过期时间", templates("user_expiration", "card_renew_success"), messageTypes(NotifyMessageType.TEXT)),
   TIME_LEFT("timeLeft", "剩余时间", templates("user_expiration"), messageTypes(NotifyMessageType.TEXT)),
   REASON("reason", "原因", templates("user_disabled", "user_deleted"), messageTypes(NotifyMessageType.TEXT)),
   PLAYBACK_DETAILS("playbackDetails", "播放详情列表", templates("simultaneous_playback"), messageTypes(NotifyMessageType.TEXT)),
   REQUEST_NAME("requestName", "求片名称", templates("request_submitted", "request_completed"), messageTypes(NotifyMessageType.PHOTO_DETAIL)),
   REQUEST_OVERVIEW_BRIEF(
      "requestOverviewBrief", "求片简介摘要（最多五行，超出省略）", templates("request_submitted", "request_completed"), messageTypes(NotifyMessageType.PHOTO_DETAIL)
   ),
   YEAR("year", "年份", templates("request_submitted", "request_completed"), messageTypes(NotifyMessageType.PHOTO_DETAIL)),
   SEASON("season", "季", templates("request_submitted", "request_completed"), messageTypes(NotifyMessageType.PHOTO_DETAIL)),
   TICKET_ID("ticketId", "工单ID", templates("support_ticket_submitted"), messageTypes(NotifyMessageType.TEXT)),
   TICKET_TITLE("ticketTitle", "工单标题", templates("support_ticket_submitted"), messageTypes(NotifyMessageType.TEXT)),
   TICKET_SUBMITTER("ticketSubmitter", "工单提交人", templates("support_ticket_submitted"), messageTypes(NotifyMessageType.TEXT)),
   TICKET_SUBMIT_TIME("ticketSubmitTime", "工单提交时间", templates("support_ticket_submitted"), messageTypes(NotifyMessageType.TEXT)),
   TICKET_CONTENT("ticketContent", "工单内容", templates("support_ticket_submitted"), messageTypes(NotifyMessageType.TEXT)),
   SUBSCRIBE_NAME(
      "subscribeName", "订阅名称", templates("subscribe_download", "subscribe_organize_success", "subscribe_organize_failed"), messageTypes(NotifyMessageType.TEXT)
   ),
   MOVIE_NAME(
      "movieName", "影片名称", templates("subscribe_download", "subscribe_organize_success", "subscribe_organize_failed"), messageTypes(NotifyMessageType.TEXT)
   ),
   MEDIA_TYPE_LABEL(
      "mediaTypeLabel",
      "媒体类型（电影/剧集）",
      templates("subscribe_download", "subscribe_organize_success", "subscribe_organize_failed"),
      messageTypes(NotifyMessageType.TEXT)
   ),
   DOWNLOAD_TITLE(
      "downloadTitle", "资源标题", templates("subscribe_download", "subscribe_organize_success", "subscribe_organize_failed"), messageTypes(NotifyMessageType.TEXT)
   ),
   DOWNLOAD_SIZE("downloadSize", "资源大小", templates("subscribe_download"), messageTypes(NotifyMessageType.TEXT)),
   DOWNLOAD_SIZE_LINE("downloadSizeLine", "资源大小信息行（为空时自动忽略）", templates("subscribe_download"), messageTypes(NotifyMessageType.TEXT)),
   DOWNLOAD_STATUS("downloadStatus", "下载/整理状态", templates("subscribe_organize_success", "subscribe_organize_failed"), messageTypes(NotifyMessageType.TEXT)),
   ERROR_MESSAGE("errorMessage", "失败原因", templates("subscribe_organize_failed"), messageTypes(NotifyMessageType.TEXT)),
   SITE_NAME(
      "siteName",
      "站点名称",
      templates("subscribe_download", "subscribe_organize_success", "subscribe_organize_failed", "subscribe_added"),
      messageTypes(NotifyMessageType.TEXT)
   ),
   CLIENT_FILTER_DETAILS("clientFilterDetails", "客户端或地区拦截详情", templates("emby_client_filter"), messageTypes(NotifyMessageType.TEXT));

   private final String key;
   private final String description;
   private final List<String> templateCodes;
   private final List<NotifyMessageType> messageTypes;

   private NotifyTemplateVariableEnum(String key, String description, List<String> templateCodes, List<NotifyMessageType> messageTypes) {
      this.key = key;
      this.description = description;
      this.templateCodes = templateCodes;
      this.messageTypes = messageTypes;
   }

   public String getKey() {
      return this.key;
   }

   public NotifyTemplateVariableResponse toResponse() {
      NotifyTemplateVariableResponse response = new NotifyTemplateVariableResponse();
      response.setKey(this.key);
      response.setDescription(this.description);
      response.setTemplateCodes(this.templateCodes);
      response.setMessageTypes(this.messageTypes.stream().map(Enum::name).collect(Collectors.toList()));
      return response;
   }

   public static List<NotifyTemplateVariableResponse> toResponseList() {
      return Arrays.stream(values()).map(NotifyTemplateVariableEnum::toResponse).collect(Collectors.toList());
   }

   private static List<String> templates(String... templateCodes) {
      List<String> list = new ArrayList<>(Arrays.asList(templateCodes));
      if (list.contains("media_photo_detail") && !list.contains("media_photo_detail_song")) {
         list.add("media_photo_detail_song");
      }

      return Collections.unmodifiableList(list);
   }

   private static List<NotifyMessageType> messageTypes(NotifyMessageType... types) {
      return Collections.unmodifiableList(Arrays.asList(types));
   }
}
