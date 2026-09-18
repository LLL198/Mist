INSERT INTO `system_config` (`id`, `name`, `config_key`, `config_value`, `is_enabled`, `description`, `is_update`, `create_datetime`, `update_datetime`, `create_user_name`, `update_user_name`, `update_user_id`, `create_user_id`, `del_flag`)
VALUES (16, 'moviepilot自动订阅', 'movie_pilot_auto_subscribe', 'true', 1, '是否在新增求片后自动向 moviepilot 发起订阅', 0, NOW(), NOW(), 'admin', 'admin', 1, 1, 0);
