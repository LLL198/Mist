package com.una.embyhub.model.dto.response.embynotifydata;

import java.io.Serializable;
import lombok.Generated;

public class NullbrResponse implements Serializable {
   private String appid;
   private String apikey;

   @Generated
   public String getAppid() {
      return this.appid;
   }

   @Generated
   public String getApikey() {
      return this.apikey;
   }

   @Generated
   public void setAppid(final String appid) {
      this.appid = appid;
   }

   @Generated
   public void setApikey(final String apikey) {
      this.apikey = apikey;
   }

   @Generated
   @Override
   public boolean equals(final Object o) {
      if (o == this) {
         return true;
      } else if (!(o instanceof NullbrResponse other)) {
         return false;
      } else if (!other.canEqual(this)) {
         return false;
      } else {
         Object this$appid = this.getAppid();
         Object other$appid = other.getAppid();
         if (this$appid == null ? other$appid == null : this$appid.equals(other$appid)) {
            Object this$apikey = this.getApikey();
            Object other$apikey = other.getApikey();
            return this$apikey == null ? other$apikey == null : this$apikey.equals(other$apikey);
         } else {
            return false;
         }
      }
   }

   @Generated
   protected boolean canEqual(final Object other) {
      return other instanceof NullbrResponse;
   }

   @Generated
   @Override
   public int hashCode() {
      int PRIME = 59;
      int result = 1;
      Object $appid = this.getAppid();
      result = result * 59 + ($appid == null ? 43 : $appid.hashCode());
      Object $apikey = this.getApikey();
      return result * 59 + ($apikey == null ? 43 : $apikey.hashCode());
   }

   @Generated
   @Override
   public String toString() {
      return "NullbrResponse(appid=" + this.getAppid() + ", apikey=" + this.getApikey() + ")";
   }
}
