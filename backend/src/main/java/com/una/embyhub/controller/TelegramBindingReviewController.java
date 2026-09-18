package com.una.embyhub.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.una.embyhub.config.mybatis.MybatisPlusPage;
import com.una.embyhub.model.dto.request.telegram.TelegramBindingReviewDecisionRequest;
import com.una.embyhub.model.dto.request.telegram.TelegramBindingReviewRequest;
import com.una.embyhub.model.dto.response.telegram.TelegramBindingReviewResponse;
import com.una.embyhub.model.dto.response.telegram.TelegramBindingReviewStatsResponse;
import com.una.embyhub.service.TelegramBindingReviewService;
import jakarta.validation.Valid;
import lombok.Generated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping({"telegramBindingReview"})
@SaCheckPermission({"admin"})
public class TelegramBindingReviewController {
   private final TelegramBindingReviewService telegramBindingReviewService;

   @PostMapping({"select"})
   public Page<TelegramBindingReviewResponse> select(@RequestBody @Valid MybatisPlusPage<TelegramBindingReviewRequest> page) {
      return this.telegramBindingReviewService.select(page);
   }

   @PostMapping({"stats"})
   public TelegramBindingReviewStatsResponse stats(@RequestBody(required = false) TelegramBindingReviewRequest request) {
      return this.telegramBindingReviewService.stats(request);
   }

   @PostMapping({"review"})
   public TelegramBindingReviewResponse review(@RequestBody @Valid TelegramBindingReviewDecisionRequest request) {
      return this.telegramBindingReviewService.review(request);
   }

   @Generated
   public TelegramBindingReviewController(final TelegramBindingReviewService telegramBindingReviewService) {
      this.telegramBindingReviewService = telegramBindingReviewService;
   }
}
