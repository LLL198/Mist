package com.una.embyhub.model.dto.response.pointsrecord;

import java.io.Serializable;
import java.util.Date;
import lombok.Generated;

public class PointsRecordResponse implements Serializable {
   private Long id;
   private Long userId;
   private String username;
   private String embyName;
   private String recordType;
   private String recordTypeCn;
   private Integer amount;
   private Integer balanceAfter;
   private String description;
   private Date createDatetime;

   @Generated
   public Long getId() {
      return this.id;
   }

   @Generated
   public Long getUserId() {
      return this.userId;
   }

   @Generated
   public String getUsername() {
      return this.username;
   }

   @Generated
   public String getEmbyName() {
      return this.embyName;
   }

   @Generated
   public String getRecordType() {
      return this.recordType;
   }

   @Generated
   public String getRecordTypeCn() {
      return this.recordTypeCn;
   }

   @Generated
   public Integer getAmount() {
      return this.amount;
   }

   @Generated
   public Integer getBalanceAfter() {
      return this.balanceAfter;
   }

   @Generated
   public String getDescription() {
      return this.description;
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
   public void setUserId(final Long userId) {
      this.userId = userId;
   }

   @Generated
   public void setUsername(final String username) {
      this.username = username;
   }

   @Generated
   public void setEmbyName(final String embyName) {
      this.embyName = embyName;
   }

   @Generated
   public void setRecordType(final String recordType) {
      this.recordType = recordType;
   }

   @Generated
   public void setRecordTypeCn(final String recordTypeCn) {
      this.recordTypeCn = recordTypeCn;
   }

   @Generated
   public void setAmount(final Integer amount) {
      this.amount = amount;
   }

   @Generated
   public void setBalanceAfter(final Integer balanceAfter) {
      this.balanceAfter = balanceAfter;
   }

   @Generated
   public void setDescription(final String description) {
      this.description = description;
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
      } else if (!(o instanceof PointsRecordResponse other)) {
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
               Object this$amount = this.getAmount();
               Object other$amount = other.getAmount();
               if (this$amount == null ? other$amount == null : this$amount.equals(other$amount)) {
                  Object this$balanceAfter = this.getBalanceAfter();
                  Object other$balanceAfter = other.getBalanceAfter();
                  if (this$balanceAfter == null ? other$balanceAfter == null : this$balanceAfter.equals(other$balanceAfter)) {
                     Object this$username = this.getUsername();
                     Object other$username = other.getUsername();
                     if (this$username == null ? other$username == null : this$username.equals(other$username)) {
                        Object this$embyName = this.getEmbyName();
                        Object other$embyName = other.getEmbyName();
                        if (this$embyName == null ? other$embyName == null : this$embyName.equals(other$embyName)) {
                           Object this$recordType = this.getRecordType();
                           Object other$recordType = other.getRecordType();
                           if (this$recordType == null ? other$recordType == null : this$recordType.equals(other$recordType)) {
                              Object this$recordTypeCn = this.getRecordTypeCn();
                              Object other$recordTypeCn = other.getRecordTypeCn();
                              if (this$recordTypeCn == null ? other$recordTypeCn == null : this$recordTypeCn.equals(other$recordTypeCn)) {
                                 Object this$description = this.getDescription();
                                 Object other$description = other.getDescription();
                                 if (this$description == null ? other$description == null : this$description.equals(other$description)) {
                                    Object this$createDatetime = this.getCreateDatetime();
                                    Object other$createDatetime = other.getCreateDatetime();
                                    return this$createDatetime == null ? other$createDatetime == null : this$createDatetime.equals(other$createDatetime);
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
      return other instanceof PointsRecordResponse;
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
      Object $amount = this.getAmount();
      result = result * 59 + ($amount == null ? 43 : $amount.hashCode());
      Object $balanceAfter = this.getBalanceAfter();
      result = result * 59 + ($balanceAfter == null ? 43 : $balanceAfter.hashCode());
      Object $username = this.getUsername();
      result = result * 59 + ($username == null ? 43 : $username.hashCode());
      Object $embyName = this.getEmbyName();
      result = result * 59 + ($embyName == null ? 43 : $embyName.hashCode());
      Object $recordType = this.getRecordType();
      result = result * 59 + ($recordType == null ? 43 : $recordType.hashCode());
      Object $recordTypeCn = this.getRecordTypeCn();
      result = result * 59 + ($recordTypeCn == null ? 43 : $recordTypeCn.hashCode());
      Object $description = this.getDescription();
      result = result * 59 + ($description == null ? 43 : $description.hashCode());
      Object $createDatetime = this.getCreateDatetime();
      return result * 59 + ($createDatetime == null ? 43 : $createDatetime.hashCode());
   }

   @Generated
   @Override
   public String toString() {
      return "PointsRecordResponse(id="
         + this.getId()
         + ", userId="
         + this.getUserId()
         + ", username="
         + this.getUsername()
         + ", embyName="
         + this.getEmbyName()
         + ", recordType="
         + this.getRecordType()
         + ", recordTypeCn="
         + this.getRecordTypeCn()
         + ", amount="
         + this.getAmount()
         + ", balanceAfter="
         + this.getBalanceAfter()
         + ", description="
         + this.getDescription()
         + ", createDatetime="
         + this.getCreateDatetime()
         + ")";
   }
}
