package com.una.embyhub.movie.client;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.una.embyhub.config.common.exception.BizException;
import com.una.embyhub.movie.model.MovieActionResponse;
import com.una.embyhub.movie.model.MovieQbittorrentConfig;
import com.una.embyhub.movie.model.MovieQbittorrentTorrent;
import java.io.IOException;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse.BodyHandlers;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import lombok.Generated;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class MovieQbittorrentClient {
   @Generated
   private static final Logger log = LoggerFactory.getLogger(MovieQbittorrentClient.class);
   private static final Set<String> ACTIVE_STATES = Set.of("downloading", "stalledDL", "pausedDL", "queuedDL", "checkingDL", "metaDL", "allocating");
   private final HttpClient httpClient;

   public MovieQbittorrentClient() {
      CookieManager cookieManager = new CookieManager();
      cookieManager.setCookiePolicy(CookiePolicy.ACCEPT_ALL);
      this.httpClient = HttpClient.newBuilder().cookieHandler(cookieManager).connectTimeout(Duration.ofSeconds(10L)).build();
   }

   public List<MovieQbittorrentTorrent> fetchQueue(MovieQbittorrentConfig config, boolean filterActive) {
      return this.fetchQueue(config, filterActive, null);
   }

   public List<MovieQbittorrentTorrent> fetchQueueByTag(MovieQbittorrentConfig config, String tag) {
      return this.fetchQueue(config, false, tag);
   }

   public List<MovieQbittorrentTorrent> fetchQueueByHashes(MovieQbittorrentConfig config, List<String> hashes) {
      this.validateConfig(config);
      String baseUrl = this.normalizeBaseUrl(config.getHost());
      String infoUrl = baseUrl + "/api/v2/torrents/info";
      if (hashes != null && !hashes.isEmpty()) {
         String joined = String.join("|", hashes);
         infoUrl = infoUrl + "?hashes=" + this.urlEncode(joined);
      }

      try {
         HttpRequest request = HttpRequest.newBuilder(URI.create(infoUrl)).timeout(Duration.ofSeconds(10L)).GET().build();
         HttpResponse<String> response = this.sendWithLoginRetry(request, baseUrl, config.getUsername(), config.getPassword());
         if (response.statusCode() >= 200 && response.statusCode() < 300) {
            return this.parseQueue(response.body(), false);
         } else {
            throw new BizException("获取下载队列失败");
         }
      } catch (InterruptedException var7) {
         Thread.currentThread().interrupt();
         log.warn("获取 qBittorrent 队列被中断: {}", var7.getMessage());
         throw new BizException("连接 qBittorrent 失败");
      } catch (IOException var8) {
         log.warn("获取 qBittorrent 队列失败: {}", var8.getMessage());
         throw new BizException("连接 qBittorrent 失败");
      }
   }

   private List<MovieQbittorrentTorrent> fetchQueue(MovieQbittorrentConfig config, boolean filterActive, String tag) {
      this.validateConfig(config);
      String baseUrl = this.normalizeBaseUrl(config.getHost());
      String infoUrl = baseUrl + "/api/v2/torrents/info";
      if (StringUtils.hasText(tag)) {
         infoUrl = infoUrl + "?tag=" + this.urlEncode(tag);
      }

      try {
         HttpRequest request = HttpRequest.newBuilder(URI.create(infoUrl)).timeout(Duration.ofSeconds(10L)).GET().build();
         HttpResponse<String> response = this.sendWithLoginRetry(request, baseUrl, config.getUsername(), config.getPassword());
         if (response.statusCode() >= 200 && response.statusCode() < 300) {
            return this.parseQueue(response.body(), filterActive);
         } else {
            throw new BizException("获取下载队列失败");
         }
      } catch (InterruptedException var8) {
         Thread.currentThread().interrupt();
         log.warn("获取 qBittorrent 队列被中断: {}", var8.getMessage());
         throw new BizException("连接 qBittorrent 失败");
      } catch (IOException var9) {
         log.warn("获取 qBittorrent 队列失败: {}", var9.getMessage());
         throw new BizException("连接 qBittorrent 失败");
      }
   }

   public MovieActionResponse addMagnet(MovieQbittorrentConfig config, String magnet, String savePath) {
      return this.addMagnet(config, magnet, savePath, null);
   }

   public MovieActionResponse addMagnet(MovieQbittorrentConfig config, String magnet, String savePath, String tag) {
      this.validateConfig(config);
      String baseUrl = this.normalizeBaseUrl(config.getHost());
      String addUrl = baseUrl + "/api/v2/torrents/add";
      StringBuilder form = new StringBuilder();
      form.append("urls=").append(this.urlEncode(magnet));
      if (StringUtils.hasText(savePath)) {
         form.append("&savepath=").append(this.urlEncode(savePath));
      }

      if (StringUtils.hasText(tag)) {
         form.append("&tags=").append(this.urlEncode(tag));
      }

      try {
         HttpRequest request = HttpRequest.newBuilder(URI.create(addUrl))
            .timeout(Duration.ofSeconds(15L))
            .header("Content-Type", "application/x-www-form-urlencoded")
            .POST(BodyPublishers.ofString(form.toString()))
            .build();
         HttpResponse<String> response = this.sendWithLoginRetry(request, baseUrl, config.getUsername(), config.getPassword());
         return response.statusCode() == 200
            ? MovieActionResponse.builder().success(true).message("已投递到 qBittorrent").build()
            : MovieActionResponse.builder().success(false).message("添加任务失败").build();
      } catch (InterruptedException var10) {
         Thread.currentThread().interrupt();
         return MovieActionResponse.builder().success(false).message("添加任务失败").build();
      } catch (IOException var11) {
         return MovieActionResponse.builder().success(false).message("连接 qBittorrent 失败").build();
      }
   }

   public MovieActionResponse addTorrentFile(MovieQbittorrentConfig config, byte[] torrentContent, String filename, String savePath) {
      return this.addTorrentFile(config, torrentContent, filename, savePath, null);
   }

   public MovieActionResponse addTorrentFile(MovieQbittorrentConfig config, byte[] torrentContent, String filename, String savePath, String tag) {
      this.validateConfig(config);
      String baseUrl = this.normalizeBaseUrl(config.getHost());
      String addUrl = baseUrl + "/api/v2/torrents/add";
      String boundary = "----FoamBoundary" + UUID.randomUUID();
      byte[] body = this.buildMultipartBody(boundary, torrentContent, filename, savePath, tag);

      try {
         HttpRequest request = HttpRequest.newBuilder(URI.create(addUrl))
            .timeout(Duration.ofSeconds(20L))
            .header("Content-Type", "multipart/form-data; boundary=" + boundary)
            .POST(BodyPublishers.ofByteArray(body))
            .build();
         HttpResponse<String> response = this.sendWithLoginRetry(request, baseUrl, config.getUsername(), config.getPassword());
         if (response.statusCode() == 200) {
            String pathMsg = StringUtils.hasText(savePath) ? " 到 " + savePath : "";
            return MovieActionResponse.builder().success(true).message("已添加到 qBittorrent" + pathMsg).build();
         } else {
            return MovieActionResponse.builder().success(false).message("添加任务失败").build();
         }
      } catch (InterruptedException var13) {
         Thread.currentThread().interrupt();
         return MovieActionResponse.builder().success(false).message("添加任务失败").build();
      } catch (IOException var14) {
         return MovieActionResponse.builder().success(false).message("连接 qBittorrent 失败").build();
      }
   }

   public boolean removeTags(MovieQbittorrentConfig config, String hashes, String tags) {
      this.validateConfig(config);
      if (StringUtils.hasText(hashes) && StringUtils.hasText(tags)) {
         String baseUrl = this.normalizeBaseUrl(config.getHost());
         String url = baseUrl + "/api/v2/torrents/removeTags";
         String form = "hashes=" + this.urlEncode(hashes) + "&tags=" + this.urlEncode(tags);

         try {
            HttpRequest request = HttpRequest.newBuilder(URI.create(url))
               .timeout(Duration.ofSeconds(10L))
               .header("Content-Type", "application/x-www-form-urlencoded")
               .POST(BodyPublishers.ofString(form))
               .build();
            HttpResponse<String> response = this.sendWithLoginRetry(request, baseUrl, config.getUsername(), config.getPassword());
            return response.statusCode() >= 200 && response.statusCode() < 300;
         } catch (InterruptedException var9) {
            Thread.currentThread().interrupt();
            return false;
         } catch (IOException var10) {
            return false;
         }
      } else {
         return false;
      }
   }

   public boolean deleteTags(MovieQbittorrentConfig config, String tags) {
      this.validateConfig(config);
      if (!StringUtils.hasText(tags)) {
         return false;
      } else {
         String baseUrl = this.normalizeBaseUrl(config.getHost());
         String url = baseUrl + "/api/v2/torrents/deleteTags";
         String form = "tags=" + this.urlEncode(tags);

         try {
            HttpRequest request = HttpRequest.newBuilder(URI.create(url))
               .timeout(Duration.ofSeconds(10L))
               .header("Content-Type", "application/x-www-form-urlencoded")
               .POST(BodyPublishers.ofString(form))
               .build();
            HttpResponse<String> response = this.sendWithLoginRetry(request, baseUrl, config.getUsername(), config.getPassword());
            return response.statusCode() >= 200 && response.statusCode() < 300;
         } catch (InterruptedException var8) {
            Thread.currentThread().interrupt();
            return false;
         } catch (IOException var9) {
            return false;
         }
      }
   }

   public boolean pauseTorrents(MovieQbittorrentConfig config, String hashes) {
      this.validateConfig(config);
      if (!StringUtils.hasText(hashes)) {
         return false;
      } else {
         String baseUrl = this.normalizeBaseUrl(config.getHost());
         String url = baseUrl + "/api/v2/torrents/pause";
         String form = "hashes=" + this.urlEncode(hashes);

         try {
            HttpRequest request = HttpRequest.newBuilder(URI.create(url))
               .timeout(Duration.ofSeconds(10L))
               .header("Content-Type", "application/x-www-form-urlencoded")
               .POST(BodyPublishers.ofString(form))
               .build();
            HttpResponse<String> response = this.sendWithLoginRetry(request, baseUrl, config.getUsername(), config.getPassword());
            if (response.statusCode() == 404) {
               String stopUrl = baseUrl + "/api/v2/torrents/stop";
               HttpRequest stopRequest = HttpRequest.newBuilder(URI.create(stopUrl))
                  .timeout(Duration.ofSeconds(10L))
                  .header("Content-Type", "application/x-www-form-urlencoded")
                  .POST(BodyPublishers.ofString(form))
                  .build();
               HttpResponse<String> stopResponse = this.sendWithLoginRetry(stopRequest, baseUrl, config.getUsername(), config.getPassword());
               return stopResponse.statusCode() >= 200 && stopResponse.statusCode() < 300;
            } else {
               return response.statusCode() >= 200 && response.statusCode() < 300;
            }
         } catch (InterruptedException var11) {
            Thread.currentThread().interrupt();
            return false;
         } catch (IOException var12) {
            return false;
         }
      }
   }

   public boolean resumeTorrents(MovieQbittorrentConfig config, String hashes) {
      this.validateConfig(config);
      if (!StringUtils.hasText(hashes)) {
         return false;
      } else {
         String baseUrl = this.normalizeBaseUrl(config.getHost());
         String url = baseUrl + "/api/v2/torrents/resume";
         String form = "hashes=" + this.urlEncode(hashes);

         try {
            HttpRequest request = HttpRequest.newBuilder(URI.create(url))
               .timeout(Duration.ofSeconds(10L))
               .header("Content-Type", "application/x-www-form-urlencoded")
               .POST(BodyPublishers.ofString(form))
               .build();
            HttpResponse<String> response = this.sendWithLoginRetry(request, baseUrl, config.getUsername(), config.getPassword());
            if (response.statusCode() == 404) {
               String startUrl = baseUrl + "/api/v2/torrents/start";
               HttpRequest startRequest = HttpRequest.newBuilder(URI.create(startUrl))
                  .timeout(Duration.ofSeconds(10L))
                  .header("Content-Type", "application/x-www-form-urlencoded")
                  .POST(BodyPublishers.ofString(form))
                  .build();
               HttpResponse<String> startResponse = this.sendWithLoginRetry(startRequest, baseUrl, config.getUsername(), config.getPassword());
               return startResponse.statusCode() >= 200 && startResponse.statusCode() < 300;
            } else {
               return response.statusCode() >= 200 && response.statusCode() < 300;
            }
         } catch (InterruptedException var11) {
            Thread.currentThread().interrupt();
            return false;
         } catch (IOException var12) {
            return false;
         }
      }
   }

   public boolean deleteTorrents(MovieQbittorrentConfig config, String hashes, boolean deleteFiles) {
      this.validateConfig(config);
      if (!StringUtils.hasText(hashes)) {
         return false;
      } else {
         String baseUrl = this.normalizeBaseUrl(config.getHost());
         String url = baseUrl + "/api/v2/torrents/delete";
         String form = "hashes=" + this.urlEncode(hashes) + "&deleteFiles=" + (deleteFiles ? "true" : "false");

         try {
            HttpRequest request = HttpRequest.newBuilder(URI.create(url))
               .timeout(Duration.ofSeconds(10L))
               .header("Content-Type", "application/x-www-form-urlencoded")
               .POST(BodyPublishers.ofString(form))
               .build();
            HttpResponse<String> response = this.sendWithLoginRetry(request, baseUrl, config.getUsername(), config.getPassword());
            return response.statusCode() >= 200 && response.statusCode() < 300;
         } catch (InterruptedException var9) {
            Thread.currentThread().interrupt();
            return false;
         } catch (IOException var10) {
            return false;
         }
      }
   }

   private void login(String baseUrl, String username, String password) {
      String loginUrl = baseUrl + "/api/v2/auth/login";
      String form = "username=" + this.urlEncode(username) + "&password=" + this.urlEncode(password);

      try {
         HttpRequest request = HttpRequest.newBuilder(URI.create(loginUrl))
            .timeout(Duration.ofSeconds(10L))
            .header("Content-Type", "application/x-www-form-urlencoded")
            .POST(BodyPublishers.ofString(form))
            .build();
         HttpResponse<String> response = this.httpClient.send(request, BodyHandlers.ofString());
         String body = response.body() == null ? "" : response.body().trim();
         if (response.statusCode() < 200 || response.statusCode() >= 300 || !"Ok.".equals(body)) {
            throw new BizException("qBittorrent 登录失败");
         }
      } catch (InterruptedException var9) {
         Thread.currentThread().interrupt();
         log.warn("qBittorrent 登录被中断: {}", var9.getMessage());
         throw new BizException("qBittorrent 登录失败");
      } catch (IOException var10) {
         log.warn("qBittorrent 登录异常: {}", var10.getMessage());
         throw new BizException("qBittorrent 登录失败");
      }
   }

   private HttpResponse<String> sendWithLoginRetry(HttpRequest request, String baseUrl, String username, String password) throws IOException, InterruptedException {
      HttpResponse<String> response = this.httpClient.send(request, BodyHandlers.ofString());
      if (this.isUnauthorized(response.statusCode())) {
         this.login(baseUrl, username, password);
         response = this.httpClient.send(request, BodyHandlers.ofString());
      }

      return response;
   }

   private boolean isUnauthorized(int statusCode) {
      return statusCode == 401 || statusCode == 403;
   }

   private List<MovieQbittorrentTorrent> parseQueue(String body, boolean filterActive) {
      JSONArray array = JSON.parseArray(body);
      List<MovieQbittorrentTorrent> list = new ArrayList<>();

      for (int i = 0; i < array.size(); i++) {
         JSONObject item = array.getJSONObject(i);
         String state = item.getString("state");
         Double progress = item.getDouble("progress");
         if (!filterActive || ACTIVE_STATES.contains(state) || this.isPausedIncomplete(state, progress)) {
            MovieQbittorrentTorrent torrent = MovieQbittorrentTorrent.builder()
               .name(item.getString("name"))
               .hash(item.getString("hash"))
               .savePath(item.getString("save_path"))
               .contentPath(item.getString("content_path"))
               .tags(item.getString("tags"))
               .progress(progress)
               .state(state)
               .dlspeed(item.getLong("dlspeed"))
               .upspeed(item.getLong("upspeed"))
               .size(item.getLong("size"))
               .eta(item.getLong("eta"))
               .build();
            list.add(torrent);
         }
      }

      return list;
   }

   private void validateConfig(MovieQbittorrentConfig config) {
      if (config == null || !StringUtils.hasText(config.getHost()) || !StringUtils.hasText(config.getUsername()) || !StringUtils.hasText(config.getPassword())) {
         throw new BizException("请先配置 qBittorrent 连接信息");
      }
   }

   private String normalizeBaseUrl(String host) {
      String trimmed = host == null ? "" : host.trim();

      while (trimmed.endsWith("/")) {
         trimmed = trimmed.substring(0, trimmed.length() - 1);
      }

      return trimmed;
   }

   private String urlEncode(String value) {
      return URLEncoder.encode(value == null ? "" : value, StandardCharsets.UTF_8);
   }

   private boolean isPausedIncomplete(String state, Double progress) {
      if (StringUtils.hasText(state) && state.startsWith("stoppedDL")) {
         double value = progress == null ? 0.0 : progress;
         return value < 1.0;
      } else {
         return false;
      }
   }

   private byte[] buildMultipartBody(String boundary, byte[] torrentContent, String filename, String savePath, String tag) {
      List<byte[]> parts = new ArrayList<>();
      String safeName = StringUtils.hasText(filename) ? filename : "download.torrent";
      String fileHeader = "--"
         + boundary
         + "\r\nContent-Disposition: form-data; name=\"torrents\"; filename=\""
         + safeName
         + "\"\r\nContent-Type: application/x-bittorrent\r\n\r\n";
      parts.add(fileHeader.getBytes(StandardCharsets.UTF_8));
      parts.add(torrentContent);
      parts.add("\r\n".getBytes(StandardCharsets.UTF_8));
      if (StringUtils.hasText(savePath)) {
         String savePart = "--" + boundary + "\r\nContent-Disposition: form-data; name=\"savepath\"\r\n\r\n" + savePath + "\r\n";
         parts.add(savePart.getBytes(StandardCharsets.UTF_8));
      }

      if (StringUtils.hasText(tag)) {
         String tagPart = "--" + boundary + "\r\nContent-Disposition: form-data; name=\"tags\"\r\n\r\n" + tag + "\r\n";
         parts.add(tagPart.getBytes(StandardCharsets.UTF_8));
      }

      String end = "--" + boundary + "--\r\n";
      parts.add(end.getBytes(StandardCharsets.UTF_8));
      int total = parts.stream().mapToInt(b -> b.length).sum();
      byte[] body = new byte[total];
      int pos = 0;

      for (byte[] part : parts) {
         System.arraycopy(part, 0, body, pos, part.length);
         pos += part.length;
      }

      return body;
   }
}
