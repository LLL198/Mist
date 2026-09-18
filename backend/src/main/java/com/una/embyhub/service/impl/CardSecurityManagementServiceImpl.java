package com.una.embyhub.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.diboot.core.binding.QueryBuilder;
import com.diboot.core.util.BeanUtils;
import com.una.embyhub.config.common.MpConvert;
import com.una.embyhub.config.common.enums.HostLineTypeEnum;
import com.una.embyhub.config.common.enums.ResponseStatusEnum;
import com.una.embyhub.config.common.exception.BizException;
import com.una.embyhub.config.mybatis.MybatisPlusPage;
import com.una.embyhub.mapper.CardSecurityManagementMapper;
import com.una.embyhub.mapper.EmbyUserMapper;
import com.una.embyhub.model.dto.request.cardsecuritymanagement.CardSecurityManagementRequest;
import com.una.embyhub.model.dto.request.embyinfo.EmbyInfoUserOptionsRequest;
import com.una.embyhub.model.dto.response.cardsecuritymanagement.CardSecurityManagementResponse;
import com.una.embyhub.model.dto.response.cardsecuritymanagement.CardSecurityManagementStatusResponse;
import com.una.embyhub.model.entity.CardSecurityManagement;
import com.una.embyhub.model.entity.EmbyInfo;
import com.una.embyhub.model.entity.EmbyUser;
import com.una.embyhub.service.CardSecurityManagementService;
import com.una.embyhub.service.EmbyInfoService;
import com.una.embyhub.service.EmbyUserService;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestParam;

