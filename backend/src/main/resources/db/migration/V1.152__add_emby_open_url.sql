ALTER TABLE `emby_info`
    ADD COLUMN `emby_open_url` varchar(500) DEFAULT NULL COMMENT 'Emby Web 外部打开地址' AFTER `emby_url`;
