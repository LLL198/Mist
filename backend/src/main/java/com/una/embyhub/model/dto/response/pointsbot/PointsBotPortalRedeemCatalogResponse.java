package com.una.embyhub.model.dto.response.pointsbot;

import java.io.Serializable;
import java.util.List;
import lombok.Generated;

public final class PointsBotPortalRedeemCatalogResponse implements Serializable {
   private final PointsBotPortalAccountResponse account;
   private final List<PointsBotPortalRedeemOptionResponse> options;

   @Generated
   PointsBotPortalRedeemCatalogResponse(final PointsBotPortalAccountResponse account, final List<PointsBotPortalRedeemOptionResponse> options) {
      this.account = account;
      this.options = options;
   }

   @Generated
   public static PointsBotPortalRedeemCatalogResponse.PointsBotPortalRedeemCatalogResponseBuilder builder() {
      return new PointsBotPortalRedeemCatalogResponse.PointsBotPortalRedeemCatalogResponseBuilder();
   }

   @Generated
   public PointsBotPortalAccountResponse getAccount() {
      return this.account;
   }

   @Generated
   public List<PointsBotPortalRedeemOptionResponse> getOptions() {
      return this.options;
   }

   @Generated
   @Override
   public boolean equals(final Object o) {
      if (o == this) {
         return true;
      } else if (!(o instanceof PointsBotPortalRedeemCatalogResponse other)) {
         return false;
      } else {
         Object this$account = this.getAccount();
         Object other$account = other.getAccount();
         if (this$account == null ? other$account == null : this$account.equals(other$account)) {
            Object this$options = this.getOptions();
            Object other$options = other.getOptions();
            return this$options == null ? other$options == null : this$options.equals(other$options);
         } else {
            return false;
         }
      }
   }

   @Generated
   @Override
   public int hashCode() {
      int PRIME = 59;
      int result = 1;
      Object $account = this.getAccount();
      result = result * 59 + ($account == null ? 43 : $account.hashCode());
      Object $options = this.getOptions();
      return result * 59 + ($options == null ? 43 : $options.hashCode());
   }

   @Generated
   @Override
   public String toString() {
      return "PointsBotPortalRedeemCatalogResponse(account=" + this.getAccount() + ", options=" + this.getOptions() + ")";
   }

   @Generated
   public static class PointsBotPortalRedeemCatalogResponseBuilder {
      @Generated
      private PointsBotPortalAccountResponse account;
      @Generated
      private List<PointsBotPortalRedeemOptionResponse> options;

      @Generated
      PointsBotPortalRedeemCatalogResponseBuilder() {
      }

      @Generated
      public PointsBotPortalRedeemCatalogResponse.PointsBotPortalRedeemCatalogResponseBuilder account(final PointsBotPortalAccountResponse account) {
         this.account = account;
         return this;
      }

      @Generated
      public PointsBotPortalRedeemCatalogResponse.PointsBotPortalRedeemCatalogResponseBuilder options(final List<PointsBotPortalRedeemOptionResponse> options) {
         this.options = options;
         return this;
      }

      @Generated
      public PointsBotPortalRedeemCatalogResponse build() {
         return new PointsBotPortalRedeemCatalogResponse(this.account, this.options);
      }

      @Generated
      @Override
      public String toString() {
         return "PointsBotPortalRedeemCatalogResponse.PointsBotPortalRedeemCatalogResponseBuilder(account=" + this.account + ", options=" + this.options + ")";
      }
   }
}
