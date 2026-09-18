UPDATE `system_config`
SET `is_update` = 1,
    `description` = '是否开启用户注册，配置值为开放注册目标服务器 ID'
WHERE `config_key` = 'registered_user';

UPDATE `system_config` sc
LEFT JOIN (
    SELECT CAST(`id` AS CHAR) AS `server_id`
    FROM `emby_info`
    WHERE `enabled` = 1
      AND `status` = 0
      AND `del_flag` = 0
    ORDER BY `id` ASC
    LIMIT 1
) server_config ON 1 = 1
SET sc.`config_value` = COALESCE(server_config.`server_id`, '')
WHERE sc.`config_key` = 'registered_user'
  AND (
      sc.`config_value` IS NULL
      OR TRIM(sc.`config_value`) = ''
      OR LOWER(TRIM(sc.`config_value`)) IN ('true', 'false')
  );
