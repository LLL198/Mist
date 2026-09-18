package com.una.embyhub.movie.model;

import java.io.Serializable;
import java.util.List;
import lombok.Generated;

public class MoviePtSubscribeSearchResponse implements Serializable {
   private static final long serialVersionUID = 1L;
   private Long subscribeId;
   private String subscribeName;
   private String keyword;
   private List<String> searchedKeywords;
   private String state;
   private Integer matchedCount;
   private Integer tmdbLatestEpisode;
   private Integer startEpisode;
   private List<Integer> downloadedEpisodes;
   private List<Integer> missingEpisodes;
   private Boolean autoDownloadTriggered;
   private MoviePtSearchResult selectedResult;
   private MovieActionResponse downloadResult;
   private List<MoviePtSearchResult> results;
   private String message;

   @Generated
   MoviePtSubscribeSearchResponse(
      final Long subscribeId,
      final String subscribeName,
      final String keyword,
      final List<String> searchedKeywords,
      final String state,
      final Integer matchedCount,
      final Integer tmdbLatestEpisode,
      final Integer startEpisode,
      final List<Integer> downloadedEpisodes,
      final List<Integer> missingEpisodes,
      final Boolean autoDownloadTriggered,
      final MoviePtSearchResult selectedResult,
      final MovieActionResponse downloadResult,
      final List<MoviePtSearchResult> results,
      final String message
   ) {
      this.subscribeId = subscribeId;
      this.subscribeName = subscribeName;
      this.keyword = keyword;
      this.searchedKeywords = searchedKeywords;
      this.state = state;
      this.matchedCount = matchedCount;
      this.tmdbLatestEpisode = tmdbLatestEpisode;
      this.startEpisode = startEpisode;
      this.downloadedEpisodes = downloadedEpisodes;
      this.missingEpisodes = missingEpisodes;
      this.autoDownloadTriggered = autoDownloadTriggered;
      this.selectedResult = selectedResult;
      this.downloadResult = downloadResult;
      this.results = results;
      this.message = message;
   }

   @Generated
   public static MoviePtSubscribeSearchResponse.MoviePtSubscribeSearchResponseBuilder builder() {
      return new MoviePtSubscribeSearchResponse.MoviePtSubscribeSearchResponseBuilder();
   }

   @Generated
   public Long getSubscribeId() {
      return this.subscribeId;
   }

   @Generated
   public String getSubscribeName() {
      return this.subscribeName;
   }

   @Generated
   public String getKeyword() {
      return this.keyword;
   }

   @Generated
   public List<String> getSearchedKeywords() {
      return this.searchedKeywords;
   }

   @Generated
   public String getState() {
      return this.state;
   }

   @Generated
   public Integer getMatchedCount() {
      return this.matchedCount;
   }

   @Generated
   public Integer getTmdbLatestEpisode() {
      return this.tmdbLatestEpisode;
   }

   @Generated
   public Integer getStartEpisode() {
      return this.startEpisode;
   }

   @Generated
   public List<Integer> getDownloadedEpisodes() {
      return this.downloadedEpisodes;
   }

   @Generated
   public List<Integer> getMissingEpisodes() {
      return this.missingEpisodes;
   }

   @Generated
   public Boolean getAutoDownloadTriggered() {
      return this.autoDownloadTriggered;
   }

   @Generated
   public MoviePtSearchResult getSelectedResult() {
      return this.selectedResult;
   }

   @Generated
   public MovieActionResponse getDownloadResult() {
      return this.downloadResult;
   }

   @Generated
   public List<MoviePtSearchResult> getResults() {
      return this.results;
   }

   @Generated
   public String getMessage() {
      return this.message;
   }

   @Generated
   public void setSubscribeId(final Long subscribeId) {
      this.subscribeId = subscribeId;
   }

   @Generated
   public void setSubscribeName(final String subscribeName) {
      this.subscribeName = subscribeName;
   }

   @Generated
   public void setKeyword(final String keyword) {
      this.keyword = keyword;
   }

   @Generated
   public void setSearchedKeywords(final List<String> searchedKeywords) {
      this.searchedKeywords = searchedKeywords;
   }

   @Generated
   public void setState(final String state) {
      this.state = state;
   }

   @Generated
   public void setMatchedCount(final Integer matchedCount) {
      this.matchedCount = matchedCount;
   }

   @Generated
   public void setTmdbLatestEpisode(final Integer tmdbLatestEpisode) {
      this.tmdbLatestEpisode = tmdbLatestEpisode;
   }

   @Generated
   public void setStartEpisode(final Integer startEpisode) {
      this.startEpisode = startEpisode;
   }

