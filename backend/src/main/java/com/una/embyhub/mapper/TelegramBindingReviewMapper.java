package com.una.embyhub.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.una.embyhub.model.entity.TelegramBindingReview;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface TelegramBindingReviewMapper extends BaseMapper<TelegramBindingReview> {
   @Select({"SELECT * FROM telegram_binding_review WHERE id = #{id} AND del_flag = 0 FOR UPDATE"})
   TelegramBindingReview selectByIdForUpdate(Long id);

   @Select({"SELECT * FROM telegram_binding_review WHERE review_uuid = #{reviewUuid} AND del_flag = 0 FOR UPDATE"})
   TelegramBindingReview selectByReviewUuidForUpdate(String reviewUuid);

   @Select({"SELECT * FROM telegram_binding_review\nWHERE user_id = #{userId}\n  AND status = 0\n  AND del_flag = 0\nORDER BY id DESC\nLIMIT 1\nFOR UPDATE\n"})
   TelegramBindingReview selectPendingByUserIdForUpdate(Long userId);

   @Select({"SELECT * FROM telegram_binding_review\nWHERE telegram_user_id = #{telegramUserId}\n  AND status = 0\n  AND del_flag = 0\nORDER BY id DESC\nLIMIT 1\nFOR UPDATE\n"})
   TelegramBindingReview selectPendingByTelegramUserIdForUpdate(String telegramUserId);
}
