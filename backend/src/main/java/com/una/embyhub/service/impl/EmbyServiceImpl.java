package com.una.embyhub.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.resource.ResourceUtil;
import cn.hutool.core.io.unit.DataSizeUtil;
import cn.hutool.core.io.unit.DataUnit;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.http.Header;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.conditions.update.LambdaUpdateChainWrapper;
import com.diboot.core.util.BeanUtils;
import com.una.embyhub.config.common.constants.NotifyMessageType;
import com.una.embyhub.config.common.utils.ConfigCacheLoaderUtils;
import com.una.embyhub.config.common.utils.EmbyClientAdminUserUtils;
import com.una.embyhub.config.common.utils.EmbyInfoCacheManagerUtils;
import com.una.embyhub.config.common.utils.EmbyStudioAliasUtils;
import com.una.embyhub.config.common.utils.IpAddressUtils;
import com.una.embyhub.config.common.utils.MovieFormatTranslatorUtils;
import com.una.embyhub.config.common.utils.NotifyUtils;
import com.una.embyhub.config.common.utils.PlaybackUtils;
import com.una.embyhub.config.common.utils.TimeStringPercentageCalculatorUtils;
import com.una.embyhub.model.dto.request.emby.GetItemsRequest;
import com.una.embyhub.model.dto.request.emby.GetShowsByIdSeasonsRequestDto;
import com.una.embyhub.model.dto.request.emby.LibraryNewRequest;
import com.una.embyhub.model.dto.request.emby.PublisherSearchRequest;
import com.una.embyhub.model.dto.request.telegram.SendMessageRequest;
import com.una.embyhub.model.dto.request.telegram.SendPhotoRequest;
import com.una.embyhub.model.dto.response.emby.EmbySettingsResponse;
import com.una.embyhub.model.dto.response.emby.EmbyStudioPresetResponse;
import com.una.embyhub.model.dto.response.emby.GetEmbyUrlResponse;
import com.una.embyhub.model.dto.response.emby.GetEpisodesByIdResponse;
import com.una.embyhub.model.dto.response.emby.NowPlayingGroupedResponse;
import com.una.embyhub.model.dto.response.emby.PublisherSearchResponse;
import com.una.embyhub.model.dto.response.emby.QueryResultBaseItemResponse;
import com.una.embyhub.model.dto.response.emby.SessionSessionInfoResponse;
import com.una.embyhub.model.dto.response.emby.StatsResponse;
import com.una.embyhub.model.dto.response.emby.TmdbSearchResponse;
import com.una.embyhub.model.entity.EmbyClientFilterRecord;
import com.una.embyhub.model.entity.EmbyInfo;
import com.una.embyhub.model.entity.EmbyNotifyData;
import com.una.embyhub.model.entity.EmbyNotifyDataDetails;
import com.una.embyhub.model.entity.EmbyUser;
import com.una.embyhub.model.entity.MediaMain;
import com.una.embyhub.model.entity.MediaViewDetail;
import com.una.embyhub.model.entity.RequestList;
import com.una.embyhub.service.EmbyBlockKeywordService;
import com.una.embyhub.service.EmbyClientFilterExclusionService;
import com.una.embyhub.service.EmbyClientFilterRecordService;
import com.una.embyhub.service.EmbyInfoService;
import com.una.embyhub.service.EmbyNotifyDataDetailsService;
import com.una.embyhub.service.EmbyNotifyDataService;
import com.una.embyhub.service.EmbyRegionBlockRuleService;
import com.una.embyhub.service.EmbyService;
import com.una.embyhub.service.EmbyStudioCacheService;
import com.una.embyhub.service.EmbyUserService;
import com.una.embyhub.service.MediaMainService;
import com.una.embyhub.service.MediaViewDetailService;
import com.una.embyhub.service.RequestListService;
import com.una.embyhub.service.TmdbService;
import com.una.embyhub.util.MovieCardRenderer;
import embyclient.ApiClient;
import embyclient.ApiException;
import embyclient.api.ItemsServiceApi;
import embyclient.api.LibraryServiceApi;
import embyclient.api.SessionsServiceApi;
import embyclient.api.TvShowsServiceApi;
import embyclient.api.UserServiceApi;
import embyclient.model.ItemCounts;
import embyclient.model.ProviderIdDictionary;
import embyclient.model.QueryResultBaseItemDto;
import embyclient.model.SessionSessionInfo;
import embyclient.model.UserDto;
import embyclient.model.UserPolicy;
import info.movito.themoviedbapi.model.core.multi.Multi;
import info.movito.themoviedbapi.model.core.multi.MultiMovie;
import info.movito.themoviedbapi.model.core.multi.MultiTvSeries;
import info.movito.themoviedbapi.model.find.FindResults;
import info.movito.themoviedbapi.model.movies.MovieDb;
import info.movito.themoviedbapi.model.tv.series.TvSeriesDb;
import info.movito.themoviedbapi.tools.TmdbException;
import info.movito.themoviedbapi.tools.model.time.ExternalSource;
import jakarta.annotation.PreDestroy;
import jakarta.servlet.http.HttpServletRequest;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.ThreadPoolExecutor.CallerRunsPolicy;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;
import java.util.stream.Collectors;
import lombok.Generated;
import net.dreamlu.mica.ip2region.core.Ip2regionSearcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Service
@Transactional(
   rollbackFor = {Exception.class}
)
public class EmbyServiceImpl implements EmbyService {
   @Generated
   private static final Logger log = LoggerFactory.getLogger(EmbyServiceImpl.class);
   private static final Pattern TMDB_MOVIE_URL_PATTERN = Pattern.compile("/movie/(\\d+)");
   private static final Pattern TMDB_TV_URL_PATTERN = Pattern.compile("/tv/(\\d+)");
   private static final Pattern TRAILING_NUMBER_PATTERN = Pattern.compile("(\\d+)(?:\\D*)$");
   private static final String NOTIFY_MEDIA_DETAIL_FIELDS = "MediaSources,MediaStreams,Width,Height,Path,Size,Type,Name";
   @Autowired
   private RequestListService requestListService;
   @Autowired
   private TmdbService tmdbService;
   @Value("${tmdb.imageUrl}")
   private String imageUrl;
   @Autowired
   private EmbyNotifyDataService embyNotifyDataService;
   @Autowired
   private EmbyNotifyDataDetailsService embyNotifyDataDetailsService;
   @Autowired
   private ConfigCacheLoaderUtils configCacheLoaderUtils;
   @Autowired
   private EmbyInfoCacheManagerUtils embyInfoCacheManager;
   @Autowired
   private EmbyInfoService embyInfoService;
   @Autowired
   private RedisTemplate<String, Object> redisTemplate;
   @Autowired
   private EmbyStudioCacheService embyStudioCacheService;
   private static final String TMDB_SERIES_URL = "https://www.themoviedb.org/tv/";
   private static final AtomicInteger PLAYBACK_IMAGE_THREAD_ID = new AtomicInteger(1);
   private static final Set<String> CLIENT_FILTER_EVENTS = Set.of(
      "user.authenticated", "user.authenticationfailed", "playback.start", "playback.progress", "playback.stop", "session.start"
   );
   private static final int CLIENT_FILTER_REQUEST_TIMEOUT_MS = 8000;
   private static final int REGION_FILTER_DEDUPLICATION_MINUTES = 5;
   private static final Map<String, List<String>> FEATURED_STUDIO_ALIASES = EmbyStudioAliasUtils.featuredStudioAliases();
   @Autowired
   private NotifyUtils notifyUtils;
   @Autowired
   private Ip2regionSearcher searchSearcher;
   @Autowired
   private MediaMainService mediaMainService;
   @Autowired
   private EmbyUserService embyUserService;
   @Autowired
   private EmbyBlockKeywordService embyBlockKeywordService;
   @Autowired
   private EmbyClientFilterRecordService embyClientFilterRecordService;
   @Autowired
   private EmbyClientFilterExclusionService embyClientFilterExclusionService;
   @Autowired
   private EmbyRegionBlockRuleService embyRegionBlockRuleService;
   @Autowired
   private MediaViewDetailService mediaViewDetailService;
   private final ExecutorService playbackImageExecutor = new ThreadPoolExecutor(
      2, Math.max(4, Math.min(Runtime.getRuntime().availableProcessors(), 8)), 60L, TimeUnit.SECONDS, new LinkedBlockingQueue<>(120), runnable -> {
         Thread thread = new Thread(runnable);
         thread.setName("playback-image-" + PLAYBACK_IMAGE_THREAD_ID.getAndIncrement());
         thread.setDaemon(true);
         return thread;
      }, new CallerRunsPolicy()
   );

   private EmbyUser getCurrentUser() {
      return (EmbyUser)StpUtil.getSession().get("user");
   }

   private EmbyInfoCacheManagerUtils.EmbyServerConfig getCurrentServerConfig() {
      return this.embyInfoCacheManager.getRequiredConfig(this.getCurrentUser());
   }

   private ApiClient buildApiClient() {
      return this.buildApiClient(this.getCurrentServerConfig());
   }

   private ApiClient buildApiClient(EmbyInfoCacheManagerUtils.EmbyServerConfig config) {
      ApiClient apiClient = new ApiClient();
      this.embyInfoCacheManager.applyTo(apiClient, config);
      return apiClient;
   }

   private String getServerUrl() {
      return this.getCurrentServerConfig().url();
   }

   private String getApiKey() {
      return this.getCurrentServerConfig().apiKey();
   }

   private String getCopyfromuserid() {
      return this.getCurrentServerConfig().copyfromuserid();
   }

   @Override
   public QueryResultBaseItemResponse getItems(GetItemsRequest getItemsRequest) throws ApiException {
      EmbyInfoCacheManagerUtils.EmbyServerConfig serverConfig = this.resolveServerConfig(getItemsRequest);
      return this.getItems(getItemsRequest, serverConfig);
   }

   private EmbyInfoCacheManagerUtils.EmbyServerConfig resolveServerConfig(GetItemsRequest getItemsRequest) {
      return StringUtils.hasText(getItemsRequest.getServerId())
         ? this.embyInfoCacheManager.getRequiredConfigByServerId(getItemsRequest.getServerId())
         : this.getCurrentServerConfig();
   }

   private EmbyInfoCacheManagerUtils.EmbyServerConfig resolveServerConfig(Long embyInfoId) {
      return embyInfoId != null ? this.embyInfoCacheManager.getRequiredConfigById(embyInfoId) : this.getCurrentServerConfig();
   }

   private QueryResultBaseItemResponse getItems(GetItemsRequest getItemsRequest, EmbyInfoCacheManagerUtils.EmbyServerConfig serverConfig) throws ApiException {
      getItemsRequest.setStartIndex(getItemsRequest.getStartIndex() == 1 ? 0 : (getItemsRequest.getStartIndex() - 1) * getItemsRequest.getLimit());
      ItemsServiceApi itemsServiceApi = new ItemsServiceApi(this.buildApiClient(serverConfig));
      String artistType = null;
      String maxOfficialRating = null;
      Boolean hasThemeSong = null;
      Boolean hasThemeVideo = null;
      Boolean hasSubtitles = null;
      Boolean hasSpecialFeature = null;
      Boolean hasTrailer = null;
      Boolean isSpecialSeason = null;
      String adjacentTo = null;
      String startItemId = null;
      Integer minIndexNumber = null;
      OffsetDateTime minStartDate = null;
      OffsetDateTime maxStartDate = null;
      OffsetDateTime minEndDate = null;
      OffsetDateTime maxEndDate = null;
      Integer minPlayers = null;
      Integer maxPlayers = null;
      Integer parentIndexNumber = null;
      Boolean hasParentalRating = null;
      Boolean isHD = null;
      Boolean isUnaired = null;
      Double minCommunityRating = null;
      Double minCriticRating = null;
      Integer airedDuringSeason = null;
      OffsetDateTime minPremiereDate = null;
      OffsetDateTime minDateLastSaved = null;
      OffsetDateTime minDateLastSavedForUser = null;
      OffsetDateTime maxPremiereDate = null;
      Boolean hasOverview = null;
      Boolean hasImdbId = null;
      Boolean hasTmdbId = null;
      Boolean hasTvdbId = null;
      String excludeItemIds = null;
      Integer startIndex = getItemsRequest.getStartIndex();
      Integer limit = getItemsRequest.getLimit();
      Boolean recursive = true;
      String searchTerm = null;
      String sortOrder = "Descending";
      String parentId = null;
      String fields = "BasicSyncInfo,CanDelete,CanDownload,PrimaryImageAspectRatio,ProductionYear,Status,EndDate ,DateCreated ,overview ,CommunityRating ,PremiereDate ,SeasonCount ,SeriesName ,People ,ProviderIds ,genres";
      String excludeItemTypes = null;
      String includeItemTypes = getItemsRequest.getIncludeItemTypes();
      String anyProviderIdEquals = null;
      String filters = null;
      Boolean isFavorite = null;
      Boolean isMovie = null;
      Boolean isSeries = null;
      Boolean isFolder = null;
      Boolean isNews = null;
      Boolean isKids = null;
      Boolean isSports = null;
      Boolean isNew = null;
      Boolean isPremiere = null;
      Boolean isNewOrPremiere = null;
      Boolean isRepeat = null;
      Boolean projectToMedia = null;
      String mediaTypes = null;
      String imageTypes = null;
      String sortBy = "DateCreated,SortName";
      Boolean isPlayed = null;
      String genres = getItemsRequest.getGenres();
      String officialRatings = null;
      String tags = null;
      String excludeTags = null;
      String years = null;
      Boolean enableImages = null;
      Boolean enableUserData = null;
      Integer imageTypeLimit = 12;
      String enableImageTypes = null;
      String person = null;
      String personIds = null;
      String personTypes = null;
      String studios = getItemsRequest.getStudios();
      String studioIds = getItemsRequest.getStudioIds();
      String artists = null;
      String artistIds = null;
      String albums = null;
      String ids = StringUtils.hasText(getItemsRequest.getIds()) ? getItemsRequest.getIds() : null;
      String videoTypes = null;
      String containers = null;
      String audioCodecs = null;
      String audioLayouts = null;
      String videoCodecs = null;
      String extendedVideoTypes = null;
      String subtitleCodecs = null;
      String path = null;
      String userId = null;
      String minOfficialRating = null;
      Boolean isLocked = null;
      Boolean isPlaceHolder = null;
      Boolean hasOfficialRating = null;
      Boolean groupItemsIntoCollections = null;
      Boolean is3D = null;
      String seriesStatus = null;
      String nameStartsWithOrGreater = null;
      String artistStartsWithOrGreater = null;
      String albumArtistStartsWithOrGreater = null;
      String nameStartsWith = null;
      String nameLessThan = null;
      QueryResultBaseItemDto queryResultBaseItemDto = itemsServiceApi.getItems(
         artistType,
         maxOfficialRating,
         hasThemeSong,
         hasThemeVideo,
         hasSubtitles,
         hasSpecialFeature,
         hasTrailer,
         isSpecialSeason,
         adjacentTo,
         startItemId,
         minIndexNumber,
         minStartDate,
         maxStartDate,
         minEndDate,
         maxEndDate,
         minPlayers,
         maxPlayers,
         parentIndexNumber,
         hasParentalRating,
         isHD,
         isUnaired,
         minCommunityRating,
         minCriticRating,
         airedDuringSeason,
         minPremiereDate,
         minDateLastSaved,
         minDateLastSavedForUser,
         maxPremiereDate,
         hasOverview,
         hasImdbId,
         hasTmdbId,
         hasTvdbId,
         excludeItemIds,
         startIndex,
         limit,
         recursive,
         searchTerm,
         sortOrder,
         parentId,
         fields,
         excludeItemTypes,
         includeItemTypes,
         anyProviderIdEquals,
         filters,
         isFavorite,
         isMovie,
         isSeries,
         isFolder,
         isNews,
         isKids,
         isSports,
         isNew,
         isPremiere,
         isNewOrPremiere,
         isRepeat,
         projectToMedia,
         mediaTypes,
         imageTypes,
         sortBy,
         isPlayed,
         genres,
         officialRatings,
         tags,
         excludeTags,
         years,
         enableImages,
         enableUserData,
         imageTypeLimit,
         enableImageTypes,
         person,
         personIds,
         personTypes,
         studios,
         studioIds,
         artists,
         artistIds,
         albums,
         ids,
         videoTypes,
         containers,
         audioCodecs,
         audioLayouts,
         videoCodecs,
         extendedVideoTypes,
         subtitleCodecs,
         path,
         userId,
         minOfficialRating,
         isLocked,
         isPlaceHolder,
         hasOfficialRating,
         groupItemsIntoCollections,
         is3D,
         seriesStatus,
         nameStartsWithOrGreater,
         artistStartsWithOrGreater,
         albumArtistStartsWithOrGreater,
         nameStartsWith,
         nameLessThan
      );
      QueryResultBaseItemResponse queryResultBaseItemResponse = new QueryResultBaseItemResponse();
      String serverUrl = serverConfig != null ? serverConfig.url() : this.getServerUrl();
      queryResultBaseItemResponse.setTotalRecordCount(queryResultBaseItemDto.getTotalRecordCount());
      queryResultBaseItemResponse.setItems(BeanUtils.convertList(queryResultBaseItemDto.getItems(), QueryResultBaseItemResponse.ItemsDTO.class));
      queryResultBaseItemResponse.getItems()
         .forEach(
            item -> {
               item.setImageUrl(
                  serverUrl
                     + "Items/"
                     + (!"Movie".equals(item.getType()) && !"Series".equals(item.getType()) ? item.getParentBackdropItemId() : item.getId())
                     + "/Images/Primary?maxHeight=600&maxWidth=400&quality=90"
               );
               item.setEmbyItemUrl(serverConfig != null ? serverConfig.url() : this.getEmbyUrl(item.getId(), item.getServerId()).getEmbyItemUrl());
               item.getPeople()
                  .forEach(
                     people -> {
                        if ("Movie".equals(item.getType())) {
                           people.setPrimaryImageTag(
                              serverUrl
                                 + "Items/"
                                 + people.getId()
                                 + "/Images/Primary?maxHeight=600&maxWidth=400&tag="
                                 + people.getPrimaryImageTag()
                                 + "&quality=90"
                           );
                        }

                        if ("Season".equals(item.getType())) {
                           people.setPrimaryImageTag(
                              serverUrl
                                 + "Items/"
                                 + people.getId()
                                 + "/Images/Primary?maxHeight=600&maxWidth=400&tag="
                                 + people.getPrimaryImageTag()
                                 + "&quality=90"
                           );
                        }

                        if ("Series".equals(item.getType())) {
                           people.setPrimaryImageTag(
                              serverUrl
                                 + "Items/"
                                 + people.getId()
                                 + "/Images/Primary?maxHeight=600&maxWidth=400&tag="
                                 + people.getPrimaryImageTag()
                                 + "&quality=90"
                           );
                        }
                     }
                  );
               if ("Movie".equals(item.getType())) {
                  item.setFilmTitle(item.getName());
                  item.setPrimaryImageAspectRatioCount(String.valueOf(item.getCommunityRating()));
                  if (!CollectionUtils.isEmpty(item.getBackdropImageTags())) {
                     item.setBackdropImageUrl(
                        serverUrl + "Items/" + item.getId() + "/Images/Backdrop/0?tag=" + item.getBackdropImageTags().get(0) + "&maxWidth=1280&quality=70"
                     );
                  }
               } else {
                  item.setFilmTitle(StringUtils.hasText(item.getSeriesName()) ? item.getSeriesName() + " " + item.getName() : item.getName());
                  if (item.getPrimaryImageAspectRatio() != null) {
                     item.setPrimaryImageAspectRatioCount(NumberUtil.roundStr(item.getPrimaryImageAspectRatio() * 10.0, 1));
                  }

                  if (!CollectionUtils.isEmpty(item.getParentBackdropImageTags())) {
                     item.setBackdropImageUrl(
                        serverUrl
                           + "Items/"
                           + item.getParentBackdropItemId()
                           + "/Images/Backdrop/0?tag="
                           + item.getParentBackdropImageTags().get(0)
                           + "&maxWidth=1280&quality=70"
                     );
                  }

                  if (!CollectionUtils.isEmpty(item.getBackdropImageTags())) {
                     item.setBackdropImageUrl(
                        serverUrl + "Items/" + item.getId() + "/Images/Backdrop/0?tag=" + item.getBackdropImageTags().get(0) + "&maxWidth=1280&quality=70"
                     );
                  }
               }
            }
         );
      return queryResultBaseItemResponse;
   }

