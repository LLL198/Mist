package com.una.embyhub.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.diboot.core.binding.Binder;
import com.diboot.core.binding.QueryBuilder;
import com.una.embyhub.config.common.MpConvert;
import com.una.embyhub.config.mybatis.MybatisPlusPage;
import com.una.embyhub.mapper.EmbyUserRenewRecordMapper;
import com.una.embyhub.model.dto.request.embyuserrecord.EmbyUserRenewRecordRequest;
import com.una.embyhub.model.dto.response.embyuserrecord.EmbyUserRenewRecordResponse;
import com.una.embyhub.model.entity.EmbyUserRenewRecord;
import com.una.embyhub.service.EmbyUserRenewRecordService;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(
   rollbackFor = {Exception.class}
)
public class EmbyUserRenewRecordServiceImpl extends ServiceImpl<EmbyUserRenewRecordMapper, EmbyUserRenewRecord> implements EmbyUserRenewRecordService {
   @Override
   public Page<EmbyUserRenewRecordResponse> select(MybatisPlusPage<EmbyUserRenewRecordRequest> page) {
      QueryWrapper queryWrapper = QueryBuilder.toQueryWrapper(page.getObject());
      queryWrapper.orderByDesc("id");
      Page<EmbyUserRenewRecordResponse> recordPage = MpConvert.page(
         queryWrapper, this.getBaseMapper(), EmbyUserRenewRecordResponse.class, page.getCurrent(), page.getSize(), page.getOrders()
      );
      List<EmbyUserRenewRecordResponse> responses = Binder.convertAndBindRelations(recordPage.getRecords(), EmbyUserRenewRecordResponse.class);
      recordPage.setRecords(responses);
      return recordPage;
   }
}
