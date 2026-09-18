package com.una.embyhub.pointsbot.service;

import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.toolkit.ChainWrappers;
import com.una.embyhub.config.common.enums.ResponseStatusEnum;
import com.una.embyhub.config.common.exception.BizException;
import com.una.embyhub.config.mybatis.MybatisPlusPage;
import com.una.embyhub.mapper.PointsBotFoamBagConfigMapper;
import com.una.embyhub.mapper.PointsBotFoamBagMapper;
import com.una.embyhub.mapper.PointsBotUserMapper;
import com.una.embyhub.model.dto.request.pointsbot.PointsBotFoamBagConfigUpdate;
import com.una.embyhub.model.dto.request.pointsbot.PointsBotFoamBagRequest;
import com.una.embyhub.model.dto.response.pointsbot.PointsBotFoamBagConfigResponse;
import com.una.embyhub.model.dto.response.pointsbot.PointsBotFoamBagMyRecordsResponse;
import com.una.embyhub.model.dto.response.pointsbot.PointsBotFoamBagResponse;
import com.una.embyhub.model.entity.EmbyUser;
import com.una.embyhub.model.entity.PointsBotFoamBag;
import com.una.embyhub.model.entity.PointsBotFoamBagConfig;
import com.una.embyhub.model.entity.PointsBotUser;
import com.una.embyhub.model.entity.UserOauthBinding;
import com.una.embyhub.pointsbot.model.FoamBagState;
import com.una.embyhub.pointsbot.model.FoamBagTransferResult;
import com.una.embyhub.pointsbot.model.PointsProfile;
import com.una.embyhub.service.TelegramBindingManager;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import lombok.Generated;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Service
public class PointsBotFoamBagService {
   public static final ZoneId BUSINESS_ZONE = ZoneId.of("Asia/Shanghai");
   private static final List<Integer> DEFAULT_TIERS = List.of(10, 300, 500);
   private static final long CONFIG_ID = 1L;
   private final PointsBotFoamBagMapper foamBagMapper;
   private final PointsBotFoamBagConfigMapper configMapper;
   private final PointsBotUserMapper pointsBotUserMapper;
   private final PointsStore pointsStore;
   private final TelegramBindingManager telegramBindingManager;

   public PointsBotFoamBagConfigResponse getConfig() {
      PointsBotFoamBagConfig stored = this.configMapper.selectById(Long.valueOf(1L));
      return this.toConfigResponse(stored);
   }

   @Transactional(
      rollbackFor = {Exception.class}
   )
   public PointsBotFoamBagConfigResponse updateConfig(PointsBotFoamBagConfigUpdate request) {
      List<Integer> tiers = this.normalizeTiers(request.getAmountTiers());
      PointsBotFoamBagConfig stored = this.configMapper.selectById(Long.valueOf(1L));
      if (stored == null) {
         stored = new PointsBotFoamBagConfig();
         stored.setId(1L);
      }

      stored.setAmountTiersJson(JSON.toJSONString(tiers));
      stored.setDailyLimit(request.getDailyLimit());
      stored.setRepaymentMultiplier(request.getRepaymentMultiplier());
      stored.setRepaymentHours(request.getRepaymentHours());
      stored.setPenaltyDays(request.getPenaltyDays());
      stored.setAllowUnboundUsers(Boolean.TRUE.equals(request.getAllowUnboundUsers()) ? 1 : 0);
      if (this.configMapper.selectById(Long.valueOf(1L)) == null) {
         this.configMapper.insert(stored);
      } else {
         this.configMapper.updateById(stored);
      }

      return this.toConfigResponse(stored);
   }

   @Transactional(
      rollbackFor = {Exception.class}
   )
   public FoamBagState getState(long chatId, long userId, String username, String displayName) {
      PointsProfile profile = this.pointsStore.getOrCreate(chatId, userId, username, displayName);
      PointsBotUser lockedUser = this.requireLockedUser(chatId, userId);
      LocalDateTime now = this.now();
      PointsBotFoamBag active = this.findActiveForUpdate(chatId, userId);
      if (this.isDue(active, now)) {
         this.penalizeLocked(active, lockedUser, now);
         active = null;
      }

      LocalDateTime penaltyUntil = this.findPenaltyUntil(chatId, userId, now);
      return FoamBagState.builder()
         .config(this.getConfig())
         .activeBag(active)
         .penaltyUntil(penaltyUntil)
         .points(lockedUser.getPoints() == null ? profile.getPoints() : lockedUser.getPoints())
         .usedToday(this.countToday(chatId, userId, now.toLocalDate()))
         .build();
   }

