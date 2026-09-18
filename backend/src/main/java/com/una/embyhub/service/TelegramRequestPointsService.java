package com.una.embyhub.service;

import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.una.embyhub.config.common.exception.BizException;
import com.una.embyhub.config.common.utils.ConfigCacheLoaderUtils;
import com.una.embyhub.mapper.PointsBotLedgerMapper;
import com.una.embyhub.mapper.PointsBotUserMapper;
import com.una.embyhub.mapper.RequestListMapper;
import com.una.embyhub.model.entity.BaseEntity;
import com.una.embyhub.model.entity.PointsBotLedger;
import com.una.embyhub.model.entity.PointsBotLevelConfig;
import com.una.embyhub.model.entity.PointsBotUser;
import com.una.embyhub.model.entity.RequestList;
import com.una.embyhub.pointsbot.service.PointsBotConfigService;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import lombok.Generated;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

@Service
@Transactional(
   rollbackFor = {Exception.class}
)
public class TelegramRequestPointsService {
   @Generated
   private static final Logger log = LoggerFactory.getLogger(TelegramRequestPointsService.class);
   public static final String REQUEST_SOURCE_TELEGRAM = "telegram";
   private static final int DEFAULT_DAILY_FREE_COUNT = 0;
   private static final int DEFAULT_POINTS_PER_REQUEST = 10;
   private static final String SUBMIT_REASON = "telegram_request_submit";
   private static final String REFUND_REASON = "telegram_request_refund";
   @Autowired
   private ConfigCacheLoaderUtils configCacheLoaderUtils;
   @Autowired
   private PointsBotConfigService pointsBotConfigService;
   @Autowired
   private PointsBotUserMapper pointsBotUserMapper;
   @Autowired
   private PointsBotLedgerMapper pointsBotLedgerMapper;
   @Autowired
   private RequestListMapper requestListMapper;
   @Autowired
   private PointsBotLevelConfigService levelConfigService;

   public TelegramRequestPointsService.TelegramRequestPointsConfig loadConfig() {
      TelegramRequestPointsService.TelegramRequestPointsConfig config = this.defaultConfig();
      String configValue = this.configCacheLoaderUtils.getConfigValue("telegram_request_points_config");
      if (StringUtils.hasText(configValue)) {
         try {
            TelegramRequestPointsService.TelegramRequestPointsConfig parsed = JSONObject.parseObject(
               configValue, TelegramRequestPointsService.TelegramRequestPointsConfig.class
            );
            if (parsed != null) {
               config = parsed;
            }
         } catch (Exception var4) {
            log.warn("Telegram 求片积分配置解析失败，使用默认配置: {}", var4.getMessage());
         }
      }

      config.setDailyFreeCount(Math.max(0, config.getDailyFreeCount()));
      config.setPointsPerRequest(Math.max(0, config.getPointsPerRequest()));
      return config;
   }

   public TelegramRequestPointsService.ChargeResult chargeForSubmit(Long embyUserId, Long telegramUserId, String pointsRefId) {
      TelegramRequestPointsService.TelegramRequestPointsConfig config = this.loadConfig();
      if (!config.isEnabled()) {
         return TelegramRequestPointsService.ChargeResult.disabled();
      } else if (embyUserId != null && telegramUserId != null) {
         int todayFreeUsed = this.countTodayFreeTelegramRequests(embyUserId, telegramUserId);
         int pointsCost = todayFreeUsed < config.getDailyFreeCount() ? 0 : config.getPointsPerRequest();
         Long pointsChatId = this.resolvePointsChatId(pointsCost > 0);
         long balanceAfter = this.findCurrentBalance(pointsChatId, telegramUserId);
         if (pointsCost <= 0) {
            return TelegramRequestPointsService.ChargeResult.enabled(0, balanceAfter, todayFreeUsed, config.getDailyFreeCount());
         } else if (!StringUtils.hasText(pointsRefId)) {
            throw new BizException("Telegram 求片积分流水信息异常，请稍后再试。");
         } else {
            int updated = this.pointsBotUserMapper
               .update(
                  null,
                  new LambdaUpdateWrapper<PointsBotUser>()
                     .setSql("points = points - " + pointsCost, new Object[0])
                     .eq(PointsBotUser::getChatId, pointsChatId)
                     .eq(PointsBotUser::getUserId, telegramUserId)
                     .ge(PointsBotUser::getPoints, Integer.valueOf(pointsCost))
               );
            if (updated <= 0) {
               long currentBalance = this.findCurrentBalance(pointsChatId, telegramUserId);
               throw new BizException("积分不足，本次求片需要 " + pointsCost + " 积分，当前余额 " + currentBalance + "。");
            } else {
               PointsBotUser user = this.findPointsUser(pointsChatId, telegramUserId);
               if (user == null) {
                  throw new BizException("积分账户异常，请稍后再试。");
               } else {
                  this.refreshLevel(user);
                  this.insertLedger(pointsChatId, telegramUserId, -pointsCost, "telegram_request_submit", pointsRefId);
                  return TelegramRequestPointsService.ChargeResult.enabled(
                     pointsCost, this.nullToZero(user.getPoints()), todayFreeUsed, config.getDailyFreeCount()
                  );
               }
            }
         }
      } else {
         throw new BizException("Telegram 求片用户信息异常，请重新绑定后再试。");
      }
   }

