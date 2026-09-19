-- Mist Telegram notification refresh.
-- Command names, callback payloads and template variables remain compatible;
-- only the user-facing Telegram presentation is refreshed.

UPDATE `notify_template`
SET `template_content` = '*🌁 Mist · 入库完成*

🎬 片名：${name}

${serverUrlBlock}${tvInfoBlock}${displayTitleBlock}${genresBlock}🗂 类型：${typeTag}
${sizeBlock}📝 简介：${overview} *',
    `update_datetime` = NOW(),
    `update_user_name` = 'system'
WHERE `template_code` = 'media_photo_detail'
  AND `channel_type` = 'telegram';

UPDATE `notify_template`
SET `template_content` = '🌁 <b>Mist · 新歌入库</b>

🎼 <b>${songTitleHtml}</b>
${songAlbumHtml} · ${songTrackHtml}

🎧 画质：${songQualityHtml}
🎚️ 音质：${songAudioHtml}
💬 字幕：${songSubtitleHtml}
💿 大小：${songSizeHtml}
📻 来源：${songServerHtml}

<blockquote expandable>📝 简介
${songNoteHtml}</blockquote>

✅ 已加入媒体库，雾中见。',
    `update_datetime` = NOW(),
    `update_user_name` = 'system'
WHERE `template_code` = 'media_photo_detail_song'
  AND `channel_type` = 'telegram';

UPDATE `notify_template`
SET `template_content` = '*🌁 Mist · 媒体详情*

${overview} *',
    `update_datetime` = NOW(),
    `update_user_name` = 'system'
WHERE `template_code` = 'media_photo_message'
  AND `channel_type` = 'telegram';

UPDATE `notify_template`
SET `template_content` = '*🌁 Mist · 开始播放*

🎬 ${name}
👤 用户：${playUser}
📺 标题：${playTitle}
📍 位置：${userLocation}
⏰ 时间：${playTime}
⏯️ 进度：${playPosition}
💻 客户端：${clientInfo}
🖥 服务器：${serverName} *',
    `update_datetime` = NOW(),
    `update_user_name` = 'system'
WHERE `template_code` = 'wechat_playback_start'
  AND `channel_type` = 'telegram';

UPDATE `notify_template`
SET `template_content` = '*🌁 Mist · 播放结束*

🎬 ${name}
👤 用户：${playUser}
📺 标题：${playTitle}
📍 位置：${userLocation}
⏰ 时间：${playTime}
⏯️ 进度：${playPosition}
💻 客户端：${clientInfo}
🖥 服务器：${serverName} *',
    `update_datetime` = NOW(),
    `update_user_name` = 'system'
WHERE `template_code` = 'wechat_playback_stop'
  AND `channel_type` = 'telegram';

UPDATE `notify_template`
SET `template_content` = '*🌁 Mist · 登录失败*

👤 用户：${userName}
🕒 时间：${loginTime}
🖥 客户端：${client}
📱 设备：${device}
🌍 IP：${ipAddress}
🖥 服务器：${serverName} *',
    `update_datetime` = NOW(),
    `update_user_name` = 'system'
WHERE `template_code` = 'auth_failed'
  AND `channel_type` IN ('common', 'telegram');

UPDATE `notify_template`
SET `template_content` = '*🌁 Mist · 登录成功*

👤 用户：${userName}
🕒 时间：${loginTime}
🖥 客户端：${client}
📱 设备：${device}
🌍 IP：${ipAddress}
🖥 服务器：${serverName} *',
    `update_datetime` = NOW(),
    `update_user_name` = 'system'
WHERE `template_code` = 'auth_success'
  AND `channel_type` IN ('common', 'telegram');

UPDATE `notify_template`
SET `template_content` = '*🌁 Mist · 账号到期提醒*

👤 用户：${userName}
📅 到期：${expirationDate}
⏱️ 剩余：${timeLeft}
🖥 服务器：${serverName}

请及时续费，避免服务中断。*',
    `update_datetime` = NOW(),
    `update_user_name` = 'system'
WHERE `template_code` = 'user_expiration'
  AND `channel_type` IN ('common', 'telegram');

UPDATE `notify_template`
SET `template_content` = '*🌁 Mist · 账号已停用*

👤 用户：${userName}
🚫 原因：${reason}
🖥 服务器：${serverName}

如需恢复，请联系管理员。*',
    `update_datetime` = NOW(),
    `update_user_name` = 'system'
WHERE `template_code` = 'user_disabled'
  AND `channel_type` IN ('common', 'telegram');

UPDATE `notify_template`
SET `template_content` = '*🌁 Mist · 账号已删除*

👤 用户：${userName}
🚫 原因：${reason}
🖥 服务器：${serverName}

如有疑问，请联系管理员。*',
    `update_datetime` = NOW(),
    `update_user_name` = 'system'
WHERE `template_code` = 'user_deleted'
  AND `channel_type` IN ('common', 'telegram');

UPDATE `notify_template`
SET `template_content` = '*🌁 Mist · 新增求片*

