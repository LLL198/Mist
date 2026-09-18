ALTER TABLE `request_list`
    ADD COLUMN `movie_pilot_subscription_id` bigint DEFAULT NULL COMMENT 'MoviePilot 订阅 ID' AFTER `points_ref_id`;

CREATE INDEX `idx_request_list_movie_pilot_subscription_id`
    ON `request_list` (`movie_pilot_subscription_id`);
