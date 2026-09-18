package com.una.embyhub.model.dto.request.wechatipconfig;

import java.io.Serializable;
import lombok.Generated;

public class WeChatIpConfigUpdate implements Serializable {
   private Long id;
   private String corpId;
   private String corpName;
   private String appIds;
   private Integer enabled;
   private String remark;

   @Generated
   public Long getId() {
      return this.id;
   }

   @Generated
   public String getCorpId() {
      return this.corpId;
   }

   @Generated
   public String getCorpName() {
      return this.corpName;
   }

   @Generated
   public String getAppIds() {
      return this.appIds;
   }

   @Generated
   public Integer getEnabled() {
      return this.enabled;
   }

   @Generated
   public String getRemark() {
      return this.remark;
   }

   @Generated
   public void setId(final Long id) {
      this.id = id;
   }

   @Generated
   public void setCorpId(final String corpId) {
      this.corpId = corpId;
   }

   @Generated
   public void setCorpName(final String corpName) {
      this.corpName = corpName;
   }

   @Generated
   public void setAppIds(final String appIds) {
      this.appIds = appIds;
   }

   @Generated
   public void setEnabled(final Integer enabled) {
      this.enabled = enabled;
   }

   @Generated
   public void setRemark(final String remark) {
      this.remark = remark;
   }

   @Generated
   @Override
   public boolean equals(final Object o) {
      if (o == this) {
         return true;
      } else if (!(o instanceof WeChatIpConfigUpdate other)) {
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
               Object this$corpId = this.getCorpId();
               Object other$corpId = other.getCorpId();
               if (this$corpId == null ? other$corpId == null : this$corpId.equals(other$corpId)) {
                  Object this$corpName = this.getCorpName();
                  Object other$corpName = other.getCorpName();
                  if (this$corpName == null ? other$corpName == null : this$corpName.equals(other$corpName)) {
                     Object this$appIds = this.getAppIds();
                     Object other$appIds = other.getAppIds();
                     if (this$appIds == null ? other$appIds == null : this$appIds.equals(other$appIds)) {
                        Object this$remark = this.getRemark();
                        Object other$remark = other.getRemark();
                        return this$remark == null ? other$remark == null : this$remark.equals(other$remark);
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
      return other instanceof WeChatIpConfigUpdate;
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
      Object $corpId = this.getCorpId();
      result = result * 59 + ($corpId == null ? 43 : $corpId.hashCode());
      Object $corpName = this.getCorpName();
      result = result * 59 + ($corpName == null ? 43 : $corpName.hashCode());
      Object $appIds = this.getAppIds();
      result = result * 59 + ($appIds == null ? 43 : $appIds.hashCode());
      Object $remark = this.getRemark();
      return result * 59 + ($remark == null ? 43 : $remark.hashCode());
   }

   @Generated
   @Override
   public String toString() {
      return "WeChatIpConfigUpdate(id="
         + this.getId()
         + ", corpId="
         + this.getCorpId()
         + ", corpName="
         + this.getCorpName()
         + ", appIds="
         + this.getAppIds()
         + ", enabled="
         + this.getEnabled()
         + ", remark="
         + this.getRemark()
         + ")";
   }
}
