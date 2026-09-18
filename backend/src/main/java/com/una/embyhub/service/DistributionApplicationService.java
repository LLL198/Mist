package com.una.embyhub.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.una.embyhub.config.mybatis.MybatisPlusPage;
import com.una.embyhub.model.dto.request.distributionapplication.DistributionApplicationDeleteRequest;
import com.una.embyhub.model.dto.request.distributionapplication.DistributionApplicationRequest;
import com.una.embyhub.model.dto.request.distributionapplication.DistributionApplicationReviewRequest;
import com.una.embyhub.model.dto.request.distributionapplication.DistributionApplicationSave;
import com.una.embyhub.model.dto.response.distributionapplication.DistributionApplicationResponse;
import com.una.embyhub.model.dto.response.distributionapplication.DistributionApplicationStatisticsResponse;
import com.una.embyhub.model.entity.DistributionApplication;
import java.util.Map;

public interface DistributionApplicationService extends IService<DistributionApplication> {
   Page<DistributionApplicationResponse> selectMyList(MybatisPlusPage<DistributionApplicationRequest> page);

   DistributionApplicationStatisticsResponse getStatistics(DistributionApplicationRequest request);

   void submitApplication(DistributionApplicationSave saveDto);

   void deleteApplication(DistributionApplicationDeleteRequest request);

   void reviewApplication(DistributionApplicationReviewRequest request);

   Map<String, Object> getDashboard();
}
