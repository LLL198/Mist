package com.una.embyhub.model.dto.response.pointsbot;

import java.io.Serializable;
import lombok.Generated;

public final class PointsBotPortalRedeemOptionResponse implements Serializable {
   private final Long id;
   private final String configName;
   private final String redeemType;
   private final Integer redeemDays;
   private final Integer requiredPoints;
   private final Long serverId;
   private final String serverName;
   private final String remark;
   private final boolean available;
   private final String unavailableReason;

   @Generated
   PointsBotPortalRedeemOptionResponse(
      final Long id,
      final String configName,
      final String redeemType,
      final Integer redeemDays,
      final Integer requiredPoints,
      final Long serverId,
      final String serverName,
      final String remark,
      final boolean available,
      final String unavailableReason
   ) {
      this.id = id;
      this.configName = configName;
      this.redeemType = redeemType;
      this.redeemDays = redeemDays;
      this.requiredPoints = requiredPoints;
      this.serverId = serverId;
      this.serverName = serverName;
      this.remark = remark;
      this.available = available;
      this.unavailableReason = unavailableReason;
   }

   @Generated
   public static PointsBotPortalRedeemOptionResponse.PointsBotPortalRedeemOptionResponseBuilder builder() {
      return new PointsBotPortalRedeemOptionResponse.PointsBotPortalRedeemOptionResponseBuilder();
   }

   @Generated
   public Long getId() {
      return this.id;
   }

   @Generated
   public String getConfigName() {
      return this.configName;
   }

   @Generated
   public String getRedeemType() {
      return this.redeemType;
   }

   @Generated
   public Integer getRedeemDays() {
      return this.redeemDays;
   }

   @Generated
   public Integer getRequiredPoints() {
      return this.requiredPoints;
   }

   @Generated
   public Long getServerId() {
      return this.serverId;
   }

   @Generated
   public String getServerName() {
      return this.serverName;
   }

   @Generated
   public String getRemark() {
      return this.remark;
   }

   @Generated
   public boolean isAvailable() {
      return this.available;
   }

   @Generated
   public String getUnavailableReason() {
      return this.unavailableReason;
   }

