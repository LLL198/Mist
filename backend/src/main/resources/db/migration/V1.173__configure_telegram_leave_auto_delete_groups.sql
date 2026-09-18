INSERT INTO `system_config`
(`name`, `config_key`, `config_value`, `is_enabled`, `description`, `is_update`,
 `create_datetime`, `update_datetime`, `create_user_name`, `update_user_name`,
 `update_user_id`, `create_user_id`, `del_flag`)
SELECT
    'TG退群自动删号',
    'telegram_leave_auto_delete_enabled',
    '{"pointsGroupEnabled":true,"libraryNotifyGroupEnabled":false}',
    0,
    '总开关默认关闭；JSON 可分别设置积分群聊和入库通知群的退群删号，默认仅开启积分群聊。',
    1,
    NOW(),
    NOW(),
    'admin',
    'admin',
    1,
    1,
    0
WHERE NOT EXISTS (
    SELECT 1 FROM `system_config`
    WHERE `config_key` = 'telegram_leave_auto_delete_enabled'
      AND `del_flag` = 0
);

UPDATE `system_config`
SET `name` = 'TG退群自动删号',
    `config_value` = '{"pointsGroupEnabled":true,"libraryNotifyGroupEnabled":false}',
    `description` = '总开关默认关闭；JSON 可分别设置积分群聊和入库通知群的退群删号，默认仅开启积分群聊。',
    `is_update` = 1,
    `update_datetime` = NOW()
WHERE `config_key` = 'telegram_leave_auto_delete_enabled'
  AND `del_flag` = 0;
