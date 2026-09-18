package com.una.embyhub.model.dto.request.requestpackagescardsecuritymanagement;

import com.diboot.core.binding.query.BindQuery;
import com.diboot.core.binding.query.Comparison;
import com.diboot.core.binding.query.BindQuery.List;
import java.io.Serializable;
import lombok.Generated;

public class RequestPackagesCardSecurityManagementRequest implements Serializable {
   @BindQuery(
      comparison = Comparison.EQ
   )
   private String cardPassword;
   @BindQuery(
      comparison = Comparison.EQ
   )
   private Integer cardStatus;
   @BindQuery(
      ignore = true
   )
   private String embyUserName;
   @BindQuery(
      comparison = Comparison.EQ
   )
   private Integer cardValidity;
   @List({@BindQuery(
         comparison = Comparison.EQ,
         field = "cardPassword"
      ), @BindQuery(
         comparison = Comparison.STARTSWITH,
         field = "embyUserName"
      )})
   private String query;
   @BindQuery(
      comparison = Comparison.EQ
   )
   private Integer cardCount;

   @Generated
   public String getCardPassword() {
      return this.cardPassword;
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
   public Integer getCardValidity() {
      return this.cardValidity;
   }

   @Generated
   public String getQuery() {
      return this.query;
   }

   @Generated
   public Integer getCardCount() {
      return this.cardCount;
   }

   @Generated
   public void setCardPassword(final String cardPassword) {
      this.cardPassword = cardPassword;
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
   public void setCardValidity(final Integer cardValidity) {
      this.cardValidity = cardValidity;
   }

   @Generated
   public void setQuery(final String query) {
      this.query = query;
   }

   @Generated
   public void setCardCount(final Integer cardCount) {
      this.cardCount = cardCount;
   }

   @Generated
   @Override
   public boolean equals(final Object o) {
      if (o == this) {
         return true;
      } else if (!(o instanceof RequestPackagesCardSecurityManagementRequest other)) {
         return false;
      } else if (!other.canEqual(this)) {
         return false;
      } else {
         Object this$cardStatus = this.getCardStatus();
         Object other$cardStatus = other.getCardStatus();
         if (this$cardStatus == null ? other$cardStatus == null : this$cardStatus.equals(other$cardStatus)) {
            Object this$cardValidity = this.getCardValidity();
            Object other$cardValidity = other.getCardValidity();
            if (this$cardValidity == null ? other$cardValidity == null : this$cardValidity.equals(other$cardValidity)) {
               Object this$cardCount = this.getCardCount();
               Object other$cardCount = other.getCardCount();
               if (this$cardCount == null ? other$cardCount == null : this$cardCount.equals(other$cardCount)) {
                  Object this$cardPassword = this.getCardPassword();
                  Object other$cardPassword = other.getCardPassword();
                  if (this$cardPassword == null ? other$cardPassword == null : this$cardPassword.equals(other$cardPassword)) {
                     Object this$embyUserName = this.getEmbyUserName();
                     Object other$embyUserName = other.getEmbyUserName();
                     if (this$embyUserName == null ? other$embyUserName == null : this$embyUserName.equals(other$embyUserName)) {
                        Object this$query = this.getQuery();
                        Object other$query = other.getQuery();
                        return this$query == null ? other$query == null : this$query.equals(other$query);
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
      return other instanceof RequestPackagesCardSecurityManagementRequest;
   }

   @Generated
   @Override
   public int hashCode() {
      int PRIME = 59;
      int result = 1;
      Object $cardStatus = this.getCardStatus();
      result = result * 59 + ($cardStatus == null ? 43 : $cardStatus.hashCode());
      Object $cardValidity = this.getCardValidity();
      result = result * 59 + ($cardValidity == null ? 43 : $cardValidity.hashCode());
      Object $cardCount = this.getCardCount();
      result = result * 59 + ($cardCount == null ? 43 : $cardCount.hashCode());
      Object $cardPassword = this.getCardPassword();
      result = result * 59 + ($cardPassword == null ? 43 : $cardPassword.hashCode());
      Object $embyUserName = this.getEmbyUserName();
      result = result * 59 + ($embyUserName == null ? 43 : $embyUserName.hashCode());
      Object $query = this.getQuery();
      return result * 59 + ($query == null ? 43 : $query.hashCode());
   }

   @Generated
   @Override
   public String toString() {
      return "RequestPackagesCardSecurityManagementRequest(cardPassword="
         + this.getCardPassword()
         + ", cardStatus="
         + this.getCardStatus()
         + ", embyUserName="
         + this.getEmbyUserName()
         + ", cardValidity="
         + this.getCardValidity()
         + ", query="
         + this.getQuery()
         + ", cardCount="
         + this.getCardCount()
         + ")";
   }
}
