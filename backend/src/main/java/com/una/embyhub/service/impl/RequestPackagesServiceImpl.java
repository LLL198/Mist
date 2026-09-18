package com.una.embyhub.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.diboot.core.util.BeanUtils;
import com.una.embyhub.mapper.RequestPackagesMapper;
import com.una.embyhub.model.dto.request.requestpackages.RequestPackagesSave;
import com.una.embyhub.model.dto.request.requestpackages.RequestPackagesUpdate;
import com.una.embyhub.model.dto.response.requestpackages.RequestPackagesResponse;
import com.una.embyhub.model.entity.RequestPackages;
import com.una.embyhub.service.RequestPackagesService;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(
   rollbackFor = {Exception.class}
)
public class RequestPackagesServiceImpl extends ServiceImpl<RequestPackagesMapper, RequestPackages> implements RequestPackagesService {
   @Override
   public List<RequestPackagesResponse> select() {
      List<RequestPackages> requestPackagesList = this.list();
      return BeanUtils.convertList(requestPackagesList, RequestPackagesResponse.class);
   }

   @Override
   public void insertRequestPackages(RequestPackagesSave requestPackagesSave) {
      RequestPackages requestPackages = BeanUtils.convert(requestPackagesSave, RequestPackages.class);
      this.save(requestPackages);
   }

   @Override
   public void updateRequestPackages(RequestPackagesUpdate requestPackagesUpdate) {
      RequestPackages requestPackages = BeanUtils.convert(requestPackagesUpdate, RequestPackages.class);
      this.updateById(requestPackages);
   }

   @Override
   public void deleteByRequestPackagesId(Long requestPackagesId) {
      this.removeById(requestPackagesId);
   }
}
