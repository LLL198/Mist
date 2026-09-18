package com.una.embyhub.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.una.embyhub.config.common.wechatbot.WechatBotCrypto;
import com.una.embyhub.config.common.wechatbot.WechatBotMenuService;
import com.una.embyhub.config.common.wechatbot.WechatBotService;
import com.una.embyhub.config.common.wechatbot.WechatMessageParser;
import com.una.embyhub.model.dto.request.wechat.WechatBotMessage;
import java.util.List;
import java.util.Optional;
import lombok.Generated;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping({"/wechat/bot"})
public class WechatBotController {
   @Generated
   private static final Logger log = LoggerFactory.getLogger(WechatBotController.class);
   private final WechatBotService wechatBotService;
   private final WechatBotCrypto wechatBotCrypto;
   private final WechatBotMenuService wechatBotMenuService;

   public WechatBotController(WechatBotService wechatBotService, WechatBotCrypto wechatBotCrypto, WechatBotMenuService wechatBotMenuService) {
      this.wechatBotService = wechatBotService;
      this.wechatBotCrypto = wechatBotCrypto;
      this.wechatBotMenuService = wechatBotMenuService;
   }

   @GetMapping
   public String echo(
      @RequestParam(value = "echostr",required = false) String echo,
      @RequestParam(value = "msg_signature",required = false) String msgSignature,
      @RequestParam(value = "timestamp",required = false) String timestamp,
      @RequestParam(value = "nonce",required = false) String nonce
   ) {
      log.info("===== 企业微信回调验证开始 =====");
      log.info("收到参数: echostr={}, msg_signature={}, timestamp={}, nonce={}", echo, msgSignature, timestamp, nonce);
      log.info("加密模式状态: isEnabled={}", this.wechatBotCrypto.isEnabled());
      log.info("签名参数检查: hasSignatureParams={}", this.wechatBotCrypto.hasSignatureParams(msgSignature, timestamp, nonce));
      if (this.wechatBotCrypto.isEnabled() && StringUtils.hasText(echo) && this.wechatBotCrypto.hasSignatureParams(msgSignature, timestamp, nonce)) {
         log.info("进入加密模式解密流程...");
         String result = this.wechatBotCrypto.decryptEcho(echo, msgSignature, timestamp, nonce).orElse("");
         log.info("解密结果: {}", StringUtils.hasText(result) ? "成功，长度=" + result.length() : "失败，返回空");
         log.info("===== 企业微信回调验证结束 =====");
         return result;
      } else {
         log.warn("拒绝未通过企业微信加密验签的回调验证请求");
         log.info("===== 企业微信回调验证结束 =====");
         return "";
      }
   }

