package com.una.embyhub.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.una.embyhub.config.mybatis.MybatisPlusPage;
import com.una.embyhub.model.dto.request.supportticket.SupportTicketPageRequest;
import com.una.embyhub.model.dto.request.supportticket.SupportTicketReplyRequest;
import com.una.embyhub.model.dto.request.supportticket.SupportTicketReviewRequest;
import com.una.embyhub.model.dto.request.supportticket.SupportTicketSubmitRequest;
import com.una.embyhub.model.dto.response.supportticket.SupportTicketResponse;
import com.una.embyhub.service.SupportTicketService;
import jakarta.validation.Valid;
import lombok.Generated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping({"supportTicket"})
public class SupportTicketController {
   private final SupportTicketService supportTicketService;

   @PostMapping({"select"})
   public Page<SupportTicketResponse> select(@RequestBody @Valid MybatisPlusPage<SupportTicketPageRequest> page) {
      return this.supportTicketService.select(page);
   }

   @PostMapping({"submit"})
   public SupportTicketResponse submit(@RequestBody @Valid SupportTicketSubmitRequest request) {
      return this.supportTicketService.submit(request);
   }

   @PostMapping({"detail/{id}"})
   public SupportTicketResponse detail(@PathVariable Long id) {
      return this.supportTicketService.detail(id);
   }

   @PostMapping({"reply"})
   public SupportTicketResponse reply(@RequestBody @Valid SupportTicketReplyRequest request) {
      return this.supportTicketService.reply(request);
   }

   @PostMapping({"review"})
   @SaCheckPermission({"admin"})
   public SupportTicketResponse review(@RequestBody @Valid SupportTicketReviewRequest request) {
      return this.supportTicketService.review(request);
   }

   @Generated
   public SupportTicketController(final SupportTicketService supportTicketService) {
      this.supportTicketService = supportTicketService;
   }
}
