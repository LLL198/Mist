package com.una.embyhub.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Generated;

@TableName("card_security_management")
public class CardSecurityManagement extends BaseEntity implements Serializable {
   public static final String COL_CARD_TYPE = "card_type";
   private static final long serialVersionUID = 1L;
   @TableId(
      value = "id",
      type = IdType.AUTO
   )
   private Long id;
   @TableField("card_password")
   private String cardPassword;
   @TableField("card_validity")
   private Integer cardValidity;
   @TableField("card_status")
   private Integer cardStatus;
   @TableField("user_id")
   private Long userId;
   @TableField("emby_user_name")
   private String embyUserName;
   @TableField("emby_info_id")
   private Long embyInfoId;
   @TableField("copyfromuserid")
   private String copyfromuserid;
   @TableField("remarks")
   private String remarks;
   @TableField("host_line_type")
   private Integer hostLineType;
   @TableField("distributor_id")
   private Long distributorId;
   @TableField("payment_order_id")
   private Long paymentOrderId;
   public static final String COL_ID = "id";
   public static final String COL_CARD_PASSWORD = "card_password";
   public static final String COL_CARD_VALIDITY = "card_validity";
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
   public static final String COL_EMBY_INFO_ID = "emby_info_id";
   public static final String COL_COPYFROMUSERID = "copyfromuserid";
   public static final String COL_REMARKS = "remarks";
   public static final String COL_HOST_LINE_TYPE = "host_line_type";
   public static final String COL_DISTRIBUTOR_ID = "distributor_id";
   public static final String COL_PAYMENT_ORDER_ID = "payment_order_id";

   @Generated
   public Long getId() {
      return this.id;
   }

   @Generated
   public String getCardPassword() {
      return this.cardPassword;
   }

