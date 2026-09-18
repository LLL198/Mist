package com.una.embyhub.model.dto.response.nullbr;

import com.alibaba.fastjson2.annotation.JSONField;
import java.util.ArrayList;
import java.util.List;
import lombok.Generated;

public class MovieListResponse {
   @JSONField(
      name = "115"
   )
   private List<MovieListResponse.MovieList115DTO> movieList115DTOList;
   private Integer id;
   private Integer page;
   @JSONField(
      name = "total_page"
   )
   private Integer totalPage;
   @JSONField(
      name = "media_type"
   )
   private String mediaType;
   private List<MovieListResponse.CreditsDTO> creditsDTOList = new ArrayList<>();

   @Generated
   public List<MovieListResponse.MovieList115DTO> getMovieList115DTOList() {
      return this.movieList115DTOList;
   }

   @Generated
   public Integer getId() {
      return this.id;
   }

   @Generated
   public Integer getPage() {
      return this.page;
   }

   @Generated
   public Integer getTotalPage() {
      return this.totalPage;
   }

   @Generated
   public String getMediaType() {
      return this.mediaType;
   }

   @Generated
   public List<MovieListResponse.CreditsDTO> getCreditsDTOList() {
      return this.creditsDTOList;
   }

   @Generated
   public void setMovieList115DTOList(final List<MovieListResponse.MovieList115DTO> movieList115DTOList) {
      this.movieList115DTOList = movieList115DTOList;
   }

   @Generated
   public void setId(final Integer id) {
      this.id = id;
   }

   @Generated
   public void setPage(final Integer page) {
      this.page = page;
   }

   @Generated
   public void setTotalPage(final Integer totalPage) {
      this.totalPage = totalPage;
   }

   @Generated
   public void setMediaType(final String mediaType) {
      this.mediaType = mediaType;
   }

   @Generated
   public void setCreditsDTOList(final List<MovieListResponse.CreditsDTO> creditsDTOList) {
      this.creditsDTOList = creditsDTOList;
   }

