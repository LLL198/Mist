package com.una.embyhub.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.diboot.core.binding.QueryBuilder;
import com.una.embyhub.config.common.MpConvert;
import com.una.embyhub.config.common.enums.ResponseStatusEnum;
import com.una.embyhub.config.common.exception.BizException;
import com.una.embyhub.config.mybatis.MybatisPlusPage;
import com.una.embyhub.mapper.RequestPackagesCardSecurityManagementMapper;
import com.una.embyhub.model.dto.request.requestpackagescardsecuritymanagement.RequestPackagesCardSecurityManagementRequest;
import com.una.embyhub.model.dto.response.requestpackagescardsecuritymanagement.RequestPackagesCardSecurityManagementResponse;
import com.una.embyhub.model.dto.response.requestpackagescardsecuritymanagement.RequestPackagesCardSecurityManagementStatusResponse;
import com.una.embyhub.model.entity.BaseEntity;
import com.una.embyhub.model.entity.EmbyUser;
import com.una.embyhub.model.entity.RequestPackagesCardSecurityManagement;
import com.una.embyhub.service.EmbyUserService;
import com.una.embyhub.service.RequestPackagesCardSecurityManagementService;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class RequestPackagesCardSecurityManagementServiceImpl
   extends ServiceImpl<RequestPackagesCardSecurityManagementMapper, RequestPackagesCardSecurityManagement>
   implements RequestPackagesCardSecurityManagementService {
   @Autowired
   private EmbyUserService embyUserService;

   @Override
   public Page<RequestPackagesCardSecurityManagementResponse> select(MybatisPlusPage<RequestPackagesCardSecurityManagementRequest> page) {
      EmbyUser embyUserData = (EmbyUser)StpUtil.getSession().get("user");
      QueryWrapper queryWrapper = QueryBuilder.toQueryWrapper(page.getObject());
      if (StringUtils.hasText(page.getObject().getEmbyUserName())) {
         EmbyUser embyUser = new LambdaQueryChainWrapper<>(this.embyUserService.getBaseMapper())
            .eq(EmbyUser::getEmbyUserName, page.getObject().getEmbyUserName())
            .one();
         if (embyUser == null) {
            queryWrapper.eq("user_id", Integer.valueOf(0));
         } else {
            queryWrapper.eq("user_id", embyUser.getId());
         }
      }

      if (embyUserData.getIsAdmin() != 1) {
         queryWrapper.eq("user_id", embyUserData.getId());
      }

      queryWrapper.orderByDesc("id");
      return MpConvert.page(
         queryWrapper, this.getBaseMapper(), RequestPackagesCardSecurityManagementResponse.class, page.getCurrent(), page.getSize(), page.getOrders()
      );
   }

   @Override
   public void verification(String cardPassword) {
      RequestPackagesCardSecurityManagement requestPackagesCardSecurityManagement = new LambdaQueryChainWrapper<>(this.getBaseMapper())
         .eq(RequestPackagesCardSecurityManagement::getCardPassword, cardPassword)
         .eq(RequestPackagesCardSecurityManagement::getCardStatus, Integer.valueOf(0))
         .one();
      if (requestPackagesCardSecurityManagement == null) {
         throw new BizException(ResponseStatusEnum.BIZ_REQUEST_PACKAGES_CARD_PASSWORD_ERROR);
      } else {
         EmbyUser embyUser = (EmbyUser)StpUtil.getSession().get("user");
         EmbyUser embyUserData = this.embyUserService.getById(embyUser.getId());
         embyUserData.setRequestPackagesCount(embyUserData.getRequestPackagesCount() + requestPackagesCardSecurityManagement.getCardCount());
         this.embyUserService.updateById(embyUserData);
         requestPackagesCardSecurityManagement.setCardStatus(1);
         requestPackagesCardSecurityManagement.setEmbyUserName(embyUserData.getEmbyUserName());
         requestPackagesCardSecurityManagement.setUserId(embyUserData.getId());
         this.updateById(requestPackagesCardSecurityManagement);
         StpUtil.getSession().set("user", embyUserData);
      }
   }

   @Override
   public List<String> add(Integer count, Integer num) {
      List<RequestPackagesCardSecurityManagement> requestPackagesCardSecurityManagementList = new ArrayList<>();
      List<String> cardPasswordList = new ArrayList<>();

      for (int i = 0; i < count; i++) {
         String cardPassword = IdUtil.fastSimpleUUID();
         RequestPackagesCardSecurityManagement requestPackagesCardSecurityManagement = new RequestPackagesCardSecurityManagement();
         requestPackagesCardSecurityManagement.setCardPassword(cardPassword);
         requestPackagesCardSecurityManagement.setCardCount(num);
         cardPasswordList.add(cardPassword);
         requestPackagesCardSecurityManagementList.add(requestPackagesCardSecurityManagement);
      }

      this.saveBatch(requestPackagesCardSecurityManagementList);
      return cardPasswordList;
   }

   @Override
   public void delete(List<Long> idList) {
      this.removeByIds(idList);
   }

   @Override
   public RequestPackagesCardSecurityManagementStatusResponse status() {
      Long allCardSecurityManagementCount = new LambdaQueryChainWrapper<>(this.getBaseMapper()).count();
      Long notUsedCardSecurityManagementCount = new LambdaQueryChainWrapper<>(this.getBaseMapper())
         .eq(RequestPackagesCardSecurityManagement::getCardStatus, Integer.valueOf(0))
         .count();
      Long usedCardSecurityManagementCount = new LambdaQueryChainWrapper<>(this.getBaseMapper())
         .eq(RequestPackagesCardSecurityManagement::getCardStatus, Integer.valueOf(1))
         .count();
      Long todayAddCount = new LambdaQueryChainWrapper<>(this.getBaseMapper())
         .ge(BaseEntity::getCreateDatetime, DateUtil.beginOfDay(new Date()))
         .le(BaseEntity::getCreateDatetime, DateUtil.endOfDay(new Date()))
         .count();
      RequestPackagesCardSecurityManagementStatusResponse requestPackagesCardSecurityManagementStatusResponse = new RequestPackagesCardSecurityManagementStatusResponse();
      requestPackagesCardSecurityManagementStatusResponse.setAllCardSecurityManagementCount(allCardSecurityManagementCount);
      requestPackagesCardSecurityManagementStatusResponse.setNotUsedCardSecurityManagementCount(notUsedCardSecurityManagementCount);
      requestPackagesCardSecurityManagementStatusResponse.setUsedCardSecurityManagementCount(usedCardSecurityManagementCount);
      requestPackagesCardSecurityManagementStatusResponse.setTodayAddCount(todayAddCount);
      return requestPackagesCardSecurityManagementStatusResponse;
   }
}
