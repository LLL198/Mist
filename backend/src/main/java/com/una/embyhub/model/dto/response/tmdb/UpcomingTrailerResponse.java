package com.una.embyhub.model.dto.response.tmdb;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;
import java.util.List;
import lombok.Generated;

public class UpcomingTrailerResponse implements Serializable {
   @JsonProperty("page")
   private Integer page;
   @JsonProperty("total_pages")
   private Integer totalPages;
   @JsonProperty("total_results")
   private Integer totalResults;
   @JsonProperty("results")
   private List<UpcomingTrailerResponse.UpcomingMedia> results;

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
   public List<UpcomingTrailerResponse.UpcomingMedia> getResults() {
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
   public void setResults(final List<UpcomingTrailerResponse.UpcomingMedia> results) {
      this.results = results;
   }

   @Generated
   @Override
   public boolean equals(final Object o) {
      if (o == this) {
         return true;
      } else if (!(o instanceof UpcomingTrailerResponse other)) {
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
      return other instanceof UpcomingTrailerResponse;
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
      return "UpcomingTrailerResponse(page="
         + this.getPage()
         + ", totalPages="
         + this.getTotalPages()
         + ", totalResults="
         + this.getTotalResults()
         + ", results="
         + this.getResults()
         + ")";
   }

   public static class UpcomingMedia implements Serializable {
      @JsonProperty("id")
      private Long id;
      @JsonProperty("media_type")
      private String mediaType;
      @JsonProperty("title")
      private String title;
      @JsonProperty("name")
      private String name;
      @JsonProperty("original_title")
      private String originalTitle;
      @JsonProperty("original_name")
      private String originalName;
      @JsonProperty("overview")
      private String overview;
      @JsonProperty("poster_path")
      private String posterPath;
      @JsonProperty("backdrop_path")
      private String backdropPath;
      @JsonProperty("release_date")
      private String releaseDate;
      @JsonProperty("first_air_date")
      private String firstAirDate;
      @JsonProperty("genre_ids")
      private List<Integer> genreIds;
      @JsonProperty("original_language")
      private String originalLanguage;
      @JsonProperty("popularity")
      private Double popularity;
      @JsonProperty("vote_average")
      private Double voteAverage;
      @JsonProperty("vote_count")
      private Integer voteCount;
      @JsonProperty("adult")
      private Boolean adult;
      @JsonProperty("videos")
      private List<UpcomingTrailerResponse.VideoInfo> videos;

      @Generated
      public Long getId() {
         return this.id;
      }

      @Generated
      public String getMediaType() {
         return this.mediaType;
      }

      @Generated
      public String getTitle() {
         return this.title;
      }

      @Generated
      public String getName() {
         return this.name;
      }

      @Generated
      public String getOriginalTitle() {
         return this.originalTitle;
      }

      @Generated
      public String getOriginalName() {
         return this.originalName;
      }

      @Generated
      public String getOverview() {
         return this.overview;
      }

      @Generated
      public String getPosterPath() {
         return this.posterPath;
      }

      @Generated
      public String getBackdropPath() {
         return this.backdropPath;
      }

      @Generated
      public String getReleaseDate() {
         return this.releaseDate;
      }

      @Generated
      public String getFirstAirDate() {
         return this.firstAirDate;
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
      public Double getPopularity() {
         return this.popularity;
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
      public Boolean getAdult() {
         return this.adult;
      }

      @Generated
      public List<UpcomingTrailerResponse.VideoInfo> getVideos() {
         return this.videos;
      }

      @JsonProperty("id")
      @Generated
      public void setId(final Long id) {
         this.id = id;
      }

      @JsonProperty("media_type")
      @Generated
      public void setMediaType(final String mediaType) {
         this.mediaType = mediaType;
      }

      @JsonProperty("title")
      @Generated
      public void setTitle(final String title) {
         this.title = title;
      }

      @JsonProperty("name")
      @Generated
      public void setName(final String name) {
         this.name = name;
      }

      @JsonProperty("original_title")
      @Generated
      public void setOriginalTitle(final String originalTitle) {
         this.originalTitle = originalTitle;
      }

      @JsonProperty("original_name")
      @Generated
      public void setOriginalName(final String originalName) {
         this.originalName = originalName;
      }

      @JsonProperty("overview")
      @Generated
      public void setOverview(final String overview) {
         this.overview = overview;
      }

      @JsonProperty("poster_path")
      @Generated
      public void setPosterPath(final String posterPath) {
         this.posterPath = posterPath;
      }

      @JsonProperty("backdrop_path")
      @Generated
      public void setBackdropPath(final String backdropPath) {
         this.backdropPath = backdropPath;
      }

      @JsonProperty("release_date")
      @Generated
      public void setReleaseDate(final String releaseDate) {
         this.releaseDate = releaseDate;
      }

      @JsonProperty("first_air_date")
      @Generated
      public void setFirstAirDate(final String firstAirDate) {
         this.firstAirDate = firstAirDate;
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

      @JsonProperty("popularity")
      @Generated
      public void setPopularity(final Double popularity) {
         this.popularity = popularity;
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

      @JsonProperty("adult")
      @Generated
      public void setAdult(final Boolean adult) {
         this.adult = adult;
      }

      @JsonProperty("videos")
      @Generated
      public void setVideos(final List<UpcomingTrailerResponse.VideoInfo> videos) {
         this.videos = videos;
      }

      @Generated
      @Override
      public boolean equals(final Object o) {
         if (o == this) {
            return true;
         } else if (!(o instanceof UpcomingTrailerResponse.UpcomingMedia other)) {
            return false;
         } else if (!other.canEqual(this)) {
            return false;
         } else {
            Object this$id = this.getId();
            Object other$id = other.getId();
            if (this$id == null ? other$id == null : this$id.equals(other$id)) {
               Object this$popularity = this.getPopularity();
               Object other$popularity = other.getPopularity();
               if (this$popularity == null ? other$popularity == null : this$popularity.equals(other$popularity)) {
                  Object this$voteAverage = this.getVoteAverage();
                  Object other$voteAverage = other.getVoteAverage();
                  if (this$voteAverage == null ? other$voteAverage == null : this$voteAverage.equals(other$voteAverage)) {
                     Object this$voteCount = this.getVoteCount();
                     Object other$voteCount = other.getVoteCount();
                     if (this$voteCount == null ? other$voteCount == null : this$voteCount.equals(other$voteCount)) {
                        Object this$adult = this.getAdult();
                        Object other$adult = other.getAdult();
                        if (this$adult == null ? other$adult == null : this$adult.equals(other$adult)) {
                           Object this$mediaType = this.getMediaType();
                           Object other$mediaType = other.getMediaType();
                           if (this$mediaType == null ? other$mediaType == null : this$mediaType.equals(other$mediaType)) {
                              Object this$title = this.getTitle();
                              Object other$title = other.getTitle();
                              if (this$title == null ? other$title == null : this$title.equals(other$title)) {
                                 Object this$name = this.getName();
                                 Object other$name = other.getName();
                                 if (this$name == null ? other$name == null : this$name.equals(other$name)) {
                                    Object this$originalTitle = this.getOriginalTitle();
                                    Object other$originalTitle = other.getOriginalTitle();
                                    if (this$originalTitle == null ? other$originalTitle == null : this$originalTitle.equals(other$originalTitle)) {
                                       Object this$originalName = this.getOriginalName();
                                       Object other$originalName = other.getOriginalName();
                                       if (this$originalName == null ? other$originalName == null : this$originalName.equals(other$originalName)) {
                                          Object this$overview = this.getOverview();
                                          Object other$overview = other.getOverview();
                                          if (this$overview == null ? other$overview == null : this$overview.equals(other$overview)) {
                                             Object this$posterPath = this.getPosterPath();
                                             Object other$posterPath = other.getPosterPath();
                                             if (this$posterPath == null ? other$posterPath == null : this$posterPath.equals(other$posterPath)) {
                                                Object this$backdropPath = this.getBackdropPath();
                                                Object other$backdropPath = other.getBackdropPath();
                                                if (this$backdropPath == null ? other$backdropPath == null : this$backdropPath.equals(other$backdropPath)) {
                                                   Object this$releaseDate = this.getReleaseDate();
                                                   Object other$releaseDate = other.getReleaseDate();
                                                   if (this$releaseDate == null ? other$releaseDate == null : this$releaseDate.equals(other$releaseDate)) {
                                                      Object this$firstAirDate = this.getFirstAirDate();
                                                      Object other$firstAirDate = other.getFirstAirDate();
                                                      if (this$firstAirDate == null ? other$firstAirDate == null : this$firstAirDate.equals(other$firstAirDate)
                                                         )
                                                       {
                                                         Object this$genreIds = this.getGenreIds();
                                                         Object other$genreIds = other.getGenreIds();
                                                         if (this$genreIds == null ? other$genreIds == null : this$genreIds.equals(other$genreIds)) {
                                                            Object this$originalLanguage = this.getOriginalLanguage();
                                                            Object other$originalLanguage = other.getOriginalLanguage();
                                                            if (this$originalLanguage == null
                                                               ? other$originalLanguage == null
                                                               : this$originalLanguage.equals(other$originalLanguage)) {
                                                               Object this$videos = this.getVideos();
                                                               Object other$videos = other.getVideos();
                                                               return this$videos == null ? other$videos == null : this$videos.equals(other$videos);
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
         return other instanceof UpcomingTrailerResponse.UpcomingMedia;
      }

      @Generated
      @Override
      public int hashCode() {
         int PRIME = 59;
         int result = 1;
         Object $id = this.getId();
         result = result * 59 + ($id == null ? 43 : $id.hashCode());
         Object $popularity = this.getPopularity();
         result = result * 59 + ($popularity == null ? 43 : $popularity.hashCode());
         Object $voteAverage = this.getVoteAverage();
         result = result * 59 + ($voteAverage == null ? 43 : $voteAverage.hashCode());
         Object $voteCount = this.getVoteCount();
         result = result * 59 + ($voteCount == null ? 43 : $voteCount.hashCode());
         Object $adult = this.getAdult();
         result = result * 59 + ($adult == null ? 43 : $adult.hashCode());
         Object $mediaType = this.getMediaType();
         result = result * 59 + ($mediaType == null ? 43 : $mediaType.hashCode());
         Object $title = this.getTitle();
         result = result * 59 + ($title == null ? 43 : $title.hashCode());
         Object $name = this.getName();
         result = result * 59 + ($name == null ? 43 : $name.hashCode());
         Object $originalTitle = this.getOriginalTitle();
         result = result * 59 + ($originalTitle == null ? 43 : $originalTitle.hashCode());
         Object $originalName = this.getOriginalName();
         result = result * 59 + ($originalName == null ? 43 : $originalName.hashCode());
         Object $overview = this.getOverview();
         result = result * 59 + ($overview == null ? 43 : $overview.hashCode());
         Object $posterPath = this.getPosterPath();
         result = result * 59 + ($posterPath == null ? 43 : $posterPath.hashCode());
         Object $backdropPath = this.getBackdropPath();
         result = result * 59 + ($backdropPath == null ? 43 : $backdropPath.hashCode());
         Object $releaseDate = this.getReleaseDate();
         result = result * 59 + ($releaseDate == null ? 43 : $releaseDate.hashCode());
         Object $firstAirDate = this.getFirstAirDate();
         result = result * 59 + ($firstAirDate == null ? 43 : $firstAirDate.hashCode());
         Object $genreIds = this.getGenreIds();
         result = result * 59 + ($genreIds == null ? 43 : $genreIds.hashCode());
         Object $originalLanguage = this.getOriginalLanguage();
         result = result * 59 + ($originalLanguage == null ? 43 : $originalLanguage.hashCode());
         Object $videos = this.getVideos();
         return result * 59 + ($videos == null ? 43 : $videos.hashCode());
      }

      @Generated
      @Override
      public String toString() {
         return "UpcomingTrailerResponse.UpcomingMedia(id="
            + this.getId()
            + ", mediaType="
            + this.getMediaType()
            + ", title="
            + this.getTitle()
            + ", name="
            + this.getName()
            + ", originalTitle="
            + this.getOriginalTitle()
            + ", originalName="
            + this.getOriginalName()
            + ", overview="
            + this.getOverview()
            + ", posterPath="
            + this.getPosterPath()
            + ", backdropPath="
            + this.getBackdropPath()
            + ", releaseDate="
            + this.getReleaseDate()
            + ", firstAirDate="
            + this.getFirstAirDate()
            + ", genreIds="
            + this.getGenreIds()
            + ", originalLanguage="
            + this.getOriginalLanguage()
            + ", popularity="
            + this.getPopularity()
            + ", voteAverage="
            + this.getVoteAverage()
            + ", voteCount="
            + this.getVoteCount()
            + ", adult="
            + this.getAdult()
            + ", videos="
            + this.getVideos()
            + ")";
      }
   }

   public static class VideoInfo implements Serializable {
      @JsonProperty("id")
      private String id;
      @JsonProperty("name")
      private String name;
      @JsonProperty("key")
      private String key;
      @JsonProperty("site")
      private String site;
      @JsonProperty("type")
      private String type;
      @JsonProperty("official")
      private Boolean official;
      @JsonProperty("published_at")
      private String publishedAt;
      @JsonProperty("size")
      private Integer size;
      @JsonProperty("iso_639_1")
      private String iso6391;
      @JsonProperty("iso_3166_1")
      private String iso31661;

      public String getYoutubeUrl() {
         return "YouTube".equalsIgnoreCase(this.site) && this.key != null ? "https://www.youtube.com/watch?v=" + this.key : null;
      }

      public String getYoutubeEmbedUrl() {
         return "YouTube".equalsIgnoreCase(this.site) && this.key != null ? "https://www.youtube.com/embed/" + this.key : null;
      }

      @Generated
      public String getId() {
         return this.id;
      }

      @Generated
      public String getName() {
         return this.name;
      }

      @Generated
      public String getKey() {
         return this.key;
      }

      @Generated
      public String getSite() {
         return this.site;
      }

      @Generated
      public String getType() {
         return this.type;
      }

      @Generated
      public Boolean getOfficial() {
         return this.official;
      }

      @Generated
      public String getPublishedAt() {
         return this.publishedAt;
      }

      @Generated
      public Integer getSize() {
         return this.size;
      }

      @Generated
      public String getIso6391() {
         return this.iso6391;
      }

      @Generated
      public String getIso31661() {
         return this.iso31661;
      }

      @JsonProperty("id")
      @Generated
      public void setId(final String id) {
         this.id = id;
      }

      @JsonProperty("name")
      @Generated
      public void setName(final String name) {
         this.name = name;
      }

      @JsonProperty("key")
      @Generated
      public void setKey(final String key) {
         this.key = key;
      }

      @JsonProperty("site")
      @Generated
      public void setSite(final String site) {
         this.site = site;
      }

      @JsonProperty("type")
      @Generated
      public void setType(final String type) {
         this.type = type;
      }

      @JsonProperty("official")
      @Generated
      public void setOfficial(final Boolean official) {
         this.official = official;
      }

      @JsonProperty("published_at")
      @Generated
      public void setPublishedAt(final String publishedAt) {
         this.publishedAt = publishedAt;
      }

      @JsonProperty("size")
      @Generated
      public void setSize(final Integer size) {
         this.size = size;
      }

      @JsonProperty("iso_639_1")
      @Generated
      public void setIso6391(final String iso6391) {
         this.iso6391 = iso6391;
      }

      @JsonProperty("iso_3166_1")
      @Generated
      public void setIso31661(final String iso31661) {
         this.iso31661 = iso31661;
      }

      @Generated
      @Override
      public boolean equals(final Object o) {
         if (o == this) {
            return true;
         } else if (!(o instanceof UpcomingTrailerResponse.VideoInfo other)) {
            return false;
         } else if (!other.canEqual(this)) {
            return false;
         } else {
            Object this$official = this.getOfficial();
            Object other$official = other.getOfficial();
            if (this$official == null ? other$official == null : this$official.equals(other$official)) {
               Object this$size = this.getSize();
               Object other$size = other.getSize();
               if (this$size == null ? other$size == null : this$size.equals(other$size)) {
                  Object this$id = this.getId();
                  Object other$id = other.getId();
                  if (this$id == null ? other$id == null : this$id.equals(other$id)) {
                     Object this$name = this.getName();
                     Object other$name = other.getName();
                     if (this$name == null ? other$name == null : this$name.equals(other$name)) {
                        Object this$key = this.getKey();
                        Object other$key = other.getKey();
                        if (this$key == null ? other$key == null : this$key.equals(other$key)) {
                           Object this$site = this.getSite();
                           Object other$site = other.getSite();
                           if (this$site == null ? other$site == null : this$site.equals(other$site)) {
                              Object this$type = this.getType();
                              Object other$type = other.getType();
                              if (this$type == null ? other$type == null : this$type.equals(other$type)) {
                                 Object this$publishedAt = this.getPublishedAt();
                                 Object other$publishedAt = other.getPublishedAt();
                                 if (this$publishedAt == null ? other$publishedAt == null : this$publishedAt.equals(other$publishedAt)) {
                                    Object this$iso6391 = this.getIso6391();
                                    Object other$iso6391 = other.getIso6391();
                                    if (this$iso6391 == null ? other$iso6391 == null : this$iso6391.equals(other$iso6391)) {
                                       Object this$iso31661 = this.getIso31661();
                                       Object other$iso31661 = other.getIso31661();
                                       return this$iso31661 == null ? other$iso31661 == null : this$iso31661.equals(other$iso31661);
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
         return other instanceof UpcomingTrailerResponse.VideoInfo;
      }

      @Generated
      @Override
      public int hashCode() {
         int PRIME = 59;
         int result = 1;
         Object $official = this.getOfficial();
         result = result * 59 + ($official == null ? 43 : $official.hashCode());
         Object $size = this.getSize();
         result = result * 59 + ($size == null ? 43 : $size.hashCode());
         Object $id = this.getId();
         result = result * 59 + ($id == null ? 43 : $id.hashCode());
         Object $name = this.getName();
         result = result * 59 + ($name == null ? 43 : $name.hashCode());
         Object $key = this.getKey();
         result = result * 59 + ($key == null ? 43 : $key.hashCode());
         Object $site = this.getSite();
         result = result * 59 + ($site == null ? 43 : $site.hashCode());
         Object $type = this.getType();
         result = result * 59 + ($type == null ? 43 : $type.hashCode());
         Object $publishedAt = this.getPublishedAt();
         result = result * 59 + ($publishedAt == null ? 43 : $publishedAt.hashCode());
         Object $iso6391 = this.getIso6391();
         result = result * 59 + ($iso6391 == null ? 43 : $iso6391.hashCode());
         Object $iso31661 = this.getIso31661();
         return result * 59 + ($iso31661 == null ? 43 : $iso31661.hashCode());
      }

      @Generated
      @Override
      public String toString() {
         return "UpcomingTrailerResponse.VideoInfo(id="
            + this.getId()
            + ", name="
            + this.getName()
            + ", key="
            + this.getKey()
            + ", site="
            + this.getSite()
            + ", type="
            + this.getType()
            + ", official="
            + this.getOfficial()
            + ", publishedAt="
            + this.getPublishedAt()
            + ", size="
            + this.getSize()
            + ", iso6391="
            + this.getIso6391()
            + ", iso31661="
            + this.getIso31661()
            + ")";
      }
   }
}
