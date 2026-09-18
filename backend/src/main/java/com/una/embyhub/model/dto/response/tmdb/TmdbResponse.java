package com.una.embyhub.model.dto.response.tmdb;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;
import java.util.List;
import lombok.Generated;

public class TmdbResponse implements Serializable {
   @JsonProperty("page")
   private Integer page;
   @JsonProperty("total_pages")
   private Integer totalPages;
   @JsonProperty("total_results")
   private Integer totalResults;
   @JsonProperty("results")
   private List<TmdbResponse.Result> results;

   @Generated
   public Integer getPage() {
      return this.page;
   }

   @Generated
   public Integer getTotalPages() {
      return this.totalPages;
   }

   @Generated
   public Integer getTotalResults() {
      return this.totalResults;
   }

   @Generated
   public List<TmdbResponse.Result> getResults() {
      return this.results;
   }

   @JsonProperty("page")
   @Generated
   public void setPage(final Integer page) {
      this.page = page;
   }

   @JsonProperty("total_pages")
   @Generated
   public void setTotalPages(final Integer totalPages) {
      this.totalPages = totalPages;
   }

   @JsonProperty("total_results")
   @Generated
   public void setTotalResults(final Integer totalResults) {
      this.totalResults = totalResults;
   }

   @JsonProperty("results")
   @Generated
   public void setResults(final List<TmdbResponse.Result> results) {
      this.results = results;
   }