   public void refundRejectedRequests(List<RequestList> requestListList) {
      if (!CollectionUtils.isEmpty(requestListList)) {
         TelegramRequestPointsService.TelegramRequestPointsConfig config = this.loadConfig();
         if (config.isRefundOnReject()) {
            for (RequestList requestList : requestListList) {
               this.refundRejectedRequest(requestList);
            }
         }
      }
   }

   private void refundRejectedRequest(RequestList requestList) {
      if (requestList != null
         && "telegram".equals(requestList.getRequestSource())
         && requestList.getTelegramUserId() != null
         && requestList.getPointsCost() != null
         && requestList.getPointsCost() > 0
         && !Integer.valueOf(1).equals(requestList.getPointsRefunded())
         && StringUtils.hasText(requestList.getPointsRefId())) {
         int updatedRequest = this.requestListMapper
            .update(
               null,
               new LambdaUpdateWrapper<RequestList>()
                  .set(RequestList::getPointsRefunded, Integer.valueOf(1))
                  .eq(RequestList::getId, requestList.getId())
                  .and(wrapper -> wrapper.eq(RequestList::getPointsRefunded, Integer.valueOf(0)).or().isNull(RequestList::getPointsRefunded))
            );
         if (updatedRequest > 0) {
            Long pointsChatId = this.resolvePointsChatId(true);
            int pointsCost = requestList.getPointsCost();
            PointsBotUser user = this.findPointsUser(pointsChatId, requestList.getTelegramUserId());
            if (user == null) {
               user = new PointsBotUser();
               user.setChatId(pointsChatId);
               user.setUserId(requestList.getTelegramUserId());
               user.setUsername(String.valueOf(requestList.getTelegramUserId()));
               user.setDisplayName(String.valueOf(requestList.getTelegramUserId()));
               user.setPoints((long)pointsCost);
               user.setCheckinStreak(0);
               user.setDailyMessagePoints(0);
               user.setDailyMessageCount(0);
               this.applyLevelFields(user);
               this.pointsBotUserMapper.insert(user);
            } else {
               this.pointsBotUserMapper
                  .update(
                     null,
                     new LambdaUpdateWrapper<PointsBotUser>()
                        .setSql("points = points + " + pointsCost, new Object[0])
                        .eq(PointsBotUser::getId, user.getId())
                  );
               user = this.findPointsUser(pointsChatId, requestList.getTelegramUserId());
               this.refreshLevel(user);
            }

            this.insertLedger(pointsChatId, requestList.getTelegramUserId(), pointsCost, "telegram_request_refund", requestList.getPointsRefId());
         }
      }
   }

