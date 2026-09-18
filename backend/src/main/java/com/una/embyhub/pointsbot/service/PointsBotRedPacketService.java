package com.una.embyhub.pointsbot.service;

import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.toolkit.ChainWrappers;
import com.una.embyhub.config.common.enums.ResponseStatusEnum;
import com.una.embyhub.config.common.exception.BizException;
import com.una.embyhub.mapper.PointsBotRedPacketClaimMapper;
import com.una.embyhub.mapper.PointsBotRedPacketMapper;
import com.una.embyhub.model.entity.PointsBotRedPacket;
import com.una.embyhub.model.entity.PointsBotRedPacketClaim;
import com.una.embyhub.pointsbot.model.PointsProfile;
import com.una.embyhub.pointsbot.model.RedPacketClaimResult;
import com.una.embyhub.pointsbot.model.RedPacketExpireResult;
import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Random;
import lombok.Generated;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Service
public class PointsBotRedPacketService {
   public static final ZoneId BUSINESS_ZONE = ZoneId.of("Asia/Shanghai");
   public static final int MAX_TOTAL_POINTS = 1000;
   public static final int MAX_PACKET_COUNT = 100;
   public static final String STATUS_PUBLISHING = "PUBLISHING";
   public static final String STATUS_OPEN = "OPEN";
   public static final String STATUS_FINISHED = "FINISHED";
   public static final String STATUS_EXPIRED = "EXPIRED";
   public static final String STATUS_CANCELLED = "CANCELLED";
   private final PointsBotRedPacketMapper redPacketMapper;
   private final PointsBotRedPacketClaimMapper claimMapper;
   private final PointsStore pointsStore;
   private final PointsBotFoamBagService foamBagService;
   private final Random random = new SecureRandom();

   public void validateParameters(int totalPoints, int totalCount) {
      if (totalPoints < 1 || totalPoints > 1000) {
         throw this.badRequest("红包总积分必须在 1-1000 之间");
      } else if (totalCount < 1 || totalCount > 100) {
         throw this.badRequest("红包个数必须在 1-100 之间");
      } else if (totalPoints < totalCount) {
         throw this.badRequest("红包总积分不能小于红包个数，每个红包至少需要 1 积分");
      }
   }

   @Transactional(
      rollbackFor = {Exception.class}
   )
   public PointsBotRedPacket create(
      long chatId,
      long creatorUserId,
      String creatorUsername,
      String creatorDisplayName,
      int totalPoints,
      int totalCount,
      String greeting,
      LocalDateTime expiresAt
   ) {
      this.validateParameters(totalPoints, totalCount);
      LocalDateTime now = this.now();
      if (expiresAt != null && expiresAt.isAfter(now)) {
         PointsProfile creator = this.pointsStore.getOrCreate(chatId, creatorUserId, creatorUsername, creatorDisplayName);
         this.foamBagService.ensureOutgoingTransferAllowed(chatId, creatorUserId);
         PointsBotRedPacket redPacket = new PointsBotRedPacket();
         redPacket.setChatId(chatId);
         redPacket.setCreatorUserId(creatorUserId);
         redPacket.setCreatorUsername(creatorUsername);
         redPacket.setCreatorDisplayName(creatorDisplayName);
         redPacket.setGreeting(this.normalizeGreeting(greeting));
         redPacket.setTotalPoints(totalPoints);
         redPacket.setTotalCount(totalCount);
         redPacket.setRemainingPoints(totalPoints);
         redPacket.setRemainingCount(totalCount);
         redPacket.setStatus("PUBLISHING");
         redPacket.setExpiresAt(expiresAt);
         redPacket.setRefundedPoints(0);
         this.redPacketMapper.insert(redPacket);
         int applied = this.pointsStore.addPoints(creator, -totalPoints, "red_packet_hold", String.valueOf(redPacket.getId()));
         if (applied != -totalPoints) {
            throw this.badRequest("积分不足，无法发送这个红包");
         } else {
            return redPacket;
         }
      } else {
         throw this.badRequest("红包过期时间必须晚于当前时间");
      }
   }

   @Transactional(
      rollbackFor = {Exception.class}
   )
   public PointsBotRedPacket publish(long redPacketId, long messageId) {
      PointsBotRedPacket redPacket = this.requireLocked(redPacketId);
      if (!"PUBLISHING".equals(redPacket.getStatus())) {
         throw this.badRequest("红包当前状态无法发布");
      } else {
         redPacket.setMessageId(messageId);
         redPacket.setStatus("OPEN");
         redPacket.setPublishedAt(this.now());
         redPacket.setPanelSyncedAt(this.now());
         this.redPacketMapper.updateById(redPacket);
         return redPacket;
      }
   }

