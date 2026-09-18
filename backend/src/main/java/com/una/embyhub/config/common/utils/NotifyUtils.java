package com.una.embyhub.config.common.utils;

import com.una.embyhub.config.common.constants.NotifyMessageType;
import com.una.embyhub.model.dto.request.telegram.SendMessageRequest;
import com.una.embyhub.model.dto.request.telegram.SendPhotoRequest;
import com.una.embyhub.model.dto.response.embynotifydata.TelegramResponse;
import com.una.embyhub.service.DingDingService;
import com.una.embyhub.service.MessagePushService;
import com.una.embyhub.service.TelegramService;
import com.una.embyhub.service.WechatService;
import com.una.embyhub.util.MovieCardRenderer;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.imageio.ImageIO;
import lombok.Generated;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Component
public class NotifyUtils {
   @Generated
   private static final Logger log = LoggerFactory.getLogger(NotifyUtils.class);
   @Autowired
   private TelegramService telegramService;
   @Autowired
   private DingDingService dingDingService;
   @Autowired
   private TelegramClientUtils telegramClientUtils;
   @Autowired
   private WechatService wechatService;
   @Autowired
   private MessagePushService messagePushService;
   @Autowired
   private NotifyTemplateCacheLoaderUtils notifyTemplateCacheLoaderUtils;
   @Autowired
   private ConfigCacheLoaderUtils configCacheLoaderUtils;
   private static final String DATA_URL_PREFIX = "data:image/jpeg;base64,";
   private static final Pattern TELEGRAM_HTML_TAG_PATTERN = Pattern.compile(
      "(?i)<\\s*/?\\s*(b|strong|i|em|u|ins|s|strike|del|span|tg-spoiler|a|code|pre|blockquote)\\b"
   );

   public void sendTelegram(SendPhotoRequest sendPhotoRequest, String templateCode, NotifyMessageType messageType, boolean toGroup) throws TelegramApiException {
      String effectiveTemplateCode = this.resolveTelegramPhotoTemplateCode(templateCode, messageType);
      if (!this.prepareTelegramPhotoRequest(sendPhotoRequest, toGroup, effectiveTemplateCode)) {
         log.info("Telegram Bot未启用，不发送图片通知");
      } else {
         String caption = this.renderPhotoTemplate(sendPhotoRequest, effectiveTemplateCode, "telegram", messageType);
         if (StringUtils.hasText(caption)) {
            sendPhotoRequest.setCaption(caption);

            try {
               if (messageType == NotifyMessageType.PHOTO_MESSAGE) {
                  this.telegramService.sendPhotoMessage(sendPhotoRequest);
               } else {
                  this.telegramService.sendPhoto(sendPhotoRequest);
               }
            } catch (Exception var8) {
               log.error("Telegram通知发送失败", (Throwable)var8);
               throw var8 instanceof TelegramApiException ? (TelegramApiException)var8 : new TelegramApiException(var8);
            }
         }
      }
   }

   public void sendTelegram(SendMessageRequest sendMessageRequest, String templateCode, boolean toGroup) {
      if (!this.prepareTelegramMessageRequest(sendMessageRequest, toGroup, templateCode)) {
         log.info("Telegram Bot未启用，不发送文本通知");
      } else {
         String content = this.renderTextTemplate(sendMessageRequest, templateCode, "telegram");
         if (StringUtils.hasText(content)) {
            sendMessageRequest.setOverview(content);

            try {
               this.telegramService.sendMessage(sendMessageRequest);
            } catch (Exception var6) {
               log.error("Telegram通知发送失败", (Throwable)var6);
            }
         }
      }
   }

   public boolean sendTelegramToChat(SendMessageRequest sendMessageRequest, String templateCode, String chatId) {
      if (!StringUtils.hasText(chatId)) {
         return false;
      } else {
         SendMessageRequest target = this.cloneSendMessageRequest(sendMessageRequest);
         target.setChatId(chatId);
         target.setTelegramClient(this.telegramClientUtils.getTelegramClient());
         if (target.getTelegramClient() == null) {
            log.info("Telegram Bot未启用，不发送指定 Chat ID 文本通知");
            return false;
         } else {
            String content = this.renderTextTemplate(target, templateCode, "telegram");
            if (!StringUtils.hasText(content)) {
               return false;
            } else {
               target.setOverview(content);

               try {
                  return this.telegramService.sendMessage(target);
               } catch (Exception var7) {
                  log.error("Telegram指定 Chat ID 通知发送失败: chatId={}", chatId, var7);
                  return false;
               }
            }
         }
      }
   }

   public void sendDingDing(SendPhotoRequest sendPhotoRequest, String templateCode, NotifyMessageType messageType) {
      String caption = this.renderPhotoTemplate(sendPhotoRequest, templateCode, "dingding", messageType);
      if (StringUtils.hasText(caption)) {
         sendPhotoRequest.setCaption(caption);

         try {
            if (messageType == NotifyMessageType.PHOTO_MESSAGE) {
               this.dingDingService.sendPhotoMessage(sendPhotoRequest);
            } else {
               this.dingDingService.sendPhoto(sendPhotoRequest);
            }
         } catch (Exception var6) {
            log.error("钉钉通知发送失败", (Throwable)var6);
         }
      }
   }

   public void sendDingDing(SendMessageRequest sendMessageRequest, String templateCode) {
      String content = this.renderTextTemplate(sendMessageRequest, templateCode, "dingding");
      if (StringUtils.hasText(content)) {
         sendMessageRequest.setOverview(content);

         try {
            this.dingDingService.sendMessage(sendMessageRequest);
         } catch (Exception var5) {
            log.error("钉钉通知发送失败", (Throwable)var5);
         }
      }
   }

