UPDATE `system_config`
SET `name` = '授权码',
    `description` = '授权码',
    `update_datetime` = NOW(),
    `update_user_name` = 'admin',
    `update_user_id` = 1
WHERE `config_key` = 'license_admin_license_code'
  AND (`name` = 'license-admin授权码' OR `description` = 'license-admin 远程授权码');