   @Override
   public StatsResponse stats() {
      EmbyUser embyUser = this.getCurrentUser();

      try {
         if (embyUser != null && embyUser.getIsAdmin() != null && embyUser.getIsAdmin() == 1) {
            return this.aggregateAdminStats();
         } else {
            LibraryServiceApi libraryServiceApi = new LibraryServiceApi(this.buildApiClient());
            ItemCounts itemsCounts = libraryServiceApi.getItemsCounts(embyUser != null ? embyUser.getEmbyUserId() : this.getCopyfromuserid(), false);
            StatsResponse statsResponse = new StatsResponse();
            statsResponse.setMovieCount(itemsCounts.getMovieCount());
            statsResponse.setSeriesCount(itemsCounts.getSeriesCount());
            statsResponse.setEpisodeCount(itemsCounts.getEpisodeCount());
            statsResponse.setMusicCount(itemsCounts.getSongCount());
            return statsResponse;
         }
      } catch (ApiException var5) {
         log.error("获取资源统计失败：{}", var5.getResponseBody());
         return new StatsResponse();
      }
   }

   private StatsResponse aggregateAdminStats() throws ApiException {
      List<EmbyInfoCacheManagerUtils.EmbyServerConfig> adminConfigs = this.embyInfoCacheManager.getAdminConfigs();
      if (adminConfigs != null && !adminConfigs.isEmpty()) {
         StatsResponse aggregated = new StatsResponse();

         for (EmbyInfoCacheManagerUtils.EmbyServerConfig config : adminConfigs) {
            LibraryServiceApi libraryServiceApi = new LibraryServiceApi(this.buildApiClient(config));
            String queryUserId = StringUtils.hasText(config.adminQueryUserid()) ? config.adminQueryUserid() : config.copyfromuserid();
            ItemCounts itemsCounts = libraryServiceApi.getItemsCounts(queryUserId, false);
            aggregated.setMovieCount(Optional.ofNullable(aggregated.getMovieCount()).orElse(0) + Optional.ofNullable(itemsCounts.getMovieCount()).orElse(0));
            aggregated.setSeriesCount(Optional.ofNullable(aggregated.getSeriesCount()).orElse(0) + Optional.ofNullable(itemsCounts.getSeriesCount()).orElse(0));
            aggregated.setEpisodeCount(
               Optional.ofNullable(aggregated.getEpisodeCount()).orElse(0) + Optional.ofNullable(itemsCounts.getEpisodeCount()).orElse(0)
            );
            aggregated.setMusicCount(Optional.ofNullable(aggregated.getMusicCount()).orElse(0) + Optional.ofNullable(itemsCounts.getSongCount()).orElse(0));
         }

         return aggregated;
      } else {
         LibraryServiceApi libraryServiceApi = new LibraryServiceApi(this.buildApiClient());
         ItemCounts itemsCounts = libraryServiceApi.getItemsCounts(this.getCopyfromuserid(), false);
         return this.buildStatsResponse(itemsCounts);
      }
   }

   private StatsResponse buildStatsResponse(ItemCounts itemsCounts) {
      StatsResponse statsResponse = new StatsResponse();
      statsResponse.setMovieCount(itemsCounts.getMovieCount());
      statsResponse.setSeriesCount(itemsCounts.getSeriesCount());
      statsResponse.setEpisodeCount(itemsCounts.getEpisodeCount());
      statsResponse.setMusicCount(itemsCounts.getSongCount());
      return statsResponse;
   }

