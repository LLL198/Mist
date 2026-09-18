package com.una.embyhub.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.una.embyhub.config.mybatis.MybatisPlusPage;
import com.una.embyhub.model.dto.request.pointsbot.PointsBotRedPacketRequest;
import com.una.embyhub.model.dto.response.pointsbot.PointsBotRedPacketClaimResponse;
import com.una.embyhub.model.dto.response.pointsbot.PointsBotRedPacketResponse;

public interface PointsBotRedPacketManageService {
   Page<PointsBotRedPacketResponse> select(MybatisPlusPage<PointsBotRedPacketRequest> page);

   Page<PointsBotRedPacketClaimResponse> listClaims(long redPacketId, long current, long size);
}
