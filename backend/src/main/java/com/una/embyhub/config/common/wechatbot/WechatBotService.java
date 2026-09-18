package com.una.embyhub.config.common.wechatbot;

import cn.hutool.http.HttpRequest;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.una.embyhub.config.common.exception.BizException;
import com.una.embyhub.config.common.utils.NotifyChannelCacheLoaderUtils;
import com.una.embyhub.config.common.utils.WechatBotUtils;
import com.una.embyhub.model.dto.request.embyuser.EmbyUserSave;
import com.una.embyhub.model.dto.response.embyuser.InsertUserResponse;
import com.una.embyhub.model.dto.response.embyuser.UserStatsResponse;
import com.una.embyhub.model.dto.response.nullbr.MovieListResponse;
import com.una.embyhub.model.dto.response.tmdb.TmdbResponse;
import com.una.embyhub.model.entity.BaseEntity;
import com.una.embyhub.model.entity.EmbyInfo;
import com.una.embyhub.service.CardSecurityManagementService;
import com.una.embyhub.service.EmbyInfoService;
import com.una.embyhub.service.EmbyUserService;
import com.una.embyhub.service.NullbrService;
import com.una.embyhub.service.TmdbService;
import java.io.Serializable;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import lombok.Generated;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

@Service
public class WechatBotService {
   @Generated
   private static final Logger log = LoggerFactory.getLogger(WechatBotService.class);
   private final TmdbService tmdbService;
   private final EmbyUserService embyUserService;
   private final CardSecurityManagementService cardSecurityManagementService;
   private final EmbyInfoService embyInfoService;
   private final NullbrService nullbrService;
   private final NotifyChannelCacheLoaderUtils notifyChannelCacheLoaderUtils;
   @Autowired
   private RedisTemplate<String, Object> redisTemplate;
   private static final int SEARCH_PAGE_SIZE = 5;
   private static final Duration SEARCH_SESSION_TTL = Duration.ofMinutes(10L);
   private static final String REDIS_KEY_PREFIX_SEARCH = "wechat:search:session:";

   public String handleMessage(String content, String fromUser) {
      if (!StringUtils.hasText(content)) {
         return "请输入指令或关键词，例如：搜索 星际穿越";
      } else {
         String normalized = this.translateMenuShortcut(content.trim());
         if (!normalized.startsWith("/start") && !normalized.equalsIgnoreCase("help") && !normalized.equals("帮助") && !"WECHAT_BOT_HELP".equals(normalized)) {
            if (normalized.startsWith("搜索") || normalized.toLowerCase().startsWith("search")) {
               String query = normalized.replaceFirst("(?i)搜索|search", "").trim();
               return !StringUtils.hasText(query) ? "请输入要搜索的影视名称，例如：搜索 银河护卫队" : this.startSearchSession(fromUser, query, 1);
            } else if (!normalized.equalsIgnoreCase("stats") && !normalized.equals("统计") && !"WECHAT_BOT_STATS".equals(normalized)) {
               if (!normalized.toLowerCase().startsWith("/createuser") && !normalized.startsWith("创建用户")) {
                  if ("WECHAT_BOT_CREATE_USER".equals(normalized)) {
                     return !this.isPrivilegedSender(fromUser) ? this.buildForbiddenSenderMessage() : this.buildCreateUserGuide();
                  } else if (!normalized.toLowerCase().startsWith("/extendusers") && !normalized.startsWith("批量延期")) {
                     if ("WECHAT_BOT_EXTEND_USERS".equals(normalized)) {
                        return !this.isPrivilegedSender(fromUser) ? this.buildForbiddenSenderMessage() : this.buildExtendUserGuide();
                     } else if (!normalized.toLowerCase().startsWith("/generatecards") && !normalized.startsWith("生成卡密")) {
                        if ("WECHAT_BOT_GENERATE_CARDS".equals(normalized)) {
                           return !this.isPrivilegedSender(fromUser) ? this.buildForbiddenSenderMessage() : this.buildGenerateCardsGuide();
                        } else if ("WECHAT_BOT_SEARCH_GUIDE".equals(normalized)) {
                           return this.buildSearchGuideMessage();
                        } else {
                           return "WECHAT_BOT_SEARCH_SAMPLE".equals(normalized) ? this.searchMovie("银河护卫队") : "未识别的指令，发送 /start 查看可用命令。";
                        }
                     } else {
                        return !this.isPrivilegedSender(fromUser) ? this.buildForbiddenSenderMessage() : this.handleGenerateCards(normalized);
                     }
                  } else {
                     return !this.isPrivilegedSender(fromUser) ? this.buildForbiddenSenderMessage() : this.handleExtendUsers(normalized);
                  }
               } else {
                  return !this.isPrivilegedSender(fromUser) ? this.buildForbiddenSenderMessage() : this.handleCreateUser(normalized);
               }
            } else {
               return !this.isPrivilegedSender(fromUser) ? this.buildForbiddenSenderMessage() : this.buildStats();
            }
         } else {
            return this.buildHelpMessage();
         }
      }
   }

   private String translateMenuShortcut(String content) {
      switch (content) {
         case "WECHAT_BOT_HELP":
            return "/start";
         case "WECHAT_BOT_STATS":
            return "统计";
         case "WECHAT_BOT_CREATE_USER":
            return "创建用户";
         case "WECHAT_BOT_EXTEND_USERS":
            return "批量延期";
         case "WECHAT_BOT_GENERATE_CARDS":
            return "生成卡密";
         case "WECHAT_BOT_SEARCH_GUIDE":
         case "WECHAT_BOT_SEARCH_SAMPLE":
            return content;
         default:
            return content;
      }
   }

   private String buildHelpMessage() {
      return String.join(
         "\n",
         "\ud83c\udfac 企业微信机器人",
         "",
         "可用指令（[N]表示服务器编号）：",
         "• 资源[N] <关键词> —— 搜索 Emby 资源库",
         "• 搜索 <关键词> —— 搜索 TMDB 影视信息",
         "• 创建用户[N] <用户名> <天数> <备注>",
         "• 生成卡密[N] <数量> <天数>",
         "• 批量延期[N] <天数> [过期范围]",
         "• 统计 —— 查看用户状态统计",
         "",
         "点击应用菜单查看服务器列表"
      );
   }

   private String buildSearchGuideMessage() {
      return String.join("\n", "如何搜索？", "1. 点击菜单中的【示例：银河护卫队】快速体验，", "2. 或直接发送：搜索 你想看的片名 (如 搜索 三体)");
   }

