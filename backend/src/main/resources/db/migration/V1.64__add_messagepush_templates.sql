INSERT INTO `notify_template` (`template_code`, `template_name`, `channel_type`, `template_content`, `variable_comment`, `enabled`, `remark`, `create_datetime`, `update_datetime`, `create_user_name`, `update_user_name`, `update_user_id`, `create_user_id`, `del_flag`)
SELECT 'media_photo_detail', '资源详情-消息推送助手', 'messagepush',
       '名称：${name}\n\n${tvInfoBlock}${displayTitleBlock}${genresBlock}🗂 类型：${typeTag}\n${sizeBlock}简介：${overview}',
       'name, overview, tvInfoBlock, displayTitleBlock, genresBlock, typeTag, sizeBlock', 1, '默认资源详情模板',
       NOW(), NOW(), 'system', 'system', NULL, NULL, 0
WHERE NOT EXISTS (SELECT 1 FROM notify_template WHERE template_code = 'media_photo_detail' AND channel_type = 'messagepush');

INSERT INTO `notify_template` (`template_code`, `template_name`, `channel_type`, `template_content`, `variable_comment`, `enabled`, `remark`, `create_datetime`, `update_datetime`, `create_user_name`, `update_user_name`, `update_user_id`, `create_user_id`, `del_flag`)
SELECT 'media_photo_message', '图文消息-消息推送助手', 'messagepush',
       '${overview}',
       'overview', 1, '默认图文消息模板',
       NOW(), NOW(), 'system', 'system', NULL, NULL, 0
WHERE NOT EXISTS (SELECT 1 FROM notify_template WHERE template_code = 'media_photo_message' AND channel_type = 'messagepush');

INSERT INTO `notify_template` (`template_code`, `template_name`, `channel_type`, `template_content`, `variable_comment`, `enabled`, `remark`, `create_datetime`, `update_datetime`, `create_user_name`, `update_user_name`, `update_user_id`, `create_user_id`, `del_flag`)
SELECT 'media_text_message', '文本消息-消息推送助手', 'messagepush',
       '${overview}',
       'overview', 1, '默认文本消息模板',
       NOW(), NOW(), 'system', 'system', NULL, NULL, 0
WHERE NOT EXISTS (SELECT 1 FROM notify_template WHERE template_code = 'media_text_message' AND channel_type = 'messagepush');

INSERT INTO `notify_template` (`template_code`, `template_name`, `channel_type`, `template_content`, `variable_comment`, `enabled`, `remark`, `create_datetime`, `update_datetime`, `create_user_name`, `update_user_name`, `update_user_id`, `create_user_id`, `del_flag`)
SELECT 'auth_failed', '认证失败-消息推送助手', 'messagepush',
       '🔐 登录失败\n\n👤 用户：${userName}\n🕒 时间：${loginTime}\n🖥 客户端：${client}\n📱 设备：${device}\n🌍 IP：${ipAddress}',
       'userName, loginTime, client, device, ipAddress', 1, '认证失败通知',
       NOW(), NOW(), 'system', 'system', NULL, NULL, 0
WHERE NOT EXISTS (SELECT 1 FROM notify_template WHERE template_code = 'auth_failed' AND channel_type = 'messagepush');

INSERT INTO `notify_template` (`template_code`, `template_name`, `channel_type`, `template_content`, `variable_comment`, `enabled`, `remark`, `create_datetime`, `update_datetime`, `create_user_name`, `update_user_name`, `update_user_id`, `create_user_id`, `del_flag`)
SELECT 'auth_success', '认证成功-消息推送助手', 'messagepush',
       '🔓 登录成功\n\n👤 用户：${userName}\n🕒 时间：${loginTime}\n🖥 客户端：${client}\n📱 设备：${device}\n🌍 IP：${ipAddress}',
       'userName, loginTime, client, device, ipAddress', 1, '认证成功通知',
       NOW(), NOW(), 'system', 'system', NULL, NULL, 0
WHERE NOT EXISTS (SELECT 1 FROM notify_template WHERE template_code = 'auth_success' AND channel_type = 'messagepush');
