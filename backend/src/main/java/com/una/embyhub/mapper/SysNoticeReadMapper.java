package com.una.embyhub.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.una.embyhub.model.entity.SysNoticeRead;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;

public interface SysNoticeReadMapper extends BaseMapper<SysNoticeRead> {
   @Insert({"INSERT IGNORE INTO sys_notice_read (notice_id, user_id, read_datetime)\nVALUES (#{noticeId}, #{userId}, NOW())\n"})
   int insertIgnore(@Param("noticeId") Long noticeId, @Param("userId") Long userId);
}
