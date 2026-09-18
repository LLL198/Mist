package com.una.embyhub.config.common.wechatbot;

import com.una.embyhub.model.dto.request.wechat.WechatBotMessage;
import java.io.StringReader;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import lombok.Generated;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.InputSource;

public class WechatMessageParser {
   @Generated
   private static final Logger log = LoggerFactory.getLogger(WechatMessageParser.class);

   public static Optional<WechatBotMessage> parseIncoming(String xml) {
      try {
         Document document = parseXmlDocument(xml);
         String msgType = getText(document, "MsgType");
         String event = getText(document, "Event");
         String eventKey = getText(document, "EventKey");
         String toUser = getText(document, "ToUserName");
         String fromUser = getText(document, "FromUserName");
         String content = getText(document, "Content");
         if (!StringUtils.hasText(content) && StringUtils.hasText(eventKey)) {
            content = eventKey;
         }

         if (fromUser != null && content != null) {
            WechatBotMessage message = new WechatBotMessage();
            message.setMsgType(msgType);
            message.setEvent(event);
            message.setEventKey(eventKey);
            message.setToUser(toUser);
            message.setFromUser(fromUser);
            message.setContent(content.trim());
            return Optional.of(message);
         } else {
            return Optional.empty();
         }
      } catch (Exception var9) {
         log.error("解析企业微信 XML 消息失败", (Throwable)var9);
         return Optional.empty();
      }
   }

   static Document parseXmlDocument(String xml) throws Exception {
      DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
      factory.setFeature("http://javax.xml.XMLConstants/feature/secure-processing", true);
      disableFeature(factory, "http://apache.org/xml/features/disallow-doctype-decl", true);
      disableFeature(factory, "http://xml.org/sax/features/external-general-entities", false);
      disableFeature(factory, "http://xml.org/sax/features/external-parameter-entities", false);
      disableFeature(factory, "http://apache.org/xml/features/nonvalidating/load-external-dtd", false);
      factory.setXIncludeAware(false);
      factory.setExpandEntityReferences(false);
      factory.setAttribute("http://javax.xml.XMLConstants/property/accessExternalDTD", "");
      factory.setAttribute("http://javax.xml.XMLConstants/property/accessExternalSchema", "");
      DocumentBuilder builder = factory.newDocumentBuilder();
      return builder.parse(new InputSource(new StringReader(xml)));
   }

   private static void disableFeature(DocumentBuilderFactory factory, String feature, boolean value) throws ParserConfigurationException {
      factory.setFeature(feature, value);
   }

   public static String buildTextResponse(WechatBotMessage request, String content) {
      long timestamp = Instant.now().getEpochSecond();
      return "<xml>"
         + wrap("ToUserName", cdata(request.getFromUser()))
         + wrap("FromUserName", cdata(request.getToUser()))
         + wrap("CreateTime", String.valueOf(timestamp))
         + wrap("MsgType", cdata("text"))
         + wrap("Content", cdata(content))
         + "</xml>";
   }

   public static String buildNewsResponse(WechatBotMessage request, List<WechatMessageParser.NewsArticle> articles) {
      if (articles != null && !articles.isEmpty()) {
         long timestamp = Instant.now().getEpochSecond();
         StringBuilder sb = new StringBuilder();
         sb.append("<xml>");
         sb.append(wrap("ToUserName", cdata(request.getFromUser())));
         sb.append(wrap("FromUserName", cdata(request.getToUser())));
         sb.append(wrap("CreateTime", String.valueOf(timestamp)));
         sb.append(wrap("MsgType", cdata("news")));
         sb.append(wrap("ArticleCount", String.valueOf(Math.min(articles.size(), 8))));
         sb.append("<Articles>");

         for (int i = 0; i < Math.min(articles.size(), 8); i++) {
            WechatMessageParser.NewsArticle article = articles.get(i);
            sb.append("<item>");
            sb.append(wrap("Title", cdata(article.getTitle())));
            sb.append(wrap("Description", cdata(article.getDescription())));
            sb.append(wrap("PicUrl", cdata(article.getPicUrl())));
            sb.append(wrap("Url", cdata(article.getUrl() != null ? article.getUrl() : "")));
            sb.append("</item>");
         }

         sb.append("</Articles>");
         sb.append("</xml>");
         return sb.toString();
      } else {
         return buildTextResponse(request, "没有找到相关内容");
      }
   }