   public String searchMovie(String query) {
      try {
         TmdbResponse response = this.tmdbService.searchDataTelegram(query, 1);
         List<TmdbResponse.Result> results = Optional.ofNullable(response.getResults()).orElse(Collections.emptyList()).stream().limit(5L).toList();
         if (results.isEmpty()) {
            return "没有找到相关的影视信息，请尝试其它关键词。";
         } else {
            StringBuilder sb = new StringBuilder();
            sb.append(String.format("\ud83c\udfac 搜索「%s」\n\n", query));

            for (int i = 0; i < results.size(); i++) {
               TmdbResponse.Result result = results.get(i);
               String title = StringUtils.hasText(result.getTitle()) ? result.getTitle() : result.getName();
               String release = StringUtils.hasText(result.getReleaseDate()) ? result.getReleaseDate() : result.getFirstAirDate();
               String year = StringUtils.hasText(release) && release.length() >= 4 ? release.substring(0, 4) : "";
               String rating = result.getVoteAverage() == null ? "暂无" : String.valueOf(result.getVoteAverage());
               String type = "movie".equalsIgnoreCase(result.getMediaType()) ? "电影" : ("tv".equalsIgnoreCase(result.getMediaType()) ? "剧集" : "其他");
               sb.append(String.format("%d) %s (%s) ⭐%s | %s\n", i + 1, title, year, rating, type));
            }

            return sb.toString().trim();
         }
      } catch (BizException var12) {
         log.warn("企业微信搜索受限: {}", var12.getMessage());
         return var12.getMessage();
      } catch (Exception var13) {
         log.error("企业微信搜索失败", (Throwable)var13);
         return "搜索时发生错误，请稍后重试。";
      }
   }

   public String handleSearchInteraction(String content, String fromUser) {
      String normalized = content.trim();
      Optional<WechatBotService.SearchSession> sessionOptional = this.getValidSession(fromUser);
      if (!sessionOptional.isPresent()) {
         return "";
      } else {
         WechatBotService.SearchSession session = sessionOptional.get();
         if (!this.isNextPageCommand(normalized) && !this.isPrevPageCommand(normalized)) {
            if (this.isCancelCommand(normalized)) {
               this.redisTemplate.delete(this.buildKey(fromUser));
               return "已退出当前搜索会话。";
            } else {
               return normalized.matches("[1-5]") && !CollectionUtils.isEmpty(session.getResults()) ? "" : "";
            }
         } else {
            if (this.isNextPageCommand(normalized)) {
               int nextPage = Math.min(session.getTotalPages(), session.getPage() + 1);
               if (nextPage == session.getPage()) {
                  return "已经是最后一页啦～";
               }
            }

            if (this.isPrevPageCommand(normalized)) {
               int prevPage = Math.max(1, session.getPage() - 1);
               if (prevPage == session.getPage()) {
                  return "已经是第一页啦～";
               }
            }

            return "";
         }
      }
   }

   public boolean isPageNavigationCommand(String content, String fromUser) {
      if (!StringUtils.hasText(content)) {
         return false;
      } else {
         String normalized = content.trim();
         if (!this.isNextPageCommand(normalized) && !this.isPrevPageCommand(normalized)) {
            return false;
         } else {
            Optional<WechatBotService.SearchSession> sessionOptional = this.getValidSession(fromUser);
            return sessionOptional.isPresent();
         }
      }
   }

   public List<WechatMessageParser.NewsArticle> handlePageNavigation(String content, String fromUser) {
      String normalized = content.trim();
      Optional<WechatBotService.SearchSession> sessionOptional = this.getValidSession(fromUser);
      if (!sessionOptional.isPresent()) {
         return Collections.emptyList();
      } else {
         WechatBotService.SearchSession session = sessionOptional.get();
         int newPage = session.getPage();
         if (this.isNextPageCommand(normalized)) {
            newPage = Math.min(session.getTotalPages(), session.getPage() + 1);
         } else if (this.isPrevPageCommand(normalized)) {
            newPage = Math.max(1, session.getPage() - 1);
         }

         return "EMBY".equals(session.getSessionType())
            ? this.searchEmbyWithImagesPage(session.getQuery(), session.getServerNumber(), newPage, fromUser)
            : this.searchMovieWithImagesPage(session.getQuery(), fromUser, newPage);
      }
   }

   public boolean isNumberSelectionCommand(String content, String fromUser) {
      if (!StringUtils.hasText(content)) {
         return false;
      } else {
         String normalized = content.trim();
         if (!normalized.matches("[1-5]")) {
            return false;
         } else {
            Optional<WechatBotService.SearchSession> sessionOptional = this.getValidSession(fromUser);
            return sessionOptional.isPresent() && !CollectionUtils.isEmpty(sessionOptional.get().getResults());
         }
      }
   }

   public List<WechatMessageParser.NewsArticle> handleNumberSelection(String content, String fromUser) {
      String normalized = content.trim();
      Optional<WechatBotService.SearchSession> sessionOptional = this.getValidSession(fromUser);
      if (!sessionOptional.isPresent()) {
         return Collections.emptyList();
      } else {
         WechatBotService.SearchSession session = sessionOptional.get();
         int index = Integer.parseInt(normalized) - 1;
         return index >= session.getResults().size() ? Collections.emptyList() : this.fetchNullbrDataWithImages(session.getResults().get(index));
      }
   }

   private String startSearchSession(String fromUser, String query, int page) {
      try {
         TmdbResponse response = this.tmdbService.searchDataTelegram(query, page);
         List<TmdbResponse.Result> results = Optional.ofNullable(response.getResults()).orElse(Collections.emptyList()).stream().limit(5L).toList();
         if (results.isEmpty()) {
            return "没有找到相关的影视信息，请尝试其它关键词。";
         } else {
            WechatBotService.SearchSession session = new WechatBotService.SearchSession();
            session.setQuery(query);
            session.setPage(response.getPage() == null ? page : response.getPage());
            session.setTotalPages(response.getTotalPages() == null ? page : response.getTotalPages());
            session.setResults(results);
            session.touch();
            this.saveSession(fromUser, session);
            return this.formatSearchList(session);
         }
      } catch (BizException var7) {
         log.warn("企业微信搜索受限: {}", var7.getMessage());
         return var7.getMessage();
      } catch (Exception var8) {
         log.error("企业微信搜索失败", (Throwable)var8);
         return "搜索时发生错误，请稍后重试。";
      }
   }

   private Optional<WechatBotService.SearchSession> getValidSession(String fromUser) {
      WechatBotService.SearchSession session = (WechatBotService.SearchSession)this.redisTemplate.opsForValue().get(this.buildKey(fromUser));
      return session == null ? Optional.empty() : Optional.of(session);
   }

   public List<WechatMessageParser.NewsArticle> searchMovieWithImages(String query, String fromUser) {
      return this.searchMovieWithImagesPage(query, fromUser, 1);
   }