   @PostMapping(
      consumes = {"text/xml", "application/xml"},
      produces = {"text/xml"}
   )
   public String onMessage(
      @RequestBody String rawXml,
      @RequestParam(value = "msg_signature",required = false) String msgSignature,
      @RequestParam(value = "timestamp",required = false) String timestamp,
      @RequestParam(value = "nonce",required = false) String nonce
   ) {
      boolean encrypted = this.wechatBotCrypto.isEnabled()
         && this.wechatBotCrypto.hasSignatureParams(msgSignature, timestamp, nonce)
         && this.wechatBotCrypto.containsEncryptTag(rawXml);
      if (!encrypted) {
         log.warn("拒绝未通过企业微信加密验签的消息回调");
         return "";
      } else {
         String plainXml = this.wechatBotCrypto.decryptMessage(rawXml, msgSignature, timestamp, nonce).orElse("");
         if (!StringUtils.hasText(plainXml)) {
            return "";
         } else {
            Optional<WechatBotMessage> parsed = WechatMessageParser.parseIncoming(plainXml);
            if (!parsed.isPresent()) {
               log.warn("收到无法解析的企业微信消息: {}", plainXml);
               return "";
            } else {
               WechatBotMessage request = parsed.get();
               String content = request.getContent();
               String searchReply = this.wechatBotService.handleSearchInteraction(content, request.getFromUser());
               if (StringUtils.hasText(searchReply)) {
                  String responseXml = WechatMessageParser.buildTextResponse(request, searchReply);
                  return this.wechatBotCrypto.encryptResponse(responseXml, timestamp, nonce);
               } else {
                  if (this.wechatBotService.isPageNavigationCommand(content, request.getFromUser())) {
                     List<WechatMessageParser.NewsArticle> articles = this.wechatBotService.handlePageNavigation(content, request.getFromUser());
                     if (!articles.isEmpty()) {
                        String responseXml = WechatMessageParser.buildNewsResponse(request, articles);
                        return this.wechatBotCrypto.encryptResponse(responseXml, timestamp, nonce);
                     }
                  }

                  if (this.wechatBotService.isNumberSelectionCommand(content, request.getFromUser())) {
                     List<WechatMessageParser.NewsArticle> articles = this.wechatBotService.handleNumberSelection(content, request.getFromUser());
                     if (!articles.isEmpty()) {
                        String responseXml = WechatMessageParser.buildNewsResponse(request, articles);
                        return this.wechatBotCrypto.encryptResponse(responseXml, timestamp, nonce);
                     }
                  }

                  if (this.wechatBotService.isEmbySearchCommand(content)) {
                     if (!this.wechatBotService.isPrivilegedSender(request.getFromUser())) {
                        String responseXml = WechatMessageParser.buildTextResponse(request, this.wechatBotService.buildForbiddenSenderMessage());
                        return this.wechatBotCrypto.encryptResponse(responseXml, timestamp, nonce);
                     } else {
                        String query = this.wechatBotService.extractEmbySearchQuery(content);
                        if (StringUtils.hasText(query)) {
                           int serverNumber = this.wechatBotService.extractEmbyServerNumber(content);
                           List<WechatMessageParser.NewsArticle> articles = this.wechatBotService
                              .searchEmbyWithImages(query, serverNumber, request.getFromUser());
                           if (articles == null) {
                              String errorText = this.wechatBotService.buildEmbyServerError();
                              String responseXml = WechatMessageParser.buildTextResponse(request, errorText);
                              return this.wechatBotCrypto.encryptResponse(responseXml, timestamp, nonce);
                           } else if (!articles.isEmpty()) {
                              String responseXml = WechatMessageParser.buildNewsResponse(request, articles);
                              return this.wechatBotCrypto.encryptResponse(responseXml, timestamp, nonce);
                           } else {
                              String notFoundText = this.wechatBotService.buildEmbySearchNotFound(query);
                              String responseXml = WechatMessageParser.buildTextResponse(request, notFoundText);
                              return this.wechatBotCrypto.encryptResponse(responseXml, timestamp, nonce);
                           }
                        } else {
                           String guideText = this.wechatBotService.buildEmbySearchGuide();
                           String responseXml = WechatMessageParser.buildTextResponse(request, guideText);
                           return this.wechatBotCrypto.encryptResponse(responseXml, timestamp, nonce);
                        }
                     }
                  } else {
                     if (this.wechatBotService.isSearchCommand(content)) {
                        String query = this.wechatBotService.extractSearchQuery(content);
                        if (StringUtils.hasText(query)) {
                           List<WechatMessageParser.NewsArticle> articles = this.wechatBotService.searchMovieWithImages(query, request.getFromUser());
                           if (!articles.isEmpty()) {
                              String responseXml = WechatMessageParser.buildNewsResponse(request, articles);
                              return this.wechatBotCrypto.encryptResponse(responseXml, timestamp, nonce);
                           }
                        }
                     }

                     String responseText = this.wechatBotService.handleMessage(content, request.getFromUser());
                     String responseXml = WechatMessageParser.buildTextResponse(request, responseText);
                     return this.wechatBotCrypto.encryptResponse(responseXml, timestamp, nonce);
                  }
               }
            }
         }
      }
   }

   @PostMapping({"/menu/sync"})
   @SaCheckPermission({"admin"})
   public String syncMenu() {
      return this.wechatBotMenuService.syncMenu() ? "ok" : "menu not enabled or sync failed";
   }
}
