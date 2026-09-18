package com.una.embyhub.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.diboot.core.binding.Binder;
import com.diboot.core.binding.QueryBuilder;
import com.una.embyhub.config.common.MpConvert;
import com.una.embyhub.config.mybatis.MybatisPlusPage;
import com.una.embyhub.mapper.EmbyUserRegisterRecordMapper;
import com.una.embyhub.model.dto.request.embyuserrecord.EmbyUserRegisterRecordRequest;
import com.una.embyhub.model.dto.response.embyuserrecord.EmbyUserRegisterRecordResponse;
import com.una.embyhub.model.entity.EmbyUserRegisterRecord;
import com.una.embyhub.service.EmbyUserRegisterRecordService;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(
   rollbackFor = {Exception.class}
)
public class EmbyUserRegisterRecordServiceImpl
   extends ServiceImpl<EmbyUserRegisterRecordMapper, EmbyUserRegisterRecord>
   implements EmbyUserRegisterRecordService {
   @Override
   public Page<EmbyUserRegisterRecordResponse> select(MybatisPlusPage<EmbyUserRegisterRecordRequest> page) {
      QueryWrapper queryWrapper = QueryBuilder.toQueryWrapper(page.getObject());
      queryWrapper.orderByDesc("id");
      Page<EmbyUserRegisterRecordResponse> recordPage = MpConvert.page(
         queryWrapper, this.getBaseMapper(), EmbyUserRegisterRecordResponse.class, page.getCurrent(), page.getSize(), page.getOrders()
      );
      List<EmbyUserRegisterRecordResponse> responses = Binder.convertAndBindRelations(recordPage.getRecords(), EmbyUserRegisterRecordResponse.class);
      recordPage.setRecords(responses);
      return recordPage;
   }
}
