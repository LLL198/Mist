package com.una.embyhub.pointsbot.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.una.embyhub.config.common.enums.PointsBotRedeemTypeEnum;
import com.una.embyhub.config.common.enums.RegisterChannelEnum;
import com.una.embyhub.config.common.enums.ResponseStatusEnum;
import com.una.embyhub.config.common.exception.BizException;
import com.una.embyhub.config.common.utils.RedisLockUtils;
import com.una.embyhub.config.mybatis.MybatisPlusPage;
import com.una.embyhub.mapper.PointsBotLedgerMapper;
import com.una.embyhub.mapper.PointsBotRedeemRecordMapper;
import com.una.embyhub.mapper.PointsBotUserMapper;
import com.una.embyhub.model.dto.request.embyuser.EmbyUserSave;
import com.una.embyhub.model.dto.request.pointsbot.PointsBotPortalLedgerRequest;
import com.una.embyhub.model.dto.request.pointsbot.PointsBotPortalRedeemRecordRequest;
import com.una.embyhub.model.dto.request.pointsbot.PointsBotPortalRedeemRequest;
import com.una.embyhub.model.dto.response.embyuser.EmbyUserCustomResponse;
import com.una.embyhub.model.dto.response.embyuser.InsertUserResponse;
import com.una.embyhub.model.dto.response.pointsbot.PointsBotPortalAccountResponse;
import com.una.embyhub.model.dto.response.pointsbot.PointsBotPortalLedgerPageResponse;
import com.una.embyhub.model.dto.response.pointsbot.PointsBotPortalLedgerResponse;
import com.una.embyhub.model.dto.response.pointsbot.PointsBotPortalRedeemCatalogResponse;
import com.una.embyhub.model.dto.response.pointsbot.PointsBotPortalRedeemOptionResponse;
import com.una.embyhub.model.dto.response.pointsbot.PointsBotPortalRedeemRecordPageResponse;
import com.una.embyhub.model.dto.response.pointsbot.PointsBotPortalRedeemRecordResponse;
import com.una.embyhub.model.dto.response.pointsbot.PointsBotPortalRedeemResponse;
import com.una.embyhub.model.entity.BaseEntity;
import com.una.embyhub.model.entity.EmbyInfo;
import com.una.embyhub.model.entity.EmbyUser;
import com.una.embyhub.model.entity.PointsBotLedger;
import com.una.embyhub.model.entity.PointsBotRedeemConfig;
import com.una.embyhub.model.entity.PointsBotRedeemRecord;
import com.una.embyhub.model.entity.PointsBotUser;
import com.una.embyhub.model.entity.UserOauthBinding;
import com.una.embyhub.pointsbot.model.PointsProfile;
import com.una.embyhub.service.EmbyInfoService;
import com.una.embyhub.service.EmbyUserService;
import com.una.embyhub.service.PointsBotLedgerManageService;
import com.una.embyhub.service.PointsBotRedeemConfigService;
import com.una.embyhub.service.TelegramBindingManager;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import lombok.Generated;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.util.StringUtils;

@Service
public class PointsBotPortalService {
   @Generated
   private static final Logger log = LoggerFactory.getLogger(PointsBotPortalService.class);
   private static final long REDEEM_LOCK_TTL_SECONDS = 180L;
   private static final String REDEEM_LOCK_PREFIX = "foam:points-bot:web-redeem:";
   private final TelegramBindingManager telegramBindingManager;
   private final PointsBotConfigService pointsBotConfigService;
   private final PointsBotUserMapper pointsBotUserMapper;
   private final PointsBotLedgerMapper pointsBotLedgerMapper;
   private final PointsBotRedeemRecordMapper redeemRecordMapper;
   private final PointsBotRedeemConfigService redeemConfigService;
   private final PointsBotLedgerManageService ledgerManageService;
   private final PointsStore pointsStore;
   private final EmbyInfoService embyInfoService;
   private final EmbyUserService embyUserService;
   private final RedisLockUtils redisLockUtils;
   private final TransactionTemplate transactionTemplate;

