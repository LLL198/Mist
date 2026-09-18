package com.una.embyhub.service;

import com.una.embyhub.config.common.exception.BizException;
import com.una.embyhub.config.common.exception.TelegramGroupMembershipRequiredException;
import com.una.embyhub.config.common.utils.ConfigCacheLoaderUtils;
import com.una.embyhub.config.common.utils.TelegramClientUtils;
import com.una.embyhub.model.dto.response.embynotifydata.TelegramResponse;
import java.net.URI;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import lombok.Generated;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.telegram.telegrambots.meta.api.methods.groupadministration.GetChat;
import org.telegram.telegrambots.meta.api.methods.groupadministration.GetChatMember;
import org.telegram.telegrambots.meta.api.objects.chat.ChatFullInfo;
import org.telegram.telegrambots.meta.api.objects.chatmember.ChatMember;
import org.telegram.telegrambots.meta.generics.TelegramClient;

@Service
public class TelegramBindingMembershipGuard {
   @Generated
   private static final Logger log = LoggerFactory.getLogger(TelegramBindingMembershipGuard.class);
   private final ConfigCacheLoaderUtils configCacheLoaderUtils;
   private final TelegramClientUtils telegramClientUtils;

   public void assertCanBind(Long telegramUserId) {
      TelegramBindingMembershipSettings settings = this.membershipSettings();
      if (settings.requiresAnyGroup()) {
         if (telegramUserId == null) {
            throw new BizException("Telegram 用户信息无效，无法校验群成员状态");
         } else {
            try {
               TelegramResponse config = this.telegramClientUtils.getTelegramResponse();
               List<TelegramBindingMembershipGuard.GroupRequirement> requirements = this.resolveRequirements(settings, config);
               if (!requirements.isEmpty()) {
                  TelegramClient client = this.telegramClientUtils.getTelegramClient();
                  if (client == null) {
                     throw new BizException("Telegram 客户端未初始化，暂时无法验证群成员状态");
                  } else {
                     for (TelegramBindingMembershipGuard.GroupRequirement requirement : requirements) {
                        this.assertActiveMember(client, requirement, telegramUserId);
                     }
                  }
               }
            } catch (BizException var8) {
               throw var8;
            } catch (Exception var9) {
               log.warn("Telegram 绑定前校验群成员失败: telegramUserId={}, error={}", telegramUserId, var9.getMessage());
               throw new BizException("暂时无法验证是否已加入所选群聊，请稍后再试");
            }
         }
      }
   }

   private TelegramBindingMembershipSettings membershipSettings() {
      return TelegramBindingMembershipSettings.fromConfigValue(this.configCacheLoaderUtils.getConfigValue("telegram_binding_points_group_required"));
   }

   private String resolvePointsChatId(TelegramResponse config) {
      if (config == null) {
         return null;
      } else if (StringUtils.hasText(config.getGroupChatId())) {
         return config.getGroupChatId().trim();
      } else {
         return StringUtils.hasText(config.getBotChatGroupId()) ? config.getBotChatGroupId().trim() : null;
      }
   }

   private List<TelegramBindingMembershipGuard.GroupRequirement> resolveRequirements(TelegramBindingMembershipSettings settings, TelegramResponse config) {
      String pointsChatId = this.resolvePointsChatId(config);
      List<TelegramBindingMembershipGuard.GroupRequirement> requirements = new ArrayList<>();
      if (settings.pointsGroupRequired()) {
         if (!StringUtils.hasText(pointsChatId)) {
            throw new BizException("当前未配置积分群/频道，暂时无法绑定 Telegram");
         }

         requirements.add(new TelegramBindingMembershipGuard.GroupRequirement("积分群/频道", pointsChatId));
      }

      if (settings.libraryNotifyGroupRequired()) {
         String libraryNotifyChatId = config == null ? null : config.getLibraryNotifyChatId();
         if (StringUtils.hasText(libraryNotifyChatId)) {
            requirements.add(new TelegramBindingMembershipGuard.GroupRequirement("入库通知群/频道", libraryNotifyChatId.trim()));
         } else {
            if (!StringUtils.hasText(pointsChatId)) {
               throw new BizException("当前未配置入库通知群/频道，暂时无法绑定 Telegram");
            }

            requirements.add(new TelegramBindingMembershipGuard.GroupRequirement("积分群/频道", pointsChatId));
         }
      }

      LinkedHashMap<String, TelegramBindingMembershipGuard.GroupRequirement> uniqueRequirements = new LinkedHashMap<>();

      for (TelegramBindingMembershipGuard.GroupRequirement requirement : requirements) {
         uniqueRequirements.putIfAbsent(requirement.chatId(), requirement);
      }

      return List.copyOf(uniqueRequirements.values());
   }

