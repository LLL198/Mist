CREATE TABLE IF NOT EXISTS `playback_ranking_config` (
    `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `emby_info_id` bigint NOT NULL COMMENT 'Emby服务器ID',
    `excluded_user_ids` mediumtext NOT NULL COMMENT '不参与排行榜的Emby用户ID JSON数组',
    `create_datetime` datetime DEFAULT NULL,
    `update_datetime` datetime DEFAULT NULL,
    `create_user_name` varchar(255) DEFAULT NULL,
    `update_user_name` varchar(255) DEFAULT NULL,
    `update_user_id` bigint DEFAULT NULL,
    `create_user_id` bigint DEFAULT NULL,
    `del_flag` tinyint NOT NULL DEFAULT 0,
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_playback_ranking_server` (`emby_info_id`),
    KEY `idx_playback_ranking_server_active` (`emby_info_id`, `del_flag`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='每日播放排行榜任务配置';