   public List<WechatMessageParser.NewsArticle> searchMovieWithImagesPage(String query, String fromUser, int page) {
      List<WechatMessageParser.NewsArticle> articles = new ArrayList<>();

      try {
         TmdbResponse response = this.tmdbService.searchDataTelegram(query, page);
         List<TmdbResponse.Result> results = response.getResults();
         if (CollectionUtils.isEmpty(results)) {
            return articles;
         }

         int totalResultsCount = response.getTotalResults() == null ? results.size() : response.getTotalResults();
         int totalPages = response.getTotalPages() == null ? 1 : response.getTotalPages();
         int currentPage = response.getPage() == null ? page : response.getPage();
         if (fromUser != null) {
            List<TmdbResponse.Result> sessionResults = results.stream().limit(5L).toList();
            WechatBotService.SearchSession session = new WechatBotService.SearchSession();
            session.setQuery(query);
            session.setPage(currentPage);
            session.setTotalPages(totalPages);
            session.setTotalResults(totalResultsCount);
            session.setSessionType("TMDB");
            session.setResults(sessionResults);
            session.touch();
            this.saveSession(fromUser, session);
         }

         int displayCount = Math.min(results.size(), 5);

         for (int i = 0; i < displayCount; i++) {
            TmdbResponse.Result result = results.get(i);
            String name = StringUtils.hasText(result.getTitle()) ? result.getTitle() : result.getName();
            String release = StringUtils.hasText(result.getReleaseDate()) ? result.getReleaseDate() : result.getFirstAirDate();
            String year = StringUtils.hasText(release) && release.length() >= 4 ? release.substring(0, 4) : "";
            String rating = result.getVoteAverage() == null ? "" : String.valueOf(result.getVoteAverage());
            String fullOverview = result.getOverview();
            String title;
            String description;
            if (i == 0) {
               String pageInfo = String.format("总数：%d | 第 %d/%d 页", totalResultsCount, currentPage, totalPages);
               String navHint = totalPages > 1 ? "p：上一页 n：下一页 c：退出" : "c：退出";
               String textPrompt = String.format("%s (%s) ⭐%s\n%s\n%s", name, year, rating, pageInfo, navHint);
               this.sendTextPrompt(fromUser, textPrompt);
               if (displayCount == 1) {
                  title = String.format("%s (%s) ⭐%s", name, year, rating);
                  description = StringUtils.hasText(fullOverview) ? fullOverview : "";
               } else {
                  String shortOverview = "";
                  if (StringUtils.hasText(fullOverview)) {
                     shortOverview = fullOverview.length() > 50 ? fullOverview.substring(0, 47) + "..." : fullOverview;
                  }

                  title = String.format("1) %s (%s) ⭐%s\n%s", name, year, rating, shortOverview);
                  description = "";
               }
            } else {
               String shortOverview = "";
               if (StringUtils.hasText(fullOverview)) {
                  shortOverview = fullOverview.length() > 50 ? fullOverview.substring(0, 47) + "..." : fullOverview;
               }

               title = String.format("%d) %s (%s) ⭐%s\n%s", i + 1, name, year, rating, shortOverview);
               description = "";
            }

            String picUrl;
            if (displayCount != 1 && i != 0) {
               picUrl = StringUtils.hasText(result.getPosterPath())
                  ? "https://image.tmdb.org/t/p/w500" + result.getPosterPath()
                  : (StringUtils.hasText(result.getBackdropPath()) ? "https://image.tmdb.org/t/p/w780" + result.getBackdropPath() : "");
            } else {
               picUrl = StringUtils.hasText(result.getBackdropPath())
                  ? "https://image.tmdb.org/t/p/w780" + result.getBackdropPath()
                  : (StringUtils.hasText(result.getPosterPath()) ? "https://image.tmdb.org/t/p/w500" + result.getPosterPath() : "");
            }

            String tmdbUrl = String.format("https://www.themoviedb.org/%s/%d", "movie".equals(result.getMediaType()) ? "movie" : "tv", result.getId());
            articles.add(new WechatMessageParser.NewsArticle(title, description, picUrl, tmdbUrl));
         }
      } catch (Exception var24) {
         log.error("企业微信搜索失败", (Throwable)var24);
      }

      return articles;
   }

   public boolean isSearchCommand(String content) {
      if (!StringUtils.hasText(content)) {
         return false;
      } else {
         String normalized = content.trim();
         return normalized.startsWith("搜索") || normalized.toLowerCase().startsWith("search") || "WECHAT_BOT_SEARCH_SAMPLE".equals(normalized);
      }
   }

   private boolean isNextPageCommand(String content) {
      return "N".equalsIgnoreCase(content) || "NEXT".equalsIgnoreCase(content);
   }

   private boolean isPrevPageCommand(String content) {
      return "P".equalsIgnoreCase(content) || "PREV".equalsIgnoreCase(content) || "PREVIOUS".equalsIgnoreCase(content);
   }

   private boolean isCancelCommand(String content) {
      return "C".equalsIgnoreCase(content) || "CANCEL".equalsIgnoreCase(content);
   }

   public String extractSearchQuery(String content) {
      return "WECHAT_BOT_SEARCH_SAMPLE".equals(content) ? "银河护卫队" : content.replaceFirst("(?i)搜索|search", "").trim();
   }

   public boolean isEmbySearchCommand(String content) {
      if (!StringUtils.hasText(content)) {
         return false;
      } else {
         String normalized = content.trim();
         return normalized.matches("^(资源|搜库)\\d*.*") || "WECHAT_BOT_EMBY_SEARCH".equals(normalized);
      }
   }

   public int extractEmbyServerNumber(String content) {
      Matcher matcher = Pattern.compile("^(资源|搜库)(\\d+)?").matcher(content.trim());
      return matcher.find() && matcher.group(2) != null ? Integer.parseInt(matcher.group(2)) : 1;
   }

   private int extractServerNumberFromCommand(String content, String... prefixes) {
      for (String prefix : prefixes) {
         Matcher matcher = Pattern.compile("^" + prefix + "(\\d+)?").matcher(content.trim());
         if (matcher.find() && matcher.group(1) != null) {
            return Integer.parseInt(matcher.group(1));
         }
      }

      return 1;
   }

   private List<EmbyInfo> getAvailableServers() {
      return this.embyInfoService
         .lambdaQuery()
         .eq(EmbyInfo::getEnabled, Integer.valueOf(1))
         .eq(EmbyInfo::getStatus, Integer.valueOf(0))
         .eq(BaseEntity::getDelFlag, Integer.valueOf(0))
         .list();
   }

   private EmbyInfo selectServer(List<EmbyInfo> servers, int serverNumber) {
      if (CollectionUtils.isEmpty(servers)) {
         return null;
      } else {
         int index = Math.max(0, Math.min(serverNumber - 1, servers.size() - 1));
         return servers.get(index);
      }
   }

   private String buildServerListText() {
      List<EmbyInfo> servers = this.getAvailableServers();
      if (CollectionUtils.isEmpty(servers)) {
         return "⚠️ 暂无可用服务器";
      } else {
         StringBuilder sb = new StringBuilder("\ud83d\udce1 可用服务器：\n");

         for (int i = 0; i < servers.size(); i++) {
            sb.append(String.format("  %d. %s\n", i + 1, servers.get(i).getServerName()));
         }

         return sb.toString();
      }
   }

   public String extractEmbySearchQuery(String content) {
      return "WECHAT_BOT_EMBY_SEARCH".equals(content) ? "" : content.replaceFirst("^(资源|搜库)\\d*\\s*", "").trim();
   }

   public List<WechatMessageParser.NewsArticle> searchEmbyWithImages(String query, int serverNumber, String fromUser) {
      return this.searchEmbyWithImagesPage(query, serverNumber, 1, fromUser);
   }

