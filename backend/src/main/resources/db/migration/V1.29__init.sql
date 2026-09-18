-- 播放排行榜发送到机器人配置
-- is_enabled = 1 启用：发送到机器人私聊
-- is_enabled = 0 停用：发送到群聊/频道
INSERT INTO `system_config` (`name`, `config_key`, `config_value`, `is_enabled`, `description`, `is_update`, `create_datetime`, `update_datetime`, `create_user_name`, `update_user_name`, `update_user_id`, `create_user_id`, `del_flag`)
VALUES ('排行榜发送到机器人', 'ranking_send_to_bot', 'true', 1, '播放排行榜发送到机器人（启用）还是群聊（停用），默认启用发送到机器人', 0, NOW(), NOW(), 'admin', 'admin', 1, 1, 0);
