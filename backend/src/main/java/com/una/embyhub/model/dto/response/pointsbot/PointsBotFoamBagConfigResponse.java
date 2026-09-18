package com.una.embyhub.model.dto.response.pointsbot;

import java.io.Serializable;
import java.util.List;
import lombok.Generated;

public class PointsBotFoamBagConfigResponse implements Serializable {
   private List<Integer> amountTiers;
   private Integer dailyLimit;
   private Integer repaymentMultiplier;
   private Integer repaymentHours;
   private Integer penaltyDays;
   private Boolean allowUnboundUsers;

   @Generated
   PointsBotFoamBagConfigResponse(
      final List<Integer> amountTiers,
      final Integer dailyLimit,
      final Integer repaymentMultiplier,
      final Integer repaymentHours,
      final Integer penaltyDays,
      final Boolean allowUnboundUsers
   ) {
      this.amountTiers = amountTiers;
      this.dailyLimit = dailyLimit;
      this.repaymentMultiplier = repaymentMultiplier;
      this.repaymentHours = repaymentHours;
      this.penaltyDays = penaltyDays;
      this.allowUnboundUsers = allowUnboundUsers;
   }

   @Generated
   public static PointsBotFoamBagConfigResponse.PointsBotFoamBagConfigResponseBuilder builder() {
      return new PointsBotFoamBagConfigResponse.PointsBotFoamBagConfigResponseBuilder();
   }

   @Generated
   public List<Integer> getAmountTiers() {
      return this.amountTiers;
   }

   @Generated
   public Integer getDailyLimit() {
      return this.dailyLimit;
   }

   @Generated
   public Integer getRepaymentMultiplier() {
      return this.repaymentMultiplier;
   }

   @Generated
   public Integer getRepaymentHours() {
      return this.repaymentHours;
   }

   @Generated
   public Integer getPenaltyDays() {
      return this.penaltyDays;
   }

   @Generated
   public Boolean getAllowUnboundUsers() {
      return this.allowUnboundUsers;
   }

   @Generated
   public void setAmountTiers(final List<Integer> amountTiers) {
      this.amountTiers = amountTiers;
   }

   @Generated
   public void setDailyLimit(final Integer dailyLimit) {
      this.dailyLimit = dailyLimit;
   }

   @Generated
   public void setRepaymentMultiplier(final Integer repaymentMultiplier) {
      this.repaymentMultiplier = repaymentMultiplier;
   }

   @Generated
   public void setRepaymentHours(final Integer repaymentHours) {
      this.repaymentHours = repaymentHours;
   }

   @Generated
   public void setPenaltyDays(final Integer penaltyDays) {
      this.penaltyDays = penaltyDays;
   }

   @Generated
   public void setAllowUnboundUsers(final Boolean allowUnboundUsers) {
      this.allowUnboundUsers = allowUnboundUsers;
   }