   public void sendMultiChannel(SendPhotoRequest sendPhotoRequest, String templateCode, NotifyMessageType messageType, boolean toGroup, String... channels) throws TelegramApiException {
      List<String> channelList = Arrays.asList(channels);
      NotifyUtils.CustomPoster customPoster = this.buildCustomPoster(sendPhotoRequest, messageType);
      if (channelList.contains("telegram")) {
         try {
            SendPhotoRequest telegramRequest = this.cloneSendPhotoRequest(sendPhotoRequest);
            this.applyVerticalPoster(telegramRequest, customPoster);
            this.sendTelegram(telegramRequest, templateCode, messageType, toGroup);
         } catch (TelegramApiException var9) {
            log.error("Telegram渠道发送失败", (Throwable)var9);
         }
      }

      if (channelList.contains("wechat")) {
         SendPhotoRequest wechatRequest = this.cloneSendPhotoRequest(sendPhotoRequest);
         this.applyHorizontalPoster(wechatRequest, customPoster);
         this.sendWechat(wechatRequest, templateCode, messageType, "wechat");
      }

      if (channelList.contains("wechatBot")) {
         SendPhotoRequest wechatBotRequest = this.cloneSendPhotoRequest(sendPhotoRequest);
         this.applyHorizontalPoster(wechatBotRequest, customPoster);
         this.sendWechat(wechatBotRequest, templateCode, messageType, "wechatBot");
      }

      if (channelList.contains("dingding")) {
         SendPhotoRequest dingDingRequest = this.cloneSendPhotoRequest(sendPhotoRequest);
         this.applyHorizontalPoster(dingDingRequest, customPoster);
         this.sendDingDing(dingDingRequest, templateCode, messageType);
      }

      if (channelList.contains("messagepush")) {
         SendPhotoRequest messagePushRequest = this.cloneSendPhotoRequest(sendPhotoRequest);
         this.sendMessagePush(messagePushRequest, templateCode, messageType);
      }
   }

   private NotifyUtils.CustomPoster buildCustomPoster(SendPhotoRequest sendPhotoRequest, NotifyMessageType messageType) {
      if (messageType != NotifyMessageType.PHOTO_DETAIL) {
         return null;
      } else {
         String customPosterEnabled = this.configCacheLoaderUtils.getConfigValue("custom_poster_enabled");
         if (!"true".equalsIgnoreCase(customPosterEnabled)) {
            return null;
         } else {
            try {
               MovieCardRenderer.MovieDetail movieDetail = this.buildMovieDetail(sendPhotoRequest);
               BufferedImage posterImage = this.resolvePosterImage(sendPhotoRequest);
               if (movieDetail != null && posterImage != null) {
                  byte[] horizontal = MovieCardRenderer.generateHorizontalCardToBytes(movieDetail, posterImage);
                  byte[] vertical = MovieCardRenderer.generateVerticalCardToBytes(movieDetail, posterImage);
                  return new NotifyUtils.CustomPoster(horizontal, vertical);
               } else {
                  return null;
               }
            } catch (Exception var8) {
               log.warn("生成自定义入库海报失败，使用默认海报", (Throwable)var8);
               return null;
            }
         }
      }
   }

   private MovieCardRenderer.MovieDetail buildMovieDetail(SendPhotoRequest sendPhotoRequest) {
      List<String> genres = this.parseGenres(sendPhotoRequest.getGenres());
      String releaseDate = StringUtils.hasText(sendPhotoRequest.getReleaseDate())
         ? sendPhotoRequest.getReleaseDate()
         : (sendPhotoRequest.getProductionYear() != null ? sendPhotoRequest.getProductionYear() + "-01-01" : null);
      boolean tvSeries = "episode".equalsIgnoreCase(sendPhotoRequest.getType())
         || "tv".equalsIgnoreCase(sendPhotoRequest.getType())
         || "series".equalsIgnoreCase(sendPhotoRequest.getType());
      double voteAverage = sendPhotoRequest.getVoteAverage() != null ? sendPhotoRequest.getVoteAverage() : 0.0;
      int voteCount = sendPhotoRequest.getVoteCount() != null ? sendPhotoRequest.getVoteCount() : 0;
      int runtime = sendPhotoRequest.getRuntime() != null ? sendPhotoRequest.getRuntime() : 0;
      List<String> productionCountries = this.parseProductionCountries(sendPhotoRequest.getProductionCountries());
      Map<String, String> extras = sendPhotoRequest.getExtraVariables();
      String downloadCurrent = extras != null ? extras.get("downloadTitle") : null;
      String downloadStatus = extras != null ? extras.get("downloadStatus") : null;
      String downloadError = extras != null ? extras.get("errorMessage") : null;
      Boolean success = extras != null && extras.containsKey("isSuccess") ? Boolean.valueOf(extras.get("isSuccess")) : null;
      return new MovieCardRenderer.MovieDetail(
         this.safe(sendPhotoRequest.getName()),
         this.safe(sendPhotoRequest.getName()),
         this.safe(sendPhotoRequest.getDisplayTitle()),
         this.safe(sendPhotoRequest.getOverview()),
         runtime,
         genres,
         releaseDate,
         productionCountries,
         voteAverage,
         voteCount,
         this.buildServerDisplayName(sendPhotoRequest.getServerName()),
         tvSeries,
         sendPhotoRequest.getSeasonNumber(),
         sendPhotoRequest.getEpisodeNumber(),
         downloadCurrent,
         downloadStatus,
         downloadError,
         success
      );
   }

   private List<String> parseProductionCountries(String countriesStr) {
      if (!StringUtils.hasText(countriesStr)) {
         return new ArrayList<>();
      } else {
         String[] parts = countriesStr.split("[,，/|]+");
         List<String> countries = new ArrayList<>();

         for (String part : parts) {
            if (StringUtils.hasText(part)) {
               countries.add(part.trim());
            }
         }

         return countries;
      }
   }