   public List<WechatMessageParser.NewsArticle> searchEmbyWithImagesPage(String query, int serverNumber, int page, String fromUser) {
      try {
         List<EmbyInfo> servers = this.embyInfoService
            .lambdaQuery()
            .eq(EmbyInfo::getStatus, Integer.valueOf(0))
            .eq(BaseEntity::getDelFlag, Integer.valueOf(0))
            .list();
         if (CollectionUtils.isEmpty(servers)) {
            return null;
         } else {
            int index = Math.max(0, Math.min(serverNumber - 1, servers.size() - 1));
            EmbyInfo server = servers.get(index);
            String baseUrl = server.getEmbyUrl();
            int startIndex = (page - 1) * 5;
            String searchUrl = baseUrl + "/emby/Users/" + server.getCopyfromuserid() + "/Items";
            Map<String, Object> params = new HashMap<>();
            params.put("X-Emby-Token", server.getEmbyApikey());
            params.put("Recursive", "true");
            params.put("IncludeItemTypes", "Movie,Series");
            params.put("SearchTerm", query);
            params.put("Limit", String.valueOf(5));
            params.put("StartIndex", String.valueOf(startIndex));
            params.put("Fields", "Overview,CommunityRating,ProductionYear,ImageTags,BackdropImageTags");
            params.put("EnableTotalRecordCount", "true");
            String response = HttpRequest.get(searchUrl).form(params).timeout(4000).execute().body();
            JSONObject result = JSON.parseObject(response);
            JSONArray items = result.getJSONArray("Items");
            int totalRecordCount = result.getIntValue("TotalRecordCount");
            if (totalRecordCount == 0 && items != null && !items.isEmpty()) {
               int returnedCount = items.size();
               if (returnedCount >= 5) {
                  totalRecordCount = startIndex + returnedCount + 5;
               } else {
                  totalRecordCount = startIndex + returnedCount;
               }
            }

            int totalPages = Math.max(1, (int)Math.ceil((double)totalRecordCount / 5.0));
            List<WechatMessageParser.NewsArticle> articles = new ArrayList<>();
            if (items != null && !items.isEmpty()) {
               if (fromUser != null) {
                  WechatBotService.SearchSession session = new WechatBotService.SearchSession();
                  session.setQuery(query);
                  session.setPage(page);
                  session.setTotalPages(totalPages);
                  session.setTotalResults(totalRecordCount);
                  session.setSessionType("EMBY");
                  session.setServerNumber(serverNumber);
                  session.touch();
                  this.saveSession(fromUser, session);
               }

               int displayCount = Math.min(items.size(), 5);

               for (int i = 0; i < displayCount; i++) {
                  JSONObject item = items.getJSONObject(i);
                  String name = item.getString("Name");
                  Integer year = item.getInteger("ProductionYear");
                  Double rating = item.getDouble("CommunityRating");
                  String overview = item.getString("Overview");
                  String itemId = item.getString("Id");
                  String title;
                  String description;
                  if (i == 0) {
                     String pageInfo = String.format("总数：%d | 第 %d/%d 页", totalRecordCount, page, totalPages);
                     String navHint = totalPages > 1 ? "p：上一页 n：下一页 c：退出" : "c：退出";
                     String textPrompt = String.format("%s (%s) ⭐%s\n%s\n%s", name, year != null ? year : "", rating != null ? rating : "", pageInfo, navHint);
                     this.sendTextPrompt(fromUser, textPrompt);
                     if (displayCount == 1) {
                        title = String.format("%s (%s) ⭐%s", name, year != null ? year : "", rating != null ? rating : "");
                        description = StringUtils.hasText(overview) ? overview : "";
                     } else {
                        String shortOverview = "";
                        if (StringUtils.hasText(overview)) {
                           shortOverview = overview.length() > 50 ? overview.substring(0, 47) + "..." : overview;
                        }

                        title = String.format("1) %s (%s) ⭐%s\n%s", name, year != null ? year : "", rating != null ? rating : "", shortOverview);
                        description = "";
                     }
                  } else {
                     String shortOverview = "";
                     if (StringUtils.hasText(overview)) {
                        shortOverview = overview.length() > 50 ? overview.substring(0, 47) + "..." : overview;
                     }

                     title = String.format("%d) %s (%s) ⭐%s\n%s", i + 1, name, year != null ? year : "", rating != null ? rating : "", shortOverview);
                     description = "";
                  }

                  String picUrl = "";
                  JSONObject imageTags = item.getJSONObject("ImageTags");
                  JSONArray backdropTags = item.getJSONArray("BackdropImageTags");
                  if (displayCount != 1 && i != 0) {
                     if (imageTags != null && imageTags.containsKey("Primary")) {
                        picUrl = baseUrl + "/emby/Items/" + itemId + "/Images/Primary?maxWidth=500&quality=90";
                     } else if (backdropTags != null && !backdropTags.isEmpty()) {
                        picUrl = baseUrl + "/emby/Items/" + itemId + "/Images/Backdrop/0?maxWidth=780&quality=90";
                     }
                  } else if (backdropTags != null && !backdropTags.isEmpty()) {
                     picUrl = baseUrl + "/emby/Items/" + itemId + "/Images/Backdrop/0?maxWidth=780&quality=90";
                  } else if (imageTags != null && imageTags.containsKey("Primary")) {
                     picUrl = baseUrl + "/emby/Items/" + itemId + "/Images/Primary?maxWidth=500&quality=90";
                  }

                  if (StringUtils.hasText(picUrl)) {
                     picUrl = picUrl + "&X-Emby-Token=" + server.getEmbyApikey();
                  }

                  String detailUrl = baseUrl + "/web/index.html#!/item?id=" + itemId + "&serverId=" + server.getEmbyServerId();
                  articles.add(new WechatMessageParser.NewsArticle(title, description, picUrl, detailUrl));
               }

               return articles;
            } else {
               return articles;
            }
         }
      } catch (Exception var32) {
         log.error("企业微信搜索Emby资源失败", (Throwable)var32);
         return null;
      }
   }

   public String buildEmbySearchGuide() {
      List<EmbyInfo> servers = this.embyInfoService
         .lambdaQuery()
         .eq(EmbyInfo::getStatus, Integer.valueOf(0))
         .eq(BaseEntity::getDelFlag, Integer.valueOf(0))
         .list();
      StringBuilder sb = new StringBuilder();
      sb.append("\ud83c\udfac 搜索Emby资源库\n\n");
      if (CollectionUtils.isEmpty(servers)) {
         sb.append("⚠️ 暂无可用服务器\n");
      } else {
         sb.append("\ud83d\udce1 可用服务器：\n");

         for (int i = 0; i < servers.size(); i++) {
            sb.append(String.format("  %d. %s\n", i + 1, servers.get(i).getServerName()));
         }

         sb.append("\n");
         sb.append("命令格式：\n");
         sb.append("• 资源 <关键词> —— 搜索默认服务器\n");
         sb.append("• 资源1 <关键词> —— 搜索服务器1\n");
         sb.append("• 搜库2 <关键词> —— 搜索服务器2\n");
         sb.append("\n例如：资源1 星际穿越");
      }

      return sb.toString();
   }

   public String buildEmbySearchNotFound(String query) {
      return String.format("\ud83d\udd0d 未找到包含「%s」的资源哦～\n\n\ud83d\udca1 请尝试其他关键词，或检查拼写是否正确", query);
   }

   public String buildEmbyServerError() {
      return "\ud83d\ude3f 哎呀，服务器开小差了～\n\n\ud83d\udce1 无法连接到资源库，请稍后再试";
   }

