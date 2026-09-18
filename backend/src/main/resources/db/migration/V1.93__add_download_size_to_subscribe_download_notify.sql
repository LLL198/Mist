-- 订阅下载通知模板：增加资源大小
UPDATE `notify_template`
SET `template_content` = '* 📥 订阅下载通知\n\n🎬 订阅：${subscribeName}\n🗂 类型：${mediaTypeLabel}\n📦 资源：${downloadTitle}\n${downloadSizeLine}\n${overview} *',
    `variable_comment` = 'subscribeName, movieName, mediaTypeLabel, downloadTitle, downloadSize, downloadSizeLine, overview'
WHERE `template_code` = 'subscribe_download' AND `channel_type` = 'common';

UPDATE `notify_template`
SET `template_content` = '📥 订阅下载通知\n\n🎬 订阅：${subscribeName}\n🗂 类型：${mediaTypeLabel}\n📦 资源：${downloadTitle}\n${downloadSizeLine}\n<blockquote expandable>📖 简介\n${overview}</blockquote>',
    `variable_comment` = 'subscribeName, movieName, mediaTypeLabel, downloadTitle, downloadSize, downloadSizeLine, overview'
WHERE `template_code` = 'subscribe_download' AND `channel_type` = 'telegram';

UPDATE `notify_template`
SET `template_content` = '📥 订阅下载通知\n🎬 订阅：${subscribeName}\n🗂 类型：${mediaTypeLabel}\n📦 资源：${downloadTitle}\n${downloadSizeLine}',
    `variable_comment` = 'subscribeName, movieName, mediaTypeLabel, downloadTitle, downloadSize, downloadSizeLine'
WHERE `template_code` = 'subscribe_download' AND `channel_type` = 'wechat';

UPDATE `notify_template`
SET `template_content` = '* 📥 订阅下载通知\n\n🎬 订阅：${subscribeName}\n🗂 类型：${mediaTypeLabel}\n📦 资源：${downloadTitle}\n${downloadSizeLine}\n${overview} *',
    `variable_comment` = 'subscribeName, movieName, mediaTypeLabel, downloadTitle, downloadSize, downloadSizeLine, overview'
WHERE `template_code` = 'subscribe_download' AND `channel_type` = 'dingding';

UPDATE `notify_template`
SET `template_content` = '📥 订阅下载通知\n🎬 订阅：${subscribeName}\n🗂 类型：${mediaTypeLabel}\n📦 资源：${downloadTitle}\n${downloadSizeLine}',
    `variable_comment` = 'subscribeName, movieName, mediaTypeLabel, downloadTitle, downloadSize, downloadSizeLine'
WHERE `template_code` = 'subscribe_download' AND `channel_type` = 'messagepush';
