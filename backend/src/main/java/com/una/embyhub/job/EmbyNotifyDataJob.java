package com.una.embyhub.job;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.resource.ResourceUtil;
import cn.hutool.core.io.unit.DataSizeUtil;
import cn.hutool.core.io.unit.DataUnit;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.conditions.update.LambdaUpdateChainWrapper;
import com.una.embyhub.config.common.constants.NotifyMessageType;
import com.una.embyhub.config.common.utils.ConfigCacheLoaderUtils;
import com.una.embyhub.config.common.utils.NotifyUtils;
import com.una.embyhub.config.job.ScheduledTaskMeta;
import com.una.embyhub.mapper.EmbyNotifyDataDetailsMapper;
import com.una.embyhub.model.dto.request.telegram.SendPhotoRequest;
import com.una.embyhub.model.dto.response.embynotifydatadetails.EmbyNotifyDataDetailsResponseData;
import com.una.embyhub.model.entity.BaseEntity;
import com.una.embyhub.model.entity.EmbyInfo;
import com.una.embyhub.model.entity.EmbyNotifyData;
import com.una.embyhub.model.entity.EmbyNotifyDataDetails;
import com.una.embyhub.service.EmbyInfoService;
import com.una.embyhub.service.EmbyNotifyDataDetailsService;
import com.una.embyhub.service.EmbyNotifyDataService;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import lombok.Generated;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

@Configuration
@EnableScheduling
public class EmbyNotifyDataJob {
   @Generated
   private static final Logger log = LoggerFactory.getLogger(EmbyNotifyDataJob.class);
   @Autowired
   private EmbyNotifyDataService embyNotifyDataService;
   @Autowired
   private EmbyNotifyDataDetailsService embyNotifyDataDetailsService;
   @Autowired
   private NotifyUtils notifyUtils;
   @Autowired
   private EmbyNotifyDataDetailsMapper embyNotifyDataDetailsMapper;
   @Autowired
   private EmbyInfoService embyInfoService;
   @Autowired
   private ConfigCacheLoaderUtils configCacheLoaderUtils;

