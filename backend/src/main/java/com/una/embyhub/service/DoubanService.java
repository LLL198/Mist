package com.una.embyhub.service;

import com.una.embyhub.model.dto.request.douban.DoubanHotRequest;
import com.una.embyhub.model.dto.request.douban.DoubanSearchRequest;
import com.una.embyhub.model.dto.response.douban.DoubanIdMappingResponse;
import com.una.embyhub.model.dto.response.douban.DoubanPageResponse;
import com.una.embyhub.model.dto.response.douban.DoubanSubjectResponse;
import com.una.embyhub.model.dto.response.douban.DoubanTmdbDetailResponse;
import org.springframework.http.ResponseEntity;

public interface DoubanService {
   DoubanPageResponse trending(DoubanHotRequest request);

   DoubanPageResponse search(DoubanSearchRequest request);

   DoubanSubjectResponse getSubject(String doubanId);

   DoubanIdMappingResponse getIdMapping(String doubanId);

   DoubanTmdbDetailResponse getTmdbDetail(String doubanId);

   ResponseEntity<byte[]> proxyImage(String imageUrl);
}
