package com.una.embyhub.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Generated;

@TableName("playback_ranking_config")
public class PlaybackRankingConfig extends BaseEntity implements Serializable {
   @TableId(
      value = "id",
      type = IdType.AUTO
   )
   private Long id;
   @TableField("emby_info_id")
   private Long embyInfoId;
   @TableField("excluded_user_ids")
   private String excludedUserIds;

   @Generated
   public Long getId() {
      return this.id;
   }

   @Generated
   public Long getEmbyInfoId() {
      return this.embyInfoId;
   }

   @Generated
   public String getExcludedUserIds() {
      return this.excludedUserIds;
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
   public void setExcludedUserIds(final String excludedUserIds) {
      this.excludedUserIds = excludedUserIds;
   }

   @Generated
   @Override
   public String toString() {
      return "PlaybackRankingConfig(id=" + this.getId() + ", embyInfoId=" + this.getEmbyInfoId() + ", excludedUserIds=" + this.getExcludedUserIds() + ")";
   }

   @Generated
   @Override
   public boolean equals(final Object o) {
      if (o == this) {
         return true;
      } else if (!(o instanceof PlaybackRankingConfig other)) {
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
               Object this$excludedUserIds = this.getExcludedUserIds();
               Object other$excludedUserIds = other.getExcludedUserIds();
               return this$excludedUserIds == null ? other$excludedUserIds == null : this$excludedUserIds.equals(other$excludedUserIds);
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
      return other instanceof PlaybackRankingConfig;
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
      Object $excludedUserIds = this.getExcludedUserIds();
      return result * 59 + ($excludedUserIds == null ? 43 : $excludedUserIds.hashCode());
   }
}
