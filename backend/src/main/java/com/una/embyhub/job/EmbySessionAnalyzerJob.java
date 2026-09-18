package com.una.embyhub.job;

import cn.hutool.core.date.DateUtil;
import cn.hutool.http.HttpUtil;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.una.embyhub.component.MapSummaryCache;
import com.una.embyhub.config.common.utils.EmbyInfoCacheManagerUtils;
import com.una.embyhub.config.common.utils.IpAddressUtils;
import com.una.embyhub.model.entity.EmbyIpLocations;
import com.una.embyhub.service.EmbyIpLocationsService;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;
import lombok.Generated;
import net.dreamlu.mica.ip2region.core.Ip2regionSearcher;
import net.dreamlu.mica.ip2region.core.IpInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.util.StringUtils;

@Configuration
@EnableScheduling
public class EmbySessionAnalyzerJob {
   @Generated
   private static final Logger log = LoggerFactory.getLogger(EmbySessionAnalyzerJob.class);
   @Autowired
   private EmbyInfoCacheManagerUtils embyInfoCacheManager;
   @Autowired
   private EmbyIpLocationsService embyIpLocationsService;
   @Autowired
   private Ip2regionSearcher searchSearcher;
   @Autowired
   private MapSummaryCache mapSummaryCache;

   public void configureTasks() {
      log.info("用户ip绑定关系定时任务：{}", DateUtil.formatDateTime(new Date()));
      List<EmbyInfoCacheManagerUtils.EmbyServerConfig> serverConfigs = this.embyInfoCacheManager.getEnabledConfigs();
      if (serverConfigs == null || serverConfigs.isEmpty()) {
         serverConfigs = List.of(this.embyInfoCacheManager.getRequiredConfig());
      }

      int totalInserted = 0;

      for (EmbyInfoCacheManagerUtils.EmbyServerConfig config : serverConfigs) {
         try {
            String sessionsJson = this.getEmbySessions(config);
            List<EmbySessionAnalyzerJob.Session> sessions = this.parseSessions(sessionsJson);
            totalInserted += this.mapUserToNonPrivateIPs(sessions, config.id());
         } catch (Exception var7) {
            log.error("处理服务器 [{}] 会话信息失败：{}", config.url(), var7.getMessage());
         }
      }

      if (totalInserted > 0) {
         int finalTotalInserted = totalInserted;
         CompletableFuture.runAsync(() -> {
            log.info("本轮新增IP记录 {} 条，触发地图汇总缓存刷新", finalTotalInserted);
            this.mapSummaryCache.refresh();
         });
      }
   }

   public String getEmbySessions(EmbyInfoCacheManagerUtils.EmbyServerConfig config) {
      Map<String, Object> paramMap = new HashMap<>();
      paramMap.put("api_key", config.apiKey());
      return HttpUtil.get(config.url() + "/Sessions", paramMap);
   }

   public List<EmbySessionAnalyzerJob.Session> parseSessions(String json) {
      Gson gson = new Gson();
      Type listType = (new TypeToken<List<EmbySessionAnalyzerJob.Session>>() {
      }).getType();
      return gson.fromJson(json, listType);
   }

