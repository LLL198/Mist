INSERT INTO `system_config`
(`name`, `config_key`, `config_value`, `is_enabled`, `description`, `is_update`,
 `create_datetime`, `update_datetime`, `create_user_name`, `update_user_name`,
 `update_user_id`, `create_user_id`, `del_flag`)
SELECT
    'TG 绑定审核配置',
    'telegram_binding_review_config',
    '{"bindReviewEnabled":true,"unbindReviewEnabled":true,"rebindReviewEnabled":true,"reviewNotifyEnabled":true}',
    1,
    '统一设置 Telegram 绑定、解绑、换绑审核及待审核消息通知。',
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
    WHERE `config_key` = 'telegram_binding_review_config'
      AND `del_flag` = 0
);

UPDATE `system_config`
SET `name` = 'TG 绑定审核配置',
    `description` = '统一设置 Telegram 绑定、解绑、换绑审核及待审核消息通知。',
    `update_datetime` = NOW()
WHERE `config_key` = 'telegram_binding_review_config'
  AND `del_flag` = 0;

INSERT INTO `system_config`
(`name`, `config_key`, `config_value`, `is_enabled`, `description`, `is_update`,
 `create_datetime`, `update_datetime`, `create_user_name`, `update_user_name`,
 `update_user_id`, `create_user_id`, `del_flag`)
SELECT
    'TG 绑定成功通知',
    'telegram_binding_success_notify_config',
    '{"enabled":true,"bindSuccessNotifyTargets":["bot","group"],"unbindSuccessNotifyTargets":["bot","group"],"rebindSuccessNotifyTargets":["bot","group"]}',
    1,
    '分别设置 Telegram 绑定、解绑、换绑成功消息发送到机器人或群聊。',
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
    WHERE `config_key` = 'telegram_binding_success_notify_config'
      AND `del_flag` = 0
);

UPDATE `system_config`
SET `name` = 'TG 绑定成功通知',
    `description` = '分别设置 Telegram 绑定、解绑、换绑成功消息发送到机器人或群聊。',
    `update_datetime` = NOW()
WHERE `config_key` = 'telegram_binding_success_notify_config'
  AND `del_flag` = 0;

UPDATE `system_config`
SET `is_enabled` = 0,
    `del_flag` = 1,
    `update_datetime` = NOW()
WHERE `config_key` IN (
    'telegram_binding_review_enabled',
    'telegram_bind_review_enabled',
    'telegram_unbind_review_enabled',
    'telegram_rebind_review_enabled',
    'telegram_binding_review_notify_enabled'
)
  AND `del_flag` = 0;