   private void assertActiveMember(TelegramClient client, TelegramBindingMembershipGuard.GroupRequirement requirement, Long telegramUserId) throws Exception {
      ChatMember chatMember = client.execute(GetChatMember.builder().chatId(requirement.chatId()).userId(telegramUserId).build());
      Boolean activeMember = TelegramGroupMembershipService.activeMemberState(chatMember);
      if (!Boolean.TRUE.equals(activeMember)) {
         if (Boolean.FALSE.equals(activeMember)) {
            throw this.buildMembershipRequiredException(client, requirement);
         } else {
            throw new BizException("暂时无法识别你在" + requirement.label() + "的成员状态，请稍后再试");
         }
      }
   }

   private TelegramGroupMembershipRequiredException buildMembershipRequiredException(
      TelegramClient client, TelegramBindingMembershipGuard.GroupRequirement requirement
   ) {
      String groupTitle = null;
      String groupLink = this.publicLinkFromConfiguredChatId(requirement.chatId());

      try {
         ChatFullInfo chat = client.execute(GetChat.builder().chatId(requirement.chatId()).build());
         if (chat != null) {
            groupTitle = chat.getTitle();
            String publicLink = this.publicLinkFromUsername(this.firstPublicUsername(chat));
            if (StringUtils.hasText(publicLink)) {
               groupLink = publicLink;
            } else if (this.isSafeTelegramLink(chat.getInviteLink())) {
               groupLink = chat.getInviteLink().trim();
            }
         }
      } catch (Exception var7) {
         log.warn("读取 Telegram 群资料失败，使用纯文本绑定提示: chatId={}, error={}", requirement.chatId(), var7.getMessage());
      }

      return new TelegramGroupMembershipRequiredException(requirement.label(), groupTitle, groupLink);
   }

   private String firstPublicUsername(ChatFullInfo chat) {
      if (StringUtils.hasText(chat.getUserName())) {
         return chat.getUserName();
      } else {
         List<String> activeUsernames = chat.getActiveUsernames();
         return activeUsernames == null ? null : activeUsernames.stream().filter(StringUtils::hasText).findFirst().orElse(null);
      }
   }

   private String publicLinkFromConfiguredChatId(String chatId) {
      return StringUtils.hasText(chatId) && chatId.trim().startsWith("@") ? this.publicLinkFromUsername(chatId.trim().substring(1)) : null;
   }

   private String publicLinkFromUsername(String username) {
      if (!StringUtils.hasText(username)) {
         return null;
      } else {
         String normalized = username.trim().replaceFirst("^@", "");
         return normalized.matches("[A-Za-z0-9_]+") ? "https://t.me/" + normalized : null;
      }
   }

   private boolean isSafeTelegramLink(String link) {
      if (StringUtils.hasText(link) && !link.contains(")") && !link.contains("\n") && !link.contains("\r")) {
         try {
            URI uri = URI.create(link.trim());
            return "https".equalsIgnoreCase(uri.getScheme()) && "t.me".equalsIgnoreCase(uri.getHost());
         } catch (IllegalArgumentException var3) {
            return false;
         }
      } else {
         return false;
      }
   }

   @Generated
   public TelegramBindingMembershipGuard(final ConfigCacheLoaderUtils configCacheLoaderUtils, final TelegramClientUtils telegramClientUtils) {
      this.configCacheLoaderUtils = configCacheLoaderUtils;
      this.telegramClientUtils = telegramClientUtils;
   }

   private static record GroupRequirement(String label, String chatId) {
   }
}
