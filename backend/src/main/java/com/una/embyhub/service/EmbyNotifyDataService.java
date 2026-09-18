package com.una.embyhub.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.una.embyhub.config.mybatis.MybatisPlusPage;
import com.una.embyhub.model.dto.request.embynotifydata.EmbyNotifyDataRequest;
import com.una.embyhub.model.dto.response.embynotifydata.EmbyNotifyDataResponse;
import com.una.embyhub.model.entity.EmbyNotifyData;

public interface EmbyNotifyDataService extends IService<EmbyNotifyData> {
   Page<EmbyNotifyDataResponse> select(MybatisPlusPage<EmbyNotifyDataRequest> page);
}
