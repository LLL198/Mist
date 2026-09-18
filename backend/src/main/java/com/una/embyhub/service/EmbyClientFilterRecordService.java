package com.una.embyhub.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.una.embyhub.config.mybatis.MybatisPlusPage;
import com.una.embyhub.model.dto.request.embyclientfilter.EmbyClientFilterRecordRequest;
import com.una.embyhub.model.dto.response.embyclientfilter.EmbyClientFilterRecordResponse;
import com.una.embyhub.model.entity.EmbyClientFilterRecord;

public interface EmbyClientFilterRecordService extends IService<EmbyClientFilterRecord> {
   Page<EmbyClientFilterRecordResponse> select(MybatisPlusPage<EmbyClientFilterRecordRequest> page);
}
