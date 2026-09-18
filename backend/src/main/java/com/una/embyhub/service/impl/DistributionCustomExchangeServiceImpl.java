package com.una.embyhub.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.una.embyhub.config.common.MpConvert;
import com.una.embyhub.config.common.enums.ResponseStatusEnum;
import com.una.embyhub.config.common.exception.BizException;
import com.una.embyhub.config.mybatis.MybatisPlusPage;
import com.una.embyhub.mapper.DistributionCustomExchangeMapper;
import com.una.embyhub.model.dto.request.distribution.DistributionCustomExchangeRequest;
import com.una.embyhub.model.dto.request.distribution.DistributionCustomExchangeReviewRequest;
import com.una.embyhub.model.dto.response.distribution.DistributionCustomExchangeResponse;
import com.una.embyhub.model.entity.DistributionCustomExchange;
import com.una.embyhub.model.entity.EmbyUser;
import com.una.embyhub.service.DistributionCustomExchangeService;
import com.una.embyhub.service.EmbyUserService;
import com.una.embyhub.service.UserPointsService;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Service
public class DistributionCustomExchangeServiceImpl
   extends ServiceImpl<DistributionCustomExchangeMapper, DistributionCustomExchange>
   implements DistributionCustomExchangeService {
   @Autowired
   private EmbyUserService embyUserService;
   @Autowired
   private UserPointsService userPointsService;

   @Transactional(
      rollbackFor = {Exception.class}
   )
   @Override
   public Page<DistributionCustomExchangeResponse> queryCustomExchange(MybatisPlusPage<DistributionCustomExchangeRequest> page) {
      Long currentUserId = StpUtil.getLoginIdAsLong();
      boolean isAdmin = StpUtil.hasPermission("admin");
      EmbyUser currentUser = this.embyUserService.getById(currentUserId);
      if (isAdmin || currentUser.getIsDistributor() != null && currentUser.getIsDistributor() != 0) {
         LambdaQueryChainWrapper<DistributionCustomExchange> query = new LambdaQueryChainWrapper<>(this.baseMapper);
         if (!isAdmin) {
            query.eq(DistributionCustomExchange::getUserId, currentUserId);
         } else if (page.getObject() != null) {
            if (page.getObject().getUserId() != null) {
               query.eq(DistributionCustomExchange::getUserId, page.getObject().getUserId());
            }

            if (StringUtils.hasText(page.getObject().getUserName())) {
               List<EmbyUser> users = this.embyUserService.lambdaQuery().like(EmbyUser::getEmbyUserName, page.getObject().getUserName()).list();
               if (users.isEmpty()) {
                  query.eq(DistributionCustomExchange::getId, Long.valueOf(-1L));
               } else {
                  List<Long> userIds = users.stream().map(EmbyUser::getId).collect(Collectors.toList());
                  query.in(DistributionCustomExchange::getUserId, userIds);
               }
            }
         }

         if (page.getObject() != null) {
            if (StringUtils.hasText(page.getObject().getExchangeNo())) {
               query.like(DistributionCustomExchange::getExchangeNo, page.getObject().getExchangeNo());
            }

            if (page.getObject().getStatus() != null) {
               query.eq(DistributionCustomExchange::getStatus, page.getObject().getStatus());
            }
         }

         query.orderByDesc(DistributionCustomExchange::getId);
         new MpConvert();
         Page<DistributionCustomExchangeResponse> resultPage = MpConvert.page(
            query.getWrapper(), this.baseMapper, DistributionCustomExchangeResponse.class, page.getCurrent(), page.getSize(), page.getOrders()
         );
         if (!resultPage.getRecords().isEmpty()) {
            List<Long> userIds = resultPage.getRecords().stream().map(DistributionCustomExchangeResponse::getUserId).distinct().collect(Collectors.toList());
            if (!userIds.isEmpty()) {
               Map<Long, String> userMap = this.embyUserService
                  .listByIds(userIds)
                  .stream()
                  .collect(Collectors.toMap(EmbyUser::getId, EmbyUser::getEmbyUserName));

               for (DistributionCustomExchangeResponse item : resultPage.getRecords()) {
                  item.setUserName(userMap.get(item.getUserId()));
               }
            }
         }

         return resultPage;
      } else {
         throw new BizException(ResponseStatusEnum.PERMISSION_DENIED);
      }
   }

   @Transactional(
      rollbackFor = {Exception.class}
   )
   @Override
   public void reviewCustomExchange(DistributionCustomExchangeReviewRequest request) {
      if (request.getIdList() != null && !request.getIdList().isEmpty()) {
         for (Long id : request.getIdList()) {
            DistributionCustomExchange exchange = this.getById(id);
            if (exchange != null && exchange.getStatus() == 0) {
               exchange.setStatus(request.getStatus());
               exchange.setReviewComment(request.getReviewComment());
               this.updateById(exchange);
               if (request.getStatus() == 2) {
                  this.userPointsService
                     .addPoints(exchange.getUserId(), exchange.getPointsCost(), "EXCHANGE_REFUND", "自定义兑换拒绝退款: " + exchange.getProductName());
               }
            }
         }
      } else {
         throw new BizException(ResponseStatusEnum.BAD_REQUEST);
      }
   }
}