   private String formatSearchList(WechatBotService.SearchSession session) {
      StringBuilder sb = new StringBuilder();
      sb.append(String.format("\ud83c\udfac 搜索「%s」\n第 %d / %d 页\n\n", session.getQuery(), session.getPage(), session.getTotalPages()));

      for (int i = 0; i < session.getResults().size(); i++) {
         TmdbResponse.Result result = session.getResults().get(i);
         String title = StringUtils.hasText(result.getTitle()) ? result.getTitle() : result.getName();
         String release = StringUtils.hasText(result.getReleaseDate()) ? result.getReleaseDate() : result.getFirstAirDate();
         String year = StringUtils.hasText(release) && release.length() >= 4 ? release.substring(0, 4) : "";
         String rating = result.getVoteAverage() == null ? "暂无" : String.valueOf(result.getVoteAverage());
         String type = "movie".equalsIgnoreCase(result.getMediaType()) ? "电影" : ("tv".equalsIgnoreCase(result.getMediaType()) ? "剧集" : "其他");
         sb.append(String.format("%d) %s (%s) ⭐%s | %s | TMDB: %d\n", i + 1, title, year, rating, type, result.getId()));
      }

      sb.append("\n发送数字获取 资源。\n发送 N 下一页，P 上一页，C 退出搜索。");
      return sb.toString();
   }

   public List<WechatMessageParser.NewsArticle> fetchNullbrDataWithImages(TmdbResponse.Result result) {
      List<WechatMessageParser.NewsArticle> articles = new ArrayList<>();

      try {
         String mediaType = StringUtils.hasText(result.getMediaType()) ? result.getMediaType() : "movie";
         MovieListResponse response = this.nullbrService.select(String.valueOf(result.getId()), mediaType);
         List<MovieListResponse.MovieList115DTO> items = Optional.ofNullable(response.getMovieList115DTOList()).orElse(Collections.emptyList());
         String title = StringUtils.hasText(result.getTitle()) ? result.getTitle() : result.getName();
         String release = StringUtils.hasText(result.getReleaseDate()) ? result.getReleaseDate() : result.getFirstAirDate();
         String year = StringUtils.hasText(release) && release.length() >= 4 ? release.substring(0, 4) : "";
         String picUrl = StringUtils.hasText(result.getBackdropPath())
            ? "https://image.tmdb.org/t/p/w780" + result.getBackdropPath()
            : (StringUtils.hasText(result.getPosterPath()) ? "https://image.tmdb.org/t/p/w500" + result.getPosterPath() : "");
         if (items.isEmpty()) {
            String notFoundTitle = String.format("❌ %s (%s)", title, year);
            String notFoundDesc = "未找到相关资源，请尝试其他影片";
            String tmdbUrl = String.format("https://www.themoviedb.org/%s/%d", "movie".equals(mediaType) ? "movie" : "tv", result.getId());
            articles.add(new WechatMessageParser.NewsArticle(notFoundTitle, notFoundDesc, picUrl, tmdbUrl));
            return articles;
         } else {
            String headerTitle = String.format("\ud83d\udce5 %s (%s)", title, year);
            String headerDesc = String.format("类型：%s | 共 %d 个资源\n点击查看 TMDB 详情", "movie".equals(mediaType) ? "电影" : "剧集", items.size());
            String tmdbUrl = String.format("https://www.themoviedb.org/%s/%d", "movie".equals(mediaType) ? "movie" : "tv", result.getId());
            articles.add(new WechatMessageParser.NewsArticle(headerTitle, headerDesc, picUrl, tmdbUrl));
            int count = 0;

            for (MovieListResponse.MovieList115DTO item : items.stream().limit(7L).toList()) {
               count++;
               StringBuilder itemTitleBuilder = new StringBuilder();
               itemTitleBuilder.append(String.format("%d. %s", count, item.getTitle()));
               StringBuilder specBuilder = new StringBuilder();
               if (StringUtils.hasText(item.getQuality())) {
                  specBuilder.append(item.getQuality());
               }

               if (StringUtils.hasText(item.getResolution())) {
                  if (specBuilder.length() > 0) {
                     specBuilder.append(" | ");
                  }

                  specBuilder.append(item.getResolution());
               }

               if (StringUtils.hasText(item.getSize())) {
                  if (specBuilder.length() > 0) {
                     specBuilder.append(" | ");
                  }

                  specBuilder.append(item.getSize());
               }

               if (specBuilder.length() > 0) {
                  itemTitleBuilder.append("\n").append((CharSequence)specBuilder);
               }

               String itemTitle = itemTitleBuilder.toString();
               String itemDesc = "";
               String itemPicUrl = StringUtils.hasText(result.getPosterPath()) ? "https://image.tmdb.org/t/p/w200" + result.getPosterPath() : "";
               articles.add(new WechatMessageParser.NewsArticle(itemTitle, itemDesc, itemPicUrl, item.getShareLink()));
            }

            return articles;
         }
      } catch (BizException var21) {
         log.warn("搜索失败：{}", var21.getMessage());
         articles.add(new WechatMessageParser.NewsArticle("❌ 搜索失败", var21.getMessage(), "", ""));
         return articles;
      } catch (Exception var22) {
         log.error("查询 资源异常", (Throwable)var22);
         articles.add(new WechatMessageParser.NewsArticle("❌ 查询失败", "查询资源时出错，请稍后重试", "", ""));
         return articles;
      }
   }

   private String fetchNullbrData(TmdbResponse.Result result) {
      try {
         String mediaType = StringUtils.hasText(result.getMediaType()) ? result.getMediaType() : "movie";
         MovieListResponse response = this.nullbrService.select(String.valueOf(result.getId()), mediaType);
         List<MovieListResponse.MovieList115DTO> items = Optional.ofNullable(response.getMovieList115DTOList()).orElse(Collections.emptyList());
         if (items.isEmpty()) {
            String name = StringUtils.hasText(result.getTitle()) ? result.getTitle() : result.getName();
            return String.format("未在 搜索 中找到 %s (TMDB %d) 的资源。", name, result.getId());
         } else {
            String title = StringUtils.hasText(result.getTitle()) ? result.getTitle() : result.getName();
            StringBuilder sb = new StringBuilder();
            sb.append(
               String.format(
                  "\ud83d\udce5 资源：%s\n类型：%s\nTMDB：%d\n页码：%d/%d\n\n",
                  title,
                  mediaType,
                  result.getId(),
                  response.getPage() == null ? 1 : response.getPage(),
                  response.getTotalPage() == null ? 1 : response.getTotalPage()
               )
            );
            int count = 1;

            for (MovieListResponse.MovieList115DTO item : items.stream().limit(5L).toList()) {
               sb.append(
                  String.format(
                     "%d. %s [%s/%s/%s]\n链接：%s\n\n",
                     count++,
                     item.getTitle(),
                     StringUtils.hasText(item.getQuality()) ? item.getQuality() : "-",
                     StringUtils.hasText(item.getResolution()) ? item.getResolution() : "-",
                     StringUtils.hasText(item.getSize()) ? item.getSize() : "-",
                     item.getShareLink()
                  )
               );
            }

            return sb.toString().trim();
         }
      } catch (BizException var10) {
         log.warn("搜索失败：{}", var10.getMessage());
         return var10.getMessage();
      } catch (Exception var11) {
         log.error("查询 资源异常", (Throwable)var11);
         return "查询 资源时出错，请稍后重试。";
      }
   }

