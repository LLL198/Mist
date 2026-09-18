package com.una.embyhub.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.una.embyhub.config.mybatis.MybatisPlusPage;
import com.una.embyhub.model.dto.request.simultaneous.SimultaneousPlaybackRecordRequest;
import com.una.embyhub.model.dto.response.simultaneous.SimultaneousPlaybackRecordResponse;
import com.una.embyhub.model.entity.SimultaneousPlaybackRecord;
import com.una.embyhub.model.entity.SimultaneousPlaybackRecordDetail;
import java.util.List;

public interface SimultaneousPlaybackRecordService extends IService<SimultaneousPlaybackRecord> {
   Page<SimultaneousPlaybackRecordResponse> select(MybatisPlusPage<SimultaneousPlaybackRecordRequest> page);

   void saveRecordWithDetails(SimultaneousPlaybackRecord record, List<SimultaneousPlaybackRecordDetail> details);
}
