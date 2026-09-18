package com.una.embyhub.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.conditions.update.LambdaUpdateChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.diboot.core.binding.QueryBuilder;
import com.diboot.core.util.BeanUtils;
import com.una.embyhub.config.common.MpConvert;
import com.una.embyhub.config.common.enums.ResponseStatusEnum;
import com.una.embyhub.config.common.exception.BizException;
import com.una.embyhub.config.common.utils.ConfigCacheLoaderUtils;
import com.una.embyhub.config.common.utils.EmbyInfoCacheManagerUtils;
import com.una.embyhub.config.common.utils.PushUtils;
import com.una.embyhub.config.mybatis.MybatisPlusPage;
import com.una.embyhub.mapper.RequestListMapper;
import com.una.embyhub.model.dto.request.requestlist.RequestListAudit;
import com.una.embyhub.model.dto.request.requestlist.RequestListReject;
import com.una.embyhub.model.dto.request.requestlist.RequestListRequest;
import com.una.embyhub.model.dto.request.requestlist.RequestListSave;
import com.una.embyhub.model.dto.request.requestlist.RequestListUpdate;
import com.una.embyhub.model.dto.response.embyuser.EmbyUserResponse;
import com.una.embyhub.model.dto.response.requestlist.RequestListResponse;
import com.una.embyhub.model.dto.response.requestlist.RequestListStatusResponse;
import com.una.embyhub.model.entity.BaseEntity;
import com.una.embyhub.model.entity.EmbyInfo;
import com.una.embyhub.model.entity.EmbyUser;
import com.una.embyhub.model.entity.RequestList;
import com.una.embyhub.service.EmbyApiClientService;
import com.una.embyhub.service.EmbyInfoService;
import com.una.embyhub.service.EmbyUserService;
import com.una.embyhub.service.MoviePilotAsyncService;
import com.una.embyhub.service.MoviePilotService;
import com.una.embyhub.service.RequestListService;
import com.una.embyhub.service.TelegramRequestPointsService;
import java.time.Clock;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

@Service
@Transactional(
   rollbackFor = {Exception.class}
)
public class RequestListServiceImpl extends ServiceImpl<RequestListMapper, RequestList> implements RequestListService {
   private static final ZoneId DEFAULT_TODAY_COUNT_ZONE = ZoneId.of("Asia/Shanghai");
   @Autowired
   private EmbyApiClientService embyApiClientService;
   @Autowired
   private EmbyUserService embyUserService;
   @Autowired
   private PushUtils pushUtils;
   @Autowired
   private EmbyInfoService embyInfoService;
   @Autowired
   private EmbyInfoCacheManagerUtils embyInfoCacheManager;
   @Autowired
   private ConfigCacheLoaderUtils configCacheLoaderUtils;
   @Autowired
   private MoviePilotAsyncService moviePilotAsyncService;
   @Autowired
   private MoviePilotService moviePilotService;
   @Autowired
   private TelegramRequestPointsService telegramRequestPointsService;

   private EmbyInfoCacheManagerUtils.EmbyServerConfig getCurrentServerConfig(EmbyUser embyUser) {
      return this.embyInfoCacheManager.getRequiredConfig(embyUser);
   }

   private EmbyInfoCacheManagerUtils.EmbyServerConfig getCurrentServerConfig() {
      EmbyUser embyUser = (EmbyUser)StpUtil.getSession().get("user");
      return this.getCurrentServerConfig(embyUser);
   }

   private Long getEmbyInfoId(EmbyUser embyUser) {
      return this.getCurrentServerConfig(embyUser).id();
   }

   private Long getEmbyInfoId() {
      return this.getCurrentServerConfig().id();
   }

   private Long resolveRequestEmbyInfoId(EmbyUser embyUser, RequestListSave requestListSave) {
      return embyUser != null && Integer.valueOf(1).equals(embyUser.getIsAdmin()) && requestListSave != null && requestListSave.getEmbyInfoId() != null
         ? requestListSave.getEmbyInfoId()
         : this.getEmbyInfoId(embyUser);
   }

