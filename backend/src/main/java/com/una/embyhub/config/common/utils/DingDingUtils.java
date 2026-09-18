package com.una.embyhub.config.common.utils;

import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.OapiRobotSendRequest;
import com.dingtalk.api.request.OapiRobotSendRequest.Markdown;
import com.dingtalk.api.response.OapiRobotSendResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import lombok.Generated;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DingDingUtils {
   @Generated
   private static final Logger log = LoggerFactory.getLogger(DingDingUtils.class);
   private static final String WEBHOOK_BASE_URL = "https://oapi.dingtalk.com/robot/send";

   public static void sendMarkdownMessage(String accessToken, String secret, String title, String text) throws Exception {
      long timestamp = System.currentTimeMillis();
      String sign = generateSign(timestamp, secret);
      String fullUrl = "https://oapi.dingtalk.com/robot/send?access_token=" + accessToken + "&timestamp=" + timestamp + "&sign=" + sign;
      DingTalkClient client = new DefaultDingTalkClient(fullUrl);
      OapiRobotSendRequest request = new OapiRobotSendRequest();
      request.setMsgtype("markdown");
      Markdown markdown = new Markdown();
      markdown.setTitle(title);
      markdown.setText(text);
      request.setMarkdown(markdown);
      OapiRobotSendResponse response = client.execute(request);
      handleResponse(response);
   }

   private static String generateSign(long timestamp, String secret) throws NoSuchAlgorithmException, InvalidKeyException, UnsupportedEncodingException {
      String stringToSign = timestamp + "\n" + secret;
      Mac mac = Mac.getInstance("HmacSHA256");
      mac.init(new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8), "HmacSHA256"));
      byte[] signData = mac.doFinal(stringToSign.getBytes(StandardCharsets.UTF_8));
      String sign = Base64.getEncoder().encodeToString(signData);
      return URLEncoder.encode(sign, "UTF-8");
   }

   private static void handleResponse(OapiRobotSendResponse response) {
      if (response.isSuccess()) {
         log.info("钉钉消息发送成功");
      } else {
         log.info("钉钉消息发送失败，错误码：" + response.getErrorCode());
         log.info("钉钉错误信息：" + response.getErrmsg());
         throw new RuntimeException("钉钉消息发送失败");
      }
   }
}
