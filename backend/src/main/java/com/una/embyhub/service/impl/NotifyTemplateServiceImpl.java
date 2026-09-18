package com.una.embyhub.service.impl;

import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.diboot.core.util.BeanUtils;
import com.una.embyhub.config.common.constants.NotifyTemplateVariableEnum;
import com.una.embyhub.config.common.enums.ResponseStatusEnum;
import com.una.embyhub.config.common.exception.BizException;
import com.una.embyhub.config.common.utils.NotifyTemplateCacheLoaderUtils;
import com.una.embyhub.mapper.NotifyTemplateMapper;
import com.una.embyhub.model.dto.request.notifytemplate.NotifyTemplateSave;
import com.una.embyhub.model.dto.request.notifytemplate.NotifyTemplateUpdate;
import com.una.embyhub.model.dto.response.notifytemplate.NotifyTemplateResponse;
import com.una.embyhub.model.dto.response.notifytemplate.NotifyTemplateVariableResponse;
import com.una.embyhub.model.entity.NotifyTemplate;
import com.una.embyhub.service.NotifyTemplateService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import org.springframework.util.StringUtils;

@Service
@Transactional(
   rollbackFor = {Exception.class}
)
public class NotifyTemplateServiceImpl extends ServiceImpl<NotifyTemplateMapper, NotifyTemplate> implements NotifyTemplateService {
   @Autowired
   private NotifyTemplateCacheLoaderUtils notifyTemplateCacheLoaderUtils;

   @Override
   public List<NotifyTemplateResponse> select() {
      return BeanUtils.convertList(this.list(), NotifyTemplateResponse.class);
   }

   @Override
   public void add(NotifyTemplateSave notifyTemplateSave) {
      this.validateTemplateUnique(notifyTemplateSave.getTemplateCode(), notifyTemplateSave.getChannelType(), null);
      NotifyTemplate notifyTemplate = BeanUtils.convert(notifyTemplateSave, NotifyTemplate.class);
      if (!StringUtils.hasText(notifyTemplate.getChannelType())) {
         notifyTemplate.setChannelType("common");
      }

      this.save(notifyTemplate);
      this.refreshCacheAfterCommit();
   }

   @Override
   public void update(NotifyTemplateUpdate notifyTemplateUpdate) {
      this.validateTemplateUnique(notifyTemplateUpdate.getTemplateCode(), notifyTemplateUpdate.getChannelType(), notifyTemplateUpdate.getId());
      NotifyTemplate notifyTemplate = BeanUtils.convert(notifyTemplateUpdate, NotifyTemplate.class);
      if (!StringUtils.hasText(notifyTemplate.getChannelType())) {
         notifyTemplate.setChannelType("common");
      }

      this.updateById(notifyTemplate);
      this.refreshCacheAfterCommit();
   }

   @Override
   public void delete(Long id) {
      this.removeById(id);
      this.refreshCacheAfterCommit();
   }

   @Override
   public List<NotifyTemplateVariableResponse> listTemplateVariables() {
      return NotifyTemplateVariableEnum.toResponseList();
   }

   private void validateTemplateUnique(String templateCode, String channelType, Long id) {
      if (!StringUtils.hasText(templateCode)) {
         throw new BizException(ResponseStatusEnum.NOTIFY_TEMPLATE_CODE_EMPTY);
      } else {
         String channel = StringUtils.hasText(channelType) ? channelType : "common";
         Long count = new LambdaQueryChainWrapper<>(this.getBaseMapper())
            .eq(NotifyTemplate::getTemplateCode, templateCode)
            .eq(NotifyTemplate::getChannelType, channel)
            .ne(id != null, NotifyTemplate::getId, id)
            .count();
         if (count > 0L) {
            throw new BizException(ResponseStatusEnum.NOTIFY_TEMPLATE_CODE_EXIST);
         }
      }
   }

   private void refreshCacheAfterCommit() {
      if (TransactionSynchronizationManager.isSynchronizationActive()) {
         TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronization() {
            @Override
            public void afterCommit() {
               NotifyTemplateServiceImpl.this.notifyTemplateCacheLoaderUtils.refreshCache();
            }
         });
      } else {
         this.notifyTemplateCacheLoaderUtils.refreshCache();
      }
   }
}