   @Generated
   @Override
   public boolean equals(final Object o) {
      if (o == this) {
         return true;
      } else if (!(o instanceof PointsBotFoamBagConfigResponse other)) {
         return false;
      } else if (!other.canEqual(this)) {
         return false;
      } else {
         Object this$dailyLimit = this.getDailyLimit();
         Object other$dailyLimit = other.getDailyLimit();
         if (this$dailyLimit == null ? other$dailyLimit == null : this$dailyLimit.equals(other$dailyLimit)) {
            Object this$repaymentMultiplier = this.getRepaymentMultiplier();
            Object other$repaymentMultiplier = other.getRepaymentMultiplier();
            if (this$repaymentMultiplier == null ? other$repaymentMultiplier == null : this$repaymentMultiplier.equals(other$repaymentMultiplier)) {
               Object this$repaymentHours = this.getRepaymentHours();
               Object other$repaymentHours = other.getRepaymentHours();
               if (this$repaymentHours == null ? other$repaymentHours == null : this$repaymentHours.equals(other$repaymentHours)) {
                  Object this$penaltyDays = this.getPenaltyDays();
                  Object other$penaltyDays = other.getPenaltyDays();
                  if (this$penaltyDays == null ? other$penaltyDays == null : this$penaltyDays.equals(other$penaltyDays)) {
                     Object this$allowUnboundUsers = this.getAllowUnboundUsers();
                     Object other$allowUnboundUsers = other.getAllowUnboundUsers();
                     if (this$allowUnboundUsers == null ? other$allowUnboundUsers == null : this$allowUnboundUsers.equals(other$allowUnboundUsers)) {
                        Object this$amountTiers = this.getAmountTiers();
                        Object other$amountTiers = other.getAmountTiers();
                        return this$amountTiers == null ? other$amountTiers == null : this$amountTiers.equals(other$amountTiers);
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
      return other instanceof PointsBotFoamBagConfigResponse;
   }

   @Generated
   @Override
   public int hashCode() {
      int PRIME = 59;
      int result = 1;
      Object $dailyLimit = this.getDailyLimit();
      result = result * 59 + ($dailyLimit == null ? 43 : $dailyLimit.hashCode());
      Object $repaymentMultiplier = this.getRepaymentMultiplier();
      result = result * 59 + ($repaymentMultiplier == null ? 43 : $repaymentMultiplier.hashCode());
      Object $repaymentHours = this.getRepaymentHours();
      result = result * 59 + ($repaymentHours == null ? 43 : $repaymentHours.hashCode());
      Object $penaltyDays = this.getPenaltyDays();
      result = result * 59 + ($penaltyDays == null ? 43 : $penaltyDays.hashCode());
      Object $allowUnboundUsers = this.getAllowUnboundUsers();
      result = result * 59 + ($allowUnboundUsers == null ? 43 : $allowUnboundUsers.hashCode());
      Object $amountTiers = this.getAmountTiers();
      return result * 59 + ($amountTiers == null ? 43 : $amountTiers.hashCode());
   }

   @Generated
   @Override
   public String toString() {
      return "PointsBotFoamBagConfigResponse(amountTiers="
         + this.getAmountTiers()
         + ", dailyLimit="
         + this.getDailyLimit()
         + ", repaymentMultiplier="
         + this.getRepaymentMultiplier()
         + ", repaymentHours="
         + this.getRepaymentHours()
         + ", penaltyDays="
         + this.getPenaltyDays()
         + ", allowUnboundUsers="
         + this.getAllowUnboundUsers()
         + ")";
   }

   @Generated
   public static class PointsBotFoamBagConfigResponseBuilder {
      @Generated
      private List<Integer> amountTiers;
      @Generated
      private Integer dailyLimit;
      @Generated
      private Integer repaymentMultiplier;
      @Generated
      private Integer repaymentHours;
      @Generated
      private Integer penaltyDays;
      @Generated
      private Boolean allowUnboundUsers;

      @Generated
      PointsBotFoamBagConfigResponseBuilder() {
      }

      @Generated
      public PointsBotFoamBagConfigResponse.PointsBotFoamBagConfigResponseBuilder amountTiers(final List<Integer> amountTiers) {
         this.amountTiers = amountTiers;
         return this;
      }

      @Generated
      public PointsBotFoamBagConfigResponse.PointsBotFoamBagConfigResponseBuilder dailyLimit(final Integer dailyLimit) {
         this.dailyLimit = dailyLimit;
         return this;
      }

      @Generated
      public PointsBotFoamBagConfigResponse.PointsBotFoamBagConfigResponseBuilder repaymentMultiplier(final Integer repaymentMultiplier) {
         this.repaymentMultiplier = repaymentMultiplier;
         return this;
      }

      @Generated
      public PointsBotFoamBagConfigResponse.PointsBotFoamBagConfigResponseBuilder repaymentHours(final Integer repaymentHours) {
         this.repaymentHours = repaymentHours;
         return this;
      }

      @Generated
      public PointsBotFoamBagConfigResponse.PointsBotFoamBagConfigResponseBuilder penaltyDays(final Integer penaltyDays) {
         this.penaltyDays = penaltyDays;
         return this;
      }

      @Generated
      public PointsBotFoamBagConfigResponse.PointsBotFoamBagConfigResponseBuilder allowUnboundUsers(final Boolean allowUnboundUsers) {
         this.allowUnboundUsers = allowUnboundUsers;
         return this;
      }

      @Generated
      public PointsBotFoamBagConfigResponse build() {
         return new PointsBotFoamBagConfigResponse(
            this.amountTiers, this.dailyLimit, this.repaymentMultiplier, this.repaymentHours, this.penaltyDays, this.allowUnboundUsers
         );
      }

      @Generated
      @Override
      public String toString() {
         return "PointsBotFoamBagConfigResponse.PointsBotFoamBagConfigResponseBuilder(amountTiers="
            + this.amountTiers
            + ", dailyLimit="
            + this.dailyLimit
            + ", repaymentMultiplier="
            + this.repaymentMultiplier
            + ", repaymentHours="
            + this.repaymentHours
            + ", penaltyDays="
            + this.penaltyDays
            + ", allowUnboundUsers="
            + this.allowUnboundUsers
            + ")";
      }
   }
}
