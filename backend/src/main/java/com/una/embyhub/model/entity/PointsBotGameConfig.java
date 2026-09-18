package com.una.embyhub.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Generated;

@TableName("points_bot_game_config")
public class PointsBotGameConfig extends BaseEntity implements Serializable {
   private static final long serialVersionUID = 1L;
   @TableId(
      value = "id",
      type = IdType.AUTO
   )
   private Long id;
   @TableField("game_code")
   private String gameCode;
   @TableField("enabled")
   private Integer enabled;
   @TableField("config_json")
   private String configJson;
   @TableField("sort_order")
   private Integer sortOrder;

   @Generated
   public Long getId() {
      return this.id;
   }

   @Generated
   public String getGameCode() {
      return this.gameCode;
   }

   @Generated
   public Integer getEnabled() {
      return this.enabled;
   }

   @Generated
   public String getConfigJson() {
      return this.configJson;
   }

   @Generated
   public Integer getSortOrder() {
      return this.sortOrder;
   }

   @Generated
   public void setId(final Long id) {
      this.id = id;
   }

   @Generated
   public void setGameCode(final String gameCode) {
      this.gameCode = gameCode;
   }

   @Generated
   public void setEnabled(final Integer enabled) {
      this.enabled = enabled;
   }

   @Generated
   public void setConfigJson(final String configJson) {
      this.configJson = configJson;
   }

   @Generated
   public void setSortOrder(final Integer sortOrder) {
      this.sortOrder = sortOrder;
   }

   @Generated
   @Override
   public String toString() {
      return "PointsBotGameConfig(id="
         + this.getId()
         + ", gameCode="
         + this.getGameCode()
         + ", enabled="
         + this.getEnabled()
         + ", configJson="
         + this.getConfigJson()
         + ", sortOrder="
         + this.getSortOrder()
         + ")";
   }

   @Generated
   @Override
   public boolean equals(final Object o) {
      if (o == this) {
         return true;
      } else if (!(o instanceof PointsBotGameConfig other)) {
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
               Object this$sortOrder = this.getSortOrder();
               Object other$sortOrder = other.getSortOrder();
               if (this$sortOrder == null ? other$sortOrder == null : this$sortOrder.equals(other$sortOrder)) {
                  Object this$gameCode = this.getGameCode();
                  Object other$gameCode = other.getGameCode();
                  if (this$gameCode == null ? other$gameCode == null : this$gameCode.equals(other$gameCode)) {
                     Object this$configJson = this.getConfigJson();
                     Object other$configJson = other.getConfigJson();
                     return this$configJson == null ? other$configJson == null : this$configJson.equals(other$configJson);
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
      return other instanceof PointsBotGameConfig;
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
      Object $sortOrder = this.getSortOrder();
      result = result * 59 + ($sortOrder == null ? 43 : $sortOrder.hashCode());
      Object $gameCode = this.getGameCode();
      result = result * 59 + ($gameCode == null ? 43 : $gameCode.hashCode());
      Object $configJson = this.getConfigJson();
      return result * 59 + ($configJson == null ? 43 : $configJson.hashCode());
   }
}
