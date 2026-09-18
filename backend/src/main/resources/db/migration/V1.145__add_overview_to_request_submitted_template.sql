UPDATE `notify_template`
SET `template_content` = '* 📝 新增求片\n\n🎬 名称：${requestName}\n📅 年份：${year}${season}\n👤 提交用户：${userName}\n\n📝 简介：${requestOverviewBrief}\n\n👉 服务器：${serverName} *',
    `variable_comment` = 'requestName, year, season, userName, requestOverviewBrief, serverName',
    `update_datetime` = NOW(),
    `update_user_name` = 'system'
WHERE `template_code` = 'request_submitted'
  AND `channel_type` = 'common';

UPDATE `notify_template`
SET `template_content` = '📝 新增求片\n🎬 名称：${requestName}\n👤 用户：${userName}\n📝 简介：${requestOverviewBrief}\n👉 服务器：${serverName}',
    `variable_comment` = 'requestName, userName, requestOverviewBrief, serverName',
    `update_datetime` = NOW(),
    `update_user_name` = 'system'
WHERE `template_code` = 'request_submitted'
  AND `channel_type` = 'wechat';
