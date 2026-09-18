package com.una.embyhub.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import lombok.Generated;

@TableName("wechat_ip_config")
public class WeChatIpConfig extends BaseEntity implements Serializable {
   private static final long serialVersionUID = 1L;
   @TableId(
      value = "id",
      type = IdType.AUTO
   )
   private Long id;
   @TableField("corp_id")
   private String corpId;
   @TableField("corp_name")
   private String corpName;
   @TableField("app_ids")
   private String appIds;
   @TableField("last_ip")
   private String lastIp;
   @TableField("last_ip_update_time")
   private Date lastIpUpdateTime;
   @TableField("enabled")
   private Integer enabled;
   @TableField("remark")
   private String remark;
   public static final String COL_ID = "id";
   public static final String COL_CORP_ID = "corp_id";
   public static final String COL_CORP_NAME = "corp_name";
   public static final String COL_APP_IDS = "app_ids";
   public static final String COL_LAST_IP = "last_ip";
   public static final String COL_LAST_IP_UPDATE_TIME = "last_ip_update_time";
   public static final String COL_ENABLED = "enabled";
   public static final String COL_REMARK = "remark";

   public List<String> getAppIdList() {
      return this.appIds != null && !this.appIds.isEmpty()
         ? Arrays.stream(this.appIds.split(",")).map(String::trim).filter(s -> !s.isEmpty()).toList()
         : List.of();
   }

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
   @Override
   public String toString() {
      return "WeChatIpConfig(id="
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
         + ")";
   }

   @Generated
   @Override
   public boolean equals(final Object o) {
      if (o == this) {
         return true;
      } else if (!(o instanceof WeChatIpConfig other)) {
         return false;
      } else if (!other.canEqual(this)) {
         return false;
      } else if (!super.equals(o)) {
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
            } else {
               return false;
            }
         } else {
            return false;
         }
      }
   }

   @Generated
   @Override
   protected boolean canEqual(final Object other) {
      return other instanceof WeChatIpConfig;
   }

   @Generated
   @Override
   public int hashCode() {
      int PRIME = 59;
      int result = super.hashCode();
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
      return result * 59 + ($remark == null ? 43 : $remark.hashCode());
   }
}
