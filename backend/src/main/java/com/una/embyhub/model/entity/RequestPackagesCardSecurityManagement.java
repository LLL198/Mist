package com.una.embyhub.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Generated;

@TableName("request_packages_card_security_management")
public class RequestPackagesCardSecurityManagement extends BaseEntity implements Serializable {
   private static final long serialVersionUID = 1L;
   @TableId(
      value = "id",
      type = IdType.AUTO
   )
   private Long id;
   @TableField("card_password")
   private String cardPassword;
   @TableField("card_count")
   private Integer cardCount;
   @TableField("card_status")
   private Integer cardStatus;
   @TableField("user_id")
   private Long userId;
   @TableField("emby_user_name")
   private String embyUserName;
   public static final String COL_ID = "id";
   public static final String COL_CARD_PASSWORD = "card_password";
   public static final String COL_CARD_COUNT = "card_count";
   public static final String COL_CARD_STATUS = "card_status";
   public static final String COL_USER_ID = "user_id";
   public static final String COL_EMBY_USER_NAME = "emby_user_name";
   public static final String COL_CREATE_DATETIME = "create_datetime";
   public static final String COL_UPDATE_DATETIME = "update_datetime";
   public static final String COL_CREATE_USER_NAME = "create_user_name";
   public static final String COL_UPDATE_USER_NAME = "update_user_name";
   public static final String COL_UPDATE_USER_ID = "update_user_id";
   public static final String COL_CREATE_USER_ID = "create_user_id";
   public static final String COL_DEL_FLAG = "del_flag";

   @Generated
   public Long getId() {
      return this.id;
   }

   @Generated
   public String getCardPassword() {
      return this.cardPassword;
   }

   @Generated
   public Integer getCardCount() {
      return this.cardCount;
   }

   @Generated
   public Integer getCardStatus() {
      return this.cardStatus;
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
   public void setId(final Long id) {
      this.id = id;
   }

   @Generated
   public void setCardPassword(final String cardPassword) {
      this.cardPassword = cardPassword;
   }

   @Generated
   public void setCardCount(final Integer cardCount) {
      this.cardCount = cardCount;
   }

   @Generated
   public void setCardStatus(final Integer cardStatus) {
      this.cardStatus = cardStatus;
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
   @Override
   public String toString() {
      return "RequestPackagesCardSecurityManagement(id="
         + this.getId()
         + ", cardPassword="
         + this.getCardPassword()
         + ", cardCount="
         + this.getCardCount()
         + ", cardStatus="
         + this.getCardStatus()
         + ", userId="
         + this.getUserId()
         + ", embyUserName="
         + this.getEmbyUserName()
         + ")";
   }

   @Generated
   @Override
   public boolean equals(final Object o) {
      if (o == this) {
         return true;
      } else if (!(o instanceof RequestPackagesCardSecurityManagement other)) {
         return false;
      } else if (!other.canEqual(this)) {
         return false;
      } else if (!super.equals(o)) {
         return false;
      } else {
         Object this$id = this.getId();
         Object other$id = other.getId();
         if (this$id == null ? other$id == null : this$id.equals(other$id)) {
            Object this$cardCount = this.getCardCount();
            Object other$cardCount = other.getCardCount();
            if (this$cardCount == null ? other$cardCount == null : this$cardCount.equals(other$cardCount)) {
               Object this$cardStatus = this.getCardStatus();
               Object other$cardStatus = other.getCardStatus();
               if (this$cardStatus == null ? other$cardStatus == null : this$cardStatus.equals(other$cardStatus)) {
                  Object this$userId = this.getUserId();
                  Object other$userId = other.getUserId();
                  if (this$userId == null ? other$userId == null : this$userId.equals(other$userId)) {
                     Object this$cardPassword = this.getCardPassword();
                     Object other$cardPassword = other.getCardPassword();
                     if (this$cardPassword == null ? other$cardPassword == null : this$cardPassword.equals(other$cardPassword)) {
                        Object this$embyUserName = this.getEmbyUserName();
                        Object other$embyUserName = other.getEmbyUserName();
                        return this$embyUserName == null ? other$embyUserName == null : this$embyUserName.equals(other$embyUserName);
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
      return other instanceof RequestPackagesCardSecurityManagement;
   }

   @Generated
   @Override
   public int hashCode() {
      int PRIME = 59;
      int result = super.hashCode();
      Object $id = this.getId();
      result = result * 59 + ($id == null ? 43 : $id.hashCode());
      Object $cardCount = this.getCardCount();
      result = result * 59 + ($cardCount == null ? 43 : $cardCount.hashCode());
      Object $cardStatus = this.getCardStatus();
      result = result * 59 + ($cardStatus == null ? 43 : $cardStatus.hashCode());
      Object $userId = this.getUserId();
      result = result * 59 + ($userId == null ? 43 : $userId.hashCode());
      Object $cardPassword = this.getCardPassword();
      result = result * 59 + ($cardPassword == null ? 43 : $cardPassword.hashCode());
      Object $embyUserName = this.getEmbyUserName();
      return result * 59 + ($embyUserName == null ? 43 : $embyUserName.hashCode());
   }
}
