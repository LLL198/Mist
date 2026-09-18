UPDATE `system_config`
SET `description` = '是否允许通过邀请码注册；编辑时可开启成功后发送 Telegram 群聊通知，群聊继承 Telegram 通知渠道里的积分群/频道 Chat ID（botChatGroupId）。',
    `update_datetime` = NOW(),
    `update_user_name` = 'system'
WHERE `config_key` = 'invitation_register_enabled';

UPDATE `system_config`
SET `description` = '是否允许通过卡密注册；编辑时可开启成功后发送 Telegram 群聊通知，群聊继承 Telegram 通知渠道里的积分群/频道 Chat ID（botChatGroupId）。',
    `update_datetime` = NOW(),
    `update_user_name` = 'system'
WHERE `config_key` = 'card_register_enabled';

INSERT INTO `notify_template` (`template_code`, `template_name`, `channel_type`, `template_content`, `variable_comment`, `enabled`, `remark`, `create_datetime`, `update_datetime`, `create_user_name`, `update_user_name`, `update_user_id`, `create_user_id`, `del_flag`)
SELECT 'card_register_success',
       '卡密注册成功-通用',
       'common',
       '🎟️ 授权码使用\n\n👤 用户：${userName}\n🔑 授权码：${registerCode}\n✅ 成功注册：${registerDays}\n🖥 服务器：${serverName}',
       'userName, registerCode, registerDays, serverName',
       1,
       '卡密注册成功发送到 Telegram 群聊',
       NOW(),
       NOW(),
       'system',
       'system',
       NULL,
       NULL,
       0
WHERE NOT EXISTS (
    SELECT 1 FROM `notify_template`
    WHERE `template_code` = 'card_register_success'
      AND `channel_type` = 'common'
);

INSERT INTO `notify_template` (`template_code`, `template_name`, `channel_type`, `template_content`, `variable_comment`, `enabled`, `remark`, `create_datetime`, `update_datetime`, `create_user_name`, `update_user_name`, `update_user_id`, `create_user_id`, `del_flag`)
SELECT 'invitation_register_success',
       '邀请码注册成功-通用',
       'common',
       '💌 邀请码使用\n\n👤 用户：${userName}\n🎫 邀请码：${invitationCode}\n✅ 成功注册：${registerDays}\n🖥 服务器：${serverName}',
       'userName, invitationCode, registerDays, serverName',
       1,
       '邀请码注册成功发送到 Telegram 群聊',
       NOW(),
       NOW(),
       'system',
       'system',
       NULL,
       NULL,
       0
WHERE NOT EXISTS (
    SELECT 1 FROM `notify_template`
    WHERE `template_code` = 'invitation_register_success'
      AND `channel_type` = 'common'
);
