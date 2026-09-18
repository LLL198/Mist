ALTER TABLE `request_list`
    ADD COLUMN `douban_id` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '豆瓣ID' AFTER `status`,
    ADD COLUMN `douban_url` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '豆瓣详情链接' AFTER `douban_id`,
    ADD COLUMN `douban_score` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '豆瓣评分' AFTER `douban_url`,
    ADD COLUMN `douban_image` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '豆瓣海报' AFTER `douban_score`;

CREATE INDEX `idx_request_list_douban_id` ON `request_list` (`douban_id`);