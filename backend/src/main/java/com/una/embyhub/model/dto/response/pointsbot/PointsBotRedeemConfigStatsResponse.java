package com.una.embyhub.model.dto.response.pointsbot;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.Map;
import lombok.Generated;

@Schema(
   description = "积分服务器兑换配置统计信息"
)
public class PointsBotRedeemConfigStatsResponse {
   @Schema(
      description = "总配置数"
   )
   private Long totalConfigs;
   @Schema(
      description = "启用配置数（enabled = 1）"
   )
   private Long enabledConfigs;
   @Schema(
      description = "停用配置数（enabled = 0）"
   )
   private Long disabledConfigs;
   @Schema(
      description = "按服务器分组的配置数（embyInfoId -> count）"
   )
   private Map<Long, Long> configsByServer;

   @Generated
   public Long getTotalConfigs() {
      return this.totalConfigs;
   }

   @Generated
   public Long getEnabledConfigs() {
      return this.enabledConfigs;
   }

   @Generated
   public Long getDisabledConfigs() {
      return this.disabledConfigs;
   }

   @Generated
   public Map<Long, Long> getConfigsByServer() {
      return this.configsByServer;
   }

   @Generated
   public void setTotalConfigs(final Long totalConfigs) {
      this.totalConfigs = totalConfigs;
   }

   @Generated
   public void setEnabledConfigs(final Long enabledConfigs) {
      this.enabledConfigs = enabledConfigs;
   }

   @Generated
   public void setDisabledConfigs(final Long disabledConfigs) {
      this.disabledConfigs = disabledConfigs;
   }

   @Generated
   public void setConfigsByServer(final Map<Long, Long> configsByServer) {
      this.configsByServer = configsByServer;
   }

   @Generated
   @Override
   public boolean equals(final Object o) {
      if (o == this) {
         return true;
      } else if (!(o instanceof PointsBotRedeemConfigStatsResponse other)) {
         return false;
      } else if (!other.canEqual(this)) {
         return false;
      } else {
         Object this$totalConfigs = this.getTotalConfigs();
         Object other$totalConfigs = other.getTotalConfigs();
         if (this$totalConfigs == null ? other$totalConfigs == null : this$totalConfigs.equals(other$totalConfigs)) {
            Object this$enabledConfigs = this.getEnabledConfigs();
            Object other$enabledConfigs = other.getEnabledConfigs();
            if (this$enabledConfigs == null ? other$enabledConfigs == null : this$enabledConfigs.equals(other$enabledConfigs)) {
               Object this$disabledConfigs = this.getDisabledConfigs();
               Object other$disabledConfigs = other.getDisabledConfigs();
               if (this$disabledConfigs == null ? other$disabledConfigs == null : this$disabledConfigs.equals(other$disabledConfigs)) {
                  Object this$configsByServer = this.getConfigsByServer();
                  Object other$configsByServer = other.getConfigsByServer();
                  return this$configsByServer == null ? other$configsByServer == null : this$configsByServer.equals(other$configsByServer);
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
      return other instanceof PointsBotRedeemConfigStatsResponse;
   }

   @Generated
   @Override
   public int hashCode() {
      int PRIME = 59;
      int result = 1;
      Object $totalConfigs = this.getTotalConfigs();
      result = result * 59 + ($totalConfigs == null ? 43 : $totalConfigs.hashCode());
      Object $enabledConfigs = this.getEnabledConfigs();
      result = result * 59 + ($enabledConfigs == null ? 43 : $enabledConfigs.hashCode());
      Object $disabledConfigs = this.getDisabledConfigs();
      result = result * 59 + ($disabledConfigs == null ? 43 : $disabledConfigs.hashCode());
      Object $configsByServer = this.getConfigsByServer();
      return result * 59 + ($configsByServer == null ? 43 : $configsByServer.hashCode());
   }

   @Generated
   @Override
   public String toString() {
      return "PointsBotRedeemConfigStatsResponse(totalConfigs="
         + this.getTotalConfigs()
         + ", enabledConfigs="
         + this.getEnabledConfigs()
         + ", disabledConfigs="
         + this.getDisabledConfigs()
         + ", configsByServer="
         + this.getConfigsByServer()
         + ")";
   }
}
