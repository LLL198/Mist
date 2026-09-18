package com.una.embyhub.movie.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.una.embyhub.movie.model.MoviePtSearchProgressEvent;
import com.una.embyhub.movie.model.MoviePtSearchResult;
import com.una.embyhub.movie.model.MoviePtSite;
import com.una.embyhub.movie.service.MoviePtSearchService;
import com.una.embyhub.movie.service.MoviePtSiteService;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import lombok.Generated;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

@Service
public class MoviePtSearchServiceImpl implements MoviePtSearchService {
   @Generated
   private static final Logger log = LoggerFactory.getLogger(MoviePtSearchServiceImpl.class);
   private static final int DEFAULT_LIMIT = 20;
   private static final int MAX_SEARCH_KEYWORDS = 4;
   private static final int MAX_KEYWORD_RETRY_PER_SITE = 3;
   private static final int NEXUS_REQUEST_TIMEOUT_MS = 6000;
   private static final int NEXUS_MAX_REQUEST_ATTEMPTS = 6;
   private static final int NEXUS_NON_MATCH_RETRY_ATTEMPTS = 2;
   private static final Pattern TORRENT_ID_PATTERN = Pattern.compile("id=(\\d+)");
   private static final Pattern SIZE_PATTERN = Pattern.compile("(\\d+(?:\\.\\d+)?\\s*(?:GB|MB|KB|TB|GiB|MiB|KiB|TiB))", 2);
   private static final Pattern RESOLUTION_PATTERN = Pattern.compile("((?:2160|1080|[48]k|720)[pi]?)", 2);
   private static final Pattern PROMO_DEADLINE_PATTERN = Pattern.compile("(\\d{4}-\\d{2}-\\d{2}\\s+\\d{2}:\\d{2}:\\d{2})");
   private static final Pattern DETAIL_ROUTE_PATTERN = Pattern.compile("(?i)/detail/(\\d+)");
   private static final Pattern DOWNLOAD_ONCLICK_PATTERN = Pattern.compile("(?i)(download\\.php[^'\"\\s)]+)");
   private static final Pattern YEAR_IN_TEXT_PATTERN = Pattern.compile("(?<!\\d)(19\\d{2}|20\\d{2})(?!\\d)");
   private static final Pattern TV_EPISODE_PATTERN = Pattern.compile("(?i)(?<![a-z0-9])s\\d{1,2}(?:e\\d{1,3}(?:[-~](?:e)?\\d{1,3})?)?(?![a-z0-9])");
   private static final Pattern IMG_SRC_PATTERN = Pattern.compile("src=['\"]([^'\"]+)['\"]");
   private static final Pattern STYLE_BG_PATTERN = Pattern.compile("background(?:-image)?:\\s*url\\(['\"]?([^')\"]+)['\"]?\\)");
   private static final Pattern QUALITY_PATTERN = Pattern.compile("(blu-?ray|bdremux|remux|web-?dl|webrip|hdtv|hdrip|dvd(?:rip)?|bdrip)", 2);
   private static final Pattern AUDIO_EFFECT_PATTERN = Pattern.compile(
      "(dolby[\\s._-]?vision|dovi|\\bDV\\b|hdr10\\+|hdr10plus|\\bHDR\\b|atmos|dts-?hd(?:\\s?ma)?|truehd|\\bFLAC\\b|\\bAAC\\b|DD[+P]?\\s?5\\.?1|\\bLPCM\\b|\\bAC3\\b|\\bEAC3\\b|\\bDDP\\b)",
      2
   );
   private final MoviePtSiteService moviePtSiteService;

   @Override
   public List<MoviePtSearchResult> search(String keyword, Long siteId, Integer limit, String title, String originalTitle, String year, String type) {
      return this.searchInternal(keyword, siteId, limit, title, originalTitle, year, type, null);
   }

   @Override
   public List<MoviePtSearchResult> searchWithProgress(
      String keyword,
      Long siteId,
      Integer limit,
      String title,
      String originalTitle,
      String year,
      String type,
      Consumer<MoviePtSearchProgressEvent> progressConsumer
   ) {
      return this.searchInternal(keyword, siteId, limit, title, originalTitle, year, type, progressConsumer);
   }

   private List<MoviePtSearchResult> searchInternal(
      String keyword,
      Long siteId,
      Integer limit,
      String title,
      String originalTitle,
      String year,
      String type,
      Consumer<MoviePtSearchProgressEvent> progressConsumer
   ) {
      if (!StringUtils.hasText(keyword)) {
         return Collections.emptyList();
      } else {
         int finalLimit = limit != null && limit > 0 ? limit : 20;
         List<String> preparedKeywords = this.buildSearchKeywords(keyword, year);
         if (CollectionUtils.isEmpty(preparedKeywords)) {
            preparedKeywords = List.of(keyword);
         }

         List<String> searchKeywords = preparedKeywords;
         List<MoviePtSite> sites = this.resolveSites(siteId);
         if (CollectionUtils.isEmpty(sites)) {
            return Collections.emptyList();
         } else {
            long startAt = System.currentTimeMillis();
            this.emitProgress(
               progressConsumer, MoviePtSearchProgressEvent.builder().type("START").keyword(keyword).totalSites(sites.size()).completedSites(0).build()
            );
            AtomicInteger completedSites = new AtomicInteger(0);

            List var22;
            try (ExecutorService executor = Executors.newVirtualThreadPerTaskExecutor()) {
               List<CompletableFuture<List<MoviePtSearchResult>>> futures = sites.stream()
                  .map(
                     site -> CompletableFuture.<List<MoviePtSearchResult>>supplyAsync(
                              () -> {
                                 this.emitProgress(
                                    progressConsumer,
                                    MoviePtSearchProgressEvent.builder()
                                       .type("SITE_START")
                                       .keyword(keyword)
                                       .totalSites(sites.size())
                                       .completedSites(completedSites.get())
                                       .siteId(site.getId())
                                       .siteName(site.getName())
                                       .build()
                                 );
                                 long siteStartAt = System.currentTimeMillis();
                                 List<MoviePtSearchResult> siteResults = this.searchBySite(site, searchKeywords, finalLimit);
                                 int currentCompleted = completedSites.incrementAndGet();
                                 this.emitProgress(
                                    progressConsumer,
                                    MoviePtSearchProgressEvent.builder()
                                       .type("SITE_DONE")
                                       .keyword(keyword)
                                       .totalSites(sites.size())
                                       .completedSites(currentCompleted)
                                       .siteId(site.getId())
                                       .siteName(site.getName())
                                       .siteResultCount(siteResults.size())
                                       .elapsedMs(System.currentTimeMillis() - siteStartAt)
                                       .build()
                                 );
                                 return siteResults;
                              },
                              executor
                           )
                           .exceptionally(
                              ex -> {
                                 String errorMessage = this.unwrapErrorMessage(ex);
                                 log.warn("站点 {} 搜索失败: {}", site.getName(), errorMessage);
                                 int currentCompleted = completedSites.incrementAndGet();
                                 this.emitProgress(
                                    progressConsumer,
                                    MoviePtSearchProgressEvent.builder()
                                       .type("SITE_ERROR")
                                       .keyword(keyword)
                                       .totalSites(sites.size())
                                       .completedSites(currentCompleted)
                                       .siteId(site.getId())
                                       .siteName(site.getName())
                                       .message(errorMessage)
                                       .build()
                                 );
                                 return Collections.emptyList();
                              }
                           )
                  )
                  .toList();
               List<MoviePtSearchResult> results = futures.stream().map(CompletableFuture::join).flatMap(Collection::stream).toList();
               List<MoviePtSearchResult> filteredResults = this.applyResultFilters(results, title, originalTitle, year, type, searchKeywords);
               int filteredOutCount = Math.max(0, results.size() - filteredResults.size());
               boolean hasFilter = StringUtils.hasText(title) || StringUtils.hasText(originalTitle) || StringUtils.hasText(year) || StringUtils.hasText(type);
               log.info("原始数据：{}", JSONObject.toJSONString(results));
               log.info(
                  "PT 搜索汇总: keyword={}, siteId={}, 原始={}条, 过滤后={}条, 过滤掉={}条, hasFilter={}, title={}, originalTitle={}, year={}, type={}",
                  keyword,
                  siteId,
                  results.size(),
                  filteredResults.size(),
                  filteredOutCount,
                  hasFilter,
                  this.safeLogValue(title),
                  this.safeLogValue(originalTitle),
                  this.safeLogValue(year),
                  this.safeLogValue(type)
               );
               this.emitProgress(
                  progressConsumer,
                  MoviePtSearchProgressEvent.builder()
                     .type("COMPLETE")
                     .keyword(keyword)
                     .totalSites(sites.size())
                     .completedSites(completedSites.get())
                     .totalResults(filteredResults.size())
                     .elapsedMs(System.currentTimeMillis() - startAt)
                     .results(filteredResults)
                     .build()
               );
               var22 = filteredResults;
            }

            return var22;
         }
      }
   }