   public PointsBotPortalService(
      TelegramBindingManager telegramBindingManager,
      PointsBotConfigService pointsBotConfigService,
      PointsBotUserMapper pointsBotUserMapper,
      PointsBotLedgerMapper pointsBotLedgerMapper,
      PointsBotRedeemRecordMapper redeemRecordMapper,
      PointsBotRedeemConfigService redeemConfigService,
      PointsBotLedgerManageService ledgerManageService,
      PointsStore pointsStore,
      EmbyInfoService embyInfoService,
      EmbyUserService embyUserService,
      RedisLockUtils redisLockUtils,
      PlatformTransactionManager transactionManager
   ) {
      this.telegramBindingManager = telegramBindingManager;
      this.pointsBotConfigService = pointsBotConfigService;
      this.pointsBotUserMapper = pointsBotUserMapper;
      this.pointsBotLedgerMapper = pointsBotLedgerMapper;
      this.redeemRecordMapper = redeemRecordMapper;
      this.redeemConfigService = redeemConfigService;
      this.ledgerManageService = ledgerManageService;
      this.pointsStore = pointsStore;
      this.embyInfoService = embyInfoService;
      this.embyUserService = embyUserService;
      this.redisLockUtils = redisLockUtils;
      this.transactionTemplate = new TransactionTemplate(transactionManager);
   }

   public PointsBotPortalLedgerPageResponse selectMyLedger(long webUserId, MybatisPlusPage<PointsBotPortalLedgerRequest> request) {
      PointsBotPortalService.PortalIdentity identity = this.findIdentity(webUserId);
      long current = this.normalizeCurrent(request.getCurrent());
      long size = this.normalizeSize(request.getSize());
      if (identity == null) {
         return PointsBotPortalLedgerPageResponse.builder().account(this.unboundAccount()).page(this.emptyPage(current, size)).build();
      } else {
         PointsBotPortalLedgerRequest filter = request.getObject();
         LambdaQueryWrapper<PointsBotLedger> wrapper = new LambdaQueryWrapper<PointsBotLedger>()
            .eq(PointsBotLedger::getUserId, Long.valueOf(identity.telegramUserId()))
            .eq(identity.chatId() != null, PointsBotLedger::getChatId, identity.chatId())
            .eq(BaseEntity::getDelFlag, Integer.valueOf(0));
         if (StringUtils.hasText(filter.getReason())) {
            wrapper.eq(PointsBotLedger::getReason, filter.getReason().trim());
         }

         String direction = this.normalize(filter.getDirection());
         if ("INCOME".equals(direction)) {
            wrapper.gt(PointsBotLedger::getDelta, Integer.valueOf(0));
         } else if ("EXPENSE".equals(direction)) {
            wrapper.lt(PointsBotLedger::getDelta, Integer.valueOf(0));
         }

         wrapper.orderByDesc(PointsBotLedger::getId);
         Page<PointsBotLedger> source = this.pointsBotLedgerMapper.selectPage(new Page<>(current, size), wrapper);
         Map<Long, String> serverNames = this.loadServerNames(
            source.getRecords().stream().map(PointsBotLedger::getServerId).filter(Objects::nonNull).collect(Collectors.toSet())
         );
         Page<PointsBotPortalLedgerResponse> result = new Page<>(source.getCurrent(), source.getSize(), source.getTotal());
         result.setRecords(source.getRecords().stream().map(row -> this.toLedgerResponse(row, serverNames.get(row.getServerId()))).toList());
         return PointsBotPortalLedgerPageResponse.builder().account(this.toAccount(identity)).page(result).build();
      }
   }

   public PointsBotPortalRedeemCatalogResponse getRedeemCatalog(long webUserId) {
      PointsBotPortalService.PortalIdentity identity = this.findIdentity(webUserId);
      if (identity == null) {
         return PointsBotPortalRedeemCatalogResponse.builder().account(this.unboundAccount()).options(List.of()).build();
      } else {
         PointsBotPortalAccountResponse account = this.toAccount(identity);
         if (identity.chatId() == null) {
            return PointsBotPortalRedeemCatalogResponse.builder().account(account).options(List.of()).build();
         } else {
            List<PointsBotRedeemConfig> configs = this.redeemConfigService
               .lambdaQuery()
               .eq(PointsBotRedeemConfig::getEnabled, Integer.valueOf(1))
               .eq(BaseEntity::getDelFlag, Integer.valueOf(0))
               .orderByDesc(PointsBotRedeemConfig::getSort)
               .orderByDesc(PointsBotRedeemConfig::getId)
               .list();
            Map<Long, EmbyInfo> servers = this.loadServers(
               configs.stream().map(PointsBotRedeemConfig::getEmbyInfoId).filter(Objects::nonNull).collect(Collectors.toSet())
            );
            List<PointsBotPortalRedeemOptionResponse> options = configs.stream()
               .filter(this::isUsableConfigShape)
               .map(config -> this.toRedeemOption(identity, account, config, servers.get(config.getEmbyInfoId())))
               .toList();
            return PointsBotPortalRedeemCatalogResponse.builder().account(account).options(options).build();
         }
      }
   }