   @Generated
   @Override
   public boolean equals(final Object o) {
      if (o == this) {
         return true;
      } else if (!(o instanceof MovieListResponse other)) {
         return false;
      } else if (!other.canEqual(this)) {
         return false;
      } else {
         Object this$id = this.getId();
         Object other$id = other.getId();
         if (this$id == null ? other$id == null : this$id.equals(other$id)) {
            Object this$page = this.getPage();
            Object other$page = other.getPage();
            if (this$page == null ? other$page == null : this$page.equals(other$page)) {
               Object this$totalPage = this.getTotalPage();
               Object other$totalPage = other.getTotalPage();
               if (this$totalPage == null ? other$totalPage == null : this$totalPage.equals(other$totalPage)) {
                  Object this$movieList115DTOList = this.getMovieList115DTOList();
                  Object other$movieList115DTOList = other.getMovieList115DTOList();
                  if (this$movieList115DTOList == null ? other$movieList115DTOList == null : this$movieList115DTOList.equals(other$movieList115DTOList)) {
                     Object this$mediaType = this.getMediaType();
                     Object other$mediaType = other.getMediaType();
                     if (this$mediaType == null ? other$mediaType == null : this$mediaType.equals(other$mediaType)) {
                        Object this$creditsDTOList = this.getCreditsDTOList();
                        Object other$creditsDTOList = other.getCreditsDTOList();
                        return this$creditsDTOList == null ? other$creditsDTOList == null : this$creditsDTOList.equals(other$creditsDTOList);
                     } else {
                        return false;
                     }
                  } else {
                     return false;
                  }
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
      return other instanceof MovieListResponse;
   }

   @Generated
   @Override
   public int hashCode() {
      int PRIME = 59;
      int result = 1;
      Object $id = this.getId();
      result = result * 59 + ($id == null ? 43 : $id.hashCode());
      Object $page = this.getPage();
      result = result * 59 + ($page == null ? 43 : $page.hashCode());
      Object $totalPage = this.getTotalPage();
      result = result * 59 + ($totalPage == null ? 43 : $totalPage.hashCode());
      Object $movieList115DTOList = this.getMovieList115DTOList();
      result = result * 59 + ($movieList115DTOList == null ? 43 : $movieList115DTOList.hashCode());
      Object $mediaType = this.getMediaType();
      result = result * 59 + ($mediaType == null ? 43 : $mediaType.hashCode());
      Object $creditsDTOList = this.getCreditsDTOList();
      return result * 59 + ($creditsDTOList == null ? 43 : $creditsDTOList.hashCode());
   }

   @Generated
   @Override
   public String toString() {
      return "MovieListResponse(movieList115DTOList="
         + this.getMovieList115DTOList()
         + ", id="
         + this.getId()
         + ", page="
         + this.getPage()
         + ", totalPage="
         + this.getTotalPage()
         + ", mediaType="
         + this.getMediaType()
         + ", creditsDTOList="
         + this.getCreditsDTOList()
         + ")";
   }

   public static class CreditsDTO {
      private String name;
      private String profilePath;

      @Generated
      public String getName() {
         return this.name;
      }

      @Generated
      public String getProfilePath() {
         return this.profilePath;
      }

      @Generated
      public void setName(final String name) {
         this.name = name;
      }

      @Generated
      public void setProfilePath(final String profilePath) {
         this.profilePath = profilePath;
      }

      @Generated
      @Override
      public boolean equals(final Object o) {
         if (o == this) {
            return true;
         } else if (!(o instanceof MovieListResponse.CreditsDTO other)) {
            return false;
         } else if (!other.canEqual(this)) {
            return false;
         } else {
            Object this$name = this.getName();
            Object other$name = other.getName();
            if (this$name == null ? other$name == null : this$name.equals(other$name)) {
               Object this$profilePath = this.getProfilePath();
               Object other$profilePath = other.getProfilePath();
               return this$profilePath == null ? other$profilePath == null : this$profilePath.equals(other$profilePath);
            } else {
               return false;
            }
         }
      }

      @Generated
      protected boolean canEqual(final Object other) {
         return other instanceof MovieListResponse.CreditsDTO;
      }

      @Generated
      @Override
      public int hashCode() {
         int PRIME = 59;
         int result = 1;
         Object $name = this.getName();
         result = result * 59 + ($name == null ? 43 : $name.hashCode());
         Object $profilePath = this.getProfilePath();
         return result * 59 + ($profilePath == null ? 43 : $profilePath.hashCode());
      }

      @Generated
      @Override
      public String toString() {
         return "MovieListResponse.CreditsDTO(name=" + this.getName() + ", profilePath=" + this.getProfilePath() + ")";
      }
   }

   public static class MovieList115DTO {
      private String title;
      private String size;
      @JSONField(
         name = "share_link"
      )
      private String shareLink;
      private String resolution;
      private String quality;

      @Generated
      public String getTitle() {
         return this.title;
      }

      @Generated
      public String getSize() {
         return this.size;
      }

      @Generated
      public String getShareLink() {
         return this.shareLink;
      }

      @Generated
      public String getResolution() {
         return this.resolution;
      }

      @Generated
      public String getQuality() {
         return this.quality;
      }

      @Generated
      public void setTitle(final String title) {
         this.title = title;
      }

      @Generated
      public void setSize(final String size) {
         this.size = size;
      }

      @Generated
      public void setShareLink(final String shareLink) {
         this.shareLink = shareLink;
      }

      @Generated
      public void setResolution(final String resolution) {
         this.resolution = resolution;
      }

      @Generated
      public void setQuality(final String quality) {
         this.quality = quality;
      }

      @Generated
      @Override
      public boolean equals(final Object o) {
         if (o == this) {
            return true;
         } else if (!(o instanceof MovieListResponse.MovieList115DTO other)) {
            return false;
         } else if (!other.canEqual(this)) {
            return false;
         } else {
            Object this$title = this.getTitle();
            Object other$title = other.getTitle();
            if (this$title == null ? other$title == null : this$title.equals(other$title)) {
               Object this$size = this.getSize();
               Object other$size = other.getSize();
               if (this$size == null ? other$size == null : this$size.equals(other$size)) {
                  Object this$shareLink = this.getShareLink();
                  Object other$shareLink = other.getShareLink();
                  if (this$shareLink == null ? other$shareLink == null : this$shareLink.equals(other$shareLink)) {
                     Object this$resolution = this.getResolution();
                     Object other$resolution = other.getResolution();
                     if (this$resolution == null ? other$resolution == null : this$resolution.equals(other$resolution)) {
                        Object this$quality = this.getQuality();
                        Object other$quality = other.getQuality();
                        return this$quality == null ? other$quality == null : this$quality.equals(other$quality);
                     } else {
                        return false;
                     }
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
         return other instanceof MovieListResponse.MovieList115DTO;
      }

      @Generated
      @Override
      public int hashCode() {
         int PRIME = 59;
         int result = 1;
         Object $title = this.getTitle();
         result = result * 59 + ($title == null ? 43 : $title.hashCode());
         Object $size = this.getSize();
         result = result * 59 + ($size == null ? 43 : $size.hashCode());
         Object $shareLink = this.getShareLink();
         result = result * 59 + ($shareLink == null ? 43 : $shareLink.hashCode());
         Object $resolution = this.getResolution();
         result = result * 59 + ($resolution == null ? 43 : $resolution.hashCode());
         Object $quality = this.getQuality();
         return result * 59 + ($quality == null ? 43 : $quality.hashCode());
      }

      @Generated
      @Override
      public String toString() {
         return "MovieListResponse.MovieList115DTO(title="
            + this.getTitle()
            + ", size="
            + this.getSize()
            + ", shareLink="
            + this.getShareLink()
            + ", resolution="
            + this.getResolution()
            + ", quality="
            + this.getQuality()
            + ")";
      }
   }
}
