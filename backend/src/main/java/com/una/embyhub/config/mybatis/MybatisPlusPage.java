package com.una.embyhub.config.mybatis;

import com.alibaba.fastjson2.JSONArray;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.PageDTO;
import jakarta.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import lombok.Generated;
import org.springframework.util.StringUtils;

public class MybatisPlusPage<T> implements Serializable {
   @NotNull(
      message = "分页请求格式有误，数据对象object不能为空"
   )
   private T object;
   private long size = 10L;
   private long current = 1L;
   private List<OrderItem> orders = new ArrayList<>();
   private String ordersList;

   public <R> PageDTO<R> getPageDto(Class<R> entityClass) {
      PageDTO<R> pageDTO = new PageDTO(this.current, this.size);
      if (StringUtils.hasText(this.ordersList)) {
         this.orders = JSONArray.parseArray(this.ordersList, OrderItem.class);
      }

      pageDTO.setOrders(this.orders);
      return pageDTO;
   }

   @Generated
   public T getObject() {
      return this.object;
   }

   @Generated
   public long getSize() {
      return this.size;
   }

   @Generated
   public long getCurrent() {
      return this.current;
   }

   @Generated
   public List<OrderItem> getOrders() {
      return this.orders;
   }

   @Generated
   public String getOrdersList() {
      return this.ordersList;
   }

   @Generated
   public void setObject(final T object) {
      this.object = object;
   }

   @Generated
   public void setSize(final long size) {
      this.size = size;
   }

   @Generated
   public void setCurrent(final long current) {
      this.current = current;
   }

   @Generated
   public void setOrders(final List<OrderItem> orders) {
      this.orders = orders;
   }

   @Generated
   public void setOrdersList(final String ordersList) {
      this.ordersList = ordersList;
   }

   @Generated
   @Override
   public boolean equals(final Object o) {
      if (o == this) {
         return true;
      } else if (!(o instanceof MybatisPlusPage<?> other)) {
         return false;
      } else if (!other.canEqual(this)) {
         return false;
      } else if (this.getSize() != other.getSize()) {
         return false;
      } else if (this.getCurrent() != other.getCurrent()) {
         return false;
      } else {
         Object this$object = this.getObject();
         Object other$object = other.getObject();
         if (this$object == null ? other$object == null : this$object.equals(other$object)) {
            Object this$orders = this.getOrders();
            Object other$orders = other.getOrders();
            if (this$orders == null ? other$orders == null : this$orders.equals(other$orders)) {
               Object this$ordersList = this.getOrdersList();
               Object other$ordersList = other.getOrdersList();
               return this$ordersList == null ? other$ordersList == null : this$ordersList.equals(other$ordersList);
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
      return other instanceof MybatisPlusPage;
   }

   @Generated
   @Override
   public int hashCode() {
      int PRIME = 59;
      int result = 1;
      long $size = this.getSize();
      result = result * 59 + (int)($size >>> 32 ^ $size);
      long $current = this.getCurrent();
      result = result * 59 + (int)($current >>> 32 ^ $current);
      Object $object = this.getObject();
      result = result * 59 + ($object == null ? 43 : $object.hashCode());
      Object $orders = this.getOrders();
      result = result * 59 + ($orders == null ? 43 : $orders.hashCode());
      Object $ordersList = this.getOrdersList();
      return result * 59 + ($ordersList == null ? 43 : $ordersList.hashCode());
   }

   @Generated
   @Override
   public String toString() {
      return "MybatisPlusPage(object="
         + this.getObject()
         + ", size="
         + this.getSize()
         + ", current="
         + this.getCurrent()
         + ", orders="
         + this.getOrders()
         + ", ordersList="
         + this.getOrdersList()
         + ")";
   }
}
