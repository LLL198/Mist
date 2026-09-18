package com.una.embyhub.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.diboot.core.binding.QueryBuilder;
import com.diboot.core.util.BeanUtils;
import com.una.embyhub.config.common.MpConvert;
import com.una.embyhub.config.common.enums.ResponseStatusEnum;
import com.una.embyhub.config.common.exception.BizException;
import com.una.embyhub.config.common.utils.NotifyUtils;
import com.una.embyhub.config.mybatis.MybatisPlusPage;
import com.una.embyhub.mapper.SupportTicketMapper;
import com.una.embyhub.mapper.SupportTicketReplyMapper;
import com.una.embyhub.model.dto.request.supportticket.SupportTicketPageRequest;
import com.una.embyhub.model.dto.request.supportticket.SupportTicketReplyRequest;
import com.una.embyhub.model.dto.request.supportticket.SupportTicketReviewRequest;
import com.una.embyhub.model.dto.request.supportticket.SupportTicketSubmitRequest;
import com.una.embyhub.model.dto.request.telegram.SendMessageRequest;
import com.una.embyhub.model.dto.response.supportticket.SupportTicketReplyResponse;
import com.una.embyhub.model.dto.response.supportticket.SupportTicketResponse;
import com.una.embyhub.model.entity.EmbyUser;
import com.una.embyhub.model.entity.SupportTicket;
import com.una.embyhub.model.entity.SupportTicketReply;
import com.una.embyhub.service.SupportTicketService;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.Generated;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Service
@Transactional(
   rollbackFor = {Exception.class}
)
public class SupportTicketServiceImpl extends ServiceImpl<SupportTicketMapper, SupportTicket> implements SupportTicketService {
   @Generated
   private static final Logger log = LoggerFactory.getLogger(SupportTicketServiceImpl.class);
   private final SupportTicketReplyMapper supportTicketReplyMapper;
   private final NotifyUtils notifyUtils;

   @Override
   public Page<SupportTicketResponse> select(MybatisPlusPage<SupportTicketPageRequest> page) {
      EmbyUser user = this.currentUser();
      QueryWrapper<SupportTicket> queryWrapper = QueryBuilder.toQueryWrapper(page.getObject());
      if (!this.isAdmin(user)) {
         queryWrapper.eq("user_id", user.getId());
      }

      queryWrapper.orderByDesc("update_datetime").orderByDesc("last_reply_datetime").orderByDesc("id");
      return MpConvert.page(queryWrapper, this.getBaseMapper(), SupportTicketResponse.class, page.getCurrent(), page.getSize(), page.getOrders());
   }

   @Override
   public SupportTicketResponse submit(SupportTicketSubmitRequest request) {
      EmbyUser user = this.currentUser();
      SupportTicket ticket = new SupportTicket();
      ticket.setTitle(request.getTitle().trim());
      ticket.setContent(request.getContent().trim());
      ticket.setStatus(0);
      ticket.setUserId(user.getId());
      ticket.setEmbyUserName(user.getEmbyUserName());
      ticket.setReplyCount(0);
      Date submitTime = new Date();
      ticket.setCreateDatetime(submitTime);
      this.save(ticket);
      this.notifyTicketSubmitted(ticket, user, submitTime);
      return this.toResponse(ticket, false);
   }

   @Override
   public SupportTicketResponse detail(Long id) {
      SupportTicket ticket = this.getCheckedTicket(id, this.currentUser());
      return this.toResponse(ticket, true);
   }

   @Override
   public SupportTicketResponse reply(SupportTicketReplyRequest request) {
      EmbyUser user = this.currentUser();
      SupportTicket ticket = this.getCheckedTicket(request.getTicketId(), user);
      this.appendReply(ticket, request.getReplyContent(), user);
      return this.detail(ticket.getId());
   }

   @Override
   public SupportTicketResponse review(SupportTicketReviewRequest request) {
      EmbyUser user = this.currentUser();
      if (!this.isAdmin(user)) {
         throw new BizException(ResponseStatusEnum.PERMISSION_DENIED);
      } else if (request.getStatus() != null && (request.getStatus() == 1 || request.getStatus() == 2)) {
         SupportTicket ticket = this.getCheckedTicket(request.getTicketId(), user);
         ticket.setStatus(request.getStatus());
         String replyContent = request.getReplyContent();
         if (StringUtils.hasText(replyContent)) {
            this.appendReply(ticket, replyContent, user);
         } else {
            this.updateById(ticket);
         }

         return this.detail(ticket.getId());
      } else {
         throw new BizException(ResponseStatusEnum.BAD_REQUEST.getCode(), "审批状态只能是批准或拒绝");
      }
   }