   private int countTodayFreeTelegramRequests(Long embyUserId, Long telegramUserId) {
      ZoneId zoneId = ZoneId.systemDefault();
      LocalDate today = LocalDate.now(zoneId);
      Date start = Date.from(today.atStartOfDay(zoneId).toInstant());
      Date end = Date.from(today.plusDays(1L).atStartOfDay(zoneId).toInstant());
      Long count = this.requestListMapper
         .selectCount(
            new LambdaQueryWrapper<RequestList>()
               .eq(RequestList::getUserId, embyUserId)
               .eq(RequestList::getRequestSource, "telegram")
               .eq(RequestList::getTelegramUserId, telegramUserId)
               .eq(RequestList::getPointsCost, Integer.valueOf(0))
               .ne(RequestList::getStatus, Integer.valueOf(2))
               .ge(BaseEntity::getCreateDatetime, start)
               .lt(BaseEntity::getCreateDatetime, end)
         );
      return count == null ? 0 : count.intValue();
   }

   private Long resolvePointsChatId(boolean required) {
      String groupChatId = this.pointsBotConfigService.loadConfig().getConfig().getGroupChatId();
      if (!StringUtils.hasText(groupChatId)) {
         if (required) {
            throw new BizException("未配置积分群/频道 ID，请先配置 Telegram 机器人的积分群/频道 Chat ID。");
         } else {
            return null;
         }
      } else {
         try {
            return Long.parseLong(groupChatId.trim());
         } catch (NumberFormatException var4) {
            if (required) {
               throw new BizException("积分群/频道 ID 配置不正确，请检查 Telegram 机器人的积分群/频道 Chat ID。");
            } else {
               return null;
            }
         }
      }
   }

   private long findCurrentBalance(Long pointsChatId, Long telegramUserId) {
      if (pointsChatId != null && telegramUserId != null) {
         PointsBotUser user = this.findPointsUser(pointsChatId, telegramUserId);
         return user == null ? 0L : this.nullToZero(user.getPoints());
      } else {
         return 0L;
      }
   }

   private PointsBotUser findPointsUser(Long pointsChatId, Long telegramUserId) {
      return pointsChatId != null && telegramUserId != null
         ? new LambdaQueryChainWrapper<>(this.pointsBotUserMapper)
            .eq(PointsBotUser::getChatId, pointsChatId)
            .eq(PointsBotUser::getUserId, telegramUserId)
            .one()
         : null;
   }

   private void refreshLevel(PointsBotUser user) {
      if (user != null) {
         this.applyLevelFields(user);
         this.pointsBotUserMapper
            .update(
               null,
               new LambdaUpdateWrapper<PointsBotUser>()
                  .set(PointsBotUser::getLevelId, user.getLevelId())
                  .set(PointsBotUser::getLevelName, user.getLevelName())
                  .eq(PointsBotUser::getId, user.getId())
            );
      }
   }

   private void applyLevelFields(PointsBotUser user) {
      PointsBotLevelConfig level = this.levelConfigService.findLevelForPoints(this.nullToZero(user.getPoints()));
      if (level == null) {
         user.setLevelId(null);
         user.setLevelName(null);
      } else {
         user.setLevelId(level.getId());
         user.setLevelName(level.getLevelName());
      }
   }

   private void insertLedger(Long chatId, Long telegramUserId, Integer delta, String reason, String refId) {
      PointsBotLedger ledger = new PointsBotLedger();
      ledger.setChatId(chatId);
      ledger.setUserId(telegramUserId);
      ledger.setDelta(delta);
      ledger.setReason(reason);
      ledger.setRefId(refId);
      this.pointsBotLedgerMapper.insert(ledger);
   }

   private long nullToZero(Long value) {
      return value == null ? 0L : value;
   }

   private TelegramRequestPointsService.TelegramRequestPointsConfig defaultConfig() {
      TelegramRequestPointsService.TelegramRequestPointsConfig config = new TelegramRequestPointsService.TelegramRequestPointsConfig();
      config.setEnabled(true);
      config.setDailyFreeCount(0);
      config.setPointsPerRequest(10);
      config.setRefundOnReject(true);
      return config;
   }

   public static class ChargeResult {
      private final boolean pointsEnabled;
      private final int pointsCost;
      private final long balanceAfter;
      private final int todayUsedBefore;
      private final int dailyFreeCount;

