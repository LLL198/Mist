package com.una.embyhub.service;

import com.una.embyhub.model.dto.request.telegram.SendMessageRequest;
import com.una.embyhub.model.dto.request.telegram.SendPhotoRequest;

public interface DingDingService {
   boolean sendPhoto(SendPhotoRequest sendPhotoRequest) throws Exception;

   boolean sendMessage(SendMessageRequest sendMessageRequest) throws Exception;

   boolean sendPhotoMessage(SendPhotoRequest sendPhotoRequest) throws Exception;
}