🎬 名称：${requestName}
📅 年份：${year}${season}
👤 提交：${userName}

📝 简介：${requestOverviewBrief}
🖥 服务器：${serverName} *',
    `update_datetime` = NOW(),
    `update_user_name` = 'system'
WHERE `template_code` = 'request_submitted'
  AND `channel_type` IN ('common', 'telegram');

UPDATE `notify_template`
SET `template_content` = '*🌁 Mist · 求片已入库*

🎬 名称：${requestName}
📅 年份：${year}${season}

📝 简介：${requestOverviewBrief}
🖥 服务器：${serverName}

✅ 已完成，欢迎回到媒体库。*',
    `update_datetime` = NOW(),
    `update_user_name` = 'system'
WHERE `template_code` = 'request_completed'
  AND `channel_type` IN ('common', 'telegram');

UPDATE `notify_template`
SET `template_content` = '*🌁 Mist · 账号已创建*

👤 用户：${userName}
🔑 卡密：${registerCode}
✅ 时长：${registerDays}
🖥 服务器：${serverName} *',
    `update_datetime` = NOW(),
    `update_user_name` = 'system'
WHERE `template_code` = 'card_register_success'
  AND `channel_type` IN ('common', 'telegram');

UPDATE `notify_template`
SET `template_content` = '*🌁 Mist · 邀请注册成功*

👤 用户：${userName}
🎫 邀请码：${invitationCode}
✅ 时长：${registerDays}
🖥 服务器：${serverName} *',
    `update_datetime` = NOW(),
    `update_user_name` = 'system'
WHERE `template_code` = 'invitation_register_success'
  AND `channel_type` IN ('common', 'telegram');

UPDATE `notify_template`
SET `template_content` = '*🌁 Mist · 卡密续费成功*

👤 用户：${userName}
🔑 卡密：${registerCode}
✅ 续费：${renewDays}
⏰ 到期：${expirationDate}
🖥 服务器：${serverName} *',
    `update_datetime` = NOW(),
    `update_user_name` = 'system'
WHERE `template_code` = 'card_renew_success'
  AND `channel_type` IN ('common', 'telegram');

UPDATE `notify_template`
SET `template_content` = '*🌁 Mist · 追剧更新*

🎬 ${mediaTypeLabel}：${name}
${tvInfoBlock}🗓 播出：${releaseDate}
🧭 来源：TMDB

📝 ${overview} *',
    `update_datetime` = NOW(),
    `update_user_name` = 'system'
WHERE `template_code` = 'tmdb_follow_update'
  AND `channel_type` = 'telegram';

UPDATE `notify_template`
SET `template_content` = '*🌁 Mist · 开始下载*

🎬 订阅：${subscribeName}
🏷 站点：${siteName}
🗂 类型：${mediaTypeLabel}
📦 资源：${downloadTitle}

📖 ${overview} *',
    `update_datetime` = NOW(),
    `update_user_name` = 'system'
WHERE `template_code` = 'subscribe_download'
  AND `channel_type` = 'telegram';

UPDATE `notify_template`
SET `template_content` = '*🌁 Mist · 整理完成*

🎬 影片：${movieName}
🏷 站点：${siteName}
🗂 类型：${mediaTypeLabel}
📦 资源：${downloadTitle}
📋 状态：${downloadStatus}

📖 ${overview} *',
    `update_datetime` = NOW(),
    `update_user_name` = 'system'
WHERE `template_code` = 'subscribe_organize_success'
  AND `channel_type` = 'telegram';

UPDATE `notify_template`
SET `template_content` = '*🌁 Mist · 整理失败*

🎬 影片：${movieName}
🏷 站点：${siteName}
🗂 类型：${mediaTypeLabel}
📦 资源：${downloadTitle}
📋 状态：${downloadStatus}
⚠️ 原因：${errorMessage}

📖 ${overview} *',
    `update_datetime` = NOW(),
    `update_user_name` = 'system'
WHERE `template_code` = 'subscribe_organize_failed'
  AND `channel_type` = 'telegram';

UPDATE `notify_template`
SET `template_content` = '*🌁 Mist · 新增订阅*

🎬 订阅：${subscribeName}
🏷 站点：${siteName}
🗂 类型：${mediaTypeLabel}
📅 年份：${year}

📖 ${overview} *',
    `update_datetime` = NOW(),
    `update_user_name` = 'system'
WHERE `template_code` = 'subscribe_added'
  AND `channel_type` = 'telegram';

UPDATE `notify_template`
SET `template_content` = '*🌁 Mist · 新工单*

🧾 工单：${ticketId}
📌 标题：${ticketTitle}
👤 提交人：${ticketSubmitter}
🕒 时间：${ticketSubmitTime}

📝 内容：
${ticketContent} *',
    `update_datetime` = NOW(),
    `update_user_name` = 'system'
WHERE `template_code` = 'support_ticket_submitted'
  AND `channel_type` = 'telegram';
