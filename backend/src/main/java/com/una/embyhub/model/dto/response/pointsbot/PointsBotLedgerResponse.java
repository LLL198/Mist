package com.una.embyhub.model.dto.response.pointsbot;

import com.diboot.core.binding.annotation.BindField;
import com.una.embyhub.model.entity.EmbyInfo;
import java.io.Serializable;
import java.util.Date;
import lombok.Generated;

public class PointsBotLedgerResponse implements Serializable {
   private Long id;
   private Long chatId;
   private Long userId;
   private Integer delta;
   private String reason;
   private String refId;
   private Long transferFromUserId;
   private String transferFromUsername;
   private String transferFromDisplayName;
   private Long transferToUserId;
   private String transferToUsername;
   private String transferToDisplayName;
   private Long serverId;
   @BindField(
      entity = EmbyInfo.class,
      field = "serverName",
      condition = "this.server_id=id"
   )
   private String serverName;
   private Date createDatetime;
   private Date updateDatetime;
   private String createUserName;
   private String updateUserName;
   private Long updateUserId;
   private Long createUserId;
   private Integer delFlag;

   @Generated
   public Long getId() {
      return this.id;
   }

   @Generated
   public Long getChatId() {
      return this.chatId;
   }

   @Generated
   public Long getUserId() {
      return this.userId;
   }

   @Generated
   public Integer getDelta() {
      return this.delta;
   }

   @Generated
   public String getReason() {
      return this.reason;
   }

   @Generated
   public String getRefId() {
      return this.refId;
   }

   @Generated
   public Long getTransferFromUserId() {
      return this.transferFromUserId;
   }

   @Generated
   public String getTransferFromUsername() {
      return this.transferFromUsername;
   }

   @Generated
   public String getTransferFromDisplayName() {
      return this.transferFromDisplayName;
   }

   @Generated
   public Long getTransferToUserId() {
      return this.transferToUserId;
   }

   @Generated
   public String getTransferToUsername() {
      return this.transferToUsername;
   }

