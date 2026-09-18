package com.una.embyhub.config.common.wechatbot;

import com.alibaba.fastjson2.JSONObject;
import com.una.embyhub.config.common.utils.NotifyChannelCacheLoaderUtils;
import jakarta.annotation.PostConstruct;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Base64;
import java.util.Optional;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import lombok.Generated;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

@Component
public class WechatBotCrypto {
   @Generated
   private static final Logger log = LoggerFactory.getLogger(WechatBotCrypto.class);
   private final NotifyChannelCacheLoaderUtils notifyChannelCacheLoaderUtils;
   private final SecureRandom secureRandom = new SecureRandom();
   private byte[] aesKey;
   private boolean enabled;

   public WechatBotCrypto(NotifyChannelCacheLoaderUtils notifyChannelCacheLoaderUtils) {
      this.notifyChannelCacheLoaderUtils = notifyChannelCacheLoaderUtils;
   }

   @PostConstruct
   public void init() {
      WechatBotProperties properties = this.getProperties();
      if (properties == null) {
         log.warn("init: 企业微信配置未找到，禁用加解密支持");
         this.enabled = false;
      } else {
         this.enabled = StringUtils.hasText(properties.getToken())
            && StringUtils.hasText(properties.getEncodingAesKey())
            && StringUtils.hasText(properties.getCorpId());
         if (this.enabled) {
            try {
               this.aesKey = Base64.getDecoder().decode(properties.getEncodingAesKey() + "=");
            } catch (IllegalArgumentException var3) {
               log.warn("企业微信 EncodingAESKey 非法，禁用加解密支持", (Throwable)var3);
               this.enabled = false;
            }
         }
      }
   }

   public boolean isEnabled() {
      return this.enabled;
   }

   public boolean hasSignatureParams(String msgSignature, String timestamp, String nonce) {
      return StringUtils.hasText(msgSignature) && StringUtils.hasText(timestamp) && StringUtils.hasText(nonce);
   }

   public Optional<String> decryptEcho(String echoStr, String msgSignature, String timestamp, String nonce) {
      log.info("decryptEcho: 开始解密 echoStr...");
      if (!this.enabled) {
         log.warn("decryptEcho: 加密模式未启用，直接返回原始 echoStr");
         return Optional.ofNullable(echoStr);
      } else {
         WechatBotProperties properties = this.getProperties();
         if (properties == null) {
            log.warn("decryptEcho: 无法获取配置");
            return Optional.empty();
         } else {
            log.info("decryptEcho: 验证签名中... timestamp={}, nonce={}", timestamp, nonce);
            if (!this.verifySignature(msgSignature, timestamp, nonce, echoStr, properties)) {
               log.warn("decryptEcho: 企业微信 echoStr 验签失败! 期望签名={}, 收到签名={}", this.sign(timestamp, nonce, echoStr, properties), msgSignature);
               return Optional.empty();
            } else {
               log.info("decryptEcho: 签名验证通过，开始解密...");
               Optional<String> result = this.decrypt(echoStr, properties);
               log.info("decryptEcho: 解密结果={}", result.isPresent() ? "成功" : "失败");
               return result;
            }
         }
      }
   }

   public Optional<String> decryptMessage(String rawXml, String msgSignature, String timestamp, String nonce) {
      if (!this.enabled) {
         return Optional.of(rawXml);
      } else {
         String cipherText = this.extractEncrypt(rawXml);
         if (!StringUtils.hasText(cipherText)) {
            log.warn("未发现企业微信 Encrypt 字段，按明文处理");
            return Optional.of(rawXml);
         } else {
            WechatBotProperties properties = this.getProperties();
            if (properties == null) {
               log.warn("decryptMessage: 无法获取配置");
               return Optional.empty();
            } else if (!this.verifySignature(msgSignature, timestamp, nonce, cipherText, properties)) {
               log.warn("企业微信消息验签失败");
               return Optional.empty();
            } else {
               return this.decrypt(cipherText, properties);
            }
         }
      }
   }

   public String encryptResponse(String plainXml, String timestamp, String nonce) {
      if (!this.enabled) {
         return plainXml;
      } else {
         WechatBotProperties properties = this.getProperties();
         if (properties == null) {
            log.warn("encryptResponse: 无法获取配置，返回原文");
            return plainXml;
         } else {
            String realTimestamp = StringUtils.hasText(timestamp) ? timestamp : String.valueOf(System.currentTimeMillis() / 1000L);
            String realNonce = StringUtils.hasText(nonce) ? nonce : this.randomNonce();
            String cipher = this.encrypt(plainXml, properties);
            if (cipher == null) {
               return plainXml;
            } else {
               String signature = this.sign(realTimestamp, realNonce, cipher, properties);
               return "<xml>"
                  + wrap("Encrypt", cdata(cipher))
                  + wrap("MsgSignature", cdata(signature))
                  + wrap("TimeStamp", realTimestamp)
                  + wrap("Nonce", cdata(realNonce))
                  + "</xml>";
            }
         }
      }
   }

   public boolean containsEncryptTag(String xml) {
      return xml != null && xml.contains("<Encrypt>");
   }

