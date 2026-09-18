package com.una.embyhub.movie.service.impl;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.una.embyhub.movie.entity.MovieDownloadRecordEntity;
import com.una.embyhub.movie.entity.MoviePtSubscribeEntity;
import com.una.embyhub.movie.mapper.MoviePtSubscribeMapper;
import com.una.embyhub.movie.model.MovieActionResponse;
import com.una.embyhub.movie.model.MoviePtDownloadRequest;
import com.una.embyhub.movie.model.MoviePtSite;
import com.una.embyhub.movie.model.MovieQbittorrentConfig;
import com.una.embyhub.movie.model.MovieQbittorrentTorrent;
import com.una.embyhub.movie.model.MovieScrapePathConfig;
import com.una.embyhub.movie.service.MovieDownloadRecordService;
import com.una.embyhub.movie.service.MoviePtDownloadNotifyAsyncService;
import com.una.embyhub.movie.service.MoviePtDownloadService;
import com.una.embyhub.movie.service.MoviePtSiteService;
import com.una.embyhub.movie.service.MovieQbittorrentService;
import com.una.embyhub.movie.service.MovieScrapePathConfigService;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.net.URI;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.GZIPInputStream;
import lombok.Generated;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class MoviePtDownloadServiceImpl implements MoviePtDownloadService {
   @Generated
   private static final Logger log = LoggerFactory.getLogger(MoviePtDownloadServiceImpl.class);
   private static final Pattern FILENAME_PATTERN = Pattern.compile("filename[*]?=(\"|')?(.+?)(\\1)?(?:;|$)");
   private static final Pattern FORM_PATTERN = Pattern.compile("<form.*?action=\"(.*?)\".*?>(.*?)</form>", 34);
   private static final Pattern INPUT_PATTERN = Pattern.compile("<input.*?name=\"(.*?)\".*?value=\"(.*?)\".*?>", 34);
   private static final Pattern DOWNLOAD_ONCLICK_PATTERN = Pattern.compile("(?i)(download\\.php[^'\"\\s)]+)");
   private static final String DEFAULT_USER_AGENT = "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/124.0.0.0 Safari/537.36";
   private static final int MAX_REDIRECTS = 5;
   private static final int QB_HASH_RETRY_COUNT = 9;
   private static final int QB_HASH_RETRY_DELAY_MS = 3000;
   private static final String QB_TAG_DEFAULT = "Mist";
   private static final int QB_TAG_REMOVE_RETRY_COUNT = 5;
   private static final int QB_TAG_REMOVE_RETRY_DELAY_MS = 1000;
   private final MoviePtSiteService moviePtSiteService;
   private final MovieQbittorrentService movieQbittorrentService;
   private final MovieScrapePathConfigService movieScrapePathConfigService;
   private final MovieDownloadRecordService movieDownloadRecordService;
   private final MoviePtSubscribeMapper moviePtSubscribeMapper;
   private final MoviePtDownloadNotifyAsyncService moviePtDownloadNotifyAsyncService;

   @Override
   public MovieActionResponse downloadAndAdd(MoviePtDownloadRequest request) {
      if (request == null) {
         return MovieActionResponse.builder().success(false).message("请求参数不能为空").build();
      } else if (!StringUtils.hasText(request.getMovieName())) {
         return MovieActionResponse.builder().success(false).message("影片名称不能为空").build();
      } else if (request.getScrapePathConfigId() == null) {
         return MovieActionResponse.builder().success(false).message("刮削目录配置不能为空").build();
      } else {
         MovieScrapePathConfig scrapePathConfig;
         try {
            scrapePathConfig = this.movieScrapePathConfigService.getById(request.getScrapePathConfigId());
         } catch (Exception var12) {
            return MovieActionResponse.builder().success(false).message("刮削目录配置不存在").build();
         }

         String savePath = this.resolveSavePath(request.getSavePath(), scrapePathConfig);
         String downloadUrl = this.trimToEmpty(request.getDownloadUrl());
         String torrentId = this.trimToEmpty(request.getTorrentId());
         if (!StringUtils.hasText(torrentId) && StringUtils.hasText(downloadUrl)) {
            torrentId = this.extractTorrentId(downloadUrl);
         }

         MoviePtSite site = null;
         if (request.getSiteId() != null) {
            try {
               site = this.moviePtSiteService.getById(request.getSiteId());
            } catch (Exception var11) {
               return MovieActionResponse.builder().success(false).message("站点不存在").build();
            }
         }

         if (!StringUtils.hasText(downloadUrl)) {
            if (site == null || !StringUtils.hasText(torrentId) || !StringUtils.hasText(site.getBaseUrl())) {
               return MovieActionResponse.builder().success(false).message("downloadUrl 或 (siteId + torrentId) 必填").build();
            }

            if (this.isMteam(site)) {
               String encoded = this.buildMteamDownloadUrl(site, torrentId);
               if (!StringUtils.hasText(encoded)) {
                  return MovieActionResponse.builder().success(false).message("M-Team 下载链接获取失败").build();
               }

               downloadUrl = encoded;
            } else {
               String baseUrl = this.trimSuffix(site.getBaseUrl());
               downloadUrl = this.resolveNexusDownloadUrl(site, baseUrl, torrentId);
            }
         }

         MoviePtDownloadServiceImpl.ResolvedDownloadUrl resolved = this.resolveDownloadUrl(downloadUrl, site);
         if (resolved == null || !StringUtils.hasText(resolved.url)) {
            return MovieActionResponse.builder().success(false).message("下载链接解析失败").build();
         } else if (site != null) {
            String finalDownloadUrl = this.normalizeSiteUrl(resolved.url, this.trimSuffix(site.getBaseUrl()));
            if (!this.isMteam(site) && StringUtils.hasText(torrentId) && this.needsNexusRefresh(finalDownloadUrl, torrentId)) {
               String refreshed = this.resolveNexusDownloadUrl(site, this.trimSuffix(site.getBaseUrl()), torrentId);
               if (StringUtils.hasText(refreshed)) {
                  finalDownloadUrl = refreshed;
               }
            }

            MoviePtDownloadServiceImpl.TorrentDownloadResult result = this.downloadTorrent(finalDownloadUrl, site, torrentId, resolved.useCookie);
            if (StringUtils.hasText(result.magnet)) {
               return this.addToQbittorrentWithRecord(request, scrapePathConfig, result.magnet, savePath);
            } else if (result.content != null && result.content.length != 0) {
               String filename = StringUtils.hasText(result.filename)
                  ? result.filename
                  : this.resolveFilename(null, StringUtils.hasText(torrentId) ? torrentId : "download");
               return this.addTorrentFileWithRecord(request, scrapePathConfig, result.content, filename, savePath);
            } else {
               return MovieActionResponse.builder().success(false).message(StringUtils.hasText(result.error) ? result.error : "下载失败：种子内容为空").build();
            }
         } else {
            return this.addToQbittorrentWithRecord(request, scrapePathConfig, resolved.url, savePath);
         }
      }
   }

   private MoviePtDownloadServiceImpl.ResolvedDownloadUrl resolveDownloadUrl(String downloadUrl, MoviePtSite site) {
      if (!StringUtils.hasText(downloadUrl)) {
         return null;
      } else {
         String trimmed = downloadUrl.trim();
         if (!trimmed.startsWith("[")) {
            return new MoviePtDownloadServiceImpl.ResolvedDownloadUrl(trimmed, true);
         } else {
            int end = trimmed.indexOf(93);
            if (end <= 1) {
               return new MoviePtDownloadServiceImpl.ResolvedDownloadUrl(trimmed, true);
            } else {
               String base64 = trimmed.substring(1, end);
               String url = trimmed.substring(end + 1);
               if (!StringUtils.hasText(url)) {
                  return new MoviePtDownloadServiceImpl.ResolvedDownloadUrl("", false);
               } else {
                  JSONObject params;
                  try {
                     String json = new String(Base64.getDecoder().decode(base64), StandardCharsets.UTF_8);
                     params = JSON.parseObject(json);
                  } catch (Exception var21) {
                     log.warn("下载链接解析失败: {}", var21.getMessage());
                     return new MoviePtDownloadServiceImpl.ResolvedDownloadUrl(trimmed, true);
                  }

                  boolean useCookie = params.getBoolean("cookie") == null || params.getBooleanValue("cookie");
                  String method = this.trimToEmpty(params.getString("method"));
                  JSONObject reqParams = params.getJSONObject("params");
                  JSONObject headers = params.getJSONObject("header");
                  String resultPath = this.trimToEmpty(params.getString("result"));
                  HttpRequest request = "get".equalsIgnoreCase(method) ? HttpRequest.get(url) : HttpRequest.post(url);
                  request.header(
                        "User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/124.0.0.0 Safari/537.36"
                     )
                     .timeout(15000);
                  if (headers != null) {
                     for (String key : headers.keySet()) {
                        Object value = headers.get(key);
                        if (value != null) {
                           request.header(key, String.valueOf(value));
                        }
                     }
                  }

                  if (useCookie && site != null) {
                     String cookieHeader = this.buildCookieHeader(site.getCookies());
                     if (StringUtils.hasText(cookieHeader)) {
                        request.cookie(cookieHeader);
                     }
                  }

                  if (reqParams != null && !reqParams.isEmpty()) {
                     Map<String, Object> form = new HashMap<>();

                     for (String keyx : reqParams.keySet()) {
                        form.put(keyx, reqParams.get(keyx));
                     }

                     request.form(form);
                  }

                  HttpResponse response = request.execute();
                  if (response != null && StringUtils.hasText(response.body())) {
                     String resolved = response.body();
                     if (StringUtils.hasText(resultPath)) {
                        try {
                           Object current = JSON.parse(response.body());

                           for (String keyx : resultPath.split("\\.")) {
                              if (!(current instanceof JSONObject)) {
                                 current = null;
                                 break;
                              }

                              current = ((JSONObject)current).get(keyx);
                           }

                           if (current != null) {
                              resolved = String.valueOf(current);
                           } else {
                              resolved = "";
                           }
                        } catch (Exception var22) {
                           log.warn("解析下载链接返回值失败: {}", var22.getMessage());
                           resolved = "";
                        }
                     }

                     resolved = resolved == null ? "" : resolved.trim();
                     return new MoviePtDownloadServiceImpl.ResolvedDownloadUrl(resolved, false);
                  } else {
                     return new MoviePtDownloadServiceImpl.ResolvedDownloadUrl("", false);
                  }
               }
            }
         }
      }
   }

   private String buildMteamDownloadUrl(MoviePtSite site, String torrentId) {
      String apiKey = this.resolveApiKey(site == null ? "" : site.getToken());
      if (site != null && StringUtils.hasText(apiKey) && StringUtils.hasText(torrentId)) {
         String baseUrl = this.trimSuffix(site.getBaseUrl());
         String domain = this.getBaseDomain(baseUrl);
         if (!StringUtils.hasText(domain)) {
            return "";
         } else {
            String apiUrl = "https://api." + domain + "/api/torrent/genDlToken";
            JSONObject params = new JSONObject();
            params.put("method", "post");
            params.put("cookie", Boolean.valueOf(false));
            JSONObject body = new JSONObject();
            body.put("id", torrentId);
            params.put("params", body);
            JSONObject headers = new JSONObject();
            headers.put("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/124.0.0.0 Safari/537.36");
            headers.put("Accept", "application/json, text/plain, */*");
            headers.put("x-api-key", apiKey);
            params.put("header", headers);
            params.put("proxy", Boolean.valueOf(false));
            params.put("result", "data");
            String base64 = Base64.getEncoder().encodeToString(params.toJSONString().getBytes(StandardCharsets.UTF_8));
            return "[" + base64 + "]" + apiUrl;
         }
      } else {
         return "";
      }
   }

   private String resolveNexusDownloadUrl(MoviePtSite site, String baseUrl, String torrentId) {
      if (StringUtils.hasText(baseUrl) && StringUtils.hasText(torrentId)) {
         String cookieHeader = this.buildCookieHeader(site == null ? "" : site.getCookies());
         String resolved = this.fetchDownloadUrlFromDetails(site, baseUrl, torrentId, cookieHeader);
         return StringUtils.hasText(resolved) ? resolved : baseUrl + "/download.php?id=" + torrentId;
      } else {
         return "";
      }
   }

   private String fetchDownloadUrlFromDetails(MoviePtSite site, String baseUrl, String torrentId, String cookieHeader) {
      String[] detailUrls = new String[]{baseUrl + "/details.php?id=" + torrentId, baseUrl + "/detail.php?id=" + torrentId};

      for (String detailUrl : detailUrls) {
         try {
            HttpRequest request = HttpRequest.get(detailUrl)
               .header("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/124.0.0.0 Safari/537.36")
               .setFollowRedirects(true)
               .timeout(15000);
            if (StringUtils.hasText(cookieHeader)) {
               request.cookie(cookieHeader);
            }

            this.applyTokenHeaders(request, site == null ? "" : site.getToken());
            HttpResponse response = request.execute();
            if (response != null && response.getStatus() >= 200 && response.getStatus() < 300) {
               String html = response.body();
               if (StringUtils.hasText(html)) {
                  String href = this.extractDownloadHrefFromHtml(html, torrentId);
                  String normalized = this.normalizeSiteUrl(href, baseUrl);
                  if (StringUtils.hasText(normalized)) {
                     return normalized;
                  }
               }
            }
         } catch (Exception var15) {
            log.debug("从详情页提取下载链接失败 detailUrl={} err={}", detailUrl, var15.getMessage());
         }
      }

      return "";
   }

   private String extractDownloadHrefFromHtml(String html, String torrentId) {
      Document doc = Jsoup.parse(html);
      Elements anchors = doc.select("a[href*=download.php]");
      String fallback = "";

      for (Element anchor : anchors) {
         String href = this.trimToEmpty(anchor.attr("href"));
         if (StringUtils.hasText(href) && !href.toLowerCase(Locale.ROOT).startsWith("javascript:")) {
            if (!StringUtils.hasText(fallback)) {
               fallback = href;
            }

            if (!StringUtils.hasText(torrentId) || torrentId.equals(this.extractTorrentId(href))) {
               return href;
            }
         }
      }

      for (Element node : doc.select("*[onclick*=download.php]")) {
         String onclick = node.attr("onclick");
         if (StringUtils.hasText(onclick)) {
            Matcher matcher = DOWNLOAD_ONCLICK_PATTERN.matcher(onclick);

            while (matcher.find()) {
               String href = this.trimToEmpty(matcher.group(1));
               if (StringUtils.hasText(href) && (!StringUtils.hasText(torrentId) || torrentId.equals(this.extractTorrentId(href)))) {
                  return href;
               }
            }
         }
      }

      return fallback;
   }

   private String normalizeSiteUrl(String href, String baseUrl) {
      if (!StringUtils.hasText(href)) {
         return "";
      } else {
         String trimmed = href.trim().replace("&amp;", "&");
         if (trimmed.toLowerCase(Locale.ROOT).startsWith("javascript:")) {
            return "";
         } else if (trimmed.startsWith("magnet:")) {
            return trimmed;
         } else if (trimmed.startsWith("http://") || trimmed.startsWith("https://")) {
            return trimmed;
         } else if (!trimmed.startsWith("//")) {
            String cleanBase = this.trimSuffix(baseUrl);
            return !StringUtils.hasText(cleanBase) ? trimmed : cleanBase + "/" + this.trimLeadingSlash(trimmed);
         } else {
            String scheme = StringUtils.hasText(baseUrl) && baseUrl.toLowerCase(Locale.ROOT).startsWith("http://") ? "http:" : "https:";
            return scheme + trimmed;
         }
      }
   }

   private MoviePtDownloadServiceImpl.TorrentDownloadResult downloadTorrent(String downloadUrl, MoviePtSite site, String torrentId, boolean useCookie) {
      if (!StringUtils.hasText(downloadUrl)) {
         return MoviePtDownloadServiceImpl.TorrentDownloadResult.fail("下载链接为空");
      } else {
         String trimmed = downloadUrl.trim();
         if (trimmed.startsWith("magnet:")) {
            return MoviePtDownloadServiceImpl.TorrentDownloadResult.magnet(trimmed);
         } else {
            boolean mteam = this.isMteam(site);
            String baseUrl = this.trimSuffix(site.getBaseUrl());
            String currentUrl = this.normalizeSiteUrl(trimmed, baseUrl);
            if (!StringUtils.hasText(currentUrl)) {
               return MoviePtDownloadServiceImpl.TorrentDownloadResult.fail("下载链接无效");
            } else {
               String referer;
               if (mteam) {
                  referer = StringUtils.hasText(torrentId) ? baseUrl + "/detail/" + torrentId : baseUrl;
               } else {
                  referer = StringUtils.hasText(torrentId) ? baseUrl + "/details.php?id=" + torrentId : baseUrl + "/index.php";
               }

               String cookieHeader = useCookie ? this.buildCookieHeader(site.getCookies()) : "";

               for (int i = 0; i < 5; i++) {
                  HttpRequest request = HttpRequest.get(currentUrl)
                     .header(
                        "User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/124.0.0.0 Safari/537.36"
                     )
                     .setFollowRedirects(false)
                     .timeout(30000);
                  if (StringUtils.hasText(referer)) {
                     request.header("Referer", referer);
                  }

                  if (StringUtils.hasText(cookieHeader)) {
                     request.cookie(cookieHeader);
                  }

                  if (!mteam) {
                     this.applyTokenHeaders(request, site.getToken());
                  }

                  HttpResponse response;
                  try {
                     response = request.execute();
                  } catch (Exception var20) {
                     String siteName = site == null ? "-" : site.getName();
                     log.warn("下载请求失败 site={} url={} err={}", siteName, this.maskSensitiveUrl(currentUrl), var20.getMessage());
                     return MoviePtDownloadServiceImpl.TorrentDownloadResult.fail("下载失败：" + var20.getMessage());
                  }

                  if (response == null) {
                     return MoviePtDownloadServiceImpl.TorrentDownloadResult.fail("下载失败：无法连接站点");
                  }

                  int status = response.getStatus();
                  if (status != 301 && status != 302 && status != 303 && status != 307 && status != 308) {
                     if (status >= 200 && status < 300) {
                        byte[] content = response.bodyBytes();
                        if (content != null && content.length != 0) {
                           content = this.decodeIfCompressed(response.header("Content-Encoding"), content);
                           if (this.isMagnetContent(content)) {
                              return MoviePtDownloadServiceImpl.TorrentDownloadResult.magnet(new String(content, StandardCharsets.UTF_8).trim());
                           }

                           String contentType = response.header("Content-Type");
                           String text = new String(content, StandardCharsets.UTF_8);
                           if (this.looksLikeHtml(contentType, text)) {
                              if (text.contains("下载种子文件")) {
                                 return this.handleFirstDownload(currentUrl, text, cookieHeader, referer, site, torrentId);
                              }

                              return MoviePtDownloadServiceImpl.TorrentDownloadResult.fail("下载失败：可能需要重新登录站点");
                           }

                           String disposition = response.header("Content-Disposition");
                           if (!this.looksLikeTorrent(content) && !this.looksLikeTorrentByHeaders(contentType, disposition)) {
                              return MoviePtDownloadServiceImpl.TorrentDownloadResult.fail("种子数据有误，请确认链接是否正确");
                           }

                           String filename = this.resolveFilename(disposition, StringUtils.hasText(torrentId) ? torrentId : "download");
                           return MoviePtDownloadServiceImpl.TorrentDownloadResult.success(content, filename);
                        }

                        return MoviePtDownloadServiceImpl.TorrentDownloadResult.fail("下载失败：种子内容为空");
                     }

                     String siteName = site == null ? "-" : site.getName();
                     log.warn("下载请求返回非成功状态 site={} status={} url={}", siteName, status, this.maskSensitiveUrl(currentUrl));
                     return MoviePtDownloadServiceImpl.TorrentDownloadResult.fail("下载失败：状态码 " + status);
                  }

                  String location = response.header("Location");
                  if (!StringUtils.hasText(location)) {
                     return MoviePtDownloadServiceImpl.TorrentDownloadResult.fail("下载失败：重定向地址为空");
                  }

                  String redirect = this.resolveRedirectUrl(location, currentUrl, baseUrl);
                  if (!StringUtils.hasText(redirect)) {
                     return MoviePtDownloadServiceImpl.TorrentDownloadResult.fail("下载失败：重定向地址无效");
                  }

                  if (redirect.startsWith("magnet:")) {
                     return MoviePtDownloadServiceImpl.TorrentDownloadResult.magnet(redirect);
                  }

                  referer = currentUrl;
                  currentUrl = redirect;
               }

               return MoviePtDownloadServiceImpl.TorrentDownloadResult.fail("下载失败：重定向过多");
            }
         }
      }
   }

   private boolean needsNexusRefresh(String downloadUrl, String torrentId) {
      if (!StringUtils.hasText(downloadUrl)) {
         return true;
      } else {
         String lower = downloadUrl.toLowerCase(Locale.ROOT);
         if (lower.startsWith("magnet:")) {
            return false;
         } else if (!lower.contains("passkey=") && !lower.contains("torrent_pass=") && !lower.contains("authkey=") && !lower.contains("downhash=")) {
            String idInUrl = this.extractTorrentId(downloadUrl);
            return !StringUtils.hasText(idInUrl) || idInUrl.equals(torrentId);
         } else {
            return false;
         }
      }
   }

   private String resolveRedirectUrl(String location, String currentUrl, String baseUrl) {
      if (!StringUtils.hasText(location)) {
         return "";
      } else {
         String trimmed = location.trim().replace("&amp;", "&");
         if (trimmed.startsWith("magnet:") || trimmed.startsWith("http://") || trimmed.startsWith("https://")) {
            return trimmed;
         } else if (!trimmed.startsWith("//")) {
            if (StringUtils.hasText(currentUrl)) {
               try {
                  URI base = URI.create(currentUrl);
                  URI resolved = base.resolve(trimmed);
                  if (resolved != null && StringUtils.hasText(resolved.getScheme())) {
                     return resolved.toString();
                  }
               } catch (Exception var7) {
               }
            }

            return this.normalizeSiteUrl(trimmed, baseUrl);
         } else {
            String scheme = "https:";
            if (StringUtils.hasText(currentUrl) && currentUrl.toLowerCase(Locale.ROOT).startsWith("http://")) {
               scheme = "http:";
            } else if (StringUtils.hasText(baseUrl) && baseUrl.toLowerCase(Locale.ROOT).startsWith("http://")) {
               scheme = "http:";
            }

            return scheme + trimmed;
         }
      }
   }

   private MoviePtDownloadServiceImpl.TorrentDownloadResult handleFirstDownload(
      String downloadUrl, String html, String cookieHeader, String referer, MoviePtSite site, String torrentId
   ) {
      Matcher formMatcher = FORM_PATTERN.matcher(html);
      boolean attempted = false;

      while (formMatcher.find()) {
         String action = formMatcher.group(1);
         if ("?".equals(action)) {
            String formBody = formMatcher.group(2);
            Map<String, Object> data = new HashMap<>();
            Matcher inputMatcher = INPUT_PATTERN.matcher(formBody);

            while (inputMatcher.find()) {
               data.put(inputMatcher.group(1), inputMatcher.group(2));
            }

            if (!data.isEmpty()) {
               attempted = true;
               HttpRequest post = HttpRequest.post(downloadUrl)
                  .header("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/124.0.0.0 Safari/537.36")
                  .header("Referer", referer)
                  .timeout(30000);
               if (StringUtils.hasText(cookieHeader)) {
                  post.cookie(cookieHeader);
               }

               this.applyTokenHeaders(post, site.getToken());
               post.form(data);
               HttpResponse response = post.execute();
               if (response != null && response.getStatus() >= 200 && response.getStatus() < 300) {
                  byte[] content = response.bodyBytes();
                  if (content != null && content.length > 0 && this.looksLikeTorrent(content)) {
                     String filename = this.resolveFilename(response.header("Content-Disposition"), StringUtils.hasText(torrentId) ? torrentId : "download");
                     return MoviePtDownloadServiceImpl.TorrentDownloadResult.success(content, filename);
                  }
               }
               break;
            }
         }
      }

      return attempted
         ? MoviePtDownloadServiceImpl.TorrentDownloadResult.fail("种子数据有误，请确认链接是否正确，如为PT站点则需手工在站点下载一次种子")
         : MoviePtDownloadServiceImpl.TorrentDownloadResult.fail("下载失败：种子内容为空");
   }

   private boolean looksLikeHtml(String contentType, String text) {
      if (StringUtils.hasText(contentType) && contentType.toLowerCase(Locale.ROOT).contains("text/html")) {
         return true;
      } else if (!StringUtils.hasText(text)) {
         return false;
      } else {
         String lower = text.toLowerCase(Locale.ROOT);
         return lower.contains("<html") || lower.contains("<!doctype html");
      }
   }

   private boolean isMagnetContent(byte[] content) {
      if (content != null && content.length != 0) {
         String text = new String(content, StandardCharsets.UTF_8).trim();
         return text.startsWith("magnet:");
      } else {
         return false;
      }
   }

   private boolean looksLikeTorrent(byte[] content) {
      if (content == null || content.length < 8) {
         return false;
      } else if (content[0] != 100) {
         return false;
      } else {
         String text = new String(content, StandardCharsets.ISO_8859_1);
         return text.contains("4:info");
      }
   }

   private boolean looksLikeTorrentByHeaders(String contentType, String contentDisposition) {
      if (StringUtils.hasText(contentType)) {
         String lower = contentType.toLowerCase(Locale.ROOT);
         if (lower.contains("application/x-bittorrent") || lower.contains("application/octet-stream")) {
            return true;
         }
      }

      if (StringUtils.hasText(contentDisposition)) {
         String lower = contentDisposition.toLowerCase(Locale.ROOT);
         return lower.contains(".torrent");
      } else {
         return false;
      }
   }

   private byte[] decodeIfCompressed(String contentEncoding, byte[] content) {
      if (content != null && content.length >= 2) {
         boolean gzip = content[0] == 31 && content[1] == -117;
         if (StringUtils.hasText(contentEncoding) && contentEncoding.toLowerCase(Locale.ROOT).contains("gzip")) {
            gzip = true;
         }

         if (!gzip) {
            return content;
         } else {
            try {
               byte[] var9;
               try (
                  ByteArrayInputStream input = new ByteArrayInputStream(content);
                  GZIPInputStream gzipStream = new GZIPInputStream(input);
                  ByteArrayOutputStream output = new ByteArrayOutputStream();
               ) {
                  byte[] buffer = new byte[4096];

                  int len;
                  while ((len = gzipStream.read(buffer)) > 0) {
                     output.write(buffer, 0, len);
                  }

                  var9 = output.toByteArray();
               }

               return var9;
            } catch (Exception var16) {
               return content;
            }
         }
      } else {
         return content;
      }
   }

   private MovieActionResponse addToQbittorrentWithRecord(MoviePtDownloadRequest request, MovieScrapePathConfig config, String magnet, String savePath) {
      Long usedDownloaderId = this.resolveDownloaderId(request.getDownloaderId());
      MovieDownloadRecordEntity record = this.movieDownloadRecordService.createRecord(request, config, savePath, usedDownloaderId);
      String magnetHash = this.extractMagnetHash(magnet);
      String magnetName = this.extractMagnetDisplayName(magnet);
      if (StringUtils.hasText(magnetHash) || StringUtils.hasText(magnetName)) {
         this.movieDownloadRecordService.updateQbInfo(record.getId(), record.getQbTag(), magnetHash, magnetName);
      }

      String qbTags = this.buildQbTags(record.getQbTag());
      MovieActionResponse response;
      if (usedDownloaderId != null) {
         response = this.movieQbittorrentService.addMagnet(usedDownloaderId, magnet, savePath, qbTags);
      } else {
         response = this.movieQbittorrentService.addMagnet(magnet, savePath, qbTags);
      }

      if (response.isSuccess()) {
         this.writeBackDownloaderIdToSubscribe(request.getSubscribeId(), usedDownloaderId);
         boolean synced = this.syncQbInfoAfterAdd(record, usedDownloaderId);
         if (!synced) {
            this.rollbackRecordOnQbSyncFail(record);
            return MovieActionResponse.builder().success(false).message("下载任务已提交，但未获取到 qBittorrent 任务信息，已回滚下载记录").build();
         }

         this.movieDownloadRecordService.updateStatus(record.getId(), "DOWNLOADING");
         this.sendDownloadNotifyAsyncIfNeeded(request);
      } else {
         this.movieDownloadRecordService.updateStatus(record.getId(), "FAILED");
      }

      return response;
   }

   private MovieActionResponse addTorrentFileWithRecord(
      MoviePtDownloadRequest request, MovieScrapePathConfig config, byte[] content, String filename, String savePath
   ) {
      Long usedDownloaderId = this.resolveDownloaderId(request.getDownloaderId());
      MovieDownloadRecordEntity record = this.movieDownloadRecordService.createRecord(request, config, savePath, usedDownloaderId);
      if (StringUtils.hasText(filename)) {
         this.movieDownloadRecordService.updateQbInfo(record.getId(), record.getQbTag(), null, filename);
      }

      String qbTags = this.buildQbTags(record.getQbTag());
      MovieActionResponse response;
      if (usedDownloaderId != null) {
         response = this.movieQbittorrentService.addTorrentFile(usedDownloaderId, content, filename, savePath, qbTags);
      } else {
         response = this.movieQbittorrentService.addTorrentFile(content, filename, savePath, qbTags);
      }

      if (response.isSuccess()) {
         this.writeBackDownloaderIdToSubscribe(request.getSubscribeId(), usedDownloaderId);
         boolean synced = this.syncQbInfoAfterAdd(record, usedDownloaderId);
         if (!synced) {
            this.rollbackRecordOnQbSyncFail(record);
            return MovieActionResponse.builder().success(false).message("下载任务已提交，但未获取到 qBittorrent 任务信息，已回滚下载记录").build();
         }

         this.movieDownloadRecordService.updateStatus(record.getId(), "DOWNLOADING");
         this.sendDownloadNotifyAsyncIfNeeded(request);
      } else {
         this.movieDownloadRecordService.updateStatus(record.getId(), "FAILED");
      }

      return response;
   }

   private void sendDownloadNotifyAsyncIfNeeded(MoviePtDownloadRequest request) {
      if (request != null && !Boolean.TRUE.equals(request.getSkipNotify())) {
         try {
            this.moviePtDownloadNotifyAsyncService.sendDownloadNotify(this.copyRequestForNotify(request));
         } catch (Exception var3) {
            log.warn("异步发送下载通知失败 movieName={} subscribeId={} err={}", request.getMovieName(), request.getSubscribeId(), var3.getMessage());
         }
      }
   }

   private MoviePtDownloadRequest copyRequestForNotify(MoviePtDownloadRequest source) {
      MoviePtDownloadRequest copy = new MoviePtDownloadRequest();
      copy.setSubscribeId(source.getSubscribeId());
      copy.setTmdbId(source.getTmdbId());
      copy.setMovieName(source.getMovieName());
      copy.setMovieYear(source.getMovieYear());
      copy.setMediaType(source.getMediaType());
      copy.setTitle(source.getTitle());
      copy.setPosterUrl(source.getPosterUrl());
      copy.setCoverUrl(source.getCoverUrl());
      copy.setSize(source.getSize());
      copy.setSiteId(source.getSiteId());
      return copy;
   }

   private Long resolveDownloaderId(Long requestDownloaderId) {
      if (requestDownloaderId != null) {
         return requestDownloaderId;
      } else {
         try {
            MovieQbittorrentConfig config = this.movieQbittorrentService.getConfig();
            return config == null ? null : config.getId();
         } catch (Exception var3) {
            return null;
         }
      }
   }

   private void writeBackDownloaderIdToSubscribe(Long subscribeId, Long downloaderId) {
      if (subscribeId != null && downloaderId != null) {
         try {
            MoviePtSubscribeEntity subscribe = this.moviePtSubscribeMapper.selectById(subscribeId);
            if (subscribe == null || subscribe.getDelFlag() == 1) {
               return;
            }

            if (downloaderId.equals(subscribe.getDownloaderId())) {
               return;
            }

            subscribe.setDownloaderId(downloaderId);
            this.moviePtSubscribeMapper.updateById(subscribe);
         } catch (Exception var4) {
            log.warn("回写订阅下载器失败 subscribeId={} downloaderId={} err={}", subscribeId, downloaderId, var4.getMessage());
         }
      }
   }

   private String buildQbTags(String uniqueTag) {
      if (!StringUtils.hasText(uniqueTag)) {
         return "Mist";
      } else {
         return !StringUtils.hasText("Mist") ? uniqueTag : "Mist," + uniqueTag;
      }
   }

   private boolean syncQbInfoAfterAdd(MovieDownloadRecordEntity record, Long downloaderId) {
      if (record != null && StringUtils.hasText(record.getQbTag())) {
         String uniqueTag = record.getQbTag();
         MovieQbittorrentTorrent torrent = this.waitForTorrentByTag(downloaderId, uniqueTag);
         if (torrent == null) {
            log.warn("未能获取 qBittorrent 任务信息 tag={}", record.getQbTag());
            return false;
         } else {
            this.movieDownloadRecordService.updateQbInfo(record.getId(), null, torrent.getHash(), torrent.getName());
            boolean removed = this.removeQbTag(downloaderId, torrent.getHash(), uniqueTag);
            if (!removed) {
               log.warn("移除 qBittorrent 临时标签失败 hash={} tag={}", torrent.getHash(), uniqueTag);
            }

            return true;
         }
      } else {
         return false;
      }
   }

   private void rollbackRecordOnQbSyncFail(MovieDownloadRecordEntity record) {
      if (record != null && record.getId() != null) {
         try {
            this.movieDownloadRecordService.deleteRecord(List.of(record.getId()), false, false);
         } catch (Exception var3) {
            log.warn("回滚下载记录失败 recordId={}: {}", record.getId(), var3.getMessage());
         }
      }
   }

   private MovieQbittorrentTorrent waitForTorrentByTag(Long downloaderId, String tag) {
      if (!StringUtils.hasText(tag)) {
         return null;
      } else {
         for (int i = 0; i < 9; i++) {
            try {
               Thread.sleep(3000L);
            } catch (InterruptedException var5) {
               Thread.currentThread().interrupt();
               return null;
            }

            List<MovieQbittorrentTorrent> torrents = downloaderId != null
               ? this.movieQbittorrentService.getQueueByTag(downloaderId, tag)
               : this.movieQbittorrentService.getQueueByTag(tag);
            if (torrents != null && !torrents.isEmpty()) {
               return torrents.get(0);
            }
         }

         return null;
      }
   }

   private boolean removeQbTag(Long downloaderId, String hash, String tag) {
      if (StringUtils.hasText(hash) && StringUtils.hasText(tag)) {
         String normalizedTag = tag.trim();

         for (int i = 0; i < 5; i++) {
            try {
               if (downloaderId != null) {
                  this.movieQbittorrentService.removeTags(downloaderId, hash, normalizedTag);
               } else {
                  this.movieQbittorrentService.removeTags(hash, normalizedTag);
               }
            } catch (Exception var8) {
               log.debug("移除 qBittorrent 临时标签异常 hash={} tag={} err={}", hash, normalizedTag, var8.getMessage());
            }

            if (!this.hasTag(downloaderId, hash, normalizedTag)) {
               this.deleteQbTag(downloaderId, normalizedTag);
               return true;
            }

            if (i < 4) {
               try {
                  Thread.sleep(1000L);
               } catch (InterruptedException var7) {
                  Thread.currentThread().interrupt();
                  return false;
               }
            }
         }

         return false;
      } else {
         return false;
      }
   }

   private boolean hasTag(Long downloaderId, String hash, String tag) {
      if (StringUtils.hasText(hash) && StringUtils.hasText(tag)) {
         try {
            List<MovieQbittorrentTorrent> torrents = downloaderId != null
               ? this.movieQbittorrentService.getQueueByHash(downloaderId, hash)
               : this.movieQbittorrentService.getQueueByHash(hash);
            if (torrents != null && !torrents.isEmpty()) {
               for (MovieQbittorrentTorrent torrent : torrents) {
                  if (this.containsTag(torrent == null ? null : torrent.getTags(), tag)) {
                     return true;
                  }
               }

               return false;
            } else {
               return false;
            }
         } catch (Exception var7) {
            log.debug("校验 qBittorrent 标签异常 hash={} tag={} err={}", hash, tag, var7.getMessage());
            return true;
         }
      } else {
         return false;
      }
   }

   private void deleteQbTag(Long downloaderId, String tag) {
      if (StringUtils.hasText(tag)) {
         try {
            boolean deleted = downloaderId != null ? this.movieQbittorrentService.deleteTags(downloaderId, tag) : this.movieQbittorrentService.deleteTags(tag);
            if (!deleted) {
               log.debug("删除 qBittorrent 全局标签失败 tag={}", tag);
            }
         } catch (Exception var4) {
            log.debug("删除 qBittorrent 全局标签异常 tag={} err={}", tag, var4.getMessage());
         }
      }
   }

   private boolean containsTag(String tags, String targetTag) {
      if (StringUtils.hasText(tags) && StringUtils.hasText(targetTag)) {
         for (String item : tags.split(",")) {
            if (targetTag.equalsIgnoreCase(this.trimToEmpty(item))) {
               return true;
            }
         }

         return false;
      } else {
         return false;
      }
   }

   private String resolveFilename(String contentDisposition, String torrentId) {
      if (StringUtils.hasText(contentDisposition)) {
         Matcher matcher = FILENAME_PATTERN.matcher(contentDisposition);
         if (matcher.find()) {
            return matcher.group(2);
         }
      }

      return torrentId + ".torrent";
   }

   private String extractTorrentId(String url) {
      if (!StringUtils.hasText(url)) {
         return "";
      } else {
         Matcher matcher = Pattern.compile("id=(\\d+)").matcher(url);
         if (matcher.find()) {
            return matcher.group(1);
         } else {
            matcher = Pattern.compile("/detail/(\\d+)").matcher(url);
            return matcher.find() ? matcher.group(1) : "";
         }
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

   private void applyTokenHeaders(HttpRequest request, String token) {
      if (StringUtils.hasText(token)) {
         try {
            JSONObject json = JSON.parseObject(token);

            for (String key : json.keySet()) {
               Object value = json.get(key);
               if (value != null) {
                  request.header(key, String.valueOf(value));
               }
            }
         } catch (Exception var7) {
            request.header("Authorization", token);
            request.header("x-token", token);
         }
      }
   }

   private String resolveSavePath(String savePath, MovieScrapePathConfig config) {
      if (StringUtils.hasText(savePath)) {
         return savePath.trim();
      } else {
         return config != null && StringUtils.hasText(config.getQbDownloadPath()) ? config.getQbDownloadPath() : null;
      }
   }

   private String extractMagnetHash(String magnet) {
      if (!StringUtils.hasText(magnet)) {
         return "";
      } else {
         String query = magnet.trim();
         int idx = query.indexOf(63);
         if (idx >= 0) {
            query = query.substring(idx + 1);
         }

         String[] pairs = query.split("&");

         for (String pair : pairs) {
            int eq = pair.indexOf(61);
            if (eq > 0) {
               String key = pair.substring(0, eq);
               if ("xt".equalsIgnoreCase(key)) {
                  String value = this.urlDecode(pair.substring(eq + 1));
                  if (StringUtils.hasText(value)) {
                     String lower = value.toLowerCase(Locale.ROOT);
                     int btihIndex = lower.indexOf("urn:btih:");
                     if (btihIndex >= 0) {
                        String hash = value.substring(btihIndex + "urn:btih:".length());
                        if (hash.length() == 40) {
                           return hash;
                        }

                        if (hash.length() == 32) {
                           String hex = this.base32ToHex(hash);
                           return StringUtils.hasText(hex) ? hex : "";
                        }
                     }
                  }
               }
            }
         }

         return "";
      }
   }

   private String extractMagnetDisplayName(String magnet) {
      if (!StringUtils.hasText(magnet)) {
         return "";
      } else {
         String query = magnet.trim();
         int idx = query.indexOf(63);
         if (idx >= 0) {
            query = query.substring(idx + 1);
         }

         String[] pairs = query.split("&");

         for (String pair : pairs) {
            int eq = pair.indexOf(61);
            if (eq > 0) {
               String key = pair.substring(0, eq);
               if ("dn".equalsIgnoreCase(key)) {
                  String value = this.urlDecode(pair.substring(eq + 1));
                  if (StringUtils.hasText(value)) {
                     return value.trim();
                  }
               }
            }
         }

         return "";
      }
   }

   private String urlDecode(String value) {
      if (!StringUtils.hasText(value)) {
         return "";
      } else {
         try {
            return URLDecoder.decode(value, StandardCharsets.UTF_8);
         } catch (Exception var3) {
            return value;
         }
      }
   }

   private String base32ToHex(String base32) {
      if (!StringUtils.hasText(base32)) {
         return "";
      } else {
         String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ234567";
         int buffer = 0;
         int bitsLeft = 0;
         byte[] out = new byte[20];
         int outIndex = 0;

         for (int i = 0; i < base32.length(); i++) {
            char c = Character.toUpperCase(base32.charAt(i));
            int val = alphabet.indexOf(c);
            if (val >= 0) {
               buffer = buffer << 5 | val;
               bitsLeft += 5;
               if (bitsLeft >= 8) {
                  bitsLeft -= 8;
                  if (outIndex >= out.length) {
                     break;
                  }

                  out[outIndex++] = (byte)(buffer >> bitsLeft & 0xFF);
               }
            }
         }

         if (outIndex == 0) {
            return "";
         } else {
            StringBuilder hex = new StringBuilder();

            for (int ix = 0; ix < outIndex; ix++) {
               hex.append(String.format("%02x", out[ix]));
            }

            return hex.toString();
         }
      }
   }

   private String resolveApiKey(String token) {
      if (!StringUtils.hasText(token)) {
         return "";
      } else {
         String trimmed = token.trim();
         if (trimmed.startsWith("{")) {
            try {
               JSONObject json = JSON.parseObject(trimmed);

               for (String key : json.keySet()) {
                  String lower = key.toLowerCase(Locale.ROOT);
                  if (lower.contains("x-api-key") || lower.contains("apikey")) {
                     String value = json.getString(key);
                     if (StringUtils.hasText(value)) {
                        return value.trim();
                     }
                  }
               }
            } catch (Exception var8) {
            }
         }

         return trimmed;
      }
   }

   private String getBaseDomain(String url) {
      if (!StringUtils.hasText(url)) {
         return "";
      } else {
         String host = url.trim();
         int schemeIdx = host.indexOf("://");
         if (schemeIdx >= 0) {
            host = host.substring(schemeIdx + 3);
         }

         int slashIdx = host.indexOf(47);
         if (slashIdx >= 0) {
            host = host.substring(0, slashIdx);
         }

         int portIdx = host.indexOf(58);
         if (portIdx >= 0) {
            host = host.substring(0, portIdx);
         }

         String[] parts = host.split("\\.");
         return parts.length <= 2 ? host : parts[parts.length - 2] + "." + parts[parts.length - 1];
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

   private String trimLeadingSlash(String value) {
      if (!StringUtils.hasText(value)) {
         return "";
      } else {
         String trimmed = value.trim();

         while (trimmed.startsWith("/")) {
            trimmed = trimmed.substring(1);
         }

         return trimmed;
      }
   }

   private String trimToEmpty(String value) {
      return value == null ? "" : value.trim();
   }

   private String maskSensitiveUrl(String url) {
      return !StringUtils.hasText(url)
         ? ""
         : url.replaceAll("(?i)(passkey=)[^&]+", "$1***")
            .replaceAll("(?i)(torrent_pass=)[^&]+", "$1***")
            .replaceAll("(?i)(authkey=)[^&]+", "$1***")
            .replaceAll("(?i)(downhash=)[^&]+", "$1***");
   }

   private boolean isMteam(MoviePtSite site) {
      if (site == null) {
         return false;
      } else if ("mteam".equalsIgnoreCase(site.getSiteType())) {
         return true;
      } else {
         String baseUrl = site.getBaseUrl();
         if (!StringUtils.hasText(baseUrl)) {
            return false;
         } else {
            String lower = baseUrl.toLowerCase(Locale.ROOT);
            return lower.contains("m-team") || lower.contains("mteam");
         }
      }
   }

   @Generated
   public MoviePtDownloadServiceImpl(
      final MoviePtSiteService moviePtSiteService,
      final MovieQbittorrentService movieQbittorrentService,
      final MovieScrapePathConfigService movieScrapePathConfigService,
      final MovieDownloadRecordService movieDownloadRecordService,
      final MoviePtSubscribeMapper moviePtSubscribeMapper,
      final MoviePtDownloadNotifyAsyncService moviePtDownloadNotifyAsyncService
   ) {
      this.moviePtSiteService = moviePtSiteService;
      this.movieQbittorrentService = movieQbittorrentService;
      this.movieScrapePathConfigService = movieScrapePathConfigService;
      this.movieDownloadRecordService = movieDownloadRecordService;
      this.moviePtSubscribeMapper = moviePtSubscribeMapper;
      this.moviePtDownloadNotifyAsyncService = moviePtDownloadNotifyAsyncService;
   }

   private static final class ResolvedDownloadUrl {
      private final String url;
      private final boolean useCookie;

      private ResolvedDownloadUrl(String url, boolean useCookie) {
         this.url = url;
         this.useCookie = useCookie;
      }
   }

   private static final class TorrentDownloadResult {
      private final byte[] content;
      private final String magnet;
      private final String filename;
      private final String error;

      private TorrentDownloadResult(byte[] content, String magnet, String filename, String error) {
         this.content = content;
         this.magnet = magnet;
         this.filename = filename;
         this.error = error;
      }

      private static MoviePtDownloadServiceImpl.TorrentDownloadResult success(byte[] content, String filename) {
         return new MoviePtDownloadServiceImpl.TorrentDownloadResult(content, "", filename, "");
      }

      private static MoviePtDownloadServiceImpl.TorrentDownloadResult magnet(String magnet) {
         return new MoviePtDownloadServiceImpl.TorrentDownloadResult(null, magnet, "", "");
      }

      private static MoviePtDownloadServiceImpl.TorrentDownloadResult fail(String error) {
         return new MoviePtDownloadServiceImpl.TorrentDownloadResult(null, "", "", error);
      }
   }
}
