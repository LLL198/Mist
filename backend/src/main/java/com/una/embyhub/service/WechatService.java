package com.una.embyhub.service;

import com.una.embyhub.model.dto.request.telegram.SendMessageRequest;
import com.una.embyhub.model.dto.request.telegram.SendPhotoRequest;

public interface WechatService {
   default boolean sendPhoto(SendPhotoRequest sendPhotoRequest) throws Exception {
      return this.sendPhoto(sendPhotoRequest, "wechat");
   }

   boolean sendPhoto(SendPhotoRequest sendPhotoRequest, String channelType) throws Exception;

   default boolean sendServerPhoto(SendPhotoRequest sendPhotoRequest) throws Exception {
      return this.sendServerPhoto(sendPhotoRequest, "wechat");
   }

   boolean sendServerPhoto(SendPhotoRequest sendPhotoRequest, String channelType) throws Exception;

   default boolean sendMessage(SendMessageRequest sendMessageRequest) throws Exception {
      return this.sendMessage(sendMessageRequest, "wechat");
   }

   boolean sendMessage(SendMessageRequest sendMessageRequest, String channelType) throws Exception;

   default boolean sendPhotoMessage(SendPhotoRequest sendPhotoRequest) throws Exception {
      return this.sendPhotoMessage(sendPhotoRequest, "wechat");
   }

   boolean sendPhotoMessage(SendPhotoRequest sendPhotoRequest, String channelType) throws Exception;
}
