package com.una.embyhub.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.una.embyhub.model.dto.response.pointsrecord.PointsRecordResponse;
import com.una.embyhub.model.entity.PointsRecord;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface PointsRecordMapper extends BaseMapper<PointsRecord> {
   Page<PointsRecordResponse> selectPageWithUserInfo(
      Page<PointsRecordResponse> page, @Param("userId") Long userId, @Param("username") String username, @Param("recordType") String recordType
   );
}
