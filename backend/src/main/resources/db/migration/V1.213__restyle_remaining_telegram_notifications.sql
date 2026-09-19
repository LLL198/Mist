-- Complete the Mist presentation refresh for Telegram's common-template fallbacks.
-- The fallback channel is also used by Telegram when a channel-specific row is absent.

UPDATE `notify_template`
SET `template_content` = '*🌁 Mist · 同时播放提醒*

👤 用户：${userName}
🖥 服务器：${serverName}

${playbackDetails} *',
    `update_datetime` = NOW(),
    `update_user_name` = 'system'
WHERE `template_code` = 'simultaneous_playback'
  AND `channel_type` = 'common';

UPDATE `notify_template`
SET `template_content` = '*🌁 Mist · TMDB 每日上映 / 播出*

📅 日期：${date}
${summary}

${list} *',
    `update_datetime` = NOW(),
    `update_user_name` = 'system'
WHERE `template_code` = 'tmdb_daily_release_text'
  AND `channel_type` = 'common';

UPDATE `notify_template`
SET `template_content` = '🌁 Mist · Emby 客户端拦截

${clientFilterDetails}',
    `update_datetime` = NOW(),
    `update_user_name` = 'system'
WHERE `template_code` = 'emby_client_filter'
  AND `channel_type` = 'common';

UPDATE `notify_template`
SET `template_content` = '*🌁 Mist · 媒体通知*

${overview} *',
    `update_datetime` = NOW(),
    `update_user_name` = 'system'
WHERE `template_code` = 'media_text_message'
  AND `channel_type` = 'common';

UPDATE `notify_template`
SET `template_content` = '*🌁 Mist · 媒体详情*

${overview} *',
    `update_datetime` = NOW(),
    `update_user_name` = 'system'
WHERE `template_code` = 'media_photo_detail'
  AND `channel_type` = 'common';

UPDATE `notify_template`
SET `template_content` = '*🌁 Mist · 媒体消息*

${overview} *',
    `update_datetime` = NOW(),
    `update_user_name` = 'system'
WHERE `template_code` = 'media_photo_message'
  AND `channel_type` = 'common';

UPDATE `notify_template`
SET `template_content` = '*🌁 Mist · 追剧更新*

🎬 ${mediaTypeLabel}：${name}
${tvInfoBlock}🗓 播出：${releaseDate}

📝 ${overview} *',
    `update_datetime` = NOW(),
    `update_user_name` = 'system'
WHERE `template_code` = 'tmdb_follow_update'
  AND `channel_type` = 'common';

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
  AND `channel_type` = 'common';

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
  AND `channel_type` = 'common';

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
  AND `channel_type` = 'common';

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
  AND `channel_type` = 'common';

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
  AND `channel_type` = 'common';
