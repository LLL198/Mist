package com.una.embyhub.model.dto.response.wechatipconfig;

import java.io.Serializable;
import java.util.Date;
import lombok.Generated;

public class WeChatIpConfigResponse implements Serializable {
   private Long id;
   private String corpId;
   private String corpName;
   private String appIds;
   private String lastIp;
   private Date lastIpUpdateTime;
   private Integer enabled;
   private String remark;
   private Date createDatetime;
   private Date updateDatetime;

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
   public String getLastIp() {
      return this.lastIp;
   }

   @Generated
   public Date getLastIpUpdateTime() {
      return this.lastIpUpdateTime;
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
   public void setLastIp(final String lastIp) {
      this.lastIp = lastIp;
   }

   @Generated
   public void setLastIpUpdateTime(final Date lastIpUpdateTime) {
      this.lastIpUpdateTime = lastIpUpdateTime;
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
      } else if (!(o instanceof WeChatIpConfigResponse other)) {
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
                        Object this$lastIp = this.getLastIp();
                        Object other$lastIp = other.getLastIp();
                        if (this$lastIp == null ? other$lastIp == null : this$lastIp.equals(other$lastIp)) {
                           Object this$lastIpUpdateTime = this.getLastIpUpdateTime();
                           Object other$lastIpUpdateTime = other.getLastIpUpdateTime();
                           if (this$lastIpUpdateTime == null ? other$lastIpUpdateTime == null : this$lastIpUpdateTime.equals(other$lastIpUpdateTime)) {
                              Object this$remark = this.getRemark();
                              Object other$remark = other.getRemark();
                              if (this$remark == null ? other$remark == null : this$remark.equals(other$remark)) {
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
      return other instanceof WeChatIpConfigResponse;
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
      Object $lastIp = this.getLastIp();
      result = result * 59 + ($lastIp == null ? 43 : $lastIp.hashCode());
      Object $lastIpUpdateTime = this.getLastIpUpdateTime();
      result = result * 59 + ($lastIpUpdateTime == null ? 43 : $lastIpUpdateTime.hashCode());
      Object $remark = this.getRemark();
      result = result * 59 + ($remark == null ? 43 : $remark.hashCode());
      Object $createDatetime = this.getCreateDatetime();
      result = result * 59 + ($createDatetime == null ? 43 : $createDatetime.hashCode());
      Object $updateDatetime = this.getUpdateDatetime();
      return result * 59 + ($updateDatetime == null ? 43 : $updateDatetime.hashCode());
   }

   @Generated
   @Override
   public String toString() {
      return "WeChatIpConfigResponse(id="
         + this.getId()
         + ", corpId="
         + this.getCorpId()
         + ", corpName="
         + this.getCorpName()
         + ", appIds="
         + this.getAppIds()
         + ", lastIp="
         + this.getLastIp()
         + ", lastIpUpdateTime="
         + this.getLastIpUpdateTime()
         + ", enabled="
         + this.getEnabled()
         + ", remark="
         + this.getRemark()
         + ", createDatetime="
         + this.getCreateDatetime()
         + ", updateDatetime="
         + this.getUpdateDatetime()
         + ")";
   }
}
