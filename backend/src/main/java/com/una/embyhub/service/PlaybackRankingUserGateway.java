package com.una.embyhub.service;

import com.una.embyhub.config.common.enums.ResponseStatusEnum;
import com.una.embyhub.config.common.exception.BizException;
import com.una.embyhub.config.common.utils.EmbyInfoCacheManagerUtils;
import com.una.embyhub.model.dto.response.scheduledtask.PlaybackRankingUserOptionResponse;
import embyclient.ApiClient;
import embyclient.ApiException;
import embyclient.api.UserServiceApi;
import embyclient.model.QueryResultUserDto;
import embyclient.model.UserDto;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import lombok.Generated;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class PlaybackRankingUserGateway {
   @Generated
   private static final Logger log = LoggerFactory.getLogger(PlaybackRankingUserGateway.class);
   private final EmbyInfoCacheManagerUtils embyInfoCacheManager;

   public List<PlaybackRankingUserOptionResponse> listUsers(EmbyInfoCacheManagerUtils.EmbyServerConfig serverConfig) {
      ApiClient apiClient = new ApiClient();
      this.embyInfoCacheManager.applyTo(apiClient, serverConfig);

      try {
         QueryResultUserDto result = new UserServiceApi(apiClient).getUsersQuery(null, null, null, null, null, null);
         return result != null && result.getItems() != null
            ? result.getItems()
               .stream()
               .filter(user -> user != null && StringUtils.hasText(user.getId()))
               .map(this::toOption)
               .sorted(Comparator.comparing(option -> option.getName() == null ? "" : option.getName().toLowerCase(Locale.ROOT)))
               .toList()
            : List.of();
      } catch (ApiException var4) {
         log.warn("排行榜配置获取Emby用户失败: serverId={}, status={}", serverConfig.id(), var4.getCode());
         throw new BizException(ResponseStatusEnum.BAD_REQUEST.getCode(), "无法获取Emby用户，请检查服务器连接后重试");
      } catch (RuntimeException var5) {
         log.warn("排行榜配置获取Emby用户失败: serverId={}, errorType={}", serverConfig.id(), var5.getClass().getSimpleName());
         throw new BizException(ResponseStatusEnum.BAD_REQUEST.getCode(), "无法获取Emby用户，请检查服务器连接后重试");
      }
   }

   private PlaybackRankingUserOptionResponse toOption(UserDto user) {
      String id = user.getId().trim();
      String name = StringUtils.hasText(user.getName()) ? user.getName().trim() : id;
      return new PlaybackRankingUserOptionResponse(id, name);
   }

   @Generated
   public PlaybackRankingUserGateway(final EmbyInfoCacheManagerUtils embyInfoCacheManager) {
      this.embyInfoCacheManager = embyInfoCacheManager;
   }
}