   public PointsBotPortalRedeemRecordPageResponse selectMyRedeemRecords(long webUserId, MybatisPlusPage<PointsBotPortalRedeemRecordRequest> request) {
      PointsBotPortalService.PortalIdentity identity = this.findIdentity(webUserId);
      long current = this.normalizeCurrent(request.getCurrent());
      long size = this.normalizeSize(request.getSize());
      if (identity == null) {
         return PointsBotPortalRedeemRecordPageResponse.builder().account(this.unboundAccount()).page(this.emptyPage(current, size)).build();
      } else {
         PointsBotPortalRedeemRecordRequest filter = request.getObject();
         LambdaQueryWrapper<PointsBotRedeemRecord> wrapper = new LambdaQueryWrapper<PointsBotRedeemRecord>()
            .eq(PointsBotRedeemRecord::getWebUserId, Long.valueOf(webUserId))
            .eq(BaseEntity::getDelFlag, Integer.valueOf(0));
         if (StringUtils.hasText(filter.getRedeemType())) {
            wrapper.eq(PointsBotRedeemRecord::getRedeemType, this.normalize(filter.getRedeemType()));
         }

         if (StringUtils.hasText(filter.getStatus())) {
            wrapper.eq(PointsBotRedeemRecord::getStatus, this.normalize(filter.getStatus()));
         }

         wrapper.orderByDesc(PointsBotRedeemRecord::getId);
         Page<PointsBotRedeemRecord> source = this.redeemRecordMapper.selectPage(new Page<>(current, size), wrapper);
         Page<PointsBotPortalRedeemRecordResponse> result = new Page<>(source.getCurrent(), source.getSize(), source.getTotal());
         result.setRecords(source.getRecords().stream().map(this::toRedeemRecordResponse).toList());
         return PointsBotPortalRedeemRecordPageResponse.builder().account(this.toAccount(identity)).page(result).build();
      }
   }

   public PointsBotPortalRedeemResponse redeem(long webUserId, PointsBotPortalRedeemRequest request) {
      PointsBotPortalService.PortalIdentity identity = this.requireRedeemIdentity(webUserId);
      String lockKey = "foam:points-bot:web-redeem:" + identity.chatId() + ":" + identity.telegramUserId();

      String lockToken;
      try {
         lockToken = this.redisLockUtils.tryLock(lockKey, 180L);
      } catch (Exception var16) {
         log.error("Web积分兑换锁获取失败: webUserId={}", webUserId, var16);
         throw this.badRequest("兑换服务暂时不可用，请稍后重试");
      }

      if (!StringUtils.hasText(lockToken)) {
         throw this.badRequest("你有一笔兑换正在处理中，请勿重复提交");
      } else {
         try {
            PointsBotRedeemRecord existing = this.findByRequestId(webUserId, request.getRequestId());
            if (existing != null) {
               return this.toExistingResponse(existing, identity);
            } else {
               PointsBotPortalService.PreparedRedeem prepared;
               try {
                  prepared = this.transactionTemplate.execute(status -> this.prepareRedeem(identity, request));
               } catch (DuplicateKeyException var17) {
                  PointsBotRedeemRecord duplicate = this.findByRequestId(webUserId, request.getRequestId());
                  if (duplicate == null) {
                     throw this.badRequest("你有一笔兑换正在处理中，请稍后查看兑换记录");
                  }

                  return this.toExistingResponse(duplicate, identity);
               }

               if (prepared == null) {
                  throw this.badRequest("兑换准备失败，请稍后重试");
               } else {
                  return this.executeRedeem(identity, request, prepared);
               }
            }
         } finally {
            this.redisLockUtils.unlock(lockKey, lockToken);
         }
      }
   }

