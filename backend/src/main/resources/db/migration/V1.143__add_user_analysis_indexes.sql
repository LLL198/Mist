ALTER TABLE `playback_reporting_record`
    ADD KEY `idx_prr_user_id_server_day` (`user_id`, `emby_info_id`, `play_day`),
    ADD KEY `idx_prr_user_name_server_day` (`user_name`, `emby_info_id`, `play_day`);
