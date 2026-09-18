package com.una.embyhub.controller;

import cn.hutool.core.util.StrUtil;
import cn.hutool.core.util.URLUtil;
import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.alibaba.fastjson2.JSONWriter.Feature;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class TelegramPublicChannelFetcher {
   private static final Pattern SHARE_LINK_PATTERN = Pattern.compile(
      "(https?://(?:www\\.)?(?:aliyundrive\\.com|alipan\\.com|115\\.com|pan\\.quark\\.cn|pan\\.baidu\\.com|drive\\.uc\\.cn|www\\.123pan\\.com|cloud\\.189\\.cn|pan\\.xunlei\\.com|mypikpak\\.com|hdhive\\.online)[\\w\\-./?=&%#]+)"
   );

   public List<TelegramPublicChannelFetcher.TelegramMessage> searchMultipleChannelsAndMerge(List<String> channelNames, String query) {
      List<TelegramPublicChannelFetcher.TelegramMessage> allMessages = channelNames.parallelStream().flatMap(channelName -> {
         System.out.println("-> 开始搜索频道: " + channelName);
         return this.searchChannelMessages(channelName, query).stream();
      }).collect(Collectors.toList());
      allMessages.sort(Comparator.comparing(TelegramPublicChannelFetcher.TelegramMessage::getTime, Comparator.nullsLast(Comparator.reverseOrder())));
      return allMessages;
   }

   public List<TelegramPublicChannelFetcher.TelegramMessage> searchChannelMessages(String channelName, String query) {
      if (StrUtil.isAllBlank(new CharSequence[]{channelName, query})) {
         System.err.println("错误：频道名和搜索词不能为空。");
         return Collections.emptyList();
      } else {
         String encodedQuery = URLUtil.encode(query);
         String url = String.format("https://t.me/s/%s?q=%s", channelName, encodedQuery);
         return this.fetchAndParse(url, channelName);
      }
   }

   private List<TelegramPublicChannelFetcher.TelegramMessage> fetchAndParse(String url, String channelName) {
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
            List<TelegramPublicChannelFetcher.TelegramMessage> messages = new ArrayList<>();

            for (Element wrap : messageWraps) {
               Element messageView = wrap.selectFirst("div.tgme_widget_message");
               if (messageView != null) {
                  TelegramPublicChannelFetcher.TelegramMessage message = new TelegramPublicChannelFetcher.TelegramMessage();
                  message.setChannelName(channelName);
                  message.setTextContent(this.extractText(messageView));
                  message.setImageUrl(this.extractImageUrl(messageView));
                  message.setMessageLink(this.extractMessageLink(messageView));
                  message.setTime(this.extractTime(messageView));
                  message.setMessageId(this.extractMessageId(messageView));
                  message.setShareLinks(this.extractShareLinks(message.getTextContent()));
                  messages.add(message);
               }
            }

            return messages;
         }
      } catch (Exception var11) {
         System.err.println("在频道 [" + channelName + "] 获取或解析时发生异常: " + var11.getMessage());
         return Collections.emptyList();
      }
   }

   private List<String> extractShareLinks(String textContent) {
      if (StrUtil.isBlank(textContent)) {
         return Collections.emptyList();
      } else {
         List<String> foundLinks = new ArrayList<>();
         Matcher matcher = SHARE_LINK_PATTERN.matcher(textContent);

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
      String channelData = "[{\"id\":\"Lsp115\",\"name\":\"115网盘资源分享频道\"},{\"id\":\"alyp_1\",\"name\":\"网盘(高品质)影视\"},{\"id\":\"shareAliyun\",\"name\":\"阿里云盘发布频道\"},{\"id\":\"Quark_Movies\",\"name\":\"夸克云盘综合资源\"},{\"id\":\"Aliyun_4K_Movies\",\"name\":\"阿里云盘4K影视\"},{\"id\":\"zaihuayun\",\"name\":\"阿里云盘资源\"},{\"id\":\"PanjClub\",\"name\":\"盘酱酱Club\"},{\"id\":\"tianyirigeng\",\"name\":\"天翼云盘资源频道\"},{\"id\":\"xx123pan\",\"name\":\"123云盘资源频道\"},{\"id\":\"zyzhpd123\",\"name\":\"123云盘综合频道\"},{\"id\":\"cloudtianyi\",\"name\":\"天翼云盘资源发布频道\"},{\"id\":\"tyypzhpd\",\"name\":\"天翼云盘综合频道\"},{\"id\":\"Oscar_4Kmovies\",\"name\":\"奥斯卡4K蓝光（精品）影视磁力站\"},{\"id\":\"ydypzyfx\",\"name\":\"移动云盘资源分享\"},{\"id\":\"bdwpzhpd\",\"name\":\"百度网盘综合频道\"},{\"id\":\"Q66share\",\"name\":\"阿里云盘吧\"},{\"id\":\"BaiduCloudDisk\",\"name\":\"百度网盘资源分享\"},{\"id\":\"yunpan139\",\"name\":\"网盘资源收藏（移动云盘）\"},{\"id\":\"yunpanuc\",\"name\":\"网盘资源收藏（UC网盘）\"},{\"id\":\"qixingzhenren\",\"name\":\"云盘资源发布频道\"},{\"id\":\"duanjucabian\",\"name\":\"热门短剧/擦边短剧/精选短剧/在线预览\"},{\"id\":\"yoyokuakeduanju\",\"name\":\"YOYO资源|夸克|短剧\"},{\"id\":\"Channel_Shares_115\",\"name\":\"Shares_115_Channel\"},{\"id\":\"yeqingjie_GJG666\",\"name\":\"爷青回动画分享\"},{\"id\":\"gotopan\",\"name\":\"迅雷云盘\"},{\"id\":\"oneonefivewpfx\",\"name\":\"影巢\"},{\"id\":\"zhenyingsg\",\"name\":\"帧影时光\"},{\"id\":\"movielover8888_TV\",\"name\":\"【热门网剧在线】\"},{\"id\":\"CBduanju\",\"name\":\"全网擦边｜电影｜资源分享\"},{\"id\":\"ucquark\",\"name\":\"UC夸克百度迅雷资源分享\"},{\"id\":\"weichengduanju666\",\"name\":\"短剧大全资源\"},{\"id\":\"yingxiangkj\",\"name\":\"影享空间\"},{\"id\":\"QuarkFree\",\"name\":\"夸克网盘资源收藏夹\"},{\"id\":\"TG654TG\",\"name\":\"综艺网盘资源频道\"},{\"id\":\"QukanMovie\",\"name\":\"115影视资源分享频道\"},{\"id\":\"WFYSFX02\",\"name\":\"夸克丶百度丶迅雷丶4K网盘\"},{\"id\":\"naclyunpan\",\"name\":\"网盘资源收藏\"},{\"id\":\"guaguale115\",\"name\":\"115影视资源分享频道\"},{\"id\":\"hao115\",\"name\":\"115网盘资源分享频道\"},{\"id\":\"sharing115withfriends\",\"name\":\"115网盘资源：人人发资源\"},{\"id\":\"kycrwp\",\"name\":\"115成人网盘\"},{\"id\":\"yunpanshare\",\"name\":\"网盘资源收藏（夸克）\"},{\"id\":\"XiangxiuNB\",\"name\":\"肯德基の4K影视综合电影云盘站\"},{\"id\":\"Aliyun_4K_ Movies\",\"name\":\"阿里云盘4K影视\"},{\"id\":\"tgsearchers\",\"name\":\"资源宇宙\"},{\"id\":\"pan115_share\",\"name\":\"爱影115资源分享频道\"},{\"id\":\"baidudianshi\",\"name\":\"可乐小屋\"},{\"id\":\"yunpanall\",\"name\":\"综合频道\"},{\"id\":\"NewQuark\",\"name\":\"夸克浏览器二三事\"},{\"id\":\"NewAliPan\",\"name\":\"阿里云盘吧（新）\"},{\"id\":\"pan123pan\",\"name\":\"网盘资源收藏（123云盘）\"},{\"id\":\"yunpanpan\",\"name\":\"云盘盘\"},{\"id\":\"pankuake_share\",\"name\":\"爱影夸克频道\"},{\"id\":\"yppan\",\"name\":\"（新）云盘盘 @yppan\"},{\"id\":\"tianyiDrive\",\"name\":\"天翼云盘资源交流群\"},{\"id\":\"tianyifc\",\"name\":\"天翼云盘 刮削资源分享\"},{\"id\":\"tianyiyunpanpindao\",\"name\":\"天翼云盘\"},{\"id\":\"PikPak_Share_Channel\",\"name\":\"PikPak磁链资源分享\"},{\"id\":\"PikPakShareChannel\",\"name\":\"PikPak云盘资源分享\"},{\"id\":\"bdbdndn11\",\"name\":\"电影网盘分享频道\"},{\"id\":\"quarkgood\",\"name\":\"夸克网盘资源分享\"},{\"id\":\"alyp_4K_Movies\",\"name\":\"网盘资源(电影)频道\"},{\"id\":\"alyp_JLP\",\"name\":\"网盘资源(纪录片)频道\"},{\"id\":\"chxyy2019\",\"name\":\"百度云 网盘 资源共享\"},{\"id\":\"vip115hot\",\"name\":\"懒狗集中营\"},{\"id\":\"gdsharing\",\"name\":\"Google Drive 资源\"},{\"id\":\"tgyy678\",\"name\":\"电影搜索|电影频道|电影资源\"},{\"id\":\"yunpansall\",\"name\":\"网盘资源收藏(综合)\"},{\"id\":\"yunpanqk\",\"name\":\"网盘资源收藏(夸克)\"},{\"id\":\"yunpan189\",\"name\":\"网盘资源收藏(天翼云盘)\"},{\"id\":\"yp123pan\",\"name\":\"网盘资源收藏(123云盘)\"},{\"id\":\"yunpanxunlei\",\"name\":\"网盘资源收藏(迅雷云盘)\"},{\"id\":\"jdjdn1111\",\"name\":\"短剧网盘分享\"},{\"id\":\"aliyunys\",\"name\":\"阿里云盘影视大全-日更\"},{\"id\":\"KFCYeah\",\"name\":\"疯狂の星期四（综合）\"},{\"id\":\"+fSHARlBjBSNhN2Ix\",\"name\":\"（新）云盘盘 @yppan（综合）\"},{\"id\":\"+cpJ_dIx_hlYxMWQx\",\"name\":\"（新）夸克云盘（夸克）\"},{\"id\":\"+XssuNX4aT5Y0MjQx\",\"name\":\"（新）阿里云盘（阿里）\"},{\"id\":\"+3jLy5rtdHcwwNjM1\",\"name\":\"爱影115热剧追更频道（115）\"},{\"id\":\"tyysypzypd\",\"name\":\"天翼臻影资源收藏（天翼）\"},{\"id\":\"ysxb48\",\"name\":\"115网盘资源发布（115）\"},{\"id\":\"yunpantv\",\"name\":\"网盘观影基地（综合）\"},{\"id\":\"XiangxiuNBB\",\"name\":\"肯德基の4K影视综合电影云盘站（综合）\"},{\"id\":\"alyp_Animation\",\"name\":\"网盘资源(动画/动漫)频道（夸克）\"},{\"id\":\"xxziliao\",\"name\":\"学习频道\"},{\"id\":\"sndkdkdl\",\"name\":\"学习频道2\"},{\"id\":\"hsndn1\",\"name\":\"学习频道3\"},{\"id\":\"xuexixiaonengshou1\",\"name\":\"学习频道4\"}]";
      String SEARCH_QUERY = "神奇四侠";
      List<TelegramPublicChannelFetcher.ChannelInfo> allChannelInfos = JSON.parseArray(
         "[{\"id\":\"Lsp115\",\"name\":\"115网盘资源分享频道\"},{\"id\":\"alyp_1\",\"name\":\"网盘(高品质)影视\"},{\"id\":\"shareAliyun\",\"name\":\"阿里云盘发布频道\"},{\"id\":\"Quark_Movies\",\"name\":\"夸克云盘综合资源\"},{\"id\":\"Aliyun_4K_Movies\",\"name\":\"阿里云盘4K影视\"},{\"id\":\"zaihuayun\",\"name\":\"阿里云盘资源\"},{\"id\":\"PanjClub\",\"name\":\"盘酱酱Club\"},{\"id\":\"tianyirigeng\",\"name\":\"天翼云盘资源频道\"},{\"id\":\"xx123pan\",\"name\":\"123云盘资源频道\"},{\"id\":\"zyzhpd123\",\"name\":\"123云盘综合频道\"},{\"id\":\"cloudtianyi\",\"name\":\"天翼云盘资源发布频道\"},{\"id\":\"tyypzhpd\",\"name\":\"天翼云盘综合频道\"},{\"id\":\"Oscar_4Kmovies\",\"name\":\"奥斯卡4K蓝光（精品）影视磁力站\"},{\"id\":\"ydypzyfx\",\"name\":\"移动云盘资源分享\"},{\"id\":\"bdwpzhpd\",\"name\":\"百度网盘综合频道\"},{\"id\":\"Q66share\",\"name\":\"阿里云盘吧\"},{\"id\":\"BaiduCloudDisk\",\"name\":\"百度网盘资源分享\"},{\"id\":\"yunpan139\",\"name\":\"网盘资源收藏（移动云盘）\"},{\"id\":\"yunpanuc\",\"name\":\"网盘资源收藏（UC网盘）\"},{\"id\":\"qixingzhenren\",\"name\":\"云盘资源发布频道\"},{\"id\":\"duanjucabian\",\"name\":\"热门短剧/擦边短剧/精选短剧/在线预览\"},{\"id\":\"yoyokuakeduanju\",\"name\":\"YOYO资源|夸克|短剧\"},{\"id\":\"Channel_Shares_115\",\"name\":\"Shares_115_Channel\"},{\"id\":\"yeqingjie_GJG666\",\"name\":\"爷青回动画分享\"},{\"id\":\"gotopan\",\"name\":\"迅雷云盘\"},{\"id\":\"oneonefivewpfx\",\"name\":\"影巢\"},{\"id\":\"zhenyingsg\",\"name\":\"帧影时光\"},{\"id\":\"movielover8888_TV\",\"name\":\"【热门网剧在线】\"},{\"id\":\"CBduanju\",\"name\":\"全网擦边｜电影｜资源分享\"},{\"id\":\"ucquark\",\"name\":\"UC夸克百度迅雷资源分享\"},{\"id\":\"weichengduanju666\",\"name\":\"短剧大全资源\"},{\"id\":\"yingxiangkj\",\"name\":\"影享空间\"},{\"id\":\"QuarkFree\",\"name\":\"夸克网盘资源收藏夹\"},{\"id\":\"TG654TG\",\"name\":\"综艺网盘资源频道\"},{\"id\":\"QukanMovie\",\"name\":\"115影视资源分享频道\"},{\"id\":\"WFYSFX02\",\"name\":\"夸克丶百度丶迅雷丶4K网盘\"},{\"id\":\"naclyunpan\",\"name\":\"网盘资源收藏\"},{\"id\":\"guaguale115\",\"name\":\"115影视资源分享频道\"},{\"id\":\"hao115\",\"name\":\"115网盘资源分享频道\"},{\"id\":\"sharing115withfriends\",\"name\":\"115网盘资源：人人发资源\"},{\"id\":\"kycrwp\",\"name\":\"115成人网盘\"},{\"id\":\"yunpanshare\",\"name\":\"网盘资源收藏（夸克）\"},{\"id\":\"XiangxiuNB\",\"name\":\"肯德基の4K影视综合电影云盘站\"},{\"id\":\"Aliyun_4K_ Movies\",\"name\":\"阿里云盘4K影视\"},{\"id\":\"tgsearchers\",\"name\":\"资源宇宙\"},{\"id\":\"pan115_share\",\"name\":\"爱影115资源分享频道\"},{\"id\":\"baidudianshi\",\"name\":\"可乐小屋\"},{\"id\":\"yunpanall\",\"name\":\"综合频道\"},{\"id\":\"NewQuark\",\"name\":\"夸克浏览器二三事\"},{\"id\":\"NewAliPan\",\"name\":\"阿里云盘吧（新）\"},{\"id\":\"pan123pan\",\"name\":\"网盘资源收藏（123云盘）\"},{\"id\":\"yunpanpan\",\"name\":\"云盘盘\"},{\"id\":\"pankuake_share\",\"name\":\"爱影夸克频道\"},{\"id\":\"yppan\",\"name\":\"（新）云盘盘 @yppan\"},{\"id\":\"tianyiDrive\",\"name\":\"天翼云盘资源交流群\"},{\"id\":\"tianyifc\",\"name\":\"天翼云盘 刮削资源分享\"},{\"id\":\"tianyiyunpanpindao\",\"name\":\"天翼云盘\"},{\"id\":\"PikPak_Share_Channel\",\"name\":\"PikPak磁链资源分享\"},{\"id\":\"PikPakShareChannel\",\"name\":\"PikPak云盘资源分享\"},{\"id\":\"bdbdndn11\",\"name\":\"电影网盘分享频道\"},{\"id\":\"quarkgood\",\"name\":\"夸克网盘资源分享\"},{\"id\":\"alyp_4K_Movies\",\"name\":\"网盘资源(电影)频道\"},{\"id\":\"alyp_JLP\",\"name\":\"网盘资源(纪录片)频道\"},{\"id\":\"chxyy2019\",\"name\":\"百度云 网盘 资源共享\"},{\"id\":\"vip115hot\",\"name\":\"懒狗集中营\"},{\"id\":\"gdsharing\",\"name\":\"Google Drive 资源\"},{\"id\":\"tgyy678\",\"name\":\"电影搜索|电影频道|电影资源\"},{\"id\":\"yunpansall\",\"name\":\"网盘资源收藏(综合)\"},{\"id\":\"yunpanqk\",\"name\":\"网盘资源收藏(夸克)\"},{\"id\":\"yunpan189\",\"name\":\"网盘资源收藏(天翼云盘)\"},{\"id\":\"yp123pan\",\"name\":\"网盘资源收藏(123云盘)\"},{\"id\":\"yunpanxunlei\",\"name\":\"网盘资源收藏(迅雷云盘)\"},{\"id\":\"jdjdn1111\",\"name\":\"短剧网盘分享\"},{\"id\":\"aliyunys\",\"name\":\"阿里云盘影视大全-日更\"},{\"id\":\"KFCYeah\",\"name\":\"疯狂の星期四（综合）\"},{\"id\":\"+fSHARlBjBSNhN2Ix\",\"name\":\"（新）云盘盘 @yppan（综合）\"},{\"id\":\"+cpJ_dIx_hlYxMWQx\",\"name\":\"（新）夸克云盘（夸克）\"},{\"id\":\"+XssuNX4aT5Y0MjQx\",\"name\":\"（新）阿里云盘（阿里）\"},{\"id\":\"+3jLy5rtdHcwwNjM1\",\"name\":\"爱影115热剧追更频道（115）\"},{\"id\":\"tyysypzypd\",\"name\":\"天翼臻影资源收藏（天翼）\"},{\"id\":\"ysxb48\",\"name\":\"115网盘资源发布（115）\"},{\"id\":\"yunpantv\",\"name\":\"网盘观影基地（综合）\"},{\"id\":\"XiangxiuNBB\",\"name\":\"肯德基の4K影视综合电影云盘站（综合）\"},{\"id\":\"alyp_Animation\",\"name\":\"网盘资源(动画/动漫)频道（夸克）\"},{\"id\":\"xxziliao\",\"name\":\"学习频道\"},{\"id\":\"sndkdkdl\",\"name\":\"学习频道2\"},{\"id\":\"hsndn1\",\"name\":\"学习频道3\"},{\"id\":\"xuexixiaonengshou1\",\"name\":\"学习频道4\"}]",
         TelegramPublicChannelFetcher.ChannelInfo.class
      );
      List<String> targetChannels = allChannelInfos.stream()
         .filter(channel -> !channel.getId().startsWith("+"))
         .map(TelegramPublicChannelFetcher.ChannelInfo::getId)
         .collect(Collectors.toList());
      allChannelInfos.stream()
         .filter(channel -> channel.getId().startsWith("+"))
         .forEach(channel -> System.out.println("提示: 已跳过私有频道/群组 -> " + channel.getName()));
      System.out.printf("\n开始任务: 在 %d 个公开频道中搜索关键词 [%s]\n", targetChannels.size(), "神奇四侠");
      System.out.println("====================================================\n");
      TelegramPublicChannelFetcher fetcher = new TelegramPublicChannelFetcher();
      List<TelegramPublicChannelFetcher.TelegramMessage> searchResults = fetcher.searchMultipleChannelsAndMerge(targetChannels, "神奇四侠");
      if (searchResults.isEmpty()) {
         System.out.println("搜索完成，在所有指定频道中都没有找到任何包含关键词的消息。");
      } else {
         System.out.println("--- 搜索结果 (共 " + searchResults.size() + " 条) ---");
         String prettyJsonString = JSONObject.toJSONString(searchResults, Feature.PrettyFormat);
         System.out.println(prettyJsonString);
      }

      System.out.println("\n--- 任务结束 ---");
   }

   public static class ChannelInfo {
      private String id;
      private String name;

      public String getId() {
         return this.id;
      }

      public void setId(String id) {
         this.id = id;
      }

      public String getName() {
         return this.name;
      }

      public void setName(String name) {
         this.name = name;
      }
   }

   public static class TelegramMessage {
      private String textContent;
      private String imageUrl;
      private String messageLink;
      private String time;
      private Integer messageId;
      private String channelName;
      private List<String> shareLinks;

      public String getTextContent() {
         return this.textContent;
      }

      public void setTextContent(String textContent) {
         this.textContent = textContent;
      }

      public String getImageUrl() {
         return this.imageUrl;
      }

      public void setImageUrl(String imageUrl) {
         this.imageUrl = imageUrl;
      }

      public String getMessageLink() {
         return this.messageLink;
      }

      public void setMessageLink(String messageLink) {
         this.messageLink = messageLink;
      }

      public String getTime() {
         return this.time;
      }

      public void setTime(String time) {
         this.time = time;
      }

      public Integer getMessageId() {
         return this.messageId;
      }

      public void setMessageId(Integer messageId) {
         this.messageId = messageId;
      }

      public String getChannelName() {
         return this.channelName;
      }

      public void setChannelName(String channelName) {
         this.channelName = channelName;
      }

      public List<String> getShareLinks() {
         return this.shareLinks;
      }

      public void setShareLinks(List<String> shareLinks) {
         this.shareLinks = shareLinks;
      }
   }
}
