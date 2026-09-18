package com.una.embyhub.model.dto.request.douban;

import jakarta.validation.constraints.NotNull;
import java.io.Serializable;
import lombok.Generated;

public class DoubanHotRequest implements Serializable {
   private String type = "movie";
   private String tag;
   @NotNull(
      message = "页码不能为空"
   )
   private Integer page;
   private Integer pageSize;

   @Generated
   public String getType() {
      return this.type;
   }

   @Generated
   public String getTag() {
      return this.tag;
   }

   @Generated
   public Integer getPage() {
      return this.page;
   }

   @Generated
   public Integer getPageSize() {
      return this.pageSize;
   }

   @Generated
   public void setType(final String type) {
      this.type = type;
   }

   @Generated
   public void setTag(final String tag) {
      this.tag = tag;
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
   @Override
   public boolean equals(final Object o) {
      if (o == this) {
         return true;
      } else if (!(o instanceof DoubanHotRequest other)) {
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
               Object this$type = this.getType();
               Object other$type = other.getType();
               if (this$type == null ? other$type == null : this$type.equals(other$type)) {
                  Object this$tag = this.getTag();
                  Object other$tag = other.getTag();
                  return this$tag == null ? other$tag == null : this$tag.equals(other$tag);
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
      return other instanceof DoubanHotRequest;
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
      Object $type = this.getType();
      result = result * 59 + ($type == null ? 43 : $type.hashCode());
      Object $tag = this.getTag();
      return result * 59 + ($tag == null ? 43 : $tag.hashCode());
   }

   @Generated
   @Override
   public String toString() {
      return "DoubanHotRequest(type=" + this.getType() + ", tag=" + this.getTag() + ", page=" + this.getPage() + ", pageSize=" + this.getPageSize() + ")";
   }
}
