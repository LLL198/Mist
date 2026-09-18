package com.una.embyhub.movie.model;

import lombok.Generated;

public class MovieDownloadRecordFileResponse {
   private Long id;
   private Long recordId;
   private String sourceFilePath;
   private String linkFilePath;
   private String episodeCodes;
   private String episodeSeqs;

   @Generated
   MovieDownloadRecordFileResponse(
      final Long id, final Long recordId, final String sourceFilePath, final String linkFilePath, final String episodeCodes, final String episodeSeqs
   ) {
      this.id = id;
      this.recordId = recordId;
      this.sourceFilePath = sourceFilePath;
      this.linkFilePath = linkFilePath;
      this.episodeCodes = episodeCodes;
      this.episodeSeqs = episodeSeqs;
   }

   @Generated
   public static MovieDownloadRecordFileResponse.MovieDownloadRecordFileResponseBuilder builder() {
      return new MovieDownloadRecordFileResponse.MovieDownloadRecordFileResponseBuilder();
   }

   @Generated
   public Long getId() {
      return this.id;
   }

   @Generated
   public Long getRecordId() {
      return this.recordId;
   }

   @Generated
   public String getSourceFilePath() {
      return this.sourceFilePath;
   }

   @Generated
   public String getLinkFilePath() {
      return this.linkFilePath;
   }

   @Generated
   public String getEpisodeCodes() {
      return this.episodeCodes;
   }

   @Generated
   public String getEpisodeSeqs() {
      return this.episodeSeqs;
   }

   @Generated
   public void setId(final Long id) {
      this.id = id;
   }

   @Generated
   public void setRecordId(final Long recordId) {
      this.recordId = recordId;
   }

   @Generated
   public void setSourceFilePath(final String sourceFilePath) {
      this.sourceFilePath = sourceFilePath;
   }

   @Generated
   public void setLinkFilePath(final String linkFilePath) {
      this.linkFilePath = linkFilePath;
   }

   @Generated
   public void setEpisodeCodes(final String episodeCodes) {
      this.episodeCodes = episodeCodes;
   }

   @Generated
   public void setEpisodeSeqs(final String episodeSeqs) {
      this.episodeSeqs = episodeSeqs;
   }

