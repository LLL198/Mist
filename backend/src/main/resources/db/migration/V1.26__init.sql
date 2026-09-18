INSERT INTO `system_config` (`name`, `config_key`, `config_value`, `is_enabled`, `description`, `is_update`, `create_datetime`, `update_datetime`, `create_user_name`, `update_user_name`, `update_user_id`, `create_user_id`, `del_flag`)
VALUES ('自动禁用同时播放', 'simultaneous_playback_disable_threshold', '2', 1, '当同一用户同时播放数达到或超过该值时自动禁用用户', 0, NOW(), NOW(), 'admin', 'admin', 1, 1, 0);
