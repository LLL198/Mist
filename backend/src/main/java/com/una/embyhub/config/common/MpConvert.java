package com.una.embyhub.config.common;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.plugins.pagination.PageDTO;
import com.diboot.core.binding.RelationsBinder;
import java.util.List;

public class MpConvert {
   public static <T, R> Page<R> page(Wrapper<T> wrapper, BaseMapper<T> mapper, Class<R> result, long current, long size, List<OrderItem> orders) {
      Page<T> objectPage = new Page<>(current, size);
      objectPage.setOrders(orders);
      Page<T> page = mapper.selectPage(objectPage, wrapper);
      List<R> rs = RelationsBinder.convertAndBind(page.getRecords(), result);
      Page<R> myIPage = new Page();
      myIPage.setTotal(page.getTotal());
      myIPage.setSize(page.getSize());
      myIPage.setCurrent(page.getCurrent());
      myIPage.setPages(page.getPages());
      myIPage.setRecords(rs);
      myIPage.setOrders(orders);
      return myIPage;
   }

   public static <T, R> Page<R> page(Wrapper<T> wrapper, BaseMapper<T> mapper, Class<R> result, PageDTO<T> pageDTO) {
      Page<T> page = mapper.selectPage(pageDTO, wrapper);
      List<R> rs = RelationsBinder.convertAndBind(page.getRecords(), result);
      Page<R> myIPage = new Page();
      myIPage.setTotal(page.getTotal());
      myIPage.setSize(page.getSize());
      myIPage.setCurrent(page.getCurrent());
      myIPage.setPages(page.getPages());
      myIPage.setRecords(rs);
      myIPage.setOrders(pageDTO.orders());
      return myIPage;
   }
}
