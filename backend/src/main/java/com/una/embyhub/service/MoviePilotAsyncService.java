package com.una.embyhub.service;

import cn.hutool.core.date.DateUtil;
import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.una.embyhub.mapper.RequestListMapper;
import com.una.embyhub.model.dto.request.moviepilot.MoviePilotSubscribeRequest;
import com.una.embyhub.model.dto.request.requestlist.RequestListSave;
import com.una.embyhub.model.dto.response.moviepilot.MoviePilotSubscribeResponse;
import com.una.embyhub.model.entity.RequestList;
import lombok.Generated;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class MoviePilotAsyncService {
   @Generated
   private static final Logger log = LoggerFactory.getLogger(MoviePilotAsyncService.class);
   @Autowired
   private MoviePilotService moviePilotService;
   @Autowired
   private RequestListMapper requestListMapper;

   @Async
   public void callMoviePilotSubscribe(RequestListSave requestListSave) {
      log.info("MoviePilot 异步调用开始：{}", JSONObject.toJSONString(requestListSave));
      MoviePilotSubscribeRequest moviePilotSubscribeRequest = new MoviePilotSubscribeRequest();
      moviePilotSubscribeRequest.setName(requestListSave.getOriginalName());
      if ("movie".equals(requestListSave.getType())) {
         moviePilotSubscribeRequest.setType("电影");
         moviePilotSubscribeRequest.setTmdbid(requestListSave.getTmdbId().longValue());
      } else if ("tv".equals(requestListSave.getType())) {
         moviePilotSubscribeRequest.setType("电视剧");
         moviePilotSubscribeRequest.setTmdbid(requestListSave.getParentTmdbId().longValue());
      }

      moviePilotSubscribeRequest.setYear(DateUtil.format(requestListSave.getReleaseDate(), "yyyy"));
      moviePilotSubscribeRequest.setSeason(requestListSave.getSeason());
      log.info("MoviePilot 异步调用参数1：{}", JSONObject.toJSONString(moviePilotSubscribeRequest));
      this.moviePilotService.subscribe(moviePilotSubscribeRequest);
   }

   @Async
   public void callMoviePilotSubscribe(RequestList requestList) {
      this.subscribeRequestList(requestList, requestList == null ? null : requestList.getName());
   }

   @Async
   public void callMoviePilotSubscribe(RequestList requestList, String subscribeName) {
      this.subscribeRequestList(requestList, subscribeName);
   }

   private void subscribeRequestList(RequestList requestList, String subscribeName) {
      log.info("MoviePilot 异步调用开始（实体对象）：{}", JSONObject.toJSONString(requestList));
      if (this.canSubscribe(requestList)) {
         MoviePilotSubscribeRequest moviePilotSubscribeRequest = new MoviePilotSubscribeRequest();
         moviePilotSubscribeRequest.setName(StringUtils.hasText(subscribeName) ? subscribeName : requestList.getName());
         if ("movie".equals(requestList.getType())) {
            moviePilotSubscribeRequest.setType("电影");
            moviePilotSubscribeRequest.setTmdbid(requestList.getTmdbId().longValue());
         } else if ("tv".equals(requestList.getType())) {
            moviePilotSubscribeRequest.setType("电视剧");
            moviePilotSubscribeRequest.setTmdbid(requestList.getParentTmdbId().longValue());
         }

         if (requestList.getReleaseDate() != null) {
            moviePilotSubscribeRequest.setYear(DateUtil.format(requestList.getReleaseDate(), "yyyy"));
         }

         moviePilotSubscribeRequest.setSeason(requestList.getSeason());
         MoviePilotSubscribeResponse response = this.moviePilotService.subscribe(moviePilotSubscribeRequest);
         this.saveSubscriptionIdOrCancelIfRequestClosed(requestList, response);
      }
   }

   private boolean canSubscribe(RequestList requestList) {
      if (requestList == null) {
         return false;
      } else if (requestList.getId() == null) {
         return true;
      } else {
         RequestList latest = this.requestListMapper.selectById(requestList.getId());
         if (latest == null) {
            return true;
         } else {
            boolean closed = Integer.valueOf(1).equals(latest.getStatus())
               || Integer.valueOf(2).equals(latest.getStatus())
               || Integer.valueOf(2).equals(latest.getAuditStatus());
            if (closed) {
               log.info("MoviePilot 订阅跳过，求片状态已关闭：id={}, status={}, auditStatus={}", latest.getId(), latest.getStatus(), latest.getAuditStatus());
            }

            return !closed;
         }
      }
   }

   private void saveSubscriptionIdOrCancelIfRequestClosed(RequestList requestList, MoviePilotSubscribeResponse response) {
      if (requestList != null && requestList.getId() != null && response != null && response.getData() != null && response.getData().getId() != null) {
         Long subscriptionId = response.getData().getId();
         int updated = this.requestListMapper
            .update(
               null,
               new LambdaUpdateWrapper<RequestList>()
                  .set(RequestList::getMoviePilotSubscriptionId, subscriptionId)
                  .eq(RequestList::getId, requestList.getId())
                  .ne(RequestList::getStatus, Integer.valueOf(1))
                  .ne(RequestList::getStatus, Integer.valueOf(2))
                  .and(wrapper -> wrapper.isNull(RequestList::getAuditStatus).or().ne(RequestList::getAuditStatus, Integer.valueOf(2)))
            );
         if (updated <= 0) {
            this.moviePilotService.cancelSubscribe(subscriptionId);
         }
      }
   }
}
