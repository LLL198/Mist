package com.una.embyhub.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.una.embyhub.config.mybatis.MybatisPlusPage;
import com.una.embyhub.model.dto.request.pointsbot.PointsBotUserAdjustRequest;
import com.una.embyhub.model.dto.request.pointsbot.PointsBotUserRequest;
import com.una.embyhub.model.dto.response.pointsbot.PointsBotUserAdjustResponse;
import com.una.embyhub.model.dto.response.pointsbot.PointsBotUserResponse;
import com.una.embyhub.model.dto.response.pointsbot.PointsBotUserStatsResponse;
import com.una.embyhub.model.entity.PointsBotUser;

public interface PointsBotUserManageService extends IService<PointsBotUser> {
   Page<PointsBotUserResponse> select(MybatisPlusPage<PointsBotUserRequest> page);

   PointsBotUserAdjustResponse adjustPoints(PointsBotUserAdjustRequest request);

   PointsBotUserStatsResponse getStats();
}
