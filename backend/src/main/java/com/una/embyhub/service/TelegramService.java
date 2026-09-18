package com.una.embyhub.service;

import com.una.embyhub.model.dto.request.telegram.SendMessageRequest;
import com.una.embyhub.model.dto.request.telegram.SendPhotoRequest;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public interface TelegramService {
   boolean sendPhoto(SendPhotoRequest sendPhotoRequest) throws TelegramApiException;

   boolean sendMessage(SendMessageRequest sendMessageRequest) throws TelegramApiException;

   boolean sendPhotoMessage(SendPhotoRequest sendPhotoRequest) throws TelegramApiException;
}
