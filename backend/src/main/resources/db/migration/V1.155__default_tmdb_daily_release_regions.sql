UPDATE `system_config`
SET `config_value` = JSON_SET(
        COALESCE(NULLIF(`config_value`, ''), '{}'),
        '$.region',
        'CN,US,GB'
    ),
    `description` = '每日抓取 TMDB 选定地区上映电影和上海时区播出剧集，并推送 Telegram 图片和企业微信文本',
    `update_datetime` = NOW(),
    `update_user_name` = 'system'
WHERE `config_key` = 'tmdb_daily_release_config'
  AND JSON_VALID(COALESCE(NULLIF(`config_value`, ''), '{}'))
  AND COALESCE(JSON_UNQUOTE(JSON_EXTRACT(COALESCE(NULLIF(`config_value`, ''), '{}'), '$.region')), '') IN ('', 'CN');
