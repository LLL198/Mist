package com.una.embyhub.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Generated;

@TableName("emby_library_access_rule")
public class EmbyLibraryAccessRule extends BaseEntity implements Serializable {
   @TableId(
      value = "id",
      type = IdType.AUTO
   )
   private Long id;
   @TableField("emby_info_id")
   private Long embyInfoId;
   @TableField("scope_type")
   private String scopeType;
   @TableField("scope_key")
   private String scopeKey;
   @TableField("target_user_id")
   private Long targetUserId;
   @TableField("rule_enabled")
   private Integer ruleEnabled;
   @TableField("visible_folder_ids")
   private String visibleFolderIds;

   @Generated
   public Long getId() {
      return this.id;
   }

   @Generated
   public Long getEmbyInfoId() {
      return this.embyInfoId;
   }

   @Generated
   public String getScopeType() {
      return this.scopeType;
   }

   @Generated
   public String getScopeKey() {
      return this.scopeKey;
   }

   @Generated
   public Long getTargetUserId() {
      return this.targetUserId;
   }

   @Generated
   public Integer getRuleEnabled() {
      return this.ruleEnabled;
   }

   @Generated
   public String getVisibleFolderIds() {
      return this.visibleFolderIds;
   }

   @Generated
   public void setId(final Long id) {
      this.id = id;
   }

   @Generated
   public void setEmbyInfoId(final Long embyInfoId) {
      this.embyInfoId = embyInfoId;
   }

   @Generated
   public void setScopeType(final String scopeType) {
      this.scopeType = scopeType;
   }

   @Generated
   public void setScopeKey(final String scopeKey) {
      this.scopeKey = scopeKey;
   }

   @Generated
   public void setTargetUserId(final Long targetUserId) {
      this.targetUserId = targetUserId;
   }

   @Generated
   public void setRuleEnabled(final Integer ruleEnabled) {
      this.ruleEnabled = ruleEnabled;
   }

   @Generated
   public void setVisibleFolderIds(final String visibleFolderIds) {
      this.visibleFolderIds = visibleFolderIds;
   }

   @Generated
   @Override
   public String toString() {
      return "EmbyLibraryAccessRule(id="
         + this.getId()
         + ", embyInfoId="
         + this.getEmbyInfoId()
         + ", scopeType="
         + this.getScopeType()
         + ", scopeKey="
         + this.getScopeKey()
         + ", targetUserId="
         + this.getTargetUserId()
         + ", ruleEnabled="
         + this.getRuleEnabled()
         + ", visibleFolderIds="
         + this.getVisibleFolderIds()
         + ")";
   }

   @Generated
   @Override
   public boolean equals(final Object o) {
      if (o == this) {
         return true;
      } else if (!(o instanceof EmbyLibraryAccessRule other)) {
         return false;
      } else if (!other.canEqual(this)) {
         return false;
      } else if (!super.equals(o)) {
         return false;
      } else {
         Object this$id = this.getId();
         Object other$id = other.getId();
         if (this$id == null ? other$id == null : this$id.equals(other$id)) {
            Object this$embyInfoId = this.getEmbyInfoId();
            Object other$embyInfoId = other.getEmbyInfoId();
            if (this$embyInfoId == null ? other$embyInfoId == null : this$embyInfoId.equals(other$embyInfoId)) {
               Object this$targetUserId = this.getTargetUserId();
               Object other$targetUserId = other.getTargetUserId();
               if (this$targetUserId == null ? other$targetUserId == null : this$targetUserId.equals(other$targetUserId)) {
                  Object this$ruleEnabled = this.getRuleEnabled();
                  Object other$ruleEnabled = other.getRuleEnabled();
                  if (this$ruleEnabled == null ? other$ruleEnabled == null : this$ruleEnabled.equals(other$ruleEnabled)) {
                     Object this$scopeType = this.getScopeType();
                     Object other$scopeType = other.getScopeType();
                     if (this$scopeType == null ? other$scopeType == null : this$scopeType.equals(other$scopeType)) {
                        Object this$scopeKey = this.getScopeKey();
                        Object other$scopeKey = other.getScopeKey();
                        if (this$scopeKey == null ? other$scopeKey == null : this$scopeKey.equals(other$scopeKey)) {
                           Object this$visibleFolderIds = this.getVisibleFolderIds();
                           Object other$visibleFolderIds = other.getVisibleFolderIds();
                           return this$visibleFolderIds == null ? other$visibleFolderIds == null : this$visibleFolderIds.equals(other$visibleFolderIds);
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
      return other instanceof EmbyLibraryAccessRule;
   }

   @Generated
   @Override
   public int hashCode() {
      int PRIME = 59;
      int result = super.hashCode();
      Object $id = this.getId();
      result = result * 59 + ($id == null ? 43 : $id.hashCode());
      Object $embyInfoId = this.getEmbyInfoId();
      result = result * 59 + ($embyInfoId == null ? 43 : $embyInfoId.hashCode());
      Object $targetUserId = this.getTargetUserId();
      result = result * 59 + ($targetUserId == null ? 43 : $targetUserId.hashCode());
      Object $ruleEnabled = this.getRuleEnabled();
      result = result * 59 + ($ruleEnabled == null ? 43 : $ruleEnabled.hashCode());
      Object $scopeType = this.getScopeType();
      result = result * 59 + ($scopeType == null ? 43 : $scopeType.hashCode());
      Object $scopeKey = this.getScopeKey();
      result = result * 59 + ($scopeKey == null ? 43 : $scopeKey.hashCode());
      Object $visibleFolderIds = this.getVisibleFolderIds();
      return result * 59 + ($visibleFolderIds == null ? 43 : $visibleFolderIds.hashCode());
   }
}