   private List<MoviePtSearchResult> applyResultFilters(
      List<MoviePtSearchResult> results, String title, String originalTitle, String year, String type, List<String> searchKeywords
   ) {
      if (CollectionUtils.isEmpty(results)) {
         return List.of();
      } else {
         Set<String> mediaTitles = this.buildMediaTitleSet(title, originalTitle);
         Set<String> mediaNames = this.buildMediaNameSet(searchKeywords, mediaTitles);
         String normalizedYear = this.normalizeYear(year);
         String normalizedType = this.normalizeType(type);
         return results.stream()
            .filter(result -> this.matchesTitle(result, mediaTitles, mediaNames))
            .filter(result -> this.matchesYear(result, normalizedYear, normalizedType))
            .filter(result -> this.matchesType(result, normalizedType))
            .toList();
      }
   }

   private boolean matchesTitle(MoviePtSearchResult result, Set<String> mediaTitles, Set<String> mediaNames) {
      if (CollectionUtils.isEmpty(mediaTitles) && CollectionUtils.isEmpty(mediaNames)) {
         return true;
      } else if (result == null) {
         return false;
      } else {
         String title = this.trimToEmpty(result.getTitle());
         String subtitle = this.trimToEmpty(result.getSubtitle());
         String combinedTitle = this.combineTexts(title, subtitle);
         if (!StringUtils.hasText(combinedTitle)) {
            return false;
         } else {
            String normalizedCombinedTitle = this.normalizeFilterText(combinedTitle);
            if (!this.matchesNormalizedTargets(normalizedCombinedTitle, mediaTitles) && !this.matchesNormalizedTargets(normalizedCombinedTitle, mediaNames)) {
               Set<String> metaNames = this.extractMetaNames(combinedTitle);
               if (this.hasIntersection(metaNames, mediaTitles)) {
                  return true;
               } else if (this.hasIntersection(metaNames, mediaNames)) {
                  return true;
               } else {
                  Set<String> titleTokens = this.extractNonEnglishTokens(title, "[\\s/【】.\\[\\]\\-]+");
                  if (this.hasIntersection(titleTokens, mediaTitles)) {
                     return true;
                  } else {
                     Set<String> subtitleTokens = this.extractNonEnglishTokens(subtitle, "[\\s/【】|]+");
                     return this.hasIntersection(subtitleTokens, mediaTitles) || this.hasIntersection(subtitleTokens, mediaNames);
                  }
               }
            } else {
               return true;
            }
         }
      }
   }

   private boolean matchesNormalizedTargets(String normalizedTitle, Set<String> targets) {
      if (StringUtils.hasText(normalizedTitle) && !CollectionUtils.isEmpty(targets)) {
         for (String target : targets) {
            if (StringUtils.hasText(target)) {
               String normalizedTarget = this.normalizeFilterText(target);
               if (StringUtils.hasText(normalizedTarget)
                  && (normalizedTarget.length() > 1 || !this.isAsciiToken(normalizedTarget))
                  && normalizedTitle.contains(normalizedTarget)) {
                  return true;
               }
            }
         }

         return false;
      } else {
         return false;
      }
   }

   private boolean matchesYear(MoviePtSearchResult result, String normalizedYear, String normalizedType) {
      if (!StringUtils.hasText(normalizedYear)) {
         return true;
      } else {
         String text = this.combineTexts(result == null ? null : result.getTitle(), result == null ? null : result.getSubtitle());
         Set<String> yearsInText = this.extractYearsFromText(text);
         if ("tv".equals(normalizedType)) {
            return CollectionUtils.isEmpty(yearsInText) || yearsInText.contains(normalizedYear);
         } else if ("movie".equals(normalizedType)) {
            if (CollectionUtils.isEmpty(yearsInText)) {
               return false;
            } else {
               int target = this.parseYearInt(normalizedYear);
               if (target <= 0) {
                  return yearsInText.contains(normalizedYear);
               } else {
                  Set<Integer> accepted = Set.of(target - 1, target, target + 1);

                  for (String year : yearsInText) {
                     int value = this.parseYearInt(year);
                     if (accepted.contains(value)) {
                        return true;
                     }
                  }

                  return false;
               }
            }
         } else {
            return CollectionUtils.isEmpty(yearsInText) || yearsInText.contains(normalizedYear);
         }
      }
   }

   private boolean matchesType(MoviePtSearchResult result, String normalizedType) {
      if (!StringUtils.hasText(normalizedType)) {
         return true;
      } else {
         boolean tvResult = this.isTvResult(result);
         if ("tv".equals(normalizedType)) {
            return tvResult;
         } else {
            return "movie".equals(normalizedType) ? !tvResult : true;
         }
      }
   }

   private boolean isTvResult(MoviePtSearchResult result) {
      return result != null && StringUtils.hasText(result.getTitle()) ? TV_EPISODE_PATTERN.matcher(result.getTitle()).find() : false;
   }

   private String normalizeType(String type) {
      if (!StringUtils.hasText(type)) {
         return "";
      } else {
         String trimmed = type.trim();
         String lower = trimmed.toLowerCase(Locale.ROOT);
         if ("tv".equals(lower) || "series".equals(lower) || "show".equals(lower) || "剧集".equals(trimmed) || "电视剧".equals(trimmed)) {
            return "tv";
         } else {
            return !"movie".equals(lower) && !"film".equals(lower) && !"电影".equals(trimmed) ? "" : "movie";
         }
      }
   }

   private String normalizeYear(String year) {
      if (!StringUtils.hasText(year)) {
         return "";
      } else {
         Matcher matcher = Pattern.compile("\\d{4}").matcher(year);
         return matcher.find() ? matcher.group() : "";
      }
   }

   private String normalizeFilterText(String text) {
      if (!StringUtils.hasText(text)) {
         return "";
      } else {
         String lower = text.toLowerCase(Locale.ROOT);
         StringBuilder sb = new StringBuilder(lower.length());

         for (int i = 0; i < lower.length(); i++) {
            char ch = lower.charAt(i);
            if (Character.isLetterOrDigit(ch)) {
               sb.append(ch);
            }
         }

         return sb.toString();
      }
   }

   private String safeLogValue(String value) {
      if (!StringUtils.hasText(value)) {
         return "-";
      } else {
         String trimmed = value.trim();
         return trimmed.length() <= 80 ? trimmed : trimmed.substring(0, 80) + "...";
      }
   }

   private Set<String> buildMediaTitleSet(String title, String originalTitle) {
      LinkedHashSet<String> mediaTitles = new LinkedHashSet<>();
      this.addNormalizedToken(mediaTitles, title);
      this.addNormalizedToken(mediaTitles, originalTitle);
      return mediaTitles;
   }

   private Set<String> buildMediaNameSet(List<String> searchKeywords, Set<String> mediaTitles) {
      LinkedHashSet<String> mediaNames = new LinkedHashSet<>();
      if (!CollectionUtils.isEmpty(searchKeywords)) {
         for (String keyword : searchKeywords) {
            this.addNormalizedToken(mediaNames, keyword);
            this.addNormalizedToken(mediaNames, this.stripSeasonKeyword(keyword));
            String keywordYear = this.normalizeYear(keyword);
            if (StringUtils.hasText(keywordYear)) {
               this.addNormalizedToken(mediaNames, this.stripYearKeyword(keyword, keywordYear));
            }
         }
      }

      if (!CollectionUtils.isEmpty(mediaTitles)) {
         mediaNames.removeAll(mediaTitles);
      }

      return mediaNames;
   }

   private void addNormalizedToken(Set<String> output, String value) {
      String normalized = this.normalizeFilterText(value);
      if (StringUtils.hasText(normalized)) {
         output.add(normalized);
      }
   }

   private Set<String> extractMetaNames(String text) {
      if (!StringUtils.hasText(text)) {
         return Set.of();
      } else {
         String[] parts = text.split("[\\s/【】\\[\\]|()（）._\\-]+");
         LinkedHashSet<String> names = new LinkedHashSet<>();

         for (String part : parts) {
            String token = this.trimToEmpty(part);
            if (StringUtils.hasText(token) && !this.isNoiseToken(token)) {
               String normalized = this.normalizeFilterText(token);
               if (StringUtils.hasText(normalized)) {
                  names.add(normalized);
               }
            }
         }

         return names;
      }
   }

   private Set<String> extractNonEnglishTokens(String text, String splitRegex) {
      if (!StringUtils.hasText(text)) {
         return Set.of();
      } else {
         String[] parts = text.split(splitRegex);
         LinkedHashSet<String> tokens = new LinkedHashSet<>();

         for (String part : parts) {
            String token = this.trimToEmpty(part);
            if (StringUtils.hasText(token) && !this.isEnglishWord(token) && !this.isNoiseToken(token)) {
               String normalized = this.normalizeFilterText(token);
               if (StringUtils.hasText(normalized)) {
                  tokens.add(normalized);
               }
            }
         }

         return tokens;
      }
   }

