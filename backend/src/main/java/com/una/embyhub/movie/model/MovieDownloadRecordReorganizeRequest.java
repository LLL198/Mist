package com.una.embyhub.movie.model;

import java.io.Serializable;
import java.util.List;
import lombok.Generated;

public class MovieDownloadRecordReorganizeRequest implements Serializable {
   private static final long serialVersionUID = 1L;
   private List<Long> recordIds;
   private Long scrapePathConfigId;
   private Integer overwrite;
   private Integer coexist;
   private Integer qualityPriority;
   private Integer sizePriority;
   private Integer hardlinkMode;

   @Generated
   public List<Long> getRecordIds() {
      return this.recordIds;
   }

   @Generated
   public Long getScrapePathConfigId() {
      return this.scrapePathConfigId;
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
   public void setRecordIds(final List<Long> recordIds) {
      this.recordIds = recordIds;
   }

   @Generated
   public void setScrapePathConfigId(final Long scrapePathConfigId) {
      this.scrapePathConfigId = scrapePathConfigId;
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
      } else if (!(o instanceof MovieDownloadRecordReorganizeRequest other)) {
         return false;
      } else if (!other.canEqual(this)) {
         return false;
      } else {
         Object this$scrapePathConfigId = this.getScrapePathConfigId();
         Object other$scrapePathConfigId = other.getScrapePathConfigId();
         if (this$scrapePathConfigId == null ? other$scrapePathConfigId == null : this$scrapePathConfigId.equals(other$scrapePathConfigId)) {
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
                           Object this$recordIds = this.getRecordIds();
                           Object other$recordIds = other.getRecordIds();
                           return this$recordIds == null ? other$recordIds == null : this$recordIds.equals(other$recordIds);
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
      return other instanceof MovieDownloadRecordReorganizeRequest;
   }

   @Generated
   @Override
   public int hashCode() {
      int PRIME = 59;
      int result = 1;
      Object $scrapePathConfigId = this.getScrapePathConfigId();
      result = result * 59 + ($scrapePathConfigId == null ? 43 : $scrapePathConfigId.hashCode());
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
      Object $recordIds = this.getRecordIds();
      return result * 59 + ($recordIds == null ? 43 : $recordIds.hashCode());
   }

   @Generated
   @Override
   public String toString() {
      return "MovieDownloadRecordReorganizeRequest(recordIds="
         + this.getRecordIds()
         + ", scrapePathConfigId="
         + this.getScrapePathConfigId()
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
}
