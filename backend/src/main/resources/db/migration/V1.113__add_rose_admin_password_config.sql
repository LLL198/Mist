INSERT INTO `system_config` (`name`, `config_key`, `config_value`, `is_enabled`, `description`, `is_update`, `create_datetime`, `update_datetime`, `create_user_name`, `update_user_name`, `update_user_id`, `create_user_id`, `del_flag`)
SELECT 'Rose管理员密码', 'rose_admin_password', '', 0, '用于用户到期删除任务自动登录 Rose 管理员并调用解绑接口；未启用或为空时，定时任务会跳过 Rose 远程解绑。', 1, NOW(), NOW(), 'admin', 'admin', 1, 1, 0
WHERE NOT EXISTS (SELECT 1 FROM `system_config` WHERE `config_key` = 'rose_admin_password');
