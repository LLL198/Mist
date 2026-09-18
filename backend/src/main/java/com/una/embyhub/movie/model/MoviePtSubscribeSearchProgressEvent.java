package com.una.embyhub.movie.model;

import lombok.Generated;

public class MoviePtSubscribeSearchProgressEvent {
   private String type;
   private String keyword;
   private Long subscribeId;
   private Integer limit;
   private Boolean autoDownload;
   private Integer totalSites;
   private Integer completedSites;
   private Long siteId;
   private String siteName;
   private Integer siteResultCount;
   private Integer totalResults;
   private Long elapsedMs;
   private String message;
   private MoviePtSubscribeSearchResponse result;

   @Generated
   MoviePtSubscribeSearchProgressEvent(
      final String type,
      final String keyword,
      final Long subscribeId,
      final Integer limit,
      final Boolean autoDownload,
      final Integer totalSites,
      final Integer completedSites,
      final Long siteId,
      final String siteName,
      final Integer siteResultCount,
      final Integer totalResults,
      final Long elapsedMs,
      final String message,
      final MoviePtSubscribeSearchResponse result
   ) {
      this.type = type;
      this.keyword = keyword;
      this.subscribeId = subscribeId;
      this.limit = limit;
      this.autoDownload = autoDownload;
      this.totalSites = totalSites;
      this.completedSites = completedSites;
      this.siteId = siteId;
      this.siteName = siteName;
      this.siteResultCount = siteResultCount;
      this.totalResults = totalResults;
      this.elapsedMs = elapsedMs;
      this.message = message;
      this.result = result;
   }

   @Generated
   public static MoviePtSubscribeSearchProgressEvent.MoviePtSubscribeSearchProgressEventBuilder builder() {
      return new MoviePtSubscribeSearchProgressEvent.MoviePtSubscribeSearchProgressEventBuilder();
   }

   @Generated
   public String getType() {
      return this.type;
   }

   @Generated
   public String getKeyword() {
      return this.keyword;
   }

   @Generated
   public Long getSubscribeId() {
      return this.subscribeId;
   }

   @Generated
   public Integer getLimit() {
      return this.limit;
   }

   @Generated
   public Boolean getAutoDownload() {
      return this.autoDownload;
   }

   @Generated
   public Integer getTotalSites() {
      return this.totalSites;
   }

   @Generated
   public Integer getCompletedSites() {
      return this.completedSites;
   }

   @Generated
   public Long getSiteId() {
      return this.siteId;
   }

   @Generated
   public String getSiteName() {
      return this.siteName;
   }

   @Generated
   public Integer getSiteResultCount() {
      return this.siteResultCount;
   }

   @Generated
   public Integer getTotalResults() {
      return this.totalResults;
   }

   @Generated
   public Long getElapsedMs() {
      return this.elapsedMs;
   }

   @Generated
   public String getMessage() {
      return this.message;
   }

   @Generated
   public MoviePtSubscribeSearchResponse getResult() {
      return this.result;
   }

   @Generated
   public void setType(final String type) {
      this.type = type;
   }

   @Generated
   public void setKeyword(final String keyword) {
      this.keyword = keyword;
   }

   @Generated
   public void setSubscribeId(final Long subscribeId) {
      this.subscribeId = subscribeId;
   }

   @Generated
   public void setLimit(final Integer limit) {
      this.limit = limit;
   }

   @Generated
   public void setAutoDownload(final Boolean autoDownload) {
      this.autoDownload = autoDownload;
   }

   @Generated
   public void setTotalSites(final Integer totalSites) {
      this.totalSites = totalSites;
   }

   @Generated
   public void setCompletedSites(final Integer completedSites) {
      this.completedSites = completedSites;
   }

   @Generated
   public void setSiteId(final Long siteId) {
      this.siteId = siteId;
   }

   @Generated
   public void setSiteName(final String siteName) {
      this.siteName = siteName;
   }

   @Generated
   public void setSiteResultCount(final Integer siteResultCount) {
      this.siteResultCount = siteResultCount;
   }

