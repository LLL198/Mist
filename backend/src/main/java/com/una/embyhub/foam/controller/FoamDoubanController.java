package com.una.embyhub.foam.controller;

import com.una.embyhub.foam.response.douban.FoamDoubanDiscoverResponse;
import com.una.embyhub.foam.service.FoamDoubanService;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URLDecoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpClient.Redirect;
import java.net.http.HttpResponse.BodyHandlers;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.Locale;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import lombok.Generated;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping({"foam/douban"})
public class FoamDoubanController {
   @Generated
   private static final Logger log = LoggerFactory.getLogger(FoamDoubanController.class);
   @Autowired
   private FoamDoubanService doubanService;
   @Autowired
   private RedisTemplate<String, byte[]> binaryRedisTemplate;
   @Autowired
   private StringRedisTemplate stringRedisTemplate;
   private static final String REDIS_IMAGE_PREFIX = "foam:douban:image:v2:";
   private static final String REDIS_IMAGE_CONTENT_TYPE_SUFFIX = ":ct";
   private static final HttpClient HTTP = HttpClient.newBuilder().followRedirects(Redirect.NEVER).connectTimeout(Duration.ofSeconds(10L)).build();
   private static final long CACHE_TTL_HOURS = 6L;
   private static final int MAX_REDIRECTS = 3;
   private static final long MAX_IMAGE_BYTES = 10485760L;
   private static final Set<String> ALLOWED_DOUBAN_IMAGE_HOSTS = Set.of("img1.doubanio.com", "img2.doubanio.com", "img3.doubanio.com", "img9.doubanio.com");
   private static final Set<String> ALLOWED_IMAGE_CONTENT_TYPES = Set.of("image/jpeg", "image/png", "image/gif", "image/webp", "image/avif");

   @GetMapping({"movies"})
   public FoamDoubanDiscoverResponse discoverMovies(
      @RequestParam(defaultValue = "1") int page,
      @RequestParam(defaultValue = "30") int count,
      @RequestParam(defaultValue = "U") String sort,
      @RequestParam(defaultValue = "") String tags
   ) {
      log.info("探索豆瓣电影: page={}, count={}, sort={}, tags={}", page, count, sort, tags);
      return this.doubanService.discoverMovies(page, count, sort, tags);
   }

   @GetMapping({"tvs"})
   public FoamDoubanDiscoverResponse discoverTvs(
      @RequestParam(defaultValue = "1") int page,
      @RequestParam(defaultValue = "30") int count,
      @RequestParam(defaultValue = "U") String sort,
      @RequestParam(defaultValue = "") String tags
   ) {
      log.info("探索豆瓣剧集: page={}, count={}, sort={}, tags={}", page, count, sort, tags);
      return this.doubanService.discoverTvs(page, count, sort, tags);
   }

   @GetMapping({"/image2"})
   public ResponseEntity<byte[]> proxyImage(@RequestParam("url") String url) {
      try {
         String targetUrl = URLDecoder.decode(url, StandardCharsets.UTF_8);
         URI targetUri = parseSafeDoubanImageUri(targetUrl);
         if (targetUri == null) {
            return ResponseEntity.badRequest().contentType(MediaType.TEXT_PLAIN).body("非法url".getBytes(StandardCharsets.UTF_8));
         } else {
            String redisKey = "foam:douban:image:v2:" + targetUri;
            byte[] hit = this.binaryRedisTemplate.opsForValue().get(redisKey);
            if (hit != null) {
               String cachedContentType = this.stringRedisTemplate.opsForValue().get(redisKey + ":ct");
               String safeCachedContentType = safeImageContentType(cachedContentType);
               if (StringUtils.hasText(safeCachedContentType)) {
                  return this.buildResponse(hit, safeCachedContentType);
               }

               this.binaryRedisTemplate.delete(redisKey);
               this.stringRedisTemplate.delete(redisKey + ":ct");
            }

            HttpResponse<InputStream> resp = this.sendDoubanImageRequest(targetUri);

            ResponseEntity var13;
            try (InputStream responseStream = resp.body()) {
               int code = resp.statusCode();
               if (code / 100 != 2) {
                  return ResponseEntity.status(code).contentType(MediaType.TEXT_PLAIN).body(("上游返回非成功状态码: " + code).getBytes(StandardCharsets.UTF_8));
               }

               String contentType = safeImageContentType(resp.headers().firstValue("Content-Type").orElse(""));
               if (!StringUtils.hasText(contentType)) {
                  return ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
                     .contentType(MediaType.TEXT_PLAIN)
                     .body("上游返回内容不是支持的图片类型".getBytes(StandardCharsets.UTF_8));
               }

               long contentLength = resp.headers().firstValueAsLong("Content-Length").orElse(-1L);
               if (contentLength > 10485760L) {
                  return ResponseEntity.status(HttpStatus.PAYLOAD_TOO_LARGE).contentType(MediaType.TEXT_PLAIN).body("图片过大".getBytes(StandardCharsets.UTF_8));
               }

               byte[] body = readLimitedImageBody(responseStream);
               this.binaryRedisTemplate.opsForValue().set(redisKey, body, 6L, TimeUnit.HOURS);
               this.stringRedisTemplate.opsForValue().set(redisKey + ":ct", contentType, 6L, TimeUnit.HOURS);
               var13 = this.buildResponse(body, contentType);
            }

            return var13;
         }
      } catch (IllegalArgumentException var16) {
         return ResponseEntity.badRequest().contentType(MediaType.TEXT_PLAIN).body("非法url".getBytes(StandardCharsets.UTF_8));
      } catch (FoamDoubanController.ImageTooLargeException var17) {
         return ResponseEntity.status(HttpStatus.PAYLOAD_TOO_LARGE).contentType(MediaType.TEXT_PLAIN).body("图片过大".getBytes(StandardCharsets.UTF_8));
      } catch (Exception var18) {
         return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
            .contentType(MediaType.TEXT_PLAIN)
            .body(("代理失败: " + var18.getMessage()).getBytes(StandardCharsets.UTF_8));
      }
   }

