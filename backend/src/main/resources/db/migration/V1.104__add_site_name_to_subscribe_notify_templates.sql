-- 为订阅相关通知模板添加站点名称变量

-- 1. 订阅下载触发通知
UPDATE `notify_template`
SET
    `template_content` = REPLACE(`template_content`, '🎬 订阅：${subscribeName}', '🎬 订阅：${subscribeName}\n🏷 站点：${siteName}'),
    `variable_comment` = REPLACE(`variable_comment`, 'subscribeName,', 'subscribeName, siteName,')
WHERE `template_code` = 'subscribe_download' AND `channel_type` IN ('common', 'telegram', 'wechat', 'dingding', 'messagepush');

-- 2. 订阅整理完成通知
UPDATE `notify_template`
SET
    `template_content` = REPLACE(`template_content`, '🎬 影片：${movieName}', '🎬 影片：${movieName}\n🏷 站点：${siteName}'),
    `variable_comment` = REPLACE(`variable_comment`, 'subscribeName,', 'subscribeName, siteName,')
WHERE `template_code` = 'subscribe_organize_success' AND `channel_type` IN ('common', 'telegram', 'wechat', 'dingding', 'messagepush');

-- 3. 订阅整理失败通知
UPDATE `notify_template`
SET
    `template_content` = REPLACE(`template_content`, '🎬 影片：${movieName}', '🎬 影片：${movieName}\n🏷 站点：${siteName}'),
    `variable_comment` = REPLACE(`variable_comment`, 'subscribeName,', 'subscribeName, siteName,')
WHERE `template_code` = 'subscribe_organize_failed' AND `channel_type` IN ('common', 'telegram', 'wechat', 'dingding', 'messagepush');

-- 4. 订阅新增通知
UPDATE `notify_template`
SET
    `template_content` = REPLACE(`template_content`, '🎬 订阅：${subscribeName}', '🎬 订阅：${subscribeName}\n🏷 站点：${siteName}'),
    `variable_comment` = REPLACE(`variable_comment`, 'subscribeName,', 'subscribeName, siteName,')
WHERE `template_code` = 'subscribe_added' AND `channel_type` IN ('common', 'telegram', 'wechat', 'dingding', 'messagepush');

