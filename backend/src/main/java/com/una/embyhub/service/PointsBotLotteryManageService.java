package com.una.embyhub.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.una.embyhub.config.mybatis.MybatisPlusPage;
import com.una.embyhub.model.dto.request.pointsbot.PointsBotLotteryRequest;
import com.una.embyhub.model.dto.response.pointsbot.PointsBotLotteryResponse;
import com.una.embyhub.model.dto.response.pointsbot.PointsBotLotteryStatsResponse;
import com.una.embyhub.model.entity.PointsBotLottery;

public interface PointsBotLotteryManageService extends IService<PointsBotLottery> {
   Page<PointsBotLotteryResponse> select(MybatisPlusPage<PointsBotLotteryRequest> page);

   PointsBotLotteryStatsResponse getStats();
}
