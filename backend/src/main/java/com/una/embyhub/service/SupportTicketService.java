package com.una.embyhub.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.una.embyhub.config.mybatis.MybatisPlusPage;
import com.una.embyhub.model.dto.request.supportticket.SupportTicketPageRequest;
import com.una.embyhub.model.dto.request.supportticket.SupportTicketReplyRequest;
import com.una.embyhub.model.dto.request.supportticket.SupportTicketReviewRequest;
import com.una.embyhub.model.dto.request.supportticket.SupportTicketSubmitRequest;
import com.una.embyhub.model.dto.response.supportticket.SupportTicketResponse;
import com.una.embyhub.model.entity.SupportTicket;

public interface SupportTicketService extends IService<SupportTicket> {
   Page<SupportTicketResponse> select(MybatisPlusPage<SupportTicketPageRequest> page);

   SupportTicketResponse submit(SupportTicketSubmitRequest request);

   SupportTicketResponse detail(Long id);

   SupportTicketResponse reply(SupportTicketReplyRequest request);

   SupportTicketResponse review(SupportTicketReviewRequest request);
}