   private PointsBotPortalService.PreparedRedeem prepareRedeem(PointsBotPortalService.PortalIdentity identity, PointsBotPortalRedeemRequest request) {
      PointsBotRedeemConfig config = this.redeemConfigService
         .lambdaQuery()
         .eq(PointsBotRedeemConfig::getId, request.getConfigId())
         .eq(PointsBotRedeemConfig::getEnabled, Integer.valueOf(1))
         .eq(BaseEntity::getDelFlag, Integer.valueOf(0))
         .last("limit 1 for update")
         .one();
      if (!this.isUsableConfigShape(config)) {
         throw this.badRequest("兑换项目不存在、已停用或配置异常");
      } else {
         EmbyInfo server = this.requireEnabledServer(config.getEmbyInfoId());
         EmbyUser targetUser = null;
         String targetUserName;
         if (PointsBotRedeemTypeEnum.CREATE_ACCOUNT.matches(config.getRedeemType())) {
            targetUserName = this.trimToNull(request.getEmbyUserName());
            if (!StringUtils.hasText(targetUserName)) {
               throw this.badRequest("请输入要创建的 Emby 用户名");
            }

            this.embyUserService.validatePointsRedeemCreate(targetUserName, this.trimToNull(request.getPassword()), server.getId());
         } else {
            if (StringUtils.hasText(request.getEmbyUserName()) || StringUtils.hasText(request.getPassword())) {
               throw this.badRequest("续费账号由当前登录身份确定，不能指定其他用户名或密码");
            }

            targetUser = this.requireRenewTarget(identity.webUser(), config.getEmbyInfoId());
            targetUserName = targetUser.getEmbyUserName();
         }

         PointsBotUser lockedUser = this.pointsBotUserMapper.selectForUpdate(identity.chatId(), identity.telegramUserId());
         if (lockedUser == null) {
            throw this.badRequest("积分账户不存在，请先在积分群发言、签到或查询积分");
         } else {
            long balance = Math.max(0L, lockedUser.getPoints() == null ? 0L : lockedUser.getPoints());
            if (balance < (long)config.getRequiredPoints().intValue()) {
               throw this.badRequest("积分不足，当前 " + balance + " 积分，需要 " + config.getRequiredPoints() + " 积分");
            } else {
               PointsBotRedeemRecord record = new PointsBotRedeemRecord();
               record.setRequestId(request.getRequestId());
               record.setActiveGuard(1);
               record.setWebUserId(identity.webUser().getId());
               record.setChatId(identity.chatId());
               record.setTelegramUserId(identity.telegramUserId());
               record.setConfigId(config.getId());
               record.setConfigName(config.getConfigName().trim());
               record.setRedeemType(config.getRedeemType().trim().toUpperCase(Locale.ROOT));
               record.setRedeemDays(config.getRedeemDays());
               record.setRequiredPoints(config.getRequiredPoints());
               record.setServerId(server.getId());
               record.setServerName(server.getServerName());
               record.setTargetUserId(targetUser == null ? null : targetUser.getId());
               record.setTargetUserName(targetUserName);
               record.setStatus("PROCESSING");
               record.setResultMessage("兑换处理中");
               this.redeemRecordMapper.insert(record);
               PointsProfile profile = this.pointsStore.toProfile(lockedUser);
               int applied = this.pointsStore.addPoints(profile, -config.getRequiredPoints(), "web_redeem", String.valueOf(record.getId()), server.getId());
               if (applied != -config.getRequiredPoints()) {
                  throw this.badRequest("积分扣除失败，请稍后重试");
               } else {
                  return new PointsBotPortalService.PreparedRedeem(record, config, server, targetUser);
               }
            }
         }
      }
   }