   private boolean isEnglishWord(String value) {
      if (!StringUtils.hasText(value)) {
         return false;
      } else {
         for (int i = 0; i < value.length(); i++) {
            char ch = value.charAt(i);
            if ((ch < 'a' || ch > 'z') && (ch < 'A' || ch > 'Z')) {
               return false;
            }
         }

         return true;
      }
   }

   private boolean isNoiseToken(String token) {
      if (!StringUtils.hasText(token)) {
         return true;
      } else {
         String lower = token.toLowerCase(Locale.ROOT);
         if (lower.matches("s\\d{1,3}(e\\d{1,4})?")) {
            return true;
         } else {
            return lower.matches("(19|20)\\d{2}")
               ? true
               : lower.matches(
                  "(2160p|1080p|720p|4k|8k|x26[45]|h26[45]|hevc|av1|aac|ddp\\d*|atmos|dts\\w*|web-?dl|webrip|bluray|remux|hdtv|bdrip|hdr|dv|dolby\\w*)"
               );
         }
      }
   }

   private Set<String> extractYearsFromText(String text) {
      if (!StringUtils.hasText(text)) {
         return Set.of();
      } else {
         LinkedHashSet<String> years = new LinkedHashSet<>();
         Matcher matcher = YEAR_IN_TEXT_PATTERN.matcher(text);

         while (matcher.find()) {
            String year = matcher.group(1);
            if (StringUtils.hasText(year)) {
               years.add(year);
            }
         }

         return years;
      }
   }

   private int parseYearInt(String year) {
      if (!StringUtils.hasText(year)) {
         return -1;
      } else {
         try {
            return Integer.parseInt(year);
         } catch (Exception var3) {
            return -1;
         }
      }
   }

   private boolean hasIntersection(Set<String> left, Set<String> right) {
      if (!CollectionUtils.isEmpty(left) && !CollectionUtils.isEmpty(right)) {
         for (String item : left) {
            if (right.contains(item)) {
               return true;
            }
         }

         return false;
      } else {
         return false;
      }
   }

   private List<String> buildSearchKeywords(String keyword, String year) {
      LinkedHashSet<String> candidates = new LinkedHashSet<>();
      this.addSearchKeyword(candidates, keyword);
      String normalizedYear = this.normalizeYear(year);
      if (StringUtils.hasText(normalizedYear)) {
         for (String item : new ArrayList<>(candidates)) {
            this.addSearchKeyword(candidates, this.stripYearKeyword(item, normalizedYear));
         }
      }

      return candidates.stream().limit(4L).toList();
   }

   private void addSearchKeyword(Set<String> output, String keyword) {
      String trimmed = this.trimToEmpty(keyword);
      if (StringUtils.hasText(trimmed)) {
         output.add(trimmed);
         String normalized = this.normalizeSearchKeyword(trimmed);
         if (StringUtils.hasText(normalized) && !normalized.equals(trimmed)) {
            output.add(normalized);
         }

         String stripped = this.stripSeasonKeyword(trimmed);
         if (StringUtils.hasText(stripped) && !stripped.equals(trimmed)) {
            output.add(stripped);
         }
      }
   }

   private String normalizeSearchKeyword(String keyword) {
      return !StringUtils.hasText(keyword) ? "" : keyword.replaceAll("[\\u3000\\p{Punct}，。！？：；、（）【】《》“”‘’]+", " ").replaceAll("\\s+", " ").trim();
   }

   private String stripSeasonKeyword(String keyword) {
      return !StringUtils.hasText(keyword) ? "" : keyword.replaceAll("(?i)\\s*S\\d{1,3}(?:E\\d{1,4})?\\s*$", "").trim();
   }

   private String stripYearKeyword(String keyword, String year) {
      return StringUtils.hasText(keyword) && StringUtils.hasText(year)
         ? keyword.replaceAll("(?<!\\d)" + Pattern.quote(year) + "(?!\\d)", " ").replaceAll("\\s+", " ").trim()
         : "";
   }

   private List<MoviePtSearchResult> searchBySite(MoviePtSite site, List<String> keywords, int limit) {
      String keywordPreview = CollectionUtils.isEmpty(keywords) ? "-" : String.join(" | ", keywords);
      log.info("开始搜索 PT 站点: {} (keywords={})", site.getName(), this.safeLogValue(keywordPreview));
      if (CollectionUtils.isEmpty(keywords)) {
         return List.of();
      } else {
         List<MoviePtSearchResult> bestMatched = List.of();
         int bestMatchCount = 0;
         int keywordAttempt = 0;

         for (String keyword : keywords) {
            if (StringUtils.hasText(keyword)) {
               keywordAttempt++;
               List<MoviePtSearchResult> siteResults = this.isMteam(site) ? this.searchMteam(site, keyword, limit) : this.searchNexusphp(site, keyword, limit);
               List<MoviePtSearchResult> matchedResults = this.filterResultsByKeyword(siteResults, keyword);
               int matchCount = matchedResults.size();
               if (matchCount > bestMatchCount) {
                  bestMatchCount = matchCount;
                  bestMatched = matchedResults;
               }

               if (matchCount > 0) {
                  return matchedResults;
               }

               if (keywordAttempt >= 3 && !CollectionUtils.isEmpty(bestMatched)) {
                  return bestMatched;
               }
            }
         }

         return bestMatchCount > 0 ? bestMatched : List.of();
      }
   }

   private int countKeywordMatches(List<MoviePtSearchResult> results, String keyword) {
      return this.filterResultsByKeyword(results, keyword).size();
   }

   private List<MoviePtSearchResult> filterResultsByKeyword(List<MoviePtSearchResult> results, String keyword) {
      if (!CollectionUtils.isEmpty(results) && StringUtils.hasText(keyword)) {
         String normalizedKeyword = this.normalizeFilterText(keyword);
         List<String> keywordTokens = this.splitKeywordTokens(keyword);
         if (!StringUtils.hasText(normalizedKeyword) && CollectionUtils.isEmpty(keywordTokens)) {
            return List.of();
         } else {
            List<MoviePtSearchResult> matched = new ArrayList<>();

            for (MoviePtSearchResult result : results) {
               if (result != null) {
                  String normalizedTitle = this.normalizeFilterText(this.combineTexts(result.getTitle(), result.getSubtitle()));
                  if (StringUtils.hasText(normalizedTitle) && this.titleMatchesKeyword(normalizedTitle, normalizedKeyword, keywordTokens)) {
                     matched.add(result);
                  }
               }
            }

            return matched;
         }
      } else {
         return List.of();
      }
   }

   private List<String> splitKeywordTokens(String keyword) {
      if (!StringUtils.hasText(keyword)) {
         return List.of();
      } else {
         String normalized = this.normalizeSearchKeyword(keyword).toLowerCase(Locale.ROOT);
         if (!StringUtils.hasText(normalized)) {
            return List.of();
         } else {
            LinkedHashSet<String> tokens = new LinkedHashSet<>();
            String[] parts = normalized.split("\\s+");

            for (String part : parts) {
               String token = this.normalizeFilterText(part);
               if (StringUtils.hasText(token) && (token.length() > 1 || !this.isAsciiToken(token))) {
                  tokens.add(token);
               }
            }

            return new ArrayList<>(tokens);
         }
      }
   }

   private boolean titleMatchesKeyword(String normalizedTitle, String normalizedKeyword, List<String> tokens) {
      if (!StringUtils.hasText(normalizedTitle)) {
         return false;
      } else if (StringUtils.hasText(normalizedKeyword) && normalizedTitle.contains(normalizedKeyword)) {
         return true;
      } else if (CollectionUtils.isEmpty(tokens)) {
         return false;
      } else {
         int matched = 0;
         int required = tokens.size() > 1 ? 2 : 1;

         for (String token : tokens) {
            if (normalizedTitle.contains(token)) {
               if (++matched >= required) {
                  return true;
               }
            }
         }

         return false;
      }
   }

   private boolean isAsciiToken(String token) {
      if (!StringUtils.hasText(token)) {
         return false;
      } else {
         for (int i = 0; i < token.length(); i++) {
            if (token.charAt(i) > 127) {
               return false;
            }
         }

         return true;
      }
   }

   private String unwrapErrorMessage(Throwable throwable) {
      Throwable current = throwable;

      while (current != null && current.getCause() != null) {
         current = current.getCause();
      }

      return current != null && StringUtils.hasText(current.getMessage()) ? current.getMessage() : "unknown error";
   }

   private void emitProgress(Consumer<MoviePtSearchProgressEvent> progressConsumer, MoviePtSearchProgressEvent event) {
      if (progressConsumer != null && event != null) {
         try {
            progressConsumer.accept(event);
         } catch (Exception var4) {
            log.debug("发送 PT 搜索进度事件失败: {}", var4.getMessage());
         }
      }
   }

