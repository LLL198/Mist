package com.una.embyhub.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.una.embyhub.model.entity.PointsBotUser;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface PointsBotUserMapper extends BaseMapper<PointsBotUser> {
   @Select({"SELECT *\nFROM points_bot_user\nWHERE chat_id = #{chatId}\n  AND user_id = #{userId}\n  AND del_flag = 0\nLIMIT 1\nFOR UPDATE\n"})
   PointsBotUser selectForUpdate(@Param("chatId") long chatId, @Param("userId") long userId);
}
