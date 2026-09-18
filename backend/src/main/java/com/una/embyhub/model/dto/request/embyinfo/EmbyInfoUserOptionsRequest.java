package com.una.embyhub.model.dto.request.embyinfo;

import java.io.Serializable;
import lombok.Generated;

public class EmbyInfoUserOptionsRequest implements Serializable {
   private Long embyInfoId;
   private String embyUrl;
   private String embyApikey;

   @Generated
   public Long getEmbyInfoId() {
      return this.embyInfoId;
   }

   @Generated
   public String getEmbyUrl() {
      return this.embyUrl;
   }

   @Generated
   public String getEmbyApikey() {
      return this.embyApikey;
   }

   @Generated
   public void setEmbyInfoId(final Long embyInfoId) {
      this.embyInfoId = embyInfoId;
   }

   @Generated
   public void setEmbyUrl(final String embyUrl) {
      this.embyUrl = embyUrl;
   }

   @Generated
   public void setEmbyApikey(final String embyApikey) {
      this.embyApikey = embyApikey;
   }

   @Generated
   @Override
   public boolean equals(final Object o) {
      if (o == this) {
         return true;
      } else if (!(o instanceof EmbyInfoUserOptionsRequest other)) {
         return false;
      } else if (!other.canEqual(this)) {
         return false;
      } else {
         Object this$embyInfoId = this.getEmbyInfoId();
         Object other$embyInfoId = other.getEmbyInfoId();
         if (this$embyInfoId == null ? other$embyInfoId == null : this$embyInfoId.equals(other$embyInfoId)) {
            Object this$embyUrl = this.getEmbyUrl();
            Object other$embyUrl = other.getEmbyUrl();
            if (this$embyUrl == null ? other$embyUrl == null : this$embyUrl.equals(other$embyUrl)) {
               Object this$embyApikey = this.getEmbyApikey();
               Object other$embyApikey = other.getEmbyApikey();
               return this$embyApikey == null ? other$embyApikey == null : this$embyApikey.equals(other$embyApikey);
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
      return other instanceof EmbyInfoUserOptionsRequest;
   }

   @Generated
   @Override
   public int hashCode() {
      int PRIME = 59;
      int result = 1;
      Object $embyInfoId = this.getEmbyInfoId();
      result = result * 59 + ($embyInfoId == null ? 43 : $embyInfoId.hashCode());
      Object $embyUrl = this.getEmbyUrl();
      result = result * 59 + ($embyUrl == null ? 43 : $embyUrl.hashCode());
      Object $embyApikey = this.getEmbyApikey();
      return result * 59 + ($embyApikey == null ? 43 : $embyApikey.hashCode());
   }

   @Generated
   @Override
   public String toString() {
      return "EmbyInfoUserOptionsRequest(embyInfoId=" + this.getEmbyInfoId() + ", embyUrl=" + this.getEmbyUrl() + ", embyApikey=" + this.getEmbyApikey() + ")";
   }
}