   private void appendReply(SupportTicket ticket, String content, EmbyUser user) {
      String trimmedContent = content == null ? "" : content.trim();
      if (!StringUtils.hasText(trimmedContent)) {
         throw new BizException(ResponseStatusEnum.BAD_REQUEST.getCode(), "回复内容不能为空");
      } else {
         SupportTicketReply reply = new SupportTicketReply();
         reply.setTicketId(ticket.getId());
         reply.setReplyContent(trimmedContent);
         reply.setUserId(user.getId());
         reply.setEmbyUserName(user.getEmbyUserName());
         reply.setReplyRole(this.isAdmin(user) ? 1 : 0);
         this.supportTicketReplyMapper.insert(reply);
         ticket.setReplyCount((ticket.getReplyCount() == null ? 0 : ticket.getReplyCount()) + 1);
         ticket.setLastReplyContent(this.trimForLastReply(trimmedContent));
         ticket.setLastReplyUserName(user.getEmbyUserName());
         ticket.setLastReplyDatetime(new Date());
         this.updateById(ticket);
      }
   }

   private SupportTicket getCheckedTicket(Long id, EmbyUser user) {
      if (id == null) {
         throw new BizException(ResponseStatusEnum.BAD_REQUEST.getCode(), "工单ID不能为空");
      } else {
         SupportTicket ticket = this.getById(id);
         if (ticket == null) {
            throw new BizException(ResponseStatusEnum.NOT_EXIST.getCode(), "工单不存在");
         } else if (this.isAdmin(user) || ticket.getUserId() != null && ticket.getUserId().equals(user.getId())) {
            return ticket;
         } else {
            throw new BizException(ResponseStatusEnum.PERMISSION_DENIED);
         }
      }
   }

   private SupportTicketResponse toResponse(SupportTicket ticket, boolean includeReplies) {
      SupportTicketResponse response = BeanUtils.convert(ticket, SupportTicketResponse.class);
      if (includeReplies) {
         List<SupportTicketReply> replies = new LambdaQueryChainWrapper<>(this.supportTicketReplyMapper)
            .eq(SupportTicketReply::getTicketId, ticket.getId())
            .orderByAsc(SupportTicketReply::getId)
            .list();
         response.setReplies(BeanUtils.convertList(replies, SupportTicketReplyResponse.class));
      }

      return response;
   }

   private EmbyUser currentUser() {
      Object userObj = StpUtil.getSession().get("user");
      if (userObj instanceof EmbyUser) {
         return (EmbyUser)userObj;
      } else {
         throw new BizException(ResponseStatusEnum.UNAUTHORIZED);
      }
   }

   private boolean isAdmin(EmbyUser user) {
      return user != null && user.getId() != null && StpUtil.hasPermission("admin");
   }

   private String trimForLastReply(String content) {
      return content != null && content.length() > 500 ? content.substring(0, 500) : content;
   }

   private void notifyTicketSubmitted(SupportTicket ticket, EmbyUser user, Date submitTime) {
      try {
         String submitter = this.buildSubmitterLabel(user, ticket);
         String content = this.normalizeNotifyContent(ticket.getContent());
         String formattedTime = DateUtil.format(submitTime, "yyyy-MM-dd HH:mm:ss");
         Map<String, String> extras = new HashMap<>();
         extras.put("ticketId", ticket.getId() == null ? "" : String.valueOf(ticket.getId()));
         extras.put("ticketTitle", ticket.getTitle());
         extras.put("ticketSubmitter", submitter);
         extras.put("ticketSubmitTime", formattedTime);
         extras.put("ticketContent", content);
         SendMessageRequest sendMessageRequest = new SendMessageRequest();
         sendMessageRequest.setName("新工单：" + ticket.getTitle());
         sendMessageRequest.setOverview("提交人：" + submitter + "\n提交时间：" + formattedTime + "\n内容：" + content);
         sendMessageRequest.setExtraVariables(extras);
         this.notifyUtils.sendMultiChannel(sendMessageRequest, "support_ticket_submitted", false, "telegram", "wechat", "wechatBot", "dingding", "messagepush");
      } catch (Exception var9) {
         log.error("工单提交通知发送失败，ticketId={}", ticket != null ? ticket.getId() : null, var9);
      }
   }

   private String buildSubmitterLabel(EmbyUser user, SupportTicket ticket) {
      String name = user != null && StringUtils.hasText(user.getEmbyUserName()) ? user.getEmbyUserName() : ticket.getEmbyUserName();
      if (!StringUtils.hasText(name)) {
         name = "未知用户";
      }

      Long userId = user != null ? user.getId() : ticket.getUserId();
      return userId == null ? name : name + "（ID: " + userId + "）";
   }

   private String normalizeNotifyContent(String content) {
      String normalized = content == null ? "" : content.trim();
      return normalized.length() <= 800 ? normalized : normalized.substring(0, 800) + "...";
   }

   @Generated
   public SupportTicketServiceImpl(final SupportTicketReplyMapper supportTicketReplyMapper, final NotifyUtils notifyUtils) {
      this.supportTicketReplyMapper = supportTicketReplyMapper;
      this.notifyUtils = notifyUtils;
   }
}
