package com.una.embyhub.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.diboot.core.binding.QueryBuilder;
import com.una.embyhub.config.common.MpConvert;
import com.una.embyhub.config.mybatis.MybatisPlusPage;
import com.una.embyhub.mapper.EmbyNotifyDataMapper;
import com.una.embyhub.model.dto.request.embynotifydata.EmbyNotifyDataRequest;
import com.una.embyhub.model.dto.response.embynotifydata.EmbyNotifyDataResponse;
import com.una.embyhub.model.entity.EmbyNotifyData;
import com.una.embyhub.service.EmbyNotifyDataService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(
   rollbackFor = {Exception.class}
)
public class EmbyNotifyDataServiceImpl extends ServiceImpl<EmbyNotifyDataMapper, EmbyNotifyData> implements EmbyNotifyDataService {
   @Override
   public Page<EmbyNotifyDataResponse> select(MybatisPlusPage<EmbyNotifyDataRequest> page) {
      QueryWrapper queryWrapper = QueryBuilder.toQueryWrapper(page.getObject());
      queryWrapper.orderByDesc("id");
      return MpConvert.page(queryWrapper, this.getBaseMapper(), EmbyNotifyDataResponse.class, page.getCurrent(), page.getSize(), page.getOrders());
   }
}
