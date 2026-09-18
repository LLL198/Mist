package com.una.embyhub.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.una.embyhub.model.entity.PointsBotHellVault;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface PointsBotHellVaultMapper extends BaseMapper<PointsBotHellVault> {
   @Insert({"INSERT INTO points_bot_hell_vault\n    (chat_id, vault_points, total_subsidy_points, total_inflow_points,\n     total_outflow_points, daily_outflow_points, initialized, del_flag)\nVALUES (#{chatId}, 0, 0, 0, 0, 0, 0, 0)\nON DUPLICATE KEY UPDATE chat_id = VALUES(chat_id)\n"})
   int ensureExists(@Param("chatId") long chatId);

   @Select({"SELECT *\nFROM points_bot_hell_vault\nWHERE chat_id = #{chatId} AND del_flag = 0\nLIMIT 1\nFOR UPDATE\n"})
   PointsBotHellVault selectForUpdate(@Param("chatId") long chatId);
}