   @Override
   public Page<RequestListResponse> select(MybatisPlusPage<RequestListRequest> page) {
      EmbyUser embyUser = (EmbyUser)StpUtil.getSession().get("user");
      QueryWrapper queryWrapper = QueryBuilder.toQueryWrapper(page.getObject());
      if (embyUser.getIsAdmin() != 1) {
         queryWrapper.eq("user_id", embyUser.getId());
      }

      queryWrapper.orderByDesc("id");
      return MpConvert.page(queryWrapper, this.getBaseMapper(), RequestListResponse.class, page.getCurrent(), page.getSize(), page.getOrders());
   }

   @Override
   public long todayCount(Long embyInfoId, String timezone) {
      EmbyUser embyUser = (EmbyUser)StpUtil.getSession().get("user");
      RequestListServiceImpl.TodayWindow today = resolveTodayWindow(timezone, Clock.systemUTC());
      QueryWrapper<RequestList> queryWrapper = new QueryWrapper<>();
      queryWrapper.eq(embyInfoId != null, "emby_info_id", embyInfoId);
      if (!Integer.valueOf(1).equals(embyUser.getIsAdmin())) {
         queryWrapper.eq("user_id", embyUser.getId());
      }

      queryWrapper.ge("create_datetime", today.start()).lt("create_datetime", today.end());
      return this.getBaseMapper().selectCount(queryWrapper);
   }

   static RequestListServiceImpl.TodayWindow resolveTodayWindow(String timezone, Clock clock) {
      ZoneId zone = resolveTodayCountZone(timezone);
      LocalDate today = LocalDate.now(clock.withZone(zone));
      return new RequestListServiceImpl.TodayWindow(
         Date.from(today.atStartOfDay(zone).toInstant()), Date.from(today.plusDays(1L).atStartOfDay(zone).toInstant()), zone
      );
   }

   private static ZoneId resolveTodayCountZone(String timezone) {
      if (StringUtils.hasText(timezone) && timezone.length() <= 64) {
         try {
            return ZoneId.of(timezone.trim());
         } catch (DateTimeException var2) {
            return DEFAULT_TODAY_COUNT_ZONE;
         }
      } else {
         return DEFAULT_TODAY_COUNT_ZONE;
      }
   }

   @Override
   public EmbyUserResponse insertRequestList(RequestListSave requestListSave) {
      EmbyUser embyUser = (EmbyUser)StpUtil.getSession().get("user");
      this.insertRequestListInternal(embyUser, requestListSave, true, null, null, null);
      StpUtil.getSession().set("user", this.embyUserService.getById(embyUser.getId()));
      return BeanUtils.convert(this.embyUserService.getById(embyUser.getId()), EmbyUserResponse.class);
   }

   @Override
   public EmbyUserResponse insertRequestListForUser(Long userId, RequestListSave requestListSave) {
      EmbyUser embyUser = this.getAvailableEmbyUser(userId);
      this.insertRequestListInternal(embyUser, requestListSave, true, null, null, null);
      return BeanUtils.convert(this.embyUserService.getById(embyUser.getId()), EmbyUserResponse.class);
   }

   @Override
   public RequestList insertTelegramRequestListForUser(
      Long userId, RequestListSave requestListSave, Long telegramUserId, Integer pointsCost, String pointsRefId
   ) {
      EmbyUser embyUser = this.getAvailableEmbyUser(userId);
      return this.insertRequestListInternal(embyUser, requestListSave, false, telegramUserId, pointsCost, pointsRefId);
   }

