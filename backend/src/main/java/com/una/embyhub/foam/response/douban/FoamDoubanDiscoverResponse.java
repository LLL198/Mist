package com.una.embyhub.foam.response.douban;

import java.util.List;
import lombok.Generated;

public class FoamDoubanDiscoverResponse {
   private Integer page;
   private Integer pageSize;
   private Long total;
   private List<FoamDoubanItem> items;

   @Generated
   public Integer getPage() {
      return this.page;
   }

   @Generated
   public Integer getPageSize() {
      return this.pageSize;
   }

   @Generated
   public Long getTotal() {
      return this.total;
   }

   @Generated
   public List<FoamDoubanItem> getItems() {
      return this.items;
   }

   @Generated
   public void setPage(final Integer page) {
      this.page = page;
   }

   @Generated
   public void setPageSize(final Integer pageSize) {
      this.pageSize = pageSize;
   }

   @Generated
   public void setTotal(final Long total) {
      this.total = total;
   }

   @Generated
   public void setItems(final List<FoamDoubanItem> items) {
      this.items = items;
   }

   @Generated
   @Override
   public boolean equals(final Object o) {
      if (o == this) {
         return true;
      } else if (!(o instanceof FoamDoubanDiscoverResponse other)) {
         return false;
      } else if (!other.canEqual(this)) {
         return false;
      } else {
         Object this$page = this.getPage();
         Object other$page = other.getPage();
         if (this$page == null ? other$page == null : this$page.equals(other$page)) {
            Object this$pageSize = this.getPageSize();
            Object other$pageSize = other.getPageSize();
            if (this$pageSize == null ? other$pageSize == null : this$pageSize.equals(other$pageSize)) {
               Object this$total = this.getTotal();
               Object other$total = other.getTotal();
               if (this$total == null ? other$total == null : this$total.equals(other$total)) {
                  Object this$items = this.getItems();
                  Object other$items = other.getItems();
                  return this$items == null ? other$items == null : this$items.equals(other$items);
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
      return other instanceof FoamDoubanDiscoverResponse;
   }

   @Generated
   @Override
   public int hashCode() {
      int PRIME = 59;
      int result = 1;
      Object $page = this.getPage();
      result = result * 59 + ($page == null ? 43 : $page.hashCode());
      Object $pageSize = this.getPageSize();
      result = result * 59 + ($pageSize == null ? 43 : $pageSize.hashCode());
      Object $total = this.getTotal();
      result = result * 59 + ($total == null ? 43 : $total.hashCode());
      Object $items = this.getItems();
      return result * 59 + ($items == null ? 43 : $items.hashCode());
   }

   @Generated
   @Override
   public String toString() {
      return "FoamDoubanDiscoverResponse(page="
         + this.getPage()
         + ", pageSize="
         + this.getPageSize()
         + ", total="
         + this.getTotal()
         + ", items="
         + this.getItems()
         + ")";
   }
}