   private PointsBotPortalRedeemResponse executeRedeem(
      PointsBotPortalService.PortalIdentity identity, PointsBotPortalRedeemRequest request, PointsBotPortalService.PreparedRedeem prepared
   ) {
      PointsBotRedeemRecord record = prepared.record();

      PointsBotPortalService.ExternalRedeemResult externalResult;
      try {
         String password = null;
         Long targetUserId;
         String targetUserName;
         Date expirationDate;
         if (PointsBotRedeemTypeEnum.CREATE_ACCOUNT.matches(record.getRedeemType())) {
            EmbyUserSave save = new EmbyUserSave();
            save.setEmbyUserName(record.getTargetUserName());
            save.setEmbyUserPassword(this.trimToNull(request.getPassword()));
            save.setDay(record.getRedeemDays());
            save.setRemarks(record.getConfigName() + "-Web积分兑换");
            save.setEmbyInfoId(record.getServerId());
            save.setRegisterChannel(RegisterChannelEnum.POINTS_REDEEM.getCode());
            save.setRegisterChannelDetail("Web积分兑换");
            InsertUserResponse created = this.embyUserService.insertUser(save);
            targetUserId = created.getId();
            targetUserName = created.getEmbyUserName();
            password = created.getEmbyUserPassword();
            expirationDate = created.getExpirationDate();
         } else {
            EmbyUserCustomResponse renewed = this.embyUserService.renewByPoints(prepared.targetUser().getId(), record.getRedeemDays());
            targetUserId = prepared.targetUser().getId();
            targetUserName = prepared.targetUser().getEmbyUserName();
            expirationDate = renewed.getExpirationDate();
         }

         externalResult = new PointsBotPortalService.ExternalRedeemResult(targetUserId, targetUserName, password, expirationDate);
      } catch (Exception var13) {
         log.error("Web积分兑换执行失败: recordId={}, configId={}, webUserId={}", record.getId(), record.getConfigId(), identity.webUser().getId(), var13);
         if (PointsBotRedeemTypeEnum.CREATE_ACCOUNT.matches(record.getRedeemType())) {
            String message = "账号创建结果待核查，请勿重复提交并联系管理员核查记录 " + record.getId();
            this.markProcessingNeedsReview(record.getId(), message);
            record.setResultMessage(message);
            return this.toExistingResponse(record, identity);
         }

         this.refund(record.getId(), identity);
         throw this.badRequest("兑换失败，积分已退回，请稍后重试或联系管理员");
      }

      try {
         this.finishSuccess(record.getId(), externalResult.targetUserId(), externalResult.targetUserName(), externalResult.expirationDate());
      } catch (Exception var12) {
         log.error("Web积分兑换权益已生效但记录完成状态写入失败: recordId={}, configId={}, webUserId={}", record.getId(), record.getConfigId(), identity.webUser().getId(), var12);
         throw this.badRequest("兑换权益已经生效，记录状态同步中，请勿重复提交并联系管理员核查记录 " + record.getId());
      }

      long remaining = this.currentBalance(identity);
      return PointsBotPortalRedeemResponse.builder()
         .recordId(record.getId())
         .status("SUCCESS")
         .configName(record.getConfigName())
         .redeemType(record.getRedeemType())
         .pointsSpent(record.getRequiredPoints())
         .remainingPoints(remaining)
         .targetUserName(externalResult.targetUserName())
         .password(externalResult.password())
         .expirationDate(externalResult.expirationDate())
         .message("兑换成功")
         .build();
   }

   private void finishSuccess(Long recordId, Long targetUserId, String targetUserName, Date expirationDate) {
      Boolean updated = this.transactionTemplate
         .execute(
            status -> this.redeemRecordMapper
                     .update(
                        null,
                        new LambdaUpdateWrapper<PointsBotRedeemRecord>()
                           .set(PointsBotRedeemRecord::getStatus, "SUCCESS")
                           .set(PointsBotRedeemRecord::getActiveGuard, null)
                           .set(PointsBotRedeemRecord::getTargetUserId, targetUserId)
                           .set(PointsBotRedeemRecord::getTargetUserName, targetUserName)
                           .set(PointsBotRedeemRecord::getExpirationDate, expirationDate)
                           .set(PointsBotRedeemRecord::getResultMessage, "兑换成功")
                           .set(PointsBotRedeemRecord::getFinishedAt, new Date())
                           .eq(PointsBotRedeemRecord::getId, recordId)
                           .eq(PointsBotRedeemRecord::getStatus, "PROCESSING")
                     )
                  == 1
         );
      if (!Boolean.TRUE.equals(updated)) {
         throw new IllegalStateException("兑换完成状态写入失败");
      }
   }

   private void markProcessingNeedsReview(Long recordId, String message) {
      try {
         this.transactionTemplate
            .executeWithoutResult(
               status -> this.redeemRecordMapper
                     .update(
                        null,
                        new LambdaUpdateWrapper<PointsBotRedeemRecord>()
                           .set(PointsBotRedeemRecord::getResultMessage, message)
                           .eq(PointsBotRedeemRecord::getId, recordId)
                           .eq(PointsBotRedeemRecord::getStatus, "PROCESSING")
                     )
            );
      } catch (Exception var4) {
         log.error("Web积分兑换待核查状态写入失败: recordId={}", recordId, var4);
      }
   }

