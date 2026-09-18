package com.una.embyhub.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.una.embyhub.model.entity.TelegramBindingReviewMessage;
import java.util.List;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface TelegramBindingReviewMessageMapper extends BaseMapper<TelegramBindingReviewMessage> {
   @Insert({"INSERT INTO telegram_binding_review_message\n    (review_id, chat_id, message_id, create_datetime, update_datetime,\n     create_user_name, update_user_name, update_user_id, create_user_id, del_flag)\nVALUES\n    (#{reviewId}, #{chatId}, #{messageId}, NOW(), NOW(),\n     'Telegram Bot', 'Telegram Bot', NULL, NULL, 0)\nON DUPLICATE KEY UPDATE\n    message_id = #{messageId},\n    update_datetime = NOW(),\n    update_user_name = 'Telegram Bot',\n    del_flag = 0\n"})
   int upsertMessage(@Param("reviewId") Long reviewId, @Param("chatId") Long chatId, @Param("messageId") Integer messageId);

   @Select({"SELECT *\nFROM telegram_binding_review_message\nWHERE review_id = #{reviewId}\n  AND del_flag = 0\nORDER BY id\n"})
   List<TelegramBindingReviewMessage> selectActiveByReviewId(@Param("reviewId") Long reviewId);
}
