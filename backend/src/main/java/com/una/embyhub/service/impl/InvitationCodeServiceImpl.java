package com.una.embyhub.service.impl;

import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.conditions.update.LambdaUpdateChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.diboot.core.util.BeanUtils;
import com.una.embyhub.config.common.enums.HostLineTypeEnum;
import com.una.embyhub.config.common.enums.ResponseStatusEnum;
import com.una.embyhub.config.common.exception.BizException;
import com.una.embyhub.config.common.utils.EmbyInfoCacheManagerUtils;
import com.una.embyhub.mapper.InvitationCodeMapper;
import com.una.embyhub.model.dto.request.invitation.InvitationCodeGenerateRequest;
import com.una.embyhub.model.dto.request.invitation.InvitationCodeQueryRequest;
import com.una.embyhub.model.dto.response.invitation.InvitationCodeResponse;
import com.una.embyhub.model.dto.response.invitation.InvitationCodeStatusResponse;
import com.una.embyhub.model.entity.BaseEntity;
import com.una.embyhub.model.entity.InvitationCode;
import com.una.embyhub.service.InvitationCodeService;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.Generated;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

@Service
@Transactional(
   rollbackFor = {Exception.class}
)
public class InvitationCodeServiceImpl extends ServiceImpl<InvitationCodeMapper, InvitationCode> implements InvitationCodeService {
   public static final String REWARD_UNIT_HOUR = "HOUR";
   public static final String REWARD_UNIT_DAY = "DAY";
   private static final int MAX_REWARD_HOURS = 87600;
   private static final int MAX_REWARD_DAYS = 3650;
   private final EmbyInfoCacheManagerUtils embyInfoCacheManager;

   @Override
   public List<InvitationCodeResponse> generate(InvitationCodeGenerateRequest request) {
      if (request != null && request.getEmbyInfoId() != null) {
         this.embyInfoCacheManager.getRequiredConfigById(request.getEmbyInfoId());
         int count = request.getCount() != null && request.getCount() >= 1 ? request.getCount() : 1;
         int usageLimit = request.getUsageLimit() != null && request.getUsageLimit() >= 1 ? request.getUsageLimit() : 1;
         List<InvitationCode> codes = new ArrayList<>(count);
         Integer hostLineType = HostLineTypeEnum.normalize(request.getHostLineType());
         int rewardDuration = this.normalizeRewardDuration(request.getRewardDuration());
         String rewardDurationUnit = normalizeRewardDurationUnit(request.getRewardDurationUnit());
         this.validateRewardDuration(rewardDuration, rewardDurationUnit);

         for (int i = 0; i < count; i++) {
            InvitationCode invitationCode = new InvitationCode();
            invitationCode.setCode(this.generateCode());
            invitationCode.setEmbyInfoId(request.getEmbyInfoId());
            invitationCode.setHostLineType(hostLineType);
            invitationCode.setStatus(0);
            invitationCode.setUsageLimit(usageLimit);
            invitationCode.setUsedCount(0);
            invitationCode.setExpireDatetime(request.getExpireDatetime());
            invitationCode.setValidityDays(request.getValidityDays());
            invitationCode.setRewardDuration(rewardDuration);
            invitationCode.setRewardDurationUnit(rewardDurationUnit);
            codes.add(invitationCode);
         }

         this.saveBatch(codes);
         return this.buildResponses(codes);
      } else {
         throw new BizException(ResponseStatusEnum.INVITATION_CODE_SERVER_REQUIRED);
      }
   }

   @Override
   public Page<InvitationCodeResponse> query(InvitationCodeQueryRequest request) {
      long current = request != null && request.getCurrent() > 0L ? request.getCurrent() : 1L;
      long size = request != null && request.getSize() > 0L ? request.getSize() : 10L;
      Page<InvitationCode> page = new Page<>(current, size);
      LambdaQueryChainWrapper<InvitationCode> query = new LambdaQueryChainWrapper<>(this.getBaseMapper());
      if (request != null) {
         if (StringUtils.hasText(request.getCode())) {
            query.like(InvitationCode::getCode, request.getCode());
         }

         if (request.getEmbyInfoId() != null) {
            query.eq(InvitationCode::getEmbyInfoId, request.getEmbyInfoId());
         }

         if (request.getHostLineType() != null) {
            query.eq(InvitationCode::getHostLineType, Integer.valueOf(HostLineTypeEnum.normalize(request.getHostLineType())));
         }

         if (request.getStatus() != null) {
            query.eq(InvitationCode::getStatus, request.getStatus());
         }
      }

      query.orderByDesc(BaseEntity::getCreateDatetime);
      Page<InvitationCode> resultPage = query.page(page);
      Page<InvitationCodeResponse> responsePage = new Page<>(resultPage.getCurrent(), resultPage.getSize(), resultPage.getTotal());
      responsePage.setRecords(this.buildResponses(resultPage.getRecords()));
      return responsePage;
   }

   @Override
   public void deleteById(Long invitationCodeId) {
      if (invitationCodeId != null) {
         this.removeById(invitationCodeId);
      }
   }

