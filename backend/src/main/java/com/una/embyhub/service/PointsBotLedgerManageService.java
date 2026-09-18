package com.una.embyhub.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.una.embyhub.config.mybatis.MybatisPlusPage;
import com.una.embyhub.model.dto.request.pointsbot.PointsBotLedgerRequest;
import com.una.embyhub.model.dto.response.pointsbot.PointsBotLedgerResponse;
import com.una.embyhub.model.dto.response.pointsbot.PointsBotLedgerStatsResponse;
import com.una.embyhub.model.entity.PointsBotLedger;
import java.util.Map;

public interface PointsBotLedgerManageService extends IService<PointsBotLedger> {
   Page<PointsBotLedgerResponse> select(MybatisPlusPage<PointsBotLedgerRequest> page);

   PointsBotLedgerStatsResponse getStats();

   String translateReason(String reason);

   Map<String, String> getAllReasonMappings();
}