   @Transactional(
      rollbackFor = {Exception.class}
   )
   public PointsBotFoamBag apply(long chatId, long userId, String username, String displayName, int principalPoints) {
      PointsBotFoamBagConfigResponse config = this.getConfig();
      if (!config.getAmountTiers().contains(principalPoints)) {
         throw this.badRequest("这个雾袋积分档位已不可用，请刷新面板后重试");
      } else if (!Boolean.TRUE.equals(config.getAllowUnboundUsers()) && this.telegramBindingManager.findBindingByTelegramId(userId) == null) {
         throw this.badRequest("当前配置要求先绑定 Emby 账号后才能使用雾袋");
      } else {
         this.pointsStore.getOrCreate(chatId, userId, username, displayName);
         PointsBotUser lockedUser = this.requireLockedUser(chatId, userId);
         LocalDateTime now = this.now();
         PointsBotFoamBag active = this.findActiveForUpdate(chatId, userId);
         if (this.isDue(active, now)) {
            this.penalizeLocked(active, lockedUser, now);
            throw this.badRequest(this.restrictionMessage(this.findPenaltyUntil(chatId, userId, now), "使用雾袋"));
         } else {
            LocalDateTime penaltyUntil = this.findPenaltyUntil(chatId, userId, now);
            if (penaltyUntil != null) {
               throw this.badRequest(this.restrictionMessage(penaltyUntil, "使用雾袋"));
            } else if (active != null) {
               throw this.badRequest("请先归还当前雾袋的 " + active.getRepaymentPoints() + " 积分后再继续使用");
            } else if (this.countToday(chatId, userId, now.toLocalDate()) >= config.getDailyLimit()) {
               throw this.badRequest("今天的雾袋次数已用完，明天再来吧");
            } else {
               int repaymentPoints;
               try {
                  repaymentPoints = Math.multiplyExact(principalPoints, config.getRepaymentMultiplier());
               } catch (ArithmeticException var17) {
                  throw this.badRequest("雾袋积分配置超出允许范围");
               }

               PointsBotFoamBag record = new PointsBotFoamBag();
               record.setChatId(chatId);
               record.setUserId(userId);
               record.setUsername(this.normalizeUsername(username, displayName));
               record.setDisplayName(displayName);
               record.setPrincipalPoints(principalPoints);
               record.setRepaymentPoints(repaymentPoints);
               record.setRepaymentMultiplier(config.getRepaymentMultiplier());
               record.setRepaymentHours(config.getRepaymentHours());
               record.setPenaltyDays(config.getPenaltyDays());
               record.setStatus("ACTIVE");
               record.setActiveGuard(1);
               record.setBorrowedAt(now);
               record.setDueAt(now.plusHours((long)config.getRepaymentHours().intValue()));
               record.setWipedPoints(0L);

               try {
                  this.foamBagMapper.insert(record);
               } catch (DuplicateKeyException var16) {
                  throw this.badRequest("你已有尚未归还的雾袋，请刷新面板查看");
               }

               PointsProfile profile = this.pointsStore.toProfile(lockedUser);
               this.pointsStore.addPoints(profile, principalPoints, "foam_bag_grant", String.valueOf(record.getId()));
               return record;
            }
         }
      }
   }

