package com.una.embyhub.job;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.resource.ResourceUtil;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.una.embyhub.config.common.utils.ConfigCacheLoaderUtils;
import com.una.embyhub.config.common.utils.EmbyInfoCacheManagerUtils;
import com.una.embyhub.config.common.utils.EmbyUrlUtils;
import com.una.embyhub.config.common.utils.TelegramClientUtils;
import com.una.embyhub.config.job.ScheduledTaskMeta;
import com.una.embyhub.model.dto.response.embynotifydata.TelegramResponse;
import com.una.embyhub.model.dto.response.playbackreporting.UserPlaylistResponse;
import com.una.embyhub.service.PlaybackRankingConfigService;
import com.una.embyhub.service.playbackreporting.PlaybackReportingService;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.RenderingHints;
import java.awt.geom.RoundRectangle2D.Float;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.Character.UnicodeBlock;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.TimeZone;
import java.util.Map.Entry;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;
import lombok.Generated;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.OkHttpClient.Builder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
@EnableScheduling
public class EmbyPlaybackReportingRankingJob {
   @Generated
   private static final Logger log = LoggerFactory.getLogger(EmbyPlaybackReportingRankingJob.class);
   static final int TELEGRAM_PHOTO_SAFE_BYTES = 9500000;
   @Autowired
   private EmbyInfoCacheManagerUtils embyInfoCacheManager;
   @Autowired
   private TelegramClientUtils telegramClientUtils;
   @Autowired
   private ConfigCacheLoaderUtils configCacheLoaderUtils;
   @Autowired
   private PlaybackReportingService playbackReportingService;
   @Autowired
   private PlaybackRankingPosterImageResolver rankingPosterImageResolver;
   @Autowired
   private PlaybackRankingConfigService playbackRankingConfigService;
   private final ThreadLocal<EmbyInfoCacheManagerUtils.EmbyServerConfig> currentConfig = new ThreadLocal<>();
   public final TimeZone ZONE = TimeZone.getTimeZone("Asia/Shanghai");
   public final boolean PREFER_MOVIE_BG = true;
   public final int IMG_WIDTH = 1200;
   public final int IMG_HEIGHT = 1600;
   public final int PADDING = 48;
   public final int COLUMN_GAP = 32;
   public final int SECTION_GAP = 28;
   public final int LINE_GAP = 16;
   public final int FOOTER_H = 380;
   public final int MAX_MOVIE_IN_IMAGE = 12;
   public final int MAX_SERIES_IN_IMAGE = 12;
   public final int MAX_VIEWERS_IN_IMAGE = 10;
   public final String CUSTOM_FONT_PATH = null;
   public final int VIEWER_COLS = 4;
   public final int VIEWER_ROWS = 2;
   public final OkHttpClient HTTP = new Builder().readTimeout(60L, TimeUnit.SECONDS).build();
   public final ObjectMapper MAPPER = new ObjectMapper().disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
   private final PlaybackRankingPosterRenderer landscapePosterRenderer = new PlaybackRankingPosterRenderer();
   private static final Pattern YEAR_ANY = Pattern.compile("(?<!\\d)(?:19|20)\\d{2}(?!\\d)");
   private static final Pattern QUALITY_TAGS = Pattern.compile(
      "(?i)\\b(4k|8k|1080p|2160p|hdr|dv|dolby\\s*vision|atmos|hevc|h26[45]|x265|web[- ]?dl|bluray|bdrip|remux|内封|外挂|中字|简中|繁中|国配|国语|粤语|国粤|双语|多音轨|重制|重制版|修复|修复版|加长|加长版|导演剪辑版|未分级|无删减|完整版|extended\\s*cut|director'?s\\s*cut|uncut|remastered|ultimate\\s*edition|tv版|剧场版|电影版|网络版|海外版|合集|合集版|全集|总集篇)\\b"
   );
   private static final Pattern SEASON_TAGS = Pattern.compile("(?i)(第\\s*[一二三四五六七八九十百零两0-9]+\\s*季|season\\s*\\d+|\\bs\\s*\\d+)");

   private EmbyInfoCacheManagerUtils.EmbyServerConfig getConfig() {
      return Optional.ofNullable(this.currentConfig.get()).orElseGet(this.embyInfoCacheManager::getRequiredConfig);
   }

   private String getServerUrl() {
      return this.getConfig().url();
   }

   private String getApiKey() {
      return this.getConfig().apiKey();
   }