   private String formatMovie(TmdbResponse.Result result) {
      String title = StringUtils.hasText(result.getTitle()) ? result.getTitle() : result.getName();
      String release = StringUtils.hasText(result.getReleaseDate()) ? result.getReleaseDate() : result.getFirstAirDate();
      String overview = result.getOverview();
      if (overview != null && overview.length() > 120) {
         overview = overview.substring(0, 117) + "...";
      }

      return String.format(
         "%s (%s)\n评分：%s\n简介：%s",
         title,
         StringUtils.hasText(release) ? release : "未知年份",
         result.getVoteAverage() == null ? "暂无" : result.getVoteAverage(),
         StringUtils.hasText(overview) ? overview : "暂无简介"
      );
   }

   private String handleCreateUser(String content) {
      int serverNumber = this.extractServerNumberFromCommand(content, "/createuser", "创建用户");
      String params = content.replaceFirst("(?i)/createuser\\d*|创建用户\\d*", "").trim();
      String[] args = params.split("\\s+", 3);
      if (args.length >= 3 && StringUtils.hasText(args[0])) {
         String userName = args[0];

         int days;
         try {
            days = Integer.parseInt(args[1]);
         } catch (NumberFormatException var12) {
            return "天数必须是数字，例如：创建用户1 test 30 备注";
         }

         if (days <= 0) {
            return "天数必须大于 0";
         } else {
            String remarks = args[2].trim();
            List<EmbyInfo> servers = this.getAvailableServers();
            EmbyInfo server = this.selectServer(servers, serverNumber);
            if (server == null) {
               return "\ud83d\ude3f 未找到可用服务器，请先在后台配置。";
            } else {
               try {
                  EmbyUserSave saveRequest = new EmbyUserSave();
                  saveRequest.setEmbyUserName(userName);
                  saveRequest.setDay(days);
                  saveRequest.setRemarks(remarks);
                  saveRequest.setEmbyInfoId(server.getId());
                  InsertUserResponse response = CompletableFuture.<InsertUserResponse>supplyAsync(() -> {
                     try {
                        return this.embyUserService.insertUser(saveRequest);
                     } catch (Exception var3x) {
                        throw new RuntimeException(var3x);
                     }
                  }).get(4L, TimeUnit.SECONDS);
                  return String.format(
                     "\ud83c\udf89 用户创建成功！\n\ud83d\udc64 用户名：%s\n\ud83d\udd11 密码：%s\n\ud83d\udcc5 有效期：%d 天\n\ud83d\udce1 服务器：%s",
                     userName,
                     response.getEmbyUserPassword(),
                     days,
                     server.getServerName()
                  );
               } catch (TimeoutException var13) {
                  log.error("企业微信创建用户超时", (Throwable)var13);
                  return "\ud83d\ude3f 服务器响应超时，请稍后再试。";
               } catch (BizException var14) {
                  return "❌ 创建失败：" + var14.getMessage();
               } catch (Exception var15) {
                  log.error("企业微信创建用户失败", (Throwable)var15);
                  return var15.getCause() instanceof BizException ? "❌ 创建失败：" + var15.getCause().getMessage() : "\ud83d\ude3f 创建用户失败，请稍后再试。";
               }
            }
         }
      } else {
         return "创建用户命令格式：\n创建用户[N] <用户名> <天数> <备注>\n示例：创建用户1 test 30 机器人创建\n\n" + this.buildServerListText();
      }
   }

   private String handleGenerateCards(String content) {
      int serverNumber = this.extractServerNumberFromCommand(content, "/generatecards", "生成卡密");
      String params = content.replaceFirst("(?i)/generatecards\\d*|生成卡密\\d*", "").trim();
      String[] args = params.split("\\s+");
      if (args.length < 2) {
         return "生成卡密命令格式：\n生成卡密[N] <数量> <天数>\n示例：生成卡密1 10 30\n\n" + this.buildServerListText();
      } else {
         int count;
         int day;
         try {
            count = Integer.parseInt(args[0]);
            day = Integer.parseInt(args[1]);
         } catch (NumberFormatException var13) {
            return "数量和天数都必须是数字，例如：生成卡密1 10 30";
         }

         if (count > 0 && day > 0) {
            List<EmbyInfo> servers = this.getAvailableServers();
            EmbyInfo server = this.selectServer(servers, serverNumber);
            if (server == null) {
               return "\ud83d\ude3f 未找到可用服务器，请先在后台配置。";
            } else {
               try {
                  Long serverId = server.getId();
                  List<String> cards = CompletableFuture.<List<String>>supplyAsync(
                        () -> this.cardSecurityManagementService.addCardSecurityManagementList(count, day, serverId, 0)
                     )
                     .get(4L, TimeUnit.SECONDS);
                  if (CollectionUtils.isEmpty(cards)) {
                     return "\ud83d\ude3f 卡密生成失败，请检查配置。";
                  } else {
                     StringBuilder sb = new StringBuilder();
                     sb.append(String.format("\ud83c\udfab 成功生成 %d 张卡密！\n\ud83d\udcc5 有效期：%d 天\n\ud83d\udce1 服务器：%s\n\n", count, day, server.getServerName()));

                     for (int i = 0; i < Math.min(cards.size(), 20); i++) {
                        sb.append(cards.get(i)).append("\n");
                     }

                     if (cards.size() > 20) {
                        sb.append("... 等共 ").append(cards.size()).append(" 张");
                     }

                     return sb.toString();
                  }
               } catch (TimeoutException var14) {
                  log.error("企业微信生成卡密超时", (Throwable)var14);
                  return "\ud83d\ude3f 服务器响应超时，请稍后再试。";
               } catch (BizException var15) {
                  return "❌ 生成失败：" + var15.getMessage();
               } catch (Exception var16) {
                  log.error("企业微信生成卡密失败", (Throwable)var16);
                  return var16.getCause() instanceof BizException ? "❌ 生成失败：" + var16.getCause().getMessage() : "\ud83d\ude3f 生成卡密失败，请稍后再试。";
               }
            }
         } else {
            return "数量和天数必须大于 0";
         }
      }
   }

