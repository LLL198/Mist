package com.una.embyhub.model.dto.response.telegram;

import java.io.Serializable;
import java.util.Date;
import lombok.Generated;

public class TelegramBindingReviewResponse implements Serializable {
   private Long id;
   private String reviewUuid;
   private Long userId;
   private String embyUserName;
   private String telegramUserId;
   private String telegramUsername;
   private String oldTelegramUserId;
   private String oldTelegramUsername;
   private String actionType;
   private String actionTypeName;
   private String requestSource;
   private String requestSourceName;
   private Integer status;
   private String statusName;
   private Long reviewerUserId;
   private String reviewerUserName;
   private String reviewRemark;
   private Date reviewDatetime;
   private Date createDatetime;

   public void setActionType(String actionType) {
      this.actionType = actionType;
      String var2 = actionType == null ? "" : actionType;

      this.actionTypeName = switch (var2) {
         case "UNBIND" -> "解绑";
         case "REBIND" -> "换绑";
         default -> "绑定";
      };
   }

   public void setRequestSource(String requestSource) {
      this.requestSource = requestSource;
      String var2 = requestSource == null ? "" : requestSource;

      this.requestSourceName = switch (var2) {
         case "BOT", "BOT_CREDENTIAL" -> "机器人命令";
         case "BOT_REGISTER" -> "机器人注册命令";
         case "BOT_CARD" -> "机器人卡密命令";
         default -> "网页";
      };
   }

   public void setStatus(Integer status) {
      this.status = status;
      String var10001;
      if (status == null) {
         var10001 = "待审批";
      } else {
         switch (status) {
            case 0:
               var10001 = "待审批";
               break;
            case 1:
               var10001 = "已通过";
               break;
            case 2:
               var10001 = "已拒绝";
               break;
            case 3:
               var10001 = "用户自助取消";
               break;
            default:
               var10001 = "未知状态";
         }
      }

      this.statusName = var10001;
   }

   @Generated
   public Long getId() {
      return this.id;
   }

   @Generated
   public String getReviewUuid() {
      return this.reviewUuid;
   }

   @Generated
   public Long getUserId() {
      return this.userId;
   }

   @Generated
   public String getEmbyUserName() {
      return this.embyUserName;
   }

   @Generated
   public String getTelegramUserId() {
      return this.telegramUserId;
   }

   @Generated
   public String getTelegramUsername() {
      return this.telegramUsername;
   }

   @Generated
   public String getOldTelegramUserId() {
      return this.oldTelegramUserId;
   }

   @Generated
   public String getOldTelegramUsername() {
      return this.oldTelegramUsername;
   }

   @Generated
   public String getActionType() {
      return this.actionType;
   }

   @Generated
   public String getActionTypeName() {
      return this.actionTypeName;
   }

   @Generated
   public String getRequestSource() {
      return this.requestSource;
   }

   @Generated
   public String getRequestSourceName() {
      return this.requestSourceName;
   }

   @Generated
   public Integer getStatus() {
      return this.status;
   }

   @Generated
   public String getStatusName() {
      return this.statusName;
   }

   @Generated
   public Long getReviewerUserId() {
      return this.reviewerUserId;
   }

   @Generated
   public String getReviewerUserName() {
      return this.reviewerUserName;
   }

   @Generated
   public String getReviewRemark() {
      return this.reviewRemark;
   }

