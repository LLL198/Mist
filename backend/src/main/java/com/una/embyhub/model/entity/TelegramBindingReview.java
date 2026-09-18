package com.una.embyhub.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.util.Date;
import lombok.Generated;

@TableName("telegram_binding_review")
public class TelegramBindingReview extends BaseEntity {
   public static final String ACTION_BIND = "BIND";
   public static final String ACTION_UNBIND = "UNBIND";
   public static final String ACTION_REBIND = "REBIND";
   public static final int STATUS_PENDING = 0;
   public static final int STATUS_APPROVED = 1;
   public static final int STATUS_REJECTED = 2;
   public static final int STATUS_CANCELLED = 3;
   public static final String SOURCE_WEB = "WEB";
   public static final String SOURCE_BOT = "BOT";
   public static final String SOURCE_BOT_CREDENTIAL = "BOT_CREDENTIAL";
   public static final String SOURCE_BOT_REGISTER = "BOT_REGISTER";
   public static final String SOURCE_BOT_CARD = "BOT_CARD";
   @TableId(
      value = "id",
      type = IdType.AUTO
   )
   private Long id;
   @TableField("review_uuid")
   private String reviewUuid;
   @TableField("user_id")
   private Long userId;
   @TableField("emby_user_name")
   private String embyUserName;
   @TableField("telegram_user_id")
   private String telegramUserId;
   @TableField("telegram_username")
   private String telegramUsername;
   @TableField("telegram_avatar")
   private String telegramAvatar;
   @TableField("old_telegram_user_id")
   private String oldTelegramUserId;
   @TableField("old_telegram_username")
   private String oldTelegramUsername;
   @TableField("old_telegram_avatar")
   private String oldTelegramAvatar;
   @TableField("action_type")
   private String actionType;
   @TableField("request_source")
   private String requestSource;
   @TableField("replace_existing")
   private Integer replaceExisting;
   @TableField("status")
   private Integer status;
   @TableField("reviewer_user_id")
   private Long reviewerUserId;
   @TableField("reviewer_user_name")
   private String reviewerUserName;
   @TableField("review_remark")
   private String reviewRemark;
   @TableField("review_datetime")
   private Date reviewDatetime;

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
   public String getTelegramAvatar() {
      return this.telegramAvatar;
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
   public String getOldTelegramAvatar() {
      return this.oldTelegramAvatar;
   }

   @Generated
   public String getActionType() {
      return this.actionType;
   }

   @Generated
   public String getRequestSource() {
      return this.requestSource;
   }

   @Generated
   public Integer getReplaceExisting() {
      return this.replaceExisting;
   }

   @Generated
   public Integer getStatus() {
      return this.status;
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
   public void setTelegramAvatar(final String telegramAvatar) {
      this.telegramAvatar = telegramAvatar;
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
   public void setOldTelegramAvatar(final String oldTelegramAvatar) {
      this.oldTelegramAvatar = oldTelegramAvatar;
   }

   @Generated
   public void setActionType(final String actionType) {
      this.actionType = actionType;
   }

   @Generated
   public void setRequestSource(final String requestSource) {
      this.requestSource = requestSource;
   }

   @Generated
   public void setReplaceExisting(final Integer replaceExisting) {
      this.replaceExisting = replaceExisting;
   }

   @Generated
   public void setStatus(final Integer status) {
      this.status = status;
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
   @Override
   public String toString() {
      return "TelegramBindingReview(id="
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
         + ", telegramAvatar="
         + this.getTelegramAvatar()
         + ", oldTelegramUserId="
         + this.getOldTelegramUserId()
         + ", oldTelegramUsername="
         + this.getOldTelegramUsername()
         + ", oldTelegramAvatar="
         + this.getOldTelegramAvatar()
         + ", actionType="
         + this.getActionType()
         + ", requestSource="
         + this.getRequestSource()
         + ", replaceExisting="
         + this.getReplaceExisting()
         + ", status="
         + this.getStatus()
         + ", reviewerUserId="
         + this.getReviewerUserId()
         + ", reviewerUserName="
         + this.getReviewerUserName()
         + ", reviewRemark="
         + this.getReviewRemark()
         + ", reviewDatetime="
         + this.getReviewDatetime()
         + ")";
   }

   @Generated
   @Override
   public boolean equals(final Object o) {
      if (o == this) {
         return true;
      } else if (!(o instanceof TelegramBindingReview other)) {
         return false;
      } else if (!other.canEqual(this)) {
         return false;
      } else if (!super.equals(o)) {
         return false;
      } else {
         Object this$id = this.getId();
         Object other$id = other.getId();
         if (this$id == null ? other$id == null : this$id.equals(other$id)) {
            Object this$userId = this.getUserId();
            Object other$userId = other.getUserId();
            if (this$userId == null ? other$userId == null : this$userId.equals(other$userId)) {
               Object this$replaceExisting = this.getReplaceExisting();
               Object other$replaceExisting = other.getReplaceExisting();
               if (this$replaceExisting == null ? other$replaceExisting == null : this$replaceExisting.equals(other$replaceExisting)) {
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
                                    Object this$telegramAvatar = this.getTelegramAvatar();
                                    Object other$telegramAvatar = other.getTelegramAvatar();
                                    if (this$telegramAvatar == null ? other$telegramAvatar == null : this$telegramAvatar.equals(other$telegramAvatar)) {
                                       Object this$oldTelegramUserId = this.getOldTelegramUserId();
                                       Object other$oldTelegramUserId = other.getOldTelegramUserId();
                                       if (this$oldTelegramUserId == null
                                          ? other$oldTelegramUserId == null
                                          : this$oldTelegramUserId.equals(other$oldTelegramUserId)) {
                                          Object this$oldTelegramUsername = this.getOldTelegramUsername();
                                          Object other$oldTelegramUsername = other.getOldTelegramUsername();
                                          if (this$oldTelegramUsername == null
                                             ? other$oldTelegramUsername == null
                                             : this$oldTelegramUsername.equals(other$oldTelegramUsername)) {
                                             Object this$oldTelegramAvatar = this.getOldTelegramAvatar();
                                             Object other$oldTelegramAvatar = other.getOldTelegramAvatar();
                                             if (this$oldTelegramAvatar == null
                                                ? other$oldTelegramAvatar == null
                                                : this$oldTelegramAvatar.equals(other$oldTelegramAvatar)) {
                                                Object this$actionType = this.getActionType();
                                                Object other$actionType = other.getActionType();
                                                if (this$actionType == null ? other$actionType == null : this$actionType.equals(other$actionType)) {
                                                   Object this$requestSource = this.getRequestSource();
                                                   Object other$requestSource = other.getRequestSource();
                                                   if (this$requestSource == null
                                                      ? other$requestSource == null
                                                      : this$requestSource.equals(other$requestSource)) {
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
                                                            return this$reviewDatetime == null
                                                               ? other$reviewDatetime == null
                                                               : this$reviewDatetime.equals(other$reviewDatetime);
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
      return other instanceof TelegramBindingReview;
   }

   @Generated
   @Override
   public int hashCode() {
      int PRIME = 59;
      int result = super.hashCode();
      Object $id = this.getId();
      result = result * 59 + ($id == null ? 43 : $id.hashCode());
      Object $userId = this.getUserId();
      result = result * 59 + ($userId == null ? 43 : $userId.hashCode());
      Object $replaceExisting = this.getReplaceExisting();
      result = result * 59 + ($replaceExisting == null ? 43 : $replaceExisting.hashCode());
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
      Object $telegramAvatar = this.getTelegramAvatar();
      result = result * 59 + ($telegramAvatar == null ? 43 : $telegramAvatar.hashCode());
      Object $oldTelegramUserId = this.getOldTelegramUserId();
      result = result * 59 + ($oldTelegramUserId == null ? 43 : $oldTelegramUserId.hashCode());
      Object $oldTelegramUsername = this.getOldTelegramUsername();
      result = result * 59 + ($oldTelegramUsername == null ? 43 : $oldTelegramUsername.hashCode());
      Object $oldTelegramAvatar = this.getOldTelegramAvatar();
      result = result * 59 + ($oldTelegramAvatar == null ? 43 : $oldTelegramAvatar.hashCode());
      Object $actionType = this.getActionType();
      result = result * 59 + ($actionType == null ? 43 : $actionType.hashCode());
      Object $requestSource = this.getRequestSource();
      result = result * 59 + ($requestSource == null ? 43 : $requestSource.hashCode());
      Object $reviewerUserName = this.getReviewerUserName();
      result = result * 59 + ($reviewerUserName == null ? 43 : $reviewerUserName.hashCode());
      Object $reviewRemark = this.getReviewRemark();
      result = result * 59 + ($reviewRemark == null ? 43 : $reviewRemark.hashCode());
      Object $reviewDatetime = this.getReviewDatetime();
      return result * 59 + ($reviewDatetime == null ? 43 : $reviewDatetime.hashCode());
   }
}
