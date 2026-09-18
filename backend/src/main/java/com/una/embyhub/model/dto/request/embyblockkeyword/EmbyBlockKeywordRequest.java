package com.una.embyhub.model.dto.request.embyblockkeyword;

import lombok.Generated;

public class EmbyBlockKeywordRequest {
   private Long id;
   private String keyword;
   private String description;
   private Integer enabled;

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
   @Override
   public boolean equals(final Object o) {
      if (o == this) {
         return true;
      } else if (!(o instanceof EmbyBlockKeywordRequest other)) {
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
                  return this$description == null ? other$description == null : this$description.equals(other$description);
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
      return other instanceof EmbyBlockKeywordRequest;
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
      return result * 59 + ($description == null ? 43 : $description.hashCode());
   }

   @Generated
   @Override
   public String toString() {
      return "EmbyBlockKeywordRequest(id="
         + this.getId()
         + ", keyword="
         + this.getKeyword()
         + ", description="
         + this.getDescription()
         + ", enabled="
         + this.getEnabled()
         + ")";
   }
}
