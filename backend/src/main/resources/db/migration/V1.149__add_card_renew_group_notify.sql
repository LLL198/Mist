UPDATE `system_config`
SET `description` = '是否允许通过卡密注册；编辑时可分别开启注册成功、卡密续费成功发送 Telegram 群聊通知，群聊继承 Telegram 通知渠道里的积分群/频道 Chat ID（botChatGroupId），通知文案可在通知模板页面自定义。',
    `update_datetime` = NOW(),
    `update_user_name` = 'system'
WHERE `config_key` = 'card_register_enabled';

INSERT INTO `notify_template` (`template_code`, `template_name`, `channel_type`, `template_content`, `variable_comment`, `enabled`, `remark`, `create_datetime`, `update_datetime`, `create_user_name`, `update_user_name`, `update_user_id`, `create_user_id`, `del_flag`)
SELECT 'card_renew_success',
       '卡密续费文案-自定义',
       'common',
       '🔁 卡密续费\n\n👤 用户：${userName}\n🔑 授权码：${registerCode}\n✅ 续费时长：${renewDays}\n⏰ 到期时间：${expirationDate}\n🖥 服务器：${serverName}',
       'userName, registerCode, renewDays, expirationDate, serverName',
       1,
       '用户使用卡密续费成功发送到 Telegram 群聊，可在通知模板页面自定义文案',
       NOW(),
       NOW(),
       'system',
       'system',
       NULL,
       NULL,
       0
WHERE NOT EXISTS (
    SELECT 1 FROM `notify_template`
    WHERE `template_code` = 'card_renew_success'
      AND `channel_type` = 'common'
);
