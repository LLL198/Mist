UPDATE `notify_template`
SET `template_content` = '* ✅ 求片入库\n\n🎬 名称：${requestName}\n📅 年份：${year}${season}\n\n📝 简介：\n${requestOverviewBrief}\n\n👉 服务器：${serverName} *',
    `variable_comment` = 'requestName, year, season, requestOverviewBrief, serverName',
    `update_datetime` = NOW(),
    `update_user_name` = 'system'
WHERE `template_code` = 'request_completed'
  AND `channel_type` = 'common';