   @Transactional(
      rollbackFor = {Exception.class}
   )
   public PointsBotFoamBag repay(long chatId, long userId) {
      PointsBotUser lockedUser = this.requireLockedUser(chatId, userId);
      LocalDateTime now = this.now();
      PointsBotFoamBag active = this.findActiveForUpdate(chatId, userId);
      if (active == null) {
         throw this.badRequest("当前没有需要归还的雾袋");
      } else if (this.isDue(active, now)) {
         this.penalizeLocked(active, lockedUser, now);
         throw this.badRequest(this.restrictionMessage(active.getPenaltyUntil(), "使用雾袋"));
      } else {
         long balance = lockedUser.getPoints() == null ? 0L : lockedUser.getPoints();
         if (balance < (long)active.getRepaymentPoints().intValue()) {
            throw this.badRequest("当前积分不足，需要一次归还 " + active.getRepaymentPoints() + " 积分，还差 " + ((long)active.getRepaymentPoints().intValue() - balance) + " 积分");
         } else {
            PointsProfile profile = this.pointsStore.toProfile(lockedUser);
            this.pointsStore.addPoints(profile, -active.getRepaymentPoints(), "foam_bag_repay", String.valueOf(active.getId()));
            active.setStatus("REPAID");
            active.setActiveGuard(null);
            active.setRepaidAt(now);
            this.foamBagMapper.updateById(active);
            return active;
         }
      }
   }

   @Transactional(
      rollbackFor = {Exception.class}
   )
   public LocalDateTime checkCheckinRestriction(long chatId, long userId) {
      PointsBotUser lockedUser = this.requireLockedUser(chatId, userId);
      LocalDateTime now = this.now();
      PointsBotFoamBag active = this.findActiveForUpdate(chatId, userId);
      if (this.isDue(active, now)) {
         this.penalizeLocked(active, lockedUser, now);
      }

      return this.findPenaltyUntil(chatId, userId, now);
   }

   @Transactional(
      rollbackFor = {Exception.class}
   )
   public void ensureOutgoingTransferAllowed(long chatId, long userId) {
      PointsBotUser lockedUser = this.requireLockedUser(chatId, userId);
      this.validateOutgoingTransferLocked(chatId, userId, lockedUser, this.now());
   }

   @Transactional(
      rollbackFor = {Exception.class}
   )
   public void ensureIncomingTransferAllowed(long chatId, long userId) {
      PointsBotUser lockedUser = this.requireLockedUser(chatId, userId);
      this.validateIncomingTransferLocked(chatId, userId, lockedUser, this.now());
   }

   @Transactional(
      rollbackFor = {Exception.class}
   )
   public FoamBagTransferResult transfer(
      PointsProfile sender, PointsProfile target, int amount, boolean adminGift, String reasonOut, String reasonIn, boolean allowPartial
   ) {
      if (amount > 0 && sender != null && target != null) {
         if (sender.getChatId() != target.getChatId()) {
            throw this.badRequest("积分只能在同一个群聊账户内转移");
         } else {
            PointsBotUser second = null;
            PointsBotUser first;
            if (adminGift) {
               first = this.requireLockedUser(target.getChatId(), target.getUserId());
            } else if (sender.getUserId() < target.getUserId()) {
               first = this.requireLockedUser(sender.getChatId(), sender.getUserId());
               second = this.requireLockedUser(target.getChatId(), target.getUserId());
            } else {
               first = this.requireLockedUser(target.getChatId(), target.getUserId());
               second = this.requireLockedUser(sender.getChatId(), sender.getUserId());
            }

            PointsBotUser lockedSender = adminGift ? null : (first.getUserId().equals(sender.getUserId()) ? first : second);
            PointsBotUser lockedTarget = first.getUserId().equals(target.getUserId()) ? first : second;
            if (lockedTarget != null && (adminGift || lockedSender != null)) {
               LocalDateTime now = this.now();
               if (!adminGift) {
                  this.validateOutgoingTransferLocked(sender.getChatId(), sender.getUserId(), lockedSender, now);
               }

               this.validateIncomingTransferLocked(target.getChatId(), target.getUserId(), lockedTarget, now);
               int transferred = amount;
               long senderBalance = lockedSender != null && lockedSender.getPoints() != null ? lockedSender.getPoints() : 0L;
               if (!adminGift) {
                  if (allowPartial) {
                     transferred = (int)Math.min((long)amount, Math.max(0L, senderBalance));
                  } else if (senderBalance < (long)amount) {
                     throw this.badRequest("积分不足，无法转账");
                  }
               }

               if (transferred <= 0) {
                  return new FoamBagTransferResult(senderBalance, lockedTarget.getPoints() == null ? 0L : lockedTarget.getPoints(), 0);
               } else {
                  if (!adminGift) {
                     PointsProfile lockedSenderProfile = this.pointsStore.toProfile(lockedSender);
                     this.pointsStore.addPoints(lockedSenderProfile, -transferred, reasonOut, String.valueOf(target.getUserId()));
                     senderBalance = lockedSenderProfile.getPoints();
                     this.copyBalance(sender, lockedSenderProfile);
                  }

                  PointsProfile lockedTargetProfile = this.pointsStore.toProfile(lockedTarget);
                  this.pointsStore.addPoints(lockedTargetProfile, transferred, reasonIn, String.valueOf(sender.getUserId()));
                  this.copyBalance(target, lockedTargetProfile);
                  return new FoamBagTransferResult(senderBalance, lockedTargetProfile.getPoints(), transferred);
               }
            } else {
               throw this.badRequest("积分账户不存在");
            }
         }
      } else {
         throw this.badRequest("积分转移参数无效");
      }
   }

