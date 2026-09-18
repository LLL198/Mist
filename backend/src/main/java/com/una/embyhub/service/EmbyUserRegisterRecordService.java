package com.una.embyhub.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.una.embyhub.config.mybatis.MybatisPlusPage;
import com.una.embyhub.model.dto.request.embyuserrecord.EmbyUserRegisterRecordRequest;
import com.una.embyhub.model.dto.response.embyuserrecord.EmbyUserRegisterRecordResponse;
import com.una.embyhub.model.entity.EmbyUserRegisterRecord;

public interface EmbyUserRegisterRecordService extends IService<EmbyUserRegisterRecord> {
   Page<EmbyUserRegisterRecordResponse> select(MybatisPlusPage<EmbyUserRegisterRecordRequest> page);
}