   private BufferedImage resolvePosterImage(SendPhotoRequest sendPhotoRequest) throws Exception {
      String imageSource = this.configCacheLoaderUtils.getConfigValue("custom_poster_image_source");
      boolean preferBackdrop = "backdrop".equalsIgnoreCase(imageSource);
      String primaryUrl = preferBackdrop ? sendPhotoRequest.getBackdropPath() : sendPhotoRequest.getImgUrl();
      String fallbackUrl = preferBackdrop ? sendPhotoRequest.getImgUrl() : sendPhotoRequest.getBackdropPath();
      if (StringUtils.hasText(primaryUrl)) {
         try {
            BufferedImage img = MovieCardRenderer.downloadPosterFromUrl(primaryUrl);
            if (img != null) {
               return img;
            }
         } catch (Exception var8) {
            log.warn("下载{}图片失败，尝试使用{}图片: {}", preferBackdrop ? "壁纸" : "海报", preferBackdrop ? "海报" : "壁纸", var8.getMessage());
         }
      }

      if (StringUtils.hasText(fallbackUrl)) {
         try {
            BufferedImage img = MovieCardRenderer.downloadPosterFromUrl(fallbackUrl);
            if (img != null) {
               return img;
            }
         } catch (Exception var7) {
            log.warn("下载{}图片也失败: {}", preferBackdrop ? "海报" : "壁纸", var7.getMessage());
         }
      }

      return sendPhotoRequest.getImgUrlInputStream() != null ? ImageIO.read(sendPhotoRequest.getImgUrlInputStream()) : null;
   }

   private SendPhotoRequest cloneSendPhotoRequest(SendPhotoRequest source) {
      SendPhotoRequest target = new SendPhotoRequest();
      target.setChatId(source.getChatId());
      target.setName(source.getName());
      target.setTvInfo(source.getTvInfo());
      target.setOverview(source.getOverview());
      target.setTmdbUrl(source.getTmdbUrl());
      target.setImgUrl(source.getImgUrl());
      target.setImgUrlInputStream(source.getImgUrlInputStream());
      target.setParseMode(source.getParseMode());
      target.setProductionYear(source.getProductionYear());
      target.setGenres(source.getGenres());
      target.setType(source.getType());
      target.setDisplayTitle(source.getDisplayTitle());
      target.setSize(source.getSize());
      target.setAudioQuality(source.getAudioQuality());
      target.setSubtitleInfo(source.getSubtitleInfo());
      target.setVoteAverage(source.getVoteAverage());
      target.setVoteCount(source.getVoteCount());
      target.setEpisodeNumber(source.getEpisodeNumber());
      target.setSeriesName(source.getSeriesName());
      target.setSeasonNumber(source.getSeasonNumber());
      target.setEpisodeName(source.getEpisodeName());
      target.setBackdropPath(source.getBackdropPath());
      target.setServerUrl(source.getServerUrl());
      target.setServerName(source.getServerName());
      target.setPlayUser(source.getPlayUser());
      target.setPlayTitle(source.getPlayTitle());
      target.setUserLocation(source.getUserLocation());
      target.setPlayTime(source.getPlayTime());
      target.setPlayPosition(source.getPlayPosition());
      target.setClientInfo(source.getClientInfo());
      target.setTelegramClient(source.getTelegramClient());
      target.setCaption(source.getCaption());
      target.setExtraVariables(source.getExtraVariables() != null ? new HashMap<>(source.getExtraVariables()) : new HashMap<>());
      target.setRuntime(source.getRuntime());
      target.setProductionCountries(source.getProductionCountries());
      target.setReleaseDate(source.getReleaseDate());
      return target;
   }

   private SendMessageRequest cloneSendMessageRequest(SendMessageRequest source) {
      SendMessageRequest target = new SendMessageRequest();
      target.setChatId(source.getChatId());
      target.setName(source.getName());
      target.setOverview(source.getOverview());
      target.setTmdbUrl(source.getTmdbUrl());
      target.setImgUrl(source.getImgUrl());
      target.setParseMode(source.getParseMode());
      target.setServerUrl(source.getServerUrl());
      target.setServerName(source.getServerName());
      target.setTelegramClient(source.getTelegramClient());
      target.setExtraVariables(source.getExtraVariables() != null ? new HashMap<>(source.getExtraVariables()) : new HashMap<>());
      return target;
   }

   private void applyHorizontalPoster(SendPhotoRequest request, NotifyUtils.CustomPoster poster) {
      if (poster != null && poster.horizontal() != null) {
         String dataUrl = "data:image/jpeg;base64," + Base64.getEncoder().encodeToString(poster.horizontal());
         request.setImgUrl(dataUrl);
         request.setBackdropPath(dataUrl);
         request.setImgUrlInputStream(null);
      }
   }

   private void applyVerticalPoster(SendPhotoRequest request, NotifyUtils.CustomPoster poster) {
      if (poster != null && poster.vertical() != null) {
         request.setImgUrlInputStream(new ByteArrayInputStream(poster.vertical()));
         request.setImgUrl(null);
         request.setBackdropPath(null);
      }
   }

   private List<String> parseGenres(String genresStr) {
      if (!StringUtils.hasText(genresStr)) {
         return new ArrayList<>();
      } else {
         String cleaned = genresStr.replace("#", " ");
         String[] parts = cleaned.split("[、,，/|\\s]+");
         List<String> genres = new ArrayList<>();

         for (String part : parts) {
            if (StringUtils.hasText(part)) {
               genres.add(part.trim());
            }
         }

         return genres;
      }
   }

