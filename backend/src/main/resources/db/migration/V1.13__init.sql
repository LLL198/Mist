INSERT INTO `system_config` (`id`, `name`, `config_key`, `config_value`, `is_enabled`, `description`, `is_update`, `create_datetime`, `update_datetime`, `create_user_name`, `update_user_name`, `update_user_id`, `create_user_id`, `del_flag`)
VALUES (15, '自定义入库海报', 'custom_poster_enabled', 'true', 0, '是否使用自定义入库海报，开启后Telegram使用竖版卡片，企业微信与钉钉使用横版卡片', 0, NOW(), NOW(), 'admin', 'admin', 1, 1, 0);
