package com.una.embyhub.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.una.embyhub.model.entity.UserOauthBinding;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface UserOauthBindingMapper extends BaseMapper<UserOauthBinding> {
   @Select({"SELECT DISTINCT binding.provider_user_id\nFROM user_oauth_binding binding\nINNER JOIN emby_user user_data ON user_data.id = binding.user_id\nWHERE binding.provider = 'telegram'\n  AND binding.del_flag = 0\n  AND user_data.del_flag = 0\n  AND user_data.user_status = 0\n  AND user_data.is_admin = 1\n  AND binding.provider_user_id IS NOT NULL\n  AND binding.provider_user_id <> ''\n"})
   List<String> selectActiveTelegramAdminIds();

   @Select({"SELECT user_data.id\nFROM user_oauth_binding binding\nINNER JOIN emby_user user_data ON user_data.id = binding.user_id\nWHERE binding.provider = 'telegram'\n  AND binding.del_flag = 0\n  AND user_data.del_flag = 0\n  AND user_data.user_status = 0\n  AND user_data.is_admin = 1\n  AND binding.provider_user_id = #{telegramUserId}\nLIMIT 1\n"})
   Long selectActiveTelegramAdminUserId(@Param("telegramUserId") String telegramUserId);

   @Update({"UPDATE user_oauth_binding\nSET provider_user_id = #{binding.providerUserId},\n    provider_username = #{binding.providerUsername,jdbcType=VARCHAR},\n    provider_avatar = #{binding.providerAvatar,jdbcType=VARCHAR},\n    update_datetime = #{binding.updateDatetime},\n    update_user_id = #{binding.updateUserId},\n    update_user_name = #{binding.updateUserName}\nWHERE id = #{binding.id}\n  AND user_id = #{binding.userId}\n  AND provider = 'telegram'\n  AND provider_user_id = #{expectedTelegramUserId}\n  AND del_flag = 0\n"})
   int replaceTelegramBinding(@Param("binding") UserOauthBinding binding, @Param("expectedTelegramUserId") String expectedTelegramUserId);
}