   public void sendMultiChannel(SendMessageRequest sendMessageRequest, String templateCode, boolean toGroup, String... channels) {
      List<String> channelList = Arrays.asList(channels);
      if (channelList.contains("telegram")) {
         this.sendTelegram(sendMessageRequest, templateCode, toGroup);
      }

      if (channelList.contains("wechat")) {
         this.sendWechat(sendMessageRequest, templateCode, "wechat");
      }

      if (channelList.contains("wechatBot")) {
         this.sendWechat(sendMessageRequest, templateCode, "wechatBot");
      }

      if (channelList.contains("dingding")) {
         this.sendDingDing(sendMessageRequest, templateCode);
      }

      if (channelList.contains("messagepush")) {
         this.sendMessagePush(sendMessageRequest, templateCode);
      }
   }

   public void sendWechat(SendPhotoRequest sendPhotoRequest, String templateCode, NotifyMessageType messageType) {
      this.sendWechat(sendPhotoRequest, templateCode, messageType, "wechat");
   }

   public void sendWechat(SendPhotoRequest sendPhotoRequest, String templateCode, NotifyMessageType messageType, String channelType) {
      String channel = StringUtils.hasText(channelType) ? channelType : "wechat";
      String caption = this.renderPhotoTemplate(sendPhotoRequest, templateCode, channel, messageType);
      if (StringUtils.hasText(caption)) {
         sendPhotoRequest.setCaption(caption);

         try {
            boolean customPosterEnabled = "true".equalsIgnoreCase(this.configCacheLoaderUtils.getConfigValue("custom_poster_enabled"));
            if (customPosterEnabled && messageType == NotifyMessageType.PHOTO_DETAIL && this.wechatService.sendServerPhoto(sendPhotoRequest, channel)) {
               return;
            }

            if (messageType == NotifyMessageType.PHOTO_MESSAGE) {
               this.wechatService.sendPhotoMessage(sendPhotoRequest, channel);
            } else {
               this.wechatService.sendPhoto(sendPhotoRequest, channel);
            }
         } catch (Exception var8) {
            log.error("企业微信通知发送失败", (Throwable)var8);
         }
      }
   }

   public void sendWechat(SendMessageRequest sendMessageRequest, String templateCode) {
      this.sendWechat(sendMessageRequest, templateCode, "wechat");
   }

   public void sendWechat(SendMessageRequest sendMessageRequest, String templateCode, String channelType) {
      String channel = StringUtils.hasText(channelType) ? channelType : "wechat";
      String content = this.renderTextTemplate(sendMessageRequest, templateCode, channel);
      if (StringUtils.hasText(content)) {
         sendMessageRequest.setOverview(content);

         try {
            this.wechatService.sendMessage(sendMessageRequest, channel);
         } catch (Exception var7) {
            log.error("企业微信通知发送失败", (Throwable)var7);
         }
      }
   }

   public void sendMessagePush(SendPhotoRequest sendPhotoRequest, String templateCode, NotifyMessageType messageType) {
      String caption = this.renderPhotoTemplate(sendPhotoRequest, templateCode, "messagepush", messageType);
      if (StringUtils.hasText(caption)) {
         sendPhotoRequest.setCaption(caption);

         try {
            if (messageType == NotifyMessageType.PHOTO_MESSAGE) {
               this.messagePushService.sendPhotoMessage(sendPhotoRequest);
            } else {
               this.messagePushService.sendPhoto(sendPhotoRequest);
            }
         } catch (Exception var6) {
            log.error("消息推送助手通知发送失败", (Throwable)var6);
         }
      }
   }

   public void sendMessagePush(SendMessageRequest sendMessageRequest, String templateCode) {
      String content = this.renderTextTemplate(sendMessageRequest, templateCode, "messagepush");
      if (StringUtils.hasText(content)) {
         sendMessageRequest.setOverview(content);

         try {
            this.messagePushService.sendMessage(sendMessageRequest);
         } catch (Exception var5) {
            log.error("消息推送助手通知发送失败", (Throwable)var5);
         }
      }
   }

   private boolean prepareTelegramPhotoRequest(SendPhotoRequest sendPhotoRequest, boolean toGroup) {
      return this.prepareTelegramPhotoRequest(sendPhotoRequest, toGroup, null);
   }

   private boolean prepareTelegramPhotoRequest(SendPhotoRequest sendPhotoRequest, boolean toGroup, String templateCode) {
      TelegramResponse telegramResponse = this.telegramClientUtils.getTelegramResponse();
      sendPhotoRequest.setTelegramClient(this.telegramClientUtils.getTelegramClient());
      if (telegramResponse != null) {
         sendPhotoRequest.setChatId(this.resolveTelegramChatId(telegramResponse, toGroup, templateCode));
         return true;
      } else {
         return false;
      }
   }

   private boolean prepareTelegramMessageRequest(SendMessageRequest sendMessageRequest, boolean toGroup) {
      return this.prepareTelegramMessageRequest(sendMessageRequest, toGroup, null);
   }

   private boolean prepareTelegramMessageRequest(SendMessageRequest sendMessageRequest, boolean toGroup, String templateCode) {
      TelegramResponse telegramResponse = this.telegramClientUtils.getTelegramResponse();
      sendMessageRequest.setTelegramClient(this.telegramClientUtils.getTelegramClient());
      if (telegramResponse != null) {
         sendMessageRequest.setChatId(this.resolveTelegramChatId(telegramResponse, toGroup, templateCode));
         return true;
      } else {
         return false;
      }
   }

   private String resolveTelegramChatId(TelegramResponse telegramResponse, boolean toGroup, String templateCode) {
      if (!toGroup) {
         return telegramResponse.getBotChatId();
      } else {
         return this.isLibraryNotifyTemplate(templateCode) && StringUtils.hasText(telegramResponse.getLibraryNotifyChatId())
            ? telegramResponse.getLibraryNotifyChatId()
            : telegramResponse.getBotChatGroupId();
      }
   }

   private boolean isLibraryNotifyTemplate(String templateCode) {
      return "media_photo_detail".equals(templateCode) || "media_photo_detail_song".equals(templateCode) || "request_completed".equals(templateCode);
   }