   @Transactional(
      rollbackFor = {Exception.class}
   )
   public RedPacketExpireResult cancelPublishing(long redPacketId) {
      PointsBotRedPacket redPacket = this.requireLocked(redPacketId);
      if (!"PUBLISHING".equals(redPacket.getStatus())) {
         return new RedPacketExpireResult(false, redPacket, 0);
      } else {
         int refunded = this.refundRemainingLocked(redPacket, "red_packet_cancel_refund");
         redPacket.setStatus("CANCELLED");
         redPacket.setFinishedAt(this.now());
         redPacket.setPanelSyncedAt(null);
         this.redPacketMapper.updateById(redPacket);
         return new RedPacketExpireResult(true, redPacket, refunded);
      }
   }

   @Transactional(
      rollbackFor = {Exception.class}
   )
   public RedPacketExpireResult cancelOpen(long redPacketId) {
      PointsBotRedPacket redPacket = this.requireLocked(redPacketId);
      if ("CANCELLED".equals(redPacket.getStatus())) {
         return new RedPacketExpireResult(false, redPacket, 0);
      } else if (!"OPEN".equals(redPacket.getStatus())) {
         throw this.badRequest("只有领取中的红包可以取消");
      } else {
         int refunded = this.refundRemainingLocked(redPacket, "red_packet_cancel_refund");
         redPacket.setStatus("CANCELLED");
         redPacket.setFinishedAt(this.now());
         redPacket.setPanelSyncedAt(null);
         this.redPacketMapper.updateById(redPacket);
         return new RedPacketExpireResult(true, redPacket, refunded);
      }
   }

   @Transactional(
      rollbackFor = {Exception.class}
   )
   public RedPacketClaimResult claim(
      long redPacketId, long callbackChatId, long callbackMessageId, long userId, String username, String displayName, LocalDateTime claimedAt
   ) {
      PointsBotRedPacket redPacket = this.redPacketMapper.selectByIdForUpdate(redPacketId);
      if (redPacket == null) {
         return this.result(RedPacketClaimResult.Status.NOT_FOUND, null, 0, 0);
      } else if (redPacket.getChatId().equals(callbackChatId) && redPacket.getMessageId() != null && redPacket.getMessageId() == callbackMessageId) {
         PointsBotRedPacketClaim existing = this.findClaim(redPacketId, userId);
         if (existing != null) {
            return this.result(RedPacketClaimResult.Status.ALREADY_CLAIMED, redPacket, existing.getPoints(), 0);
         } else if ("FINISHED".equals(redPacket.getStatus())) {
            return this.result(RedPacketClaimResult.Status.FINISHED, redPacket, 0, 0);
         } else if ("EXPIRED".equals(redPacket.getStatus())) {
            return this.result(RedPacketClaimResult.Status.EXPIRED, redPacket, 0, redPacket.getRefundedPoints());
         } else if ("CANCELLED".equals(redPacket.getStatus())) {
            return this.result(RedPacketClaimResult.Status.CANCELLED, redPacket, 0, redPacket.getRefundedPoints());
         } else if (!"OPEN".equals(redPacket.getStatus())) {
            return this.result(RedPacketClaimResult.Status.NOT_OPEN, redPacket, 0, 0);
         } else {
            LocalDateTime effectiveClaimedAt = claimedAt == null ? this.now() : claimedAt;
            if (!effectiveClaimedAt.isBefore(redPacket.getExpiresAt())) {
               int refunded = this.expireLocked(redPacket, effectiveClaimedAt);
               return this.result(RedPacketClaimResult.Status.EXPIRED, redPacket, 0, refunded);
            } else {
               int remainingPoints = this.positive(redPacket.getRemainingPoints());
               int remainingCount = this.positive(redPacket.getRemainingCount());
               if (remainingPoints > 0 && remainingCount > 0) {
                  PointsProfile receiver = this.pointsStore.getOrCreate(callbackChatId, userId, username, displayName);
                  this.foamBagService.ensureIncomingTransferAllowed(callbackChatId, userId);
                  int points = randomAmount(this.random, remainingPoints, remainingCount);
                  PointsBotRedPacketClaim claim = new PointsBotRedPacketClaim();
                  claim.setRedPacketId(redPacketId);
                  claim.setChatId(callbackChatId);
                  claim.setUserId(userId);
                  claim.setUsername(username);
                  claim.setDisplayName(displayName);
                  claim.setPoints(points);
                  claim.setClaimedAt(effectiveClaimedAt);
                  this.claimMapper.insert(claim);
                  int applied = this.pointsStore.addPoints(receiver, points, "red_packet_claim", String.valueOf(redPacketId));
                  if (applied != points) {
                     throw new IllegalStateException("红包领取积分未完整到账");
                  } else {
                     redPacket.setRemainingPoints(remainingPoints - points);
                     redPacket.setRemainingCount(remainingCount - 1);
                     redPacket.setPanelSyncedAt(null);
                     if (redPacket.getRemainingCount() == 0) {
                        this.finishLocked(redPacket, effectiveClaimedAt);
                     } else {
                        this.redPacketMapper.updateById(redPacket);
                     }

                     return this.result(RedPacketClaimResult.Status.CLAIMED, redPacket, points, 0);
                  }
               } else {
                  this.finishLocked(redPacket, effectiveClaimedAt);
                  return this.result(RedPacketClaimResult.Status.FINISHED, redPacket, 0, 0);
               }
            }
         }
      } else {
         return this.result(RedPacketClaimResult.Status.WRONG_MESSAGE, redPacket, 0, 0);
      }
   }

