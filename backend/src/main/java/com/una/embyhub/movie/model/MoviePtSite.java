package com.una.embyhub.movie.model;

import java.io.Serializable;
import lombok.Generated;

public class MoviePtSite implements Serializable {
   private static final long serialVersionUID = 1L;
   private Long id;
   private String name;
   private String baseUrl;
   private String cookies;
   private String token;
   private String siteType;
   private Integer enabled;
   private String favicon;
   private MoviePtUserStats userStats;

   @Generated
   MoviePtSite(
      final Long id,
      final String name,
      final String baseUrl,
      final String cookies,
      final String token,
      final String siteType,
      final Integer enabled,
      final String favicon,
      final MoviePtUserStats userStats
   ) {
      this.id = id;
      this.name = name;
      this.baseUrl = baseUrl;
      this.cookies = cookies;
      this.token = token;
      this.siteType = siteType;
      this.enabled = enabled;
      this.favicon = favicon;
      this.userStats = userStats;
   }

   @Generated
   public static MoviePtSite.MoviePtSiteBuilder builder() {
      return new MoviePtSite.MoviePtSiteBuilder();
   }

   @Generated
   public Long getId() {
      return this.id;
   }

   @Generated
   public String getName() {
      return this.name;
   }

   @Generated
   public String getBaseUrl() {
      return this.baseUrl;
   }

   @Generated
   public String getCookies() {
      return this.cookies;
   }

   @Generated
   public String getToken() {
      return this.token;
   }

   @Generated
   public String getSiteType() {
      return this.siteType;
   }

   @Generated
   public Integer getEnabled() {
      return this.enabled;
   }

   @Generated
   public String getFavicon() {
      return this.favicon;
   }

   @Generated
   public MoviePtUserStats getUserStats() {
      return this.userStats;
   }

   @Generated
   public void setId(final Long id) {
      this.id = id;
   }

   @Generated
   public void setName(final String name) {
      this.name = name;
   }

   @Generated
   public void setBaseUrl(final String baseUrl) {
      this.baseUrl = baseUrl;
   }

   @Generated
   public void setCookies(final String cookies) {
      this.cookies = cookies;
   }

   @Generated
   public void setToken(final String token) {
      this.token = token;
   }

   @Generated
   public void setSiteType(final String siteType) {
      this.siteType = siteType;
   }

   @Generated
   public void setEnabled(final Integer enabled) {
      this.enabled = enabled;
   }

   @Generated
   public void setFavicon(final String favicon) {
      this.favicon = favicon;
   }

   @Generated
   public void setUserStats(final MoviePtUserStats userStats) {
      this.userStats = userStats;
   }

