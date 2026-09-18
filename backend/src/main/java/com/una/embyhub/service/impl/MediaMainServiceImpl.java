package com.una.embyhub.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.diboot.core.binding.QueryBuilder;
import com.una.embyhub.config.common.MpConvert;
import com.una.embyhub.config.mybatis.MybatisPlusPage;
import com.una.embyhub.mapper.MediaMainMapper;
import com.una.embyhub.model.dto.request.mediamain.MediaMainRequest;
import com.una.embyhub.model.dto.response.mediamain.MediaMainResponse;
import com.una.embyhub.model.entity.MediaMain;
import com.una.embyhub.service.MediaMainService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(
   rollbackFor = {Exception.class}
)
public class MediaMainServiceImpl extends ServiceImpl<MediaMainMapper, MediaMain> implements MediaMainService {
   @Override
   public Page<MediaMainResponse> select(MybatisPlusPage<MediaMainRequest> page) {
      QueryWrapper queryWrapper = QueryBuilder.toQueryWrapper(page.getObject());
      queryWrapper.orderByDesc("play_count");
      queryWrapper.orderByDesc("create_datetime");
      return MpConvert.page(queryWrapper, this.getBaseMapper(), MediaMainResponse.class, page.getCurrent(), page.getSize(), page.getOrders());
   }
}
