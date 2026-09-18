-- Ensure WeCom login notifications include client and device details
UPDATE notify_template
SET template_content = '🔐 登录失败\\n\\n👤 用户：${userName}\\n🕒 时间：${loginTime}\\n🖥 客户端：${client}\\n📱 设备：${device}\\n🌍 IP：${ipAddress}\\n\\n🌐 服务器：${serverUrl}',
    variable_comment = 'userName, loginTime, client, device, ipAddress, serverUrl',
    update_datetime = NOW()
WHERE template_code = 'auth_failed'
  AND channel_type = 'wechat';

UPDATE notify_template
SET template_content = '🔓 登录成功\\n\\n👤 用户：${userName}\\n🕒 时间：${loginTime}\\n🖥 客户端：${client}\\n📱 设备：${device}\\n🌍 IP：${ipAddress}\\n\\n🌐 服务器：${serverUrl}',
    variable_comment = 'userName, loginTime, client, device, ipAddress, serverUrl',
    update_datetime = NOW()
WHERE template_code = 'auth_success'
  AND channel_type = 'wechat';

-- Add or update DingTalk login templates with client and device information
INSERT INTO `notify_template` (`id`, `template_code`, `template_name`, `channel_type`, `template_content`, `variable_comment`, `enabled`, `remark`, `create_datetime`, `update_datetime`, `create_user_name`, `update_user_name`, `update_user_id`, `create_user_id`, `del_flag`)
VALUES (89, 'auth_failed', '认证失败-钉钉', 'dingding', '🔐 登录失败\\n\\n👤 用户：${userName}\\n🕒 时间：${loginTime}\\n🖥 客户端：${client}\\n📱 设备：${device}\\n🌍 IP：${ipAddress}\\n\\n🌐 服务器：${serverUrl}', 'userName, loginTime, client, device, ipAddress, serverUrl', 1, '认证失败通知', NOW(), NOW(), 'system', 'system', NULL, NULL, 0)
ON DUPLICATE KEY UPDATE template_content = VALUES(template_content),
                        variable_comment = VALUES(variable_comment),
                        update_datetime = VALUES(update_datetime),
                        update_user_name = VALUES(update_user_name),
                        update_user_id = VALUES(update_user_id),
                        del_flag = VALUES(del_flag);

INSERT INTO `notify_template` (`id`, `template_code`, `template_name`, `channel_type`, `template_content`, `variable_comment`, `enabled`, `remark`, `create_datetime`, `update_datetime`, `create_user_name`, `update_user_name`, `update_user_id`, `create_user_id`, `del_flag`)
VALUES (90, 'auth_success', '认证成功-钉钉', 'dingding', '🔓 登录成功\\n\\n👤 用户：${userName}\\n🕒 时间：${loginTime}\\n🖥 客户端：${client}\\n📱 设备：${device}\\n🌍 IP：${ipAddress}\\n\\n🌐 服务器：${serverUrl}', 'userName, loginTime, client, device, ipAddress, serverUrl', 1, '认证成功通知', NOW(), NOW(), 'system', 'system', NULL, NULL, 0)
ON DUPLICATE KEY UPDATE template_content = VALUES(template_content),
                        variable_comment = VALUES(variable_comment),
                        update_datetime = VALUES(update_datetime),
                        update_user_name = VALUES(update_user_name),
                        update_user_id = VALUES(update_user_id),
                        del_flag = VALUES(del_flag);
