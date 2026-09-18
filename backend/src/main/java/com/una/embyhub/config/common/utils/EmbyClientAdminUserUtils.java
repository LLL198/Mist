package com.una.embyhub.config.common.utils;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public final class EmbyClientAdminUserUtils {
   private EmbyClientAdminUserUtils() {
   }

   public static List<EmbyClientAdminUserUtils.AdminUser> listAdministratorsNoPaging(String baseUrl, String token, Integer timeoutMs) {
      String safeBaseUrl = Objects.requireNonNull(baseUrl, "baseUrl required").replaceAll("/+$", "");
      String safeToken = Objects.requireNonNull(token, "token required");
      int safeTimeout = timeoutMs == null ? 10000 : timeoutMs;
      String url = safeBaseUrl + "/Users";
      HttpResponse resp = HttpRequest.get(url).header("X-Emby-Token", safeToken).timeout(safeTimeout).execute();
      if (resp.getStatus() != 200) {
         throw new RuntimeException("Emby /Users failed: HTTP " + resp.getStatus() + " - " + resp.body());
      } else {
         JSONArray items = JSON.parseArray(resp.body());
         List<EmbyClientAdminUserUtils.AdminUser> admins = new ArrayList<>();
         if (items != null) {
            for (int i = 0; i < items.size(); i++) {
               JSONObject u = items.getJSONObject(i);
               JSONObject policy = u.getJSONObject("Policy");
               boolean isAdmin = policy != null && Boolean.TRUE.equals(policy.getBoolean("IsAdministrator"));
               if (isAdmin) {
                  String id = u.getString("Id");
                  String name = u.getString("Name");
                  String lastLogin = u.getString("LastLoginDate");
                  admins.add(new EmbyClientAdminUserUtils.AdminUser(id, name, lastLogin));
               }
            }
         }

         return admins;
      }
   }

   public static List<EmbyClientAdminUserUtils.AdminUser> listAdministratorsNoPaging(String baseUrl, String token) {
      return listAdministratorsNoPaging(baseUrl, token, 10000);
   }

   public static List<EmbyClientAdminUserUtils.SelectableUser> listEnabledNonAdministratorUsersNoPaging(String baseUrl, String token, Integer timeoutMs) {
      String safeBaseUrl = Objects.requireNonNull(baseUrl, "baseUrl required").replaceAll("/+$", "");
      String safeToken = Objects.requireNonNull(token, "token required");
      int safeTimeout = timeoutMs == null ? 10000 : timeoutMs;
      HttpResponse resp = HttpRequest.get(safeBaseUrl + "/Users").header("X-Emby-Token", safeToken).timeout(safeTimeout).execute();
      if (resp.getStatus() != 200) {
         throw new RuntimeException("Emby /Users failed: HTTP " + resp.getStatus() + " - " + resp.body());
      } else {
         JSONArray items = JSON.parseArray(resp.body());
         List<EmbyClientAdminUserUtils.SelectableUser> users = new ArrayList<>();
         if (items != null) {
            for (int i = 0; i < items.size(); i++) {
               JSONObject u = items.getJSONObject(i);
               JSONObject policy = u.getJSONObject("Policy");
               boolean isAdmin = policy != null && Boolean.TRUE.equals(policy.getBoolean("IsAdministrator"));
               boolean isDisabled = policy != null && Boolean.TRUE.equals(policy.getBoolean("IsDisabled"));
               if (!isAdmin && !isDisabled) {
                  String id = u.getString("Id");
                  String name = u.getString("Name");
                  String primaryImageTag = u.getString("PrimaryImageTag");
                  String avatarUrl = primaryImageTag != null && !primaryImageTag.isBlank()
                     ? safeBaseUrl + "/Users/" + id + "/Images/Primary?tag=" + primaryImageTag + "&quality=90&maxWidth=80"
                     : null;
                  users.add(new EmbyClientAdminUserUtils.SelectableUser(id, name, avatarUrl));
               }
            }
         }

         users.sort(Comparator.comparing(user -> user.name == null ? "" : user.name.toLowerCase(Locale.ROOT)));
         return users;
      }
   }

   public static List<EmbyClientAdminUserUtils.SelectableUser> listEnabledNonAdministratorUsersNoPaging(String baseUrl, String token) {
      return listEnabledNonAdministratorUsersNoPaging(baseUrl, token, 10000);
   }

   public static class AdminUser {
      public final String id;
      public final String name;
      public final String lastLoginDate;

      public AdminUser(String id, String name, String lastLoginDate) {
         this.id = id;
         this.name = name;
         this.lastLoginDate = lastLoginDate;
      }

      @Override
      public String toString() {
         return "AdminUser{id='%s', name='%s', lastLogin='%s'}".formatted(this.id, this.name, this.lastLoginDate);
      }
   }

   public static class SelectableUser {
      public final String id;
      public final String name;
      public final String avatarUrl;

      public SelectableUser(String id, String name, String avatarUrl) {
         this.id = id;
         this.name = name;
         this.avatarUrl = avatarUrl;
      }
   }
}
