package com.una.embyhub.model.dto.request.emby;

import com.alibaba.fastjson2.JSONObject;
import java.io.Serializable;
import java.util.List;
import lombok.Generated;

public class LibraryNewRequest implements Serializable {
   private String Title;
   private String Description;
   private String Date;
   private String Event;
   private LibraryNewRequest.ItemDTO Item;
   private LibraryNewRequest.ServerDTO Server;

   @Generated
   public String getTitle() {
      return this.Title;
   }

   @Generated
   public String getDescription() {
      return this.Description;
   }

   @Generated
   public String getDate() {
      return this.Date;
   }

   @Generated
   public String getEvent() {
      return this.Event;
   }

   @Generated
   public LibraryNewRequest.ItemDTO getItem() {
      return this.Item;
   }

   @Generated
   public LibraryNewRequest.ServerDTO getServer() {
      return this.Server;
   }

   @Generated
   public void setTitle(final String Title) {
      this.Title = Title;
   }

   @Generated
   public void setDescription(final String Description) {
      this.Description = Description;
   }

   @Generated
   public void setDate(final String Date) {
      this.Date = Date;
   }

   @Generated
   public void setEvent(final String Event) {
      this.Event = Event;
   }

   @Generated
   public void setItem(final LibraryNewRequest.ItemDTO Item) {
      this.Item = Item;
   }

   @Generated
   public void setServer(final LibraryNewRequest.ServerDTO Server) {
      this.Server = Server;
   }