   @Transactional(
      rollbackFor = {Exception.class}
   )
   public RedPacketExpireResult expire(long redPacketId, LocalDateTime expiredAt) {
      PointsBotRedPacket redPacket = this.redPacketMapper.selectByIdForUpdate(redPacketId);
      if (redPacket == null) {
         return new RedPacketExpireResult(false, null, 0);
      } else if (!"OPEN".equals(redPacket.getStatus()) && !"PUBLISHING".equals(redPacket.getStatus())) {
         return new RedPacketExpireResult(false, redPacket, 0);
      } else {
         LocalDateTime effectiveExpiredAt = expiredAt == null ? this.now() : expiredAt;
         if (effectiveExpiredAt.isBefore(redPacket.getExpiresAt())) {
            return new RedPacketExpireResult(false, redPacket, 0);
         } else {
            int refunded = this.expireLocked(redPacket, effectiveExpiredAt);
            return new RedPacketExpireResult(true, redPacket, refunded);
         }
      }
   }

   public PointsBotRedPacketService.RedPacketView getView(long redPacketId) {
      PointsBotRedPacket redPacket = this.redPacketMapper.selectById(Long.valueOf(redPacketId));
      return redPacket == null ? null : this.toView(redPacket);
   }

   public List<PointsBotRedPacketClaim> listClaims(long redPacketId) {
      return new LambdaQueryChainWrapper<>(this.claimMapper)
         .eq(PointsBotRedPacketClaim::getRedPacketId, Long.valueOf(redPacketId))
         .orderByAsc(PointsBotRedPacketClaim::getClaimedAt)
         .orderByAsc(PointsBotRedPacketClaim::getId)
         .last("limit 100")
         .list();
   }

   public List<PointsBotRedPacket> listOpenPackets(int limit) {
      return ChainWrappers.lambdaQueryChain(this.redPacketMapper)
         .eq(PointsBotRedPacket::getStatus, "OPEN")
         .orderByAsc(PointsBotRedPacket::getExpiresAt)
         .orderByAsc(PointsBotRedPacket::getId)
         .last("limit " + this.boundedLimit(limit))
         .list();
   }

   public List<Long> findDuePacketIds(LocalDateTime now, int limit) {
      return ChainWrappers.lambdaQueryChain(this.redPacketMapper)
         .select(PointsBotRedPacket::getId)
         .in(PointsBotRedPacket::getStatus, List.of("PUBLISHING", "OPEN"))
         .le(PointsBotRedPacket::getExpiresAt, now == null ? this.now() : now)
         .orderByAsc(PointsBotRedPacket::getExpiresAt)
         .orderByAsc(PointsBotRedPacket::getId)
         .last("limit " + this.boundedLimit(limit))
         .list()
         .stream()
         .map(PointsBotRedPacket::getId)
         .toList();
   }

   public List<PointsBotRedPacket> listUnsyncedTerminalPackets(int limit) {
      return ChainWrappers.lambdaQueryChain(this.redPacketMapper)
         .in(PointsBotRedPacket::getStatus, List.of("FINISHED", "EXPIRED", "CANCELLED"))
         .isNull(PointsBotRedPacket::getPanelSyncedAt)
         .isNotNull(PointsBotRedPacket::getMessageId)
         .orderByAsc(PointsBotRedPacket::getFinishedAt)
         .orderByAsc(PointsBotRedPacket::getId)
         .last("limit " + this.boundedLimit(limit))
         .list();
   }

   public void markPanelSynced(long redPacketId) {
      ChainWrappers.lambdaUpdateChain(this.redPacketMapper)
         .eq(PointsBotRedPacket::getId, Long.valueOf(redPacketId))
         .set(PointsBotRedPacket::getPanelSyncedAt, this.now())
         .update();
   }