   @Scheduled(
      cron = "0 * * * * *",
      zone = "Asia/Shanghai"
   )
   @ScheduledTaskMeta(
      name = "入库任务",
      remark = "Emby入库会发送入库通知"
   )
   public void configureTasks() {
      log.info("入库通知定时任务：{}", DateUtil.formatDateTime(new Date()));
      List<EmbyNotifyData> embyNotifyDataListMovie = new LambdaQueryChainWrapper<>(this.embyNotifyDataService.getBaseMapper())
         .eq(EmbyNotifyData::getStatus, Integer.valueOf(2))
         .and(x -> x.eq(EmbyNotifyData::getType, "Movie").or().eq(EmbyNotifyData::getType, "movie"))
         .last("limit 5")
         .list();
      embyNotifyDataListMovie.forEach(
         embyNotifyData -> {
            try {
               SendPhotoRequest sendPhotoRequest = new SendPhotoRequest();
               if (!StringUtils.hasText(embyNotifyData.getImgUrl())) {
                  sendPhotoRequest.setImgUrlInputStream(ResourceUtil.getStream("img/default.jpg"));
               }

               sendPhotoRequest.setName(this.addServerLabel(embyNotifyData.getEmbyInfoId(), embyNotifyData.getName()));
               sendPhotoRequest.setServerUrl(this.getServerUrl(embyNotifyData.getEmbyInfoId()));
               sendPhotoRequest.setServerName(this.getServerName(embyNotifyData.getEmbyInfoId()));
               sendPhotoRequest.setOverview(embyNotifyData.getOverview());
               sendPhotoRequest.setTmdbUrl(embyNotifyData.getTmdbUrl());
               sendPhotoRequest.setImgUrl(embyNotifyData.getImgUrl());
               sendPhotoRequest.setParseMode("Markdown");
               sendPhotoRequest.setProductionYear(
                  StringUtils.hasText(embyNotifyData.getProductionYear()) ? Integer.valueOf(embyNotifyData.getProductionYear()) : null
               );
               sendPhotoRequest.setGenres(embyNotifyData.getGenres());
               sendPhotoRequest.setType(embyNotifyData.getType());
               sendPhotoRequest.setDisplayTitle(embyNotifyData.getDisplayTitle());
               sendPhotoRequest.setAudioQuality(embyNotifyData.getAudioQuality());
               sendPhotoRequest.setSubtitleInfo(embyNotifyData.getSubtitleInfo());
               sendPhotoRequest.setSize(DataSizeUtil.format(Long.valueOf(embyNotifyData.getSize()), DataUnit.GIGABYTES));
               sendPhotoRequest.setBackdropPath(embyNotifyData.getBackdropPath());
               sendPhotoRequest.setVoteAverage(embyNotifyData.getVoteAverage());
               sendPhotoRequest.setVoteCount(embyNotifyData.getVoteCount());
               sendPhotoRequest.setProductionCountries(embyNotifyData.getProductionCountries());
               this.notifyUtils
                  .sendMultiChannel(
                     sendPhotoRequest, "media_photo_detail", NotifyMessageType.PHOTO_DETAIL, true, "telegram", "wechat", "wechatBot", "dingding", "messagepush"
                  );
               embyNotifyData.setStatus(1);
               this.embyNotifyDataService.updateById(embyNotifyData);
            } catch (Exception var3) {
               var3.printStackTrace();
               log.error("电影发送通知失败：{}", var3.getMessage());
               embyNotifyData.setStatus(0);
               this.embyNotifyDataService.updateById(embyNotifyData);
            }
         }
      );
      List<EmbyNotifyDataDetailsResponseData> embyNotifyDataDetailsResponseDataList = this.embyNotifyDataDetailsMapper.getEmbyNotifyDataDetails();
      embyNotifyDataDetailsResponseDataList.forEach(
         embyNotifyDataDetailsResponseData -> {
            List<String> idList = StrUtil.split(embyNotifyDataDetailsResponseData.getIdList(), ",");

            try {
               StringBuilder stringBuilder = new StringBuilder();
               EmbyNotifyData embyNotifyData = this.embyNotifyDataService.getById(embyNotifyDataDetailsResponseData.getEmbyNotifyDataId());
               if (embyNotifyData == null) {
                  return;
               }

               SendPhotoRequest sendPhotoRequest = new SendPhotoRequest();
               if (!StringUtils.hasText(embyNotifyData.getImgUrl())) {
                  sendPhotoRequest.setImgUrlInputStream(ResourceUtil.getStream("img/default.jpg"));
               }

               if ("Episode".equals(embyNotifyData.getType())) {
                  stringBuilder.append("\ud83d\udc26\u200d\ud83d\udd25 新增");
                  stringBuilder.append(embyNotifyDataDetailsResponseData.getCount());
                  stringBuilder.append("集 \ud83d\udc26\u200d\ud83d\udd25\n\n");
                  stringBuilder.append(embyNotifyDataDetailsResponseData.getEpisodeList());
               }

               if ("Series".equals(embyNotifyData.getType())) {
                  stringBuilder.append("\ud83d\udc26\u200d\ud83d\udd25 新增内容汇总 \ud83d\udc26\u200d\ud83d\udd25\n\n");
                  stringBuilder.append("\ud83e\udddd\ud83c\udffb\u200d♀️ " + embyNotifyDataDetailsResponseData.getEpisodeList());
               }

               sendPhotoRequest.setTvInfo(stringBuilder.toString());
               sendPhotoRequest.setName(this.addServerLabel(embyNotifyDataDetailsResponseData.getEmbyInfoId(), embyNotifyData.getName()));
               sendPhotoRequest.setServerUrl(this.getServerUrl(embyNotifyDataDetailsResponseData.getEmbyInfoId()));
               sendPhotoRequest.setServerName(this.getServerName(embyNotifyDataDetailsResponseData.getEmbyInfoId()));
               sendPhotoRequest.setOverview(embyNotifyData.getOverview());
               sendPhotoRequest.setTmdbUrl(embyNotifyData.getTmdbUrl());
               sendPhotoRequest.setImgUrl(embyNotifyData.getImgUrl());
               sendPhotoRequest.setParseMode("Markdown");
               sendPhotoRequest.setProductionYear(
                  StringUtils.hasText(embyNotifyData.getProductionYear()) ? Integer.valueOf(embyNotifyData.getProductionYear()) : null
               );
               sendPhotoRequest.setGenres(embyNotifyData.getGenres());
               sendPhotoRequest.setType(embyNotifyData.getType());
               sendPhotoRequest.setDisplayTitle(embyNotifyData.getDisplayTitle());
               sendPhotoRequest.setAudioQuality(embyNotifyData.getAudioQuality());
               sendPhotoRequest.setSubtitleInfo(embyNotifyData.getSubtitleInfo());
               sendPhotoRequest.setSize(DataSizeUtil.format(embyNotifyDataDetailsResponseData.getTotalSize(), DataUnit.GIGABYTES));
               sendPhotoRequest.setBackdropPath(embyNotifyData.getBackdropPath());
               sendPhotoRequest.setVoteAverage(embyNotifyData.getVoteAverage());
               sendPhotoRequest.setVoteCount(embyNotifyData.getVoteCount());
               sendPhotoRequest.setProductionCountries(embyNotifyData.getProductionCountries());
               this.notifyUtils
                  .sendMultiChannel(sendPhotoRequest, "media_photo_detail", NotifyMessageType.PHOTO_DETAIL, true, "telegram", "dingding", "messagepush");
               StringBuilder wechatSb = new StringBuilder();
               String compactTvInfo = this.formatTvSeasonEpisodes(embyNotifyData.getName(), embyNotifyDataDetailsResponseData.getEpisodeList());
               if ("Episode".equals(embyNotifyData.getType())) {
                  wechatSb.append("\ud83d\udc26\u200d\ud83d\udd25 新增");
                  wechatSb.append(embyNotifyDataDetailsResponseData.getCount());
                  wechatSb.append("集 \ud83d\udc26\u200d\ud83d\udd25\n\n");
                  wechatSb.append(StringUtils.hasText(compactTvInfo) ? compactTvInfo : embyNotifyDataDetailsResponseData.getEpisodeList());
               } else if ("Series".equals(embyNotifyData.getType())) {
                  wechatSb.append("\ud83d\udc26\u200d\ud83d\udd25 新增内容汇总 \ud83d\udc26\u200d\ud83d\udd25\n\n");
                  wechatSb.append("\ud83e\udddd\ud83c\udffb\u200d♀️ ")
                     .append(StringUtils.hasText(compactTvInfo) ? compactTvInfo : embyNotifyDataDetailsResponseData.getEpisodeList());
               }

               sendPhotoRequest.setTvInfo(wechatSb.toString());
               String customPosterEnabled = this.configCacheLoaderUtils.getConfigValue("custom_poster_enabled");
               if ("true".equalsIgnoreCase(customPosterEnabled)) {
                  sendPhotoRequest.setName(sendPhotoRequest.getName() + " " + compactTvInfo);
               }

               sendPhotoRequest.setProductionCountries(embyNotifyData.getProductionCountries());
               this.notifyUtils.sendMultiChannel(sendPhotoRequest, "media_photo_detail", NotifyMessageType.PHOTO_DETAIL, true, "wechat", "wechatBot");
               new LambdaUpdateChainWrapper<>(this.embyNotifyDataDetailsService.getBaseMapper())
                  .in(EmbyNotifyDataDetails::getId, idList)
                  .set(EmbyNotifyDataDetails::getStatus, Integer.valueOf(1))
                  .set(BaseEntity::getUpdateDatetime, new Date())
                  .update();
            } catch (Exception var9) {
               var9.printStackTrace();
               log.error("电视剧发送通知失败：{}", var9.getMessage());
               if (!CollectionUtils.isEmpty(idList)) {
                  new LambdaUpdateChainWrapper<>(this.embyNotifyDataDetailsService.getBaseMapper())
                     .in(EmbyNotifyDataDetails::getId, idList)
                     .set(EmbyNotifyDataDetails::getStatus, Integer.valueOf(0))
                     .set(BaseEntity::getUpdateDatetime, new Date())
                     .update();
               }
            }
         }
      );
   }

