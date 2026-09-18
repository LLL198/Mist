package com.una.embyhub.model.dto.response.pointsbot;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import java.io.Serializable;
import lombok.Generated;

public final class PointsBotPortalLedgerPageResponse implements Serializable {
   private final PointsBotPortalAccountResponse account;
   private final Page<PointsBotPortalLedgerResponse> page;

   @Generated
   PointsBotPortalLedgerPageResponse(final PointsBotPortalAccountResponse account, final Page<PointsBotPortalLedgerResponse> page) {
      this.account = account;
      this.page = page;
   }

   @Generated
   public static PointsBotPortalLedgerPageResponse.PointsBotPortalLedgerPageResponseBuilder builder() {
      return new PointsBotPortalLedgerPageResponse.PointsBotPortalLedgerPageResponseBuilder();
   }

   @Generated
   public PointsBotPortalAccountResponse getAccount() {
      return this.account;
   }

   @Generated
   public Page<PointsBotPortalLedgerResponse> getPage() {
      return this.page;
   }

   @Generated
   @Override
   public boolean equals(final Object o) {
      if (o == this) {
         return true;
      } else if (!(o instanceof PointsBotPortalLedgerPageResponse other)) {
         return false;
      } else {
         Object this$account = this.getAccount();
         Object other$account = other.getAccount();
         if (this$account == null ? other$account == null : this$account.equals(other$account)) {
            Object this$page = this.getPage();
            Object other$page = other.getPage();
            return this$page == null ? other$page == null : this$page.equals(other$page);
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
      Object $page = this.getPage();
      return result * 59 + ($page == null ? 43 : $page.hashCode());
   }

   @Generated
   @Override
   public String toString() {
      return "PointsBotPortalLedgerPageResponse(account=" + this.getAccount() + ", page=" + this.getPage() + ")";
   }

   @Generated
   public static class PointsBotPortalLedgerPageResponseBuilder {
      @Generated
      private PointsBotPortalAccountResponse account;
      @Generated
      private Page<PointsBotPortalLedgerResponse> page;

      @Generated
      PointsBotPortalLedgerPageResponseBuilder() {
      }

      @Generated
      public PointsBotPortalLedgerPageResponse.PointsBotPortalLedgerPageResponseBuilder account(final PointsBotPortalAccountResponse account) {
         this.account = account;
         return this;
      }

      @Generated
      public PointsBotPortalLedgerPageResponse.PointsBotPortalLedgerPageResponseBuilder page(final Page<PointsBotPortalLedgerResponse> page) {
         this.page = page;
         return this;
      }

      @Generated
      public PointsBotPortalLedgerPageResponse build() {
         return new PointsBotPortalLedgerPageResponse(this.account, this.page);
      }

      @Generated
      @Override
      public String toString() {
         return "PointsBotPortalLedgerPageResponse.PointsBotPortalLedgerPageResponseBuilder(account=" + this.account + ", page=" + this.page + ")";
      }
   }
}