   @Override
   public void notifier(JSONObject data) {
      LibraryNewRequest libraryNewRequest = JSONObject.parseObject(JSONObject.toJSONString(data), LibraryNewRequest.class);
      log.info("emby webhook 请求对象：{}", data);
      String event = data.getString("Event");
      log.info("emby webhook 通知接口：{}", event);
      this.clientFilterWebhook(data);
      EmbyInfo payloadEmbyInfo = this.resolveWebhookEmbyInfo(data);
      Long embyInfoId = payloadEmbyInfo != null ? payloadEmbyInfo.getId() : this.resolveWebhookEmbyInfoId(data);
      EmbyInfoCacheManagerUtils.EmbyServerConfig serverConfig = this.resolveWebhookServerConfig(payloadEmbyInfo, embyInfoId);
      String serverUrl = this.buildServerUrl(payloadEmbyInfo);
      String serverName = this.resolveServerName(payloadEmbyInfo, embyInfoId, serverConfig);
      if (!StringUtils.hasText(serverName)) {
         JSONObject serverObj = data.getJSONObject("Server");
         if (serverObj != null) {
            serverName = serverObj.getString("Name");
         }
      }

      if (!StringUtils.hasText(serverUrl) && embyInfoId != null) {
         EmbyInfo embyInfo = this.embyInfoService.getById(embyInfoId);
         serverUrl = this.buildServerUrl(embyInfo);
         if (!StringUtils.hasText(serverName) && embyInfo != null) {
            serverName = embyInfo.getServerName();
         }
      }

      String embyUserAuthenticationNotify = this.configCacheLoaderUtils.getConfigValue("emby_user_authentication_notify");
      if (StringUtils.hasText(embyUserAuthenticationNotify) && ("user.authenticationfailed".equals(event) || "user.authenticated".equals(event))) {
         String userName = "未知用户";
         String remoteEndPoint = "未知";
         String client = "未知";
         String deviceName = "未知";
         if ("user.authenticated".equals(event)) {
            JSONObject userObj = data.getJSONObject("User");
            JSONObject sessionObj = data.getJSONObject("Session");
            if (userObj != null) {
               userName = userObj.getString("Name");
            }

            if (sessionObj != null) {
               remoteEndPoint = sessionObj.getString("RemoteEndPoint");
               client = sessionObj.getString("Client");
               deviceName = sessionObj.getString("DeviceName");
            }
         } else {
            String title = data.getString("Title");
            if (StringUtils.hasText(title)) {
               Matcher matcher = Pattern.compile("来自\\s*(.*?)\\s*的").matcher(title);
               if (matcher.find()) {
                  userName = matcher.group(1);
               }
            }

            String description = data.getString("Description");
            if (StringUtils.hasText(description)) {
               String[] lines = description.split("\\n");

               for (int i = lines.length - 1; i >= 0; i--) {
                  String line = lines[i].trim();
                  if (IpAddressUtils.parseLiteral(line).isPresent()) {
                     remoteEndPoint = line;
                     break;
                  }
               }
            }

            JSONObject deviceInfo = data.getJSONObject("DeviceInfo");
            if (deviceInfo != null) {
               client = deviceInfo.getString("AppName");
               deviceName = deviceInfo.getString("Name");
            }
         }

         Map<String, String> extras = new HashMap<>();
         extras.put("userName", userName);
         extras.put("client", client);
         extras.put("device", deviceName);
         extras.put("clientInfo", this.buildClientInfo(client, deviceName));
         extras.put("loginTime", DateUtil.formatDateTime(new Date()));
         if (StringUtils.hasText(remoteEndPoint) && !"未知".equals(remoteEndPoint)) {
            String address = IpAddressUtils.safeAddressAndIsp(this.searchSearcher, remoteEndPoint);
            extras.put("ipAddress", StringUtils.hasText(address) ? remoteEndPoint + " " + address : remoteEndPoint);
         } else {
            extras.put("ipAddress", "未知");
         }

         SendMessageRequest sendMessageRequest = new SendMessageRequest();
         sendMessageRequest.setParseMode("Markdown");
         sendMessageRequest.setServerUrl(serverUrl);
         sendMessageRequest.setServerName(serverName);
         sendMessageRequest.setExtraVariables(extras);
         String templateCode;
         if ("user.authenticationfailed".equals(event)) {
            templateCode = "auth_failed";
            sendMessageRequest.setName("Emby用户(" + userName + ")登录失败提醒");
         } else {
            templateCode = "auth_success";
            sendMessageRequest.setName("Emby用户(" + userName + ")登录成功提醒");
         }

         this.notifyUtils.sendMultiChannel(sendMessageRequest, templateCode, false, "telegram", "wechat", "wechatBot", "dingding", "messagepush");
      }

      String embyPlaybackNotify = this.configCacheLoaderUtils.getConfigValue("emby_playback_notify");
      if (StringUtils.hasText(embyPlaybackNotify)
         && ("playback.start".equals(event) || "playback.pause".equals(event) || "playback.unpause".equals(event) || "playback.stop".equals(event))) {
         String itemId = "";
         String tmdbId = "";
         String imgUrl = "";
         String backdropPath = "";
         String tmdbUrl = "";
         String tvdbId = "";
         String tab = "";
         String releaseDate = "";
         Integer runtime = null;
         String productionCountries = "";
         Double voteAverage = null;
         Integer voteCount = null;

         for (JSONObject x : libraryNewRequest.getItem().getExternalUrls()) {
            if (x.getString("Name").equals("TheTVDB")) {
               String[] pairs = x.getString("Url").split("&");
               if (pairs.length == 2) {
                  tab = StrUtil.subAfter(pairs[0], "=", true);
                  tvdbId = StrUtil.subAfter(pairs[1], "=", true);
               }
            }
         }

         if ("Episode".equals(libraryNewRequest.getItem().getType())) {
            itemId = libraryNewRequest.getItem().getParentId();
         }

         if ("Movie".equals(libraryNewRequest.getItem().getType())) {
            itemId = libraryNewRequest.getItem().getId();
         }

         QueryResultBaseItemResponse queryResultBaseItemResponse = this.getItemsById(itemId, serverConfig);
         if (queryResultBaseItemResponse != null) {
            Iterator var104 = queryResultBaseItemResponse.getItems().iterator();
            if (var104.hasNext()) {
               QueryResultBaseItemResponse.ItemsDTO item = (QueryResultBaseItemResponse.ItemsDTO)var104.next();
               ProviderIdDictionary providerIdDictionary = item.getProviderIds();
               if (providerIdDictionary != null) {
                  tmdbId = providerIdDictionary.get("Tmdb");
               }
            }

            if (StringUtils.hasText(tmdbId)) {
               if ("Episode".equals(libraryNewRequest.getItem().getType())) {
                  try {
                     TvSeriesDb tvSeries = this.tmdbService.getTvSeries(Integer.parseInt(tmdbId), "zh-CN");
                     if (tvSeries != null) {
                        imgUrl = this.buildTmdbImageUrl(tvSeries.getPosterPath());
                        backdropPath = this.buildTmdbImageUrl(tvSeries.getBackdropPath());
                        tmdbUrl = "https://www.themoviedb.org/tv/" + tmdbId;
                        releaseDate = tvSeries.getFirstAirDate();
                        productionCountries = this.joinProductionCountries(tvSeries.getOriginCountry());
                        if (!CollectionUtils.isEmpty(tvSeries.getEpisodeRunTime())) {
                           runtime = tvSeries.getEpisodeRunTime().get(0);
                        }

                        voteAverage = tvSeries.getVoteAverage();
                        voteCount = tvSeries.getVoteCount();
                     }
                  } catch (TmdbException var56) {
                     log.error("获取剧集tmdb信息失败：{}", var56.getMessage());
                  }
               }

               if ("Movie".equals(libraryNewRequest.getItem().getType())) {
                  try {
                     MovieDb movieDb = this.tmdbService.getMovieDetails(Integer.parseInt(tmdbId), "zh-CN");
                     if (movieDb != null) {
                        imgUrl = this.buildTmdbImageUrl(movieDb.getPosterPath());
                        backdropPath = this.buildTmdbImageUrl(movieDb.getBackdropPath());
                        releaseDate = movieDb.getReleaseDate();
                        productionCountries = this.joinProductionCountries(movieDb.getProductionCountries());
                        runtime = movieDb.getRuntime();
                        voteAverage = movieDb.getVoteAverage();
                        voteCount = movieDb.getVoteCount();
                     }
                  } catch (TmdbException var55) {
                     log.error("获取电影tmdb信息失败：{}", var55.getMessage());
                  }
               }
            }

            if ("Movie".equals(libraryNewRequest.getItem().getType())
               && !StringUtils.hasText(imgUrl)
               && !StringUtils.hasText(backdropPath)
               && !StringUtils.hasText(releaseDate)) {
               TmdbSearchResponse tmdbSearchResponse = this.search(libraryNewRequest.getItem().getOriginalTitle(), libraryNewRequest.getItem().getType());
               if (StringUtils.hasText(tmdbSearchResponse.getImgUrl())) {
                  imgUrl = tmdbSearchResponse.getImgUrl();
               }

               if (StringUtils.hasText(tmdbSearchResponse.getBackdropPath())) {
                  backdropPath = tmdbSearchResponse.getBackdropPath();
               }

               releaseDate = tmdbSearchResponse.getReleaseDate();
               productionCountries = tmdbSearchResponse.getProductionCountries();
               runtime = tmdbSearchResponse.getRuntime();
               voteAverage = tmdbSearchResponse.getVoteAverage();
               voteCount = tmdbSearchResponse.getVoteCount();
            }

            if ("Episode".equals(libraryNewRequest.getItem().getType()) && !StringUtils.hasText(tmdbId) && StringUtils.hasText(tvdbId)) {
               try {
                  FindResults findResults = this.tmdbService.findById(tvdbId, ExternalSource.TVDB_ID, "zh-CN");
                  if (!CollectionUtils.isEmpty(findResults.getTvEpisodeResults())) {
                     imgUrl = this.buildTmdbImageUrl(findResults.getTvEpisodeResults().get(0).getStillPath());
                     backdropPath = this.buildTmdbImageUrl(findResults.getTvEpisodeResults().get(0).getStillPath());
                     releaseDate = findResults.getTvEpisodeResults().get(0).getAirDate();
                  } else {
                     TmdbSearchResponse tmdbSearchResponsex = this.search(libraryNewRequest.getItem().getSeriesName(), libraryNewRequest.getItem().getType());
                     if (StringUtils.hasText(tmdbSearchResponsex.getImgUrl())) {
                        imgUrl = tmdbSearchResponsex.getImgUrl();
                     }

                     if (StringUtils.hasText(tmdbSearchResponsex.getBackdropPath())) {
                        backdropPath = tmdbSearchResponsex.getBackdropPath();
                     }

                     releaseDate = tmdbSearchResponsex.getReleaseDate();
                     productionCountries = tmdbSearchResponsex.getProductionCountries();
                     runtime = tmdbSearchResponsex.getRuntime();
                     voteAverage = tmdbSearchResponsex.getVoteAverage();
                     voteCount = tmdbSearchResponsex.getVoteCount();
                  }
               } catch (TmdbException var54) {
                  var54.printStackTrace();
               }
            }

            if ("Episode".equals(libraryNewRequest.getItem().getType()) && !StringUtils.hasText(tmdbId) && !StringUtils.hasText(tvdbId)) {
               TmdbSearchResponse tmdbSearchResponsexx = this.search(libraryNewRequest.getItem().getSeriesName(), libraryNewRequest.getItem().getType());
               if (StringUtils.hasText(tmdbSearchResponsexx.getImgUrl())) {
                  imgUrl = tmdbSearchResponsexx.getImgUrl();
               }

               if (StringUtils.hasText(tmdbSearchResponsexx.getBackdropPath())) {
                  backdropPath = tmdbSearchResponsexx.getBackdropPath();
               }

               releaseDate = tmdbSearchResponsexx.getReleaseDate();
               productionCountries = tmdbSearchResponsexx.getProductionCountries();
               runtime = tmdbSearchResponsexx.getRuntime();
               voteAverage = tmdbSearchResponsexx.getVoteAverage();
               voteCount = tmdbSearchResponsexx.getVoteCount();
            }
         }

         PlaybackUtils.PlaybackInfo playbackInfo = PlaybackUtils.parsePlaybackInfo(JSONObject.toJSONString(data), this.searchSearcher);
         SendPhotoRequest sendPhotoRequest = new SendPhotoRequest();
         sendPhotoRequest.setName(playbackInfo.getEventName());
         sendPhotoRequest.setPlayUser(playbackInfo.getUserName());
         sendPhotoRequest.setPlayTitle(playbackInfo.getFormattedTitle());
         sendPhotoRequest.setUserLocation(this.buildUserLocation(playbackInfo));
         sendPhotoRequest.setPlayTime(this.buildPlayTime(playbackInfo));
         sendPhotoRequest.setPlayPosition(this.buildPlayPosition(playbackInfo));
         sendPhotoRequest.setClientInfo(this.buildClientInfo(playbackInfo));
         sendPhotoRequest.setTmdbUrl(tmdbUrl);
         sendPhotoRequest.setImgUrlInputStream(ResourceUtil.getStream("img/default.jpg"));
         sendPhotoRequest.setImgUrl(imgUrl);
         sendPhotoRequest.setParseMode("Markdown");
         sendPhotoRequest.setType(libraryNewRequest.getItem().getType());
         sendPhotoRequest.setBackdropPath(backdropPath);
         sendPhotoRequest.setRuntime(runtime);
         sendPhotoRequest.setProductionCountries(productionCountries);
         sendPhotoRequest.setReleaseDate(StringUtils.hasText(releaseDate) ? releaseDate : null);
         sendPhotoRequest.setServerUrl(serverUrl);
         sendPhotoRequest.setServerName(serverName);
         String playbackTemplateCode = "media_photo_message";
         if ("playback.start".equals(event)) {
            playbackTemplateCode = "wechat_playback_start";
         }

         if ("playback.stop".equals(event)) {
            playbackTemplateCode = "wechat_playback_stop";
         }

         String customPosterEnabled = this.configCacheLoaderUtils.getConfigValue("custom_poster_enabled");
         if ("true".equalsIgnoreCase(customPosterEnabled)) {
            try {
               String backdropPathForPoster = backdropPath;
               String imgUrlForPoster = imgUrl;
               CompletableFuture<BufferedImage> backdropFuture = CompletableFuture.supplyAsync(() -> {
                  try {
                     return StringUtils.hasText(backdropPathForPoster) ? MovieCardRenderer.downloadPosterFromUrl(backdropPathForPoster) : null;
                  } catch (Exception var2x) {
                     return null;
                  }
               }, this.playbackImageExecutor);
               CompletableFuture<BufferedImage> posterFuture = CompletableFuture.supplyAsync(() -> {
                  try {
                     return StringUtils.hasText(imgUrlForPoster) ? MovieCardRenderer.downloadPosterFromUrl(imgUrlForPoster) : null;
                  } catch (Exception var2x) {
                     return null;
                  }
               }, this.playbackImageExecutor);
               BufferedImage backdropImg = this.awaitImageResult(backdropFuture, 6L, TimeUnit.SECONDS);
               BufferedImage posterImg = this.awaitImageResult(posterFuture, 6L, TimeUnit.SECONDS);
               if (backdropImg != null || posterImg != null) {
                  String durationStr = runtime != null ? this.formatDuration(runtime) : "";
                  String dateStr = StringUtils.hasText(playbackInfo.getDescription())
                     ? playbackInfo.getDescription()
                     : (StringUtils.hasText(releaseDate) ? releaseDate : "");
                  String resolutionStr = "";
                  List<String> genresList = libraryNewRequest.getItem().getGenres();
                  boolean isPlaying = "playback.start".equals(event);
                  String finalUserName = "未知用户";
                  JSONObject userObjx = data.getJSONObject("User");
                  if (userObjx != null) {
                     finalUserName = userObjx.getString("Name");
                  }

                  String cardTitle;
                  String cardSubtitle;
                  if ("Episode".equals(playbackInfo.getType())) {
                     cardTitle = playbackInfo.getSeriesName() + " (" + playbackInfo.getProductionYear() + ")";
                     cardSubtitle = String.format("S%02dE%02d %s", playbackInfo.getSeasonNumber(), playbackInfo.getEpisodeNumber(), playbackInfo.getItemName());
                  } else {
                     cardTitle = playbackInfo.getItemName() + " (" + playbackInfo.getProductionYear() + ")";
                     cardSubtitle = null;
                  }

                  MovieCardRenderer.PlaybackDetail playbackDetail = new MovieCardRenderer.PlaybackDetail(
                     cardTitle, cardSubtitle, durationStr, dateStr, resolutionStr, genresList, isPlaying, finalUserName, serverUrl
                  );
                  byte[] cardBytes = null;

                  try {
                     cardBytes = MovieCardRenderer.generatePlaybackCardToBytes(playbackDetail, backdropImg, posterImg);
                  } catch (Exception var53) {
                     log.warn("生成自定义播放卡片失败：{}", var53.getMessage());
                  }

                  if (cardBytes != null && cardBytes.length > 0) {
                     sendPhotoRequest.setImgUrlInputStream(new ByteArrayInputStream(cardBytes));
                     sendPhotoRequest.setImgUrl(null);
                     log.info("已生成自定义播放卡片 (背景: {}, 海报: {})", backdropImg != null, posterImg != null);
                  }

                  byte[] finalCardBytes = cardBytes;

                  try {
                     if (finalCardBytes != null && finalCardBytes.length > 0) {
                        sendPhotoRequest.setImgUrlInputStream(new ByteArrayInputStream(finalCardBytes));
                     }

                     this.notifyUtils.sendTelegram(sendPhotoRequest, playbackTemplateCode, NotifyMessageType.PHOTO_MESSAGE, false);
                  } catch (TelegramApiException var52) {
                     log.error("用户播放 Telegram 发送异常", (Throwable)var52);
                  }

                  if (cardBytes != null && cardBytes.length > 0) {
                     sendPhotoRequest.setImgUrlInputStream(new ByteArrayInputStream(cardBytes));
                  }

                  this.notifyUtils.sendWechat(sendPhotoRequest, playbackTemplateCode, NotifyMessageType.PHOTO_MESSAGE);
                  if (cardBytes != null && cardBytes.length > 0) {
                     sendPhotoRequest.setImgUrlInputStream(new ByteArrayInputStream(cardBytes));
                  }

                  this.notifyUtils.sendWechat(sendPhotoRequest, playbackTemplateCode, NotifyMessageType.PHOTO_MESSAGE, "wechatBot");
                  if (cardBytes != null && cardBytes.length > 0) {
                     sendPhotoRequest.setImgUrlInputStream(new ByteArrayInputStream(cardBytes));
                  }

                  this.notifyUtils.sendMessagePush(sendPhotoRequest, playbackTemplateCode, NotifyMessageType.PHOTO_MESSAGE);
               }
            } catch (Exception var58) {
               log.warn("生成自定义播放卡片流程异常，使用默认图片：{}", var58.getMessage());
            }
         } else {
            try {
               this.notifyUtils.sendTelegram(sendPhotoRequest, playbackTemplateCode, NotifyMessageType.PHOTO_MESSAGE, false);
            } catch (TelegramApiException var51) {
               log.error("用户播放 Telegram 发送异常", (Throwable)var51);
            }

            this.notifyUtils.sendWechat(sendPhotoRequest, playbackTemplateCode, NotifyMessageType.PHOTO_MESSAGE);
            this.notifyUtils.sendWechat(sendPhotoRequest, playbackTemplateCode, NotifyMessageType.PHOTO_MESSAGE, "wechatBot");
            this.notifyUtils.sendMessagePush(sendPhotoRequest, playbackTemplateCode, NotifyMessageType.PHOTO_MESSAGE);
         }

         if ("playback.start".equals(event)) {
            log.info("用户开始播放：{}", JSONObject.toJSONString(playbackInfo));
            LambdaQueryChainWrapper<MediaMain> mediaMainQuery = new LambdaQueryChainWrapper<>(this.mediaMainService.getBaseMapper())
               .eq(MediaMain::getTitle, playbackInfo.getFormattedTitle());
            if (embyInfoId != null) {
               mediaMainQuery.eq(MediaMain::getEmbyInfoId, embyInfoId);
            } else {
               mediaMainQuery.isNull(MediaMain::getEmbyInfoId);
            }

            MediaMain mediaMainData = mediaMainQuery.one();
            if (mediaMainData == null) {
               MediaMain mediaMain = new MediaMain();
               mediaMain.setTitle(playbackInfo.getFormattedTitle());
               mediaMain.setEmbyInfoId(embyInfoId);
               mediaMain.setType("Movie".equals(libraryNewRequest.getItem().getType()) ? "movie" : "tv");
               mediaMain.setPosterPath(imgUrl);
               mediaMain.setReleaseDate(StringUtils.hasText(releaseDate) ? DateUtil.parseDate(releaseDate).toJdkDate() : null);
               mediaMain.setPlayCount(1);
               mediaMain.setRating(playbackInfo.getCommunityRating() != null ? BigDecimal.valueOf(playbackInfo.getCommunityRating()) : BigDecimal.ZERO);
               double ticksPerMinute = 6.0E8;
               Integer duration = (int)Math.round((double)playbackInfo.getRunTimeTicks().longValue() / ticksPerMinute);
               mediaMain.setDuration(duration);
               this.mediaMainService.save(mediaMain);
               this.addMediaViewDetail(playbackInfo, mediaMain);
            } else {
               mediaMainData.setPlayCount(mediaMainData.getPlayCount() + 1);
               this.mediaMainService.updateById(mediaMainData);
               this.addMediaViewDetail(playbackInfo, mediaMainData);
            }
         }
      }

      if ("library.new".equals(event)) {
         log.info("新增资源：{}", JSONObject.toJSONString(libraryNewRequest));
         String tmdbid = "";
         boolean requestMatchTmdbReliable = false;
         String tmdbUrl = "";
         String imgUrl = "";
         String overview = "";
         String backdropPath = "";
         String tvdbId = "";
         String tab = "";
         String releaseDate = "";
         Integer runtime = null;
         String productionCountries = "";
         Double voteAverage = null;
         Integer voteCount = null;
         overview = libraryNewRequest.getItem().getOverview();
         List<String> genres = libraryNewRequest.getItem().getGenres();
         EmbyServiceImpl.MediaStreamNotifyInfo streamNotifyInfo = this.buildMediaStreamNotifyInfo(libraryNewRequest.getItem())
            .mergeMissing(this.fetchMediaStreamNotifyInfo(serverConfig, libraryNewRequest.getItem().getId()));
         String displayTitle = streamNotifyInfo.videoQuality();
         if (!CollectionUtils.isEmpty(libraryNewRequest.getItem().getExternalUrls())) {
            for (JSONObject xx : libraryNewRequest.getItem().getExternalUrls()) {
               String externalName = xx.getString("Name");
               String url = xx.getString("Url");
               if ("MovieDb".equals(externalName) || "TheMovieDb".equals(externalName)) {
                  tmdbUrl = url;
                  if ("Movie".equals(libraryNewRequest.getItem().getType())) {
                     tmdbid = this.extractMovieTmdbId(url);
                  } else if ("Episode".equals(libraryNewRequest.getItem().getType()) || "Series".equals(libraryNewRequest.getItem().getType())) {
                     tmdbid = this.extractSeriesTmdbId(url);
                  }

                  if (StringUtils.hasText(tmdbid)) {
                     requestMatchTmdbReliable = true;
                  }
               }

               if ("TheTVDB".equals(externalName) && StringUtils.hasText(url)) {
                  String[] pairs = url.split("&");
                  if (pairs.length == 2) {
                     tab = StrUtil.subAfter(pairs[0], "=", true);
                     tvdbId = StrUtil.subAfter(pairs[1], "=", true);
                  }
               }
            }
         }

         String embyMerge = this.configCacheLoaderUtils.getConfigValue("emby_merge");
         if ("Movie".equals(libraryNewRequest.getItem().getType()) && StringUtils.hasText(tmdbid)) {
            try {
               MovieDb movieDb = this.tmdbService.getMovieDetails(Integer.parseInt(tmdbid), "zh-CN");
               if (movieDb != null) {
                  imgUrl = this.buildTmdbImageUrl(movieDb.getPosterPath());
                  backdropPath = this.buildTmdbImageUrl(movieDb.getBackdropPath());
                  overview = movieDb.getOverview();
                  voteAverage = movieDb.getVoteAverage();
                  voteCount = movieDb.getVoteCount();
               }
            } catch (TmdbException var50) {
               log.error("获取电影tmdb信息失败：{}", var50.getMessage());
            }
         }

         if ("Episode".equals(libraryNewRequest.getItem().getType()) || "Series".equals(libraryNewRequest.getItem().getType())) {
            if (!StringUtils.hasText(tmdbid)) {
               tmdbid = this.resolveSeriesTmdbIdFromEmby(libraryNewRequest.getItem(), serverConfig);
               requestMatchTmdbReliable = StringUtils.hasText(tmdbid);
            }

            try {
               if (StringUtils.hasText(tmdbid)) {
                  TvSeriesDb tvSeries = this.tmdbService.getTvSeries(Integer.parseInt(tmdbid), "zh-CN");
                  if (tvSeries != null) {
                     imgUrl = this.buildTmdbImageUrl(tvSeries.getPosterPath());
                     backdropPath = this.buildTmdbImageUrl(tvSeries.getBackdropPath());
                     overview = tvSeries.getOverview();
                     tmdbUrl = "https://www.themoviedb.org/tv/" + tmdbid;
                     productionCountries = this.joinProductionCountries(tvSeries.getOriginCountry());
                     if (!CollectionUtils.isEmpty(tvSeries.getEpisodeRunTime())) {
                        runtime = tvSeries.getEpisodeRunTime().get(0);
                     }

                     releaseDate = tvSeries.getFirstAirDate();
                     voteAverage = tvSeries.getVoteAverage();
                     voteCount = tvSeries.getVoteCount();
                  }
               } else if (StringUtils.hasText(tvdbId) && StringUtils.hasText(tab)) {
                  TmdbSearchResponse tmdbSearchResponsexxx = this.search(libraryNewRequest.getItem().getSeriesName(), libraryNewRequest.getItem().getType());
                  if (tmdbSearchResponsexxx.getTmdbId() != null) {
                     tmdbid = tmdbSearchResponsexxx.getTmdbId().toString();
                  }

                  productionCountries = tmdbSearchResponsexxx.getProductionCountries();
                  runtime = tmdbSearchResponsexxx.getRuntime();
                  releaseDate = tmdbSearchResponsexxx.getReleaseDate();
                  voteAverage = tmdbSearchResponsexxx.getVoteAverage();
                  voteCount = tmdbSearchResponsexxx.getVoteCount();
                  if ("episode".equals(tab)) {
                     try {
                        FindResults findResults = this.tmdbService.findById(tvdbId, ExternalSource.TVDB_ID, "zh-CN");
                        if (!CollectionUtils.isEmpty(findResults.getTvEpisodeResults())) {
                           Integer showId = findResults.getTvEpisodeResults().get(0).getShowId();
                           if (showId != null) {
                              tmdbid = showId.toString();
                              requestMatchTmdbReliable = true;
                           }

                           if (showId != null) {
                              TvSeriesDb tvSeries = this.tmdbService.getTvSeries(showId, "zh-CN");
                              if (tvSeries != null) {
                                 imgUrl = this.buildTmdbImageUrl(tvSeries.getPosterPath());
                                 backdropPath = this.buildTmdbImageUrl(tvSeries.getBackdropPath());
                                 overview = tvSeries.getOverview();
                                 productionCountries = this.joinProductionCountries(tvSeries.getOriginCountry());
                                 if (!CollectionUtils.isEmpty(tvSeries.getEpisodeRunTime())) {
                                    runtime = tvSeries.getEpisodeRunTime().get(0);
                                 }

                                 releaseDate = tvSeries.getFirstAirDate();
                                 voteAverage = tvSeries.getVoteAverage();
                                 voteCount = tvSeries.getVoteCount();
                              }
                           }
                        }
                     } catch (TmdbException var49) {
                        var49.printStackTrace();
                     }
                  }

                  if ("series".equals(tab)) {
                     try {
                        FindResults findResults = this.tmdbService.findById(tvdbId, ExternalSource.TVDB_ID, "zh-CN");
                        if (!CollectionUtils.isEmpty(findResults.getTvSeriesResults())) {
                           Integer seriesId = findResults.getTvSeriesResults().get(0).getId();
                           if (seriesId != null) {
                              tmdbid = seriesId.toString();
                              requestMatchTmdbReliable = true;
                           }

                           imgUrl = this.buildTmdbImageUrl(findResults.getTvSeriesResults().get(0).getPosterPath());
                           backdropPath = this.buildTmdbImageUrl(findResults.getTvSeriesResults().get(0).getBackdropPath());
                           overview = findResults.getTvSeriesResults().get(0).getOverview();
                           productionCountries = this.joinProductionCountries(findResults.getTvSeriesResults().get(0).getOriginCountry());
                           releaseDate = findResults.getTvSeriesResults().get(0).getFirstAirDate();
                           voteAverage = findResults.getTvSeriesResults().get(0).getVoteAverage();
                           voteCount = findResults.getTvSeriesResults().get(0).getVoteCount();
                        }
                     } catch (TmdbException var48) {
                        var48.printStackTrace();
                     }
                  }
               } else {
                  TmdbSearchResponse tmdbSearchResponsexxxx = this.search(libraryNewRequest.getItem().getSeriesName(), libraryNewRequest.getItem().getType());
                  if (StringUtils.hasText(tmdbSearchResponsexxxx.getImgUrl())) {
                     imgUrl = tmdbSearchResponsexxxx.getImgUrl();
                  }

                  if (StringUtils.hasText(tmdbSearchResponsexxxx.getBackdropPath())) {
                     backdropPath = tmdbSearchResponsexxxx.getBackdropPath();
                  }

                  if (tmdbSearchResponsexxxx.getTmdbId() != null) {
                     tmdbid = tmdbSearchResponsexxxx.getTmdbId().toString();
                  }

                  overview = tmdbSearchResponsexxxx.getOverview();
                  productionCountries = tmdbSearchResponsexxxx.getProductionCountries();
                  runtime = tmdbSearchResponsexxxx.getRuntime();
                  releaseDate = tmdbSearchResponsexxxx.getReleaseDate();
                  voteAverage = tmdbSearchResponsexxxx.getVoteAverage();
                  voteCount = tmdbSearchResponsexxxx.getVoteCount();
               }
            } catch (TmdbException var57) {
               log.error("获取剧集tmdb信息失败：{}", var57.getMessage());
            }
         }

         Integer requestMatchTmdbId = requestMatchTmdbReliable ? this.parseIntegerId(tmdbid) : null;
         RequestList matchedRequestList = this.getRequestList(
            libraryNewRequest,
            requestMatchTmdbId != null ? requestMatchTmdbId : 0,
            embyInfoId,
            payloadEmbyInfo != null
               ? payloadEmbyInfo.getEmbyServerId()
               : (libraryNewRequest.getServer() != null ? libraryNewRequest.getServer().getId() : null)
         );
         if (matchedRequestList != null) {
            if (this.isHttpUrl(matchedRequestList.getImageUrl())) {
               imgUrl = matchedRequestList.getImageUrl();
            }

            if (!StringUtils.hasText(backdropPath) && this.isHttpUrl(matchedRequestList.getBackdropPath())) {
               backdropPath = matchedRequestList.getBackdropPath();
            }

            if (!StringUtils.hasText(overview) && StringUtils.hasText(matchedRequestList.getOverview())) {
               overview = matchedRequestList.getOverview();
            }

            if (!StringUtils.hasText(tmdbUrl) && StringUtils.hasText(matchedRequestList.getTmdbUrl())) {
               tmdbUrl = matchedRequestList.getTmdbUrl();
            }
         }

         int indexNumber = libraryNewRequest.getItem().getIndexNumber() != null ? libraryNewRequest.getItem().getIndexNumber() : 0;
         String seriesName = libraryNewRequest.getItem().getSeriesName()
            + (libraryNewRequest.getItem().getProductionYear() != null ? " (" + libraryNewRequest.getItem().getProductionYear() + ")" : "")
            + " - 第"
            + (libraryNewRequest.getItem().getParentIndexNumber() != null ? libraryNewRequest.getItem().getParentIndexNumber() : 0)
            + "季";
         String episodeName = libraryNewRequest.getItem().getName();
         String translatedDisplayTitle = displayTitle;
         String searchName = "";
         if ("Movie".equals(libraryNewRequest.getItem().getType())) {
            searchName = libraryNewRequest.getItem().getName()
               + (libraryNewRequest.getItem().getProductionYear() != null ? " (" + libraryNewRequest.getItem().getProductionYear() + ")" : "");
         }

         if ("Episode".equals(libraryNewRequest.getItem().getType())) {
            searchName = seriesName;
         }

         if ("Series".equals(libraryNewRequest.getItem().getType())) {
            searchName = libraryNewRequest.getItem().getName()
               + (libraryNewRequest.getItem().getProductionYear() != null ? " (" + libraryNewRequest.getItem().getProductionYear() + ")" : "");
         }

         if (StringUtils.hasText(embyMerge)) {
            try {
               SendPhotoRequest sendPhotoRequestx = new SendPhotoRequest();
               if (!StringUtils.hasText(imgUrl)) {
                  sendPhotoRequestx.setImgUrlInputStream(ResourceUtil.getStream("img/default.jpg"));
               }

               sendPhotoRequestx.setServerUrl(serverUrl);
               sendPhotoRequestx.setServerName(serverName);
               if ("Series".equals(libraryNewRequest.getItem().getType())) {
                  String descriptionx = libraryNewRequest.getDescription();
                  String[] parts = descriptionx.split("TmdbId: ");
                  String episodes = parts[0].trim();
                  if (StringUtils.hasText(episodes)) {
                     searchName = libraryNewRequest.getItem().getName()
                        + (libraryNewRequest.getItem().getProductionYear() != null ? " (" + libraryNewRequest.getItem().getProductionYear() + ")" : "")
                        + "\n\n\ud83c\udfcf 集数详情："
                        + episodes;
                  }
               }

               if ("Episode".equals(libraryNewRequest.getItem().getType())) {
                  searchName = searchName + "\n\n\ud83c\udfcf 集数详情： 第" + indexNumber + "集 - " + episodeName;
               }

               sendPhotoRequestx.setName(searchName);
               sendPhotoRequestx.setOverview(overview);
               sendPhotoRequestx.setTmdbUrl(tmdbUrl);
               sendPhotoRequestx.setImgUrl(imgUrl);
               sendPhotoRequestx.setParseMode("Markdown");
               sendPhotoRequestx.setProductionYear(
                  libraryNewRequest.getItem().getProductionYear() != null ? libraryNewRequest.getItem().getProductionYear() : null
               );
               sendPhotoRequestx.setGenres(
                  genres.stream().reduce(new StringBuilder(), (sb2, s2) -> sb2.append("#").append(s2).append(' '), StringBuilder::append).toString()
               );
               sendPhotoRequestx.setType(libraryNewRequest.getItem().getType());
               sendPhotoRequestx.setDisplayTitle(translatedDisplayTitle);
               sendPhotoRequestx.setAudioQuality(streamNotifyInfo.audioQuality());
               sendPhotoRequestx.setSubtitleInfo(streamNotifyInfo.subtitleInfo());
               sendPhotoRequestx.setSize(DataSizeUtil.format(libraryNewRequest.getItem().getSize(), DataUnit.GIGABYTES));
               sendPhotoRequestx.setBackdropPath(backdropPath);
               sendPhotoRequestx.setRuntime(runtime);
               sendPhotoRequestx.setProductionCountries(productionCountries);
               sendPhotoRequestx.setReleaseDate(StringUtils.hasText(releaseDate) ? releaseDate : null);
               sendPhotoRequestx.setVoteAverage(voteAverage);
               sendPhotoRequestx.setVoteCount(voteCount);
               this.notifyUtils
                  .sendMultiChannel(
                     sendPhotoRequestx,
                     "media_photo_detail",
                     NotifyMessageType.PHOTO_DETAIL,
                     true,
                     "telegram",
                     "wechat",
                     "wechatBot",
                     "dingding",
                     "messagepush"
                  );
            } catch (Exception var47) {
               var47.printStackTrace();
               log.error("emby webhooks 电影发送通知失败：{}", var47.getMessage());
            }
         } else {
            LambdaQueryChainWrapper<EmbyNotifyData> queryChainWrapper = new LambdaQueryChainWrapper<>(this.embyNotifyDataService.getBaseMapper())
               .eq(EmbyNotifyData::getName, searchName);
            if (embyInfoId != null) {
               queryChainWrapper.eq(EmbyNotifyData::getEmbyInfoId, embyInfoId);
            } else {
               queryChainWrapper.isNull(EmbyNotifyData::getEmbyInfoId);
            }

            EmbyNotifyData embyNotifyDataCustom = queryChainWrapper.one();
            EmbyNotifyDataDetails embyNotifyDataDetails = new EmbyNotifyDataDetails();
            String episodeNumber = "第"
               + indexNumber
               + "集 - "
               + (StringUtils.hasText(episodeName) && episodeName.length() > 30 ? "第" + indexNumber + "集" : episodeName);
            if (embyNotifyDataCustom != null) {
               boolean needUpdate = false;
               if (voteAverage != null) {
                  embyNotifyDataCustom.setVoteAverage(voteAverage);
                  embyNotifyDataCustom.setVoteCount(voteCount);
                  needUpdate = true;
               }

               if (StringUtils.hasText(productionCountries)) {
                  embyNotifyDataCustom.setProductionCountries(productionCountries);
                  needUpdate = true;
               }

               if (StringUtils.hasText(imgUrl) && !StringUtils.hasText(embyNotifyDataCustom.getImgUrl())) {
                  embyNotifyDataCustom.setImgUrl(imgUrl);
                  needUpdate = true;
               }

               if (StringUtils.hasText(backdropPath) && !StringUtils.hasText(embyNotifyDataCustom.getBackdropPath())) {
                  embyNotifyDataCustom.setBackdropPath(backdropPath);
                  needUpdate = true;
               }

               if (StringUtils.hasText(tmdbUrl) && !StringUtils.hasText(embyNotifyDataCustom.getTmdbUrl())) {
                  embyNotifyDataCustom.setTmdbUrl(tmdbUrl);
                  needUpdate = true;
               }

               if (StringUtils.hasText(overview) && !StringUtils.hasText(embyNotifyDataCustom.getOverview())) {
                  embyNotifyDataCustom.setOverview(overview);
                  needUpdate = true;
               }

               if (this.shouldReplaceMediaText(embyNotifyDataCustom.getDisplayTitle(), displayTitle)) {
                  embyNotifyDataCustom.setDisplayTitle(displayTitle);
                  needUpdate = true;
               }

               if (this.shouldReplaceMediaText(embyNotifyDataCustom.getAudioQuality(), streamNotifyInfo.audioQuality())) {
                  embyNotifyDataCustom.setAudioQuality(streamNotifyInfo.audioQuality());
                  needUpdate = true;
               }

               if (this.shouldReplaceMediaText(embyNotifyDataCustom.getSubtitleInfo(), streamNotifyInfo.subtitleInfo())) {
                  embyNotifyDataCustom.setSubtitleInfo(streamNotifyInfo.subtitleInfo());
                  needUpdate = true;
               }

               if (needUpdate) {
                  this.embyNotifyDataService.updateById(embyNotifyDataCustom);
               }

               if ("Episode".equals(libraryNewRequest.getItem().getType())) {
                  embyNotifyDataDetails.setEmbyNotifyDataId(embyNotifyDataCustom.getId());
                  EmbyNotifyDataDetails embyNotifyDataDetailsCustom = new LambdaQueryChainWrapper<>(this.embyNotifyDataDetailsService.getBaseMapper())
                     .eq(EmbyNotifyDataDetails::getEpisodeDetails, episodeNumber)
                     .eq(EmbyNotifyDataDetails::getEmbyNotifyDataId, embyNotifyDataCustom.getId())
                     .eq(EmbyNotifyDataDetails::getEmbyInfoId, embyInfoId)
                     .one();
                  if (embyNotifyDataDetailsCustom == null) {
                     embyNotifyDataDetails.setEpisodeDetails(episodeNumber);
                     embyNotifyDataDetails.setEpisodeInfo(libraryNewRequest.getItem().getOverview());
                     embyNotifyDataDetails.setSize(String.valueOf(libraryNewRequest.getItem().getSize()));
                     embyNotifyDataDetails.setStatus(2);
                     embyNotifyDataDetails.setEmbyInfoId(embyInfoId);
                     this.embyNotifyDataDetailsService.save(embyNotifyDataDetails);
                  }
               }
            } else {
               EmbyNotifyData embyNotifyData = new EmbyNotifyData();
               embyNotifyData.setOverview(overview);
               embyNotifyData.setType(libraryNewRequest.getItem().getType());
               embyNotifyData.setProductionYear(
                  libraryNewRequest.getItem().getProductionYear() != null ? String.valueOf(libraryNewRequest.getItem().getProductionYear()) : ""
               );
               embyNotifyData.setStatus(2);
               embyNotifyData.setName(searchName);
               embyNotifyData.setEmbyInfoId(embyInfoId);
               if (("Episode".equals(libraryNewRequest.getItem().getType()) || "Series".equals(libraryNewRequest.getItem().getType()))
                  && "Series".equals(libraryNewRequest.getItem().getType())) {
                  String descriptionx = libraryNewRequest.getDescription();
                  String[] parts = descriptionx.split("TmdbId: ");
                  String episodes = parts[0].trim();
                  embyNotifyDataDetails.setEpisodeDetails(episodes);
                  embyNotifyData.setName(searchName);
               }

               embyNotifyData.setImgUrl(imgUrl);
               embyNotifyData.setBackdropPath(backdropPath);
               embyNotifyData.setTmdbUrl(tmdbUrl);
               embyNotifyData.setDisplayTitle(displayTitle);
               embyNotifyData.setAudioQuality(streamNotifyInfo.audioQuality());
               embyNotifyData.setSubtitleInfo(streamNotifyInfo.subtitleInfo());
               embyNotifyData.setGenres(
                  genres.stream().reduce(new StringBuilder(), (sb2, s2) -> sb2.append("#").append(s2).append(' '), StringBuilder::append).toString()
               );
               embyNotifyData.setSize(
                  "Movie".equals(libraryNewRequest.getItem().getType())
                     ? String.valueOf(libraryNewRequest.getItem().getSize())
                     : ("Episode".equals(libraryNewRequest.getItem().getType()) ? "0" : "0")
               );
               embyNotifyData.setVoteAverage(voteAverage);
               embyNotifyData.setVoteCount(voteCount);
               embyNotifyData.setProductionCountries(productionCountries);
               this.embyNotifyDataService.save(embyNotifyData);
               if ("Episode".equals(libraryNewRequest.getItem().getType()) || "Series".equals(libraryNewRequest.getItem().getType())) {
                  embyNotifyDataDetails.setEmbyNotifyDataId(embyNotifyData.getId());
                  if ("Episode".equals(libraryNewRequest.getItem().getType())) {
                     embyNotifyDataDetails.setEpisodeDetails(episodeNumber);
                  }

                  embyNotifyDataDetails.setEpisodeInfo(libraryNewRequest.getItem().getOverview());
                  embyNotifyDataDetails.setSize(String.valueOf(libraryNewRequest.getItem().getSize()));
                  embyNotifyDataDetails.setStatus(2);
                  embyNotifyDataDetails.setEmbyInfoId(embyInfoId);
                  this.embyNotifyDataDetailsService.save(embyNotifyDataDetails);
               }
            }
         }
      }
   }

