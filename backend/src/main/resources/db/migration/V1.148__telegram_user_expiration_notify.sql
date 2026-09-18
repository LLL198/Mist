INSERT INTO `system_config` (`name`, `config_key`, `config_value`, `is_enabled`, `description`, `is_update`, `create_datetime`, `update_datetime`, `create_user_name`, `update_user_name`, `update_user_id`, `create_user_id`, `del_flag`)
SELECT 'Telegram到期提醒直发用户',
       'telegram_user_expiration_notify_enabled',
       'true',
       1,
       '开启后，用户到期提醒、到期禁用和到期删除的 Telegram 通知会优先私聊发送给已绑定 Telegram 的用户；未绑定或发送失败时回退到 Telegram 管理员 ID。关闭后按原逻辑发送给管理员 ID。',
       1,
       NOW(),
       NOW(),
       'admin',
       'admin',
       1,
       1,
       0
WHERE NOT EXISTS (SELECT 1 FROM `system_config` WHERE `config_key` = 'telegram_user_expiration_notify_enabled');

INSERT INTO `notify_template` (`template_code`, `template_name`, `channel_type`, `template_content`, `variable_comment`, `enabled`, `remark`, `create_datetime`, `update_datetime`, `create_user_name`, `update_user_name`, `update_user_id`, `create_user_id`, `del_flag`)
SELECT 'user_expiration',
       '用户过期提醒-Telegram',
       'telegram',
       '⏳ Emby 账号即将到期\n\n👤 用户：${userName}\n📅 到期时间：${expirationDate}\n⏱ 剩余时间：${timeLeft}\n🖥️ 服务器：${serverName}\n\n💡 到期后账号可能会被停用，请及时续费。',
       'userName, expirationDate, timeLeft, serverName',
       1,
       '用户过期提醒',
       NOW(),
       NOW(),
       'system',
       'system',
       NULL,
       NULL,
       0
WHERE NOT EXISTS (SELECT 1 FROM `notify_template` WHERE `template_code` = 'user_expiration' AND `channel_type` = 'telegram');

INSERT INTO `notify_template` (`template_code`, `template_name`, `channel_type`, `template_content`, `variable_comment`, `enabled`, `remark`, `create_datetime`, `update_datetime`, `create_user_name`, `update_user_name`, `update_user_id`, `create_user_id`, `del_flag`)
SELECT 'user_disabled',
       '用户禁用-Telegram',
       'telegram',
       '⚠️ Emby 账号已停用\n\n👤 用户：${userName}\n🚫 原因：${reason}\n🖥️ 服务器：${serverName}\n\n💡 如需继续使用，请联系管理员处理。',
       'userName, reason, serverName',
       1,
       '用户禁用通知',
       NOW(),
       NOW(),
       'system',
       'system',
       NULL,
       NULL,
       0
WHERE NOT EXISTS (SELECT 1 FROM `notify_template` WHERE `template_code` = 'user_disabled' AND `channel_type` = 'telegram');

INSERT INTO `notify_template` (`template_code`, `template_name`, `channel_type`, `template_content`, `variable_comment`, `enabled`, `remark`, `create_datetime`, `update_datetime`, `create_user_name`, `update_user_name`, `update_user_id`, `create_user_id`, `del_flag`)
SELECT 'user_deleted',
       '用户删除-Telegram',
       'telegram',
       '🗑️ Emby 账号已删除\n\n👤 用户：${userName}\n🚫 原因：${reason}\n🖥️ 服务器：${serverName}\n\n💡 如有疑问，请联系管理员。',
       'userName, reason, serverName',
       1,
       '用户删除通知',
       NOW(),
       NOW(),
       'system',
       'system',
       NULL,
       NULL,
       0
WHERE NOT EXISTS (SELECT 1 FROM `notify_template` WHERE `template_code` = 'user_deleted' AND `channel_type` = 'telegram');

UPDATE `notify_template`
SET `template_content` = '⏳ Emby 账号即将到期\n\n👤 用户：${userName}\n📅 到期时间：${expirationDate}\n⏱ 剩余时间：${timeLeft}\n🖥️ 服务器：${serverName}\n\n💡 到期后账号可能会被停用，请及时续费。',
    `variable_comment` = 'userName, expirationDate, timeLeft, serverName',
    `update_datetime` = NOW(),
    `update_user_name` = 'system'
WHERE `template_code` = 'user_expiration'
  AND `channel_type` IN ('common', 'telegram', 'wechat', 'wechatBot', 'dingding', 'messagepush');

UPDATE `notify_template`
SET `template_content` = '⚠️ Emby 账号已停用\n\n👤 用户：${userName}\n🚫 原因：${reason}\n🖥️ 服务器：${serverName}\n\n💡 如需继续使用，请联系管理员处理。',
    `variable_comment` = 'userName, reason, serverName',
    `update_datetime` = NOW(),
    `update_user_name` = 'system'
WHERE `template_code` = 'user_disabled'
  AND `channel_type` IN ('common', 'telegram', 'wechat', 'wechatBot', 'dingding', 'messagepush');

UPDATE `notify_template`
SET `template_content` = '🗑️ Emby 账号已删除\n\n👤 用户：${userName}\n🚫 原因：${reason}\n🖥️ 服务器：${serverName}\n\n💡 如有疑问，请联系管理员。',
    `variable_comment` = 'userName, reason, serverName',
    `update_datetime` = NOW(),
    `update_user_name` = 'system'
WHERE `template_code` = 'user_deleted'
  AND `channel_type` IN ('common', 'telegram', 'wechat', 'wechatBot', 'dingding', 'messagepush');
