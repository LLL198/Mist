-- 将入库通知的 telegram 模板简介改为引用块样式（默认折叠显示）
UPDATE notify_template
SET template_content = '名称：${name}\n\n${serverUrlBlock}${tvInfoBlock}${displayTitleBlock}${genresBlock}🗂 类型：${typeTag}\n${sizeBlock}<blockquote expandable>📖 简介\n${overview}</blockquote>',
    update_datetime = NOW()
WHERE template_code = 'media_photo_detail'
  AND channel_type = 'telegram';

UPDATE notify_template
SET template_content = '${overview}',
    update_datetime = NOW()
WHERE template_code = 'media_photo_message'
  AND channel_type = 'telegram';

UPDATE notify_template
SET template_content = '${name}
👤 用户：${playUser}
📺 标题：${playTitle}
📍 用户归属地：${userLocation}
⏰ 播放时间：${playTime}
⏯️ 播放位置：${playPosition}
💻 客户端：${clientInfo}
🌐 服务器地址：${serverUrl}',
    update_datetime = NOW()
WHERE template_code = 'wechat_playback_start'
  AND channel_type = 'telegram';

UPDATE notify_template
SET template_content = '${name}
👤 用户：${playUser}
📺 标题：${playTitle}
📍 用户归属地：${userLocation}
⏰ 播放时间：${playTime}
⏯️ 播放位置：${playPosition}
💻 客户端：${clientInfo}
🌐 服务器地址：${serverUrl}',
    update_datetime = NOW()
WHERE template_code = 'wechat_playback_stop'
  AND channel_type = 'telegram';
