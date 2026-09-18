package com.una.embyhub.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.stp.StpUtil;
import com.una.embyhub.config.mybatis.MybatisPlusPage;
import com.una.embyhub.model.dto.request.pointsbot.PointsBotFoamBagRequest;
import com.una.embyhub.model.dto.response.pointsbot.PointsBotFoamBagMyRecordsResponse;
import com.una.embyhub.pointsbot.service.PointsBotFoamBagService;
import jakarta.validation.Valid;
import lombok.Generated;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping({"pointsBot/foamBag/my"})
@Validated
public class PointsBotFoamBagUserController {
   private final PointsBotFoamBagService foamBagService;

   @PostMapping({"select"})
   @SaCheckLogin
   public PointsBotFoamBagMyRecordsResponse selectMine(@RequestBody @Valid MybatisPlusPage<PointsBotFoamBagRequest> request) {
      return this.foamBagService.selectMine(StpUtil.getLoginIdAsLong(), request);
   }

   @Generated
   public PointsBotFoamBagUserController(final PointsBotFoamBagService foamBagService) {
      this.foamBagService = foamBagService;
   }
}
