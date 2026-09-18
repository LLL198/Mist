INSERT INTO `system_config` (`name`, `config_key`, `config_value`, `is_enabled`, `description`, `is_update`, `create_datetime`, `update_datetime`, `create_user_name`, `update_user_name`, `update_user_id`, `create_user_id`, `del_flag`)
SELECT 'Telegram机器人注册默认有效期', 'telegram_bot_register_default_days', '0', 1, 'Telegram 私聊注册创建账号的默认有效期天数，0 表示永不过期', 1, NOW(), NOW(), 'admin', 'admin', 1, 1, 0
WHERE NOT EXISTS (SELECT 1 FROM `system_config` WHERE `config_key` = 'telegram_bot_register_default_days');
