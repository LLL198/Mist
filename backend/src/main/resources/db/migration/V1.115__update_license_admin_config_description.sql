UPDATE `system_config`
SET `description` = '授权码',
    `update_datetime` = NOW(),
    `update_user_name` = 'admin',
    `update_user_id` = 1
WHERE `config_key` = 'license_admin_license_code'
  AND `description` = 'license-admin 远程授权码';
