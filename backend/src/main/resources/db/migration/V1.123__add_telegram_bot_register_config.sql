INSERT INTO `system_config` (`name`, `config_key`, `config_value`, `is_enabled`, `description`, `is_update`, `create_datetime`, `update_datetime`, `create_user_name`, `update_user_name`, `update_user_id`, `create_user_id`, `del_flag`)
SELECT '开启Telegram机器人注册', 'telegram_bot_register_enabled', 'true', 0, '是否允许用户通过 Telegram 私聊机器人注册 Emby 账号', 1, NOW(), NOW(), 'admin', 'admin', 1, 1, 0
WHERE NOT EXISTS (SELECT 1 FROM `system_config` WHERE `config_key` = 'telegram_bot_register_enabled');

INSERT INTO `system_config` (`name`, `config_key`, `config_value`, `is_enabled`, `description`, `is_update`, `create_datetime`, `update_datetime`, `create_user_name`, `update_user_name`, `update_user_id`, `create_user_id`, `del_flag`)
SELECT 'Telegram机器人注册名额', 'telegram_bot_register_max_count', '0', 1, '每次开启 Telegram 机器人注册允许创建的最大账号数量，0 表示不开放', 1, NOW(), NOW(), 'admin', 'admin', 1, 1, 0
WHERE NOT EXISTS (SELECT 1 FROM `system_config` WHERE `config_key` = 'telegram_bot_register_max_count');

INSERT INTO `system_config` (`name`, `config_key`, `config_value`, `is_enabled`, `description`, `is_update`, `create_datetime`, `update_datetime`, `create_user_name`, `update_user_name`, `update_user_id`, `create_user_id`, `del_flag`)
SELECT 'Telegram机器人已注册数量', 'telegram_bot_register_used_count', '0', 1, '当前这次开启 Telegram 机器人注册后已经创建的账号数量', 0, NOW(), NOW(), 'admin', 'admin', 1, 1, 0
WHERE NOT EXISTS (SELECT 1 FROM `system_config` WHERE `config_key` = 'telegram_bot_register_used_count');
