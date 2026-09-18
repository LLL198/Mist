package com.una.embyhub.model.dto.request.emby;

import com.alibaba.fastjson2.JSONObject;
import java.io.Serializable;
import java.util.List;
import lombok.Generated;

public class LibraryDeletedRequest implements Serializable {
   private String Title;
   private String Description;
   private String Date;
   private String Event;
   private LibraryDeletedRequest.ItemDTO Item;
   private LibraryDeletedRequest.ServerDTO Server;

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
   public LibraryDeletedRequest.ItemDTO getItem() {
      return this.Item;
   }

   @Generated
   public LibraryDeletedRequest.ServerDTO getServer() {
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
   public void setItem(final LibraryDeletedRequest.ItemDTO Item) {
      this.Item = Item;
   }

   @Generated
   public void setServer(final LibraryDeletedRequest.ServerDTO Server) {
      this.Server = Server;
   }

   @Generated
   @Override
   public boolean equals(final Object o) {
      if (o == this) {
         return true;
      } else if (!(o instanceof LibraryDeletedRequest other)) {
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
      return other instanceof LibraryDeletedRequest;
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
      return "LibraryDeletedRequest(Title="
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
      private List<?> Genres;
      private Long RunTimeTicks;
      private Long Size;
      private String FileName;
      private Integer Bitrate;
      private Integer ProductionYear;
      private List<?> RemoteTrailers;
      private LibraryDeletedRequest.ItemDTO.ProviderIdsDTO ProviderIds;
      private Boolean IsFolder;
      private String Type;
      private List<?> Studios;
      private List<?> GenreItems;
      private List<?> TagItems;
      private Double PrimaryImageAspectRatio;
      private LibraryDeletedRequest.ItemDTO.ImageTagsDTO ImageTags;
      private List<?> BackdropImageTags;
      private String MediaType;
      private Integer Width;
      private Integer Height;

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
      public List<?> getGenres() {
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
      public LibraryDeletedRequest.ItemDTO.ProviderIdsDTO getProviderIds() {
         return this.ProviderIds;
      }

      @Generated
      public Boolean getIsFolder() {
         return this.IsFolder;
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
      public List<?> getGenreItems() {
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
      public LibraryDeletedRequest.ItemDTO.ImageTagsDTO getImageTags() {
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
      public void setGenres(final List<?> Genres) {
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
      public void setProviderIds(final LibraryDeletedRequest.ItemDTO.ProviderIdsDTO ProviderIds) {
         this.ProviderIds = ProviderIds;
      }

      @Generated
      public void setIsFolder(final Boolean IsFolder) {
         this.IsFolder = IsFolder;
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
      public void setGenreItems(final List<?> GenreItems) {
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
      public void setImageTags(final LibraryDeletedRequest.ItemDTO.ImageTagsDTO ImageTags) {
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
      @Override
      public boolean equals(final Object o) {
         if (o == this) {
            return true;
         } else if (!(o instanceof LibraryDeletedRequest.ItemDTO other)) {
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
                                                      if (this$ExternalUrls == null ? other$ExternalUrls == null : this$ExternalUrls.equals(other$ExternalUrls)
                                                         )
                                                       {
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
                                                                     if (this$FileName == null ? other$FileName == null : this$FileName.equals(other$FileName)) {
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
                                                                              Object this$Type = this.getType();
                                                                              Object other$Type = other.getType();
                                                                              if (this$Type == null ? other$Type == null : this$Type.equals(other$Type)) {
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
                                                                                                : this$BackdropImageTags.equals(other$BackdropImageTags)) {
                                                                                                Object this$MediaType = this.getMediaType();
                                                                                                Object other$MediaType = other.getMediaType();
                                                                                                return this$MediaType == null
                                                                                                   ? other$MediaType == null
                                                                                                   : this$MediaType.equals(other$MediaType);
                                                                                             } else {
                                                                                                return false;
                                                                                             }
                                                                                          } else {
                                                                                             return false;
                                                                                          }
                                                                                       } else {
                                                                                          return false;
                                                                                       }
                                                                                    } else {
                                                                                       return false;
                                                                                    }
                                                                                 } else {
                                                                                    return false;
                                                                                 }
                                                                              } else {
                                                                                 return false;
                                                                              }
                                                                           } else {
                                                                              return false;
                                                                           }
                                                                        } else {
                                                                           return false;
                                                                        }
                                                                     } else {
                                                                        return false;
                                                                     }
                                                                  } else {
                                                                     return false;
                                                                  }
                                                               } else {
                                                                  return false;
                                                               }
                                                            } else {
                                                               return false;
                                                            }
                                                         } else {
                                                            return false;
                                                         }
                                                      } else {
                                                         return false;
                                                      }
                                                   } else {
                                                      return false;
                                                   }
                                                } else {
                                                   return false;
                                                }
                                             } else {
                                                return false;
                                             }
                                          } else {
                                             return false;
                                          }
                                       } else {
                                          return false;
                                       }
                                    } else {
                                       return false;
                                    }
                                 } else {
                                    return false;
                                 }
                              } else {
                                 return false;
                              }
                           } else {
                              return false;
                           }
                        } else {
                           return false;
                        }
                     } else {
                        return false;
                     }
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
         return other instanceof LibraryDeletedRequest.ItemDTO;
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
         return result * 59 + ($MediaType == null ? 43 : $MediaType.hashCode());
      }

      @Generated
      @Override
      public String toString() {
         return "LibraryDeletedRequest.ItemDTO(Name="
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
            + ")";
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
            } else if (!(o instanceof LibraryDeletedRequest.ItemDTO.ImageTagsDTO other)) {
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
            return other instanceof LibraryDeletedRequest.ItemDTO.ImageTagsDTO;
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
            return "LibraryDeletedRequest.ItemDTO.ImageTagsDTO(Primary=" + this.getPrimary() + ")";
         }
      }

      public static class ProviderIdsDTO {
         @Generated
         @Override
         public boolean equals(final Object o) {
            if (o == this) {
               return true;
            } else {
               return !(o instanceof LibraryDeletedRequest.ItemDTO.ProviderIdsDTO other) ? false : other.canEqual(this);
            }
         }

         @Generated
         protected boolean canEqual(final Object other) {
            return other instanceof LibraryDeletedRequest.ItemDTO.ProviderIdsDTO;
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
            return "LibraryDeletedRequest.ItemDTO.ProviderIdsDTO()";
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
         } else if (!(o instanceof LibraryDeletedRequest.ServerDTO other)) {
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
         return other instanceof LibraryDeletedRequest.ServerDTO;
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
         return "LibraryDeletedRequest.ServerDTO(Name=" + this.getName() + ", Id=" + this.getId() + ", Version=" + this.getVersion() + ")";
      }
   }
}
