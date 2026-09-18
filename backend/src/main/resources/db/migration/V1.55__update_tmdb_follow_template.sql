-- update tmdb_follow_template

-- Common / General
UPDATE `notify_template`
SET `template_content` = '✨ ${mediaTypeLabel}更新提醒 ✨\n\n🎬 片名：${name}\n${tvInfoBlock}🗓 播出：${releaseDate}\n\n📝 简介：${overview}',
    `variable_comment` = 'mediaTypeLabel,name,tvInfoBlock,releaseDate,overview'
WHERE `template_code` = 'tmdb_follow_update' AND `channel_type` = 'common';

-- Telegram (HTML Mode)
UPDATE `notify_template`
SET `template_content` = '<b>✨ ${mediaTypeLabel}更新提醒 ✨</b>\n\n🎬 <b>片名</b>：${name}\n${tvInfoBlock}🗓 <b>播出</b>：${releaseDate}\n🧭 <b>来源</b>：TMDB\n\n📝 <b>简介</b>：${overview}',
    `variable_comment` = 'mediaTypeLabel,name,tvInfoBlock,releaseDate,overview'
WHERE `template_code` = 'tmdb_follow_update' AND `channel_type` = 'telegram';

-- DingDing
UPDATE `notify_template`
SET `template_content` = '${backdropImageBlock}✨ ${mediaTypeLabel}更新提醒 ✨\n\n🎬 片名：${name}\n${tvInfoBlock}🗓 播出：${releaseDate}\n\n📝 简介：${overview}',
    `variable_comment` = 'backdropImageBlock,mediaTypeLabel,name,tvInfoBlock,releaseDate,overview'
WHERE `template_code` = 'tmdb_follow_update' AND `channel_type` = 'dingding';

-- WeChat (Enterprise WeChat)
UPDATE `notify_template`
SET `template_content` = '✨ ${mediaTypeLabel}更新提醒 ✨\n\n🎬 片名：${name}\n${tvInfoBlock}🗓 播出：${releaseDate}\n\n📝 简介：${overview}',
    `variable_comment` = 'mediaTypeLabel,name,tvInfoBlock,releaseDate,overview'
WHERE `template_code` = 'tmdb_follow_update' AND `channel_type` = 'wechat';
