package com.una.embyhub.model.dto.response.pointsbot;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import java.io.Serializable;
import lombok.Generated;

public final class PointsBotPortalRedeemRecordPageResponse implements Serializable {
   private final PointsBotPortalAccountResponse account;
   private final Page<PointsBotPortalRedeemRecordResponse> page;

   @Generated
   PointsBotPortalRedeemRecordPageResponse(final PointsBotPortalAccountResponse account, final Page<PointsBotPortalRedeemRecordResponse> page) {
      this.account = account;
      this.page = page;
   }

   @Generated
   public static PointsBotPortalRedeemRecordPageResponse.PointsBotPortalRedeemRecordPageResponseBuilder builder() {
      return new PointsBotPortalRedeemRecordPageResponse.PointsBotPortalRedeemRecordPageResponseBuilder();
   }

   @Generated
   public PointsBotPortalAccountResponse getAccount() {
      return this.account;
   }

   @Generated
   public Page<PointsBotPortalRedeemRecordResponse> getPage() {
      return this.page;
   }

   @Generated
   @Override
   public boolean equals(final Object o) {
      if (o == this) {
         return true;
      } else if (!(o instanceof PointsBotPortalRedeemRecordPageResponse other)) {
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
      return "PointsBotPortalRedeemRecordPageResponse(account=" + this.getAccount() + ", page=" + this.getPage() + ")";
   }

   @Generated
   public static class PointsBotPortalRedeemRecordPageResponseBuilder {
      @Generated
      private PointsBotPortalAccountResponse account;
      @Generated
      private Page<PointsBotPortalRedeemRecordResponse> page;

      @Generated
      PointsBotPortalRedeemRecordPageResponseBuilder() {
      }

      @Generated
      public PointsBotPortalRedeemRecordPageResponse.PointsBotPortalRedeemRecordPageResponseBuilder account(final PointsBotPortalAccountResponse account) {
         this.account = account;
         return this;
      }

      @Generated
      public PointsBotPortalRedeemRecordPageResponse.PointsBotPortalRedeemRecordPageResponseBuilder page(final Page<PointsBotPortalRedeemRecordResponse> page) {
         this.page = page;
         return this;
      }

      @Generated
      public PointsBotPortalRedeemRecordPageResponse build() {
         return new PointsBotPortalRedeemRecordPageResponse(this.account, this.page);
      }

      @Generated
      @Override
      public String toString() {
         return "PointsBotPortalRedeemRecordPageResponse.PointsBotPortalRedeemRecordPageResponseBuilder(account=" + this.account + ", page=" + this.page + ")";
      }
   }
}
