UPDATE `system_config`
SET `name` = 'TG绑定审核',
    `config_key` = 'telegram_bind_review_enabled',
    `description` = '开启后，用户主动绑定 Telegram 需要管理员审批；默认关闭。',
    `update_datetime` = NOW()
WHERE `config_key` = 'telegram_binding_review_enabled';

UPDATE `system_config`
SET `name` = 'TG绑定解绑审核通知',
    `description` = '开启后，将待审核的绑定或解绑申请发送给 Telegram 管理员；默认关闭。',
    `update_datetime` = NOW()
WHERE `config_key` = 'telegram_binding_review_notify_enabled';

INSERT INTO `system_config`
(`name`, `config_key`, `config_value`, `is_enabled`, `description`, `is_update`,
 `create_datetime`, `update_datetime`, `create_user_name`, `update_user_name`,
 `update_user_id`, `create_user_id`, `del_flag`)
SELECT
    'TG绑定审核',
    'telegram_bind_review_enabled',
    'true',
    0,
    '开启后，用户主动绑定 Telegram 需要管理员审批；默认关闭。',
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
    WHERE `config_key` = 'telegram_bind_review_enabled'
);

INSERT INTO `system_config`
(`name`, `config_key`, `config_value`, `is_enabled`, `description`, `is_update`,
 `create_datetime`, `update_datetime`, `create_user_name`, `update_user_name`,
 `update_user_id`, `create_user_id`, `del_flag`)
SELECT
    'TG解绑审核',
    'telegram_unbind_review_enabled',
    'true',
    0,
    '开启后，用户主动解绑 Telegram 需要管理员审批；默认关闭。',
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
    WHERE `config_key` = 'telegram_unbind_review_enabled'
);