   private String resolveTelegramPhotoTemplateCode(String templateCode, NotifyMessageType messageType) {
      return messageType == NotifyMessageType.PHOTO_DETAIL && "media_photo_detail".equals(templateCode) ? "media_photo_detail_song" : templateCode;
   }

   private String renderPhotoTemplate(SendPhotoRequest sendPhotoRequest, String templateCode, String channelType, NotifyMessageType messageType) {
      String template = this.notifyTemplateCacheLoaderUtils.getTemplateContent(templateCode, channelType);
      if (!StringUtils.hasText(template)) {
         log.warn("未获取到模板：code={}, channel={}, type={}", templateCode, channelType, messageType);
         return "";
      } else {
         this.applyTelegramParseMode(sendPhotoRequest, channelType, template);
         Map<String, String> variables = this.buildPhotoVariables(sendPhotoRequest, channelType);
         return TemplateRenderUtils.render(template, variables);
      }
   }

   private String renderTextTemplate(SendMessageRequest sendMessageRequest, String templateCode, String channelType) {
      String template = this.notifyTemplateCacheLoaderUtils.getTemplateContent(templateCode, channelType);
      if (!StringUtils.hasText(template)) {
         log.warn("未获取到模板：code={}, channel={}, type={}", templateCode, channelType, NotifyMessageType.TEXT);
         return "";
      } else {
         this.applyTelegramParseMode(sendMessageRequest, channelType, template);
         Map<String, String> variables = this.buildTextVariables(sendMessageRequest, channelType);
         return TemplateRenderUtils.render(template, variables);
      }
   }

   private void applyTelegramParseMode(SendPhotoRequest request, String channelType, String template) {
      if ("telegram".equals(channelType)) {
         request.setParseMode(this.resolveTelegramParseMode(request.getParseMode(), template));
      }
   }

   private void applyTelegramParseMode(SendMessageRequest request, String channelType, String template) {
      if ("telegram".equals(channelType)) {
         request.setParseMode(this.resolveTelegramParseMode(request.getParseMode(), template));
      }
   }

   private String resolveTelegramParseMode(String currentParseMode, String template) {
      if ("plain".equalsIgnoreCase(currentParseMode) || "none".equalsIgnoreCase(currentParseMode)) {
         return null;
      } else if (TELEGRAM_HTML_TAG_PATTERN.matcher(template).find()) {
         return "HTML";
      } else {
         return StringUtils.hasText(currentParseMode) && !"HTML".equalsIgnoreCase(currentParseMode) ? currentParseMode : "Markdown";
      }
   }

   private Map<String, String> buildPhotoVariables(SendPhotoRequest sendPhotoRequest, String channelType) {
      Map<String, String> variables = new HashMap<>();
      boolean hideServerUrl = "messagepush".equals(channelType);
      variables.put("name", this.safe(sendPhotoRequest.getName()));
      variables.put("overview", this.safe(sendPhotoRequest.getOverview()));
      variables.put("tvInfo", this.safe(sendPhotoRequest.getTvInfo()));
      variables.put("tvInfoBlock", StringUtils.hasText(sendPhotoRequest.getTvInfo()) ? sendPhotoRequest.getTvInfo() + "\n\n" : "");
      variables.put("displayTitle", this.safe(sendPhotoRequest.getDisplayTitle()));
      variables.put(
         "displayTitleBlock", StringUtils.hasText(sendPhotoRequest.getDisplayTitle()) ? "\ud83d\udcfa 分辨率：" + sendPhotoRequest.getDisplayTitle() + "\n" : ""
      );
      variables.put("genres", this.safe(sendPhotoRequest.getGenres()));
      variables.put("genresBlock", StringUtils.hasText(sendPhotoRequest.getGenres()) ? "\ud83c\udff7 标签：" + sendPhotoRequest.getGenres() + "\n" : "");
      variables.put("type", this.safe(sendPhotoRequest.getType()));
      variables.put("typeTag", this.buildTypeTag(sendPhotoRequest.getType()));
      variables.put("size", this.safe(sendPhotoRequest.getSize()));
      variables.put("sizeBlock", this.buildSizeBlock(sendPhotoRequest.getSize()));
      variables.put("backdropPath", this.safe(sendPhotoRequest.getBackdropPath()));
      String backdropImageBlock = "";
      if (!"wechat".equals(channelType) && !"wechatBot".equals(channelType) && StringUtils.hasText(sendPhotoRequest.getBackdropPath())) {
         backdropImageBlock = "![封面图](" + sendPhotoRequest.getBackdropPath() + ")\n";
      }

      variables.put("backdropImageBlock", backdropImageBlock);
      variables.put("imgUrl", this.safe(sendPhotoRequest.getImgUrl()));
      variables.put("tmdbUrl", this.safe(sendPhotoRequest.getTmdbUrl()));
      variables.put("parseMode", this.safe(sendPhotoRequest.getParseMode()));
      String serverDisplayName = this.buildServerDisplayName(sendPhotoRequest.getServerName());
      variables.put("serverUrl", hideServerUrl ? "" : serverDisplayName);
      variables.put("serverName", serverDisplayName);
      variables.put("serverUrlBlock", !hideServerUrl && StringUtils.hasText(serverDisplayName) ? "\ud83c\udf10 服务器名称：" + serverDisplayName + "\n\n" : "");
      variables.put("playUser", this.safe(sendPhotoRequest.getPlayUser()));
      variables.put("playTitle", this.safe(sendPhotoRequest.getPlayTitle()));
      variables.put("userLocation", this.safe(sendPhotoRequest.getUserLocation()));
      variables.put("playTime", this.safe(sendPhotoRequest.getPlayTime()));
      variables.put("playPosition", this.safe(sendPhotoRequest.getPlayPosition()));
      variables.put("clientInfo", this.safe(sendPhotoRequest.getClientInfo()));
      variables.put("productionYear", sendPhotoRequest.getProductionYear() != null ? String.valueOf(sendPhotoRequest.getProductionYear()) : "");
      variables.put("seriesName", this.safe(sendPhotoRequest.getSeriesName()));
      variables.put("seasonNumber", sendPhotoRequest.getSeasonNumber() > 0 ? String.valueOf(sendPhotoRequest.getSeasonNumber()) : "");
      variables.put("episodeNumber", sendPhotoRequest.getEpisodeNumber() > 0 ? String.valueOf(sendPhotoRequest.getEpisodeNumber()) : "");
      variables.put("doubleLineBreak", "\n\n");
      variables.put("lineBreak", "\n");
      variables.put("releaseDate", this.safe(sendPhotoRequest.getReleaseDate()));
      this.addSongLibraryVariables(variables, sendPhotoRequest, serverDisplayName);
      if (sendPhotoRequest.getExtraVariables() != null) {
         variables.putAll(sendPhotoRequest.getExtraVariables());
      }

      if (!StringUtils.hasText(variables.get("mediaTypeLabel"))) {
         variables.put("mediaTypeLabel", "movie".equalsIgnoreCase(sendPhotoRequest.getType()) ? "电影" : "剧集");
      }

      return variables;
   }

