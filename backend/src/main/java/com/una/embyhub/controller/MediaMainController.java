package com.una.embyhub.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.una.embyhub.config.mybatis.MybatisPlusPage;
import com.una.embyhub.model.dto.request.mediamain.MediaMainRequest;
import com.una.embyhub.model.dto.response.mediamain.MediaMainResponse;
import com.una.embyhub.service.MediaMainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping({"mediaMain"})
public class MediaMainController {
   @Autowired
   private MediaMainService mediaMainService;

   @PostMapping({"select"})
   public Page<MediaMainResponse> select(@RequestBody MybatisPlusPage<MediaMainRequest> page) {
      return this.mediaMainService.select(page);
   }
}
