package com.una.embyhub.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.una.embyhub.config.mybatis.MybatisPlusPage;
import com.una.embyhub.model.dto.request.pointsrecord.PointsRecordRequest;
import com.una.embyhub.model.dto.response.pointsrecord.PointsRecordResponse;
import com.una.embyhub.model.entity.PointsRecord;

public interface PointsRecordService extends IService<PointsRecord> {
   Page<PointsRecordResponse> queryPageWithUserInfo(MybatisPlusPage<PointsRecordRequest> page);
}