   @Override
   public JSONObject clientFilterWebhook(JSONObject data) {
      try {
         if (data != null && !data.isEmpty()) {
            boolean clientFilterEnabled = this.embyBlockKeywordService.isClientFilterEnabled();
            boolean regionFilterEnabled = this.isRegionFilterEnabledSafely();
            if (!clientFilterEnabled && !regionFilterEnabled) {
               return this.clientFilterResponse("skipped", "Access filters disabled");
            } else {
               String event = data.getString("Event");
               if (!CLIENT_FILTER_EVENTS.contains(event)) {
                  JSONObject response = this.clientFilterResponse("ignored", "Not listen event");
                  response.put("event", event);
                  return response;
               } else {
                  JSONObject sessionInfo = data.getJSONObject("Session");
                  JSONObject userInfo = data.getJSONObject("User");
                  String clientName = sessionInfo != null ? sessionInfo.getString("Client") : null;
                  String remoteEndPoint = sessionInfo != null ? sessionInfo.getString("RemoteEndPoint") : null;
                  Optional<EmbyRegionBlockRuleService.RegionMatch> regionMatch = regionFilterEnabled
                     ? this.findMatchedRegion(remoteEndPoint)
                     : Optional.empty();
                  if (regionMatch.isPresent()) {
                     return this.handleRegionFilterMatch(data, event, userInfo, sessionInfo, clientName, regionMatch.get());
                  } else {
                     Optional<String> matchedPattern = clientFilterEnabled
                        ? this.findMatchedClientFilterPattern(clientName, this.embyBlockKeywordService.getEffectiveClientFilterPatterns())
                        : Optional.empty();
                     if (!matchedPattern.isEmpty()) {
                        String sessionId = sessionInfo != null ? sessionInfo.getString("Id") : null;
                        String userId = userInfo != null ? userInfo.getString("Id") : null;
                        String userName = userInfo != null ? userInfo.getString("Name") : null;
                        Long embyInfoId = this.resolveVerifiedWebhookEmbyInfoId(data);
                        if (this.embyClientFilterExclusionService.isExcluded(embyInfoId, userId)) {
                           JSONObject response = this.clientFilterResponse("excluded", "用户已排除");
                           JSONObject detail = new JSONObject();
                           detail.put("emby_info_id", embyInfoId);
                           detail.put("user_id", userId);
                           detail.put("user_name", userName);
                           detail.put("session_id", sessionId);
                           detail.put("client_name", clientName);
                           detail.put("matched_pattern", matchedPattern.get());
                           detail.put("event", event);
                           response.put("data", detail);
                           return response;
                        } else {
                           EmbyServiceImpl.ClientFilterTerminateResult terminateResult = this.terminateBlockedClientSession(
                              data, sessionId, clientName, matchedPattern.get()
                           );
                           boolean blockUserSuccess = false;
                           if (this.embyBlockKeywordService.isClientFilterBlockUserEnabled()) {
                              blockUserSuccess = this.disableBlockedClientUser(data, userId, userName, clientName, matchedPattern.get());
                           }

                           EmbyClientFilterRecord filterRecord = this.saveClientFilterRecord(
                              data, event, userInfo, sessionInfo, matchedPattern.get(), terminateResult, blockUserSuccess
                           );
                           boolean notifySent = this.sendClientFilterNotify(filterRecord, matchedPattern.get(), terminateResult, blockUserSuccess);
                           log.warn(
                              "拦截非法客户端 event={} user={} embyId={} sessionId={} client={} pattern={} stopSuccess={} messageSuccess={} blockUserSuccess={} notifySent={}",
                              event,
                              userName,
                              userId,
                              sessionId,
                              clientName,
                              matchedPattern.get(),
                              terminateResult.stopSuccess(),
                              terminateResult.messageSuccess(),
                              blockUserSuccess,
                              notifySent
                           );
                           JSONObject response = this.clientFilterResponse("blocked", "Client blocked");
                           JSONObject detail = new JSONObject();
                           detail.put("record_id", filterRecord != null ? filterRecord.getId() : null);
                           detail.put("user_id", userId);
                           detail.put("user_name", userName);
                           detail.put("session_id", sessionId);
                           detail.put("client_name", clientName);
                           detail.put("matched_pattern", matchedPattern.get());
                           detail.put("event", event);
                           detail.put("using_default_patterns", Boolean.valueOf(this.embyBlockKeywordService.isUsingDefaultClientFilterPatterns()));
                           detail.put("terminate_success", Boolean.valueOf(terminateResult.stopSuccess()));
                           detail.put("message_success", Boolean.valueOf(terminateResult.messageSuccess()));
                           detail.put("block_user_enabled", Boolean.valueOf(this.embyBlockKeywordService.isClientFilterBlockUserEnabled()));
                           detail.put("block_user_success", Boolean.valueOf(blockUserSuccess));
                           detail.put("notify_sent", Boolean.valueOf(notifySent));
                           detail.put("timestamp", DateUtil.formatDateTime(new Date()));
                           response.put("data", detail);
                           return response;
                        }
                     } else if (clientFilterEnabled && !StringUtils.hasText(clientName)) {
                        return this.clientFilterResponse("ignored", "No Client info found");
                     } else {
                        JSONObject response = this.clientFilterResponse("allowed", "Access allowed");
                        JSONObject detail = new JSONObject();
                        detail.put("client", clientName);
                        detail.put("remote_endpoint", remoteEndPoint);
                        detail.put("event", event);
                        detail.put(
                           "using_default_patterns", Boolean.valueOf(clientFilterEnabled && this.embyBlockKeywordService.isUsingDefaultClientFilterPatterns())
                        );
                        response.put("data", detail);
                        return response;
                     }
                  }
               }
            }
         } else {
            return this.clientFilterResponse("error", "No data received");
         }
      } catch (Exception var21) {
         log.error("处理 Emby 访问拦截 webhook 失败: {}", var21.getMessage(), var21);
         JSONObject response = this.clientFilterResponse("error", "Webhook handle failed");
         response.put("detail", var21.getMessage());
         return response;
      }
   }

   private boolean isRegionFilterEnabledSafely() {
      if (this.embyRegionBlockRuleService == null) {
         return false;
      } else {
         try {
            return this.embyRegionBlockRuleService.isEnabled();
         } catch (Exception var2) {
            log.warn("读取 Emby 地区拦截开关失败，地区规则按关闭处理: {}", var2.getMessage());
            return false;
         }
      }
   }

   private Optional<EmbyRegionBlockRuleService.RegionMatch> findMatchedRegion(String remoteEndPoint) {
      try {
         return this.embyRegionBlockRuleService.match(remoteEndPoint);
      } catch (Exception var3) {
         log.warn("Emby 地区拦截匹配失败，按未命中处理 endpoint={}: {}", remoteEndPoint, var3.getMessage());
         return Optional.empty();
      }
   }

   private JSONObject handleRegionFilterMatch(
      JSONObject data, String event, JSONObject userInfo, JSONObject sessionInfo, String clientName, EmbyRegionBlockRuleService.RegionMatch match
   ) {
      String sessionId = sessionInfo != null ? sessionInfo.getString("Id") : null;
      String userId = userInfo != null ? userInfo.getString("Id") : null;
      String userName = userInfo != null ? userInfo.getString("Name") : null;
      if (!StringUtils.hasText(userId)) {
         JSONObject response = this.clientFilterResponse("ignored", "No user info found for region block");
         response.put(
            "data",
            this.buildRegionResponseDetail(
               null, event, userId, userName, sessionId, clientName, match, new EmbyServiceImpl.ClientFilterTerminateResult(false, false), false, false
            )
         );
         return response;
      } else {
         Long embyInfoId = this.resolveVerifiedWebhookEmbyInfoId(data);
         if (embyInfoId == null) {
            log.warn("地区拦截命中但 Webhook 服务器身份无法验证，拒绝执行禁用 event={} user={} sessionId={} rule={}", event, userId, sessionId, match.ruleCode());
            JSONObject response = this.clientFilterResponse("ignored", "Unverified Emby server");
            response.put(
               "data",
               this.buildRegionResponseDetail(
                  null, event, userId, userName, sessionId, clientName, match, new EmbyServiceImpl.ClientFilterTerminateResult(false, false), false, false
               )
            );
            return response;
         } else if (this.isDuplicateRegionFilterRecord(embyInfoId, userId, sessionId, match)) {
            JSONObject response = this.clientFilterResponse("duplicate", "Region block already processed");
            JSONObject detail = this.buildRegionResponseDetail(
               null, event, userId, userName, sessionId, clientName, match, new EmbyServiceImpl.ClientFilterTerminateResult(false, false), false, false
            );
            detail.put("duplicate", Boolean.valueOf(true));
            response.put("data", detail);
            return response;
         } else {
            EmbyInfo payloadEmbyInfo = this.resolveWebhookEmbyInfo(data);
            EmbyInfoCacheManagerUtils.EmbyServerConfig serverConfig = null;

            try {
               serverConfig = this.resolveWebhookServerConfig(payloadEmbyInfo, embyInfoId);
            } catch (Exception var19) {
               log.error("地区拦截命中但 Emby 服务器配置不可用 embyInfoId={} rule={}: {}", embyInfoId, match.ruleCode(), var19.getMessage());
            }

            EmbyServiceImpl.ClientFilterTerminateResult terminateResult = this.terminateBlockedRegionSession(serverConfig, sessionId, match);
            boolean blockUserSuccess = this.disableRegionBlockedUser(serverConfig, embyInfoId, userId, userName, match);
            EmbyClientFilterRecord filterRecord = this.saveRegionFilterRecord(
               data, event, userInfo, sessionInfo, embyInfoId, serverConfig, match, terminateResult, blockUserSuccess
            );
            boolean notifySent = this.sendRegionFilterNotify(filterRecord, match, terminateResult, blockUserSuccess);
            log.warn(
               "地区拦截命中 event={} user={} embyUserId={} sessionId={} client={} rule={} ip={} country={} province={} stopSuccess={} messageSuccess={} blockUserSuccess={} notifySent={}",
               event,
               userName,
               userId,
               sessionId,
               clientName,
               match.ruleCode(),
               match.location().ip(),
               match.location().country(),
               match.location().province(),
               terminateResult.stopSuccess(),
               terminateResult.messageSuccess(),
               blockUserSuccess,
               notifySent
            );
            JSONObject response = this.clientFilterResponse("blocked", "Region blocked");
            JSONObject detail = this.buildRegionResponseDetail(
               filterRecord, event, userId, userName, sessionId, clientName, match, terminateResult, blockUserSuccess, notifySent
            );
            response.put("data", detail);
            return response;
         }
      }
   }

   private JSONObject buildRegionResponseDetail(
      EmbyClientFilterRecord record,
      String event,
      String userId,
      String userName,
      String sessionId,
      String clientName,
      EmbyRegionBlockRuleService.RegionMatch match,
      EmbyServiceImpl.ClientFilterTerminateResult terminateResult,
      boolean blockUserSuccess,
      boolean notifySent
   ) {
      JSONObject detail = new JSONObject();
      detail.put("record_id", record != null ? record.getId() : null);
      detail.put("filter_type", "REGION");
      detail.put("user_id", userId);
      detail.put("user_name", userName);
      detail.put("session_id", sessionId);
      detail.put("client_name", clientName);
      detail.put("matched_pattern", match.ruleCode());
      detail.put("matched_region", match.displayName());
      detail.put("resolved_ip", match.location().ip());
      detail.put("country", match.location().country());
      detail.put("province", match.location().province());
      detail.put("city", match.location().city());
      detail.put("event", event);
      detail.put("terminate_success", Boolean.valueOf(terminateResult.stopSuccess()));
      detail.put("message_success", Boolean.valueOf(terminateResult.messageSuccess()));
      detail.put("block_user_enabled", Boolean.valueOf(true));
      detail.put("block_user_success", Boolean.valueOf(blockUserSuccess));
      detail.put("notify_sent", Boolean.valueOf(notifySent));
      detail.put("timestamp", DateUtil.formatDateTime(new Date()));
      return detail;
   }

   private boolean isDuplicateRegionFilterRecord(Long embyInfoId, String userId, String sessionId, EmbyRegionBlockRuleService.RegionMatch match) {
      try {
         LambdaQueryWrapper<EmbyClientFilterRecord> wrapper = Wrappers.lambdaQuery(EmbyClientFilterRecord.class)
            .eq(EmbyClientFilterRecord::getFilterType, "REGION")
            .eq(EmbyClientFilterRecord::getEmbyInfoId, embyInfoId)
            .eq(EmbyClientFilterRecord::getEmbyUserId, userId)
            .eq(EmbyClientFilterRecord::getMatchedPattern, match.ruleCode())
            .ge(EmbyClientFilterRecord::getTriggerTime, DateUtil.offsetMinute(new Date(), -5));
         if (StringUtils.hasText(sessionId)) {
            wrapper.eq(EmbyClientFilterRecord::getSessionId, sessionId);
         } else {
            wrapper.eq(EmbyClientFilterRecord::getResolvedIp, match.location().ip());
         }

         return this.embyClientFilterRecordService.count(wrapper) > 0L;
      } catch (Exception var6) {
         log.warn("查询地区拦截幂等记录失败，将继续执行本次拦截 user={} rule={}: {}", userId, match.ruleCode(), var6.getMessage());
         return false;
      }
   }

   private JSONObject clientFilterResponse(String status, String message) {
      JSONObject response = new JSONObject();
      response.put("status", status);
      response.put("message", message);
      return response;
   }

   private Optional<String> findMatchedClientFilterPattern(String clientName, List<String> patterns) {
      if (StringUtils.hasText(clientName) && !CollectionUtils.isEmpty(patterns)) {
         String clientLower = clientName.toLowerCase(Locale.ROOT);

         for (String pattern : patterns) {
            if (StringUtils.hasText(pattern)) {
               String normalizedPattern = pattern.trim().toLowerCase(Locale.ROOT);

               try {
                  if (Pattern.compile(normalizedPattern).matcher(clientLower).find()) {
                     return Optional.of(pattern);
                  }
               } catch (PatternSyntaxException var8) {
                  log.error("客户端过滤正则错误: {} - {}", pattern, var8.getMessage());
               }
            }
         }

         return Optional.empty();
      } else {
         return Optional.empty();
      }
   }

   private EmbyServiceImpl.ClientFilterTerminateResult terminateBlockedClientSession(
      JSONObject data, String sessionId, String clientName, String matchedPattern
   ) {
      if (!StringUtils.hasText(sessionId)) {
         return new EmbyServiceImpl.ClientFilterTerminateResult(false, false);
      } else {
         EmbyInfo payloadEmbyInfo = this.resolveWebhookEmbyInfo(data);
         Long embyInfoId = payloadEmbyInfo != null ? payloadEmbyInfo.getId() : this.resolveWebhookEmbyInfoId(data);
         EmbyInfoCacheManagerUtils.EmbyServerConfig serverConfig = this.resolveWebhookServerConfig(payloadEmbyInfo, embyInfoId);
         boolean stopSuccess = this.stopBlockedClientSession(serverConfig, sessionId, clientName, matchedPattern);
         boolean messageSuccess = this.sendBlockedClientSessionMessage(serverConfig, sessionId, clientName, matchedPattern);
         return new EmbyServiceImpl.ClientFilterTerminateResult(stopSuccess, messageSuccess);
      }
   }

