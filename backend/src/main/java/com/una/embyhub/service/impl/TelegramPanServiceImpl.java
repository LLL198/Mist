package com.una.embyhub.service.impl;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.una.embyhub.config.common.utils.TelegramClientUtils;
import com.una.embyhub.model.dto.response.telegram.SearchResponse;
import com.una.embyhub.service.TelegramPanService;
import java.net.URI;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import lombok.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.AnswerCallbackQuery;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.TelegramClient;

@Service
public class TelegramPanServiceImpl implements TelegramPanService {
   @Autowired
   private TelegramClientUtils telegramClientUtils;
   private String defaultChatId;
   private static final int TG_TEXT_LIMIT = 4096;
   private static final boolean DISABLE_WEB_PREVIEW = true;
   private static final int CATEGORY_PAGE_SIZE = 8;
   private static final int ENTRY_PAGE_SIZE = 12;
   private Map<String, List<TelegramPanServiceImpl.LinkEntry>> categoryMap = new LinkedHashMap<>();
   private List<String> categoryOrder = new ArrayList<>();
   private final Map<String, String> tipByChat = new ConcurrentHashMap<>();

   @Override
   public void pushHome(String chatId, SearchResponse payload, String ignoredBotToken, String name, String tip) {
      try {
         JSONObject root = JSON.parseObject(JSONObject.toJSONString(payload));
         JSONArray results = root.getJSONObject("data").getJSONArray("results");
         this.buildCategories(results);
      } catch (Exception var10) {
         System.err.println("[TG] JSON 结构解析失败，请检查 data.results：" + var10.getMessage());
         return;
      }

      if (chatId != null) {
         this.tipByChat.put(chatId, tip == null ? "" : tip);
      }

      int page = 0;
      String storedTip = this.tipByChat.getOrDefault(chatId, "");
      String text = this.renderHomeText(page, storedTip);
      InlineKeyboardMarkup kb = this.buildCategoryKeyboard(page);
      this.sendOrChunk(chatId, text, kb, true);
   }

   public void onCallback(CallbackQuery cb) {
      if (cb != null) {
         String data = cb.getData();
         if (data != null) {
            String chatId = String.valueOf(cb.getMessage().getChatId());
            Integer messageId = cb.getMessage().getMessageId();

            try {
               TelegramClient client = this.telegramClientUtils.getTelegramClient();
               client.execute(AnswerCallbackQuery.builder().callbackQueryId(cb.getId()).build());
            } catch (TelegramApiException var12) {
            }

            if ("home".equalsIgnoreCase(data)) {
               data = "home:0";
            }

            if (!"noop".equalsIgnoreCase(data)) {
               try {
                  if (data.startsWith("home:")) {
                     int page = this.safeParseInt(data.substring("home:".length()), 0);
                     String tip = this.tipByChat.getOrDefault(chatId, "");
                     String text = this.renderHomeText(page, tip);
                     InlineKeyboardMarkup kb = this.buildCategoryKeyboard(page);
                     this.editOrChunk(chatId, messageId, text, kb, true);
                  } else if (data.startsWith("cat:")) {
                     String[] parts = data.split(":", 3);
                     String type = parts.length > 1 ? parts[1] : "";
                     int page = parts.length > 2 ? this.safeParseInt(parts[2], 0) : 0;
                     String text = this.renderCategoryText(type, page);
                     InlineKeyboardMarkup kb = this.buildEntryKeyboard(type, page);
                     this.editOrChunk(chatId, messageId, text, kb, true);
                  }
               } catch (Exception var11) {
                  try {
                     String tip = this.tipByChat.getOrDefault(chatId, "");
                     String text = this.renderHomeText(0, tip);
                     InlineKeyboardMarkup kb = this.buildCategoryKeyboard(0);
                     this.editOrChunk(chatId, messageId, text, kb, true);
                  } catch (Exception var10) {
                  }
               }
            }
         }
      }
   }

