UPDATE `system_config`
SET `description` = '是否允许通过邀请码注册；编辑时可开启成功后发送 Telegram 群聊通知，群聊继承 Telegram 通知渠道里的积分群/频道 Chat ID（botChatGroupId），通知文案可在通知模板页面自定义。',
    `update_datetime` = NOW(),
    `update_user_name` = 'system'
WHERE `config_key` = 'invitation_register_enabled';

UPDATE `system_config`
SET `description` = '是否允许通过卡密注册；编辑时可开启成功后发送 Telegram 群聊通知，群聊继承 Telegram 通知渠道里的积分群/频道 Chat ID（botChatGroupId），通知文案可在通知模板页面自定义。',
    `update_datetime` = NOW(),
    `update_user_name` = 'system'
WHERE `config_key` = 'card_register_enabled';

UPDATE `notify_template`
SET `template_name` = '授权码使用文案-自定义',
    `variable_comment` = 'userName, registerCode, registerDays, serverName',
    `remark` = '授权码注册成功发送到 Telegram 群聊，可在通知模板页面自定义文案',
    `update_datetime` = NOW(),
    `update_user_name` = 'system'
WHERE `template_code` = 'card_register_success'
  AND `channel_type` = 'common';

UPDATE `notify_template`
SET `template_name` = '邀请码使用文案-自定义',
    `variable_comment` = 'userName, invitationCode, registerDays, serverName',
    `remark` = '邀请码注册成功发送到 Telegram 群聊，可在通知模板页面自定义文案',
    `update_datetime` = NOW(),
    `update_user_name` = 'system'
WHERE `template_code` = 'invitation_register_success'
  AND `channel_type` = 'common';
