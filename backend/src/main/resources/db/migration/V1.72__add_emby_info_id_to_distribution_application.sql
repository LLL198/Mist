-- Add emby_info_id column to distribution_application table
ALTER TABLE `distribution_application` ADD COLUMN `emby_info_id` bigint DEFAULT NULL COMMENT '指定服务器ID' AFTER `card_days`;
