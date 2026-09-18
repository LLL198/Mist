ALTER TABLE `request_list`
    ADD COLUMN `request_source` varchar(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '求片来源' AFTER `production_countries`,
    ADD COLUMN `telegram_user_id` bigint DEFAULT NULL COMMENT 'Telegram 用户 ID' AFTER `request_source`,
    ADD COLUMN `points_cost` int DEFAULT '0' COMMENT '本次求片消耗积分' AFTER `telegram_user_id`,
    ADD COLUMN `points_refunded` tinyint DEFAULT '0' COMMENT '积分是否已退款 0 未退款 1 已退款' AFTER `points_cost`,
    ADD COLUMN `points_ref_id` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '积分流水关联 ID' AFTER `points_refunded`;

CREATE INDEX `idx_request_list_telegram_daily`
    ON `request_list` (`request_source`, `telegram_user_id`, `create_datetime`);

CREATE INDEX `idx_request_list_points_ref_id`
    ON `request_list` (`points_ref_id`);

INSERT INTO `system_config` (`name`, `config_key`, `config_value`, `is_enabled`, `description`, `is_update`, `create_datetime`, `update_datetime`, `create_user_name`, `update_user_name`, `update_user_id`, `create_user_id`, `del_flag`)
SELECT 'Telegram求片积分配置',
       'telegram_request_points_config',
       '{"enabled":true,"dailyFreeCount":0,"pointsPerRequest":10,"refundOnReject":true}',
       1,
       'JSON配置：enabled 是否启用积分求片；dailyFreeCount 每个 Telegram 用户每日免费求片次数，默认 0 表示纯积分；pointsPerRequest 超过免费次数后每次消耗积分；refundOnReject 拒绝求片时是否自动退回积分',
       1,
       NOW(),
       NOW(),
       'admin',
       'admin',
       1,
       1,
       0
WHERE NOT EXISTS (SELECT 1 FROM `system_config` WHERE `config_key` = 'telegram_request_points_config');
