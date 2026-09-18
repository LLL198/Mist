package com.una.embyhub.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.una.embyhub.config.mybatis.MybatisPlusPage;
import com.una.embyhub.model.dto.request.playrecords.PlayRecordsRequest;
import com.una.embyhub.model.dto.request.playrecords.UserPlayStats;
import com.una.embyhub.model.dto.response.playrecords.PlayCountSummary;
import com.una.embyhub.model.dto.response.playrecords.PlayRecordsResponse;
import com.una.embyhub.model.entity.PlayRecords;
import java.util.Date;
import java.util.List;

public interface PlayRecordsService extends IService<PlayRecords> {
   Page<PlayRecordsResponse> select(MybatisPlusPage<PlayRecordsRequest> page);

   List<UserPlayStats> getPlayStats(String userName, Date startDate, Date endDate, Long embyInfoId);

   List<PlayRecords> findRecentPlays(String userName, int limit);

   List<PlayCountSummary> summaryByRange(Date start, Date end);

   List<PlayCountSummary> summaryByRange(Date start, Date end, Long embyInfoId);
}
