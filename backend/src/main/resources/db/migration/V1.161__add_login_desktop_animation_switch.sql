INSERT INTO `system_config` (`name`, `config_key`, `config_value`, `is_enabled`, `description`, `is_update`, `create_datetime`, `update_datetime`, `create_user_name`, `update_user_name`, `update_user_id`, `create_user_id`, `del_flag`)
SELECT '桌面端登录动效',
       'login_desktop_animation_enabled',
       'true',
       1,
       '开启后，非手机端登录页使用星空背景、中心闪电和右侧卡片动效；关闭后恢复旧登录布局。手机端始终保持旧登录布局。',
       1,
       NOW(),
       NOW(),
       'system',
       'system',
       NULL,
       NULL,
       0
WHERE NOT EXISTS (SELECT 1 FROM `system_config` WHERE `config_key` = 'login_desktop_animation_enabled');
