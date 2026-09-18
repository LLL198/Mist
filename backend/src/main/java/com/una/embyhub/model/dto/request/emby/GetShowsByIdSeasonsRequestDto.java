package com.una.embyhub.model.dto.request.emby;

import java.io.Serializable;
import lombok.Generated;

public class GetShowsByIdSeasonsRequestDto implements Serializable {
   private String id;
   private String artistType;
   private String maxOfficialRating;
   private Boolean hasThemeSong;
   private Boolean hasThemeVideo;
   private Boolean hasSubtitles;
   private Boolean hasSpecialFeature;
   private Boolean hasTrailer;
   private Boolean isSpecialSeason;
   private String adjacentTo;
   private String startItemId;
   private Integer minIndexNumber;
   private String minStartDate;
   private String maxStartDate;
   private String minEndDate;
   private String maxEndDate;
   private Integer minPlayers;
   private Integer maxPlayers;
   private Integer parentIndexNumber;
   private Boolean hasParentalRating;
   private Boolean isHD;
   private Boolean isUnaired;
   private Double minCommunityRating;
   private Double minCriticRating;
   private Integer airedDuringSeason;
   private String minPremiereDate;
   private String minDateLastSaved;
   private String minDateLastSavedForUser;
   private String maxPremiereDate;
   private Boolean hasOverview;
   private Boolean hasImdbId;
   private Boolean hasTmdbId;
   private Boolean hasTvdbId;
   private String excludeItemIds;
   private Integer startIndex;
   private Integer limit;
   private Boolean recursive;
   private String searchTerm;
   private String sortOrder;
   private String parentId;
   private String fields;
   private String excludeItemTypes;
   private String includeItemTypes;
   private String anyProviderIdEquals;
   private String filters;
   private Boolean isFavorite;
   private Boolean isMovie;
   private Boolean isSeries;
   private Boolean isFolder;
   private Boolean isNews;
   private Boolean isKids;
   private Boolean isSports;
   private Boolean isNew;
   private Boolean isPremiere;
   private Boolean isNewOrPremiere;
   private Boolean isRepeat;
   private Boolean projectToMedia;
   private String mediaTypes;
   private String imageTypes;
   private String sortBy;
   private Boolean isPlayed;
   private String genres;
   private String officialRatings;
   private String tags;
   private String excludeTags;
   private String years;
   private Boolean enableImages;
   private Boolean enableUserData;
   private Integer imageTypeLimit;
   private String enableImageTypes;
   private String person;
   private String personIds;
   private String personTypes;
   private String studios;
   private String studioIds;
   private String artists;
   private String artistIds;
   private String albums;
   private String ids;
   private String videoTypes;
   private String containers;
   private String audioCodecs;
   private String audioLayouts;
   private String videoCodecs;
   private String extendedVideoTypes;
   private String subtitleCodecs;
   private String path;
   private String userId;
   private String minOfficialRating;
   private Boolean isLocked;
   private Boolean isPlaceHolder;
   private Boolean hasOfficialRating;
   private Boolean groupItemsIntoCollections;
   private Boolean is3D;
   private String seriesStatus;
   private String nameStartsWithOrGreater;
   private String artistStartsWithOrGreater;
   private String albumArtistStartsWithOrGreater;
   private String nameStartsWith;
   private String nameLessThan;

   @Generated
   public String getId() {
      return this.id;
   }

   @Generated
   public String getArtistType() {
      return this.artistType;
   }

   @Generated
   public String getMaxOfficialRating() {
      return this.maxOfficialRating;
   }

   @Generated
   public Boolean getHasThemeSong() {
      return this.hasThemeSong;
   }

   @Generated
   public Boolean getHasThemeVideo() {
      return this.hasThemeVideo;
   }

   @Generated
   public Boolean getHasSubtitles() {
      return this.hasSubtitles;
   }

   @Generated
   public Boolean getHasSpecialFeature() {
      return this.hasSpecialFeature;
   }

   @Generated
   public Boolean getHasTrailer() {
      return this.hasTrailer;
   }

   @Generated
   public Boolean getIsSpecialSeason() {
      return this.isSpecialSeason;
   }

   @Generated
   public String getAdjacentTo() {
      return this.adjacentTo;
   }

   @Generated
   public String getStartItemId() {
      return this.startItemId;
   }

   @Generated
   public Integer getMinIndexNumber() {
      return this.minIndexNumber;
   }

   @Generated
   public String getMinStartDate() {
      return this.minStartDate;
   }

   @Generated
   public String getMaxStartDate() {
      return this.maxStartDate;
   }

   @Generated
   public String getMinEndDate() {
      return this.minEndDate;
   }

   @Generated
   public String getMaxEndDate() {
      return this.maxEndDate;
   }

   @Generated
   public Integer getMinPlayers() {
      return this.minPlayers;
   }

   @Generated
   public Integer getMaxPlayers() {
      return this.maxPlayers;
   }

   @Generated
   public Integer getParentIndexNumber() {
      return this.parentIndexNumber;
   }

   @Generated
   public Boolean getHasParentalRating() {
      return this.hasParentalRating;
   }

   @Generated
   public Boolean getIsHD() {
      return this.isHD;
   }

   @Generated
   public Boolean getIsUnaired() {
      return this.isUnaired;
   }

   @Generated
   public Double getMinCommunityRating() {
      return this.minCommunityRating;
   }

   @Generated
   public Double getMinCriticRating() {
      return this.minCriticRating;
   }

   @Generated
   public Integer getAiredDuringSeason() {
      return this.airedDuringSeason;
   }

   @Generated
   public String getMinPremiereDate() {
      return this.minPremiereDate;
   }

   @Generated
   public String getMinDateLastSaved() {
      return this.minDateLastSaved;
   }

   @Generated
   public String getMinDateLastSavedForUser() {
      return this.minDateLastSavedForUser;
   }

   @Generated
   public String getMaxPremiereDate() {
      return this.maxPremiereDate;
   }

   @Generated
   public Boolean getHasOverview() {
      return this.hasOverview;
   }

   @Generated
   public Boolean getHasImdbId() {
      return this.hasImdbId;
   }

   @Generated
   public Boolean getHasTmdbId() {
      return this.hasTmdbId;
   }

   @Generated
   public Boolean getHasTvdbId() {
      return this.hasTvdbId;
   }

   @Generated
   public String getExcludeItemIds() {
      return this.excludeItemIds;
   }

   @Generated
   public Integer getStartIndex() {
      return this.startIndex;
   }

   @Generated
   public Integer getLimit() {
      return this.limit;
   }

   @Generated
   public Boolean getRecursive() {
      return this.recursive;
   }

   @Generated
   public String getSearchTerm() {
      return this.searchTerm;
   }

   @Generated
   public String getSortOrder() {
      return this.sortOrder;
   }

   @Generated
   public String getParentId() {
      return this.parentId;
   }

   @Generated
   public String getFields() {
      return this.fields;
   }

   @Generated
   public String getExcludeItemTypes() {
      return this.excludeItemTypes;
   }

   @Generated
   public String getIncludeItemTypes() {
      return this.includeItemTypes;
   }

   @Generated
   public String getAnyProviderIdEquals() {
      return this.anyProviderIdEquals;
   }

   @Generated
   public String getFilters() {
      return this.filters;
   }

   @Generated
   public Boolean getIsFavorite() {
      return this.isFavorite;
   }

   @Generated
   public Boolean getIsMovie() {
      return this.isMovie;
   }

   @Generated
   public Boolean getIsSeries() {
      return this.isSeries;
   }

