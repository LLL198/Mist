package com.una.embyhub.service;

import com.una.embyhub.config.common.enums.ResponseStatusEnum;
import com.una.embyhub.config.common.exception.BizException;
import com.una.embyhub.config.common.utils.TelegramClientUtils;
import com.una.embyhub.model.dto.response.embynotifydata.TelegramResponse;
import com.una.embyhub.model.dto.response.embyuser.EmbyUserTelegramGroupCheckResponse;
import com.una.embyhub.model.entity.EmbyUser;
import com.una.embyhub.model.entity.UserOauthBinding;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;
import lombok.Generated;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.telegram.telegrambots.meta.api.methods.groupadministration.GetChatMember;
import org.telegram.telegrambots.meta.api.objects.chatmember.ChatMember;
import org.telegram.telegrambots.meta.api.objects.chatmember.ChatMemberRestricted;
import org.telegram.telegrambots.meta.generics.TelegramClient;

@Service
public class TelegramGroupMembershipService {
   @Generated
   private static final Logger log = LoggerFactory.getLogger(TelegramGroupMembershipService.class);
   private final EmbyUserService embyUserService;
   private final TelegramBindingManager telegramBindingManager;
   private final TelegramClientUtils telegramClientUtils;

   public EmbyUserTelegramGroupCheckResponse checkUsers(List<Long> requestedUserIds) {
      List<Long> userIds = new LinkedHashSet<>(requestedUserIds).stream().toList();
      Map<Long, EmbyUser> usersById = this.embyUserService
         .listByIds(userIds)
         .stream()
         .filter(Objects::nonNull)
         .filter(user -> user.getId() != null)
         .collect(Collectors.toMap(EmbyUser::getId, Function.identity(), (first, second) -> first));

      for (Long userId : userIds) {
         EmbyUser user = usersById.get(userId);
         if (user == null || Integer.valueOf(1).equals(user.getDelFlag())) {
            throw new BizException(ResponseStatusEnum.USER_NOT_EXIST);
         }

         this.embyUserService.assertUserCanBeViewed(userId);
      }

      TelegramGroupMembershipService.TelegramCheckContext context = this.createCheckContext();
      EmbyUserTelegramGroupCheckResponse response = new EmbyUserTelegramGroupCheckResponse();
      List<EmbyUserTelegramGroupCheckResponse.Result> results = userIds.stream().map(userId -> this.checkUser(usersById.get(userId), context)).toList();
      response.setResults(results);
      response.setTotalCount(results.size());
      response.setInGroupCount(this.countStatus(results, "IN_GROUP"));
      response.setNotInGroupCount(this.countStatus(results, "NOT_IN_GROUP"));
      response.setUnboundCount(this.countStatus(results, "UNBOUND"));
      response.setFailedCount(this.countStatus(results, "CHECK_FAILED"));
      return response;
   }

   private EmbyUserTelegramGroupCheckResponse.Result checkUser(EmbyUser user, TelegramGroupMembershipService.TelegramCheckContext context) {
      EmbyUserTelegramGroupCheckResponse.Result result = new EmbyUserTelegramGroupCheckResponse.Result();
      result.setUserId(user.getId());
      result.setEmbyUserName(user.getEmbyUserName());
      UserOauthBinding binding = this.telegramBindingManager.findBindingByUserId(user.getId());
      if (binding != null && StringUtils.hasText(binding.getProviderUserId())) {
         result.setTelegramUserId(binding.getProviderUserId());
         result.setTelegramUsername(binding.getProviderUsername());
         Long telegramUserId = this.parseTelegramUserId(binding.getProviderUserId());
         if (telegramUserId == null) {
            this.setStatus(result, "CHECK_FAILED", "检测失败", "TG 绑定 ID 无效");
            return result;
         } else if (StringUtils.hasText(context.errorMessage())) {
            this.setStatus(result, "CHECK_FAILED", "检测失败", context.errorMessage());
            return result;
         } else {
            try {
               ChatMember chatMember = context.client().execute(GetChatMember.builder().chatId(context.groupChatId()).userId(telegramUserId).build());
               Boolean activeMember = activeMemberState(chatMember);
               if (activeMember == null) {
                  this.setStatus(result, "CHECK_FAILED", "检测失败", "Telegram 返回了无法识别的成员状态");
               } else if (activeMember) {
                  this.setStatus(result, "IN_GROUP", "在群", "已在当前 TG 群/频道");
               } else {
                  this.setStatus(result, "NOT_IN_GROUP", "不在群", "已退群、被移除或不在当前 TG 群/频道");
               }
            } catch (Exception var8) {
               log.warn("批量检测 Telegram 群成员失败: userId={}, telegramUserId={}, error={}", user.getId(), telegramUserId, var8.getMessage());
               this.setStatus(result, "CHECK_FAILED", "检测失败", "Telegram 检测失败：" + this.safeErrorMessage(var8));
            }

            return result;
         }
      } else {
         this.setStatus(result, "UNBOUND", "未绑定 TG", "该用户尚未绑定 Telegram");
         return result;
      }
   }