   private void addSongLibraryVariables(Map<String, String> variables, SendPhotoRequest request, String serverDisplayName) {
      String songTitle = this.buildSongTitle(request.getName());
      String songAlbum = this.buildSongAlbum(request, songTitle);
      String songTrack = this.buildSongTrack(request);
      String mediaTypeName = this.buildMediaTypeName(request.getType());
      String productionYearText = this.buildProductionYearText(request);
      String genresText = this.buildPlainGenres(request.getGenres());
      String qualityText = this.safe(request.getDisplayTitle());
      String audioText = this.safe(request.getAudioQuality());
      String subtitleText = this.safe(request.getSubtitleInfo());
      String librarySizeText = this.buildLibrarySizeText(request.getSize());
      String runtimeText = "";
      String songNote = this.safe(request.getOverview());
      variables.put("songTitle", songTitle);
      variables.put("songAlbum", songAlbum);
      variables.put("songTrack", songTrack);
      variables.put("songMediaType", mediaTypeName);
      variables.put("songYear", productionYearText);
      variables.put("songGenre", genresText);
      variables.put("songQuality", qualityText);
      variables.put("songAudio", audioText);
      variables.put("songSubtitle", subtitleText);
      variables.put("songSize", librarySizeText);
      variables.put("songRuntime", runtimeText);
      variables.put("songServer", serverDisplayName);
      variables.put("songNote", songNote);
      this.putHtmlVariable(variables, "songTitleHtml", songTitle);
      this.putHtmlVariable(variables, "songAlbumHtml", songAlbum);
      this.putHtmlVariable(variables, "songTrackHtml", songTrack);
      this.putHtmlVariable(variables, "songMediaTypeHtml", mediaTypeName);
      this.putHtmlVariable(variables, "songYearHtml", productionYearText);
      this.putHtmlVariable(variables, "songGenreHtml", genresText);
      this.putHtmlVariable(variables, "songQualityHtml", qualityText);
      this.putHtmlVariable(variables, "songAudioHtml", audioText);
      this.putHtmlVariable(variables, "songSubtitleHtml", subtitleText);
      this.putHtmlVariable(variables, "songSizeHtml", librarySizeText);
      this.putHtmlVariable(variables, "songRuntimeHtml", runtimeText);
      this.putHtmlVariable(variables, "songServerHtml", serverDisplayName);
      this.putHtmlVariable(variables, "songNoteHtml", songNote);
      this.putOptionalHtmlLine(variables, "songQualityLineHtml", "\ud83c\udfa7 画面版本：", qualityText);
      this.putOptionalHtmlLine(variables, "songAudioLineHtml", "\ud83c\udf9a️ 音质：", audioText);
      this.putOptionalHtmlLine(variables, "songSubtitleLineHtml", "\ud83d\udcac 字幕：", subtitleText);
      this.putOptionalHtmlLine(variables, "songRuntimeLineHtml", "\ud83e\udd41 播放时长：", runtimeText);
   }

   private String buildSongTitle(String name) {
      String text = this.firstContentLine(name);
      return StringUtils.hasText(text) ? this.truncateText(text, 80) : "未获取到影片名称";
   }

   private String buildSongAlbum(SendPhotoRequest request, String songTitle) {
      if (!"Movie".equalsIgnoreCase(request.getType()) && !"movie".equalsIgnoreCase(request.getType())) {
         String tvInfo = this.firstContentLine(request.getTvInfo());
         return StringUtils.hasText(tvInfo) ? this.truncateText(tvInfo, 80) : songTitle;
      } else {
         return "电影正片";
      }
   }