   private EmbyUser getAvailableEmbyUser(Long userId) {
      if (userId == null) {
         throw new BizException(ResponseStatusEnum.UNAUTHORIZED);
      } else {
         EmbyUser embyUser = this.embyUserService.getById(userId);
         if (embyUser == null || Integer.valueOf(1).equals(embyUser.getDelFlag())) {
            throw new BizException(ResponseStatusEnum.USER_NOT_EXIST);
         } else if (Integer.valueOf(1).equals(embyUser.getUserStatus())) {
            throw new BizException(ResponseStatusEnum.USER_DISABLED);
         } else {
            return embyUser;
         }
      }
   }

   private RequestList insertRequestListInternal(
      EmbyUser embyUser, RequestListSave requestListSave, boolean deductRequestPackage, Long telegramUserId, Integer pointsCost, String pointsRefId
   ) {
      Long embyInfoId = this.resolveRequestEmbyInfoId(embyUser, requestListSave);
      EmbyInfo targetEmbyInfo = embyInfoId != null ? this.embyInfoService.getById(embyInfoId) : null;
      if (embyInfoId != null && targetEmbyInfo == null) {
         throw new BizException(ResponseStatusEnum.EMBY_SERVER_NOT_FOUND);
      } else {
         if (targetEmbyInfo != null) {
            requestListSave.setEmbyInfoId(embyInfoId);
            requestListSave.setEmbyServerId(targetEmbyInfo.getEmbyServerId());
         }

         boolean embyByTmdbId = this.embyApiClientService.getEmbyByTmdbId(String.valueOf(requestListSave.getTmdbId()), embyInfoId);
         if (embyByTmdbId) {
            throw new BizException(ResponseStatusEnum.REQUEST_LIST_ALREADY_STOCK);
         } else {
            Long rejectedCount = this.getBaseMapper()
               .selectCount(
                  this.requestIdentityQuery(String.valueOf(requestListSave.getTmdbId()), requestListSave.getSeason(), requestListSave.getType())
                     .eq(RequestList::getUserId, embyUser.getId())
                     .eq(RequestList::getStatus, Integer.valueOf(2))
               );
            if (rejectedCount != null && rejectedCount > 0L) {
               throw new BizException(ResponseStatusEnum.REQUEST_LIST_REJECTED_EXISTS);
            } else {
               Long activeCount = this.getBaseMapper()
                  .selectCount(
                     this.requestIdentityQuery(String.valueOf(requestListSave.getTmdbId()), requestListSave.getSeason(), requestListSave.getType())
                        .ne(RequestList::getStatus, Integer.valueOf(2))
                  );
               if (activeCount != null && activeCount > 0L) {
                  throw new BizException(ResponseStatusEnum.REQUEST_LIST_ALREADY_EXISTS);
               } else {
                  EmbyUser embyUserData = this.embyUserService.getById(embyUser.getId());
                  if (embyUserData != null && !Integer.valueOf(1).equals(embyUserData.getDelFlag())) {
                     Integer requestPackagesCount = embyUserData.getRequestPackagesCount();
                     if (requestListSave.getReleaseDate() != null && !requestListSave.getReleaseDate().after(new Date())) {
                        if (deductRequestPackage) {
                           if (requestPackagesCount == null || requestPackagesCount <= 0) {
                              throw new BizException(ResponseStatusEnum.REQUEST_LIST_PACKAGE_NOT_ENOUGH);
                           }

                           embyUserData.setRequestPackagesCount(requestPackagesCount - 1);
                           this.embyUserService.updateById(embyUserData);
                        }

                        String imageUrl = requestListSave.getImageUrl();
                        String backdropPath = requestListSave.getBackdropPath();
                        if (!this.isValidHttpUrl(imageUrl)) {
                           if (this.isValidHttpUrl(backdropPath)) {
                              requestListSave.setImageUrl(backdropPath);
                           } else {
                              requestListSave.setImageUrl(null);
                              requestListSave.setBackdropPath(null);
                           }
                        }

                        RequestList requestList = BeanUtils.convert(requestListSave, RequestList.class);
                        requestList.setUserId(embyUser.getId());
                        requestList.setEmbyUserName(embyUser.getEmbyUserName());
                        requestList.setBackdropPath(requestListSave.getBackdropPath());
                        requestList.setEmbyInfoId(embyInfoId);
                        if (telegramUserId != null) {
                           requestList.setRequestSource("telegram");
                           requestList.setTelegramUserId(telegramUserId);
                           requestList.setPointsCost(pointsCost == null ? 0 : Math.max(pointsCost, 0));
                           requestList.setPointsRefunded(0);
                           requestList.setPointsRefId(pointsRefId);
                        }

                        if (targetEmbyInfo != null) {
                           requestList.setEmbyServerId(targetEmbyInfo.getEmbyServerId());
                        }

                        boolean autoSubscribe = this.isMoviePilotAutoSubscribeEnabled();
                        requestList.setAuditStatus(autoSubscribe ? 1 : 0);
                        requestList.setRemark("");
                        if (autoSubscribe) {
                           String autoAuditRemark = "已开启自动审核，已自动通过";
                           if (StringUtils.hasText(requestList.getRemark())) {
                              requestList.setRemark(requestList.getRemark() + "；" + autoAuditRemark);
                           } else {
                              requestList.setRemark(autoAuditRemark);
                           }
                        }

                        this.save(requestList);
                        this.pushUtils.pushAsync(requestListSave, requestList, embyUser);
                        if (autoSubscribe && this.isMoviePilotConfigured()) {
                           this.scheduleMoviePilotSubscribe(requestList, requestListSave.getOriginalName());
                        }

                        return requestList;
                     } else {
                        throw new BizException(ResponseStatusEnum.REQUEST_LIST_NOT_RELEASED);
                     }
                  } else {
                     throw new BizException(ResponseStatusEnum.USER_NOT_EXIST);
                  }
               }
            }
         }
      }
   }