   @Generated
   public Integer getCardValidity() {
      return this.cardValidity;
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
   public Long getEmbyInfoId() {
      return this.embyInfoId;
   }

   @Generated
   public String getCopyfromuserid() {
      return this.copyfromuserid;
   }

   @Generated
   public String getRemarks() {
      return this.remarks;
   }

   @Generated
   public Integer getHostLineType() {
      return this.hostLineType;
   }

   @Generated
   public Long getDistributorId() {
      return this.distributorId;
   }

   @Generated
   public Long getPaymentOrderId() {
      return this.paymentOrderId;
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
   public void setCardValidity(final Integer cardValidity) {
      this.cardValidity = cardValidity;
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
   public void setEmbyInfoId(final Long embyInfoId) {
      this.embyInfoId = embyInfoId;
   }

   @Generated
   public void setCopyfromuserid(final String copyfromuserid) {
      this.copyfromuserid = copyfromuserid;
   }

   @Generated
   public void setRemarks(final String remarks) {
      this.remarks = remarks;
   }

   @Generated
   public void setHostLineType(final Integer hostLineType) {
      this.hostLineType = hostLineType;
   }

   @Generated
   public void setDistributorId(final Long distributorId) {
      this.distributorId = distributorId;
   }

   @Generated
   public void setPaymentOrderId(final Long paymentOrderId) {
      this.paymentOrderId = paymentOrderId;
   }

   @Generated
   @Override
   public String toString() {
      return "CardSecurityManagement(id="
         + this.getId()
         + ", cardPassword="
         + this.getCardPassword()
         + ", cardValidity="
         + this.getCardValidity()
         + ", cardStatus="
         + this.getCardStatus()
         + ", userId="
         + this.getUserId()
         + ", embyUserName="
         + this.getEmbyUserName()
         + ", embyInfoId="
         + this.getEmbyInfoId()
         + ", copyfromuserid="
         + this.getCopyfromuserid()
         + ", remarks="
         + this.getRemarks()
         + ", hostLineType="
         + this.getHostLineType()
         + ", distributorId="
         + this.getDistributorId()
         + ", paymentOrderId="
         + this.getPaymentOrderId()
         + ")";
   }

   @Generated
   @Override
   public boolean equals(final Object o) {
      if (o == this) {
         return true;
      } else if (!(o instanceof CardSecurityManagement other)) {
         return false;
      } else if (!other.canEqual(this)) {
         return false;
      } else if (!super.equals(o)) {
         return false;
      } else {
         Object this$id = this.getId();
         Object other$id = other.getId();
         if (this$id == null ? other$id == null : this$id.equals(other$id)) {
            Object this$cardValidity = this.getCardValidity();
            Object other$cardValidity = other.getCardValidity();
            if (this$cardValidity == null ? other$cardValidity == null : this$cardValidity.equals(other$cardValidity)) {
               Object this$cardStatus = this.getCardStatus();
               Object other$cardStatus = other.getCardStatus();
               if (this$cardStatus == null ? other$cardStatus == null : this$cardStatus.equals(other$cardStatus)) {
                  Object this$userId = this.getUserId();
                  Object other$userId = other.getUserId();
                  if (this$userId == null ? other$userId == null : this$userId.equals(other$userId)) {
                     Object this$embyInfoId = this.getEmbyInfoId();
                     Object other$embyInfoId = other.getEmbyInfoId();
                     if (this$embyInfoId == null ? other$embyInfoId == null : this$embyInfoId.equals(other$embyInfoId)) {
                        Object this$hostLineType = this.getHostLineType();
                        Object other$hostLineType = other.getHostLineType();
                        if (this$hostLineType == null ? other$hostLineType == null : this$hostLineType.equals(other$hostLineType)) {
                           Object this$distributorId = this.getDistributorId();
                           Object other$distributorId = other.getDistributorId();
                           if (this$distributorId == null ? other$distributorId == null : this$distributorId.equals(other$distributorId)) {
                              Object this$paymentOrderId = this.getPaymentOrderId();
                              Object other$paymentOrderId = other.getPaymentOrderId();
                              if (this$paymentOrderId == null ? other$paymentOrderId == null : this$paymentOrderId.equals(other$paymentOrderId)) {
                                 Object this$cardPassword = this.getCardPassword();
                                 Object other$cardPassword = other.getCardPassword();
                                 if (this$cardPassword == null ? other$cardPassword == null : this$cardPassword.equals(other$cardPassword)) {
                                    Object this$embyUserName = this.getEmbyUserName();
                                    Object other$embyUserName = other.getEmbyUserName();
                                    if (this$embyUserName == null ? other$embyUserName == null : this$embyUserName.equals(other$embyUserName)) {
                                       Object this$copyfromuserid = this.getCopyfromuserid();
                                       Object other$copyfromuserid = other.getCopyfromuserid();
                                       if (this$copyfromuserid == null ? other$copyfromuserid == null : this$copyfromuserid.equals(other$copyfromuserid)) {
                                          Object this$remarks = this.getRemarks();
                                          Object other$remarks = other.getRemarks();
                                          return this$remarks == null ? other$remarks == null : this$remarks.equals(other$remarks);
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
      return other instanceof CardSecurityManagement;
   }

   @Generated
   @Override
   public int hashCode() {
      int PRIME = 59;
      int result = super.hashCode();
      Object $id = this.getId();
      result = result * 59 + ($id == null ? 43 : $id.hashCode());
      Object $cardValidity = this.getCardValidity();
      result = result * 59 + ($cardValidity == null ? 43 : $cardValidity.hashCode());
      Object $cardStatus = this.getCardStatus();
      result = result * 59 + ($cardStatus == null ? 43 : $cardStatus.hashCode());
      Object $userId = this.getUserId();
      result = result * 59 + ($userId == null ? 43 : $userId.hashCode());
      Object $embyInfoId = this.getEmbyInfoId();
      result = result * 59 + ($embyInfoId == null ? 43 : $embyInfoId.hashCode());
      Object $hostLineType = this.getHostLineType();
      result = result * 59 + ($hostLineType == null ? 43 : $hostLineType.hashCode());
      Object $distributorId = this.getDistributorId();
      result = result * 59 + ($distributorId == null ? 43 : $distributorId.hashCode());
      Object $paymentOrderId = this.getPaymentOrderId();
      result = result * 59 + ($paymentOrderId == null ? 43 : $paymentOrderId.hashCode());
      Object $cardPassword = this.getCardPassword();
      result = result * 59 + ($cardPassword == null ? 43 : $cardPassword.hashCode());
      Object $embyUserName = this.getEmbyUserName();
      result = result * 59 + ($embyUserName == null ? 43 : $embyUserName.hashCode());
      Object $copyfromuserid = this.getCopyfromuserid();
      result = result * 59 + ($copyfromuserid == null ? 43 : $copyfromuserid.hashCode());
      Object $remarks = this.getRemarks();
      return result * 59 + ($remarks == null ? 43 : $remarks.hashCode());
   }
}