   @Generated
   public Boolean getIsFolder() {
      return this.isFolder;
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
   public Boolean getIsSports() {
      return this.isSports;
   }

   @Generated
   public Boolean getIsNew() {
      return this.isNew;
   }

   @Generated
   public Boolean getIsPremiere() {
      return this.isPremiere;
   }

   @Generated
   public Boolean getIsNewOrPremiere() {
      return this.isNewOrPremiere;
   }

   @Generated
   public Boolean getIsRepeat() {
      return this.isRepeat;
   }

   @Generated
   public Boolean getProjectToMedia() {
      return this.projectToMedia;
   }

   @Generated
   public String getMediaTypes() {
      return this.mediaTypes;
   }

   @Generated
   public String getImageTypes() {
      return this.imageTypes;
   }

   @Generated
   public String getSortBy() {
      return this.sortBy;
   }

   @Generated
   public Boolean getIsPlayed() {
      return this.isPlayed;
   }

   @Generated
   public String getGenres() {
      return this.genres;
   }

   @Generated
   public String getOfficialRatings() {
      return this.officialRatings;
   }

   @Generated
   public String getTags() {
      return this.tags;
   }

   @Generated
   public String getExcludeTags() {
      return this.excludeTags;
   }

   @Generated
   public String getYears() {
      return this.years;
   }

   @Generated
   public Boolean getEnableImages() {
      return this.enableImages;
   }

   @Generated
   public Boolean getEnableUserData() {
      return this.enableUserData;
   }

   @Generated
   public Integer getImageTypeLimit() {
      return this.imageTypeLimit;
   }

   @Generated
   public String getEnableImageTypes() {
      return this.enableImageTypes;
   }

   @Generated
   public String getPerson() {
      return this.person;
   }

   @Generated
   public String getPersonIds() {
      return this.personIds;
   }

   @Generated
   public String getPersonTypes() {
      return this.personTypes;
   }

   @Generated
   public String getStudios() {
      return this.studios;
   }

   @Generated
   public String getStudioIds() {
      return this.studioIds;
   }

   @Generated
   public String getArtists() {
      return this.artists;
   }

   @Generated
   public String getArtistIds() {
      return this.artistIds;
   }

   @Generated
   public String getAlbums() {
      return this.albums;
   }

   @Generated
   public String getIds() {
      return this.ids;
   }

   @Generated
   public String getVideoTypes() {
      return this.videoTypes;
   }

   @Generated
   public String getContainers() {
      return this.containers;
   }

   @Generated
   public String getAudioCodecs() {
      return this.audioCodecs;
   }

   @Generated
   public String getAudioLayouts() {
      return this.audioLayouts;
   }

   @Generated
   public String getVideoCodecs() {
      return this.videoCodecs;
   }

   @Generated
   public String getExtendedVideoTypes() {
      return this.extendedVideoTypes;
   }

   @Generated
   public String getSubtitleCodecs() {
      return this.subtitleCodecs;
   }

   @Generated
   public String getPath() {
      return this.path;
   }

   @Generated
   public String getUserId() {
      return this.userId;
   }

   @Generated
   public String getMinOfficialRating() {
      return this.minOfficialRating;
   }

   @Generated
   public Boolean getIsLocked() {
      return this.isLocked;
   }

   @Generated
   public Boolean getIsPlaceHolder() {
      return this.isPlaceHolder;
   }

   @Generated
   public Boolean getHasOfficialRating() {
      return this.hasOfficialRating;
   }

   @Generated
   public Boolean getGroupItemsIntoCollections() {
      return this.groupItemsIntoCollections;
   }

   @Generated
   public Boolean getIs3D() {
      return this.is3D;
   }

   @Generated
   public String getSeriesStatus() {
      return this.seriesStatus;
   }

   @Generated
   public String getNameStartsWithOrGreater() {
      return this.nameStartsWithOrGreater;
   }

   @Generated
   public String getArtistStartsWithOrGreater() {
      return this.artistStartsWithOrGreater;
   }

   @Generated
   public String getAlbumArtistStartsWithOrGreater() {
      return this.albumArtistStartsWithOrGreater;
   }

   @Generated
   public String getNameStartsWith() {
      return this.nameStartsWith;
   }

   @Generated
   public String getNameLessThan() {
      return this.nameLessThan;
   }

   @Generated
   public void setId(final String id) {
      this.id = id;
   }

   @Generated
   public void setArtistType(final String artistType) {
      this.artistType = artistType;
   }

   @Generated
   public void setMaxOfficialRating(final String maxOfficialRating) {
      this.maxOfficialRating = maxOfficialRating;
   }

   @Generated
   public void setHasThemeSong(final Boolean hasThemeSong) {
      this.hasThemeSong = hasThemeSong;
   }

   @Generated
   public void setHasThemeVideo(final Boolean hasThemeVideo) {
      this.hasThemeVideo = hasThemeVideo;
   }

   @Generated
   public void setHasSubtitles(final Boolean hasSubtitles) {
      this.hasSubtitles = hasSubtitles;
   }

   @Generated
   public void setHasSpecialFeature(final Boolean hasSpecialFeature) {
      this.hasSpecialFeature = hasSpecialFeature;
   }

   @Generated
   public void setHasTrailer(final Boolean hasTrailer) {
      this.hasTrailer = hasTrailer;
   }

   @Generated
   public void setIsSpecialSeason(final Boolean isSpecialSeason) {
      this.isSpecialSeason = isSpecialSeason;
   }

   @Generated
   public void setAdjacentTo(final String adjacentTo) {
      this.adjacentTo = adjacentTo;
   }

   @Generated
   public void setStartItemId(final String startItemId) {
      this.startItemId = startItemId;
   }

   @Generated
   public void setMinIndexNumber(final Integer minIndexNumber) {
      this.minIndexNumber = minIndexNumber;
   }

   @Generated
   public void setMinStartDate(final String minStartDate) {
      this.minStartDate = minStartDate;
   }

   @Generated
   public void setMaxStartDate(final String maxStartDate) {
      this.maxStartDate = maxStartDate;
   }

   @Generated
   public void setMinEndDate(final String minEndDate) {
      this.minEndDate = minEndDate;
   }

   @Generated
   public void setMaxEndDate(final String maxEndDate) {
      this.maxEndDate = maxEndDate;
   }

   @Generated
   public void setMinPlayers(final Integer minPlayers) {
      this.minPlayers = minPlayers;
   }

   @Generated
   public void setMaxPlayers(final Integer maxPlayers) {
      this.maxPlayers = maxPlayers;
   }

   @Generated
   public void setParentIndexNumber(final Integer parentIndexNumber) {
      this.parentIndexNumber = parentIndexNumber;
   }

   @Generated
   public void setHasParentalRating(final Boolean hasParentalRating) {
      this.hasParentalRating = hasParentalRating;
   }

   @Generated
   public void setIsHD(final Boolean isHD) {
      this.isHD = isHD;
   }

   @Generated
   public void setIsUnaired(final Boolean isUnaired) {
      this.isUnaired = isUnaired;
   }

   @Generated
   public void setMinCommunityRating(final Double minCommunityRating) {
      this.minCommunityRating = minCommunityRating;
   }

   @Generated
   public void setMinCriticRating(final Double minCriticRating) {
      this.minCriticRating = minCriticRating;
   }

   @Generated
   public void setAiredDuringSeason(final Integer airedDuringSeason) {
      this.airedDuringSeason = airedDuringSeason;
   }

   @Generated
   public void setMinPremiereDate(final String minPremiereDate) {
      this.minPremiereDate = minPremiereDate;
   }

   @Generated
   public void setMinDateLastSaved(final String minDateLastSaved) {
      this.minDateLastSaved = minDateLastSaved;
   }

   @Generated
   public void setMinDateLastSavedForUser(final String minDateLastSavedForUser) {
      this.minDateLastSavedForUser = minDateLastSavedForUser;
   }

   @Generated
   public void setMaxPremiereDate(final String maxPremiereDate) {
      this.maxPremiereDate = maxPremiereDate;
   }

   @Generated
   public void setHasOverview(final Boolean hasOverview) {
      this.hasOverview = hasOverview;
   }

   @Generated
   public void setHasImdbId(final Boolean hasImdbId) {
      this.hasImdbId = hasImdbId;
   }

   @Generated
   public void setHasTmdbId(final Boolean hasTmdbId) {
      this.hasTmdbId = hasTmdbId;
   }

   @Generated
   public void setHasTvdbId(final Boolean hasTvdbId) {
      this.hasTvdbId = hasTvdbId;
   }

   @Generated
   public void setExcludeItemIds(final String excludeItemIds) {
      this.excludeItemIds = excludeItemIds;
   }

   @Generated
   public void setStartIndex(final Integer startIndex) {
      this.startIndex = startIndex;
   }

   @Generated
   public void setLimit(final Integer limit) {
      this.limit = limit;
   }

   @Generated
   public void setRecursive(final Boolean recursive) {
      this.recursive = recursive;
   }

   @Generated
   public void setSearchTerm(final String searchTerm) {
      this.searchTerm = searchTerm;
   }

   @Generated
   public void setSortOrder(final String sortOrder) {
      this.sortOrder = sortOrder;
   }

   @Generated
   public void setParentId(final String parentId) {
      this.parentId = parentId;
   }

   @Generated
   public void setFields(final String fields) {
      this.fields = fields;
   }

   @Generated
   public void setExcludeItemTypes(final String excludeItemTypes) {
      this.excludeItemTypes = excludeItemTypes;
   }

   @Generated
   public void setIncludeItemTypes(final String includeItemTypes) {
      this.includeItemTypes = includeItemTypes;
   }

   @Generated
   public void setAnyProviderIdEquals(final String anyProviderIdEquals) {
      this.anyProviderIdEquals = anyProviderIdEquals;
   }

   @Generated
   public void setFilters(final String filters) {
      this.filters = filters;
   }

   @Generated
   public void setIsFavorite(final Boolean isFavorite) {
      this.isFavorite = isFavorite;
   }

   @Generated
   public void setIsMovie(final Boolean isMovie) {
      this.isMovie = isMovie;
   }

   @Generated
   public void setIsSeries(final Boolean isSeries) {
      this.isSeries = isSeries;
   }

   @Generated
   public void setIsFolder(final Boolean isFolder) {
      this.isFolder = isFolder;
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
   public void setIsSports(final Boolean isSports) {
      this.isSports = isSports;
   }

   @Generated
   public void setIsNew(final Boolean isNew) {
      this.isNew = isNew;
   }

   @Generated
   public void setIsPremiere(final Boolean isPremiere) {
      this.isPremiere = isPremiere;
   }

   @Generated
   public void setIsNewOrPremiere(final Boolean isNewOrPremiere) {
      this.isNewOrPremiere = isNewOrPremiere;
   }

   @Generated
   public void setIsRepeat(final Boolean isRepeat) {
      this.isRepeat = isRepeat;
   }

   @Generated
   public void setProjectToMedia(final Boolean projectToMedia) {
      this.projectToMedia = projectToMedia;
   }

   @Generated
   public void setMediaTypes(final String mediaTypes) {
      this.mediaTypes = mediaTypes;
   }

   @Generated
   public void setImageTypes(final String imageTypes) {
      this.imageTypes = imageTypes;
   }

   @Generated
   public void setSortBy(final String sortBy) {
      this.sortBy = sortBy;
   }

   @Generated
   public void setIsPlayed(final Boolean isPlayed) {
      this.isPlayed = isPlayed;
   }

   @Generated
   public void setGenres(final String genres) {
      this.genres = genres;
   }

   @Generated
   public void setOfficialRatings(final String officialRatings) {
      this.officialRatings = officialRatings;
   }

   @Generated
   public void setTags(final String tags) {
      this.tags = tags;
   }

   @Generated
   public void setExcludeTags(final String excludeTags) {
      this.excludeTags = excludeTags;
   }

   @Generated
   public void setYears(final String years) {
      this.years = years;
   }

   @Generated
   public void setEnableImages(final Boolean enableImages) {
      this.enableImages = enableImages;
   }

   @Generated
   public void setEnableUserData(final Boolean enableUserData) {
      this.enableUserData = enableUserData;
   }

   @Generated
   public void setImageTypeLimit(final Integer imageTypeLimit) {
      this.imageTypeLimit = imageTypeLimit;
   }

   @Generated
   public void setEnableImageTypes(final String enableImageTypes) {
      this.enableImageTypes = enableImageTypes;
   }

   @Generated
   public void setPerson(final String person) {
      this.person = person;
   }

   @Generated
   public void setPersonIds(final String personIds) {
      this.personIds = personIds;
   }

   @Generated
   public void setPersonTypes(final String personTypes) {
      this.personTypes = personTypes;
   }

   @Generated
   public void setStudios(final String studios) {
      this.studios = studios;
   }

   @Generated
   public void setStudioIds(final String studioIds) {
      this.studioIds = studioIds;
   }

   @Generated
   public void setArtists(final String artists) {
      this.artists = artists;
   }

   @Generated
   public void setArtistIds(final String artistIds) {
      this.artistIds = artistIds;
   }

   @Generated
   public void setAlbums(final String albums) {
      this.albums = albums;
   }

   @Generated
   public void setIds(final String ids) {
      this.ids = ids;
   }

   @Generated
   public void setVideoTypes(final String videoTypes) {
      this.videoTypes = videoTypes;
   }

   @Generated
   public void setContainers(final String containers) {
      this.containers = containers;
   }

   @Generated
   public void setAudioCodecs(final String audioCodecs) {
      this.audioCodecs = audioCodecs;
   }

   @Generated
   public void setAudioLayouts(final String audioLayouts) {
      this.audioLayouts = audioLayouts;
   }

   @Generated
   public void setVideoCodecs(final String videoCodecs) {
      this.videoCodecs = videoCodecs;
   }

   @Generated
   public void setExtendedVideoTypes(final String extendedVideoTypes) {
      this.extendedVideoTypes = extendedVideoTypes;
   }

   @Generated
   public void setSubtitleCodecs(final String subtitleCodecs) {
      this.subtitleCodecs = subtitleCodecs;
   }

   @Generated
   public void setPath(final String path) {
      this.path = path;
   }

   @Generated
   public void setUserId(final String userId) {
      this.userId = userId;
   }

   @Generated
   public void setMinOfficialRating(final String minOfficialRating) {
      this.minOfficialRating = minOfficialRating;
   }

   @Generated
   public void setIsLocked(final Boolean isLocked) {
      this.isLocked = isLocked;
   }

   @Generated
   public void setIsPlaceHolder(final Boolean isPlaceHolder) {
      this.isPlaceHolder = isPlaceHolder;
   }

   @Generated
   public void setHasOfficialRating(final Boolean hasOfficialRating) {
      this.hasOfficialRating = hasOfficialRating;
   }

   @Generated
   public void setGroupItemsIntoCollections(final Boolean groupItemsIntoCollections) {
      this.groupItemsIntoCollections = groupItemsIntoCollections;
   }

   @Generated
   public void setIs3D(final Boolean is3D) {
      this.is3D = is3D;
   }

   @Generated
   public void setSeriesStatus(final String seriesStatus) {
      this.seriesStatus = seriesStatus;
   }

   @Generated
   public void setNameStartsWithOrGreater(final String nameStartsWithOrGreater) {
      this.nameStartsWithOrGreater = nameStartsWithOrGreater;
   }

   @Generated
   public void setArtistStartsWithOrGreater(final String artistStartsWithOrGreater) {
      this.artistStartsWithOrGreater = artistStartsWithOrGreater;
   }

   @Generated
   public void setAlbumArtistStartsWithOrGreater(final String albumArtistStartsWithOrGreater) {
      this.albumArtistStartsWithOrGreater = albumArtistStartsWithOrGreater;
   }

   @Generated
   public void setNameStartsWith(final String nameStartsWith) {
      this.nameStartsWith = nameStartsWith;
   }

   @Generated
   public void setNameLessThan(final String nameLessThan) {
      this.nameLessThan = nameLessThan;
   }

   @Generated
   @Override
   public boolean equals(final Object o) {
      if (o == this) {
         return true;
      } else if (!(o instanceof GetShowsByIdSeasonsRequestDto other)) {
         return false;
      } else if (!other.canEqual(this)) {
         return false;
      } else {
         Object this$hasThemeSong = this.getHasThemeSong();
         Object other$hasThemeSong = other.getHasThemeSong();
         if (this$hasThemeSong == null ? other$hasThemeSong == null : this$hasThemeSong.equals(other$hasThemeSong)) {
            Object this$hasThemeVideo = this.getHasThemeVideo();
            Object other$hasThemeVideo = other.getHasThemeVideo();
            if (this$hasThemeVideo == null ? other$hasThemeVideo == null : this$hasThemeVideo.equals(other$hasThemeVideo)) {
               Object this$hasSubtitles = this.getHasSubtitles();
               Object other$hasSubtitles = other.getHasSubtitles();
               if (this$hasSubtitles == null ? other$hasSubtitles == null : this$hasSubtitles.equals(other$hasSubtitles)) {
                  Object this$hasSpecialFeature = this.getHasSpecialFeature();
                  Object other$hasSpecialFeature = other.getHasSpecialFeature();
                  if (this$hasSpecialFeature == null ? other$hasSpecialFeature == null : this$hasSpecialFeature.equals(other$hasSpecialFeature)) {
                     Object this$hasTrailer = this.getHasTrailer();
                     Object other$hasTrailer = other.getHasTrailer();
                     if (this$hasTrailer == null ? other$hasTrailer == null : this$hasTrailer.equals(other$hasTrailer)) {
                        Object this$isSpecialSeason = this.getIsSpecialSeason();
                        Object other$isSpecialSeason = other.getIsSpecialSeason();
                        if (this$isSpecialSeason == null ? other$isSpecialSeason == null : this$isSpecialSeason.equals(other$isSpecialSeason)) {
                           Object this$minIndexNumber = this.getMinIndexNumber();
                           Object other$minIndexNumber = other.getMinIndexNumber();
                           if (this$minIndexNumber == null ? other$minIndexNumber == null : this$minIndexNumber.equals(other$minIndexNumber)) {
                              Object this$minPlayers = this.getMinPlayers();
                              Object other$minPlayers = other.getMinPlayers();
                              if (this$minPlayers == null ? other$minPlayers == null : this$minPlayers.equals(other$minPlayers)) {
                                 Object this$maxPlayers = this.getMaxPlayers();
                                 Object other$maxPlayers = other.getMaxPlayers();
                                 if (this$maxPlayers == null ? other$maxPlayers == null : this$maxPlayers.equals(other$maxPlayers)) {
                                    Object this$parentIndexNumber = this.getParentIndexNumber();
                                    Object other$parentIndexNumber = other.getParentIndexNumber();
                                    if (this$parentIndexNumber == null
                                       ? other$parentIndexNumber == null
                                       : this$parentIndexNumber.equals(other$parentIndexNumber)) {
                                       Object this$hasParentalRating = this.getHasParentalRating();
                                       Object other$hasParentalRating = other.getHasParentalRating();
                                       if (this$hasParentalRating == null
                                          ? other$hasParentalRating == null
                                          : this$hasParentalRating.equals(other$hasParentalRating)) {
                                          Object this$isHD = this.getIsHD();
                                          Object other$isHD = other.getIsHD();
                                          if (this$isHD == null ? other$isHD == null : this$isHD.equals(other$isHD)) {
                                             Object this$isUnaired = this.getIsUnaired();
                                             Object other$isUnaired = other.getIsUnaired();
                                             if (this$isUnaired == null ? other$isUnaired == null : this$isUnaired.equals(other$isUnaired)) {
                                                Object this$minCommunityRating = this.getMinCommunityRating();
                                                Object other$minCommunityRating = other.getMinCommunityRating();
                                                if (this$minCommunityRating == null
                                                   ? other$minCommunityRating == null
                                                   : this$minCommunityRating.equals(other$minCommunityRating)) {
                                                   Object this$minCriticRating = this.getMinCriticRating();
                                                   Object other$minCriticRating = other.getMinCriticRating();
                                                   if (this$minCriticRating == null
                                                      ? other$minCriticRating == null
                                                      : this$minCriticRating.equals(other$minCriticRating)) {
                                                      Object this$airedDuringSeason = this.getAiredDuringSeason();
                                                      Object other$airedDuringSeason = other.getAiredDuringSeason();
                                                      if (this$airedDuringSeason == null
                                                         ? other$airedDuringSeason == null
                                                         : this$airedDuringSeason.equals(other$airedDuringSeason)) {
                                                         Object this$hasOverview = this.getHasOverview();
                                                         Object other$hasOverview = other.getHasOverview();
                                                         if (this$hasOverview == null ? other$hasOverview == null : this$hasOverview.equals(other$hasOverview)) {
                                                            Object this$hasImdbId = this.getHasImdbId();
                                                            Object other$hasImdbId = other.getHasImdbId();
                                                            if (this$hasImdbId == null ? other$hasImdbId == null : this$hasImdbId.equals(other$hasImdbId)) {
                                                               Object this$hasTmdbId = this.getHasTmdbId();
                                                               Object other$hasTmdbId = other.getHasTmdbId();
                                                               if (this$hasTmdbId == null ? other$hasTmdbId == null : this$hasTmdbId.equals(other$hasTmdbId)) {
                                                                  Object this$hasTvdbId = this.getHasTvdbId();
                                                                  Object other$hasTvdbId = other.getHasTvdbId();
                                                                  if (this$hasTvdbId == null ? other$hasTvdbId == null : this$hasTvdbId.equals(other$hasTvdbId)
                                                                     )
                                                                   {
                                                                     Object this$startIndex = this.getStartIndex();
                                                                     Object other$startIndex = other.getStartIndex();
                                                                     if (this$startIndex == null
                                                                        ? other$startIndex == null
                                                                        : this$startIndex.equals(other$startIndex)) {
                                                                        Object this$limit = this.getLimit();
                                                                        Object other$limit = other.getLimit();
                                                                        if (this$limit == null ? other$limit == null : this$limit.equals(other$limit)) {
                                                                           Object this$recursive = this.getRecursive();
                                                                           Object other$recursive = other.getRecursive();
                                                                           if (this$recursive == null
                                                                              ? other$recursive == null
                                                                              : this$recursive.equals(other$recursive)) {
                                                                              Object this$isFavorite = this.getIsFavorite();
                                                                              Object other$isFavorite = other.getIsFavorite();
                                                                              if (this$isFavorite == null
                                                                                 ? other$isFavorite == null
                                                                                 : this$isFavorite.equals(other$isFavorite)) {
                                                                                 Object this$isMovie = this.getIsMovie();
                                                                                 Object other$isMovie = other.getIsMovie();
                                                                                 if (this$isMovie == null
                                                                                    ? other$isMovie == null
                                                                                    : this$isMovie.equals(other$isMovie)) {
                                                                                    Object this$isSeries = this.getIsSeries();
                                                                                    Object other$isSeries = other.getIsSeries();
                                                                                    if (this$isSeries == null
                                                                                       ? other$isSeries == null
                                                                                       : this$isSeries.equals(other$isSeries)) {
                                                                                       Object this$isFolder = this.getIsFolder();
                                                                                       Object other$isFolder = other.getIsFolder();
                                                                                       if (this$isFolder == null
                                                                                          ? other$isFolder == null
                                                                                          : this$isFolder.equals(other$isFolder)) {
                                                                                          Object this$isNews = this.getIsNews();
                                                                                          Object other$isNews = other.getIsNews();
                                                                                          if (this$isNews == null
                                                                                             ? other$isNews == null
                                                                                             : this$isNews.equals(other$isNews)) {
                                                                                             Object this$isKids = this.getIsKids();
                                                                                             Object other$isKids = other.getIsKids();
                                                                                             if (this$isKids == null
                                                                                                ? other$isKids == null
                                                                                                : this$isKids.equals(other$isKids)) {
                                                                                                Object this$isSports = this.getIsSports();
                                                                                                Object other$isSports = other.getIsSports();
                                                                                                if (this$isSports == null
                                                                                                   ? other$isSports == null
                                                                                                   : this$isSports.equals(other$isSports)) {
                                                                                                   Object this$isNew = this.getIsNew();
                                                                                                   Object other$isNew = other.getIsNew();
                                                                                                   if (this$isNew == null
                                                                                                      ? other$isNew == null
                                                                                                      : this$isNew.equals(other$isNew)) {
                                                                                                      Object this$isPremiere = this.getIsPremiere();
                                                                                                      Object other$isPremiere = other.getIsPremiere();
                                                                                                      if (this$isPremiere == null
                                                                                                         ? other$isPremiere == null
                                                                                                         : this$isPremiere.equals(other$isPremiere)) {
                                                                                                         Object this$isNewOrPremiere = this.getIsNewOrPremiere();
                                                                                                         Object other$isNewOrPremiere = other.getIsNewOrPremiere();
                                                                                                         if (this$isNewOrPremiere == null
                                                                                                            ? other$isNewOrPremiere == null
                                                                                                            : this$isNewOrPremiere.equals(other$isNewOrPremiere)
                                                                                                            )
                                                                                                          {
                                                                                                            Object this$isRepeat = this.getIsRepeat();
                                                                                                            Object other$isRepeat = other.getIsRepeat();
                                                                                                            if (this$isRepeat == null
                                                                                                               ? other$isRepeat == null
                                                                                                               : this$isRepeat.equals(other$isRepeat)) {
                                                                                                               Object this$projectToMedia = this.getProjectToMedia();
                                                                                                               Object other$projectToMedia = other.getProjectToMedia();
                                                                                                               if (this$projectToMedia == null
                                                                                                                  ? other$projectToMedia == null
                                                                                                                  : this$projectToMedia.equals(
                                                                                                                     other$projectToMedia
                                                                                                                  )) {
                                                                                                                  Object this$isPlayed = this.getIsPlayed();
                                                                                                                  Object other$isPlayed = other.getIsPlayed();
                                                                                                                  if (this$isPlayed == null
                                                                                                                     ? other$isPlayed == null
                                                                                                                     : this$isPlayed.equals(other$isPlayed)) {
                                                                                                                     Object this$enableImages = this.getEnableImages();
                                                                                                                     Object other$enableImages = other.getEnableImages();
                                                                                                                     if (this$enableImages == null
                                                                                                                        ? other$enableImages == null
                                                                                                                        : this$enableImages.equals(
                                                                                                                           other$enableImages
                                                                                                                        )) {
                                                                                                                        Object this$enableUserData = this.getEnableUserData();
                                                                                                                        Object other$enableUserData = other.getEnableUserData();
                                                                                                                        if (this$enableUserData == null
                                                                                                                           ? other$enableUserData == null
                                                                                                                           : this$enableUserData.equals(
                                                                                                                              other$enableUserData
                                                                                                                           )) {
                                                                                                                           Object this$imageTypeLimit = this.getImageTypeLimit();
                                                                                                                           Object other$imageTypeLimit = other.getImageTypeLimit();
                                                                                                                           if (this$imageTypeLimit == null
                                                                                                                              ? other$imageTypeLimit == null
                                                                                                                              : this$imageTypeLimit.equals(
                                                                                                                                 other$imageTypeLimit
                                                                                                                              )) {
                                                                                                                              Object this$isLocked = this.getIsLocked();
                                                                                                                              Object other$isLocked = other.getIsLocked();
                                                                                                                              if (this$isLocked == null
                                                                                                                                 ? other$isLocked == null
                                                                                                                                 : this$isLocked.equals(
                                                                                                                                    other$isLocked
                                                                                                                                 )) {
                                                                                                                                 Object this$isPlaceHolder = this.getIsPlaceHolder();
                                                                                                                                 Object other$isPlaceHolder = other.getIsPlaceHolder();
                                                                                                                                 if (this$isPlaceHolder == null
                                                                                                                                    ? other$isPlaceHolder
                                                                                                                                       == null
                                                                                                                                    : this$isPlaceHolder.equals(
                                                                                                                                       other$isPlaceHolder
                                                                                                                                    )) {
                                                                                                                                    Object this$hasOfficialRating = this.getHasOfficialRating();
                                                                                                                                    Object other$hasOfficialRating = other.getHasOfficialRating();
                                                                                                                                    if (this$hasOfficialRating
                                                                                                                                          == null
                                                                                                                                       ? other$hasOfficialRating
                                                                                                                                          == null
                                                                                                                                       : this$hasOfficialRating.equals(
                                                                                                                                          other$hasOfficialRating
                                                                                                                                       )) {
                                                                                                                                       Object this$groupItemsIntoCollections = this.getGroupItemsIntoCollections();
                                                                                                                                       Object other$groupItemsIntoCollections = other.getGroupItemsIntoCollections();
                                                                                                                                       if (this$groupItemsIntoCollections
                                                                                                                                             == null
                                                                                                                                          ? other$groupItemsIntoCollections
                                                                                                                                             == null
                                                                                                                                          : this$groupItemsIntoCollections.equals(
                                                                                                                                             other$groupItemsIntoCollections
                                                                                                                                          )) {
                                                                                                                                          Object this$is3D = this.getIs3D();
                                                                                                                                          Object other$is3D = other.getIs3D();
                                                                                                                                          if (this$is3D == null
                                                                                                                                             ? other$is3D
                                                                                                                                                == null
                                                                                                                                             : this$is3D.equals(
                                                                                                                                                other$is3D
                                                                                                                                             )) {
                                                                                                                                             Object this$id = this.getId();
                                                                                                                                             Object other$id = other.getId();
                                                                                                                                             if (this$id
                                                                                                                                                   == null
                                                                                                                                                ? other$id
                                                                                                                                                   == null
                                                                                                                                                : this$id.equals(
                                                                                                                                                   other$id
                                                                                                                                                )) {
                                                                                                                                                Object this$artistType = this.getArtistType();
                                                                                                                                                Object other$artistType = other.getArtistType();
                                                                                                                                                if (this$artistType
                                                                                                                                                      == null
                                                                                                                                                   ? other$artistType
                                                                                                                                                      == null
                                                                                                                                                   : this$artistType.equals(
                                                                                                                                                      other$artistType
                                                                                                                                                   )) {
                                                                                                                                                   Object this$maxOfficialRating = this.getMaxOfficialRating();
                                                                                                                                                   Object other$maxOfficialRating = other.getMaxOfficialRating();
                                                                                                                                                   if (this$maxOfficialRating
                                                                                                                                                         == null
                                                                                                                                                      ? other$maxOfficialRating
                                                                                                                                                         == null
                                                                                                                                                      : this$maxOfficialRating.equals(
                                                                                                                                                         other$maxOfficialRating
                                                                                                                                                      )) {
                                                                                                                                                      Object this$adjacentTo = this.getAdjacentTo();
                                                                                                                                                      Object other$adjacentTo = other.getAdjacentTo();
                                                                                                                                                      if (this$adjacentTo
                                                                                                                                                            == null
                                                                                                                                                         ? other$adjacentTo
                                                                                                                                                            == null
                                                                                                                                                         : this$adjacentTo.equals(
                                                                                                                                                            other$adjacentTo
                                                                                                                                                         )) {
                                                                                                                                                         Object this$startItemId = this.getStartItemId();
                                                                                                                                                         Object other$startItemId = other.getStartItemId();
                                                                                                                                                         if (this$startItemId
                                                                                                                                                               == null
                                                                                                                                                            ? other$startItemId
                                                                                                                                                               == null
                                                                                                                                                            : this$startItemId.equals(
                                                                                                                                                               other$startItemId
                                                                                                                                                            )) {
                                                                                                                                                            Object this$minStartDate = this.getMinStartDate();
                                                                                                                                                            Object other$minStartDate = other.getMinStartDate();
                                                                                                                                                            if (this$minStartDate
                                                                                                                                                                  == null
                                                                                                                                                               ? other$minStartDate
                                                                                                                                                                  == null
                                                                                                                                                               : this$minStartDate.equals(
                                                                                                                                                                  other$minStartDate
                                                                                                                                                               )
                                                                                                                                                               )
                                                                                                                                                             {
                                                                                                                                                               Object this$maxStartDate = this.getMaxStartDate();
                                                                                                                                                               Object other$maxStartDate = other.getMaxStartDate();
                                                                                                                                                               if (this$maxStartDate
                                                                                                                                                                     == null
                                                                                                                                                                  ? other$maxStartDate
                                                                                                                                                                     == null
                                                                                                                                                                  : this$maxStartDate.equals(
                                                                                                                                                                     other$maxStartDate
                                                                                                                                                                  )
                                                                                                                                                                  )
                                                                                                                                                                {
                                                                                                                                                                  Object this$minEndDate = this.getMinEndDate();
                                                                                                                                                                  Object other$minEndDate = other.getMinEndDate();
                                                                                                                                                                  if (this$minEndDate
                                                                                                                                                                        == null
                                                                                                                                                                     ? other$minEndDate
                                                                                                                                                                        == null
                                                                                                                                                                     : this$minEndDate.equals(
                                                                                                                                                                        other$minEndDate
                                                                                                                                                                     )
                                                                                                                                                                     )
                                                                                                                                                                   {
                                                                                                                                                                     Object this$maxEndDate = this.getMaxEndDate();
                                                                                                                                                                     Object other$maxEndDate = other.getMaxEndDate();
                                                                                                                                                                     if (this$maxEndDate
                                                                                                                                                                           == null
                                                                                                                                                                        ? other$maxEndDate
                                                                                                                                                                           == null
                                                                                                                                                                        : this$maxEndDate.equals(
                                                                                                                                                                           other$maxEndDate
                                                                                                                                                                        )
                                                                                                                                                                        )
                                                                                                                                                                      {
                                                                                                                                                                        Object this$minPremiereDate = this.getMinPremiereDate();
                                                                                                                                                                        Object other$minPremiereDate = other.getMinPremiereDate();
                                                                                                                                                                        if (this$minPremiereDate
                                                                                                                                                                              == null
                                                                                                                                                                           ? other$minPremiereDate
                                                                                                                                                                              == null
                                                                                                                                                                           : this$minPremiereDate.equals(
                                                                                                                                                                              other$minPremiereDate
                                                                                                                                                                           )
                                                                                                                                                                           )
                                                                                                                                                                         {
                                                                                                                                                                           Object this$minDateLastSaved = this.getMinDateLastSaved();
                                                                                                                                                                           Object other$minDateLastSaved = other.getMinDateLastSaved();
                                                                                                                                                                           if (this$minDateLastSaved
                                                                                                                                                                                 == null
                                                                                                                                                                              ? other$minDateLastSaved
                                                                                                                                                                                 == null
                                                                                                                                                                              : this$minDateLastSaved.equals(
                                                                                                                                                                                 other$minDateLastSaved
                                                                                                                                                                              )
                                                                                                                                                                              )
                                                                                                                                                                            {
                                                                                                                                                                              Object this$minDateLastSavedForUser = this.getMinDateLastSavedForUser();
                                                                                                                                                                              Object other$minDateLastSavedForUser = other.getMinDateLastSavedForUser();
                                                                                                                                                                              if (this$minDateLastSavedForUser
                                                                                                                                                                                    == null
                                                                                                                                                                                 ? other$minDateLastSavedForUser
                                                                                                                                                                                    == null
                                                                                                                                                                                 : this$minDateLastSavedForUser.equals(
                                                                                                                                                                                    other$minDateLastSavedForUser
                                                                                                                                                                                 )
                                                                                                                                                                                 )
                                                                                                                                                                               {
                                                                                                                                                                                 Object this$maxPremiereDate = this.getMaxPremiereDate();
                                                                                                                                                                                 Object other$maxPremiereDate = other.getMaxPremiereDate();
                                                                                                                                                                                 if (this$maxPremiereDate
                                                                                                                                                                                       == null
                                                                                                                                                                                    ? other$maxPremiereDate
                                                                                                                                                                                       == null
                                                                                                                                                                                    : this$maxPremiereDate.equals(
                                                                                                                                                                                       other$maxPremiereDate
                                                                                                                                                                                    )
                                                                                                                                                                                    )
                                                                                                                                                                                  {
                                                                                                                                                                                    Object this$excludeItemIds = this.getExcludeItemIds();
                                                                                                                                                                                    Object other$excludeItemIds = other.getExcludeItemIds();
                                                                                                                                                                                    if (this$excludeItemIds
                                                                                                                                                                                          == null
                                                                                                                                                                                       ? other$excludeItemIds
                                                                                                                                                                                          == null
                                                                                                                                                                                       : this$excludeItemIds.equals(
                                                                                                                                                                                          other$excludeItemIds
                                                                                                                                                                                       )
                                                                                                                                                                                       )
                                                                                                                                                                                     {
                                                                                                                                                                                       Object this$searchTerm = this.getSearchTerm();
                                                                                                                                                                                       Object other$searchTerm = other.getSearchTerm();
                                                                                                                                                                                       if (this$searchTerm
                                                                                                                                                                                             == null
                                                                                                                                                                                          ? other$searchTerm
                                                                                                                                                                                             == null
                                                                                                                                                                                          : this$searchTerm.equals(
                                                                                                                                                                                             other$searchTerm
                                                                                                                                                                                          )
                                                                                                                                                                                          )
                                                                                                                                                                                        {
                                                                                                                                                                                          Object this$sortOrder = this.getSortOrder();
                                                                                                                                                                                          Object other$sortOrder = other.getSortOrder();
                                                                                                                                                                                          if (this$sortOrder
                                                                                                                                                                                                == null
                                                                                                                                                                                             ? other$sortOrder
                                                                                                                                                                                                == null
                                                                                                                                                                                             : this$sortOrder.equals(
                                                                                                                                                                                                other$sortOrder
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
                                                                                                                                                                                                Object this$fields = this.getFields();
                                                                                                                                                                                                Object other$fields = other.getFields();
                                                                                                                                                                                                if (this$fields
                                                                                                                                                                                                      == null
                                                                                                                                                                                                   ? other$fields
                                                                                                                                                                                                      == null
                                                                                                                                                                                                   : this$fields.equals(
                                                                                                                                                                                                      other$fields
                                                                                                                                                                                                   )
                                                                                                                                                                                                   )
                                                                                                                                                                                                 {
                                                                                                                                                                                                   Object this$excludeItemTypes = this.getExcludeItemTypes();
                                                                                                                                                                                                   Object other$excludeItemTypes = other.getExcludeItemTypes();
                                                                                                                                                                                                   if (this$excludeItemTypes
                                                                                                                                                                                                         == null
                                                                                                                                                                                                      ? other$excludeItemTypes
                                                                                                                                                                                                         == null
                                                                                                                                                                                                      : this$excludeItemTypes.equals(
                                                                                                                                                                                                         other$excludeItemTypes
                                                                                                                                                                                                      )
                                                                                                                                                                                                      )
                                                                                                                                                                                                    {
                                                                                                                                                                                                      Object this$includeItemTypes = this.getIncludeItemTypes();
                                                                                                                                                                                                      Object other$includeItemTypes = other.getIncludeItemTypes();
                                                                                                                                                                                                      if (this$includeItemTypes
                                                                                                                                                                                                            == null
                                                                                                                                                                                                         ? other$includeItemTypes
                                                                                                                                                                                                            == null
                                                                                                                                                                                                         : this$includeItemTypes.equals(
                                                                                                                                                                                                            other$includeItemTypes
                                                                                                                                                                                                         )
                                                                                                                                                                                                         )
                                                                                                                                                                                                       {
                                                                                                                                                                                                         Object this$anyProviderIdEquals = this.getAnyProviderIdEquals();
                                                                                                                                                                                                         Object other$anyProviderIdEquals = other.getAnyProviderIdEquals();
                                                                                                                                                                                                         if (this$anyProviderIdEquals
                                                                                                                                                                                                               == null
                                                                                                                                                                                                            ? other$anyProviderIdEquals
                                                                                                                                                                                                               == null
                                                                                                                                                                                                            : this$anyProviderIdEquals.equals(
                                                                                                                                                                                                               other$anyProviderIdEquals
                                                                                                                                                                                                            )
                                                                                                                                                                                                            )
                                                                                                                                                                                                          {
                                                                                                                                                                                                            Object this$filters = this.getFilters();
                                                                                                                                                                                                            Object other$filters = other.getFilters();
                                                                                                                                                                                                            if (this$filters
                                                                                                                                                                                                                  == null
                                                                                                                                                                                                               ? other$filters
                                                                                                                                                                                                                  == null
                                                                                                                                                                                                               : this$filters.equals(
                                                                                                                                                                                                                  other$filters
                                                                                                                                                                                                               )
                                                                                                                                                                                                               )
                                                                                                                                                                                                             {
                                                                                                                                                                                                               Object this$mediaTypes = this.getMediaTypes();
                                                                                                                                                                                                               Object other$mediaTypes = other.getMediaTypes();
                                                                                                                                                                                                               if (this$mediaTypes
                                                                                                                                                                                                                     == null
                                                                                                                                                                                                                  ? other$mediaTypes
                                                                                                                                                                                                                     == null
                                                                                                                                                                                                                  : this$mediaTypes.equals(
                                                                                                                                                                                                                     other$mediaTypes
                                                                                                                                                                                                                  )
                                                                                                                                                                                                                  )
                                                                                                                                                                                                                {
                                                                                                                                                                                                                  Object this$imageTypes = this.getImageTypes();
                                                                                                                                                                                                                  Object other$imageTypes = other.getImageTypes();
                                                                                                                                                                                                                  if (this$imageTypes
                                                                                                                                                                                                                        == null
                                                                                                                                                                                                                     ? other$imageTypes
                                                                                                                                                                                                                        == null
                                                                                                                                                                                                                     : this$imageTypes.equals(
                                                                                                                                                                                                                        other$imageTypes
                                                                                                                                                                                                                     )
                                                                                                                                                                                                                     )
                                                                                                                                                                                                                   {
                                                                                                                                                                                                                     Object this$sortBy = this.getSortBy();
                                                                                                                                                                                                                     Object other$sortBy = other.getSortBy();
                                                                                                                                                                                                                     if (this$sortBy
                                                                                                                                                                                                                           == null
                                                                                                                                                                                                                        ? other$sortBy
                                                                                                                                                                                                                           == null
                                                                                                                                                                                                                        : this$sortBy.equals(
                                                                                                                                                                                                                           other$sortBy
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
                                                                                                                                                                                                                           Object this$officialRatings = this.getOfficialRatings();
                                                                                                                                                                                                                           Object other$officialRatings = other.getOfficialRatings();
                                                                                                                                                                                                                           if (this$officialRatings
                                                                                                                                                                                                                                 == null
                                                                                                                                                                                                                              ? other$officialRatings
                                                                                                                                                                                                                                 == null
                                                                                                                                                                                                                              : this$officialRatings.equals(
                                                                                                                                                                                                                                 other$officialRatings
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
                                                                                                                                                                                                                                 Object this$excludeTags = this.getExcludeTags();
                                                                                                                                                                                                                                 Object other$excludeTags = other.getExcludeTags();
                                                                                                                                                                                                                                 if (this$excludeTags
                                                                                                                                                                                                                                       == null
                                                                                                                                                                                                                                    ? other$excludeTags
                                                                                                                                                                                                                                       == null
                                                                                                                                                                                                                                    : this$excludeTags.equals(
                                                                                                                                                                                                                                       other$excludeTags
                                                                                                                                                                                                                                    )
                                                                                                                                                                                                                                    )
                                                                                                                                                                                                                                  {
                                                                                                                                                                                                                                    Object this$years = this.getYears();
                                                                                                                                                                                                                                    Object other$years = other.getYears();
                                                                                                                                                                                                                                    if (this$years
                                                                                                                                                                                                                                          == null
                                                                                                                                                                                                                                       ? other$years
                                                                                                                                                                                                                                          == null
                                                                                                                                                                                                                                       : this$years.equals(
                                                                                                                                                                                                                                          other$years
                                                                                                                                                                                                                                       )
                                                                                                                                                                                                                                       )
                                                                                                                                                                                                                                     {
                                                                                                                                                                                                                                       Object this$enableImageTypes = this.getEnableImageTypes();
                                                                                                                                                                                                                                       Object other$enableImageTypes = other.getEnableImageTypes();
                                                                                                                                                                                                                                       if (this$enableImageTypes
                                                                                                                                                                                                                                             == null
                                                                                                                                                                                                                                          ? other$enableImageTypes
                                                                                                                                                                                                                                             == null
                                                                                                                                                                                                                                          : this$enableImageTypes.equals(
                                                                                                                                                                                                                                             other$enableImageTypes
                                                                                                                                                                                                                                          )
                                                                                                                                                                                                                                          )
                                                                                                                                                                                                                                        {
                                                                                                                                                                                                                                          Object this$person = this.getPerson();
                                                                                                                                                                                                                                          Object other$person = other.getPerson();
                                                                                                                                                                                                                                          if (this$person
                                                                                                                                                                                                                                                == null
                                                                                                                                                                                                                                             ? other$person
                                                                                                                                                                                                                                                == null
                                                                                                                                                                                                                                             : this$person.equals(
                                                                                                                                                                                                                                                other$person
                                                                                                                                                                                                                                             )
                                                                                                                                                                                                                                             )
                                                                                                                                                                                                                                           {
                                                                                                                                                                                                                                             Object this$personIds = this.getPersonIds();
                                                                                                                                                                                                                                             Object other$personIds = other.getPersonIds();
                                                                                                                                                                                                                                             if (this$personIds
                                                                                                                                                                                                                                                   == null
                                                                                                                                                                                                                                                ? other$personIds
                                                                                                                                                                                                                                                   == null
                                                                                                                                                                                                                                                : this$personIds.equals(
                                                                                                                                                                                                                                                   other$personIds
                                                                                                                                                                                                                                                )
                                                                                                                                                                                                                                                )
                                                                                                                                                                                                                                              {
                                                                                                                                                                                                                                                Object this$personTypes = this.getPersonTypes();
                                                                                                                                                                                                                                                Object other$personTypes = other.getPersonTypes();
                                                                                                                                                                                                                                                if (this$personTypes
                                                                                                                                                                                                                                                      == null
                                                                                                                                                                                                                                                   ? other$personTypes
                                                                                                                                                                                                                                                      == null
                                                                                                                                                                                                                                                   : this$personTypes.equals(
                                                                                                                                                                                                                                                      other$personTypes
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
                                                                                                                                                                                                                                                      Object this$studioIds = this.getStudioIds();
                                                                                                                                                                                                                                                      Object other$studioIds = other.getStudioIds();
                                                                                                                                                                                                                                                      if (this$studioIds
                                                                                                                                                                                                                                                            == null
                                                                                                                                                                                                                                                         ? other$studioIds
                                                                                                                                                                                                                                                            == null
                                                                                                                                                                                                                                                         : this$studioIds.equals(
                                                                                                                                                                                                                                                            other$studioIds
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
                                                                                                                                                                                                                                                            Object this$artistIds = this.getArtistIds();
                                                                                                                                                                                                                                                            Object other$artistIds = other.getArtistIds();
                                                                                                                                                                                                                                                            if (this$artistIds
                                                                                                                                                                                                                                                                  == null
                                                                                                                                                                                                                                                               ? other$artistIds
                                                                                                                                                                                                                                                                  == null
                                                                                                                                                                                                                                                               : this$artistIds.equals(
                                                                                                                                                                                                                                                                  other$artistIds
                                                                                                                                                                                                                                                               )
                                                                                                                                                                                                                                                               )
                                                                                                                                                                                                                                                             {
                                                                                                                                                                                                                                                               Object this$albums = this.getAlbums();
                                                                                                                                                                                                                                                               Object other$albums = other.getAlbums();
                                                                                                                                                                                                                                                               if (this$albums
                                                                                                                                                                                                                                                                     == null
                                                                                                                                                                                                                                                                  ? other$albums
                                                                                                                                                                                                                                                                     == null
                                                                                                                                                                                                                                                                  : this$albums.equals(
                                                                                                                                                                                                                                                                     other$albums
                                                                                                                                                                                                                                                                  )
                                                                                                                                                                                                                                                                  )
                                                                                                                                                                                                                                                                {
                                                                                                                                                                                                                                                                  Object this$ids = this.getIds();
                                                                                                                                                                                                                                                                  Object other$ids = other.getIds();
                                                                                                                                                                                                                                                                  if (this$ids
                                                                                                                                                                                                                                                                        == null
                                                                                                                                                                                                                                                                     ? other$ids
                                                                                                                                                                                                                                                                        == null
                                                                                                                                                                                                                                                                     : this$ids.equals(
                                                                                                                                                                                                                                                                        other$ids
                                                                                                                                                                                                                                                                     )
                                                                                                                                                                                                                                                                     )
                                                                                                                                                                                                                                                                   {
                                                                                                                                                                                                                                                                     Object this$videoTypes = this.getVideoTypes();
                                                                                                                                                                                                                                                                     Object other$videoTypes = other.getVideoTypes();
                                                                                                                                                                                                                                                                     if (this$videoTypes
                                                                                                                                                                                                                                                                           == null
                                                                                                                                                                                                                                                                        ? other$videoTypes
                                                                                                                                                                                                                                                                           == null
                                                                                                                                                                                                                                                                        : this$videoTypes.equals(
                                                                                                                                                                                                                                                                           other$videoTypes
                                                                                                                                                                                                                                                                        )
                                                                                                                                                                                                                                                                        )
                                                                                                                                                                                                                                                                      {
                                                                                                                                                                                                                                                                        Object this$containers = this.getContainers();
                                                                                                                                                                                                                                                                        Object other$containers = other.getContainers();
                                                                                                                                                                                                                                                                        if (this$containers
                                                                                                                                                                                                                                                                              == null
                                                                                                                                                                                                                                                                           ? other$containers
                                                                                                                                                                                                                                                                              == null
                                                                                                                                                                                                                                                                           : this$containers.equals(
                                                                                                                                                                                                                                                                              other$containers
                                                                                                                                                                                                                                                                           )
                                                                                                                                                                                                                                                                           )
                                                                                                                                                                                                                                                                         {
                                                                                                                                                                                                                                                                           Object this$audioCodecs = this.getAudioCodecs();
                                                                                                                                                                                                                                                                           Object other$audioCodecs = other.getAudioCodecs();
                                                                                                                                                                                                                                                                           if (this$audioCodecs
                                                                                                                                                                                                                                                                                 == null
                                                                                                                                                                                                                                                                              ? other$audioCodecs
                                                                                                                                                                                                                                                                                 == null
                                                                                                                                                                                                                                                                              : this$audioCodecs.equals(
                                                                                                                                                                                                                                                                                 other$audioCodecs
                                                                                                                                                                                                                                                                              )
                                                                                                                                                                                                                                                                              )
                                                                                                                                                                                                                                                                            {
                                                                                                                                                                                                                                                                              Object this$audioLayouts = this.getAudioLayouts();
                                                                                                                                                                                                                                                                              Object other$audioLayouts = other.getAudioLayouts();
                                                                                                                                                                                                                                                                              if (this$audioLayouts
                                                                                                                                                                                                                                                                                    == null
                                                                                                                                                                                                                                                                                 ? other$audioLayouts
                                                                                                                                                                                                                                                                                    == null
                                                                                                                                                                                                                                                                                 : this$audioLayouts.equals(
                                                                                                                                                                                                                                                                                    other$audioLayouts
                                                                                                                                                                                                                                                                                 )
                                                                                                                                                                                                                                                                                 )
                                                                                                                                                                                                                                                                               {
                                                                                                                                                                                                                                                                                 Object this$videoCodecs = this.getVideoCodecs();
                                                                                                                                                                                                                                                                                 Object other$videoCodecs = other.getVideoCodecs();
                                                                                                                                                                                                                                                                                 if (this$videoCodecs
                                                                                                                                                                                                                                                                                       == null
                                                                                                                                                                                                                                                                                    ? other$videoCodecs
                                                                                                                                                                                                                                                                                       == null
                                                                                                                                                                                                                                                                                    : this$videoCodecs.equals(
                                                                                                                                                                                                                                                                                       other$videoCodecs
                                                                                                                                                                                                                                                                                    )
                                                                                                                                                                                                                                                                                    )
                                                                                                                                                                                                                                                                                  {
                                                                                                                                                                                                                                                                                    Object this$extendedVideoTypes = this.getExtendedVideoTypes();
                                                                                                                                                                                                                                                                                    Object other$extendedVideoTypes = other.getExtendedVideoTypes();
                                                                                                                                                                                                                                                                                    if (this$extendedVideoTypes
                                                                                                                                                                                                                                                                                          == null
                                                                                                                                                                                                                                                                                       ? other$extendedVideoTypes
                                                                                                                                                                                                                                                                                          == null
                                                                                                                                                                                                                                                                                       : this$extendedVideoTypes.equals(
                                                                                                                                                                                                                                                                                          other$extendedVideoTypes
                                                                                                                                                                                                                                                                                       )
                                                                                                                                                                                                                                                                                       )
                                                                                                                                                                                                                                                                                     {
                                                                                                                                                                                                                                                                                       Object this$subtitleCodecs = this.getSubtitleCodecs();
                                                                                                                                                                                                                                                                                       Object other$subtitleCodecs = other.getSubtitleCodecs();
                                                                                                                                                                                                                                                                                       if (this$subtitleCodecs
                                                                                                                                                                                                                                                                                             == null
                                                                                                                                                                                                                                                                                          ? other$subtitleCodecs
                                                                                                                                                                                                                                                                                             == null
                                                                                                                                                                                                                                                                                          : this$subtitleCodecs.equals(
                                                                                                                                                                                                                                                                                             other$subtitleCodecs
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
                                                                                                                                                                                                                                                                                             Object this$userId = this.getUserId();
                                                                                                                                                                                                                                                                                             Object other$userId = other.getUserId();
                                                                                                                                                                                                                                                                                             if (this$userId
                                                                                                                                                                                                                                                                                                   == null
                                                                                                                                                                                                                                                                                                ? other$userId
                                                                                                                                                                                                                                                                                                   == null
                                                                                                                                                                                                                                                                                                : this$userId.equals(
                                                                                                                                                                                                                                                                                                   other$userId
                                                                                                                                                                                                                                                                                                )
                                                                                                                                                                                                                                                                                                )
                                                                                                                                                                                                                                                                                              {
                                                                                                                                                                                                                                                                                                Object this$minOfficialRating = this.getMinOfficialRating();
                                                                                                                                                                                                                                                                                                Object other$minOfficialRating = other.getMinOfficialRating();
                                                                                                                                                                                                                                                                                                if (this$minOfficialRating
                                                                                                                                                                                                                                                                                                      == null
                                                                                                                                                                                                                                                                                                   ? other$minOfficialRating
                                                                                                                                                                                                                                                                                                      == null
                                                                                                                                                                                                                                                                                                   : this$minOfficialRating.equals(
                                                                                                                                                                                                                                                                                                      other$minOfficialRating
                                                                                                                                                                                                                                                                                                   )
                                                                                                                                                                                                                                                                                                   )
                                                                                                                                                                                                                                                                                                 {
                                                                                                                                                                                                                                                                                                   Object this$seriesStatus = this.getSeriesStatus();
                                                                                                                                                                                                                                                                                                   Object other$seriesStatus = other.getSeriesStatus();
                                                                                                                                                                                                                                                                                                   if (this$seriesStatus
                                                                                                                                                                                                                                                                                                         == null
                                                                                                                                                                                                                                                                                                      ? other$seriesStatus
                                                                                                                                                                                                                                                                                                         == null
                                                                                                                                                                                                                                                                                                      : this$seriesStatus.equals(
                                                                                                                                                                                                                                                                                                         other$seriesStatus
                                                                                                                                                                                                                                                                                                      )
                                                                                                                                                                                                                                                                                                      )
                                                                                                                                                                                                                                                                                                    {
                                                                                                                                                                                                                                                                                                      Object this$nameStartsWithOrGreater = this.getNameStartsWithOrGreater();
                                                                                                                                                                                                                                                                                                      Object other$nameStartsWithOrGreater = other.getNameStartsWithOrGreater();
                                                                                                                                                                                                                                                                                                      if (this$nameStartsWithOrGreater
                                                                                                                                                                                                                                                                                                            == null
                                                                                                                                                                                                                                                                                                         ? other$nameStartsWithOrGreater
                                                                                                                                                                                                                                                                                                            == null
                                                                                                                                                                                                                                                                                                         : this$nameStartsWithOrGreater.equals(
                                                                                                                                                                                                                                                                                                            other$nameStartsWithOrGreater
                                                                                                                                                                                                                                                                                                         )
                                                                                                                                                                                                                                                                                                         )
                                                                                                                                                                                                                                                                                                       {
                                                                                                                                                                                                                                                                                                         Object this$artistStartsWithOrGreater = this.getArtistStartsWithOrGreater();
                                                                                                                                                                                                                                                                                                         Object other$artistStartsWithOrGreater = other.getArtistStartsWithOrGreater();
                                                                                                                                                                                                                                                                                                         if (this$artistStartsWithOrGreater
                                                                                                                                                                                                                                                                                                               == null
                                                                                                                                                                                                                                                                                                            ? other$artistStartsWithOrGreater
                                                                                                                                                                                                                                                                                                               == null
                                                                                                                                                                                                                                                                                                            : this$artistStartsWithOrGreater.equals(
                                                                                                                                                                                                                                                                                                               other$artistStartsWithOrGreater
                                                                                                                                                                                                                                                                                                            )
                                                                                                                                                                                                                                                                                                            )
                                                                                                                                                                                                                                                                                                          {
                                                                                                                                                                                                                                                                                                            Object this$albumArtistStartsWithOrGreater = this.getAlbumArtistStartsWithOrGreater();
                                                                                                                                                                                                                                                                                                            Object other$albumArtistStartsWithOrGreater = other.getAlbumArtistStartsWithOrGreater();
                                                                                                                                                                                                                                                                                                            if (this$albumArtistStartsWithOrGreater
                                                                                                                                                                                                                                                                                                                  == null
                                                                                                                                                                                                                                                                                                               ? other$albumArtistStartsWithOrGreater
                                                                                                                                                                                                                                                                                                                  == null
                                                                                                                                                                                                                                                                                                               : this$albumArtistStartsWithOrGreater.equals(
                                                                                                                                                                                                                                                                                                                  other$albumArtistStartsWithOrGreater
                                                                                                                                                                                                                                                                                                               )
                                                                                                                                                                                                                                                                                                               )
                                                                                                                                                                                                                                                                                                             {
                                                                                                                                                                                                                                                                                                               Object this$nameStartsWith = this.getNameStartsWith();
                                                                                                                                                                                                                                                                                                               Object other$nameStartsWith = other.getNameStartsWith();
                                                                                                                                                                                                                                                                                                               if (this$nameStartsWith
                                                                                                                                                                                                                                                                                                                     == null
                                                                                                                                                                                                                                                                                                                  ? other$nameStartsWith
                                                                                                                                                                                                                                                                                                                     == null
                                                                                                                                                                                                                                                                                                                  : this$nameStartsWith.equals(
                                                                                                                                                                                                                                                                                                                     other$nameStartsWith
                                                                                                                                                                                                                                                                                                                  )
                                                                                                                                                                                                                                                                                                                  )
                                                                                                                                                                                                                                                                                                                {
                                                                                                                                                                                                                                                                                                                  Object this$nameLessThan = this.getNameLessThan();
                                                                                                                                                                                                                                                                                                                  Object other$nameLessThan = other.getNameLessThan();
                                                                                                                                                                                                                                                                                                                  return this$nameLessThan
                                                                                                                                                                                                                                                                                                                        == null
                                                                                                                                                                                                                                                                                                                     ? other$nameLessThan
                                                                                                                                                                                                                                                                                                                        == null
                                                                                                                                                                                                                                                                                                                     : this$nameLessThan.equals(
                                                                                                                                                                                                                                                                                                                        other$nameLessThan
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
      }
   }

   @Generated
   protected boolean canEqual(final Object other) {
      return other instanceof GetShowsByIdSeasonsRequestDto;
   }

   @Generated
   @Override
   public int hashCode() {
      int PRIME = 59;
      int result = 1;
      Object $hasThemeSong = this.getHasThemeSong();
      result = result * 59 + ($hasThemeSong == null ? 43 : $hasThemeSong.hashCode());
      Object $hasThemeVideo = this.getHasThemeVideo();
      result = result * 59 + ($hasThemeVideo == null ? 43 : $hasThemeVideo.hashCode());
      Object $hasSubtitles = this.getHasSubtitles();
      result = result * 59 + ($hasSubtitles == null ? 43 : $hasSubtitles.hashCode());
      Object $hasSpecialFeature = this.getHasSpecialFeature();
      result = result * 59 + ($hasSpecialFeature == null ? 43 : $hasSpecialFeature.hashCode());
      Object $hasTrailer = this.getHasTrailer();
      result = result * 59 + ($hasTrailer == null ? 43 : $hasTrailer.hashCode());
      Object $isSpecialSeason = this.getIsSpecialSeason();
      result = result * 59 + ($isSpecialSeason == null ? 43 : $isSpecialSeason.hashCode());
      Object $minIndexNumber = this.getMinIndexNumber();
      result = result * 59 + ($minIndexNumber == null ? 43 : $minIndexNumber.hashCode());
      Object $minPlayers = this.getMinPlayers();
      result = result * 59 + ($minPlayers == null ? 43 : $minPlayers.hashCode());
      Object $maxPlayers = this.getMaxPlayers();
      result = result * 59 + ($maxPlayers == null ? 43 : $maxPlayers.hashCode());
      Object $parentIndexNumber = this.getParentIndexNumber();
      result = result * 59 + ($parentIndexNumber == null ? 43 : $parentIndexNumber.hashCode());
      Object $hasParentalRating = this.getHasParentalRating();
      result = result * 59 + ($hasParentalRating == null ? 43 : $hasParentalRating.hashCode());
      Object $isHD = this.getIsHD();
      result = result * 59 + ($isHD == null ? 43 : $isHD.hashCode());
      Object $isUnaired = this.getIsUnaired();
      result = result * 59 + ($isUnaired == null ? 43 : $isUnaired.hashCode());
      Object $minCommunityRating = this.getMinCommunityRating();
      result = result * 59 + ($minCommunityRating == null ? 43 : $minCommunityRating.hashCode());
      Object $minCriticRating = this.getMinCriticRating();
      result = result * 59 + ($minCriticRating == null ? 43 : $minCriticRating.hashCode());
      Object $airedDuringSeason = this.getAiredDuringSeason();
      result = result * 59 + ($airedDuringSeason == null ? 43 : $airedDuringSeason.hashCode());
      Object $hasOverview = this.getHasOverview();
      result = result * 59 + ($hasOverview == null ? 43 : $hasOverview.hashCode());
      Object $hasImdbId = this.getHasImdbId();
      result = result * 59 + ($hasImdbId == null ? 43 : $hasImdbId.hashCode());
      Object $hasTmdbId = this.getHasTmdbId();
      result = result * 59 + ($hasTmdbId == null ? 43 : $hasTmdbId.hashCode());
      Object $hasTvdbId = this.getHasTvdbId();
      result = result * 59 + ($hasTvdbId == null ? 43 : $hasTvdbId.hashCode());
      Object $startIndex = this.getStartIndex();
      result = result * 59 + ($startIndex == null ? 43 : $startIndex.hashCode());
      Object $limit = this.getLimit();
      result = result * 59 + ($limit == null ? 43 : $limit.hashCode());
      Object $recursive = this.getRecursive();
      result = result * 59 + ($recursive == null ? 43 : $recursive.hashCode());
      Object $isFavorite = this.getIsFavorite();
      result = result * 59 + ($isFavorite == null ? 43 : $isFavorite.hashCode());
      Object $isMovie = this.getIsMovie();
      result = result * 59 + ($isMovie == null ? 43 : $isMovie.hashCode());
      Object $isSeries = this.getIsSeries();
      result = result * 59 + ($isSeries == null ? 43 : $isSeries.hashCode());
      Object $isFolder = this.getIsFolder();
      result = result * 59 + ($isFolder == null ? 43 : $isFolder.hashCode());
      Object $isNews = this.getIsNews();
      result = result * 59 + ($isNews == null ? 43 : $isNews.hashCode());
      Object $isKids = this.getIsKids();
      result = result * 59 + ($isKids == null ? 43 : $isKids.hashCode());
      Object $isSports = this.getIsSports();
      result = result * 59 + ($isSports == null ? 43 : $isSports.hashCode());
      Object $isNew = this.getIsNew();
      result = result * 59 + ($isNew == null ? 43 : $isNew.hashCode());
      Object $isPremiere = this.getIsPremiere();
      result = result * 59 + ($isPremiere == null ? 43 : $isPremiere.hashCode());
      Object $isNewOrPremiere = this.getIsNewOrPremiere();
      result = result * 59 + ($isNewOrPremiere == null ? 43 : $isNewOrPremiere.hashCode());
      Object $isRepeat = this.getIsRepeat();
      result = result * 59 + ($isRepeat == null ? 43 : $isRepeat.hashCode());
      Object $projectToMedia = this.getProjectToMedia();
      result = result * 59 + ($projectToMedia == null ? 43 : $projectToMedia.hashCode());
      Object $isPlayed = this.getIsPlayed();
      result = result * 59 + ($isPlayed == null ? 43 : $isPlayed.hashCode());
      Object $enableImages = this.getEnableImages();
      result = result * 59 + ($enableImages == null ? 43 : $enableImages.hashCode());
      Object $enableUserData = this.getEnableUserData();
      result = result * 59 + ($enableUserData == null ? 43 : $enableUserData.hashCode());
      Object $imageTypeLimit = this.getImageTypeLimit();
      result = result * 59 + ($imageTypeLimit == null ? 43 : $imageTypeLimit.hashCode());
      Object $isLocked = this.getIsLocked();
      result = result * 59 + ($isLocked == null ? 43 : $isLocked.hashCode());
      Object $isPlaceHolder = this.getIsPlaceHolder();
      result = result * 59 + ($isPlaceHolder == null ? 43 : $isPlaceHolder.hashCode());
      Object $hasOfficialRating = this.getHasOfficialRating();
      result = result * 59 + ($hasOfficialRating == null ? 43 : $hasOfficialRating.hashCode());
      Object $groupItemsIntoCollections = this.getGroupItemsIntoCollections();
      result = result * 59 + ($groupItemsIntoCollections == null ? 43 : $groupItemsIntoCollections.hashCode());
      Object $is3D = this.getIs3D();
      result = result * 59 + ($is3D == null ? 43 : $is3D.hashCode());
      Object $id = this.getId();
      result = result * 59 + ($id == null ? 43 : $id.hashCode());
      Object $artistType = this.getArtistType();
      result = result * 59 + ($artistType == null ? 43 : $artistType.hashCode());
      Object $maxOfficialRating = this.getMaxOfficialRating();
      result = result * 59 + ($maxOfficialRating == null ? 43 : $maxOfficialRating.hashCode());
      Object $adjacentTo = this.getAdjacentTo();
      result = result * 59 + ($adjacentTo == null ? 43 : $adjacentTo.hashCode());
      Object $startItemId = this.getStartItemId();
      result = result * 59 + ($startItemId == null ? 43 : $startItemId.hashCode());
      Object $minStartDate = this.getMinStartDate();
      result = result * 59 + ($minStartDate == null ? 43 : $minStartDate.hashCode());
      Object $maxStartDate = this.getMaxStartDate();
      result = result * 59 + ($maxStartDate == null ? 43 : $maxStartDate.hashCode());
      Object $minEndDate = this.getMinEndDate();
      result = result * 59 + ($minEndDate == null ? 43 : $minEndDate.hashCode());
      Object $maxEndDate = this.getMaxEndDate();
      result = result * 59 + ($maxEndDate == null ? 43 : $maxEndDate.hashCode());
      Object $minPremiereDate = this.getMinPremiereDate();
      result = result * 59 + ($minPremiereDate == null ? 43 : $minPremiereDate.hashCode());
      Object $minDateLastSaved = this.getMinDateLastSaved();
      result = result * 59 + ($minDateLastSaved == null ? 43 : $minDateLastSaved.hashCode());
      Object $minDateLastSavedForUser = this.getMinDateLastSavedForUser();
      result = result * 59 + ($minDateLastSavedForUser == null ? 43 : $minDateLastSavedForUser.hashCode());
      Object $maxPremiereDate = this.getMaxPremiereDate();
      result = result * 59 + ($maxPremiereDate == null ? 43 : $maxPremiereDate.hashCode());
      Object $excludeItemIds = this.getExcludeItemIds();
      result = result * 59 + ($excludeItemIds == null ? 43 : $excludeItemIds.hashCode());
      Object $searchTerm = this.getSearchTerm();
      result = result * 59 + ($searchTerm == null ? 43 : $searchTerm.hashCode());
      Object $sortOrder = this.getSortOrder();
      result = result * 59 + ($sortOrder == null ? 43 : $sortOrder.hashCode());
      Object $parentId = this.getParentId();
      result = result * 59 + ($parentId == null ? 43 : $parentId.hashCode());
      Object $fields = this.getFields();
      result = result * 59 + ($fields == null ? 43 : $fields.hashCode());
      Object $excludeItemTypes = this.getExcludeItemTypes();
      result = result * 59 + ($excludeItemTypes == null ? 43 : $excludeItemTypes.hashCode());
      Object $includeItemTypes = this.getIncludeItemTypes();
      result = result * 59 + ($includeItemTypes == null ? 43 : $includeItemTypes.hashCode());
      Object $anyProviderIdEquals = this.getAnyProviderIdEquals();
      result = result * 59 + ($anyProviderIdEquals == null ? 43 : $anyProviderIdEquals.hashCode());
      Object $filters = this.getFilters();
      result = result * 59 + ($filters == null ? 43 : $filters.hashCode());
      Object $mediaTypes = this.getMediaTypes();
      result = result * 59 + ($mediaTypes == null ? 43 : $mediaTypes.hashCode());
      Object $imageTypes = this.getImageTypes();
      result = result * 59 + ($imageTypes == null ? 43 : $imageTypes.hashCode());
      Object $sortBy = this.getSortBy();
      result = result * 59 + ($sortBy == null ? 43 : $sortBy.hashCode());
      Object $genres = this.getGenres();
      result = result * 59 + ($genres == null ? 43 : $genres.hashCode());
      Object $officialRatings = this.getOfficialRatings();
      result = result * 59 + ($officialRatings == null ? 43 : $officialRatings.hashCode());
      Object $tags = this.getTags();
      result = result * 59 + ($tags == null ? 43 : $tags.hashCode());
      Object $excludeTags = this.getExcludeTags();
      result = result * 59 + ($excludeTags == null ? 43 : $excludeTags.hashCode());
      Object $years = this.getYears();
      result = result * 59 + ($years == null ? 43 : $years.hashCode());
      Object $enableImageTypes = this.getEnableImageTypes();
      result = result * 59 + ($enableImageTypes == null ? 43 : $enableImageTypes.hashCode());
      Object $person = this.getPerson();
      result = result * 59 + ($person == null ? 43 : $person.hashCode());
      Object $personIds = this.getPersonIds();
      result = result * 59 + ($personIds == null ? 43 : $personIds.hashCode());
      Object $personTypes = this.getPersonTypes();
      result = result * 59 + ($personTypes == null ? 43 : $personTypes.hashCode());
      Object $studios = this.getStudios();
      result = result * 59 + ($studios == null ? 43 : $studios.hashCode());
      Object $studioIds = this.getStudioIds();
      result = result * 59 + ($studioIds == null ? 43 : $studioIds.hashCode());
      Object $artists = this.getArtists();
      result = result * 59 + ($artists == null ? 43 : $artists.hashCode());
      Object $artistIds = this.getArtistIds();
      result = result * 59 + ($artistIds == null ? 43 : $artistIds.hashCode());
      Object $albums = this.getAlbums();
      result = result * 59 + ($albums == null ? 43 : $albums.hashCode());
      Object $ids = this.getIds();
      result = result * 59 + ($ids == null ? 43 : $ids.hashCode());
      Object $videoTypes = this.getVideoTypes();
      result = result * 59 + ($videoTypes == null ? 43 : $videoTypes.hashCode());
      Object $containers = this.getContainers();
      result = result * 59 + ($containers == null ? 43 : $containers.hashCode());
      Object $audioCodecs = this.getAudioCodecs();
      result = result * 59 + ($audioCodecs == null ? 43 : $audioCodecs.hashCode());
      Object $audioLayouts = this.getAudioLayouts();
      result = result * 59 + ($audioLayouts == null ? 43 : $audioLayouts.hashCode());
      Object $videoCodecs = this.getVideoCodecs();
      result = result * 59 + ($videoCodecs == null ? 43 : $videoCodecs.hashCode());
      Object $extendedVideoTypes = this.getExtendedVideoTypes();
      result = result * 59 + ($extendedVideoTypes == null ? 43 : $extendedVideoTypes.hashCode());
      Object $subtitleCodecs = this.getSubtitleCodecs();
      result = result * 59 + ($subtitleCodecs == null ? 43 : $subtitleCodecs.hashCode());
      Object $path = this.getPath();
      result = result * 59 + ($path == null ? 43 : $path.hashCode());
      Object $userId = this.getUserId();
      result = result * 59 + ($userId == null ? 43 : $userId.hashCode());
      Object $minOfficialRating = this.getMinOfficialRating();
      result = result * 59 + ($minOfficialRating == null ? 43 : $minOfficialRating.hashCode());
      Object $seriesStatus = this.getSeriesStatus();
      result = result * 59 + ($seriesStatus == null ? 43 : $seriesStatus.hashCode());
      Object $nameStartsWithOrGreater = this.getNameStartsWithOrGreater();
      result = result * 59 + ($nameStartsWithOrGreater == null ? 43 : $nameStartsWithOrGreater.hashCode());
      Object $artistStartsWithOrGreater = this.getArtistStartsWithOrGreater();
      result = result * 59 + ($artistStartsWithOrGreater == null ? 43 : $artistStartsWithOrGreater.hashCode());
      Object $albumArtistStartsWithOrGreater = this.getAlbumArtistStartsWithOrGreater();
      result = result * 59 + ($albumArtistStartsWithOrGreater == null ? 43 : $albumArtistStartsWithOrGreater.hashCode());
      Object $nameStartsWith = this.getNameStartsWith();
      result = result * 59 + ($nameStartsWith == null ? 43 : $nameStartsWith.hashCode());
      Object $nameLessThan = this.getNameLessThan();
      return result * 59 + ($nameLessThan == null ? 43 : $nameLessThan.hashCode());
   }

   @Generated
   @Override
   public String toString() {
      return "GetShowsByIdSeasonsRequestDto(id="
         + this.getId()
         + ", artistType="
         + this.getArtistType()
         + ", maxOfficialRating="
         + this.getMaxOfficialRating()
         + ", hasThemeSong="
         + this.getHasThemeSong()
         + ", hasThemeVideo="
         + this.getHasThemeVideo()
         + ", hasSubtitles="
         + this.getHasSubtitles()
         + ", hasSpecialFeature="
         + this.getHasSpecialFeature()
         + ", hasTrailer="
         + this.getHasTrailer()
         + ", isSpecialSeason="
         + this.getIsSpecialSeason()
         + ", adjacentTo="
         + this.getAdjacentTo()
         + ", startItemId="
         + this.getStartItemId()
         + ", minIndexNumber="
         + this.getMinIndexNumber()
         + ", minStartDate="
         + this.getMinStartDate()
         + ", maxStartDate="
         + this.getMaxStartDate()
         + ", minEndDate="
         + this.getMinEndDate()
         + ", maxEndDate="
         + this.getMaxEndDate()
         + ", minPlayers="
         + this.getMinPlayers()
         + ", maxPlayers="
         + this.getMaxPlayers()
         + ", parentIndexNumber="
         + this.getParentIndexNumber()
         + ", hasParentalRating="
         + this.getHasParentalRating()
         + ", isHD="
         + this.getIsHD()
         + ", isUnaired="
         + this.getIsUnaired()
         + ", minCommunityRating="
         + this.getMinCommunityRating()
         + ", minCriticRating="
         + this.getMinCriticRating()
         + ", airedDuringSeason="
         + this.getAiredDuringSeason()
         + ", minPremiereDate="
         + this.getMinPremiereDate()
         + ", minDateLastSaved="
         + this.getMinDateLastSaved()
         + ", minDateLastSavedForUser="
         + this.getMinDateLastSavedForUser()
         + ", maxPremiereDate="
         + this.getMaxPremiereDate()
         + ", hasOverview="
         + this.getHasOverview()
         + ", hasImdbId="
         + this.getHasImdbId()
         + ", hasTmdbId="
         + this.getHasTmdbId()
         + ", hasTvdbId="
         + this.getHasTvdbId()
         + ", excludeItemIds="
         + this.getExcludeItemIds()
         + ", startIndex="
         + this.getStartIndex()
         + ", limit="
         + this.getLimit()
         + ", recursive="
         + this.getRecursive()
         + ", searchTerm="
         + this.getSearchTerm()
         + ", sortOrder="
         + this.getSortOrder()
         + ", parentId="
         + this.getParentId()
         + ", fields="
         + this.getFields()
         + ", excludeItemTypes="
         + this.getExcludeItemTypes()
         + ", includeItemTypes="
         + this.getIncludeItemTypes()
         + ", anyProviderIdEquals="
         + this.getAnyProviderIdEquals()
         + ", filters="
         + this.getFilters()
         + ", isFavorite="
         + this.getIsFavorite()
         + ", isMovie="
         + this.getIsMovie()
         + ", isSeries="
         + this.getIsSeries()
         + ", isFolder="
         + this.getIsFolder()
         + ", isNews="
         + this.getIsNews()
         + ", isKids="
         + this.getIsKids()
         + ", isSports="
         + this.getIsSports()
         + ", isNew="
         + this.getIsNew()
         + ", isPremiere="
         + this.getIsPremiere()
         + ", isNewOrPremiere="
         + this.getIsNewOrPremiere()
         + ", isRepeat="
         + this.getIsRepeat()
         + ", projectToMedia="
         + this.getProjectToMedia()
         + ", mediaTypes="
         + this.getMediaTypes()
         + ", imageTypes="
         + this.getImageTypes()
         + ", sortBy="
         + this.getSortBy()
         + ", isPlayed="
         + this.getIsPlayed()
         + ", genres="
         + this.getGenres()
         + ", officialRatings="
         + this.getOfficialRatings()
         + ", tags="
         + this.getTags()
         + ", excludeTags="
         + this.getExcludeTags()
         + ", years="
         + this.getYears()
         + ", enableImages="
         + this.getEnableImages()
         + ", enableUserData="
         + this.getEnableUserData()
         + ", imageTypeLimit="
         + this.getImageTypeLimit()
         + ", enableImageTypes="
         + this.getEnableImageTypes()
         + ", person="
         + this.getPerson()
         + ", personIds="
         + this.getPersonIds()
         + ", personTypes="
         + this.getPersonTypes()
         + ", studios="
         + this.getStudios()
         + ", studioIds="
         + this.getStudioIds()
         + ", artists="
         + this.getArtists()
         + ", artistIds="
         + this.getArtistIds()
         + ", albums="
         + this.getAlbums()
         + ", ids="
         + this.getIds()
         + ", videoTypes="
         + this.getVideoTypes()
         + ", containers="
         + this.getContainers()
         + ", audioCodecs="
         + this.getAudioCodecs()
         + ", audioLayouts="
         + this.getAudioLayouts()
         + ", videoCodecs="
         + this.getVideoCodecs()
         + ", extendedVideoTypes="
         + this.getExtendedVideoTypes()
         + ", subtitleCodecs="
         + this.getSubtitleCodecs()
         + ", path="
         + this.getPath()
         + ", userId="
         + this.getUserId()
         + ", minOfficialRating="
         + this.getMinOfficialRating()
         + ", isLocked="
         + this.getIsLocked()
         + ", isPlaceHolder="
         + this.getIsPlaceHolder()
         + ", hasOfficialRating="
         + this.getHasOfficialRating()
         + ", groupItemsIntoCollections="
         + this.getGroupItemsIntoCollections()
         + ", is3D="
         + this.getIs3D()
         + ", seriesStatus="
         + this.getSeriesStatus()
         + ", nameStartsWithOrGreater="
         + this.getNameStartsWithOrGreater()
         + ", artistStartsWithOrGreater="
         + this.getArtistStartsWithOrGreater()
         + ", albumArtistStartsWithOrGreater="
         + this.getAlbumArtistStartsWithOrGreater()
         + ", nameStartsWith="
         + this.getNameStartsWith()
         + ", nameLessThan="
         + this.getNameLessThan()
         + ")";
   }
}
