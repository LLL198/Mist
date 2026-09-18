package com.una.embyhub.service.impl;

import com.una.embyhub.config.common.utils.ExpiringGuavaCacheUtils;
import com.una.embyhub.model.dto.response.emby.EmbyTmdbResponse;
import com.una.embyhub.service.EmbyApiClientService;
import java.util.List;
import lombok.Generated;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

@Service
@Transactional(
   rollbackFor = {Exception.class}
)
public class EmbyApiClientServiceImpl implements EmbyApiClientService {
   @Generated
   private static final Logger log = LoggerFactory.getLogger(EmbyApiClientServiceImpl.class);
   @Autowired
   private ExpiringGuavaCacheUtils expiringMapCacheUtils;

   @Override
   public EmbyTmdbResponse getEmbyTmdbResponseByTmdbIdAll(String tmdbId) {
      return null;
   }

   @Override
   public boolean getEmbyByTmdbId(String tmdbId, Long embyInfoId) {
      try {
         List<EmbyTmdbResponse> movies = this.searchMoviesByTmdbId(tmdbId, embyInfoId);
         if (movies.isEmpty()) {
            return false;
         } else {
            log.info("找到 " + movies.size() + " 个匹配的影片:");

            for (EmbyTmdbResponse movie : movies) {
               String itemId = movie.getId();
               String name = movie.getName();
               log.info("- 名称: " + name + ", ID: " + itemId);
            }

            return true;
         }
      } catch (Exception var8) {
         log.error("获取Emby影片失败", (Throwable)var8);
         return false;
      }
   }

   @Override
   public EmbyTmdbResponse getEmbyTmdbResponseByTmdbId(String tmdbId, Long embyInfoId) {
      List<EmbyTmdbResponse> movies = this.searchMoviesByTmdbId(tmdbId, embyInfoId);
      return !CollectionUtils.isEmpty(movies) ? movies.get(0) : new EmbyTmdbResponse();
   }

   public List<EmbyTmdbResponse> searchMoviesByTmdbId(String tmdbId) {
      return this.searchMoviesByTmdbId(tmdbId, null);
   }

   public List<EmbyTmdbResponse> searchMoviesByTmdbId(String tmdbId, Long embyInfoId) {
      List<EmbyTmdbResponse> embyTmdbResponseList = this.expiringMapCacheUtils.getEmbyTmdbResponseByTmdbId(tmdbId, embyInfoId);
      return embyTmdbResponseList.stream().filter(item -> {
         EmbyTmdbResponse.ProviderIdsDTO providerIdsDTO = item.getProviderIds();
         return providerIdsDTO != null && providerIdsDTO.getTmdb() != null && providerIdsDTO.getTmdb().equals(tmdbId);
      }).toList();
   }
}
