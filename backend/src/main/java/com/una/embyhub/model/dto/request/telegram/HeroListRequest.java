package com.una.embyhub.model.dto.request.telegram;

import java.io.Serializable;
import lombok.Generated;

public class HeroListRequest implements Serializable {
   private String timeWindow;
   private Integer page;

   @Generated
   public String getTimeWindow() {
      return this.timeWindow;
   }

   @Generated
   public Integer getPage() {
      return this.page;
   }

   @Generated
   public void setTimeWindow(final String timeWindow) {
      this.timeWindow = timeWindow;
   }

   @Generated
   public void setPage(final Integer page) {
      this.page = page;
   }

   @Generated
   @Override
   public boolean equals(final Object o) {
      if (o == this) {
         return true;
      } else if (!(o instanceof HeroListRequest other)) {
         return false;
      } else if (!other.canEqual(this)) {
         return false;
      } else {
         Object this$page = this.getPage();
         Object other$page = other.getPage();
         if (this$page == null ? other$page == null : this$page.equals(other$page)) {
            Object this$timeWindow = this.getTimeWindow();
            Object other$timeWindow = other.getTimeWindow();
            return this$timeWindow == null ? other$timeWindow == null : this$timeWindow.equals(other$timeWindow);
         } else {
            return false;
         }
      }
   }

   @Generated
   protected boolean canEqual(final Object other) {
      return other instanceof HeroListRequest;
   }

   @Generated
   @Override
   public int hashCode() {
      int PRIME = 59;
      int result = 1;
      Object $page = this.getPage();
      result = result * 59 + ($page == null ? 43 : $page.hashCode());
      Object $timeWindow = this.getTimeWindow();
      return result * 59 + ($timeWindow == null ? 43 : $timeWindow.hashCode());
   }

   @Generated
   @Override
   public String toString() {
      return "HeroListRequest(timeWindow=" + this.getTimeWindow() + ", page=" + this.getPage() + ")";
   }
}
