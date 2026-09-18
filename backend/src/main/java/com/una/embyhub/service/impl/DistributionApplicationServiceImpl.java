package com.una.embyhub.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.una.embyhub.config.common.MpConvert;
import com.una.embyhub.config.common.enums.RegisterChannelEnum;
import com.una.embyhub.config.common.enums.ResponseStatusEnum;
import com.una.embyhub.config.common.exception.BizException;
import com.una.embyhub.config.mybatis.MybatisPlusPage;
import com.una.embyhub.mapper.DistributionApplicationMapper;
import com.una.embyhub.model.dto.request.distributionapplication.DistributionApplicationDeleteRequest;
import com.una.embyhub.model.dto.request.distributionapplication.DistributionApplicationRequest;
import com.una.embyhub.model.dto.request.distributionapplication.DistributionApplicationReviewRequest;
import com.una.embyhub.model.dto.request.distributionapplication.DistributionApplicationSave;
import com.una.embyhub.model.dto.response.distributionapplication.DistributionApplicationResponse;
import com.una.embyhub.model.dto.response.distributionapplication.DistributionApplicationStatisticsResponse;
import com.una.embyhub.model.entity.CardSecurityManagement;
import com.una.embyhub.model.entity.DistributionApplication;
import com.una.embyhub.model.entity.EmbyUser;
import com.una.embyhub.model.entity.UserPoints;
import com.una.embyhub.service.CardSecurityManagementService;
import com.una.embyhub.service.DistributionApplicationService;
import com.una.embyhub.service.EmbyUserService;
import com.una.embyhub.service.UserPointsService;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Service
public class DistributionApplicationServiceImpl
   extends ServiceImpl<DistributionApplicationMapper, DistributionApplication>
   implements DistributionApplicationService {
   @Autowired
   private EmbyUserService embyUserService;
   @Autowired
   private UserPointsService userPointsService;
   @Autowired
   private CardSecurityManagementService cardSecurityManagementService;

   @Override
   public Page<DistributionApplicationResponse> selectMyList(MybatisPlusPage<DistributionApplicationRequest> page) {
      Long userId = StpUtil.getLoginIdAsLong();
      LambdaQueryChainWrapper<DistributionApplication> query = new LambdaQueryChainWrapper<>(this.baseMapper);
      if (!StpUtil.hasPermission("admin")) {
         query.eq(DistributionApplication::getUserId, userId);
      } else if (page.getObject() != null) {
         if (page.getObject().getUserId() != null) {
            query.eq(DistributionApplication::getUserId, page.getObject().getUserId());
         }

         if (StringUtils.hasText(page.getObject().getUserName())) {
            List<EmbyUser> users = this.embyUserService.lambdaQuery().like(EmbyUser::getEmbyUserName, page.getObject().getUserName()).list();
            if (users.isEmpty()) {
               query.eq(DistributionApplication::getId, Long.valueOf(-1L));
            } else {
               List<Long> userIds = users.stream().map(EmbyUser::getId).collect(Collectors.toList());
               query.in(DistributionApplication::getUserId, userIds);
            }
         }

         if (StringUtils.hasText(page.getObject().getOrderNo())) {
            query.like(DistributionApplication::getOrderNo, page.getObject().getOrderNo());
         }
      }

      if (page.getObject() != null && page.getObject().getStatus() != null) {
         query.eq(DistributionApplication::getStatus, page.getObject().getStatus());
      }

      query.orderByDesc(DistributionApplication::getId);
      new MpConvert();
      return MpConvert.page(query.getWrapper(), this.baseMapper, DistributionApplicationResponse.class, page.getCurrent(), page.getSize(), page.getOrders());
   }

   @Override
   public DistributionApplicationStatisticsResponse getStatistics(DistributionApplicationRequest request) {
      Long userId = StpUtil.getLoginIdAsLong();
      LambdaQueryChainWrapper<DistributionApplication> query = new LambdaQueryChainWrapper<>(this.baseMapper);
      if (!StpUtil.hasPermission("admin")) {
         query.eq(DistributionApplication::getUserId, userId);
      } else if (request != null) {
         if (request.getUserId() != null) {
            query.eq(DistributionApplication::getUserId, request.getUserId());
         }

         if (StringUtils.hasText(request.getUserName())) {
            List<EmbyUser> users = this.embyUserService.lambdaQuery().like(EmbyUser::getEmbyUserName, request.getUserName()).list();
            if (users.isEmpty()) {
               query.eq(DistributionApplication::getId, Long.valueOf(-1L));
            } else {
               List<Long> userIds = users.stream().map(EmbyUser::getId).collect(Collectors.toList());
               query.in(DistributionApplication::getUserId, userIds);
            }
         }
      }

      List<DistributionApplication> list = query.list();
      DistributionApplicationStatisticsResponse response = new DistributionApplicationStatisticsResponse();
      response.setTotal((long)list.size());
      response.setPending(list.stream().filter(item -> item.getStatus() == 0).count());
      response.setApproved(list.stream().filter(item -> item.getStatus() == 1).count());
      response.setRejected(list.stream().filter(item -> item.getStatus() == 2).count());
      return response;
   }

   @Transactional(
      rollbackFor = {Exception.class}
   )
   @Override
   public void submitApplication(DistributionApplicationSave saveDto) {
      Long userId = StpUtil.getLoginIdAsLong();
      EmbyUser user = this.embyUserService.getById(userId);
      if (user.getIsDistributor() != null && user.getIsDistributor() != 0) {
         if (saveDto.getCardCount() != null && saveDto.getCardCount() > 200) {
            throw new BizException(ResponseStatusEnum.BAD_REQUEST.getCode(), "单次申请卡密数量不能超过200张");
         } else {
            DistributionApplication application = new DistributionApplication();
            application.setOrderNo("FXS" + IdUtil.getSnowflakeNextIdStr());
            application.setUserId(userId);
            application.setCardCount(saveDto.getCardCount());
            application.setCardDays(saveDto.getCardDays());
            application.setEmbyInfoId(saveDto.getEmbyInfoId());
            application.setStatus(0);
            this.save(application);
         }
      } else {
         throw new BizException(ResponseStatusEnum.PERMISSION_DENIED);
      }
   }

   @Transactional(
      rollbackFor = {Exception.class}
   )
   @Override
   public void deleteApplication(DistributionApplicationDeleteRequest request) {
      Long userId = StpUtil.getLoginIdAsLong();
      if (request.getIds() != null && !request.getIds().isEmpty()) {
         List<DistributionApplication> apps = new LambdaQueryChainWrapper<>(this.baseMapper)
            .in(DistributionApplication::getId, request.getIds())
            .eq(DistributionApplication::getUserId, userId)
            .eq(DistributionApplication::getStatus, Integer.valueOf(0))
            .list();
         if (!apps.isEmpty()) {
            List<Long> idsToDelete = apps.stream().map(DistributionApplication::getId).collect(Collectors.toList());
            this.removeByIds(idsToDelete);
         }
      }
   }

   @Transactional(
      rollbackFor = {Exception.class}
   )
   @Override
   public void reviewApplication(DistributionApplicationReviewRequest request) {
      if (request.getApplicationIds() != null && !request.getApplicationIds().isEmpty()) {
         for (Long applicationId : request.getApplicationIds()) {
            try {
               DistributionApplication app = new LambdaQueryChainWrapper<>(this.baseMapper)
                  .eq(DistributionApplication::getId, applicationId)
                  .last("FOR UPDATE")
                  .one();
               if (app != null && app.getStatus() == 0) {
                  Integer status = request.getStatus();
                  if (status == 1 && app.getCardCount() > 200) {
                     throw new BizException(ResponseStatusEnum.BAD_REQUEST.getCode(), "单次生成卡密数量不能超过200张");
                  }

                  app.setStatus(status);
                  app.setReviewComment(request.getReviewComment());
                  this.updateById(app);
                  if (status == 1) {
                     Long targetEmbyInfoId = app.getEmbyInfoId();
                     List<CardSecurityManagement> cards = new ArrayList<>();

                     for (int i = 0; i < app.getCardCount(); i++) {
                        CardSecurityManagement card = new CardSecurityManagement();
                        card.setCardPassword(IdUtil.fastSimpleUUID());
                        card.setCardValidity(app.getCardDays());
                        card.setEmbyInfoId(targetEmbyInfoId);
                        card.setDistributorId(app.getUserId());
                        card.setCardStatus(0);
                        cards.add(card);
                     }

                     this.cardSecurityManagementService.saveBatch(cards);
                  }
               }
            } catch (Exception var10) {
               throw var10;
            }
         }
      } else {
         throw new BizException(ResponseStatusEnum.BAD_REQUEST);
      }
   }

   @Override
   public Map<String, Object> getDashboard() {
      Long userId = StpUtil.getLoginIdAsLong();
      UserPoints userPoints = this.userPointsService.getMyPoints();
      long cardRegisteredUserCount = this.embyUserService
         .count(
            (Wrapper<EmbyUser>)((QueryWrapper)new QueryWrapper().eq("register_channel", Integer.valueOf(RegisterChannelEnum.CARD_REGISTER.getCode())))
               .inSql(
                  "id", "SELECT DISTINCT user_id FROM card_security_management WHERE user_id IS NOT NULL AND card_status = 1 AND distributor_id = " + userId
               )
         );
      Map<String, Object> result = new HashMap<>();
      result.put("inviteCount", cardRegisteredUserCount);
      result.put("cardRegisteredUserCount", cardRegisteredUserCount);
      result.put("points", userPoints != null && userPoints.getPointsBalance() != null ? userPoints.getPointsBalance() : 0);
      result.put("totalEarned", userPoints != null && userPoints.getTotalEarned() != null ? userPoints.getTotalEarned() : 0);
      return result;
   }
}
