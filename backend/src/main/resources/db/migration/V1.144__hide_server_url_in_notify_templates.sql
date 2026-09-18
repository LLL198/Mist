-- 通知正文不再展示 Emby 服务器地址，历史 serverUrl 变量统一改为展示服务器名称。

UPDATE `notify_template`
SET `template_content` = REPLACE(`template_content`, '\n🔗 地址：${serverUrl}', ''),
    `update_datetime` = NOW(),
    `update_user_name` = 'system'
WHERE `template_content` LIKE '%🔗 地址：${serverUrl}%';

UPDATE `notify_template`
SET `template_content` = REPLACE(`template_content`, '\\n🔗 地址：${serverUrl}', ''),
    `update_datetime` = NOW(),
    `update_user_name` = 'system'
WHERE `template_content` LIKE '%🔗 地址：${serverUrl}%';

UPDATE `notify_template`
SET `template_content` = REPLACE(`template_content`, '服务器地址：${serverUrl}', '服务器名称：${serverName}'),
    `update_datetime` = NOW(),
    `update_user_name` = 'system'
WHERE `template_content` LIKE '%服务器地址：${serverUrl}%';

UPDATE `notify_template`
SET `template_content` = REPLACE(`template_content`, '服务器：${serverUrl}', '服务器名称：${serverName}'),
    `update_datetime` = NOW(),
    `update_user_name` = 'system'
WHERE `template_content` LIKE '%服务器：${serverUrl}%';

UPDATE `notify_template`
SET `template_content` = REPLACE(`template_content`, '${serverUrl}', '${serverName}'),
    `update_datetime` = NOW(),
    `update_user_name` = 'system'
WHERE `template_content` LIKE '%${serverUrl}%';

UPDATE `notify_template`
SET `template_content` = REPLACE(`template_content`, '服务器地址：${serverName}', '服务器名称：${serverName}'),
    `update_datetime` = NOW(),
    `update_user_name` = 'system'
WHERE `template_content` LIKE '%服务器地址：${serverName}%';

UPDATE `notify_template`
SET `template_content` = REPLACE(`template_content`, '服务器：${serverName}', '服务器名称：${serverName}'),
    `update_datetime` = NOW(),
    `update_user_name` = 'system'
WHERE `template_content` LIKE '%服务器：${serverName}%';
