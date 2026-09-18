package com.una.embyhub.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.una.embyhub.model.dto.request.notifytemplate.NotifyTemplateSave;
import com.una.embyhub.model.dto.request.notifytemplate.NotifyTemplateUpdate;
import com.una.embyhub.model.dto.response.notifytemplate.NotifyTemplateResponse;
import com.una.embyhub.model.dto.response.notifytemplate.NotifyTemplateVariableResponse;
import com.una.embyhub.service.NotifyTemplateService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping({"notifyTemplate"})
public class NotifyTemplateController {
   @Autowired
   private NotifyTemplateService notifyTemplateService;

   @PostMapping({"select"})
   @SaCheckPermission({"admin"})
   public List<NotifyTemplateResponse> select() {
      return this.notifyTemplateService.select();
   }

   @PostMapping({"add"})
   @SaCheckPermission({"admin"})
   public void add(@RequestBody NotifyTemplateSave notifyTemplateSave) {
      this.notifyTemplateService.add(notifyTemplateSave);
   }

   @PostMapping({"update"})
   @SaCheckPermission({"admin"})
   public void update(@RequestBody NotifyTemplateUpdate notifyTemplateUpdate) {
      this.notifyTemplateService.update(notifyTemplateUpdate);
   }

   @PostMapping({"delete"})
   @SaCheckPermission({"admin"})
   public void delete(@RequestParam Long id) {
      this.notifyTemplateService.delete(id);
   }

   @PostMapping({"variables"})
   @SaCheckPermission({"admin"})
   public List<NotifyTemplateVariableResponse> variables() {
      return this.notifyTemplateService.listTemplateVariables();
   }
}