   @Override
   public void updateRequestList(RequestListUpdate requestListUpdate) {
      RequestList requestList = BeanUtils.convert(requestListUpdate, RequestList.class);
      if (!StringUtils.hasText(requestList.getEmbyServerId()) && requestList.getEmbyInfoId() != null) {
         EmbyInfo embyInfo = this.embyInfoService.getById(requestList.getEmbyInfoId());
         if (embyInfo != null) {
            requestList.setEmbyServerId(embyInfo.getEmbyServerId());
         }
      }

      this.updateById(requestList);
   }

   @Override
   public void deleteByRequestListId(Long requestListId) {
      EmbyUser embyUser = (EmbyUser)StpUtil.getSession().get("user");
      RequestList requestList = new LambdaQueryChainWrapper<>(this.getBaseMapper())
         .eq(RequestList::getId, requestListId)
         .eq(embyUser.getIsAdmin() != 1, RequestList::getUserId, embyUser.getId())
         .one();
      if (requestList == null) {
         throw new BizException(ResponseStatusEnum.REQUEST_LIST_NOT_EXISTS);
      } else {
         this.removeById(requestList);
      }
   }

   @Override
   public void updateByRequestListId(List<Long> requestListIdList, Long embyInfoId) {
      try {
         List<RequestList> requestListList = new LambdaQueryChainWrapper<>(this.getBaseMapper())
            .in(RequestList::getId, requestListIdList)
            .eq(embyInfoId != null, RequestList::getEmbyInfoId, embyInfoId)
            .list();
         if (CollectionUtils.isEmpty(requestListList)) {
            throw new BizException(ResponseStatusEnum.REQUEST_LIST_NOT_EXISTS);
         }

         long count = requestListList.stream().filter(requestListx -> requestListx.getStatus() == 1).count();
         if (count > 0L) {
            throw new BizException(ResponseStatusEnum.REQUEST_LIST_ALREADY_STORE);
         }

         EmbyUser embyUser = (EmbyUser)StpUtil.getSession().get("user");
         Long targetEmbyInfoId = embyInfoId != null ? embyInfoId : requestListList.get(0).getEmbyInfoId();
         new LambdaUpdateChainWrapper<>(this.getBaseMapper())
            .set(RequestList::getStatus, Integer.valueOf(1))
            .set(RequestList::getAuditStatus, Integer.valueOf(1))
            .set(BaseEntity::getUpdateDatetime, new Date())
            .set(BaseEntity::getUpdateUserId, embyUser.getId())
            .set(BaseEntity::getUpdateUserName, embyUser.getEmbyUserName())
            .eq(targetEmbyInfoId != null, RequestList::getEmbyInfoId, targetEmbyInfoId)
            .in(RequestList::getId, requestListIdList)
            .update();
         EmbyInfo embyInfo = targetEmbyInfoId != null ? this.embyInfoService.getById(targetEmbyInfoId) : null;
         String serverUrl = this.buildServerUrl(embyInfo);
         String serverName = embyInfo != null ? embyInfo.getServerName() : null;

         for (RequestList requestList : requestListList) {
            this.pushUtils.pushCompletedAsync(requestList, serverUrl, serverName);
         }
      } catch (Exception var13) {
         this.log.error("修改求片状态列表通知发送失败", var13);
      }
   }