   private TelegramGroupMembershipService.TelegramCheckContext createCheckContext() {
      TelegramResponse config;
      try {
         config = this.telegramClientUtils.getTelegramResponse();
      } catch (Exception var5) {
         log.warn("读取 Telegram 配置失败", (Throwable)var5);
         return new TelegramGroupMembershipService.TelegramCheckContext(null, null, "Telegram 配置读取失败");
      }

      if (config != null && StringUtils.hasText(config.getBotToken())) {
         String groupChatId = StringUtils.hasText(config.getGroupChatId())
            ? config.getGroupChatId().trim()
            : (StringUtils.hasText(config.getBotChatGroupId()) ? config.getBotChatGroupId().trim() : null);
         if (!StringUtils.hasText(groupChatId)) {
            return new TelegramGroupMembershipService.TelegramCheckContext(null, null, "未配置积分群/频道");
         } else {
            try {
               TelegramClient client = this.telegramClientUtils.getTelegramClient();
               return client == null
                  ? new TelegramGroupMembershipService.TelegramCheckContext(groupChatId, null, "Telegram 客户端未初始化")
                  : new TelegramGroupMembershipService.TelegramCheckContext(groupChatId, client, null);
            } catch (Exception var4) {
               log.warn("初始化 Telegram 客户端失败", (Throwable)var4);
               return new TelegramGroupMembershipService.TelegramCheckContext(groupChatId, null, "Telegram 客户端初始化失败");
            }
         }
      } else {
         return new TelegramGroupMembershipService.TelegramCheckContext(null, null, "Telegram 通知渠道未启用");
      }
   }

   static Boolean activeMemberState(ChatMember chatMember) {
      if (chatMember != null && StringUtils.hasText(chatMember.getStatus())) {
         String status = chatMember.getStatus().trim().toLowerCase(Locale.ROOT);
         if ("creator".equals(status) || "administrator".equals(status) || "member".equals(status)) {
            return true;
         } else if ("restricted".equals(status)) {
            return chatMember instanceof ChatMemberRestricted restricted ? Boolean.TRUE.equals(restricted.getIsMember()) : null;
         } else {
            return !"left".equals(status) && !"kicked".equals(status) ? null : false;
         }
      } else {
         return null;
      }
   }

   private int countStatus(List<EmbyUserTelegramGroupCheckResponse.Result> results, String status) {
      return (int)results.stream().filter(result -> status.equals(result.getStatus())).count();
   }

   private Long parseTelegramUserId(String value) {
      try {
         return Long.valueOf(value.trim());
      } catch (Exception var3) {
         return null;
      }
   }

   private String safeErrorMessage(Exception exception) {
      String message = exception == null ? null : exception.getMessage();
      if (!StringUtils.hasText(message)) {
         return "未知错误";
      } else {
         String normalized = message.trim().replaceAll("\\s+", " ");
         return normalized.length() <= 160 ? normalized : normalized.substring(0, 160) + "…";
      }
   }

   private void setStatus(EmbyUserTelegramGroupCheckResponse.Result result, String status, String statusName, String message) {
      result.setStatus(status);
      result.setStatusName(statusName);
      result.setMessage(message);
   }

   @Generated
   public TelegramGroupMembershipService(
      final EmbyUserService embyUserService, final TelegramBindingManager telegramBindingManager, final TelegramClientUtils telegramClientUtils
   ) {
      this.embyUserService = embyUserService;
      this.telegramBindingManager = telegramBindingManager;
      this.telegramClientUtils = telegramClientUtils;
   }

   private static record TelegramCheckContext(String groupChatId, TelegramClient client, String errorMessage) {
   }
}
