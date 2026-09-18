package com.una.embyhub.model.dto.response.emby;

import embyclient.model.BaseItemDto;
import embyclient.model.BaseItemPerson;
import embyclient.model.ChapterInfo;
import embyclient.model.DayOfWeek;
import embyclient.model.DrawingImageOrientation;
import embyclient.model.ExternalUrl;
import embyclient.model.LiveTvTimerType;
import embyclient.model.LocationType;
import embyclient.model.MediaSourceInfo;
import embyclient.model.MediaStream;
import embyclient.model.MediaUrl;
import embyclient.model.MetadataFields;
import embyclient.model.NameIdPair;
import embyclient.model.NameLongIdPair;
import embyclient.model.ProviderIdDictionary;
import embyclient.model.SyncJobItemStatus;
import embyclient.model.UserItemDataDto;
import embyclient.model.Video3DFormat;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Map;
import lombok.Generated;

public class QueryResultBaseItemResponse {
   private List<QueryResultBaseItemResponse.ItemsDTO> items;
   private Integer totalRecordCount;

   @Generated
   public List<QueryResultBaseItemResponse.ItemsDTO> getItems() {
      return this.items;
   }

   @Generated
   public Integer getTotalRecordCount() {
      return this.totalRecordCount;
   }

   @Generated
   public void setItems(final List<QueryResultBaseItemResponse.ItemsDTO> items) {
      this.items = items;
   }

   @Generated
   public void setTotalRecordCount(final Integer totalRecordCount) {
      this.totalRecordCount = totalRecordCount;
   }

   @Generated
   @Override
   public boolean equals(final Object o) {
      if (o == this) {
         return true;
      } else if (!(o instanceof QueryResultBaseItemResponse other)) {
         return false;
      } else if (!other.canEqual(this)) {
         return false;
      } else {
         Object this$totalRecordCount = this.getTotalRecordCount();
         Object other$totalRecordCount = other.getTotalRecordCount();
         if (this$totalRecordCount == null ? other$totalRecordCount == null : this$totalRecordCount.equals(other$totalRecordCount)) {
            Object this$items = this.getItems();
            Object other$items = other.getItems();
            return this$items == null ? other$items == null : this$items.equals(other$items);
         } else {
            return false;
         }
      }
   }

   @Generated
   protected boolean canEqual(final Object other) {
      return other instanceof QueryResultBaseItemResponse;
   }

   @Generated
   @Override
   public int hashCode() {
      int PRIME = 59;
      int result = 1;
      Object $totalRecordCount = this.getTotalRecordCount();
      result = result * 59 + ($totalRecordCount == null ? 43 : $totalRecordCount.hashCode());
      Object $items = this.getItems();
      return result * 59 + ($items == null ? 43 : $items.hashCode());
   }

   @Generated
   @Override
   public String toString() {
      return "QueryResultBaseItemResponse(items=" + this.getItems() + ", totalRecordCount=" + this.getTotalRecordCount() + ")";
   }

   public static class ItemsDTO {
      private String name = null;
      private String originalTitle = null;
      private String serverId = null;
      private String id = null;
      private String guid = null;
      private String etag = null;
      private String prefix = null;
      private String tunerName = null;
      private String playlistItemId = null;
      private OffsetDateTime dateCreated = null;
      private String extraType = null;
      private Integer sortIndexNumber = null;
      private Integer sortParentIndexNumber = null;
      private Boolean canDelete = null;
      private Boolean canDownload = null;
      private Boolean canEditItems = null;
      private Boolean supportsResume = null;
      private String presentationUniqueKey = null;
      private String preferredMetadataLanguage = null;
      private String preferredMetadataCountryCode = null;
      private Boolean supportsSync = null;
      private SyncJobItemStatus syncStatus = null;
      private Boolean canManageAccess = null;
      private Boolean canLeaveContent = null;
      private Boolean canMakePublic = null;
      private String container = null;
      private String sortName = null;
      private String forcedSortName = null;
      private Video3DFormat video3DFormat = null;
      private OffsetDateTime premiereDate = null;
      private List<ExternalUrl> externalUrls = null;
      private List<MediaSourceInfo> mediaSources = null;
      private Float criticRating = null;
      private Long gameSystemId = null;
      private Boolean asSeries = null;
      private String gameSystem = null;
      private List<String> productionLocations = null;
      private String path = null;
      private String officialRating = null;
      private String customRating = null;
      private String channelId = null;
      private String channelName = null;
      private String overview = null;
      private List<String> taglines = null;
      private List<String> genres = null;
      private Float communityRating = null;
      private Long runTimeTicks = null;
      private Long size = null;
      private String fileName = null;
      private Integer bitrate = null;
      private Integer productionYear = null;
      private String number = null;
      private String channelNumber = null;
      private Integer indexNumber = null;
      private Integer indexNumberEnd = null;
      private Integer parentIndexNumber = null;
      private List<MediaUrl> remoteTrailers = null;
      private ProviderIdDictionary providerIds = null;
      private Boolean isFolder = null;
      private String parentId = null;
      private String type = null;
      private List<BaseItemPerson> people = null;
      private List<NameLongIdPair> studios = null;
      private List<NameLongIdPair> genreItems = null;
      private List<NameLongIdPair> tagItems = null;
      private String parentLogoItemId = null;
      private String parentBackdropItemId = null;
      private List<String> parentBackdropImageTags = null;
      private Integer localTrailerCount = null;
      private UserItemDataDto userData = null;
      private Integer recursiveItemCount = null;
      private Integer childCount = null;
      private Integer seasonCount = null;
      private String seriesName = null;
      private String seriesId = null;
      private String seasonId = null;
      private Integer specialFeatureCount = null;
      private String displayPreferencesId = null;
      private String status = null;
      private List<DayOfWeek> airDays = null;
      private List<String> tags = null;
      private Double primaryImageAspectRatio = null;
      private List<String> artists = null;
      private List<NameIdPair> artistItems = null;
      private List<NameIdPair> composers = null;
      private String album = null;
      private String collectionType = null;
      private String displayOrder = null;
      private String albumId = null;
      private String albumPrimaryImageTag = null;
      private String seriesPrimaryImageTag = null;
      private String albumArtist = null;
      private List<NameIdPair> albumArtists = null;
      private String seasonName = null;
      private List<MediaStream> mediaStreams = null;
      private Integer partCount = null;
      private Map<String, String> imageTags = null;
      private List<String> backdropImageTags = null;
      private String parentLogoImageTag = null;
      private String seriesStudio = null;
      private String primaryImageItemId = null;
      private String primaryImageTag = null;
      private String parentThumbItemId = null;
      private String parentThumbImageTag = null;
      private List<ChapterInfo> chapters = null;
      private LocationType locationType = null;
      private String mediaType = null;
      private OffsetDateTime endDate = null;
      private List<MetadataFields> lockedFields = null;
      private Boolean lockData = null;
      private Integer width = null;
      private Integer height = null;
      private String cameraMake = null;
      private String cameraModel = null;
      private String software = null;
      private Double exposureTime = null;
      private Double focalLength = null;
      private DrawingImageOrientation imageOrientation = null;
      private Double aperture = null;
      private Double shutterSpeed = null;
      private Double latitude = null;
      private Double longitude = null;
      private Double altitude = null;
      private Integer isoSpeedRating = null;
      private String seriesTimerId = null;
      private String channelPrimaryImageTag = null;
      private OffsetDateTime startDate = null;
      private Double completionPercentage = null;
      private Boolean isRepeat = null;
      private Boolean isNew = null;
      private String episodeTitle = null;
      private Boolean isMovie = null;
      private Boolean isSports = null;
      private Boolean isSeries = null;
      private Boolean isLive = null;
      private Boolean isNews = null;
      private Boolean isKids = null;
      private Boolean isPremiere = null;
      private LiveTvTimerType timerType = null;
      private Boolean disabled = null;
      private String managementId = null;
      private String timerId = null;
      private BaseItemDto currentProgram = null;
      private Integer movieCount = null;
      private Integer seriesCount = null;
      private Integer albumCount = null;
      private Integer songCount = null;
      private Integer musicVideoCount = null;
      private List<String> subviews = null;
      private String listingsProviderId = null;
      private String listingsChannelId = null;
      private String listingsPath = null;
      private String listingsId = null;
      private String listingsChannelName = null;
      private String listingsChannelNumber = null;
      private String affiliateCallSign = null;
      private String imageUrl;
      private String filmTitle;
      private String primaryImageAspectRatioCount;
      private String embyItemUrl;
      private String backdropImageUrl;

      @Generated
      public String getName() {
         return this.name;
      }

      @Generated
      public String getOriginalTitle() {
         return this.originalTitle;
      }

      @Generated
      public String getServerId() {
         return this.serverId;
      }

      @Generated
      public String getId() {
         return this.id;
      }

      @Generated
      public String getGuid() {
         return this.guid;
      }

      @Generated
      public String getEtag() {
         return this.etag;
      }

      @Generated
      public String getPrefix() {
         return this.prefix;
      }

      @Generated
      public String getTunerName() {
         return this.tunerName;
      }

      @Generated
      public String getPlaylistItemId() {
         return this.playlistItemId;
      }

      @Generated
      public OffsetDateTime getDateCreated() {
         return this.dateCreated;
      }

      @Generated
      public String getExtraType() {
         return this.extraType;
      }

      @Generated
      public Integer getSortIndexNumber() {
         return this.sortIndexNumber;
      }

      @Generated
      public Integer getSortParentIndexNumber() {
         return this.sortParentIndexNumber;
      }

      @Generated
      public Boolean getCanDelete() {
         return this.canDelete;
      }

      @Generated
      public Boolean getCanDownload() {
         return this.canDownload;
      }

      @Generated
      public Boolean getCanEditItems() {
         return this.canEditItems;
      }

      @Generated
      public Boolean getSupportsResume() {
         return this.supportsResume;
      }

      @Generated
      public String getPresentationUniqueKey() {
         return this.presentationUniqueKey;
      }

      @Generated
      public String getPreferredMetadataLanguage() {
         return this.preferredMetadataLanguage;
      }

      @Generated
      public String getPreferredMetadataCountryCode() {
         return this.preferredMetadataCountryCode;
      }

      @Generated
      public Boolean getSupportsSync() {
         return this.supportsSync;
      }

      @Generated
      public SyncJobItemStatus getSyncStatus() {
         return this.syncStatus;
      }

      @Generated
      public Boolean getCanManageAccess() {
         return this.canManageAccess;
      }

      @Generated
      public Boolean getCanLeaveContent() {
         return this.canLeaveContent;
      }

      @Generated
      public Boolean getCanMakePublic() {
         return this.canMakePublic;
      }

      @Generated
      public String getContainer() {
         return this.container;
      }

      @Generated
      public String getSortName() {
         return this.sortName;
      }

      @Generated
      public String getForcedSortName() {
         return this.forcedSortName;
      }

      @Generated
      public Video3DFormat getVideo3DFormat() {
         return this.video3DFormat;
      }

      @Generated
      public OffsetDateTime getPremiereDate() {
         return this.premiereDate;
      }

      @Generated
      public List<ExternalUrl> getExternalUrls() {
         return this.externalUrls;
      }

      @Generated
      public List<MediaSourceInfo> getMediaSources() {
         return this.mediaSources;
      }

      @Generated
      public Float getCriticRating() {
         return this.criticRating;
      }

      @Generated
      public Long getGameSystemId() {
         return this.gameSystemId;
      }

      @Generated
      public Boolean getAsSeries() {
         return this.asSeries;
      }

      @Generated
      public String getGameSystem() {
         return this.gameSystem;
      }

      @Generated
      public List<String> getProductionLocations() {
         return this.productionLocations;
      }

      @Generated
      public String getPath() {
         return this.path;
      }

      @Generated
      public String getOfficialRating() {
         return this.officialRating;
      }

      @Generated
      public String getCustomRating() {
         return this.customRating;
      }

      @Generated
      public String getChannelId() {
         return this.channelId;
      }

      @Generated
      public String getChannelName() {
         return this.channelName;
      }

      @Generated
      public String getOverview() {
         return this.overview;
      }

      @Generated
      public List<String> getTaglines() {
         return this.taglines;
      }

      @Generated
      public List<String> getGenres() {
         return this.genres;
      }

      @Generated
      public Float getCommunityRating() {
         return this.communityRating;
      }

      @Generated
      public Long getRunTimeTicks() {
         return this.runTimeTicks;
      }

      @Generated
      public Long getSize() {
         return this.size;
      }

      @Generated
      public String getFileName() {
         return this.fileName;
      }

      @Generated
      public Integer getBitrate() {
         return this.bitrate;
      }

      @Generated
      public Integer getProductionYear() {
         return this.productionYear;
      }

      @Generated
      public String getNumber() {
         return this.number;
      }

      @Generated
      public String getChannelNumber() {
         return this.channelNumber;
      }

      @Generated
      public Integer getIndexNumber() {
         return this.indexNumber;
      }

      @Generated
      public Integer getIndexNumberEnd() {
         return this.indexNumberEnd;
      }

      @Generated
      public Integer getParentIndexNumber() {
         return this.parentIndexNumber;
      }

      @Generated
      public List<MediaUrl> getRemoteTrailers() {
         return this.remoteTrailers;
      }

      @Generated
      public ProviderIdDictionary getProviderIds() {
         return this.providerIds;
      }

      @Generated
      public Boolean getIsFolder() {
         return this.isFolder;
      }

      @Generated
      public String getParentId() {
         return this.parentId;
      }

      @Generated
      public String getType() {
         return this.type;
      }

      @Generated
      public List<BaseItemPerson> getPeople() {
         return this.people;
      }

      @Generated
      public List<NameLongIdPair> getStudios() {
         return this.studios;
      }

      @Generated
      public List<NameLongIdPair> getGenreItems() {
         return this.genreItems;
      }

      @Generated
      public List<NameLongIdPair> getTagItems() {
         return this.tagItems;
      }

      @Generated
      public String getParentLogoItemId() {
         return this.parentLogoItemId;
      }

      @Generated
      public String getParentBackdropItemId() {
         return this.parentBackdropItemId;
      }

      @Generated
      public List<String> getParentBackdropImageTags() {
         return this.parentBackdropImageTags;
      }

      @Generated
      public Integer getLocalTrailerCount() {
         return this.localTrailerCount;
      }

      @Generated
      public UserItemDataDto getUserData() {
         return this.userData;
      }

      @Generated
      public Integer getRecursiveItemCount() {
         return this.recursiveItemCount;
      }

      @Generated
      public Integer getChildCount() {
         return this.childCount;
      }

      @Generated
      public Integer getSeasonCount() {
         return this.seasonCount;
      }

      @Generated
      public String getSeriesName() {
         return this.seriesName;
      }

      @Generated
      public String getSeriesId() {
         return this.seriesId;
      }

      @Generated
      public String getSeasonId() {
         return this.seasonId;
      }

      @Generated
      public Integer getSpecialFeatureCount() {
         return this.specialFeatureCount;
      }

      @Generated
      public String getDisplayPreferencesId() {
         return this.displayPreferencesId;
      }

      @Generated
      public String getStatus() {
         return this.status;
      }

      @Generated
      public List<DayOfWeek> getAirDays() {
         return this.airDays;
      }

      @Generated
      public List<String> getTags() {
         return this.tags;
      }

      @Generated
      public Double getPrimaryImageAspectRatio() {
         return this.primaryImageAspectRatio;
      }

      @Generated
      public List<String> getArtists() {
         return this.artists;
      }

      @Generated
      public List<NameIdPair> getArtistItems() {
         return this.artistItems;
      }

      @Generated
      public List<NameIdPair> getComposers() {
         return this.composers;
      }

      @Generated
      public String getAlbum() {
         return this.album;
      }

      @Generated
      public String getCollectionType() {
         return this.collectionType;
      }

      @Generated
      public String getDisplayOrder() {
         return this.displayOrder;
      }

      @Generated
      public String getAlbumId() {
         return this.albumId;
      }

      @Generated
      public String getAlbumPrimaryImageTag() {
         return this.albumPrimaryImageTag;
      }

      @Generated
      public String getSeriesPrimaryImageTag() {
         return this.seriesPrimaryImageTag;
      }

      @Generated
      public String getAlbumArtist() {
         return this.albumArtist;
      }

      @Generated
      public List<NameIdPair> getAlbumArtists() {
         return this.albumArtists;
      }

      @Generated
      public String getSeasonName() {
         return this.seasonName;
      }

      @Generated
      public List<MediaStream> getMediaStreams() {
         return this.mediaStreams;
      }

      @Generated
      public Integer getPartCount() {
         return this.partCount;
      }

      @Generated
      public Map<String, String> getImageTags() {
         return this.imageTags;
      }

      @Generated
      public List<String> getBackdropImageTags() {
         return this.backdropImageTags;
      }

      @Generated
      public String getParentLogoImageTag() {
         return this.parentLogoImageTag;
      }

      @Generated
      public String getSeriesStudio() {
         return this.seriesStudio;
      }

      @Generated
      public String getPrimaryImageItemId() {
         return this.primaryImageItemId;
      }

      @Generated
      public String getPrimaryImageTag() {
         return this.primaryImageTag;
      }

      @Generated
      public String getParentThumbItemId() {
         return this.parentThumbItemId;
      }

      @Generated
      public String getParentThumbImageTag() {
         return this.parentThumbImageTag;
      }

      @Generated
      public List<ChapterInfo> getChapters() {
         return this.chapters;
      }

      @Generated
      public LocationType getLocationType() {
         return this.locationType;
      }

      @Generated
      public String getMediaType() {
         return this.mediaType;
      }

      @Generated
      public OffsetDateTime getEndDate() {
         return this.endDate;
      }

      @Generated
      public List<MetadataFields> getLockedFields() {
         return this.lockedFields;
      }

      @Generated
      public Boolean getLockData() {
         return this.lockData;
      }

      @Generated
      public Integer getWidth() {
         return this.width;
      }

      @Generated
      public Integer getHeight() {
         return this.height;
      }

      @Generated
      public String getCameraMake() {
         return this.cameraMake;
      }

      @Generated
      public String getCameraModel() {
         return this.cameraModel;
      }

      @Generated
      public String getSoftware() {
         return this.software;
      }

      @Generated
      public Double getExposureTime() {
         return this.exposureTime;
      }

      @Generated
      public Double getFocalLength() {
         return this.focalLength;
      }

      @Generated
      public DrawingImageOrientation getImageOrientation() {
         return this.imageOrientation;
      }

      @Generated
      public Double getAperture() {
         return this.aperture;
      }

      @Generated
      public Double getShutterSpeed() {
         return this.shutterSpeed;
      }

      @Generated
      public Double getLatitude() {
         return this.latitude;
      }

      @Generated
      public Double getLongitude() {
         return this.longitude;
      }

      @Generated
      public Double getAltitude() {
         return this.altitude;
      }

      @Generated
      public Integer getIsoSpeedRating() {
         return this.isoSpeedRating;
      }

      @Generated
      public String getSeriesTimerId() {
         return this.seriesTimerId;
      }

      @Generated
      public String getChannelPrimaryImageTag() {
         return this.channelPrimaryImageTag;
      }

      @Generated
      public OffsetDateTime getStartDate() {
         return this.startDate;
      }

      @Generated
      public Double getCompletionPercentage() {
         return this.completionPercentage;
      }

      @Generated
      public Boolean getIsRepeat() {
         return this.isRepeat;
      }

      @Generated
      public Boolean getIsNew() {
         return this.isNew;
      }

      @Generated
      public String getEpisodeTitle() {
         return this.episodeTitle;
      }

      @Generated
      public Boolean getIsMovie() {
         return this.isMovie;
      }

      @Generated
      public Boolean getIsSports() {
         return this.isSports;
      }

      @Generated
      public Boolean getIsSeries() {
         return this.isSeries;
      }

      @Generated
      public Boolean getIsLive() {
         return this.isLive;
      }

      @Generated
      public Boolean getIsNews() {
         return this.isNews;
      }

      @Generated
      public Boolean getIsKids() {
         return this.isKids;
      }

      @Generated
      public Boolean getIsPremiere() {
         return this.isPremiere;
      }

      @Generated
      public LiveTvTimerType getTimerType() {
         return this.timerType;
      }

      @Generated
      public Boolean getDisabled() {
         return this.disabled;
      }

      @Generated
      public String getManagementId() {
         return this.managementId;
      }

      @Generated
      public String getTimerId() {
         return this.timerId;
      }

      @Generated
      public BaseItemDto getCurrentProgram() {
         return this.currentProgram;
      }

      @Generated
      public Integer getMovieCount() {
         return this.movieCount;
      }

      @Generated
      public Integer getSeriesCount() {
         return this.seriesCount;
      }

      @Generated
      public Integer getAlbumCount() {
         return this.albumCount;
      }

      @Generated
      public Integer getSongCount() {
         return this.songCount;
      }

      @Generated
      public Integer getMusicVideoCount() {
         return this.musicVideoCount;
      }

      @Generated
      public List<String> getSubviews() {
         return this.subviews;
      }

      @Generated
      public String getListingsProviderId() {
         return this.listingsProviderId;
      }

      @Generated
      public String getListingsChannelId() {
         return this.listingsChannelId;
      }

      @Generated
      public String getListingsPath() {
         return this.listingsPath;
      }