   private void validateOutgoingTransferLocked(long chatId, long userId, PointsBotUser lockedUser, LocalDateTime now) {
      PointsBotFoamBag active = this.findActiveForUpdate(chatId, userId);
      if (this.isDue(active, now)) {
         this.penalizeLocked(active, lockedUser, now);
         active = null;
      }

      if (active != null) {
         throw this.badRequest("当前雾袋尚未归还，归还前不能转赠积分");
      }
   }

   private void validateIncomingTransferLocked(long chatId, long userId, PointsBotUser lockedUser, LocalDateTime now) {
      PointsBotFoamBag targetActive = this.findActiveForUpdate(chatId, userId);
      if (this.isDue(targetActive, now)) {
         this.penalizeLocked(targetActive, lockedUser, now);
      }

      LocalDateTime targetPenaltyUntil = this.findPenaltyUntil(chatId, userId, now);
      if (targetPenaltyUntil != null) {
         throw this.badRequest(this.restrictionMessage(targetPenaltyUntil, "接收他人转赠积分"));
      }
   }

   @Transactional(
      rollbackFor = {Exception.class}
   )
   public List<FoamBagTransferResult> transferBothWays(
      PointsProfile first,
      PointsProfile second,
      int firstAmount,
      int secondAmount,
      String firstReasonOut,
      String firstReasonIn,
      String secondReasonOut,
      String secondReasonIn
   ) {
      FoamBagTransferResult firstResult = this.transfer(first, second, firstAmount, false, firstReasonOut, firstReasonIn, true);
      FoamBagTransferResult secondResult = this.transfer(second, first, secondAmount, false, secondReasonOut, secondReasonIn, true);
      return List.of(firstResult, secondResult);
   }

   public List<Long> findDueIds(int limit) {
      return ChainWrappers.lambdaQueryChain(this.foamBagMapper)
         .select(PointsBotFoamBag::getId)
         .eq(PointsBotFoamBag::getStatus, "ACTIVE")
         .le(PointsBotFoamBag::getDueAt, this.now())
         .orderByAsc(PointsBotFoamBag::getDueAt)
         .orderByAsc(PointsBotFoamBag::getId)
         .last("limit " + Math.max(1, Math.min(limit, 500)))
         .list()
         .stream()
         .map(PointsBotFoamBag::getId)
         .toList();
   }

   @Transactional(
      rollbackFor = {Exception.class}
   )
   public PointsBotFoamBag penalizeDue(long recordId) {
      PointsBotFoamBag snapshot = this.foamBagMapper.selectById(Long.valueOf(recordId));
      if (snapshot != null && "ACTIVE".equals(snapshot.getStatus())) {
         PointsBotUser lockedUser = this.requireLockedUser(snapshot.getChatId(), snapshot.getUserId());
         PointsBotFoamBag lockedRecord = ChainWrappers.lambdaQueryChain(this.foamBagMapper)
            .eq(PointsBotFoamBag::getId, Long.valueOf(recordId))
            .last("limit 1 for update")
            .one();
         LocalDateTime now = this.now();
         if (!this.isDue(lockedRecord, now)) {
            return null;
         } else {
            this.penalizeLocked(lockedRecord, lockedUser, now);
            return lockedRecord;
         }
      } else {
         return null;
      }
   }

