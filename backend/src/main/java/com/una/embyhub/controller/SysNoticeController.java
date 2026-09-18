package com.una.embyhub.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.una.embyhub.config.mybatis.MybatisPlusPage;
import com.una.embyhub.model.dto.request.sysnotice.SysNoticePageRequest;
import com.una.embyhub.model.dto.response.sysnotice.SysNoticeResponse;
import com.una.embyhub.model.entity.SysNotice;
import com.una.embyhub.service.SysNoticeService;
import jakarta.validation.Valid;
import java.util.List;
import lombok.Generated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping({"sysNotice"})
public class SysNoticeController {
   private final SysNoticeService sysNoticeService;

   @PostMapping({"select"})
   public Page<SysNoticeResponse> select(@RequestBody @Valid MybatisPlusPage<SysNoticePageRequest> page) {
      return this.sysNoticeService.select(page);
   }

   @PostMapping({"publicExternal"})
   public List<SysNoticeResponse> publicExternal() {
      return this.sysNoticeService.publicExternalList();
   }

   @PostMapping({"siteList"})
   public List<SysNoticeResponse> siteList() {
      return this.sysNoticeService.siteList();
   }

   @PostMapping({"unreadCount"})
   public Long unreadCount() {
      return this.sysNoticeService.unreadCount();
   }

   @PostMapping({"read/{id}"})
   public Boolean markRead(@PathVariable Long id) {
      return this.sysNoticeService.markRead(id);
   }

   @PostMapping({"insert"})
   @SaCheckPermission({"admin"})
   public Boolean insert(@RequestBody SysNotice sysNotice) {
      return this.sysNoticeService.insert(sysNotice);
   }

   @PostMapping({"update"})
   @SaCheckPermission({"admin"})
   public Boolean update(@RequestBody SysNotice sysNotice) {
      return this.sysNoticeService.update(sysNotice);
   }

   @PostMapping({"delete/{id}"})
   @SaCheckPermission({"admin"})
   public Boolean delete(@PathVariable Long id) {
      return this.sysNoticeService.delete(id);
   }

   @PostMapping({"deleteBatch"})
   @SaCheckPermission({"admin"})
   public Boolean deleteBatch(@RequestBody List<Long> ids) {
      return this.sysNoticeService.deleteBatch(ids);
   }

   @Generated
   public SysNoticeController(final SysNoticeService sysNoticeService) {
      this.sysNoticeService = sysNoticeService;
   }
}
