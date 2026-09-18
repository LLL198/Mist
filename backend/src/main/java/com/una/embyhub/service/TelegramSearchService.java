package com.una.embyhub.service;

import com.una.embyhub.model.dto.request.telegram.TelegramMessageRequest;
import com.una.embyhub.model.dto.response.telegram.TelegramMessageResponse;
import java.util.List;

public interface TelegramSearchService {
   List<TelegramMessageResponse> searchMultipleChannelsAndMerge(TelegramMessageRequest telegramMessageRequest);
}
