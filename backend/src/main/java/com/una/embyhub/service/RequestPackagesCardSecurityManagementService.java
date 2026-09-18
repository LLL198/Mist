package com.una.embyhub.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.una.embyhub.config.mybatis.MybatisPlusPage;
import com.una.embyhub.model.dto.request.requestpackagescardsecuritymanagement.RequestPackagesCardSecurityManagementRequest;
import com.una.embyhub.model.dto.response.requestpackagescardsecuritymanagement.RequestPackagesCardSecurityManagementResponse;
import com.una.embyhub.model.dto.response.requestpackagescardsecuritymanagement.RequestPackagesCardSecurityManagementStatusResponse;
import com.una.embyhub.model.entity.RequestPackagesCardSecurityManagement;
import java.util.List;

public interface RequestPackagesCardSecurityManagementService extends IService<RequestPackagesCardSecurityManagement> {
   Page<RequestPackagesCardSecurityManagementResponse> select(MybatisPlusPage<RequestPackagesCardSecurityManagementRequest> page);

   void verification(String cardPassword);

   List<String> add(Integer count, Integer num);

   void delete(List<Long> idList);

   RequestPackagesCardSecurityManagementStatusResponse status();
}
