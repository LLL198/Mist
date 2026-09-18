package com.una.embyhub.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.una.embyhub.config.mybatis.MybatisPlusPage;
import com.una.embyhub.model.dto.request.embydevice.EmbyDeviceBlockRequest;
import com.una.embyhub.model.dto.request.embydevice.EmbyDeviceRequest;
import com.una.embyhub.model.dto.response.embydevice.EmbyDeviceResponse;
import com.una.embyhub.model.entity.EmbyDevice;
import java.util.List;

public interface EmbyDeviceService extends IService<EmbyDevice> {
   Page<EmbyDeviceResponse> select(MybatisPlusPage<EmbyDeviceRequest> page);

   void blockDevice(EmbyDeviceBlockRequest request);

   void syncDevices();

   List<String> getDefaultBlockKeywords();
}