   private String formatTvSeasonEpisodes(String name, String episodeList) {
      try {
         String seasonStr = "S01";
         if (StrUtil.isNotBlank(name)) {
            Pattern seasonPattern = Pattern.compile("第(\\d+)季");
            Matcher matcher = seasonPattern.matcher(name);
            if (matcher.find()) {
               int s = Integer.parseInt(matcher.group(1));
               seasonStr = String.format("S%02d", s);
            }
         }

         if (StrUtil.isBlank(episodeList)) {
            return "";
         } else {
            List<Integer> episodes = new ArrayList<>();
            Pattern episodePattern = Pattern.compile("第(\\d+)集");
            Matcher epMatcher = episodePattern.matcher(episodeList);

            while (epMatcher.find()) {
               episodes.add(Integer.parseInt(epMatcher.group(1)));
            }

            if (episodes.isEmpty()) {
               return "";
            } else {
               Collections.sort(episodes);
               StringBuilder sb = new StringBuilder(seasonStr);
               List<String> groups = new ArrayList<>();
               if (!episodes.isEmpty()) {
                  int start = episodes.get(0);
                  int end = start;

                  for (int i = 1; i < episodes.size(); i++) {
                     int current = episodes.get(i);
                     if (current == end + 1) {
                        end = current;
                     } else {
                        groups.add(this.formatRange(start, end));
                        start = current;
                        end = current;
                     }
                  }

                  groups.add(this.formatRange(start, end));
               }

               sb.append(String.join(" ,", groups));
               return sb.toString();
            }
         }
      } catch (Exception var13) {
         log.error("解析剧集信息失败", (Throwable)var13);
         return "";
      }
   }

