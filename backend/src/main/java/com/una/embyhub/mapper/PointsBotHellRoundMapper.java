package com.una.embyhub.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.una.embyhub.model.entity.PointsBotHellRound;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface PointsBotHellRoundMapper extends BaseMapper<PointsBotHellRound> {
   @Select({"SELECT *\nFROM points_bot_hell_round\nWHERE id = #{id} AND del_flag = 0\nLIMIT 1\nFOR UPDATE\n"})
   PointsBotHellRound selectForUpdate(@Param("id") long id);
}
