INSERT INTO `system_config`
(`name`, `config_key`, `config_value`, `is_enabled`, `description`, `is_update`,
 `create_datetime`, `update_datetime`, `create_user_name`, `update_user_name`,
 `update_user_id`, `create_user_id`, `del_flag`)
SELECT
    'TG绑定需加入积分群聊',
    'telegram_binding_points_group_required',
    'true',
    1,
    '开启后，Telegram 用户必须已加入积分群/频道才能绑定或换绑 Emby 账号；默认开启。',
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
    WHERE `config_key` = 'telegram_binding_points_group_required'
      AND `del_flag` = 0
);

UPDATE `system_config`
SET `name` = 'TG绑定需加入积分群聊',
    `config_value` = 'true',
    `description` = '开启后，Telegram 用户必须已加入积分群/频道才能绑定或换绑 Emby 账号；默认开启。',
    `is_update` = 1,
    `update_datetime` = NOW()
WHERE `config_key` = 'telegram_binding_points_group_required'
  AND `del_flag` = 0;
