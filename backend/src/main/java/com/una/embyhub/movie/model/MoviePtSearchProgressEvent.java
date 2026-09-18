package com.una.embyhub.movie.model;

import java.util.List;
import lombok.Generated;

public class MoviePtSearchProgressEvent {
   private String type;
   private String keyword;
   private Integer totalSites;
   private Integer completedSites;
   private Long siteId;
   private String siteName;
   private Integer siteResultCount;
   private Integer totalResults;
   private Long elapsedMs;
   private String message;
   private List<MoviePtSearchResult> results;

   @Generated
   MoviePtSearchProgressEvent(
      final String type,
      final String keyword,
      final Integer totalSites,
      final Integer completedSites,
      final Long siteId,
      final String siteName,
      final Integer siteResultCount,
      final Integer totalResults,
      final Long elapsedMs,
      final String message,
      final List<MoviePtSearchResult> results
   ) {
      this.type = type;
      this.keyword = keyword;
      this.totalSites = totalSites;
      this.completedSites = completedSites;
      this.siteId = siteId;
      this.siteName = siteName;
      this.siteResultCount = siteResultCount;
      this.totalResults = totalResults;
      this.elapsedMs = elapsedMs;
      this.message = message;
      this.results = results;
   }

   @Generated
   public static MoviePtSearchProgressEvent.MoviePtSearchProgressEventBuilder builder() {
      return new MoviePtSearchProgressEvent.MoviePtSearchProgressEventBuilder();
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
   public List<MoviePtSearchResult> getResults() {
      return this.results;
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
   public void setResults(final List<MoviePtSearchResult> results) {
      this.results = results;
   }

   @Generated
   @Override
   public boolean equals(final Object o) {
      if (o == this) {
         return true;
      } else if (!(o instanceof MoviePtSearchProgressEvent other)) {
         return false;
      } else if (!other.canEqual(this)) {
         return false;
      } else {
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
                                       Object this$results = this.getResults();
                                       Object other$results = other.getResults();
                                       return this$results == null ? other$results == null : this$results.equals(other$results);
                                    } else {
                                       return false;
                                    }
                                 } else {
                                    return false;
                                 }
                              } else {
                                 return false;
                              }
                           } else {
                              return false;
                           }
                        } else {
                           return false;
                        }
                     } else {
                        return false;
                     }
                  } else {
                     return false;
                  }
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
      return other instanceof MoviePtSearchProgressEvent;
   }

   @Generated
   @Override
   public int hashCode() {
      int PRIME = 59;
      int result = 1;
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
      Object $results = this.getResults();
      return result * 59 + ($results == null ? 43 : $results.hashCode());
   }

   @Generated
   @Override
   public String toString() {
      return "MoviePtSearchProgressEvent(type="
         + this.getType()
         + ", keyword="
         + this.getKeyword()
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
         + ", results="
         + this.getResults()
         + ")";
   }

   @Generated
   public static class MoviePtSearchProgressEventBuilder {
      @Generated
      private String type;
      @Generated
      private String keyword;
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
      private List<MoviePtSearchResult> results;

      @Generated
      MoviePtSearchProgressEventBuilder() {
      }

      @Generated
      public MoviePtSearchProgressEvent.MoviePtSearchProgressEventBuilder type(final String type) {
         this.type = type;
         return this;
      }

      @Generated
      public MoviePtSearchProgressEvent.MoviePtSearchProgressEventBuilder keyword(final String keyword) {
         this.keyword = keyword;
         return this;
      }

      @Generated
      public MoviePtSearchProgressEvent.MoviePtSearchProgressEventBuilder totalSites(final Integer totalSites) {
         this.totalSites = totalSites;
         return this;
      }

      @Generated
      public MoviePtSearchProgressEvent.MoviePtSearchProgressEventBuilder completedSites(final Integer completedSites) {
         this.completedSites = completedSites;
         return this;
      }

      @Generated
      public MoviePtSearchProgressEvent.MoviePtSearchProgressEventBuilder siteId(final Long siteId) {
         this.siteId = siteId;
         return this;
      }

      @Generated
      public MoviePtSearchProgressEvent.MoviePtSearchProgressEventBuilder siteName(final String siteName) {
         this.siteName = siteName;
         return this;
      }

      @Generated
      public MoviePtSearchProgressEvent.MoviePtSearchProgressEventBuilder siteResultCount(final Integer siteResultCount) {
         this.siteResultCount = siteResultCount;
         return this;
      }

      @Generated
      public MoviePtSearchProgressEvent.MoviePtSearchProgressEventBuilder totalResults(final Integer totalResults) {
         this.totalResults = totalResults;
         return this;
      }

      @Generated
      public MoviePtSearchProgressEvent.MoviePtSearchProgressEventBuilder elapsedMs(final Long elapsedMs) {
         this.elapsedMs = elapsedMs;
         return this;
      }

      @Generated
      public MoviePtSearchProgressEvent.MoviePtSearchProgressEventBuilder message(final String message) {
         this.message = message;
         return this;
      }

      @Generated
      public MoviePtSearchProgressEvent.MoviePtSearchProgressEventBuilder results(final List<MoviePtSearchResult> results) {
         this.results = results;
         return this;
      }

      @Generated
      public MoviePtSearchProgressEvent build() {
         return new MoviePtSearchProgressEvent(
            this.type,
            this.keyword,
            this.totalSites,
            this.completedSites,
            this.siteId,
            this.siteName,
            this.siteResultCount,
            this.totalResults,
            this.elapsedMs,
            this.message,
            this.results
         );
      }

      @Generated
      @Override
      public String toString() {
         return "MoviePtSearchProgressEvent.MoviePtSearchProgressEventBuilder(type="
            + this.type
            + ", keyword="
            + this.keyword
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
            + ", results="
            + this.results
            + ")";
      }
   }
}
