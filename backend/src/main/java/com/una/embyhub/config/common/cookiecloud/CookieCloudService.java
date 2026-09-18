package com.una.embyhub.config.common.cookiecloud;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import lombok.Generated;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class CookieCloudService {
   @Generated
   private static final Logger log = LoggerFactory.getLogger(CookieCloudService.class);
   private static final int BLOCK_SIZE = 16;
   private static final String SALTED_PREFIX = "Salted__";
   private static final String AES_ALGORITHM = "AES/CBC/NoPadding";
   private final CookieCloudProperties properties;
   private final RestTemplate restTemplate;

   public CookieCloudService(CookieCloudProperties properties) {
      this.properties = properties;
      this.restTemplate = new RestTemplate();
   }

   public CookieCloudService(CookieCloudProperties properties, RestTemplate restTemplate) {
      this.properties = properties;
      this.restTemplate = restTemplate;
   }

   public boolean checkConnection() {
      try {
         ResponseEntity<String> response = this.restTemplate.getForEntity(this.properties.getUrl(), String.class);
         return response.getStatusCode().is2xxSuccessful();
      } catch (Exception var2) {
         log.warn("CookieCloud 连接检查失败: {}", var2.getMessage());
         return false;
      }
   }

   public boolean updateCookie(Map<String, Object> cookieData) {
      try {
         Map<String, Object> dataToEncrypt;
         if (!cookieData.containsKey("cookie_data")) {
            dataToEncrypt = new HashMap<>();
            dataToEncrypt.put("cookie_data", cookieData);
         } else {
            dataToEncrypt = cookieData;
         }

         String rawData = JSON.toJSONString(dataToEncrypt);
         String encryptedData = this.encrypt(rawData, this.getTheKey());
         String updateUrl = this.properties.getUrl() + "/update";
         HttpHeaders headers = new HttpHeaders();
         headers.setContentType(MediaType.APPLICATION_JSON);
         Map<String, String> requestBody = new HashMap<>();
         requestBody.put("uuid", this.properties.getUuid());
         requestBody.put("encrypted", encryptedData);
         HttpEntity<Map<String, String>> entity = new HttpEntity<>(requestBody, headers);
         ResponseEntity<String> response = this.restTemplate.postForEntity(updateUrl, entity, String.class);
         if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
            JSONObject jsonResponse = JSON.parseObject(response.getBody());
            String action = jsonResponse.getString("action");
            if ("done".equals(action)) {
               log.info("CookieCloud Cookie 上传成功");
               return true;
            }
         }

         log.warn("CookieCloud Cookie 上传失败: {}", response.getBody());
         return false;
      } catch (Exception var12) {
         log.error("CookieCloud Cookie 上传异常: {}", var12.getMessage(), var12);
         return false;
      }
   }

   public Map<String, Object> getCookie() {
      try {
         String getUrl = this.properties.getUrl() + "/get/" + this.properties.getUuid();
         ResponseEntity<String> response = this.restTemplate.getForEntity(getUrl, String.class);
         if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
            JSONObject jsonResponse = JSON.parseObject(response.getBody());
            String encrypted = jsonResponse.getString("encrypted");
            if (encrypted != null && !encrypted.isEmpty()) {
               String decrypted = this.decrypt(encrypted, this.getTheKey());
               JSONObject cookieJson = JSON.parseObject(decrypted);
               return JSON.toJavaObject(cookieJson, Map.class);
            }
         }

         log.warn("CookieCloud Cookie 获取失败: {}", response.getBody());
         return null;
      } catch (Exception var7) {
         log.error("CookieCloud Cookie 获取异常: {}", var7.getMessage(), var7);
         return null;
      }
   }

   public String getTheKey() {
      return getTheKey(this.properties.getUuid(), this.properties.getPassword());
   }

   public static String getTheKey(String uuid, String password) {
      try {
         MessageDigest md5 = MessageDigest.getInstance("MD5");
         byte[] digest = md5.digest((uuid + "-" + password).getBytes(StandardCharsets.UTF_8));
         return bytesToHex(digest).substring(0, 16);
      } catch (Exception var4) {
         throw new RuntimeException("生成密钥失败", var4);
      }
   }

   public String encrypt(String message, String passphrase) {
      try {
         SecureRandom random = new SecureRandom();
         byte[] salt = new byte[8];
         random.nextBytes(salt);
         byte[] keyIv = this.bytesToKey(passphrase.getBytes(StandardCharsets.UTF_8), salt, 48);
         byte[] key = Arrays.copyOfRange(keyIv, 0, 32);
         byte[] iv = Arrays.copyOfRange(keyIv, 32, 48);
         SecretKeySpec secretKey = new SecretKeySpec(key, "AES");
         IvParameterSpec ivSpec = new IvParameterSpec(iv);
         Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");
         cipher.init(1, secretKey, ivSpec);
         byte[] paddedMessage = this.applyPadding(message.getBytes(StandardCharsets.UTF_8));
         byte[] encrypted = cipher.doFinal(paddedMessage);
         byte[] result = new byte[16 + encrypted.length];
         System.arraycopy("Salted__".getBytes(StandardCharsets.US_ASCII), 0, result, 0, 8);
         System.arraycopy(salt, 0, result, 8, 8);
         System.arraycopy(encrypted, 0, result, 16, encrypted.length);
         return Base64.getEncoder().encodeToString(result);
      } catch (Exception var14) {
         throw new RuntimeException("加密失败", var14);
      }
   }

   public String decrypt(String encrypted, String passphrase) {
      try {
         byte[] data = Base64.getDecoder().decode(encrypted);
         String prefix = new String(data, 0, 8, StandardCharsets.US_ASCII);
         if (!"Salted__".equals(prefix)) {
            throw new IllegalArgumentException("无效的加密数据格式：缺少 Salted__ 前缀");
         } else {
            byte[] salt = Arrays.copyOfRange(data, 8, 16);
            byte[] ciphertext = Arrays.copyOfRange(data, 16, data.length);
            byte[] keyIv = this.bytesToKey(passphrase.getBytes(StandardCharsets.UTF_8), salt, 48);
            byte[] key = Arrays.copyOfRange(keyIv, 0, 32);
            byte[] iv = Arrays.copyOfRange(keyIv, 32, 48);
            SecretKeySpec secretKey = new SecretKeySpec(key, "AES");
            IvParameterSpec ivSpec = new IvParameterSpec(iv);
            Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");
            cipher.init(2, secretKey, ivSpec);
            byte[] decrypted = cipher.doFinal(ciphertext);
            byte[] unpadded = this.removePadding(decrypted);
            return new String(unpadded, StandardCharsets.UTF_8);
         }
      } catch (Exception var15) {
         throw new RuntimeException("解密失败", var15);
      }
   }

   private byte[] bytesToKey(byte[] data, byte[] salt, int output) {
      try {
         if (salt.length != 8) {
            throw new IllegalArgumentException("Salt 必须是 8 字节");
         } else {
            byte[] dataAndSalt = new byte[data.length + salt.length];
            System.arraycopy(data, 0, dataAndSalt, 0, data.length);
            System.arraycopy(salt, 0, dataAndSalt, data.length, salt.length);
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            byte[] key = md5.digest(dataAndSalt);
            byte[] finalKey = Arrays.copyOf(key, key.length);

            while (finalKey.length < output) {
               byte[] combined = new byte[key.length + dataAndSalt.length];
               System.arraycopy(key, 0, combined, 0, key.length);
               System.arraycopy(dataAndSalt, 0, combined, key.length, dataAndSalt.length);
               key = md5.digest(combined);
               byte[] newFinalKey = new byte[finalKey.length + key.length];
               System.arraycopy(finalKey, 0, newFinalKey, 0, finalKey.length);
               System.arraycopy(key, 0, newFinalKey, finalKey.length, key.length);
               finalKey = newFinalKey;
            }

            return Arrays.copyOf(finalKey, output);
         }
      } catch (Exception var10) {
         throw new RuntimeException("密钥派生失败", var10);
      }
   }

   private byte[] applyPadding(byte[] data) {
      int padding = 16 - data.length % 16;
      byte[] padded = new byte[data.length + padding];
      System.arraycopy(data, 0, padded, 0, data.length);
      Arrays.fill(padded, data.length, padded.length, (byte)padding);
      return padded;
   }

   private byte[] removePadding(byte[] data) {
      int padding = data[data.length - 1] & 255;
      if (padding >= 1 && padding <= 16) {
         return Arrays.copyOf(data, data.length - padding);
      } else {
         throw new IllegalArgumentException("无效的 PKCS7 填充");
      }
   }

   private static String bytesToHex(byte[] bytes) {
      StringBuilder sb = new StringBuilder();

      for (byte b : bytes) {
         sb.append(String.format("%02x", b));
      }

      return sb.toString();
   }
}