   @Generated
   public void setDownloadedEpisodes(final List<Integer> downloadedEpisodes) {
      this.downloadedEpisodes = downloadedEpisodes;
   }

   @Generated
   public void setMissingEpisodes(final List<Integer> missingEpisodes) {
      this.missingEpisodes = missingEpisodes;
   }

   @Generated
   public void setAutoDownloadTriggered(final Boolean autoDownloadTriggered) {
      this.autoDownloadTriggered = autoDownloadTriggered;
   }

   @Generated
   public void setSelectedResult(final MoviePtSearchResult selectedResult) {
      this.selectedResult = selectedResult;
   }

   @Generated
   public void setDownloadResult(final MovieActionResponse downloadResult) {
      this.downloadResult = downloadResult;
   }

   @Generated
   public void setResults(final List<MoviePtSearchResult> results) {
      this.results = results;
   }

   @Generated
   public void setMessage(final String message) {
      this.message = message;
   }

   @Generated
   @Override
   public boolean equals(final Object o) {
      if (o == this) {
         return true;
      } else if (!(o instanceof MoviePtSubscribeSearchResponse other)) {
         return false;
      } else if (!other.canEqual(this)) {
         return false;
      } else {
         Object this$subscribeId = this.getSubscribeId();
         Object other$subscribeId = other.getSubscribeId();
         if (this$subscribeId == null ? other$subscribeId == null : this$subscribeId.equals(other$subscribeId)) {
            Object this$matchedCount = this.getMatchedCount();
            Object other$matchedCount = other.getMatchedCount();
            if (this$matchedCount == null ? other$matchedCount == null : this$matchedCount.equals(other$matchedCount)) {
               Object this$tmdbLatestEpisode = this.getTmdbLatestEpisode();
               Object other$tmdbLatestEpisode = other.getTmdbLatestEpisode();
               if (this$tmdbLatestEpisode == null ? other$tmdbLatestEpisode == null : this$tmdbLatestEpisode.equals(other$tmdbLatestEpisode)) {
                  Object this$startEpisode = this.getStartEpisode();
                  Object other$startEpisode = other.getStartEpisode();
                  if (this$startEpisode == null ? other$startEpisode == null : this$startEpisode.equals(other$startEpisode)) {
                     Object this$autoDownloadTriggered = this.getAutoDownloadTriggered();
                     Object other$autoDownloadTriggered = other.getAutoDownloadTriggered();
                     if (this$autoDownloadTriggered == null
                        ? other$autoDownloadTriggered == null
                        : this$autoDownloadTriggered.equals(other$autoDownloadTriggered)) {
                        Object this$subscribeName = this.getSubscribeName();
                        Object other$subscribeName = other.getSubscribeName();
                        if (this$subscribeName == null ? other$subscribeName == null : this$subscribeName.equals(other$subscribeName)) {
                           Object this$keyword = this.getKeyword();
                           Object other$keyword = other.getKeyword();
                           if (this$keyword == null ? other$keyword == null : this$keyword.equals(other$keyword)) {
                              Object this$searchedKeywords = this.getSearchedKeywords();
                              Object other$searchedKeywords = other.getSearchedKeywords();
                              if (this$searchedKeywords == null ? other$searchedKeywords == null : this$searchedKeywords.equals(other$searchedKeywords)) {
                                 Object this$state = this.getState();
                                 Object other$state = other.getState();
                                 if (this$state == null ? other$state == null : this$state.equals(other$state)) {
                                    Object this$downloadedEpisodes = this.getDownloadedEpisodes();
                                    Object other$downloadedEpisodes = other.getDownloadedEpisodes();
                                    if (this$downloadedEpisodes == null
                                       ? other$downloadedEpisodes == null
                                       : this$downloadedEpisodes.equals(other$downloadedEpisodes)) {
                                       Object this$missingEpisodes = this.getMissingEpisodes();
                                       Object other$missingEpisodes = other.getMissingEpisodes();
                                       if (this$missingEpisodes == null ? other$missingEpisodes == null : this$missingEpisodes.equals(other$missingEpisodes)) {
                                          Object this$selectedResult = this.getSelectedResult();
                                          Object other$selectedResult = other.getSelectedResult();
                                          if (this$selectedResult == null ? other$selectedResult == null : this$selectedResult.equals(other$selectedResult)) {
                                             Object this$downloadResult = this.getDownloadResult();
                                             Object other$downloadResult = other.getDownloadResult();
                                             if (this$downloadResult == null ? other$downloadResult == null : this$downloadResult.equals(other$downloadResult)) {
                                                Object this$results = this.getResults();
                                                Object other$results = other.getResults();
                                                if (this$results == null ? other$results == null : this$results.equals(other$results)) {
                                                   Object this$message = this.getMessage();
                                                   Object other$message = other.getMessage();
                                                   return this$message == null ? other$message == null : this$message.equals(other$message);
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
      return other instanceof MoviePtSubscribeSearchResponse;
   }

   @Generated
   @Override
   public int hashCode() {
      int PRIME = 59;
      int result = 1;
      Object $subscribeId = this.getSubscribeId();
      result = result * 59 + ($subscribeId == null ? 43 : $subscribeId.hashCode());
      Object $matchedCount = this.getMatchedCount();
      result = result * 59 + ($matchedCount == null ? 43 : $matchedCount.hashCode());
      Object $tmdbLatestEpisode = this.getTmdbLatestEpisode();
      result = result * 59 + ($tmdbLatestEpisode == null ? 43 : $tmdbLatestEpisode.hashCode());
      Object $startEpisode = this.getStartEpisode();
      result = result * 59 + ($startEpisode == null ? 43 : $startEpisode.hashCode());
      Object $autoDownloadTriggered = this.getAutoDownloadTriggered();
      result = result * 59 + ($autoDownloadTriggered == null ? 43 : $autoDownloadTriggered.hashCode());
      Object $subscribeName = this.getSubscribeName();
      result = result * 59 + ($subscribeName == null ? 43 : $subscribeName.hashCode());
      Object $keyword = this.getKeyword();
      result = result * 59 + ($keyword == null ? 43 : $keyword.hashCode());
      Object $searchedKeywords = this.getSearchedKeywords();
      result = result * 59 + ($searchedKeywords == null ? 43 : $searchedKeywords.hashCode());
      Object $state = this.getState();
      result = result * 59 + ($state == null ? 43 : $state.hashCode());
      Object $downloadedEpisodes = this.getDownloadedEpisodes();
      result = result * 59 + ($downloadedEpisodes == null ? 43 : $downloadedEpisodes.hashCode());
      Object $missingEpisodes = this.getMissingEpisodes();
      result = result * 59 + ($missingEpisodes == null ? 43 : $missingEpisodes.hashCode());
      Object $selectedResult = this.getSelectedResult();
      result = result * 59 + ($selectedResult == null ? 43 : $selectedResult.hashCode());
      Object $downloadResult = this.getDownloadResult();
      result = result * 59 + ($downloadResult == null ? 43 : $downloadResult.hashCode());
      Object $results = this.getResults();
      result = result * 59 + ($results == null ? 43 : $results.hashCode());
      Object $message = this.getMessage();
      return result * 59 + ($message == null ? 43 : $message.hashCode());
   }

   @Generated
   @Override
   public String toString() {
      return "MoviePtSubscribeSearchResponse(subscribeId="
         + this.getSubscribeId()
         + ", subscribeName="
         + this.getSubscribeName()
         + ", keyword="
         + this.getKeyword()
         + ", searchedKeywords="
         + this.getSearchedKeywords()
         + ", state="
         + this.getState()
         + ", matchedCount="
         + this.getMatchedCount()
         + ", tmdbLatestEpisode="
         + this.getTmdbLatestEpisode()
         + ", startEpisode="
         + this.getStartEpisode()
         + ", downloadedEpisodes="
         + this.getDownloadedEpisodes()
         + ", missingEpisodes="
         + this.getMissingEpisodes()
         + ", autoDownloadTriggered="
         + this.getAutoDownloadTriggered()
         + ", selectedResult="
         + this.getSelectedResult()
         + ", downloadResult="
         + this.getDownloadResult()
         + ", results="
         + this.getResults()
         + ", message="
         + this.getMessage()
         + ")";
   }

   @Generated
   public static class MoviePtSubscribeSearchResponseBuilder {
      @Generated
      private Long subscribeId;
      @Generated
      private String subscribeName;
      @Generated
      private String keyword;
      @Generated
      private List<String> searchedKeywords;
      @Generated
      private String state;
      @Generated
      private Integer matchedCount;
      @Generated
      private Integer tmdbLatestEpisode;
      @Generated
      private Integer startEpisode;
      @Generated
      private List<Integer> downloadedEpisodes;
      @Generated
      private List<Integer> missingEpisodes;
      @Generated
      private Boolean autoDownloadTriggered;
      @Generated
      private MoviePtSearchResult selectedResult;
      @Generated
      private MovieActionResponse downloadResult;
      @Generated
      private List<MoviePtSearchResult> results;
      @Generated
      private String message;

      @Generated
      MoviePtSubscribeSearchResponseBuilder() {
      }

      @Generated
      public MoviePtSubscribeSearchResponse.MoviePtSubscribeSearchResponseBuilder subscribeId(final Long subscribeId) {
         this.subscribeId = subscribeId;
         return this;
      }

      @Generated
      public MoviePtSubscribeSearchResponse.MoviePtSubscribeSearchResponseBuilder subscribeName(final String subscribeName) {
         this.subscribeName = subscribeName;
         return this;
      }

      @Generated
      public MoviePtSubscribeSearchResponse.MoviePtSubscribeSearchResponseBuilder keyword(final String keyword) {
         this.keyword = keyword;
         return this;
      }

      @Generated
      public MoviePtSubscribeSearchResponse.MoviePtSubscribeSearchResponseBuilder searchedKeywords(final List<String> searchedKeywords) {
         this.searchedKeywords = searchedKeywords;
         return this;
      }

      @Generated
      public MoviePtSubscribeSearchResponse.MoviePtSubscribeSearchResponseBuilder state(final String state) {
         this.state = state;
         return this;
      }

      @Generated
      public MoviePtSubscribeSearchResponse.MoviePtSubscribeSearchResponseBuilder matchedCount(final Integer matchedCount) {
         this.matchedCount = matchedCount;
         return this;
      }

      @Generated
      public MoviePtSubscribeSearchResponse.MoviePtSubscribeSearchResponseBuilder tmdbLatestEpisode(final Integer tmdbLatestEpisode) {
         this.tmdbLatestEpisode = tmdbLatestEpisode;
         return this;
      }

      @Generated
      public MoviePtSubscribeSearchResponse.MoviePtSubscribeSearchResponseBuilder startEpisode(final Integer startEpisode) {
         this.startEpisode = startEpisode;
         return this;
      }

      @Generated
      public MoviePtSubscribeSearchResponse.MoviePtSubscribeSearchResponseBuilder downloadedEpisodes(final List<Integer> downloadedEpisodes) {
         this.downloadedEpisodes = downloadedEpisodes;
         return this;
      }

      @Generated
      public MoviePtSubscribeSearchResponse.MoviePtSubscribeSearchResponseBuilder missingEpisodes(final List<Integer> missingEpisodes) {
         this.missingEpisodes = missingEpisodes;
         return this;
      }

      @Generated
      public MoviePtSubscribeSearchResponse.MoviePtSubscribeSearchResponseBuilder autoDownloadTriggered(final Boolean autoDownloadTriggered) {
         this.autoDownloadTriggered = autoDownloadTriggered;
         return this;
      }

      @Generated
      public MoviePtSubscribeSearchResponse.MoviePtSubscribeSearchResponseBuilder selectedResult(final MoviePtSearchResult selectedResult) {
         this.selectedResult = selectedResult;
         return this;
      }

      @Generated
      public MoviePtSubscribeSearchResponse.MoviePtSubscribeSearchResponseBuilder downloadResult(final MovieActionResponse downloadResult) {
         this.downloadResult = downloadResult;
         return this;
      }

      @Generated
      public MoviePtSubscribeSearchResponse.MoviePtSubscribeSearchResponseBuilder results(final List<MoviePtSearchResult> results) {
         this.results = results;
         return this;
      }

      @Generated
      public MoviePtSubscribeSearchResponse.MoviePtSubscribeSearchResponseBuilder message(final String message) {
         this.message = message;
         return this;
      }

      @Generated
      public MoviePtSubscribeSearchResponse build() {
         return new MoviePtSubscribeSearchResponse(
            this.subscribeId,
            this.subscribeName,
            this.keyword,
            this.searchedKeywords,
            this.state,
            this.matchedCount,
            this.tmdbLatestEpisode,
            this.startEpisode,
            this.downloadedEpisodes,
            this.missingEpisodes,
            this.autoDownloadTriggered,
            this.selectedResult,
            this.downloadResult,
            this.results,
            this.message
         );
      }

      @Generated
      @Override
      public String toString() {
         return "MoviePtSubscribeSearchResponse.MoviePtSubscribeSearchResponseBuilder(subscribeId="
            + this.subscribeId
            + ", subscribeName="
            + this.subscribeName
            + ", keyword="
            + this.keyword
            + ", searchedKeywords="
            + this.searchedKeywords
            + ", state="
            + this.state
            + ", matchedCount="
            + this.matchedCount
            + ", tmdbLatestEpisode="
            + this.tmdbLatestEpisode
            + ", startEpisode="
            + this.startEpisode
            + ", downloadedEpisodes="
            + this.downloadedEpisodes
            + ", missingEpisodes="
            + this.missingEpisodes
            + ", autoDownloadTriggered="
            + this.autoDownloadTriggered
            + ", selectedResult="
            + this.selectedResult
            + ", downloadResult="
            + this.downloadResult
            + ", results="
            + this.results
            + ", message="
            + this.message
            + ")";
      }
   }
}