   private List<MoviePtSite> resolveSites(Long siteId) {
      if (siteId == null) {
         return this.moviePtSiteService.list().stream().filter(this::isEnabledSite).toList();
      } else {
         try {
            MoviePtSite site = this.moviePtSiteService.getById(siteId);
            if (site != null && this.isEnabledSite(site)) {
               return List.of(site);
            }
         } catch (Exception var3) {
            return Collections.emptyList();
         }

         return Collections.emptyList();
      }
   }

   private boolean isEnabledSite(MoviePtSite site) {
      return site != null && Integer.valueOf(1).equals(site.getEnabled());
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

   private List<MoviePtSearchResult> searchNexusphp(MoviePtSite site, String keyword, int limit) {
      String baseUrl = this.trimSuffix(site.getBaseUrl());
      if (!StringUtils.hasText(baseUrl)) {
         return Collections.emptyList();
      } else {
         Map<String, String> headers = this.buildCommonHeaders(baseUrl);
         this.applyTokenHeaders(headers, site.getToken());
         String cookieHeader = this.buildCookieHeader(site.getCookies());
         List<String> searchPaths = this.buildNexusSearchPaths(site, baseUrl);
         List<Map<String, Object>> searchParams = this.buildNexusSearchParams(keyword);
         Set<String> visited = new HashSet<>();
         List<MoviePtSearchResult> bestMatched = List.of();
         List<MoviePtSearchResult> fallbackResults = List.of();
         int bestMatchCount = 0;
         int attemptCount = 0;

         for (String searchPath : searchPaths) {
            for (Map<String, Object> params : searchParams) {
               String queryUrl = this.buildQueryUrl(searchPath, params);
               if (visited.add(queryUrl)) {
                  attemptCount++;
                  List<MoviePtSearchResult> parsed = this.requestAndParseNexus(site, queryUrl, limit, baseUrl, headers, cookieHeader);
                  if (parsed == null) {
                     return List.of();
                  }

                  if (parsed.isEmpty()) {
                     if (!CollectionUtils.isEmpty(fallbackResults) && attemptCount >= 2) {
                        return fallbackResults;
                     }

                     if (attemptCount >= 6) {
                        return bestMatchCount > 0 ? bestMatched : fallbackResults;
                     }
                  } else {
                     if (CollectionUtils.isEmpty(fallbackResults)) {
                        fallbackResults = parsed;
                     }

                     int matchCount = this.countKeywordMatches(parsed, keyword);
                     if (matchCount > bestMatchCount) {
                        bestMatchCount = matchCount;
                        bestMatched = parsed;
                     }

                     if (matchCount > 0) {
                        return parsed;
                     }

                     if (!CollectionUtils.isEmpty(fallbackResults) && attemptCount >= 2) {
                        return fallbackResults;
                     }

                     if (attemptCount >= 6) {
                        return bestMatchCount > 0 ? bestMatched : fallbackResults;
                     }
                  }
               }
            }
         }

         return bestMatchCount > 0 ? bestMatched : fallbackResults;
      }
   }

   private List<String> buildNexusSearchPaths(MoviePtSite site, String baseUrl) {
      LinkedHashSet<String> paths = new LinkedHashSet<>();
      if (this.isHhanclub(site, baseUrl)) {
         paths.add(baseUrl + "/special.php");
      }

      paths.add(baseUrl + "/torrents.php");
      paths.add(baseUrl + "/browse.php");
      return new ArrayList<>(paths);
   }

   private List<Map<String, Object>> buildNexusSearchParams(String keyword) {
      List<Map<String, Object>> paramsList = new ArrayList<>();
      paramsList.add(this.buildNexusSearchParam("search", keyword, true));
      paramsList.add(this.buildNexusSearchParam("searchstr", keyword, true));
      paramsList.add(this.buildNexusSearchParam("search", keyword, false));
      return paramsList;
   }

   private Map<String, Object> buildNexusSearchParam(String field, String keyword, boolean fullParams) {
      Map<String, Object> params = new LinkedHashMap<>();
      params.put(field, keyword);
      params.put("incldead", 1);
      if (fullParams) {
         params.put("search_area", 0);
         params.put("search_mode", 0);
         params.put("notnewword", 1);
         params.put("page", 0);
      }

      return params;
   }

   private List<MoviePtSearchResult> requestAndParseNexus(
      MoviePtSite site, String queryUrl, int limit, String baseUrl, Map<String, String> headers, String cookieHeader
   ) {
      try {
         HttpResponse response = HttpRequest.get(queryUrl).headerMap(headers, true).setFollowRedirects(true).timeout(6000).cookie(cookieHeader).execute();
         if (response != null && response.getStatus() >= 200 && response.getStatus() < 300) {
            String html = response.body();
            if (!StringUtils.hasText(html)) {
               return List.of();
            } else if (this.looksLikeLoginPage(html)) {
               log.warn("站点 {} 返回登录页，请检查 Cookies 是否有效", site.getName());
               return null;
            } else {
               return this.parseNexusSearchHtml(html, limit, baseUrl, site.getId(), site.getName(), headers, cookieHeader);
            }
         } else {
            return List.of();
         }
      } catch (Exception var9) {
         log.debug("Nexus 搜索请求失败 site={} url={} err={}", site.getName(), queryUrl, var9.getMessage());
         return List.of();
      }
   }

   private List<MoviePtSearchResult> parseNexusSearchHtml(
      String html, int limit, String baseUrl, Long siteId, String siteName, Map<String, String> headers, String cookieHeader
   ) {
      LinkedHashMap<String, MoviePtSearchResult> merged = new LinkedHashMap<>();

      for (MoviePtSearchResult result : this.parseOpenCdHtml(html, limit, baseUrl, siteId, siteName, headers, cookieHeader)) {
         merged.putIfAbsent(this.buildResultUniqueKey(result), result);
      }

      for (MoviePtSearchResult result : this.parseGenericNexusHtml(html, limit, baseUrl, siteId, siteName, headers, cookieHeader)) {
         merged.putIfAbsent(this.buildResultUniqueKey(result), result);
      }

      return merged.isEmpty() ? List.of() : merged.values().stream().limit((long)limit).toList();
   }

   private String buildResultUniqueKey(MoviePtSearchResult result) {
      if (result == null) {
         return "";
      } else {
         return StringUtils.hasText(result.getTorrentId())
            ? this.trimToEmpty(result.getSiteName()) + "#" + result.getTorrentId()
            : this.trimToEmpty(result.getSiteName()) + "#" + this.normalizeFilterText(this.combineTexts(result.getTitle(), result.getSubtitle()));
      }
   }

   private boolean isHhanclub(MoviePtSite site, String baseUrl) {
      return site != null && "hhanclub".equalsIgnoreCase(site.getSiteType()) ? true : baseUrl != null && baseUrl.contains("hhanclub");
   }

   private List<MoviePtSearchResult> searchMteam(MoviePtSite site, String keyword, int limit) {
      String baseUrl = this.trimSuffix("https://api.m-team.cc");
      if (!StringUtils.hasText(baseUrl)) {
         return Collections.emptyList();
      } else {
         String apiBase = this.resolveMteamApiBase(baseUrl);
         String apiKey = this.resolveMteamApiKey(site.getToken());
         if (!StringUtils.hasText(apiKey)) {
            log.warn("M-Team 站点 {} 缺少 API Key", site.getName());
            return Collections.emptyList();
         } else {
            String url = apiBase + "/api/torrent/search";
            JSONObject payload = new JSONObject();
            payload.put("visible", Integer.valueOf(1));
            payload.put("keyword", keyword);
            payload.put("pageNumber", Integer.valueOf(1));
            payload.put("pageSize", Integer.valueOf(limit));
            HttpResponse response = HttpRequest.post(url).header("x-api-key", apiKey).body(payload.toJSONString()).timeout(15000).execute();
            if (response.getStatus() >= 200 && response.getStatus() < 300) {
               JSONObject data = JSON.parseObject(response.body());
               if (!"SUCCESS".equalsIgnoreCase(data.getString("message"))) {
                  return Collections.emptyList();
               } else {
                  JSONArray items = data.getJSONObject("data").getJSONArray("data");
                  return items == null ? Collections.emptyList() : this.parseMteamItems(items, baseUrl, site);
               }
            } else {
               return Collections.emptyList();
            }
         }
      }
   }

   protected List<MoviePtSearchResult> parseMteamItems(JSONArray items, String baseUrl, MoviePtSite site) {
      List<MoviePtSearchResult> results = new ArrayList<>();

      for (int i = 0; i < items.size(); i++) {
         JSONObject item = items.getJSONObject(i);
         String torrentId = String.valueOf(item.getLong("id"));
         String title = item.getString("name");
         String subtitle = item.getString("smallDescr");
         long sizeBytes = item.getLongValue("size");
         String size = this.formatSize(sizeBytes);
         JSONObject status = item.getJSONObject("status");
         String seeders = status == null ? "?" : String.valueOf(status.get("seeders"));
         String leechers = status == null ? "?" : String.valueOf(status.get("leechers"));
         String promo = status == null ? "" : status.getString("discount");
         String cover = "";
         if (StringUtils.hasText(item.getString("smallImage"))) {
            cover = item.getString("smallImage");
         } else if (item.containsKey("imageList")) {
            JSONArray imageList = item.getJSONArray("imageList");
            if (imageList != null && !imageList.isEmpty()) {
               cover = imageList.getString(0);
            }
         }

         String downloadUrl = baseUrl + "/download.php?id=" + torrentId;
         String detailUrl = baseUrl + "/detail/" + torrentId;
         List<String> tags = new ArrayList<>();
         if (item.containsKey("labelsNew")) {
            JSONArray labels = item.getJSONArray("labelsNew");
            if (labels != null) {
               for (int j = 0; j < labels.size(); j++) {
                  String label = labels.getString(j);
                  if (StringUtils.hasText(label)) {
                     tags.add(label);
                  }
               }
            }
         }

         String combinedText = this.combineTexts(title, subtitle);
         String resolution = this.extractResolution(title);
         String quality = this.extractQuality(combinedText);
         List<String> audioEffects = this.extractAudioEffects(combinedText);
         Date promotionUntil = null;
         if (status != null && status.containsKey("discountEndTime")) {
            String endTimeStr = status.getString("discountEndTime");
            if (StringUtils.hasText(endTimeStr)) {
               try {
                  promotionUntil = DateUtil.parse(endTimeStr);
               } catch (Exception var28) {
                  log.warn("Failed to parse M-Team discountEndTime: {}", endTimeStr);
               }
            }
         }

         results.add(
            MoviePtSearchResult.builder()
               .title(title)
               .subtitle(subtitle)
               .cover(cover)
               .size(size)
               .seeders(seeders)
               .leechers(leechers)
               .promo(this.normalizePromo(promo))
               .detailUrl(detailUrl)
               .torrentId(torrentId)
               .siteId(site.getId())
               .siteName(site.getName())
               .tags(tags)
               .resolution(resolution)
               .quality(quality)
               .audioEffects(audioEffects)
               .promotionUntil(promotionUntil)
               .build()
         );
      }

      return results;
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

   private Map<String, String> buildCommonHeaders(String baseUrl) {
      Map<String, String> headers = new HashMap<>();
      headers.put("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36");
      headers.put("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
      headers.put("Accept-Language", "zh-CN,zh;q=0.9,en;q=0.8");
      headers.put("Cache-Control", "no-cache");
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

   private List<MoviePtSearchResult> parseOpenCdHtml(
      String html, int limit, String baseUrl, Long siteId, String siteName, Map<String, String> headers, String cookieHeader
   ) {
      Document doc = Jsoup.parse(html);
      Element table = doc.selectFirst("table.torrents");
      if (table == null) {
         return Collections.emptyList();
      } else {
         List<MoviePtSearchResult> results = new ArrayList<>();

         for (Element row : table.select("tr")) {
            if (results.size() >= limit) {
               break;
            }

            Element torrentTable = row.selectFirst("table.torrentname");
            if (torrentTable != null) {
               Element titleLink = this.selectDetailLink(torrentTable);
               if (titleLink == null) {
                  titleLink = torrentTable.selectFirst("a[title]");
               }

               if (titleLink != null) {
                  String title = this.trimToEmpty(titleLink.attr("title"));
                  if (!StringUtils.hasText(title)) {
                     title = this.trimToEmpty(titleLink.text());
                  }

                  if (StringUtils.hasText(title)) {
                     String detailHref = titleLink.attr("href");
                     String torrentId = this.extractTorrentId(detailHref);
                     if (StringUtils.hasText(torrentId)) {
                        String subtitle = this.extractSubtitle(torrentTable);
                        String coverUrl = this.extractCover(row, baseUrl);
                        String size = "";
                        String seeders = "0";
                        String leechers = "0";

                        for (Element cell : row.select("td.rowfollow")) {
                           String text = cell.text().trim();
                           if (this.containsSize(text)) {
                              size = text.replace(" ", " ");
                           } else if (this.isPureNumber(text) || cell.selectFirst("a") != null && this.isPureNumber(cell.selectFirst("a").text())) {
                              Element aTag = cell.selectFirst("a");
                              if (aTag != null) {
                                 String num = aTag.text().trim();
                                 String href = aTag.attr("href");
                                 if (href.contains("#seeders")) {
                                    seeders = num;
                                 } else if (href.contains("#leechers")) {
                                    leechers = num;
                                 }
                              }
                           }
                        }

                        String promo = this.extractPromoFromImages(torrentTable);
                        if (!StringUtils.hasText(promo)) {
                           promo = this.extractPromoFromSpans(torrentTable);
                        }

                        List<String> tags = this.extractTags(torrentTable);
                        String combinedText2 = this.combineTexts(title, subtitle);
                        String resolution = this.extractResolution(title);
                        String quality = this.extractQuality(combinedText2);
                        List<String> audioEffects = this.extractAudioEffects(combinedText2);
                        Date promotionUntil = this.extractPromotionUntil(torrentTable);
                        String cleanBase = this.trimSuffix(baseUrl);
                        String downloadUrl = this.extractDownloadUrl(titleLink, row, cleanBase, torrentId);
                        String detailUrl = this.normalizeSiteUrl(detailHref, cleanBase);
                        if (!StringUtils.hasText(detailUrl)) {
                           detailUrl = cleanBase + "/details.php?id=" + torrentId;
                        }

                        results.add(
                           MoviePtSearchResult.builder()
                              .title(title)
                              .subtitle(subtitle)
                              .cover(coverUrl)
                              .size(size)
                              .seeders(seeders)
                              .leechers(leechers)
                              .promo(this.normalizePromo(promo))
                              .downloadUrl(downloadUrl)
                              .detailUrl(detailUrl)
                              .torrentId(torrentId)
                              .siteId(siteId)
                              .siteName(siteName)
                              .tags(tags)
                              .resolution(resolution)
                              .quality(quality)
                              .audioEffects(audioEffects)
                              .promotionUntil(promotionUntil)
                              .build()
                        );
                     }
                  }
               }
            }
         }

         return results;
      }
   }

   private List<MoviePtSearchResult> parseGenericNexusHtml(
      String html, int limit, String baseUrl, Long siteId, String siteName, Map<String, String> headers, String cookieHeader
   ) {
      Document doc = Jsoup.parse(html);
      Elements links = doc.select("a[href*=details.php][href*=id=], a[href*=detail.php][href*=id=], a[href*=/detail/]");
      List<MoviePtSearchResult> results = new ArrayList<>();
      Set<String> seen = new HashSet<>();

      for (Element link : links) {
         if (results.size() >= limit) {
            break;
         }

         String href = link.attr("href");
         if (!this.isUserDetailsLink(href)) {
            String torrentId = this.extractTorrentId(href);
            if (StringUtils.hasText(torrentId) && !seen.contains(torrentId)) {
               seen.add(torrentId);
               String title = link.attr("title");
               if (!StringUtils.hasText(title)) {
                  title = link.text().trim();
               }

               if (StringUtils.hasText(title) && title.length() >= 2) {
                  Element container = this.findContainer(link);
                  String containerText = container == null ? "" : container.text();
                  String size = this.extractSize(containerText, container);
                  String[] seederLeecher = this.extractSeedersLeechers(container);
                  String seeders = seederLeecher[0];
                  String leechers = seederLeecher[1];
                  String cover = this.extractCoverFromContainer(container, baseUrl);
                  String subtitle = this.extractSubtitle(container);
                  String promo = this.extractPromoFromContainer(container, containerText);
                  String cleanBase = this.trimSuffix(baseUrl);
                  String downloadUrl = this.extractDownloadUrl(link, container, cleanBase, torrentId);
                  String detailUrl = this.normalizeSiteUrl(href, cleanBase);
                  if (!StringUtils.hasText(detailUrl)) {
                     detailUrl = cleanBase + "/details.php?id=" + torrentId;
                  }

                  String combinedText3 = this.combineTexts(title, subtitle);
                  List<String> tags = this.extractTags(container);
                  results.add(
                     MoviePtSearchResult.builder()
                        .title(title)
                        .subtitle(subtitle)
                        .cover(cover)
                        .size(size)
                        .seeders(seeders)
                        .leechers(leechers)
                        .promo(this.normalizePromo(promo))
                        .downloadUrl(downloadUrl)
                        .detailUrl(detailUrl)
                        .torrentId(torrentId)
                        .siteId(siteId)
                        .siteName(siteName)
                        .tags(tags)
                        .resolution(this.extractResolution(title))
                        .quality(this.extractQuality(combinedText3))
                        .audioEffects(this.extractAudioEffects(combinedText3))
                        .build()
                  );
               }
            }
         }
      }

      return results;
   }

   private Element selectDetailLink(Element scope) {
      if (scope == null) {
         return null;
      } else {
         Element link = scope.selectFirst("a[href*=details.php][href*=id=]");
         if (link != null) {
            return link;
         } else {
            link = scope.selectFirst("a[href*=detail.php][href*=id=]");
            return link != null ? link : scope.selectFirst("a[href*=/detail/]");
         }
      }
   }

   private boolean isUserDetailsLink(String href) {
      return StringUtils.hasText(href) && href.toLowerCase(Locale.ROOT).contains("userdetails.php");
   }

   private Element findContainer(Element link) {
      Element current = link;

      for (int i = 0; i < 10; i++) {
         if (current == null) {
            return link;
         }

         Element parent = current.parent();
         if (parent == null) {
            return current;
         }

         if ("tr".equalsIgnoreCase(parent.tagName())) {
            return parent;
         }

         if ("div".equalsIgnoreCase(parent.tagName())) {
            String className = parent.className().toLowerCase(Locale.ROOT);
            if (className.contains("torrent") || className.contains("row") || className.contains("item") || className.contains("card")) {
               return parent;
            }
         }

         current = parent;
      }

      return link;
   }

   private String extractSize(String text, Element container) {
      Matcher matcher = SIZE_PATTERN.matcher(text == null ? "" : text);
      if (matcher.find()) {
         return matcher.group(1).replace(" ", "");
      } else {
         Element search = container;

         for (int i = 0; i < 5; i++) {
            if (search != null) {
               String candidate = search.text();
               Matcher matcher2 = SIZE_PATTERN.matcher(candidate);
               if (matcher2.find()) {
                  return matcher2.group(1).replace(" ", "");
               }

               search = search.parent();
            }
         }

         return "";
      }
   }

   private String[] extractSeedersLeechers(Element container) {
      String seeders = "0";
      String leechers = "0";
      Element search = container;

      for (int i = 0; i < 8 && search != null; i++) {
         Element seederLink = search.selectFirst("a[href*=#seeders], a[href*=seeders]");
         if (seederLink != null) {
            seeders = seederLink.text().trim();
            String siblingText = seederLink.nextSibling() == null ? "" : seederLink.nextSibling().toString();
            Matcher matcher = Pattern.compile("[/\\s]+(\\d+)").matcher(siblingText);
            if (matcher.find()) {
               leechers = matcher.group(1);
            }

            return new String[]{seeders, leechers};
         }

         Element snatchLink = search.selectFirst("a[href*=viewsnatches]");
         if (snatchLink != null) {
            String prev = snatchLink.previousSibling() == null ? "" : snatchLink.previousSibling().toString();
            List<String> nums = new ArrayList<>();
            Matcher matcher = Pattern.compile("\\d+").matcher(prev);

            while (matcher.find()) {
               nums.add(matcher.group());
            }

            if (nums.size() >= 2) {
               seeders = nums.get(nums.size() - 2);
               leechers = nums.get(nums.size() - 1);
               return new String[]{seeders, leechers};
            }
         }

         search = search.parent();
      }

      if (container != null) {
         String text = container.text();
         Matcher matcher = Pattern.compile("\\b(\\d+)\\b").matcher(text);
         List<String> nums = new ArrayList<>();

         while (matcher.find()) {
            String num = matcher.group(1);
            if (num.length() <= 4) {
               nums.add(num);
            }
         }

         if (nums.size() >= 2) {
            seeders = nums.get(0);
            leechers = nums.get(1);
         }
      }

      return new String[]{seeders, leechers};
   }

   private boolean hasDownloadLink(Element container) {
      if (container == null) {
         return false;
      } else if (container.selectFirst("a[href*=download.php]") != null) {
         return true;
      } else {
         Element search = container;

         for (int i = 0; i < 5 && search != null; i++) {
            if (search.selectFirst("a[href*=download.php]") != null) {
               return true;
            }

            search = search.parent();
         }

         return false;
      }
   }

   private String extractDownloadUrl(Element sourceLink, Element container, String baseUrl, String torrentId) {
      List<Element> scopes = new ArrayList<>();
      if (container != null) {
         scopes.add(container);
         Element parent = container.parent();

         for (int i = 0; i < 5 && parent != null; i++) {
            scopes.add(parent);
            parent = parent.parent();
         }
      }

      if (sourceLink != null) {
         scopes.add(sourceLink);
         Element parent = sourceLink.parent();

         for (int i = 0; i < 3 && parent != null; i++) {
            scopes.add(parent);
            parent = parent.parent();
         }
      }

      for (Element scope : scopes) {
         String href = this.extractDownloadHref(scope, torrentId);
         String normalized = this.normalizeSiteUrl(href, baseUrl);
         if (StringUtils.hasText(normalized)) {
            return normalized;
         }

         String onclickHref = this.extractDownloadHrefFromOnclick(scope, torrentId);
         normalized = this.normalizeSiteUrl(onclickHref, baseUrl);
         if (StringUtils.hasText(normalized)) {
            return normalized;
         }
      }

      return this.trimSuffix(baseUrl) + "/download.php?id=" + torrentId;
   }

   private String extractDownloadHref(Element scope, String torrentId) {
      if (scope == null) {
         return "";
      } else {
         Elements anchors = scope.select("a[href*=download.php]");
         if (anchors.isEmpty()) {
            return "";
         } else {
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

            return fallback;
         }
      }
   }

   private String extractDownloadHrefFromOnclick(Element scope, String torrentId) {
      if (scope == null) {
         return "";
      } else {
         for (Element node : scope.select("*[onclick*=download.php]")) {
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

         return "";
      }
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

   private String extractCover(Element row, String baseUrl) {
      String cover = "";
      if (row == null) {
         return cover;
      } else {
         for (Element elem : row.getElementsByAttribute("onmouseover")) {
            String mouse = elem.attr("onmouseover");
            Matcher matcher = IMG_SRC_PATTERN.matcher(mouse);
            if (matcher.find()) {
               cover = matcher.group(1);
               if (!cover.startsWith("http")) {
                  cover = baseUrl + "/" + this.trimLeadingSlash(cover);
               }

               return cover;
            }
         }

         for (Element img : row.select("img")) {
            String src = img.attr("src");
            if (src.contains("thumb") && (src.contains("attachments") || src.contains("pic"))) {
               return src.startsWith("http") ? src : baseUrl + "/" + this.trimLeadingSlash(src);
            }
         }

         return cover;
      }
   }

   private String extractCoverFromContainer(Element container, String baseUrl) {
      if (container == null) {
         return "";
      } else {
         for (Element elem : container.getElementsByAttribute("onmouseover")) {
            String mouse = elem.attr("onmouseover");
            Matcher matcher = IMG_SRC_PATTERN.matcher(mouse);
            if (matcher.find()) {
               String cover = matcher.group(1);
               if (!cover.startsWith("http")) {
                  cover = baseUrl + "/" + this.trimLeadingSlash(cover);
               }

               return cover;
            }
         }

         for (Element img : container.select("img")) {
            String src = this.firstNonBlank(img.attr("src"), img.attr("data-src"), img.attr("data-original"));
            if (StringUtils.hasText(src)) {
               String lower = src.toLowerCase(Locale.ROOT);
               if (!lower.contains("icon")
                  && !lower.contains("sprite")
                  && !lower.contains("arrow")
                  && !lower.contains("button")
                  && !lower.contains("logo")
                  && !lower.contains("catspic")
                  && !lower.contains("pic/trans")
                  && (
                     lower.contains("attachments") || lower.contains("pic") || lower.contains("poster") || lower.contains("cover") || lower.contains("images/")
                  )) {
                  return src.startsWith("http") ? src : baseUrl + "/" + this.trimLeadingSlash(src);
               }
            }
         }

         for (Element elemx : container.select("[style]")) {
            String style = elemx.attr("style");
            Matcher matcher = STYLE_BG_PATTERN.matcher(style);
            if (matcher.find()) {
               String cover = matcher.group(1);
               return cover.startsWith("http") ? cover : baseUrl + "/" + this.trimLeadingSlash(cover);
            }
         }

         return "";
      }
   }

   private String extractSubtitle(Element container) {
      if (container == null) {
         return "";
      } else {
         LinkedHashSet<String> parts = new LinkedHashSet<>();
         Element subtitleElem = container.selectFirst("font[color~=^(?i)#?8{3,6}|gray$]");
         if (subtitleElem == null) {
            subtitleElem = container.selectFirst("[class~=sub|desc|small|secondary]");
         }

         this.addSubtitlePart(parts, subtitleElem == null ? "" : subtitleElem.text());

         for (Element span : container.select("span")) {
            if (this.isSummarySpan(span)) {
               this.addSubtitlePart(parts, span.text());
            }
         }

         this.addSubtitlePart(parts, this.extractPlainSubtitleText(container));
         return parts.isEmpty() ? "" : String.join(" / ", parts);
      }
   }

   private void addSubtitlePart(Set<String> parts, String text) {
      String normalized = this.normalizeSubtitleText(text);
      if (StringUtils.hasText(normalized)) {
         parts.add(normalized);
      }
   }

   private String normalizeSubtitleText(String text) {
      return !StringUtils.hasText(text) ? "" : text.replace(' ', ' ').replaceAll("\\s+", " ").trim();
   }

   private boolean isSummarySpan(Element span) {
      if (span != null && !span.hasAttr("alt")) {
         String text = this.normalizeSubtitleText(span.text());
         if (!StringUtils.hasText(text)) {
            return false;
         } else {
            String className = this.trimToEmpty(span.className()).toLowerCase(Locale.ROOT);
            if (className.contains("tag")) {
               return false;
            } else if (text.length() <= 6 && !text.contains("：") && !text.contains(":") && !text.contains("|")) {
               return false;
            } else {
               String style = this.trimToEmpty(span.attr("style")).toLowerCase(Locale.ROOT);
               if (style.contains("line-height") || style.contains("padding")) {
                  return true;
               } else {
                  return !text.contains("类型：")
                        && !text.contains("类型:")
                        && !text.contains("主演：")
                        && !text.contains("主演:")
                        && !text.contains("导演：")
                        && !text.contains("导演:")
                        && !text.contains("|")
                     ? text.length() >= 18 && (text.contains("/") || text.contains("／"))
                     : true;
               }
            }
         }
      } else {
         return false;
      }
   }

   private String extractPlainSubtitleText(Element container) {
      if (container == null) {
         return "";
      } else {
         Element detailLink = this.selectDetailLink(container);
         Element textCell = this.findSubtitleTextCell(detailLink);
         if (textCell == null) {
            textCell = container.selectFirst("td.embedded:has(a[href*=details.php][href*=id=],a[href*=detail.php][href*=id=],a[href*=/detail/])");
         }

         if (textCell == null) {
            return "";
         } else {
            Element clean = textCell.clone();
            clean.select("a,b,font,img,svg,script,style,table,i").remove();
            clean.select("span").remove();
            String text = this.normalizeSubtitleText(clean.text());
            return StringUtils.hasText(text) && text.length() >= 6 ? text : "";
         }
      }
   }

   private Element findSubtitleTextCell(Element element) {
      Element current = element;

      for (int i = 0; i < 8 && current != null; i++) {
         if ("td".equalsIgnoreCase(current.tagName())) {
            return current;
         }

         current = current.parent();
      }

      return null;
   }

   private String extractPromoFromImages(Element torrentTable) {
      if (torrentTable == null) {
         return "";
      } else {
         for (Element img : torrentTable.select("img[class*=pro_]")) {
            for (String cls : img.classNames()) {
               if (cls.contains("pro_free2up")) {
                  return "免费2x↑";
               }

               if (cls.contains("pro_free")) {
                  return "免费";
               }

               if (cls.contains("pro_50pctdown2up")) {
                  return "50%↓2x↑";
               }

               if (cls.contains("pro_50pctdown")) {
                  return "50%↓";
               }

               if (cls.contains("pro_30pctdown")) {
                  return "30%↓";
               }

               if (cls.contains("pro_2up")) {
                  return "2x↑";
               }
            }
         }

         return "";
      }
   }

   private String extractPromoFromContainer(Element container, String text) {
      String promo = "";
      if (container != null) {
         Element promoImg = container.selectFirst("img[class*=pro_]");
         if (promoImg != null) {
            for (String cls : promoImg.classNames()) {
               if (cls.contains("pro_free2up")) {
                  promo = "免费2x↑";
               } else if (cls.contains("pro_free")) {
                  promo = "免费";
               } else if (cls.contains("pro_50pctdown2up")) {
                  promo = "50%↓2x↑";
               } else if (cls.contains("pro_50pctdown")) {
                  promo = "50%↓";
               } else if (cls.contains("pro_30pctdown")) {
                  promo = "30%↓";
               } else if (cls.contains("pro_2up")) {
                  promo = "2x↑";
               }

               if (StringUtils.hasText(promo)) {
                  break;
               }
            }
         }
      }

      if (!StringUtils.hasText(promo) && StringUtils.hasText(text)) {
         String lower = text.toLowerCase(Locale.ROOT);
         if (lower.contains("free") || lower.contains("免费")) {
            promo = "免费";
         }
      }

      return promo;
   }

   private String fetchCoverFromDetails(String detailUrl, Map<String, String> headers, String cookieHeader, String baseUrl) {
      try {
         HttpResponse response = HttpRequest.get(detailUrl).headerMap(headers, true).cookie(cookieHeader).timeout(10000).execute();
         String body = response.body();
         if (!StringUtils.hasText(body)) {
            return "";
         }

         Document doc = Jsoup.parse(body);
         Elements imgs = doc.select("img");
         String[] skipKeywords = new String[]{"icon", "sprite", "logo", "button", "catspic", "default", "trans", "avatar"};

         for (Element img : imgs) {
            String dataSrc = this.firstNonBlank(img.attr("data-src"), img.attr("data-original"));
            if (StringUtils.hasText(dataSrc) && this.hasValidImageExt(dataSrc)) {
               String lower = dataSrc.toLowerCase(Locale.ROOT);
               if (!this.containsAny(lower, skipKeywords)) {
                  return dataSrc.startsWith("http") ? dataSrc : baseUrl + "/" + this.trimLeadingSlash(dataSrc);
               }
            }
         }

         Element coverLabel = doc.selectFirst("*:matchesOwn((?i)封面|Cover)");
         if (coverLabel != null) {
            Element parent = coverLabel.parent();

            for (int i = 0; i < 5 && parent != null; i++) {
               Element imgx = parent.selectFirst("img");
               if (imgx != null) {
                  String src = this.firstNonBlank(imgx.attr("data-src"), imgx.attr("src"));
                  if (StringUtils.hasText(src) && this.hasValidImageExt(src) && !src.toLowerCase(Locale.ROOT).contains("default")) {
                     return src.startsWith("http") ? src : baseUrl + "/" + this.trimLeadingSlash(src);
                  }
               }

               parent = parent.parent();
            }
         }

         for (Element imgx : imgs) {
            String src = this.firstNonBlank(imgx.attr("src"), imgx.attr("data-src"));
            if (StringUtils.hasText(src) && src.contains("attachments") && this.hasValidImageExt(src)) {
               String lower = src.toLowerCase(Locale.ROOT);
               if (!this.containsAny(lower, skipKeywords)) {
                  return src.startsWith("http") ? src : baseUrl + "/" + this.trimLeadingSlash(src);
               }
            }
         }
      } catch (Exception var15) {
         log.warn("获取封面失败: {}", var15.getMessage());
      }

      return "";
   }

   private boolean hasValidImageExt(String url) {
      String lower = url.toLowerCase(Locale.ROOT);
      return lower.endsWith(".jpg") || lower.endsWith(".jpeg") || lower.endsWith(".png") || lower.endsWith(".webp");
   }

   private boolean containsAny(String text, String[] keywords) {
      for (String keyword : keywords) {
         if (text.contains(keyword)) {
            return true;
         }
      }

      return false;
   }

   private String extractTorrentId(String href) {
      if (!StringUtils.hasText(href)) {
         return "";
      } else {
         Matcher matcher = TORRENT_ID_PATTERN.matcher(href);
         if (matcher.find()) {
            return matcher.group(1);
         } else {
            matcher = DETAIL_ROUTE_PATTERN.matcher(href);
            return matcher.find() ? matcher.group(1) : "";
         }
      }
   }

   private boolean containsSize(String text) {
      if (!StringUtils.hasText(text)) {
         return false;
      } else {
         String upper = text.toUpperCase(Locale.ROOT);
         return upper.contains("GB") || upper.contains("MB") || upper.contains("KB") || upper.contains("TB");
      }
   }

   private boolean isPureNumber(String text) {
      if (!StringUtils.hasText(text)) {
         return false;
      } else {
         for (int i = 0; i < text.length(); i++) {
            if (!Character.isDigit(text.charAt(i))) {
               return false;
            }
         }

         return true;
      }
   }

   private String normalizePromo(String promo) {
      if (!StringUtils.hasText(promo)) {
         return "";
      } else {
         String lower = promo.toLowerCase(Locale.ROOT).trim();
         if (lower.equals("normal") || lower.equals("null") || lower.equals("none")) {
            return "";
         } else if (lower.contains("percent_50")) {
            return "50%↓";
         } else if (lower.contains("percent_30")) {
            return "30%↓";
         } else if (lower.contains("percent_100") || lower.contains("free")) {
            return "免费";
         } else if (lower.contains("free2up") || lower.contains("free_2x_free") || lower.contains("2xfree")) {
            return "免费2x↑";
         } else if (lower.contains("free") || lower.contains("免费")) {
            return "免费";
         } else if (lower.contains("50pctdown2up") || lower.contains("50%2x") || lower.contains("half2x")) {
            return "50%↓2x↑";
         } else if (lower.contains("50pctdown") || lower.contains("50%") || lower.contains("half") || lower.contains("_50")) {
            return "50%↓";
         } else if (lower.contains("30pctdown") || lower.contains("30%") || lower.contains("_30")) {
            return "30%↓";
         } else if (lower.contains("2up") || lower.contains("2x") || lower.contains("double")) {
            return "2x↑";
         } else {
            return !promo.contains("免费") && !promo.contains("↓") && !promo.contains("↑") && !promo.contains("%") ? promo : promo;
         }
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

   private String trimToEmpty(String value) {
      return value == null ? "" : value.trim();
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

   private String buildQueryUrl(String baseUrl, Map<String, Object> params) {
      if (params != null && !params.isEmpty()) {
         StringBuilder builder = new StringBuilder(baseUrl);
         builder.append(baseUrl.contains("?") ? "&" : "?");
         boolean first = true;

         for (Entry<String, Object> entry : params.entrySet()) {
            if (!first) {
               builder.append("&");
            }

            first = false;
            builder.append(this.urlEncode(entry.getKey())).append("=").append(this.urlEncode(entry.getValue() == null ? "" : String.valueOf(entry.getValue())));
         }

         return builder.toString();
      } else {
         return baseUrl;
      }
   }

   private String urlEncode(String value) {
      try {
         return URLEncoder.encode(value == null ? "" : value, StandardCharsets.UTF_8);
      } catch (Exception var3) {
         return value == null ? "" : value;
      }
   }

   private String firstNonBlank(String... values) {
      for (String value : values) {
         if (StringUtils.hasText(value)) {
            return value;
         }
      }

      return "";
   }

   private String formatSize(long sizeBytes) {
      if (sizeBytes <= 0L) {
         return "";
      } else {
         return sizeBytes >= 1073741824L
            ? String.format(Locale.ROOT, "%.2f GB", (double)sizeBytes / 1024.0 / 1024.0 / 1024.0)
            : String.format(Locale.ROOT, "%.2f MB", (double)sizeBytes / 1024.0 / 1024.0);
      }
   }

   private String extractPromoFromSpans(Element container) {
      if (container == null) {
         return "";
      } else {
         for (Element span : container.select("span[alt]")) {
            String alt = span.attr("alt");
            if (StringUtils.hasText(alt)) {
               String lower = alt.toLowerCase(Locale.ROOT);
               if (lower.contains("free")) {
                  return "免费";
               }

               if (lower.contains("50%")) {
                  return "50%↓";
               }

               if (lower.contains("30%")) {
                  return "30%↓";
               }

               if (lower.contains("2x") || lower.contains("2up")) {
                  return "2x↑";
               }
            }
         }

         for (Element spanx : container.select("span")) {
            String text = spanx.text().trim();
            if ("free".equalsIgnoreCase(text) || "免费".equals(text)) {
               return "免费";
            }

            if (text.endsWith("%")) {
               return text + "↓";
            }
         }

         return "";
      }
   }

   private List<String> extractTags(Element container) {
      if (container == null) {
         return Collections.emptyList();
      } else {
         Set<String> tagSet = new LinkedHashSet<>();

         for (Element span : container.select("span")) {
            String style = span.attr("style");
            if (style.contains("background") || span.hasClass("tag") || span.hasClass("tags")) {
               String text = span.text().trim();
               if (StringUtils.hasText(text) && text.length() < 10 && !text.contains("Free") && !text.contains("%")) {
                  tagSet.add(text);
               }
            }
         }

         for (Element node : container.select(".tag, .tags, [class*=tag-], [class*=tags-]")) {
            boolean isTagNode = node.hasClass("tag") || node.hasClass("tags");
            if (!isTagNode) {
               for (String cls : node.classNames()) {
                  String lowerClass = this.trimToEmpty(cls).toLowerCase(Locale.ROOT);
                  if ("tags".equals(lowerClass) || lowerClass.startsWith("tag-") || lowerClass.startsWith("tags-")) {
                     isTagNode = true;
                     break;
                  }
               }
            }

            if (isTagNode) {
               String text = this.normalizeSubtitleText(node.text());
               if (StringUtils.hasText(text) && text.length() <= 12) {
                  tagSet.add(text);
               }
            }
         }

         for (Element circleText : container.select(".circle-text")) {
            String text = this.normalizeSubtitleText(circleText.text()).toLowerCase(Locale.ROOT);
            if (StringUtils.hasText(text) && text.length() <= 10) {
               tagSet.add(text);
            }
         }

         return (List<String>)(tagSet.isEmpty() ? Collections.emptyList() : new ArrayList<>(tagSet));
      }
   }

   private String extractResolution(String title) {
      if (!StringUtils.hasText(title)) {
         return "";
      } else {
         Matcher matcher = RESOLUTION_PATTERN.matcher(title);
         return matcher.find() ? matcher.group(1) : "";
      }
   }

   private String extractQuality(String text) {
      if (!StringUtils.hasText(text)) {
         return "";
      } else {
         Matcher matcher = QUALITY_PATTERN.matcher(text);
         return matcher.find() ? this.normalizeQuality(matcher.group(1)) : "";
      }
   }

   private List<String> extractAudioEffects(String text) {
      if (!StringUtils.hasText(text)) {
         return List.of();
      } else {
         Set<String> effects = new LinkedHashSet<>();
         Matcher matcher = AUDIO_EFFECT_PATTERN.matcher(text);

         while (matcher.find()) {
            effects.add(this.normalizeAudioEffect(matcher.group(1)));
         }

         return (List<String>)(effects.isEmpty() ? List.of() : new ArrayList<>(effects));
      }
   }

   private String normalizeQuality(String raw) {
      if (!StringUtils.hasText(raw)) {
         return "";
      } else {
         String lower = raw.toLowerCase(Locale.ROOT).replaceAll("[-\\s_.]+", "");
         if (lower.contains("bdremux") || lower.equals("remux")) {
            return "remux";
         } else if (lower.contains("bluray") || lower.contains("bdray")) {
            return "bluray";
         } else if (lower.contains("webdl")) {
            return "webdl";
         } else if (lower.contains("webrip")) {
            return "webrip";
         } else if (lower.contains("hdtv")) {
            return "hdtv";
         } else if (lower.contains("hdrip")) {
            return "hdrip";
         } else if (lower.contains("dvdrip")) {
            return "dvdrip";
         } else if (lower.contains("dvd")) {
            return "dvd";
         } else {
            return lower.contains("bdrip") ? "bdrip" : lower;
         }
      }
   }

   private String normalizeAudioEffect(String raw) {
      if (!StringUtils.hasText(raw)) {
         return "";
      } else {
         String lower = raw.toLowerCase(Locale.ROOT).replaceAll("[\\s._]+", "");
         if (lower.contains("dolbyvision") || lower.equals("dovi") || lower.equals("dv")) {
            return "dolby_vision";
         } else if (lower.contains("hdr10plus") || lower.equals("hdr10+")) {
            return "hdr10plus";
         } else if (lower.equals("hdr")) {
            return "hdr";
         } else if (lower.contains("atmos")) {
            return "atmos";
         } else if (lower.contains("dtshd") || lower.contains("dts-hd")) {
            return "dts_hd";
         } else if (lower.contains("truehd")) {
            return "truehd";
         } else if (lower.equals("flac")) {
            return "flac";
         } else if (lower.equals("aac")) {
            return "aac";
         } else if (lower.contains("dd") || lower.contains("ddp") || lower.contains("eac3")) {
            return "ddplus";
         } else if (lower.equals("ac3")) {
            return "ac3";
         } else {
            return lower.equals("lpcm") ? "lpcm" : lower;
         }
      }
   }

   private String combineTexts(String... texts) {
      StringBuilder sb = new StringBuilder();

      for (String text : texts) {
         if (StringUtils.hasText(text)) {
            if (sb.length() > 0) {
               sb.append(" ");
            }

            sb.append(text.trim());
         }
      }

      return sb.toString();
   }

   private Date extractPromotionUntil(Element container) {
      if (container == null) {
         return null;
      } else {
         for (Element font : container.select("font:contains(剩余时间)")) {
            Element dateSpan = font.selectFirst("span[title]");
            if (dateSpan != null) {
               String dateStr = dateSpan.attr("title");
               Matcher matcher = PROMO_DEADLINE_PATTERN.matcher(dateStr);
               if (matcher.find()) {
                  try {
                     return DateUtil.parse(matcher.group(1));
                  } catch (Exception var9) {
                  }
               }
            }
         }

         return null;
      }
   }

   @Generated
   public MoviePtSearchServiceImpl(final MoviePtSiteService moviePtSiteService) {
      this.moviePtSiteService = moviePtSiteService;
   }
}