   private void refund(Long recordId, PointsBotPortalService.PortalIdentity identity) {
      try {
         this.transactionTemplate
            .executeWithoutResult(
               status -> {
                  PointsBotRedeemRecord record = this.redeemRecordMapper
                     .selectOne(
                        new LambdaQueryWrapper<PointsBotRedeemRecord>()
                           .eq(PointsBotRedeemRecord::getId, recordId)
                           .eq(PointsBotRedeemRecord::getStatus, "PROCESSING")
                           .last("limit 1 for update")
                     );
                  if (record != null) {
                     PointsBotUser locked = this.pointsBotUserMapper.selectForUpdate(identity.chatId(), identity.telegramUserId());
                     if (locked == null) {
                        throw new IllegalStateException("退款时积分账户不存在");
                     } else {
                        int applied = this.pointsStore
                           .addPoints(
                              this.pointsStore.toProfile(locked),
                              record.getRequiredPoints(),
                              "web_redeem_refund",
                              String.valueOf(record.getId()),
                              record.getServerId()
                           );
                        if (applied != record.getRequiredPoints()) {
                           throw new IllegalStateException("兑换退款积分写入不完整");
                        } else {
                           int updated = this.redeemRecordMapper
                              .update(
                                 null,
                                 new LambdaUpdateWrapper<PointsBotRedeemRecord>()
                                    .set(PointsBotRedeemRecord::getStatus, "REFUNDED")
                                    .set(PointsBotRedeemRecord::getActiveGuard, null)
                                    .set(PointsBotRedeemRecord::getResultMessage, "兑换失败，积分已退回")
                                    .set(PointsBotRedeemRecord::getFinishedAt, new Date())
                                    .eq(PointsBotRedeemRecord::getId, recordId)
                                    .eq(PointsBotRedeemRecord::getStatus, "PROCESSING")
                              );
                           if (updated != 1) {
                              throw new IllegalStateException("兑换退款状态写入失败");
                           }
                        }
                     }
                  }
               }
            );
      } catch (Exception var4) {
         log.error("Web积分兑换退款失败，记录保留处理中等待人工核查: recordId={}", recordId, var4);
         throw new IllegalStateException("兑换处理异常，请联系管理员核查记录 " + recordId, var4);
      }
   }

   private PointsBotPortalRedeemOptionResponse toRedeemOption(
      PointsBotPortalService.PortalIdentity identity, PointsBotPortalAccountResponse account, PointsBotRedeemConfig config, EmbyInfo server
   ) {
      String unavailableReason = null;
      if (!account.isPointsAccountExists()) {
         unavailableReason = "未找到积分账户";
      } else if (server != null && this.isEnabledServer(server)) {
         if (account.getBalance() < (long)config.getRequiredPoints().intValue()) {
            unavailableReason = "积分不足";
         } else if (PointsBotRedeemTypeEnum.RENEW.matches(config.getRedeemType())
            && this.resolveRenewTarget(identity.webUser(), config.getEmbyInfoId()) == null) {
            unavailableReason = "当前账号不属于该服务器";
         }
      } else {
         unavailableReason = "关联服务器暂不可用";
      }

      return PointsBotPortalRedeemOptionResponse.builder()
         .id(config.getId())
         .configName(config.getConfigName())
         .redeemType(config.getRedeemType())
         .redeemDays(config.getRedeemDays())
         .requiredPoints(config.getRequiredPoints())
         .serverId(config.getEmbyInfoId())
         .serverName(server == null ? null : server.getServerName())
         .remark(config.getRemark())
         .available(unavailableReason == null)
         .unavailableReason(unavailableReason)
         .build();
   }

   private PointsBotPortalService.PortalIdentity requireRedeemIdentity(long webUserId) {
      PointsBotPortalService.PortalIdentity identity = this.findIdentity(webUserId);
      if (identity == null) {
         throw this.badRequest("当前 Web 账号尚未绑定 Telegram，请先在个人资料中完成绑定");
      } else {
         PointsBotConfigService.PointsBotChannelConfig channel = this.pointsBotConfigService.loadConfig();
         if (channel.isEnabled() && identity.chatId() != null) {
            return identity;
         } else {
            throw this.badRequest("积分兑换暂未启用，请联系管理员");
         }
      }
   }

   private PointsBotPortalService.PortalIdentity findIdentity(long webUserId) {
      EmbyUser webUser = this.telegramBindingManager.findUsableUser(webUserId);
      if (webUser == null) {
         return null;
      } else {
         UserOauthBinding binding = this.telegramBindingManager.findBindingByUserId(webUserId);
         if (binding != null && StringUtils.hasText(binding.getProviderUserId())) {
            long telegramUserId;
            try {
               telegramUserId = Long.parseLong(binding.getProviderUserId());
            } catch (NumberFormatException var8) {
               return null;
            }

            Long chatId = this.resolveConfiguredChatId();
            return new PointsBotPortalService.PortalIdentity(webUser, binding, telegramUserId, chatId);
         } else {
            return null;
         }
      }
   }

   private Long resolveConfiguredChatId() {
      String raw = this.pointsBotConfigService.loadConfig().getConfig().getGroupChatId();
      if (!StringUtils.hasText(raw)) {
         return null;
      } else {
         try {
            return Long.valueOf(raw.trim());
         } catch (NumberFormatException var3) {
            return null;
         }
      }
   }