   @Generated
   public Date getReviewDatetime() {
      return this.reviewDatetime;
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
   public void setReviewUuid(final String reviewUuid) {
      this.reviewUuid = reviewUuid;
   }

   @Generated
   public void setUserId(final Long userId) {
      this.userId = userId;
   }

   @Generated
   public void setEmbyUserName(final String embyUserName) {
      this.embyUserName = embyUserName;
   }

   @Generated
   public void setTelegramUserId(final String telegramUserId) {
      this.telegramUserId = telegramUserId;
   }

   @Generated
   public void setTelegramUsername(final String telegramUsername) {
      this.telegramUsername = telegramUsername;
   }

   @Generated
   public void setOldTelegramUserId(final String oldTelegramUserId) {
      this.oldTelegramUserId = oldTelegramUserId;
   }

   @Generated
   public void setOldTelegramUsername(final String oldTelegramUsername) {
      this.oldTelegramUsername = oldTelegramUsername;
   }

   @Generated
   public void setActionTypeName(final String actionTypeName) {
      this.actionTypeName = actionTypeName;
   }

   @Generated
   public void setRequestSourceName(final String requestSourceName) {
      this.requestSourceName = requestSourceName;
   }

   @Generated
   public void setStatusName(final String statusName) {
      this.statusName = statusName;
   }

   @Generated
   public void setReviewerUserId(final Long reviewerUserId) {
      this.reviewerUserId = reviewerUserId;
   }

   @Generated
   public void setReviewerUserName(final String reviewerUserName) {
      this.reviewerUserName = reviewerUserName;
   }

   @Generated
   public void setReviewRemark(final String reviewRemark) {
      this.reviewRemark = reviewRemark;
   }

   @Generated
   public void setReviewDatetime(final Date reviewDatetime) {
      this.reviewDatetime = reviewDatetime;
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
      } else if (!(o instanceof TelegramBindingReviewResponse other)) {
         return false;
      } else if (!other.canEqual(this)) {
         return false;
      } else {
         Object this$id = this.getId();
         Object other$id = other.getId();
         if (this$id == null ? other$id == null : this$id.equals(other$id)) {
            Object this$userId = this.getUserId();
            Object other$userId = other.getUserId();
            if (this$userId == null ? other$userId == null : this$userId.equals(other$userId)) {
               Object this$status = this.getStatus();
               Object other$status = other.getStatus();
               if (this$status == null ? other$status == null : this$status.equals(other$status)) {
                  Object this$reviewerUserId = this.getReviewerUserId();
                  Object other$reviewerUserId = other.getReviewerUserId();
                  if (this$reviewerUserId == null ? other$reviewerUserId == null : this$reviewerUserId.equals(other$reviewerUserId)) {
                     Object this$reviewUuid = this.getReviewUuid();
                     Object other$reviewUuid = other.getReviewUuid();
                     if (this$reviewUuid == null ? other$reviewUuid == null : this$reviewUuid.equals(other$reviewUuid)) {
                        Object this$embyUserName = this.getEmbyUserName();
                        Object other$embyUserName = other.getEmbyUserName();
                        if (this$embyUserName == null ? other$embyUserName == null : this$embyUserName.equals(other$embyUserName)) {
                           Object this$telegramUserId = this.getTelegramUserId();
                           Object other$telegramUserId = other.getTelegramUserId();
                           if (this$telegramUserId == null ? other$telegramUserId == null : this$telegramUserId.equals(other$telegramUserId)) {
                              Object this$telegramUsername = this.getTelegramUsername();
                              Object other$telegramUsername = other.getTelegramUsername();
                              if (this$telegramUsername == null ? other$telegramUsername == null : this$telegramUsername.equals(other$telegramUsername)) {
                                 Object this$oldTelegramUserId = this.getOldTelegramUserId();
                                 Object other$oldTelegramUserId = other.getOldTelegramUserId();
                                 if (this$oldTelegramUserId == null ? other$oldTelegramUserId == null : this$oldTelegramUserId.equals(other$oldTelegramUserId)) {
                                    Object this$oldTelegramUsername = this.getOldTelegramUsername();
                                    Object other$oldTelegramUsername = other.getOldTelegramUsername();
                                    if (this$oldTelegramUsername == null
                                       ? other$oldTelegramUsername == null
                                       : this$oldTelegramUsername.equals(other$oldTelegramUsername)) {
                                       Object this$actionType = this.getActionType();
                                       Object other$actionType = other.getActionType();
                                       if (this$actionType == null ? other$actionType == null : this$actionType.equals(other$actionType)) {
                                          Object this$actionTypeName = this.getActionTypeName();
                                          Object other$actionTypeName = other.getActionTypeName();
                                          if (this$actionTypeName == null ? other$actionTypeName == null : this$actionTypeName.equals(other$actionTypeName)) {
                                             Object this$requestSource = this.getRequestSource();
                                             Object other$requestSource = other.getRequestSource();
                                             if (this$requestSource == null ? other$requestSource == null : this$requestSource.equals(other$requestSource)) {
                                                Object this$requestSourceName = this.getRequestSourceName();
                                                Object other$requestSourceName = other.getRequestSourceName();
                                                if (this$requestSourceName == null
                                                   ? other$requestSourceName == null
                                                   : this$requestSourceName.equals(other$requestSourceName)) {
                                                   Object this$statusName = this.getStatusName();
                                                   Object other$statusName = other.getStatusName();
                                                   if (this$statusName == null ? other$statusName == null : this$statusName.equals(other$statusName)) {
                                                      Object this$reviewerUserName = this.getReviewerUserName();
                                                      Object other$reviewerUserName = other.getReviewerUserName();
                                                      if (this$reviewerUserName == null
                                                         ? other$reviewerUserName == null
                                                         : this$reviewerUserName.equals(other$reviewerUserName)) {
                                                         Object this$reviewRemark = this.getReviewRemark();
                                                         Object other$reviewRemark = other.getReviewRemark();
                                                         if (this$reviewRemark == null
                                                            ? other$reviewRemark == null
                                                            : this$reviewRemark.equals(other$reviewRemark)) {
                                                            Object this$reviewDatetime = this.getReviewDatetime();
                                                            Object other$reviewDatetime = other.getReviewDatetime();
                                                            if (this$reviewDatetime == null
                                                               ? other$reviewDatetime == null
                                                               : this$reviewDatetime.equals(other$reviewDatetime)) {
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
      return other instanceof TelegramBindingReviewResponse;
   }

   @Generated
   @Override
   public int hashCode() {
      int PRIME = 59;
      int result = 1;
      Object $id = this.getId();
      result = result * 59 + ($id == null ? 43 : $id.hashCode());
      Object $userId = this.getUserId();
      result = result * 59 + ($userId == null ? 43 : $userId.hashCode());
      Object $status = this.getStatus();
      result = result * 59 + ($status == null ? 43 : $status.hashCode());
      Object $reviewerUserId = this.getReviewerUserId();
      result = result * 59 + ($reviewerUserId == null ? 43 : $reviewerUserId.hashCode());
      Object $reviewUuid = this.getReviewUuid();
      result = result * 59 + ($reviewUuid == null ? 43 : $reviewUuid.hashCode());
      Object $embyUserName = this.getEmbyUserName();
      result = result * 59 + ($embyUserName == null ? 43 : $embyUserName.hashCode());
      Object $telegramUserId = this.getTelegramUserId();
      result = result * 59 + ($telegramUserId == null ? 43 : $telegramUserId.hashCode());
      Object $telegramUsername = this.getTelegramUsername();
      result = result * 59 + ($telegramUsername == null ? 43 : $telegramUsername.hashCode());
      Object $oldTelegramUserId = this.getOldTelegramUserId();
      result = result * 59 + ($oldTelegramUserId == null ? 43 : $oldTelegramUserId.hashCode());
      Object $oldTelegramUsername = this.getOldTelegramUsername();
      result = result * 59 + ($oldTelegramUsername == null ? 43 : $oldTelegramUsername.hashCode());
      Object $actionType = this.getActionType();
      result = result * 59 + ($actionType == null ? 43 : $actionType.hashCode());
      Object $actionTypeName = this.getActionTypeName();
      result = result * 59 + ($actionTypeName == null ? 43 : $actionTypeName.hashCode());
      Object $requestSource = this.getRequestSource();
      result = result * 59 + ($requestSource == null ? 43 : $requestSource.hashCode());
      Object $requestSourceName = this.getRequestSourceName();
      result = result * 59 + ($requestSourceName == null ? 43 : $requestSourceName.hashCode());
      Object $statusName = this.getStatusName();
      result = result * 59 + ($statusName == null ? 43 : $statusName.hashCode());
      Object $reviewerUserName = this.getReviewerUserName();
      result = result * 59 + ($reviewerUserName == null ? 43 : $reviewerUserName.hashCode());
      Object $reviewRemark = this.getReviewRemark();
      result = result * 59 + ($reviewRemark == null ? 43 : $reviewRemark.hashCode());
      Object $reviewDatetime = this.getReviewDatetime();
      result = result * 59 + ($reviewDatetime == null ? 43 : $reviewDatetime.hashCode());
      Object $createDatetime = this.getCreateDatetime();
      return result * 59 + ($createDatetime == null ? 43 : $createDatetime.hashCode());
   }

   @Generated
   @Override
   public String toString() {
      return "TelegramBindingReviewResponse(id="
         + this.getId()
         + ", reviewUuid="
         + this.getReviewUuid()
         + ", userId="
         + this.getUserId()
         + ", embyUserName="
         + this.getEmbyUserName()
         + ", telegramUserId="
         + this.getTelegramUserId()
         + ", telegramUsername="
         + this.getTelegramUsername()
         + ", oldTelegramUserId="
         + this.getOldTelegramUserId()
         + ", oldTelegramUsername="
         + this.getOldTelegramUsername()
         + ", actionType="
         + this.getActionType()
         + ", actionTypeName="
         + this.getActionTypeName()
         + ", requestSource="
         + this.getRequestSource()
         + ", requestSourceName="
         + this.getRequestSourceName()
         + ", status="
         + this.getStatus()
         + ", statusName="
         + this.getStatusName()
         + ", reviewerUserId="
         + this.getReviewerUserId()
         + ", reviewerUserName="
         + this.getReviewerUserName()
         + ", reviewRemark="
         + this.getReviewRemark()
         + ", reviewDatetime="
         + this.getReviewDatetime()
         + ", createDatetime="
         + this.getCreateDatetime()
         + ")";
   }
}
