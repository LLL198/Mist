-- 邀请码/卡密注册开关
INSERT INTO `system_config` (`name`, `config_key`, `config_value`, `is_enabled`, `description`, `is_update`, `create_datetime`, `update_datetime`, `create_user_name`, `update_user_name`, `update_user_id`, `create_user_id`, `del_flag`)
VALUES ('开启邀请码注册', 'invitation_register_enabled', 'true', 0, '是否允许通过邀请码注册', 1, NOW(), NOW(), 'admin', 'admin', 1, 1, 0),
       ('开启卡密注册', 'card_register_enabled', 'true', 0, '是否允许通过卡密注册', 1, NOW(), NOW(), 'admin', 'admin', 1, 1, 0);