   private void buildCategories(JSONArray results) {
      if (results == null) {
         results = new JSONArray();
      }

      LinkedHashMap<String, TelegramPanServiceImpl.LinkEntry> uniq = new LinkedHashMap<>();

      for (int i = 0; i < results.size(); i++) {
         JSONObject item = results.getJSONObject(i);
         String title = this.coalesce(item.getString("title"), "(无标题)");
         JSONArray links = item.getJSONArray("links");
         if (links != null) {
            for (int j = 0; j < links.size(); j++) {
               JSONObject lk = links.getJSONObject(j);
               String type = this.coalesce(lk.getString("type"), "other").toLowerCase(Locale.ROOT);
               String url = this.coalesce(lk.getString("url"), "");
               String pwd = this.coalesce(lk.getString("password"), "");
               if (!url.isEmpty()) {
                  String keyUrl = this.normalizeUrl(url);
                  TelegramPanServiceImpl.LinkEntry in = new TelegramPanServiceImpl.LinkEntry(type, title, url, pwd);
                  uniq.merge(keyUrl, in, this::mergeByUrl);
               }
            }
         }
      }

      Map<String, List<TelegramPanServiceImpl.LinkEntry>> buckets = new LinkedHashMap<>();

      for (TelegramPanServiceImpl.LinkEntry e : uniq.values()) {
         buckets.computeIfAbsent(e.type, k -> new ArrayList<>()).add(e);
      }

      List<String> order = new ArrayList<>(buckets.keySet());
      order.sort((a, b) -> {
         int ra = this.typeRank(a);
         int rb = this.typeRank(b);
         return ra != rb ? Integer.compare(ra, rb) : a.compareToIgnoreCase(b);
      });
      Map<String, List<TelegramPanServiceImpl.LinkEntry>> out = new LinkedHashMap<>();

      for (String type : order) {
         out.put(type, buckets.get(type));
      }

      this.categoryMap = out;
      this.categoryOrder = order;
   }

   private TelegramPanServiceImpl.LinkEntry mergeByUrl(TelegramPanServiceImpl.LinkEntry a, TelegramPanServiceImpl.LinkEntry b) {
      int ra = this.typeRank(a.type);
      int rb = this.typeRank(b.type);
      TelegramPanServiceImpl.LinkEntry base = ra <= rb ? a : b;
      TelegramPanServiceImpl.LinkEntry other = base == a ? b : a;
      String pwd = !this.coalesce(base.password, "").isEmpty() ? base.password : other.password;
      String at = this.coalesce(base.title, "(无标题)");
      String bt = this.coalesce(other.title, "(无标题)");
      String title;
      if ("(无标题)".equals(at) && !"(无标题)".equals(bt)) {
         title = bt;
      } else if (!"(无标题)".equals(at) && "(无标题)".equals(bt)) {
         title = at;
      } else {
         title = bt.length() > at.length() ? bt : at;
      }

      return new TelegramPanServiceImpl.LinkEntry(base.type, title, base.url, pwd);
   }

   private String normalizeUrl(String url) {
      try {
         String raw = url.trim();
         URI u = new URI(raw);
         String scheme = this.coalesce(u.getScheme(), "").toLowerCase(Locale.ROOT);
         String host = this.coalesce(u.getHost(), "").toLowerCase(Locale.ROOT);
         String path = this.coalesce(u.getRawPath(), "");
         String query = u.getRawQuery() == null ? "" : "?" + u.getRawQuery();
         String frag = u.getRawFragment() == null ? "" : "#" + u.getRawFragment();
         String auth = host;
         if (u.getPort() != -1) {
            auth = host + ":" + u.getPort();
         }

         return !scheme.isEmpty() && !auth.isEmpty() ? scheme + "://" + auth + path + query + frag : raw;
      } catch (Exception var10) {
         return url.trim();
      }
   }

   private TelegramPanServiceImpl.LinkEntry mergePreferred(TelegramPanServiceImpl.LinkEntry a, TelegramPanServiceImpl.LinkEntry b) {
      String pwd = !a.password.isEmpty() ? a.password : b.password;
      String at = this.coalesce(a.title, "(无标题)");
      String bt = this.coalesce(b.title, "(无标题)");
      String title;
      if ("(无标题)".equals(at) && !"(无标题)".equals(bt)) {
         title = bt;
      } else if (!"(无标题)".equals(at) && "(无标题)".equals(bt)) {
         title = at;
      } else {
         title = bt.length() > at.length() ? bt : at;
      }

      return new TelegramPanServiceImpl.LinkEntry(a.type, title, a.url, pwd);
   }

   private int typeRank(String t) {
      String var2 = t.toLowerCase(Locale.ROOT);
      switch (var2) {
         case "quark":
            return 1;
         case "aliyun":
            return 2;
         case "tianyi":
            return 3;
         case "115":
            return 4;
         default:
            return 9;
      }
   }