   @Generated
   public void setTotalResults(final Integer totalResults) {
      this.totalResults = totalResults;
   }

   @Generated
   public void setElapsedMs(final Long elapsedMs) {
      this.elapsedMs = elapsedMs;
   }

   @Generated
   public void setMessage(final String message) {
      this.message = message;
   }

   @Generated
   public void setResult(final MoviePtSubscribeSearchResponse result) {
      this.result = result;
   }

   @Generated
   @Override
   public boolean equals(final Object o) {
      if (o == this) {
         return true;
      } else if (!(o instanceof MoviePtSubscribeSearchProgressEvent other)) {
         return false;
      } else if (!other.canEqual(this)) {
         return false;
      } else {
         Object this$subscribeId = this.getSubscribeId();
         Object other$subscribeId = other.getSubscribeId();
         if (this$subscribeId == null ? other$subscribeId == null : this$subscribeId.equals(other$subscribeId)) {
            Object this$limit = this.getLimit();
            Object other$limit = other.getLimit();
            if (this$limit == null ? other$limit == null : this$limit.equals(other$limit)) {
               Object this$autoDownload = this.getAutoDownload();
               Object other$autoDownload = other.getAutoDownload();
               if (this$autoDownload == null ? other$autoDownload == null : this$autoDownload.equals(other$autoDownload)) {
                  Object this$totalSites = this.getTotalSites();
                  Object other$totalSites = other.getTotalSites();
                  if (this$totalSites == null ? other$totalSites == null : this$totalSites.equals(other$totalSites)) {
                     Object this$completedSites = this.getCompletedSites();
                     Object other$completedSites = other.getCompletedSites();
                     if (this$completedSites == null ? other$completedSites == null : this$completedSites.equals(other$completedSites)) {
                        Object this$siteId = this.getSiteId();
                        Object other$siteId = other.getSiteId();
                        if (this$siteId == null ? other$siteId == null : this$siteId.equals(other$siteId)) {
                           Object this$siteResultCount = this.getSiteResultCount();
                           Object other$siteResultCount = other.getSiteResultCount();
                           if (this$siteResultCount == null ? other$siteResultCount == null : this$siteResultCount.equals(other$siteResultCount)) {
                              Object this$totalResults = this.getTotalResults();
                              Object other$totalResults = other.getTotalResults();
                              if (this$totalResults == null ? other$totalResults == null : this$totalResults.equals(other$totalResults)) {
                                 Object this$elapsedMs = this.getElapsedMs();
                                 Object other$elapsedMs = other.getElapsedMs();
                                 if (this$elapsedMs == null ? other$elapsedMs == null : this$elapsedMs.equals(other$elapsedMs)) {
                                    Object this$type = this.getType();
                                    Object other$type = other.getType();
                                    if (this$type == null ? other$type == null : this$type.equals(other$type)) {
                                       Object this$keyword = this.getKeyword();
                                       Object other$keyword = other.getKeyword();
                                       if (this$keyword == null ? other$keyword == null : this$keyword.equals(other$keyword)) {
                                          Object this$siteName = this.getSiteName();
                                          Object other$siteName = other.getSiteName();
                                          if (this$siteName == null ? other$siteName == null : this$siteName.equals(other$siteName)) {
                                             Object this$message = this.getMessage();
                                             Object other$message = other.getMessage();
                                             if (this$message == null ? other$message == null : this$message.equals(other$message)) {
                                                Object this$result = this.getResult();
                                                Object other$result = other.getResult();
                                                return this$result == null ? other$result == null : this$result.equals(other$result);
                                             } else {
                                                return false;
                                             }
                                          } else {
                                             return false;
                                          }
                                       } else {
                                          return false;
                                       }
                                    } else {
                                       return false;
                                    }
                                 } else {
                                    return false;
                                 }
                              } else {
                                 return false;
                              }
                           } else {
                              return false;
                           }
                        } else {
                           return false;
                        }
                     } else {
                        return false;
                     }
                  } else {
                     return false;
                  }
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
      return other instanceof MoviePtSubscribeSearchProgressEvent;
   }

   @Generated
   @Override
   public int hashCode() {
      int PRIME = 59;
      int result = 1;
      Object $subscribeId = this.getSubscribeId();
      result = result * 59 + ($subscribeId == null ? 43 : $subscribeId.hashCode());
      Object $limit = this.getLimit();
      result = result * 59 + ($limit == null ? 43 : $limit.hashCode());
      Object $autoDownload = this.getAutoDownload();
      result = result * 59 + ($autoDownload == null ? 43 : $autoDownload.hashCode());
      Object $totalSites = this.getTotalSites();
      result = result * 59 + ($totalSites == null ? 43 : $totalSites.hashCode());
      Object $completedSites = this.getCompletedSites();
      result = result * 59 + ($completedSites == null ? 43 : $completedSites.hashCode());
      Object $siteId = this.getSiteId();
      result = result * 59 + ($siteId == null ? 43 : $siteId.hashCode());
      Object $siteResultCount = this.getSiteResultCount();
      result = result * 59 + ($siteResultCount == null ? 43 : $siteResultCount.hashCode());
      Object $totalResults = this.getTotalResults();
      result = result * 59 + ($totalResults == null ? 43 : $totalResults.hashCode());
      Object $elapsedMs = this.getElapsedMs();
      result = result * 59 + ($elapsedMs == null ? 43 : $elapsedMs.hashCode());
      Object $type = this.getType();
      result = result * 59 + ($type == null ? 43 : $type.hashCode());
      Object $keyword = this.getKeyword();
      result = result * 59 + ($keyword == null ? 43 : $keyword.hashCode());
      Object $siteName = this.getSiteName();
      result = result * 59 + ($siteName == null ? 43 : $siteName.hashCode());
      Object $message = this.getMessage();
      result = result * 59 + ($message == null ? 43 : $message.hashCode());
      Object $result = this.getResult();
      return result * 59 + ($result == null ? 43 : $result.hashCode());
   }

   @Generated
   @Override
   public String toString() {
      return "MoviePtSubscribeSearchProgressEvent(type="
         + this.getType()
         + ", keyword="
         + this.getKeyword()
         + ", subscribeId="
         + this.getSubscribeId()
         + ", limit="
         + this.getLimit()
         + ", autoDownload="
         + this.getAutoDownload()
         + ", totalSites="
         + this.getTotalSites()
         + ", completedSites="
         + this.getCompletedSites()
         + ", siteId="
         + this.getSiteId()
         + ", siteName="
         + this.getSiteName()
         + ", siteResultCount="
         + this.getSiteResultCount()
         + ", totalResults="
         + this.getTotalResults()
         + ", elapsedMs="
         + this.getElapsedMs()
         + ", message="
         + this.getMessage()
         + ", result="
         + this.getResult()
         + ")";
   }

   @Generated
   public static class MoviePtSubscribeSearchProgressEventBuilder {
      @Generated
      private String type;
      @Generated
      private String keyword;
      @Generated
      private Long subscribeId;
      @Generated
      private Integer limit;
      @Generated
      private Boolean autoDownload;
      @Generated
      private Integer totalSites;
      @Generated
      private Integer completedSites;
      @Generated
      private Long siteId;
      @Generated
      private String siteName;
      @Generated
      private Integer siteResultCount;
      @Generated
      private Integer totalResults;
      @Generated
      private Long elapsedMs;
      @Generated
      private String message;
      @Generated
      private MoviePtSubscribeSearchResponse result;

      @Generated
      MoviePtSubscribeSearchProgressEventBuilder() {
      }

      @Generated
      public MoviePtSubscribeSearchProgressEvent.MoviePtSubscribeSearchProgressEventBuilder type(final String type) {
         this.type = type;
         return this;
      }

      @Generated
      public MoviePtSubscribeSearchProgressEvent.MoviePtSubscribeSearchProgressEventBuilder keyword(final String keyword) {
         this.keyword = keyword;
         return this;
      }

      @Generated
      public MoviePtSubscribeSearchProgressEvent.MoviePtSubscribeSearchProgressEventBuilder subscribeId(final Long subscribeId) {
         this.subscribeId = subscribeId;
         return this;
      }

      @Generated
      public MoviePtSubscribeSearchProgressEvent.MoviePtSubscribeSearchProgressEventBuilder limit(final Integer limit) {
         this.limit = limit;
         return this;
      }

      @Generated
      public MoviePtSubscribeSearchProgressEvent.MoviePtSubscribeSearchProgressEventBuilder autoDownload(final Boolean autoDownload) {
         this.autoDownload = autoDownload;
         return this;
      }

      @Generated
      public MoviePtSubscribeSearchProgressEvent.MoviePtSubscribeSearchProgressEventBuilder totalSites(final Integer totalSites) {
         this.totalSites = totalSites;
         return this;
      }

      @Generated
      public MoviePtSubscribeSearchProgressEvent.MoviePtSubscribeSearchProgressEventBuilder completedSites(final Integer completedSites) {
         this.completedSites = completedSites;
         return this;
      }

      @Generated
      public MoviePtSubscribeSearchProgressEvent.MoviePtSubscribeSearchProgressEventBuilder siteId(final Long siteId) {
         this.siteId = siteId;
         return this;
      }

      @Generated
      public MoviePtSubscribeSearchProgressEvent.MoviePtSubscribeSearchProgressEventBuilder siteName(final String siteName) {
         this.siteName = siteName;
         return this;
      }

      @Generated
      public MoviePtSubscribeSearchProgressEvent.MoviePtSubscribeSearchProgressEventBuilder siteResultCount(final Integer siteResultCount) {
         this.siteResultCount = siteResultCount;
         return this;
      }

      @Generated
      public MoviePtSubscribeSearchProgressEvent.MoviePtSubscribeSearchProgressEventBuilder totalResults(final Integer totalResults) {
         this.totalResults = totalResults;
         return this;
      }

      @Generated
      public MoviePtSubscribeSearchProgressEvent.MoviePtSubscribeSearchProgressEventBuilder elapsedMs(final Long elapsedMs) {
         this.elapsedMs = elapsedMs;
         return this;
      }

      @Generated
      public MoviePtSubscribeSearchProgressEvent.MoviePtSubscribeSearchProgressEventBuilder message(final String message) {
         this.message = message;
         return this;
      }

      @Generated
      public MoviePtSubscribeSearchProgressEvent.MoviePtSubscribeSearchProgressEventBuilder result(final MoviePtSubscribeSearchResponse result) {
         this.result = result;
         return this;
      }

      @Generated
      public MoviePtSubscribeSearchProgressEvent build() {
         return new MoviePtSubscribeSearchProgressEvent(
            this.type,
            this.keyword,
            this.subscribeId,
            this.limit,
            this.autoDownload,
            this.totalSites,
            this.completedSites,
            this.siteId,
            this.siteName,
            this.siteResultCount,
            this.totalResults,
            this.elapsedMs,
            this.message,
            this.result
         );
      }

      @Generated
      @Override
      public String toString() {
         return "MoviePtSubscribeSearchProgressEvent.MoviePtSubscribeSearchProgressEventBuilder(type="
            + this.type
            + ", keyword="
            + this.keyword
            + ", subscribeId="
            + this.subscribeId
            + ", limit="
            + this.limit
            + ", autoDownload="
            + this.autoDownload
            + ", totalSites="
            + this.totalSites
            + ", completedSites="
            + this.completedSites
            + ", siteId="
            + this.siteId
            + ", siteName="
            + this.siteName
            + ", siteResultCount="
            + this.siteResultCount
            + ", totalResults="
            + this.totalResults
            + ", elapsedMs="
            + this.elapsedMs
            + ", message="
            + this.message
            + ", result="
            + this.result
            + ")";
      }
   }
}
