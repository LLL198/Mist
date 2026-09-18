-- PT 站点配置表
CREATE TABLE IF NOT EXISTS `movie_pt_site` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `name` varchar(100) NOT NULL COMMENT '站点名称',
  `base_url` varchar(255) NOT NULL COMMENT '站点地址',
  `cookies` text COMMENT '站点 Cookies',
  `token` text COMMENT '站点 Token 或自定义请求头',
  `site_type` varchar(50) NOT NULL DEFAULT 'nexusphp' COMMENT '站点类型（mteam/nexusphp）',
  `enabled` tinyint(1) NOT NULL DEFAULT 1 COMMENT '是否启用（1 启用 0 禁用）',
  `create_datetime` datetime DEFAULT NULL COMMENT '创建时间',
  `update_datetime` datetime DEFAULT NULL COMMENT '修改时间',
  `create_user_name` varchar(50) DEFAULT NULL COMMENT '创建人名称',
  `update_user_name` varchar(50) DEFAULT NULL COMMENT '修改人名称',
  `update_user_id` bigint DEFAULT NULL COMMENT '修改人id',
  `create_user_id` bigint DEFAULT NULL COMMENT '创建人id',
  `del_flag` int DEFAULT '0' COMMENT '是否删除 0 未删除 1 已删除',
  PRIMARY KEY (`id`),
  KEY `idx_movie_pt_site_enabled` (`enabled`, `del_flag`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='PT站点配置表';
