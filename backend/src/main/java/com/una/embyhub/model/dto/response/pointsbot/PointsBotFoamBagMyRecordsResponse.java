package com.una.embyhub.model.dto.response.pointsbot;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import java.io.Serializable;
import lombok.Generated;

public class PointsBotFoamBagMyRecordsResponse implements Serializable {
   private Boolean telegramBound;
   private Page<PointsBotFoamBagResponse> page;

   @Generated
   PointsBotFoamBagMyRecordsResponse(final Boolean telegramBound, final Page<PointsBotFoamBagResponse> page) {
      this.telegramBound = telegramBound;
      this.page = page;
   }

   @Generated
   public static PointsBotFoamBagMyRecordsResponse.PointsBotFoamBagMyRecordsResponseBuilder builder() {
      return new PointsBotFoamBagMyRecordsResponse.PointsBotFoamBagMyRecordsResponseBuilder();
   }

   @Generated
   public Boolean getTelegramBound() {
      return this.telegramBound;
   }

   @Generated
   public Page<PointsBotFoamBagResponse> getPage() {
      return this.page;
   }

   @Generated
   public void setTelegramBound(final Boolean telegramBound) {
      this.telegramBound = telegramBound;
   }

   @Generated
   public void setPage(final Page<PointsBotFoamBagResponse> page) {
      this.page = page;
   }

   @Generated
   @Override
   public boolean equals(final Object o) {
      if (o == this) {
         return true;
      } else if (!(o instanceof PointsBotFoamBagMyRecordsResponse other)) {
         return false;
      } else if (!other.canEqual(this)) {
         return false;
      } else {
         Object this$telegramBound = this.getTelegramBound();
         Object other$telegramBound = other.getTelegramBound();
         if (this$telegramBound == null ? other$telegramBound == null : this$telegramBound.equals(other$telegramBound)) {
            Object this$page = this.getPage();
            Object other$page = other.getPage();
            return this$page == null ? other$page == null : this$page.equals(other$page);
         } else {
            return false;
         }
      }
   }

   @Generated
   protected boolean canEqual(final Object other) {
      return other instanceof PointsBotFoamBagMyRecordsResponse;
   }

   @Generated
   @Override
   public int hashCode() {
      int PRIME = 59;
      int result = 1;
      Object $telegramBound = this.getTelegramBound();
      result = result * 59 + ($telegramBound == null ? 43 : $telegramBound.hashCode());
      Object $page = this.getPage();
      return result * 59 + ($page == null ? 43 : $page.hashCode());
   }

   @Generated
   @Override
   public String toString() {
      return "PointsBotFoamBagMyRecordsResponse(telegramBound=" + this.getTelegramBound() + ", page=" + this.getPage() + ")";
   }

   @Generated
   public static class PointsBotFoamBagMyRecordsResponseBuilder {
      @Generated
      private Boolean telegramBound;
      @Generated
      private Page<PointsBotFoamBagResponse> page;

      @Generated
      PointsBotFoamBagMyRecordsResponseBuilder() {
      }

      @Generated
      public PointsBotFoamBagMyRecordsResponse.PointsBotFoamBagMyRecordsResponseBuilder telegramBound(final Boolean telegramBound) {
         this.telegramBound = telegramBound;
         return this;
      }

      @Generated
      public PointsBotFoamBagMyRecordsResponse.PointsBotFoamBagMyRecordsResponseBuilder page(final Page<PointsBotFoamBagResponse> page) {
         this.page = page;
         return this;
      }

      @Generated
      public PointsBotFoamBagMyRecordsResponse build() {
         return new PointsBotFoamBagMyRecordsResponse(this.telegramBound, this.page);
      }

      @Generated
      @Override
      public String toString() {
         return "PointsBotFoamBagMyRecordsResponse.PointsBotFoamBagMyRecordsResponseBuilder(telegramBound=" + this.telegramBound + ", page=" + this.page + ")";
      }
   }
}
