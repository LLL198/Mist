package com.una.embyhub.service.impl;

import cn.hutool.core.io.resource.ResourceUtil;
import com.alibaba.fastjson2.JSONObject;
import com.una.embyhub.model.dto.request.telegram.SendMessageRequest;
import com.una.embyhub.model.dto.request.telegram.SendPhotoRequest;
import com.una.embyhub.service.TelegramService;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import lombok.Generated;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage.SendMessageBuilder;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto.SendPhotoBuilder;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Service
public class TelegramServiceImpl implements TelegramService {
   @Generated
   private static final Logger log = LoggerFactory.getLogger(TelegramServiceImpl.class);
   private static final String SERVER_NAME_CALLBACK_DATA = "server_name_noop";
   private static final String DEFAULT_PHOTO_RESOURCE = "img/default.jpg";

   @Override
   public boolean sendPhoto(SendPhotoRequest sendPhotoRequest) throws TelegramApiException {
      if (sendPhotoRequest.getTelegramClient() == null) {
         log.info("Telegram Bot未启用，不发送图片消息");
         return false;
      } else {
         log.info("Telegram发送图片消息:{}", JSONObject.toJSONString(sendPhotoRequest));
         String caption = sendPhotoRequest.getCaption();
         if (!StringUtils.hasText(caption)) {
            StringBuilder sb = new StringBuilder();
            if (!"Movie".equals(sendPhotoRequest.getType()) && !"movie".equals(sendPhotoRequest.getType())) {
               sb.append("名称：" + sendPhotoRequest.getName());
               sb.append("\n\n");
               if (StringUtils.hasText(sendPhotoRequest.getTvInfo())) {
                  sb.append(sendPhotoRequest.getTvInfo());
                  sb.append("\n\n");
               }
            } else {
               sb.append("名称：" + sendPhotoRequest.getName());
               sb.append("\n\n");
            }

            if (StringUtils.hasText(sendPhotoRequest.getDisplayTitle())) {
               sb.append("\ud83d\udcfa 分辨率：" + sendPhotoRequest.getDisplayTitle());
               sb.append("\n");
            }

            if (StringUtils.hasText(sendPhotoRequest.getGenres())) {
               sb.append("\ud83c\udff7 标签：" + sendPhotoRequest.getGenres());
               sb.append("\n");
            }

            sb.append("\ud83d\uddc2 类型：" + (!"Movie".equals(sendPhotoRequest.getType()) && !"movie".equals(sendPhotoRequest.getType()) ? "#剧集" : "#电影"));
            sb.append("\n");
            if (sendPhotoRequest.getSize() != null && !"0".equals(sendPhotoRequest.getSize())) {
               sb.append("\ud83d\udce6 文件大小：" + sendPhotoRequest.getSize());
               sb.append("\n");
            }

            sb.append("\n");
            sb.append("简介：" + sendPhotoRequest.getOverview());
            caption = sb.toString();
         }

         SendPhotoBuilder<?, ?> sendPhotoBuilder = SendPhoto.builder();
         List<InlineKeyboardRow> inlineKeyboardRowList = new ArrayList<>();
         if (StringUtils.hasText(sendPhotoRequest.getTmdbUrl())) {
            InlineKeyboardRow tmdbRow = new InlineKeyboardRow();
            InlineKeyboardButton tmdbButton = new InlineKeyboardButton("");
            tmdbButton.setText("TMDB链接");
            tmdbButton.setUrl(sendPhotoRequest.getTmdbUrl());
            tmdbRow.add(tmdbButton);
            inlineKeyboardRowList.add(tmdbRow);
         }

         this.addServerNameButton(inlineKeyboardRowList, sendPhotoRequest.getServerName(), sendPhotoRequest.getServerUrl());
         if (!inlineKeyboardRowList.isEmpty()) {
            InlineKeyboardMarkup keyboardMarkup = new InlineKeyboardMarkup(inlineKeyboardRowList);
            sendPhotoBuilder.replyMarkup(keyboardMarkup);
         }

         return this.executePhotoWithFallback(sendPhotoRequest, sendPhotoBuilder, caption);
      }
   }

   @Override
   public boolean sendMessage(SendMessageRequest sendMessageRequest) throws TelegramApiException {
      log.info("Telegram发送文本消息:{}", JSONObject.toJSONString(sendMessageRequest));
      SendMessageBuilder<?, ?> builder = SendMessage.builder()
         .chatId(sendMessageRequest.getChatId())
         .text(sendMessageRequest.getOverview())
         .parseMode(sendMessageRequest.getParseMode());
      List<InlineKeyboardRow> inlineKeyboardRows = new ArrayList<>();
      if (StringUtils.hasText(sendMessageRequest.getTmdbUrl())) {
         InlineKeyboardRow tmdbRow = new InlineKeyboardRow();
         InlineKeyboardButton tmdbButton = new InlineKeyboardButton("");
         tmdbButton.setText("TMDB链接");
         tmdbButton.setUrl(sendMessageRequest.getTmdbUrl());
         tmdbRow.add(tmdbButton);
         inlineKeyboardRows.add(tmdbRow);
      }

      this.addServerNameButton(inlineKeyboardRows, sendMessageRequest.getServerName(), sendMessageRequest.getServerUrl());
      if (!inlineKeyboardRows.isEmpty()) {
         InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup(inlineKeyboardRows);
         builder.replyMarkup(inlineKeyboardMarkup);
      }

      SendMessage sendMessage = builder.build();
      sendMessageRequest.getTelegramClient().execute(sendMessage);
      return true;
   }

