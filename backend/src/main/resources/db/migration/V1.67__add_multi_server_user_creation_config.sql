-- 多服务器同步创建用户开关（默认关闭）
INSERT INTO `system_config` (`name`, `config_key`, `config_value`, `is_enabled`, `description`, `is_update`, `create_datetime`, `update_datetime`, `create_user_name`, `update_user_name`, `update_user_id`, `create_user_id`, `del_flag`)
VALUES ('多服务器同步创建用户开关', 'multi_server_user_creation', 'true', 0, '多服务器同步创建用户开关', 1, NOW(), NOW(), 'admin', 'admin', 1, 1, 0);