   private PointsBotPortalAccountResponse toAccount(PointsBotPortalService.PortalIdentity identity) {
      PointsBotUser pointsUser = null;
      if (identity.chatId() != null) {
         pointsUser = this.pointsBotUserMapper
            .selectOne(
               new LambdaQueryWrapper<PointsBotUser>()
                  .eq(PointsBotUser::getChatId, identity.chatId())
                  .eq(PointsBotUser::getUserId, Long.valueOf(identity.telegramUserId()))
                  .eq(BaseEntity::getDelFlag, Integer.valueOf(0))
                  .last("limit 1")
            );
      } else {
         pointsUser = this.pointsBotUserMapper
            .selectOne(
               new LambdaQueryWrapper<PointsBotUser>()
                  .eq(PointsBotUser::getUserId, Long.valueOf(identity.telegramUserId()))
                  .eq(BaseEntity::getDelFlag, Integer.valueOf(0))
                  .orderByDesc(PointsBotUser::getId)
                  .last("limit 1")
            );
      }

      return PointsBotPortalAccountResponse.builder()
         .telegramBound(true)
         .pointsAccountExists(pointsUser != null)
         .balance(pointsUser != null && pointsUser.getPoints() != null ? Math.max(0L, pointsUser.getPoints()) : 0L)
         .levelName(pointsUser == null ? null : pointsUser.getLevelName())
         .telegramUsername(identity.binding().getProviderUsername())
         .build();
   }

   private long currentBalance(PointsBotPortalService.PortalIdentity identity) {
      PointsBotUser user = this.pointsBotUserMapper
         .selectOne(
            new LambdaQueryWrapper<PointsBotUser>()
               .eq(PointsBotUser::getChatId, identity.chatId())
               .eq(PointsBotUser::getUserId, Long.valueOf(identity.telegramUserId()))
               .eq(BaseEntity::getDelFlag, Integer.valueOf(0))
               .last("limit 1")
         );
      return user != null && user.getPoints() != null ? Math.max(0L, user.getPoints()) : 0L;
   }

   private EmbyUser requireRenewTarget(EmbyUser webUser, Long serverId) {
      EmbyUser target = this.resolveRenewTarget(webUser, serverId);
      if (target == null) {
         throw this.badRequest("当前登录账号不属于该兑换项目的服务器，不能续费其他账号");
      } else if (Integer.valueOf(1).equals(target.getIsPrimaryAdmin())) {
         throw this.badRequest("最高管理员账号不能通过积分兑换续费");
      } else {
         return target;
      }
   }

   private EmbyUser resolveRenewTarget(EmbyUser webUser, Long serverId) {
      if (webUser != null && serverId != null) {
         if (Objects.equals(webUser.getEmbyInfoId(), serverId)) {
            return webUser;
         } else if (webUser.getIdentityGroupId() == null) {
            return null;
         } else {
            List<EmbyUser> matches = this.embyUserService
               .lambdaQuery()
               .eq(EmbyUser::getIdentityGroupId, webUser.getIdentityGroupId())
               .eq(EmbyUser::getEmbyInfoId, serverId)
               .eq(EmbyUser::getUserStatus, Integer.valueOf(0))
               .eq(BaseEntity::getDelFlag, Integer.valueOf(0))
               .orderByAsc(EmbyUser::getId)
               .last("limit 2")
               .list();
            return matches.size() == 1 ? matches.getFirst() : null;
         }
      } else {
         return null;
      }
   }

   private EmbyInfo requireEnabledServer(Long serverId) {
      EmbyInfo server = serverId == null ? null : this.embyInfoService.getById(serverId);
      if (!this.isEnabledServer(server)) {
         throw this.badRequest("兑换项目关联的服务器暂不可用");
      } else {
         return server;
      }
   }

   private boolean isEnabledServer(EmbyInfo server) {
      return server != null && Integer.valueOf(1).equals(server.getEnabled()) && Integer.valueOf(0).equals(server.getStatus()) && server.getDelFlag() == 0;
   }

   private boolean isUsableConfigShape(PointsBotRedeemConfig config) {
      return config != null
         && StringUtils.hasText(config.getConfigName())
         && PointsBotRedeemTypeEnum.isSupported(config.getRedeemType())
         && config.getRedeemDays() != null
         && config.getRedeemDays() > 0
         && config.getRequiredPoints() != null
         && config.getRequiredPoints() > 0
         && config.getEmbyInfoId() != null;
   }

