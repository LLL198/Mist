package com.una.embyhub.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.una.embyhub.config.mybatis.MybatisPlusPage;
import com.una.embyhub.model.dto.request.simultaneous.SimultaneousPlaybackRecordRequest;
import com.una.embyhub.model.dto.response.simultaneous.SimultaneousPlaybackRecordResponse;
import com.una.embyhub.service.SimultaneousPlaybackRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping({"simultaneousPlayback"})
public class SimultaneousPlaybackRecordController {
   @Autowired
   private SimultaneousPlaybackRecordService simultaneousPlaybackRecordService;

   @PostMapping({"select"})
   @SaCheckPermission({"admin"})
   public Page<SimultaneousPlaybackRecordResponse> select(@RequestBody MybatisPlusPage<SimultaneousPlaybackRecordRequest> page) {
      return this.simultaneousPlaybackRecordService.select(page);
   }
}