   @Scheduled(
      cron = "0 0 1 * * *",
      zone = "Asia/Shanghai"
   )
   @ScheduledTaskMeta(
      name = "排行榜任务",
      remark = "Emby每日播放排行榜"
   )
   public void configureTasks() throws IOException {
      log.info("每日播放排行定时任务：{}", DateUtil.formatDateTime(new Date()));
      TelegramResponse telegramResponse = this.telegramClientUtils.getTelegramResponse();
      if (telegramResponse == null) {
         log.info("每日播放排行定时任务未配置Telegram，跳过执行");
      } else {
         List<EmbyInfoCacheManagerUtils.EmbyServerConfig> serverConfigs = this.embyInfoCacheManager.getEnabledConfigs();
         if (serverConfigs == null || serverConfigs.isEmpty()) {
            serverConfigs = List.of(this.embyInfoCacheManager.getRequiredConfig());
         }

         for (EmbyInfoCacheManagerUtils.EmbyServerConfig serverConfig : serverConfigs) {
            this.currentConfig.set(serverConfig);

            try {
               Set<String> excludedUserIds = this.playbackRankingConfigService.excludedUserIds(serverConfig.id());
               Calendar cal = Calendar.getInstance(this.ZONE);
               cal.add(5, -1);
               Date targetDate = cal.getTime();
               String endDateStr = DateUtil.format(targetDate, "yyyy-MM-dd");
               List<UserPlaylistResponse> playlist = null;

               try {
                  playlist = this.playbackReportingService.getUserPlaylist(serverConfig.id(), null, false, null, 1, endDateStr, "Movie,Episode");
               } catch (Exception var38) {
                  log.error("获取服务器[{}]播放记录失败", serverConfig.serverName(), var38);
                  continue;
               }

               if (playlist != null && !playlist.isEmpty()) {
                  Map<String, EmbyPlaybackReportingRankingJob.MovieAgg> movieAggMap = new HashMap<>();
                  Map<String, EmbyPlaybackReportingRankingJob.SeriesAgg> seriesAggMap = new HashMap<>();
                  Map<String, EmbyPlaybackReportingRankingJob.ViewerRow> viewers = new HashMap<>();
                  Date targetDayStart = this.startOfDay(targetDate);
                  Date targetDayEndExclusive = this.addDays(targetDayStart, 1);

                  for (UserPlaylistResponse r : playlist) {
                     if (!shouldExcludePlayback(r, excludedUserIds)) {
                        Date playDate = this.parseDateAndTime(r.getDate(), r.getTime());
                        if (this.isWithinDay(playDate, targetDayStart, targetDayEndExclusive)) {
                           long seconds = 0L;

                           try {
                              if (r.getDuration() != null) {
                                 seconds = Long.parseLong(r.getDuration());
                              }
                           } catch (NumberFormatException var37) {
                           }

                           if (seconds > 0L) {
                              String uid = r.getUserId();
                              String uname = r.getUserName();
                              String nickName = r.getNickName();
                              String displayName = nickName != null && !nickName.isBlank() ? nickName : uname;
                              if (uid == null) {
                                 uid = "unknown";
                              }

                              EmbyPlaybackReportingRankingJob.ViewerRow v = viewers.computeIfAbsent(
                                 uid, k -> new EmbyPlaybackReportingRankingJob.ViewerRow(k, displayName)
                              );
                              v.totalSeconds += seconds;
                              v.playCount++;
                              if (v.firstSeen == null || playDate.before(v.firstSeen)) {
                                 v.firstSeen = playDate;
                              }

                              if (v.lastSeen == null || playDate.after(v.lastSeen)) {
                                 v.lastSeen = playDate;
                              }

                              String type = r.getItemType();
                              String itemName = r.getItemName();
                              String itemId = r.getItemId() != null ? String.valueOf(r.getItemId()) : null;
                              if ("Movie".equalsIgnoreCase(type)) {
                                 String norm = this.normalizeMovieTitle(itemName);
                                 EmbyPlaybackReportingRankingJob.MovieAgg agg = movieAggMap.computeIfAbsent(
                                    norm, k -> new EmbyPlaybackReportingRankingJob.MovieAgg(itemName)
                                 );
                                 agg.totalSeconds += seconds;
                                 agg.playCount++;
                                 if (itemId != null) {
                                    agg.secondsByMovieId.merge(itemId, seconds, Long::sum);
                                 }

                                 agg.secondsByDisplayName.merge(itemName, seconds, Long::sum);
                              } else if ("Episode".equalsIgnoreCase(type)) {
                                 String seriesName = this.extractSeriesName(itemName);
                                 String norm = this.normalizeSeriesTitle(seriesName);
                                 EmbyPlaybackReportingRankingJob.SeriesAgg agg = seriesAggMap.computeIfAbsent(
                                    norm, k -> new EmbyPlaybackReportingRankingJob.SeriesAgg(seriesName)
                                 );
                                 agg.totalSeconds += seconds;
                                 String epCode = this.extractEpCode(itemName);
                                 if (epCode != null) {
                                    if (agg.episodes.add(epCode)) {
                                       agg.playCount++;
                                    }
                                 } else {
                                    agg.playCount++;
                                 }

                                 if (itemId != null) {
                                    agg.secondsBySeriesId.merge(itemId, seconds, Long::sum);
                                 }

                                 agg.secondsByDisplayName.merge(seriesName, seconds, Long::sum);
                              }
                           }
                        }
                     }
                  }

                  List<EmbyPlaybackReportingRankingJob.MovieRow> movieRows = new ArrayList<>();

                  for (EmbyPlaybackReportingRankingJob.MovieAgg aggx : movieAggMap.values()) {
                     String bestId = null;
                     long bestSec = -1L;

                     for (Entry<String, Long> e : aggx.secondsByMovieId.entrySet()) {
                        if (e.getValue() > bestSec) {
                           bestSec = e.getValue();
                           bestId = e.getKey();
                        }
                     }

                     String bestName = this.chooseBestNamePreferCJK(aggx.secondsByDisplayName);
                     EmbyPlaybackReportingRankingJob.MovieRow row = new EmbyPlaybackReportingRankingJob.MovieRow(bestId, bestName);
                     row.watchedSeconds = aggx.totalSeconds;
                     row.playCount = aggx.playCount;
                     movieRows.add(row);
                  }

                  List<EmbyPlaybackReportingRankingJob.SeriesRow> seriesRows = new ArrayList<>();

                  for (EmbyPlaybackReportingRankingJob.SeriesAgg aggx : seriesAggMap.values()) {
                     String bestId = null;
                     long bestSec = -1L;

                     for (Entry<String, Long> ex : aggx.secondsBySeriesId.entrySet()) {
                        if (ex.getValue() > bestSec) {
                           bestSec = ex.getValue();
                           bestId = ex.getKey();
                        }
                     }

                     String bestName = this.chooseBestNamePreferCJK(aggx.secondsByDisplayName);
                     EmbyPlaybackReportingRankingJob.SeriesRow row = new EmbyPlaybackReportingRankingJob.SeriesRow(bestId, bestName);
                     row.totalSeconds = aggx.totalSeconds;
                     row.playCount = aggx.playCount > 0 ? aggx.playCount : 1;
                     row.episodes = aggx.episodes;
                     seriesRows.add(row);
                  }

                  movieRows.sort(Comparator.<EmbyPlaybackReportingRankingJob.MovieRow>comparingLong(rx -> rx.watchedSeconds).reversed());
                  seriesRows.sort(Comparator.<EmbyPlaybackReportingRankingJob.SeriesRow>comparingLong(rx -> rx.totalSeconds).reversed());
                  List<EmbyPlaybackReportingRankingJob.ViewerRow> viewerRows = new ArrayList<>(viewers.values());
                  viewerRows.sort(
                     Comparator.<EmbyPlaybackReportingRankingJob.ViewerRow>comparingLong(vx -> vx.totalSeconds)
                        .reversed()
                        .thenComparingInt(vx -> -vx.playCount)
                  );
                  byte[] png = this.renderConfiguredLeaderboardImage(movieRows, seriesRows, viewerRows, targetDate);
                  String sendToBotConfig = this.configCacheLoaderUtils.getConfigValue("ranking_send_to_bot");
                  boolean sendToBot = sendToBotConfig != null;
                  String targetChatId = sendToBot ? telegramResponse.getBotChatId() : telegramResponse.getBotChatGroupId();
                  if (targetChatId != null && !targetChatId.isBlank()) {
                     this.sendTelegramPhoto(telegramResponse.getBotToken(), targetChatId, png, "leaderboard.png", null);
                     log.info("已发送排行海报，服务器：{}，发送目标：{}", serverConfig.url(), sendToBot ? "机器人" : "群聊");
                  } else {
                     log.warn("目标 Chat ID 未配置，sendToBot={}", sendToBot);
                  }
               } else {
                  log.info("服务器[{}]昨日无播放记录", serverConfig.serverName());
               }
            } catch (Exception var39) {
               log.error("每日播放排行任务异常", (Throwable)var39);
            } finally {
               this.currentConfig.remove();
            }
         }
      }
   }

   static boolean shouldExcludePlayback(UserPlaylistResponse playback, Set<String> excludedUserIds) {
      return playback != null && excludedUserIds != null && !excludedUserIds.isEmpty() && playback.getUserId() != null
         ? excludedUserIds.contains(playback.getUserId().trim())
         : false;
   }

   public void sendTelegramPhoto(String token, String chatId, byte[] imageBytes, String filename, String caption) throws IOException {
      EmbyPlaybackReportingRankingJob.TelegramPhotoPayload payload = prepareTelegramPhotoPayload(imageBytes, filename);
      if (payload.converted()) {
         log.warn("排行榜 PNG 超过 Telegram 照片安全大小，已转换为高质量 JPEG：beforeBytes={}, afterBytes={}", imageBytes.length, payload.bytes().length);
      }

      String url = "https://api.telegram.org/bot" + token + "/sendPhoto";
      okhttp3.MultipartBody.Builder mb = new okhttp3.MultipartBody.Builder().setType(MultipartBody.FORM).addFormDataPart("chat_id", chatId);
      if (caption != null && !caption.isEmpty()) {
         mb.addFormDataPart("caption", caption);
      }

      mb.addFormDataPart("photo", payload.filename(), RequestBody.create(payload.bytes(), MediaType.parse(payload.mediaType())));
      Request req = new okhttp3.Request.Builder().url(url).post(mb.build()).build();

      try (Response r = this.HTTP.newCall(req).execute()) {
         if (!r.isSuccessful()) {
            throw new IOException("Telegram HTTP " + r.code());
         }
      }
   }

   static EmbyPlaybackReportingRankingJob.TelegramPhotoPayload prepareTelegramPhotoPayload(byte[] imageBytes, String filename) throws IOException {
      return prepareTelegramPhotoPayload(imageBytes, filename, 9500000);
   }

