package com.una.embyhub.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.una.embyhub.config.mybatis.MybatisPlusPage;
import com.una.embyhub.model.dto.request.embyuserrecord.EmbyUserRenewRecordRequest;
import com.una.embyhub.model.dto.response.embyuserrecord.EmbyUserRenewRecordResponse;
import com.una.embyhub.model.entity.EmbyUserRenewRecord;

public interface EmbyUserRenewRecordService extends IService<EmbyUserRenewRecord> {
   Page<EmbyUserRenewRecordResponse> select(MybatisPlusPage<EmbyUserRenewRecordRequest> page);
}