   @Override
   public boolean sendPhotoMessage(SendPhotoRequest sendPhotoRequest) throws TelegramApiException {
      if (sendPhotoRequest.getTelegramClient() == null) {
         log.info("Telegram Bot未启用，不发送图片文字消息");
         return false;
      } else {
         log.info("Telegram发送图片文字消息:{}", JSONObject.toJSONString(sendPhotoRequest));
         SendPhotoBuilder<?, ?> sendPhotoBuilder = SendPhoto.builder();
         List<InlineKeyboardRow> inlineKeyboardRowList = new ArrayList<>();
         if (StringUtils.hasText(sendPhotoRequest.getTmdbUrl())) {
            InlineKeyboardRow tmdbRow = new InlineKeyboardRow();
            InlineKeyboardButton tmdbButton = new InlineKeyboardButton("");
            tmdbButton.setText("TMDB链接");
            tmdbButton.setUrl(sendPhotoRequest.getTmdbUrl());
            tmdbRow.add(tmdbButton);
            inlineKeyboardRowList.add(tmdbRow);
         }

         this.addServerNameButton(inlineKeyboardRowList, sendPhotoRequest.getServerName(), sendPhotoRequest.getServerUrl());
         if (!inlineKeyboardRowList.isEmpty()) {
            InlineKeyboardMarkup keyboardMarkup = new InlineKeyboardMarkup(inlineKeyboardRowList);
            sendPhotoBuilder.replyMarkup(keyboardMarkup);
         }

         String caption = StringUtils.hasText(sendPhotoRequest.getCaption()) ? sendPhotoRequest.getCaption() : sendPhotoRequest.getOverview();
         return this.executePhotoWithFallback(sendPhotoRequest, sendPhotoBuilder, caption);
      }
   }

   private boolean executePhotoWithFallback(SendPhotoRequest request, SendPhotoBuilder<?, ?> sendPhotoBuilder, String caption) throws TelegramApiException {
      SendPhoto sendPhoto = sendPhotoBuilder.chatId(request.getChatId())
         .photo(this.buildPhotoInput(request))
         .caption(caption)
         .parseMode(request.getParseMode())
         .build();

      try {
         request.getTelegramClient().execute(sendPhoto);
         return true;
      } catch (TelegramApiException var7) {
         if (!StringUtils.hasText(this.normalizePhotoUrl(request.getImgUrl()))) {
            throw var7;
         } else {
            log.warn("Telegram 图片 URL 发送失败，改用默认图片重试: imgUrl={}, error={}", request.getImgUrl(), var7.getMessage());
            SendPhoto fallbackPhoto = sendPhotoBuilder.chatId(request.getChatId())
               .photo(this.defaultPhotoInput())
               .caption(caption)
               .parseMode(request.getParseMode())
               .build();
            request.getTelegramClient().execute(fallbackPhoto);
            return true;
         }
      }
   }

   private InputFile buildPhotoInput(SendPhotoRequest request) {
      String imgUrl = this.normalizePhotoUrl(request.getImgUrl());
      if (StringUtils.hasText(imgUrl)) {
         return new InputFile(imgUrl);
      } else {
         return request.getImgUrlInputStream() != null ? new InputFile(request.getImgUrlInputStream(), "image.jpg") : this.defaultPhotoInput();
      }
   }

   private InputFile defaultPhotoInput() {
      return new InputFile(ResourceUtil.getStream("img/default.jpg"), "default.jpg");
   }

   private String normalizePhotoUrl(String imgUrl) {
      if (!StringUtils.hasText(imgUrl)) {
         return null;
      } else {
         String value = imgUrl.trim();
         String lower = value.toLowerCase(Locale.ROOT);
         if ("null".equals(lower) || lower.endsWith("null")) {
            return null;
         } else {
            return !lower.startsWith("http://") && !lower.startsWith("https://") ? null : value;
         }
      }
   }

   private void addServerNameButton(List<InlineKeyboardRow> rows, String serverName, String serverUrl) {
      if (StringUtils.hasText(serverName) || StringUtils.hasText(serverUrl)) {
         InlineKeyboardRow serverRow = new InlineKeyboardRow();
         InlineKeyboardButton serverButton = new InlineKeyboardButton("");
         serverButton.setText("服务器名称：" + this.resolveServerLabel(serverName));
         serverButton.setCallbackData("server_name_noop");
         serverRow.add(serverButton);
         rows.add(serverRow);
      }
   }

   private String resolveServerLabel(String serverName) {
      return StringUtils.hasText(serverName) ? serverName : "未知服务器";
   }
}