@Service
@Transactional(
   rollbackFor = {Exception.class}
)
public class CardSecurityManagementServiceImpl
   extends ServiceImpl<CardSecurityManagementMapper, CardSecurityManagement>
   implements CardSecurityManagementService {
   @Autowired
   private EmbyUserService embyUserService;
   @Autowired
   private EmbyUserMapper embyUserMapper;
   @Autowired
   private EmbyInfoService embyInfoService;

   @Override
   public Page<CardSecurityManagementResponse> select(MybatisPlusPage<CardSecurityManagementRequest> page) {
      QueryWrapper queryWrapper = QueryBuilder.toQueryWrapper(page.getObject());
      if (page.getObject().getQueryIsDistributor() != null) {
         if (page.getObject().getQueryIsDistributor()) {
            queryWrapper.gt("distributor_id", Integer.valueOf(0));
         } else {
            queryWrapper.apply("(distributor_id IS NULL OR distributor_id = 0)", new Object[0]);
         }
      }

      if (StringUtils.hasText(page.getObject().getDistributorName())) {
         List<Long> userIds = this.embyUserService
            .lambdaQuery()
            .like(EmbyUser::getEmbyUserName, page.getObject().getDistributorName())
            .select(EmbyUser::getId)
            .list()
            .stream()
            .map(EmbyUser::getId)
            .toList();
         if (userIds.isEmpty()) {
            queryWrapper.apply("1 = 2", new Object[0]);
         } else {
            queryWrapper.in("distributor_id", userIds);
         }
      }

      queryWrapper.orderByDesc("id");
      return MpConvert.page(queryWrapper, this.getBaseMapper(), CardSecurityManagementResponse.class, page.getCurrent(), page.getSize(), page.getOrders());
   }

   @Override
   public Page<CardSecurityManagementResponse> selectDistributor(MybatisPlusPage<CardSecurityManagementRequest> page) {
      boolean isAdmin = StpUtil.hasPermission("admin");
      QueryWrapper queryWrapper = QueryBuilder.toQueryWrapper(page.getObject());
      if (StringUtils.hasText(page.getObject().getDistributorName())) {
         List<Long> userIds = this.embyUserService
            .lambdaQuery()
            .like(EmbyUser::getEmbyUserName, page.getObject().getDistributorName())
            .select(EmbyUser::getId)
            .list()
            .stream()
            .map(EmbyUser::getId)
            .toList();
         if (userIds.isEmpty()) {
            queryWrapper.apply("1 = 2", new Object[0]);
         } else {
            queryWrapper.in("distributor_id", userIds);
         }
      }

      if (!isAdmin) {
         EmbyUser embyUser = (EmbyUser)StpUtil.getSession().get("user");
         if (embyUser == null || embyUser.getIsDistributor() == null || embyUser.getIsDistributor() == 0) {
            throw new BizException(ResponseStatusEnum.PERMISSION_DENIED);
         }

         queryWrapper.eq("distributor_id", embyUser.getId());
      }

      queryWrapper.orderByDesc("id");
      return MpConvert.page(queryWrapper, this.getBaseMapper(), CardSecurityManagementResponse.class, page.getCurrent(), page.getSize(), page.getOrders());
   }

   @Override
   public CardSecurityManagementResponse cardPasswordVerification(String cardPassword) {
      EmbyUser embyUser = (EmbyUser)StpUtil.getSession().get("user");
      CardSecurityManagement cardSecurityManagement = new LambdaQueryChainWrapper<>(this.getBaseMapper())
         .eq(CardSecurityManagement::getCardPassword, cardPassword)
         .eq(CardSecurityManagement::getCardStatus, Integer.valueOf(0))
         .one();
      if (cardSecurityManagement == null) {
         throw new BizException(ResponseStatusEnum.BIZ_CARD_PASSWORD_ERROR);
      } else if (!Objects.equals(embyUser.getEmbyInfoId(), cardSecurityManagement.getEmbyInfoId())) {
         throw new BizException(ResponseStatusEnum.PLEASE_USE_CORRECT_CARD_KEY);
      } else {
         EmbyInfo embyInfo = new LambdaQueryChainWrapper<>(this.embyInfoService.getBaseMapper())
            .eq(EmbyInfo::getId, cardSecurityManagement.getEmbyInfoId())
            .eq(EmbyInfo::getEnabled, Integer.valueOf(1))
            .eq(EmbyInfo::getStatus, Integer.valueOf(0))
            .one();
         if (embyInfo == null) {
            throw new BizException(ResponseStatusEnum.EMBY_INFO_DISABLED);
         } else {
            CardSecurityManagementResponse cardSecurityManagementResponse = BeanUtils.convert(cardSecurityManagement, CardSecurityManagementResponse.class);
            Integer renewedHostLineType = HostLineTypeEnum.normalize(cardSecurityManagement.getHostLineType());
            if (renewedHostLineType == HostLineTypeEnum.WHITELIST.getCode()) {
               cardSecurityManagementResponse.setExpirationDate(null);
               return cardSecurityManagementResponse;
            } else {
               Date date = new Date();
               Date currentExpiration = HostLineTypeEnum.normalize(embyUser.getHostLineType()) == HostLineTypeEnum.WHITELIST.getCode()
                  ? null
                  : embyUser.getExpirationDate();
               if (currentExpiration != null && DateUtil.compare(currentExpiration, date) >= 0) {
                  cardSecurityManagementResponse.setExpirationDate(DateUtil.offsetDay(currentExpiration, cardSecurityManagement.getCardValidity()));
               } else {
                  cardSecurityManagementResponse.setExpirationDate(DateUtil.offsetDay(date, cardSecurityManagement.getCardValidity()));
               }

               return cardSecurityManagementResponse;
            }
         }
      }
   }

   @Override
   public List<String> addCardSecurityManagementList(
      Integer count, @RequestParam Integer day, @RequestParam Long embyInfoId, Integer hostLineType, String remarks
   ) {
      return this.addCardSecurityManagementList(count, day, embyInfoId, hostLineType, remarks, null, null);
   }

   @Override
   public List<String> addCardSecurityManagementList(
      Integer count, @RequestParam Integer day, @RequestParam Long embyInfoId, Integer hostLineType, String remarks, String creatorName
   ) {
      return this.addCardSecurityManagementList(count, day, embyInfoId, hostLineType, remarks, null, creatorName);
   }

   @Override
   public List<String> addCardSecurityManagementList(
      Integer count, @RequestParam Integer day, @RequestParam Long embyInfoId, Integer hostLineType, String remarks, String copyfromuserid, String creatorName
   ) {
      EmbyInfo embyInfo = new LambdaQueryChainWrapper<>(this.embyInfoService.getBaseMapper())
         .eq(EmbyInfo::getId, embyInfoId)
         .eq(EmbyInfo::getEnabled, Integer.valueOf(1))
         .one();
      if (embyInfo == null) {
         throw new BizException(ResponseStatusEnum.EMBY_INFO_DISABLED);
      } else {
         Integer normalizedHostLineType = HostLineTypeEnum.normalize(hostLineType);
         String normalizedRemarks = StringUtils.hasText(remarks) ? remarks.trim() : null;
         String normalizedCopyfromuserid = this.normalizeCopyfromuserid(embyInfoId, copyfromuserid);
         List<CardSecurityManagement> cardSecurityManagementList = new ArrayList<>();
         List<String> cardPasswordList = new ArrayList<>();

         for (int i = 0; i < count; i++) {
            String cardPassword = IdUtil.fastSimpleUUID();
            CardSecurityManagement cardSecurityManagement = new CardSecurityManagement();
            cardSecurityManagement.setCardPassword(cardPassword);
            cardSecurityManagement.setCardValidity(day);
            cardSecurityManagement.setEmbyInfoId(embyInfoId);
            cardSecurityManagement.setCopyfromuserid(normalizedCopyfromuserid);
            cardSecurityManagement.setHostLineType(normalizedHostLineType);
            cardSecurityManagement.setRemarks(normalizedRemarks);
            if (StringUtils.hasText(creatorName)) {
               cardSecurityManagement.setCreateUserName(creatorName.trim());
            }

            cardPasswordList.add(cardPassword);
            cardSecurityManagementList.add(cardSecurityManagement);
         }

         this.saveBatch(cardSecurityManagementList);
         return cardPasswordList;
      }
   }

   String normalizeCopyfromuserid(Long embyInfoId, String copyfromuserid) {
      if (!StringUtils.hasText(copyfromuserid)) {
         return null;
      } else {
         String normalized = copyfromuserid.trim();
         EmbyInfoUserOptionsRequest request = new EmbyInfoUserOptionsRequest();
         request.setEmbyInfoId(embyInfoId);
         boolean selectable = this.embyInfoService.listSelectableUsers(request).stream().anyMatch(user -> normalized.equals(user.getId()));
         if (!selectable) {
            throw new BizException(ResponseStatusEnum.BAD_REQUEST.getCode(), "所选模板用户不可用，请重新选择");
         } else {
            return normalized;
         }
      }
   }

   @Override
   public CardSecurityManagement createPaymentCard(Long paymentOrderId, Integer day, Long embyInfoId, Integer hostLineType, String remarks) {
      if (paymentOrderId != null && day != null && day >= 1 && day <= 3650) {
         CardSecurityManagement existing = new LambdaQueryChainWrapper<>(this.getBaseMapper())
            .eq(CardSecurityManagement::getPaymentOrderId, paymentOrderId)
            .oneOpt()
            .orElse(null);
         if (existing != null) {
            return existing;
         } else {
            EmbyInfo embyInfo = new LambdaQueryChainWrapper<>(this.embyInfoService.getBaseMapper())
               .eq(EmbyInfo::getId, embyInfoId)
               .eq(EmbyInfo::getEnabled, Integer.valueOf(1))
               .eq(EmbyInfo::getStatus, Integer.valueOf(0))
               .oneOpt()
               .orElse(null);
            if (embyInfo == null) {
               throw new BizException(ResponseStatusEnum.EMBY_INFO_DISABLED);
            } else {
               CardSecurityManagement card = new CardSecurityManagement();
               card.setCardPassword(IdUtil.fastSimpleUUID());
               card.setCardValidity(day);
               card.setCardStatus(0);
               card.setEmbyInfoId(embyInfoId);
               card.setHostLineType(HostLineTypeEnum.normalize(hostLineType));
               card.setRemarks(StringUtils.hasText(remarks) ? remarks.trim() : null);
               card.setPaymentOrderId(paymentOrderId);
               card.setCreateUserName("支付系统");
               this.save(card);
               return card;
            }
         }
      } else {
         throw new BizException(ResponseStatusEnum.BAD_REQUEST);
      }
   }

   @Override
   public void deleteCardSecurityManagementList(List<Long> idList) {
      this.removeByIds(idList);
   }

   @Override
   public CardSecurityManagementStatusResponse cardSecurityManagementListStatus() {
      Long allCardSecurityManagementCount = new LambdaQueryChainWrapper<>(this.getBaseMapper()).count();
      Long notUsedCardSecurityManagementCount = new LambdaQueryChainWrapper<>(this.getBaseMapper())
         .eq(CardSecurityManagement::getCardStatus, Integer.valueOf(0))
         .count();
      Long usedCardSecurityManagementCount = new LambdaQueryChainWrapper<>(this.getBaseMapper())
         .eq(CardSecurityManagement::getCardStatus, Integer.valueOf(1))
         .count();
      CardSecurityManagementStatusResponse cardSecurityManagementStatusResponse = new CardSecurityManagementStatusResponse();
      cardSecurityManagementStatusResponse.setAllCardSecurityManagementCount(allCardSecurityManagementCount);
      cardSecurityManagementStatusResponse.setNotUsedCardSecurityManagementCount(notUsedCardSecurityManagementCount);
      cardSecurityManagementStatusResponse.setUsedCardSecurityManagementCount(usedCardSecurityManagementCount);
      boolean isAdmin = StpUtil.hasPermission("admin");
      if (isAdmin) {
         Long distributorAllCardCount = new LambdaQueryChainWrapper<>(this.getBaseMapper())
            .gt(CardSecurityManagement::getDistributorId, Integer.valueOf(0))
            .count();
         Long distributorNotUsedCardCount = new LambdaQueryChainWrapper<>(this.getBaseMapper())
            .gt(CardSecurityManagement::getDistributorId, Integer.valueOf(0))
            .eq(CardSecurityManagement::getCardStatus, Integer.valueOf(0))
            .count();
         Long distributorUsedCardCount = new LambdaQueryChainWrapper<>(this.getBaseMapper())
            .gt(CardSecurityManagement::getDistributorId, Integer.valueOf(0))
            .eq(CardSecurityManagement::getCardStatus, Integer.valueOf(1))
            .count();
         cardSecurityManagementStatusResponse.setDistributorAllCardCount(distributorAllCardCount);
         cardSecurityManagementStatusResponse.setDistributorNotUsedCardCount(distributorNotUsedCardCount);
         cardSecurityManagementStatusResponse.setDistributorUsedCardCount(distributorUsedCardCount);
      }

      return cardSecurityManagementStatusResponse;
   }

   @Override
   public CardSecurityManagementStatusResponse cardSecurityManagementListStatusDistributor() {
      boolean isAdmin = StpUtil.hasPermission("admin");
      Long allCardSecurityManagementCount;
      Long notUsedCardSecurityManagementCount;
      Long usedCardSecurityManagementCount;
      if (isAdmin) {
         allCardSecurityManagementCount = new LambdaQueryChainWrapper<>(this.getBaseMapper()).count();
         notUsedCardSecurityManagementCount = new LambdaQueryChainWrapper<>(this.getBaseMapper())
            .eq(CardSecurityManagement::getCardStatus, Integer.valueOf(0))
            .count();
         usedCardSecurityManagementCount = new LambdaQueryChainWrapper<>(this.getBaseMapper())
            .eq(CardSecurityManagement::getCardStatus, Integer.valueOf(1))
            .count();
      } else {
         EmbyUser embyUser = (EmbyUser)StpUtil.getSession().get("user");
         if (embyUser == null || embyUser.getIsDistributor() == null || embyUser.getIsDistributor() == 0) {
            throw new BizException(ResponseStatusEnum.PERMISSION_DENIED);
         }

         Long distributorId = embyUser.getId();
         allCardSecurityManagementCount = new LambdaQueryChainWrapper<>(this.getBaseMapper())
            .eq(CardSecurityManagement::getDistributorId, distributorId)
            .count();
         notUsedCardSecurityManagementCount = new LambdaQueryChainWrapper<>(this.getBaseMapper())
            .eq(CardSecurityManagement::getDistributorId, distributorId)
            .eq(CardSecurityManagement::getCardStatus, Integer.valueOf(0))
            .count();
         usedCardSecurityManagementCount = new LambdaQueryChainWrapper<>(this.getBaseMapper())
            .eq(CardSecurityManagement::getDistributorId, distributorId)
            .eq(CardSecurityManagement::getCardStatus, Integer.valueOf(1))
            .count();
      }

      CardSecurityManagementStatusResponse cardSecurityManagementStatusResponse = new CardSecurityManagementStatusResponse();
      cardSecurityManagementStatusResponse.setAllCardSecurityManagementCount(allCardSecurityManagementCount);
      cardSecurityManagementStatusResponse.setNotUsedCardSecurityManagementCount(notUsedCardSecurityManagementCount);
      cardSecurityManagementStatusResponse.setUsedCardSecurityManagementCount(usedCardSecurityManagementCount);
      if (isAdmin) {
         Long distributorAllCardCount = new LambdaQueryChainWrapper<>(this.getBaseMapper())
            .gt(CardSecurityManagement::getDistributorId, Integer.valueOf(0))
            .count();
         Long distributorNotUsedCardCount = new LambdaQueryChainWrapper<>(this.getBaseMapper())
            .gt(CardSecurityManagement::getDistributorId, Integer.valueOf(0))
            .eq(CardSecurityManagement::getCardStatus, Integer.valueOf(0))
            .count();
         Long distributorUsedCardCount = new LambdaQueryChainWrapper<>(this.getBaseMapper())
            .gt(CardSecurityManagement::getDistributorId, Integer.valueOf(0))
            .eq(CardSecurityManagement::getCardStatus, Integer.valueOf(1))
            .count();
         cardSecurityManagementStatusResponse.setDistributorAllCardCount(distributorAllCardCount);
         cardSecurityManagementStatusResponse.setDistributorNotUsedCardCount(distributorNotUsedCardCount);
         cardSecurityManagementStatusResponse.setDistributorUsedCardCount(distributorUsedCardCount);
      } else {
         cardSecurityManagementStatusResponse.setDistributorAllCardCount(allCardSecurityManagementCount);
         cardSecurityManagementStatusResponse.setDistributorNotUsedCardCount(notUsedCardSecurityManagementCount);
         cardSecurityManagementStatusResponse.setDistributorUsedCardCount(usedCardSecurityManagementCount);
      }

      return cardSecurityManagementStatusResponse;
   }
}