   @Generated
   @Override
   public boolean equals(final Object o) {
      if (o == this) {
         return true;
      } else if (!(o instanceof MovieDownloadRecordFileResponse other)) {
         return false;
      } else if (!other.canEqual(this)) {
         return false;
      } else {
         Object this$id = this.getId();
         Object other$id = other.getId();
         if (this$id == null ? other$id == null : this$id.equals(other$id)) {
            Object this$recordId = this.getRecordId();
            Object other$recordId = other.getRecordId();
            if (this$recordId == null ? other$recordId == null : this$recordId.equals(other$recordId)) {
               Object this$sourceFilePath = this.getSourceFilePath();
               Object other$sourceFilePath = other.getSourceFilePath();
               if (this$sourceFilePath == null ? other$sourceFilePath == null : this$sourceFilePath.equals(other$sourceFilePath)) {
                  Object this$linkFilePath = this.getLinkFilePath();
                  Object other$linkFilePath = other.getLinkFilePath();
                  if (this$linkFilePath == null ? other$linkFilePath == null : this$linkFilePath.equals(other$linkFilePath)) {
                     Object this$episodeCodes = this.getEpisodeCodes();
                     Object other$episodeCodes = other.getEpisodeCodes();
                     if (this$episodeCodes == null ? other$episodeCodes == null : this$episodeCodes.equals(other$episodeCodes)) {
                        Object this$episodeSeqs = this.getEpisodeSeqs();
                        Object other$episodeSeqs = other.getEpisodeSeqs();
                        return this$episodeSeqs == null ? other$episodeSeqs == null : this$episodeSeqs.equals(other$episodeSeqs);
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
      return other instanceof MovieDownloadRecordFileResponse;
   }

   @Generated
   @Override
   public int hashCode() {
      int PRIME = 59;
      int result = 1;
      Object $id = this.getId();
      result = result * 59 + ($id == null ? 43 : $id.hashCode());
      Object $recordId = this.getRecordId();
      result = result * 59 + ($recordId == null ? 43 : $recordId.hashCode());
      Object $sourceFilePath = this.getSourceFilePath();
      result = result * 59 + ($sourceFilePath == null ? 43 : $sourceFilePath.hashCode());
      Object $linkFilePath = this.getLinkFilePath();
      result = result * 59 + ($linkFilePath == null ? 43 : $linkFilePath.hashCode());
      Object $episodeCodes = this.getEpisodeCodes();
      result = result * 59 + ($episodeCodes == null ? 43 : $episodeCodes.hashCode());
      Object $episodeSeqs = this.getEpisodeSeqs();
      return result * 59 + ($episodeSeqs == null ? 43 : $episodeSeqs.hashCode());
   }

   @Generated
   @Override
   public String toString() {
      return "MovieDownloadRecordFileResponse(id="
         + this.getId()
         + ", recordId="
         + this.getRecordId()
         + ", sourceFilePath="
         + this.getSourceFilePath()
         + ", linkFilePath="
         + this.getLinkFilePath()
         + ", episodeCodes="
         + this.getEpisodeCodes()
         + ", episodeSeqs="
         + this.getEpisodeSeqs()
         + ")";
   }

   @Generated
   public static class MovieDownloadRecordFileResponseBuilder {
      @Generated
      private Long id;
      @Generated
      private Long recordId;
      @Generated
      private String sourceFilePath;
      @Generated
      private String linkFilePath;
      @Generated
      private String episodeCodes;
      @Generated
      private String episodeSeqs;

      @Generated
      MovieDownloadRecordFileResponseBuilder() {
      }

      @Generated
      public MovieDownloadRecordFileResponse.MovieDownloadRecordFileResponseBuilder id(final Long id) {
         this.id = id;
         return this;
      }

      @Generated
      public MovieDownloadRecordFileResponse.MovieDownloadRecordFileResponseBuilder recordId(final Long recordId) {
         this.recordId = recordId;
         return this;
      }

      @Generated
      public MovieDownloadRecordFileResponse.MovieDownloadRecordFileResponseBuilder sourceFilePath(final String sourceFilePath) {
         this.sourceFilePath = sourceFilePath;
         return this;
      }

      @Generated
      public MovieDownloadRecordFileResponse.MovieDownloadRecordFileResponseBuilder linkFilePath(final String linkFilePath) {
         this.linkFilePath = linkFilePath;
         return this;
      }

      @Generated
      public MovieDownloadRecordFileResponse.MovieDownloadRecordFileResponseBuilder episodeCodes(final String episodeCodes) {
         this.episodeCodes = episodeCodes;
         return this;
      }

      @Generated
      public MovieDownloadRecordFileResponse.MovieDownloadRecordFileResponseBuilder episodeSeqs(final String episodeSeqs) {
         this.episodeSeqs = episodeSeqs;
         return this;
      }

      @Generated
      public MovieDownloadRecordFileResponse build() {
         return new MovieDownloadRecordFileResponse(this.id, this.recordId, this.sourceFilePath, this.linkFilePath, this.episodeCodes, this.episodeSeqs);
      }

      @Generated
      @Override
      public String toString() {
         return "MovieDownloadRecordFileResponse.MovieDownloadRecordFileResponseBuilder(id="
            + this.id
            + ", recordId="
            + this.recordId
            + ", sourceFilePath="
            + this.sourceFilePath
            + ", linkFilePath="
            + this.linkFilePath
            + ", episodeCodes="
            + this.episodeCodes
            + ", episodeSeqs="
            + this.episodeSeqs
            + ")";
      }
   }
}