   @Override
   public InvitationCode useInvitationCode(String code, String usedBy) {
      if (!StringUtils.hasText(code)) {
         throw new BizException(ResponseStatusEnum.INVITATION_CODE_NOT_FOUND);
      } else {
         InvitationCode invitationCode = new LambdaQueryChainWrapper<>(this.getBaseMapper()).eq(InvitationCode::getCode, code).one();
         if (invitationCode == null) {
            throw new BizException(ResponseStatusEnum.INVITATION_CODE_NOT_FOUND);
         } else if (invitationCode.getExpireDatetime() != null && invitationCode.getExpireDatetime().before(new Date())) {
            throw new BizException(ResponseStatusEnum.INVITATION_CODE_EXPIRED);
         } else {
            int usageLimit = invitationCode.getUsageLimit() != null && invitationCode.getUsageLimit() >= 1 ? invitationCode.getUsageLimit() : 1;
            int usedCount = invitationCode.getUsedCount() == null ? 0 : invitationCode.getUsedCount();
            if (usedCount < usageLimit && (invitationCode.getStatus() == null || invitationCode.getStatus() != 1)) {
               int newUsedCount = usedCount + 1;
               boolean updated = new LambdaUpdateChainWrapper<>(this.getBaseMapper())
                  .eq(InvitationCode::getId, invitationCode.getId())
                  .eq(InvitationCode::getStatus, Integer.valueOf(0))
                  .eq(InvitationCode::getUsedCount, Integer.valueOf(usedCount))
                  .set(InvitationCode::getStatus, Integer.valueOf(newUsedCount >= usageLimit ? 1 : 0))
                  .set(InvitationCode::getUsedBy, usedBy)
                  .set(InvitationCode::getUsedDatetime, new Date())
                  .set(InvitationCode::getUsedCount, Integer.valueOf(newUsedCount))
                  .set(InvitationCode::getUsedDatetime, new Date())
                  .update();
               if (!updated) {
                  throw new BizException(ResponseStatusEnum.INVITATION_CODE_USAGE_EXCEEDED);
               } else {
                  invitationCode.setStatus(newUsedCount >= usageLimit ? 1 : 0);
                  invitationCode.setUsedBy(usedBy);
                  invitationCode.setUsedDatetime(new Date());
                  invitationCode.setUsedCount(newUsedCount);
                  return invitationCode;
               }
            } else {
               throw new BizException(ResponseStatusEnum.INVITATION_CODE_USAGE_EXCEEDED);
            }
         }
      }
   }

   public static String normalizeRewardDurationUnit(String unit) {
      if (!StringUtils.hasText(unit)) {
         return "DAY";
      } else {
         String normalized = unit.trim().toUpperCase(Locale.ROOT);
         return "HOUR".equals(normalized) ? "HOUR" : "DAY";
      }
   }

   private int normalizeRewardDuration(Integer rewardDuration) {
      return rewardDuration != null && rewardDuration >= 0 ? rewardDuration : 0;
   }

   private void validateRewardDuration(int rewardDuration, String rewardDurationUnit) {
      int max = "HOUR".equals(rewardDurationUnit) ? 87600 : 3650;
      if (rewardDuration > max) {
         String unitLabel = "HOUR".equals(rewardDurationUnit) ? "小时" : "天";
         throw new BizException(ResponseStatusEnum.BAD_REQUEST.getCode(), "新人奖励时长不能超过 " + max + " " + unitLabel);
      }
   }

   private String generateCode() {
      return IdUtil.fastSimpleUUID().toUpperCase();
   }

   private List<InvitationCodeResponse> buildResponses(List<InvitationCode> codes) {
      if (CollectionUtils.isEmpty(codes)) {
         return List.of();
      } else {
         Map<Long, String> serverNameMap = new HashMap<>();
         codes.stream().map(InvitationCode::getEmbyInfoId).distinct().forEach(id -> serverNameMap.put(id, this.resolveServerName(id)));
         return codes.stream().map(item -> {
            InvitationCodeResponse response = BeanUtils.convert(item, InvitationCodeResponse.class);
            response.setServerName(serverNameMap.get(item.getEmbyInfoId()));
            return response;
         }).collect(Collectors.toList());
      }
   }

   private String resolveServerName(Long embyInfoId) {
      try {
         return this.embyInfoCacheManager.getRequiredConfigById(embyInfoId).serverName();
      } catch (Exception var3) {
         return null;
      }
   }

   @Override
   public InvitationCodeStatusResponse status() {
      InvitationCodeStatusResponse response = new InvitationCodeStatusResponse();
      long allCount = new LambdaQueryChainWrapper<>(this.getBaseMapper()).count();
      response.setAllInvitationCodeCount(allCount);
      long usedCount = new LambdaQueryChainWrapper<>(this.getBaseMapper()).eq(InvitationCode::getStatus, Integer.valueOf(1)).count();
      response.setUsedInvitationCodeCount(usedCount);
      long availableCount = new LambdaQueryChainWrapper<>(this.getBaseMapper())
         .eq(InvitationCode::getStatus, Integer.valueOf(0))
         .nested(i -> i.isNull(InvitationCode::getExpireDatetime).or().gt(InvitationCode::getExpireDatetime, new Date()))
         .count();
      response.setAvailableInvitationCodeCount(availableCount);
      return response;
   }

   @Generated
   public InvitationCodeServiceImpl(final EmbyInfoCacheManagerUtils embyInfoCacheManager) {
      this.embyInfoCacheManager = embyInfoCacheManager;
   }
}
