package com.una.embyhub.config.common.telegrambot;

import com.una.embyhub.config.common.utils.TelegramClientUtils;
import com.una.embyhub.model.dto.response.embynotifydata.TelegramBotAdminConfig;
import com.una.embyhub.model.dto.response.embynotifydata.TelegramResponse;
import java.util.Collections;
import java.util.EnumSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import lombok.Generated;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

@Component
public class TelegramBotAuthorizationService {
   private final TelegramClientUtils telegramClientUtils;

   public boolean isOwner(long telegramUserId) {
      TelegramResponse config = this.telegramClientUtils.getTelegramResponse();
      return config != null && this.parsePositiveLong(config.getBotChatId()) == telegramUserId;
   }

   public boolean isAdmin(long telegramUserId) {
      return this.isOwner(telegramUserId) || !this.permissionsFor(telegramUserId).isEmpty();
   }

   public boolean isConfiguredAdmin(long telegramUserId) {
      return telegramUserId > 0L && this.configuredAdminsById().containsKey(telegramUserId);
   }

   public boolean hasPermission(long telegramUserId, TelegramBotPermission permission) {
      return permission == null ? false : this.isOwner(telegramUserId) || this.permissionsFor(telegramUserId).contains(permission);
   }

   public Set<TelegramBotPermission> permissionsFor(long telegramUserId) {
      if (telegramUserId <= 0L) {
         return Collections.emptySet();
      } else {
         TelegramBotAdminConfig admin = this.configuredAdminsById().get(telegramUserId);
         if (admin != null && !CollectionUtils.isEmpty(admin.getPermissions())) {
            EnumSet<TelegramBotPermission> permissions = EnumSet.noneOf(TelegramBotPermission.class);
            admin.getPermissions().forEach(value -> TelegramBotPermission.from(value).ifPresent(permissions::add));
            return Collections.unmodifiableSet(permissions);
         } else {
            return Collections.emptySet();
         }
      }
   }

   public Map<Long, Set<TelegramBotPermission>> configuredAdminPermissions() {
      Map<Long, Set<TelegramBotPermission>> result = new LinkedHashMap<>();
      this.configuredAdminsById().forEach((telegramId, ignored) -> result.put(telegramId, this.permissionsFor(telegramId)));
      return result;
   }

   public Set<Long> authorizedUserIds(TelegramBotPermission permission) {
      if (permission == null) {
         return Collections.emptySet();
      } else {
         LinkedHashSet<Long> result = new LinkedHashSet<>();
         TelegramResponse config = this.telegramClientUtils.getTelegramResponse();
         long ownerId = config == null ? 0L : this.parsePositiveLong(config.getBotChatId());
         if (ownerId > 0L) {
            result.add(Long.valueOf(ownerId));
         }

         this.configuredAdminsById().keySet().stream().filter(adminId -> this.permissionsFor(adminId).contains(permission)).forEach(result::add);
         return Collections.unmodifiableSet(result);
      }
   }

   public boolean isConfiguredManagementGroup(long chatId) {
      TelegramResponse config = this.telegramClientUtils.getTelegramResponse();
      return config != null && this.parseLong(config.getBotChatGroupId()) == chatId;
   }

   private Map<Long, TelegramBotAdminConfig> configuredAdminsById() {
      TelegramResponse config = this.telegramClientUtils.getTelegramResponse();
      if (config != null && !CollectionUtils.isEmpty(config.getBotAdmins())) {
         Map<Long, TelegramBotAdminConfig> result = new LinkedHashMap<>();

         for (TelegramBotAdminConfig admin : config.getBotAdmins()) {
            if (admin != null) {
               long telegramId = this.parsePositiveLong(admin.getTelegramId());
               if (telegramId > 0L) {
                  result.putIfAbsent(telegramId, admin);
               }
            }
         }

         return result;
      } else {
         return Collections.emptyMap();
      }
   }

   private long parsePositiveLong(String value) {
      long parsed = this.parseLong(value);
      return parsed > 0L ? parsed : 0L;
   }

   private long parseLong(String value) {
      if (!StringUtils.hasText(value)) {
         return 0L;
      } else {
         try {
            return Long.parseLong(value.trim());
         } catch (NumberFormatException var3) {
            return 0L;
         }
      }
   }

   @Generated
   public TelegramBotAuthorizationService(final TelegramClientUtils telegramClientUtils) {
      this.telegramClientUtils = telegramClientUtils;
   }
}
