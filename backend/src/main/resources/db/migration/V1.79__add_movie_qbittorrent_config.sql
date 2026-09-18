-- qBittorrent 配置表
CREATE TABLE IF NOT EXISTS `movie_qbittorrent_config` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `host` varchar(255) DEFAULT NULL COMMENT 'WebUI 地址',
  `username` varchar(100) DEFAULT NULL COMMENT '用户名',
  `password` varchar(255) DEFAULT NULL COMMENT '密码',
  `save_path` varchar(255) DEFAULT NULL COMMENT '默认保存路径',
  `download_paths` text COMMENT '常用下载路径(JSON 数组)',
  `create_datetime` datetime DEFAULT NULL COMMENT '创建时间',
  `update_datetime` datetime DEFAULT NULL COMMENT '修改时间',
  `create_user_name` varchar(50) DEFAULT NULL COMMENT '创建人名称',
  `update_user_name` varchar(50) DEFAULT NULL COMMENT '修改人名称',
  `update_user_id` bigint DEFAULT NULL COMMENT '修改人id',
  `create_user_id` bigint DEFAULT NULL COMMENT '创建人id',
  `del_flag` int DEFAULT '0' COMMENT '是否删除 0 未删除 1 已删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='qBittorrent 配置表';
