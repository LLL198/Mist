ALTER TABLE `telegram_binding_review`
    ADD COLUMN `old_telegram_user_id` VARCHAR(64) DEFAULT NULL
        COMMENT '换绑前 Telegram 用户 ID' AFTER `telegram_avatar`,
    ADD COLUMN `old_telegram_username` VARCHAR(255) DEFAULT NULL
        COMMENT '换绑前 Telegram 用户名' AFTER `old_telegram_user_id`,
    ADD COLUMN `old_telegram_avatar` VARCHAR(1024) DEFAULT NULL
        COMMENT '换绑前 Telegram 头像' AFTER `old_telegram_username`;

INSERT INTO `system_config`
(`name`, `config_key`, `config_value`, `is_enabled`, `description`, `is_update`,
 `create_datetime`, `update_datetime`, `create_user_name`, `update_user_name`,
 `update_user_id`, `create_user_id`, `del_flag`)
SELECT
    'TG换绑审核',
    'telegram_rebind_review_enabled',
    'true',
    0,
    '开启后，用户更换已绑定的 Telegram 需要管理员审批；默认关闭。',
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
    WHERE `config_key` = 'telegram_rebind_review_enabled'
);

UPDATE `system_config`
SET `name` = 'TG绑定解绑换绑审核通知',
    `description` = '开启后，将待审核的绑定、解绑或换绑申请发送给 Telegram 管理员；默认关闭。',
    `update_datetime` = NOW()
WHERE `config_key` = 'telegram_binding_review_notify_enabled';
