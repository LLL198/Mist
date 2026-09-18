package com.una.embyhub.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.una.embyhub.model.entity.PointsBotRedPacket;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface PointsBotRedPacketMapper extends BaseMapper<PointsBotRedPacket> {
   @Select({"SELECT *\nFROM points_bot_red_packet\nWHERE id = #{id}\n  AND del_flag = 0\nLIMIT 1\nFOR UPDATE\n"})
   PointsBotRedPacket selectByIdForUpdate(@Param("id") long id);
}