   @Generated
   @Override
   public boolean equals(final Object o) {
      if (o == this) {
         return true;
      } else if (!(o instanceof LibraryNewRequest other)) {
         return false;
      } else if (!other.canEqual(this)) {
         return false;
      } else {
         Object this$Title = this.getTitle();
         Object other$Title = other.getTitle();
         if (this$Title == null ? other$Title == null : this$Title.equals(other$Title)) {
            Object this$Description = this.getDescription();
            Object other$Description = other.getDescription();
            if (this$Description == null ? other$Description == null : this$Description.equals(other$Description)) {
               Object this$Date = this.getDate();
               Object other$Date = other.getDate();
               if (this$Date == null ? other$Date == null : this$Date.equals(other$Date)) {
                  Object this$Event = this.getEvent();
                  Object other$Event = other.getEvent();
                  if (this$Event == null ? other$Event == null : this$Event.equals(other$Event)) {
                     Object this$Item = this.getItem();
                     Object other$Item = other.getItem();
                     if (this$Item == null ? other$Item == null : this$Item.equals(other$Item)) {
                        Object this$Server = this.getServer();
                        Object other$Server = other.getServer();
                        return this$Server == null ? other$Server == null : this$Server.equals(other$Server);
                     } else {
                        return false;
                     }
                  } else {
                     return false;
                  }
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
      return other instanceof LibraryNewRequest;
   }

   @Generated
   @Override
   public int hashCode() {
      int PRIME = 59;
      int result = 1;
      Object $Title = this.getTitle();
      result = result * 59 + ($Title == null ? 43 : $Title.hashCode());
      Object $Description = this.getDescription();
      result = result * 59 + ($Description == null ? 43 : $Description.hashCode());
      Object $Date = this.getDate();
      result = result * 59 + ($Date == null ? 43 : $Date.hashCode());
      Object $Event = this.getEvent();
      result = result * 59 + ($Event == null ? 43 : $Event.hashCode());
      Object $Item = this.getItem();
      result = result * 59 + ($Item == null ? 43 : $Item.hashCode());
      Object $Server = this.getServer();
      return result * 59 + ($Server == null ? 43 : $Server.hashCode());
   }

   @Generated
   @Override
   public String toString() {
      return "LibraryNewRequest(Title="
         + this.getTitle()
         + ", Description="
         + this.getDescription()
         + ", Date="
         + this.getDate()
         + ", Event="
         + this.getEvent()
         + ", Item="
         + this.getItem()
         + ", Server="
         + this.getServer()
         + ")";
   }

   public static class ItemDTO {
      private String Name;
      private String ServerId;
      private String Id;
      private String DateCreated;
      private String Container;
      private String SortName;
      private List<JSONObject> ExternalUrls;
      private String Path;
      private String Overview;
      private List<?> Taglines;
      private List<String> Genres;
      private Long RunTimeTicks;
      private Long Size = 0L;
      private String FileName;
      private Integer Bitrate;
      private Integer ProductionYear;
      private List<?> RemoteTrailers;
      private LibraryNewRequest.ItemDTO.ProviderIdsDTO ProviderIds;
      private Boolean IsFolder;
      private String ParentId;
      private String Type;
      private List<?> Studios;
      private List<LibraryNewRequest.ItemDTO.GenreItemsDTO> GenreItems;
      private List<?> TagItems;
      private Double PrimaryImageAspectRatio;
      private LibraryNewRequest.ItemDTO.ImageTagsDTO ImageTags;
      private List<?> BackdropImageTags;
      private String MediaType;
      private Integer Width;
      private Integer Height;
      private List<JSONObject> MediaSources;
      private List<JSONObject> MediaStreams;
      private String SeriesName;
      private String SeriesId;
      private String SeasonId;
      private String OriginalTitle;
      private String PremiereDate;
      private String SeasonName;
      private Integer IndexNumber;
      private Integer ParentIndexNumber;

      @Generated
      public String getName() {
         return this.Name;
      }

      @Generated
      public String getServerId() {
         return this.ServerId;
      }

      @Generated
      public String getId() {
         return this.Id;
      }

      @Generated
      public String getDateCreated() {
         return this.DateCreated;
      }

      @Generated
      public String getContainer() {
         return this.Container;
      }

      @Generated
      public String getSortName() {
         return this.SortName;
      }

      @Generated
      public List<JSONObject> getExternalUrls() {
         return this.ExternalUrls;
      }

      @Generated
      public String getPath() {
         return this.Path;
      }

      @Generated
      public String getOverview() {
         return this.Overview;
      }

      @Generated
      public List<?> getTaglines() {
         return this.Taglines;
      }

      @Generated
      public List<String> getGenres() {
         return this.Genres;
      }

      @Generated
      public Long getRunTimeTicks() {
         return this.RunTimeTicks;
      }

      @Generated
      public Long getSize() {
         return this.Size;
      }

      @Generated
      public String getFileName() {
         return this.FileName;
      }

      @Generated
      public Integer getBitrate() {
         return this.Bitrate;
      }

      @Generated
      public Integer getProductionYear() {
         return this.ProductionYear;
      }

      @Generated
      public List<?> getRemoteTrailers() {
         return this.RemoteTrailers;
      }

      @Generated
      public LibraryNewRequest.ItemDTO.ProviderIdsDTO getProviderIds() {
         return this.ProviderIds;
      }

      @Generated
      public Boolean getIsFolder() {
         return this.IsFolder;
      }

      @Generated
      public String getParentId() {
         return this.ParentId;
      }

      @Generated
      public String getType() {
         return this.Type;
      }

      @Generated
      public List<?> getStudios() {
         return this.Studios;
      }

      @Generated
      public List<LibraryNewRequest.ItemDTO.GenreItemsDTO> getGenreItems() {
         return this.GenreItems;
      }

      @Generated
      public List<?> getTagItems() {
         return this.TagItems;
      }

      @Generated
      public Double getPrimaryImageAspectRatio() {
         return this.PrimaryImageAspectRatio;
      }

      @Generated
      public LibraryNewRequest.ItemDTO.ImageTagsDTO getImageTags() {
         return this.ImageTags;
      }

      @Generated
      public List<?> getBackdropImageTags() {
         return this.BackdropImageTags;
      }

      @Generated
      public String getMediaType() {
         return this.MediaType;
      }

      @Generated
      public Integer getWidth() {
         return this.Width;
      }

      @Generated
      public Integer getHeight() {
         return this.Height;
      }

      @Generated
      public List<JSONObject> getMediaSources() {
         return this.MediaSources;
      }

      @Generated
      public List<JSONObject> getMediaStreams() {
         return this.MediaStreams;
      }

      @Generated
      public String getSeriesName() {
         return this.SeriesName;
      }

      @Generated
      public String getSeriesId() {
         return this.SeriesId;
      }

      @Generated
      public String getSeasonId() {
         return this.SeasonId;
      }

      @Generated
      public String getOriginalTitle() {
         return this.OriginalTitle;
      }

      @Generated
      public String getPremiereDate() {
         return this.PremiereDate;
      }

      @Generated
      public String getSeasonName() {
         return this.SeasonName;
      }

      @Generated
      public Integer getIndexNumber() {
         return this.IndexNumber;
      }

      @Generated
      public Integer getParentIndexNumber() {
         return this.ParentIndexNumber;
      }

      @Generated
      public void setName(final String Name) {
         this.Name = Name;
      }

      @Generated
      public void setServerId(final String ServerId) {
         this.ServerId = ServerId;
      }

      @Generated
      public void setId(final String Id) {
         this.Id = Id;
      }

      @Generated
      public void setDateCreated(final String DateCreated) {
         this.DateCreated = DateCreated;
      }

      @Generated
      public void setContainer(final String Container) {
         this.Container = Container;
      }

      @Generated
      public void setSortName(final String SortName) {
         this.SortName = SortName;
      }

      @Generated
      public void setExternalUrls(final List<JSONObject> ExternalUrls) {
         this.ExternalUrls = ExternalUrls;
      }

      @Generated
      public void setPath(final String Path) {
         this.Path = Path;
      }

      @Generated
      public void setOverview(final String Overview) {
         this.Overview = Overview;
      }

      @Generated
      public void setTaglines(final List<?> Taglines) {
         this.Taglines = Taglines;
      }

      @Generated
      public void setGenres(final List<String> Genres) {
         this.Genres = Genres;
      }

      @Generated
      public void setRunTimeTicks(final Long RunTimeTicks) {
         this.RunTimeTicks = RunTimeTicks;
      }

      @Generated
      public void setSize(final Long Size) {
         this.Size = Size;
      }

      @Generated
      public void setFileName(final String FileName) {
         this.FileName = FileName;
      }

      @Generated
      public void setBitrate(final Integer Bitrate) {
         this.Bitrate = Bitrate;
      }

      @Generated
      public void setProductionYear(final Integer ProductionYear) {
         this.ProductionYear = ProductionYear;
      }

      @Generated
      public void setRemoteTrailers(final List<?> RemoteTrailers) {
         this.RemoteTrailers = RemoteTrailers;
      }

      @Generated
      public void setProviderIds(final LibraryNewRequest.ItemDTO.ProviderIdsDTO ProviderIds) {
         this.ProviderIds = ProviderIds;
      }

      @Generated
      public void setIsFolder(final Boolean IsFolder) {
         this.IsFolder = IsFolder;
      }

      @Generated
      public void setParentId(final String ParentId) {
         this.ParentId = ParentId;
      }

      @Generated
      public void setType(final String Type) {
         this.Type = Type;
      }

      @Generated
      public void setStudios(final List<?> Studios) {
         this.Studios = Studios;
      }

      @Generated
      public void setGenreItems(final List<LibraryNewRequest.ItemDTO.GenreItemsDTO> GenreItems) {
         this.GenreItems = GenreItems;
      }

      @Generated
      public void setTagItems(final List<?> TagItems) {
         this.TagItems = TagItems;
      }

      @Generated
      public void setPrimaryImageAspectRatio(final Double PrimaryImageAspectRatio) {
         this.PrimaryImageAspectRatio = PrimaryImageAspectRatio;
      }

      @Generated
      public void setImageTags(final LibraryNewRequest.ItemDTO.ImageTagsDTO ImageTags) {
         this.ImageTags = ImageTags;
      }

      @Generated
      public void setBackdropImageTags(final List<?> BackdropImageTags) {
         this.BackdropImageTags = BackdropImageTags;
      }

      @Generated
      public void setMediaType(final String MediaType) {
         this.MediaType = MediaType;
      }

      @Generated
      public void setWidth(final Integer Width) {
         this.Width = Width;
      }

      @Generated
      public void setHeight(final Integer Height) {
         this.Height = Height;
      }

      @Generated
      public void setMediaSources(final List<JSONObject> MediaSources) {
         this.MediaSources = MediaSources;
      }

      @Generated
      public void setMediaStreams(final List<JSONObject> MediaStreams) {
         this.MediaStreams = MediaStreams;
      }

      @Generated
      public void setSeriesName(final String SeriesName) {
         this.SeriesName = SeriesName;
      }

      @Generated
      public void setSeriesId(final String SeriesId) {
         this.SeriesId = SeriesId;
      }

      @Generated
      public void setSeasonId(final String SeasonId) {
         this.SeasonId = SeasonId;
      }

      @Generated
      public void setOriginalTitle(final String OriginalTitle) {
         this.OriginalTitle = OriginalTitle;
      }

      @Generated
      public void setPremiereDate(final String PremiereDate) {
         this.PremiereDate = PremiereDate;
      }

      @Generated
      public void setSeasonName(final String SeasonName) {
         this.SeasonName = SeasonName;
      }

      @Generated
      public void setIndexNumber(final Integer IndexNumber) {
         this.IndexNumber = IndexNumber;
      }

      @Generated
      public void setParentIndexNumber(final Integer ParentIndexNumber) {
         this.ParentIndexNumber = ParentIndexNumber;
      }

      @Generated
      @Override
      public boolean equals(final Object o) {
         if (o == this) {
            return true;
         } else if (!(o instanceof LibraryNewRequest.ItemDTO other)) {
            return false;
         } else if (!other.canEqual(this)) {
            return false;
         } else {
            Object this$RunTimeTicks = this.getRunTimeTicks();
            Object other$RunTimeTicks = other.getRunTimeTicks();
            if (this$RunTimeTicks == null ? other$RunTimeTicks == null : this$RunTimeTicks.equals(other$RunTimeTicks)) {
               Object this$Size = this.getSize();
               Object other$Size = other.getSize();
               if (this$Size == null ? other$Size == null : this$Size.equals(other$Size)) {
                  Object this$Bitrate = this.getBitrate();
                  Object other$Bitrate = other.getBitrate();
                  if (this$Bitrate == null ? other$Bitrate == null : this$Bitrate.equals(other$Bitrate)) {
                     Object this$ProductionYear = this.getProductionYear();
                     Object other$ProductionYear = other.getProductionYear();
                     if (this$ProductionYear == null ? other$ProductionYear == null : this$ProductionYear.equals(other$ProductionYear)) {
                        Object this$IsFolder = this.getIsFolder();
                        Object other$IsFolder = other.getIsFolder();
                        if (this$IsFolder == null ? other$IsFolder == null : this$IsFolder.equals(other$IsFolder)) {
                           Object this$PrimaryImageAspectRatio = this.getPrimaryImageAspectRatio();
                           Object other$PrimaryImageAspectRatio = other.getPrimaryImageAspectRatio();
                           if (this$PrimaryImageAspectRatio == null
                              ? other$PrimaryImageAspectRatio == null
                              : this$PrimaryImageAspectRatio.equals(other$PrimaryImageAspectRatio)) {
                              Object this$Width = this.getWidth();
                              Object other$Width = other.getWidth();
                              if (this$Width == null ? other$Width == null : this$Width.equals(other$Width)) {
                                 Object this$Height = this.getHeight();
                                 Object other$Height = other.getHeight();
                                 if (this$Height == null ? other$Height == null : this$Height.equals(other$Height)) {
                                    Object this$IndexNumber = this.getIndexNumber();
                                    Object other$IndexNumber = other.getIndexNumber();
                                    if (this$IndexNumber == null ? other$IndexNumber == null : this$IndexNumber.equals(other$IndexNumber)) {
                                       Object this$ParentIndexNumber = this.getParentIndexNumber();
                                       Object other$ParentIndexNumber = other.getParentIndexNumber();
                                       if (this$ParentIndexNumber == null
                                          ? other$ParentIndexNumber == null
                                          : this$ParentIndexNumber.equals(other$ParentIndexNumber)) {
                                          Object this$Name = this.getName();
                                          Object other$Name = other.getName();
                                          if (this$Name == null ? other$Name == null : this$Name.equals(other$Name)) {
                                             Object this$ServerId = this.getServerId();
                                             Object other$ServerId = other.getServerId();
                                             if (this$ServerId == null ? other$ServerId == null : this$ServerId.equals(other$ServerId)) {
                                                Object this$Id = this.getId();
                                                Object other$Id = other.getId();
                                                if (this$Id == null ? other$Id == null : this$Id.equals(other$Id)) {
                                                   Object this$DateCreated = this.getDateCreated();
                                                   Object other$DateCreated = other.getDateCreated();
                                                   if (this$DateCreated == null ? other$DateCreated == null : this$DateCreated.equals(other$DateCreated)) {
                                                      Object this$Container = this.getContainer();
                                                      Object other$Container = other.getContainer();
                                                      if (this$Container == null ? other$Container == null : this$Container.equals(other$Container)) {
                                                         Object this$SortName = this.getSortName();
                                                         Object other$SortName = other.getSortName();
                                                         if (this$SortName == null ? other$SortName == null : this$SortName.equals(other$SortName)) {
                                                            Object this$ExternalUrls = this.getExternalUrls();
                                                            Object other$ExternalUrls = other.getExternalUrls();
                                                            if (this$ExternalUrls == null
                                                               ? other$ExternalUrls == null
                                                               : this$ExternalUrls.equals(other$ExternalUrls)) {
                                                               Object this$Path = this.getPath();
                                                               Object other$Path = other.getPath();
                                                               if (this$Path == null ? other$Path == null : this$Path.equals(other$Path)) {
                                                                  Object this$Overview = this.getOverview();
                                                                  Object other$Overview = other.getOverview();
                                                                  if (this$Overview == null ? other$Overview == null : this$Overview.equals(other$Overview)) {
                                                                     Object this$Taglines = this.getTaglines();
                                                                     Object other$Taglines = other.getTaglines();
                                                                     if (this$Taglines == null ? other$Taglines == null : this$Taglines.equals(other$Taglines)) {
                                                                        Object this$Genres = this.getGenres();
                                                                        Object other$Genres = other.getGenres();
                                                                        if (this$Genres == null ? other$Genres == null : this$Genres.equals(other$Genres)) {
                                                                           Object this$FileName = this.getFileName();
                                                                           Object other$FileName = other.getFileName();
                                                                           if (this$FileName == null
                                                                              ? other$FileName == null
                                                                              : this$FileName.equals(other$FileName)) {
                                                                              Object this$RemoteTrailers = this.getRemoteTrailers();
                                                                              Object other$RemoteTrailers = other.getRemoteTrailers();
                                                                              if (this$RemoteTrailers == null
                                                                                 ? other$RemoteTrailers == null
                                                                                 : this$RemoteTrailers.equals(other$RemoteTrailers)) {
                                                                                 Object this$ProviderIds = this.getProviderIds();
                                                                                 Object other$ProviderIds = other.getProviderIds();
                                                                                 if (this$ProviderIds == null
                                                                                    ? other$ProviderIds == null
                                                                                    : this$ProviderIds.equals(other$ProviderIds)) {
                                                                                    Object this$ParentId = this.getParentId();
                                                                                    Object other$ParentId = other.getParentId();
                                                                                    if (this$ParentId == null
                                                                                       ? other$ParentId == null
                                                                                       : this$ParentId.equals(other$ParentId)) {
                                                                                       Object this$Type = this.getType();
                                                                                       Object other$Type = other.getType();
                                                                                       if (this$Type == null
                                                                                          ? other$Type == null
                                                                                          : this$Type.equals(other$Type)) {
                                                                                          Object this$Studios = this.getStudios();
                                                                                          Object other$Studios = other.getStudios();
                                                                                          if (this$Studios == null
                                                                                             ? other$Studios == null
                                                                                             : this$Studios.equals(other$Studios)) {
                                                                                             Object this$GenreItems = this.getGenreItems();
                                                                                             Object other$GenreItems = other.getGenreItems();
                                                                                             if (this$GenreItems == null
                                                                                                ? other$GenreItems == null
                                                                                                : this$GenreItems.equals(other$GenreItems)) {
                                                                                                Object this$TagItems = this.getTagItems();
                                                                                                Object other$TagItems = other.getTagItems();
                                                                                                if (this$TagItems == null
                                                                                                   ? other$TagItems == null
                                                                                                   : this$TagItems.equals(other$TagItems)) {
                                                                                                   Object this$ImageTags = this.getImageTags();
                                                                                                   Object other$ImageTags = other.getImageTags();
                                                                                                   if (this$ImageTags == null
                                                                                                      ? other$ImageTags == null
                                                                                                      : this$ImageTags.equals(other$ImageTags)) {
                                                                                                      Object this$BackdropImageTags = this.getBackdropImageTags();
                                                                                                      Object other$BackdropImageTags = other.getBackdropImageTags();
                                                                                                      if (this$BackdropImageTags == null
                                                                                                         ? other$BackdropImageTags == null
                                                                                                         : this$BackdropImageTags.equals(
                                                                                                            other$BackdropImageTags
                                                                                                         )) {
                                                                                                         Object this$MediaType = this.getMediaType();
                                                                                                         Object other$MediaType = other.getMediaType();
                                                                                                         if (this$MediaType == null
                                                                                                            ? other$MediaType == null
                                                                                                            : this$MediaType.equals(other$MediaType)) {
                                                                                                            Object this$MediaSources = this.getMediaSources();
                                                                                                            Object other$MediaSources = other.getMediaSources();
                                                                                                            if (this$MediaSources == null
                                                                                                               ? other$MediaSources == null
                                                                                                               : this$MediaSources.equals(other$MediaSources)) {
                                                                                                               Object this$MediaStreams = this.getMediaStreams();
                                                                                                               Object other$MediaStreams = other.getMediaStreams();
                                                                                                               if (this$MediaStreams == null
                                                                                                                  ? other$MediaStreams == null
                                                                                                                  : this$MediaStreams.equals(other$MediaStreams)
                                                                                                                  )
                                                                                                                {
                                                                                                                  Object this$SeriesName = this.getSeriesName();
                                                                                                                  Object other$SeriesName = other.getSeriesName();
                                                                                                                  if (this$SeriesName == null
                                                                                                                     ? other$SeriesName == null
                                                                                                                     : this$SeriesName.equals(other$SeriesName)
                                                                                                                     )
                                                                                                                   {
                                                                                                                     Object this$SeriesId = this.getSeriesId();
                                                                                                                     Object other$SeriesId = other.getSeriesId();
                                                                                                                     if (this$SeriesId == null
                                                                                                                        ? other$SeriesId == null
                                                                                                                        : this$SeriesId.equals(other$SeriesId)) {
                                                                                                                        Object this$SeasonId = this.getSeasonId();
                                                                                                                        Object other$SeasonId = other.getSeasonId();
                                                                                                                        if (this$SeasonId == null
                                                                                                                           ? other$SeasonId == null
                                                                                                                           : this$SeasonId.equals(
                                                                                                                              other$SeasonId
                                                                                                                           )) {
                                                                                                                           Object this$OriginalTitle = this.getOriginalTitle();
                                                                                                                           Object other$OriginalTitle = other.getOriginalTitle();
                                                                                                                           if (this$OriginalTitle == null
                                                                                                                              ? other$OriginalTitle == null
                                                                                                                              : this$OriginalTitle.equals(
                                                                                                                                 other$OriginalTitle
                                                                                                                              )) {
                                                                                                                              Object this$PremiereDate = this.getPremiereDate();
                                                                                                                              Object other$PremiereDate = other.getPremiereDate();
                                                                                                                              if (this$PremiereDate == null
                                                                                                                                 ? other$PremiereDate == null
                                                                                                                                 : this$PremiereDate.equals(
                                                                                                                                    other$PremiereDate
                                                                                                                                 )) {
                                                                                                                                 Object this$SeasonName = this.getSeasonName();
                                                                                                                                 Object other$SeasonName = other.getSeasonName();
                                                                                                                                 return this$SeasonName == null
                                                                                                                                    ? other$SeasonName == null
                                                                                                                                    : this$SeasonName.equals(
                                                                                                                                       other$SeasonName
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
         }
      }

      @Generated
      protected boolean canEqual(final Object other) {
         return other instanceof LibraryNewRequest.ItemDTO;
      }

      @Generated
      @Override
      public int hashCode() {
         int PRIME = 59;
         int result = 1;
         Object $RunTimeTicks = this.getRunTimeTicks();
         result = result * 59 + ($RunTimeTicks == null ? 43 : $RunTimeTicks.hashCode());
         Object $Size = this.getSize();
         result = result * 59 + ($Size == null ? 43 : $Size.hashCode());
         Object $Bitrate = this.getBitrate();
         result = result * 59 + ($Bitrate == null ? 43 : $Bitrate.hashCode());
         Object $ProductionYear = this.getProductionYear();
         result = result * 59 + ($ProductionYear == null ? 43 : $ProductionYear.hashCode());
         Object $IsFolder = this.getIsFolder();
         result = result * 59 + ($IsFolder == null ? 43 : $IsFolder.hashCode());
         Object $PrimaryImageAspectRatio = this.getPrimaryImageAspectRatio();
         result = result * 59 + ($PrimaryImageAspectRatio == null ? 43 : $PrimaryImageAspectRatio.hashCode());
         Object $Width = this.getWidth();
         result = result * 59 + ($Width == null ? 43 : $Width.hashCode());
         Object $Height = this.getHeight();
         result = result * 59 + ($Height == null ? 43 : $Height.hashCode());
         Object $IndexNumber = this.getIndexNumber();
         result = result * 59 + ($IndexNumber == null ? 43 : $IndexNumber.hashCode());
         Object $ParentIndexNumber = this.getParentIndexNumber();
         result = result * 59 + ($ParentIndexNumber == null ? 43 : $ParentIndexNumber.hashCode());
         Object $Name = this.getName();
         result = result * 59 + ($Name == null ? 43 : $Name.hashCode());
         Object $ServerId = this.getServerId();
         result = result * 59 + ($ServerId == null ? 43 : $ServerId.hashCode());
         Object $Id = this.getId();
         result = result * 59 + ($Id == null ? 43 : $Id.hashCode());
         Object $DateCreated = this.getDateCreated();
         result = result * 59 + ($DateCreated == null ? 43 : $DateCreated.hashCode());
         Object $Container = this.getContainer();
         result = result * 59 + ($Container == null ? 43 : $Container.hashCode());
         Object $SortName = this.getSortName();
         result = result * 59 + ($SortName == null ? 43 : $SortName.hashCode());
         Object $ExternalUrls = this.getExternalUrls();
         result = result * 59 + ($ExternalUrls == null ? 43 : $ExternalUrls.hashCode());
         Object $Path = this.getPath();
         result = result * 59 + ($Path == null ? 43 : $Path.hashCode());
         Object $Overview = this.getOverview();
         result = result * 59 + ($Overview == null ? 43 : $Overview.hashCode());
         Object $Taglines = this.getTaglines();
         result = result * 59 + ($Taglines == null ? 43 : $Taglines.hashCode());
         Object $Genres = this.getGenres();
         result = result * 59 + ($Genres == null ? 43 : $Genres.hashCode());
         Object $FileName = this.getFileName();
         result = result * 59 + ($FileName == null ? 43 : $FileName.hashCode());
         Object $RemoteTrailers = this.getRemoteTrailers();
         result = result * 59 + ($RemoteTrailers == null ? 43 : $RemoteTrailers.hashCode());
         Object $ProviderIds = this.getProviderIds();
         result = result * 59 + ($ProviderIds == null ? 43 : $ProviderIds.hashCode());
         Object $ParentId = this.getParentId();
         result = result * 59 + ($ParentId == null ? 43 : $ParentId.hashCode());
         Object $Type = this.getType();
         result = result * 59 + ($Type == null ? 43 : $Type.hashCode());
         Object $Studios = this.getStudios();
         result = result * 59 + ($Studios == null ? 43 : $Studios.hashCode());
         Object $GenreItems = this.getGenreItems();
         result = result * 59 + ($GenreItems == null ? 43 : $GenreItems.hashCode());
         Object $TagItems = this.getTagItems();
         result = result * 59 + ($TagItems == null ? 43 : $TagItems.hashCode());
         Object $ImageTags = this.getImageTags();
         result = result * 59 + ($ImageTags == null ? 43 : $ImageTags.hashCode());
         Object $BackdropImageTags = this.getBackdropImageTags();
         result = result * 59 + ($BackdropImageTags == null ? 43 : $BackdropImageTags.hashCode());
         Object $MediaType = this.getMediaType();
         result = result * 59 + ($MediaType == null ? 43 : $MediaType.hashCode());
         Object $MediaSources = this.getMediaSources();
         result = result * 59 + ($MediaSources == null ? 43 : $MediaSources.hashCode());
         Object $MediaStreams = this.getMediaStreams();
         result = result * 59 + ($MediaStreams == null ? 43 : $MediaStreams.hashCode());
         Object $SeriesName = this.getSeriesName();
         result = result * 59 + ($SeriesName == null ? 43 : $SeriesName.hashCode());
         Object $SeriesId = this.getSeriesId();
         result = result * 59 + ($SeriesId == null ? 43 : $SeriesId.hashCode());
         Object $SeasonId = this.getSeasonId();
         result = result * 59 + ($SeasonId == null ? 43 : $SeasonId.hashCode());
         Object $OriginalTitle = this.getOriginalTitle();
         result = result * 59 + ($OriginalTitle == null ? 43 : $OriginalTitle.hashCode());
         Object $PremiereDate = this.getPremiereDate();
         result = result * 59 + ($PremiereDate == null ? 43 : $PremiereDate.hashCode());
         Object $SeasonName = this.getSeasonName();
         return result * 59 + ($SeasonName == null ? 43 : $SeasonName.hashCode());
      }

      @Generated
      @Override
      public String toString() {
         return "LibraryNewRequest.ItemDTO(Name="
            + this.getName()
            + ", ServerId="
            + this.getServerId()
            + ", Id="
            + this.getId()
            + ", DateCreated="
            + this.getDateCreated()
            + ", Container="
            + this.getContainer()
            + ", SortName="
            + this.getSortName()
            + ", ExternalUrls="
            + this.getExternalUrls()
            + ", Path="
            + this.getPath()
            + ", Overview="
            + this.getOverview()
            + ", Taglines="
            + this.getTaglines()
            + ", Genres="
            + this.getGenres()
            + ", RunTimeTicks="
            + this.getRunTimeTicks()
            + ", Size="
            + this.getSize()
            + ", FileName="
            + this.getFileName()
            + ", Bitrate="
            + this.getBitrate()
            + ", ProductionYear="
            + this.getProductionYear()
            + ", RemoteTrailers="
            + this.getRemoteTrailers()
            + ", ProviderIds="
            + this.getProviderIds()
            + ", IsFolder="
            + this.getIsFolder()
            + ", ParentId="
            + this.getParentId()
            + ", Type="
            + this.getType()
            + ", Studios="
            + this.getStudios()
            + ", GenreItems="
            + this.getGenreItems()
            + ", TagItems="
            + this.getTagItems()
            + ", PrimaryImageAspectRatio="
            + this.getPrimaryImageAspectRatio()
            + ", ImageTags="
            + this.getImageTags()
            + ", BackdropImageTags="
            + this.getBackdropImageTags()
            + ", MediaType="
            + this.getMediaType()
            + ", Width="
            + this.getWidth()
            + ", Height="
            + this.getHeight()
            + ", MediaSources="
            + this.getMediaSources()
            + ", MediaStreams="
            + this.getMediaStreams()
            + ", SeriesName="
            + this.getSeriesName()
            + ", SeriesId="
            + this.getSeriesId()
            + ", SeasonId="
            + this.getSeasonId()
            + ", OriginalTitle="
            + this.getOriginalTitle()
            + ", PremiereDate="
            + this.getPremiereDate()
            + ", SeasonName="
            + this.getSeasonName()
            + ", IndexNumber="
            + this.getIndexNumber()
            + ", ParentIndexNumber="
            + this.getParentIndexNumber()
            + ")";
      }

      public static class GenreItemsDTO {
         private String Name;
         private Integer Id;

         @Generated
         public String getName() {
            return this.Name;
         }

         @Generated
         public Integer getId() {
            return this.Id;
         }

         @Generated
         public void setName(final String Name) {
            this.Name = Name;
         }

         @Generated
         public void setId(final Integer Id) {
            this.Id = Id;
         }

         @Generated
         @Override
         public boolean equals(final Object o) {
            if (o == this) {
               return true;
            } else if (!(o instanceof LibraryNewRequest.ItemDTO.GenreItemsDTO other)) {
               return false;
            } else if (!other.canEqual(this)) {
               return false;
            } else {
               Object this$Id = this.getId();
               Object other$Id = other.getId();
               if (this$Id == null ? other$Id == null : this$Id.equals(other$Id)) {
                  Object this$Name = this.getName();
                  Object other$Name = other.getName();
                  return this$Name == null ? other$Name == null : this$Name.equals(other$Name);
               } else {
                  return false;
               }
            }
         }

         @Generated
         protected boolean canEqual(final Object other) {
            return other instanceof LibraryNewRequest.ItemDTO.GenreItemsDTO;
         }

         @Generated
         @Override
         public int hashCode() {
            int PRIME = 59;
            int result = 1;
            Object $Id = this.getId();
            result = result * 59 + ($Id == null ? 43 : $Id.hashCode());
            Object $Name = this.getName();
            return result * 59 + ($Name == null ? 43 : $Name.hashCode());
         }

         @Generated
         @Override
         public String toString() {
            return "LibraryNewRequest.ItemDTO.GenreItemsDTO(Name=" + this.getName() + ", Id=" + this.getId() + ")";
         }
      }

      public static class ImageTagsDTO {
         private String Primary;

         @Generated
         public String getPrimary() {
            return this.Primary;
         }

         @Generated
         public void setPrimary(final String Primary) {
            this.Primary = Primary;
         }

         @Generated
         @Override
         public boolean equals(final Object o) {
            if (o == this) {
               return true;
            } else if (!(o instanceof LibraryNewRequest.ItemDTO.ImageTagsDTO other)) {
               return false;
            } else if (!other.canEqual(this)) {
               return false;
            } else {
               Object this$Primary = this.getPrimary();
               Object other$Primary = other.getPrimary();
               return this$Primary == null ? other$Primary == null : this$Primary.equals(other$Primary);
            }
         }

         @Generated
         protected boolean canEqual(final Object other) {
            return other instanceof LibraryNewRequest.ItemDTO.ImageTagsDTO;
         }

         @Generated
         @Override
         public int hashCode() {
            int PRIME = 59;
            int result = 1;
            Object $Primary = this.getPrimary();
            return result * 59 + ($Primary == null ? 43 : $Primary.hashCode());
         }

         @Generated
         @Override
         public String toString() {
            return "LibraryNewRequest.ItemDTO.ImageTagsDTO(Primary=" + this.getPrimary() + ")";
         }
      }

      public static class ProviderIdsDTO {
         @Generated
         @Override
         public boolean equals(final Object o) {
            if (o == this) {
               return true;
            } else {
               return !(o instanceof LibraryNewRequest.ItemDTO.ProviderIdsDTO other) ? false : other.canEqual(this);
            }
         }

         @Generated
         protected boolean canEqual(final Object other) {
            return other instanceof LibraryNewRequest.ItemDTO.ProviderIdsDTO;
         }

         @Generated
         @Override
         public int hashCode() {
            int result = 1;
            return 1;
         }

         @Generated
         @Override
         public String toString() {
            return "LibraryNewRequest.ItemDTO.ProviderIdsDTO()";
         }
      }
   }

   public static class ServerDTO {
      private String Name;
      private String Id;
      private String Version;

      @Generated
      public String getName() {
         return this.Name;
      }

      @Generated
      public String getId() {
         return this.Id;
      }

      @Generated
      public String getVersion() {
         return this.Version;
      }

      @Generated
      public void setName(final String Name) {
         this.Name = Name;
      }

      @Generated
      public void setId(final String Id) {
         this.Id = Id;
      }

      @Generated
      public void setVersion(final String Version) {
         this.Version = Version;
      }

      @Generated
      @Override
      public boolean equals(final Object o) {
         if (o == this) {
            return true;
         } else if (!(o instanceof LibraryNewRequest.ServerDTO other)) {
            return false;
         } else if (!other.canEqual(this)) {
            return false;
         } else {
            Object this$Name = this.getName();
            Object other$Name = other.getName();
            if (this$Name == null ? other$Name == null : this$Name.equals(other$Name)) {
               Object this$Id = this.getId();
               Object other$Id = other.getId();
               if (this$Id == null ? other$Id == null : this$Id.equals(other$Id)) {
                  Object this$Version = this.getVersion();
                  Object other$Version = other.getVersion();
                  return this$Version == null ? other$Version == null : this$Version.equals(other$Version);
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
         return other instanceof LibraryNewRequest.ServerDTO;
      }

      @Generated
      @Override
      public int hashCode() {
         int PRIME = 59;
         int result = 1;
         Object $Name = this.getName();
         result = result * 59 + ($Name == null ? 43 : $Name.hashCode());
         Object $Id = this.getId();
         result = result * 59 + ($Id == null ? 43 : $Id.hashCode());
         Object $Version = this.getVersion();
         return result * 59 + ($Version == null ? 43 : $Version.hashCode());
      }

      @Generated
      @Override
      public String toString() {
         return "LibraryNewRequest.ServerDTO(Name=" + this.getName() + ", Id=" + this.getId() + ", Version=" + this.getVersion() + ")";
      }
   }
}
