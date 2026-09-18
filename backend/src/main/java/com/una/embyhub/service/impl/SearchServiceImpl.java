package com.una.embyhub.service.impl;

import cn.hutool.http.ContentType;
import cn.hutool.http.Header;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.una.embyhub.model.dto.request.telegram.SearchRequest;
import com.una.embyhub.model.dto.response.telegram.SearchResponse;
import com.una.embyhub.service.SearchService;
import lombok.Generated;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class SearchServiceImpl implements SearchService {
   @Generated
   private static final Logger log = LoggerFactory.getLogger(SearchServiceImpl.class);
   @Value("${embyHubSearch.url}")
   private String embyHubSearchUrl;

   @Override
   public SearchResponse search(SearchRequest req) {
      if (StringUtils.hasText(this.embyHubSearchUrl)) {
         String reqJson = JSON.toJSONString(req);
         HttpResponse resp = HttpRequest.post(this.embyHubSearchUrl + "/api/search")
            .header(Header.CONTENT_TYPE, ContentType.JSON.toString())
            .timeout(15000)
            .body(reqJson)
            .execute();
         if (!resp.isOk()) {
            throw new RuntimeException("上游搜索接口异常，HTTP " + resp.getStatus());
         } else {
            String body = resp.body();
            JSONObject raw = JSON.parseObject(body);
            Integer code = raw.getInteger("code");
            String message = raw.getString("message");
            Object dataNode = raw.get("data");
            if (dataNode == null) {
               dataNode = raw;
               if (code == null) {
                  code = 0;
               }

               if (message == null) {
                  message = "success";
               }
            } else {
               if (code == null) {
                  code = 0;
               }

               if (message == null) {
                  message = "success";
               }
            }

            SearchResponse.SearchDataResponse data = JSON.parseObject(JSON.toJSONString(dataNode), SearchResponse.SearchDataResponse.class);
            return SearchResponse.builder().code(code).message(message).data(data).build();
         }
      } else {
         return new SearchResponse();
      }
   }
}