   private String handleExtendUsers(String content) {
      int serverNumber = this.extractServerNumberFromCommand(content, "/extendusers", "批量延期");
      String params = content.replaceFirst("(?i)/extendusers\\d*|批量延期\\d*", "").trim();
      String[] args = params.split("\\s+");
      if (args.length >= 1 && StringUtils.hasText(args[0])) {
         Integer expiredRange = null;

         Integer extensionDay;
         try {
            extensionDay = Integer.parseInt(args[0]);
            if (args.length > 1) {
               expiredRange = Integer.parseInt(args[1]);
            }
         } catch (NumberFormatException var16) {
            return "参数必须为数字，例如：批量延期1 15 30";
         }

         if (extensionDay <= 0) {
            return "延期天数必须大于 0";
         } else {
            List<EmbyInfo> servers = this.getAvailableServers();
            EmbyInfo server = this.selectServer(servers, serverNumber);
            if (server == null) {
               return "\ud83d\ude3f 未找到可用服务器，请先在后台配置。";
            } else {
               try {
                  Long serverId = server.getId();
                  Integer range = expiredRange;
                  int updated = CompletableFuture.<Integer>supplyAsync(() -> this.embyUserService.extendExpiredUser(serverId, range, extensionDay))
                     .get(4L, TimeUnit.SECONDS);
                  return String.format("\ud83c\udf89 已为 %d 位用户延期 %d 天\n\ud83d\udce1 服务器：%s", updated, extensionDay, server.getServerName());
               } catch (TimeoutException var13) {
                  log.error("企业微信批量延期超时", (Throwable)var13);
                  return "\ud83d\ude3f 服务器响应超时，请稍后再试。";
               } catch (BizException var14) {
                  return "❌ 延期失败：" + var14.getMessage();
               } catch (Exception var15) {
                  log.error("企业微信批量延期失败", (Throwable)var15);
                  return var15.getCause() instanceof BizException ? "❌ 延期失败：" + var15.getCause().getMessage() : "\ud83d\ude3f 批量延期失败，请稍后再试。";
               }
            }
         }
      } else {
         return "批量延期命令格式：\n批量延期[N] <延期天数> [过期天数范围]\n示例：批量延期1 15 30\n\n" + this.buildServerListText();
      }
   }

   private String buildCreateUserGuide() {
      return String.join("\n", "\ud83d\udc64 创建用户", "", "命令格式：创建用户[N] <用户名> <天数> <备注>", "示例：创建用户1 test 30 机器人创建", "", this.buildServerListText());
   }

   private String buildExtendUserGuide() {
      return String.join("\n", "⏰ 批量延期", "", "命令格式：批量延期[N] <延期天数> [过期天数范围]", "示例：批量延期1 15 30", "（将服务器1中过期天数在30天内的用户延期15天）", "", this.buildServerListText());
   }

   private String buildGenerateCardsGuide() {
      return String.join("\n", "\ud83c\udfab 生成卡密", "", "命令格式：生成卡密[N] <数量> <天数>", "示例：生成卡密1 10 30", "（生成10张30天有效期的卡密）", "", this.buildServerListText());
   }

   private String buildStats() {
      try {
         UserStatsResponse stats = CompletableFuture.<UserStatsResponse>supplyAsync(() -> this.embyUserService.userStats()).get(4L, TimeUnit.SECONDS);
         return "\ud83d\udcca 当前用户统计：\n"
            + String.format(
               "✅ 活跃：%d 人\n⛔ 禁用：%d 人\n⏰ 即将过期：%d 人\n\ud83d\udccb 总计：%d 人",
               stats.getActiveUserCount(),
               stats.getInactiveUserCount(),
               stats.getExpiringSoonUserCount(),
               stats.getAllUserCount()
            );
      } catch (TimeoutException var2) {
         log.error("查询用户统计超时", (Throwable)var2);
         return "\ud83d\ude3f 服务器响应超时，请稍后再试。";
      } catch (Exception var3) {
         log.error("查询用户统计失败", (Throwable)var3);
         return "\ud83d\ude3f 无法获取用户统计，请检查配置。";
      }
   }

   public boolean isPrivilegedSender(String fromUser) {
      if (!StringUtils.hasText(fromUser)) {
         return false;
      } else {
         Set<String> allowedSenders = this.getAllowedSenders();
         return !allowedSenders.isEmpty() && allowedSenders.contains(fromUser.trim());
      }
   }

   public String buildForbiddenSenderMessage() {
      return "当前企业微信用户未加入机器人管理指令白名单，请联系管理员在通知渠道 wechatBot 配置 allowedSenders。";
   }

   private Set<String> getAllowedSenders() {
      WechatBotProperties properties = this.getProperties();
      if (properties != null && StringUtils.hasText(properties.getAllowedSenders())) {
         Set<String> allowedSenders = new LinkedHashSet<>();
         Arrays.stream(properties.getAllowedSenders().split("[,，;；\\s]+")).map(String::trim).filter(StringUtils::hasText).forEach(allowedSenders::add);
         return allowedSenders;
      } else {
         return Collections.emptySet();
      }
   }

   private void sendTextPrompt(String toUser, String content) {
      if (StringUtils.hasText(toUser)) {
         WechatBotProperties properties = this.getProperties();
         if (properties != null) {
            WechatBotUtils.sendMarkdownMessage(properties, content, toUser);
         }
      }
   }

   private void saveSession(String fromUser, WechatBotService.SearchSession session) {
      if (session != null) {
         this.redisTemplate.opsForValue().set(this.buildKey(fromUser), session, SEARCH_SESSION_TTL.toSeconds(), TimeUnit.SECONDS);
      }
   }

   private String buildKey(String fromUser) {
      return "wechat:search:session:" + fromUser;
   }

   private WechatBotProperties getProperties() {
      String channelValue = this.notifyChannelCacheLoaderUtils.getNotifyChannelValue("wechatBot");
      return !StringUtils.hasText(channelValue) ? null : JSON.parseObject(channelValue, WechatBotProperties.class);
   }

   @Generated
   public WechatBotService(
      final TmdbService tmdbService,
      final EmbyUserService embyUserService,
      final CardSecurityManagementService cardSecurityManagementService,
      final EmbyInfoService embyInfoService,
      final NullbrService nullbrService,
      final NotifyChannelCacheLoaderUtils notifyChannelCacheLoaderUtils
   ) {
      this.tmdbService = tmdbService;
      this.embyUserService = embyUserService;
      this.cardSecurityManagementService = cardSecurityManagementService;
      this.embyInfoService = embyInfoService;
      this.nullbrService = nullbrService;
      this.notifyChannelCacheLoaderUtils = notifyChannelCacheLoaderUtils;
   }

   public static class SearchSession implements Serializable {
      private String query;
      private int page;
      private int totalPages;
      private int totalResults;
      private String sessionType = "TMDB";
      private int serverNumber = 1;
      private List<TmdbResponse.Result> results = new ArrayList<>();
      private List<JSONObject> embyResults = new ArrayList<>();
      private String embyBaseUrl;
      private String embyApiKey;
      private String embyServerId;
      private Instant updatedAt = Instant.now();

      public void touch() {
         this.updatedAt = Instant.now();
      }

      @Generated
      public String getQuery() {
         return this.query;
      }

      @Generated
      public int getPage() {
         return this.page;
      }

      @Generated
      public int getTotalPages() {
         return this.totalPages;
      }

      @Generated
      public int getTotalResults() {
         return this.totalResults;
      }

      @Generated
      public String getSessionType() {
         return this.sessionType;
      }

      @Generated
      public int getServerNumber() {
         return this.serverNumber;
      }

      @Generated
      public List<TmdbResponse.Result> getResults() {
         return this.results;
      }

      @Generated
      public List<JSONObject> getEmbyResults() {
         return this.embyResults;
      }

      @Generated
      public String getEmbyBaseUrl() {
         return this.embyBaseUrl;
      }

      @Generated
      public String getEmbyApiKey() {
         return this.embyApiKey;
      }

