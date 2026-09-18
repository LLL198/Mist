package com.una.embyhub.model.dto.response.emby;

import java.io.Serializable;
import java.util.List;
import lombok.Generated;

public class EmbyTmdbResponse implements Serializable {
   private String Name;
   private String ServerId;
   private String Id;
   private Long RunTimeTicks;
   private EmbyTmdbResponse.ProviderIdsDTO ProviderIds;
   private Boolean IsFolder;
   private String Type;
   private EmbyTmdbResponse.ImageTagsDTO ImageTags;
   private List<String> BackdropImageTags;
   private String MediaType;
   private List<?> AirDays;

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
   public Long getRunTimeTicks() {
      return this.RunTimeTicks;
   }

   @Generated
   public EmbyTmdbResponse.ProviderIdsDTO getProviderIds() {
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
   public EmbyTmdbResponse.ImageTagsDTO getImageTags() {
      return this.ImageTags;
   }

   @Generated
   public List<String> getBackdropImageTags() {
      return this.BackdropImageTags;
   }

   @Generated
   public String getMediaType() {
      return this.MediaType;
   }

   @Generated
   public List<?> getAirDays() {
      return this.AirDays;
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
   public void setRunTimeTicks(final Long RunTimeTicks) {
      this.RunTimeTicks = RunTimeTicks;
   }

   @Generated
   public void setProviderIds(final EmbyTmdbResponse.ProviderIdsDTO ProviderIds) {
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
   public void setImageTags(final EmbyTmdbResponse.ImageTagsDTO ImageTags) {
      this.ImageTags = ImageTags;
   }

   @Generated
   public void setBackdropImageTags(final List<String> BackdropImageTags) {
      this.BackdropImageTags = BackdropImageTags;
   }

   @Generated
   public void setMediaType(final String MediaType) {
      this.MediaType = MediaType;
   }

   @Generated
   public void setAirDays(final List<?> AirDays) {
      this.AirDays = AirDays;
   }

   @Generated
   @Override
   public boolean equals(final Object o) {
      if (o == this) {
         return true;
      } else if (!(o instanceof EmbyTmdbResponse other)) {
         return false;
      } else if (!other.canEqual(this)) {
         return false;
      } else {
         Object this$RunTimeTicks = this.getRunTimeTicks();
         Object other$RunTimeTicks = other.getRunTimeTicks();
         if (this$RunTimeTicks == null ? other$RunTimeTicks == null : this$RunTimeTicks.equals(other$RunTimeTicks)) {
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
                        Object this$ProviderIds = this.getProviderIds();
                        Object other$ProviderIds = other.getProviderIds();
                        if (this$ProviderIds == null ? other$ProviderIds == null : this$ProviderIds.equals(other$ProviderIds)) {
                           Object this$Type = this.getType();
                           Object other$Type = other.getType();
                           if (this$Type == null ? other$Type == null : this$Type.equals(other$Type)) {
                              Object this$ImageTags = this.getImageTags();
                              Object other$ImageTags = other.getImageTags();
                              if (this$ImageTags == null ? other$ImageTags == null : this$ImageTags.equals(other$ImageTags)) {
                                 Object this$BackdropImageTags = this.getBackdropImageTags();
                                 Object other$BackdropImageTags = other.getBackdropImageTags();
                                 if (this$BackdropImageTags == null ? other$BackdropImageTags == null : this$BackdropImageTags.equals(other$BackdropImageTags)) {
                                    Object this$MediaType = this.getMediaType();
                                    Object other$MediaType = other.getMediaType();
                                    if (this$MediaType == null ? other$MediaType == null : this$MediaType.equals(other$MediaType)) {
                                       Object this$AirDays = this.getAirDays();
                                       Object other$AirDays = other.getAirDays();
                                       return this$AirDays == null ? other$AirDays == null : this$AirDays.equals(other$AirDays);
                                    } else {
                                       return false;
                                    }
                                 } else {
                                    return false;
                                 }
                              } else {
                                 return false;
                              }
                           } else {
                              return false;
                           }
                        } else {
                           return false;
                        }
                     } else {
                        return false;
                     }
                  } else {
                     return false;
                  }
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
      return other instanceof EmbyTmdbResponse;
   }

   @Generated
   @Override
   public int hashCode() {
      int PRIME = 59;
      int result = 1;
      Object $RunTimeTicks = this.getRunTimeTicks();
      result = result * 59 + ($RunTimeTicks == null ? 43 : $RunTimeTicks.hashCode());
      Object $IsFolder = this.getIsFolder();
      result = result * 59 + ($IsFolder == null ? 43 : $IsFolder.hashCode());
      Object $Name = this.getName();
      result = result * 59 + ($Name == null ? 43 : $Name.hashCode());
      Object $ServerId = this.getServerId();
      result = result * 59 + ($ServerId == null ? 43 : $ServerId.hashCode());
      Object $Id = this.getId();
      result = result * 59 + ($Id == null ? 43 : $Id.hashCode());
      Object $ProviderIds = this.getProviderIds();
      result = result * 59 + ($ProviderIds == null ? 43 : $ProviderIds.hashCode());
      Object $Type = this.getType();
      result = result * 59 + ($Type == null ? 43 : $Type.hashCode());
      Object $ImageTags = this.getImageTags();
      result = result * 59 + ($ImageTags == null ? 43 : $ImageTags.hashCode());
      Object $BackdropImageTags = this.getBackdropImageTags();
      result = result * 59 + ($BackdropImageTags == null ? 43 : $BackdropImageTags.hashCode());
      Object $MediaType = this.getMediaType();
      result = result * 59 + ($MediaType == null ? 43 : $MediaType.hashCode());
      Object $AirDays = this.getAirDays();
      return result * 59 + ($AirDays == null ? 43 : $AirDays.hashCode());
   }

   @Generated
   @Override
   public String toString() {
      return "EmbyTmdbResponse(Name="
         + this.getName()
         + ", ServerId="
         + this.getServerId()
         + ", Id="
         + this.getId()
         + ", RunTimeTicks="
         + this.getRunTimeTicks()
         + ", ProviderIds="
         + this.getProviderIds()
         + ", IsFolder="
         + this.getIsFolder()
         + ", Type="
         + this.getType()
         + ", ImageTags="
         + this.getImageTags()
         + ", BackdropImageTags="
         + this.getBackdropImageTags()
         + ", MediaType="
         + this.getMediaType()
         + ", AirDays="
         + this.getAirDays()
         + ")";
   }

   public static class ImageTagsDTO implements Serializable {
      private String Primary;
      private String Logo;
      private String Thumb;
      private String Banner;
      private String Disc;

      @Generated
      public String getPrimary() {
         return this.Primary;
      }

      @Generated
      public String getLogo() {
         return this.Logo;
      }

      @Generated
      public String getThumb() {
         return this.Thumb;
      }

      @Generated
      public String getBanner() {
         return this.Banner;
      }

      @Generated
      public String getDisc() {
         return this.Disc;
      }

      @Generated
      public void setPrimary(final String Primary) {
         this.Primary = Primary;
      }

      @Generated
      public void setLogo(final String Logo) {
         this.Logo = Logo;
      }

      @Generated
      public void setThumb(final String Thumb) {
         this.Thumb = Thumb;
      }

      @Generated
      public void setBanner(final String Banner) {
         this.Banner = Banner;
      }

      @Generated
      public void setDisc(final String Disc) {
         this.Disc = Disc;
      }

      @Generated
      @Override
      public boolean equals(final Object o) {
         if (o == this) {
            return true;
         } else if (!(o instanceof EmbyTmdbResponse.ImageTagsDTO other)) {
            return false;
         } else if (!other.canEqual(this)) {
            return false;
         } else {
            Object this$Primary = this.getPrimary();
            Object other$Primary = other.getPrimary();
            if (this$Primary == null ? other$Primary == null : this$Primary.equals(other$Primary)) {
               Object this$Logo = this.getLogo();
               Object other$Logo = other.getLogo();
               if (this$Logo == null ? other$Logo == null : this$Logo.equals(other$Logo)) {
                  Object this$Thumb = this.getThumb();
                  Object other$Thumb = other.getThumb();
                  if (this$Thumb == null ? other$Thumb == null : this$Thumb.equals(other$Thumb)) {
                     Object this$Banner = this.getBanner();
                     Object other$Banner = other.getBanner();
                     if (this$Banner == null ? other$Banner == null : this$Banner.equals(other$Banner)) {
                        Object this$Disc = this.getDisc();
                        Object other$Disc = other.getDisc();
                        return this$Disc == null ? other$Disc == null : this$Disc.equals(other$Disc);
                     } else {
                        return false;
                     }
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
         return other instanceof EmbyTmdbResponse.ImageTagsDTO;
      }

      @Generated
      @Override
      public int hashCode() {
         int PRIME = 59;
         int result = 1;
         Object $Primary = this.getPrimary();
         result = result * 59 + ($Primary == null ? 43 : $Primary.hashCode());
         Object $Logo = this.getLogo();
         result = result * 59 + ($Logo == null ? 43 : $Logo.hashCode());
         Object $Thumb = this.getThumb();
         result = result * 59 + ($Thumb == null ? 43 : $Thumb.hashCode());
         Object $Banner = this.getBanner();
         result = result * 59 + ($Banner == null ? 43 : $Banner.hashCode());
         Object $Disc = this.getDisc();
         return result * 59 + ($Disc == null ? 43 : $Disc.hashCode());
      }

      @Generated
      @Override
      public String toString() {
         return "EmbyTmdbResponse.ImageTagsDTO(Primary="
            + this.getPrimary()
            + ", Logo="
            + this.getLogo()
            + ", Thumb="
            + this.getThumb()
            + ", Banner="
            + this.getBanner()
            + ", Disc="
            + this.getDisc()
            + ")";
      }
   }

   public static class ProviderIdsDTO implements Serializable {
      private String Tmdb;
      private String Imdb;
      private String Tvdb;

      @Generated
      public String getTmdb() {
         return this.Tmdb;
      }

      @Generated
      public String getImdb() {
         return this.Imdb;
      }

      @Generated
      public String getTvdb() {
         return this.Tvdb;
      }

      @Generated
      public void setTmdb(final String Tmdb) {
         this.Tmdb = Tmdb;
      }

      @Generated
      public void setImdb(final String Imdb) {
         this.Imdb = Imdb;
      }

      @Generated
      public void setTvdb(final String Tvdb) {
         this.Tvdb = Tvdb;
      }

      @Generated
      @Override
      public boolean equals(final Object o) {
         if (o == this) {
            return true;
         } else if (!(o instanceof EmbyTmdbResponse.ProviderIdsDTO other)) {
            return false;
         } else if (!other.canEqual(this)) {
            return false;
         } else {
            Object this$Tmdb = this.getTmdb();
            Object other$Tmdb = other.getTmdb();
            if (this$Tmdb == null ? other$Tmdb == null : this$Tmdb.equals(other$Tmdb)) {
               Object this$Imdb = this.getImdb();
               Object other$Imdb = other.getImdb();
               if (this$Imdb == null ? other$Imdb == null : this$Imdb.equals(other$Imdb)) {
                  Object this$Tvdb = this.getTvdb();
                  Object other$Tvdb = other.getTvdb();
                  return this$Tvdb == null ? other$Tvdb == null : this$Tvdb.equals(other$Tvdb);
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
         return other instanceof EmbyTmdbResponse.ProviderIdsDTO;
      }

      @Generated
      @Override
      public int hashCode() {
         int PRIME = 59;
         int result = 1;
         Object $Tmdb = this.getTmdb();
         result = result * 59 + ($Tmdb == null ? 43 : $Tmdb.hashCode());
         Object $Imdb = this.getImdb();
         result = result * 59 + ($Imdb == null ? 43 : $Imdb.hashCode());
         Object $Tvdb = this.getTvdb();
         return result * 59 + ($Tvdb == null ? 43 : $Tvdb.hashCode());
      }

      @Generated
      @Override
      public String toString() {
         return "EmbyTmdbResponse.ProviderIdsDTO(Tmdb=" + this.getTmdb() + ", Imdb=" + this.getImdb() + ", Tvdb=" + this.getTvdb() + ")";
      }
   }
}
