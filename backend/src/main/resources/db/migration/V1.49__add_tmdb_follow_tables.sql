-- TMDB 追剧订阅主表
CREATE TABLE IF NOT EXISTS `tmdb_follow` (
    `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键id',
    `tmdb_id` int NOT NULL COMMENT 'TMDB 剧集ID',
    `name` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '名称',
    `original_name` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '原始名称',
    `poster_path` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '海报',
    `backdrop_path` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '背景',
    `overview` text COLLATE utf8mb4_unicode_ci COMMENT '简介',
    `next_air_date` datetime DEFAULT NULL COMMENT '下一集播出时间',
    `next_season_number` int DEFAULT NULL COMMENT '下一集季',
    `next_episode_number` int DEFAULT NULL COMMENT '下一集集',
    `last_notified_season` int DEFAULT NULL COMMENT '最后推送季',
    `last_notified_episode` int DEFAULT NULL COMMENT '最后推送集',
    `language` varchar(32) COLLATE utf8mb4_unicode_ci DEFAULT 'zh-CN' COMMENT '语言',
    `notify_channels` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '通知渠道',
    `status` int DEFAULT '0' COMMENT '0 跟踪中 1 暂停',
    `last_sync_time` datetime DEFAULT NULL COMMENT '最近同步时间',
    `subscriber_name` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '订阅人',
    `create_datetime` datetime DEFAULT NULL COMMENT '创建时间',
    `update_datetime` datetime DEFAULT NULL COMMENT '修改时间',
    `create_user_name` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '创建人名称',
    `update_user_name` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '修改人名称',
    `update_user_id` bigint DEFAULT NULL COMMENT '修改人id',
    `create_user_id` bigint DEFAULT NULL COMMENT '创建人id',
    `del_flag` int DEFAULT '0' COMMENT '是否删除 0 未删除 1 已删除',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE KEY `uk_tmdb_follow_tmdb` (`tmdb_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='TMDB 追剧订阅';

-- TMDB 分集表
CREATE TABLE IF NOT EXISTS `tmdb_episode` (
    `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键id',
    `follow_id` bigint NOT NULL COMMENT '订阅ID',
    `tmdb_id` int NOT NULL COMMENT 'TMDB 剧集ID',
    `season_number` int NOT NULL COMMENT '季',
    `episode_number` int NOT NULL COMMENT '集',
    `name` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '标题',
    `overview` text COLLATE utf8mb4_unicode_ci COMMENT '简介',
    `air_date` datetime DEFAULT NULL COMMENT '播出时间',
    `still_path` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '剧照',
    `create_datetime` datetime DEFAULT NULL COMMENT '创建时间',
    `update_datetime` datetime DEFAULT NULL COMMENT '修改时间',
    `create_user_name` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '创建人名称',
    `update_user_name` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '修改人名称',
    `update_user_id` bigint DEFAULT NULL COMMENT '修改人id',
    `create_user_id` bigint DEFAULT NULL COMMENT '创建人id',
    `del_flag` int DEFAULT '0' COMMENT '是否删除 0 未删除 1 已删除',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE KEY `uk_follow_episode` (`follow_id`,`season_number`,`episode_number`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='TMDB 分集信息';

-- TMDB 追剧进度表
CREATE TABLE IF NOT EXISTS `tmdb_watch_progress` (
    `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键id',
    `follow_id` bigint NOT NULL COMMENT '订阅ID',
    `watcher_name` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '观看人',
    `season_number` int DEFAULT NULL COMMENT '当前季',
    `episode_number` int DEFAULT NULL COMMENT '当前集',
    `create_datetime` datetime DEFAULT NULL COMMENT '创建时间',
    `update_datetime` datetime DEFAULT NULL COMMENT '修改时间',
    `create_user_name` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '创建人名称',
    `update_user_name` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '修改人名称',
    `update_user_id` bigint DEFAULT NULL COMMENT '修改人id',
    `create_user_id` bigint DEFAULT NULL COMMENT '创建人id',
    `del_flag` int DEFAULT '0' COMMENT '是否删除 0 未删除 1 已删除',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE KEY `uk_progress_follow_watcher` (`follow_id`,`watcher_name`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='TMDB 追剧进度';

-- 追剧推送模板
INSERT INTO `notify_template` (`template_code`, `template_name`, `channel_type`, `template_content`, `variable_comment`, `enabled`, `remark`, `create_datetime`, `update_datetime`, `create_user_name`, `update_user_name`, `update_user_id`, `create_user_id`, `del_flag`)
SELECT 'tmdb_follow_update', '追剧更新-通用', 'common', '🎬 剧集：${name}\n${tvInfoBlock}📅 播出：${releaseDate}\n📝 简介：${overview}', 'name,tvInfoBlock,releaseDate,overview', 1, '追剧更新通用模板', NOW(), NOW(), 'system', 'system', NULL, NULL, 0
WHERE NOT EXISTS (SELECT 1 FROM notify_template WHERE template_code = 'tmdb_follow_update' AND channel_type = 'common');

INSERT INTO `notify_template` (`template_code`, `template_name`, `channel_type`, `template_content`, `variable_comment`, `enabled`, `remark`, `create_datetime`, `update_datetime`, `create_user_name`, `update_user_name`, `update_user_id`, `create_user_id`, `del_flag`)
SELECT 'tmdb_follow_update', '追剧更新-Telegram', 'telegram', '🎬 剧集：${name}\n${tvInfoBlock}📅 播出：${releaseDate}\n🧭 来源：TMDB\n📝 简介：${overview}', 'name,tvInfoBlock,releaseDate,overview', 1, 'Telegram 追剧更新', NOW(), NOW(), 'system', 'system', NULL, NULL, 0
WHERE NOT EXISTS (SELECT 1 FROM notify_template WHERE template_code = 'tmdb_follow_update' AND channel_type = 'telegram');

INSERT INTO `notify_template` (`template_code`, `template_name`, `channel_type`, `template_content`, `variable_comment`, `enabled`, `remark`, `create_datetime`, `update_datetime`, `create_user_name`, `update_user_name`, `update_user_id`, `create_user_id`, `del_flag`)
SELECT 'tmdb_follow_update', '追剧更新-钉钉', 'dingding', '${backdropImageBlock}🎬 剧集：${name}\n${tvInfoBlock}📅 播出：${releaseDate}\n📝 简介：${overview}', 'backdropImageBlock,name,tvInfoBlock,releaseDate,overview', 1, '钉钉 追剧更新', NOW(), NOW(), 'system', 'system', NULL, NULL, 0
WHERE NOT EXISTS (SELECT 1 FROM notify_template WHERE template_code = 'tmdb_follow_update' AND channel_type = 'dingding');

INSERT INTO `notify_template` (`template_code`, `template_name`, `channel_type`, `template_content`, `variable_comment`, `enabled`, `remark`, `create_datetime`, `update_datetime`, `create_user_name`, `update_user_name`, `update_user_id`, `create_user_id`, `del_flag`)
SELECT 'tmdb_follow_update', '追剧更新-企业微信', 'wechat', '🎬 剧集：${name}\n${tvInfoBlock}📅 播出：${releaseDate}\n📝 简介：${overview}', 'name,tvInfoBlock,releaseDate,overview', 1, '企业微信 追剧更新', NOW(), NOW(), 'system', 'system', NULL, NULL, 0
WHERE NOT EXISTS (SELECT 1 FROM notify_template WHERE template_code = 'tmdb_follow_update' AND channel_type = 'wechat');
