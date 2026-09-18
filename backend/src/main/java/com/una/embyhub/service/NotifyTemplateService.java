package com.una.embyhub.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.una.embyhub.model.dto.request.notifytemplate.NotifyTemplateSave;
import com.una.embyhub.model.dto.request.notifytemplate.NotifyTemplateUpdate;
import com.una.embyhub.model.dto.response.notifytemplate.NotifyTemplateResponse;
import com.una.embyhub.model.dto.response.notifytemplate.NotifyTemplateVariableResponse;
import com.una.embyhub.model.entity.NotifyTemplate;
import java.util.List;

public interface NotifyTemplateService extends IService<NotifyTemplate> {
   List<NotifyTemplateResponse> select();

   void add(NotifyTemplateSave notifyTemplateSave);

   void update(NotifyTemplateUpdate notifyTemplateUpdate);

   void delete(Long id);

   List<NotifyTemplateVariableResponse> listTemplateVariables();
}
