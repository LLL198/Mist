package com.una.embyhub.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.una.embyhub.config.mybatis.MybatisPlusPage;
import com.una.embyhub.model.dto.request.distribution.DistributionCustomExchangeRequest;
import com.una.embyhub.model.dto.request.distribution.DistributionCustomExchangeReviewRequest;
import com.una.embyhub.model.dto.response.distribution.DistributionCustomExchangeResponse;
import com.una.embyhub.model.entity.DistributionCustomExchange;

public interface DistributionCustomExchangeService extends IService<DistributionCustomExchange> {
   Page<DistributionCustomExchangeResponse> queryCustomExchange(MybatisPlusPage<DistributionCustomExchangeRequest> page);

   void reviewCustomExchange(DistributionCustomExchangeReviewRequest request);
}