      @Generated
      public String getListingsId() {
         return this.listingsId;
      }

      @Generated
      public String getListingsChannelName() {
         return this.listingsChannelName;
      }

      @Generated
      public String getListingsChannelNumber() {
         return this.listingsChannelNumber;
      }

      @Generated
      public String getAffiliateCallSign() {
         return this.affiliateCallSign;
      }

      @Generated
      public String getImageUrl() {
         return this.imageUrl;
      }

      @Generated
      public String getFilmTitle() {
         return this.filmTitle;
      }

      @Generated
      public String getPrimaryImageAspectRatioCount() {
         return this.primaryImageAspectRatioCount;
      }

      @Generated
      public String getEmbyItemUrl() {
         return this.embyItemUrl;
      }

      @Generated
      public String getBackdropImageUrl() {
         return this.backdropImageUrl;
      }

      @Generated
      public void setName(final String name) {
         this.name = name;
      }

      @Generated
      public void setOriginalTitle(final String originalTitle) {
         this.originalTitle = originalTitle;
      }

      @Generated
      public void setServerId(final String serverId) {
         this.serverId = serverId;
      }

      @Generated
      public void setId(final String id) {
         this.id = id;
      }

      @Generated
      public void setGuid(final String guid) {
         this.guid = guid;
      }

      @Generated
      public void setEtag(final String etag) {
         this.etag = etag;
      }

      @Generated
      public void setPrefix(final String prefix) {
         this.prefix = prefix;
      }

      @Generated
      public void setTunerName(final String tunerName) {
         this.tunerName = tunerName;
      }

      @Generated
      public void setPlaylistItemId(final String playlistItemId) {
         this.playlistItemId = playlistItemId;
      }

      @Generated
      public void setDateCreated(final OffsetDateTime dateCreated) {
         this.dateCreated = dateCreated;
      }

      @Generated
      public void setExtraType(final String extraType) {
         this.extraType = extraType;
      }

      @Generated
      public void setSortIndexNumber(final Integer sortIndexNumber) {
         this.sortIndexNumber = sortIndexNumber;
      }

      @Generated
      public void setSortParentIndexNumber(final Integer sortParentIndexNumber) {
         this.sortParentIndexNumber = sortParentIndexNumber;
      }

      @Generated
      public void setCanDelete(final Boolean canDelete) {
         this.canDelete = canDelete;
      }

      @Generated
      public void setCanDownload(final Boolean canDownload) {
         this.canDownload = canDownload;
      }

      @Generated
      public void setCanEditItems(final Boolean canEditItems) {
         this.canEditItems = canEditItems;
      }

      @Generated
      public void setSupportsResume(final Boolean supportsResume) {
         this.supportsResume = supportsResume;
      }

      @Generated
      public void setPresentationUniqueKey(final String presentationUniqueKey) {
         this.presentationUniqueKey = presentationUniqueKey;
      }

      @Generated
      public void setPreferredMetadataLanguage(final String preferredMetadataLanguage) {
         this.preferredMetadataLanguage = preferredMetadataLanguage;
      }

      @Generated
      public void setPreferredMetadataCountryCode(final String preferredMetadataCountryCode) {
         this.preferredMetadataCountryCode = preferredMetadataCountryCode;
      }

      @Generated
      public void setSupportsSync(final Boolean supportsSync) {
         this.supportsSync = supportsSync;
      }

      @Generated
      public void setSyncStatus(final SyncJobItemStatus syncStatus) {
         this.syncStatus = syncStatus;
      }

      @Generated
      public void setCanManageAccess(final Boolean canManageAccess) {
         this.canManageAccess = canManageAccess;
      }

      @Generated
      public void setCanLeaveContent(final Boolean canLeaveContent) {
         this.canLeaveContent = canLeaveContent;
      }

      @Generated
      public void setCanMakePublic(final Boolean canMakePublic) {
         this.canMakePublic = canMakePublic;
      }

      @Generated
      public void setContainer(final String container) {
         this.container = container;
      }

      @Generated
      public void setSortName(final String sortName) {
         this.sortName = sortName;
      }

      @Generated
      public void setForcedSortName(final String forcedSortName) {
         this.forcedSortName = forcedSortName;
      }

      @Generated
      public void setVideo3DFormat(final Video3DFormat video3DFormat) {
         this.video3DFormat = video3DFormat;
      }

      @Generated
      public void setPremiereDate(final OffsetDateTime premiereDate) {
         this.premiereDate = premiereDate;
      }

      @Generated
      public void setExternalUrls(final List<ExternalUrl> externalUrls) {
         this.externalUrls = externalUrls;
      }

      @Generated
      public void setMediaSources(final List<MediaSourceInfo> mediaSources) {
         this.mediaSources = mediaSources;
      }

      @Generated
      public void setCriticRating(final Float criticRating) {
         this.criticRating = criticRating;
      }

      @Generated
      public void setGameSystemId(final Long gameSystemId) {
         this.gameSystemId = gameSystemId;
      }

      @Generated
      public void setAsSeries(final Boolean asSeries) {
         this.asSeries = asSeries;
      }

      @Generated
      public void setGameSystem(final String gameSystem) {
         this.gameSystem = gameSystem;
      }

      @Generated
      public void setProductionLocations(final List<String> productionLocations) {
         this.productionLocations = productionLocations;
      }

      @Generated
      public void setPath(final String path) {
         this.path = path;
      }

      @Generated
      public void setOfficialRating(final String officialRating) {
         this.officialRating = officialRating;
      }

      @Generated
      public void setCustomRating(final String customRating) {
         this.customRating = customRating;
      }

      @Generated
      public void setChannelId(final String channelId) {
         this.channelId = channelId;
      }

      @Generated
      public void setChannelName(final String channelName) {
         this.channelName = channelName;
      }

      @Generated
      public void setOverview(final String overview) {
         this.overview = overview;
      }

      @Generated
      public void setTaglines(final List<String> taglines) {
         this.taglines = taglines;
      }

      @Generated
      public void setGenres(final List<String> genres) {
         this.genres = genres;
      }

      @Generated
      public void setCommunityRating(final Float communityRating) {
         this.communityRating = communityRating;
      }

      @Generated
      public void setRunTimeTicks(final Long runTimeTicks) {
         this.runTimeTicks = runTimeTicks;
      }

      @Generated
      public void setSize(final Long size) {
         this.size = size;
      }

      @Generated
      public void setFileName(final String fileName) {
         this.fileName = fileName;
      }

      @Generated
      public void setBitrate(final Integer bitrate) {
         this.bitrate = bitrate;
      }

      @Generated
      public void setProductionYear(final Integer productionYear) {
         this.productionYear = productionYear;
      }

      @Generated
      public void setNumber(final String number) {
         this.number = number;
      }

      @Generated
      public void setChannelNumber(final String channelNumber) {
         this.channelNumber = channelNumber;
      }

      @Generated
      public void setIndexNumber(final Integer indexNumber) {
         this.indexNumber = indexNumber;
      }

      @Generated
      public void setIndexNumberEnd(final Integer indexNumberEnd) {
         this.indexNumberEnd = indexNumberEnd;
      }

      @Generated
      public void setParentIndexNumber(final Integer parentIndexNumber) {
         this.parentIndexNumber = parentIndexNumber;
      }

      @Generated
      public void setRemoteTrailers(final List<MediaUrl> remoteTrailers) {
         this.remoteTrailers = remoteTrailers;
      }

      @Generated
      public void setProviderIds(final ProviderIdDictionary providerIds) {
         this.providerIds = providerIds;
      }

      @Generated
      public void setIsFolder(final Boolean isFolder) {
         this.isFolder = isFolder;
      }

      @Generated
      public void setParentId(final String parentId) {
         this.parentId = parentId;
      }

      @Generated
      public void setType(final String type) {
         this.type = type;
      }

      @Generated
      public void setPeople(final List<BaseItemPerson> people) {
         this.people = people;
      }

      @Generated
      public void setStudios(final List<NameLongIdPair> studios) {
         this.studios = studios;
      }

      @Generated
      public void setGenreItems(final List<NameLongIdPair> genreItems) {
         this.genreItems = genreItems;
      }

      @Generated
      public void setTagItems(final List<NameLongIdPair> tagItems) {
         this.tagItems = tagItems;
      }

      @Generated
      public void setParentLogoItemId(final String parentLogoItemId) {
         this.parentLogoItemId = parentLogoItemId;
      }

      @Generated
      public void setParentBackdropItemId(final String parentBackdropItemId) {
         this.parentBackdropItemId = parentBackdropItemId;
      }

      @Generated
      public void setParentBackdropImageTags(final List<String> parentBackdropImageTags) {
         this.parentBackdropImageTags = parentBackdropImageTags;
      }

      @Generated
      public void setLocalTrailerCount(final Integer localTrailerCount) {
         this.localTrailerCount = localTrailerCount;
      }

      @Generated
      public void setUserData(final UserItemDataDto userData) {
         this.userData = userData;
      }

      @Generated
      public void setRecursiveItemCount(final Integer recursiveItemCount) {
         this.recursiveItemCount = recursiveItemCount;
      }

      @Generated
      public void setChildCount(final Integer childCount) {
         this.childCount = childCount;
      }

      @Generated
      public void setSeasonCount(final Integer seasonCount) {
         this.seasonCount = seasonCount;
      }

      @Generated
      public void setSeriesName(final String seriesName) {
         this.seriesName = seriesName;
      }

      @Generated
      public void setSeriesId(final String seriesId) {
         this.seriesId = seriesId;
      }

      @Generated
      public void setSeasonId(final String seasonId) {
         this.seasonId = seasonId;
      }

      @Generated
      public void setSpecialFeatureCount(final Integer specialFeatureCount) {
         this.specialFeatureCount = specialFeatureCount;
      }

      @Generated
      public void setDisplayPreferencesId(final String displayPreferencesId) {
         this.displayPreferencesId = displayPreferencesId;
      }

      @Generated
      public void setStatus(final String status) {
         this.status = status;
      }

      @Generated
      public void setAirDays(final List<DayOfWeek> airDays) {
         this.airDays = airDays;
      }

      @Generated
      public void setTags(final List<String> tags) {
         this.tags = tags;
      }

      @Generated
      public void setPrimaryImageAspectRatio(final Double primaryImageAspectRatio) {
         this.primaryImageAspectRatio = primaryImageAspectRatio;
      }

      @Generated
      public void setArtists(final List<String> artists) {
         this.artists = artists;
      }

      @Generated
      public void setArtistItems(final List<NameIdPair> artistItems) {
         this.artistItems = artistItems;
      }

      @Generated
      public void setComposers(final List<NameIdPair> composers) {
         this.composers = composers;
      }

      @Generated
      public void setAlbum(final String album) {
         this.album = album;
      }

      @Generated
      public void setCollectionType(final String collectionType) {
         this.collectionType = collectionType;
      }

      @Generated
      public void setDisplayOrder(final String displayOrder) {
         this.displayOrder = displayOrder;
      }

      @Generated
      public void setAlbumId(final String albumId) {
         this.albumId = albumId;
      }

      @Generated
      public void setAlbumPrimaryImageTag(final String albumPrimaryImageTag) {
         this.albumPrimaryImageTag = albumPrimaryImageTag;
      }

      @Generated
      public void setSeriesPrimaryImageTag(final String seriesPrimaryImageTag) {
         this.seriesPrimaryImageTag = seriesPrimaryImageTag;
      }

      @Generated
      public void setAlbumArtist(final String albumArtist) {
         this.albumArtist = albumArtist;
      }

      @Generated
      public void setAlbumArtists(final List<NameIdPair> albumArtists) {
         this.albumArtists = albumArtists;
      }

      @Generated
      public void setSeasonName(final String seasonName) {
         this.seasonName = seasonName;
      }

      @Generated
      public void setMediaStreams(final List<MediaStream> mediaStreams) {
         this.mediaStreams = mediaStreams;
      }

      @Generated
      public void setPartCount(final Integer partCount) {
         this.partCount = partCount;
      }

      @Generated
      public void setImageTags(final Map<String, String> imageTags) {
         this.imageTags = imageTags;
      }

      @Generated
      public void setBackdropImageTags(final List<String> backdropImageTags) {
         this.backdropImageTags = backdropImageTags;
      }

      @Generated
      public void setParentLogoImageTag(final String parentLogoImageTag) {
         this.parentLogoImageTag = parentLogoImageTag;
      }

      @Generated
      public void setSeriesStudio(final String seriesStudio) {
         this.seriesStudio = seriesStudio;
      }

      @Generated
      public void setPrimaryImageItemId(final String primaryImageItemId) {
         this.primaryImageItemId = primaryImageItemId;
      }

      @Generated
      public void setPrimaryImageTag(final String primaryImageTag) {
         this.primaryImageTag = primaryImageTag;
      }

      @Generated
      public void setParentThumbItemId(final String parentThumbItemId) {
         this.parentThumbItemId = parentThumbItemId;
      }

      @Generated
      public void setParentThumbImageTag(final String parentThumbImageTag) {
         this.parentThumbImageTag = parentThumbImageTag;
      }

      @Generated
      public void setChapters(final List<ChapterInfo> chapters) {
         this.chapters = chapters;
      }

      @Generated
      public void setLocationType(final LocationType locationType) {
         this.locationType = locationType;
      }

      @Generated
      public void setMediaType(final String mediaType) {
         this.mediaType = mediaType;
      }

      @Generated
      public void setEndDate(final OffsetDateTime endDate) {
         this.endDate = endDate;
      }

      @Generated
      public void setLockedFields(final List<MetadataFields> lockedFields) {
         this.lockedFields = lockedFields;
      }

      @Generated
      public void setLockData(final Boolean lockData) {
         this.lockData = lockData;
      }

      @Generated
      public void setWidth(final Integer width) {
         this.width = width;
      }

      @Generated
      public void setHeight(final Integer height) {
         this.height = height;
      }

      @Generated
      public void setCameraMake(final String cameraMake) {
         this.cameraMake = cameraMake;
      }

      @Generated
      public void setCameraModel(final String cameraModel) {
         this.cameraModel = cameraModel;
      }

      @Generated
      public void setSoftware(final String software) {
         this.software = software;
      }

      @Generated
      public void setExposureTime(final Double exposureTime) {
         this.exposureTime = exposureTime;
      }

      @Generated
      public void setFocalLength(final Double focalLength) {
         this.focalLength = focalLength;
      }

      @Generated
      public void setImageOrientation(final DrawingImageOrientation imageOrientation) {
         this.imageOrientation = imageOrientation;
      }

      @Generated
      public void setAperture(final Double aperture) {
         this.aperture = aperture;
      }

      @Generated
      public void setShutterSpeed(final Double shutterSpeed) {
         this.shutterSpeed = shutterSpeed;
      }

      @Generated
      public void setLatitude(final Double latitude) {
         this.latitude = latitude;
      }

      @Generated
      public void setLongitude(final Double longitude) {
         this.longitude = longitude;
      }

      @Generated
      public void setAltitude(final Double altitude) {
         this.altitude = altitude;
      }

      @Generated
      public void setIsoSpeedRating(final Integer isoSpeedRating) {
         this.isoSpeedRating = isoSpeedRating;
      }

      @Generated
      public void setSeriesTimerId(final String seriesTimerId) {
         this.seriesTimerId = seriesTimerId;
      }

      @Generated
      public void setChannelPrimaryImageTag(final String channelPrimaryImageTag) {
         this.channelPrimaryImageTag = channelPrimaryImageTag;
      }

      @Generated
      public void setStartDate(final OffsetDateTime startDate) {
         this.startDate = startDate;
      }

      @Generated
      public void setCompletionPercentage(final Double completionPercentage) {
         this.completionPercentage = completionPercentage;
      }

      @Generated
      public void setIsRepeat(final Boolean isRepeat) {
         this.isRepeat = isRepeat;
      }

      @Generated
      public void setIsNew(final Boolean isNew) {
         this.isNew = isNew;
      }

      @Generated
      public void setEpisodeTitle(final String episodeTitle) {
         this.episodeTitle = episodeTitle;
      }

      @Generated
      public void setIsMovie(final Boolean isMovie) {
         this.isMovie = isMovie;
      }

      @Generated
      public void setIsSports(final Boolean isSports) {
         this.isSports = isSports;
      }

      @Generated
      public void setIsSeries(final Boolean isSeries) {
         this.isSeries = isSeries;
      }

      @Generated
      public void setIsLive(final Boolean isLive) {
         this.isLive = isLive;
      }

      @Generated
      public void setIsNews(final Boolean isNews) {
         this.isNews = isNews;
      }

      @Generated
      public void setIsKids(final Boolean isKids) {
         this.isKids = isKids;
      }

      @Generated
      public void setIsPremiere(final Boolean isPremiere) {
         this.isPremiere = isPremiere;
      }

      @Generated
      public void setTimerType(final LiveTvTimerType timerType) {
         this.timerType = timerType;
      }

      @Generated
      public void setDisabled(final Boolean disabled) {
         this.disabled = disabled;
      }

      @Generated
      public void setManagementId(final String managementId) {
         this.managementId = managementId;
      }

      @Generated
      public void setTimerId(final String timerId) {
         this.timerId = timerId;
      }

      @Generated
      public void setCurrentProgram(final BaseItemDto currentProgram) {
         this.currentProgram = currentProgram;
      }

      @Generated
      public void setMovieCount(final Integer movieCount) {
         this.movieCount = movieCount;
      }

      @Generated
      public void setSeriesCount(final Integer seriesCount) {
         this.seriesCount = seriesCount;
      }

      @Generated
      public void setAlbumCount(final Integer albumCount) {
         this.albumCount = albumCount;
      }

      @Generated
      public void setSongCount(final Integer songCount) {
         this.songCount = songCount;
      }

      @Generated
      public void setMusicVideoCount(final Integer musicVideoCount) {
         this.musicVideoCount = musicVideoCount;
      }

      @Generated
      public void setSubviews(final List<String> subviews) {
         this.subviews = subviews;
      }

      @Generated
      public void setListingsProviderId(final String listingsProviderId) {
         this.listingsProviderId = listingsProviderId;
      }

      @Generated
      public void setListingsChannelId(final String listingsChannelId) {
         this.listingsChannelId = listingsChannelId;
      }

      @Generated
      public void setListingsPath(final String listingsPath) {
         this.listingsPath = listingsPath;
      }

      @Generated
      public void setListingsId(final String listingsId) {
         this.listingsId = listingsId;
      }

      @Generated
      public void setListingsChannelName(final String listingsChannelName) {
         this.listingsChannelName = listingsChannelName;
      }

      @Generated
      public void setListingsChannelNumber(final String listingsChannelNumber) {
         this.listingsChannelNumber = listingsChannelNumber;
      }

      @Generated
      public void setAffiliateCallSign(final String affiliateCallSign) {
         this.affiliateCallSign = affiliateCallSign;
      }

      @Generated
      public void setImageUrl(final String imageUrl) {
         this.imageUrl = imageUrl;
      }

      @Generated
      public void setFilmTitle(final String filmTitle) {
         this.filmTitle = filmTitle;
      }

      @Generated
      public void setPrimaryImageAspectRatioCount(final String primaryImageAspectRatioCount) {
         this.primaryImageAspectRatioCount = primaryImageAspectRatioCount;
      }

      @Generated
      public void setEmbyItemUrl(final String embyItemUrl) {
         this.embyItemUrl = embyItemUrl;
      }

      @Generated
      public void setBackdropImageUrl(final String backdropImageUrl) {
         this.backdropImageUrl = backdropImageUrl;
      }

