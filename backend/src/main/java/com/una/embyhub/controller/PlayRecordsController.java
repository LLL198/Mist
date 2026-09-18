package com.una.embyhub.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.una.embyhub.config.mybatis.MybatisPlusPage;
import com.una.embyhub.model.dto.request.playrecords.PlayRecordsRequest;
import com.una.embyhub.model.dto.request.playrecords.UserPlayStats;
import com.una.embyhub.model.dto.response.playrecords.PlayRecordsResponse;
import com.una.embyhub.service.PlayRecordsService;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping({"playRecords"})
public class PlayRecordsController {
   @Autowired
   private PlayRecordsService playRecordsService;

   @PostMapping({"select"})
   @SaCheckPermission({"admin"})
   public Page<PlayRecordsResponse> select(@RequestBody MybatisPlusPage<PlayRecordsRequest> page) {
      return this.playRecordsService.select(page);
   }

   @PostMapping({"getPlayStats"})
   @SaCheckPermission({"admin"})
   public List<UserPlayStats> getPlayStats(
      @RequestParam(required = false) String userName,
      @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate,
      @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate,
      @RequestParam(required = false) Long embyInfoId
   ) {
      if (startDate != null && endDate == null) {
         Calendar calendar = Calendar.getInstance();
         calendar.setTime(startDate);
         calendar.add(5, 1);
         endDate = calendar.getTime();
      }

      return this.playRecordsService.getPlayStats(userName, startDate, endDate, embyInfoId);
   }
}
