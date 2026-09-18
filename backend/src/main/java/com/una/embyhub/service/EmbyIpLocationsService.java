package com.una.embyhub.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.una.embyhub.config.mybatis.MybatisPlusPage;
import com.una.embyhub.model.dto.request.embyiplocations.EmbyIpLocationsRequest;
import com.una.embyhub.model.dto.request.embyiplocations.ThresholdUserRequest;
import com.una.embyhub.model.dto.response.embyiplocations.EmbyIpLocationMapResponse;
import com.una.embyhub.model.dto.response.embyiplocations.EmbyIpLocationsResponse;
import com.una.embyhub.model.dto.response.embyiplocations.ThresholdUserResponse;
import com.una.embyhub.model.entity.EmbyIpLocations;
import java.util.List;

public interface EmbyIpLocationsService extends IService<EmbyIpLocations> {
   Page<EmbyIpLocationsResponse> select(MybatisPlusPage<EmbyIpLocationsRequest> page);

   Page<ThresholdUserResponse> thresholdUser(MybatisPlusPage<ThresholdUserRequest> page);

   List<EmbyIpLocationMapResponse> mapSummary(Long embyInfoId);
}
