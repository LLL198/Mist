package com.una.embyhub.model.dto.request.cardsecuritymanagement;

import com.diboot.core.binding.query.BindQuery;
import com.diboot.core.binding.query.Comparison;
import java.io.Serializable;
import lombok.Generated;

public class CardSecurityManagementRequest implements Serializable {
   @BindQuery(
      comparison = Comparison.EQ
   )
   private String cardPassword;
   @BindQuery(
      comparison = Comparison.EQ
   )
   private Integer cardValidity;
   @BindQuery(
      comparison = Comparison.EQ
   )
   private Integer cardStatus;
   @BindQuery(
      comparison = Comparison.LIKE
   )
   private String embyUserName;
   @BindQuery(
      comparison = Comparison.EQ
   )
   private Long embyInfoId;
   @BindQuery(
      comparison = Comparison.LIKE
   )
   private String remarks;
   @BindQuery(
      comparison = Comparison.EQ
   )
   private Integer hostLineType;
   @BindQuery(
      ignore = true
   )
   private Boolean queryIsDistributor;
   @BindQuery(
      ignore = true
   )
   private String distributorName;

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
   public String getEmbyUserName() {
      return this.embyUserName;
   }

   @Generated
   public Long getEmbyInfoId() {
      return this.embyInfoId;
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
   public Boolean getQueryIsDistributor() {
      return this.queryIsDistributor;
   }

   @Generated
   public String getDistributorName() {
      return this.distributorName;
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
   public void setEmbyUserName(final String embyUserName) {
      this.embyUserName = embyUserName;
   }

   @Generated
   public void setEmbyInfoId(final Long embyInfoId) {
      this.embyInfoId = embyInfoId;
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
   public void setQueryIsDistributor(final Boolean queryIsDistributor) {
      this.queryIsDistributor = queryIsDistributor;
   }

   @Generated
   public void setDistributorName(final String distributorName) {
      this.distributorName = distributorName;
   }

   @Generated
   @Override
   public boolean equals(final Object o) {
      if (o == this) {
         return true;
      } else if (!(o instanceof CardSecurityManagementRequest other)) {
         return false;
      } else if (!other.canEqual(this)) {
         return false;
      } else {
         Object this$cardValidity = this.getCardValidity();
         Object other$cardValidity = other.getCardValidity();
         if (this$cardValidity == null ? other$cardValidity == null : this$cardValidity.equals(other$cardValidity)) {
            Object this$cardStatus = this.getCardStatus();
            Object other$cardStatus = other.getCardStatus();
            if (this$cardStatus == null ? other$cardStatus == null : this$cardStatus.equals(other$cardStatus)) {
               Object this$embyInfoId = this.getEmbyInfoId();
               Object other$embyInfoId = other.getEmbyInfoId();
               if (this$embyInfoId == null ? other$embyInfoId == null : this$embyInfoId.equals(other$embyInfoId)) {
                  Object this$hostLineType = this.getHostLineType();
                  Object other$hostLineType = other.getHostLineType();
                  if (this$hostLineType == null ? other$hostLineType == null : this$hostLineType.equals(other$hostLineType)) {
                     Object this$queryIsDistributor = this.getQueryIsDistributor();
                     Object other$queryIsDistributor = other.getQueryIsDistributor();
                     if (this$queryIsDistributor == null ? other$queryIsDistributor == null : this$queryIsDistributor.equals(other$queryIsDistributor)) {
                        Object this$cardPassword = this.getCardPassword();
                        Object other$cardPassword = other.getCardPassword();
                        if (this$cardPassword == null ? other$cardPassword == null : this$cardPassword.equals(other$cardPassword)) {
                           Object this$embyUserName = this.getEmbyUserName();
                           Object other$embyUserName = other.getEmbyUserName();
                           if (this$embyUserName == null ? other$embyUserName == null : this$embyUserName.equals(other$embyUserName)) {
                              Object this$remarks = this.getRemarks();
                              Object other$remarks = other.getRemarks();
                              if (this$remarks == null ? other$remarks == null : this$remarks.equals(other$remarks)) {
                                 Object this$distributorName = this.getDistributorName();
                                 Object other$distributorName = other.getDistributorName();
                                 return this$distributorName == null ? other$distributorName == null : this$distributorName.equals(other$distributorName);
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
      return other instanceof CardSecurityManagementRequest;
   }

   @Generated
   @Override
   public int hashCode() {
      int PRIME = 59;
      int result = 1;
      Object $cardValidity = this.getCardValidity();
      result = result * 59 + ($cardValidity == null ? 43 : $cardValidity.hashCode());
      Object $cardStatus = this.getCardStatus();
      result = result * 59 + ($cardStatus == null ? 43 : $cardStatus.hashCode());
      Object $embyInfoId = this.getEmbyInfoId();
      result = result * 59 + ($embyInfoId == null ? 43 : $embyInfoId.hashCode());
      Object $hostLineType = this.getHostLineType();
      result = result * 59 + ($hostLineType == null ? 43 : $hostLineType.hashCode());
      Object $queryIsDistributor = this.getQueryIsDistributor();
      result = result * 59 + ($queryIsDistributor == null ? 43 : $queryIsDistributor.hashCode());
      Object $cardPassword = this.getCardPassword();
      result = result * 59 + ($cardPassword == null ? 43 : $cardPassword.hashCode());
      Object $embyUserName = this.getEmbyUserName();
      result = result * 59 + ($embyUserName == null ? 43 : $embyUserName.hashCode());
      Object $remarks = this.getRemarks();
      result = result * 59 + ($remarks == null ? 43 : $remarks.hashCode());
      Object $distributorName = this.getDistributorName();
      return result * 59 + ($distributorName == null ? 43 : $distributorName.hashCode());
   }

   @Generated
   @Override
   public String toString() {
      return "CardSecurityManagementRequest(cardPassword="
         + this.getCardPassword()
         + ", cardValidity="
         + this.getCardValidity()
         + ", cardStatus="
         + this.getCardStatus()
         + ", embyUserName="
         + this.getEmbyUserName()
         + ", embyInfoId="
         + this.getEmbyInfoId()
         + ", remarks="
         + this.getRemarks()
         + ", hostLineType="
         + this.getHostLineType()
         + ", queryIsDistributor="
         + this.getQueryIsDistributor()
         + ", distributorName="
         + this.getDistributorName()
         + ")";
   }
}
