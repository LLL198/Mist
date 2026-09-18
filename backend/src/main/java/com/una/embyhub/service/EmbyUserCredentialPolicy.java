package com.una.embyhub.service;

import cn.dev33.satoken.secure.SaSecureUtil;
import com.una.embyhub.config.common.exception.BizException;
import com.una.embyhub.config.common.utils.RedisLockUtils;
import com.una.embyhub.mapper.EmbyUserMapper;
import java.util.Locale;
import lombok.Generated;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class EmbyUserCredentialPolicy {
   public static final String CREDENTIAL_UNAVAILABLE_MESSAGE = "账户名和密码不可用";
   private static final String CREDENTIAL_LOCK_PREFIX = "foam:credential:name:";
   private static final long CREDENTIAL_LOCK_TTL_SECONDS = 600L;
   private final EmbyUserMapper embyUserMapper;
   private final RedisLockUtils redisLockUtils;

   public EmbyUserCredentialPolicy.RegistrationGuard guardIndependentRegistration(String userName, String rawPassword) {
      return this.guardCredential(userName, rawPassword, null, null);
   }

   public EmbyUserCredentialPolicy.RegistrationGuard guardIdentityMemberCreation(Long identityGroupId, String userName, String rawPassword) {
      if (identityGroupId == null) {
         throw new BizException("账户名和密码不可用");
      } else {
         return this.guardCredential(userName, rawPassword, null, identityGroupId);
      }
   }

   public EmbyUserCredentialPolicy.RegistrationGuard guardCredentialChange(Long currentUserId, Long identityGroupId, String userName, String rawPassword) {
      if (currentUserId == null) {
         throw new BizException("账户名和密码不可用");
      } else {
         return this.guardCredential(userName, rawPassword, currentUserId, identityGroupId);
      }
   }

   private EmbyUserCredentialPolicy.RegistrationGuard guardCredential(String userName, String rawPassword, Long excludeUserId, Long excludeIdentityGroupId) {
      if (StringUtils.hasText(userName) && StringUtils.hasText(rawPassword)) {
         String lockKey = "foam:credential:name:" + this.normalizeUserName(userName);
         String lockToken = this.redisLockUtils.tryLock(lockKey, 600L);
         if (!StringUtils.hasText(lockToken)) {
            throw new BizException("当前账户正在处理中，请稍后再试");
         } else {
            boolean accepted = false;

            EmbyUserCredentialPolicy.RegistrationGuard var10;
            try {
               long matches = this.embyUserMapper
                  .countActiveCredentialMatches(userName.trim(), SaSecureUtil.md5(rawPassword), excludeUserId, excludeIdentityGroupId);
               if (matches > 0L) {
                  throw new BizException("账户名和密码不可用");
               }

               accepted = true;
               var10 = new EmbyUserCredentialPolicy.RegistrationGuard(this.redisLockUtils, lockKey, lockToken);
            } finally {
               if (!accepted) {
                  this.redisLockUtils.unlock(lockKey, lockToken);
               }
            }

            return var10;
         }
      } else {
         throw new BizException("账户名和密码不可用");
      }
   }

   private String normalizeUserName(String userName) {
      return userName.trim().toLowerCase(Locale.ROOT);
   }

   @Generated
   public EmbyUserCredentialPolicy(final EmbyUserMapper embyUserMapper, final RedisLockUtils redisLockUtils) {
      this.embyUserMapper = embyUserMapper;
      this.redisLockUtils = redisLockUtils;
   }

   public static final class RegistrationGuard implements AutoCloseable {
      private final RedisLockUtils redisLockUtils;
      private final String lockKey;
      private final String lockToken;
      private boolean closed;

      private RegistrationGuard(RedisLockUtils redisLockUtils, String lockKey, String lockToken) {
         this.redisLockUtils = redisLockUtils;
         this.lockKey = lockKey;
         this.lockToken = lockToken;
      }

      @Override
      public void close() {
         if (!this.closed) {
            this.closed = true;
            this.redisLockUtils.unlock(this.lockKey, this.lockToken);
         }
      }
   }
}
