package com.una.embyhub.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.diboot.core.binding.QueryBuilder;
import com.una.embyhub.config.common.MpConvert;
import com.una.embyhub.config.common.utils.EmbyInfoCacheManagerUtils;
import com.una.embyhub.config.mybatis.MybatisPlusPage;
import com.una.embyhub.mapper.SimultaneousPlaybackRecordMapper;
import com.una.embyhub.model.dto.request.simultaneous.SimultaneousPlaybackRecordRequest;
import com.una.embyhub.model.dto.response.simultaneous.SimultaneousPlaybackRecordDetailResponse;
import com.una.embyhub.model.dto.response.simultaneous.SimultaneousPlaybackRecordResponse;
import com.una.embyhub.model.entity.SimultaneousPlaybackRecord;
import com.una.embyhub.model.entity.SimultaneousPlaybackRecordDetail;
import com.una.embyhub.service.SimultaneousPlaybackRecordDetailService;
import com.una.embyhub.service.SimultaneousPlaybackRecordService;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

@Service
@Transactional(
   rollbackFor = {Exception.class}
)
public class SimultaneousPlaybackRecordServiceImpl
   extends ServiceImpl<SimultaneousPlaybackRecordMapper, SimultaneousPlaybackRecord>
   implements SimultaneousPlaybackRecordService {
   @Autowired
   private SimultaneousPlaybackRecordDetailService simultaneousPlaybackRecordDetailService;
   @Autowired
   private EmbyInfoCacheManagerUtils embyInfoCacheManager;

   @Override
   public Page<SimultaneousPlaybackRecordResponse> select(MybatisPlusPage<SimultaneousPlaybackRecordRequest> page) {
      QueryWrapper<SimultaneousPlaybackRecord> queryWrapper = QueryBuilder.toQueryWrapper(page.getObject());
      queryWrapper.orderByDesc("detection_time");
      SimultaneousPlaybackRecordRequest request = page.getObject();
      if (request != null && StringUtils.hasText(request.getContent())) {
         queryWrapper.exists(
            "select 1 from simultaneous_playback_record_detail d where d.record_id = simultaneous_playback_record.id and d.item_name like {0}",
            new Object[]{"%" + request.getContent() + "%"}
         );
      }

      Page<SimultaneousPlaybackRecordResponse> result = MpConvert.page(
         queryWrapper, this.getBaseMapper(), SimultaneousPlaybackRecordResponse.class, page.getCurrent(), page.getSize(), page.getOrders()
      );

      for (SimultaneousPlaybackRecordResponse record : result.getRecords()) {
         if (record.getEmbyInfoId() != null && !CollectionUtils.isEmpty(record.getDetails())) {
            String baseUrl = this.getServerBaseUrl(record.getEmbyInfoId());
            if (StringUtils.hasText(baseUrl)) {
               for (SimultaneousPlaybackRecordDetailResponse detail : record.getDetails()) {
                  if (StringUtils.hasText(detail.getPosterUrl()) && !detail.getPosterUrl().startsWith("http")) {
                     detail.setPosterUrl(baseUrl + detail.getPosterUrl());
                  }
               }
            }
         }
      }

      return result;
   }

   private String getServerBaseUrl(Long embyInfoId) {
      try {
         EmbyInfoCacheManagerUtils.EmbyServerConfig config = this.embyInfoCacheManager.getRequiredConfigById(embyInfoId);
         if (config != null && StringUtils.hasText(config.url())) {
            return config.url().replaceAll("emby/?$", "");
         }
      } catch (Exception var3) {
      }

      return null;
   }

   @Override
   public void saveRecordWithDetails(SimultaneousPlaybackRecord record, List<SimultaneousPlaybackRecordDetail> details) {
      if (record != null) {
         if (record.getDetectionTime() == null) {
            record.setDetectionTime(new Date());
         }

         this.save(record);
         if (!CollectionUtils.isEmpty(details)) {
            details.forEach(detail -> detail.setRecordId(record.getId()));
            this.simultaneousPlaybackRecordDetailService.saveBatch(details);
         }
      }
   }
}