   private ResponseEntity<byte[]> buildResponse(byte[] bytes, String contentType) {
      HttpHeaders headers = new HttpHeaders();
      headers.set("Content-Type", contentType);
      headers.setCacheControl(CacheControl.maxAge(Duration.ofHours(6L)).cachePublic());
      return new ResponseEntity<>(bytes, headers, HttpStatus.OK);
   }

   static URI parseSafeDoubanImageUri(String url) {
      if (!StringUtils.hasText(url)) {
         return null;
      } else {
         try {
            URI uri = URI.create(url);
            return isSafeDoubanImageUri(uri) ? uri : null;
         } catch (Exception var2) {
            return null;
         }
      }
   }

   static boolean isSafeDoubanImageUri(URI uri) {
      if (uri == null || !"https".equalsIgnoreCase(uri.getScheme())) {
         return false;
      } else if (StringUtils.hasText(uri.getUserInfo())) {
         return false;
      } else {
         String host = uri.getHost();
         return !StringUtils.hasText(host) ? false : ALLOWED_DOUBAN_IMAGE_HOSTS.contains(host.toLowerCase(Locale.ROOT));
      }
   }

   static String safeImageContentType(String contentType) {
      if (!StringUtils.hasText(contentType)) {
         return "";
      } else {
         String type = contentType.split(";", 2)[0].trim().toLowerCase(Locale.ROOT);
         return ALLOWED_IMAGE_CONTENT_TYPES.contains(type) ? type : "";
      }
   }

   private HttpResponse<InputStream> sendDoubanImageRequest(URI initialUri) throws IOException, InterruptedException {
      URI currentUri = initialUri;

      for (int redirectCount = 0; redirectCount <= 3; redirectCount++) {
         HttpResponse<InputStream> response = HTTP.send(this.buildImageRequest(currentUri), BodyHandlers.ofInputStream());
         if (!isRedirect(response.statusCode())) {
            return response;
         }

         try (InputStream ignored = response.body()) {
            String location = response.headers().firstValue("Location").orElseThrow(() -> new IllegalArgumentException("缺少重定向地址"));
            currentUri = currentUri.resolve(location);
            if (!isSafeDoubanImageUri(currentUri)) {
               throw new IllegalArgumentException("非法重定向地址");
            }
         }
      }

      throw new IOException("重定向次数过多");
   }

   private HttpRequest buildImageRequest(URI targetUri) {
      return HttpRequest.newBuilder(targetUri)
         .timeout(Duration.ofSeconds(20L))
         .header("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0.0.0 Safari/537.36")
         .header("Accept", "image/avif,image/webp,image/apng,image/*,*/*;q=0.8")
         .header("Accept-Language", "zh-CN,zh;q=0.9,en;q=0.8")
         .header("Referer", "https://movie.douban.com/")
         .GET()
         .build();
   }

   private static boolean isRedirect(int code) {
      return code == 301 || code == 302 || code == 303 || code == 307 || code == 308;
   }

   private static byte[] readLimitedImageBody(InputStream input) throws IOException {
      ByteArrayOutputStream output = new ByteArrayOutputStream();
      byte[] buffer = new byte[8192];
      long total = 0L;

      int read;
      while ((read = input.read(buffer)) != -1) {
         total += (long)read;
         if (total > 10485760L) {
            throw new FoamDoubanController.ImageTooLargeException();
         }

         output.write(buffer, 0, read);
      }

      return output.toByteArray();
   }

   private static final class ImageTooLargeException extends IOException {
   }
}
