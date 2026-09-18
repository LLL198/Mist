INSERT INTO `notify_template` (`template_code`, `template_name`, `channel_type`, `template_content`, `variable_comment`, `enabled`, `remark`, `create_datetime`, `update_datetime`, `create_user_name`, `update_user_name`, `update_user_id`, `create_user_id`, `del_flag`)
SELECT 'wechat_playback_start', '播放开始-消息推送助手', 'messagepush',
       '${name}\n👤 用户：${playUser}\n📺 标题：${playTitle}\n📍 用户归属地：${userLocation}\n⏰ 播放时间：${playTime}\n⏯️ 播放位置：${playPosition}\n💻 客户端：${clientInfo}',
       'playUser, playTitle, userLocation, playTime, playPosition, clientInfo', 1, '默认播放开始模板',
       NOW(), NOW(), 'system', 'system', NULL, NULL, 0
WHERE NOT EXISTS (SELECT 1 FROM notify_template WHERE template_code = 'wechat_playback_start' AND channel_type = 'messagepush');

INSERT INTO `notify_template` (`template_code`, `template_name`, `channel_type`, `template_content`, `variable_comment`, `enabled`, `remark`, `create_datetime`, `update_datetime`, `create_user_name`, `update_user_name`, `update_user_id`, `create_user_id`, `del_flag`)
SELECT 'wechat_playback_stop', '播放停止-消息推送助手', 'messagepush',
       '${name}\n👤 用户：${playUser}\n📺 标题：${playTitle}\n📍 用户归属地：${userLocation}\n⏰ 播放时间：${playTime}\n⏯️ 播放位置：${playPosition}\n💻 客户端：${clientInfo}',
       'playUser, playTitle, userLocation, playTime, playPosition, clientInfo', 1, '默认播放停止模板',
       NOW(), NOW(), 'system', 'system', NULL, NULL, 0
WHERE NOT EXISTS (SELECT 1 FROM notify_template WHERE template_code = 'wechat_playback_stop' AND channel_type = 'messagepush');