   private Optional<String> decrypt(String cipherText, WechatBotProperties properties) {
      try {
         Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");
         SecretKeySpec keySpec = new SecretKeySpec(this.aesKey, "AES");
         IvParameterSpec iv = new IvParameterSpec(Arrays.copyOfRange(this.aesKey, 0, 16));
         cipher.init(2, keySpec, iv);
         byte[] decrypted = cipher.doFinal(Base64.getDecoder().decode(cipherText));
         byte[] bytes = this.removePadding(decrypted);
         byte[] networkOrder = Arrays.copyOfRange(bytes, 16, 20);
         int xmlLength = ByteBuffer.wrap(networkOrder).order(ByteOrder.BIG_ENDIAN).getInt();
         String xml = new String(Arrays.copyOfRange(bytes, 20, 20 + xmlLength), StandardCharsets.UTF_8);
         String corpId = new String(Arrays.copyOfRange(bytes, 20 + xmlLength, bytes.length), StandardCharsets.UTF_8);
         if (!properties.getCorpId().equals(corpId)) {
            log.warn("企业微信 corpId 校验失败");
            return Optional.empty();
         } else {
            return Optional.of(xml);
         }
      } catch (Exception var12) {
         log.error("企业微信消息解密失败", (Throwable)var12);
         return Optional.empty();
      }
   }

   private String encrypt(String plainText, WechatBotProperties properties) {
      try {
         byte[] random16 = new byte[16];
         this.secureRandom.nextBytes(random16);
         byte[] plainBytes = plainText.getBytes(StandardCharsets.UTF_8);
         byte[] lengthBytes = ByteBuffer.allocate(4).order(ByteOrder.BIG_ENDIAN).putInt(plainBytes.length).array();
         byte[] corpIdBytes = properties.getCorpId().getBytes(StandardCharsets.UTF_8);
         byte[] unPadded = ByteBuffer.allocate(random16.length + lengthBytes.length + plainBytes.length + corpIdBytes.length)
            .put(random16)
            .put(lengthBytes)
            .put(plainBytes)
            .put(corpIdBytes)
            .array();
         byte[] padded = this.applyPadding(unPadded);
         Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");
         SecretKeySpec keySpec = new SecretKeySpec(this.aesKey, "AES");
         IvParameterSpec iv = new IvParameterSpec(Arrays.copyOfRange(this.aesKey, 0, 16));
         cipher.init(1, keySpec, iv);
         return Base64.getEncoder().encodeToString(cipher.doFinal(padded));
      } catch (Exception var12) {
         log.error("企业微信消息加密失败", (Throwable)var12);
         return null;
      }
   }

   private boolean verifySignature(String msgSignature, String timestamp, String nonce, String cipherText, WechatBotProperties properties) {
      if (!this.hasSignatureParams(msgSignature, timestamp, nonce)) {
         return false;
      } else {
         String calculated = this.sign(timestamp, nonce, cipherText, properties);
         return msgSignature.equals(calculated);
      }
   }

   private String sign(String timestamp, String nonce, String cipherText, WechatBotProperties properties) {
      try {
         String[] arr = new String[]{properties.getToken(), timestamp, nonce, cipherText};
         Arrays.sort((Object[])arr);
         String joined = String.join("", arr);
         MessageDigest md = MessageDigest.getInstance("SHA-1");
         byte[] digest = md.digest(joined.getBytes(StandardCharsets.UTF_8));
         StringBuilder sb = new StringBuilder();

         for (byte b : digest) {
            sb.append(String.format("%02x", b));
         }

         return sb.toString();
      } catch (Exception var14) {
         throw new IllegalStateException("计算企业微信签名失败", var14);
      }
   }

   private String randomNonce() {
      byte[] nonce = new byte[8];
      this.secureRandom.nextBytes(nonce);
      StringBuilder sb = new StringBuilder();

      for (byte b : nonce) {
         sb.append(Integer.toHexString(b & 255));
      }

      return sb.toString();
   }

   private String extractEncrypt(String xml) {
      try {
         Document document = WechatMessageParser.parseXmlDocument(xml);
         Node node = document.getElementsByTagName("Encrypt").item(0);
         return node == null ? null : node.getTextContent();
      } catch (Exception var4) {
         log.error("解析企业微信 Encrypt 失败", (Throwable)var4);
         return null;
      }
   }

   private byte[] applyPadding(byte[] input) {
      int blockSize = 32;
      int padLength = blockSize - input.length % blockSize;
      if (padLength == 0) {
         padLength = blockSize;
      }

      byte pad = (byte)padLength;
      byte[] padding = new byte[padLength];
      Arrays.fill(padding, pad);
      byte[] output = Arrays.copyOf(input, input.length + padLength);
      System.arraycopy(padding, 0, output, input.length, padLength);
      return output;
   }

   private byte[] removePadding(byte[] decrypted) {
      int pad = decrypted[decrypted.length - 1];
      if (pad < 1 || pad > 32) {
         pad = 0;
      }

      return Arrays.copyOfRange(decrypted, 0, decrypted.length - pad);
   }

   private static String wrap(String tag, String value) {
      return "<" + tag + ">" + value + "</" + tag + ">";
   }

   private static String cdata(String value) {
      return "<![CDATA[" + (value == null ? "" : value) + "]]>";
   }

   private WechatBotProperties getProperties() {
      String json = this.notifyChannelCacheLoaderUtils.getNotifyChannelValue("wechatBot");
      return !StringUtils.hasText(json) ? null : JSONObject.parseObject(json, WechatBotProperties.class);
   }
}
