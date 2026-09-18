-- 更新 qBittorrent 配置表，移除下载路径字段
ALTER TABLE `movie_qbittorrent_config`
  DROP COLUMN `save_path`,
  DROP COLUMN `download_paths`;

-- 刮削目录配置表
CREATE TABLE IF NOT EXISTS `movie_scrape_path_config` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `qb_download_path` varchar(255) DEFAULT NULL COMMENT 'qBittorrent 下载路径',
  `hardlink_path` varchar(255) DEFAULT NULL COMMENT '硬链接目标路径',
  `create_datetime` datetime DEFAULT NULL COMMENT '创建时间',
  `update_datetime` datetime DEFAULT NULL COMMENT '修改时间',
  `create_user_name` varchar(50) DEFAULT NULL COMMENT '创建人名称',
  `update_user_name` varchar(50) DEFAULT NULL COMMENT '修改人名称',
  `update_user_id` bigint DEFAULT NULL COMMENT '修改人id',
  `create_user_id` bigint DEFAULT NULL COMMENT '创建人id',
  `del_flag` int DEFAULT '0' COMMENT '是否删除 0 未删除 1 已删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='刮削目录配置表';

-- 下载记录表
CREATE TABLE IF NOT EXISTS `movie_download_record` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `movie_name` varchar(255) DEFAULT NULL COMMENT '影片名称',
  `movie_year` varchar(20) DEFAULT NULL COMMENT '影片年份',
  `poster_url` varchar(1000) DEFAULT NULL COMMENT '海报图',
  `cover_url` varchar(1000) DEFAULT NULL COMMENT '封面图',
  `qb_download_path` varchar(255) DEFAULT NULL COMMENT 'qBittorrent 下载路径',
  `hardlink_path` varchar(255) DEFAULT NULL COMMENT '硬链接目标路径',
  `qb_tag` varchar(100) DEFAULT NULL COMMENT 'qBittorrent 标签',
  `qb_hash` varchar(100) DEFAULT NULL COMMENT 'qBittorrent Hash',
  `qb_torrent_name` varchar(255) DEFAULT NULL COMMENT 'qBittorrent 任务名称',
  `status` varchar(50) DEFAULT NULL COMMENT '下载状态',
  `create_datetime` datetime DEFAULT NULL COMMENT '创建时间',
  `update_datetime` datetime DEFAULT NULL COMMENT '修改时间',
  `create_user_name` varchar(50) DEFAULT NULL COMMENT '创建人名称',
  `update_user_name` varchar(50) DEFAULT NULL COMMENT '修改人名称',
  `update_user_id` bigint DEFAULT NULL COMMENT '修改人id',
  `create_user_id` bigint DEFAULT NULL COMMENT '创建人id',
  `del_flag` int DEFAULT '0' COMMENT '是否删除 0 未删除 1 已删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='下载记录表';