      private ChargeResult(boolean pointsEnabled, int pointsCost, long balanceAfter, int todayUsedBefore, int dailyFreeCount) {
         this.pointsEnabled = pointsEnabled;
         this.pointsCost = pointsCost;
         this.balanceAfter = balanceAfter;
         this.todayUsedBefore = todayUsedBefore;
         this.dailyFreeCount = dailyFreeCount;
      }

      public static TelegramRequestPointsService.ChargeResult disabled() {
         return new TelegramRequestPointsService.ChargeResult(false, 0, 0L, 0, 0);
      }

      public static TelegramRequestPointsService.ChargeResult enabled(int pointsCost, long balanceAfter, int todayUsedBefore, int dailyFreeCount) {
         return new TelegramRequestPointsService.ChargeResult(true, pointsCost, balanceAfter, todayUsedBefore, dailyFreeCount);
      }

      public boolean isFree() {
         return this.pointsEnabled && this.pointsCost <= 0;
      }

      public int getTodayUsedAfter() {
         return this.todayUsedBefore + 1;
      }

      @Generated
      public boolean isPointsEnabled() {
         return this.pointsEnabled;
      }

      @Generated
      public int getPointsCost() {
         return this.pointsCost;
      }

      @Generated
      public long getBalanceAfter() {
         return this.balanceAfter;
      }

      @Generated
      public int getTodayUsedBefore() {
         return this.todayUsedBefore;
      }

      @Generated
      public int getDailyFreeCount() {
         return this.dailyFreeCount;
      }
   }

   public static class TelegramRequestPointsConfig {
      private boolean enabled = true;
      private int dailyFreeCount = 0;
      private int pointsPerRequest = 10;
      private boolean refundOnReject = true;

      @Generated
      public boolean isEnabled() {
         return this.enabled;
      }

      @Generated
      public int getDailyFreeCount() {
         return this.dailyFreeCount;
      }

      @Generated
      public int getPointsPerRequest() {
         return this.pointsPerRequest;
      }

      @Generated
      public boolean isRefundOnReject() {
         return this.refundOnReject;
      }

      @Generated
      public void setEnabled(final boolean enabled) {
         this.enabled = enabled;
      }

      @Generated
      public void setDailyFreeCount(final int dailyFreeCount) {
         this.dailyFreeCount = dailyFreeCount;
      }

      @Generated
      public void setPointsPerRequest(final int pointsPerRequest) {
         this.pointsPerRequest = pointsPerRequest;
      }

      @Generated
      public void setRefundOnReject(final boolean refundOnReject) {
         this.refundOnReject = refundOnReject;
      }

      @Generated
      @Override
      public boolean equals(final Object o) {
         if (o == this) {
            return true;
         } else if (!(o instanceof TelegramRequestPointsService.TelegramRequestPointsConfig other)) {
            return false;
         } else if (!other.canEqual(this)) {
            return false;
         } else if (this.isEnabled() != other.isEnabled()) {
            return false;
         } else if (this.getDailyFreeCount() != other.getDailyFreeCount()) {
            return false;
         } else {
            return this.getPointsPerRequest() != other.getPointsPerRequest() ? false : this.isRefundOnReject() == other.isRefundOnReject();
         }
      }

      @Generated
      protected boolean canEqual(final Object other) {
         return other instanceof TelegramRequestPointsService.TelegramRequestPointsConfig;
      }

      @Generated
      @Override
      public int hashCode() {
         int PRIME = 59;
         int result = 1;
         result = result * 59 + (this.isEnabled() ? 79 : 97);
         result = result * 59 + this.getDailyFreeCount();
         result = result * 59 + this.getPointsPerRequest();
         return result * 59 + (this.isRefundOnReject() ? 79 : 97);
      }

      @Generated
      @Override
      public String toString() {
         return "TelegramRequestPointsService.TelegramRequestPointsConfig(enabled="
            + this.isEnabled()
            + ", dailyFreeCount="
            + this.getDailyFreeCount()
            + ", pointsPerRequest="
            + this.getPointsPerRequest()
            + ", refundOnReject="
            + this.isRefundOnReject()
            + ")";
      }
   }
}