   public Page<PointsBotFoamBagResponse> selectAdmin(MybatisPlusPage<PointsBotFoamBagRequest> request) {
      return this.selectRecords(request, null);
   }

   public PointsBotFoamBagMyRecordsResponse selectMine(long webUserId, MybatisPlusPage<PointsBotFoamBagRequest> request) {
      UserOauthBinding binding = this.telegramBindingManager.findBindingByUserId(webUserId);
      if (binding != null && StringUtils.hasText(binding.getProviderUserId())) {
         long telegramUserId;
         try {
            telegramUserId = Long.parseLong(binding.getProviderUserId());
         } catch (NumberFormatException var9) {
            Page<PointsBotFoamBagResponse> empty = new Page<>(request.getCurrent(), request.getSize(), 0L);
            empty.setRecords(List.of());
            return PointsBotFoamBagMyRecordsResponse.builder().telegramBound(false).page(empty).build();
         }

         return PointsBotFoamBagMyRecordsResponse.builder().telegramBound(true).page(this.selectRecords(request, telegramUserId)).build();
      } else {
         Page<PointsBotFoamBagResponse> empty = new Page<>(request.getCurrent(), request.getSize(), 0L);
         empty.setRecords(List.of());
         return PointsBotFoamBagMyRecordsResponse.builder().telegramBound(false).page(empty).build();
      }
   }

   public String restrictionMessage(LocalDateTime until, String action) {
      return until == null ? "当前无法" + action : "雾袋逾期限制中，暂时无法" + action + "，还需等待 " + this.formatRemaining(until);
   }

   public String formatRemaining(LocalDateTime until) {
      long seconds = Math.max(0L, Duration.between(this.now(), until).getSeconds());
      return formatRemainingSeconds(seconds);
   }

   static String formatRemainingSeconds(long remainingSeconds) {
      long seconds = Math.max(0L, remainingSeconds);
      long days = seconds / 86400L;
      long hours = seconds % 86400L / 3600L;
      long minutes = seconds % 3600L / 60L;
      long secondsPart = seconds % 60L;
      String time = hours + "小时" + minutes + "分" + secondsPart + "秒";
      return days > 0L ? days + "天" + time : time;
   }

   private Page<PointsBotFoamBagResponse> selectRecords(MybatisPlusPage<PointsBotFoamBagRequest> request, Long forcedTelegramUserId) {
      PointsBotFoamBagRequest filter = request.getObject();
      LambdaQueryWrapper<PointsBotFoamBag> wrapper = new LambdaQueryWrapper<>();
      if (forcedTelegramUserId != null) {
         wrapper.eq(PointsBotFoamBag::getUserId, forcedTelegramUserId);
      } else if (filter.getUserId() != null) {
         wrapper.eq(PointsBotFoamBag::getUserId, filter.getUserId());
      }

      if (filter.getChatId() != null) {
         wrapper.eq(PointsBotFoamBag::getChatId, filter.getChatId());
      }

      if (StringUtils.hasText(filter.getStatus())) {
         wrapper.eq(PointsBotFoamBag::getStatus, filter.getStatus().trim().toUpperCase(Locale.ROOT));
      }

      if (StringUtils.hasText(filter.getKeyword())) {
         String keyword = filter.getKeyword().trim().replaceFirst("^@", "");
         wrapper.and(query -> query.like(PointsBotFoamBag::getUsername, keyword).or().like(PointsBotFoamBag::getDisplayName, keyword));
      } else if (StringUtils.hasText(filter.getUsername())) {
         wrapper.like(PointsBotFoamBag::getUsername, filter.getUsername().trim().replaceFirst("^@", ""));
      }

      if (StringUtils.hasText(filter.getDisplayName())) {
         wrapper.like(PointsBotFoamBag::getDisplayName, filter.getDisplayName().trim());
      }

      wrapper.orderByDesc(PointsBotFoamBag::getId);
      Page<PointsBotFoamBag> source = this.foamBagMapper.selectPage(new Page<>(request.getCurrent(), request.getSize()), wrapper);
      Page<PointsBotFoamBagResponse> result = new Page<>(source.getCurrent(), source.getSize(), source.getTotal());
      result.setRecords(source.getRecords().stream().map(this::toResponse).toList());
      return result;
   }