   private EmbyClientFilterRecord saveClientFilterRecord(
      JSONObject data,
      String event,
      JSONObject userInfo,
      JSONObject sessionInfo,
      String matchedPattern,
      EmbyServiceImpl.ClientFilterTerminateResult terminateResult,
      boolean blockUserSuccess
   ) {
      try {
         EmbyInfo payloadEmbyInfo = this.resolveWebhookEmbyInfo(data);
         Long embyInfoId = payloadEmbyInfo != null ? payloadEmbyInfo.getId() : this.resolveWebhookEmbyInfoId(data);
         EmbyInfoCacheManagerUtils.EmbyServerConfig serverConfig = this.resolveWebhookServerConfig(payloadEmbyInfo, embyInfoId);
         JSONObject serverInfo = data != null ? data.getJSONObject("Server") : null;
         JSONObject itemInfo = data != null ? data.getJSONObject("Item") : null;
         EmbyClientFilterRecord record = new EmbyClientFilterRecord();
         record.setEmbyInfoId(serverConfig != null ? serverConfig.id() : embyInfoId);
         record.setEmbyServerId(serverInfo != null ? serverInfo.getString("Id") : null);
         record.setServerName(this.resolveServerName(payloadEmbyInfo, embyInfoId, serverConfig));
         if (!StringUtils.hasText(record.getServerName()) && serverInfo != null) {
            record.setServerName(serverInfo.getString("Name"));
         }

         record.setEvent(event);
         record.setFilterType("UA");
         record.setEmbyUserId(userInfo != null ? userInfo.getString("Id") : null);
         record.setEmbyUserName(userInfo != null ? userInfo.getString("Name") : null);
         record.setSessionId(sessionInfo != null ? sessionInfo.getString("Id") : null);
         record.setClientName(sessionInfo != null ? sessionInfo.getString("Client") : null);
         record.setDeviceName(sessionInfo != null ? sessionInfo.getString("DeviceName") : null);
         record.setDeviceId(sessionInfo != null ? sessionInfo.getString("DeviceId") : null);
         record.setApplicationVersion(sessionInfo != null ? sessionInfo.getString("ApplicationVersion") : null);
         record.setRemoteEndpoint(sessionInfo != null ? sessionInfo.getString("RemoteEndPoint") : null);
         record.setItemId(itemInfo != null ? itemInfo.getString("Id") : null);
         record.setItemName(itemInfo != null ? itemInfo.getString("Name") : null);
         record.setItemType(itemInfo != null ? itemInfo.getString("Type") : null);
         record.setMatchedPattern(matchedPattern);
         record.setUsingDefaultPatterns(this.toFlag(this.embyBlockKeywordService.isUsingDefaultClientFilterPatterns()));
         record.setStopSuccess(this.toFlag(terminateResult.stopSuccess()));
         record.setMessageSuccess(this.toFlag(terminateResult.messageSuccess()));
         record.setBlockUserEnabled(this.toFlag(this.embyBlockKeywordService.isClientFilterBlockUserEnabled()));
         record.setBlockUserSuccess(this.toFlag(blockUserSuccess));
         record.setNotifySent(0);
         record.setTriggerTime(new Date());
         record.setRawPayload(data != null ? JSON.toJSONString(data) : null);
         this.embyClientFilterRecordService.save(record);
         return record;
      } catch (Exception var14) {
         log.error("保存 UA 拦截记录失败: {}", var14.getMessage(), var14);
         return null;
      }
   }

   private boolean sendClientFilterNotify(
      EmbyClientFilterRecord record, String matchedPattern, EmbyServiceImpl.ClientFilterTerminateResult terminateResult, boolean blockUserSuccess
   ) {
      try {
         String blockUserEnabledText = this.embyBlockKeywordService.isClientFilterBlockUserEnabled() ? "已开启" : "未开启";
         String blockUserResultText = this.embyBlockKeywordService.isClientFilterBlockUserEnabled() ? (blockUserSuccess ? "已禁用用户" : "禁用用户失败") : "未启用禁用用户，仅终止会话";
         String details = this.buildClientFilterNotifyDetails(record, matchedPattern, terminateResult, blockUserEnabledText, blockUserResultText);
         Map<String, String> extras = new HashMap<>();
         extras.put("clientFilterDetails", details);
         extras.put("userName", this.safeText(record != null ? record.getEmbyUserName() : null));
         extras.put("clientName", this.safeText(record != null ? record.getClientName() : null));
         extras.put("event", this.safeText(record != null ? record.getEvent() : null));
         extras.put("matchedPattern", this.safeText(matchedPattern));
         extras.put("blockUserResult", blockUserResultText);
         SendMessageRequest sendMessageRequest = new SendMessageRequest();
         sendMessageRequest.setName("UA拦截提醒");
         sendMessageRequest.setOverview(details);
         sendMessageRequest.setServerName(record != null ? record.getServerName() : null);
         sendMessageRequest.setExtraVariables(extras);
         this.notifyUtils.sendMultiChannel(sendMessageRequest, "emby_client_filter", false, "telegram", "wechat", "wechatBot", "dingding", "messagepush");
         if (record != null && record.getId() != null) {
            record.setNotifySent(1);
            this.embyClientFilterRecordService.updateById(record);
         }

         return true;
      } catch (Exception var10) {
         log.error("发送 UA 拦截通知失败: {}", var10.getMessage(), var10);
         return false;
      }
   }

   private String buildClientFilterNotifyDetails(
      EmbyClientFilterRecord record,
      String matchedPattern,
      EmbyServiceImpl.ClientFilterTerminateResult terminateResult,
      String blockUserEnabledText,
      String blockUserResultText
   ) {
      String triggerTime = record != null && record.getTriggerTime() != null
         ? DateUtil.formatDateTime(record.getTriggerTime())
         : DateUtil.formatDateTime(new Date());
      String itemName = record != null ? record.getItemName() : null;
      StringBuilder builder = new StringBuilder()
         .append("🌁 Mist · UA 拦截\n")
         .append("\ud83d\udda5️ 服务器：")
         .append(this.safeText(record != null ? record.getServerName() : null))
         .append("\n")
         .append("\ud83d\udce1 事件：")
         .append(this.clientFilterEventLabel(record != null ? record.getEvent() : null))
         .append("\n")
         .append("\ud83d\udc64 用户：")
         .append(this.safeText(record != null ? record.getEmbyUserName() : null))
         .append("\n")
         .append("\ud83c\udd94 Emby ID：")
         .append(this.safeText(record != null ? record.getEmbyUserId() : null))
         .append("\n")
         .append("\ud83d\udcf1 客户端：")
         .append(this.safeText(record != null ? record.getClientName() : null))
         .append("\n")
         .append("\ud83d\udcbb 设备：")
         .append(this.safeText(record != null ? record.getDeviceName() : null))
         .append("\n")
         .append("\ud83c\udf10 IP：")
         .append(this.safeText(record != null ? record.getRemoteEndpoint() : null))
         .append("\n");
      if (StringUtils.hasText(itemName)) {
         builder.append("\ud83c\udfac 内容：").append(itemName).append("\n");
      }

      return builder.append("\ud83c\udfaf 规则：")
         .append(this.safeText(matchedPattern))
         .append("\n")
         .append("⏹️ 停止播放：")
         .append(this.successText(terminateResult.stopSuccess()))
         .append("\n")
         .append("\ud83d\udcac 发送提示：")
         .append(this.successText(terminateResult.messageSuccess()))
         .append("\n")
         .append("\ud83d\udd12 禁用用户开关：")
         .append(blockUserEnabledText)
         .append("\n")
         .append("✅ 处理结果：")
         .append(blockUserResultText)
         .append("\n")
         .append("\ud83d\udd52 时间：")
         .append(triggerTime)
         .toString();
   }

   private String clientFilterEventLabel(String event) {
      if (!StringUtils.hasText(event)) {
         return "--";
      } else {
         return switch (event) {
            case "session.start" -> "会话开始";
            case "playback.start" -> "开始播放";
            case "playback.progress" -> "播放进度";
            case "playback.stop" -> "停止播放";
            case "user.authenticated" -> "登录成功";
            case "user.authenticationfailed" -> "登录失败";
            default -> event;
         };
      }
   }

   private String successText(boolean success) {
      return success ? "成功" : "失败";
   }

   private int toFlag(boolean value) {
      return value ? 1 : 0;
   }

   private String safeText(String value) {
      return StringUtils.hasText(value) ? value : "--";
   }

   private EmbyServiceImpl.ClientFilterTerminateResult terminateBlockedRegionSession(
      EmbyInfoCacheManagerUtils.EmbyServerConfig serverConfig, String sessionId, EmbyRegionBlockRuleService.RegionMatch match
   ) {
      if (serverConfig != null && StringUtils.hasText(sessionId)) {
         boolean stopSuccess = this.stopBlockedClientSession(serverConfig, sessionId, this.safeText(match.displayName()), match.ruleCode());
         boolean messageSuccess = this.sendBlockedRegionSessionMessage(serverConfig, sessionId, match);
         return new EmbyServiceImpl.ClientFilterTerminateResult(stopSuccess, messageSuccess);
      } else {
         return new EmbyServiceImpl.ClientFilterTerminateResult(false, false);
      }
   }

   private boolean sendBlockedRegionSessionMessage(
      EmbyInfoCacheManagerUtils.EmbyServerConfig serverConfig, String sessionId, EmbyRegionBlockRuleService.RegionMatch match
   ) {
      try {
         JSONObject message = new JSONObject();
         message.put("Text", "会话已被终止：当前 IP 所在地区受限（" + this.safeText(match.displayName()) + "）");
         message.put("Header", "地区访问限制");
         message.put("TimeoutMs", Integer.valueOf(10000));
         HttpResponse response = HttpRequest.post(this.buildEmbyApiUrl(serverConfig, "Sessions/" + sessionId + "/Message"))
            .header("X-Emby-Token", serverConfig.apiKey())
            .header(Header.CONTENT_TYPE, "application/json")
            .body(JSON.toJSONString(message))
            .timeout(8000)
            .execute();
         boolean success = response.getStatus() >= 200 && response.getStatus() < 300;
         if (!success) {
            log.warn("发送地区拦截提示失败 sessionId={} rule={} status={} body={}", sessionId, match.ruleCode(), response.getStatus(), response.body());
         }

         return success;
      } catch (Exception var7) {
         log.warn("发送地区拦截提示异常 sessionId={} rule={}: {}", sessionId, match.ruleCode(), var7.getMessage(), var7);
         return false;
      }
   }

   private boolean disableRegionBlockedUser(
      EmbyInfoCacheManagerUtils.EmbyServerConfig serverConfig, Long embyInfoId, String userId, String userName, EmbyRegionBlockRuleService.RegionMatch match
   ) {
      if (serverConfig != null && embyInfoId != null && StringUtils.hasText(userId)) {
         try {
            EmbyUser localUser = this.embyUserService
               .lambdaQuery()
               .eq(EmbyUser::getEmbyInfoId, embyInfoId)
               .eq(EmbyUser::getEmbyUserId, userId)
               .last("LIMIT 1")
               .one();
            if (localUser == null || !Integer.valueOf(1).equals(localUser.getIsAdmin()) && !Integer.valueOf(1).equals(localUser.getIsPrimaryAdmin())) {
               UserServiceApi userServiceApi = new UserServiceApi(this.buildApiClient(serverConfig));
               UserDto userDto = userServiceApi.getUsersById(userId);
               if (userDto != null && userDto.getPolicy() != null) {
                  UserPolicy userPolicy = userDto.getPolicy();
                  if (Boolean.TRUE.equals(userPolicy.isIsAdministrator())) {
                     log.warn("地区拦截命中 Emby 管理员用户，跳过禁用 user={} embyId={} rule={}", userName, userId, match.ruleCode());
                     return false;
                  } else {
                     userPolicy.setIsDisabled(true);
                     userServiceApi.postUsersByIdPolicy(userPolicy, userId);

                     try {
                        this.embyUserService
                           .lambdaUpdate()
                           .eq(EmbyUser::getEmbyInfoId, embyInfoId)
                           .eq(EmbyUser::getEmbyUserId, userId)
                           .set(EmbyUser::getUserStatus, Integer.valueOf(1))
                           .set(EmbyUser::getDisableReason, "地区拦截: " + match.ruleCode())
                           .set(EmbyUser::getDisabledDatetime, new Date())
                           .update();
                     } catch (Exception var11) {
                        log.error("地区拦截已禁用 Emby 用户，但同步本地状态失败 user={} embyId={} rule={}: {}", userName, userId, match.ruleCode(), var11.getMessage(), var11);
                     }

                     return true;
                  }
               } else {
                  log.error("地区拦截禁用用户失败，未获取到用户策略 user={} embyId={} rule={}", userName, userId, match.ruleCode());
                  return false;
               }
            } else {
               log.warn("地区拦截命中本地管理员用户，跳过禁用 user={} embyId={} rule={}", userName, userId, match.ruleCode());
               return false;
            }
         } catch (ApiException var12) {
            log.error(
               "地区拦截禁用用户失败 user={} embyId={} rule={} status={} body={}", userName, userId, match.ruleCode(), var12.getCode(), var12.getResponseBody(), var12
            );
            return false;
         } catch (Exception var13) {
            log.error("地区拦截禁用用户异常 user={} embyId={} rule={}: {}", userName, userId, match.ruleCode(), var13.getMessage(), var13);
            return false;
         }
      } else {
         return false;
      }
   }

   private EmbyClientFilterRecord saveRegionFilterRecord(
      JSONObject data,
      String event,
      JSONObject userInfo,
      JSONObject sessionInfo,
      Long embyInfoId,
      EmbyInfoCacheManagerUtils.EmbyServerConfig serverConfig,
      EmbyRegionBlockRuleService.RegionMatch match,
      EmbyServiceImpl.ClientFilterTerminateResult terminateResult,
      boolean blockUserSuccess
   ) {
      JSONObject serverInfo = data != null ? data.getJSONObject("Server") : null;
      JSONObject itemInfo = data != null ? data.getJSONObject("Item") : null;
      EmbyInfo payloadEmbyInfo = this.resolveWebhookEmbyInfo(data);
      EmbyClientFilterRecord record = new EmbyClientFilterRecord();
      record.setEmbyInfoId(embyInfoId);
      record.setEmbyServerId(serverInfo != null ? serverInfo.getString("Id") : null);
      record.setServerName(
         serverConfig != null
            ? serverConfig.serverName()
            : (payloadEmbyInfo != null ? payloadEmbyInfo.getServerName() : (serverInfo != null ? serverInfo.getString("Name") : null))
      );
      record.setEvent(event);
      record.setFilterType("REGION");
      record.setEmbyUserId(userInfo != null ? userInfo.getString("Id") : null);
      record.setEmbyUserName(userInfo != null ? userInfo.getString("Name") : null);
      record.setSessionId(sessionInfo != null ? sessionInfo.getString("Id") : null);
      record.setClientName(sessionInfo != null ? sessionInfo.getString("Client") : null);
      record.setDeviceName(sessionInfo != null ? sessionInfo.getString("DeviceName") : null);
      record.setDeviceId(sessionInfo != null ? sessionInfo.getString("DeviceId") : null);
      record.setApplicationVersion(sessionInfo != null ? sessionInfo.getString("ApplicationVersion") : null);
      record.setRemoteEndpoint(sessionInfo != null ? sessionInfo.getString("RemoteEndPoint") : null);
      record.setResolvedIp(match.location().ip());
      record.setCountry(match.location().country());
      record.setProvince(match.location().province());
      record.setCity(match.location().city());
      record.setItemId(itemInfo != null ? itemInfo.getString("Id") : null);
      record.setItemName(itemInfo != null ? itemInfo.getString("Name") : null);
      record.setItemType(itemInfo != null ? itemInfo.getString("Type") : null);
      record.setMatchedPattern(match.ruleCode());
      record.setUsingDefaultPatterns(0);
      record.setStopSuccess(this.toFlag(terminateResult.stopSuccess()));
      record.setMessageSuccess(this.toFlag(terminateResult.messageSuccess()));
      record.setBlockUserEnabled(1);
      record.setBlockUserSuccess(this.toFlag(blockUserSuccess));
      record.setNotifySent(0);
      record.setTriggerTime(new Date());
      record.setRawPayload(data != null ? JSON.toJSONString(data) : null);

      try {
         this.embyClientFilterRecordService.save(record);
      } catch (Exception var15) {
         log.error("保存地区拦截记录失败 user={} rule={}: {}", record.getEmbyUserId(), match.ruleCode(), var15.getMessage(), var15);
      }

      return record;
   }

   private boolean sendRegionFilterNotify(
      EmbyClientFilterRecord record,
      EmbyRegionBlockRuleService.RegionMatch match,
      EmbyServiceImpl.ClientFilterTerminateResult terminateResult,
      boolean blockUserSuccess
   ) {
      try {
         String blockUserResult = blockUserSuccess ? "已禁用用户" : "禁用失败或用户受管理员保护";
         StringBuilder location = new StringBuilder(this.safeText(match.location().country()));
         if (StringUtils.hasText(match.location().province())) {
            location.append(" / ").append(match.location().province());
         }

         if (StringUtils.hasText(match.location().city())) {
            location.append(" / ").append(match.location().city());
         }

         String details = new StringBuilder()
            .append("🌁 Mist · 地区拦截\n")
            .append("\ud83d\udda5️ 服务器：")
            .append(this.safeText(record.getServerName()))
            .append("\n")
            .append("\ud83d\udce1 事件：")
            .append(this.clientFilterEventLabel(record.getEvent()))
            .append("\n")
            .append("\ud83d\udc64 用户：")
            .append(this.safeText(record.getEmbyUserName()))
            .append("\n")
            .append("\ud83c\udd94 Emby ID：")
            .append(this.safeText(record.getEmbyUserId()))
            .append("\n")
            .append("\ud83d\udcf1 客户端：")
            .append(this.safeText(record.getClientName()))
            .append("\n")
            .append("\ud83c\udf10 IP：")
            .append(this.safeText(match.location().ip()))
            .append("\n")
            .append("\ud83d\udccd 归属地：")
            .append((CharSequence)location)
            .append("\n")
            .append("\ud83c\udfaf 规则：")
            .append(this.safeText(match.displayName()))
            .append("（")
            .append(match.ruleCode())
            .append("）\n")
            .append("⏹️ 停止播放：")
            .append(this.successText(terminateResult.stopSuccess()))
            .append("\n")
            .append("\ud83d\udcac 发送提示：")
            .append(this.successText(terminateResult.messageSuccess()))
            .append("\n")
            .append("✅ 处理结果：")
            .append(blockUserResult)
            .append("\n")
            .append("\ud83d\udd52 时间：")
            .append(DateUtil.formatDateTime(record.getTriggerTime()))
            .toString();
         Map<String, String> extras = new HashMap<>();
         extras.put("clientFilterDetails", details);
         extras.put("userName", this.safeText(record.getEmbyUserName()));
         extras.put("clientName", this.safeText(record.getClientName()));
         extras.put("event", this.safeText(record.getEvent()));
         extras.put("matchedPattern", match.ruleCode());
         extras.put("blockUserResult", blockUserResult);
         SendMessageRequest sendMessageRequest = new SendMessageRequest();
         sendMessageRequest.setName("地区拦截提醒");
         sendMessageRequest.setOverview(details);
         sendMessageRequest.setServerName(record.getServerName());
         sendMessageRequest.setExtraVariables(extras);
         this.notifyUtils.sendMultiChannel(sendMessageRequest, "emby_client_filter", false, "telegram", "wechat", "wechatBot", "dingding", "messagepush");
         if (record.getId() != null) {
            record.setNotifySent(1);
            this.embyClientFilterRecordService.updateById(record);
         }

         return true;
      } catch (Exception var10) {
         log.error("发送地区拦截通知失败 rule={}: {}", match.ruleCode(), var10.getMessage(), var10);
         return false;
      }
   }