   static EmbyPlaybackReportingRankingJob.TelegramPhotoPayload prepareTelegramPhotoPayload(byte[] imageBytes, String filename, int maxBytes) throws IOException {
      if (imageBytes != null && imageBytes.length != 0) {
         String resolvedFilename = filename != null && !filename.isBlank() ? filename : "leaderboard.png";
         if (imageBytes.length <= maxBytes) {
            return new EmbyPlaybackReportingRankingJob.TelegramPhotoPayload(imageBytes, resolvedFilename, "image/png", false);
         } else {
            BufferedImage source = ImageIO.read(new ByteArrayInputStream(imageBytes));
            if (source == null) {
               throw new IOException("排行榜图片无法解码");
            } else {
               String jpegFilename = resolvedFilename.replaceFirst("(?i)\\.png$", "") + ".jpg";
               byte[] candidate = null;

               for (float quality : new float[]{0.94F, 0.9F, 0.86F, 0.82F, 0.78F}) {
                  candidate = encodeJpeg(source, quality);
                  if (candidate.length <= maxBytes) {
                     return new EmbyPlaybackReportingRankingJob.TelegramPhotoPayload(candidate, jpegFilename, "image/jpeg", true);
                  }
               }

               throw new IOException("排行榜图片压缩后仍超过 Telegram 照片大小限制：bytes=" + candidate.length);
            }
         }
      } else {
         throw new IOException("排行榜图片内容为空");
      }
   }

   private static byte[] encodeJpeg(BufferedImage source, float quality) throws IOException {
      BufferedImage rgb = source.getType() == 1 ? source : new BufferedImage(source.getWidth(), source.getHeight(), 1);
      if (rgb != source) {
         Graphics2D graphics = rgb.createGraphics();
         graphics.setColor(Color.BLACK);
         graphics.fillRect(0, 0, rgb.getWidth(), rgb.getHeight());
         graphics.drawImage(source, 0, 0, null);
         graphics.dispose();
      }

      ImageWriter writer = ImageIO.getImageWritersByFormatName("jpeg").next();
      ByteArrayOutputStream output = new ByteArrayOutputStream();

      try (ImageOutputStream imageOutput = ImageIO.createImageOutputStream(output)) {
         writer.setOutput(imageOutput);
         ImageWriteParam parameters = writer.getDefaultWriteParam();
         parameters.setCompressionMode(2);
         parameters.setCompressionQuality(quality);
         writer.write(null, new IIOImage(rgb, null, null), parameters);
      } finally {
         writer.dispose();
      }

      return output.toByteArray();
   }