   private String renderHomeText(int page, String tip) {
      int totalPages = this.totalCategoryPages();
      page = this.clamp(page, 0, totalPages - 1);
      StringBuilder sb = new StringBuilder();
      sb.append("\ud83d\udcc2 <b>资源分类</b>（这是为用户求片提供的资源列表）\n\n");
      if (tip != null && !tip.trim().isEmpty()) {
         sb.append(this.escapeHtml(tip.trim())).append("\n\n");
      }

      sb.append("\ud83d\udc49 点击下面的分类按钮查看条目列表。");
      return sb.toString();
   }

   private String renderCategoryText(String type, int page) {
      List<TelegramPanServiceImpl.LinkEntry> list = this.categoryMap.getOrDefault(type, Collections.emptyList());
      int total = list.size();
      int totalPages = Math.max(1, (total + 12 - 1) / 12);
      page = this.clamp(page, 0, totalPages - 1);
      StringBuilder sb = new StringBuilder();
      sb.append("\ud83d\udcc1 <b>")
         .append(this.escapeHtml(this.displayName(type)))
         .append("</b>")
         .append("（总计 ")
         .append(total)
         .append(" 条）\n")
         .append("第 ")
         .append(page + 1)
         .append(" / ")
         .append(totalPages)
         .append(" 页\n\n");
      if (total == 0) {
         sb.append("（该分类暂无链接）\n");
         return sb.toString();
      } else {
         int start = page * 12;
         int end = Math.min(start + 12, total);

         for (int i = start; i < end; i++) {
            TelegramPanServiceImpl.LinkEntry e = list.get(i);
            int idx = i + 1;
            sb.append(idx)
               .append(". ")
               .append("<a href=\"")
               .append(this.escapeHtml(e.url))
               .append("\">")
               .append(this.escapeHtml(this.trimTitle(e.title)))
               .append("</a>");
            if (!e.password.isEmpty()) {
               sb.append(" （密码：").append(this.escapeHtml(e.password)).append("）");
            }

            sb.append("\n");
         }

         return sb.toString();
      }
   }

   private String displayName(String type) {
      String var2 = type.toLowerCase(Locale.ROOT);
      switch (var2) {
         case "quark":
            return "夸克网盘";
         case "aliyun":
            return "阿里云盘";
         case "tianyi":
            return "天翼云盘";
         case "115":
            return "115网盘";
         default:
            return type.toUpperCase(Locale.ROOT);
      }
   }

   private int totalCategoryPages() {
      return this.categoryOrder.isEmpty() ? 1 : (this.categoryOrder.size() + 8 - 1) / 8;
   }

   private String trimTitle(String t) {
      return t == null ? "(无标题)" : (t.length() > 80 ? t.substring(0, 77) + "..." : t);
   }

   private InlineKeyboardMarkup buildCategoryKeyboard(int page) {
      int totalPages = this.totalCategoryPages();
      page = this.clamp(page, 0, totalPages - 1);
      List<InlineKeyboardRow> rows = new ArrayList<>();
      InlineKeyboardRow row = new InlineKeyboardRow();
      int start = page * 8;
      int end = Math.min(start + 8, this.categoryOrder.size());
      int col = 0;

      for (int i = start; i < end; i++) {
         String type = this.categoryOrder.get(i);
         List<TelegramPanServiceImpl.LinkEntry> list = this.categoryMap.get(type);
         if (list != null && !list.isEmpty()) {
            InlineKeyboardButton btn = InlineKeyboardButton.builder()
               .text(this.displayName(type) + " (" + list.size() + ")")
               .callbackData("cat:" + type + ":0")
               .build();
            row.add(btn);
            if (++col % 2 == 0) {
               rows.add(row);
               row = new InlineKeyboardRow();
            }
         }
      }

      if (!row.isEmpty()) {
         rows.add(row);
      }

      InlineKeyboardRow nav = new InlineKeyboardRow();
      if (page > 0) {
         nav.add(InlineKeyboardButton.builder().text("◀️ 上一页").callbackData("home:" + (page - 1)).build());
      }

      nav.add(InlineKeyboardButton.builder().text("第 " + (page + 1) + "/" + totalPages + " 页").callbackData("noop").build());
      if (page < totalPages - 1) {
         nav.add(InlineKeyboardButton.builder().text("下一页 ▶️").callbackData("home:" + (page + 1)).build());
      }

      rows.add(nav);
      return InlineKeyboardMarkup.builder().keyboard(rows).build();
   }

