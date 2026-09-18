package com.una.embyhub.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.una.embyhub.config.common.MpConvert;
import com.una.embyhub.config.mybatis.MybatisPlusPage;
import com.una.embyhub.mapper.EmbyClientFilterRecordMapper;
import com.una.embyhub.model.dto.request.embyclientfilter.EmbyClientFilterRecordRequest;
import com.una.embyhub.model.dto.response.embyclientfilter.EmbyClientFilterRecordResponse;
import com.una.embyhub.model.entity.EmbyClientFilterRecord;
import com.una.embyhub.service.EmbyClientFilterRecordService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Service
@Transactional(
   rollbackFor = {Exception.class}
)
public class EmbyClientFilterRecordServiceImpl
   extends ServiceImpl<EmbyClientFilterRecordMapper, EmbyClientFilterRecord>
   implements EmbyClientFilterRecordService {
   @Override
   public Page<EmbyClientFilterRecordResponse> select(MybatisPlusPage<EmbyClientFilterRecordRequest> page) {
      EmbyClientFilterRecordRequest request = page.getObject();
      LambdaQueryWrapper<EmbyClientFilterRecord> wrapper = new LambdaQueryWrapper<>();
      if (request != null) {
         String keyword = request.getKeyword();
         wrapper.and(
            StringUtils.hasText(keyword),
            q -> q.like(EmbyClientFilterRecord::getEmbyUserName, keyword)
                  .or()
                  .like(EmbyClientFilterRecord::getClientName, keyword)
                  .or()
                  .like(EmbyClientFilterRecord::getDeviceName, keyword)
                  .or()
                  .like(EmbyClientFilterRecord::getMatchedPattern, keyword)
                  .or()
                  .like(EmbyClientFilterRecord::getItemName, keyword)
                  .or()
                  .like(EmbyClientFilterRecord::getRemoteEndpoint, keyword)
                  .or()
                  .like(EmbyClientFilterRecord::getResolvedIp, keyword)
                  .or()
                  .like(EmbyClientFilterRecord::getCountry, keyword)
                  .or()
                  .like(EmbyClientFilterRecord::getProvince, keyword)
                  .or()
                  .like(EmbyClientFilterRecord::getCity, keyword)
         );
         wrapper.like(StringUtils.hasText(request.getEmbyUserName()), EmbyClientFilterRecord::getEmbyUserName, request.getEmbyUserName());
         wrapper.like(StringUtils.hasText(request.getClientName()), EmbyClientFilterRecord::getClientName, request.getClientName());
         wrapper.eq(StringUtils.hasText(request.getEvent()), EmbyClientFilterRecord::getEvent, request.getEvent());
         wrapper.eq(StringUtils.hasText(request.getFilterType()), EmbyClientFilterRecord::getFilterType, request.getFilterType());
         wrapper.eq(request.getBlockUserSuccess() != null, EmbyClientFilterRecord::getBlockUserSuccess, request.getBlockUserSuccess());
         wrapper.eq(request.getStopSuccess() != null, EmbyClientFilterRecord::getStopSuccess, request.getStopSuccess());
         wrapper.eq(request.getEmbyInfoId() != null, EmbyClientFilterRecord::getEmbyInfoId, request.getEmbyInfoId());
         wrapper.ge(request.getTriggerTimeStart() != null, EmbyClientFilterRecord::getTriggerTime, request.getTriggerTimeStart());
         wrapper.le(request.getTriggerTimeEnd() != null, EmbyClientFilterRecord::getTriggerTime, request.getTriggerTimeEnd());
      }

      wrapper.orderByDesc(EmbyClientFilterRecord::getTriggerTime).orderByDesc(EmbyClientFilterRecord::getId);
      return MpConvert.page(wrapper, this.getBaseMapper(), EmbyClientFilterRecordResponse.class, page.getCurrent(), page.getSize(), page.getOrders());
   }
}
