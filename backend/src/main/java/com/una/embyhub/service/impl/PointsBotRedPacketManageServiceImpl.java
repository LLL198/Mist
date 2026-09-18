package com.una.embyhub.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.una.embyhub.config.mybatis.MybatisPlusPage;
import com.una.embyhub.mapper.PointsBotRedPacketClaimMapper;
import com.una.embyhub.mapper.PointsBotRedPacketMapper;
import com.una.embyhub.model.dto.request.pointsbot.PointsBotRedPacketRequest;
import com.una.embyhub.model.dto.response.pointsbot.PointsBotRedPacketClaimResponse;
import com.una.embyhub.model.dto.response.pointsbot.PointsBotRedPacketResponse;
import com.una.embyhub.model.entity.PointsBotRedPacket;
import com.una.embyhub.model.entity.PointsBotRedPacketClaim;
import com.una.embyhub.service.PointsBotRedPacketManageService;
import java.util.Locale;
import lombok.Generated;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Service
@Transactional(
   readOnly = true
)
public class PointsBotRedPacketManageServiceImpl implements PointsBotRedPacketManageService {
   private static final long MAX_PAGE_SIZE = 100L;
   private final PointsBotRedPacketMapper redPacketMapper;
   private final PointsBotRedPacketClaimMapper claimMapper;

   @Override
   public Page<PointsBotRedPacketResponse> select(MybatisPlusPage<PointsBotRedPacketRequest> request) {
      PointsBotRedPacketRequest filters = request.getObject();
      LambdaQueryWrapper<PointsBotRedPacket> wrapper = Wrappers.lambdaQuery();
      if (filters.getChatId() != null) {
         wrapper.eq(PointsBotRedPacket::getChatId, filters.getChatId());
      }

      if (filters.getCreatorUserId() != null) {
         wrapper.eq(PointsBotRedPacket::getCreatorUserId, filters.getCreatorUserId());
      }

      if (StringUtils.hasText(filters.getStatus())) {
         wrapper.eq(PointsBotRedPacket::getStatus, filters.getStatus().trim().toUpperCase(Locale.ROOT));
      }

      if (StringUtils.hasText(filters.getKeyword())) {
         String keyword = filters.getKeyword().trim();
         wrapper.and(
            query -> query.like(PointsBotRedPacket::getCreatorUsername, keyword)
                  .or()
                  .like(PointsBotRedPacket::getCreatorDisplayName, keyword)
                  .or()
                  .like(PointsBotRedPacket::getGreeting, keyword)
         );
      }

      wrapper.orderByDesc(PointsBotRedPacket::getId);
      long current = Math.max(1L, request.getCurrent());
      long size = Math.max(1L, Math.min(request.getSize(), 100L));
      Page<PointsBotRedPacket> source = this.redPacketMapper.selectPage(new Page<>(current, size), wrapper);
      Page<PointsBotRedPacketResponse> result = new Page<>(source.getCurrent(), source.getSize(), source.getTotal());
      result.setRecords(source.getRecords().stream().map(this::toResponse).toList());
      return result;
   }

   @Override
   public Page<PointsBotRedPacketClaimResponse> listClaims(long redPacketId, long current, long size) {
      long boundedCurrent = Math.max(1L, current);
      long boundedSize = Math.max(1L, Math.min(size, 100L));
      Page<PointsBotRedPacketClaim> source = this.claimMapper
         .selectPage(
            new Page<>(boundedCurrent, boundedSize),
            Wrappers.<PointsBotRedPacketClaim>lambdaQuery()
               .eq(PointsBotRedPacketClaim::getRedPacketId, Long.valueOf(redPacketId))
               .orderByAsc(PointsBotRedPacketClaim::getClaimedAt)
               .orderByAsc(PointsBotRedPacketClaim::getId)
         );
      Page<PointsBotRedPacketClaimResponse> result = new Page<>(source.getCurrent(), source.getSize(), source.getTotal());
      result.setRecords(source.getRecords().stream().map(this::toClaimResponse).toList());
      return result;
   }

   private PointsBotRedPacketResponse toResponse(PointsBotRedPacket source) {
      PointsBotRedPacketResponse response = new PointsBotRedPacketResponse();
      response.setId(source.getId());
      response.setChatId(source.getChatId());
      response.setMessageId(source.getMessageId());
      response.setCreatorUserId(source.getCreatorUserId());
      response.setCreatorUsername(source.getCreatorUsername());
      response.setCreatorDisplayName(source.getCreatorDisplayName());
      response.setGreeting(source.getGreeting());
      response.setTotalPoints(source.getTotalPoints());
      response.setTotalCount(source.getTotalCount());
      response.setClaimedCount(Math.max(0, this.positive(source.getTotalCount()) - this.positive(source.getRemainingCount())));
      response.setRemainingPoints(source.getRemainingPoints());
      response.setRemainingCount(source.getRemainingCount());
      response.setStatus(source.getStatus());
      response.setPublishedAt(source.getPublishedAt());
      response.setExpiresAt(source.getExpiresAt());
      response.setFinishedAt(source.getFinishedAt());
      response.setRefundedPoints(source.getRefundedPoints());
      response.setRefundedAt(source.getRefundedAt());
      response.setCreateDatetime(source.getCreateDatetime());
      response.setUpdateDatetime(source.getUpdateDatetime());
      return response;
   }

   private PointsBotRedPacketClaimResponse toClaimResponse(PointsBotRedPacketClaim source) {
      PointsBotRedPacketClaimResponse response = new PointsBotRedPacketClaimResponse();
      response.setId(source.getId());
      response.setRedPacketId(source.getRedPacketId());
      response.setUserId(source.getUserId());
      response.setUsername(source.getUsername());
      response.setDisplayName(source.getDisplayName());
      response.setPoints(source.getPoints());
      response.setClaimedAt(source.getClaimedAt());
      return response;
   }

   private int positive(Integer value) {
      return value == null ? 0 : Math.max(0, value);
   }

   @Generated
   public PointsBotRedPacketManageServiceImpl(final PointsBotRedPacketMapper redPacketMapper, final PointsBotRedPacketClaimMapper claimMapper) {
      this.redPacketMapper = redPacketMapper;
      this.claimMapper = claimMapper;
   }
}