   private PointsBotFoamBagResponse toResponse(PointsBotFoamBag source) {
      PointsBotFoamBagResponse response = new PointsBotFoamBagResponse();
      response.setId(source.getId());
      response.setChatId(source.getChatId());
      response.setUserId(source.getUserId());
      response.setUsername(source.getUsername());
      response.setDisplayName(source.getDisplayName());
      response.setPrincipalPoints(source.getPrincipalPoints());
      response.setRepaymentPoints(source.getRepaymentPoints());
      response.setRepaymentMultiplier(source.getRepaymentMultiplier());
      response.setRepaymentHours(source.getRepaymentHours());
      response.setPenaltyDays(source.getPenaltyDays());
      response.setStatus(source.getStatus());
      response.setBorrowedAt(source.getBorrowedAt());
      response.setDueAt(source.getDueAt());
      response.setRepaidAt(source.getRepaidAt());
      response.setPenalizedAt(source.getPenalizedAt());
      response.setPenaltyUntil(source.getPenaltyUntil());
      response.setWipedPoints(source.getWipedPoints());
      response.setCreateDatetime(source.getCreateDatetime());
      response.setUpdateDatetime(source.getUpdateDatetime());
      UserOauthBinding binding = this.telegramBindingManager.findBindingByTelegramId(source.getUserId());
      response.setTelegramBound(binding != null);
      if (binding != null) {
         EmbyUser user = this.telegramBindingManager.findUser(binding.getUserId());
         response.setEmbyUserName(user == null ? null : user.getEmbyUserName());
      }

      return response;
   }

   private PointsBotFoamBagConfigResponse toConfigResponse(PointsBotFoamBagConfig stored) {
      if (stored == null) {
         return PointsBotFoamBagConfigResponse.builder()
            .amountTiers(DEFAULT_TIERS)
            .dailyLimit(3)
            .repaymentMultiplier(2)
            .repaymentHours(24)
            .penaltyDays(7)
            .allowUnboundUsers(true)
            .build();
      } else {
         List<Integer> tiers;
         try {
            tiers = this.normalizeTiers(JSON.parseArray(stored.getAmountTiersJson(), Integer.class));
         } catch (RuntimeException var4) {
            tiers = DEFAULT_TIERS;
         }

         return PointsBotFoamBagConfigResponse.builder()
            .amountTiers(tiers)
            .dailyLimit(this.positiveOrDefault(stored.getDailyLimit(), 3))
            .repaymentMultiplier(this.positiveOrDefault(stored.getRepaymentMultiplier(), 2))
            .repaymentHours(this.positiveOrDefault(stored.getRepaymentHours(), 24))
            .penaltyDays(this.positiveOrDefault(stored.getPenaltyDays(), 7))
            .allowUnboundUsers(!Integer.valueOf(0).equals(stored.getAllowUnboundUsers()))
            .build();
      }
   }

   private List<Integer> normalizeTiers(List<Integer> source) {
      if (source != null && !source.isEmpty()) {
         LinkedHashSet<Integer> unique = new LinkedHashSet<>();
         source.stream().filter(value -> value != null && value > 0 && value <= 1000000).sorted().forEach(unique::add);
         if (unique.isEmpty()) {
            throw this.badRequest("至少配置一个有效的雾袋积分档位");
         } else {
            return List.copyOf(unique);
         }
      } else {
         return DEFAULT_TIERS;
      }
   }

   private int positiveOrDefault(Integer value, int fallback) {
      return value != null && value > 0 ? value : fallback;
   }

   private PointsBotUser requireLockedUser(long chatId, long userId) {
      PointsBotUser user = this.pointsBotUserMapper.selectForUpdate(chatId, userId);
      if (user == null) {
         throw this.badRequest("积分账户不存在，请先在积分群发言或查询积分");
      } else {
         return user;
      }
   }