   public int mapUserToNonPrivateIPs(List<EmbySessionAnalyzerJob.Session> sessions, Long embyInfoId) {
      if (sessions != null && !sessions.isEmpty()) {
         Map<String, EmbySessionAnalyzerJob.SessionCandidate> candidates = new LinkedHashMap<>();

         for (EmbySessionAnalyzerJob.Session session : sessions) {
            if (StringUtils.hasText(session.getUserName()) && StringUtils.hasText(session.getRemoteEndPoint())) {
               Optional<IpAddressUtils.ParsedIp> parsedIp = IpAddressUtils.parseLiteral(session.getRemoteEndPoint());
               if (!parsedIp.isEmpty() && !parsedIp.get().privateOrLocal()) {
                  String key = this.buildKey(session.getUserName(), session.getRemoteEndPoint());
                  candidates.putIfAbsent(
                     key,
                     new EmbySessionAnalyzerJob.SessionCandidate(
                        session.getUserName(), session.getRemoteEndPoint(), session.getClient(), parsedIp.get().address()
                     )
                  );
               }
            }
         }

         if (candidates.isEmpty()) {
            return 0;
         } else {
            Set<String> usernames = candidates.values().stream().map(EmbySessionAnalyzerJob.SessionCandidate::userName).collect(Collectors.toSet());
            Set<String> remoteEndpoints = candidates.values().stream().map(EmbySessionAnalyzerJob.SessionCandidate::remoteEndPoint).collect(Collectors.toSet());
            Set<String> existingKeys = new HashSet<>();
            if (!usernames.isEmpty() && !remoteEndpoints.isEmpty()) {
               List<EmbyIpLocations> existing = new LambdaQueryChainWrapper<>(this.embyIpLocationsService.getBaseMapper())
                  .select(EmbyIpLocations::getEmbyUserName, EmbyIpLocations::getIpAddress)
                  .in(EmbyIpLocations::getEmbyUserName, usernames)
                  .in(EmbyIpLocations::getIpAddress, remoteEndpoints)
                  .list();
               existingKeys = existing.stream().map(item -> this.buildKey(item.getEmbyUserName(), item.getIpAddress())).collect(Collectors.toSet());
            }

            List<EmbyIpLocations> toInsert = new ArrayList<>();

            for (EmbySessionAnalyzerJob.SessionCandidate candidate : candidates.values()) {
               String key = this.buildKey(candidate.userName(), candidate.remoteEndPoint());
               if (!existingKeys.contains(key)) {
                  IpInfo ipInfo = this.safeMemorySearch(candidate.ip());
                  EmbyIpLocations embyIpLocations = new EmbyIpLocations();
                  embyIpLocations.setEmbyUserName(candidate.userName());
                  embyIpLocations.setClient(candidate.client());
                  embyIpLocations.setIpAddress(candidate.remoteEndPoint());
                  embyIpLocations.setEmbyInfoId(embyInfoId);
                  if (ipInfo != null) {
                     embyIpLocations.setCity(ipInfo.getCity());
                     embyIpLocations.setCountry(ipInfo.getCountry());
                     embyIpLocations.setRegion(ipInfo.getProvince());
                     embyIpLocations.setIsp(ipInfo.getIsp());
                  }

                  toInsert.add(embyIpLocations);
               }
            }

            if (toInsert.isEmpty()) {
               return 0;
            } else {
               this.embyIpLocationsService.saveBatch(toInsert);
               log.info("新增IP记录数量：{}", toInsert.size());
               return toInsert.size();
            }
         }
      } else {
         return 0;
      }
   }

   private IpInfo safeMemorySearch(String ip) {
      return IpAddressUtils.safeLookup(this.searchSearcher, ip).orElse(null);
   }

   private String buildKey(String username, String remoteEndPoint) {
      return username + "|" + remoteEndPoint;
   }

   static class Session {
      private String UserName;
      private String RemoteEndPoint;
      private String Client;

      @Generated
      public Session() {
      }

      @Generated
      public String getUserName() {
         return this.UserName;
      }

      @Generated
      public String getRemoteEndPoint() {
         return this.RemoteEndPoint;
      }

      @Generated
      public String getClient() {
         return this.Client;
      }

      @Generated
      public void setUserName(final String UserName) {
         this.UserName = UserName;
      }

      @Generated
      public void setRemoteEndPoint(final String RemoteEndPoint) {
         this.RemoteEndPoint = RemoteEndPoint;
      }

      @Generated
      public void setClient(final String Client) {
         this.Client = Client;
      }

      @Generated
      @Override
      public boolean equals(final Object o) {
         if (o == this) {
            return true;
         } else if (!(o instanceof EmbySessionAnalyzerJob.Session other)) {
            return false;
         } else if (!other.canEqual(this)) {
            return false;
         } else {
            Object this$UserName = this.getUserName();
            Object other$UserName = other.getUserName();
            if (this$UserName == null ? other$UserName == null : this$UserName.equals(other$UserName)) {
               Object this$RemoteEndPoint = this.getRemoteEndPoint();
               Object other$RemoteEndPoint = other.getRemoteEndPoint();
               if (this$RemoteEndPoint == null ? other$RemoteEndPoint == null : this$RemoteEndPoint.equals(other$RemoteEndPoint)) {
                  Object this$Client = this.getClient();
                  Object other$Client = other.getClient();
                  return this$Client == null ? other$Client == null : this$Client.equals(other$Client);
               } else {
                  return false;
               }
            } else {
               return false;
            }
         }
      }

      @Generated
      protected boolean canEqual(final Object other) {
         return other instanceof EmbySessionAnalyzerJob.Session;
      }

      @Generated
      @Override
      public int hashCode() {
         int PRIME = 59;
         int result = 1;
         Object $UserName = this.getUserName();
         result = result * 59 + ($UserName == null ? 43 : $UserName.hashCode());
         Object $RemoteEndPoint = this.getRemoteEndPoint();
         result = result * 59 + ($RemoteEndPoint == null ? 43 : $RemoteEndPoint.hashCode());
         Object $Client = this.getClient();
         return result * 59 + ($Client == null ? 43 : $Client.hashCode());
      }

      @Generated
      @Override
      public String toString() {
         return "EmbySessionAnalyzerJob.Session(UserName="
            + this.getUserName()
            + ", RemoteEndPoint="
            + this.getRemoteEndPoint()
            + ", Client="
            + this.getClient()
            + ")";
      }
   }

   private static record SessionCandidate(String userName, String remoteEndPoint, String client, String ip) {
   }
}
