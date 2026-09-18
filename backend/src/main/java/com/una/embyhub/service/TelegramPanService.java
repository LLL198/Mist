package com.una.embyhub.service;

import com.una.embyhub.model.dto.response.telegram.SearchResponse;

public interface TelegramPanService {
   void pushHome(String chatId, SearchResponse payload, String botToken, String name, String data);
}
