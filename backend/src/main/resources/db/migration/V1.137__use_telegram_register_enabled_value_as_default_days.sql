UPDATE `system_config` telegram_register
LEFT JOIN `system_config` telegram_register_days
  ON telegram_register_days.`config_key` = 'telegram_bot_register_default_days'
SET telegram_register.`name` = '开启Telegram机器人注册',
    telegram_register.`config_value` = CASE
        WHEN telegram_register_days.`id` IS NOT NULL
             AND TRIM(COALESCE(telegram_register_days.`config_value`, '')) REGEXP '^[0-9]+$'
            THEN TRIM(telegram_register_days.`config_value`)
        WHEN TRIM(COALESCE(telegram_register.`config_value`, '')) REGEXP '^[0-9]+$'
            THEN TRIM(telegram_register.`config_value`)
        ELSE '0'
    END,
    telegram_register.`description` = '是否允许用户通过 Telegram 私聊机器人注册 Emby 账号；配置值为注册用户默认有效期天数，0 表示永不过期',
    telegram_register.`is_update` = 1,
    telegram_register.`update_datetime` = NOW(),
    telegram_register.`update_user_name` = 'admin',
    telegram_register.`update_user_id` = 1
WHERE telegram_register.`config_key` = 'telegram_bot_register_enabled';

DELETE FROM `system_config`
WHERE `config_key` = 'telegram_bot_register_default_days';
