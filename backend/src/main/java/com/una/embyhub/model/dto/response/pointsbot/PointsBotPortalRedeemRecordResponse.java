package com.una.embyhub.model.dto.response.pointsbot;

import java.io.Serializable;
import java.util.Date;
import lombok.Generated;

public class PointsBotPortalRedeemRecordResponse implements Serializable {
   private Long id;
   private String configName;
   private String redeemType;
   private Integer redeemDays;
   private Integer requiredPoints;
   private Long serverId;
   private String serverName;
   private String targetUserName;
   private String status;
   private String resultMessage;
   private Date expirationDate;
   private Date finishedAt;
   private Date createDatetime;

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
   public String getTargetUserName() {
      return this.targetUserName;
   }

   @Generated
   public String getStatus() {
      return this.status;
   }

   @Generated
   public String getResultMessage() {
      return this.resultMessage;
   }

   @Generated
   public Date getExpirationDate() {
      return this.expirationDate;
   }

   @Generated
   public Date getFinishedAt() {
      return this.finishedAt;
   }

   @Generated
   public Date getCreateDatetime() {
      return this.createDatetime;
   }

   @Generated
   public void setId(final Long id) {
      this.id = id;
   }

   @Generated
   public void setConfigName(final String configName) {
      this.configName = configName;
   }

   @Generated
   public void setRedeemType(final String redeemType) {
      this.redeemType = redeemType;
   }

   @Generated
   public void setRedeemDays(final Integer redeemDays) {
      this.redeemDays = redeemDays;
   }

   @Generated
   public void setRequiredPoints(final Integer requiredPoints) {
      this.requiredPoints = requiredPoints;
   }

   @Generated
   public void setServerId(final Long serverId) {
      this.serverId = serverId;
   }

   @Generated
   public void setServerName(final String serverName) {
      this.serverName = serverName;
   }

   @Generated
   public void setTargetUserName(final String targetUserName) {
      this.targetUserName = targetUserName;
   }

   @Generated
   public void setStatus(final String status) {
      this.status = status;
   }

   @Generated
   public void setResultMessage(final String resultMessage) {
      this.resultMessage = resultMessage;
   }

   @Generated
   public void setExpirationDate(final Date expirationDate) {
      this.expirationDate = expirationDate;
   }

   @Generated
   public void setFinishedAt(final Date finishedAt) {
      this.finishedAt = finishedAt;
   }

   @Generated
   public void setCreateDatetime(final Date createDatetime) {
      this.createDatetime = createDatetime;
   }

   @Generated
   @Override
   public boolean equals(final Object o) {
      if (o == this) {
         return true;
      } else if (!(o instanceof PointsBotPortalRedeemRecordResponse other)) {
         return false;
      } else if (!other.canEqual(this)) {
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
                              Object this$targetUserName = this.getTargetUserName();
                              Object other$targetUserName = other.getTargetUserName();
                              if (this$targetUserName == null ? other$targetUserName == null : this$targetUserName.equals(other$targetUserName)) {
                                 Object this$status = this.getStatus();
                                 Object other$status = other.getStatus();
                                 if (this$status == null ? other$status == null : this$status.equals(other$status)) {
                                    Object this$resultMessage = this.getResultMessage();
                                    Object other$resultMessage = other.getResultMessage();
                                    if (this$resultMessage == null ? other$resultMessage == null : this$resultMessage.equals(other$resultMessage)) {
                                       Object this$expirationDate = this.getExpirationDate();
                                       Object other$expirationDate = other.getExpirationDate();
                                       if (this$expirationDate == null ? other$expirationDate == null : this$expirationDate.equals(other$expirationDate)) {
                                          Object this$finishedAt = this.getFinishedAt();
                                          Object other$finishedAt = other.getFinishedAt();
                                          if (this$finishedAt == null ? other$finishedAt == null : this$finishedAt.equals(other$finishedAt)) {
                                             Object this$createDatetime = this.getCreateDatetime();
                                             Object other$createDatetime = other.getCreateDatetime();
                                             return this$createDatetime == null
                                                ? other$createDatetime == null
                                                : this$createDatetime.equals(other$createDatetime);
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
      return other instanceof PointsBotPortalRedeemRecordResponse;
   }

   @Generated
   @Override
   public int hashCode() {
      int PRIME = 59;
      int result = 1;
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
      Object $targetUserName = this.getTargetUserName();
      result = result * 59 + ($targetUserName == null ? 43 : $targetUserName.hashCode());
      Object $status = this.getStatus();
      result = result * 59 + ($status == null ? 43 : $status.hashCode());
      Object $resultMessage = this.getResultMessage();
      result = result * 59 + ($resultMessage == null ? 43 : $resultMessage.hashCode());
      Object $expirationDate = this.getExpirationDate();
      result = result * 59 + ($expirationDate == null ? 43 : $expirationDate.hashCode());
      Object $finishedAt = this.getFinishedAt();
      result = result * 59 + ($finishedAt == null ? 43 : $finishedAt.hashCode());
      Object $createDatetime = this.getCreateDatetime();
      return result * 59 + ($createDatetime == null ? 43 : $createDatetime.hashCode());
   }

   @Generated
   @Override
   public String toString() {
      return "PointsBotPortalRedeemRecordResponse(id="
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
         + ", targetUserName="
         + this.getTargetUserName()
         + ", status="
         + this.getStatus()
         + ", resultMessage="
         + this.getResultMessage()
         + ", expirationDate="
         + this.getExpirationDate()
         + ", finishedAt="
         + this.getFinishedAt()
         + ", createDatetime="
         + this.getCreateDatetime()
         + ")";
   }
}
