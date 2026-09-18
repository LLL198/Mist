package com.una.embyhub.service;

import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.una.embyhub.model.entity.EmbyUser;
import java.util.Collection;
import java.util.Objects;

public final class EmbyUserIdentityUtils {
   public static final String ACCOUNT_REQUIRES_ADMIN_MESSAGE = "当前账户暂时无法登录，请联系管理员处理";

   private EmbyUserIdentityUtils() {
   }

   public static Long newIdentityGroupId() {
      return IdWorker.getId();
   }

   public static boolean belongToSameIdentity(Collection<EmbyUser> users) {
      return users != null && !users.isEmpty() && !users.stream().anyMatch(Objects::isNull)
         ? users.stream().map(EmbyUserIdentityUtils::effectiveIdentityKey).distinct().limit(2L).count() == 1L
         : false;
   }

   private static String effectiveIdentityKey(EmbyUser user) {
      if (user.getIdentityGroupId() != null) {
         return "group:" + user.getIdentityGroupId();
      } else {
         return user.getId() != null ? "legacy-user:" + user.getId() : "transient-user:" + System.identityHashCode(user);
      }
   }
}
