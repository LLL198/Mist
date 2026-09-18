package com.una.embyhub.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.una.embyhub.config.common.enums.ResponseStatusEnum;
import com.una.embyhub.config.common.exception.BizException;
import com.una.embyhub.mapper.PointsExchangeProductMapper;
import com.una.embyhub.model.dto.request.distributionapplication.ExchangeRequest;
import com.una.embyhub.model.dto.request.distributionapplication.ProductListRequest;
import com.una.embyhub.model.dto.request.distributionapplication.ProductSaveRequest;
import com.una.embyhub.model.entity.CardSecurityManagement;
import com.una.embyhub.model.entity.DistributionCustomExchange;
import com.una.embyhub.model.entity.EmbyUser;
import com.una.embyhub.model.entity.PointsExchangeProduct;
import com.una.embyhub.service.CardSecurityManagementService;
import com.una.embyhub.service.DistributionCustomExchangeService;
import com.una.embyhub.service.EmbyUserService;
import com.una.embyhub.service.PointsExchangeProductService;
import com.una.embyhub.service.UserPointsService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Service
public class PointsExchangeProductServiceImpl extends ServiceImpl<PointsExchangeProductMapper, PointsExchangeProduct> implements PointsExchangeProductService {
   @Autowired
   private UserPointsService userPointsService;
   @Autowired
   private CardSecurityManagementService cardSecurityManagementService;
   @Autowired
   private EmbyUserService embyUserService;
   @Autowired
   private DistributionCustomExchangeService distributionCustomExchangeService;

   @Override
   public List<PointsExchangeProduct> listProducts(ProductListRequest request) {
      LambdaQueryChainWrapper<PointsExchangeProduct> query = new LambdaQueryChainWrapper<>(this.baseMapper);
      if (request != null) {
         if (StringUtils.hasText(request.getName())) {
            query.like(PointsExchangeProduct::getName, request.getName());
         }

         if (request.getIsEnabled() != null) {
            query.eq(PointsExchangeProduct::getIsEnabled, request.getIsEnabled());
         }

         if (StringUtils.hasText(request.getProductType())) {
            query.eq(PointsExchangeProduct::getProductType, request.getProductType());
         }
      } else {
         query.eq(PointsExchangeProduct::getIsEnabled, Integer.valueOf(1));
      }

      return query.orderByDesc(PointsExchangeProduct::getSortOrder).list();
   }

   @Override
   public void saveProduct(ProductSaveRequest request) {
      PointsExchangeProduct product = new PointsExchangeProduct();
      if (request.getId() != null) {
         product.setId(request.getId());
      }

      product.setName(request.getName());
      product.setProductType(request.getProductType());
      product.setPointsCost(request.getPointsCost());
      product.setProductValue(request.getProductValue());
      product.setEmbyInfoId(request.getEmbyInfoId());
      product.setIsEnabled(request.getIsEnabled());
      product.setSortOrder(request.getSortOrder());
      this.saveOrUpdate(product);
   }

   @Override
   public void deleteProduct(Long id) {
      this.removeById(id);
   }

   @Transactional(
      rollbackFor = {Exception.class}
   )
   @Override
   public void exchange(ExchangeRequest request) {
      Long userId = StpUtil.getLoginIdAsLong();
      PointsExchangeProduct product = this.getById(request.getProductId());
      if (product != null && product.getIsEnabled() != 0) {
         EmbyUser user = this.embyUserService.getById(userId);
         if (user.getIsDistributor() != null && user.getIsDistributor() != 0) {
            Long targetEmbyInfoId = product.getEmbyInfoId();
            this.userPointsService.deductPoints(userId, product.getPointsCost(), "EXCHANGE_SPEND", "兑换商品: " + product.getName());
            String productType = product.getProductType();
            switch (productType) {
               case "CARD":
                  if (targetEmbyInfoId == null) {
                     throw new BizException(ResponseStatusEnum.BAD_REQUEST.getCode(), "该商品未配置服务器且未指定服务器ID");
                  }

                  CardSecurityManagement card = new CardSecurityManagement();
                  card.setCardPassword(IdUtil.fastSimpleUUID());
                  card.setCardValidity(product.getProductValue());
                  card.setEmbyInfoId(targetEmbyInfoId);
                  card.setDistributorId(userId);
                  card.setCardStatus(0);
                  this.cardSecurityManagementService.save(card);
                  break;
               case "CUSTOM":
                  DistributionCustomExchange customExchange = new DistributionCustomExchange();
                  customExchange.setExchangeNo("DCE" + IdUtil.getSnowflakeNextIdStr());
                  customExchange.setUserId(userId);
                  customExchange.setProductId(product.getId());
                  customExchange.setProductName(product.getName());
                  customExchange.setProductValue(product.getProductValue());
                  customExchange.setPointsCost(product.getPointsCost());
                  customExchange.setEmbyInfoId(targetEmbyInfoId);
                  customExchange.setStatus(0);
                  this.distributionCustomExchangeService.save(customExchange);
                  break;
               default:
                  throw new BizException(ResponseStatusEnum.BAD_REQUEST.getCode(), "未知的商品类型: " + productType);
            }
         } else {
            throw new BizException(ResponseStatusEnum.PERMISSION_DENIED);
         }
      } else {
         throw new BizException(ResponseStatusEnum.PRODUCT_NOT_EXIST);
      }
   }
}
