UPDATE `system_config`
SET `description` = '是否允许通过邀请码注册；编辑时可开启成功后发送 Telegram 群聊通知，群聊继承 Telegram 通知渠道里的积分群/频道 Chat ID（botChatGroupId）。',
    `update_datetime` = NOW(),
    `update_user_name` = 'system'
WHERE `config_key` = 'invitation_register_enabled';

UPDATE `system_config`
SET `description` = '是否允许通过卡密注册；编辑时可分别开启注册成功、卡密续费成功发送 Telegram 群聊通知，群聊继承 Telegram 通知渠道里的积分群/频道 Chat ID（botChatGroupId）。',
    `update_datetime` = NOW(),
    `update_user_name` = 'system'
WHERE `config_key` = 'card_register_enabled';

UPDATE `notify_template` t
LEFT JOIN `notify_template` tg
    ON tg.`template_code` = 'card_register_success'
   AND tg.`channel_type` = 'telegram'
SET t.`channel_type` = CASE WHEN tg.`id` IS NULL THEN 'telegram' ELSE t.`channel_type` END,
    t.`template_name` = '授权码使用文案',
    t.`remark` = '授权码注册成功发送到 Telegram 群聊',
    t.`update_datetime` = NOW(),
    t.`update_user_name` = 'system'
WHERE t.`template_code` = 'card_register_success'
  AND t.`channel_type` = 'common';

UPDATE `notify_template`
SET `template_name` = '授权码使用文案',
    `remark` = '授权码注册成功发送到 Telegram 群聊',
    `update_datetime` = NOW(),
    `update_user_name` = 'system'
WHERE `template_code` = 'card_register_success'
  AND `channel_type` = 'telegram';

DELETE t FROM `notify_template` t
JOIN `notify_template` tg
    ON tg.`template_code` = t.`template_code`
   AND tg.`channel_type` = 'telegram'
WHERE t.`template_code` = 'card_register_success'
  AND t.`channel_type` = 'common';

UPDATE `notify_template` t
LEFT JOIN `notify_template` tg
    ON tg.`template_code` = 'invitation_register_success'
   AND tg.`channel_type` = 'telegram'
SET t.`channel_type` = CASE WHEN tg.`id` IS NULL THEN 'telegram' ELSE t.`channel_type` END,
    t.`template_name` = '邀请码使用文案',
    t.`remark` = '邀请码注册成功发送到 Telegram 群聊',
    t.`update_datetime` = NOW(),
    t.`update_user_name` = 'system'
WHERE t.`template_code` = 'invitation_register_success'
  AND t.`channel_type` = 'common';

UPDATE `notify_template`
SET `template_name` = '邀请码使用文案',
    `remark` = '邀请码注册成功发送到 Telegram 群聊',
    `update_datetime` = NOW(),
    `update_user_name` = 'system'
WHERE `template_code` = 'invitation_register_success'
  AND `channel_type` = 'telegram';

DELETE t FROM `notify_template` t
JOIN `notify_template` tg
    ON tg.`template_code` = t.`template_code`
   AND tg.`channel_type` = 'telegram'
WHERE t.`template_code` = 'invitation_register_success'
  AND t.`channel_type` = 'common';

UPDATE `notify_template` t
LEFT JOIN `notify_template` tg
    ON tg.`template_code` = 'card_renew_success'
   AND tg.`channel_type` = 'telegram'
SET t.`channel_type` = CASE WHEN tg.`id` IS NULL THEN 'telegram' ELSE t.`channel_type` END,
    t.`template_name` = '卡密续费文案',
    t.`remark` = '用户使用卡密续费成功发送到 Telegram 群聊',
    t.`update_datetime` = NOW(),
    t.`update_user_name` = 'system'
WHERE t.`template_code` = 'card_renew_success'
  AND t.`channel_type` = 'common';

UPDATE `notify_template`
SET `template_name` = '卡密续费文案',
    `remark` = '用户使用卡密续费成功发送到 Telegram 群聊',
    `update_datetime` = NOW(),
    `update_user_name` = 'system'
WHERE `template_code` = 'card_renew_success'
  AND `channel_type` = 'telegram';

DELETE t FROM `notify_template` t
JOIN `notify_template` tg
    ON tg.`template_code` = t.`template_code`
   AND tg.`channel_type` = 'telegram'
WHERE t.`template_code` = 'card_renew_success'
  AND t.`channel_type` = 'common';
