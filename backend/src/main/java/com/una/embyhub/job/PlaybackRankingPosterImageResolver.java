package com.una.embyhub.job;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.una.embyhub.foam.client.TmdbClient;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.TimeUnit;
import javax.imageio.ImageIO;
import lombok.Generated;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.OkHttpClient.Builder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class PlaybackRankingPosterImageResolver {
   @Generated
   private static final Logger log = LoggerFactory.getLogger(PlaybackRankingPosterImageResolver.class);
   private static final long SUCCESS_TTL_MILLIS = TimeUnit.HOURS.toMillis(24L);
   private static final long MISS_TTL_MILLIS = TimeUnit.MINUTES.toMillis(30L);
   private static final int MAX_CACHE_ENTRIES = 128;
   private static final int MAX_IMAGE_BYTES = 8388608;
   private static final int MAX_IMAGE_DIMENSION = 6000;
   private final TmdbClient tmdbClient;
   private final String tmdbImageBaseUrl;
   private final OkHttpClient imageHttpClient;
   private final Map<String, PlaybackRankingPosterImageResolver.CacheEntry> cache = new LinkedHashMap<String, PlaybackRankingPosterImageResolver.CacheEntry>(
      32, 0.75F, true
   ) {
      @Override
      protected boolean removeEldestEntry(Entry<String, PlaybackRankingPosterImageResolver.CacheEntry> eldest) {
         return this.size() > 128;
      }
   };

   @Autowired
   public PlaybackRankingPosterImageResolver(TmdbClient tmdbClient, @Value("${tmdb.imageUrl:https://image.tmdb.org/t/p/original}") String tmdbImageBaseUrl) {
      this(
         tmdbClient,
         tmdbImageBaseUrl,
         new Builder().connectTimeout(5L, TimeUnit.SECONDS).readTimeout(10L, TimeUnit.SECONDS).callTimeout(12L, TimeUnit.SECONDS).build()
      );
   }

   PlaybackRankingPosterImageResolver(TmdbClient tmdbClient, String tmdbImageBaseUrl, OkHttpClient imageHttpClient) {
      this.tmdbClient = tmdbClient;
      this.tmdbImageBaseUrl = normalizeBaseUrl(tmdbImageBaseUrl);
      this.imageHttpClient = imageHttpClient;
   }

   public BufferedImage resolvePoster(String mediaType, String title, Integer year, Map<String, String> providerIds) {
      return this.resolveArtwork(PlaybackRankingPosterImageResolver.ArtworkKind.POSTER, mediaType, title, year, providerIds);
   }

   public BufferedImage resolveBackdrop(String mediaType, String title, Integer year, Map<String, String> providerIds) {
      return this.resolveArtwork(PlaybackRankingPosterImageResolver.ArtworkKind.BACKDROP, mediaType, title, year, providerIds);
   }

   private BufferedImage resolveArtwork(
      PlaybackRankingPosterImageResolver.ArtworkKind kind, String mediaType, String title, Integer year, Map<String, String> providerIds
   ) {
      String normalizedType = "tv".equalsIgnoreCase(mediaType) ? "tv" : "movie";
      String tmdbId = providerId(providerIds, "tmdb");
      String cacheKey = buildCacheKey(kind, normalizedType, tmdbId, title, year);
      PlaybackRankingPosterImageResolver.CacheEntry cached = this.getCached(cacheKey);
      if (cached != null) {
         return cached.image();
      } else {
         BufferedImage artwork = null;

         try {
            String artworkPath = tmdbId == null
               ? this.searchArtworkPath(kind, normalizedType, title, year)
               : this.detailArtworkPath(kind, normalizedType, tmdbId);
            artwork = this.downloadArtwork(kind, artworkPath);
            if (artwork == null) {
               log.debug("排行榜 TMDB 图片未找到：kind={}, type={}, tmdbId={}, title={}", kind.cacheKey, normalizedType, tmdbId, title);
            }
         } catch (Exception var12) {
            log.warn("排行榜 TMDB 图片补全失败：kind={}, type={}, tmdbId={}, title={}", kind.cacheKey, normalizedType, tmdbId, title, var12);
         }

         this.putCached(cacheKey, artwork, artwork == null ? MISS_TTL_MILLIS : SUCCESS_TTL_MILLIS);
         return artwork;
      }
   }

   private String detailArtworkPath(PlaybackRankingPosterImageResolver.ArtworkKind kind, String mediaType, String tmdbId) {
      if (!tmdbId.matches("\\d+")) {
         return null;
      } else {
         JSONObject result = this.tmdbClient.request("/" + mediaType + "/" + tmdbId, Map.of());
         return safeArtworkPath(result == null ? null : result.getString(kind.pathField));
      }
   }

   private String searchArtworkPath(PlaybackRankingPosterImageResolver.ArtworkKind kind, String mediaType, String title, Integer year) {
      if (title != null && !title.isBlank()) {
         Map<String, Object> params = new LinkedHashMap<>();
         params.put("query", title.trim());
         params.put("include_adult", false);
         params.put("page", 1);
         if (year != null && year > 1800) {
            params.put("movie".equals(mediaType) ? "year" : "first_air_date_year", year);
         }

         JSONObject response = this.tmdbClient.request("/search/" + mediaType, params);
         JSONArray results = response == null ? null : response.getJSONArray("results");
         if (results == null) {
            return null;
         } else {
            for (int index = 0; index < results.size(); index++) {
               JSONObject item = results.getJSONObject(index);
               String artworkPath = safeArtworkPath(item == null ? null : item.getString(kind.pathField));
               if (artworkPath != null) {
                  return artworkPath;
               }
            }

            return null;
         }
      } else {
         return null;
      }
   }

   private BufferedImage downloadArtwork(PlaybackRankingPosterImageResolver.ArtworkKind kind, String artworkPath) {
      if (artworkPath == null) {
         return null;
      } else {
         Request request = new okhttp3.Request.Builder().url(this.imageBaseUrl(kind) + artworkPath).get().build();

         try {
            Object contentLength;
            try (Response response = this.imageHttpClient.newCall(request).execute()) {
               if (response.isSuccessful() && response.body() != null) {
                  long contentLengthx = response.body().contentLength();
                  if (contentLengthx > 8388608L) {
                     log.warn("排行榜 TMDB 图片超过大小限制：bytes={}, path={}", contentLengthx, artworkPath);
                     return null;
                  }

                  byte[] bytes = response.body().byteStream().readNBytes(8388609);
                  if (bytes.length > 8388608) {
                     log.warn("排行榜 TMDB 图片超过读取限制：path={}", artworkPath);
                     return null;
                  }

                  BufferedImage image = ImageIO.read(new ByteArrayInputStream(bytes));
                  if (image != null && image.getWidth() > 0 && image.getHeight() > 0 && image.getWidth() <= 6000 && image.getHeight() <= 6000) {
                     if (!kind.accepts(image)) {
                        log.warn("排行榜 TMDB 图片比例不符合用途：kind={}, size={}x{}, path={}", kind.cacheKey, image.getWidth(), image.getHeight(), artworkPath);
                        return null;
                     }

                     return image;
                  }

                  log.warn("排行榜 TMDB 图片尺寸无效：path={}", artworkPath);
                  return null;
               }

               log.debug("排行榜 TMDB 图片下载失败：kind={}, status={}, path={}", kind.cacheKey, response.code(), artworkPath);
               contentLength = null;
            }

            return (BufferedImage)contentLength;
         } catch (Exception var12) {
            log.debug("排行榜 TMDB 图片读取失败：kind={}, path={}", kind.cacheKey, artworkPath, var12);
            return null;
         }
      }
   }

   private String imageBaseUrl(PlaybackRankingPosterImageResolver.ArtworkKind kind) {
      return this.tmdbImageBaseUrl.replaceFirst("/(w\\d+|h\\d+|original)$", "/original");
   }

   private PlaybackRankingPosterImageResolver.CacheEntry getCached(String key) {
      synchronized (this.cache) {
         PlaybackRankingPosterImageResolver.CacheEntry entry = this.cache.get(key);
         if (entry == null) {
            return null;
         } else if (entry.expiresAt() <= System.currentTimeMillis()) {
            this.cache.remove(key);
            return null;
         } else {
            return entry;
         }
      }
   }

   private void putCached(String key, BufferedImage image, long ttlMillis) {
      synchronized (this.cache) {
         this.cache.put(key, new PlaybackRankingPosterImageResolver.CacheEntry(image, System.currentTimeMillis() + ttlMillis));
      }
   }

   private static String providerId(Map<String, String> providerIds, String name) {
      if (providerIds == null) {
         return null;
      } else {
         for (Entry<String, String> entry : providerIds.entrySet()) {
            if (entry.getKey() != null && entry.getKey().equalsIgnoreCase(name) && entry.getValue() != null && !entry.getValue().isBlank()) {
               return entry.getValue().trim();
            }
         }

         return null;
      }
   }

   private static String safeArtworkPath(String value) {
      return value != null
            && !value.isBlank()
            && value.startsWith("/")
            && !value.contains("..")
            && !value.contains(":")
            && !value.contains("?")
            && !value.contains("#")
         ? value
         : null;
   }

   private static String buildCacheKey(PlaybackRankingPosterImageResolver.ArtworkKind kind, String mediaType, String tmdbId, String title, Integer year) {
      if (tmdbId != null && !tmdbId.isBlank()) {
         return kind.cacheKey + ":" + mediaType + ":id:" + tmdbId.trim();
      } else {
         String normalizedTitle = title == null ? "" : title.trim().toLowerCase(Locale.ROOT);
         return kind.cacheKey + ":" + mediaType + ":search:" + normalizedTitle + ":" + (year == null ? "" : year);
      }
   }

   private static String normalizeBaseUrl(String value) {
      String fallback = "https://image.tmdb.org/t/p/original";
      String result = value != null && !value.isBlank() ? value.trim() : fallback;

      while (result.endsWith("/")) {
         result = result.substring(0, result.length() - 1);
      }

      return result;
   }

   private static enum ArtworkKind {
      POSTER("poster", "poster_path") {
         @Override
         boolean accepts(BufferedImage image) {
            double ratio = (double)image.getWidth() / (double)image.getHeight();
            return ratio >= 0.45 && ratio <= 0.85;
         }
      },
      BACKDROP("backdrop", "backdrop_path") {
         @Override
         boolean accepts(BufferedImage image) {
            return (double)image.getWidth() / (double)image.getHeight() >= 1.3;
         }
      };

      private final String cacheKey;
      private final String pathField;

      private ArtworkKind(String cacheKey, String pathField) {
         this.cacheKey = cacheKey;
         this.pathField = pathField;
      }

      abstract boolean accepts(BufferedImage image);
   }

   private static record CacheEntry(BufferedImage image, long expiresAt) {
   }
}
