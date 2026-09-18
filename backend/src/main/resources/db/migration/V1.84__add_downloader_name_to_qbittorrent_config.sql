-- 添加下载器名称字段到 qBittorrent 配置表
ALTER TABLE `movie_qbittorrent_config`
ADD COLUMN `downloader_name` varchar(100) DEFAULT NULL COMMENT '下载器名称';
