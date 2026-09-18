package com.una.embyhub.service.impl;

import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.una.embyhub.config.mybatis.MybatisPlusPage;
import com.una.embyhub.mapper.PlayRecordsMapper;
import com.una.embyhub.model.dto.request.playrecords.PlayRecordsRequest;
import com.una.embyhub.model.dto.request.playrecords.UserPlayStats;
import com.una.embyhub.model.dto.response.playrecords.PlayCountSummary;
import com.una.embyhub.model.dto.response.playrecords.PlayRecordsResponse;
import com.una.embyhub.model.entity.PlayRecords;
import com.una.embyhub.service.EmbyInfoService;
import com.una.embyhub.service.PlayRecordsService;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(
   rollbackFor = {Exception.class}
)
public class PlayRecordsServiceImpl extends ServiceImpl<PlayRecordsMapper, PlayRecords> implements PlayRecordsService {
   @Autowired
   private PlayRecordsMapper playRecordsMapper;
   @Autowired
   private EmbyInfoService embyInfoService;

   @Override
   public Page<PlayRecordsResponse> select(MybatisPlusPage<PlayRecordsRequest> page) {
      PlayRecordsRequest request = page.getObject();
      if (request != null && request.getPlayDateStart() != null) {
         request.setPlayDateEnd(DateUtil.beginOfDay(request.getPlayDateEnd()));
      }

      return this.playRecordsMapper
         .selectDailyStats(
            new Page<>(page.getCurrent(), page.getSize()),
            request == null ? null : request.getEmbyUserName(),
            request == null ? null : request.getPlayDateStart(),
            request == null ? null : request.getPlayDateEnd(),
            request.getEmbyInfoId()
         );
   }

   @Override
   public List<UserPlayStats> getPlayStats(String userName, Date startDate, Date endDate, Long embyInfoId) {
      return this.playRecordsMapper.getUserPlayStats(userName, startDate, endDate, embyInfoId);
   }

   @Override
   public List<PlayRecords> findRecentPlays(String userName, int limit) {
      return this.playRecordsMapper.findRecentPlays(userName, limit);
   }

   @Override
   public List<PlayCountSummary> summaryByRange(Date start, Date end) {
      return this.summaryByRange(start, end, null);
   }

   @Override
   public List<PlayCountSummary> summaryByRange(Date start, Date end, Long embyInfoId) {
      return this.playRecordsMapper.summaryByRange(start, end, embyInfoId);
   }
}