   static int randomAmount(Random random, int remainingPoints, int remainingCount) {
      if (remainingPoints < remainingCount || remainingCount <= 0) {
         throw new IllegalArgumentException("红包剩余积分和数量不满足分配条件");
      } else if (remainingCount == 1) {
         return remainingPoints;
      } else {
         int reservableMaximum = remainingPoints - (remainingCount - 1);
         int doubleMeanMaximum = Math.max(1, remainingPoints * 2 / remainingCount);
         int maximum = Math.min(reservableMaximum, doubleMeanMaximum);
         return random.nextInt(maximum) + 1;
      }
   }

   private int expireLocked(PointsBotRedPacket redPacket, LocalDateTime expiredAt) {
      int refunded = this.refundRemainingLocked(redPacket, "red_packet_refund");
      redPacket.setStatus("EXPIRED");
      redPacket.setFinishedAt(expiredAt);
      redPacket.setPanelSyncedAt(null);
      this.redPacketMapper.updateById(redPacket);
      return refunded;
   }

   private int refundRemainingLocked(PointsBotRedPacket redPacket, String reason) {
      int remainingPoints = this.positive(redPacket.getRemainingPoints());
      if (remainingPoints <= 0) {
         return 0;
      } else {
         PointsProfile creator = this.pointsStore.findByUserId(redPacket.getChatId(), redPacket.getCreatorUserId());
         if (creator == null) {
            throw new IllegalStateException("找不到红包发起人的积分账户");
         } else {
            int applied = this.pointsStore.addPoints(creator, remainingPoints, reason, String.valueOf(redPacket.getId()));
            if (applied != remainingPoints) {
               throw new IllegalStateException("红包剩余积分未完整退回");
            } else {
               redPacket.setRemainingPoints(0);
               redPacket.setRefundedPoints(remainingPoints);
               redPacket.setRefundedAt(this.now());
               return remainingPoints;
            }
         }
      }
   }

   private void finishLocked(PointsBotRedPacket redPacket, LocalDateTime finishedAt) {
      redPacket.setRemainingPoints(0);
      redPacket.setRemainingCount(0);
      redPacket.setStatus("FINISHED");
      redPacket.setFinishedAt(finishedAt);
      redPacket.setPanelSyncedAt(null);
      this.redPacketMapper.updateById(redPacket);
   }

   private PointsBotRedPacket requireLocked(long redPacketId) {
      PointsBotRedPacket redPacket = this.redPacketMapper.selectByIdForUpdate(redPacketId);
      if (redPacket == null) {
         throw this.badRequest("红包不存在");
      } else {
         return redPacket;
      }
   }

   private PointsBotRedPacketClaim findClaim(long redPacketId, long userId) {
      return new LambdaQueryChainWrapper<>(this.claimMapper)
         .eq(PointsBotRedPacketClaim::getRedPacketId, Long.valueOf(redPacketId))
         .eq(PointsBotRedPacketClaim::getUserId, Long.valueOf(userId))
         .last("limit 1")
         .one();
   }

   private PointsBotRedPacketService.RedPacketView toView(PointsBotRedPacket redPacket) {
      int totalCount = this.positive(redPacket.getTotalCount());
      int remainingCount = this.positive(redPacket.getRemainingCount());
      return new PointsBotRedPacketService.RedPacketView(redPacket, Math.max(0, totalCount - remainingCount));
   }

   private RedPacketClaimResult result(RedPacketClaimResult.Status status, PointsBotRedPacket redPacket, int points, int refundedPoints) {
      return new RedPacketClaimResult(status, redPacket, points, refundedPoints);
   }

   private String normalizeGreeting(String greeting) {
      if (!StringUtils.hasText(greeting)) {
         return "祝大家好运！";
      } else {
         String normalized = greeting.trim();
         return normalized.length() <= 200 ? normalized : normalized.substring(0, 200);
      }
   }

   private int boundedLimit(int limit) {
      return Math.max(1, Math.min(limit, 500));
   }

   private int positive(Integer value) {
      return value == null ? 0 : Math.max(0, value);
   }

   private LocalDateTime now() {
      return LocalDateTime.now(BUSINESS_ZONE);
   }

   private BizException badRequest(String message) {
      return new BizException(ResponseStatusEnum.BAD_REQUEST.getCode(), message);
   }

   @Generated
   public PointsBotRedPacketService(
      final PointsBotRedPacketMapper redPacketMapper,
      final PointsBotRedPacketClaimMapper claimMapper,
      final PointsStore pointsStore,
      final PointsBotFoamBagService foamBagService
   ) {
      this.redPacketMapper = redPacketMapper;
      this.claimMapper = claimMapper;
      this.pointsStore = pointsStore;
      this.foamBagService = foamBagService;
   }

   public static record RedPacketView(PointsBotRedPacket redPacket, int claimedCount) {
   }
}
