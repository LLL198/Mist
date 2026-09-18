package com.una.embyhub.model.entity;

import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Generated;

@TableName("points_bot_redeem_record")
public class PointsBotRedeemRecord extends BaseEntity implements Serializable {
   public static final String STATUS_PROCESSING = "PROCESSING";
   public static final String STATUS_SUCCESS = "SUCCESS";
   public static final String STATUS_REFUNDED = "REFUNDED";
   @TableId(
      value = "id",
      type = IdType.AUTO
   )
   private Long id;
   @TableField("request_id")
   private String requestId;
   @TableField(
      value = "active_guard",
      updateStrategy = FieldStrategy.ALWAYS
   )
   private Integer activeGuard;
   @TableField("web_user_id")
   private Long webUserId;
   @TableField("chat_id")
   private Long chatId;
   @TableField("telegram_user_id")
   private Long telegramUserId;
   @TableField("config_id")
   private Long configId;
   @TableField("config_name")
   private String configName;
   @TableField("redeem_type")
   private String redeemType;
   @TableField("redeem_days")
   private Integer redeemDays;
   @TableField("required_points")
   private Integer requiredPoints;
   @TableField("server_id")
   private Long serverId;
   @TableField("server_name")
   private String serverName;
   @TableField("target_user_id")
   private Long targetUserId;
   @TableField("target_user_name")
   private String targetUserName;
   @TableField("status")
   private String status;
   @TableField("result_message")
   private String resultMessage;
   @TableField("expiration_date")
   private Date expirationDate;
   @TableField("finished_at")
   private Date finishedAt;

   @Generated
   public Long getId() {
      return this.id;
   }

   @Generated
   public String getRequestId() {
      return this.requestId;
   }

   @Generated
   public Integer getActiveGuard() {
      return this.activeGuard;
   }

   @Generated
   public Long getWebUserId() {
      return this.webUserId;
   }

   @Generated
   public Long getChatId() {
      return this.chatId;
   }

   @Generated
   public Long getTelegramUserId() {
      return this.telegramUserId;
   }

