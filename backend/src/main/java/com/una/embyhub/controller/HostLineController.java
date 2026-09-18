package com.una.embyhub.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.una.embyhub.config.mybatis.MybatisPlusPage;
import com.una.embyhub.model.dto.request.hostline.HostLineRequest;
import com.una.embyhub.model.dto.request.hostline.HostLineSave;
import com.una.embyhub.model.dto.request.hostline.HostLineUpdate;
import com.una.embyhub.model.dto.response.hostline.HostLineResponse;
import com.una.embyhub.service.HostLineService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping({"hostLine"})
public class HostLineController {
   @Autowired
   private HostLineService hostLineService;

   @PostMapping({"select"})
   @SaCheckPermission({"admin"})
   public Page<HostLineResponse> select(@RequestBody MybatisPlusPage<HostLineRequest> page) {
      return this.hostLineService.select(page);
   }

   @PostMapping({"available"})
   public List<HostLineResponse> listAvailable(@RequestParam Long embyInfoId) {
      return this.hostLineService.listAvailableLines(embyInfoId);
   }

   @PostMapping({"myLines"})
   public List<HostLineResponse> listMyLines() {
      return this.hostLineService.listCurrentUserLines();
   }

   @PostMapping({"insert"})
   @SaCheckPermission({"admin"})
   public void insert(@RequestBody HostLineSave save) {
      this.hostLineService.insertHostLine(save);
   }

   @PostMapping({"update"})
   @SaCheckPermission({"admin"})
   public void update(@RequestBody HostLineUpdate update) {
      this.hostLineService.updateHostLine(update);
   }

   @PostMapping({"delete"})
   @SaCheckPermission({"admin"})
   public void delete(@RequestParam List<Long> ids) {
      this.hostLineService.deleteHostLine(ids);
   }
}
