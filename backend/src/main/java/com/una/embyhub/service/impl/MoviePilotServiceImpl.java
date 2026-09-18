package com.una.embyhub.service.impl;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.una.embyhub.config.common.enums.ResponseStatusEnum;
import com.una.embyhub.config.common.exception.BizException;
import com.una.embyhub.config.common.utils.MoviePilotConfigUtils;
import com.una.embyhub.model.dto.request.moviepilot.MoviePilotLoginRequest;
import com.una.embyhub.model.dto.request.moviepilot.MoviePilotSubscribeRequest;
import com.una.embyhub.model.dto.response.moviepilot.MoviePilotLoginResponse;
import com.una.embyhub.model.dto.response.moviepilot.MoviePilotSubscribeResponse;
import com.una.embyhub.model.dto.response.moviepilot.MoviePilotSubscriptionItemResponse;
import com.una.embyhub.service.MoviePilotService;
import java.util.List;
import lombok.Generated;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class MoviePilotServiceImpl implements MoviePilotService {
   @Generated
   private static final Logger log = LoggerFactory.getLogger(MoviePilotServiceImpl.class);
   private static String token;
   private static final String LOGIN_PATH = "/api/v1/login/access-token";
   private static final String SUBSCRIBE_PATH = "/api/v1/subscribe/";
   @Autowired
   private MoviePilotConfigUtils moviePilotConfigUtils;

   @Override
   public MoviePilotLoginResponse login(MoviePilotLoginRequest request) {
      String url = this.buildUrl(request.getUrl(), "/api/v1/login/access-token");
      HttpRequest httpRequest = HttpRequest.post(url).form("username", request.getUsername()).form("password", request.getPassword());
      if (StringUtils.hasText(request.getOtpPassword())) {
         httpRequest.form("otp_password", request.getOtpPassword());
      }

      HttpResponse response = httpRequest.execute();

      MoviePilotLoginResponse var6;
      try {
         if (!response.isOk()) {
            log.error("moviepilot 登录失败, status:{}, body:{}", response.getStatus(), response.body());
            throw new BizException(ResponseStatusEnum.SYSTEM_ERROR.getCode(), "moviepilot 登录失败");
         }

         MoviePilotLoginResponse loginResponse = JSON.parseObject(response.body(), MoviePilotLoginResponse.class);
         token = loginResponse.getAccessToken();
         var6 = loginResponse;
      } finally {
         response.close();
      }

      return var6;
   }

   @Override
   public MoviePilotSubscribeResponse subscribe(MoviePilotSubscribeRequest request) {
      log.info("subscribe request:{}", JSON.toJSONString(request));
      MoviePilotLoginRequest moviePilotLoginRequest = this.moviePilotConfigUtils.getMoviePilotLoginRequest();
      if (moviePilotLoginRequest == null) {
         throw new BizException(ResponseStatusEnum.MOVIEPILOT_NOT_CONFIGURED);
      } else {
         if (!StringUtils.hasText(token)) {
            this.login(moviePilotLoginRequest);
         }

         try {
            return this.doSubscribe(request);
         } catch (BizException var4) {
            if (var4.getMessage().equals(ResponseStatusEnum.MOVIEPILOT_FORBIDDEN.getMsg())) {
               this.login(moviePilotLoginRequest);
               return this.doSubscribe(request);
            } else {
               throw var4;
            }
         }
      }
   }

   @Override
   public MoviePilotSubscribeResponse cancelSubscribe(Long id) {
      MoviePilotLoginRequest moviePilotLoginRequest = this.moviePilotConfigUtils.getMoviePilotLoginRequest();
      if (moviePilotLoginRequest == null) {
         throw new BizException(ResponseStatusEnum.MOVIEPILOT_NOT_CONFIGURED);
      } else {
         if (!StringUtils.hasText(token)) {
            this.login(moviePilotLoginRequest);
         }

         try {
            return this.doCancelSubscribe(id);
         } catch (BizException var4) {
            if (var4.getMessage().equals(ResponseStatusEnum.MOVIEPILOT_FORBIDDEN.getMsg())) {
               this.login(moviePilotLoginRequest);
               return this.doCancelSubscribe(id);
            } else {
               throw var4;
            }
         }
      }
   }

   private MoviePilotSubscribeResponse doSubscribe(MoviePilotSubscribeRequest request) {
      this.validateRequired(token, "token");
      this.validateRequired(request.getName(), "影片名称");
      this.validateRequired(request.getType(), "影片类型");
      this.validateRequired(request.getYear(), "年份");
      MoviePilotLoginRequest moviePilotLoginRequest = this.moviePilotConfigUtils.getMoviePilotLoginRequest();
      if (moviePilotLoginRequest == null) {
         throw new BizException(ResponseStatusEnum.MOVIEPILOT_NOT_CONFIGURED);
      } else {
         String url = this.buildUrl(moviePilotLoginRequest.getUrl(), "/api/v1/subscribe/");
         HttpRequest httpRequest = HttpRequest.post(url)
            .header("Authorization", "Bearer " + token)
            .body(this.buildSubscribeBody(request))
            .contentType("application/json");
         HttpResponse response = httpRequest.execute();

         MoviePilotSubscribeResponse var6;
         try {
            if (!response.isOk()) {
               if (response.getStatus() == 403) {
                  throw new BizException(ResponseStatusEnum.MOVIEPILOT_FORBIDDEN);
               }

               log.error("moviepilot 订阅失败, status:{}, body:{}", response.getStatus(), response.body());
               throw new BizException(ResponseStatusEnum.SYSTEM_ERROR.getCode(), "moviepilot 订阅失败");
            }

            var6 = JSON.parseObject(response.body(), MoviePilotSubscribeResponse.class);
         } finally {
            response.close();
         }

         return var6;
      }
   }

   private MoviePilotSubscribeResponse doCancelSubscribe(Long id) {
      this.validateRequired(token, "token");
      if (id == null) {
         throw new BizException(ResponseStatusEnum.BAD_REQUEST.getCode(), "订阅ID不能为空");
      } else {
         MoviePilotLoginRequest moviePilotLoginRequest = this.moviePilotConfigUtils.getMoviePilotLoginRequest();
         if (moviePilotLoginRequest == null) {
            throw new BizException(ResponseStatusEnum.MOVIEPILOT_NOT_CONFIGURED);
         } else {
            String url = this.buildUrl(moviePilotLoginRequest.getUrl(), "/api/v1/subscribe/" + id);
            HttpRequest httpRequest = HttpRequest.delete(url).header("Authorization", "Bearer " + token);
            HttpResponse response = httpRequest.execute();

            MoviePilotSubscribeResponse var6;
            try {
               if (!response.isOk()) {
                  if (response.getStatus() == 403) {
                     throw new BizException(ResponseStatusEnum.MOVIEPILOT_FORBIDDEN);
                  }

                  log.error("moviepilot 取消订阅失败, status:{}, body:{}", response.getStatus(), response.body());
                  throw new BizException(ResponseStatusEnum.SYSTEM_ERROR.getCode(), "moviepilot 取消订阅失败");
               }

               var6 = JSON.parseObject(response.body(), MoviePilotSubscribeResponse.class);
            } finally {
               response.close();
            }

            return var6;
         }
      }
   }

   @Override
   public List<MoviePilotSubscriptionItemResponse> subscribeList() {
      MoviePilotLoginRequest moviePilotLoginRequest = this.moviePilotConfigUtils.getMoviePilotLoginRequest();
      if (moviePilotLoginRequest == null) {
         throw new BizException(ResponseStatusEnum.MOVIEPILOT_NOT_CONFIGURED);
      } else {
         if (!StringUtils.hasText(token)) {
            this.login(moviePilotLoginRequest);
         }

         try {
            return this.doSubscribeList();
         } catch (BizException var3) {
            if (var3.getMessage().equals(ResponseStatusEnum.MOVIEPILOT_FORBIDDEN.getMsg())) {
               this.login(moviePilotLoginRequest);
               return this.doSubscribeList();
            } else {
               throw var3;
            }
         }
      }
   }

   private List<MoviePilotSubscriptionItemResponse> doSubscribeList() {
      this.validateRequired(token, "token");
      MoviePilotLoginRequest moviePilotLoginRequest = this.moviePilotConfigUtils.getMoviePilotLoginRequest();
      if (moviePilotLoginRequest == null) {
         throw new BizException(ResponseStatusEnum.MOVIEPILOT_NOT_CONFIGURED);
      } else {
         String url = this.buildUrl(moviePilotLoginRequest.getUrl(), "/api/v1/subscribe/");
         HttpRequest httpRequest = HttpRequest.get(url).header("Authorization", "Bearer " + token);
         HttpResponse response = httpRequest.execute();

         List var5;
         try {
            if (!response.isOk()) {
               if (response.getStatus() == 403) {
                  throw new BizException(ResponseStatusEnum.MOVIEPILOT_FORBIDDEN);
               }

               log.error("moviepilot 查询订阅失败, status:{}, body:{}", response.getStatus(), response.body());
               throw new BizException(ResponseStatusEnum.SYSTEM_ERROR.getCode(), "moviepilot 查询订阅失败");
            }

            var5 = JSON.parseArray(response.body(), MoviePilotSubscriptionItemResponse.class);
         } finally {
            response.close();
         }

         return var5;
      }
   }

   private String buildUrl(String baseUrl, String path) {
      String sanitizedBase = baseUrl.replaceAll("/+$", "");
      return sanitizedBase + path;
   }

   private void validateRequired(String value, String fieldName) {
      if (!StringUtils.hasText(value)) {
         throw new BizException(ResponseStatusEnum.BAD_REQUEST.getCode(), fieldName + "不能为空");
      }
   }

   private String buildSubscribeBody(MoviePilotSubscribeRequest request) {
      JSONObject body = new JSONObject();
      body.put("name", request.getName());
      body.put("type", request.getType());
      body.put("year", request.getYear());
      body.put("tmdbid", request.getTmdbid());
      body.put("doubanid", request.getDoubanid());
      body.put("bangumiid", request.getBangumiid());
      body.put("mediaid", request.getMediaid());
      body.put("season", request.getSeason());
      body.put("best_version", request.getBestVersion());
      body.put("episode_group", request.getEpisodeGroup());
      return body.toJSONString();
   }
}