   private String buildSongTrack(SendPhotoRequest request) {
      if (!"Movie".equalsIgnoreCase(request.getType()) && !"movie".equalsIgnoreCase(request.getType())) {
         String text = (this.safe(request.getName()) + "\n" + this.safe(request.getTvInfo())).trim();
         int season = request.getSeasonNumber() > 0 ? request.getSeasonNumber() : 1;
         Matcher chineseSeasonMatcher = Pattern.compile("第\\s*(\\d+)\\s*季").matcher(text);
         if (chineseSeasonMatcher.find()) {
            season = this.parseIntOrDefault(chineseSeasonMatcher.group(1), season);
         }

         List<int[]> detailRanges = this.parseEpisodeRanges(this.extractEpisodeDetailText(request), season, true);
         String detailEpisodeText = this.formatSeasonEpisodeRanges(detailRanges);
         if (StringUtils.hasText(detailEpisodeText)) {
            return detailEpisodeText;
         } else {
            List<int[]> tvInfoRanges = this.parseEpisodeRanges(this.safe(request.getTvInfo()), season, true);
            String tvInfoEpisodeText = this.formatSeasonEpisodeRanges(tvInfoRanges);
            if (StringUtils.hasText(tvInfoEpisodeText)) {
               return tvInfoEpisodeText;
            } else {
               List<int[]> textRanges = this.parseEpisodeRanges(text, season, false);
               String textEpisodeText = this.formatSeasonEpisodeRanges(textRanges);
               if (StringUtils.hasText(textEpisodeText)) {
                  return textEpisodeText;
               } else {
                  return request.getSeasonNumber() > 0 && request.getEpisodeNumber() > 0
                     ? this.formatSeasonEpisode(request.getSeasonNumber(), request.getEpisodeNumber(), null)
                     : "剧集汇总";
               }
            }
         }
      } else {
         return "正片";
      }
   }

   private String extractEpisodeDetailText(SendPhotoRequest request) {
      StringBuilder builder = new StringBuilder();
      this.appendLine(builder, this.safe(request.getTvInfo()));
      this.appendEpisodeDetailSegment(builder, this.safe(request.getName()));
      return builder.toString().trim();
   }

   private void appendEpisodeDetailSegment(StringBuilder builder, String text) {
      if (StringUtils.hasText(text)) {
         String marker = "集数详情：";
         int markerIndex = text.indexOf(marker);
         if (markerIndex >= 0) {
            this.appendLine(builder, text.substring(markerIndex + marker.length()));
         }
      }
   }

   private void appendLine(StringBuilder builder, String value) {
      if (StringUtils.hasText(value)) {
         if (builder.length() > 0) {
            builder.append('\n');
         }

         builder.append(value);
      }
   }

   private List<int[]> parseEpisodeRanges(String text, int fallbackSeason, boolean includeLooseTokens) {
      List<int[]> ranges = new ArrayList<>();
      if (!StringUtils.hasText(text)) {
         return ranges;
      } else {
         Matcher seasonEpisodeMatcher = Pattern.compile("(?i)S\\s*(\\d{1,2})\\s*E\\s*(\\d{1,3})(?:\\s*[-~至到]+\\s*E?\\s*(\\d{1,3}))?").matcher(text);

         while (seasonEpisodeMatcher.find()) {
            this.addEpisodeRange(
               ranges,
               this.parseIntOrDefault(seasonEpisodeMatcher.group(1), fallbackSeason),
               this.parseIntOrDefault(seasonEpisodeMatcher.group(2), 0),
               this.parseIntOrDefault(seasonEpisodeMatcher.group(3), 0)
            );
         }

         Matcher chineseEpisodeMatcher = Pattern.compile("第\\s*(\\d{1,3})(?:\\s*[-~至到]+\\s*(\\d{1,3}))?\\s*集").matcher(text);

         while (chineseEpisodeMatcher.find()) {
            this.addEpisodeRange(
               ranges, fallbackSeason, this.parseIntOrDefault(chineseEpisodeMatcher.group(1), 0), this.parseIntOrDefault(chineseEpisodeMatcher.group(2), 0)
            );
         }

         if (includeLooseTokens) {
            String compactText = text.replaceAll("(?i)S\\s*\\d{1,2}\\s*E", " ")
               .replaceAll("(?i)\\bE\\s*(?=\\d)", " ")
               .replaceAll("第\\s*\\d{1,3}(?:\\s*[-~至到]+\\s*\\d{1,3})?\\s*集", " ")
               .replaceAll("新增\\s*\\d+\\s*集", " ")
               .replaceAll("新增内容汇总", " ");
            Matcher looseEpisodeMatcher = Pattern.compile("(?<!\\d)(\\d{1,3})(?:\\s*[-~至到]+\\s*(\\d{1,3}))?(?!\\d)").matcher(compactText);

            while (looseEpisodeMatcher.find()) {
               this.addEpisodeRange(
                  ranges, fallbackSeason, this.parseIntOrDefault(looseEpisodeMatcher.group(1), 0), this.parseIntOrDefault(looseEpisodeMatcher.group(2), 0)
               );
            }
         }

         return ranges;
      }
   }

   private void addEpisodeRange(List<int[]> ranges, int season, int start, int end) {
      if (ranges != null && start > 0) {
         ranges.add(new int[]{Math.max(season, 1), start, end > start ? end : start});
      }
   }

   private String buildMediaTypeName(String type) {
      if ("Movie".equalsIgnoreCase(type) || "movie".equalsIgnoreCase(type)) {
         return "电影";
      } else if ("Episode".equalsIgnoreCase(type)) {
         return "单集";
      } else if (!"Series".equalsIgnoreCase(type) && !"tv".equalsIgnoreCase(type)) {
         return StringUtils.hasText(type) ? type : "未知";
      } else {
         return "剧集";
      }
   }

   private String buildProductionYearText(SendPhotoRequest request) {
      if (request.getProductionYear() != null) {
         return String.valueOf(request.getProductionYear());
      } else {
         String releaseDate = request.getReleaseDate();
         return StringUtils.hasText(releaseDate) && releaseDate.length() >= 4 ? releaseDate.substring(0, 4) : "未记录";
      }
   }

   private String buildPlainGenres(String genres) {
      if (!StringUtils.hasText(genres)) {
         return "未标注";
      } else {
         String[] parts = genres.replace("#", " ").split("[、,，/|\\s]+");
         List<String> values = new ArrayList<>();

         for (String part : parts) {
            if (StringUtils.hasText(part)) {
               values.add(part.trim());
            }
         }

         return values.isEmpty() ? "未标注" : String.join(" / ", values);
      }
   }

