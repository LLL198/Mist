package com.una.embyhub.model.dto.response.emby;

import java.util.List;
import lombok.Generated;

public class GetEpisodesByIdResponse {
   private List<GetEpisodesByIdResponse.ItemsDTO> Items;
   private Integer TotalRecordCount;

   @Generated
   public List<GetEpisodesByIdResponse.ItemsDTO> getItems() {
      return this.Items;
   }

   @Generated
   public Integer getTotalRecordCount() {
      return this.TotalRecordCount;
   }

   @Generated
   public void setItems(final List<GetEpisodesByIdResponse.ItemsDTO> Items) {
      this.Items = Items;
   }

   @Generated
   public void setTotalRecordCount(final Integer TotalRecordCount) {
      this.TotalRecordCount = TotalRecordCount;
   }

   @Generated
   @Override
   public boolean equals(final Object o) {
      if (o == this) {
         return true;
      } else if (!(o instanceof GetEpisodesByIdResponse other)) {
         return false;
      } else if (!other.canEqual(this)) {
         return false;
      } else {
         Object this$TotalRecordCount = this.getTotalRecordCount();
         Object other$TotalRecordCount = other.getTotalRecordCount();
         if (this$TotalRecordCount == null ? other$TotalRecordCount == null : this$TotalRecordCount.equals(other$TotalRecordCount)) {
            Object this$Items = this.getItems();
            Object other$Items = other.getItems();
            return this$Items == null ? other$Items == null : this$Items.equals(other$Items);
         } else {
            return false;
         }
      }
   }

   @Generated
   protected boolean canEqual(final Object other) {
      return other instanceof GetEpisodesByIdResponse;
   }

   @Generated
   @Override
   public int hashCode() {
      int PRIME = 59;
      int result = 1;
      Object $TotalRecordCount = this.getTotalRecordCount();
      result = result * 59 + ($TotalRecordCount == null ? 43 : $TotalRecordCount.hashCode());
      Object $Items = this.getItems();
      return result * 59 + ($Items == null ? 43 : $Items.hashCode());
   }

   @Generated
   @Override
   public String toString() {
      return "GetEpisodesByIdResponse(Items=" + this.getItems() + ", TotalRecordCount=" + this.getTotalRecordCount() + ")";
   }

   public static class ItemsDTO {
      private String Name;
      private String ServerId;
      private String Id;
      private String PremiereDate;
      private Long RunTimeTicks;
      private Integer IndexNumber;
      private Integer ParentIndexNumber;
      private Boolean IsFolder;
      private String Type;
      private String ParentLogoItemId;
      private String ParentBackdropItemId;
      private List<String> ParentBackdropImageTags;
      private String SeriesName;
      private String SeriesId;
      private String SeasonId;
      private String SeriesPrimaryImageTag;
      private String SeasonName;
      private GetEpisodesByIdResponse.ItemsDTO.ImageTagsDTO ImageTags;
      private List<?> BackdropImageTags;
      private String ParentLogoImageTag;
      private String ParentThumbItemId;
      private String ParentThumbImageTag;
      private String MediaType;
      private String embyEpisodeUrl;

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
      public String getPremiereDate() {
         return this.PremiereDate;
      }

