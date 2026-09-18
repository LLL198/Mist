UPDATE `system_config` register_config
LEFT JOIN `system_config` max_count_config
       ON max_count_config.`config_key` = 'telegram_bot_register_max_count'
LEFT JOIN `system_config` used_count_config
       ON used_count_config.`config_key` = 'telegram_bot_register_used_count'
LEFT JOIN `system_config` default_days_config
       ON default_days_config.`config_key` = 'telegram_bot_register_default_days'
SET register_config.`config_value` = JSON_OBJECT(
        'defaultDays',
        CAST(
            CASE
                WHEN default_days_config.`config_value` REGEXP '^[0-9]+$' THEN default_days_config.`config_value`
                WHEN JSON_VALID(COALESCE(NULLIF(register_config.`config_value`, ''), '{}'))
                     AND JSON_UNQUOTE(JSON_EXTRACT(
                         IF(JSON_VALID(COALESCE(NULLIF(register_config.`config_value`, ''), '{}')),
                            COALESCE(NULLIF(register_config.`config_value`, ''), '{}'),
                            '{}'),
                         '$.defaultDays')) REGEXP '^[0-9]+$'
                    THEN JSON_UNQUOTE(JSON_EXTRACT(
                         IF(JSON_VALID(COALESCE(NULLIF(register_config.`config_value`, ''), '{}')),
                            COALESCE(NULLIF(register_config.`config_value`, ''), '{}'),
                            '{}'),
                         '$.defaultDays'))
                WHEN register_config.`config_value` REGEXP '^[0-9]+$' THEN register_config.`config_value`
                ELSE '0'
            END AS UNSIGNED
        ),
        'maxCount',
        CAST(
            CASE
                WHEN max_count_config.`config_value` REGEXP '^[0-9]+$' THEN max_count_config.`config_value`
                WHEN JSON_VALID(COALESCE(NULLIF(register_config.`config_value`, ''), '{}'))
                     AND JSON_UNQUOTE(JSON_EXTRACT(
                         IF(JSON_VALID(COALESCE(NULLIF(register_config.`config_value`, ''), '{}')),
                            COALESCE(NULLIF(register_config.`config_value`, ''), '{}'),
                            '{}'),
                         '$.maxCount')) REGEXP '^[0-9]+$'
                    THEN JSON_UNQUOTE(JSON_EXTRACT(
                         IF(JSON_VALID(COALESCE(NULLIF(register_config.`config_value`, ''), '{}')),
                            COALESCE(NULLIF(register_config.`config_value`, ''), '{}'),
                            '{}'),
                         '$.maxCount'))
                ELSE '0'
            END AS UNSIGNED
        ),
        'usedCount',
        CAST(
            CASE
                WHEN used_count_config.`config_value` REGEXP '^[0-9]+$' THEN used_count_config.`config_value`
                WHEN JSON_VALID(COALESCE(NULLIF(register_config.`config_value`, ''), '{}'))
                     AND JSON_UNQUOTE(JSON_EXTRACT(
                         IF(JSON_VALID(COALESCE(NULLIF(register_config.`config_value`, ''), '{}')),
                            COALESCE(NULLIF(register_config.`config_value`, ''), '{}'),
                            '{}'),
                         '$.usedCount')) REGEXP '^[0-9]+$'
                    THEN JSON_UNQUOTE(JSON_EXTRACT(
                         IF(JSON_VALID(COALESCE(NULLIF(register_config.`config_value`, ''), '{}')),
                            COALESCE(NULLIF(register_config.`config_value`, ''), '{}'),
                            '{}'),
                         '$.usedCount'))
                ELSE '0'
            END AS UNSIGNED
        )
    ),
    register_config.`description` = '是否允许用户通过 Telegram 私聊机器人注册 Emby 账号；配置值为 JSON，包含默认有效期天数和本轮注册名额',
    register_config.`is_update` = 1,
    register_config.`update_datetime` = NOW(),
    register_config.`update_user_name` = 'admin',
    register_config.`update_user_id` = 1
WHERE register_config.`config_key` = 'telegram_bot_register_enabled';

DELETE FROM `system_config`
WHERE `config_key` IN (
    'telegram_bot_register_max_count',
    'telegram_bot_register_used_count',
    'telegram_bot_register_default_days'
);