   @Generated
   @Override
   public boolean equals(final Object o) {
      if (o == this) {
         return true;
      } else if (!(o instanceof MoviePtSite other)) {
         return false;
      } else if (!other.canEqual(this)) {
         return false;
      } else {
         Object this$id = this.getId();
         Object other$id = other.getId();
         if (this$id == null ? other$id == null : this$id.equals(other$id)) {
            Object this$enabled = this.getEnabled();
            Object other$enabled = other.getEnabled();
            if (this$enabled == null ? other$enabled == null : this$enabled.equals(other$enabled)) {
               Object this$name = this.getName();
               Object other$name = other.getName();
               if (this$name == null ? other$name == null : this$name.equals(other$name)) {
                  Object this$baseUrl = this.getBaseUrl();
                  Object other$baseUrl = other.getBaseUrl();
                  if (this$baseUrl == null ? other$baseUrl == null : this$baseUrl.equals(other$baseUrl)) {
                     Object this$cookies = this.getCookies();
                     Object other$cookies = other.getCookies();
                     if (this$cookies == null ? other$cookies == null : this$cookies.equals(other$cookies)) {
                        Object this$token = this.getToken();
                        Object other$token = other.getToken();
                        if (this$token == null ? other$token == null : this$token.equals(other$token)) {
                           Object this$siteType = this.getSiteType();
                           Object other$siteType = other.getSiteType();
                           if (this$siteType == null ? other$siteType == null : this$siteType.equals(other$siteType)) {
                              Object this$favicon = this.getFavicon();
                              Object other$favicon = other.getFavicon();
                              if (this$favicon == null ? other$favicon == null : this$favicon.equals(other$favicon)) {
                                 Object this$userStats = this.getUserStats();
                                 Object other$userStats = other.getUserStats();
                                 return this$userStats == null ? other$userStats == null : this$userStats.equals(other$userStats);
                              } else {
                                 return false;
                              }
                           } else {
                              return false;
                           }
                        } else {
                           return false;
                        }
                     } else {
                        return false;
                     }
                  } else {
                     return false;
                  }
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
      return other instanceof MoviePtSite;
   }

   @Generated
   @Override
   public int hashCode() {
      int PRIME = 59;
      int result = 1;
      Object $id = this.getId();
      result = result * 59 + ($id == null ? 43 : $id.hashCode());
      Object $enabled = this.getEnabled();
      result = result * 59 + ($enabled == null ? 43 : $enabled.hashCode());
      Object $name = this.getName();
      result = result * 59 + ($name == null ? 43 : $name.hashCode());
      Object $baseUrl = this.getBaseUrl();
      result = result * 59 + ($baseUrl == null ? 43 : $baseUrl.hashCode());
      Object $cookies = this.getCookies();
      result = result * 59 + ($cookies == null ? 43 : $cookies.hashCode());
      Object $token = this.getToken();
      result = result * 59 + ($token == null ? 43 : $token.hashCode());
      Object $siteType = this.getSiteType();
      result = result * 59 + ($siteType == null ? 43 : $siteType.hashCode());
      Object $favicon = this.getFavicon();
      result = result * 59 + ($favicon == null ? 43 : $favicon.hashCode());
      Object $userStats = this.getUserStats();
      return result * 59 + ($userStats == null ? 43 : $userStats.hashCode());
   }

   @Generated
   @Override
   public String toString() {
      return "MoviePtSite(id="
         + this.getId()
         + ", name="
         + this.getName()
         + ", baseUrl="
         + this.getBaseUrl()
         + ", cookies="
         + this.getCookies()
         + ", token="
         + this.getToken()
         + ", siteType="
         + this.getSiteType()
         + ", enabled="
         + this.getEnabled()
         + ", favicon="
         + this.getFavicon()
         + ", userStats="
         + this.getUserStats()
         + ")";
   }

   @Generated
   public static class MoviePtSiteBuilder {
      @Generated
      private Long id;
      @Generated
      private String name;
      @Generated
      private String baseUrl;
      @Generated
      private String cookies;
      @Generated
      private String token;
      @Generated
      private String siteType;
      @Generated
      private Integer enabled;
      @Generated
      private String favicon;
      @Generated
      private MoviePtUserStats userStats;

      @Generated
      MoviePtSiteBuilder() {
      }

      @Generated
      public MoviePtSite.MoviePtSiteBuilder id(final Long id) {
         this.id = id;
         return this;
      }

      @Generated
      public MoviePtSite.MoviePtSiteBuilder name(final String name) {
         this.name = name;
         return this;
      }

      @Generated
      public MoviePtSite.MoviePtSiteBuilder baseUrl(final String baseUrl) {
         this.baseUrl = baseUrl;
         return this;
      }

      @Generated
      public MoviePtSite.MoviePtSiteBuilder cookies(final String cookies) {
         this.cookies = cookies;
         return this;
      }

      @Generated
      public MoviePtSite.MoviePtSiteBuilder token(final String token) {
         this.token = token;
         return this;
      }

      @Generated
      public MoviePtSite.MoviePtSiteBuilder siteType(final String siteType) {
         this.siteType = siteType;
         return this;
      }

      @Generated
      public MoviePtSite.MoviePtSiteBuilder enabled(final Integer enabled) {
         this.enabled = enabled;
         return this;
      }

      @Generated
      public MoviePtSite.MoviePtSiteBuilder favicon(final String favicon) {
         this.favicon = favicon;
         return this;
      }

      @Generated
      public MoviePtSite.MoviePtSiteBuilder userStats(final MoviePtUserStats userStats) {
         this.userStats = userStats;
         return this;
      }

      @Generated
      public MoviePtSite build() {
         return new MoviePtSite(this.id, this.name, this.baseUrl, this.cookies, this.token, this.siteType, this.enabled, this.favicon, this.userStats);
      }

      @Generated
      @Override
      public String toString() {
         return "MoviePtSite.MoviePtSiteBuilder(id="
            + this.id
            + ", name="
            + this.name
            + ", baseUrl="
            + this.baseUrl
            + ", cookies="
            + this.cookies
            + ", token="
            + this.token
            + ", siteType="
            + this.siteType
            + ", enabled="
            + this.enabled
            + ", favicon="
            + this.favicon
            + ", userStats="
            + this.userStats
            + ")";
      }
   }
}