   @Generated
   public Long getConfigId() {
      return this.configId;
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
   public Long getTargetUserId() {
      return this.targetUserId;
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
   public void setId(final Long id) {
      this.id = id;
   }

   @Generated
   public void setRequestId(final String requestId) {
      this.requestId = requestId;
   }

   @Generated
   public void setActiveGuard(final Integer activeGuard) {
      this.activeGuard = activeGuard;
   }

   @Generated
   public void setWebUserId(final Long webUserId) {
      this.webUserId = webUserId;
   }

   @Generated
   public void setChatId(final Long chatId) {
      this.chatId = chatId;
   }

   @Generated
   public void setTelegramUserId(final Long telegramUserId) {
      this.telegramUserId = telegramUserId;
   }

   @Generated
   public void setConfigId(final Long configId) {
      this.configId = configId;
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
   public void setTargetUserId(final Long targetUserId) {
      this.targetUserId = targetUserId;
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
   @Override
   public String toString() {
      return "PointsBotRedeemRecord(id="
         + this.getId()
         + ", requestId="
         + this.getRequestId()
         + ", activeGuard="
         + this.getActiveGuard()
         + ", webUserId="
         + this.getWebUserId()
         + ", chatId="
         + this.getChatId()
         + ", telegramUserId="
         + this.getTelegramUserId()
         + ", configId="
         + this.getConfigId()
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
         + ", targetUserId="
         + this.getTargetUserId()
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
         + ")";
   }

   @Generated
   @Override
   public boolean equals(final Object o) {
      if (o == this) {
         return true;
      } else if (!(o instanceof PointsBotRedeemRecord other)) {
         return false;
      } else if (!other.canEqual(this)) {
         return false;
      } else if (!super.equals(o)) {
         return false;
      } else {
         Object this$id = this.getId();
         Object other$id = other.getId();
         if (this$id == null ? other$id == null : this$id.equals(other$id)) {
            Object this$activeGuard = this.getActiveGuard();
            Object other$activeGuard = other.getActiveGuard();
            if (this$activeGuard == null ? other$activeGuard == null : this$activeGuard.equals(other$activeGuard)) {
               Object this$webUserId = this.getWebUserId();
               Object other$webUserId = other.getWebUserId();
               if (this$webUserId == null ? other$webUserId == null : this$webUserId.equals(other$webUserId)) {
                  Object this$chatId = this.getChatId();
                  Object other$chatId = other.getChatId();
                  if (this$chatId == null ? other$chatId == null : this$chatId.equals(other$chatId)) {
                     Object this$telegramUserId = this.getTelegramUserId();
                     Object other$telegramUserId = other.getTelegramUserId();
                     if (this$telegramUserId == null ? other$telegramUserId == null : this$telegramUserId.equals(other$telegramUserId)) {
                        Object this$configId = this.getConfigId();
                        Object other$configId = other.getConfigId();
                        if (this$configId == null ? other$configId == null : this$configId.equals(other$configId)) {
                           Object this$redeemDays = this.getRedeemDays();
                           Object other$redeemDays = other.getRedeemDays();
                           if (this$redeemDays == null ? other$redeemDays == null : this$redeemDays.equals(other$redeemDays)) {
                              Object this$requiredPoints = this.getRequiredPoints();
                              Object other$requiredPoints = other.getRequiredPoints();
                              if (this$requiredPoints == null ? other$requiredPoints == null : this$requiredPoints.equals(other$requiredPoints)) {
                                 Object this$serverId = this.getServerId();
                                 Object other$serverId = other.getServerId();
                                 if (this$serverId == null ? other$serverId == null : this$serverId.equals(other$serverId)) {
                                    Object this$targetUserId = this.getTargetUserId();
                                    Object other$targetUserId = other.getTargetUserId();
                                    if (this$targetUserId == null ? other$targetUserId == null : this$targetUserId.equals(other$targetUserId)) {
                                       Object this$requestId = this.getRequestId();
                                       Object other$requestId = other.getRequestId();
                                       if (this$requestId == null ? other$requestId == null : this$requestId.equals(other$requestId)) {
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
                                                   if (this$targetUserName == null
                                                      ? other$targetUserName == null
                                                      : this$targetUserName.equals(other$targetUserName)) {
                                                      Object this$status = this.getStatus();
                                                      Object other$status = other.getStatus();
                                                      if (this$status == null ? other$status == null : this$status.equals(other$status)) {
                                                         Object this$resultMessage = this.getResultMessage();
                                                         Object other$resultMessage = other.getResultMessage();
                                                         if (this$resultMessage == null
                                                            ? other$resultMessage == null
                                                            : this$resultMessage.equals(other$resultMessage)) {
                                                            Object this$expirationDate = this.getExpirationDate();
                                                            Object other$expirationDate = other.getExpirationDate();
                                                            if (this$expirationDate == null
                                                               ? other$expirationDate == null
                                                               : this$expirationDate.equals(other$expirationDate)) {
                                                               Object this$finishedAt = this.getFinishedAt();
                                                               Object other$finishedAt = other.getFinishedAt();
                                                               return this$finishedAt == null
                                                                  ? other$finishedAt == null
                                                                  : this$finishedAt.equals(other$finishedAt);
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
      return other instanceof PointsBotRedeemRecord;
   }

   @Generated
   @Override
   public int hashCode() {
      int PRIME = 59;
      int result = super.hashCode();
      Object $id = this.getId();
      result = result * 59 + ($id == null ? 43 : $id.hashCode());
      Object $activeGuard = this.getActiveGuard();
      result = result * 59 + ($activeGuard == null ? 43 : $activeGuard.hashCode());
      Object $webUserId = this.getWebUserId();
      result = result * 59 + ($webUserId == null ? 43 : $webUserId.hashCode());
      Object $chatId = this.getChatId();
      result = result * 59 + ($chatId == null ? 43 : $chatId.hashCode());
      Object $telegramUserId = this.getTelegramUserId();
      result = result * 59 + ($telegramUserId == null ? 43 : $telegramUserId.hashCode());
      Object $configId = this.getConfigId();
      result = result * 59 + ($configId == null ? 43 : $configId.hashCode());
      Object $redeemDays = this.getRedeemDays();
      result = result * 59 + ($redeemDays == null ? 43 : $redeemDays.hashCode());
      Object $requiredPoints = this.getRequiredPoints();
      result = result * 59 + ($requiredPoints == null ? 43 : $requiredPoints.hashCode());
      Object $serverId = this.getServerId();
      result = result * 59 + ($serverId == null ? 43 : $serverId.hashCode());
      Object $targetUserId = this.getTargetUserId();
      result = result * 59 + ($targetUserId == null ? 43 : $targetUserId.hashCode());
      Object $requestId = this.getRequestId();
      result = result * 59 + ($requestId == null ? 43 : $requestId.hashCode());
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
      return result * 59 + ($finishedAt == null ? 43 : $finishedAt.hashCode());
   }
}
