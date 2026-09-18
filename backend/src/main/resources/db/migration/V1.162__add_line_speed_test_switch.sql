INSERT INTO `system_config` (`name`, `config_key`, `config_value`, `is_enabled`, `description`, `is_update`, `create_datetime`, `update_datetime`, `create_user_name`, `update_user_name`, `update_user_id`, `create_user_id`, `del_flag`)
SELECT '线路测速',
       'line_speed_test_enabled',
       'true',
       0,
       '开启后，服务器主机线路弹窗显示单线路测速和批量测速，并允许获取测速媒体；默认关闭。',
       1,
       NOW(),
       NOW(),
       'system',
       'system',
       NULL,
       NULL,
       0
WHERE NOT EXISTS (SELECT 1 FROM `system_config` WHERE `config_key` = 'line_speed_test_enabled');