   @Override
   public void rejectRequestList(RequestListReject requestListReject) {
      EmbyUser embyUser = (EmbyUser)StpUtil.getSession().get("user");
      List<RequestList> requestListList = new LambdaQueryChainWrapper<>(this.getBaseMapper())
         .in(RequestList::getId, requestListReject.getRequestListIdList())
         .eq(requestListReject.getEmbyInfoId() != null, RequestList::getEmbyInfoId, requestListReject.getEmbyInfoId())
         .list();
      if (CollectionUtils.isEmpty(requestListList)) {
         throw new BizException(ResponseStatusEnum.REQUEST_LIST_NOT_EXISTS);
      } else {
         long count = requestListList.stream().filter(requestList -> Integer.valueOf(1).equals(requestList.getStatus())).count();
         if (count > 0L) {
            throw new BizException(ResponseStatusEnum.REQUEST_LIST_ALREADY_STORE);
         } else {
            this.cancelMoviePilotSubscriptionsIfNeeded(requestListList);
            new LambdaUpdateChainWrapper<>(this.getBaseMapper())
               .set(RequestList::getStatus, Integer.valueOf(2))
               .set(RequestList::getAuditStatus, Integer.valueOf(2))
               .set(RequestList::getRemark, normalizeReviewRemark(requestListReject.getRemark()))
               .set(BaseEntity::getUpdateDatetime, new Date())
               .set(BaseEntity::getUpdateUserId, embyUser.getId())
               .set(BaseEntity::getUpdateUserName, embyUser.getEmbyUserName())
               .eq(requestListReject.getEmbyInfoId() != null, RequestList::getEmbyInfoId, requestListReject.getEmbyInfoId())
               .in(RequestList::getId, requestListReject.getRequestListIdList())
               .update();
            this.telegramRequestPointsService.refundRejectedRequests(requestListList);
         }
      }
   }