   private static String getText(Document document, String tagName) {
      Node node = document.getElementsByTagName(tagName).item(0);
      return node == null ? null : node.getTextContent();
   }

   private static String wrap(String tag, String value) {
      return "<" + tag + ">" + value + "</" + tag + ">";
   }

   private static String cdata(String value) {
      return "<![CDATA[" + (value == null ? "" : value) + "]]>";
   }

   @Generated
   private WechatMessageParser() {
   }

   public static class NewsArticle {
      private String title;
      private String description;
      private String picUrl;
      private String url;

      @Generated
      public String getTitle() {
         return this.title;
      }

      @Generated
      public String getDescription() {
         return this.description;
      }

      @Generated
      public String getPicUrl() {
         return this.picUrl;
      }

      @Generated
      public String getUrl() {
         return this.url;
      }

      @Generated
      public void setTitle(final String title) {
         this.title = title;
      }

      @Generated
      public void setDescription(final String description) {
         this.description = description;
      }

      @Generated
      public void setPicUrl(final String picUrl) {
         this.picUrl = picUrl;
      }

      @Generated
      public void setUrl(final String url) {
         this.url = url;
      }

      @Generated
      @Override
      public boolean equals(final Object o) {
         if (o == this) {
            return true;
         } else if (!(o instanceof WechatMessageParser.NewsArticle other)) {
            return false;
         } else if (!other.canEqual(this)) {
            return false;
         } else {
            Object this$title = this.getTitle();
            Object other$title = other.getTitle();
            if (this$title == null ? other$title == null : this$title.equals(other$title)) {
               Object this$description = this.getDescription();
               Object other$description = other.getDescription();
               if (this$description == null ? other$description == null : this$description.equals(other$description)) {
                  Object this$picUrl = this.getPicUrl();
                  Object other$picUrl = other.getPicUrl();
                  if (this$picUrl == null ? other$picUrl == null : this$picUrl.equals(other$picUrl)) {
                     Object this$url = this.getUrl();
                     Object other$url = other.getUrl();
                     return this$url == null ? other$url == null : this$url.equals(other$url);
                  } else {
                     return false;
                  }
               } else {
                  return false;
               }
            } else {
               return false;
            }
         }
      }

      @Generated
      protected boolean canEqual(final Object other) {
         return other instanceof WechatMessageParser.NewsArticle;
      }

      @Generated
      @Override
      public int hashCode() {
         int PRIME = 59;
         int result = 1;
         Object $title = this.getTitle();
         result = result * 59 + ($title == null ? 43 : $title.hashCode());
         Object $description = this.getDescription();
         result = result * 59 + ($description == null ? 43 : $description.hashCode());
         Object $picUrl = this.getPicUrl();
         result = result * 59 + ($picUrl == null ? 43 : $picUrl.hashCode());
         Object $url = this.getUrl();
         return result * 59 + ($url == null ? 43 : $url.hashCode());
      }

      @Generated
      @Override
      public String toString() {
         return "WechatMessageParser.NewsArticle(title="
            + this.getTitle()
            + ", description="
            + this.getDescription()
            + ", picUrl="
            + this.getPicUrl()
            + ", url="
            + this.getUrl()
            + ")";
      }

      @Generated
      public NewsArticle(final String title, final String description, final String picUrl, final String url) {
         this.title = title;
         this.description = description;
         this.picUrl = picUrl;
         this.url = url;
      }

      @Generated
      public NewsArticle() {
      }
   }
}