   @Generated
   public String getTransferToDisplayName() {
      return this.transferToDisplayName;
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
   public Date getCreateDatetime() {
      return this.createDatetime;
   }

   @Generated
   public Date getUpdateDatetime() {
      return this.updateDatetime;
   }

   @Generated
   public String getCreateUserName() {
      return this.createUserName;
   }

   @Generated
   public String getUpdateUserName() {
      return this.updateUserName;
   }

   @Generated
   public Long getUpdateUserId() {
      return this.updateUserId;
   }

   @Generated
   public Long getCreateUserId() {
      return this.createUserId;
   }

   @Generated
   public Integer getDelFlag() {
      return this.delFlag;
   }

   @Generated
   public void setId(final Long id) {
      this.id = id;
   }

   @Generated
   public void setChatId(final Long chatId) {
      this.chatId = chatId;
   }

   @Generated
   public void setUserId(final Long userId) {
      this.userId = userId;
   }

   @Generated
   public void setDelta(final Integer delta) {
      this.delta = delta;
   }

   @Generated
   public void setReason(final String reason) {
      this.reason = reason;
   }

   @Generated
   public void setRefId(final String refId) {
      this.refId = refId;
   }

   @Generated
   public void setTransferFromUserId(final Long transferFromUserId) {
      this.transferFromUserId = transferFromUserId;
   }

   @Generated
   public void setTransferFromUsername(final String transferFromUsername) {
      this.transferFromUsername = transferFromUsername;
   }

   @Generated
   public void setTransferFromDisplayName(final String transferFromDisplayName) {
      this.transferFromDisplayName = transferFromDisplayName;
   }

   @Generated
   public void setTransferToUserId(final Long transferToUserId) {
      this.transferToUserId = transferToUserId;
   }

   @Generated
   public void setTransferToUsername(final String transferToUsername) {
      this.transferToUsername = transferToUsername;
   }

   @Generated
   public void setTransferToDisplayName(final String transferToDisplayName) {
      this.transferToDisplayName = transferToDisplayName;
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
   public void setCreateDatetime(final Date createDatetime) {
      this.createDatetime = createDatetime;
   }

   @Generated
   public void setUpdateDatetime(final Date updateDatetime) {
      this.updateDatetime = updateDatetime;
   }

   @Generated
   public void setCreateUserName(final String createUserName) {
      this.createUserName = createUserName;
   }

   @Generated
   public void setUpdateUserName(final String updateUserName) {
      this.updateUserName = updateUserName;
   }

   @Generated
   public void setUpdateUserId(final Long updateUserId) {
      this.updateUserId = updateUserId;
   }

   @Generated
   public void setCreateUserId(final Long createUserId) {
      this.createUserId = createUserId;
   }

   @Generated
   public void setDelFlag(final Integer delFlag) {
      this.delFlag = delFlag;
   }

   @Generated
   @Override
   public boolean equals(final Object o) {
      if (o == this) {
         return true;
      } else if (!(o instanceof PointsBotLedgerResponse other)) {
         return false;
      } else if (!other.canEqual(this)) {
         return false;
      } else {
         Object this$id = this.getId();
         Object other$id = other.getId();
         if (this$id == null ? other$id == null : this$id.equals(other$id)) {
            Object this$chatId = this.getChatId();
            Object other$chatId = other.getChatId();
            if (this$chatId == null ? other$chatId == null : this$chatId.equals(other$chatId)) {
               Object this$userId = this.getUserId();
               Object other$userId = other.getUserId();
               if (this$userId == null ? other$userId == null : this$userId.equals(other$userId)) {
                  Object this$delta = this.getDelta();
                  Object other$delta = other.getDelta();
                  if (this$delta == null ? other$delta == null : this$delta.equals(other$delta)) {
                     Object this$transferFromUserId = this.getTransferFromUserId();
                     Object other$transferFromUserId = other.getTransferFromUserId();
                     if (this$transferFromUserId == null ? other$transferFromUserId == null : this$transferFromUserId.equals(other$transferFromUserId)) {
                        Object this$transferToUserId = this.getTransferToUserId();
                        Object other$transferToUserId = other.getTransferToUserId();
                        if (this$transferToUserId == null ? other$transferToUserId == null : this$transferToUserId.equals(other$transferToUserId)) {
                           Object this$serverId = this.getServerId();
                           Object other$serverId = other.getServerId();
                           if (this$serverId == null ? other$serverId == null : this$serverId.equals(other$serverId)) {
                              Object this$updateUserId = this.getUpdateUserId();
                              Object other$updateUserId = other.getUpdateUserId();
                              if (this$updateUserId == null ? other$updateUserId == null : this$updateUserId.equals(other$updateUserId)) {
                                 Object this$createUserId = this.getCreateUserId();
                                 Object other$createUserId = other.getCreateUserId();
                                 if (this$createUserId == null ? other$createUserId == null : this$createUserId.equals(other$createUserId)) {
                                    Object this$delFlag = this.getDelFlag();
                                    Object other$delFlag = other.getDelFlag();
                                    if (this$delFlag == null ? other$delFlag == null : this$delFlag.equals(other$delFlag)) {
                                       Object this$reason = this.getReason();
                                       Object other$reason = other.getReason();
                                       if (this$reason == null ? other$reason == null : this$reason.equals(other$reason)) {
                                          Object this$refId = this.getRefId();
                                          Object other$refId = other.getRefId();
                                          if (this$refId == null ? other$refId == null : this$refId.equals(other$refId)) {
                                             Object this$transferFromUsername = this.getTransferFromUsername();
                                             Object other$transferFromUsername = other.getTransferFromUsername();
                                             if (this$transferFromUsername == null
                                                ? other$transferFromUsername == null
                                                : this$transferFromUsername.equals(other$transferFromUsername)) {
                                                Object this$transferFromDisplayName = this.getTransferFromDisplayName();
                                                Object other$transferFromDisplayName = other.getTransferFromDisplayName();
                                                if (this$transferFromDisplayName == null
                                                   ? other$transferFromDisplayName == null
                                                   : this$transferFromDisplayName.equals(other$transferFromDisplayName)) {
                                                   Object this$transferToUsername = this.getTransferToUsername();
                                                   Object other$transferToUsername = other.getTransferToUsername();
                                                   if (this$transferToUsername == null
                                                      ? other$transferToUsername == null
                                                      : this$transferToUsername.equals(other$transferToUsername)) {
                                                      Object this$transferToDisplayName = this.getTransferToDisplayName();
                                                      Object other$transferToDisplayName = other.getTransferToDisplayName();
                                                      if (this$transferToDisplayName == null
                                                         ? other$transferToDisplayName == null
                                                         : this$transferToDisplayName.equals(other$transferToDisplayName)) {
                                                         Object this$serverName = this.getServerName();
                                                         Object other$serverName = other.getServerName();
                                                         if (this$serverName == null ? other$serverName == null : this$serverName.equals(other$serverName)) {
                                                            Object this$createDatetime = this.getCreateDatetime();
                                                            Object other$createDatetime = other.getCreateDatetime();
                                                            if (this$createDatetime == null
                                                               ? other$createDatetime == null
                                                               : this$createDatetime.equals(other$createDatetime)) {
                                                               Object this$updateDatetime = this.getUpdateDatetime();
                                                               Object other$updateDatetime = other.getUpdateDatetime();
                                                               if (this$updateDatetime == null
                                                                  ? other$updateDatetime == null
                                                                  : this$updateDatetime.equals(other$updateDatetime)) {
                                                                  Object this$createUserName = this.getCreateUserName();
                                                                  Object other$createUserName = other.getCreateUserName();
                                                                  if (this$createUserName == null
                                                                     ? other$createUserName == null
                                                                     : this$createUserName.equals(other$createUserName)) {
                                                                     Object this$updateUserName = this.getUpdateUserName();
                                                                     Object other$updateUserName = other.getUpdateUserName();
                                                                     return this$updateUserName == null
                                                                        ? other$updateUserName == null
                                                                        : this$updateUserName.equals(other$updateUserName);
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
      return other instanceof PointsBotLedgerResponse;
   }

   @Generated
   @Override
   public int hashCode() {
      int PRIME = 59;
      int result = 1;
      Object $id = this.getId();
      result = result * 59 + ($id == null ? 43 : $id.hashCode());
      Object $chatId = this.getChatId();
      result = result * 59 + ($chatId == null ? 43 : $chatId.hashCode());
      Object $userId = this.getUserId();
      result = result * 59 + ($userId == null ? 43 : $userId.hashCode());
      Object $delta = this.getDelta();
      result = result * 59 + ($delta == null ? 43 : $delta.hashCode());
      Object $transferFromUserId = this.getTransferFromUserId();
      result = result * 59 + ($transferFromUserId == null ? 43 : $transferFromUserId.hashCode());
      Object $transferToUserId = this.getTransferToUserId();
      result = result * 59 + ($transferToUserId == null ? 43 : $transferToUserId.hashCode());
      Object $serverId = this.getServerId();
      result = result * 59 + ($serverId == null ? 43 : $serverId.hashCode());
      Object $updateUserId = this.getUpdateUserId();
      result = result * 59 + ($updateUserId == null ? 43 : $updateUserId.hashCode());
      Object $createUserId = this.getCreateUserId();
      result = result * 59 + ($createUserId == null ? 43 : $createUserId.hashCode());
      Object $delFlag = this.getDelFlag();
      result = result * 59 + ($delFlag == null ? 43 : $delFlag.hashCode());
      Object $reason = this.getReason();
      result = result * 59 + ($reason == null ? 43 : $reason.hashCode());
      Object $refId = this.getRefId();
      result = result * 59 + ($refId == null ? 43 : $refId.hashCode());
      Object $transferFromUsername = this.getTransferFromUsername();
      result = result * 59 + ($transferFromUsername == null ? 43 : $transferFromUsername.hashCode());
      Object $transferFromDisplayName = this.getTransferFromDisplayName();
      result = result * 59 + ($transferFromDisplayName == null ? 43 : $transferFromDisplayName.hashCode());
      Object $transferToUsername = this.getTransferToUsername();
      result = result * 59 + ($transferToUsername == null ? 43 : $transferToUsername.hashCode());
      Object $transferToDisplayName = this.getTransferToDisplayName();
      result = result * 59 + ($transferToDisplayName == null ? 43 : $transferToDisplayName.hashCode());
      Object $serverName = this.getServerName();
      result = result * 59 + ($serverName == null ? 43 : $serverName.hashCode());
      Object $createDatetime = this.getCreateDatetime();
      result = result * 59 + ($createDatetime == null ? 43 : $createDatetime.hashCode());
      Object $updateDatetime = this.getUpdateDatetime();
      result = result * 59 + ($updateDatetime == null ? 43 : $updateDatetime.hashCode());
      Object $createUserName = this.getCreateUserName();
      result = result * 59 + ($createUserName == null ? 43 : $createUserName.hashCode());
      Object $updateUserName = this.getUpdateUserName();
      return result * 59 + ($updateUserName == null ? 43 : $updateUserName.hashCode());
   }

   @Generated
   @Override
   public String toString() {
      return "PointsBotLedgerResponse(id="
         + this.getId()
         + ", chatId="
         + this.getChatId()
         + ", userId="
         + this.getUserId()
         + ", delta="
         + this.getDelta()
         + ", reason="
         + this.getReason()
         + ", refId="
         + this.getRefId()
         + ", transferFromUserId="
         + this.getTransferFromUserId()
         + ", transferFromUsername="
         + this.getTransferFromUsername()
         + ", transferFromDisplayName="
         + this.getTransferFromDisplayName()
         + ", transferToUserId="
         + this.getTransferToUserId()
         + ", transferToUsername="
         + this.getTransferToUsername()
         + ", transferToDisplayName="
         + this.getTransferToDisplayName()
         + ", serverId="
         + this.getServerId()
         + ", serverName="
         + this.getServerName()
         + ", createDatetime="
         + this.getCreateDatetime()
         + ", updateDatetime="
         + this.getUpdateDatetime()
         + ", createUserName="
         + this.getCreateUserName()
         + ", updateUserName="
         + this.getUpdateUserName()
         + ", updateUserId="
         + this.getUpdateUserId()
         + ", createUserId="
         + this.getCreateUserId()
         + ", delFlag="
         + this.getDelFlag()
         + ")";
   }
}
