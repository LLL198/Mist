INSERT INTO `system_config` (`name`, `config_key`, `config_value`, `is_enabled`, `description`, `is_update`, `create_datetime`, `update_datetime`, `create_user_name`, `update_user_name`, `update_user_id`, `create_user_id`, `del_flag`)
SELECT 'TG退群自动删号', 'telegram_leave_auto_delete_enabled', 'true', 0, '开启后，Telegram 配置群内用户退群或被踢时，如果已绑定 Emby 账号，将自动解绑并删除对应 Emby 账号；默认关闭。', 1, NOW(), NOW(), 'admin', 'admin', 1, 1, 0
WHERE NOT EXISTS (SELECT 1 FROM `system_config` WHERE `config_key` = 'telegram_leave_auto_delete_enabled');
