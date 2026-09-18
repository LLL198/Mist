CREATE TABLE IF NOT EXISTS `tmdb_daily_release` (
    `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键id',
    `publish_date` date NOT NULL COMMENT '上映/播出日期',
    `media_type` varchar(20) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '媒体类型 movie/tv',
    `tmdb_id` int NOT NULL COMMENT 'TMDB ID',
    `title` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '标题',
    `original_title` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '原始标题',
    `year` int DEFAULT NULL COMMENT '年份',
    `overview` text COLLATE utf8mb4_unicode_ci COMMENT '简介',
    `poster_path` varchar(500) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '海报',
    `backdrop_path` varchar(500) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '背景',
    `tmdb_url` varchar(500) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT 'TMDB 链接',
    `release_date` date DEFAULT NULL COMMENT '电影上映日期',
    `first_air_date` date DEFAULT NULL COMMENT '剧集首播日期',
    `season_number` int DEFAULT NULL COMMENT '季',
    `episode_start` int DEFAULT NULL COMMENT '起始集',
    `episode_end` int DEFAULT NULL COMMENT '结束集',
    `episode_display` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '集数展示',
    `vote_average` decimal(5,2) DEFAULT NULL COMMENT '评分',
    `vote_count` int DEFAULT NULL COMMENT '评分人数',
    `popularity` decimal(12,4) DEFAULT NULL COMMENT '热度',
    `origin_country` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '原产地',
    `original_language` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '原始语言',
    `rank_no` int DEFAULT NULL COMMENT '当天排序',
    `source` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '来源接口',
    `raw_json` json DEFAULT NULL COMMENT 'TMDB 原始数据',
    `telegram_group_sent` tinyint NOT NULL DEFAULT 0 COMMENT 'Telegram 群聊是否已发送',
    `telegram_bot_sent` tinyint NOT NULL DEFAULT 0 COMMENT 'Telegram 私聊是否已发送',
    `wechat_sent` tinyint NOT NULL DEFAULT 0 COMMENT '企业微信群机器人是否已发送',
    `wechat_bot_sent` tinyint NOT NULL DEFAULT 0 COMMENT '企业微信自建应用是否已发送',
    `last_notify_time` datetime DEFAULT NULL COMMENT '最近通知时间',
    `last_error` varchar(1000) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '最近错误',
    `create_datetime` datetime DEFAULT NULL COMMENT '创建时间',
    `update_datetime` datetime DEFAULT NULL COMMENT '修改时间',
    `create_user_name` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '创建人名称',
    `update_user_name` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '修改人名称',
    `update_user_id` bigint DEFAULT NULL COMMENT '修改人id',
    `create_user_id` bigint DEFAULT NULL COMMENT '创建人id',
    `del_flag` int DEFAULT '0' COMMENT '是否删除 0 未删除 1 已删除',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE KEY `uk_tmdb_daily_release_active` (`publish_date`,`media_type`,`tmdb_id`,`season_number`,`episode_start`,`episode_end`,`del_flag`) USING BTREE,
    KEY `idx_tmdb_daily_release_day_rank` (`publish_date`,`del_flag`,`rank_no`) USING BTREE,
    KEY `idx_tmdb_daily_release_media` (`media_type`,`publish_date`,`del_flag`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='TMDB 每日上映/播出快照';

INSERT INTO `system_config` (`name`, `config_key`, `config_value`, `is_enabled`, `description`, `is_update`, `create_datetime`, `update_datetime`, `create_user_name`, `update_user_name`, `update_user_id`, `create_user_id`, `del_flag`)
SELECT 'TMDB每日上映播出', 'tmdb_daily_release_config', '{"enabled":false,"includeMovies":true,"includeTv":true,"limit":15,"region":"CN","timezone":"Asia/Shanghai","language":"zh-CN","releaseTypes":"2|3","originCountry":"","originalLanguage":"","telegramGroupEnabled":true,"telegramBotEnabled":false,"wechatEnabled":true,"wechatBotEnabled":false,"sendWhenEmpty":false,"queryDelayMillis":500,"maxPages":3}', 1, '每日抓取 TMDB 中国地区上映电影和上海时区播出剧集，并推送 Telegram 图片和企业微信文本', 1, NOW(), NOW(), 'system', 'system', NULL, NULL, 0
WHERE NOT EXISTS (SELECT 1 FROM `system_config` WHERE `config_key` = 'tmdb_daily_release_config');

INSERT INTO `notify_template` (`template_code`, `template_name`, `channel_type`, `template_content`, `variable_comment`, `enabled`, `remark`, `create_datetime`, `update_datetime`, `create_user_name`, `update_user_name`, `update_user_id`, `create_user_id`, `del_flag`)
SELECT 'tmdb_daily_release_text', 'TMDB每日上映播出-通用', 'common', 'TMDB 每日上映/播出 ${date}\n${summary}\n\n${list}', 'date,summary,list', 1, 'TMDB 每日上映播出文本模板', NOW(), NOW(), 'system', 'system', NULL, NULL, 0
WHERE NOT EXISTS (SELECT 1 FROM notify_template WHERE template_code = 'tmdb_daily_release_text' AND channel_type = 'common');

INSERT INTO `notify_template` (`template_code`, `template_name`, `channel_type`, `template_content`, `variable_comment`, `enabled`, `remark`, `create_datetime`, `update_datetime`, `create_user_name`, `update_user_name`, `update_user_id`, `create_user_id`, `del_flag`)
SELECT 'tmdb_daily_release_text', 'TMDB每日上映播出-企业微信', 'wechat', 'TMDB 每日上映/播出 ${date}\n${summary}\n\n${list}', 'date,summary,list', 1, '企业微信 TMDB 每日上映播出文本模板', NOW(), NOW(), 'system', 'system', NULL, NULL, 0
WHERE NOT EXISTS (SELECT 1 FROM notify_template WHERE template_code = 'tmdb_daily_release_text' AND channel_type = 'wechat');

INSERT INTO `notify_template` (`template_code`, `template_name`, `channel_type`, `template_content`, `variable_comment`, `enabled`, `remark`, `create_datetime`, `update_datetime`, `create_user_name`, `update_user_name`, `update_user_id`, `create_user_id`, `del_flag`)
SELECT 'tmdb_daily_release_text', 'TMDB每日上映播出-企业微信机器人', 'wechatBot', 'TMDB 每日上映/播出 ${date}\n${summary}\n\n${list}', 'date,summary,list', 1, '企业微信机器人 TMDB 每日上映播出文本模板', NOW(), NOW(), 'system', 'system', NULL, NULL, 0
WHERE NOT EXISTS (SELECT 1 FROM notify_template WHERE template_code = 'tmdb_daily_release_text' AND channel_type = 'wechatBot');
