CREATE TABLE IF NOT EXISTS `movie_download_record_file` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `record_id` bigint NOT NULL COMMENT '下载记录ID',
  `source_file_path` varchar(1000) DEFAULT NULL COMMENT '原始文件路径',
  `link_file_path` varchar(1000) DEFAULT NULL COMMENT '硬链接文件路径',
  `episode_codes` varchar(255) DEFAULT NULL COMMENT '季集编码（如S01E01,S01E02）',
  `episode_seqs` varchar(100) DEFAULT NULL COMMENT '集数展示（如1-2）',
  `create_datetime` datetime DEFAULT NULL COMMENT '创建时间',
  `update_datetime` datetime DEFAULT NULL COMMENT '修改时间',
  `create_user_name` varchar(50) DEFAULT NULL COMMENT '创建人名称',
  `update_user_name` varchar(50) DEFAULT NULL COMMENT '修改人名称',
  `update_user_id` bigint DEFAULT NULL COMMENT '修改人id',
  `create_user_id` bigint DEFAULT NULL COMMENT '创建人id',
  `del_flag` int DEFAULT '0' COMMENT '是否删除 0 未删除 1 已删除',
  PRIMARY KEY (`id`),
  KEY `idx_movie_download_record_file_record_id` (`record_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='下载记录文件明细表';
