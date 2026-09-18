ALTER TABLE `movie_download_record` ADD COLUMN `site_id` bigint(20) DEFAULT NULL COMMENT '站点ID' AFTER `downloader_id`;