   private String buildLibrarySizeText(String size) {
      return StringUtils.hasText(size) && !"0".equals(size) ? size : "未记录";
   }

   private String firstContentLine(String value) {
      if (!StringUtils.hasText(value)) {
         return "";
      } else {
         String normalized = value.replace("\r", "\n")
            .replace("\ud83d\udc26\u200d\ud83d\udd25", "")
            .replace("\ud83e\udddd\ud83c\udffb\u200d♀️", "")
            .replace("\ud83c\udfcf 集数详情：", "")
            .trim();

         for (String line : normalized.split("\\n+")) {
            String cleaned = line.replaceAll("\\s+", " ").trim();
            if (StringUtils.hasText(cleaned)) {
               return cleaned;
            }
         }

         return "";
      }
   }

   private String truncateText(String value, int maxLength) {
      if (value != null && value.length() > maxLength) {
         return value.substring(0, Math.max(0, maxLength - 1)) + "…";
      } else {
         return value == null ? "" : value;
      }
   }

   private String formatSeasonEpisode(int season, int start, Integer end) {
      String prefix = String.format("S%02dE%02d", Math.max(season, 1), start);
      return end != null && end > start ? prefix + String.format("-E%02d", end) : prefix;
   }

   private String formatSeasonEpisodeRanges(List<int[]> ranges) {
      if (ranges != null && !ranges.isEmpty()) {
         ranges.sort((left, right) -> {
            int seasonCompare = Integer.compare(left[0], right[0]);
            return seasonCompare != 0 ? seasonCompare : Integer.compare(left[1], right[1]);
         });
         List<int[]> merged = new ArrayList<>();

         for (int[] range : ranges) {
            if (range != null && range.length >= 3 && range[1] > 0) {
               int season = Math.max(range[0], 1);
               int start = range[1];
               int end = Math.max(range[2], start);
               if (merged.isEmpty()) {
                  merged.add(new int[]{season, start, end});
               } else {
                  int[] last = merged.get(merged.size() - 1);
                  if (last[0] == season && start <= last[2] + 1) {
                     last[2] = Math.max(last[2], end);
                  } else {
                     merged.add(new int[]{season, start, end});
                  }
               }
            }
         }

         if (merged.isEmpty()) {
            return "";
         } else {
            boolean singleSeason = merged.stream().allMatch(rangex -> rangex[0] == merged.get(0)[0]);
            List<String> parts = new ArrayList<>();

            for (int index = 0; index < merged.size(); index++) {
               int[] rangex = merged.get(index);
               if (singleSeason && index > 0) {
                  parts.add(this.formatEpisodeOnlyRange(rangex[1], rangex[2]));
               } else {
                  parts.add(this.formatSeasonEpisode(rangex[0], rangex[1], rangex[2] > rangex[1] ? rangex[2] : null));
               }

               if (parts.size() >= 5) {
                  break;
               }
            }

            return String.join(" / ", parts);
         }
      } else {
         return "";
      }
   }

   private String formatEpisodeOnlyRange(int start, int end) {
      return end > start ? String.format("E%02d-E%02d", start, end) : String.format("E%02d", start);
   }

   private int parseIntOrDefault(String value, int fallback) {
      try {
         return Integer.parseInt(value);
      } catch (Exception var4) {
         return fallback;
      }
   }

   private void putHtmlVariable(Map<String, String> variables, String key, String value) {
      variables.put(key, this.escapeTelegramHtml(value));
   }

   private void putOptionalHtmlLine(Map<String, String> variables, String key, String label, String value) {
      variables.put(key, StringUtils.hasText(value) ? label + this.escapeTelegramHtml(value) + "\n" : "");
   }

   private String escapeTelegramHtml(String value) {
      return this.safe(value).replace("&", "&amp;").replace("<", "&lt;").replace(">", "&gt;");
   }

   private Map<String, String> buildTextVariables(SendMessageRequest sendMessageRequest, String channelType) {
      Map<String, String> variables = new HashMap<>();
      boolean hideServerUrl = "messagepush".equals(channelType);
      variables.put("name", this.safe(sendMessageRequest.getName()));
      variables.put("overview", this.safe(sendMessageRequest.getOverview()));
      variables.put("tmdbUrl", this.safe(sendMessageRequest.getTmdbUrl()));
      variables.put("imgUrl", this.safe(sendMessageRequest.getImgUrl()));
      variables.put("parseMode", this.safe(sendMessageRequest.getParseMode()));
      String serverDisplayName = this.buildServerDisplayName(sendMessageRequest.getServerName());
      variables.put("serverUrl", hideServerUrl ? "" : serverDisplayName);
      variables.put("serverName", serverDisplayName);
      variables.put("serverUrlBlock", !hideServerUrl && StringUtils.hasText(serverDisplayName) ? "\ud83c\udf10 服务器名称：" + serverDisplayName + "\n\n" : "");
      variables.put("doubleLineBreak", "\n\n");
      variables.put("lineBreak", "\n");
      if (sendMessageRequest.getExtraVariables() != null) {
         variables.putAll(sendMessageRequest.getExtraVariables());
      }

      return variables;
   }

   private String buildTypeTag(String type) {
      if (!StringUtils.hasText(type)) {
         return "#电影";
      } else {
         return "Movie".equalsIgnoreCase(type) ? "#电影" : "#剧集";
      }
   }

   private String buildSizeBlock(String size) {
      return StringUtils.hasText(size) && !"0".equals(size) ? "\ud83d\udce6 文件大小：" + size + "\n\n" : "\n";
   }

   private String buildServerDisplayName(String serverName) {
      return StringUtils.hasText(serverName) ? serverName.trim() : "未知服务器";
   }

   private String safe(String value) {
      return value == null ? "" : value;
   }

   private static record CustomPoster(byte[] horizontal, byte[] vertical) {
   }
}
