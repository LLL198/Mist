INSERT INTO `system_config`
(`name`, `config_key`, `config_value`, `is_enabled`, `description`, `is_update`, `create_datetime`, `update_datetime`, `create_user_name`, `update_user_name`, `update_user_id`, `create_user_id`, `del_flag`)
SELECT 'license-admin授权码', 'license_admin_license_code', '', 1, 'license-admin 远程授权码', 0, NOW(), NOW(), 'admin', 'admin', 1, 1, 0
WHERE NOT EXISTS (SELECT 1 FROM `system_config` WHERE `config_key` = 'license_admin_license_code');