      @Generated
      public Long getRunTimeTicks() {
         return this.RunTimeTicks;
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
      public Boolean getIsFolder() {
         return this.IsFolder;
      }

      @Generated
      public String getType() {
         return this.Type;
      }

      @Generated
      public String getParentLogoItemId() {
         return this.ParentLogoItemId;
      }

      @Generated
      public String getParentBackdropItemId() {
         return this.ParentBackdropItemId;
      }

      @Generated
      public List<String> getParentBackdropImageTags() {
         return this.ParentBackdropImageTags;
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
      public String getSeriesPrimaryImageTag() {
         return this.SeriesPrimaryImageTag;
      }

      @Generated
      public String getSeasonName() {
         return this.SeasonName;
      }

      @Generated
      public GetEpisodesByIdResponse.ItemsDTO.ImageTagsDTO getImageTags() {
         return this.ImageTags;
      }

      @Generated
      public List<?> getBackdropImageTags() {
         return this.BackdropImageTags;
      }

      @Generated
      public String getParentLogoImageTag() {
         return this.ParentLogoImageTag;
      }

      @Generated
      public String getParentThumbItemId() {
         return this.ParentThumbItemId;
      }

      @Generated
      public String getParentThumbImageTag() {
         return this.ParentThumbImageTag;
      }

      @Generated
      public String getMediaType() {
         return this.MediaType;
      }

      @Generated
      public String getEmbyEpisodeUrl() {
         return this.embyEpisodeUrl;
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
      public void setPremiereDate(final String PremiereDate) {
         this.PremiereDate = PremiereDate;
      }

      @Generated
      public void setRunTimeTicks(final Long RunTimeTicks) {
         this.RunTimeTicks = RunTimeTicks;
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
      public void setIsFolder(final Boolean IsFolder) {
         this.IsFolder = IsFolder;
      }

      @Generated
      public void setType(final String Type) {
         this.Type = Type;
      }

      @Generated
      public void setParentLogoItemId(final String ParentLogoItemId) {
         this.ParentLogoItemId = ParentLogoItemId;
      }

      @Generated
      public void setParentBackdropItemId(final String ParentBackdropItemId) {
         this.ParentBackdropItemId = ParentBackdropItemId;
      }

      @Generated
      public void setParentBackdropImageTags(final List<String> ParentBackdropImageTags) {
         this.ParentBackdropImageTags = ParentBackdropImageTags;
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
      public void setSeriesPrimaryImageTag(final String SeriesPrimaryImageTag) {
         this.SeriesPrimaryImageTag = SeriesPrimaryImageTag;
      }

      @Generated
      public void setSeasonName(final String SeasonName) {
         this.SeasonName = SeasonName;
      }

      @Generated
      public void setImageTags(final GetEpisodesByIdResponse.ItemsDTO.ImageTagsDTO ImageTags) {
         this.ImageTags = ImageTags;
      }

      @Generated
      public void setBackdropImageTags(final List<?> BackdropImageTags) {
         this.BackdropImageTags = BackdropImageTags;
      }

      @Generated
      public void setParentLogoImageTag(final String ParentLogoImageTag) {
         this.ParentLogoImageTag = ParentLogoImageTag;
      }

      @Generated
      public void setParentThumbItemId(final String ParentThumbItemId) {
         this.ParentThumbItemId = ParentThumbItemId;
      }

      @Generated
      public void setParentThumbImageTag(final String ParentThumbImageTag) {
         this.ParentThumbImageTag = ParentThumbImageTag;
      }

      @Generated
      public void setMediaType(final String MediaType) {
         this.MediaType = MediaType;
      }

      @Generated
      public void setEmbyEpisodeUrl(final String embyEpisodeUrl) {
         this.embyEpisodeUrl = embyEpisodeUrl;
      }

      @Generated
      @Override
      public boolean equals(final Object o) {
         if (o == this) {
            return true;
         } else if (!(o instanceof GetEpisodesByIdResponse.ItemsDTO other)) {
            return false;
         } else if (!other.canEqual(this)) {
            return false;
         } else {
            Object this$RunTimeTicks = this.getRunTimeTicks();
            Object other$RunTimeTicks = other.getRunTimeTicks();
            if (this$RunTimeTicks == null ? other$RunTimeTicks == null : this$RunTimeTicks.equals(other$RunTimeTicks)) {
               Object this$IndexNumber = this.getIndexNumber();
               Object other$IndexNumber = other.getIndexNumber();
               if (this$IndexNumber == null ? other$IndexNumber == null : this$IndexNumber.equals(other$IndexNumber)) {
                  Object this$ParentIndexNumber = this.getParentIndexNumber();
                  Object other$ParentIndexNumber = other.getParentIndexNumber();
                  if (this$ParentIndexNumber == null ? other$ParentIndexNumber == null : this$ParentIndexNumber.equals(other$ParentIndexNumber)) {
                     Object this$IsFolder = this.getIsFolder();
                     Object other$IsFolder = other.getIsFolder();
                     if (this$IsFolder == null ? other$IsFolder == null : this$IsFolder.equals(other$IsFolder)) {
                        Object this$Name = this.getName();
                        Object other$Name = other.getName();
                        if (this$Name == null ? other$Name == null : this$Name.equals(other$Name)) {
                           Object this$ServerId = this.getServerId();
                           Object other$ServerId = other.getServerId();
                           if (this$ServerId == null ? other$ServerId == null : this$ServerId.equals(other$ServerId)) {
                              Object this$Id = this.getId();
                              Object other$Id = other.getId();
                              if (this$Id == null ? other$Id == null : this$Id.equals(other$Id)) {
                                 Object this$PremiereDate = this.getPremiereDate();
                                 Object other$PremiereDate = other.getPremiereDate();
                                 if (this$PremiereDate == null ? other$PremiereDate == null : this$PremiereDate.equals(other$PremiereDate)) {
                                    Object this$Type = this.getType();
                                    Object other$Type = other.getType();
                                    if (this$Type == null ? other$Type == null : this$Type.equals(other$Type)) {
                                       Object this$ParentLogoItemId = this.getParentLogoItemId();
                                       Object other$ParentLogoItemId = other.getParentLogoItemId();
                                       if (this$ParentLogoItemId == null
                                          ? other$ParentLogoItemId == null
                                          : this$ParentLogoItemId.equals(other$ParentLogoItemId)) {
                                          Object this$ParentBackdropItemId = this.getParentBackdropItemId();
                                          Object other$ParentBackdropItemId = other.getParentBackdropItemId();
                                          if (this$ParentBackdropItemId == null
                                             ? other$ParentBackdropItemId == null
                                             : this$ParentBackdropItemId.equals(other$ParentBackdropItemId)) {
                                             Object this$ParentBackdropImageTags = this.getParentBackdropImageTags();
                                             Object other$ParentBackdropImageTags = other.getParentBackdropImageTags();
                                             if (this$ParentBackdropImageTags == null
                                                ? other$ParentBackdropImageTags == null
                                                : this$ParentBackdropImageTags.equals(other$ParentBackdropImageTags)) {
                                                Object this$SeriesName = this.getSeriesName();
                                                Object other$SeriesName = other.getSeriesName();
                                                if (this$SeriesName == null ? other$SeriesName == null : this$SeriesName.equals(other$SeriesName)) {
                                                   Object this$SeriesId = this.getSeriesId();
                                                   Object other$SeriesId = other.getSeriesId();
                                                   if (this$SeriesId == null ? other$SeriesId == null : this$SeriesId.equals(other$SeriesId)) {
                                                      Object this$SeasonId = this.getSeasonId();
                                                      Object other$SeasonId = other.getSeasonId();
                                                      if (this$SeasonId == null ? other$SeasonId == null : this$SeasonId.equals(other$SeasonId)) {
                                                         Object this$SeriesPrimaryImageTag = this.getSeriesPrimaryImageTag();
                                                         Object other$SeriesPrimaryImageTag = other.getSeriesPrimaryImageTag();
                                                         if (this$SeriesPrimaryImageTag == null
                                                            ? other$SeriesPrimaryImageTag == null
                                                            : this$SeriesPrimaryImageTag.equals(other$SeriesPrimaryImageTag)) {
                                                            Object this$SeasonName = this.getSeasonName();
                                                            Object other$SeasonName = other.getSeasonName();
                                                            if (this$SeasonName == null ? other$SeasonName == null : this$SeasonName.equals(other$SeasonName)) {
                                                               Object this$ImageTags = this.getImageTags();
                                                               Object other$ImageTags = other.getImageTags();
                                                               if (this$ImageTags == null ? other$ImageTags == null : this$ImageTags.equals(other$ImageTags)) {
                                                                  Object this$BackdropImageTags = this.getBackdropImageTags();
                                                                  Object other$BackdropImageTags = other.getBackdropImageTags();
                                                                  if (this$BackdropImageTags == null
                                                                     ? other$BackdropImageTags == null
                                                                     : this$BackdropImageTags.equals(other$BackdropImageTags)) {
                                                                     Object this$ParentLogoImageTag = this.getParentLogoImageTag();
                                                                     Object other$ParentLogoImageTag = other.getParentLogoImageTag();
                                                                     if (this$ParentLogoImageTag == null
                                                                        ? other$ParentLogoImageTag == null
                                                                        : this$ParentLogoImageTag.equals(other$ParentLogoImageTag)) {
                                                                        Object this$ParentThumbItemId = this.getParentThumbItemId();
                                                                        Object other$ParentThumbItemId = other.getParentThumbItemId();
                                                                        if (this$ParentThumbItemId == null
                                                                           ? other$ParentThumbItemId == null
                                                                           : this$ParentThumbItemId.equals(other$ParentThumbItemId)) {
                                                                           Object this$ParentThumbImageTag = this.getParentThumbImageTag();
                                                                           Object other$ParentThumbImageTag = other.getParentThumbImageTag();
                                                                           if (this$ParentThumbImageTag == null
                                                                              ? other$ParentThumbImageTag == null
                                                                              : this$ParentThumbImageTag.equals(other$ParentThumbImageTag)) {
                                                                              Object this$MediaType = this.getMediaType();
                                                                              Object other$MediaType = other.getMediaType();
                                                                              if (this$MediaType == null
                                                                                 ? other$MediaType == null
                                                                                 : this$MediaType.equals(other$MediaType)) {
                                                                                 Object this$embyEpisodeUrl = this.getEmbyEpisodeUrl();
                                                                                 Object other$embyEpisodeUrl = other.getEmbyEpisodeUrl();
                                                                                 return this$embyEpisodeUrl == null
                                                                                    ? other$embyEpisodeUrl == null
                                                                                    : this$embyEpisodeUrl.equals(other$embyEpisodeUrl);
                                                                              } else {
                                                                                 return false;
                                                                              }
                                                                           } else {
                                                                              return false;
                                                                           }
                                                                        } else {
                                                                           return false;
                                                                        }
                                                                     } else {
                                                                        return false;
                                                                     }
                                                                  } else {
                                                                     return false;
                                                                  }
                                                               } else {
                                                                  return false;
                                                               }
                                                            } else {
                                                               return false;
                                                            }
                                                         } else {
                                                            return false;
                                                         }
                                                      } else {
                                                         return false;
                                                      }
                                                   } else {
                                                      return false;
                                                   }
                                                } else {
                                                   return false;
                                                }
                                             } else {
                                                return false;
                                             }
                                          } else {
                                             return false;
                                          }
                                       } else {
                                          return false;
                                       }
                                    } else {
                                       return false;
                                    }
                                 } else {
                                    return false;
                                 }
                              } else {
                                 return false;
                              }
                           } else {
                              return false;
                           }
                        } else {
                           return false;
                        }
                     } else {
                        return false;
                     }
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
         return other instanceof GetEpisodesByIdResponse.ItemsDTO;
      }

      @Generated
      @Override
      public int hashCode() {
         int PRIME = 59;
         int result = 1;
         Object $RunTimeTicks = this.getRunTimeTicks();
         result = result * 59 + ($RunTimeTicks == null ? 43 : $RunTimeTicks.hashCode());
         Object $IndexNumber = this.getIndexNumber();
         result = result * 59 + ($IndexNumber == null ? 43 : $IndexNumber.hashCode());
         Object $ParentIndexNumber = this.getParentIndexNumber();
         result = result * 59 + ($ParentIndexNumber == null ? 43 : $ParentIndexNumber.hashCode());
         Object $IsFolder = this.getIsFolder();
         result = result * 59 + ($IsFolder == null ? 43 : $IsFolder.hashCode());
         Object $Name = this.getName();
         result = result * 59 + ($Name == null ? 43 : $Name.hashCode());
         Object $ServerId = this.getServerId();
         result = result * 59 + ($ServerId == null ? 43 : $ServerId.hashCode());
         Object $Id = this.getId();
         result = result * 59 + ($Id == null ? 43 : $Id.hashCode());
         Object $PremiereDate = this.getPremiereDate();
         result = result * 59 + ($PremiereDate == null ? 43 : $PremiereDate.hashCode());
         Object $Type = this.getType();
         result = result * 59 + ($Type == null ? 43 : $Type.hashCode());
         Object $ParentLogoItemId = this.getParentLogoItemId();
         result = result * 59 + ($ParentLogoItemId == null ? 43 : $ParentLogoItemId.hashCode());
         Object $ParentBackdropItemId = this.getParentBackdropItemId();
         result = result * 59 + ($ParentBackdropItemId == null ? 43 : $ParentBackdropItemId.hashCode());
         Object $ParentBackdropImageTags = this.getParentBackdropImageTags();
         result = result * 59 + ($ParentBackdropImageTags == null ? 43 : $ParentBackdropImageTags.hashCode());
         Object $SeriesName = this.getSeriesName();
         result = result * 59 + ($SeriesName == null ? 43 : $SeriesName.hashCode());
         Object $SeriesId = this.getSeriesId();
         result = result * 59 + ($SeriesId == null ? 43 : $SeriesId.hashCode());
         Object $SeasonId = this.getSeasonId();
         result = result * 59 + ($SeasonId == null ? 43 : $SeasonId.hashCode());
         Object $SeriesPrimaryImageTag = this.getSeriesPrimaryImageTag();
         result = result * 59 + ($SeriesPrimaryImageTag == null ? 43 : $SeriesPrimaryImageTag.hashCode());
         Object $SeasonName = this.getSeasonName();
         result = result * 59 + ($SeasonName == null ? 43 : $SeasonName.hashCode());
         Object $ImageTags = this.getImageTags();
         result = result * 59 + ($ImageTags == null ? 43 : $ImageTags.hashCode());
         Object $BackdropImageTags = this.getBackdropImageTags();
         result = result * 59 + ($BackdropImageTags == null ? 43 : $BackdropImageTags.hashCode());
         Object $ParentLogoImageTag = this.getParentLogoImageTag();
         result = result * 59 + ($ParentLogoImageTag == null ? 43 : $ParentLogoImageTag.hashCode());
         Object $ParentThumbItemId = this.getParentThumbItemId();
         result = result * 59 + ($ParentThumbItemId == null ? 43 : $ParentThumbItemId.hashCode());
         Object $ParentThumbImageTag = this.getParentThumbImageTag();
         result = result * 59 + ($ParentThumbImageTag == null ? 43 : $ParentThumbImageTag.hashCode());
         Object $MediaType = this.getMediaType();
         result = result * 59 + ($MediaType == null ? 43 : $MediaType.hashCode());
         Object $embyEpisodeUrl = this.getEmbyEpisodeUrl();
         return result * 59 + ($embyEpisodeUrl == null ? 43 : $embyEpisodeUrl.hashCode());
      }

      @Generated
      @Override
      public String toString() {
         return "GetEpisodesByIdResponse.ItemsDTO(Name="
            + this.getName()
            + ", ServerId="
            + this.getServerId()
            + ", Id="
            + this.getId()
            + ", PremiereDate="
            + this.getPremiereDate()
            + ", RunTimeTicks="
            + this.getRunTimeTicks()
            + ", IndexNumber="
            + this.getIndexNumber()
            + ", ParentIndexNumber="
            + this.getParentIndexNumber()
            + ", IsFolder="
            + this.getIsFolder()
            + ", Type="
            + this.getType()
            + ", ParentLogoItemId="
            + this.getParentLogoItemId()
            + ", ParentBackdropItemId="
            + this.getParentBackdropItemId()
            + ", ParentBackdropImageTags="
            + this.getParentBackdropImageTags()
            + ", SeriesName="
            + this.getSeriesName()
            + ", SeriesId="
            + this.getSeriesId()
            + ", SeasonId="
            + this.getSeasonId()
            + ", SeriesPrimaryImageTag="
            + this.getSeriesPrimaryImageTag()
            + ", SeasonName="
            + this.getSeasonName()
            + ", ImageTags="
            + this.getImageTags()
            + ", BackdropImageTags="
            + this.getBackdropImageTags()
            + ", ParentLogoImageTag="
            + this.getParentLogoImageTag()
            + ", ParentThumbItemId="
            + this.getParentThumbItemId()
            + ", ParentThumbImageTag="
            + this.getParentThumbImageTag()
            + ", MediaType="
            + this.getMediaType()
            + ", embyEpisodeUrl="
            + this.getEmbyEpisodeUrl()
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
            } else if (!(o instanceof GetEpisodesByIdResponse.ItemsDTO.ImageTagsDTO other)) {
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
            return other instanceof GetEpisodesByIdResponse.ItemsDTO.ImageTagsDTO;
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
            return "GetEpisodesByIdResponse.ItemsDTO.ImageTagsDTO(Primary=" + this.getPrimary() + ")";
         }
      }
   }
}