      @Generated
      @Override
      public boolean equals(final Object o) {
         if (o == this) {
            return true;
         } else if (!(o instanceof QueryResultBaseItemResponse.ItemsDTO other)) {
            return false;
         } else if (!other.canEqual(this)) {
            return false;
         } else {
            Object this$sortIndexNumber = this.getSortIndexNumber();
            Object other$sortIndexNumber = other.getSortIndexNumber();
            if (this$sortIndexNumber == null ? other$sortIndexNumber == null : this$sortIndexNumber.equals(other$sortIndexNumber)) {
               Object this$sortParentIndexNumber = this.getSortParentIndexNumber();
               Object other$sortParentIndexNumber = other.getSortParentIndexNumber();
               if (this$sortParentIndexNumber == null ? other$sortParentIndexNumber == null : this$sortParentIndexNumber.equals(other$sortParentIndexNumber)) {
                  Object this$canDelete = this.getCanDelete();
                  Object other$canDelete = other.getCanDelete();
                  if (this$canDelete == null ? other$canDelete == null : this$canDelete.equals(other$canDelete)) {
                     Object this$canDownload = this.getCanDownload();
                     Object other$canDownload = other.getCanDownload();
                     if (this$canDownload == null ? other$canDownload == null : this$canDownload.equals(other$canDownload)) {
                        Object this$canEditItems = this.getCanEditItems();
                        Object other$canEditItems = other.getCanEditItems();
                        if (this$canEditItems == null ? other$canEditItems == null : this$canEditItems.equals(other$canEditItems)) {
                           Object this$supportsResume = this.getSupportsResume();
                           Object other$supportsResume = other.getSupportsResume();
                           if (this$supportsResume == null ? other$supportsResume == null : this$supportsResume.equals(other$supportsResume)) {
                              Object this$supportsSync = this.getSupportsSync();
                              Object other$supportsSync = other.getSupportsSync();
                              if (this$supportsSync == null ? other$supportsSync == null : this$supportsSync.equals(other$supportsSync)) {
                                 Object this$canManageAccess = this.getCanManageAccess();
                                 Object other$canManageAccess = other.getCanManageAccess();
                                 if (this$canManageAccess == null ? other$canManageAccess == null : this$canManageAccess.equals(other$canManageAccess)) {
                                    Object this$canLeaveContent = this.getCanLeaveContent();
                                    Object other$canLeaveContent = other.getCanLeaveContent();
                                    if (this$canLeaveContent == null ? other$canLeaveContent == null : this$canLeaveContent.equals(other$canLeaveContent)) {
                                       Object this$canMakePublic = this.getCanMakePublic();
                                       Object other$canMakePublic = other.getCanMakePublic();
                                       if (this$canMakePublic == null ? other$canMakePublic == null : this$canMakePublic.equals(other$canMakePublic)) {
                                          Object this$criticRating = this.getCriticRating();
                                          Object other$criticRating = other.getCriticRating();
                                          if (this$criticRating == null ? other$criticRating == null : this$criticRating.equals(other$criticRating)) {
                                             Object this$gameSystemId = this.getGameSystemId();
                                             Object other$gameSystemId = other.getGameSystemId();
                                             if (this$gameSystemId == null ? other$gameSystemId == null : this$gameSystemId.equals(other$gameSystemId)) {
                                                Object this$asSeries = this.getAsSeries();
                                                Object other$asSeries = other.getAsSeries();
                                                if (this$asSeries == null ? other$asSeries == null : this$asSeries.equals(other$asSeries)) {
                                                   Object this$communityRating = this.getCommunityRating();
                                                   Object other$communityRating = other.getCommunityRating();
                                                   if (this$communityRating == null
                                                      ? other$communityRating == null
                                                      : this$communityRating.equals(other$communityRating)) {
                                                      Object this$runTimeTicks = this.getRunTimeTicks();
                                                      Object other$runTimeTicks = other.getRunTimeTicks();
                                                      if (this$runTimeTicks == null ? other$runTimeTicks == null : this$runTimeTicks.equals(other$runTimeTicks)
                                                         )
                                                       {
                                                         Object this$size = this.getSize();
                                                         Object other$size = other.getSize();
                                                         if (this$size == null ? other$size == null : this$size.equals(other$size)) {
                                                            Object this$bitrate = this.getBitrate();
                                                            Object other$bitrate = other.getBitrate();
                                                            if (this$bitrate == null ? other$bitrate == null : this$bitrate.equals(other$bitrate)) {
                                                               Object this$productionYear = this.getProductionYear();
                                                               Object other$productionYear = other.getProductionYear();
                                                               if (this$productionYear == null
                                                                  ? other$productionYear == null
                                                                  : this$productionYear.equals(other$productionYear)) {
                                                                  Object this$indexNumber = this.getIndexNumber();
                                                                  Object other$indexNumber = other.getIndexNumber();
                                                                  if (this$indexNumber == null
                                                                     ? other$indexNumber == null
                                                                     : this$indexNumber.equals(other$indexNumber)) {
                                                                     Object this$indexNumberEnd = this.getIndexNumberEnd();
                                                                     Object other$indexNumberEnd = other.getIndexNumberEnd();
                                                                     if (this$indexNumberEnd == null
                                                                        ? other$indexNumberEnd == null
                                                                        : this$indexNumberEnd.equals(other$indexNumberEnd)) {
                                                                        Object this$parentIndexNumber = this.getParentIndexNumber();
                                                                        Object other$parentIndexNumber = other.getParentIndexNumber();
                                                                        if (this$parentIndexNumber == null
                                                                           ? other$parentIndexNumber == null
                                                                           : this$parentIndexNumber.equals(other$parentIndexNumber)) {
                                                                           Object this$isFolder = this.getIsFolder();
                                                                           Object other$isFolder = other.getIsFolder();
                                                                           if (this$isFolder == null
                                                                              ? other$isFolder == null
                                                                              : this$isFolder.equals(other$isFolder)) {
                                                                              Object this$localTrailerCount = this.getLocalTrailerCount();
                                                                              Object other$localTrailerCount = other.getLocalTrailerCount();
                                                                              if (this$localTrailerCount == null
                                                                                 ? other$localTrailerCount == null
                                                                                 : this$localTrailerCount.equals(other$localTrailerCount)) {
                                                                                 Object this$recursiveItemCount = this.getRecursiveItemCount();
                                                                                 Object other$recursiveItemCount = other.getRecursiveItemCount();
                                                                                 if (this$recursiveItemCount == null
                                                                                    ? other$recursiveItemCount == null
                                                                                    : this$recursiveItemCount.equals(other$recursiveItemCount)) {
                                                                                    Object this$childCount = this.getChildCount();
                                                                                    Object other$childCount = other.getChildCount();
                                                                                    if (this$childCount == null
                                                                                       ? other$childCount == null
                                                                                       : this$childCount.equals(other$childCount)) {
                                                                                       Object this$seasonCount = this.getSeasonCount();
                                                                                       Object other$seasonCount = other.getSeasonCount();
                                                                                       if (this$seasonCount == null
                                                                                          ? other$seasonCount == null
                                                                                          : this$seasonCount.equals(other$seasonCount)) {
                                                                                          Object this$specialFeatureCount = this.getSpecialFeatureCount();
                                                                                          Object other$specialFeatureCount = other.getSpecialFeatureCount();
                                                                                          if (this$specialFeatureCount == null
                                                                                             ? other$specialFeatureCount == null
                                                                                             : this$specialFeatureCount.equals(other$specialFeatureCount)) {
                                                                                             Object this$primaryImageAspectRatio = this.getPrimaryImageAspectRatio();
                                                                                             Object other$primaryImageAspectRatio = other.getPrimaryImageAspectRatio();
                                                                                             if (this$primaryImageAspectRatio == null
                                                                                                ? other$primaryImageAspectRatio == null
                                                                                                : this$primaryImageAspectRatio.equals(
                                                                                                   other$primaryImageAspectRatio
                                                                                                )) {
                                                                                                Object this$partCount = this.getPartCount();
                                                                                                Object other$partCount = other.getPartCount();
                                                                                                if (this$partCount == null
                                                                                                   ? other$partCount == null
                                                                                                   : this$partCount.equals(other$partCount)) {
                                                                                                   Object this$lockData = this.getLockData();
                                                                                                   Object other$lockData = other.getLockData();
                                                                                                   if (this$lockData == null
                                                                                                      ? other$lockData == null
                                                                                                      : this$lockData.equals(other$lockData)) {
                                                                                                      Object this$width = this.getWidth();
                                                                                                      Object other$width = other.getWidth();
                                                                                                      if (this$width == null
                                                                                                         ? other$width == null
                                                                                                         : this$width.equals(other$width)) {
                                                                                                         Object this$height = this.getHeight();
                                                                                                         Object other$height = other.getHeight();
                                                                                                         if (this$height == null
                                                                                                            ? other$height == null
                                                                                                            : this$height.equals(other$height)) {
                                                                                                            Object this$exposureTime = this.getExposureTime();
                                                                                                            Object other$exposureTime = other.getExposureTime();
                                                                                                            if (this$exposureTime == null
                                                                                                               ? other$exposureTime == null
                                                                                                               : this$exposureTime.equals(other$exposureTime)) {
                                                                                                               Object this$focalLength = this.getFocalLength();
                                                                                                               Object other$focalLength = other.getFocalLength();
                                                                                                               if (this$focalLength == null
                                                                                                                  ? other$focalLength == null
                                                                                                                  : this$focalLength.equals(other$focalLength)) {
                                                                                                                  Object this$aperture = this.getAperture();
                                                                                                                  Object other$aperture = other.getAperture();
                                                                                                                  if (this$aperture == null
                                                                                                                     ? other$aperture == null
                                                                                                                     : this$aperture.equals(other$aperture)) {
                                                                                                                     Object this$shutterSpeed = this.getShutterSpeed();
                                                                                                                     Object other$shutterSpeed = other.getShutterSpeed();
                                                                                                                     if (this$shutterSpeed == null
                                                                                                                        ? other$shutterSpeed == null
                                                                                                                        : this$shutterSpeed.equals(
                                                                                                                           other$shutterSpeed
                                                                                                                        )) {
                                                                                                                        Object this$latitude = this.getLatitude();
                                                                                                                        Object other$latitude = other.getLatitude();
                                                                                                                        if (this$latitude == null
                                                                                                                           ? other$latitude == null
                                                                                                                           : this$latitude.equals(
                                                                                                                              other$latitude
                                                                                                                           )) {
                                                                                                                           Object this$longitude = this.getLongitude();
                                                                                                                           Object other$longitude = other.getLongitude();
                                                                                                                           if (this$longitude == null
                                                                                                                              ? other$longitude == null
                                                                                                                              : this$longitude.equals(
                                                                                                                                 other$longitude
                                                                                                                              )) {
                                                                                                                              Object this$altitude = this.getAltitude();
                                                                                                                              Object other$altitude = other.getAltitude();
                                                                                                                              if (this$altitude == null
                                                                                                                                 ? other$altitude == null
                                                                                                                                 : this$altitude.equals(
                                                                                                                                    other$altitude
                                                                                                                                 )) {
                                                                                                                                 Object this$isoSpeedRating = this.getIsoSpeedRating();
                                                                                                                                 Object other$isoSpeedRating = other.getIsoSpeedRating();
                                                                                                                                 if (this$isoSpeedRating
                                                                                                                                       == null
                                                                                                                                    ? other$isoSpeedRating
                                                                                                                                       == null
                                                                                                                                    : this$isoSpeedRating.equals(
                                                                                                                                       other$isoSpeedRating
                                                                                                                                    )) {
                                                                                                                                    Object this$completionPercentage = this.getCompletionPercentage();
                                                                                                                                    Object other$completionPercentage = other.getCompletionPercentage();
                                                                                                                                    if (this$completionPercentage
                                                                                                                                          == null
                                                                                                                                       ? other$completionPercentage
                                                                                                                                          == null
                                                                                                                                       : this$completionPercentage.equals(
                                                                                                                                          other$completionPercentage
                                                                                                                                       )) {
                                                                                                                                       Object this$isRepeat = this.getIsRepeat();
                                                                                                                                       Object other$isRepeat = other.getIsRepeat();
                                                                                                                                       if (this$isRepeat
                                                                                                                                             == null
                                                                                                                                          ? other$isRepeat
                                                                                                                                             == null
                                                                                                                                          : this$isRepeat.equals(
                                                                                                                                             other$isRepeat
                                                                                                                                          )) {
                                                                                                                                          Object this$isNew = this.getIsNew();
                                                                                                                                          Object other$isNew = other.getIsNew();
                                                                                                                                          if (this$isNew
                                                                                                                                                == null
                                                                                                                                             ? other$isNew
                                                                                                                                                == null
                                                                                                                                             : this$isNew.equals(
                                                                                                                                                other$isNew
                                                                                                                                             )) {
                                                                                                                                             Object this$isMovie = this.getIsMovie();
                                                                                                                                             Object other$isMovie = other.getIsMovie();
                                                                                                                                             if (this$isMovie
                                                                                                                                                   == null
                                                                                                                                                ? other$isMovie
                                                                                                                                                   == null
                                                                                                                                                : this$isMovie.equals(
                                                                                                                                                   other$isMovie
                                                                                                                                                )) {
                                                                                                                                                Object this$isSports = this.getIsSports();
                                                                                                                                                Object other$isSports = other.getIsSports();
                                                                                                                                                if (this$isSports
                                                                                                                                                      == null
                                                                                                                                                   ? other$isSports
                                                                                                                                                      == null
                                                                                                                                                   : this$isSports.equals(
                                                                                                                                                      other$isSports
                                                                                                                                                   )) {
                                                                                                                                                   Object this$isSeries = this.getIsSeries();
                                                                                                                                                   Object other$isSeries = other.getIsSeries();
                                                                                                                                                   if (this$isSeries
                                                                                                                                                         == null
                                                                                                                                                      ? other$isSeries
                                                                                                                                                         == null
                                                                                                                                                      : this$isSeries.equals(
                                                                                                                                                         other$isSeries
                                                                                                                                                      )) {
                                                                                                                                                      Object this$isLive = this.getIsLive();
                                                                                                                                                      Object other$isLive = other.getIsLive();
                                                                                                                                                      if (this$isLive
                                                                                                                                                            == null
                                                                                                                                                         ? other$isLive
                                                                                                                                                            == null
                                                                                                                                                         : this$isLive.equals(
                                                                                                                                                            other$isLive
                                                                                                                                                         )) {
                                                                                                                                                         Object this$isNews = this.getIsNews();
                                                                                                                                                         Object other$isNews = other.getIsNews();
                                                                                                                                                         if (this$isNews
                                                                                                                                                               == null
                                                                                                                                                            ? other$isNews
                                                                                                                                                               == null
                                                                                                                                                            : this$isNews.equals(
                                                                                                                                                               other$isNews
                                                                                                                                                            )) {
                                                                                                                                                            Object this$isKids = this.getIsKids();
                                                                                                                                                            Object other$isKids = other.getIsKids();
                                                                                                                                                            if (this$isKids
                                                                                                                                                                  == null
                                                                                                                                                               ? other$isKids
                                                                                                                                                                  == null
                                                                                                                                                               : this$isKids.equals(
                                                                                                                                                                  other$isKids
                                                                                                                                                               )
                                                                                                                                                               )
                                                                                                                                                             {
                                                                                                                                                               Object this$isPremiere = this.getIsPremiere();
                                                                                                                                                               Object other$isPremiere = other.getIsPremiere();
                                                                                                                                                               if (this$isPremiere
                                                                                                                                                                     == null
                                                                                                                                                                  ? other$isPremiere
                                                                                                                                                                     == null
                                                                                                                                                                  : this$isPremiere.equals(
                                                                                                                                                                     other$isPremiere
                                                                                                                                                                  )
                                                                                                                                                                  )
                                                                                                                                                                {
                                                                                                                                                                  Object this$disabled = this.getDisabled();
                                                                                                                                                                  Object other$disabled = other.getDisabled();
                                                                                                                                                                  if (this$disabled
                                                                                                                                                                        == null
                                                                                                                                                                     ? other$disabled
                                                                                                                                                                        == null
                                                                                                                                                                     : this$disabled.equals(
                                                                                                                                                                        other$disabled
                                                                                                                                                                     )
                                                                                                                                                                     )
                                                                                                                                                                   {
                                                                                                                                                                     Object this$movieCount = this.getMovieCount();
                                                                                                                                                                     Object other$movieCount = other.getMovieCount();
                                                                                                                                                                     if (this$movieCount
                                                                                                                                                                           == null
                                                                                                                                                                        ? other$movieCount
                                                                                                                                                                           == null
                                                                                                                                                                        : this$movieCount.equals(
                                                                                                                                                                           other$movieCount
                                                                                                                                                                        )
                                                                                                                                                                        )
                                                                                                                                                                      {
                                                                                                                                                                        Object this$seriesCount = this.getSeriesCount();
                                                                                                                                                                        Object other$seriesCount = other.getSeriesCount();
                                                                                                                                                                        if (this$seriesCount
                                                                                                                                                                              == null
                                                                                                                                                                           ? other$seriesCount
                                                                                                                                                                              == null
                                                                                                                                                                           : this$seriesCount.equals(
                                                                                                                                                                              other$seriesCount
                                                                                                                                                                           )
                                                                                                                                                                           )
                                                                                                                                                                         {
                                                                                                                                                                           Object this$albumCount = this.getAlbumCount();
                                                                                                                                                                           Object other$albumCount = other.getAlbumCount();
                                                                                                                                                                           if (this$albumCount
                                                                                                                                                                                 == null
                                                                                                                                                                              ? other$albumCount
                                                                                                                                                                                 == null
                                                                                                                                                                              : this$albumCount.equals(
                                                                                                                                                                                 other$albumCount
                                                                                                                                                                              )
                                                                                                                                                                              )
                                                                                                                                                                            {
                                                                                                                                                                              Object this$songCount = this.getSongCount();
                                                                                                                                                                              Object other$songCount = other.getSongCount();
                                                                                                                                                                              if (this$songCount
                                                                                                                                                                                    == null
                                                                                                                                                                                 ? other$songCount
                                                                                                                                                                                    == null
                                                                                                                                                                                 : this$songCount.equals(
                                                                                                                                                                                    other$songCount
                                                                                                                                                                                 )
                                                                                                                                                                                 )
                                                                                                                                                                               {
                                                                                                                                                                                 Object this$musicVideoCount = this.getMusicVideoCount();
                                                                                                                                                                                 Object other$musicVideoCount = other.getMusicVideoCount();
                                                                                                                                                                                 if (this$musicVideoCount
                                                                                                                                                                                       == null
                                                                                                                                                                                    ? other$musicVideoCount
                                                                                                                                                                                       == null
                                                                                                                                                                                    : this$musicVideoCount.equals(
                                                                                                                                                                                       other$musicVideoCount
                                                                                                                                                                                    )
                                                                                                                                                                                    )
                                                                                                                                                                                  {
                                                                                                                                                                                    Object this$name = this.getName();
                                                                                                                                                                                    Object other$name = other.getName();
                                                                                                                                                                                    if (this$name
                                                                                                                                                                                          == null
                                                                                                                                                                                       ? other$name
                                                                                                                                                                                          == null
                                                                                                                                                                                       : this$name.equals(
                                                                                                                                                                                          other$name
                                                                                                                                                                                       )
                                                                                                                                                                                       )
                                                                                                                                                                                     {
                                                                                                                                                                                       Object this$originalTitle = this.getOriginalTitle();
                                                                                                                                                                                       Object other$originalTitle = other.getOriginalTitle();
                                                                                                                                                                                       if (this$originalTitle
                                                                                                                                                                                             == null
                                                                                                                                                                                          ? other$originalTitle
                                                                                                                                                                                             == null
                                                                                                                                                                                          : this$originalTitle.equals(
                                                                                                                                                                                             other$originalTitle
                                                                                                                                                                                          )
                                                                                                                                                                                          )
                                                                                                                                                                                        {
                                                                                                                                                                                          Object this$serverId = this.getServerId();
                                                                                                                                                                                          Object other$serverId = other.getServerId();
                                                                                                                                                                                          if (this$serverId
                                                                                                                                                                                                == null
                                                                                                                                                                                             ? other$serverId
                                                                                                                                                                                                == null
                                                                                                                                                                                             : this$serverId.equals(
                                                                                                                                                                                                other$serverId
                                                                                                                                                                                             )
                                                                                                                                                                                             )
                                                                                                                                                                                           {
                                                                                                                                                                                             Object this$id = this.getId();
                                                                                                                                                                                             Object other$id = other.getId();
                                                                                                                                                                                             if (this$id
                                                                                                                                                                                                   == null
                                                                                                                                                                                                ? other$id
                                                                                                                                                                                                   == null
                                                                                                                                                                                                : this$id.equals(
                                                                                                                                                                                                   other$id
                                                                                                                                                                                                )
                                                                                                                                                                                                )
                                                                                                                                                                                              {
                                                                                                                                                                                                Object this$guid = this.getGuid();
                                                                                                                                                                                                Object other$guid = other.getGuid();
                                                                                                                                                                                                if (this$guid
                                                                                                                                                                                                      == null
                                                                                                                                                                                                   ? other$guid
                                                                                                                                                                                                      == null
                                                                                                                                                                                                   : this$guid.equals(
                                                                                                                                                                                                      other$guid
                                                                                                                                                                                                   )
                                                                                                                                                                                                   )
                                                                                                                                                                                                 {
                                                                                                                                                                                                   Object this$etag = this.getEtag();
                                                                                                                                                                                                   Object other$etag = other.getEtag();
                                                                                                                                                                                                   if (this$etag
                                                                                                                                                                                                         == null
                                                                                                                                                                                                      ? other$etag
                                                                                                                                                                                                         == null
                                                                                                                                                                                                      : this$etag.equals(
                                                                                                                                                                                                         other$etag
                                                                                                                                                                                                      )
                                                                                                                                                                                                      )
                                                                                                                                                                                                    {
                                                                                                                                                                                                      Object this$prefix = this.getPrefix();
                                                                                                                                                                                                      Object other$prefix = other.getPrefix();
                                                                                                                                                                                                      if (this$prefix
                                                                                                                                                                                                            == null
                                                                                                                                                                                                         ? other$prefix
                                                                                                                                                                                                            == null
                                                                                                                                                                                                         : this$prefix.equals(
                                                                                                                                                                                                            other$prefix
                                                                                                                                                                                                         )
                                                                                                                                                                                                         )
                                                                                                                                                                                                       {
                                                                                                                                                                                                         Object this$tunerName = this.getTunerName();
                                                                                                                                                                                                         Object other$tunerName = other.getTunerName();
                                                                                                                                                                                                         if (this$tunerName
                                                                                                                                                                                                               == null
                                                                                                                                                                                                            ? other$tunerName
                                                                                                                                                                                                               == null
                                                                                                                                                                                                            : this$tunerName.equals(
                                                                                                                                                                                                               other$tunerName
                                                                                                                                                                                                            )
                                                                                                                                                                                                            )
                                                                                                                                                                                                          {
                                                                                                                                                                                                            Object this$playlistItemId = this.getPlaylistItemId();
                                                                                                                                                                                                            Object other$playlistItemId = other.getPlaylistItemId();
                                                                                                                                                                                                            if (this$playlistItemId
                                                                                                                                                                                                                  == null
                                                                                                                                                                                                               ? other$playlistItemId
                                                                                                                                                                                                                  == null
                                                                                                                                                                                                               : this$playlistItemId.equals(
                                                                                                                                                                                                                  other$playlistItemId
                                                                                                                                                                                                               )
                                                                                                                                                                                                               )
                                                                                                                                                                                                             {
                                                                                                                                                                                                               Object this$dateCreated = this.getDateCreated();
                                                                                                                                                                                                               Object other$dateCreated = other.getDateCreated();
                                                                                                                                                                                                               if (this$dateCreated
                                                                                                                                                                                                                     == null
                                                                                                                                                                                                                  ? other$dateCreated
                                                                                                                                                                                                                     == null
                                                                                                                                                                                                                  : this$dateCreated.equals(
                                                                                                                                                                                                                     other$dateCreated
                                                                                                                                                                                                                  )
                                                                                                                                                                                                                  )
                                                                                                                                                                                                                {
                                                                                                                                                                                                                  Object this$extraType = this.getExtraType();
                                                                                                                                                                                                                  Object other$extraType = other.getExtraType();
                                                                                                                                                                                                                  if (this$extraType
                                                                                                                                                                                                                        == null
                                                                                                                                                                                                                     ? other$extraType
                                                                                                                                                                                                                        == null
                                                                                                                                                                                                                     : this$extraType.equals(
                                                                                                                                                                                                                        other$extraType
                                                                                                                                                                                                                     )
                                                                                                                                                                                                                     )
                                                                                                                                                                                                                   {
                                                                                                                                                                                                                     Object this$presentationUniqueKey = this.getPresentationUniqueKey();
                                                                                                                                                                                                                     Object other$presentationUniqueKey = other.getPresentationUniqueKey();
                                                                                                                                                                                                                     if (this$presentationUniqueKey
                                                                                                                                                                                                                           == null
                                                                                                                                                                                                                        ? other$presentationUniqueKey
                                                                                                                                                                                                                           == null
                                                                                                                                                                                                                        : this$presentationUniqueKey.equals(
                                                                                                                                                                                                                           other$presentationUniqueKey
                                                                                                                                                                                                                        )
                                                                                                                                                                                                                        )
                                                                                                                                                                                                                      {
                                                                                                                                                                                                                        Object this$preferredMetadataLanguage = this.getPreferredMetadataLanguage();
                                                                                                                                                                                                                        Object other$preferredMetadataLanguage = other.getPreferredMetadataLanguage();
                                                                                                                                                                                                                        if (this$preferredMetadataLanguage
                                                                                                                                                                                                                              == null
                                                                                                                                                                                                                           ? other$preferredMetadataLanguage
                                                                                                                                                                                                                              == null
                                                                                                                                                                                                                           : this$preferredMetadataLanguage.equals(
                                                                                                                                                                                                                              other$preferredMetadataLanguage
                                                                                                                                                                                                                           )
                                                                                                                                                                                                                           )
                                                                                                                                                                                                                         {
                                                                                                                                                                                                                           Object this$preferredMetadataCountryCode = this.getPreferredMetadataCountryCode();
                                                                                                                                                                                                                           Object other$preferredMetadataCountryCode = other.getPreferredMetadataCountryCode();
                                                                                                                                                                                                                           if (this$preferredMetadataCountryCode
                                                                                                                                                                                                                                 == null
                                                                                                                                                                                                                              ? other$preferredMetadataCountryCode
                                                                                                                                                                                                                                 == null
                                                                                                                                                                                                                              : this$preferredMetadataCountryCode.equals(
                                                                                                                                                                                                                                 other$preferredMetadataCountryCode
                                                                                                                                                                                                                              )
                                                                                                                                                                                                                              )
                                                                                                                                                                                                                            {
                                                                                                                                                                                                                              Object this$syncStatus = this.getSyncStatus();
                                                                                                                                                                                                                              Object other$syncStatus = other.getSyncStatus();
                                                                                                                                                                                                                              if (this$syncStatus
                                                                                                                                                                                                                                    == null
                                                                                                                                                                                                                                 ? other$syncStatus
                                                                                                                                                                                                                                    == null
                                                                                                                                                                                                                                 : this$syncStatus.equals(
                                                                                                                                                                                                                                    other$syncStatus
                                                                                                                                                                                                                                 )
                                                                                                                                                                                                                                 )
                                                                                                                                                                                                                               {
                                                                                                                                                                                                                                 Object this$container = this.getContainer();
                                                                                                                                                                                                                                 Object other$container = other.getContainer();
                                                                                                                                                                                                                                 if (this$container
                                                                                                                                                                                                                                       == null
                                                                                                                                                                                                                                    ? other$container
                                                                                                                                                                                                                                       == null
                                                                                                                                                                                                                                    : this$container.equals(
                                                                                                                                                                                                                                       other$container
                                                                                                                                                                                                                                    )
                                                                                                                                                                                                                                    )
                                                                                                                                                                                                                                  {
                                                                                                                                                                                                                                    Object this$sortName = this.getSortName();
                                                                                                                                                                                                                                    Object other$sortName = other.getSortName();
                                                                                                                                                                                                                                    if (this$sortName
                                                                                                                                                                                                                                          == null
                                                                                                                                                                                                                                       ? other$sortName
                                                                                                                                                                                                                                          == null
                                                                                                                                                                                                                                       : this$sortName.equals(
                                                                                                                                                                                                                                          other$sortName
                                                                                                                                                                                                                                       )
                                                                                                                                                                                                                                       )
                                                                                                                                                                                                                                     {
                                                                                                                                                                                                                                       Object this$forcedSortName = this.getForcedSortName();
                                                                                                                                                                                                                                       Object other$forcedSortName = other.getForcedSortName();
                                                                                                                                                                                                                                       if (this$forcedSortName
                                                                                                                                                                                                                                             == null
                                                                                                                                                                                                                                          ? other$forcedSortName
                                                                                                                                                                                                                                             == null
                                                                                                                                                                                                                                          : this$forcedSortName.equals(
                                                                                                                                                                                                                                             other$forcedSortName
                                                                                                                                                                                                                                          )
                                                                                                                                                                                                                                          )
                                                                                                                                                                                                                                        {
                                                                                                                                                                                                                                          Object this$video3DFormat = this.getVideo3DFormat();
                                                                                                                                                                                                                                          Object other$video3DFormat = other.getVideo3DFormat();
                                                                                                                                                                                                                                          if (this$video3DFormat
                                                                                                                                                                                                                                                == null
                                                                                                                                                                                                                                             ? other$video3DFormat
                                                                                                                                                                                                                                                == null
                                                                                                                                                                                                                                             : this$video3DFormat.equals(
                                                                                                                                                                                                                                                other$video3DFormat
                                                                                                                                                                                                                                             )
                                                                                                                                                                                                                                             )
                                                                                                                                                                                                                                           {
                                                                                                                                                                                                                                             Object this$premiereDate = this.getPremiereDate();
                                                                                                                                                                                                                                             Object other$premiereDate = other.getPremiereDate();
                                                                                                                                                                                                                                             if (this$premiereDate
                                                                                                                                                                                                                                                   == null
                                                                                                                                                                                                                                                ? other$premiereDate
                                                                                                                                                                                                                                                   == null
                                                                                                                                                                                                                                                : this$premiereDate.equals(
                                                                                                                                                                                                                                                   other$premiereDate
                                                                                                                                                                                                                                                )
                                                                                                                                                                                                                                                )
                                                                                                                                                                                                                                              {
                                                                                                                                                                                                                                                Object this$externalUrls = this.getExternalUrls();
                                                                                                                                                                                                                                                Object other$externalUrls = other.getExternalUrls();
                                                                                                                                                                                                                                                if (this$externalUrls
                                                                                                                                                                                                                                                      == null
                                                                                                                                                                                                                                                   ? other$externalUrls
                                                                                                                                                                                                                                                      == null
                                                                                                                                                                                                                                                   : this$externalUrls.equals(
                                                                                                                                                                                                                                                      other$externalUrls
                                                                                                                                                                                                                                                   )
                                                                                                                                                                                                                                                   )
                                                                                                                                                                                                                                                 {
                                                                                                                                                                                                                                                   Object this$mediaSources = this.getMediaSources();
                                                                                                                                                                                                                                                   Object other$mediaSources = other.getMediaSources();
                                                                                                                                                                                                                                                   if (this$mediaSources
                                                                                                                                                                                                                                                         == null
                                                                                                                                                                                                                                                      ? other$mediaSources
                                                                                                                                                                                                                                                         == null
                                                                                                                                                                                                                                                      : this$mediaSources.equals(
                                                                                                                                                                                                                                                         other$mediaSources
                                                                                                                                                                                                                                                      )
                                                                                                                                                                                                                                                      )
                                                                                                                                                                                                                                                    {
                                                                                                                                                                                                                                                      Object this$gameSystem = this.getGameSystem();
                                                                                                                                                                                                                                                      Object other$gameSystem = other.getGameSystem();
                                                                                                                                                                                                                                                      if (this$gameSystem
                                                                                                                                                                                                                                                            == null
                                                                                                                                                                                                                                                         ? other$gameSystem
                                                                                                                                                                                                                                                            == null
                                                                                                                                                                                                                                                         : this$gameSystem.equals(
                                                                                                                                                                                                                                                            other$gameSystem
                                                                                                                                                                                                                                                         )
                                                                                                                                                                                                                                                         )
                                                                                                                                                                                                                                                       {
                                                                                                                                                                                                                                                         Object this$productionLocations = this.getProductionLocations();
                                                                                                                                                                                                                                                         Object other$productionLocations = other.getProductionLocations();
                                                                                                                                                                                                                                                         if (this$productionLocations
                                                                                                                                                                                                                                                               == null
                                                                                                                                                                                                                                                            ? other$productionLocations
                                                                                                                                                                                                                                                               == null
                                                                                                                                                                                                                                                            : this$productionLocations.equals(
                                                                                                                                                                                                                                                               other$productionLocations
                                                                                                                                                                                                                                                            )
                                                                                                                                                                                                                                                            )
                                                                                                                                                                                                                                                          {
                                                                                                                                                                                                                                                            Object this$path = this.getPath();
                                                                                                                                                                                                                                                            Object other$path = other.getPath();
                                                                                                                                                                                                                                                            if (this$path
                                                                                                                                                                                                                                                                  == null
                                                                                                                                                                                                                                                               ? other$path
                                                                                                                                                                                                                                                                  == null
                                                                                                                                                                                                                                                               : this$path.equals(
                                                                                                                                                                                                                                                                  other$path
                                                                                                                                                                                                                                                               )
                                                                                                                                                                                                                                                               )
                                                                                                                                                                                                                                                             {
                                                                                                                                                                                                                                                               Object this$officialRating = this.getOfficialRating();
                                                                                                                                                                                                                                                               Object other$officialRating = other.getOfficialRating();
                                                                                                                                                                                                                                                               if (this$officialRating
                                                                                                                                                                                                                                                                     == null
                                                                                                                                                                                                                                                                  ? other$officialRating
                                                                                                                                                                                                                                                                     == null
                                                                                                                                                                                                                                                                  : this$officialRating.equals(
                                                                                                                                                                                                                                                                     other$officialRating
                                                                                                                                                                                                                                                                  )
                                                                                                                                                                                                                                                                  )
                                                                                                                                                                                                                                                                {
                                                                                                                                                                                                                                                                  Object this$customRating = this.getCustomRating();
                                                                                                                                                                                                                                                                  Object other$customRating = other.getCustomRating();
                                                                                                                                                                                                                                                                  if (this$customRating
                                                                                                                                                                                                                                                                        == null
                                                                                                                                                                                                                                                                     ? other$customRating
                                                                                                                                                                                                                                                                        == null
                                                                                                                                                                                                                                                                     : this$customRating.equals(
                                                                                                                                                                                                                                                                        other$customRating
                                                                                                                                                                                                                                                                     )
                                                                                                                                                                                                                                                                     )
                                                                                                                                                                                                                                                                   {
                                                                                                                                                                                                                                                                     Object this$channelId = this.getChannelId();
                                                                                                                                                                                                                                                                     Object other$channelId = other.getChannelId();
                                                                                                                                                                                                                                                                     if (this$channelId
                                                                                                                                                                                                                                                                           == null
                                                                                                                                                                                                                                                                        ? other$channelId
                                                                                                                                                                                                                                                                           == null
                                                                                                                                                                                                                                                                        : this$channelId.equals(
                                                                                                                                                                                                                                                                           other$channelId
                                                                                                                                                                                                                                                                        )
                                                                                                                                                                                                                                                                        )
                                                                                                                                                                                                                                                                      {
                                                                                                                                                                                                                                                                        Object this$channelName = this.getChannelName();
                                                                                                                                                                                                                                                                        Object other$channelName = other.getChannelName();
                                                                                                                                                                                                                                                                        if (this$channelName
                                                                                                                                                                                                                                                                              == null
                                                                                                                                                                                                                                                                           ? other$channelName
                                                                                                                                                                                                                                                                              == null
                                                                                                                                                                                                                                                                           : this$channelName.equals(
                                                                                                                                                                                                                                                                              other$channelName
                                                                                                                                                                                                                                                                           )
                                                                                                                                                                                                                                                                           )
                                                                                                                                                                                                                                                                         {
                                                                                                                                                                                                                                                                           Object this$overview = this.getOverview();
                                                                                                                                                                                                                                                                           Object other$overview = other.getOverview();
                                                                                                                                                                                                                                                                           if (this$overview
                                                                                                                                                                                                                                                                                 == null
                                                                                                                                                                                                                                                                              ? other$overview
                                                                                                                                                                                                                                                                                 == null
                                                                                                                                                                                                                                                                              : this$overview.equals(
                                                                                                                                                                                                                                                                                 other$overview
                                                                                                                                                                                                                                                                              )
                                                                                                                                                                                                                                                                              )
                                                                                                                                                                                                                                                                            {
                                                                                                                                                                                                                                                                              Object this$taglines = this.getTaglines();
                                                                                                                                                                                                                                                                              Object other$taglines = other.getTaglines();
                                                                                                                                                                                                                                                                              if (this$taglines
                                                                                                                                                                                                                                                                                    == null
                                                                                                                                                                                                                                                                                 ? other$taglines
                                                                                                                                                                                                                                                                                    == null
                                                                                                                                                                                                                                                                                 : this$taglines.equals(
                                                                                                                                                                                                                                                                                    other$taglines
                                                                                                                                                                                                                                                                                 )
                                                                                                                                                                                                                                                                                 )
                                                                                                                                                                                                                                                                               {
                                                                                                                                                                                                                                                                                 Object this$genres = this.getGenres();
                                                                                                                                                                                                                                                                                 Object other$genres = other.getGenres();
                                                                                                                                                                                                                                                                                 if (this$genres
                                                                                                                                                                                                                                                                                       == null
                                                                                                                                                                                                                                                                                    ? other$genres
                                                                                                                                                                                                                                                                                       == null
                                                                                                                                                                                                                                                                                    : this$genres.equals(
                                                                                                                                                                                                                                                                                       other$genres
                                                                                                                                                                                                                                                                                    )
                                                                                                                                                                                                                                                                                    )
                                                                                                                                                                                                                                                                                  {
                                                                                                                                                                                                                                                                                    Object this$fileName = this.getFileName();
                                                                                                                                                                                                                                                                                    Object other$fileName = other.getFileName();
                                                                                                                                                                                                                                                                                    if (this$fileName
                                                                                                                                                                                                                                                                                          == null
                                                                                                                                                                                                                                                                                       ? other$fileName
                                                                                                                                                                                                                                                                                          == null
                                                                                                                                                                                                                                                                                       : this$fileName.equals(
                                                                                                                                                                                                                                                                                          other$fileName
                                                                                                                                                                                                                                                                                       )
                                                                                                                                                                                                                                                                                       )
                                                                                                                                                                                                                                                                                     {
                                                                                                                                                                                                                                                                                       Object this$number = this.getNumber();
                                                                                                                                                                                                                                                                                       Object other$number = other.getNumber();
                                                                                                                                                                                                                                                                                       if (this$number
                                                                                                                                                                                                                                                                                             == null
                                                                                                                                                                                                                                                                                          ? other$number
                                                                                                                                                                                                                                                                                             == null
                                                                                                                                                                                                                                                                                          : this$number.equals(
                                                                                                                                                                                                                                                                                             other$number
                                                                                                                                                                                                                                                                                          )
                                                                                                                                                                                                                                                                                          )
                                                                                                                                                                                                                                                                                        {
                                                                                                                                                                                                                                                                                          Object this$channelNumber = this.getChannelNumber();
                                                                                                                                                                                                                                                                                          Object other$channelNumber = other.getChannelNumber();
                                                                                                                                                                                                                                                                                          if (this$channelNumber
                                                                                                                                                                                                                                                                                                == null
                                                                                                                                                                                                                                                                                             ? other$channelNumber
                                                                                                                                                                                                                                                                                                == null
                                                                                                                                                                                                                                                                                             : this$channelNumber.equals(
                                                                                                                                                                                                                                                                                                other$channelNumber
                                                                                                                                                                                                                                                                                             )
                                                                                                                                                                                                                                                                                             )
                                                                                                                                                                                                                                                                                           {
                                                                                                                                                                                                                                                                                             Object this$remoteTrailers = this.getRemoteTrailers();
                                                                                                                                                                                                                                                                                             Object other$remoteTrailers = other.getRemoteTrailers();
                                                                                                                                                                                                                                                                                             if (this$remoteTrailers
                                                                                                                                                                                                                                                                                                   == null
                                                                                                                                                                                                                                                                                                ? other$remoteTrailers
                                                                                                                                                                                                                                                                                                   == null
                                                                                                                                                                                                                                                                                                : this$remoteTrailers.equals(
                                                                                                                                                                                                                                                                                                   other$remoteTrailers
                                                                                                                                                                                                                                                                                                )
                                                                                                                                                                                                                                                                                                )
                                                                                                                                                                                                                                                                                              {
                                                                                                                                                                                                                                                                                                Object this$providerIds = this.getProviderIds();
                                                                                                                                                                                                                                                                                                Object other$providerIds = other.getProviderIds();
                                                                                                                                                                                                                                                                                                if (this$providerIds
                                                                                                                                                                                                                                                                                                      == null
                                                                                                                                                                                                                                                                                                   ? other$providerIds
                                                                                                                                                                                                                                                                                                      == null
                                                                                                                                                                                                                                                                                                   : this$providerIds.equals(
                                                                                                                                                                                                                                                                                                      other$providerIds
                                                                                                                                                                                                                                                                                                   )
                                                                                                                                                                                                                                                                                                   )
                                                                                                                                                                                                                                                                                                 {
                                                                                                                                                                                                                                                                                                   Object this$parentId = this.getParentId();
                                                                                                                                                                                                                                                                                                   Object other$parentId = other.getParentId();
                                                                                                                                                                                                                                                                                                   if (this$parentId
                                                                                                                                                                                                                                                                                                         == null
                                                                                                                                                                                                                                                                                                      ? other$parentId
                                                                                                                                                                                                                                                                                                         == null
                                                                                                                                                                                                                                                                                                      : this$parentId.equals(
                                                                                                                                                                                                                                                                                                         other$parentId
                                                                                                                                                                                                                                                                                                      )
                                                                                                                                                                                                                                                                                                      )
                                                                                                                                                                                                                                                                                                    {
                                                                                                                                                                                                                                                                                                      Object this$type = this.getType();
                                                                                                                                                                                                                                                                                                      Object other$type = other.getType();
                                                                                                                                                                                                                                                                                                      if (this$type
                                                                                                                                                                                                                                                                                                            == null
                                                                                                                                                                                                                                                                                                         ? other$type
                                                                                                                                                                                                                                                                                                            == null
                                                                                                                                                                                                                                                                                                         : this$type.equals(
                                                                                                                                                                                                                                                                                                            other$type
                                                                                                                                                                                                                                                                                                         )
                                                                                                                                                                                                                                                                                                         )
                                                                                                                                                                                                                                                                                                       {
                                                                                                                                                                                                                                                                                                         Object this$people = this.getPeople();
                                                                                                                                                                                                                                                                                                         Object other$people = other.getPeople();
                                                                                                                                                                                                                                                                                                         if (this$people
                                                                                                                                                                                                                                                                                                               == null
                                                                                                                                                                                                                                                                                                            ? other$people
                                                                                                                                                                                                                                                                                                               == null
                                                                                                                                                                                                                                                                                                            : this$people.equals(
                                                                                                                                                                                                                                                                                                               other$people
                                                                                                                                                                                                                                                                                                            )
                                                                                                                                                                                                                                                                                                            )
                                                                                                                                                                                                                                                                                                          {
                                                                                                                                                                                                                                                                                                            Object this$studios = this.getStudios();
                                                                                                                                                                                                                                                                                                            Object other$studios = other.getStudios();
                                                                                                                                                                                                                                                                                                            if (this$studios
                                                                                                                                                                                                                                                                                                                  == null
                                                                                                                                                                                                                                                                                                               ? other$studios
                                                                                                                                                                                                                                                                                                                  == null
                                                                                                                                                                                                                                                                                                               : this$studios.equals(
                                                                                                                                                                                                                                                                                                                  other$studios
                                                                                                                                                                                                                                                                                                               )
                                                                                                                                                                                                                                                                                                               )
                                                                                                                                                                                                                                                                                                             {
                                                                                                                                                                                                                                                                                                               Object this$genreItems = this.getGenreItems();
                                                                                                                                                                                                                                                                                                               Object other$genreItems = other.getGenreItems();
                                                                                                                                                                                                                                                                                                               if (this$genreItems
                                                                                                                                                                                                                                                                                                                     == null
                                                                                                                                                                                                                                                                                                                  ? other$genreItems
                                                                                                                                                                                                                                                                                                                     == null
                                                                                                                                                                                                                                                                                                                  : this$genreItems.equals(
                                                                                                                                                                                                                                                                                                                     other$genreItems
                                                                                                                                                                                                                                                                                                                  )
                                                                                                                                                                                                                                                                                                                  )
                                                                                                                                                                                                                                                                                                                {
                                                                                                                                                                                                                                                                                                                  Object this$tagItems = this.getTagItems();
                                                                                                                                                                                                                                                                                                                  Object other$tagItems = other.getTagItems();
                                                                                                                                                                                                                                                                                                                  if (this$tagItems
                                                                                                                                                                                                                                                                                                                        == null
                                                                                                                                                                                                                                                                                                                     ? other$tagItems
                                                                                                                                                                                                                                                                                                                        == null
                                                                                                                                                                                                                                                                                                                     : this$tagItems.equals(
                                                                                                                                                                                                                                                                                                                        other$tagItems
                                                                                                                                                                                                                                                                                                                     )
                                                                                                                                                                                                                                                                                                                     )
                                                                                                                                                                                                                                                                                                                   {
                                                                                                                                                                                                                                                                                                                     Object this$parentLogoItemId = this.getParentLogoItemId();
                                                                                                                                                                                                                                                                                                                     Object other$parentLogoItemId = other.getParentLogoItemId();
                                                                                                                                                                                                                                                                                                                     if (this$parentLogoItemId
                                                                                                                                                                                                                                                                                                                           == null
                                                                                                                                                                                                                                                                                                                        ? other$parentLogoItemId
                                                                                                                                                                                                                                                                                                                           == null
                                                                                                                                                                                                                                                                                                                        : this$parentLogoItemId.equals(
                                                                                                                                                                                                                                                                                                                           other$parentLogoItemId
                                                                                                                                                                                                                                                                                                                        )
                                                                                                                                                                                                                                                                                                                        )
                                                                                                                                                                                                                                                                                                                      {
                                                                                                                                                                                                                                                                                                                        Object this$parentBackdropItemId = this.getParentBackdropItemId();
                                                                                                                                                                                                                                                                                                                        Object other$parentBackdropItemId = other.getParentBackdropItemId();
                                                                                                                                                                                                                                                                                                                        if (this$parentBackdropItemId
                                                                                                                                                                                                                                                                                                                              == null
                                                                                                                                                                                                                                                                                                                           ? other$parentBackdropItemId
                                                                                                                                                                                                                                                                                                                              == null
                                                                                                                                                                                                                                                                                                                           : this$parentBackdropItemId.equals(
                                                                                                                                                                                                                                                                                                                              other$parentBackdropItemId
                                                                                                                                                                                                                                                                                                                           )
                                                                                                                                                                                                                                                                                                                           )
                                                                                                                                                                                                                                                                                                                         {
                                                                                                                                                                                                                                                                                                                           Object this$parentBackdropImageTags = this.getParentBackdropImageTags();
                                                                                                                                                                                                                                                                                                                           Object other$parentBackdropImageTags = other.getParentBackdropImageTags();
                                                                                                                                                                                                                                                                                                                           if (this$parentBackdropImageTags
                                                                                                                                                                                                                                                                                                                                 == null
                                                                                                                                                                                                                                                                                                                              ? other$parentBackdropImageTags
                                                                                                                                                                                                                                                                                                                                 == null
                                                                                                                                                                                                                                                                                                                              : this$parentBackdropImageTags.equals(
                                                                                                                                                                                                                                                                                                                                 other$parentBackdropImageTags
                                                                                                                                                                                                                                                                                                                              )
                                                                                                                                                                                                                                                                                                                              )
                                                                                                                                                                                                                                                                                                                            {
                                                                                                                                                                                                                                                                                                                              Object this$userData = this.getUserData();
                                                                                                                                                                                                                                                                                                                              Object other$userData = other.getUserData();
                                                                                                                                                                                                                                                                                                                              if (this$userData
                                                                                                                                                                                                                                                                                                                                    == null
                                                                                                                                                                                                                                                                                                                                 ? other$userData
                                                                                                                                                                                                                                                                                                                                    == null
                                                                                                                                                                                                                                                                                                                                 : this$userData.equals(
                                                                                                                                                                                                                                                                                                                                    other$userData
                                                                                                                                                                                                                                                                                                                                 )
                                                                                                                                                                                                                                                                                                                                 )
                                                                                                                                                                                                                                                                                                                               {
                                                                                                                                                                                                                                                                                                                                 Object this$seriesName = this.getSeriesName();
                                                                                                                                                                                                                                                                                                                                 Object other$seriesName = other.getSeriesName();
                                                                                                                                                                                                                                                                                                                                 if (this$seriesName
                                                                                                                                                                                                                                                                                                                                       == null
                                                                                                                                                                                                                                                                                                                                    ? other$seriesName
                                                                                                                                                                                                                                                                                                                                       == null
                                                                                                                                                                                                                                                                                                                                    : this$seriesName.equals(
                                                                                                                                                                                                                                                                                                                                       other$seriesName
                                                                                                                                                                                                                                                                                                                                    )
                                                                                                                                                                                                                                                                                                                                    )
                                                                                                                                                                                                                                                                                                                                  {
                                                                                                                                                                                                                                                                                                                                    Object this$seriesId = this.getSeriesId();
                                                                                                                                                                                                                                                                                                                                    Object other$seriesId = other.getSeriesId();
                                                                                                                                                                                                                                                                                                                                    if (this$seriesId
                                                                                                                                                                                                                                                                                                                                          == null
                                                                                                                                                                                                                                                                                                                                       ? other$seriesId
                                                                                                                                                                                                                                                                                                                                          == null
                                                                                                                                                                                                                                                                                                                                       : this$seriesId.equals(
                                                                                                                                                                                                                                                                                                                                          other$seriesId
                                                                                                                                                                                                                                                                                                                                       )
                                                                                                                                                                                                                                                                                                                                       )
                                                                                                                                                                                                                                                                                                                                     {
                                                                                                                                                                                                                                                                                                                                       Object this$seasonId = this.getSeasonId();
                                                                                                                                                                                                                                                                                                                                       Object other$seasonId = other.getSeasonId();
                                                                                                                                                                                                                                                                                                                                       if (this$seasonId
                                                                                                                                                                                                                                                                                                                                             == null
                                                                                                                                                                                                                                                                                                                                          ? other$seasonId
                                                                                                                                                                                                                                                                                                                                             == null
                                                                                                                                                                                                                                                                                                                                          : this$seasonId.equals(
                                                                                                                                                                                                                                                                                                                                             other$seasonId
                                                                                                                                                                                                                                                                                                                                          )
                                                                                                                                                                                                                                                                                                                                          )
                                                                                                                                                                                                                                                                                                                                        {
                                                                                                                                                                                                                                                                                                                                          Object this$displayPreferencesId = this.getDisplayPreferencesId();
                                                                                                                                                                                                                                                                                                                                          Object other$displayPreferencesId = other.getDisplayPreferencesId();
                                                                                                                                                                                                                                                                                                                                          if (this$displayPreferencesId
                                                                                                                                                                                                                                                                                                                                                == null
                                                                                                                                                                                                                                                                                                                                             ? other$displayPreferencesId
                                                                                                                                                                                                                                                                                                                                                == null
                                                                                                                                                                                                                                                                                                                                             : this$displayPreferencesId.equals(
                                                                                                                                                                                                                                                                                                                                                other$displayPreferencesId
                                                                                                                                                                                                                                                                                                                                             )
                                                                                                                                                                                                                                                                                                                                             )
                                                                                                                                                                                                                                                                                                                                           {
                                                                                                                                                                                                                                                                                                                                             Object this$status = this.getStatus();
                                                                                                                                                                                                                                                                                                                                             Object other$status = other.getStatus();
                                                                                                                                                                                                                                                                                                                                             if (this$status
                                                                                                                                                                                                                                                                                                                                                   == null
                                                                                                                                                                                                                                                                                                                                                ? other$status
                                                                                                                                                                                                                                                                                                                                                   == null
                                                                                                                                                                                                                                                                                                                                                : this$status.equals(
                                                                                                                                                                                                                                                                                                                                                   other$status
                                                                                                                                                                                                                                                                                                                                                )
                                                                                                                                                                                                                                                                                                                                                )
                                                                                                                                                                                                                                                                                                                                              {
                                                                                                                                                                                                                                                                                                                                                Object this$airDays = this.getAirDays();
                                                                                                                                                                                                                                                                                                                                                Object other$airDays = other.getAirDays();
                                                                                                                                                                                                                                                                                                                                                if (this$airDays
                                                                                                                                                                                                                                                                                                                                                      == null
                                                                                                                                                                                                                                                                                                                                                   ? other$airDays
                                                                                                                                                                                                                                                                                                                                                      == null
                                                                                                                                                                                                                                                                                                                                                   : this$airDays.equals(
                                                                                                                                                                                                                                                                                                                                                      other$airDays
                                                                                                                                                                                                                                                                                                                                                   )
                                                                                                                                                                                                                                                                                                                                                   )
                                                                                                                                                                                                                                                                                                                                                 {
                                                                                                                                                                                                                                                                                                                                                   Object this$tags = this.getTags();
                                                                                                                                                                                                                                                                                                                                                   Object other$tags = other.getTags();
                                                                                                                                                                                                                                                                                                                                                   if (this$tags
                                                                                                                                                                                                                                                                                                                                                         == null
                                                                                                                                                                                                                                                                                                                                                      ? other$tags
                                                                                                                                                                                                                                                                                                                                                         == null
                                                                                                                                                                                                                                                                                                                                                      : this$tags.equals(
                                                                                                                                                                                                                                                                                                                                                         other$tags
                                                                                                                                                                                                                                                                                                                                                      )
                                                                                                                                                                                                                                                                                                                                                      )
                                                                                                                                                                                                                                                                                                                                                    {
                                                                                                                                                                                                                                                                                                                                                      Object this$artists = this.getArtists();
                                                                                                                                                                                                                                                                                                                                                      Object other$artists = other.getArtists();
                                                                                                                                                                                                                                                                                                                                                      if (this$artists
                                                                                                                                                                                                                                                                                                                                                            == null
                                                                                                                                                                                                                                                                                                                                                         ? other$artists
                                                                                                                                                                                                                                                                                                                                                            == null
                                                                                                                                                                                                                                                                                                                                                         : this$artists.equals(
                                                                                                                                                                                                                                                                                                                                                            other$artists
                                                                                                                                                                                                                                                                                                                                                         )
                                                                                                                                                                                                                                                                                                                                                         )
                                                                                                                                                                                                                                                                                                                                                       {
                                                                                                                                                                                                                                                                                                                                                         Object this$artistItems = this.getArtistItems();
                                                                                                                                                                                                                                                                                                                                                         Object other$artistItems = other.getArtistItems();
                                                                                                                                                                                                                                                                                                                                                         if (this$artistItems
                                                                                                                                                                                                                                                                                                                                                               == null
                                                                                                                                                                                                                                                                                                                                                            ? other$artistItems
                                                                                                                                                                                                                                                                                                                                                               == null
                                                                                                                                                                                                                                                                                                                                                            : this$artistItems.equals(
                                                                                                                                                                                                                                                                                                                                                               other$artistItems
                                                                                                                                                                                                                                                                                                                                                            )
                                                                                                                                                                                                                                                                                                                                                            )
                                                                                                                                                                                                                                                                                                                                                          {
                                                                                                                                                                                                                                                                                                                                                            Object this$composers = this.getComposers();
                                                                                                                                                                                                                                                                                                                                                            Object other$composers = other.getComposers();
                                                                                                                                                                                                                                                                                                                                                            if (this$composers
                                                                                                                                                                                                                                                                                                                                                                  == null
                                                                                                                                                                                                                                                                                                                                                               ? other$composers
                                                                                                                                                                                                                                                                                                                                                                  == null
                                                                                                                                                                                                                                                                                                                                                               : this$composers.equals(
                                                                                                                                                                                                                                                                                                                                                                  other$composers
                                                                                                                                                                                                                                                                                                                                                               )
                                                                                                                                                                                                                                                                                                                                                               )
                                                                                                                                                                                                                                                                                                                                                             {
                                                                                                                                                                                                                                                                                                                                                               Object this$album = this.getAlbum();
                                                                                                                                                                                                                                                                                                                                                               Object other$album = other.getAlbum();
                                                                                                                                                                                                                                                                                                                                                               if (this$album
                                                                                                                                                                                                                                                                                                                                                                     == null
                                                                                                                                                                                                                                                                                                                                                                  ? other$album
                                                                                                                                                                                                                                                                                                                                                                     == null
                                                                                                                                                                                                                                                                                                                                                                  : this$album.equals(
                                                                                                                                                                                                                                                                                                                                                                     other$album
                                                                                                                                                                                                                                                                                                                                                                  )
                                                                                                                                                                                                                                                                                                                                                                  )
                                                                                                                                                                                                                                                                                                                                                                {
                                                                                                                                                                                                                                                                                                                                                                  Object this$collectionType = this.getCollectionType();
                                                                                                                                                                                                                                                                                                                                                                  Object other$collectionType = other.getCollectionType();
                                                                                                                                                                                                                                                                                                                                                                  if (this$collectionType
                                                                                                                                                                                                                                                                                                                                                                        == null
                                                                                                                                                                                                                                                                                                                                                                     ? other$collectionType
                                                                                                                                                                                                                                                                                                                                                                        == null
                                                                                                                                                                                                                                                                                                                                                                     : this$collectionType.equals(
                                                                                                                                                                                                                                                                                                                                                                        other$collectionType
                                                                                                                                                                                                                                                                                                                                                                     )
                                                                                                                                                                                                                                                                                                                                                                     )
                                                                                                                                                                                                                                                                                                                                                                   {
                                                                                                                                                                                                                                                                                                                                                                     Object this$displayOrder = this.getDisplayOrder();
                                                                                                                                                                                                                                                                                                                                                                     Object other$displayOrder = other.getDisplayOrder();
                                                                                                                                                                                                                                                                                                                                                                     if (this$displayOrder
                                                                                                                                                                                                                                                                                                                                                                           == null
                                                                                                                                                                                                                                                                                                                                                                        ? other$displayOrder
                                                                                                                                                                                                                                                                                                                                                                           == null
                                                                                                                                                                                                                                                                                                                                                                        : this$displayOrder.equals(
                                                                                                                                                                                                                                                                                                                                                                           other$displayOrder
                                                                                                                                                                                                                                                                                                                                                                        )
                                                                                                                                                                                                                                                                                                                                                                        )
                                                                                                                                                                                                                                                                                                                                                                      {
                                                                                                                                                                                                                                                                                                                                                                        Object this$albumId = this.getAlbumId();
                                                                                                                                                                                                                                                                                                                                                                        Object other$albumId = other.getAlbumId();
                                                                                                                                                                                                                                                                                                                                                                        if (this$albumId
                                                                                                                                                                                                                                                                                                                                                                              == null
                                                                                                                                                                                                                                                                                                                                                                           ? other$albumId
                                                                                                                                                                                                                                                                                                                                                                              == null
                                                                                                                                                                                                                                                                                                                                                                           : this$albumId.equals(
                                                                                                                                                                                                                                                                                                                                                                              other$albumId
                                                                                                                                                                                                                                                                                                                                                                           )
                                                                                                                                                                                                                                                                                                                                                                           )
                                                                                                                                                                                                                                                                                                                                                                         {
                                                                                                                                                                                                                                                                                                                                                                           Object this$albumPrimaryImageTag = this.getAlbumPrimaryImageTag();
                                                                                                                                                                                                                                                                                                                                                                           Object other$albumPrimaryImageTag = other.getAlbumPrimaryImageTag();
                                                                                                                                                                                                                                                                                                                                                                           if (this$albumPrimaryImageTag
                                                                                                                                                                                                                                                                                                                                                                                 == null
                                                                                                                                                                                                                                                                                                                                                                              ? other$albumPrimaryImageTag
                                                                                                                                                                                                                                                                                                                                                                                 == null
                                                                                                                                                                                                                                                                                                                                                                              : this$albumPrimaryImageTag.equals(
                                                                                                                                                                                                                                                                                                                                                                                 other$albumPrimaryImageTag
                                                                                                                                                                                                                                                                                                                                                                              )
                                                                                                                                                                                                                                                                                                                                                                              )
                                                                                                                                                                                                                                                                                                                                                                            {
                                                                                                                                                                                                                                                                                                                                                                              Object this$seriesPrimaryImageTag = this.getSeriesPrimaryImageTag();
                                                                                                                                                                                                                                                                                                                                                                              Object other$seriesPrimaryImageTag = other.getSeriesPrimaryImageTag();
                                                                                                                                                                                                                                                                                                                                                                              if (this$seriesPrimaryImageTag
                                                                                                                                                                                                                                                                                                                                                                                    == null
                                                                                                                                                                                                                                                                                                                                                                                 ? other$seriesPrimaryImageTag
                                                                                                                                                                                                                                                                                                                                                                                    == null
                                                                                                                                                                                                                                                                                                                                                                                 : this$seriesPrimaryImageTag.equals(
                                                                                                                                                                                                                                                                                                                                                                                    other$seriesPrimaryImageTag
                                                                                                                                                                                                                                                                                                                                                                                 )
                                                                                                                                                                                                                                                                                                                                                                                 )
                                                                                                                                                                                                                                                                                                                                                                               {
                                                                                                                                                                                                                                                                                                                                                                                 Object this$albumArtist = this.getAlbumArtist();
                                                                                                                                                                                                                                                                                                                                                                                 Object other$albumArtist = other.getAlbumArtist();
                                                                                                                                                                                                                                                                                                                                                                                 if (this$albumArtist
                                                                                                                                                                                                                                                                                                                                                                                       == null
                                                                                                                                                                                                                                                                                                                                                                                    ? other$albumArtist
                                                                                                                                                                                                                                                                                                                                                                                       == null
                                                                                                                                                                                                                                                                                                                                                                                    : this$albumArtist.equals(
                                                                                                                                                                                                                                                                                                                                                                                       other$albumArtist
                                                                                                                                                                                                                                                                                                                                                                                    )
                                                                                                                                                                                                                                                                                                                                                                                    )
                                                                                                                                                                                                                                                                                                                                                                                  {
                                                                                                                                                                                                                                                                                                                                                                                    Object this$albumArtists = this.getAlbumArtists();
                                                                                                                                                                                                                                                                                                                                                                                    Object other$albumArtists = other.getAlbumArtists();
                                                                                                                                                                                                                                                                                                                                                                                    if (this$albumArtists
                                                                                                                                                                                                                                                                                                                                                                                          == null
                                                                                                                                                                                                                                                                                                                                                                                       ? other$albumArtists
                                                                                                                                                                                                                                                                                                                                                                                          == null
                                                                                                                                                                                                                                                                                                                                                                                       : this$albumArtists.equals(
                                                                                                                                                                                                                                                                                                                                                                                          other$albumArtists
                                                                                                                                                                                                                                                                                                                                                                                       )
                                                                                                                                                                                                                                                                                                                                                                                       )
                                                                                                                                                                                                                                                                                                                                                                                     {
                                                                                                                                                                                                                                                                                                                                                                                       Object this$seasonName = this.getSeasonName();
                                                                                                                                                                                                                                                                                                                                                                                       Object other$seasonName = other.getSeasonName();
                                                                                                                                                                                                                                                                                                                                                                                       if (this$seasonName
                                                                                                                                                                                                                                                                                                                                                                                             == null
                                                                                                                                                                                                                                                                                                                                                                                          ? other$seasonName
                                                                                                                                                                                                                                                                                                                                                                                             == null
                                                                                                                                                                                                                                                                                                                                                                                          : this$seasonName.equals(
                                                                                                                                                                                                                                                                                                                                                                                             other$seasonName
                                                                                                                                                                                                                                                                                                                                                                                          )
                                                                                                                                                                                                                                                                                                                                                                                          )
                                                                                                                                                                                                                                                                                                                                                                                        {
                                                                                                                                                                                                                                                                                                                                                                                          Object this$mediaStreams = this.getMediaStreams();
                                                                                                                                                                                                                                                                                                                                                                                          Object other$mediaStreams = other.getMediaStreams();
                                                                                                                                                                                                                                                                                                                                                                                          if (this$mediaStreams
                                                                                                                                                                                                                                                                                                                                                                                                == null
                                                                                                                                                                                                                                                                                                                                                                                             ? other$mediaStreams
                                                                                                                                                                                                                                                                                                                                                                                                == null
                                                                                                                                                                                                                                                                                                                                                                                             : this$mediaStreams.equals(
                                                                                                                                                                                                                                                                                                                                                                                                other$mediaStreams
                                                                                                                                                                                                                                                                                                                                                                                             )
                                                                                                                                                                                                                                                                                                                                                                                             )
                                                                                                                                                                                                                                                                                                                                                                                           {
                                                                                                                                                                                                                                                                                                                                                                                             Object this$imageTags = this.getImageTags();
                                                                                                                                                                                                                                                                                                                                                                                             Object other$imageTags = other.getImageTags();
                                                                                                                                                                                                                                                                                                                                                                                             if (this$imageTags
                                                                                                                                                                                                                                                                                                                                                                                                   == null
                                                                                                                                                                                                                                                                                                                                                                                                ? other$imageTags
                                                                                                                                                                                                                                                                                                                                                                                                   == null
                                                                                                                                                                                                                                                                                                                                                                                                : this$imageTags.equals(
                                                                                                                                                                                                                                                                                                                                                                                                   other$imageTags
                                                                                                                                                                                                                                                                                                                                                                                                )
                                                                                                                                                                                                                                                                                                                                                                                                )
                                                                                                                                                                                                                                                                                                                                                                                              {
                                                                                                                                                                                                                                                                                                                                                                                                Object this$backdropImageTags = this.getBackdropImageTags();
                                                                                                                                                                                                                                                                                                                                                                                                Object other$backdropImageTags = other.getBackdropImageTags();
                                                                                                                                                                                                                                                                                                                                                                                                if (this$backdropImageTags
                                                                                                                                                                                                                                                                                                                                                                                                      == null
                                                                                                                                                                                                                                                                                                                                                                                                   ? other$backdropImageTags
                                                                                                                                                                                                                                                                                                                                                                                                      == null
                                                                                                                                                                                                                                                                                                                                                                                                   : this$backdropImageTags.equals(
                                                                                                                                                                                                                                                                                                                                                                                                      other$backdropImageTags
                                                                                                                                                                                                                                                                                                                                                                                                   )
                                                                                                                                                                                                                                                                                                                                                                                                   )
                                                                                                                                                                                                                                                                                                                                                                                                 {
                                                                                                                                                                                                                                                                                                                                                                                                   Object this$parentLogoImageTag = this.getParentLogoImageTag();
                                                                                                                                                                                                                                                                                                                                                                                                   Object other$parentLogoImageTag = other.getParentLogoImageTag();
                                                                                                                                                                                                                                                                                                                                                                                                   if (this$parentLogoImageTag
                                                                                                                                                                                                                                                                                                                                                                                                         == null
                                                                                                                                                                                                                                                                                                                                                                                                      ? other$parentLogoImageTag
                                                                                                                                                                                                                                                                                                                                                                                                         == null
                                                                                                                                                                                                                                                                                                                                                                                                      : this$parentLogoImageTag.equals(
                                                                                                                                                                                                                                                                                                                                                                                                         other$parentLogoImageTag
                                                                                                                                                                                                                                                                                                                                                                                                      )
                                                                                                                                                                                                                                                                                                                                                                                                      )
                                                                                                                                                                                                                                                                                                                                                                                                    {
                                                                                                                                                                                                                                                                                                                                                                                                      Object this$seriesStudio = this.getSeriesStudio();
                                                                                                                                                                                                                                                                                                                                                                                                      Object other$seriesStudio = other.getSeriesStudio();
                                                                                                                                                                                                                                                                                                                                                                                                      if (this$seriesStudio
                                                                                                                                                                                                                                                                                                                                                                                                            == null
                                                                                                                                                                                                                                                                                                                                                                                                         ? other$seriesStudio
                                                                                                                                                                                                                                                                                                                                                                                                            == null
                                                                                                                                                                                                                                                                                                                                                                                                         : this$seriesStudio.equals(
                                                                                                                                                                                                                                                                                                                                                                                                            other$seriesStudio
                                                                                                                                                                                                                                                                                                                                                                                                         )
                                                                                                                                                                                                                                                                                                                                                                                                         )
                                                                                                                                                                                                                                                                                                                                                                                                       {
                                                                                                                                                                                                                                                                                                                                                                                                         Object this$primaryImageItemId = this.getPrimaryImageItemId();
                                                                                                                                                                                                                                                                                                                                                                                                         Object other$primaryImageItemId = other.getPrimaryImageItemId();
                                                                                                                                                                                                                                                                                                                                                                                                         if (this$primaryImageItemId
                                                                                                                                                                                                                                                                                                                                                                                                               == null
                                                                                                                                                                                                                                                                                                                                                                                                            ? other$primaryImageItemId
                                                                                                                                                                                                                                                                                                                                                                                                               == null
                                                                                                                                                                                                                                                                                                                                                                                                            : this$primaryImageItemId.equals(
                                                                                                                                                                                                                                                                                                                                                                                                               other$primaryImageItemId
                                                                                                                                                                                                                                                                                                                                                                                                            )
                                                                                                                                                                                                                                                                                                                                                                                                            )
                                                                                                                                                                                                                                                                                                                                                                                                          {
                                                                                                                                                                                                                                                                                                                                                                                                            Object this$primaryImageTag = this.getPrimaryImageTag();
                                                                                                                                                                                                                                                                                                                                                                                                            Object other$primaryImageTag = other.getPrimaryImageTag();
                                                                                                                                                                                                                                                                                                                                                                                                            if (this$primaryImageTag
                                                                                                                                                                                                                                                                                                                                                                                                                  == null
                                                                                                                                                                                                                                                                                                                                                                                                               ? other$primaryImageTag
                                                                                                                                                                                                                                                                                                                                                                                                                  == null
                                                                                                                                                                                                                                                                                                                                                                                                               : this$primaryImageTag.equals(
                                                                                                                                                                                                                                                                                                                                                                                                                  other$primaryImageTag
                                                                                                                                                                                                                                                                                                                                                                                                               )
                                                                                                                                                                                                                                                                                                                                                                                                               )
                                                                                                                                                                                                                                                                                                                                                                                                             {
                                                                                                                                                                                                                                                                                                                                                                                                               Object this$parentThumbItemId = this.getParentThumbItemId();
                                                                                                                                                                                                                                                                                                                                                                                                               Object other$parentThumbItemId = other.getParentThumbItemId();
                                                                                                                                                                                                                                                                                                                                                                                                               if (this$parentThumbItemId
                                                                                                                                                                                                                                                                                                                                                                                                                     == null
                                                                                                                                                                                                                                                                                                                                                                                                                  ? other$parentThumbItemId
                                                                                                                                                                                                                                                                                                                                                                                                                     == null
                                                                                                                                                                                                                                                                                                                                                                                                                  : this$parentThumbItemId.equals(
                                                                                                                                                                                                                                                                                                                                                                                                                     other$parentThumbItemId
                                                                                                                                                                                                                                                                                                                                                                                                                  )
                                                                                                                                                                                                                                                                                                                                                                                                                  )
                                                                                                                                                                                                                                                                                                                                                                                                                {
                                                                                                                                                                                                                                                                                                                                                                                                                  Object this$parentThumbImageTag = this.getParentThumbImageTag();
                                                                                                                                                                                                                                                                                                                                                                                                                  Object other$parentThumbImageTag = other.getParentThumbImageTag();
                                                                                                                                                                                                                                                                                                                                                                                                                  if (this$parentThumbImageTag
                                                                                                                                                                                                                                                                                                                                                                                                                        == null
                                                                                                                                                                                                                                                                                                                                                                                                                     ? other$parentThumbImageTag
                                                                                                                                                                                                                                                                                                                                                                                                                        == null
                                                                                                                                                                                                                                                                                                                                                                                                                     : this$parentThumbImageTag.equals(
                                                                                                                                                                                                                                                                                                                                                                                                                        other$parentThumbImageTag
                                                                                                                                                                                                                                                                                                                                                                                                                     )
                                                                                                                                                                                                                                                                                                                                                                                                                     )
                                                                                                                                                                                                                                                                                                                                                                                                                   {
                                                                                                                                                                                                                                                                                                                                                                                                                     Object this$chapters = this.getChapters();
                                                                                                                                                                                                                                                                                                                                                                                                                     Object other$chapters = other.getChapters();
                                                                                                                                                                                                                                                                                                                                                                                                                     if (this$chapters
                                                                                                                                                                                                                                                                                                                                                                                                                           == null
                                                                                                                                                                                                                                                                                                                                                                                                                        ? other$chapters
                                                                                                                                                                                                                                                                                                                                                                                                                           == null
                                                                                                                                                                                                                                                                                                                                                                                                                        : this$chapters.equals(
                                                                                                                                                                                                                                                                                                                                                                                                                           other$chapters
                                                                                                                                                                                                                                                                                                                                                                                                                        )
                                                                                                                                                                                                                                                                                                                                                                                                                        )
                                                                                                                                                                                                                                                                                                                                                                                                                      {
                                                                                                                                                                                                                                                                                                                                                                                                                        Object this$locationType = this.getLocationType();
                                                                                                                                                                                                                                                                                                                                                                                                                        Object other$locationType = other.getLocationType();
                                                                                                                                                                                                                                                                                                                                                                                                                        if (this$locationType
                                                                                                                                                                                                                                                                                                                                                                                                                              == null
                                                                                                                                                                                                                                                                                                                                                                                                                           ? other$locationType
                                                                                                                                                                                                                                                                                                                                                                                                                              == null
                                                                                                                                                                                                                                                                                                                                                                                                                           : this$locationType.equals(
                                                                                                                                                                                                                                                                                                                                                                                                                              other$locationType
                                                                                                                                                                                                                                                                                                                                                                                                                           )
                                                                                                                                                                                                                                                                                                                                                                                                                           )
                                                                                                                                                                                                                                                                                                                                                                                                                         {
                                                                                                                                                                                                                                                                                                                                                                                                                           Object this$mediaType = this.getMediaType();
                                                                                                                                                                                                                                                                                                                                                                                                                           Object other$mediaType = other.getMediaType();
                                                                                                                                                                                                                                                                                                                                                                                                                           if (this$mediaType
                                                                                                                                                                                                                                                                                                                                                                                                                                 == null
                                                                                                                                                                                                                                                                                                                                                                                                                              ? other$mediaType
                                                                                                                                                                                                                                                                                                                                                                                                                                 == null
                                                                                                                                                                                                                                                                                                                                                                                                                              : this$mediaType.equals(
                                                                                                                                                                                                                                                                                                                                                                                                                                 other$mediaType
                                                                                                                                                                                                                                                                                                                                                                                                                              )
                                                                                                                                                                                                                                                                                                                                                                                                                              )
                                                                                                                                                                                                                                                                                                                                                                                                                            {
                                                                                                                                                                                                                                                                                                                                                                                                                              Object this$endDate = this.getEndDate();
                                                                                                                                                                                                                                                                                                                                                                                                                              Object other$endDate = other.getEndDate();
                                                                                                                                                                                                                                                                                                                                                                                                                              if (this$endDate
                                                                                                                                                                                                                                                                                                                                                                                                                                    == null
                                                                                                                                                                                                                                                                                                                                                                                                                                 ? other$endDate
                                                                                                                                                                                                                                                                                                                                                                                                                                    == null
                                                                                                                                                                                                                                                                                                                                                                                                                                 : this$endDate.equals(
                                                                                                                                                                                                                                                                                                                                                                                                                                    other$endDate
                                                                                                                                                                                                                                                                                                                                                                                                                                 )
                                                                                                                                                                                                                                                                                                                                                                                                                                 )
                                                                                                                                                                                                                                                                                                                                                                                                                               {
                                                                                                                                                                                                                                                                                                                                                                                                                                 Object this$lockedFields = this.getLockedFields();
                                                                                                                                                                                                                                                                                                                                                                                                                                 Object other$lockedFields = other.getLockedFields();
                                                                                                                                                                                                                                                                                                                                                                                                                                 if (this$lockedFields
                                                                                                                                                                                                                                                                                                                                                                                                                                       == null
                                                                                                                                                                                                                                                                                                                                                                                                                                    ? other$lockedFields
                                                                                                                                                                                                                                                                                                                                                                                                                                       == null
                                                                                                                                                                                                                                                                                                                                                                                                                                    : this$lockedFields.equals(
                                                                                                                                                                                                                                                                                                                                                                                                                                       other$lockedFields
                                                                                                                                                                                                                                                                                                                                                                                                                                    )
                                                                                                                                                                                                                                                                                                                                                                                                                                    )
                                                                                                                                                                                                                                                                                                                                                                                                                                  {
                                                                                                                                                                                                                                                                                                                                                                                                                                    Object this$cameraMake = this.getCameraMake();
                                                                                                                                                                                                                                                                                                                                                                                                                                    Object other$cameraMake = other.getCameraMake();
                                                                                                                                                                                                                                                                                                                                                                                                                                    if (this$cameraMake
                                                                                                                                                                                                                                                                                                                                                                                                                                          == null
                                                                                                                                                                                                                                                                                                                                                                                                                                       ? other$cameraMake
                                                                                                                                                                                                                                                                                                                                                                                                                                          == null
                                                                                                                                                                                                                                                                                                                                                                                                                                       : this$cameraMake.equals(
                                                                                                                                                                                                                                                                                                                                                                                                                                          other$cameraMake
                                                                                                                                                                                                                                                                                                                                                                                                                                       )
                                                                                                                                                                                                                                                                                                                                                                                                                                       )
                                                                                                                                                                                                                                                                                                                                                                                                                                     {
                                                                                                                                                                                                                                                                                                                                                                                                                                       Object this$cameraModel = this.getCameraModel();
                                                                                                                                                                                                                                                                                                                                                                                                                                       Object other$cameraModel = other.getCameraModel();
                                                                                                                                                                                                                                                                                                                                                                                                                                       if (this$cameraModel
                                                                                                                                                                                                                                                                                                                                                                                                                                             == null
                                                                                                                                                                                                                                                                                                                                                                                                                                          ? other$cameraModel
                                                                                                                                                                                                                                                                                                                                                                                                                                             == null
                                                                                                                                                                                                                                                                                                                                                                                                                                          : this$cameraModel.equals(
                                                                                                                                                                                                                                                                                                                                                                                                                                             other$cameraModel
                                                                                                                                                                                                                                                                                                                                                                                                                                          )
                                                                                                                                                                                                                                                                                                                                                                                                                                          )
                                                                                                                                                                                                                                                                                                                                                                                                                                        {
                                                                                                                                                                                                                                                                                                                                                                                                                                          Object this$software = this.getSoftware();
                                                                                                                                                                                                                                                                                                                                                                                                                                          Object other$software = other.getSoftware();
                                                                                                                                                                                                                                                                                                                                                                                                                                          if (this$software
                                                                                                                                                                                                                                                                                                                                                                                                                                                == null
                                                                                                                                                                                                                                                                                                                                                                                                                                             ? other$software
                                                                                                                                                                                                                                                                                                                                                                                                                                                == null
                                                                                                                                                                                                                                                                                                                                                                                                                                             : this$software.equals(
                                                                                                                                                                                                                                                                                                                                                                                                                                                other$software
                                                                                                                                                                                                                                                                                                                                                                                                                                             )
                                                                                                                                                                                                                                                                                                                                                                                                                                             )
                                                                                                                                                                                                                                                                                                                                                                                                                                           {
                                                                                                                                                                                                                                                                                                                                                                                                                                             Object this$imageOrientation = this.getImageOrientation();
                                                                                                                                                                                                                                                                                                                                                                                                                                             Object other$imageOrientation = other.getImageOrientation();
                                                                                                                                                                                                                                                                                                                                                                                                                                             if (this$imageOrientation
                                                                                                                                                                                                                                                                                                                                                                                                                                                   == null
                                                                                                                                                                                                                                                                                                                                                                                                                                                ? other$imageOrientation
                                                                                                                                                                                                                                                                                                                                                                                                                                                   == null
                                                                                                                                                                                                                                                                                                                                                                                                                                                : this$imageOrientation.equals(
                                                                                                                                                                                                                                                                                                                                                                                                                                                   other$imageOrientation
                                                                                                                                                                                                                                                                                                                                                                                                                                                )
                                                                                                                                                                                                                                                                                                                                                                                                                                                )
                                                                                                                                                                                                                                                                                                                                                                                                                                              {
                                                                                                                                                                                                                                                                                                                                                                                                                                                Object this$seriesTimerId = this.getSeriesTimerId();
                                                                                                                                                                                                                                                                                                                                                                                                                                                Object other$seriesTimerId = other.getSeriesTimerId();
                                                                                                                                                                                                                                                                                                                                                                                                                                                if (this$seriesTimerId
                                                                                                                                                                                                                                                                                                                                                                                                                                                      == null
                                                                                                                                                                                                                                                                                                                                                                                                                                                   ? other$seriesTimerId
                                                                                                                                                                                                                                                                                                                                                                                                                                                      == null
                                                                                                                                                                                                                                                                                                                                                                                                                                                   : this$seriesTimerId.equals(
                                                                                                                                                                                                                                                                                                                                                                                                                                                      other$seriesTimerId
                                                                                                                                                                                                                                                                                                                                                                                                                                                   )
                                                                                                                                                                                                                                                                                                                                                                                                                                                   )
                                                                                                                                                                                                                                                                                                                                                                                                                                                 {
                                                                                                                                                                                                                                                                                                                                                                                                                                                   Object this$channelPrimaryImageTag = this.getChannelPrimaryImageTag();
                                                                                                                                                                                                                                                                                                                                                                                                                                                   Object other$channelPrimaryImageTag = other.getChannelPrimaryImageTag();
                                                                                                                                                                                                                                                                                                                                                                                                                                                   if (this$channelPrimaryImageTag
                                                                                                                                                                                                                                                                                                                                                                                                                                                         == null
                                                                                                                                                                                                                                                                                                                                                                                                                                                      ? other$channelPrimaryImageTag
                                                                                                                                                                                                                                                                                                                                                                                                                                                         == null
                                                                                                                                                                                                                                                                                                                                                                                                                                                      : this$channelPrimaryImageTag.equals(
                                                                                                                                                                                                                                                                                                                                                                                                                                                         other$channelPrimaryImageTag
                                                                                                                                                                                                                                                                                                                                                                                                                                                      )
                                                                                                                                                                                                                                                                                                                                                                                                                                                      )
                                                                                                                                                                                                                                                                                                                                                                                                                                                    {
                                                                                                                                                                                                                                                                                                                                                                                                                                                      Object this$startDate = this.getStartDate();
                                                                                                                                                                                                                                                                                                                                                                                                                                                      Object other$startDate = other.getStartDate();
                                                                                                                                                                                                                                                                                                                                                                                                                                                      if (this$startDate
                                                                                                                                                                                                                                                                                                                                                                                                                                                            == null
                                                                                                                                                                                                                                                                                                                                                                                                                                                         ? other$startDate
                                                                                                                                                                                                                                                                                                                                                                                                                                                            == null
                                                                                                                                                                                                                                                                                                                                                                                                                                                         : this$startDate.equals(
                                                                                                                                                                                                                                                                                                                                                                                                                                                            other$startDate
                                                                                                                                                                                                                                                                                                                                                                                                                                                         )
                                                                                                                                                                                                                                                                                                                                                                                                                                                         )
                                                                                                                                                                                                                                                                                                                                                                                                                                                       {
                                                                                                                                                                                                                                                                                                                                                                                                                                                         Object this$episodeTitle = this.getEpisodeTitle();
                                                                                                                                                                                                                                                                                                                                                                                                                                                         Object other$episodeTitle = other.getEpisodeTitle();
                                                                                                                                                                                                                                                                                                                                                                                                                                                         if (this$episodeTitle
                                                                                                                                                                                                                                                                                                                                                                                                                                                               == null
                                                                                                                                                                                                                                                                                                                                                                                                                                                            ? other$episodeTitle
                                                                                                                                                                                                                                                                                                                                                                                                                                                               == null
                                                                                                                                                                                                                                                                                                                                                                                                                                                            : this$episodeTitle.equals(
                                                                                                                                                                                                                                                                                                                                                                                                                                                               other$episodeTitle
                                                                                                                                                                                                                                                                                                                                                                                                                                                            )
                                                                                                                                                                                                                                                                                                                                                                                                                                                            )
                                                                                                                                                                                                                                                                                                                                                                                                                                                          {
                                                                                                                                                                                                                                                                                                                                                                                                                                                            Object this$timerType = this.getTimerType();
                                                                                                                                                                                                                                                                                                                                                                                                                                                            Object other$timerType = other.getTimerType();
                                                                                                                                                                                                                                                                                                                                                                                                                                                            if (this$timerType
                                                                                                                                                                                                                                                                                                                                                                                                                                                                  == null
                                                                                                                                                                                                                                                                                                                                                                                                                                                               ? other$timerType
                                                                                                                                                                                                                                                                                                                                                                                                                                                                  == null
                                                                                                                                                                                                                                                                                                                                                                                                                                                               : this$timerType.equals(
                                                                                                                                                                                                                                                                                                                                                                                                                                                                  other$timerType
                                                                                                                                                                                                                                                                                                                                                                                                                                                               )
                                                                                                                                                                                                                                                                                                                                                                                                                                                               )
                                                                                                                                                                                                                                                                                                                                                                                                                                                             {
                                                                                                                                                                                                                                                                                                                                                                                                                                                               Object this$managementId = this.getManagementId();
                                                                                                                                                                                                                                                                                                                                                                                                                                                               Object other$managementId = other.getManagementId();
                                                                                                                                                                                                                                                                                                                                                                                                                                                               if (this$managementId
                                                                                                                                                                                                                                                                                                                                                                                                                                                                     == null
                                                                                                                                                                                                                                                                                                                                                                                                                                                                  ? other$managementId
                                                                                                                                                                                                                                                                                                                                                                                                                                                                     == null
                                                                                                                                                                                                                                                                                                                                                                                                                                                                  : this$managementId.equals(
                                                                                                                                                                                                                                                                                                                                                                                                                                                                     other$managementId
                                                                                                                                                                                                                                                                                                                                                                                                                                                                  )
                                                                                                                                                                                                                                                                                                                                                                                                                                                                  )
                                                                                                                                                                                                                                                                                                                                                                                                                                                                {
                                                                                                                                                                                                                                                                                                                                                                                                                                                                  Object this$timerId = this.getTimerId();
                                                                                                                                                                                                                                                                                                                                                                                                                                                                  Object other$timerId = other.getTimerId();
                                                                                                                                                                                                                                                                                                                                                                                                                                                                  if (this$timerId
                                                                                                                                                                                                                                                                                                                                                                                                                                                                        == null
                                                                                                                                                                                                                                                                                                                                                                                                                                                                     ? other$timerId
                                                                                                                                                                                                                                                                                                                                                                                                                                                                        == null
                                                                                                                                                                                                                                                                                                                                                                                                                                                                     : this$timerId.equals(
                                                                                                                                                                                                                                                                                                                                                                                                                                                                        other$timerId
                                                                                                                                                                                                                                                                                                                                                                                                                                                                     )
                                                                                                                                                                                                                                                                                                                                                                                                                                                                     )
                                                                                                                                                                                                                                                                                                                                                                                                                                                                   {
                                                                                                                                                                                                                                                                                                                                                                                                                                                                     Object this$currentProgram = this.getCurrentProgram();
                                                                                                                                                                                                                                                                                                                                                                                                                                                                     Object other$currentProgram = other.getCurrentProgram();
                                                                                                                                                                                                                                                                                                                                                                                                                                                                     if (this$currentProgram
                                                                                                                                                                                                                                                                                                                                                                                                                                                                           == null
                                                                                                                                                                                                                                                                                                                                                                                                                                                                        ? other$currentProgram
                                                                                                                                                                                                                                                                                                                                                                                                                                                                           == null
                                                                                                                                                                                                                                                                                                                                                                                                                                                                        : this$currentProgram.equals(
                                                                                                                                                                                                                                                                                                                                                                                                                                                                           other$currentProgram
                                                                                                                                                                                                                                                                                                                                                                                                                                                                        )
                                                                                                                                                                                                                                                                                                                                                                                                                                                                        )
                                                                                                                                                                                                                                                                                                                                                                                                                                                                      {
                                                                                                                                                                                                                                                                                                                                                                                                                                                                        Object this$subviews = this.getSubviews();
                                                                                                                                                                                                                                                                                                                                                                                                                                                                        Object other$subviews = other.getSubviews();
                                                                                                                                                                                                                                                                                                                                                                                                                                                                        if (this$subviews
                                                                                                                                                                                                                                                                                                                                                                                                                                                                              == null
                                                                                                                                                                                                                                                                                                                                                                                                                                                                           ? other$subviews
                                                                                                                                                                                                                                                                                                                                                                                                                                                                              == null
                                                                                                                                                                                                                                                                                                                                                                                                                                                                           : this$subviews.equals(
                                                                                                                                                                                                                                                                                                                                                                                                                                                                              other$subviews
                                                                                                                                                                                                                                                                                                                                                                                                                                                                           )
                                                                                                                                                                                                                                                                                                                                                                                                                                                                           )
                                                                                                                                                                                                                                                                                                                                                                                                                                                                         {
                                                                                                                                                                                                                                                                                                                                                                                                                                                                           Object this$listingsProviderId = this.getListingsProviderId();
                                                                                                                                                                                                                                                                                                                                                                                                                                                                           Object other$listingsProviderId = other.getListingsProviderId();
                                                                                                                                                                                                                                                                                                                                                                                                                                                                           if (this$listingsProviderId
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                 == null
                                                                                                                                                                                                                                                                                                                                                                                                                                                                              ? other$listingsProviderId
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                 == null
                                                                                                                                                                                                                                                                                                                                                                                                                                                                              : this$listingsProviderId.equals(
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                 other$listingsProviderId
                                                                                                                                                                                                                                                                                                                                                                                                                                                                              )
                                                                                                                                                                                                                                                                                                                                                                                                                                                                              )
                                                                                                                                                                                                                                                                                                                                                                                                                                                                            {
                                                                                                                                                                                                                                                                                                                                                                                                                                                                              Object this$listingsChannelId = this.getListingsChannelId();
                                                                                                                                                                                                                                                                                                                                                                                                                                                                              Object other$listingsChannelId = other.getListingsChannelId();
                                                                                                                                                                                                                                                                                                                                                                                                                                                                              if (this$listingsChannelId
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                    == null
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                 ? other$listingsChannelId
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                    == null
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                 : this$listingsChannelId.equals(
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                    other$listingsChannelId
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                 )
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                 )
                                                                                                                                                                                                                                                                                                                                                                                                                                                                               {
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                 Object this$listingsPath = this.getListingsPath();
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                 Object other$listingsPath = other.getListingsPath();
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                 if (this$listingsPath
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                       == null
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                    ? other$listingsPath
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                       == null
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                    : this$listingsPath.equals(
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                       other$listingsPath
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                    )
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                    )
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                  {
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                    Object this$listingsId = this.getListingsId();
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                    Object other$listingsId = other.getListingsId();
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                    if (this$listingsId
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                          == null
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                       ? other$listingsId
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                          == null
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                       : this$listingsId.equals(
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                          other$listingsId
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                       )
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                       )
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                     {
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                       Object this$listingsChannelName = this.getListingsChannelName();
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                       Object other$listingsChannelName = other.getListingsChannelName();
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                       if (this$listingsChannelName
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                             == null
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                          ? other$listingsChannelName
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                             == null
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                          : this$listingsChannelName.equals(
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                             other$listingsChannelName
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                          )
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                          )
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                        {
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                          Object this$listingsChannelNumber = this.getListingsChannelNumber();
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                          Object other$listingsChannelNumber = other.getListingsChannelNumber();
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                          if (this$listingsChannelNumber
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                == null
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                             ? other$listingsChannelNumber
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                == null
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                             : this$listingsChannelNumber.equals(
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                other$listingsChannelNumber
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                             )
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                             )
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                           {
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                             Object this$affiliateCallSign = this.getAffiliateCallSign();
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                             Object other$affiliateCallSign = other.getAffiliateCallSign();
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                             if (this$affiliateCallSign
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                   == null
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                ? other$affiliateCallSign
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                   == null
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                : this$affiliateCallSign.equals(
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                   other$affiliateCallSign
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                )
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                )
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                              {
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                Object this$imageUrl = this.getImageUrl();
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                Object other$imageUrl = other.getImageUrl();
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                if (this$imageUrl
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                      == null
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                   ? other$imageUrl
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                      == null
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                   : this$imageUrl.equals(
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                      other$imageUrl
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                   )
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                   )
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                 {
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                   Object this$filmTitle = this.getFilmTitle();
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                   Object other$filmTitle = other.getFilmTitle();
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                   if (this$filmTitle
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                         == null
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                      ? other$filmTitle
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                         == null
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                      : this$filmTitle.equals(
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                         other$filmTitle
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                      )
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                      )
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                    {
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                      Object this$primaryImageAspectRatioCount = this.getPrimaryImageAspectRatioCount();
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                      Object other$primaryImageAspectRatioCount = other.getPrimaryImageAspectRatioCount();
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                      if (this$primaryImageAspectRatioCount
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                            == null
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                         ? other$primaryImageAspectRatioCount
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                            == null
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                         : this$primaryImageAspectRatioCount.equals(
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                            other$primaryImageAspectRatioCount
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                         )
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                         )
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                       {
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                         Object this$embyItemUrl = this.getEmbyItemUrl();
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                         Object other$embyItemUrl = other.getEmbyItemUrl();
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                         if (this$embyItemUrl
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                               == null
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                            ? other$embyItemUrl
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                               == null
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                            : this$embyItemUrl.equals(
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                               other$embyItemUrl
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                            )
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                            )
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                          {
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                            Object this$backdropImageUrl = this.getBackdropImageUrl();
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                            Object other$backdropImageUrl = other.getBackdropImageUrl();
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                            return this$backdropImageUrl
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                  == null
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                               ? other$backdropImageUrl
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                  == null
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                               : this$backdropImageUrl.equals(
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                  other$backdropImageUrl
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                               );
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
         return other instanceof QueryResultBaseItemResponse.ItemsDTO;
      }

      @Generated
      @Override
      public int hashCode() {
         int PRIME = 59;
         int result = 1;
         Object $sortIndexNumber = this.getSortIndexNumber();
         result = result * 59 + ($sortIndexNumber == null ? 43 : $sortIndexNumber.hashCode());
         Object $sortParentIndexNumber = this.getSortParentIndexNumber();
         result = result * 59 + ($sortParentIndexNumber == null ? 43 : $sortParentIndexNumber.hashCode());
         Object $canDelete = this.getCanDelete();
         result = result * 59 + ($canDelete == null ? 43 : $canDelete.hashCode());
         Object $canDownload = this.getCanDownload();
         result = result * 59 + ($canDownload == null ? 43 : $canDownload.hashCode());
         Object $canEditItems = this.getCanEditItems();
         result = result * 59 + ($canEditItems == null ? 43 : $canEditItems.hashCode());
         Object $supportsResume = this.getSupportsResume();
         result = result * 59 + ($supportsResume == null ? 43 : $supportsResume.hashCode());
         Object $supportsSync = this.getSupportsSync();
         result = result * 59 + ($supportsSync == null ? 43 : $supportsSync.hashCode());
         Object $canManageAccess = this.getCanManageAccess();
         result = result * 59 + ($canManageAccess == null ? 43 : $canManageAccess.hashCode());
         Object $canLeaveContent = this.getCanLeaveContent();
         result = result * 59 + ($canLeaveContent == null ? 43 : $canLeaveContent.hashCode());
         Object $canMakePublic = this.getCanMakePublic();
         result = result * 59 + ($canMakePublic == null ? 43 : $canMakePublic.hashCode());
         Object $criticRating = this.getCriticRating();
         result = result * 59 + ($criticRating == null ? 43 : $criticRating.hashCode());
         Object $gameSystemId = this.getGameSystemId();
         result = result * 59 + ($gameSystemId == null ? 43 : $gameSystemId.hashCode());
         Object $asSeries = this.getAsSeries();
         result = result * 59 + ($asSeries == null ? 43 : $asSeries.hashCode());
         Object $communityRating = this.getCommunityRating();
         result = result * 59 + ($communityRating == null ? 43 : $communityRating.hashCode());
         Object $runTimeTicks = this.getRunTimeTicks();
         result = result * 59 + ($runTimeTicks == null ? 43 : $runTimeTicks.hashCode());
         Object $size = this.getSize();
         result = result * 59 + ($size == null ? 43 : $size.hashCode());
         Object $bitrate = this.getBitrate();
         result = result * 59 + ($bitrate == null ? 43 : $bitrate.hashCode());
         Object $productionYear = this.getProductionYear();
         result = result * 59 + ($productionYear == null ? 43 : $productionYear.hashCode());
         Object $indexNumber = this.getIndexNumber();
         result = result * 59 + ($indexNumber == null ? 43 : $indexNumber.hashCode());
         Object $indexNumberEnd = this.getIndexNumberEnd();
         result = result * 59 + ($indexNumberEnd == null ? 43 : $indexNumberEnd.hashCode());
         Object $parentIndexNumber = this.getParentIndexNumber();
         result = result * 59 + ($parentIndexNumber == null ? 43 : $parentIndexNumber.hashCode());
         Object $isFolder = this.getIsFolder();
         result = result * 59 + ($isFolder == null ? 43 : $isFolder.hashCode());
         Object $localTrailerCount = this.getLocalTrailerCount();
         result = result * 59 + ($localTrailerCount == null ? 43 : $localTrailerCount.hashCode());
         Object $recursiveItemCount = this.getRecursiveItemCount();
         result = result * 59 + ($recursiveItemCount == null ? 43 : $recursiveItemCount.hashCode());
         Object $childCount = this.getChildCount();
         result = result * 59 + ($childCount == null ? 43 : $childCount.hashCode());
         Object $seasonCount = this.getSeasonCount();
         result = result * 59 + ($seasonCount == null ? 43 : $seasonCount.hashCode());
         Object $specialFeatureCount = this.getSpecialFeatureCount();
         result = result * 59 + ($specialFeatureCount == null ? 43 : $specialFeatureCount.hashCode());
         Object $primaryImageAspectRatio = this.getPrimaryImageAspectRatio();
         result = result * 59 + ($primaryImageAspectRatio == null ? 43 : $primaryImageAspectRatio.hashCode());
         Object $partCount = this.getPartCount();
         result = result * 59 + ($partCount == null ? 43 : $partCount.hashCode());
         Object $lockData = this.getLockData();
         result = result * 59 + ($lockData == null ? 43 : $lockData.hashCode());
         Object $width = this.getWidth();
         result = result * 59 + ($width == null ? 43 : $width.hashCode());
         Object $height = this.getHeight();
         result = result * 59 + ($height == null ? 43 : $height.hashCode());
         Object $exposureTime = this.getExposureTime();
         result = result * 59 + ($exposureTime == null ? 43 : $exposureTime.hashCode());
         Object $focalLength = this.getFocalLength();
         result = result * 59 + ($focalLength == null ? 43 : $focalLength.hashCode());
         Object $aperture = this.getAperture();
         result = result * 59 + ($aperture == null ? 43 : $aperture.hashCode());
         Object $shutterSpeed = this.getShutterSpeed();
         result = result * 59 + ($shutterSpeed == null ? 43 : $shutterSpeed.hashCode());
         Object $latitude = this.getLatitude();
         result = result * 59 + ($latitude == null ? 43 : $latitude.hashCode());
         Object $longitude = this.getLongitude();
         result = result * 59 + ($longitude == null ? 43 : $longitude.hashCode());
         Object $altitude = this.getAltitude();
         result = result * 59 + ($altitude == null ? 43 : $altitude.hashCode());
         Object $isoSpeedRating = this.getIsoSpeedRating();
         result = result * 59 + ($isoSpeedRating == null ? 43 : $isoSpeedRating.hashCode());
         Object $completionPercentage = this.getCompletionPercentage();
         result = result * 59 + ($completionPercentage == null ? 43 : $completionPercentage.hashCode());
         Object $isRepeat = this.getIsRepeat();
         result = result * 59 + ($isRepeat == null ? 43 : $isRepeat.hashCode());
         Object $isNew = this.getIsNew();
         result = result * 59 + ($isNew == null ? 43 : $isNew.hashCode());
         Object $isMovie = this.getIsMovie();
         result = result * 59 + ($isMovie == null ? 43 : $isMovie.hashCode());
         Object $isSports = this.getIsSports();
         result = result * 59 + ($isSports == null ? 43 : $isSports.hashCode());
         Object $isSeries = this.getIsSeries();
         result = result * 59 + ($isSeries == null ? 43 : $isSeries.hashCode());
         Object $isLive = this.getIsLive();
         result = result * 59 + ($isLive == null ? 43 : $isLive.hashCode());
         Object $isNews = this.getIsNews();
         result = result * 59 + ($isNews == null ? 43 : $isNews.hashCode());
         Object $isKids = this.getIsKids();
         result = result * 59 + ($isKids == null ? 43 : $isKids.hashCode());
         Object $isPremiere = this.getIsPremiere();
         result = result * 59 + ($isPremiere == null ? 43 : $isPremiere.hashCode());
         Object $disabled = this.getDisabled();
         result = result * 59 + ($disabled == null ? 43 : $disabled.hashCode());
         Object $movieCount = this.getMovieCount();
         result = result * 59 + ($movieCount == null ? 43 : $movieCount.hashCode());
         Object $seriesCount = this.getSeriesCount();
         result = result * 59 + ($seriesCount == null ? 43 : $seriesCount.hashCode());
         Object $albumCount = this.getAlbumCount();
         result = result * 59 + ($albumCount == null ? 43 : $albumCount.hashCode());
         Object $songCount = this.getSongCount();
         result = result * 59 + ($songCount == null ? 43 : $songCount.hashCode());
         Object $musicVideoCount = this.getMusicVideoCount();
         result = result * 59 + ($musicVideoCount == null ? 43 : $musicVideoCount.hashCode());
         Object $name = this.getName();
         result = result * 59 + ($name == null ? 43 : $name.hashCode());
         Object $originalTitle = this.getOriginalTitle();
         result = result * 59 + ($originalTitle == null ? 43 : $originalTitle.hashCode());
         Object $serverId = this.getServerId();
         result = result * 59 + ($serverId == null ? 43 : $serverId.hashCode());
         Object $id = this.getId();
         result = result * 59 + ($id == null ? 43 : $id.hashCode());
         Object $guid = this.getGuid();
         result = result * 59 + ($guid == null ? 43 : $guid.hashCode());
         Object $etag = this.getEtag();
         result = result * 59 + ($etag == null ? 43 : $etag.hashCode());
         Object $prefix = this.getPrefix();
         result = result * 59 + ($prefix == null ? 43 : $prefix.hashCode());
         Object $tunerName = this.getTunerName();
         result = result * 59 + ($tunerName == null ? 43 : $tunerName.hashCode());
         Object $playlistItemId = this.getPlaylistItemId();
         result = result * 59 + ($playlistItemId == null ? 43 : $playlistItemId.hashCode());
         Object $dateCreated = this.getDateCreated();
         result = result * 59 + ($dateCreated == null ? 43 : $dateCreated.hashCode());
         Object $extraType = this.getExtraType();
         result = result * 59 + ($extraType == null ? 43 : $extraType.hashCode());
         Object $presentationUniqueKey = this.getPresentationUniqueKey();
         result = result * 59 + ($presentationUniqueKey == null ? 43 : $presentationUniqueKey.hashCode());
         Object $preferredMetadataLanguage = this.getPreferredMetadataLanguage();
         result = result * 59 + ($preferredMetadataLanguage == null ? 43 : $preferredMetadataLanguage.hashCode());
         Object $preferredMetadataCountryCode = this.getPreferredMetadataCountryCode();
         result = result * 59 + ($preferredMetadataCountryCode == null ? 43 : $preferredMetadataCountryCode.hashCode());
         Object $syncStatus = this.getSyncStatus();
         result = result * 59 + ($syncStatus == null ? 43 : $syncStatus.hashCode());
         Object $container = this.getContainer();
         result = result * 59 + ($container == null ? 43 : $container.hashCode());
         Object $sortName = this.getSortName();
         result = result * 59 + ($sortName == null ? 43 : $sortName.hashCode());
         Object $forcedSortName = this.getForcedSortName();
         result = result * 59 + ($forcedSortName == null ? 43 : $forcedSortName.hashCode());
         Object $video3DFormat = this.getVideo3DFormat();
         result = result * 59 + ($video3DFormat == null ? 43 : $video3DFormat.hashCode());
         Object $premiereDate = this.getPremiereDate();
         result = result * 59 + ($premiereDate == null ? 43 : $premiereDate.hashCode());
         Object $externalUrls = this.getExternalUrls();
         result = result * 59 + ($externalUrls == null ? 43 : $externalUrls.hashCode());
         Object $mediaSources = this.getMediaSources();
         result = result * 59 + ($mediaSources == null ? 43 : $mediaSources.hashCode());
         Object $gameSystem = this.getGameSystem();
         result = result * 59 + ($gameSystem == null ? 43 : $gameSystem.hashCode());
         Object $productionLocations = this.getProductionLocations();
         result = result * 59 + ($productionLocations == null ? 43 : $productionLocations.hashCode());
         Object $path = this.getPath();
         result = result * 59 + ($path == null ? 43 : $path.hashCode());
         Object $officialRating = this.getOfficialRating();
         result = result * 59 + ($officialRating == null ? 43 : $officialRating.hashCode());
         Object $customRating = this.getCustomRating();
         result = result * 59 + ($customRating == null ? 43 : $customRating.hashCode());
         Object $channelId = this.getChannelId();
         result = result * 59 + ($channelId == null ? 43 : $channelId.hashCode());
         Object $channelName = this.getChannelName();
         result = result * 59 + ($channelName == null ? 43 : $channelName.hashCode());
         Object $overview = this.getOverview();
         result = result * 59 + ($overview == null ? 43 : $overview.hashCode());
         Object $taglines = this.getTaglines();
         result = result * 59 + ($taglines == null ? 43 : $taglines.hashCode());
         Object $genres = this.getGenres();
         result = result * 59 + ($genres == null ? 43 : $genres.hashCode());
         Object $fileName = this.getFileName();
         result = result * 59 + ($fileName == null ? 43 : $fileName.hashCode());
         Object $number = this.getNumber();
         result = result * 59 + ($number == null ? 43 : $number.hashCode());
         Object $channelNumber = this.getChannelNumber();
         result = result * 59 + ($channelNumber == null ? 43 : $channelNumber.hashCode());
         Object $remoteTrailers = this.getRemoteTrailers();
         result = result * 59 + ($remoteTrailers == null ? 43 : $remoteTrailers.hashCode());
         Object $providerIds = this.getProviderIds();
         result = result * 59 + ($providerIds == null ? 43 : $providerIds.hashCode());
         Object $parentId = this.getParentId();
         result = result * 59 + ($parentId == null ? 43 : $parentId.hashCode());
         Object $type = this.getType();
         result = result * 59 + ($type == null ? 43 : $type.hashCode());
         Object $people = this.getPeople();
         result = result * 59 + ($people == null ? 43 : $people.hashCode());
         Object $studios = this.getStudios();
         result = result * 59 + ($studios == null ? 43 : $studios.hashCode());
         Object $genreItems = this.getGenreItems();
         result = result * 59 + ($genreItems == null ? 43 : $genreItems.hashCode());
         Object $tagItems = this.getTagItems();
         result = result * 59 + ($tagItems == null ? 43 : $tagItems.hashCode());
         Object $parentLogoItemId = this.getParentLogoItemId();
         result = result * 59 + ($parentLogoItemId == null ? 43 : $parentLogoItemId.hashCode());
         Object $parentBackdropItemId = this.getParentBackdropItemId();
         result = result * 59 + ($parentBackdropItemId == null ? 43 : $parentBackdropItemId.hashCode());
         Object $parentBackdropImageTags = this.getParentBackdropImageTags();
         result = result * 59 + ($parentBackdropImageTags == null ? 43 : $parentBackdropImageTags.hashCode());
         Object $userData = this.getUserData();
         result = result * 59 + ($userData == null ? 43 : $userData.hashCode());
         Object $seriesName = this.getSeriesName();
         result = result * 59 + ($seriesName == null ? 43 : $seriesName.hashCode());
         Object $seriesId = this.getSeriesId();
         result = result * 59 + ($seriesId == null ? 43 : $seriesId.hashCode());
         Object $seasonId = this.getSeasonId();
         result = result * 59 + ($seasonId == null ? 43 : $seasonId.hashCode());
         Object $displayPreferencesId = this.getDisplayPreferencesId();
         result = result * 59 + ($displayPreferencesId == null ? 43 : $displayPreferencesId.hashCode());
         Object $status = this.getStatus();
         result = result * 59 + ($status == null ? 43 : $status.hashCode());
         Object $airDays = this.getAirDays();
         result = result * 59 + ($airDays == null ? 43 : $airDays.hashCode());
         Object $tags = this.getTags();
         result = result * 59 + ($tags == null ? 43 : $tags.hashCode());
         Object $artists = this.getArtists();
         result = result * 59 + ($artists == null ? 43 : $artists.hashCode());
         Object $artistItems = this.getArtistItems();
         result = result * 59 + ($artistItems == null ? 43 : $artistItems.hashCode());
         Object $composers = this.getComposers();
         result = result * 59 + ($composers == null ? 43 : $composers.hashCode());
         Object $album = this.getAlbum();
         result = result * 59 + ($album == null ? 43 : $album.hashCode());
         Object $collectionType = this.getCollectionType();
         result = result * 59 + ($collectionType == null ? 43 : $collectionType.hashCode());
         Object $displayOrder = this.getDisplayOrder();
         result = result * 59 + ($displayOrder == null ? 43 : $displayOrder.hashCode());
         Object $albumId = this.getAlbumId();
         result = result * 59 + ($albumId == null ? 43 : $albumId.hashCode());
         Object $albumPrimaryImageTag = this.getAlbumPrimaryImageTag();
         result = result * 59 + ($albumPrimaryImageTag == null ? 43 : $albumPrimaryImageTag.hashCode());
         Object $seriesPrimaryImageTag = this.getSeriesPrimaryImageTag();
         result = result * 59 + ($seriesPrimaryImageTag == null ? 43 : $seriesPrimaryImageTag.hashCode());
         Object $albumArtist = this.getAlbumArtist();
         result = result * 59 + ($albumArtist == null ? 43 : $albumArtist.hashCode());
         Object $albumArtists = this.getAlbumArtists();
         result = result * 59 + ($albumArtists == null ? 43 : $albumArtists.hashCode());
         Object $seasonName = this.getSeasonName();
         result = result * 59 + ($seasonName == null ? 43 : $seasonName.hashCode());
         Object $mediaStreams = this.getMediaStreams();
         result = result * 59 + ($mediaStreams == null ? 43 : $mediaStreams.hashCode());
         Object $imageTags = this.getImageTags();
         result = result * 59 + ($imageTags == null ? 43 : $imageTags.hashCode());
         Object $backdropImageTags = this.getBackdropImageTags();
         result = result * 59 + ($backdropImageTags == null ? 43 : $backdropImageTags.hashCode());
         Object $parentLogoImageTag = this.getParentLogoImageTag();
         result = result * 59 + ($parentLogoImageTag == null ? 43 : $parentLogoImageTag.hashCode());
         Object $seriesStudio = this.getSeriesStudio();
         result = result * 59 + ($seriesStudio == null ? 43 : $seriesStudio.hashCode());
         Object $primaryImageItemId = this.getPrimaryImageItemId();
         result = result * 59 + ($primaryImageItemId == null ? 43 : $primaryImageItemId.hashCode());
         Object $primaryImageTag = this.getPrimaryImageTag();
         result = result * 59 + ($primaryImageTag == null ? 43 : $primaryImageTag.hashCode());
         Object $parentThumbItemId = this.getParentThumbItemId();
         result = result * 59 + ($parentThumbItemId == null ? 43 : $parentThumbItemId.hashCode());
         Object $parentThumbImageTag = this.getParentThumbImageTag();
         result = result * 59 + ($parentThumbImageTag == null ? 43 : $parentThumbImageTag.hashCode());
         Object $chapters = this.getChapters();
         result = result * 59 + ($chapters == null ? 43 : $chapters.hashCode());
         Object $locationType = this.getLocationType();
         result = result * 59 + ($locationType == null ? 43 : $locationType.hashCode());
         Object $mediaType = this.getMediaType();
         result = result * 59 + ($mediaType == null ? 43 : $mediaType.hashCode());
         Object $endDate = this.getEndDate();
         result = result * 59 + ($endDate == null ? 43 : $endDate.hashCode());
         Object $lockedFields = this.getLockedFields();
         result = result * 59 + ($lockedFields == null ? 43 : $lockedFields.hashCode());
         Object $cameraMake = this.getCameraMake();
         result = result * 59 + ($cameraMake == null ? 43 : $cameraMake.hashCode());
         Object $cameraModel = this.getCameraModel();
         result = result * 59 + ($cameraModel == null ? 43 : $cameraModel.hashCode());
         Object $software = this.getSoftware();
         result = result * 59 + ($software == null ? 43 : $software.hashCode());
         Object $imageOrientation = this.getImageOrientation();
         result = result * 59 + ($imageOrientation == null ? 43 : $imageOrientation.hashCode());
         Object $seriesTimerId = this.getSeriesTimerId();
         result = result * 59 + ($seriesTimerId == null ? 43 : $seriesTimerId.hashCode());
         Object $channelPrimaryImageTag = this.getChannelPrimaryImageTag();
         result = result * 59 + ($channelPrimaryImageTag == null ? 43 : $channelPrimaryImageTag.hashCode());
         Object $startDate = this.getStartDate();
         result = result * 59 + ($startDate == null ? 43 : $startDate.hashCode());
         Object $episodeTitle = this.getEpisodeTitle();
         result = result * 59 + ($episodeTitle == null ? 43 : $episodeTitle.hashCode());
         Object $timerType = this.getTimerType();
         result = result * 59 + ($timerType == null ? 43 : $timerType.hashCode());
         Object $managementId = this.getManagementId();
         result = result * 59 + ($managementId == null ? 43 : $managementId.hashCode());
         Object $timerId = this.getTimerId();
         result = result * 59 + ($timerId == null ? 43 : $timerId.hashCode());
         Object $currentProgram = this.getCurrentProgram();
         result = result * 59 + ($currentProgram == null ? 43 : $currentProgram.hashCode());
         Object $subviews = this.getSubviews();
         result = result * 59 + ($subviews == null ? 43 : $subviews.hashCode());
         Object $listingsProviderId = this.getListingsProviderId();
         result = result * 59 + ($listingsProviderId == null ? 43 : $listingsProviderId.hashCode());
         Object $listingsChannelId = this.getListingsChannelId();
         result = result * 59 + ($listingsChannelId == null ? 43 : $listingsChannelId.hashCode());
         Object $listingsPath = this.getListingsPath();
         result = result * 59 + ($listingsPath == null ? 43 : $listingsPath.hashCode());
         Object $listingsId = this.getListingsId();
         result = result * 59 + ($listingsId == null ? 43 : $listingsId.hashCode());
         Object $listingsChannelName = this.getListingsChannelName();
         result = result * 59 + ($listingsChannelName == null ? 43 : $listingsChannelName.hashCode());
         Object $listingsChannelNumber = this.getListingsChannelNumber();
         result = result * 59 + ($listingsChannelNumber == null ? 43 : $listingsChannelNumber.hashCode());
         Object $affiliateCallSign = this.getAffiliateCallSign();
         result = result * 59 + ($affiliateCallSign == null ? 43 : $affiliateCallSign.hashCode());
         Object $imageUrl = this.getImageUrl();
         result = result * 59 + ($imageUrl == null ? 43 : $imageUrl.hashCode());
         Object $filmTitle = this.getFilmTitle();
         result = result * 59 + ($filmTitle == null ? 43 : $filmTitle.hashCode());
         Object $primaryImageAspectRatioCount = this.getPrimaryImageAspectRatioCount();
         result = result * 59 + ($primaryImageAspectRatioCount == null ? 43 : $primaryImageAspectRatioCount.hashCode());
         Object $embyItemUrl = this.getEmbyItemUrl();
         result = result * 59 + ($embyItemUrl == null ? 43 : $embyItemUrl.hashCode());
         Object $backdropImageUrl = this.getBackdropImageUrl();
         return result * 59 + ($backdropImageUrl == null ? 43 : $backdropImageUrl.hashCode());
      }

      @Generated
      @Override
      public String toString() {
         return "QueryResultBaseItemResponse.ItemsDTO(name="
            + this.getName()
            + ", originalTitle="
            + this.getOriginalTitle()
            + ", serverId="
            + this.getServerId()
            + ", id="
            + this.getId()
            + ", guid="
            + this.getGuid()
            + ", etag="
            + this.getEtag()
            + ", prefix="
            + this.getPrefix()
            + ", tunerName="
            + this.getTunerName()
            + ", playlistItemId="
            + this.getPlaylistItemId()
            + ", dateCreated="
            + this.getDateCreated()
            + ", extraType="
            + this.getExtraType()
            + ", sortIndexNumber="
            + this.getSortIndexNumber()
            + ", sortParentIndexNumber="
            + this.getSortParentIndexNumber()
            + ", canDelete="
            + this.getCanDelete()
            + ", canDownload="
            + this.getCanDownload()
            + ", canEditItems="
            + this.getCanEditItems()
            + ", supportsResume="
            + this.getSupportsResume()
            + ", presentationUniqueKey="
            + this.getPresentationUniqueKey()
            + ", preferredMetadataLanguage="
            + this.getPreferredMetadataLanguage()
            + ", preferredMetadataCountryCode="
            + this.getPreferredMetadataCountryCode()
            + ", supportsSync="
            + this.getSupportsSync()
            + ", syncStatus="
            + this.getSyncStatus()
            + ", canManageAccess="
            + this.getCanManageAccess()
            + ", canLeaveContent="
            + this.getCanLeaveContent()
            + ", canMakePublic="
            + this.getCanMakePublic()
            + ", container="
            + this.getContainer()
            + ", sortName="
            + this.getSortName()
            + ", forcedSortName="
            + this.getForcedSortName()
            + ", video3DFormat="
            + this.getVideo3DFormat()
            + ", premiereDate="
            + this.getPremiereDate()
            + ", externalUrls="
            + this.getExternalUrls()
            + ", mediaSources="
            + this.getMediaSources()
            + ", criticRating="
            + this.getCriticRating()
            + ", gameSystemId="
            + this.getGameSystemId()
            + ", asSeries="
            + this.getAsSeries()
            + ", gameSystem="
            + this.getGameSystem()
            + ", productionLocations="
            + this.getProductionLocations()
            + ", path="
            + this.getPath()
            + ", officialRating="
            + this.getOfficialRating()
            + ", customRating="
            + this.getCustomRating()
            + ", channelId="
            + this.getChannelId()
            + ", channelName="
            + this.getChannelName()
            + ", overview="
            + this.getOverview()
            + ", taglines="
            + this.getTaglines()
            + ", genres="
            + this.getGenres()
            + ", communityRating="
            + this.getCommunityRating()
            + ", runTimeTicks="
            + this.getRunTimeTicks()
            + ", size="
            + this.getSize()
            + ", fileName="
            + this.getFileName()
            + ", bitrate="
            + this.getBitrate()
            + ", productionYear="
            + this.getProductionYear()
            + ", number="
            + this.getNumber()
            + ", channelNumber="
            + this.getChannelNumber()
            + ", indexNumber="
            + this.getIndexNumber()
            + ", indexNumberEnd="
            + this.getIndexNumberEnd()
            + ", parentIndexNumber="
            + this.getParentIndexNumber()
            + ", remoteTrailers="
            + this.getRemoteTrailers()
            + ", providerIds="
            + this.getProviderIds()
            + ", isFolder="
            + this.getIsFolder()
            + ", parentId="
            + this.getParentId()
            + ", type="
            + this.getType()
            + ", people="
            + this.getPeople()
            + ", studios="
            + this.getStudios()
            + ", genreItems="
            + this.getGenreItems()
            + ", tagItems="
            + this.getTagItems()
            + ", parentLogoItemId="
            + this.getParentLogoItemId()
            + ", parentBackdropItemId="
            + this.getParentBackdropItemId()
            + ", parentBackdropImageTags="
            + this.getParentBackdropImageTags()
            + ", localTrailerCount="
            + this.getLocalTrailerCount()
            + ", userData="
            + this.getUserData()
            + ", recursiveItemCount="
            + this.getRecursiveItemCount()
            + ", childCount="
            + this.getChildCount()
            + ", seasonCount="
            + this.getSeasonCount()
            + ", seriesName="
            + this.getSeriesName()
            + ", seriesId="
            + this.getSeriesId()
            + ", seasonId="
            + this.getSeasonId()
            + ", specialFeatureCount="
            + this.getSpecialFeatureCount()
            + ", displayPreferencesId="
            + this.getDisplayPreferencesId()
            + ", status="
            + this.getStatus()
            + ", airDays="
            + this.getAirDays()
            + ", tags="
            + this.getTags()
            + ", primaryImageAspectRatio="
            + this.getPrimaryImageAspectRatio()
            + ", artists="
            + this.getArtists()
            + ", artistItems="
            + this.getArtistItems()
            + ", composers="
            + this.getComposers()
            + ", album="
            + this.getAlbum()
            + ", collectionType="
            + this.getCollectionType()
            + ", displayOrder="
            + this.getDisplayOrder()
            + ", albumId="
            + this.getAlbumId()
            + ", albumPrimaryImageTag="
            + this.getAlbumPrimaryImageTag()
            + ", seriesPrimaryImageTag="
            + this.getSeriesPrimaryImageTag()
            + ", albumArtist="
            + this.getAlbumArtist()
            + ", albumArtists="
            + this.getAlbumArtists()
            + ", seasonName="
            + this.getSeasonName()
            + ", mediaStreams="
            + this.getMediaStreams()
            + ", partCount="
            + this.getPartCount()
            + ", imageTags="
            + this.getImageTags()
            + ", backdropImageTags="
            + this.getBackdropImageTags()
            + ", parentLogoImageTag="
            + this.getParentLogoImageTag()
            + ", seriesStudio="
            + this.getSeriesStudio()
            + ", primaryImageItemId="
            + this.getPrimaryImageItemId()
            + ", primaryImageTag="
            + this.getPrimaryImageTag()
            + ", parentThumbItemId="
            + this.getParentThumbItemId()
            + ", parentThumbImageTag="
            + this.getParentThumbImageTag()
            + ", chapters="
            + this.getChapters()
            + ", locationType="
            + this.getLocationType()
            + ", mediaType="
            + this.getMediaType()
            + ", endDate="
            + this.getEndDate()
            + ", lockedFields="
            + this.getLockedFields()
            + ", lockData="
            + this.getLockData()
            + ", width="
            + this.getWidth()
            + ", height="
            + this.getHeight()
            + ", cameraMake="
            + this.getCameraMake()
            + ", cameraModel="
            + this.getCameraModel()
            + ", software="
            + this.getSoftware()
            + ", exposureTime="
            + this.getExposureTime()
            + ", focalLength="
            + this.getFocalLength()
            + ", imageOrientation="
            + this.getImageOrientation()
            + ", aperture="
            + this.getAperture()
            + ", shutterSpeed="
            + this.getShutterSpeed()
            + ", latitude="
            + this.getLatitude()
            + ", longitude="
            + this.getLongitude()
            + ", altitude="
            + this.getAltitude()
            + ", isoSpeedRating="
            + this.getIsoSpeedRating()
            + ", seriesTimerId="
            + this.getSeriesTimerId()
            + ", channelPrimaryImageTag="
            + this.getChannelPrimaryImageTag()
            + ", startDate="
            + this.getStartDate()
            + ", completionPercentage="
            + this.getCompletionPercentage()
            + ", isRepeat="
            + this.getIsRepeat()
            + ", isNew="
            + this.getIsNew()
            + ", episodeTitle="
            + this.getEpisodeTitle()
            + ", isMovie="
            + this.getIsMovie()
            + ", isSports="
            + this.getIsSports()
            + ", isSeries="
            + this.getIsSeries()
            + ", isLive="
            + this.getIsLive()
            + ", isNews="
            + this.getIsNews()
            + ", isKids="
            + this.getIsKids()
            + ", isPremiere="
            + this.getIsPremiere()
            + ", timerType="
            + this.getTimerType()
            + ", disabled="
            + this.getDisabled()
            + ", managementId="
            + this.getManagementId()
            + ", timerId="
            + this.getTimerId()
            + ", currentProgram="
            + this.getCurrentProgram()
            + ", movieCount="
            + this.getMovieCount()
            + ", seriesCount="
            + this.getSeriesCount()
            + ", albumCount="
            + this.getAlbumCount()
            + ", songCount="
            + this.getSongCount()
            + ", musicVideoCount="
            + this.getMusicVideoCount()
            + ", subviews="
            + this.getSubviews()
            + ", listingsProviderId="
            + this.getListingsProviderId()
            + ", listingsChannelId="
            + this.getListingsChannelId()
            + ", listingsPath="
            + this.getListingsPath()
            + ", listingsId="
            + this.getListingsId()
            + ", listingsChannelName="
            + this.getListingsChannelName()
            + ", listingsChannelNumber="
            + this.getListingsChannelNumber()
            + ", affiliateCallSign="
            + this.getAffiliateCallSign()
            + ", imageUrl="
            + this.getImageUrl()
            + ", filmTitle="
            + this.getFilmTitle()
            + ", primaryImageAspectRatioCount="
            + this.getPrimaryImageAspectRatioCount()
            + ", embyItemUrl="
            + this.getEmbyItemUrl()
            + ", backdropImageUrl="
            + this.getBackdropImageUrl()
            + ")";
      }

      public static class ImageTagsDTO {
         private String primary;
         private String logo;

         @Generated
         public String getPrimary() {
            return this.primary;
         }

         @Generated
         public String getLogo() {
            return this.logo;
         }

         @Generated
         public void setPrimary(final String primary) {
            this.primary = primary;
         }

         @Generated
         public void setLogo(final String logo) {
            this.logo = logo;
         }

         @Generated
         @Override
         public boolean equals(final Object o) {
            if (o == this) {
               return true;
            } else if (!(o instanceof QueryResultBaseItemResponse.ItemsDTO.ImageTagsDTO other)) {
               return false;
            } else if (!other.canEqual(this)) {
               return false;
            } else {
               Object this$primary = this.getPrimary();
               Object other$primary = other.getPrimary();
               if (this$primary == null ? other$primary == null : this$primary.equals(other$primary)) {
                  Object this$logo = this.getLogo();
                  Object other$logo = other.getLogo();
                  return this$logo == null ? other$logo == null : this$logo.equals(other$logo);
               } else {
                  return false;
               }
            }
         }

         @Generated
         protected boolean canEqual(final Object other) {
            return other instanceof QueryResultBaseItemResponse.ItemsDTO.ImageTagsDTO;
         }

         @Generated
         @Override
         public int hashCode() {
            int PRIME = 59;
            int result = 1;
            Object $primary = this.getPrimary();
            result = result * 59 + ($primary == null ? 43 : $primary.hashCode());
            Object $logo = this.getLogo();
            return result * 59 + ($logo == null ? 43 : $logo.hashCode());
         }

         @Generated
         @Override
         public String toString() {
            return "QueryResultBaseItemResponse.ItemsDTO.ImageTagsDTO(primary=" + this.getPrimary() + ", logo=" + this.getLogo() + ")";
         }
      }
   }
}
