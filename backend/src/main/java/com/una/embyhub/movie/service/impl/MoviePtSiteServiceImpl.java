package com.una.embyhub.movie.service.impl;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.una.embyhub.config.common.exception.BizException;
import com.una.embyhub.movie.entity.MoviePtSiteEntity;
import com.una.embyhub.movie.mapper.MoviePtSiteMapper;
import com.una.embyhub.movie.model.MoviePtSite;
import com.una.embyhub.movie.model.MoviePtSiteSaveRequest;
import com.una.embyhub.movie.model.MoviePtUserStats;
import com.una.embyhub.movie.service.MoviePtSiteService;
import jakarta.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import lombok.Generated;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class MoviePtSiteServiceImpl implements MoviePtSiteService {
   private static final String SITE_TYPE_MTEAM = "mteam";
   private static final String SITE_TYPE_NEXUSPHP = "nexusphp";
   private static final Pattern UPLOAD_PATTERN = Pattern.compile("(?:上传(?:量)?|Uploaded)[:\\s]*([\\d.]+\\s*(?:PB|TB|GB|MB|KB|B|PiB|TiB|GiB|MiB|KiB))", 2);
   private static final Pattern DOWNLOAD_PATTERN = Pattern.compile("(?:下载(?:量)?|Downloaded)[:\\s]*([\\d.]+\\s*(?:PB|TB|GB|MB|KB|B|PiB|TiB|GiB|MiB|KiB))", 2);
   private static final Pattern RATIO_PATTERN = Pattern.compile("(?:分享率|Ratio)[:\\s]*([\\d.]+)", 2);
   private final Map<Long, MoviePtUserStats> userStatsCache = new ConcurrentHashMap<>();
   private final MoviePtSiteMapper moviePtSiteMapper;

   @Override
   public List<MoviePtSite> list() {
      return this.list(false);
   }

   @Override
   public List<MoviePtSite> list(boolean includeStats) {
      return this.list(null, includeStats);
   }

   @Override
   public List<MoviePtSite> list(Integer enabled, boolean includeStats) {
      QueryWrapper<MoviePtSiteEntity> wrapper = new QueryWrapper<>();
      if (enabled != null) {
         wrapper.eq("enabled", enabled);
      }

      wrapper.orderByAsc("id");
      List<MoviePtSiteEntity> entities = this.moviePtSiteMapper.selectList(wrapper);
      List<MoviePtSite> list = new ArrayList<>();

      for (MoviePtSiteEntity entity : entities) {
         MoviePtSite model = this.toModel(entity);
         if (includeStats) {
            MoviePtUserStats stats = this.userStatsCache.get(entity.getId());
            if (stats == null) {
               stats = this.fetchUserStats(entity);
               if (stats != null) {
                  this.userStatsCache.put(entity.getId(), stats);
               }
            }

            model.setUserStats(stats);
         }

         list.add(model);
      }

      return list;
   }

   @Override
   public MoviePtSite getById(Long id) {
      return this.getById(id, false);
   }

   @Override
   public MoviePtSite getById(Long id, boolean includeStats) {
      MoviePtSiteEntity entity = this.moviePtSiteMapper.selectById(id);
      if (entity == null) {
         throw new BizException("站点不存在");
      } else {
         MoviePtSite model = this.toModel(entity);
         if (includeStats) {
            MoviePtUserStats stats = this.userStatsCache.get(entity.getId());
            if (stats == null) {
               stats = this.fetchUserStats(entity);
               if (stats != null) {
                  this.userStatsCache.put(entity.getId(), stats);
               }
            }

            model.setUserStats(stats);
         }

         return model;
      }
   }

   @Override
   public void refreshAllUserStats() {
      QueryWrapper<MoviePtSiteEntity> wrapper = new QueryWrapper<>();

      for (MoviePtSiteEntity entity : this.moviePtSiteMapper.selectList(wrapper)) {
         try {
            MoviePtUserStats stats = this.fetchUserStats(entity);
            if (stats != null) {
               this.userStatsCache.put(entity.getId(), stats);
            }
         } catch (Exception var6) {
         }
      }
   }

   @Override
   public MoviePtSite save(MoviePtSiteSaveRequest request) {
      String name = this.trimToNull(request.getName());
      String baseUrl = this.trimToNull(request.getBaseUrl());
      if (StringUtils.hasText(name) && StringUtils.hasText(baseUrl)) {
         String siteType = this.resolveSiteType(baseUrl, request.getSiteType());
         Integer enabled = request.getEnabled() == null ? 1 : request.getEnabled();
         MoviePtSiteEntity entity;
         if (request.getId() != null) {
            entity = this.moviePtSiteMapper.selectById(request.getId());
            if (entity == null) {
               throw new BizException("站点不存在，无法修改");
            }
         } else {
            entity = new MoviePtSiteEntity();
         }

         entity.setName(name);
         entity.setBaseUrl(baseUrl);
         entity.setCookies(request.getCookies());
         entity.setToken(request.getToken());
         entity.setSiteType(siteType);
         entity.setEnabled(enabled);
         if (entity.getId() == null) {
            this.moviePtSiteMapper.insert(entity);
         } else {
            this.moviePtSiteMapper.updateById(entity);
         }

         this.refreshUserStatsCache(entity);
         return this.toModel(entity);
      } else {
         throw new BizException("站点名称和地址不能为空");
      }
   }

   private void refreshUserStatsCache(MoviePtSiteEntity entity) {
      if (entity != null && entity.getId() != null) {
         Long siteId = entity.getId();
         if (!Integer.valueOf(1).equals(entity.getEnabled())) {
            this.userStatsCache.remove(siteId);
         } else {
            MoviePtUserStats latestStats = this.fetchUserStats(entity);
            if (latestStats == null) {
               this.userStatsCache.remove(siteId);
            } else {
               this.userStatsCache.put(siteId, latestStats);
            }
         }
      }
   }

   private String trimToNull(String value) {
      if (value == null) {
         return null;
      } else {
         String trimmed = value.trim();
         return trimmed.isEmpty() ? null : trimmed;
      }
   }

   private String resolveSiteType(String baseUrl, String provided) {
      String providedType = this.trimToNull(provided);
      if (StringUtils.hasText(providedType)) {
         return this.normalizeSiteType(providedType);
      } else {
         if (StringUtils.hasText(baseUrl)) {
            String lower = baseUrl.toLowerCase(Locale.ROOT);
            if (lower.contains("m-team") || lower.contains("mteam")) {
               return "mteam";
            }
         }

         return "nexusphp";
      }
   }

   private String normalizeSiteType(String type) {
      String lower = type.toLowerCase(Locale.ROOT);
      return lower.contains("mteam") ? "mteam" : "nexusphp";
   }

   private MoviePtUserStats fetchUserStats(MoviePtSiteEntity entity) {
      if (entity != null && StringUtils.hasText(entity.getBaseUrl())) {
         String baseUrl = this.trimSuffix(entity.getBaseUrl());
         String siteType = this.resolveSiteType(baseUrl, entity.getSiteType());
         return "mteam".equalsIgnoreCase(siteType)
            ? this.fetchMteamStats(baseUrl, entity.getToken())
            : this.fetchNexusStats(baseUrl, entity.getCookies(), entity.getToken());
      } else {
         return null;
      }
   }

   private MoviePtUserStats fetchMteamStats(String baseUrl, String token) {
      String apiKey = this.resolveMteamApiKey(token);
      if (!StringUtils.hasText(apiKey)) {
         return null;
      } else {
         String apiBase = this.resolveMteamApiBase(baseUrl);

         try {
            HttpResponse response = HttpRequest.post(apiBase + "/api/member/profile").header("x-api-key", apiKey).timeout(10000).execute();
            if (!response.isOk()) {
               return null;
            }

            MoviePtUserStats stats = this.parseMteamStats(response.body());
            if (stats != null) {
               return stats;
            }
         } catch (Exception var7) {
         }

         return null;
      }
   }

   private MoviePtUserStats parseMteamStats(String body) {
      if (!StringUtils.hasText(body)) {
         return null;
      } else {
         JSONObject payload;
         try {
            payload = JSON.parseObject(body);
         } catch (Exception var11) {
            return null;
         }

         JSONObject data = payload.getJSONObject("data");
         if (data == null) {
            data = payload;
         }

         JSONObject memberCount = data.getJSONObject("memberCount");
         JSONObject statsSource = memberCount != null ? memberCount : data;
         String username = this.resolveJsonString(data, "username", "userName", "nickname", "name");
         String uploaded = this.resolveJsonString(statsSource, "uploaded", "upload", "uploadSize", "uploadBytes");
         String downloaded = this.resolveJsonString(statsSource, "downloaded", "download", "downloadSize", "downloadBytes");
         String ratio = this.resolveJsonString(statsSource, "shareRate", "ratio", "shareRatio", "share_ratio");
         MoviePtUserStats stats = MoviePtUserStats.builder()
            .username(username)
            .uploaded(this.formatBytesIfNumeric(uploaded))
            .downloaded(this.formatBytesIfNumeric(downloaded))
            .ratio(ratio)
            .build();
         return !StringUtils.hasText(stats.getUsername())
               && !StringUtils.hasText(stats.getUploaded())
               && !StringUtils.hasText(stats.getDownloaded())
               && !StringUtils.hasText(stats.getRatio())
            ? null
            : stats;
      }
   }

   private MoviePtUserStats fetchNexusStats(String baseUrl, String cookies, String token) {
      Map<String, String> headers = this.buildCommonHeaders(baseUrl);
      this.applyTokenHeaders(headers, token);
      String cookieHeader = this.buildCookieHeader(cookies);
      String url = baseUrl + "/index.php";

      try {
         HttpResponse response = HttpRequest.get(url).headerMap(headers, true).setFollowRedirects(true).timeout(10000).cookie(cookieHeader).execute();
         String html = response.body();
         return StringUtils.hasText(html) && !this.looksLikeLoginPage(html) ? this.parseNexusStats(html) : null;
      } catch (Exception var9) {
         return null;
      }
   }

   private MoviePtUserStats parseNexusStats(String html) {
      Document doc = Jsoup.parse(html);
      String username = this.extractUsername(doc);
      String text = doc.text().replace(' ', ' ').replace('：', ':');
      String uploaded = this.extractByPattern(UPLOAD_PATTERN, text);
      String downloaded = this.extractByPattern(DOWNLOAD_PATTERN, text);
      String ratio = this.extractByPattern(RATIO_PATTERN, text);
      MoviePtUserStats stats = MoviePtUserStats.builder().username(username).uploaded(uploaded).downloaded(downloaded).ratio(ratio).build();
      return !StringUtils.hasText(stats.getUsername())
            && !StringUtils.hasText(stats.getUploaded())
            && !StringUtils.hasText(stats.getDownloaded())
            && !StringUtils.hasText(stats.getRatio())
         ? null
         : stats;
   }

   private String extractUsername(Document doc) {
      if (doc == null) {
         return "";
      } else {
         Element userLink = doc.selectFirst("a[href*=userdetails.php]");
         return userLink != null && StringUtils.hasText(userLink.text()) ? userLink.text().trim() : "";
      }
   }

   private String extractByPattern(Pattern pattern, String text) {
      if (!StringUtils.hasText(text)) {
         return "";
      } else {
         Matcher matcher = pattern.matcher(text);
         return matcher.find() ? matcher.group(1).trim() : "";
      }
   }

   private String resolveMteamApiBase(String baseUrl) {
      String pattern = "^https?://[^.]+\\.";
      return baseUrl.replaceFirst(pattern, "https://api.");
   }

   private String resolveMteamApiKey(String token) {
      if (!StringUtils.hasText(token)) {
         return "";
      } else {
         String trimmed = token.trim();
         if (trimmed.startsWith("{")) {
            try {
               JSONObject json = JSON.parseObject(trimmed);
               String key = json.getString("x-api-key");
               return StringUtils.hasText(key) ? key : "";
            } catch (Exception var5) {
               return trimmed;
            }
         } else {
            return trimmed;
         }
      }
   }

   private String resolveJsonString(JSONObject data, String... keys) {
      if (data != null && keys != null) {
         for (String key : keys) {
            Object value = data.get(key);
            if (value != null && StringUtils.hasText(value.toString())) {
               return value.toString();
            }
         }

         return "";
      } else {
         return "";
      }
   }

   private String formatBytesIfNumeric(String value) {
      if (!StringUtils.hasText(value)) {
         return value;
      } else {
         try {
            double bytes = Double.parseDouble(value);
            return this.formatBytes(bytes);
         } catch (NumberFormatException var4) {
            return value;
         }
      }
   }

   private String formatBytes(double bytes) {
      if (bytes < 0.0) {
         return String.valueOf(bytes);
      } else {
         String[] units = new String[]{"B", "KB", "MB", "GB", "TB", "PB"};
         int index = 0;

         double value;
         for (value = bytes; value >= 1024.0 && index < units.length - 1; index++) {
            value /= 1024.0;
         }

         return String.format(Locale.ROOT, "%.2f %s", value, units[index]);
      }
   }

   private Map<String, String> buildCommonHeaders(String baseUrl) {
      Map<String, String> headers = new HashMap<>();
      headers.put("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36");
      headers.put("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
      headers.put("Referer", baseUrl + "/index.php");
      return headers;
   }

   private void applyTokenHeaders(Map<String, String> headers, String token) {
      if (StringUtils.hasText(token)) {
         try {
            if (JSON.parse(token) instanceof JSONObject json) {
               for (String key : json.keySet()) {
                  Object value = json.get(key);
                  if (value != null) {
                     headers.put(key, String.valueOf(value));
                  }
               }

               return;
            }
         } catch (Exception var8) {
         }

         headers.put("Authorization", token);
         headers.put("x-token", token);
      }
   }

   private String buildCookieHeader(String cookies) {
      if (!StringUtils.hasText(cookies)) {
         return "";
      } else {
         String trimmed = cookies.trim();
         if (trimmed.startsWith("{")) {
            try {
               JSONObject json = JSON.parseObject(trimmed);
               StringBuilder builder = new StringBuilder();

               for (String key : json.keySet()) {
                  if (builder.length() > 0) {
                     builder.append("; ");
                  }

                  builder.append(key).append("=").append(json.getString(key));
               }

               return builder.toString();
            } catch (Exception var7) {
               return trimmed;
            }
         } else {
            return trimmed;
         }
      }
   }

   private boolean looksLikeLoginPage(String html) {
      if (!StringUtils.hasText(html)) {
         return false;
      } else {
         String lower = html.toLowerCase(Locale.ROOT);
         return lower.contains("<title>登录</title>")
            || lower.contains("login.php")
            || lower.contains("name=\"password\"")
            || lower.contains("name=\"username\"")
            || lower.contains("loginform");
      }
   }

   private String trimSuffix(String value) {
      if (!StringUtils.hasText(value)) {
         return "";
      } else {
         String trimmed = value.trim();

         while (trimmed.endsWith("/")) {
            trimmed = trimmed.substring(0, trimmed.length() - 1);
         }

         return trimmed;
      }
   }

   @Override
   public void getFavicon(Long id, HttpServletResponse response) {
      MoviePtSiteEntity entity = this.moviePtSiteMapper.selectById(id);
      if (entity == null) {
         response.setStatus(404);
      } else {
         String baseUrl = entity.getBaseUrl();
         if (!StringUtils.hasText(baseUrl)) {
            response.setStatus(404);
         } else {
            if (baseUrl.endsWith("/")) {
               baseUrl = baseUrl.substring(0, baseUrl.length() - 1);
            }

            String[] potentialPaths = new String[]{"/favicon.ico", "/favicon.png", "/static/favicon.ico"};

            for (String path : potentialPaths) {
               String url = baseUrl + path;

               try {
                  try (HttpResponse resp = HttpUtil.createGet(url).timeout(5000).execute()) {
                     if (!resp.isOk() || resp.bodyBytes().length <= 0) {
                        continue;
                     }

                     String contentType = resp.header("Content-Type");
                     if (contentType != null && contentType.toLowerCase().contains("html")) {
                        continue;
                     }

                     if (contentType == null) {
                        contentType = "image/x-icon";
                     }

                     response.setContentType(contentType);
                     response.getOutputStream().write(resp.bodyBytes());
                     response.flushBuffer();
                  }

                  return;
               } catch (Exception var16) {
               }
            }

            response.setStatus(404);
         }
      }
   }

   @Override
   public void deleteById(Long id) {
      MoviePtSiteEntity entity = this.moviePtSiteMapper.selectById(id);
      if (entity == null) {
         throw new BizException("站点不存在");
      } else {
         this.moviePtSiteMapper.deleteById(id);
         this.userStatsCache.remove(id);
      }
   }

   private MoviePtSite toModel(MoviePtSiteEntity entity) {
      if (entity == null) {
         return null;
      } else {
         MoviePtSite model = MoviePtSite.builder()
            .id(entity.getId())
            .name(entity.getName())
            .baseUrl(entity.getBaseUrl())
            .cookies(entity.getCookies())
            .token(entity.getToken())
            .siteType(entity.getSiteType())
            .enabled(entity.getEnabled())
            .build();
         model.setFavicon("/api/movie/pt-sites/" + entity.getId() + "/favicon");
         return model;
      }
   }

   @Generated
   public MoviePtSiteServiceImpl(final MoviePtSiteMapper moviePtSiteMapper) {
      this.moviePtSiteMapper = moviePtSiteMapper;
   }
}