   private PointsBotRedeemRecord findByRequestId(long webUserId, String requestId) {
      return this.redeemRecordMapper
         .selectOne(
            new LambdaQueryWrapper<PointsBotRedeemRecord>()
               .eq(PointsBotRedeemRecord::getWebUserId, Long.valueOf(webUserId))
               .eq(PointsBotRedeemRecord::getRequestId, requestId)
               .eq(BaseEntity::getDelFlag, Integer.valueOf(0))
               .last("limit 1")
         );
   }

   private PointsBotPortalRedeemResponse toExistingResponse(PointsBotRedeemRecord record, PointsBotPortalService.PortalIdentity identity) {
      String message = record.getResultMessage();
      if ("SUCCESS".equals(record.getStatus()) && PointsBotRedeemTypeEnum.CREATE_ACCOUNT.matches(record.getRedeemType())) {
         message = "该兑换已处理；出于安全考虑，账号密码只在首次成功响应中显示";
      }

      return PointsBotPortalRedeemResponse.builder()
         .recordId(record.getId())
         .status(record.getStatus())
         .configName(record.getConfigName())
         .redeemType(record.getRedeemType())
         .pointsSpent(record.getRequiredPoints())
         .remainingPoints(this.currentBalance(identity))
         .targetUserName(record.getTargetUserName())
         .expirationDate(record.getExpirationDate())
         .message(message)
         .build();
   }

   private PointsBotPortalLedgerResponse toLedgerResponse(PointsBotLedger row, String serverName) {
      PointsBotPortalLedgerResponse response = new PointsBotPortalLedgerResponse();
      response.setId(row.getId());
      response.setDelta(row.getDelta());
      response.setReason(this.ledgerManageService.translateReason(row.getReason()));
      response.setRefId(row.getRefId());
      response.setServerName(serverName);
      response.setCreateDatetime(row.getCreateDatetime());
      return response;
   }

   private PointsBotPortalRedeemRecordResponse toRedeemRecordResponse(PointsBotRedeemRecord row) {
      PointsBotPortalRedeemRecordResponse response = new PointsBotPortalRedeemRecordResponse();
      response.setId(row.getId());
      response.setConfigName(row.getConfigName());
      response.setRedeemType(row.getRedeemType());
      response.setRedeemDays(row.getRedeemDays());
      response.setRequiredPoints(row.getRequiredPoints());
      response.setServerId(row.getServerId());
      response.setServerName(row.getServerName());
      response.setTargetUserName(row.getTargetUserName());
      response.setStatus(row.getStatus());
      response.setResultMessage(row.getResultMessage());
      response.setExpirationDate(row.getExpirationDate());
      response.setFinishedAt(row.getFinishedAt());
      response.setCreateDatetime(row.getCreateDatetime());
      return response;
   }

   private Map<Long, EmbyInfo> loadServers(Set<Long> serverIds) {
      return serverIds.isEmpty()
         ? Collections.emptyMap()
         : this.embyInfoService
            .listByIds(serverIds)
            .stream()
            .collect(Collectors.toMap(EmbyInfo::getId, Function.identity(), (first, ignored) -> first, LinkedHashMap::new));
   }

   private Map<Long, String> loadServerNames(Set<Long> serverIds) {
      return this.loadServers(serverIds).values().stream().collect(Collectors.toMap(EmbyInfo::getId, EmbyInfo::getServerName));
   }

   private PointsBotPortalAccountResponse unboundAccount() {
      return PointsBotPortalAccountResponse.builder().telegramBound(false).pointsAccountExists(false).balance(0L).build();
   }

   private <T> Page<T> emptyPage(long current, long size) {
      Page<T> page = new Page<>(current, size, 0L);
      page.setRecords(List.of());
      return page;
   }

   private long normalizeCurrent(long value) {
      return Math.max(1L, value);
   }

   private long normalizeSize(long value) {
      return Math.max(1L, Math.min(value, 100L));
   }

   private String normalize(String value) {
      return StringUtils.hasText(value) ? value.trim().toUpperCase(Locale.ROOT) : "";
   }

   private String trimToNull(String value) {
      return StringUtils.hasText(value) ? value.trim() : null;
   }

   private BizException badRequest(String message) {
      return new BizException(ResponseStatusEnum.BAD_REQUEST.getCode(), message);
   }

   private static record ExternalRedeemResult(Long targetUserId, String targetUserName, String password, Date expirationDate) {
   }

   private static record PortalIdentity(EmbyUser webUser, UserOauthBinding binding, long telegramUserId, Long chatId) {
   }

   private static record PreparedRedeem(PointsBotRedeemRecord record, PointsBotRedeemConfig config, EmbyInfo server, EmbyUser targetUser) {
   }
}
