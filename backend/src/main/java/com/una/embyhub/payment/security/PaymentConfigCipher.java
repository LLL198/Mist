package com.una.embyhub.payment.security;

import com.una.embyhub.config.common.enums.ResponseStatusEnum;
import com.una.embyhub.config.common.exception.BizException;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.nio.file.AtomicMoveNotSupportedException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.nio.file.StandardOpenOption;
import java.nio.file.attribute.PosixFilePermission;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.EnumSet;
import java.util.Set;
import javax.crypto.Cipher;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class PaymentConfigCipher {
   private static final int NONCE_LENGTH = 12;
   private static final int GCM_TAG_BITS = 128;
   private static final int MIN_KEY_LENGTH = 32;
   private static final int MAX_KEY_LENGTH = 256;
   private static final Set<PosixFilePermission> OWNER_ONLY_PERMISSIONS = EnumSet.of(PosixFilePermission.OWNER_READ, PosixFilePermission.OWNER_WRITE);
   private final SecureRandom secureRandom = new SecureRandom();
   @Value("${FOAM_PAYMENT_CONFIG_ENCRYPTION_KEY:}")
   private String environmentKey;
   @Value("${foam.payment.config-key-file:data/payment-config.key}")
   private String keyFilePath;
   private volatile String cachedFileKey;

   public boolean isConfigured() {
      try {
         return validKey(this.resolveKey());
      } catch (RuntimeException var2) {
         return false;
      }
   }

   public boolean isAdminEditable() {
      return !validKey(this.environmentKey);
   }

   public synchronized void initializeFromAdminInput(String input) {
      String requested = normalizeCandidate(input);
      String existing = this.resolveKey();
      if (validKey(existing)) {
         if (!MessageDigest.isEqual(existing.getBytes(StandardCharsets.UTF_8), requested.getBytes(StandardCharsets.UTF_8))) {
            throw new BizException("商户密钥保护已初始化，不能通过页面覆盖");
         }
      } else {
         Path target = this.configuredKeyFile();

         try {
            Path parent = target.getParent();
            if (parent == null) {
               throw new IllegalStateException("保护密钥目录无效");
            }

            Files.createDirectories(parent);
            if (Files.exists(target, LinkOption.NOFOLLOW_LINKS)) {
               this.cachedFileKey = this.readFileKey(target);
               if (MessageDigest.isEqual(this.cachedFileKey.getBytes(StandardCharsets.UTF_8), requested.getBytes(StandardCharsets.UTF_8))) {
                  return;
               }

               throw new BizException("商户密钥保护已由其他实例初始化，请刷新页面");
            }

            Path temporary = Files.createTempFile(parent, ".payment-config-", ".tmp");

            try {
               Files.writeString(temporary, requested, StandardCharsets.UTF_8, StandardOpenOption.TRUNCATE_EXISTING, StandardOpenOption.WRITE);
               applyOwnerOnlyPermissions(temporary);
               Files.move(temporary, target);
               applyOwnerOnlyPermissions(target);
            } finally {
               Files.deleteIfExists(temporary);
            }

            this.cachedFileKey = requested;
         } catch (FileAlreadyExistsException var13) {
            this.cachedFileKey = this.readFileKey(target);
            if (!MessageDigest.isEqual(this.cachedFileKey.getBytes(StandardCharsets.UTF_8), requested.getBytes(StandardCharsets.UTF_8))) {
               throw new BizException("商户密钥保护已由其他实例初始化，请刷新页面");
            }
         } catch (BizException var14) {
            throw var14;
         } catch (Exception var15) {
            throw new BizException(ResponseStatusEnum.SYSTEM_ERROR.getCode(), "商户密钥保护初始化失败，请检查服务器数据目录权限");
         }
      }
   }

   public synchronized PaymentConfigCipher.KeyRotation rotateFromAdminInput(String input) {
      String requested = normalizeCandidate(input);
      String existing = this.resolveKey();
      if (!validKey(existing)) {
         throw new BizException("商户密钥保护尚未初始化");
      } else if (MessageDigest.isEqual(existing.getBytes(StandardCharsets.UTF_8), requested.getBytes(StandardCharsets.UTF_8))) {
         return PaymentConfigCipher.KeyRotation.unchanged();
      } else if (!this.isAdminEditable()) {
         throw new BizException("商户密钥保护由服务器环境变量管理，不能通过页面更改");
      } else {
         Path target = this.configuredKeyFile();
         this.replaceFileKey(target, requested);
         this.cachedFileKey = requested;
         return new PaymentConfigCipher.KeyRotation(this, target, existing, requested);
      }
   }

   public String encrypt(String plainText) {
      this.requireConfigured();
      if (!StringUtils.hasText(plainText)) {
         throw new BizException("易支付商户密钥不能为空");
      } else {
         try {
            byte[] nonce = new byte[12];
            this.secureRandom.nextBytes(nonce);
            Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
            cipher.init(1, this.secretKey(), new GCMParameterSpec(128, nonce));
            byte[] encrypted = cipher.doFinal(plainText.trim().getBytes(StandardCharsets.UTF_8));
            return Base64.getEncoder().encodeToString(ByteBuffer.allocate(nonce.length + encrypted.length).put(nonce).put(encrypted).array());
         } catch (BizException var5) {
            throw var5;
         } catch (Exception var6) {
            throw new BizException(ResponseStatusEnum.SYSTEM_ERROR.getCode(), "易支付商户密钥加密失败");
         }
      }
   }

   public String decrypt(String cipherText) {
      this.requireConfigured();
      if (!StringUtils.hasText(cipherText)) {
         return null;
      } else {
         try {
            byte[] payload = Base64.getDecoder().decode(cipherText);
            if (payload.length <= 12) {
               throw new IllegalArgumentException("invalid encrypted payload");
            } else {
               byte[] nonce = new byte[12];
               byte[] encrypted = new byte[payload.length - 12];
               System.arraycopy(payload, 0, nonce, 0, nonce.length);
               System.arraycopy(payload, nonce.length, encrypted, 0, encrypted.length);
               Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
               cipher.init(2, this.secretKey(), new GCMParameterSpec(128, nonce));
               return new String(cipher.doFinal(encrypted), StandardCharsets.UTF_8);
            }
         } catch (BizException var6) {
            throw var6;
         } catch (Exception var7) {
            throw new BizException(ResponseStatusEnum.SYSTEM_ERROR.getCode(), "易支付商户密钥解密失败，请检查平台加密主密钥");
         }
      }
   }

   private void requireConfigured() {
      if (!this.isConfigured()) {
         throw new BizException("请先在支付设置页面初始化商户密钥保护");
      }
   }

   private SecretKeySpec secretKey() throws Exception {
      byte[] digest = MessageDigest.getInstance("SHA-256").digest(this.resolveKey().getBytes(StandardCharsets.UTF_8));
      return new SecretKeySpec(digest, "AES");
   }

   private String resolveKey() {
      if (validKey(this.environmentKey)) {
         return this.environmentKey.trim();
      } else if (validKey(this.cachedFileKey)) {
         return this.cachedFileKey;
      } else {
         Path target = this.configuredKeyFile();
         if (!Files.exists(target, LinkOption.NOFOLLOW_LINKS)) {
            return null;
         } else {
            this.cachedFileKey = this.readFileKey(target);
            return this.cachedFileKey;
         }
      }
   }

   private String readFileKey(Path target) {
      try {
         if (Files.isSymbolicLink(target) || !Files.isRegularFile(target, LinkOption.NOFOLLOW_LINKS)) {
            throw new BizException("商户密钥保护文件无效");
         } else if (Files.size(target) > 4096L) {
            throw new BizException("商户密钥保护文件无效");
         } else {
            return normalizeCandidate(Files.readString(target, StandardCharsets.UTF_8));
         }
      } catch (BizException var3) {
         throw var3;
      } catch (Exception var4) {
         throw new BizException(ResponseStatusEnum.SYSTEM_ERROR.getCode(), "商户密钥保护文件读取失败");
      }
   }

   private Path configuredKeyFile() {
      if (!StringUtils.hasText(this.keyFilePath)) {
         throw new BizException("商户密钥保护文件路径无效");
      } else {
         return Paths.get(this.keyFilePath.trim()).toAbsolutePath().normalize();
      }
   }

   private void replaceFileKey(Path target, String key) {
      try {
         Path parent = target.getParent();
         if (parent == null) {
            throw new IllegalStateException("保护密钥目录无效");
         } else {
            Files.createDirectories(parent);
            Path temporary = Files.createTempFile(parent, ".payment-config-", ".tmp");

            try {
               Files.writeString(temporary, key, StandardCharsets.UTF_8, StandardOpenOption.TRUNCATE_EXISTING, StandardOpenOption.WRITE);
               applyOwnerOnlyPermissions(temporary);

               try {
                  Files.move(temporary, target, StandardCopyOption.ATOMIC_MOVE, StandardCopyOption.REPLACE_EXISTING);
               } catch (AtomicMoveNotSupportedException var11) {
                  Files.move(temporary, target, StandardCopyOption.REPLACE_EXISTING);
               }

               applyOwnerOnlyPermissions(target);
            } finally {
               Files.deleteIfExists(temporary);
            }
         }
      } catch (BizException var13) {
         throw var13;
      } catch (Exception var14) {
         throw new BizException(ResponseStatusEnum.SYSTEM_ERROR.getCode(), "商户密钥保护文件更新失败，请检查服务器数据目录权限");
      }
   }

   private synchronized void rollbackRotation(Path target, String previousKey, String rotatedKey) {
      String current = this.readFileKey(target);
      if (!MessageDigest.isEqual(current.getBytes(StandardCharsets.UTF_8), rotatedKey.getBytes(StandardCharsets.UTF_8))) {
         throw new BizException("商户密钥保护已被再次更改，无法自动恢复旧密钥");
      } else {
         this.replaceFileKey(target, previousKey);
         this.cachedFileKey = previousKey;
      }
   }

   private static String normalizeCandidate(String input) {
      if (!StringUtils.hasText(input)) {
         throw new BizException("请输入商户密钥保护密钥");
      } else {
         String normalized = input.trim();
         if (normalized.length() < 32 || normalized.length() > 256) {
            throw new BizException("保护密钥长度需为 32 到 256 个字符");
         } else if (normalized.chars().anyMatch(Character::isISOControl)) {
            throw new BizException("保护密钥不能包含控制字符");
         } else {
            return normalized;
         }
      }
   }

   private static boolean validKey(String value) {
      if (!StringUtils.hasText(value)) {
         return false;
      } else {
         int length = value.trim().length();
         return length >= 32 && length <= 256;
      }
   }

   private static void applyOwnerOnlyPermissions(Path path) throws Exception {
      try {
         Files.setPosixFilePermissions(path, OWNER_ONLY_PERMISSIONS);
      } catch (UnsupportedOperationException var2) {
      }
   }

   public static final class KeyRotation {
      private final PaymentConfigCipher cipher;
      private final Path target;
      private final String previousKey;
      private final String rotatedKey;
      private boolean rolledBack;

      private KeyRotation(PaymentConfigCipher cipher, Path target, String previousKey, String rotatedKey) {
         this.cipher = cipher;
         this.target = target;
         this.previousKey = previousKey;
         this.rotatedKey = rotatedKey;
      }

      private KeyRotation() {
         this(null, null, null, null);
      }

      private static PaymentConfigCipher.KeyRotation unchanged() {
         return new PaymentConfigCipher.KeyRotation();
      }

      public boolean changed() {
         return this.cipher != null;
      }

      public synchronized void rollback() {
         if (this.changed() && !this.rolledBack) {
            this.cipher.rollbackRotation(this.target, this.previousKey, this.rotatedKey);
            this.rolledBack = true;
         }
      }
   }
}
