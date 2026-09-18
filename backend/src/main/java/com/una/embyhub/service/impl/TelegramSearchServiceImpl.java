package com.una.embyhub.service.impl;

import cn.hutool.core.util.StrUtil;
import cn.hutool.core.util.URLUtil;
import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.alibaba.fastjson2.JSONWriter.Feature;
import com.una.embyhub.model.dto.request.telegram.ChannelInfoRequest;
import com.una.embyhub.model.dto.request.telegram.TelegramMessageRequest;
import com.una.embyhub.model.dto.response.telegram.TelegramMessageResponse;
import com.una.embyhub.service.TelegramSearchService;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import lombok.Generated;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class TelegramSearchServiceImpl implements TelegramSearchService {
   @Generated
   private static final Logger log = LoggerFactory.getLogger(TelegramSearchServiceImpl.class);
   private static final Pattern SHARE_LINK_PATTERN = Pattern.compile(
      "(https?://(?:www\\.)?(?:aliyundrive\\.com|alipan\\.com|115\\.com|pan\\.quark\\.cn|pan\\.baidu\\.com|drive\\.uc\\.cn|www\\.123pan\\.com|cloud\\.189\\.cn|pan\\.xunlei\\.com|mypikpak\\.com|hdhive\\.online)[\\w\\-./?=&%#]+)"
   );

   @Override
   public List<TelegramMessageResponse> searchMultipleChannelsAndMerge(TelegramMessageRequest telegramMessageRequest) {
      List<TelegramMessageResponse> allMessages = telegramMessageRequest.getChannels().parallelStream().flatMap(channelName -> {
         System.out.println("-> 开始搜索频道: " + channelName);
         return this.searchChannelMessages(channelName, telegramMessageRequest.getQuery()).stream();
      }).collect(Collectors.toList());
      allMessages.sort(Comparator.comparing(TelegramMessageResponse::getTime, Comparator.nullsLast(Comparator.reverseOrder())));
      return allMessages;
   }

   public List<TelegramMessageResponse> searchChannelMessages(String channelName, String query) {
      if (StrUtil.isAllBlank(new CharSequence[]{channelName, query})) {
         log.error("错误：频道名和搜索词不能为空。");
         return Collections.emptyList();
      } else {
         String encodedQuery = URLUtil.encode(query);
         String url = String.format("https://t.me/s/%s?q=%s", channelName, encodedQuery);
         return this.fetchAndParse(url, channelName);
      }
   }

   private List<TelegramMessageResponse> fetchAndParse(String url, String channelName) {
      try {
         String htmlContent = HttpUtil.createGet(url)
            .header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.124 Safari/537.36")
            .execute()
            .body();
         Document doc = Jsoup.parse(htmlContent);
         Elements messageWraps = doc.select("div.tgme_widget_message_wrap");
         if (messageWraps.isEmpty()) {
            return Collections.emptyList();
         } else {
            List<TelegramMessageResponse> messages = new ArrayList<>();

            for (Element wrap : messageWraps) {
               Element messageView = wrap.selectFirst("div.tgme_widget_message");
               if (messageView != null) {
                  TelegramMessageResponse message = new TelegramMessageResponse();
                  message.setChannelName(channelName);
                  Element messageTextElement = messageView.selectFirst("div.tgme_widget_message_text");
                  String title = "";
                  String textContent = "";
                  if (messageTextElement != null) {
                     title = this.extractTitle(messageTextElement);
                     String fullText = messageTextElement.text();
                     if (StrUtil.isNotBlank(title) && fullText.startsWith(title)) {
                        textContent = fullText.substring(title.length()).trim();
                     } else {
                        textContent = fullText;
                     }
                  }

                  message.setTitle(title);
                  message.setTextContent(textContent);
                  message.setImageUrl(this.extractImageUrl(messageView));
                  message.setMessageLink(this.extractMessageLink(messageView));
                  message.setTime(this.extractTime(messageView));
                  message.setMessageId(this.extractMessageId(messageView));
                  message.setShareLinks(this.extractShareLinks(messageView));
                  messages.add(message);
               }
            }

            return messages;
         }
      } catch (Exception var15) {
         System.err.println("在频道 [" + channelName + "] 获取或解析时发生异常: " + var15.getMessage());
         return Collections.emptyList();
      }
   }

   private String extractTitle(Element messageTextElement) {
      if (messageTextElement == null) {
         return "";
      } else {
         try {
            String messageHtml = messageTextElement.html();
            String[] parts = messageHtml.split("(?i)<br\\s*/?>");
            return parts.length > 0 ? Jsoup.parse(parts[0]).text().trim() : "";
         } catch (Exception var4) {
            return "";
         }
      }
   }

   private List<String> extractShareLinks(Element messageElement) {
      if (messageElement == null) {
         return Collections.emptyList();
      } else {
         String fullHtml = messageElement.html();
         List<String> foundLinks = new ArrayList<>();
         Matcher matcher = SHARE_LINK_PATTERN.matcher(fullHtml);

         while (matcher.find()) {
            foundLinks.add(matcher.group());
         }

         return foundLinks;
      }
   }

   private String extractText(Element messageView) {
      return messageView.selectFirst("div.tgme_widget_message_text") != null ? messageView.selectFirst("div.tgme_widget_message_text").text() : "";
   }

   private String extractImageUrl(Element messageView) {
      Element photoWrap = messageView.selectFirst("a.tgme_widget_message_photo_wrap");
      if (photoWrap != null) {
         String style = photoWrap.attr("style");
         if (style != null && style.contains("background-image:url('")) {
            int start = style.indexOf("('") + 2;
            int end = style.indexOf("')");
            if (start < end) {
               return style.substring(start, end);
            }
         }
      }

      return null;
   }

   private String extractMessageLink(Element messageView) {
      Element dateLink = messageView.selectFirst("a.tgme_widget_message_date");
      return dateLink != null ? dateLink.attr("href") : null;
   }

   private String extractTime(Element messageView) {
      Element timeElement = messageView.selectFirst("time.time");
      return timeElement != null ? timeElement.attr("datetime") : null;
   }

   private Integer extractMessageId(Element messageView) {
      String link = messageView.selectFirst("a.tgme_widget_message_date").attr("href");
      if (StrUtil.isNotBlank(link) && link.contains("/")) {
         try {
            return Integer.parseInt(StrUtil.subAfter(link, "/", true));
         } catch (NumberFormatException var4) {
            return null;
         }
      } else {
         return null;
      }
   }

   public static void main(String[] args) {
      String channelData = "[{\"id\":\"Quark_Movies\",\"name\":\"夸克云盘综合资源\"}]";
      String SEARCH_QUERY = "神奇四侠";
      List<ChannelInfoRequest> allChannelInfos = JSON.parseArray("[{\"id\":\"Quark_Movies\",\"name\":\"夸克云盘综合资源\"}]", ChannelInfoRequest.class);
      List<String> targetChannels = allChannelInfos.stream()
         .filter(channel -> !channel.getId().startsWith("+"))
         .map(ChannelInfoRequest::getId)
         .collect(Collectors.toList());
      allChannelInfos.stream()
         .filter(channel -> channel.getId().startsWith("+"))
         .forEach(channel -> System.out.println("提示: 已跳过私有频道/群组 -> " + channel.getName()));
      System.out.printf("\n开始任务: 在 %d 个公开频道中搜索关键词 [%s]\n", targetChannels.size(), "神奇四侠");
      System.out.println("====================================================\n");
      TelegramSearchServiceImpl fetcher = new TelegramSearchServiceImpl();
      TelegramMessageRequest telegramMessageRequest = new TelegramMessageRequest();
      telegramMessageRequest.setChannels(targetChannels);
      telegramMessageRequest.setQuery("神奇四侠");
      List<TelegramMessageResponse> searchResults = fetcher.searchMultipleChannelsAndMerge(telegramMessageRequest);
      if (searchResults.isEmpty()) {
         System.out.println("搜索完成，在所有指定频道中都没有找到任何包含关键词的消息。");
      } else {
         System.out.println("--- 搜索结果 (共 " + searchResults.size() + " 条) ---");
         String prettyJsonString = JSONObject.toJSONString(searchResults, Feature.PrettyFormat);
         System.out.println(prettyJsonString);
      }

      System.out.println("\n--- 任务结束 ---");
   }
}
