package com.una.embyhub.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.una.embyhub.model.dto.request.requestpackages.RequestPackagesSave;
import com.una.embyhub.model.dto.request.requestpackages.RequestPackagesUpdate;
import com.una.embyhub.model.dto.response.requestpackages.RequestPackagesResponse;
import com.una.embyhub.model.entity.RequestPackages;
import java.util.List;

public interface RequestPackagesService extends IService<RequestPackages> {
   List<RequestPackagesResponse> select();

   void insertRequestPackages(RequestPackagesSave requestPackagesSave);

   void updateRequestPackages(RequestPackagesUpdate requestPackagesUpdate);

   void deleteByRequestPackagesId(Long requestPackagesId);
}