   private boolean disableBlockedClientUser(JSONObject data, String userId, String userName, String clientName, String matchedPattern) {
      if (!StringUtils.hasText(userId)) {
         return false;
      } else {
         EmbyInfo payloadEmbyInfo = this.resolveWebhookEmbyInfo(data);
         Long embyInfoId = payloadEmbyInfo != null ? payloadEmbyInfo.getId() : this.resolveWebhookEmbyInfoId(data);
         EmbyInfoCacheManagerUtils.EmbyServerConfig serverConfig = this.resolveWebhookServerConfig(payloadEmbyInfo, embyInfoId);

         try {
            UserServiceApi userServiceApi = new UserServiceApi(this.buildApiClient(serverConfig));
            UserDto userDto = userServiceApi.getUsersById(userId);
            if (userDto != null && userDto.getPolicy() != null) {
               UserPolicy userPolicy = userDto.getPolicy();
               if (Boolean.TRUE.equals(userPolicy.isIsAdministrator())) {
                  log.warn("非法客户端命中管理员用户，跳过禁用 user={} embyId={} client={} pattern={}", userName, userId, clientName, matchedPattern);
                  return false;
               } else {
                  userPolicy.setIsDisabled(true);
                  userServiceApi.postUsersByIdPolicy(userPolicy, userId);
                  LambdaUpdateChainWrapper<EmbyUser> updateChain = this.embyUserService
                     .lambdaUpdate()
                     .eq(EmbyUser::getEmbyUserId, userId)
                     .set(EmbyUser::getUserStatus, Integer.valueOf(1))
                     .set(EmbyUser::getDisableReason, "UA拦截: " + clientName)
                     .set(EmbyUser::getDisabledDatetime, new Date());
                  if (serverConfig != null && serverConfig.id() != null) {
                     updateChain.eq(EmbyUser::getEmbyInfoId, serverConfig.id());
                  }

                  updateChain.update();
                  return true;
               }
            } else {
               log.error("非法客户端禁用用户失败，未获取到用户策略 user={} embyId={} client={} pattern={}", userName, userId, clientName, matchedPattern);
               return false;
            }
         } catch (ApiException var13) {
            log.error(
               "非法客户端禁用用户失败 user={} embyId={} client={} pattern={} status={} body={}",
               userName,
               userId,
               clientName,
               matchedPattern,
               var13.getCode(),
               var13.getResponseBody(),
               var13
            );
            return false;
         } catch (Exception var14) {
            log.error("非法客户端禁用用户异常 user={} embyId={} client={} pattern={}: {}", userName, userId, clientName, matchedPattern, var14.getMessage(), var14);
            return false;
         }
      }
   }

   private boolean stopBlockedClientSession(EmbyInfoCacheManagerUtils.EmbyServerConfig serverConfig, String sessionId, String clientName, String matchedPattern) {
      try {
         HttpResponse response = HttpRequest.post(this.buildEmbyApiUrl(serverConfig, "Sessions/" + sessionId + "/Playing/Stop"))
            .header("X-Emby-Token", serverConfig.apiKey())
            .timeout(8000)
            .execute();
         boolean success = response.getStatus() >= 200 && response.getStatus() < 300;
         if (!success) {
            log.error(
               "停止非法客户端播放失败 sessionId={} client={} pattern={} status={} body={}", sessionId, clientName, matchedPattern, response.getStatus(), response.body()
            );
         }

         return success;
      } catch (Exception var7) {
         log.error("停止非法客户端播放异常 sessionId={} client={} pattern={}: {}", sessionId, clientName, matchedPattern, var7.getMessage(), var7);
         return false;
      }
   }

   private boolean sendBlockedClientSessionMessage(
      EmbyInfoCacheManagerUtils.EmbyServerConfig serverConfig, String sessionId, String clientName, String matchedPattern
   ) {
      try {
         String reason = "检测到可疑客户端: " + clientName;
         JSONObject message = new JSONObject();
         message.put("Text", "会话已被终止: " + reason);
         message.put("Header", "安全警告");
         message.put("TimeoutMs", Integer.valueOf(10000));
         HttpResponse response = HttpRequest.post(this.buildEmbyApiUrl(serverConfig, "Sessions/" + sessionId + "/Message"))
            .header("X-Emby-Token", serverConfig.apiKey())
            .header(Header.CONTENT_TYPE, "application/json")
            .body(JSON.toJSONString(message))
            .timeout(8000)
            .execute();
         boolean success = response.getStatus() >= 200 && response.getStatus() < 300;
         if (!success) {
            log.warn("发送非法客户端拦截提示失败 sessionId={} pattern={} status={} body={}", sessionId, matchedPattern, response.getStatus(), response.body());
         }

         return success;
      } catch (Exception var9) {
         log.warn("发送非法客户端拦截提示异常 sessionId={} pattern={}: {}", sessionId, matchedPattern, var9.getMessage(), var9);
         return false;
      }
   }

   private String buildEmbyApiUrl(EmbyInfoCacheManagerUtils.EmbyServerConfig serverConfig, String path) {
      String baseUrl = serverConfig.url();
      if (!baseUrl.endsWith("/")) {
         baseUrl = baseUrl + "/";
      }

      return baseUrl + path;
   }

   private void addMediaViewDetail(PlaybackUtils.PlaybackInfo playbackInfo, MediaMain mediaMain) {
      MediaViewDetail mediaViewDetail = new MediaViewDetail();
      mediaViewDetail.setMediaId(mediaMain.getId());
      EmbyUser embyUser = new LambdaQueryChainWrapper<>(this.embyUserService.getBaseMapper()).eq(EmbyUser::getEmbyUserName, playbackInfo.getUserName()).one();
      if (embyUser != null) {
         mediaViewDetail.setUserId(embyUser.getId());
      }

      mediaViewDetail.setEmbyUserName(playbackInfo.getUserName());
      if (StringUtils.hasText(playbackInfo.getDescription())) {
         try {
            String inputPattern = "yyyy年MM月dd日EEEE ahh:mm";
            DateTime dateTime = DateUtil.parse(playbackInfo.getDescription(), inputPattern);
            String viewTime = DateUtil.format(dateTime, "yyyy-MM-dd HH:mm");
            mediaViewDetail.setViewTime(viewTime);
         } catch (Exception var8) {
            log.error("获取播放时间失败：{}", var8.getMessage());
            mediaViewDetail.setViewTime(DateUtil.formatDateTime(new Date()));
         }
      }

      mediaViewDetail.setDevice(playbackInfo.getDeviceName());
      this.mediaViewDetailService.save(mediaViewDetail);
   }

   private String buildUserLocation(PlaybackUtils.PlaybackInfo playbackInfo) {
      StringBuilder location = new StringBuilder();
      if (StringUtils.hasText(playbackInfo.getRemoteEndPoint())) {
         location.append(playbackInfo.getRemoteEndPoint());
      }

      if (StringUtils.hasText(playbackInfo.getIpAddress())) {
         if (!location.isEmpty()) {
            location.append(" ");
         }

         location.append(playbackInfo.getIpAddress());
      }

      return location.isEmpty() ? "未知" : location.toString();
   }

   private String buildPlayTime(PlaybackUtils.PlaybackInfo playbackInfo) {
      return StringUtils.hasText(playbackInfo.getDescription()) ? playbackInfo.getDescription() : DateUtil.formatDateTime(new Date());
   }

   private String buildPlayPosition(PlaybackUtils.PlaybackInfo playbackInfo) {
      String positionStr = PlaybackUtils.formatTicksToTime(playbackInfo.getPositionTicks() == null ? 0L : playbackInfo.getPositionTicks());
      String runtimeStr = PlaybackUtils.formatTicksToTime(playbackInfo.getRunTimeTicks() == null ? 0L : playbackInfo.getRunTimeTicks());
      String progress = TimeStringPercentageCalculatorUtils.calculatePercentage(positionStr, runtimeStr);
      return positionStr + " / 总时长" + runtimeStr + " 进度为:" + progress;
   }

   private String buildClientInfo(PlaybackUtils.PlaybackInfo playbackInfo) {
      return this.buildClientInfo(playbackInfo.getClient(), playbackInfo.getDeviceName());
   }

   private String buildClientInfo(String client, String deviceName) {
      if (StringUtils.hasText(client) && StringUtils.hasText(deviceName)) {
         return client + " (" + deviceName + ")";
      } else if (StringUtils.hasText(client)) {
         return client;
      } else {
         return StringUtils.hasText(deviceName) ? deviceName : "";
      }
   }

   private EmbyInfo resolveWebhookEmbyInfo(JSONObject data) {
      JSONObject serverObj = data != null ? data.getJSONObject("Server") : null;
      String serverId = serverObj != null ? serverObj.getString("Id") : null;
      if (StringUtils.hasText(serverId)) {
         EmbyInfo embyInfo = this.embyInfoService.getByServerId(serverId);
         if (embyInfo != null) {
            return embyInfo;
         }
      }

      return null;
   }

   private Long resolveWebhookEmbyInfoId(JSONObject data) {
      Long verifiedEmbyInfoId = this.resolveVerifiedWebhookEmbyInfoId(data);
      if (verifiedEmbyInfoId != null) {
         return verifiedEmbyInfoId;
      } else {
         EmbyInfoCacheManagerUtils.EmbyServerConfig config = this.embyInfoCacheManager.getConfig();
         return config != null ? config.id() : null;
      }
   }

   private Long resolveVerifiedWebhookEmbyInfoId(JSONObject data) {
      EmbyInfo embyInfoByServer = this.resolveWebhookEmbyInfo(data);
      if (embyInfoByServer != null) {
         return embyInfoByServer.getId();
      } else {
         ServletRequestAttributes attributes = (ServletRequestAttributes)RequestContextHolder.getRequestAttributes();
         if (attributes != null) {
            String headerApiKey = this.extractApiKey(attributes.getRequest());
            if (StringUtils.hasText(headerApiKey)) {
               EmbyInfo embyInfo = this.embyInfoService.getByApiKey(headerApiKey);
               if (embyInfo != null) {
                  return embyInfo.getId();
               }
            }
         }

         String payloadApiKey = data != null ? data.getString("ApiKey") : null;
         if (StringUtils.hasText(payloadApiKey)) {
            EmbyInfo embyInfo = this.embyInfoService.getByApiKey(payloadApiKey);
            if (embyInfo != null) {
               return embyInfo.getId();
            }
         }

         return null;
      }
   }

   private String extractApiKey(HttpServletRequest request) {
      if (request == null) {
         return null;
      } else {
         String tokenHeader = request.getHeader("X-Emby-Token");
         if (StringUtils.hasText(tokenHeader)) {
            return tokenHeader;
         } else {
            String authHeader = request.getHeader("X-Emby-Authorization");
            if (StringUtils.hasText(authHeader)) {
               Pattern pattern = Pattern.compile("Token=\\\"?([^,;\\\"]+)");
               Matcher matcher = pattern.matcher(authHeader);
               if (matcher.find()) {
                  return matcher.group(1);
               }
            }

            return null;
         }
      }
   }

   private EmbyInfoCacheManagerUtils.EmbyServerConfig resolveWebhookServerConfig(EmbyInfo payloadEmbyInfo, Long embyInfoId) {
      if (payloadEmbyInfo != null) {
         return this.embyInfoCacheManager.getRequiredConfigById(payloadEmbyInfo.getId());
      } else {
         return embyInfoId != null ? this.embyInfoCacheManager.getRequiredConfigById(embyInfoId) : this.embyInfoCacheManager.getRequiredConfig();
      }
   }

   private String resolveServerName(EmbyInfo payloadEmbyInfo, Long embyInfoId, EmbyInfoCacheManagerUtils.EmbyServerConfig serverConfig) {
      if (payloadEmbyInfo != null && StringUtils.hasText(payloadEmbyInfo.getServerName())) {
         return payloadEmbyInfo.getServerName();
      } else {
         if (embyInfoId != null) {
            EmbyInfo embyInfo = this.embyInfoService.getById(embyInfoId);
            if (embyInfo != null && StringUtils.hasText(embyInfo.getServerName())) {
               return embyInfo.getServerName();
            }
         }

         if (serverConfig != null && serverConfig.id() != null) {
            EmbyInfo embyInfo = this.embyInfoService.getById(serverConfig.id());
            if (embyInfo != null && StringUtils.hasText(embyInfo.getServerName())) {
               return embyInfo.getServerName();
            }
         }

         return null;
      }
   }

   private String buildServerUrl(EmbyInfo embyInfo) {
      if (embyInfo == null) {
         return null;
      } else {
         String embyUrl = embyInfo.getEmbyUrl();
         if (!StringUtils.hasText(embyUrl) || !embyUrl.startsWith("http://") && !embyUrl.startsWith("https://")) {
            StringBuilder baseUrl = new StringBuilder();
            if (StringUtils.hasText(embyInfo.getEmbyAgreement())) {
               baseUrl.append(embyInfo.getEmbyAgreement()).append("://");
            }

            if (StringUtils.hasText(embyInfo.getEmbyUrl())) {
               baseUrl.append(embyInfo.getEmbyUrl());
            }

            if (StringUtils.hasText(embyInfo.getEmbyPort())) {
               if (embyInfo.getEmbyUrl() != null && !embyInfo.getEmbyUrl().contains(":")) {
                  baseUrl.append(":");
               }

               baseUrl.append(embyInfo.getEmbyPort());
            }

            return baseUrl.length() > 0 ? baseUrl.toString() : null;
         } else {
            return embyUrl;
         }
      }
   }

   private QueryResultBaseItemResponse getItemsById(String itemId) {
      return this.getItemsById(itemId, this.getCurrentServerConfig());
   }

   private QueryResultBaseItemResponse getItemsById(String itemId, EmbyInfoCacheManagerUtils.EmbyServerConfig serverConfig) {
      GetItemsRequest getItemsRequest = new GetItemsRequest();
      getItemsRequest.setIds(itemId);
      getItemsRequest.setLimit(10);
      getItemsRequest.setStartIndex(0);
      getItemsRequest.setIncludeItemTypes("Movie,Season,Series");

      try {
         return this.getItems(getItemsRequest, serverConfig);
      } catch (ApiException var5) {
         log.error("获取资源信息失败：{}", var5.getResponseBody());
         return null;
      }
   }

   @Override
   public QueryResultBaseItemDto getShowsByIdSeasons(String tvId) throws ApiException {
      GetShowsByIdSeasonsRequestDto getShowsByIdSeasonsRequestDto = new GetShowsByIdSeasonsRequestDto();
      getShowsByIdSeasonsRequestDto.setId(tvId);
      return this.getShowsByIdSeasons(getShowsByIdSeasonsRequestDto);
   }

   @Override
   public GetEpisodesByIdResponse getEpisodesById(String tvId) throws ApiException {
      return this.getEpisodesById(tvId, null);
   }

   @Override
   public GetEpisodesByIdResponse getEpisodesById(String tvId, Long embyInfoId) throws ApiException {
      EmbyInfoCacheManagerUtils.EmbyServerConfig serverConfig = this.resolveServerConfig(embyInfoId);
      String jsonResponse = HttpUtil.get(serverConfig.url() + "Shows/" + tvId + "/Episodes?api_key=" + serverConfig.apiKey());
      GetEpisodesByIdResponse getEpisodesByIdResponse = JSONObject.parseObject(jsonResponse, GetEpisodesByIdResponse.class);
      if (getEpisodesByIdResponse.getItems() != null) {
         String baseUrl = serverConfig.url().replaceAll("emby/", "");
         getEpisodesByIdResponse.getItems()
            .forEach(episode -> episode.setEmbyEpisodeUrl(baseUrl + "web/index.html#!/item?id=" + episode.getId() + "&serverId=" + episode.getServerId()));
      }

      return getEpisodesByIdResponse;
   }

   @Override
   public GetEmbyUrlResponse getEmbyUrl(String itemId, String serverId) {
      GetEmbyUrlResponse getEmbyUrlResponse = new GetEmbyUrlResponse();
      String baseUrl = this.getServerUrl().replaceAll("emby/", "");
      getEmbyUrlResponse.setEmbyItemUrl(baseUrl + "web/index.html#!/item?id=" + itemId + "&serverId=" + serverId);
      return getEmbyUrlResponse;
   }

   @Override
   public List<SessionSessionInfoResponse> getNowPlaying() throws ApiException {
      SessionsServiceApi sessionServiceApi = new SessionsServiceApi(this.buildApiClient());
      List<SessionSessionInfo> sessions = sessionServiceApi.getSessions(null, null, null);
      List<SessionSessionInfoResponse> sessionSessionInfoResponses = BeanUtils.convertList(sessions, SessionSessionInfoResponse.class);
      sessionSessionInfoResponses = sessionSessionInfoResponses.stream()
         .filter(sessionSessionInfo -> sessionSessionInfo.getNowPlayingItem() != null)
         .collect(Collectors.toList());
      String baseUrl = this.getServerUrl().replaceAll("emby/", "");
      sessionSessionInfoResponses.forEach(
         sessionSessionInfoResponse -> {
            sessionSessionInfoResponse.setRemoteAddress(this.resolveRemoteAddress(sessionSessionInfoResponse.getRemoteEndPoint()));
            if ("Movie".equals(sessionSessionInfoResponse.getNowPlayingItem().getType())) {
               sessionSessionInfoResponse.setCoverImage(
                  baseUrl
                     + "Items/"
                     + sessionSessionInfoResponse.getNowPlayingItem().getId()
                     + "/Images/Primary?tag="
                     + sessionSessionInfoResponse.getNowPlayingItem().getImageTags().get("Primary")
                     + "&quality=90&maxWidth=200"
               );
            }

            if ("Episode".equals(sessionSessionInfoResponse.getNowPlayingItem().getType())) {
               sessionSessionInfoResponse.setCoverImage(
                  baseUrl
                     + "Items/"
                     + sessionSessionInfoResponse.getNowPlayingItem().getParentId()
                     + "/Images/Primary?tag="
                     + sessionSessionInfoResponse.getNowPlayingItem().getSeriesPrimaryImageTag()
                     + "&quality=90&maxWidth=200"
               );
            }

            sessionSessionInfoResponse.setUserAvatar(
               baseUrl
                  + "Users/"
                  + sessionSessionInfoResponse.getUserId()
                  + "/Images/Primary?tag="
                  + sessionSessionInfoResponse.getUserPrimaryImageTag()
                  + "&quality=90"
            );
         }
      );
      return sessionSessionInfoResponses;
   }

   @Override
   public List<NowPlayingGroupedResponse> getNowPlayingGrouped() throws ApiException {
      List<EmbyInfo> enabledServers = new LambdaQueryChainWrapper<>(this.embyInfoService.getBaseMapper())
         .eq(EmbyInfo::getEnabled, Integer.valueOf(1))
         .eq(EmbyInfo::getStatus, Integer.valueOf(0))
         .list();
      List<NowPlayingGroupedResponse> result = new ArrayList<>();

      for (EmbyInfo embyInfo : enabledServers) {
         NowPlayingGroupedResponse groupedResponse = new NowPlayingGroupedResponse();
         groupedResponse.setServerId(embyInfo.getId());
         groupedResponse.setServerName(embyInfo.getServerName());

         try {
            EmbyInfoCacheManagerUtils.EmbyServerConfig serverConfig = this.embyInfoCacheManager.getRequiredConfigById(embyInfo.getId());
            List<SessionSessionInfoResponse> sessions = this.getNowPlayingForServer(serverConfig);
            groupedResponse.setSessions(sessions);
         } catch (Exception var8) {
            log.error("获取服务器 [{}] 的正在播放信息失败: {}", embyInfo.getServerName(), var8.getMessage());
            groupedResponse.setSessions(new ArrayList<>());
         }

         result.add(groupedResponse);
      }

      return result;
   }

   private List<SessionSessionInfoResponse> getNowPlayingForServer(EmbyInfoCacheManagerUtils.EmbyServerConfig serverConfig) throws ApiException {
      SessionsServiceApi sessionServiceApi = new SessionsServiceApi(this.buildApiClient(serverConfig));
      List<SessionSessionInfo> sessions = sessionServiceApi.getSessions(null, null, null);
      List<SessionSessionInfoResponse> sessionSessionInfoResponses = BeanUtils.convertList(sessions, SessionSessionInfoResponse.class);
      sessionSessionInfoResponses = sessionSessionInfoResponses.stream()
         .filter(sessionSessionInfo -> sessionSessionInfo.getNowPlayingItem() != null)
         .collect(Collectors.toList());
      String baseUrl = serverConfig.url().replaceAll("emby/", "");
      sessionSessionInfoResponses.forEach(
         sessionSessionInfoResponse -> {
            sessionSessionInfoResponse.setRemoteAddress(this.resolveRemoteAddress(sessionSessionInfoResponse.getRemoteEndPoint()));
            if ("Movie".equals(sessionSessionInfoResponse.getNowPlayingItem().getType())) {
               sessionSessionInfoResponse.setCoverImage(
                  baseUrl
                     + "Items/"
                     + sessionSessionInfoResponse.getNowPlayingItem().getId()
                     + "/Images/Primary?tag="
                     + sessionSessionInfoResponse.getNowPlayingItem().getImageTags().get("Primary")
                     + "&quality=90&maxWidth=200"
               );
            }

            if ("Episode".equals(sessionSessionInfoResponse.getNowPlayingItem().getType())) {
               sessionSessionInfoResponse.setCoverImage(
                  baseUrl
                     + "Items/"
                     + sessionSessionInfoResponse.getNowPlayingItem().getParentId()
                     + "/Images/Primary?tag="
                     + sessionSessionInfoResponse.getNowPlayingItem().getSeriesPrimaryImageTag()
                     + "&quality=90&maxWidth=200"
               );
            }

            sessionSessionInfoResponse.setUserAvatar(
               baseUrl
                  + "Users/"
                  + sessionSessionInfoResponse.getUserId()
                  + "/Images/Primary?tag="
                  + sessionSessionInfoResponse.getUserPrimaryImageTag()
                  + "&quality=90"
            );
         }
      );
      return sessionSessionInfoResponses;
   }

