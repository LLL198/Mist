package com.una.embyhub.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.una.embyhub.config.mybatis.MybatisPlusPage;
import com.una.embyhub.model.dto.request.pointsbot.PointsBotLotteryEntryRequest;
import com.una.embyhub.model.dto.response.pointsbot.PointsBotLotteryEntryResponse;
import com.una.embyhub.model.dto.response.pointsbot.PointsBotLotteryEntryStatsResponse;
import com.una.embyhub.model.entity.PointsBotLotteryEntry;

public interface PointsBotLotteryEntryManageService extends IService<PointsBotLotteryEntry> {
   Page<PointsBotLotteryEntryResponse> select(MybatisPlusPage<PointsBotLotteryEntryRequest> page);

   PointsBotLotteryEntryStatsResponse getStats();
}
