CREATE INDEX `idx_points_bot_user_user_del_id`
    ON `points_bot_user` (`user_id`, `del_flag`, `id`);

CREATE INDEX `idx_points_bot_user_level_del_id`
    ON `points_bot_user` (`level_id`, `del_flag`, `id`);

CREATE INDEX `idx_oauth_user_provider_del`
    ON `user_oauth_binding` (`user_id`, `provider`, `del_flag`, `provider_user_id`);