   @Override
   public void auditRequestList(RequestListAudit requestListAudit) {
      EmbyUser embyUser = (EmbyUser)StpUtil.getSession().get("user");
      List<RequestList> requestListList = new LambdaQueryChainWrapper<>(this.getBaseMapper())
         .in(RequestList::getId, requestListAudit.getRequestListIdList())
         .eq(requestListAudit.getEmbyInfoId() != null, RequestList::getEmbyInfoId, requestListAudit.getEmbyInfoId())
         .list();
      if (CollectionUtils.isEmpty(requestListList)) {
         throw new BizException(ResponseStatusEnum.REQUEST_LIST_NOT_EXISTS);
      } else {
         List<RequestList> moviePilotSubscribeList = this.isMoviePilotConfigured()
            ? requestListList.stream().filter(requestList -> requestList.getAuditStatus() == null || requestList.getAuditStatus() != 1).toList()
            : List.of();
         new LambdaUpdateChainWrapper<>(this.getBaseMapper())
            .set(RequestList::getAuditStatus, Integer.valueOf(1))
            .set(RequestList::getRemark, normalizeReviewRemark(requestListAudit.getRemark()))
            .set(BaseEntity::getUpdateDatetime, new Date())
            .set(BaseEntity::getUpdateUserId, embyUser.getId())
            .set(BaseEntity::getUpdateUserName, embyUser.getEmbyUserName())
            .eq(requestListAudit.getEmbyInfoId() != null, RequestList::getEmbyInfoId, requestListAudit.getEmbyInfoId())
            .in(RequestList::getId, requestListAudit.getRequestListIdList())
            .update();
         this.scheduleMoviePilotSubscribe(moviePilotSubscribeList);
      }
   }

   static String normalizeReviewRemark(String remark) {
      return StringUtils.hasText(remark) ? remark.trim() : "";
   }

   private String buildServerUrl(EmbyInfo embyInfo) {
      if (embyInfo == null) {
         return null;
      } else {
         String embyUrl = embyInfo.getEmbyUrl();
         if (!StringUtils.hasText(embyUrl) || !embyUrl.startsWith("http://") && !embyUrl.startsWith("https://")) {
            StringBuilder baseUrl = new StringBuilder();
            if (StringUtils.hasText(embyInfo.getEmbyAgreement())) {
               baseUrl.append(embyInfo.getEmbyAgreement()).append("://");
            }

            if (StringUtils.hasText(embyInfo.getEmbyUrl())) {
               baseUrl.append(embyInfo.getEmbyUrl());
            }

            if (StringUtils.hasText(embyInfo.getEmbyPort())) {
               if (embyInfo.getEmbyUrl() != null && !embyInfo.getEmbyUrl().contains(":")) {
                  baseUrl.append(":");
               }

               baseUrl.append(embyInfo.getEmbyPort());
            }

            return baseUrl.length() > 0 ? baseUrl.toString() : null;
         } else {
            return embyUrl;
         }
      }
   }

   @Override
   public boolean getRequestListStatusByTmdbid(String tmdbid) {
      Long count = new LambdaQueryChainWrapper<>(this.getBaseMapper())
         .eq(RequestList::getTmdbId, tmdbid)
         .ne(RequestList::getStatus, Integer.valueOf(2))
         .count();
      return count > 0L;
   }

   @Override
   public boolean getRequestListStatusByTmdbidAndSeasons(String tmdbid, Integer seasons, String type) {
      Long count = this.getBaseMapper().selectCount(this.requestIdentityQuery(tmdbid, seasons, type).ne(RequestList::getStatus, Integer.valueOf(2)));
      return count > 0L;
   }

   @Override
   public boolean isCurrentUserRequestSubmitted(String tmdbid, Integer seasons, String type) {
      EmbyUser embyUser = (EmbyUser)StpUtil.getSession().get("user");
      if (embyUser != null && embyUser.getId() != null) {
         Long count = this.getBaseMapper()
            .selectCount(
               this.requestIdentityQuery(tmdbid, seasons, type).eq(RequestList::getUserId, embyUser.getId()).ne(RequestList::getStatus, Integer.valueOf(2))
            );
         return count > 0L;
      } else {
         return false;
      }
   }