   private String formatRange(int start, int end) {
      return start == end ? String.format("E%02d", start) : String.format("E%02d-E%02d", start, end);
   }

   private String addServerLabel(Long embyInfoId, String name) {
      return StringUtils.hasText(name) ? name : "未获取到影片名称";
   }

   private String getServerLabel(Long embyInfoId) {
      EmbyInfo embyInfo = embyInfoId != null ? this.embyInfoService.getById(embyInfoId) : null;
      if (embyInfo != null && StringUtils.hasText(embyInfo.getServerName())) {
         return embyInfo.getServerName();
      } else {
         return embyInfoId != null ? "服务器-" + embyInfoId : "默认服务器";
      }
   }

   private String getServerName(Long embyInfoId) {
      EmbyInfo embyInfo = embyInfoId != null ? this.embyInfoService.getById(embyInfoId) : null;
      return embyInfo != null && StringUtils.hasText(embyInfo.getServerName()) ? embyInfo.getServerName() : null;
   }

   private String getServerUrl(Long embyInfoId) {
      EmbyInfo embyInfo = embyInfoId != null ? this.embyInfoService.getById(embyInfoId) : null;
      if (embyInfo == null) {
         return null;
      } else {
         String url = embyInfo.getEmbyUrl();
         if (!StringUtils.hasText(url) || !url.startsWith("http://") && !url.startsWith("https://")) {
            StringBuilder builder = new StringBuilder();
            if (StringUtils.hasText(embyInfo.getEmbyAgreement())) {
               builder.append(embyInfo.getEmbyAgreement()).append("://");
            }

            if (StringUtils.hasText(embyInfo.getEmbyUrl())) {
               builder.append(embyInfo.getEmbyUrl());
            }

            if (StringUtils.hasText(embyInfo.getEmbyPort())) {
               if (embyInfo.getEmbyUrl() != null && !embyInfo.getEmbyUrl().contains(":")) {
                  builder.append(":");
               }

               builder.append(embyInfo.getEmbyPort());
            }

            return builder.length() > 0 ? builder.toString() : null;
         } else {
            return url;
         }
      }
   }
}
