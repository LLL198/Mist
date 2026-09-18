INSERT INTO `system_config` (`name`, `config_key`, `config_value`, `is_enabled`, `description`, `is_update`, `create_datetime`, `update_datetime`, `create_user_name`, `update_user_name`, `update_user_id`, `create_user_id`, `del_flag`)
SELECT '自助注册服务器',
       'self_service_register_emby_info_id',
       COALESCE(
           (SELECT CASE
                       WHEN TRIM(sc.`config_value`) REGEXP '^[0-9]+$' THEN TRIM(sc.`config_value`)
                       ELSE NULL
                   END
            FROM `system_config` sc
            WHERE sc.`config_key` = 'registered_user'
            LIMIT 1),
           (SELECT CAST(ei.`id` AS CHAR)
            FROM `emby_info` ei
            WHERE ei.`enabled` = 1
              AND ei.`status` = 0
              AND ei.`del_flag` = 0
            ORDER BY ei.`id` ASC
            LIMIT 1),
           ''
       ),
       1,
       '页面注册和 Telegram 私聊注册创建账号使用的目标 Emby 服务器',
       1,
       NOW(),
       NOW(),
       'admin',
       'admin',
       1,
       1,
       0
WHERE NOT EXISTS (SELECT 1 FROM `system_config` WHERE `config_key` = 'self_service_register_emby_info_id');

UPDATE `system_config`
SET `name` = '开启页面注册',
    `config_value` = 'true',
    `description` = '是否在登录页开放普通账号注册入口',
    `is_update` = 1
WHERE `config_key` = 'registered_user';

UPDATE `notify_channel` nc
JOIN `system_config` sc ON sc.`config_key` = 'telegram_register_enabled'
SET nc.`params` = JSON_SET(
        IF(JSON_VALID(nc.`params`), nc.`params`, JSON_OBJECT()),
        '$.botName',
        TRIM(LEADING '@' FROM TRIM(sc.`config_value`))
    )
WHERE nc.`icon_type` = 'telegram'
  AND nc.`del_flag` = 0
  AND TRIM(sc.`config_value`) <> ''
  AND LOWER(TRIM(sc.`config_value`)) NOT IN ('0', '1', 'true', 'false', 'null', 'undefined', 'off', 'on', '绑定机器人名称')
  AND (
      JSON_UNQUOTE(JSON_EXTRACT(IF(JSON_VALID(nc.`params`), nc.`params`, JSON_OBJECT()), '$.botName')) IS NULL
      OR JSON_UNQUOTE(JSON_EXTRACT(IF(JSON_VALID(nc.`params`), nc.`params`, JSON_OBJECT()), '$.botName')) = ''
  );

UPDATE `system_config`
SET `name` = '已废弃：Telegram机器人用户名',
    `description` = '已迁移到通知渠道 Telegram 的 botName，请在通知渠道中维护机器人用户名',
    `is_enabled` = 0
WHERE `config_key` = 'telegram_register_enabled';
