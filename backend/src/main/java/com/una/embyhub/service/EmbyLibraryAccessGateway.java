package com.una.embyhub.service;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.una.embyhub.config.common.exception.BizException;
import com.una.embyhub.config.common.utils.EmbyInfoCacheManagerUtils;
import com.una.embyhub.foam.client.EmbyClient;
import com.una.embyhub.model.dto.response.embylibraryaccess.EmbyLibraryFolderResponse;
import embyclient.ApiClient;
import embyclient.ApiException;
import embyclient.api.UserServiceApi;
import embyclient.model.UserDto;
import embyclient.model.UserPolicy;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import lombok.Generated;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class EmbyLibraryAccessGateway {
   private final EmbyClient embyClient;
   private final EmbyInfoCacheManagerUtils embyInfoCacheManager;

   public List<EmbyLibraryFolderResponse> listFolders(EmbyInfoCacheManagerUtils.EmbyServerConfig serverConfig) {
      JSONArray selectableFolders = this.embyClient.getSelectableMediaFolders(serverConfig);
      if (selectableFolders == null) {
         return List.of();
      } else {
         JSONObject mediaPayload = this.embyClient.getMediaFolders(serverConfig);
         JSONArray mediaItems = mediaPayload == null ? null : mediaPayload.getJSONArray("Items");
         Map<String, EmbyLibraryAccessGateway.MediaFolderDisplay> displaysByGuid = new LinkedHashMap<>();
         Map<String, EmbyLibraryAccessGateway.MediaFolderDisplay> displaysById = new LinkedHashMap<>();
         Map<String, List<EmbyLibraryAccessGateway.MediaFolderDisplay>> displaysByName = new LinkedHashMap<>();
         if (mediaItems != null) {
            for (int index = 0; index < mediaItems.size(); index++) {
               EmbyLibraryAccessGateway.MediaFolderDisplay display = this.toDisplay(mediaItems.getJSONObject(index));
               if (display != null) {
                  if (StringUtils.hasText(display.guid())) {
                     displaysByGuid.put(this.normalizedKey(display.guid()), display);
                  }

                  displaysById.put(display.id(), display);
                  displaysByName.computeIfAbsent(this.normalizedKey(display.name()), ignored -> new ArrayList<>()).add(display);
               }
            }
         }

         List<EmbyLibraryFolderResponse> folders = new ArrayList<>();
         Set<String> seenGuids = new LinkedHashSet<>();

         for (int indexx = 0; indexx < selectableFolders.size(); indexx++) {
            JSONObject folder = selectableFolders.getJSONObject(indexx);
            if (folder != null && !Boolean.FALSE.equals(folder.getBoolean("IsUserAccessConfigurable"))) {
               String guid = this.trimmed(folder.getString("Guid"));
               if (StringUtils.hasText(guid) && seenGuids.add(this.normalizedKey(guid))) {
                  String selectableItemId = this.trimmed(folder.getString("Id"));
                  String name = this.trimmed(folder.getString("Name"));
                  EmbyLibraryAccessGateway.MediaFolderDisplay display = this.findDisplay(
                     guid, selectableItemId, name, displaysByGuid, displaysById, displaysByName
                  );
                  String imageItemId = display != null ? display.id() : selectableItemId;
                  String primaryImageTag = display != null ? display.primaryImageTag() : null;
                  LinkedHashSet<String> legacyIds = new LinkedHashSet<>();
                  if (StringUtils.hasText(selectableItemId) && !guid.equals(selectableItemId)) {
                     legacyIds.add(selectableItemId);
                  }

                  if (display != null && !guid.equals(display.id())) {
                     legacyIds.add(display.id());
                  }

                  folders.add(
                     new EmbyLibraryFolderResponse(
                        guid,
                        StringUtils.hasText(name) ? name : guid,
                        primaryImageTag,
                        imageItemId,
                        new ArrayList<>(legacyIds),
                        this.subFolderAccessIds(folder, guid)
                     )
                  );
               }
            }
         }

         folders.sort(Comparator.comparing(EmbyLibraryFolderResponse::getName, String.CASE_INSENSITIVE_ORDER));
         return folders;
      }
   }

   private EmbyLibraryAccessGateway.MediaFolderDisplay toDisplay(JSONObject item) {
      if (item == null) {
         return null;
      } else {
         String id = this.trimmed(item.getString("Id"));
         if (!StringUtils.hasText(id)) {
            return null;
         } else {
            String name = this.trimmed(item.getString("Name"));
            JSONObject imageTags = item.getJSONObject("ImageTags");
            String primaryImageTag = imageTags == null ? null : this.trimmed(imageTags.getString("Primary"));
            if (!StringUtils.hasText(primaryImageTag)) {
               primaryImageTag = this.trimmed(item.getString("PrimaryImageTag"));
            }

            return new EmbyLibraryAccessGateway.MediaFolderDisplay(id, this.trimmed(item.getString("Guid")), name, primaryImageTag);
         }
      }
   }

   private EmbyLibraryAccessGateway.MediaFolderDisplay findDisplay(
      String guid,
      String selectableItemId,
      String name,
      Map<String, EmbyLibraryAccessGateway.MediaFolderDisplay> displaysByGuid,
      Map<String, EmbyLibraryAccessGateway.MediaFolderDisplay> displaysById,
      Map<String, List<EmbyLibraryAccessGateway.MediaFolderDisplay>> displaysByName
   ) {
      EmbyLibraryAccessGateway.MediaFolderDisplay display = displaysByGuid.get(this.normalizedKey(guid));
      if (display == null && StringUtils.hasText(selectableItemId)) {
         display = displaysById.get(selectableItemId);
      }

      if (display == null && StringUtils.hasText(name)) {
         List<EmbyLibraryAccessGateway.MediaFolderDisplay> matches = displaysByName.get(this.normalizedKey(name));
         if (matches != null && matches.size() == 1) {
            display = matches.get(0);
         }
      }

      return display;
   }

   private List<String> subFolderAccessIds(JSONObject folder, String guid) {
      JSONArray subFolders = folder.getJSONArray("SubFolders");
      if (subFolders == null) {
         return List.of();
      } else {
         LinkedHashSet<String> result = new LinkedHashSet<>();

         for (int index = 0; index < subFolders.size(); index++) {
            JSONObject subFolder = subFolders.getJSONObject(index);
            if (subFolder != null && !Boolean.FALSE.equals(subFolder.getBoolean("IsUserAccessConfigurable"))) {
               String subFolderId = this.trimmed(subFolder.getString("Id"));
               if (StringUtils.hasText(subFolderId)) {
                  result.add(guid + "_" + subFolderId);
               }
            }
         }

         return new ArrayList<>(result);
      }
   }

   private String trimmed(String value) {
      return StringUtils.hasText(value) ? value.trim() : null;
   }

   private String normalizedKey(String value) {
      return StringUtils.hasText(value) ? value.trim().toLowerCase(Locale.ROOT) : "";
   }

   public void applyFolderAccess(
      EmbyInfoCacheManagerUtils.EmbyServerConfig serverConfig,
      String embyUserId,
      boolean restrictionEnabled,
      List<String> visibleFolderIds,
      List<EmbyLibraryFolderResponse> folders
   ) {
      if (!StringUtils.hasText(embyUserId)) {
         throw new BizException("用户缺少 Emby 用户 ID，无法同步媒体库权限");
      } else {
         try {
            UserServiceApi userServiceApi = this.buildUserServiceApi(serverConfig);
            UserDto user = userServiceApi.getUsersById(embyUserId.trim());
            UserPolicy policy = user.getPolicy() == null ? new UserPolicy() : user.getPolicy();
            Set<String> allFolderIds = new LinkedHashSet<>();
            Map<String, List<String>> subFolderIds = new LinkedHashMap<>();
            if (folders != null) {
               for (EmbyLibraryFolderResponse folder : folders) {
                  if (folder != null && StringUtils.hasText(folder.getId())) {
                     allFolderIds.add(folder.getId());
                     subFolderIds.put(folder.getId(), folder.getSubFolderAccessIds() == null ? List.of() : folder.getSubFolderAccessIds());
                  }
               }
            }

            configureFolderAccessPolicy(policy, restrictionEnabled, visibleFolderIds, allFolderIds, subFolderIds);
            userServiceApi.postUsersByIdPolicy(policy, embyUserId.trim());
         } catch (BizException var13) {
            throw var13;
         } catch (ApiException var14) {
            throw new BizException("Emby 媒体库权限同步失败");
         }
      }
   }

   static void configureFolderAccessPolicy(UserPolicy policy, boolean restrictionEnabled, List<String> visibleFolderIds, Set<String> allFolderIds) {
      configureFolderAccessPolicy(policy, restrictionEnabled, visibleFolderIds, allFolderIds, Map.of());
   }

   static void configureFolderAccessPolicy(
      UserPolicy policy, boolean restrictionEnabled, List<String> visibleFolderIds, Set<String> allFolderIds, Map<String, List<String>> subFolderIdsByFolderId
   ) {
      if (policy == null) {
         throw new BizException("Emby 用户策略不存在，无法同步媒体库权限");
      } else if (Boolean.TRUE.equals(policy.isIsAdministrator())) {
         throw new BizException("Emby 管理员账号不允许通过媒体库分级修改");
      } else {
         Set<String> validFolderIds = allFolderIds == null ? Set.of() : allFolderIds;
         LinkedHashSet<String> normalizedVisibleIds = new LinkedHashSet<>();
         if (visibleFolderIds != null) {
            visibleFolderIds.stream().filter(StringUtils::hasText).map(String::trim).filter(validFolderIds::contains).forEach(normalizedVisibleIds::add);
         }

         boolean enableAllFolders = !restrictionEnabled || normalizedVisibleIds.containsAll(validFolderIds);
         policy.setEnableAllFolders(enableAllFolders);
         policy.setEnabledFolders(enableAllFolders ? new ArrayList<>() : new ArrayList<>(normalizedVisibleIds));
         if (enableAllFolders) {
            policy.setExcludedSubFolders(new ArrayList<>());
         } else {
            LinkedHashSet<String> excludedSubFolders = new LinkedHashSet<>();

            for (String folderId : validFolderIds) {
               if (!normalizedVisibleIds.contains(folderId)) {
                  List<String> subFolderIds = subFolderIdsByFolderId == null ? null : subFolderIdsByFolderId.get(folderId);
                  if (subFolderIds != null) {
                     subFolderIds.stream().filter(StringUtils::hasText).map(String::trim).forEach(excludedSubFolders::add);
                  }
               }
            }

            policy.setExcludedSubFolders(new ArrayList<>(excludedSubFolders));
         }
      }
   }

   private UserServiceApi buildUserServiceApi(EmbyInfoCacheManagerUtils.EmbyServerConfig serverConfig) {
      ApiClient apiClient = new ApiClient();
      this.embyInfoCacheManager.applyTo(apiClient, serverConfig);
      return new UserServiceApi(apiClient);
   }

   @Generated
   public EmbyLibraryAccessGateway(final EmbyClient embyClient, final EmbyInfoCacheManagerUtils embyInfoCacheManager) {
      this.embyClient = embyClient;
      this.embyInfoCacheManager = embyInfoCacheManager;
   }

   private static record MediaFolderDisplay(String id, String guid, String name, String primaryImageTag) {
   }
}