   private String resolveRemoteAddress(String remoteEndPoint) {
      return !StringUtils.hasText(remoteEndPoint) ? "" : IpAddressUtils.safeAddressAndIsp(this.searchSearcher, remoteEndPoint);
   }

   @Override
   public EmbySettingsResponse getEmbySettings() {
      return this.buildEmbySettings(this.getServerUrl(), this.getApiKey());
   }

   private EmbySettingsResponse buildEmbySettings(String serverUrl, String apiKey) {
      EmbySettingsResponse embySettingsResponse = new EmbySettingsResponse();
      embySettingsResponse.setEmbyUrl(serverUrl);
      embySettingsResponse.setEmbyKey(apiKey);
      List<EmbyClientAdminUserUtils.AdminUser> adminUserList = EmbyClientAdminUserUtils.listAdministratorsNoPaging(serverUrl, apiKey);
      embySettingsResponse.setAdminUserId(adminUserList.get(0).id);
      return embySettingsResponse;
   }

   @Override
   public JSONObject getShowSeasons(String showId) {
      EmbySettingsResponse embySettings = this.getEmbySettings();
      String url = String.format("%s/Shows/%s/Seasons", embySettings.getEmbyUrl(), showId);
      Map<String, Object> params = new HashMap<>();
      params.put("UserId", embySettings.getAdminUserId());
      params.put("IsMissing", false);
      params.put("ExcludeLocationTypes", "Virtual,Remote");
      params.put("api_key", embySettings.getEmbyKey());

      try {
         HttpRequest request = HttpRequest.get(url).form(params).header(Header.ACCEPT, "application/json, text/plain, */*");
         log.info("正在执行请求，URL: {}", request.getUrl());
         HttpResponse response = request.execute();
         if (response.isOk()) {
            String responseBody = response.body();
            log.info("成功从Emby API获取响应。");
            return JSON.parseObject(responseBody);
         } else {
            log.error("调用Emby API失败。状态码: {}, 响应体: {}", response.getStatus(), response.body());
            return null;
         }
      } catch (Exception var8) {
         log.error("调用Emby API时发生异常", (Throwable)var8);
         return null;
      }
   }

   @Override
   public String getEmbySettingsUrl() {
      return this.getServerUrl();
   }

   @Override
   public PublisherSearchResponse searchByPublisher(PublisherSearchRequest request) throws ApiException {
      PublisherSearchResponse response = new PublisherSearchResponse();
      response.setPublisher(request.getPublisher());
      GetItemsRequest getItemsRequest = new GetItemsRequest();
      getItemsRequest.setIncludeItemTypes(StringUtils.hasText(request.getIncludeItemTypes()) ? request.getIncludeItemTypes() : "Movie,Series");
      getItemsRequest.setStartIndex(Optional.ofNullable(request.getPage()).orElse(1));
      getItemsRequest.setLimit(Optional.ofNullable(request.getLimit()).orElse(50));
      getItemsRequest.setStudios(request.getPublisher());
      getItemsRequest.setGenres(request.getGenres());
      QueryResultBaseItemResponse queryResult = this.getItems(getItemsRequest);
      response.setItems(queryResult);
      PublisherSearchResponse.SearchMeta meta = new PublisherSearchResponse.SearchMeta();
      meta.setPage(Optional.ofNullable(request.getPage()).orElse(1));
      meta.setLimit(getItemsRequest.getLimit());
      meta.setIncludeItemTypes(getItemsRequest.getIncludeItemTypes());
      meta.setGenres(request.getGenres());
      response.setMeta(meta);
      return response;
   }

   @Override
   public List<EmbyStudioPresetResponse> getStudioPresets(Long embyInfoId) {
      EmbyInfoCacheManagerUtils.EmbyServerConfig config = embyInfoId != null
         ? this.embyInfoCacheManager.getRequiredConfigById(embyInfoId)
         : this.embyInfoCacheManager.getRequiredConfig();
      List<EmbyStudioPresetResponse.Studio> studios = this.embyStudioCacheService.getCachedStudios(config);
      if (!this.embyStudioCacheService.hasCache(config)) {
         this.embyStudioCacheService.refreshIfMissingAsync(config);
      }

      return FEATURED_STUDIO_ALIASES.entrySet()
         .stream()
         .map(entry -> this.buildStudioPreset(entry.getKey(), entry.getValue(), studios))
         .collect(Collectors.toList());
   }

   private EmbyStudioPresetResponse buildStudioPreset(String label, List<String> aliases, List<EmbyStudioPresetResponse.Studio> studios) {
      EmbyStudioPresetResponse response = new EmbyStudioPresetResponse();
      response.setLabel(label);
      response.setAliases(new ArrayList<>(aliases));
      List<EmbyStudioPresetResponse.Studio> matched = new ArrayList<>();
      Set<String> matchedIds = new LinkedHashSet<>();

      for (EmbyStudioPresetResponse.Studio studio : studios) {
         if (EmbyStudioAliasUtils.matchesAlias(studio.getName(), aliases) && matchedIds.add(studio.getId())) {
            EmbyStudioPresetResponse.Studio item = new EmbyStudioPresetResponse.Studio();
            item.setId(studio.getId());
            item.setName(studio.getName());
            matched.add(item);
         }
      }

      response.setStudios(matched);
      response.setStudioIds(String.join(",", matchedIds));
      return response;
   }

   public QueryResultBaseItemDto getShowsByIdSeasons(GetShowsByIdSeasonsRequestDto param) throws ApiException {
      TvShowsServiceApi tvShowsServiceApi = new TvShowsServiceApi(this.buildApiClient());
      OffsetDateTime minStartDate = this.parseOffsetDateTime(param.getMinStartDate());
      OffsetDateTime maxStartDate = this.parseOffsetDateTime(param.getMaxStartDate());
      OffsetDateTime minEndDate = this.parseOffsetDateTime(param.getMinEndDate());
      OffsetDateTime maxEndDate = this.parseOffsetDateTime(param.getMaxEndDate());
      OffsetDateTime minPremiereDate = this.parseOffsetDateTime(param.getMinPremiereDate());
      OffsetDateTime minDateLastSaved = this.parseOffsetDateTime(param.getMinDateLastSaved());
      OffsetDateTime minDateLastSavedForUser = this.parseOffsetDateTime(param.getMinDateLastSavedForUser());
      OffsetDateTime maxPremiereDate = this.parseOffsetDateTime(param.getMaxPremiereDate());
      return tvShowsServiceApi.getShowsByIdSeasons(
         param.getId(),
         param.getArtistType(),
         param.getMaxOfficialRating(),
         param.getHasThemeSong(),
         param.getHasThemeVideo(),
         param.getHasSubtitles(),
         param.getHasSpecialFeature(),
         param.getHasTrailer(),
         param.getIsSpecialSeason(),
         param.getAdjacentTo(),
         param.getStartItemId(),
         param.getMinIndexNumber(),
         minStartDate,
         maxStartDate,
         minEndDate,
         maxEndDate,
         param.getMinPlayers(),
         param.getMaxPlayers(),
         param.getParentIndexNumber(),
         param.getHasParentalRating(),
         param.getIsHD(),
         param.getIsUnaired(),
         param.getMinCommunityRating(),
         param.getMinCriticRating(),
         param.getAiredDuringSeason(),
         minPremiereDate,
         minDateLastSaved,
         minDateLastSavedForUser,
         maxPremiereDate,
         param.getHasOverview(),
         param.getHasImdbId(),
         param.getHasTmdbId(),
         param.getHasTvdbId(),
         param.getExcludeItemIds(),
         param.getStartIndex(),
         param.getLimit(),
         param.getRecursive(),
         param.getSearchTerm(),
         param.getSortOrder(),
         param.getParentId(),
         param.getFields(),
         param.getExcludeItemTypes(),
         param.getIncludeItemTypes(),
         param.getAnyProviderIdEquals(),
         param.getFilters(),
         param.getIsFavorite(),
         param.getIsMovie(),
         param.getIsSeries(),
         param.getIsFolder(),
         param.getIsNews(),
         param.getIsKids(),
         param.getIsSports(),
         param.getIsNew(),
         param.getIsPremiere(),
         param.getIsNewOrPremiere(),
         param.getIsRepeat(),
         param.getProjectToMedia(),
         param.getMediaTypes(),
         param.getImageTypes(),
         param.getSortBy(),
         param.getIsPlayed(),
         param.getGenres(),
         param.getOfficialRatings(),
         param.getTags(),
         param.getExcludeTags(),
         param.getYears(),
         param.getEnableImages(),
         param.getEnableUserData(),
         param.getImageTypeLimit(),
         param.getEnableImageTypes(),
         param.getPerson(),
         param.getPersonIds(),
         param.getPersonTypes(),
         param.getStudios(),
         param.getStudioIds(),
         param.getArtists(),
         param.getArtistIds(),
         param.getAlbums(),
         param.getIds(),
         param.getVideoTypes(),
         param.getContainers(),
         param.getAudioCodecs(),
         param.getAudioLayouts(),
         param.getVideoCodecs(),
         param.getExtendedVideoTypes(),
         param.getSubtitleCodecs(),
         param.getPath(),
         param.getUserId(),
         param.getMinOfficialRating(),
         param.getIsLocked(),
         param.getIsPlaceHolder(),
         param.getHasOfficialRating(),
         param.getGroupItemsIntoCollections(),
         param.getIs3D(),
         param.getSeriesStatus(),
         param.getNameStartsWithOrGreater(),
         param.getArtistStartsWithOrGreater(),
         param.getAlbumArtistStartsWithOrGreater(),
         param.getNameStartsWith(),
         param.getNameLessThan()
      );
   }

   private OffsetDateTime parseOffsetDateTime(String date) {
      if (!StringUtils.hasText(date)) {
         return null;
      } else {
         DateTime dateTime = DateUtil.parse(date);
         return OffsetDateTime.ofInstant(dateTime.toInstant(), ZoneId.systemDefault());
      }
   }

   private String extractMovieTmdbId(String url) {
      String tmdbId = this.extractPatternGroup(url, TMDB_MOVIE_URL_PATTERN);
      return StringUtils.hasText(tmdbId) ? tmdbId : this.extractPatternGroup(url, TRAILING_NUMBER_PATTERN);
   }

   private String extractSeriesTmdbId(String url) {
      return this.extractPatternGroup(url, TMDB_TV_URL_PATTERN);
   }

   private String extractPatternGroup(String value, Pattern pattern) {
      if (!StringUtils.hasText(value)) {
         return "";
      } else {
         Matcher matcher = pattern.matcher(value);
         return matcher.find() ? matcher.group(1) : "";
      }
   }

   private Integer parseIntegerId(String value) {
      return !this.isNumericId(value) ? null : Integer.valueOf(value);
   }

   private boolean isNumericId(String value) {
      return StringUtils.hasText(value) && value.matches("\\d+");
   }

   private String resolveSeriesTmdbIdFromEmby(LibraryNewRequest.ItemDTO item, EmbyInfoCacheManagerUtils.EmbyServerConfig serverConfig) {
      if (item == null) {
         return "";
      } else {
         String tmdbId = this.resolveSeriesTmdbIdByEmbyId(item.getSeriesId(), serverConfig);
         if (StringUtils.hasText(tmdbId)) {
            return tmdbId;
         } else {
            tmdbId = this.resolveSeriesTmdbIdFromRelatedEmbyId(item.getSeasonId(), serverConfig);
            return StringUtils.hasText(tmdbId) ? tmdbId : this.resolveSeriesTmdbIdFromRelatedEmbyId(item.getParentId(), serverConfig);
         }
      }
   }

   private String resolveSeriesTmdbIdFromRelatedEmbyId(String itemId, EmbyInfoCacheManagerUtils.EmbyServerConfig serverConfig) {
      if (!StringUtils.hasText(itemId)) {
         return "";
      } else {
         QueryResultBaseItemResponse response = this.getItemsById(itemId, serverConfig);
         if (response != null && !CollectionUtils.isEmpty(response.getItems())) {
            for (QueryResultBaseItemResponse.ItemsDTO item : response.getItems()) {
               if ("Series".equals(item.getType())) {
                  String tmdbId = this.getProviderTmdbId(item.getProviderIds());
                  if (StringUtils.hasText(tmdbId)) {
                     return tmdbId;
                  }
               }

               if (StringUtils.hasText(item.getSeriesId()) && !itemId.equals(item.getSeriesId())) {
                  String tmdbId = this.resolveSeriesTmdbIdByEmbyId(item.getSeriesId(), serverConfig);
                  if (StringUtils.hasText(tmdbId)) {
                     return tmdbId;
                  }
               }
            }

            return "";
         } else {
            return "";
         }
      }
   }

   private String resolveSeriesTmdbIdByEmbyId(String itemId, EmbyInfoCacheManagerUtils.EmbyServerConfig serverConfig) {
      if (!StringUtils.hasText(itemId)) {
         return "";
      } else {
         QueryResultBaseItemResponse response = this.getItemsById(itemId, serverConfig);
         if (response != null && !CollectionUtils.isEmpty(response.getItems())) {
            for (QueryResultBaseItemResponse.ItemsDTO item : response.getItems()) {
               if ("Series".equals(item.getType())) {
                  String tmdbId = this.getProviderTmdbId(item.getProviderIds());
                  if (StringUtils.hasText(tmdbId)) {
                     return tmdbId;
                  }
               }
            }

            return "";
         } else {
            return "";
         }
      }
   }

   private String getProviderTmdbId(ProviderIdDictionary providerIds) {
      if (providerIds == null) {
         return "";
      } else {
         String tmdbId = providerIds.get("Tmdb");
         return this.isNumericId(tmdbId) ? tmdbId : "";
      }
   }

   private RequestList getRequestList(LibraryNewRequest libraryNewRequest, Integer tmdbid, Long embyInfoId, String embyServerId) {
      if (libraryNewRequest == null || libraryNewRequest.getItem() == null || tmdbid == null || tmdbid == 0) {
         return null;
      } else if (embyInfoId == null && !StringUtils.hasText(embyServerId)) {
         log.warn("新增资源：{}，缺少服务器信息，跳过求片匹配", libraryNewRequest.getItem().getName());
         return null;
      } else {
         String itemType = libraryNewRequest.getItem().getType();
         LambdaQueryChainWrapper<RequestList> query = new LambdaQueryChainWrapper<>(this.requestListService.getBaseMapper())
            .in(RequestList::getStatus, Arrays.asList(0, 1))
            .eq(embyInfoId != null, RequestList::getEmbyInfoId, embyInfoId)
            .eq(StringUtils.hasText(embyServerId), RequestList::getEmbyServerId, embyServerId);
         if ("Movie".equals(itemType)) {
            query.eq(RequestList::getType, "movie").eq(RequestList::getTmdbId, tmdbid);
         } else {
            if (!"Series".equals(itemType) && !"Episode".equals(itemType)) {
               return null;
            }

            Integer seasonNumber = libraryNewRequest.getItem().getParentIndexNumber();
            if (seasonNumber == null || seasonNumber <= 0) {
               log.warn("新增剧集资源：{}，缺少季号，跳过求片匹配", libraryNewRequest.getItem().getName());
               return null;
            }

            query.eq(RequestList::getType, "tv").eq(RequestList::getParentTmdbId, tmdbid).eq(RequestList::getSeason, seasonNumber);
         }

         RequestList requestList = query.orderByAsc(RequestList::getStatus).orderByDesc(RequestList::getId).last("limit 1").one();
         if (requestList != null) {
            if (Integer.valueOf(0).equals(requestList.getStatus())) {
               log.info("新增资源：{}，匹配到求片列表：{}", libraryNewRequest.getItem().getName(), requestList.getName());
               requestList.setStatus(1);
               requestList.setAuditStatus(1);
               if (embyInfoId != null) {
                  requestList.setEmbyInfoId(embyInfoId);
               }

               if (StringUtils.hasText(embyServerId)) {
                  requestList.setEmbyServerId(embyServerId);
               }

               this.requestListService.updateById(requestList);
            } else {
               log.info("新增资源：{}，匹配到已入库求片列表用于补充通知：{}", libraryNewRequest.getItem().getName(), requestList.getName());
            }
         }

         return requestList;
      }
   }

   public TmdbSearchResponse search(String searchName, String type) {
      TmdbSearchResponse tmdbSearchResponse = new TmdbSearchResponse();

      try {
         List<Multi> multiList = this.tmdbService.search(searchName, 1).getResults();
         if (CollectionUtils.isEmpty(multiList)) {
            multiList = this.tmdbService.search(searchName, 1).getResults();
         }

         multiList.stream().filter(multi -> {
            if ("Movie".equals(type) && multi instanceof MultiMovie) {
               boolean equals = searchName.equals(((MultiMovie)multi).getTitle());
               if (equals) {
                  tmdbSearchResponse.setImgUrl(this.buildTmdbImageUrl(((MultiMovie)multi).getPosterPath()));
                  tmdbSearchResponse.setBackdropPath(this.buildTmdbImageUrl(((MultiMovie)multi).getBackdropPath()));
                  tmdbSearchResponse.setOverview(((MultiMovie)multi).getOverview());
                  tmdbSearchResponse.setReleaseDate(((MultiMovie)multi).getReleaseDate());
                  tmdbSearchResponse.setTmdbId(((MultiMovie)multi).getId());
                  tmdbSearchResponse.setVoteAverage(((MultiMovie)multi).getVoteAverage());
                  tmdbSearchResponse.setVoteCount(((MultiMovie)multi).getVoteCount());
                  return true;
               }
            }

            if (("Episode".equals(type) || "Series".equals(type)) && multi instanceof MultiTvSeries && searchName.equals(((MultiTvSeries)multi).getName())) {
               tmdbSearchResponse.setImgUrl(this.buildTmdbImageUrl(((MultiTvSeries)multi).getPosterPath()));
               tmdbSearchResponse.setBackdropPath(this.buildTmdbImageUrl(((MultiTvSeries)multi).getBackdropPath()));
               tmdbSearchResponse.setOverview(((MultiTvSeries)multi).getOverview());
               tmdbSearchResponse.setReleaseDate(((MultiTvSeries)multi).getFirstAirDate());
               tmdbSearchResponse.setTmdbId(((MultiTvSeries)multi).getId());
               tmdbSearchResponse.setVoteAverage(((MultiTvSeries)multi).getVoteAverage());
               tmdbSearchResponse.setVoteCount(((MultiTvSeries)multi).getVoteCount());
               return true;
            } else {
               return false;
            }
         }).collect(Collectors.toList());
      } catch (TmdbException var5) {
         log.error("获取tmdb图片失败：{}", var5.getMessage());
      }

      this.enrichSearchResponseWithDetails(tmdbSearchResponse, tmdbSearchResponse.getTmdbId(), type);
      return tmdbSearchResponse;
   }

   private void enrichSearchResponseWithDetails(TmdbSearchResponse tmdbSearchResponse, Integer tmdbId, String type) {
      if (tmdbSearchResponse != null && tmdbId != null) {
         try {
            if ("Movie".equals(type)) {
               MovieDb movieDb = this.tmdbService.getMovieDetails(tmdbId, "zh-CN");
               if (movieDb != null) {
                  if (tmdbSearchResponse.getRuntime() == null) {
                     tmdbSearchResponse.setRuntime(movieDb.getRuntime());
                  }

                  if (!StringUtils.hasText(tmdbSearchResponse.getProductionCountries())) {
                     tmdbSearchResponse.setProductionCountries(this.joinProductionCountries(movieDb.getProductionCountries()));
                  }

                  if (!StringUtils.hasText(tmdbSearchResponse.getReleaseDate())) {
                     tmdbSearchResponse.setReleaseDate(movieDb.getReleaseDate());
                  }
               }
            } else if ("Episode".equals(type) || "Series".equals(type)) {
               TvSeriesDb tvSeries = this.tmdbService.getTvSeries(tmdbId, "zh-CN");
               if (tvSeries != null) {
                  if (tmdbSearchResponse.getRuntime() == null && !CollectionUtils.isEmpty(tvSeries.getEpisodeRunTime())) {
                     tmdbSearchResponse.setRuntime(tvSeries.getEpisodeRunTime().get(0));
                  }

                  if (!StringUtils.hasText(tmdbSearchResponse.getProductionCountries())) {
                     tmdbSearchResponse.setProductionCountries(this.joinProductionCountries(tvSeries.getOriginCountry()));
                  }

                  if (!StringUtils.hasText(tmdbSearchResponse.getReleaseDate())) {
                     tmdbSearchResponse.setReleaseDate(tvSeries.getFirstAirDate());
                  }
               }
            }
         } catch (TmdbException var5) {
            log.warn("补充 TMDB 元数据失败: {}", var5.getMessage());
         }
      }
   }