      @Generated
      public String getEmbyServerId() {
         return this.embyServerId;
      }

      @Generated
      public Instant getUpdatedAt() {
         return this.updatedAt;
      }

      @Generated
      public void setQuery(final String query) {
         this.query = query;
      }

      @Generated
      public void setPage(final int page) {
         this.page = page;
      }

      @Generated
      public void setTotalPages(final int totalPages) {
         this.totalPages = totalPages;
      }

      @Generated
      public void setTotalResults(final int totalResults) {
         this.totalResults = totalResults;
      }

      @Generated
      public void setSessionType(final String sessionType) {
         this.sessionType = sessionType;
      }

      @Generated
      public void setServerNumber(final int serverNumber) {
         this.serverNumber = serverNumber;
      }

      @Generated
      public void setResults(final List<TmdbResponse.Result> results) {
         this.results = results;
      }

      @Generated
      public void setEmbyResults(final List<JSONObject> embyResults) {
         this.embyResults = embyResults;
      }

      @Generated
      public void setEmbyBaseUrl(final String embyBaseUrl) {
         this.embyBaseUrl = embyBaseUrl;
      }

      @Generated
      public void setEmbyApiKey(final String embyApiKey) {
         this.embyApiKey = embyApiKey;
      }

      @Generated
      public void setEmbyServerId(final String embyServerId) {
         this.embyServerId = embyServerId;
      }

      @Generated
      public void setUpdatedAt(final Instant updatedAt) {
         this.updatedAt = updatedAt;
      }

      @Generated
      @Override
      public boolean equals(final Object o) {
         if (o == this) {
            return true;
         } else if (!(o instanceof WechatBotService.SearchSession other)) {
            return false;
         } else if (!other.canEqual(this)) {
            return false;
         } else if (this.getPage() != other.getPage()) {
            return false;
         } else if (this.getTotalPages() != other.getTotalPages()) {
            return false;
         } else if (this.getTotalResults() != other.getTotalResults()) {
            return false;
         } else if (this.getServerNumber() != other.getServerNumber()) {
            return false;
         } else {
            Object this$query = this.getQuery();
            Object other$query = other.getQuery();
            if (this$query == null ? other$query == null : this$query.equals(other$query)) {
               Object this$sessionType = this.getSessionType();
               Object other$sessionType = other.getSessionType();
               if (this$sessionType == null ? other$sessionType == null : this$sessionType.equals(other$sessionType)) {
                  Object this$results = this.getResults();
                  Object other$results = other.getResults();
                  if (this$results == null ? other$results == null : this$results.equals(other$results)) {
                     Object this$embyResults = this.getEmbyResults();
                     Object other$embyResults = other.getEmbyResults();
                     if (this$embyResults == null ? other$embyResults == null : this$embyResults.equals(other$embyResults)) {
                        Object this$embyBaseUrl = this.getEmbyBaseUrl();
                        Object other$embyBaseUrl = other.getEmbyBaseUrl();
                        if (this$embyBaseUrl == null ? other$embyBaseUrl == null : this$embyBaseUrl.equals(other$embyBaseUrl)) {
                           Object this$embyApiKey = this.getEmbyApiKey();
                           Object other$embyApiKey = other.getEmbyApiKey();
                           if (this$embyApiKey == null ? other$embyApiKey == null : this$embyApiKey.equals(other$embyApiKey)) {
                              Object this$embyServerId = this.getEmbyServerId();
                              Object other$embyServerId = other.getEmbyServerId();
                              if (this$embyServerId == null ? other$embyServerId == null : this$embyServerId.equals(other$embyServerId)) {
                                 Object this$updatedAt = this.getUpdatedAt();
                                 Object other$updatedAt = other.getUpdatedAt();
                                 return this$updatedAt == null ? other$updatedAt == null : this$updatedAt.equals(other$updatedAt);
                              } else {
                                 return false;
                              }
                           } else {
                              return false;
                           }
                        } else {
                           return false;
                        }
                     } else {
                        return false;
                     }
                  } else {
                     return false;
                  }
               } else {
                  return false;
               }
            } else {
               return false;
            }
         }
      }

      @Generated
      protected boolean canEqual(final Object other) {
         return other instanceof WechatBotService.SearchSession;
      }

      @Generated
      @Override
      public int hashCode() {
         int PRIME = 59;
         int result = 1;
         result = result * 59 + this.getPage();
         result = result * 59 + this.getTotalPages();
         result = result * 59 + this.getTotalResults();
         result = result * 59 + this.getServerNumber();
         Object $query = this.getQuery();
         result = result * 59 + ($query == null ? 43 : $query.hashCode());
         Object $sessionType = this.getSessionType();
         result = result * 59 + ($sessionType == null ? 43 : $sessionType.hashCode());
         Object $results = this.getResults();
         result = result * 59 + ($results == null ? 43 : $results.hashCode());
         Object $embyResults = this.getEmbyResults();
         result = result * 59 + ($embyResults == null ? 43 : $embyResults.hashCode());
         Object $embyBaseUrl = this.getEmbyBaseUrl();
         result = result * 59 + ($embyBaseUrl == null ? 43 : $embyBaseUrl.hashCode());
         Object $embyApiKey = this.getEmbyApiKey();
         result = result * 59 + ($embyApiKey == null ? 43 : $embyApiKey.hashCode());
         Object $embyServerId = this.getEmbyServerId();
         result = result * 59 + ($embyServerId == null ? 43 : $embyServerId.hashCode());
         Object $updatedAt = this.getUpdatedAt();
         return result * 59 + ($updatedAt == null ? 43 : $updatedAt.hashCode());
      }

      @Generated
      @Override
      public String toString() {
         return "WechatBotService.SearchSession(query="
            + this.getQuery()
            + ", page="
            + this.getPage()
            + ", totalPages="
            + this.getTotalPages()
            + ", totalResults="
            + this.getTotalResults()
            + ", sessionType="
            + this.getSessionType()
            + ", serverNumber="
            + this.getServerNumber()
            + ", results="
            + this.getResults()
            + ", embyResults="
            + this.getEmbyResults()
            + ", embyBaseUrl="
            + this.getEmbyBaseUrl()
            + ", embyApiKey="
            + this.getEmbyApiKey()
            + ", embyServerId="
            + this.getEmbyServerId()
            + ", updatedAt="
            + this.getUpdatedAt()
            + ")";
      }

      @Generated
      public SearchSession() {
      }

      @Generated
      public SearchSession(
         final String query,
         final int page,
         final int totalPages,
         final int totalResults,
         final String sessionType,
         final int serverNumber,
         final List<TmdbResponse.Result> results,
         final List<JSONObject> embyResults,
         final String embyBaseUrl,
         final String embyApiKey,
         final String embyServerId,
         final Instant updatedAt
      ) {
         this.query = query;
         this.page = page;
         this.totalPages = totalPages;
         this.totalResults = totalResults;
         this.sessionType = sessionType;
         this.serverNumber = serverNumber;
         this.results = results;
         this.embyResults = embyResults;
         this.embyBaseUrl = embyBaseUrl;
         this.embyApiKey = embyApiKey;
         this.embyServerId = embyServerId;
         this.updatedAt = updatedAt;
      }
   }
}
