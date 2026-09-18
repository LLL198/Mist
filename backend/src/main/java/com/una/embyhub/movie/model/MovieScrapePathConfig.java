package com.una.embyhub.movie.model;

import java.io.Serializable;
import lombok.Generated;

public class MovieScrapePathConfig implements Serializable {
   private static final long serialVersionUID = 1L;
   private Long id;
   private String name;
   private String qbDownloadPath;
   private String hardlinkPath;
   private Integer overwrite;
   private Integer coexist;
   private Integer qualityPriority;
   private Integer sizePriority;
   private Integer hardlinkMode;

   @Generated
   MovieScrapePathConfig(
      final Long id,
      final String name,
      final String qbDownloadPath,
      final String hardlinkPath,
      final Integer overwrite,
      final Integer coexist,
      final Integer qualityPriority,
      final Integer sizePriority,
      final Integer hardlinkMode
   ) {
      this.id = id;
      this.name = name;
      this.qbDownloadPath = qbDownloadPath;
      this.hardlinkPath = hardlinkPath;
      this.overwrite = overwrite;
      this.coexist = coexist;
      this.qualityPriority = qualityPriority;
      this.sizePriority = sizePriority;
      this.hardlinkMode = hardlinkMode;
   }

   @Generated
   public static MovieScrapePathConfig.MovieScrapePathConfigBuilder builder() {
      return new MovieScrapePathConfig.MovieScrapePathConfigBuilder();
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
   public String getQbDownloadPath() {
      return this.qbDownloadPath;
   }

   @Generated
   public String getHardlinkPath() {
      return this.hardlinkPath;
   }

   @Generated
   public Integer getOverwrite() {
      return this.overwrite;
   }

   @Generated
   public Integer getCoexist() {
      return this.coexist;
   }

   @Generated
   public Integer getQualityPriority() {
      return this.qualityPriority;
   }

   @Generated
   public Integer getSizePriority() {
      return this.sizePriority;
   }

   @Generated
   public Integer getHardlinkMode() {
      return this.hardlinkMode;
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
   public void setQbDownloadPath(final String qbDownloadPath) {
      this.qbDownloadPath = qbDownloadPath;
   }

   @Generated
   public void setHardlinkPath(final String hardlinkPath) {
      this.hardlinkPath = hardlinkPath;
   }

   @Generated
   public void setOverwrite(final Integer overwrite) {
      this.overwrite = overwrite;
   }

   @Generated
   public void setCoexist(final Integer coexist) {
      this.coexist = coexist;
   }

   @Generated
   public void setQualityPriority(final Integer qualityPriority) {
      this.qualityPriority = qualityPriority;
   }

   @Generated
   public void setSizePriority(final Integer sizePriority) {
      this.sizePriority = sizePriority;
   }

   @Generated
   public void setHardlinkMode(final Integer hardlinkMode) {
      this.hardlinkMode = hardlinkMode;
   }

   @Generated
   @Override
   public boolean equals(final Object o) {
      if (o == this) {
         return true;
      } else if (!(o instanceof MovieScrapePathConfig other)) {
         return false;
      } else if (!other.canEqual(this)) {
         return false;
      } else {
         Object this$id = this.getId();
         Object other$id = other.getId();
         if (this$id == null ? other$id == null : this$id.equals(other$id)) {
            Object this$overwrite = this.getOverwrite();
            Object other$overwrite = other.getOverwrite();
            if (this$overwrite == null ? other$overwrite == null : this$overwrite.equals(other$overwrite)) {
               Object this$coexist = this.getCoexist();
               Object other$coexist = other.getCoexist();
               if (this$coexist == null ? other$coexist == null : this$coexist.equals(other$coexist)) {
                  Object this$qualityPriority = this.getQualityPriority();
                  Object other$qualityPriority = other.getQualityPriority();
                  if (this$qualityPriority == null ? other$qualityPriority == null : this$qualityPriority.equals(other$qualityPriority)) {
                     Object this$sizePriority = this.getSizePriority();
                     Object other$sizePriority = other.getSizePriority();
                     if (this$sizePriority == null ? other$sizePriority == null : this$sizePriority.equals(other$sizePriority)) {
                        Object this$hardlinkMode = this.getHardlinkMode();
                        Object other$hardlinkMode = other.getHardlinkMode();
                        if (this$hardlinkMode == null ? other$hardlinkMode == null : this$hardlinkMode.equals(other$hardlinkMode)) {
                           Object this$name = this.getName();
                           Object other$name = other.getName();
                           if (this$name == null ? other$name == null : this$name.equals(other$name)) {
                              Object this$qbDownloadPath = this.getQbDownloadPath();
                              Object other$qbDownloadPath = other.getQbDownloadPath();
                              if (this$qbDownloadPath == null ? other$qbDownloadPath == null : this$qbDownloadPath.equals(other$qbDownloadPath)) {
                                 Object this$hardlinkPath = this.getHardlinkPath();
                                 Object other$hardlinkPath = other.getHardlinkPath();
                                 return this$hardlinkPath == null ? other$hardlinkPath == null : this$hardlinkPath.equals(other$hardlinkPath);
                              } else {
                                 return false;
                              }
                           } else {
                              return false;
                           }
                        } else {
                           return false;
                        }
                     } else {
                        return false;
                     }
                  } else {
                     return false;
                  }
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
      return other instanceof MovieScrapePathConfig;
   }

   @Generated
   @Override
   public int hashCode() {
      int PRIME = 59;
      int result = 1;
      Object $id = this.getId();
      result = result * 59 + ($id == null ? 43 : $id.hashCode());
      Object $overwrite = this.getOverwrite();
      result = result * 59 + ($overwrite == null ? 43 : $overwrite.hashCode());
      Object $coexist = this.getCoexist();
      result = result * 59 + ($coexist == null ? 43 : $coexist.hashCode());
      Object $qualityPriority = this.getQualityPriority();
      result = result * 59 + ($qualityPriority == null ? 43 : $qualityPriority.hashCode());
      Object $sizePriority = this.getSizePriority();
      result = result * 59 + ($sizePriority == null ? 43 : $sizePriority.hashCode());
      Object $hardlinkMode = this.getHardlinkMode();
      result = result * 59 + ($hardlinkMode == null ? 43 : $hardlinkMode.hashCode());
      Object $name = this.getName();
      result = result * 59 + ($name == null ? 43 : $name.hashCode());
      Object $qbDownloadPath = this.getQbDownloadPath();
      result = result * 59 + ($qbDownloadPath == null ? 43 : $qbDownloadPath.hashCode());
      Object $hardlinkPath = this.getHardlinkPath();
      return result * 59 + ($hardlinkPath == null ? 43 : $hardlinkPath.hashCode());
   }

   @Generated
   @Override
   public String toString() {
      return "MovieScrapePathConfig(id="
         + this.getId()
         + ", name="
         + this.getName()
         + ", qbDownloadPath="
         + this.getQbDownloadPath()
         + ", hardlinkPath="
         + this.getHardlinkPath()
         + ", overwrite="
         + this.getOverwrite()
         + ", coexist="
         + this.getCoexist()
         + ", qualityPriority="
         + this.getQualityPriority()
         + ", sizePriority="
         + this.getSizePriority()
         + ", hardlinkMode="
         + this.getHardlinkMode()
         + ")";
   }

   @Generated
   public static class MovieScrapePathConfigBuilder {
      @Generated
      private Long id;
      @Generated
      private String name;
      @Generated
      private String qbDownloadPath;
      @Generated
      private String hardlinkPath;
      @Generated
      private Integer overwrite;
      @Generated
      private Integer coexist;
      @Generated
      private Integer qualityPriority;
      @Generated
      private Integer sizePriority;
      @Generated
      private Integer hardlinkMode;

      @Generated
      MovieScrapePathConfigBuilder() {
      }

      @Generated
      public MovieScrapePathConfig.MovieScrapePathConfigBuilder id(final Long id) {
         this.id = id;
         return this;
      }

      @Generated
      public MovieScrapePathConfig.MovieScrapePathConfigBuilder name(final String name) {
         this.name = name;
         return this;
      }

      @Generated
      public MovieScrapePathConfig.MovieScrapePathConfigBuilder qbDownloadPath(final String qbDownloadPath) {
         this.qbDownloadPath = qbDownloadPath;
         return this;
      }

      @Generated
      public MovieScrapePathConfig.MovieScrapePathConfigBuilder hardlinkPath(final String hardlinkPath) {
         this.hardlinkPath = hardlinkPath;
         return this;
      }

      @Generated
      public MovieScrapePathConfig.MovieScrapePathConfigBuilder overwrite(final Integer overwrite) {
         this.overwrite = overwrite;
         return this;
      }

      @Generated
      public MovieScrapePathConfig.MovieScrapePathConfigBuilder coexist(final Integer coexist) {
         this.coexist = coexist;
         return this;
      }

      @Generated
      public MovieScrapePathConfig.MovieScrapePathConfigBuilder qualityPriority(final Integer qualityPriority) {
         this.qualityPriority = qualityPriority;
         return this;
      }

      @Generated
      public MovieScrapePathConfig.MovieScrapePathConfigBuilder sizePriority(final Integer sizePriority) {
         this.sizePriority = sizePriority;
         return this;
      }

      @Generated
      public MovieScrapePathConfig.MovieScrapePathConfigBuilder hardlinkMode(final Integer hardlinkMode) {
         this.hardlinkMode = hardlinkMode;
         return this;
      }

      @Generated
      public MovieScrapePathConfig build() {
         return new MovieScrapePathConfig(
            this.id,
            this.name,
            this.qbDownloadPath,
            this.hardlinkPath,
            this.overwrite,
            this.coexist,
            this.qualityPriority,
            this.sizePriority,
            this.hardlinkMode
         );
      }

      @Generated
      @Override
      public String toString() {
         return "MovieScrapePathConfig.MovieScrapePathConfigBuilder(id="
            + this.id
            + ", name="
            + this.name
            + ", qbDownloadPath="
            + this.qbDownloadPath
            + ", hardlinkPath="
            + this.hardlinkPath
            + ", overwrite="
            + this.overwrite
            + ", coexist="
            + this.coexist
            + ", qualityPriority="
            + this.qualityPriority
            + ", sizePriority="
            + this.sizePriority
            + ", hardlinkMode="
            + this.hardlinkMode
            + ")";
      }
   }
}