   private PointsBotFoamBag findActiveForUpdate(long chatId, long userId) {
      return ChainWrappers.lambdaQueryChain(this.foamBagMapper)
         .eq(PointsBotFoamBag::getChatId, Long.valueOf(chatId))
         .eq(PointsBotFoamBag::getUserId, Long.valueOf(userId))
         .eq(PointsBotFoamBag::getStatus, "ACTIVE")
         .last("limit 1 for update")
         .one();
   }

   private LocalDateTime findPenaltyUntil(long chatId, long userId, LocalDateTime now) {
      PointsBotFoamBag record = ChainWrappers.lambdaQueryChain(this.foamBagMapper)
         .eq(PointsBotFoamBag::getChatId, Long.valueOf(chatId))
         .eq(PointsBotFoamBag::getUserId, Long.valueOf(userId))
         .eq(PointsBotFoamBag::getStatus, "PENALIZED")
         .gt(PointsBotFoamBag::getPenaltyUntil, now)
         .orderByDesc(PointsBotFoamBag::getPenaltyUntil)
         .last("limit 1")
         .one();
      return record == null ? null : record.getPenaltyUntil();
   }

   private int countToday(long chatId, long userId, LocalDate day) {
      LocalDateTime start = day.atStartOfDay();
      LocalDateTime end = day.atTime(LocalTime.MAX).plusNanos(1L);
      Long count = ChainWrappers.lambdaQueryChain(this.foamBagMapper)
         .eq(PointsBotFoamBag::getChatId, Long.valueOf(chatId))
         .eq(PointsBotFoamBag::getUserId, Long.valueOf(userId))
         .ge(PointsBotFoamBag::getBorrowedAt, start)
         .lt(PointsBotFoamBag::getBorrowedAt, end)
         .count();
      return count == null ? 0 : count.intValue();
   }

   private void penalizeLocked(PointsBotFoamBag active, PointsBotUser lockedUser, LocalDateTime now) {
      if (active != null && "ACTIVE".equals(active.getStatus())) {
         long balance = Math.max(0L, lockedUser.getPoints() == null ? 0L : lockedUser.getPoints());
         PointsProfile profile = this.pointsStore.toProfile(lockedUser);
         long remaining = balance;

         while (remaining > 0L) {
            int chunk = (int)Math.min(remaining, 2147483647L);
            this.pointsStore.addPoints(profile, -chunk, "foam_bag_overdue_wipe", String.valueOf(active.getId()));
            remaining -= (long)chunk;
         }

         lockedUser.setPoints(profile.getPoints());
         active.setStatus("PENALIZED");
         active.setActiveGuard(null);
         active.setPenalizedAt(now);
         active.setPenaltyUntil(now.plusDays((long)active.getPenaltyDays().intValue()));
         active.setWipedPoints(balance);
         this.foamBagMapper.updateById(active);
      }
   }

   private boolean isDue(PointsBotFoamBag record, LocalDateTime now) {
      return record != null && "ACTIVE".equals(record.getStatus()) && record.getDueAt() != null && !now.isBefore(record.getDueAt());
   }

   private LocalDateTime now() {
      return LocalDateTime.now(BUSINESS_ZONE);
   }

   private String normalizeUsername(String username, String displayName) {
      return StringUtils.hasText(username) ? username : displayName;
   }

   private void copyBalance(PointsProfile target, PointsProfile source) {
      target.setPoints(source.getPoints());
      target.setLevelId(source.getLevelId());
      target.setLevelName(source.getLevelName());
   }

   private BizException badRequest(String message) {
      return new BizException(ResponseStatusEnum.BAD_REQUEST.getCode(), message);
   }

   @Generated
   public PointsBotFoamBagService(
      final PointsBotFoamBagMapper foamBagMapper,
      final PointsBotFoamBagConfigMapper configMapper,
      final PointsBotUserMapper pointsBotUserMapper,
      final PointsStore pointsStore,
      final TelegramBindingManager telegramBindingManager
   ) {
      this.foamBagMapper = foamBagMapper;
      this.configMapper = configMapper;
      this.pointsBotUserMapper = pointsBotUserMapper;
      this.pointsStore = pointsStore;
      this.telegramBindingManager = telegramBindingManager;
   }
}