   @Override
   public RequestListStatusResponse getRequestStatus(String tmdbid, Integer seasons, String type) {
      EmbyUser embyUser = (EmbyUser)StpUtil.getSession().get("user");
      Long currentUserId = embyUser != null ? embyUser.getId() : null;
      List<RequestList> records = this.getBaseMapper()
         .selectList(this.requestIdentityQuery(tmdbid, seasons, type).select(RequestList::getUserId, RequestList::getStatus, RequestList::getAuditStatus));
      boolean submitted = records.stream().anyMatch(record -> !Integer.valueOf(2).equals(record.getStatus()));
      boolean currentUserSubmitted = currentUserId != null
         && records.stream().anyMatch(record -> currentUserId.equals(record.getUserId()) && !Integer.valueOf(2).equals(record.getStatus()));
      boolean currentUserRejected = currentUserId != null
         && records.stream().anyMatch(record -> currentUserId.equals(record.getUserId()) && Integer.valueOf(2).equals(record.getStatus()));
      boolean pendingImport = records.stream()
         .anyMatch(record -> Integer.valueOf(0).equals(record.getStatus()) && Integer.valueOf(1).equals(record.getAuditStatus()));
      return new RequestListStatusResponse(submitted, currentUserSubmitted, currentUserRejected, pendingImport);
   }

   private LambdaQueryWrapper<RequestList> requestIdentityQuery(String tmdbid, Integer seasons, String type) {
      LambdaQueryWrapper<RequestList> query = new LambdaQueryWrapper<RequestList>().eq(RequestList::getTmdbId, tmdbid)
         .eq(RequestList::getType, type);
      if ("movie".equalsIgnoreCase(type)) {
         query.and(seasonQuery -> seasonQuery.isNull(RequestList::getSeason).or().eq(RequestList::getSeason, Integer.valueOf(0)));
      } else if (seasons != null) {
         query.eq(RequestList::getSeason, seasons);
      }

      return query;
   }

   private boolean isMoviePilotAutoSubscribeEnabled() {
      String autoSubscribe = this.configCacheLoaderUtils.getConfigValue("movie_pilot_auto_subscribe");
      return !StringUtils.hasText(autoSubscribe) ? false : Boolean.parseBoolean(autoSubscribe);
   }

   private boolean isMoviePilotConfigured() {
      String configValue = this.configCacheLoaderUtils.getConfigValue("movie_pilot_config");
      return StringUtils.hasText(configValue);
   }

   private void scheduleMoviePilotSubscribe(RequestList requestList, String subscribeName) {
      if (requestList != null) {
         this.runAfterCommit(() -> this.moviePilotAsyncService.callMoviePilotSubscribe(requestList, subscribeName));
      }
   }

   private void scheduleMoviePilotSubscribe(List<RequestList> requestListList) {
      if (!CollectionUtils.isEmpty(requestListList)) {
         this.runAfterCommit(() -> requestListList.forEach(this.moviePilotAsyncService::callMoviePilotSubscribe));
      }
   }

   private void runAfterCommit(Runnable task) {
      if (TransactionSynchronizationManager.isSynchronizationActive()) {
         TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronization() {
            @Override
            public void afterCommit() {
               task.run();
            }
         });
      } else {
         task.run();
      }
   }

   private void cancelMoviePilotSubscriptionsIfNeeded(List<RequestList> requestListList) {
      if (this.isMoviePilotConfigured() && !CollectionUtils.isEmpty(requestListList)) {
         List<RequestList> cancelCandidates = requestListList.stream()
            .filter(requestListx -> Integer.valueOf(1).equals(requestListx.getAuditStatus()))
            .toList();
         if (!CollectionUtils.isEmpty(cancelCandidates)) {
            Set<Long> canceledIds = new HashSet<>();

            for (RequestList requestList : cancelCandidates) {
               Long subscriptionId = requestList.getMoviePilotSubscriptionId();
               if (subscriptionId != null && canceledIds.add(subscriptionId)) {
                  this.moviePilotService.cancelSubscribe(subscriptionId);
               }
            }
         }
      }
   }

   private boolean isValidHttpUrl(String url) {
      return StringUtils.hasText(url) && (url.startsWith("http://") || url.startsWith("https://"));
   }

   static record TodayWindow(Date start, Date end, ZoneId zone) {
   }
}