   private InlineKeyboardMarkup buildEntryKeyboard(String type, int page) {
      List<TelegramPanServiceImpl.LinkEntry> list = this.categoryMap.getOrDefault(type, Collections.emptyList());
      int total = list.size();
      int totalPages = Math.max(1, (total + 12 - 1) / 12);
      page = this.clamp(page, 0, totalPages - 1);
      InlineKeyboardRow nav = new InlineKeyboardRow();
      if (page > 0) {
         nav.add(InlineKeyboardButton.builder().text("◀️ 上一页").callbackData("cat:" + type + ":" + (page - 1)).build());
      }

      nav.add(InlineKeyboardButton.builder().text("\ud83d\udd19 返回分类").callbackData("home:0").build());
      if (page < totalPages - 1) {
         nav.add(InlineKeyboardButton.builder().text("下一页 ▶️").callbackData("cat:" + type + ":" + (page + 1)).build());
      }

      return InlineKeyboardMarkup.builder().keyboard(Collections.singletonList(nav)).build();
   }

   private void sendOrChunk(String chatId, String text, InlineKeyboardMarkup kb, boolean noPreview) {
      TelegramClient client = this.telegramClientUtils.getTelegramClient();
      List<String> parts = this.chunkText(text, 4096);

      for (int i = 0; i < parts.size(); i++) {
         SendMessage sm = SendMessage.builder()
            .chatId(chatId)
            .text(parts.get(i))
            .parseMode("html")
            .disableWebPagePreview(noPreview)
            .replyMarkup(i == 0 ? kb : null)
            .build();

         try {
            client.execute(sm);
         } catch (TelegramApiException var10) {
            System.err.println("[TG] sendMessage 失败：" + var10.getMessage());
         }
      }
   }

   private void editOrChunk(String chatId, Integer messageId, String text, InlineKeyboardMarkup kb, boolean noPreview) {
      List<String> parts = this.chunkText(text, 4096);
      TelegramClient client = this.telegramClientUtils.getTelegramClient();
      EditMessageText edit = EditMessageText.builder()
         .chatId(chatId)
         .messageId(messageId)
         .text(parts.get(0))
         .parseMode("html")
         .disableWebPagePreview(noPreview)
         .replyMarkup(kb)
         .build();

      try {
         client.execute(edit);
      } catch (TelegramApiException var13) {
         String msg = (var13.getMessage() == null ? "" : var13.getMessage()).toLowerCase(Locale.ROOT);
         if (!msg.contains("not modified")) {
            if (msg.contains("message to edit not found")) {
               this.sendOrChunk(chatId, text, kb, noPreview);
               return;
            }

            System.err.println("[TG] editMessageText 失败：" + var13.getMessage());
         }
      }

      for (int i = 1; i < parts.size(); i++) {
         SendMessage sm = SendMessage.builder().chatId(chatId).text(parts.get(i)).parseMode("html").disableWebPagePreview(noPreview).build();

         try {
            client.execute(sm);
         } catch (TelegramApiException var12) {
            System.err.println("[TG] sendMessage(chunk) 失败：" + var12.getMessage());
         }
      }
   }

   private List<String> chunkText(String text, int limit) {
      if (text == null) {
         return List.of("");
      } else if (text.length() <= limit) {
         return List.of(text);
      } else {
         List<String> chunks = new ArrayList<>();
         int start = 0;

         while (start < text.length()) {
            int end = Math.min(text.length(), start + limit);
            int lastBreak = text.lastIndexOf(10, end);
            if (lastBreak <= start) {
               lastBreak = end;
            }

            chunks.add(text.substring(start, lastBreak));
            start = lastBreak;
         }

         return chunks;
      }
   }

   private int clamp(int v, int min, int max) {
      return Math.max(min, Math.min(max, v));
   }

   private String escapeHtml(String s) {
      return s == null ? "" : s.replace("&", "&amp;").replace("<", "&lt;").replace(">", "&gt;").replace("\"", "&quot;");
   }

   private String coalesce(String s, String defVal) {
      return s != null && !s.trim().isEmpty() ? s.trim() : defVal;
   }

   private int safeParseInt(String s, int def) {
      try {
         return Integer.parseInt(s);
      } catch (Exception var4) {
         return def;
      }
   }

   private static class LinkEntry {
      final String type;
      final String title;
      final String url;
      final String password;

      LinkEntry(String type, String title, String url, String password) {
         this.type = type;
         this.title = title;
         this.url = url;
         this.password = password == null ? "" : password;
      }
   }
}