   private Date parseDateAndTime(String date, String time) {
      if (date != null && time != null) {
         try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            sdf.setTimeZone(this.ZONE);
            return sdf.parse(date + " " + time);
         } catch (Exception var4) {
            return null;
         }
      } else {
         return null;
      }
   }

   private Date startOfDay(Date date) {
      Calendar cal = Calendar.getInstance(this.ZONE);
      cal.setTime(date);
      cal.set(11, 0);
      cal.set(12, 0);
      cal.set(13, 0);
      cal.set(14, 0);
      return cal.getTime();
   }

   private Date addDays(Date date, int days) {
      Calendar cal = Calendar.getInstance(this.ZONE);
      cal.setTime(date);
      cal.add(5, days);
      return cal.getTime();
   }

   private boolean isWithinDay(Date date, Date startInclusive, Date endExclusive) {
      return date != null && !date.before(startInclusive) && date.before(endExclusive);
   }

   private String extractSeriesName(String itemName) {
      if (itemName == null) {
         return "未知剧名";
      } else {
         int idx = itemName.indexOf(" - ");
         return idx > 0 ? itemName.substring(0, idx) : itemName;
      }
   }

   private String extractEpCode(String itemName) {
      if (itemName == null) {
         return null;
      } else {
         Matcher m = Pattern.compile("(?i)(S\\d+E\\d+)").matcher(itemName);
         return m.find() ? m.group(1).toUpperCase() : null;
      }
   }

   public byte[] renderConfiguredLeaderboardImage(
      List<EmbyPlaybackReportingRankingJob.MovieRow> movieRows,
      List<EmbyPlaybackReportingRankingJob.SeriesRow> seriesRows,
      List<EmbyPlaybackReportingRankingJob.ViewerRow> viewerRows,
      Date targetDate
   ) throws IOException {
      PlaybackRankingPosterStyle style = PlaybackRankingPosterStyle.fromConfigValue(this.configCacheLoaderUtils.getConfigValue("playback_ranking_poster_style"));
      if (style == PlaybackRankingPosterStyle.CLASSIC_PORTRAIT) {
         return this.renderLeaderboardImageWithPosterBG(movieRows, seriesRows, viewerRows, targetDate);
      } else {
         try {
            List<PlaybackRankingPosterRenderer.MediaRank> movieRanks = new ArrayList<>();

            for (EmbyPlaybackReportingRankingJob.MovieRow row : movieRows.stream().limit(5L).toList()) {
               EmbyPlaybackReportingRankingJob.RankingArtwork artwork = this.fetchMovieArtwork(row);
               movieRanks.add(new PlaybackRankingPosterRenderer.MediaRank(row.title, row.watchedSeconds, row.playCount, artwork.poster(), artwork.backdrop()));
            }

            List<PlaybackRankingPosterRenderer.MediaRank> seriesRanks = new ArrayList<>();

            for (EmbyPlaybackReportingRankingJob.SeriesRow row : seriesRows.stream().limit(4L).toList()) {
               EmbyPlaybackReportingRankingJob.RankingArtwork artwork = this.fetchSeriesArtwork(row);
               seriesRanks.add(
                  new PlaybackRankingPosterRenderer.MediaRank(row.seriesName, row.totalSeconds, row.playCount, artwork.poster(), artwork.backdrop())
               );
            }

            BufferedImage backdrop = this.selectRankingBackground(movieRanks, seriesRanks);
            List<PlaybackRankingPosterRenderer.ViewerRank> viewerRanks = viewerRows.stream()
               .limit(10L)
               .map(row -> new PlaybackRankingPosterRenderer.ViewerRank(row.userName, row.totalSeconds, row.playCount, row.firstSeen, row.lastSeen))
               .toList();
            return this.landscapePosterRenderer
               .render(style, movieRanks, seriesRanks, viewerRanks, targetDate, backdrop, this.buildServerLabel(this.getConfig()));
         } catch (Exception var11) {
            log.error("排行榜横幅海报生成失败，回退经典长图：style={}", style.configValue(), var11);
            return this.renderLeaderboardImageWithPosterBG(movieRows, seriesRows, viewerRows, targetDate);
         }
      }
   }

   public byte[] renderLeaderboardImageWithPosterBG(
      List<EmbyPlaybackReportingRankingJob.MovieRow> movieRows,
      List<EmbyPlaybackReportingRankingJob.SeriesRow> seriesRows,
      List<EmbyPlaybackReportingRankingJob.ViewerRow> viewerRows,
      Date targetDate
   ) throws IOException {
      InputStream DEFAULT_BG_IMAGE_PATH = ResourceUtil.getStream("img/暂无今日排行.png");
      boolean hasRankingData = !movieRows.isEmpty() || !seriesRows.isEmpty() || !viewerRows.isEmpty();
      BufferedImage bg = this.fetchRankingBackground(movieRows, seriesRows);
      if (bg == null && hasRankingData) {
         log.warn("排行榜有数据但未加载到媒体背景，使用渐变背景：movies={}, series={}, viewers={}", movieRows.size(), seriesRows.size(), viewerRows.size());
      }

      if (bg == null && !hasRankingData && DEFAULT_BG_IMAGE_PATH != null) {
         try {
            bg = ImageIO.read(DEFAULT_BG_IMAGE_PATH);
         } catch (Exception var9) {
         }
      }

      return this.renderClassicPortraitImage(movieRows, seriesRows, viewerRows, targetDate, bg, this.buildServerLabel(this.getConfig()));
   }

   byte[] renderClassicPortraitImage(
      List<EmbyPlaybackReportingRankingJob.MovieRow> movieRows,
      List<EmbyPlaybackReportingRankingJob.SeriesRow> seriesRows,
      List<EmbyPlaybackReportingRankingJob.ViewerRow> viewerRows,
      Date targetDate,
      BufferedImage bg,
      String serverLabel
   ) throws IOException {
      BufferedImage img = new BufferedImage(1200, 1600, 2);
      Graphics2D g = img.createGraphics();
      this.enableAA(g);
      this.drawOriginalBackdrop(g, bg, 1200, 1600);
      this.drawClassicPortraitBackdropScrim(g);
      int panelX = 48;
      int panelY = 48;
      int panelW = 1104;
      int contentBottom = 1172;
      Font base = this.loadFontOrDefault(20.0F);
      Font h1 = base.deriveFont(1, 48.0F);
      Font h2 = base.deriveFont(1, 34.0F);
      Font rankFont = base.deriveFont(1, 28.0F);
      Font itemFont = base.deriveFont(26.0F);
      Font metaFont = base.deriveFont(22.0F);
      Font footerTitleFont = base.deriveFont(1, 30.0F);
      Font footerItemFont = base.deriveFont(22.0F);
      int x = panelX + 36;
      int y = panelY + 52;
      g.setFont(h1);
      g.setColor(new Color(0, 0, 0, 150));
      g.drawString("Emby 今日排行榜", x + 2, y + 3);
      g.setColor(Color.WHITE);
      g.drawString("Emby 今日排行榜", x, y);
      SimpleDateFormat fmt = new SimpleDateFormat("yyyy年MM月dd日（EEE）", Locale.CHINA);
      String dateText = fmt.format(targetDate);
      int pillUsedH = this.drawDatePill(g, dateText, x, y + 16, base.deriveFont(1, 36.0F));
      int contentTop = y + pillUsedH + 70;
      int columnWidth = (panelW - 72 - 32) / 2;
      int col2X = x + columnWidth + 32;
      int movieCount = Math.min(12, movieRows.size());
      int seriesCount = Math.min(12, seriesRows.size());
      int yy = this.drawSectionHeader(g, "\ud83c\udfac 电影 Top " + movieCount, h2, x, contentTop);
      yy += 8;
      yy = this.drawMovieList(g, movieRows, movieCount, x, yy, columnWidth, rankFont, itemFont, metaFont);
      int yy2 = this.drawSectionHeader(g, "\ud83d\udcfa 剧集 Top " + seriesCount, h2, col2X, contentTop);
      yy2 += 8;
      yy2 = this.drawSeriesList(g, seriesRows, seriesCount, col2X, yy2, columnWidth, rankFont, itemFont, metaFont);
      this.drawViewerFooter(g, panelX, contentBottom, panelW, 372, viewerRows, footerTitleFont, footerItemFont, serverLabel);
      g.dispose();
      ByteArrayOutputStream bos = new ByteArrayOutputStream();
      ImageIO.write(img, "png", bos);
      return bos.toByteArray();
   }

   public void drawCover(Graphics2D g, BufferedImage src, int W, int H) {
      double scale = Math.max((double)W / (double)src.getWidth(), (double)H / (double)src.getHeight());
      int sw = (int)Math.round((double)src.getWidth() * scale);
      int sh = (int)Math.round((double)src.getHeight() * scale);
      int dx = (W - sw) / 2;
      int dy = (H - sh) / 2;
      g.drawImage(src, dx, dy, sw, sh, null);
   }

   private void drawOriginalBackdrop(Graphics2D g, BufferedImage source, int width, int height) {
      if (source == null) {
         GradientPaint fallback = new GradientPaint(0.0F, 0.0F, new Color(91, 119, 143), (float)width, (float)height, new Color(57, 83, 108));
         g.setPaint(fallback);
         g.fillRect(0, 0, width, height);
      } else {
         this.drawCover(g, source, width, height);
      }
   }

   private void drawClassicPortraitBackdropScrim(Graphics2D g) {
      g.setColor(new Color(0, 0, 0, 150));
      g.fillRect(0, 0, 1200, 1600);
   }

   private BufferedImage fetchRankingBackground(
      List<EmbyPlaybackReportingRankingJob.MovieRow> movieRows, List<EmbyPlaybackReportingRankingJob.SeriesRow> seriesRows
   ) {
      BufferedImage backdrop = this.firstMovieBackdrop(movieRows);
      return backdrop == null ? this.firstSeriesBackdrop(seriesRows) : backdrop;
   }

   private BufferedImage firstMovieBackdrop(List<EmbyPlaybackReportingRankingJob.MovieRow> movieRows) {
      return movieRows != null && !movieRows.isEmpty() ? this.fetchMovieArtwork(movieRows.get(0)).backdrop() : null;
   }

   private BufferedImage firstSeriesBackdrop(List<EmbyPlaybackReportingRankingJob.SeriesRow> seriesRows) {
      return seriesRows != null && !seriesRows.isEmpty() ? this.fetchSeriesArtwork(seriesRows.get(0)).backdrop() : null;
   }

   private BufferedImage selectRankingBackground(
      List<PlaybackRankingPosterRenderer.MediaRank> movieRanks, List<PlaybackRankingPosterRenderer.MediaRank> seriesRanks
   ) {
      return selectFirstRankingBackground(movieRanks, seriesRanks, true);
   }

   static BufferedImage selectFirstRankingBackground(
      List<PlaybackRankingPosterRenderer.MediaRank> movieRanks, List<PlaybackRankingPosterRenderer.MediaRank> seriesRanks, boolean preferMovie
   ) {
      PlaybackRankingPosterRenderer.MediaRank movieFirst = firstRank(movieRanks);
      PlaybackRankingPosterRenderer.MediaRank seriesFirst = firstRank(seriesRanks);
      BufferedImage preferred = preferMovie ? backdropOf(movieFirst) : backdropOf(seriesFirst);
      return preferred != null ? preferred : (preferMovie ? backdropOf(seriesFirst) : backdropOf(movieFirst));
   }

   private static PlaybackRankingPosterRenderer.MediaRank firstRank(List<PlaybackRankingPosterRenderer.MediaRank> ranks) {
      return ranks != null && !ranks.isEmpty() ? ranks.get(0) : null;
   }

   private static BufferedImage backdropOf(PlaybackRankingPosterRenderer.MediaRank rank) {
      return rank == null ? null : rank.backdrop();
   }

   public BufferedImage fetchEmbyPrimaryImage(String itemId) {
      return this.fetchEmbyImage(itemId, "Primary", null);
   }

   private EmbyPlaybackReportingRankingJob.RankingArtwork fetchMovieArtwork(EmbyPlaybackReportingRankingJob.MovieRow row) {
      if (row == null) {
         return EmbyPlaybackReportingRankingJob.RankingArtwork.EMPTY;
      } else {
         EmbyPlaybackReportingRankingJob.BaseItem movie = this.fetchEmbyItem(row.movieId);
         BufferedImage poster = this.fetchEmbyPrimaryImage(row.movieId);
         if (!isPortraitArtwork(poster)) {
            poster = null;
         }

         BufferedImage backdrop = this.fetchEmbyImage(row.movieId, "Backdrop", 0);
         if (!isBackdropArtwork(backdrop)) {
            backdrop = null;
         }

         if (poster == null) {
            poster = this.rankingPosterImageResolver
               .resolvePoster("movie", row.title, movie == null ? null : movie.ProductionYear, movie == null ? null : movie.ProviderIds);
         }

         if (backdrop == null) {
            backdrop = this.rankingPosterImageResolver
               .resolveBackdrop("movie", row.title, movie == null ? null : movie.ProductionYear, movie == null ? null : movie.ProviderIds);
         }

         return new EmbyPlaybackReportingRankingJob.RankingArtwork(poster, backdrop);
      }
   }

   private EmbyPlaybackReportingRankingJob.RankingArtwork fetchSeriesArtwork(EmbyPlaybackReportingRankingJob.SeriesRow row) {
      if (row == null) {
         return EmbyPlaybackReportingRankingJob.RankingArtwork.EMPTY;
      } else {
         EmbyPlaybackReportingRankingJob.BaseItem episode = this.fetchEmbyItem(row.seriesId);
         String primaryItemId = episode != null && episode.SeriesId != null && !episode.SeriesId.isBlank() ? episode.SeriesId : null;
         EmbyPlaybackReportingRankingJob.BaseItem series = primaryItemId == null ? null : this.fetchEmbyItem(primaryItemId);
         EmbyPlaybackReportingRankingJob.BaseItem metadata = series == null ? episode : series;
         BufferedImage poster = this.fetchEmbyPrimaryImage(primaryItemId);
         if (!isPortraitArtwork(poster)) {
            poster = null;
         }

         BufferedImage backdrop = this.fetchEmbyImage(primaryItemId, "Backdrop", 0);
         if (!isBackdropArtwork(backdrop)) {
            backdrop = null;
         }

         if (poster == null) {
            poster = this.rankingPosterImageResolver
               .resolvePoster("tv", row.seriesName, metadata == null ? null : metadata.ProductionYear, metadata == null ? null : metadata.ProviderIds);
         }

         if (backdrop == null) {
            backdrop = this.rankingPosterImageResolver
               .resolveBackdrop("tv", row.seriesName, metadata == null ? null : metadata.ProductionYear, metadata == null ? null : metadata.ProviderIds);
         }

         return new EmbyPlaybackReportingRankingJob.RankingArtwork(poster, backdrop);
      }
   }

   static boolean isPortraitArtwork(BufferedImage image) {
      if (image != null && image.getWidth() > 0 && image.getHeight() > 0) {
         double ratio = (double)image.getWidth() / (double)image.getHeight();
         return ratio >= 0.45 && ratio <= 0.85;
      } else {
         return false;
      }
   }

   static boolean isBackdropArtwork(BufferedImage image) {
      return image != null && image.getWidth() > 0 && image.getHeight() > 0 && (double)image.getWidth() / (double)image.getHeight() >= 1.3;
   }

   private EmbyPlaybackReportingRankingJob.BaseItem fetchEmbyItem(String itemId) {
      if (itemId != null && !itemId.isBlank()) {
         HttpUrl baseUrl = HttpUrl.parse(EmbyUrlUtils.buildApiUrl(this.getServerUrl(), "/Items/" + itemId));
         if (baseUrl == null) {
            return null;
         } else {
            HttpUrl url = baseUrl.newBuilder()
               .addQueryParameter("Fields", "SeriesId,ProviderIds,ProductionYear")
               .addQueryParameter("api_key", this.getApiKey())
               .build();
            Request request = new okhttp3.Request.Builder().url(url).get().build();

            try {
               Object var6;
               try (Response response = this.HTTP.newCall(request).execute()) {
                  if (response.isSuccessful() && response.body() != null) {
                     return this.MAPPER.readValue(response.body().byteStream(), EmbyPlaybackReportingRankingJob.BaseItem.class);
                  }

                  log.debug("排行榜剧集信息加载失败：itemId={}, status={}", itemId, response.code());
                  var6 = null;
               }

               return (EmbyPlaybackReportingRankingJob.BaseItem)var6;
            } catch (Exception var10) {
               log.debug("排行榜剧集信息读取失败：itemId={}", itemId, var10);
               return null;
            }
         }
      } else {
         return null;
      }
   }

   private BufferedImage fetchEmbyImage(String itemId, String imageType, Integer imageIndex) {
      if (itemId != null && !itemId.isBlank()) {
         String path = "/Items/" + itemId + "/Images/" + imageType;
         if ("Backdrop".equalsIgnoreCase(imageType) && imageIndex != null) {
            path = path + "/" + imageIndex;
         }

         HttpUrl baseUrl = HttpUrl.parse(EmbyUrlUtils.buildApiUrl(this.getServerUrl(), path));
         if (baseUrl == null) {
            log.warn("排行榜背景图片 URL 构建失败：itemId={}, imageType={}", itemId, imageType);
            return null;
         } else {
            HttpUrl url = baseUrl.newBuilder().addQueryParameter("api_key", this.getApiKey()).build();
            Request req = new okhttp3.Request.Builder().url(url).get().build();

            try {
               Object var9;
               try (Response r = this.HTTP.newCall(req).execute()) {
                  if (r.isSuccessful() && r.body() != null) {
                     return ImageIO.read(r.body().byteStream());
                  }

                  log.debug("排行榜背景图片加载失败：itemId={}, imageType={}, status={}", itemId, imageType, r.code());
                  var9 = null;
               }

               return (BufferedImage)var9;
            } catch (Exception var13) {
               log.debug("排行榜背景图片读取失败：itemId={}, imageType={}", itemId, imageType, var13);
               return null;
            }
         }
      } else {
         return null;
      }
   }

   public int drawSectionHeader(Graphics2D g, String text, Font font, int x, int y) {
      g.setFont(font);
      g.setColor(new Color(250, 252, 255));
      g.drawString(text, x, y);
      g.setColor(new Color(120, 140, 180, 160));
      g.fillRect(x, y + 8, 320, 2);
      return y + 48;
   }

   public int drawMovieList(
      Graphics2D g, List<EmbyPlaybackReportingRankingJob.MovieRow> list, int count, int x, int y, int width, Font rankFont, Font itemFont, Font metaFont
   ) {
      int lineH = 40;

      for (int i = 0; i < count; i++) {
         EmbyPlaybackReportingRankingJob.MovieRow r = list.get(i);
         int baseline = y + i * (lineH + 16);
         g.setFont(rankFont);
         g.setColor(this.rankColor(i));
         g.drawString(String.format("%2d", i + 1), x, baseline);
         int titleX = x + 46;
         g.setFont(itemFont);
         g.setColor(new Color(248, 250, 255));
         String title = this.wrapEllipsis("《" + r.title + "》", g.getFontMetrics(), width - 46 - 180);
         g.drawString(title, titleX, baseline);
         g.setFont(metaFont);
         g.setColor(new Color(228, 235, 246));
         String meta = "⏱ " + this.formatDuration(r.watchedSeconds) + " ｜ ×" + r.playCount;
         int metaW = g.getFontMetrics().stringWidth(meta);
         g.drawString(meta, x + width - metaW, baseline);
      }

      return y + count * (lineH + 16);
   }

   public int drawSeriesList(
      Graphics2D g, List<EmbyPlaybackReportingRankingJob.SeriesRow> list, int count, int x, int y, int width, Font rankFont, Font itemFont, Font metaFont
   ) {
      int lineH = 34;

      for (int i = 0; i < count; i++) {
         EmbyPlaybackReportingRankingJob.SeriesRow r = list.get(i);
         int baseline = y + i * (lineH + 16);
         g.setFont(rankFont);
         g.setColor(this.rankColor(i));
         g.drawString(String.format("%2d", i + 1), x, baseline);
         int titleX = x + 46;
         g.setFont(itemFont);
         g.setColor(new Color(248, 250, 255));
         String title = this.wrapEllipsis("《" + r.seriesName + "》", g.getFontMetrics(), width - 46 - 180);
         g.drawString(title, titleX, baseline);
         g.setFont(metaFont);
         g.setColor(new Color(228, 235, 246));
         String meta = "⏱ " + this.formatDuration(r.totalSeconds) + " ｜ ×" + r.playCount;
         int metaW = g.getFontMetrics().stringWidth(meta);
         g.drawString(meta, x + width - metaW, baseline);
      }

      return y + count * (lineH + 16);
   }

   public void drawViewerFooter(
      Graphics2D g,
      int x,
      int footerTop,
      int width,
      int height,
      List<EmbyPlaybackReportingRankingJob.ViewerRow> viewerRows,
      Font titleFont,
      Font itemFont,
      String serverLabel
   ) {
      g.setColor(new Color(0, 0, 0, 160));
      g.fillRoundRect(x, footerTop, width, height, 28, 28);
      g.setColor(new Color(255, 255, 255, 40));
      g.setStroke(new BasicStroke(2.0F));
      g.drawRoundRect(x + 1, footerTop + 1, width - 2, height - 2, 28, 28);
      int padX = 28;
      int padY = 18;
      int innerX = x + padX;
      int innerY = footerTop + padY;
      if (serverLabel != null && !serverLabel.isBlank()) {
         Font serverFont = titleFont.deriveFont(1, 20.0F);
         g.setFont(serverFont);
         FontMetrics fmSrv = g.getFontMetrics(serverFont);
         int maxLabelWidth = Math.max(180, width / 2);
         String prefix = "\ud83d\ude80 推送服务器 ";
         String ellipsedServer = serverLabel;
         int prefixW = fmSrv.stringWidth(prefix);
         if (fmSrv.stringWidth(prefix + serverLabel) > maxLabelWidth) {
            ellipsedServer = this.wrapEllipsis(serverLabel, fmSrv, Math.max(60, maxLabelWidth - prefixW));
         }

         String text = prefix + ellipsedServer;
         int pillPadX = 16;
         int pillPadY = 10;
         int pillW = fmSrv.stringWidth(text) + pillPadX * 2;
         int pillH = fmSrv.getAscent() + fmSrv.getDescent() + pillPadY * 2;
         int pillX = x + width - padX - pillW;
         int pillY = innerY - 6;
         g.setColor(new Color(44, 67, 84, 92));
         g.fillRoundRect(pillX + 2, pillY + 3, pillW, pillH, pillH, pillH);
         GradientPaint gpBadge = new GradientPaint(
            (float)pillX, (float)pillY, new Color(82, 102, 255, 235), (float)(pillX + pillW), (float)(pillY + pillH), new Color(120, 234, 255, 230)
         );
         g.setPaint(gpBadge);
         g.fillRoundRect(pillX, pillY, pillW, pillH, pillH, pillH);
         g.setColor(new Color(255, 255, 255, 180));
         g.setStroke(new BasicStroke(1.6F));
         g.drawRoundRect(pillX, pillY, pillW, pillH, pillH, pillH);
         g.setColor(Color.WHITE);
         g.drawString(text, pillX + pillPadX, pillY + pillPadY + fmSrv.getAscent());
      }

      int total = viewerRows.size();
      int gridMax = 8;
      int showCards = Math.min(Math.min(10, total), gridMax);
      List<EmbyPlaybackReportingRankingJob.ActivityRow> actives = new ArrayList<>();

      for (EmbyPlaybackReportingRankingJob.ViewerRow v : viewerRows) {
         if (v.firstSeen != null && v.lastSeen != null && !v.firstSeen.after(v.lastSeen)) {
            long dur = Math.max(1L, (v.lastSeen.getTime() - v.firstSeen.getTime()) / 1000L);
            actives.add(new EmbyPlaybackReportingRankingJob.ActivityRow(v.userName, v.firstSeen, v.lastSeen, dur));
         }
      }

      actives.sort(Comparator.<EmbyPlaybackReportingRankingJob.ActivityRow>comparingLong(a -> a.durationSeconds).reversed());
      int showActive = Math.min(6, actives.size());
      g.setFont(titleFont);
      g.setColor(new Color(246, 250, 255));
      String sectionTitle = "⏰ 活跃时间范围排行";
      g.drawString(sectionTitle, innerX, innerY + titleFont.getSize() + 2);
      int sepY0 = innerY + titleFont.getSize() + 12;
      g.setColor(new Color(120, 140, 180, 140));
      g.fillRect(innerX, sepY0, width - padX * 2, 2);
      int activeTop = sepY0 + 16;
      Font activeNameFont = itemFont.deriveFont(20.0F);
      Font activeMetaFont = itemFont.deriveFont(18.0F);
      FontMetrics fmAName = g.getFontMetrics(activeNameFont);
      int aCols = 2;
      int aRows = (int)Math.ceil((double)showActive / 2.0);
      int aColW = (width - padX * 2 - 16) / 2;
      int aRowH = 28;

      for (int i = 0; i < showActive; i++) {
         EmbyPlaybackReportingRankingJob.ActivityRow ar = actives.get(i);
         int row = i / aCols;
         int col = i % aCols;
         int ax = innerX + col * (aColW + 16);
         int ay = activeTop + row * aRowH;
         g.setFont(activeNameFont);
         g.setColor(this.rankColor(i));
         String rk = String.format("%2d", i + 1);
         g.drawString(rk, ax, ay + fmAName.getAscent());
         int nameX = ax + fmAName.stringWidth("00 ");
         g.setColor(new Color(248, 250, 255));
         g.setFont(activeNameFont);
         String name = this.wrapEllipsis(ar.userName, g.getFontMetrics(), aColW - fmAName.stringWidth("00 ") - 200);
         g.drawString(name, nameX, ay + fmAName.getAscent());
         g.setFont(activeMetaFont);
         g.setColor(new Color(228, 235, 246));
         String range = this.formatHM(ar.start) + "–" + this.formatHM(ar.end) + " · " + this.formatDuration(ar.durationSeconds);
         int rw = g.getFontMetrics().stringWidth(range);
         g.drawString(range, ax + aColW - rw, ay + fmAName.getAscent());
      }

      int sepY = activeTop + (aRows == 0 ? 0 : aRows * aRowH) + (showActive == 0 ? 0 : 12);
      if (showActive > 0) {
         g.setColor(new Color(120, 140, 180, 140));
         g.fillRect(innerX, sepY, width - padX * 2, 2);
      }

      int cardsTop = showActive > 0 ? sepY + 16 : sepY0 + 16;
      if (showCards != 0) {
         int areaH = footerTop + height - cardsTop - padY;
         int cols = 4;
         int rows = 2;
         int hGap = 18;
         int vGap = 14;
         int cardW = (width - padX * 2 - hGap * (cols - 1)) / cols;
         int cardH = (areaH - vGap * (rows - 1)) / rows;
         Font nameFont = itemFont.deriveFont(22.0F);
         Font durationFont = itemFont.deriveFont(18.0F);
         Font pillFont = itemFont.deriveFont(1, 16.0F);
         FontMetrics fmName = g.getFontMetrics(nameFont);
         FontMetrics fmDur = g.getFontMetrics(durationFont);
         FontMetrics fmPill = g.getFontMetrics(pillFont);
         int avatarR = 26;
         int badgeR = 20;
         int barH = 9;
         int contentPad = 12;
         int gapNameToDur = 6;
         int gapDurToBar = 10;
         long maxSec = 1L;

         for (int i = 0; i < showCards; i++) {
            maxSec = Math.max(maxSec, viewerRows.get(i).totalSeconds);
         }

         for (int i = 0; i < showCards; i++) {
            EmbyPlaybackReportingRankingJob.ViewerRow vx = viewerRows.get(i);
            int row = i / cols;
            int col = i % cols;
            int cx = innerX + col * (cardW + hGap);
            int cy = cardsTop + row * (cardH + vGap);
            g.setColor(new Color(226, 240, 249, 52));
            g.fillRoundRect(cx, cy, cardW, cardH, 18, 18);
            g.setColor(new Color(242, 249, 255, 86));
            g.setStroke(new BasicStroke(1.5F));
            g.drawRoundRect(cx, cy, cardW, cardH, 18, 18);
            Color badgeColor = this.rankColor(i);
            int badgeX = cx + contentPad;
            int badgeY = cy + contentPad;
            g.setColor(new Color(47, 68, 84, 105));
            g.fillOval(badgeX, badgeY, badgeR, badgeR);
            g.setColor(badgeColor);
            g.fillOval(badgeX - 1, badgeY - 1, badgeR, badgeR);
            g.setColor(new Color(20, 32, 48));
            g.setFont(durationFont.deriveFont(1, 14.0F));
            String rnk = String.valueOf(i + 1);
            FontMetrics fmR = g.getFontMetrics();
            g.drawString(rnk, badgeX + (badgeR - fmR.stringWidth(rnk)) / 2, badgeY + (badgeR + fmR.getAscent()) / 2 - 2);
            int avX = badgeX + badgeR + 8;
            int avY = cy + contentPad;
            Color avColor = this.colorFromName(vx.userName);
            g.setColor(new Color(47, 68, 84, 105));
            g.fillOval(avX, avY, avatarR, avatarR);
            g.setColor(avColor);
            g.fillOval(avX - 1, avY - 1, avatarR, avatarR);
            g.setColor(Color.WHITE);
            g.setFont(durationFont.deriveFont(1, 16.0F));
            String initial = this.getInitial(vx.userName);
            FontMetrics fmInit = g.getFontMetrics();
            g.drawString(initial, avX + (avatarR - fmInit.stringWidth(initial)) / 2, avY + (avatarR + fmInit.getAscent()) / 2 - 3);
            String countText = "×" + (vx.playCount > 999 ? "999+" : String.valueOf(vx.playCount));
            g.setFont(pillFont);
            int pillTextW = fmPill.stringWidth(countText);
            int pillPadX = 8;
            int pillPadY = 5;
            int pillW = Math.max(36, pillTextW + pillPadX * 2);
            int pillH = fmPill.getAscent() + fmPill.getDescent() + pillPadY * 2;
            int pillX = cx + cardW - contentPad - pillW;
            int pillY = cy + contentPad;
            Color c1;
            Color c2;
            Color border;
            if (i == 0) {
               c1 = new Color(255, 213, 110);
               c2 = new Color(255, 158, 0);
               border = new Color(255, 140, 0, 180);
            } else if (i == 1) {
               c1 = new Color(215, 215, 215);
               c2 = new Color(168, 168, 168);
               border = new Color(140, 140, 140, 180);
            } else if (i == 2) {
               c1 = new Color(210, 166, 121);
               c2 = new Color(178, 115, 55);
               border = new Color(150, 92, 40, 180);
            } else {
               c1 = new Color(125, 229, 214);
               c2 = new Color(42, 161, 149);
               border = new Color(20, 120, 110, 180);
            }

            GradientPaint pillGp = new GradientPaint((float)pillX, (float)pillY, c1, (float)pillX, (float)(pillY + pillH), c2);
            g.setPaint(pillGp);
            g.fillRoundRect(pillX, pillY, pillW, pillH, pillH, pillH);
            g.setColor(border);
            g.drawRoundRect(pillX, pillY, pillW, pillH, pillH, pillH);
            g.setColor(Color.WHITE);
            g.drawString(countText, pillX + (pillW - pillTextW) / 2, pillY + pillPadY + fmPill.getAscent());
            int nameX = avX + avatarR + 10;
            int nameRight = pillX - 8;
            int nameMaxW = Math.max(30, nameRight - nameX);
            g.setFont(nameFont);
            g.setColor(new Color(248, 250, 255));
            String name = this.wrapEllipsis(vx.userName, g.getFontMetrics(), nameMaxW);
            int nameBase = avY + fmName.getAscent() + 2;
            g.drawString(name, nameX, nameBase);
            g.setFont(durationFont);
            g.setColor(new Color(228, 235, 246));
            String dur = "⏱ " + this.formatDuration(vx.totalSeconds);
            int durBase = nameBase + gapNameToDur + fmDur.getAscent();
            g.drawString(dur, nameX, durBase);
            int barX = cx + contentPad;
            int barW = cardW - contentPad * 2;
            int barY = Math.max(durBase + gapDurToBar, cy + cardH - contentPad - barH);
            g.setColor(new Color(255, 255, 255, 40));
            g.fillRoundRect(barX, barY, barW, barH, barH, barH);
            double ratio = Math.min(1.0, (double)vx.totalSeconds / (double)maxSec);
            int fillW = Math.max(6, (int)Math.round((double)barW * ratio));
            GradientPaint gp = new GradientPaint((float)barX, (float)barY, new Color(144, 255, 197), (float)barX, (float)(barY + barH), new Color(26, 163, 90));
            g.setPaint(gp);
            g.fillRoundRect(barX, barY, fillW, barH, barH, barH);
         }
      }
   }

   private String buildServerLabel(EmbyInfoCacheManagerUtils.EmbyServerConfig serverConfig) {
      if (serverConfig == null) {
         return "";
      } else {
         return serverConfig.serverName() != null && !serverConfig.serverName().isBlank() ? serverConfig.serverName().trim() : "未知服务器";
      }
   }

   private Color rankColor(int index) {
      if (index == 0) {
         return new Color(255, 215, 0);
      } else if (index == 1) {
         return new Color(192, 192, 192);
      } else {
         return index == 2 ? new Color(205, 127, 50) : new Color(110, 150, 255);
      }
   }

   public String wrapEllipsis(String text, FontMetrics fm, int maxWidth) {
      if (fm.stringWidth(text) <= maxWidth) {
         return text;
      } else {
         String ell = "…";
         int ellW = fm.stringWidth(ell);
         StringBuilder sb = new StringBuilder();

         for (int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);
            if (fm.stringWidth(sb.toString() + c) + ellW > maxWidth) {
               break;
            }

            sb.append(c);
         }

         return sb.toString() + ell;
      }
   }

   private String chooseBestNamePreferCJK(Map<String, Long> secondsByName) {
      String best = null;
      long bestVal = -1L;

      for (Entry<String, Long> e : secondsByName.entrySet()) {
         if (e.getValue() > bestVal) {
            bestVal = e.getValue();
            best = e.getKey();
         }
      }

      String bestCJK = null;
      long bestCJKVal = -1L;

      for (Entry<String, Long> ex : secondsByName.entrySet()) {
         if (this.isCJK(ex.getKey()) && ex.getValue() > bestCJKVal) {
            bestCJKVal = ex.getValue();
            bestCJK = ex.getKey();
         }
      }

      if (bestCJK != null && bestVal > 0L && (double)bestCJKVal >= (double)bestVal * 0.8) {
         return bestCJK;
      } else {
         return best == null ? "未知" : best;
      }
   }

   private boolean isCJK(String s) {
      if (s == null) {
         return false;
      } else {
         for (int i = 0; i < s.length(); i++) {
            UnicodeBlock b = UnicodeBlock.of(s.charAt(i));
            if (b == UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS
               || b == UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A
               || b == UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_B
               || b == UnicodeBlock.HIRAGANA
               || b == UnicodeBlock.KATAKANA
               || b == UnicodeBlock.HANGUL_SYLLABLES) {
               return true;
            }
         }

         return false;
      }
   }

   private Color colorFromName(String name) {
      if (name == null) {
         name = "user";
      }

      int h = name.hashCode();
      float hue = (float)(h & 65535) / 65535.0F;
      float sat = 0.55F;
      float bri = 0.95F;
      Color c = Color.getHSBColor(hue, sat, bri);
      int r = Math.min(255, (int)((double)c.getRed() * 0.95));
      int g = Math.min(255, (int)((double)c.getGreen() * 0.95));
      int b = Math.min(255, (int)((double)c.getBlue() * 0.95));
      return new Color(r, g, b);
   }

   private String getInitial(String name) {
      if (name != null && !name.isEmpty()) {
         char c = name.charAt(0);
         String s = String.valueOf(c).toUpperCase(Locale.ROOT);
         return Character.isLetterOrDigit(c) ? s.substring(0, 1) : s;
      } else {
         return "?";
      }
   }

   private String formatHM(Date d) {
      if (d == null) {
         return "--:--";
      } else {
         SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
         sdf.setTimeZone(this.ZONE);
         return sdf.format(d);
      }
   }

   public String normalizeMovieTitle(String s) {
      return this.normalizeTitleBase(s, false);
   }

   public String normalizeSeriesTitle(String s) {
      return this.normalizeTitleBase(s, true);
   }

   public String normalizeTitleBase(String s, boolean isSeries) {
      if (s == null) {
         return "unknown";
      } else {
         String t = this.toHalfWidth(s).toLowerCase(Locale.ROOT);
         t = QUALITY_TAGS.matcher(t).replaceAll(" ");
         t = YEAR_ANY.matcher(t).replaceAll(" ");

         for (int i = 0; i < 5; i++) {
            String nt = t.replaceAll("[\\(（\\[【\\{][^\\)）\\]】\\}]{0,64}[\\)）\\]】\\}]", " ");
            if (nt.equals(t)) {
               break;
            }

            t = nt;
         }

         if (isSeries) {
            t = SEASON_TAGS.matcher(t).replaceAll(" ");
         }

         t = t.trim();
         return t.replaceAll("[\\p{Punct}\\p{IsPunctuation}\\s·　]", "");
      }
   }

   public String toHalfWidth(String input) {
      if (input == null) {
         return null;
      } else {
         char[] chars = input.toCharArray();

         for (int i = 0; i < chars.length; i++) {
            char c = chars[i];
            if (c == 12288) {
               chars[i] = ' ';
            } else if (c >= '！' && c <= '～') {
               chars[i] = (char)(c - 'ﻠ');
            }
         }

         return new String(chars);
      }
   }

   public void enableAA(Graphics2D g) {
      g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
      g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
   }

   public Font loadFontOrDefault(float size) {
      try {
         if (this.CUSTOM_FONT_PATH != null) {
            Font f = Font.createFont(0, new FileInputStream(this.CUSTOM_FONT_PATH));
            return f.deriveFont(size);
         }
      } catch (Exception var3) {
      }

      return new Font("SansSerif", 0, Math.round(size));
   }

   public String enc(String s) {
      return URLEncoder.encode(s, StandardCharsets.UTF_8);
   }

   public <T> T getJson(String url, Class<T> type) throws IOException {
      Request req = new okhttp3.Request.Builder().url(url).get().build();

      Object var5;
      try (Response r = this.HTTP.newCall(req).execute()) {
         if (!r.isSuccessful()) {
            throw new IOException("HTTP " + r.code() + " for " + url);
         }

         var5 = this.MAPPER.readValue(Objects.requireNonNull(r.body()).byteStream(), type);
      }

      return (T)var5;
   }

   public long ticksToSeconds(long ticks) {
      return ticks / 10000000L;
   }

   public String formatDuration(long totalSeconds) {
      long hours = totalSeconds / 3600L;
      long minutes = totalSeconds % 3600L / 60L;
      long seconds = totalSeconds % 60L;
      return hours > 0L ? String.format("%d:%02d:%02d", hours, minutes, seconds) : String.format("%02d:%02d", minutes, seconds);
   }

   public String buildEpCode(Integer season, Integer ep) {
      if (season == null && ep == null) {
         return null;
      } else {
         String s = season != null ? String.format("S%02d", season) : "S??";
         String e = ep != null ? String.format("E%02d", ep) : "E??";
         return s + e;
      }
   }

   private String chooseBestName(Map<String, Long> secondsByName) {
      String best = null;
      long max = -1L;

      for (Entry<String, Long> e : secondsByName.entrySet()) {
         if (e.getValue() > max) {
            max = e.getValue();
            best = e.getKey();
         }
      }

      return best == null ? "未知" : best;
   }

   public int drawDatePill(Graphics2D g, String text, int x, int topY, Font font) {
      Font oldFont = g.getFont();
      Paint oldPaint = g.getPaint();
      Object aa = g.getRenderingHint(RenderingHints.KEY_ANTIALIASING);
      Object taa = g.getRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING);
      g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
      g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
      g.setFont(font);
      FontMetrics fm = g.getFontMetrics();
      int textW = fm.stringWidth(text);
      int textH = fm.getAscent() + fm.getDescent();
      int padX = 18;
      int padY = 10;
      int pillW = textW + padX * 2;
      int pillH = textH + padY * 2;
      int baseY = topY + padY + fm.getAscent();
      g.setColor(new Color(255, 255, 255, 36));
      g.fill(new Float((float)x, (float)topY, (float)pillW, (float)pillH, (float)pillH, (float)pillH));
      g.setStroke(new BasicStroke(2.0F));
      g.setColor(new Color(255, 255, 255, 60));
      g.draw(new Float((float)x + 0.5F, (float)topY + 0.5F, (float)pillW - 1.0F, (float)pillH - 1.0F, (float)pillH, (float)pillH));
      g.setColor(new Color(0, 0, 0, 180));
      g.drawString(text, x + padX + 1, baseY + 1);
      GradientPaint gp = new GradientPaint((float)x, (float)topY, new Color(144, 255, 197), (float)x, (float)(topY + pillH), new Color(26, 163, 90));
      g.setPaint(gp);
      g.drawString(text, x + padX, baseY);
      g.setFont(oldFont);
      g.setPaint(oldPaint);
      g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, aa);
      g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, taa);
      return pillH;
   }

   public static class ActivityRow {
      String userName;
      Date start;
      Date end;
      long durationSeconds;

      ActivityRow(String userName, Date start, Date end, long durationSeconds) {
         this.userName = userName;
         this.start = start;
         this.end = end;
         this.durationSeconds = durationSeconds;
      }
   }

   @JsonIgnoreProperties(
      ignoreUnknown = true
   )
   public static class BaseItem {
      public String Id;
      public String Name;
      public String Type;
      public String SeriesName;
      public String SeriesId;
      public Integer IndexNumber;
      public Integer ParentIndexNumber;
      public Integer ProductionYear;
      public Map<String, String> ProviderIds;
      public Long RunTimeTicks;
      public EmbyPlaybackReportingRankingJob.UserData UserData;
   }

   @JsonIgnoreProperties(
      ignoreUnknown = true
   )
   public static class EmbyUser {
      public String Id;
      public String Name;
      public Boolean IsHidden;
      public Boolean IsDisabled;
   }

   public static class MovieAgg {
      long totalSeconds;
      int playCount;
      Map<String, Long> secondsByMovieId = new HashMap<>();
      Map<String, Long> secondsByDisplayName = new HashMap<>();

      MovieAgg(String any) {
      }
   }

   public static class MovieRow {
      String movieId;
      String title;
      long watchedSeconds;
      int playCount;

      MovieRow(String movieId, String title) {
         this.movieId = movieId;
         this.title = title;
      }
   }

   @JsonIgnoreProperties(
      ignoreUnknown = true
   )
   public static class QueryResult {
      public List<EmbyPlaybackReportingRankingJob.BaseItem> Items;
      public Integer TotalRecordCount;
   }

   private static record RankingArtwork(BufferedImage poster, BufferedImage backdrop) {
      private static final EmbyPlaybackReportingRankingJob.RankingArtwork EMPTY = new EmbyPlaybackReportingRankingJob.RankingArtwork(null, null);
   }

   public static class SeriesAgg {
      String anyDisplayName;
      long totalSeconds;
      int playCount;
      Set<String> episodes = new HashSet<>();
      Map<String, Long> secondsBySeriesId = new HashMap<>();
      Map<String, Long> secondsByDisplayName = new HashMap<>();

      SeriesAgg(String displayName) {
         this.anyDisplayName = displayName;
      }
   }

   public static class SeriesRow {
      String seriesId;
      String seriesName;
      long totalSeconds;
      int playCount;
      Set<String> episodes = new HashSet<>();

      SeriesRow(String seriesId, String seriesName) {
         this.seriesId = seriesId;
         this.seriesName = seriesName;
      }
   }

   static record TelegramPhotoPayload(byte[] bytes, String filename, String mediaType, boolean converted) {
   }

   @JsonIgnoreProperties(
      ignoreUnknown = true
   )
   public static class UserData {
      public Date LastPlayedDate;
      public Boolean Played;
      public Long PlaybackPositionTicks;
      public Double PlayedPercentage;
      public Integer PlayCount;
   }

   public static class ViewerRow {
      String userId;
      String userName;
      long totalSeconds;
      int playCount;
      Date firstSeen;
      Date lastSeen;

      ViewerRow(String userId, String userName) {
         this.userId = userId;
         this.userName = userName;
      }
   }
}
