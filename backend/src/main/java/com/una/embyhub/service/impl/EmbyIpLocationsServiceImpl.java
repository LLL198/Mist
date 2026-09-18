package com.una.embyhub.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.una.embyhub.component.MapSummaryCache;
import com.una.embyhub.config.common.utils.PlaybackReportingLocationUtils;
import com.una.embyhub.config.mybatis.MybatisPlusPage;
import com.una.embyhub.mapper.EmbyIpLocationsMapper;
import com.una.embyhub.model.dto.request.embyiplocations.EmbyIpLocationsRequest;
import com.una.embyhub.model.dto.request.embyiplocations.ThresholdUserRequest;
import com.una.embyhub.model.dto.response.embyiplocations.EmbyIpLocationMapResponse;
import com.una.embyhub.model.dto.response.embyiplocations.EmbyIpLocationsResponse;
import com.una.embyhub.model.dto.response.embyiplocations.ThresholdUserResponse;
import com.una.embyhub.model.entity.EmbyInfo;
import com.una.embyhub.model.entity.EmbyIpLocations;
import com.una.embyhub.service.EmbyInfoService;
import com.una.embyhub.service.EmbyIpLocationsService;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Service
@Transactional(
   rollbackFor = {Exception.class}
)
public class EmbyIpLocationsServiceImpl extends ServiceImpl<EmbyIpLocationsMapper, EmbyIpLocations> implements EmbyIpLocationsService {
   @Autowired
   private EmbyIpLocationsMapper embyIpLocationsMapper;
   @Autowired
   private EmbyInfoService embyInfoService;
   @Autowired
   private MapSummaryCache mapSummaryCache;

   @Override
   public Page<EmbyIpLocationsResponse> select(MybatisPlusPage<EmbyIpLocationsRequest> page) {
      EmbyIpLocationsRequest request = page.getObject();
      Page<EmbyIpLocationsResponse> resultPage = this.embyIpLocationsMapper
         .selectPlaybackReportingSources(
            new Page<>(page.getCurrent(), page.getSize()),
            request == null ? null : request.getEmbyInfoId(),
            this.trimToNull(request == null ? null : request.getEmbyUserName()),
            this.trimToNull(request == null ? null : request.getIpAddress())
         );
      List<EmbyIpLocationsResponse> records = resultPage.getRecords();
      records.forEach(this::applyLocation);
      this.fillSourceServerNames(records);
      return resultPage;
   }

   @Override
   public Page<ThresholdUserResponse> thresholdUser(MybatisPlusPage<ThresholdUserRequest> page) {
      ThresholdUserRequest request = page.getObject();
      Long embyInfoId = request == null ? null : request.getEmbyInfoId();
      Integer thresholdUserCount = request != null && request.getThresholdUserCount() != null ? request.getThresholdUserCount() : 0;
      Page<ThresholdUserResponse> thresholdUserResponsePage = this.embyIpLocationsMapper
         .selectPlaybackReportingThresholdUsers(page.getPageDto(EmbyIpLocations.class), thresholdUserCount, embyInfoId);
      List<ThresholdUserResponse> records = thresholdUserResponsePage.getRecords();
      records.forEach(this::applyThresholdLocationList);
      this.fillThresholdServerNames(records);
      return thresholdUserResponsePage;
   }

   @Override
   public List<EmbyIpLocationMapResponse> mapSummary(Long embyInfoId) {
      if (embyInfoId == null) {
         return this.mapSummaryCache.getGlobalSummary();
      } else {
         List<EmbyIpLocationMapResponse> list = this.embyIpLocationsMapper.selectMapSummary(embyInfoId);
         if (list != null && !list.isEmpty()) {
            list.forEach(this::applyMapLocation);
            list.forEach(this.mapSummaryCache::processGeocoding);
         }

         return list;
      }
   }

   private void applyLocation(EmbyIpLocationsResponse response) {
      PlaybackReportingLocationUtils.LocationParts parts = PlaybackReportingLocationUtils.parse(response.getLocation());
      response.setCountry(parts.country());
      response.setRegion(parts.region());
      response.setCity(parts.city());
      response.setIsp(parts.isp());
   }

   private void applyThresholdLocationList(ThresholdUserResponse response) {
      String cityList = this.toLocationDisplayList(response.getCityList());
      response.setCityList(cityList);
      List<String> cities = this.splitCommaList(cityList);
      response.setCity(cities.isEmpty() ? null : cities.get(0));
   }

   private void applyMapLocation(EmbyIpLocationMapResponse response) {
      PlaybackReportingLocationUtils.LocationParts parts = PlaybackReportingLocationUtils.parse(response.getName());
      response.setCountry(parts.country());
      response.setRegion(parts.region());
      response.setCity(parts.city());
      response.setName(parts.displayName());
   }

   private String toLocationDisplayList(String locations) {
      LinkedHashSet<String> names = new LinkedHashSet<>();

      for (String location : this.splitCommaList(locations)) {
         names.add(PlaybackReportingLocationUtils.parse(location).displayName());
      }

      return String.join(",", names);
   }

   private List<String> splitCommaList(String value) {
      return !StringUtils.hasText(value) ? List.of() : Arrays.stream(value.split(",")).map(String::trim).filter(StringUtils::hasText).toList();
   }

   private void fillSourceServerNames(List<EmbyIpLocationsResponse> records) {
      Map<Long, String> serverNameMap = this.loadServerNameMap(records.stream().map(EmbyIpLocationsResponse::getEmbyInfoId).toList());
      records.forEach(record -> record.setServerName(serverNameMap.get(record.getEmbyInfoId())));
   }

   private void fillThresholdServerNames(List<ThresholdUserResponse> records) {
      Map<Long, String> serverNameMap = this.loadServerNameMap(records.stream().map(ThresholdUserResponse::getEmbyInfoId).toList());
      records.forEach(record -> record.setServerName(serverNameMap.get(record.getEmbyInfoId())));
   }

   private Map<Long, String> loadServerNameMap(Collection<Long> ids) {
      LinkedHashSet<Long> idSet = ids.stream().filter(Objects::nonNull).collect(Collectors.toCollection(LinkedHashSet::new));
      return idSet.isEmpty()
         ? Map.of()
         : this.embyInfoService.listByIds(idSet).stream().collect(Collectors.toMap(EmbyInfo::getId, EmbyInfo::getServerName, (left, right) -> left));
   }

   private String trimToNull(String value) {
      return !StringUtils.hasText(value) ? null : value.trim();
   }
}
