package com.una.embyhub.model.dto.response.dashboard;

import java.io.Serializable;
import lombok.Generated;

public class DashboardPopularMovieResponse implements Serializable {
   private static final long serialVersionUID = 1L;
   private String itemId;
   private Long embyInfoId;
   private String title;
   private String year;
   private String imageTag;
   private Long playCount;

   @Generated
   public String getItemId() {
      return this.itemId;
   }

   @Generated
   public Long getEmbyInfoId() {
      return this.embyInfoId;
   }

   @Generated
   public String getTitle() {
      return this.title;
   }

   @Generated
   public String getYear() {
      return this.year;
   }

   @Generated
   public String getImageTag() {
      return this.imageTag;
   }

   @Generated
   public Long getPlayCount() {
      return this.playCount;
   }

   @Generated
   public void setItemId(final String itemId) {
      this.itemId = itemId;
   }

   @Generated
   public void setEmbyInfoId(final Long embyInfoId) {
      this.embyInfoId = embyInfoId;
   }

   @Generated
   public void setTitle(final String title) {
      this.title = title;
   }

   @Generated
   public void setYear(final String year) {
      this.year = year;
   }

   @Generated
   public void setImageTag(final String imageTag) {
      this.imageTag = imageTag;
   }

   @Generated
   public void setPlayCount(final Long playCount) {
      this.playCount = playCount;
   }

   @Generated
   @Override
   public boolean equals(final Object o) {
      if (o == this) {
         return true;
      } else if (!(o instanceof DashboardPopularMovieResponse other)) {
         return false;
      } else if (!other.canEqual(this)) {
         return false;
      } else {
         Object this$embyInfoId = this.getEmbyInfoId();
         Object other$embyInfoId = other.getEmbyInfoId();
         if (this$embyInfoId == null ? other$embyInfoId == null : this$embyInfoId.equals(other$embyInfoId)) {
            Object this$playCount = this.getPlayCount();
            Object other$playCount = other.getPlayCount();
            if (this$playCount == null ? other$playCount == null : this$playCount.equals(other$playCount)) {
               Object this$itemId = this.getItemId();
               Object other$itemId = other.getItemId();
               if (this$itemId == null ? other$itemId == null : this$itemId.equals(other$itemId)) {
                  Object this$title = this.getTitle();
                  Object other$title = other.getTitle();
                  if (this$title == null ? other$title == null : this$title.equals(other$title)) {
                     Object this$year = this.getYear();
                     Object other$year = other.getYear();
                     if (this$year == null ? other$year == null : this$year.equals(other$year)) {
                        Object this$imageTag = this.getImageTag();
                        Object other$imageTag = other.getImageTag();
                        return this$imageTag == null ? other$imageTag == null : this$imageTag.equals(other$imageTag);
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
      return other instanceof DashboardPopularMovieResponse;
   }

   @Generated
   @Override
   public int hashCode() {
      int PRIME = 59;
      int result = 1;
      Object $embyInfoId = this.getEmbyInfoId();
      result = result * 59 + ($embyInfoId == null ? 43 : $embyInfoId.hashCode());
      Object $playCount = this.getPlayCount();
      result = result * 59 + ($playCount == null ? 43 : $playCount.hashCode());
      Object $itemId = this.getItemId();
      result = result * 59 + ($itemId == null ? 43 : $itemId.hashCode());
      Object $title = this.getTitle();
      result = result * 59 + ($title == null ? 43 : $title.hashCode());
      Object $year = this.getYear();
      result = result * 59 + ($year == null ? 43 : $year.hashCode());
      Object $imageTag = this.getImageTag();
      return result * 59 + ($imageTag == null ? 43 : $imageTag.hashCode());
   }

   @Generated
   @Override
   public String toString() {
      return "DashboardPopularMovieResponse(itemId="
         + this.getItemId()
         + ", embyInfoId="
         + this.getEmbyInfoId()
         + ", title="
         + this.getTitle()
         + ", year="
         + this.getYear()
         + ", imageTag="
         + this.getImageTag()
         + ", playCount="
         + this.getPlayCount()
         + ")";
   }
}
