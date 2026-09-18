package com.una.embyhub.service;

import com.alibaba.fastjson2.JSONObject;

public interface TelegramMediaService {
   JSONObject getHeroList(String timeWindow, int page);

   JSONObject getList(String mediaType, String category, int page);

   JSONObject search(String mediaType, String query, int page);

   JSONObject getDetails(String mediaType, Long id);

   JSONObject getSeasonDetails(Long tvId, int seasonNumber);
}