   private String joinProductionCountries(List<?> countries) {
      return CollectionUtils.isEmpty(countries) ? "" : countries.stream().map(country -> {
         if (country == null) {
            return null;
         } else if (country instanceof CharSequence) {
            return country.toString();
         } else {
            try {
               Object name = country.getClass().getMethod("getName").invoke(country);
               return name == null ? null : name.toString();
            } catch (ReflectiveOperationException var2) {
               return country.toString();
            }
         }
      }).filter(StringUtils::hasText).distinct().collect(Collectors.joining("/"));
   }

   private String buildTmdbImageUrl(String path) {
      if (!StringUtils.hasText(path)) {
         return "";
      } else {
         String value = path.trim();
         if ("null".equalsIgnoreCase(value)) {
            return "";
         } else if (value.startsWith("http://") || value.startsWith("https://")) {
            return value;
         } else if (!StringUtils.hasText(this.imageUrl)) {
            return "";
         } else {
            String base = this.imageUrl.trim();
            if (base.endsWith("/") && value.startsWith("/")) {
               return base.substring(0, base.length() - 1) + value;
            } else {
               return !base.endsWith("/") && !value.startsWith("/") ? base + "/" + value : base + value;
            }
         }
      }
   }

   private boolean isHttpUrl(String url) {
      if (!StringUtils.hasText(url)) {
         return false;
      } else {
         String value = url.trim().toLowerCase(Locale.ROOT);
         return value.startsWith("http://") || value.startsWith("https://");
      }
   }

   private EmbyServiceImpl.MediaStreamNotifyInfo buildMediaStreamNotifyInfo(LibraryNewRequest.ItemDTO item) {
      if (item == null) {
         return EmbyServiceImpl.MediaStreamNotifyInfo.empty();
      } else {
         List<JSONObject> streams = this.collectMediaStreams(item);
         JSONObject videoStream = this.firstStream(streams, "Video");
         JSONObject audioStream = this.firstStream(streams, "Audio");
         String videoQuality = this.buildVideoQuality(item.getWidth(), item.getHeight(), videoStream);
         String audioQuality = this.buildAudioQuality(audioStream);
         String subtitleInfo = this.hasChineseSubtitle(streams) ? "中文" : "";
         return new EmbyServiceImpl.MediaStreamNotifyInfo(videoQuality, audioQuality, subtitleInfo);
      }
   }

   private EmbyServiceImpl.MediaStreamNotifyInfo fetchMediaStreamNotifyInfo(EmbyInfoCacheManagerUtils.EmbyServerConfig serverConfig, String itemId) {
      JSONObject itemDetail = this.fetchEmbyItemDetail(serverConfig, itemId);
      return itemDetail == null ? EmbyServiceImpl.MediaStreamNotifyInfo.empty() : this.buildMediaStreamNotifyInfo(itemDetail);
   }

   private EmbyServiceImpl.MediaStreamNotifyInfo buildMediaStreamNotifyInfo(JSONObject item) {
      if (item == null) {
         return EmbyServiceImpl.MediaStreamNotifyInfo.empty();
      } else {
         List<JSONObject> streams = this.collectMediaStreams(item);
         JSONObject videoStream = this.firstStream(streams, "Video");
         JSONObject audioStream = this.firstStream(streams, "Audio");
         String videoQuality = this.buildVideoQuality(this.firstInteger(item, "Width"), this.firstInteger(item, "Height"), videoStream);
         String audioQuality = this.buildAudioQuality(audioStream);
         String subtitleInfo = this.hasChineseSubtitle(streams) ? "中文" : "";
         return new EmbyServiceImpl.MediaStreamNotifyInfo(videoQuality, audioQuality, subtitleInfo);
      }
   }

   private List<JSONObject> collectMediaStreams(LibraryNewRequest.ItemDTO item) {
      List<JSONObject> streams = new ArrayList<>();
      this.addJsonObjects(streams, item.getMediaStreams());
      if (!CollectionUtils.isEmpty(item.getMediaSources())) {
         for (JSONObject mediaSource : item.getMediaSources()) {
            if (mediaSource != null) {
               this.addJsonObjects(streams, mediaSource.get("MediaStreams"));
            }
         }
      }

      return streams;
   }

   private List<JSONObject> collectMediaStreams(JSONObject item) {
      List<JSONObject> streams = new ArrayList<>();
      this.addJsonObjects(streams, item.get("MediaStreams"));
      this.addJsonObjects(streams, item.get("mediaStreams"));
      Object mediaSources = item.get("MediaSources");
      if (mediaSources == null) {
         mediaSources = item.get("mediaSources");
      }

      if (mediaSources instanceof Collection) {
         for (Object mediaSource : (Collection)mediaSources) {
            if (mediaSource instanceof JSONObject object) {
               this.addJsonObjects(streams, object.get("MediaStreams"));
               this.addJsonObjects(streams, object.get("mediaStreams"));
            }
         }
      }

      return streams;
   }

   private JSONObject fetchEmbyItemDetail(EmbyInfoCacheManagerUtils.EmbyServerConfig serverConfig, String itemId) {
      if (serverConfig != null && StringUtils.hasText(serverConfig.url()) && StringUtils.hasText(serverConfig.apiKey()) && StringUtils.hasText(itemId)) {
         Set<String> candidatePaths = new LinkedHashSet<>();
         String queryUserId = this.firstNonBlank(serverConfig.adminQueryUserid(), serverConfig.copyfromuserid());
         if (StringUtils.hasText(queryUserId)) {
            candidatePaths.add("Users/" + queryUserId + "/Items/" + itemId);
         }

         candidatePaths.add("Items/" + itemId);

         for (String path : candidatePaths) {
            String url = this.buildEmbyApiUrl(serverConfig.url(), path);

            try {
               JSONObject var9;
               try (HttpResponse response = HttpRequest.get(url)
                     .header(Header.ACCEPT, "application/json")
                     .header("X-Emby-Token", serverConfig.apiKey())
                     .form("Fields", "MediaSources,MediaStreams,Width,Height,Path,Size,Type,Name")
                     .timeout(5000)
                     .execute()) {
                  if (!response.isOk() || !StringUtils.hasText(response.body())) {
                     log.warn("入库通知读取 Emby 媒体信息失败，itemId={}, status={}", itemId, response.getStatus());
                     continue;
                  }

                  var9 = JSON.parseObject(response.body());
               }

               return var9;
            } catch (Exception var13) {
               log.warn("入库通知读取 Emby 媒体信息异常，itemId={}, error={}", itemId, var13.getMessage());
            }
         }

         return null;
      } else {
         return null;
      }
   }

   private String buildEmbyApiUrl(String baseUrl, String path) {
      String base = baseUrl.trim();
      String target = path.startsWith("/") ? path.substring(1) : path;
      if (!base.endsWith("/")) {
         base = base + "/";
      }

      return base + target;
   }

   private String firstNonBlank(String... values) {
      if (values == null) {
         return "";
      } else {
         for (String value : values) {
            if (StringUtils.hasText(value)) {
               return value.trim();
            }
         }

         return "";
      }
   }

   private void addJsonObjects(List<JSONObject> target, Object source) {
      if (source instanceof Collection) {
         for (Object value : (Collection)source) {
            if (value instanceof JSONObject object) {
               target.add(object);
            }
         }
      }
   }

   private JSONObject firstStream(List<JSONObject> streams, String type) {
      if (CollectionUtils.isEmpty(streams)) {
         return null;
      } else {
         for (JSONObject stream : streams) {
            if (stream != null && type.equalsIgnoreCase(this.firstText(stream, "Type"))) {
               return stream;
            }
         }

         return null;
      }
   }

   private String buildVideoQuality(Integer fallbackWidth, Integer fallbackHeight, JSONObject videoStream) {
      String displayTitle = this.firstText(videoStream, "DisplayTitle");
      if (StringUtils.hasText(displayTitle)) {
         return MovieFormatTranslatorUtils.translate(this.enrichVideoQualityText(displayTitle, videoStream));
      } else {
         Integer width = this.firstInteger(videoStream, "Width");
         Integer height = this.firstInteger(videoStream, "Height");
         if (width == null || width <= 0) {
            width = fallbackWidth;
         }

         if (height == null || height <= 0) {
            height = fallbackHeight;
         }

         String resolution = this.buildResolutionText(width, height);
         String videoRange = this.firstText(videoStream, "VideoRange", "VideoRangeType");
         if (!StringUtils.hasText(resolution)) {
            return "";
         } else {
            List<String> parts = new ArrayList<>();
            parts.add(resolution);
            String dolbyVision = this.hasDolbyVision(videoStream) ? "Dolby Vision" : "";
            if (StringUtils.hasText(dolbyVision)) {
               parts.add(dolbyVision);
            }

            String hdrText = this.normalizeHdrText(videoRange, videoStream);
            if (StringUtils.hasText(hdrText) && !this.containsIgnoreCase(String.join(" ", parts), hdrText)) {
               parts.add(hdrText);
            }

            return MovieFormatTranslatorUtils.translate(String.join(" ", parts));
         }
      }
   }

   private String buildAudioQuality(JSONObject audioStream) {
      String displayTitle = this.firstText(audioStream, "DisplayTitle");
      if (StringUtils.hasText(displayTitle)) {
         return MovieFormatTranslatorUtils.translate(this.enrichAudioQualityText(displayTitle, audioStream));
      } else {
         List<String> parts = new ArrayList<>();
         String codec = this.normalizeCodec(this.firstText(audioStream, "Codec"));
         String profile = this.firstText(audioStream, "Profile");
         String channelLayout = this.firstText(audioStream, "ChannelLayout");
         Integer channels = this.firstInteger(audioStream, "Channels");
         if (StringUtils.hasText(codec)) {
            parts.add(codec);
         }

         if (StringUtils.hasText(profile) && !parts.contains(profile)) {
            parts.add(profile);
         }

         if (StringUtils.hasText(channelLayout)) {
            parts.add(channelLayout);
         } else if (channels != null && channels > 0) {
            parts.add(channels + "声道");
         }

         if (this.hasAtmos(audioStream)) {
            parts.add("Dolby Atmos");
         }

         return parts.isEmpty() ? "" : MovieFormatTranslatorUtils.translate(String.join(" ", parts));
      }
   }

   private String enrichVideoQualityText(String base, JSONObject videoStream) {
      List<String> parts = new ArrayList<>();
      parts.add(base);
      if (this.hasDolbyVision(videoStream) && !this.containsAnyIgnoreCase(base, "dolby vision", "dovi", "杜比视界")) {
         parts.add("Dolby Vision");
      }

      String hdrText = this.normalizeHdrText(this.firstText(videoStream, "VideoRange", "VideoRangeType"), videoStream);
      String current = String.join(" ", parts);
      if (StringUtils.hasText(hdrText) && !this.containsAnyIgnoreCase(current, hdrText, "hdr", "hlg") && !"Dolby Vision".equalsIgnoreCase(hdrText)) {
         parts.add(hdrText);
      }

      return String.join(" ", parts);
   }

   private String enrichAudioQualityText(String base, JSONObject audioStream) {
      List<String> parts = new ArrayList<>();
      parts.add(base);
      String codec = this.normalizeCodec(this.firstText(audioStream, "Codec"));
      if (StringUtils.hasText(codec)
         && !this.containsIgnoreCase(base, codec)
         && (!this.containsIgnoreCase(base, "dolby digital") || !"DD".equals(codec) && !"DDP".equals(codec))) {
         parts.add(codec);
      }

      if (this.hasAtmos(audioStream) && !this.containsAnyIgnoreCase(base, "atmos", "全景声")) {
         parts.add("Dolby Atmos");
      }

      return String.join(" ", parts);
   }

   private boolean hasDolbyVision(JSONObject videoStream) {
      return this.containsAnyIgnoreCase(this.streamText(videoStream), "dolby vision", "dovi", "dvhe", "dvh1");
   }

   private boolean hasAtmos(JSONObject audioStream) {
      return this.containsAnyIgnoreCase(this.streamText(audioStream), "atmos", "truehd atmos", "eac3 atmos", "全景声");
   }

   private String normalizeHdrText(String raw, JSONObject videoStream) {
      String value = this.firstNonBlank(raw, this.firstText(videoStream, "VideoRangeType", "ColorTransfer", "ColorPrimaries"));
      String lower = value.toLowerCase(Locale.ROOT);
      String all = this.streamText(videoStream).toLowerCase(Locale.ROOT);
      if (!all.contains("hdr10+") && !all.contains("hdr10plus")) {
         if (this.containsAnyIgnoreCase(value, "dovi", "dolby vision")) {
            return "Dolby Vision";
         } else if (lower.contains("hdr10") || lower.contains("smpte2084")) {
            return "HDR10";
         } else if (lower.contains("hlg") || lower.contains("arib-std-b67")) {
            return "HLG";
         } else {
            return !lower.contains("hdr") && !all.contains("\"ishdr\":true") ? "" : "HDR";
         }
      } else {
         return "HDR10+";
      }
   }

   private String streamText(JSONObject stream) {
      return stream == null ? "" : stream.toJSONString();
   }

   private boolean containsIgnoreCase(String text, String needle) {
      return StringUtils.hasText(text) && StringUtils.hasText(needle) && text.toLowerCase(Locale.ROOT).contains(needle.toLowerCase(Locale.ROOT));
   }

   private boolean containsAnyIgnoreCase(String text, String... needles) {
      if (StringUtils.hasText(text) && needles != null) {
         for (String needle : needles) {
            if (this.containsIgnoreCase(text, needle)) {
               return true;
            }
         }

         return false;
      } else {
         return false;
      }
   }

   private boolean hasChineseSubtitle(List<JSONObject> streams) {
      if (CollectionUtils.isEmpty(streams)) {
         return false;
      } else {
         for (JSONObject stream : streams) {
            if (stream != null && "Subtitle".equalsIgnoreCase(this.firstText(stream, "Type"))) {
               String language = this.firstText(stream, "Language");
               if (this.isChineseSubtitleLanguage(language)) {
                  return true;
               }

               String text = String.join(
                  " ",
                  this.safeStreamText(language),
                  this.safeStreamText(this.firstText(stream, "DisplayLanguage")),
                  this.safeStreamText(this.firstText(stream, "Title")),
                  this.safeStreamText(this.firstText(stream, "DisplayTitle")),
                  this.safeStreamText(this.firstText(stream, "Codec"))
               );
               String lower = text.toLowerCase(Locale.ROOT);
               if (lower.contains("chinese")
                  || lower.contains("mandarin")
                  || lower.contains("cantonese")
                  || text.contains("中文")
                  || text.contains("简")
                  || text.contains("繁")) {
                  return true;
               }
            }
         }

         return false;
      }
   }

   private boolean isChineseSubtitleLanguage(String language) {
      if (!StringUtils.hasText(language)) {
         return false;
      } else {
         String value = language.trim().toLowerCase(Locale.ROOT);
         return value.equals("zh")
            || value.startsWith("zh-")
            || value.equals("chi")
            || value.equals("zho")
            || value.equals("chs")
            || value.equals("cht")
            || value.equals("cmn")
            || value.equals("yue");
      }
   }

   private String buildResolutionText(Integer width, Integer height) {
      int w = width != null ? width : 0;
      int h = height != null ? height : 0;
      if (w >= 7600 || h >= 4000) {
         return "4320p";
      } else if (w >= 3800 || h >= 2000) {
         return "2160p";
      } else if (w >= 2500 || h >= 1300) {
         return "1440p";
      } else if (h >= 1000) {
         return "1080p";
      } else if (h >= 700) {
         return "720p";
      } else if (h >= 560) {
         return "576p";
      } else if (h >= 450) {
         return "480p";
      } else {
         return h > 0 ? h + "p" : "";
      }
   }

   private Integer firstInteger(JSONObject object, String key) {
      if (object != null && StringUtils.hasText(key)) {
         try {
            return object.getInteger(key);
         } catch (Exception var4) {
            return null;
         }
      } else {
         return null;
      }
   }

   private String firstText(JSONObject object, String... keys) {
      if (object != null && keys != null) {
         for (String key : keys) {
            String value = this.safeStreamText(object.getString(key));
            if (StringUtils.hasText(value)) {
               return value;
            }
         }

         return "";
      } else {
         return "";
      }
   }

   private String safeStreamText(String value) {
      if (!StringUtils.hasText(value)) {
         return "";
      } else {
         String text = value.replaceAll("\\s+", " ").trim();
         String lower = text.toLowerCase(Locale.ROOT);
         if (!"unknown".equals(lower) && !"undefined".equals(lower) && !"null".equals(lower) && !"未知".equals(text) && !"未记录".equals(text)) {
            return text.length() > 80 ? text.substring(0, 79) + "…" : text;
         } else {
            return "";
         }
      }
   }

   private String normalizeCodec(String codec) {
      if (!StringUtils.hasText(codec)) {
         return "";
      } else {
         String value = codec.trim();
         String lower = value.toLowerCase(Locale.ROOT);

         return switch (lower) {
            case "h264", "avc" -> "H264";
            case "h265", "hevc" -> "H265";
            case "eac3" -> "DDP";
            case "ac3" -> "DD";
            case "dca", "dts" -> "DTS";
            case "truehd" -> "TrueHD";
            case "aac" -> "AAC";
            case "flac" -> "FLAC";
            case "opus" -> "Opus";
            default -> value;
         };
      }
   }

   private boolean shouldReplaceMediaText(String existing, String candidate) {
      if (!StringUtils.hasText(candidate)) {
         return false;
      } else if (!StringUtils.hasText(existing)) {
         return true;
      } else {
         String oldText = existing.trim();
         String newText = candidate.trim();
         return !oldText.equals(newText) && newText.length() > oldText.length();
      }
   }

   @PreDestroy
   public void shutdownPlaybackImageExecutor() {
      this.playbackImageExecutor.shutdown();

      try {
         if (!this.playbackImageExecutor.awaitTermination(5L, TimeUnit.SECONDS)) {
            this.playbackImageExecutor.shutdownNow();
         }
      } catch (InterruptedException var2) {
         this.playbackImageExecutor.shutdownNow();
         Thread.currentThread().interrupt();
      }
   }

   private BufferedImage awaitImageResult(CompletableFuture<BufferedImage> future, long timeout, TimeUnit unit) {
      if (future == null) {
         return null;
      } else {
         try {
            return future.get(timeout, unit);
         } catch (InterruptedException var6) {
            Thread.currentThread().interrupt();
            future.cancel(true);
            return null;
         } catch (Exception var7) {
            future.cancel(true);
            return null;
         }
      }
   }

   private String formatDuration(int minutes) {
      if (minutes <= 0) {
         return "";
      } else {
         int hours = minutes / 60;
         int mins = minutes % 60;
         if (hours > 0 && mins > 0) {
            return hours + "小时" + mins + "分钟";
         } else {
            return hours > 0 ? hours + "小时" : mins + "分钟";
         }
      }
   }

   private static record ClientFilterTerminateResult(boolean stopSuccess, boolean messageSuccess) {
   }

   private static record MediaStreamNotifyInfo(String videoQuality, String audioQuality, String subtitleInfo) {
      static EmbyServiceImpl.MediaStreamNotifyInfo empty() {
         return new EmbyServiceImpl.MediaStreamNotifyInfo("", "", "");
      }

      EmbyServiceImpl.MediaStreamNotifyInfo mergeMissing(EmbyServiceImpl.MediaStreamNotifyInfo candidate) {
         return candidate == null
            ? this
            : new EmbyServiceImpl.MediaStreamNotifyInfo(
               this.preferRicher(this.videoQuality, candidate.videoQuality),
               this.preferRicher(this.audioQuality, candidate.audioQuality),
               this.preferRicher(this.subtitleInfo, candidate.subtitleInfo)
            );
      }

      private String preferRicher(String current, String candidate) {
         if (!StringUtils.hasText(candidate)) {
            return current;
         } else if (!StringUtils.hasText(current)) {
            return candidate;
         } else {
            return candidate.trim().length() > current.trim().length() ? candidate : current;
         }
      }
   }
}