   @Generated
   @Override
   public boolean equals(final Object o) {
      if (o == this) {
         return true;
      } else if (!(o instanceof PointsBotPortalRedeemOptionResponse other)) {
         return false;
      } else if (this.isAvailable() != other.isAvailable()) {
         return false;
      } else {
         Object this$id = this.getId();
         Object other$id = other.getId();
         if (this$id == null ? other$id == null : this$id.equals(other$id)) {
            Object this$redeemDays = this.getRedeemDays();
            Object other$redeemDays = other.getRedeemDays();
            if (this$redeemDays == null ? other$redeemDays == null : this$redeemDays.equals(other$redeemDays)) {
               Object this$requiredPoints = this.getRequiredPoints();
               Object other$requiredPoints = other.getRequiredPoints();
               if (this$requiredPoints == null ? other$requiredPoints == null : this$requiredPoints.equals(other$requiredPoints)) {
                  Object this$serverId = this.getServerId();
                  Object other$serverId = other.getServerId();
                  if (this$serverId == null ? other$serverId == null : this$serverId.equals(other$serverId)) {
                     Object this$configName = this.getConfigName();
                     Object other$configName = other.getConfigName();
                     if (this$configName == null ? other$configName == null : this$configName.equals(other$configName)) {
                        Object this$redeemType = this.getRedeemType();
                        Object other$redeemType = other.getRedeemType();
                        if (this$redeemType == null ? other$redeemType == null : this$redeemType.equals(other$redeemType)) {
                           Object this$serverName = this.getServerName();
                           Object other$serverName = other.getServerName();
                           if (this$serverName == null ? other$serverName == null : this$serverName.equals(other$serverName)) {
                              Object this$remark = this.getRemark();
                              Object other$remark = other.getRemark();
                              if (this$remark == null ? other$remark == null : this$remark.equals(other$remark)) {
                                 Object this$unavailableReason = this.getUnavailableReason();
                                 Object other$unavailableReason = other.getUnavailableReason();
                                 return this$unavailableReason == null
                                    ? other$unavailableReason == null
                                    : this$unavailableReason.equals(other$unavailableReason);
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
   @Override
   public int hashCode() {
      int PRIME = 59;
      int result = 1;
      result = result * 59 + (this.isAvailable() ? 79 : 97);
      Object $id = this.getId();
      result = result * 59 + ($id == null ? 43 : $id.hashCode());
      Object $redeemDays = this.getRedeemDays();
      result = result * 59 + ($redeemDays == null ? 43 : $redeemDays.hashCode());
      Object $requiredPoints = this.getRequiredPoints();
      result = result * 59 + ($requiredPoints == null ? 43 : $requiredPoints.hashCode());
      Object $serverId = this.getServerId();
      result = result * 59 + ($serverId == null ? 43 : $serverId.hashCode());
      Object $configName = this.getConfigName();
      result = result * 59 + ($configName == null ? 43 : $configName.hashCode());
      Object $redeemType = this.getRedeemType();
      result = result * 59 + ($redeemType == null ? 43 : $redeemType.hashCode());
      Object $serverName = this.getServerName();
      result = result * 59 + ($serverName == null ? 43 : $serverName.hashCode());
      Object $remark = this.getRemark();
      result = result * 59 + ($remark == null ? 43 : $remark.hashCode());
      Object $unavailableReason = this.getUnavailableReason();
      return result * 59 + ($unavailableReason == null ? 43 : $unavailableReason.hashCode());
   }

   @Generated
   @Override
   public String toString() {
      return "PointsBotPortalRedeemOptionResponse(id="
         + this.getId()
         + ", configName="
         + this.getConfigName()
         + ", redeemType="
         + this.getRedeemType()
         + ", redeemDays="
         + this.getRedeemDays()
         + ", requiredPoints="
         + this.getRequiredPoints()
         + ", serverId="
         + this.getServerId()
         + ", serverName="
         + this.getServerName()
         + ", remark="
         + this.getRemark()
         + ", available="
         + this.isAvailable()
         + ", unavailableReason="
         + this.getUnavailableReason()
         + ")";
   }

   @Generated
   public static class PointsBotPortalRedeemOptionResponseBuilder {
      @Generated
      private Long id;
      @Generated
      private String configName;
      @Generated
      private String redeemType;
      @Generated
      private Integer redeemDays;
      @Generated
      private Integer requiredPoints;
      @Generated
      private Long serverId;
      @Generated
      private String serverName;
      @Generated
      private String remark;
      @Generated
      private boolean available;
      @Generated
      private String unavailableReason;

      @Generated
      PointsBotPortalRedeemOptionResponseBuilder() {
      }

      @Generated
      public PointsBotPortalRedeemOptionResponse.PointsBotPortalRedeemOptionResponseBuilder id(final Long id) {
         this.id = id;
         return this;
      }

      @Generated
      public PointsBotPortalRedeemOptionResponse.PointsBotPortalRedeemOptionResponseBuilder configName(final String configName) {
         this.configName = configName;
         return this;
      }

      @Generated
      public PointsBotPortalRedeemOptionResponse.PointsBotPortalRedeemOptionResponseBuilder redeemType(final String redeemType) {
         this.redeemType = redeemType;
         return this;
      }

      @Generated
      public PointsBotPortalRedeemOptionResponse.PointsBotPortalRedeemOptionResponseBuilder redeemDays(final Integer redeemDays) {
         this.redeemDays = redeemDays;
         return this;
      }

      @Generated
      public PointsBotPortalRedeemOptionResponse.PointsBotPortalRedeemOptionResponseBuilder requiredPoints(final Integer requiredPoints) {
         this.requiredPoints = requiredPoints;
         return this;
      }

      @Generated
      public PointsBotPortalRedeemOptionResponse.PointsBotPortalRedeemOptionResponseBuilder serverId(final Long serverId) {
         this.serverId = serverId;
         return this;
      }

      @Generated
      public PointsBotPortalRedeemOptionResponse.PointsBotPortalRedeemOptionResponseBuilder serverName(final String serverName) {
         this.serverName = serverName;
         return this;
      }

      @Generated
      public PointsBotPortalRedeemOptionResponse.PointsBotPortalRedeemOptionResponseBuilder remark(final String remark) {
         this.remark = remark;
         return this;
      }

      @Generated
      public PointsBotPortalRedeemOptionResponse.PointsBotPortalRedeemOptionResponseBuilder available(final boolean available) {
         this.available = available;
         return this;
      }

      @Generated
      public PointsBotPortalRedeemOptionResponse.PointsBotPortalRedeemOptionResponseBuilder unavailableReason(final String unavailableReason) {
         this.unavailableReason = unavailableReason;
         return this;
      }

      @Generated
      public PointsBotPortalRedeemOptionResponse build() {
         return new PointsBotPortalRedeemOptionResponse(
            this.id,
            this.configName,
            this.redeemType,
            this.redeemDays,
            this.requiredPoints,
            this.serverId,
            this.serverName,
            this.remark,
            this.available,
            this.unavailableReason
         );
      }

      @Generated
      @Override
      public String toString() {
         return "PointsBotPortalRedeemOptionResponse.PointsBotPortalRedeemOptionResponseBuilder(id="
            + this.id
            + ", configName="
            + this.configName
            + ", redeemType="
            + this.redeemType
            + ", redeemDays="
            + this.redeemDays
            + ", requiredPoints="
            + this.requiredPoints
            + ", serverId="
            + this.serverId
            + ", serverName="
            + this.serverName
            + ", remark="
            + this.remark
            + ", available="
            + this.available
            + ", unavailableReason="
            + this.unavailableReason
            + ")";
      }
   }
}