   @Generated
   @Override
   public boolean equals(final Object o) {
      if (o == this) {
         return true;
      } else if (!(o instanceof TmdbResponse other)) {
         return false;
      } else if (!other.canEqual(this)) {
         return false;
      } else {
         Object this$page = this.getPage();
         Object other$page = other.getPage();
         if (this$page == null ? other$page == null : this$page.equals(other$page)) {
            Object this$totalPages = this.getTotalPages();
            Object other$totalPages = other.getTotalPages();
            if (this$totalPages == null ? other$totalPages == null : this$totalPages.equals(other$totalPages)) {
               Object this$totalResults = this.getTotalResults();
               Object other$totalResults = other.getTotalResults();
               if (this$totalResults == null ? other$totalResults == null : this$totalResults.equals(other$totalResults)) {
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
      }
   }

   @Generated
   protected boolean canEqual(final Object other) {
      return other instanceof TmdbResponse;
   }

   @Generated
   @Override
   public int hashCode() {
      int PRIME = 59;
      int result = 1;
      Object $page = this.getPage();
      result = result * 59 + ($page == null ? 43 : $page.hashCode());
      Object $totalPages = this.getTotalPages();
      result = result * 59 + ($totalPages == null ? 43 : $totalPages.hashCode());
      Object $totalResults = this.getTotalResults();
      result = result * 59 + ($totalResults == null ? 43 : $totalResults.hashCode());
      Object $results = this.getResults();
      return result * 59 + ($results == null ? 43 : $results.hashCode());
   }

   @Generated
   @Override
   public String toString() {
      return "TmdbResponse(page="
         + this.getPage()
         + ", totalPages="
         + this.getTotalPages()
         + ", totalResults="
         + this.getTotalResults()
         + ", results="
         + this.getResults()
         + ")";
   }

   @Generated
   public TmdbResponse() {
   }

   @Generated
   public TmdbResponse(final Integer page, final Integer totalPages, final Integer totalResults, final List<TmdbResponse.Result> results) {
      this.page = page;
      this.totalPages = totalPages;
      this.totalResults = totalResults;
      this.results = results;
   }

   public static class Result implements Serializable {
      @JsonProperty("media_type")
      private String mediaType;
      @JsonProperty("id")
      private int id;
      @JsonProperty("adult")
      private Boolean adult;
      @JsonProperty("backdrop_path")
      private String backdropPath;
      @JsonProperty("genre_ids")
      private List<Integer> genreIds;
      @JsonProperty("original_language")
      private String originalLanguage;
      @JsonProperty("overview")
      private String overview;
      @JsonProperty("popularity")
      private Double popularity;
      @JsonProperty("poster_path")
      private String posterPath;
      @JsonProperty("vote_average")
      private Double voteAverage;
      @JsonProperty("vote_count")
      private Integer voteCount;
      @JsonProperty("original_title")
      private String originalTitle;
      @JsonProperty("title")
      private String title;
      @JsonProperty("video")
      private Boolean video;
      @JsonProperty("release_date")
      private String releaseDate;
      @JsonProperty("origin_country")
      private List<String> originCountry;
      @JsonProperty("original_name")
      private String originalName;
      @JsonProperty("name")
      private String name;
      @JsonProperty("first_air_date")
      private String firstAirDate;
      private Boolean isExsit;
      private Boolean isSubmitted;

      @Generated
      public String getMediaType() {
         return this.mediaType;
      }

      @Generated
      public int getId() {
         return this.id;
      }

      @Generated
      public Boolean getAdult() {
         return this.adult;
      }

      @Generated
      public String getBackdropPath() {
         return this.backdropPath;
      }

      @Generated
      public List<Integer> getGenreIds() {
         return this.genreIds;
      }

      @Generated
      public String getOriginalLanguage() {
         return this.originalLanguage;
      }

      @Generated
      public String getOverview() {
         return this.overview;
      }

      @Generated
      public Double getPopularity() {
         return this.popularity;
      }

      @Generated
      public String getPosterPath() {
         return this.posterPath;
      }

      @Generated
      public Double getVoteAverage() {
         return this.voteAverage;
      }

      @Generated
      public Integer getVoteCount() {
         return this.voteCount;
      }

      @Generated
      public String getOriginalTitle() {
         return this.originalTitle;
      }

      @Generated
      public String getTitle() {
         return this.title;
      }

      @Generated
      public Boolean getVideo() {
         return this.video;
      }

      @Generated
      public String getReleaseDate() {
         return this.releaseDate;
      }

      @Generated
      public List<String> getOriginCountry() {
         return this.originCountry;
      }

      @Generated
      public String getOriginalName() {
         return this.originalName;
      }

      @Generated
      public String getName() {
         return this.name;
      }

      @Generated
      public String getFirstAirDate() {
         return this.firstAirDate;
      }

      @Generated
      public Boolean getIsExsit() {
         return this.isExsit;
      }

      @Generated
      public Boolean getIsSubmitted() {
         return this.isSubmitted;
      }

      @JsonProperty("media_type")
      @Generated
      public void setMediaType(final String mediaType) {
         this.mediaType = mediaType;
      }

      @JsonProperty("id")
      @Generated
      public void setId(final int id) {
         this.id = id;
      }

      @JsonProperty("adult")
      @Generated
      public void setAdult(final Boolean adult) {
         this.adult = adult;
      }

      @JsonProperty("backdrop_path")
      @Generated
      public void setBackdropPath(final String backdropPath) {
         this.backdropPath = backdropPath;
      }

      @JsonProperty("genre_ids")
      @Generated
      public void setGenreIds(final List<Integer> genreIds) {
         this.genreIds = genreIds;
      }

      @JsonProperty("original_language")
      @Generated
      public void setOriginalLanguage(final String originalLanguage) {
         this.originalLanguage = originalLanguage;
      }

      @JsonProperty("overview")
      @Generated
      public void setOverview(final String overview) {
         this.overview = overview;
      }

      @JsonProperty("popularity")
      @Generated
      public void setPopularity(final Double popularity) {
         this.popularity = popularity;
      }

      @JsonProperty("poster_path")
      @Generated
      public void setPosterPath(final String posterPath) {
         this.posterPath = posterPath;
      }

      @JsonProperty("vote_average")
      @Generated
      public void setVoteAverage(final Double voteAverage) {
         this.voteAverage = voteAverage;
      }

      @JsonProperty("vote_count")
      @Generated
      public void setVoteCount(final Integer voteCount) {
         this.voteCount = voteCount;
      }

      @JsonProperty("original_title")
      @Generated
      public void setOriginalTitle(final String originalTitle) {
         this.originalTitle = originalTitle;
      }

      @JsonProperty("title")
      @Generated
      public void setTitle(final String title) {
         this.title = title;
      }

      @JsonProperty("video")
      @Generated
      public void setVideo(final Boolean video) {
         this.video = video;
      }

      @JsonProperty("release_date")
      @Generated
      public void setReleaseDate(final String releaseDate) {
         this.releaseDate = releaseDate;
      }

      @JsonProperty("origin_country")
      @Generated
      public void setOriginCountry(final List<String> originCountry) {
         this.originCountry = originCountry;
      }

      @JsonProperty("original_name")
      @Generated
      public void setOriginalName(final String originalName) {
         this.originalName = originalName;
      }

      @JsonProperty("name")
      @Generated
      public void setName(final String name) {
         this.name = name;
      }

      @JsonProperty("first_air_date")
      @Generated
      public void setFirstAirDate(final String firstAirDate) {
         this.firstAirDate = firstAirDate;
      }

      @Generated
      public void setIsExsit(final Boolean isExsit) {
         this.isExsit = isExsit;
      }

      @Generated
      public void setIsSubmitted(final Boolean isSubmitted) {
         this.isSubmitted = isSubmitted;
      }

      @Generated
      @Override
      public boolean equals(final Object o) {
         if (o == this) {
            return true;
         } else if (!(o instanceof TmdbResponse.Result other)) {
            return false;
         } else if (!other.canEqual(this)) {
            return false;
         } else if (this.getId() != other.getId()) {
            return false;
         } else {
            Object this$adult = this.getAdult();
            Object other$adult = other.getAdult();
            if (this$adult == null ? other$adult == null : this$adult.equals(other$adult)) {
               Object this$popularity = this.getPopularity();
               Object other$popularity = other.getPopularity();
               if (this$popularity == null ? other$popularity == null : this$popularity.equals(other$popularity)) {
                  Object this$voteAverage = this.getVoteAverage();
                  Object other$voteAverage = other.getVoteAverage();
                  if (this$voteAverage == null ? other$voteAverage == null : this$voteAverage.equals(other$voteAverage)) {
                     Object this$voteCount = this.getVoteCount();
                     Object other$voteCount = other.getVoteCount();
                     if (this$voteCount == null ? other$voteCount == null : this$voteCount.equals(other$voteCount)) {
                        Object this$video = this.getVideo();
                        Object other$video = other.getVideo();
                        if (this$video == null ? other$video == null : this$video.equals(other$video)) {
                           Object this$isExsit = this.getIsExsit();
                           Object other$isExsit = other.getIsExsit();
                           if (this$isExsit == null ? other$isExsit == null : this$isExsit.equals(other$isExsit)) {
                              Object this$isSubmitted = this.getIsSubmitted();
                              Object other$isSubmitted = other.getIsSubmitted();
                              if (this$isSubmitted == null ? other$isSubmitted == null : this$isSubmitted.equals(other$isSubmitted)) {
                                 Object this$mediaType = this.getMediaType();
                                 Object other$mediaType = other.getMediaType();
                                 if (this$mediaType == null ? other$mediaType == null : this$mediaType.equals(other$mediaType)) {
                                    Object this$backdropPath = this.getBackdropPath();
                                    Object other$backdropPath = other.getBackdropPath();
                                    if (this$backdropPath == null ? other$backdropPath == null : this$backdropPath.equals(other$backdropPath)) {
                                       Object this$genreIds = this.getGenreIds();
                                       Object other$genreIds = other.getGenreIds();
                                       if (this$genreIds == null ? other$genreIds == null : this$genreIds.equals(other$genreIds)) {
                                          Object this$originalLanguage = this.getOriginalLanguage();
                                          Object other$originalLanguage = other.getOriginalLanguage();
                                          if (this$originalLanguage == null
                                             ? other$originalLanguage == null
                                             : this$originalLanguage.equals(other$originalLanguage)) {
                                             Object this$overview = this.getOverview();
                                             Object other$overview = other.getOverview();
                                             if (this$overview == null ? other$overview == null : this$overview.equals(other$overview)) {
                                                Object this$posterPath = this.getPosterPath();
                                                Object other$posterPath = other.getPosterPath();
                                                if (this$posterPath == null ? other$posterPath == null : this$posterPath.equals(other$posterPath)) {
                                                   Object this$originalTitle = this.getOriginalTitle();
                                                   Object other$originalTitle = other.getOriginalTitle();
                                                   if (this$originalTitle == null
                                                      ? other$originalTitle == null
                                                      : this$originalTitle.equals(other$originalTitle)) {
                                                      Object this$title = this.getTitle();
                                                      Object other$title = other.getTitle();
                                                      if (this$title == null ? other$title == null : this$title.equals(other$title)) {
                                                         Object this$releaseDate = this.getReleaseDate();
                                                         Object other$releaseDate = other.getReleaseDate();
                                                         if (this$releaseDate == null ? other$releaseDate == null : this$releaseDate.equals(other$releaseDate)) {
                                                            Object this$originCountry = this.getOriginCountry();
                                                            Object other$originCountry = other.getOriginCountry();
                                                            if (this$originCountry == null
                                                               ? other$originCountry == null
                                                               : this$originCountry.equals(other$originCountry)) {
                                                               Object this$originalName = this.getOriginalName();
                                                               Object other$originalName = other.getOriginalName();
                                                               if (this$originalName == null
                                                                  ? other$originalName == null
                                                                  : this$originalName.equals(other$originalName)) {
                                                                  Object this$name = this.getName();
                                                                  Object other$name = other.getName();
                                                                  if (this$name == null ? other$name == null : this$name.equals(other$name)) {
                                                                     Object this$firstAirDate = this.getFirstAirDate();
                                                                     Object other$firstAirDate = other.getFirstAirDate();
                                                                     return this$firstAirDate == null
                                                                        ? other$firstAirDate == null
                                                                        : this$firstAirDate.equals(other$firstAirDate);
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
         return other instanceof TmdbResponse.Result;
      }

      @Generated
      @Override
      public int hashCode() {
         int PRIME = 59;
         int result = 1;
         result = result * 59 + this.getId();
         Object $adult = this.getAdult();
         result = result * 59 + ($adult == null ? 43 : $adult.hashCode());
         Object $popularity = this.getPopularity();
         result = result * 59 + ($popularity == null ? 43 : $popularity.hashCode());
         Object $voteAverage = this.getVoteAverage();
         result = result * 59 + ($voteAverage == null ? 43 : $voteAverage.hashCode());
         Object $voteCount = this.getVoteCount();
         result = result * 59 + ($voteCount == null ? 43 : $voteCount.hashCode());
         Object $video = this.getVideo();
         result = result * 59 + ($video == null ? 43 : $video.hashCode());
         Object $isExsit = this.getIsExsit();
         result = result * 59 + ($isExsit == null ? 43 : $isExsit.hashCode());
         Object $isSubmitted = this.getIsSubmitted();
         result = result * 59 + ($isSubmitted == null ? 43 : $isSubmitted.hashCode());
         Object $mediaType = this.getMediaType();
         result = result * 59 + ($mediaType == null ? 43 : $mediaType.hashCode());
         Object $backdropPath = this.getBackdropPath();
         result = result * 59 + ($backdropPath == null ? 43 : $backdropPath.hashCode());
         Object $genreIds = this.getGenreIds();
         result = result * 59 + ($genreIds == null ? 43 : $genreIds.hashCode());
         Object $originalLanguage = this.getOriginalLanguage();
         result = result * 59 + ($originalLanguage == null ? 43 : $originalLanguage.hashCode());
         Object $overview = this.getOverview();
         result = result * 59 + ($overview == null ? 43 : $overview.hashCode());
         Object $posterPath = this.getPosterPath();
         result = result * 59 + ($posterPath == null ? 43 : $posterPath.hashCode());
         Object $originalTitle = this.getOriginalTitle();
         result = result * 59 + ($originalTitle == null ? 43 : $originalTitle.hashCode());
         Object $title = this.getTitle();
         result = result * 59 + ($title == null ? 43 : $title.hashCode());
         Object $releaseDate = this.getReleaseDate();
         result = result * 59 + ($releaseDate == null ? 43 : $releaseDate.hashCode());
         Object $originCountry = this.getOriginCountry();
         result = result * 59 + ($originCountry == null ? 43 : $originCountry.hashCode());
         Object $originalName = this.getOriginalName();
         result = result * 59 + ($originalName == null ? 43 : $originalName.hashCode());
         Object $name = this.getName();
         result = result * 59 + ($name == null ? 43 : $name.hashCode());
         Object $firstAirDate = this.getFirstAirDate();
         return result * 59 + ($firstAirDate == null ? 43 : $firstAirDate.hashCode());
      }

      @Generated
      @Override
      public String toString() {
         return "TmdbResponse.Result(mediaType="
            + this.getMediaType()
            + ", id="
            + this.getId()
            + ", adult="
            + this.getAdult()
            + ", backdropPath="
            + this.getBackdropPath()
            + ", genreIds="
            + this.getGenreIds()
            + ", originalLanguage="
            + this.getOriginalLanguage()
            + ", overview="
            + this.getOverview()
            + ", popularity="
            + this.getPopularity()
            + ", posterPath="
            + this.getPosterPath()
            + ", voteAverage="
            + this.getVoteAverage()
            + ", voteCount="
            + this.getVoteCount()
            + ", originalTitle="
            + this.getOriginalTitle()
            + ", title="
            + this.getTitle()
            + ", video="
            + this.getVideo()
            + ", releaseDate="
            + this.getReleaseDate()
            + ", originCountry="
            + this.getOriginCountry()
            + ", originalName="
            + this.getOriginalName()
            + ", name="
            + this.getName()
            + ", firstAirDate="
            + this.getFirstAirDate()
            + ", isExsit="
            + this.getIsExsit()
            + ", isSubmitted="
            + this.getIsSubmitted()
            + ")";
      }

      @Generated
      public Result() {
      }

      @Generated
      public Result(
         final String mediaType,
         final int id,
         final Boolean adult,
         final String backdropPath,
         final List<Integer> genreIds,
         final String originalLanguage,
         final String overview,
         final Double popularity,
         final String posterPath,
         final Double voteAverage,
         final Integer voteCount,
         final String originalTitle,
         final String title,
         final Boolean video,
         final String releaseDate,
         final List<String> originCountry,
         final String originalName,
         final String name,
         final String firstAirDate,
         final Boolean isExsit,
         final Boolean isSubmitted
      ) {
         this.mediaType = mediaType;
         this.id = id;
         this.adult = adult;
         this.backdropPath = backdropPath;
         this.genreIds = genreIds;
         this.originalLanguage = originalLanguage;
         this.overview = overview;
         this.popularity = popularity;
         this.posterPath = posterPath;
         this.voteAverage = voteAverage;
         this.voteCount = voteCount;
         this.originalTitle = originalTitle;
         this.title = title;
         this.video = video;
         this.releaseDate = releaseDate;
         this.originCountry = originCountry;
         this.originalName = originalName;
         this.name = name;
         this.firstAirDate = firstAirDate;
         this.isExsit = isExsit;
         this.isSubmitted = isSubmitted;
      }
   }
}
