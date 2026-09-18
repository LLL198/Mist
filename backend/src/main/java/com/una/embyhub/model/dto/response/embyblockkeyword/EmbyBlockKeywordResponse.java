package com.una.embyhub.model.dto.response.embyblockkeyword;

import java.util.Date;
import lombok.Generated;

public class EmbyBlockKeywordResponse {
   private Long id;
   private String keyword;
   private String description;
   private Integer enabled;
   private Date createDatetime;
   private Date updateDatetime;

   @Generated
   public Long getId() {
      return this.id;
   }

   @Generated
   public String getKeyword() {
      return this.keyword;
   }

   @Generated
   public String getDescription() {
      return this.description;
   }

   @Generated
   public Integer getEnabled() {
      return this.enabled;
   }

   @Generated
   public Date getCreateDatetime() {
      return this.createDatetime;
   }

   @Generated
   public Date getUpdateDatetime() {
      return this.updateDatetime;
   }

   @Generated
   public void setId(final Long id) {
      this.id = id;
   }

   @Generated
   public void setKeyword(final String keyword) {
      this.keyword = keyword;
   }

   @Generated
   public void setDescription(final String description) {
      this.description = description;
   }

   @Generated
   public void setEnabled(final Integer enabled) {
      this.enabled = enabled;
   }

   @Generated
   public void setCreateDatetime(final Date createDatetime) {
      this.createDatetime = createDatetime;
   }

   @Generated
   public void setUpdateDatetime(final Date updateDatetime) {
      this.updateDatetime = updateDatetime;
   }

   @Generated
   @Override
   public boolean equals(final Object o) {
      if (o == this) {
         return true;
      } else if (!(o instanceof EmbyBlockKeywordResponse other)) {
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
               Object this$keyword = this.getKeyword();
               Object other$keyword = other.getKeyword();
               if (this$keyword == null ? other$keyword == null : this$keyword.equals(other$keyword)) {
                  Object this$description = this.getDescription();
                  Object other$description = other.getDescription();
                  if (this$description == null ? other$description == null : this$description.equals(other$description)) {
                     Object this$createDatetime = this.getCreateDatetime();
                     Object other$createDatetime = other.getCreateDatetime();
                     if (this$createDatetime == null ? other$createDatetime == null : this$createDatetime.equals(other$createDatetime)) {
                        Object this$updateDatetime = this.getUpdateDatetime();
                        Object other$updateDatetime = other.getUpdateDatetime();
                        return this$updateDatetime == null ? other$updateDatetime == null : this$updateDatetime.equals(other$updateDatetime);
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
      return other instanceof EmbyBlockKeywordResponse;
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
      Object $keyword = this.getKeyword();
      result = result * 59 + ($keyword == null ? 43 : $keyword.hashCode());
      Object $description = this.getDescription();
      result = result * 59 + ($description == null ? 43 : $description.hashCode());
      Object $createDatetime = this.getCreateDatetime();
      result = result * 59 + ($createDatetime == null ? 43 : $createDatetime.hashCode());
      Object $updateDatetime = this.getUpdateDatetime();
      return result * 59 + ($updateDatetime == null ? 43 : $updateDatetime.hashCode());
   }

   @Generated
   @Override
   public String toString() {
      return "EmbyBlockKeywordResponse(id="
         + this.getId()
         + ", keyword="
         + this.getKeyword()
         + ", description="
         + this.getDescription()
         + ", enabled="
         + this.getEnabled()
         + ", createDatetime="
         + this.getCreateDatetime()
         + ", updateDatetime="
         + this.getUpdateDatetime()
         + ")";
   }
}
