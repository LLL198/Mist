-- 主机线路表增量脚本（分协议、域名、端口存储，子表关联 emby_info 主表）
CREATE TABLE IF NOT EXISTS `host_line` (
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `emby_info_id` BIGINT NOT NULL COMMENT '所属 Emby 服务器ID',
  `line_name` VARCHAR(255) NOT NULL COMMENT '线路名称',
  `protocol` VARCHAR(16) NOT NULL COMMENT '协议，如 http、https',
  `domain` VARCHAR(255) NOT NULL COMMENT '域名或主机名',
  `port` INT NOT NULL COMMENT '端口号',
  `is_display` TINYINT(1) NOT NULL DEFAULT 1 COMMENT '是否展示（0 不展示 1 展示）',
  `enabled` TINYINT(1) NOT NULL DEFAULT 1 COMMENT '是否启用（0 禁用 1 启用）',
  `sort_no` INT DEFAULT 0 COMMENT '展示排序，值越小越靠前',
  `remark` VARCHAR(500) DEFAULT NULL COMMENT '备注说明',
  `create_datetime` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_datetime` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `create_user_name` VARCHAR(255) DEFAULT NULL COMMENT '创建人名称',
  `update_user_name` VARCHAR(255) DEFAULT NULL COMMENT '修改人名称',
  `update_user_id` BIGINT DEFAULT NULL COMMENT '修改人ID',
  `create_user_id` BIGINT DEFAULT NULL COMMENT '创建人ID',
  `del_flag` TINYINT(1) NOT NULL DEFAULT 0 COMMENT '删除标记 0 未删除 1 已删除',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_host_line_name` (`emby_info_id`, `line_name`),
  UNIQUE KEY `uk_host_line_endpoint` (`emby_info_id`, `protocol`, `domain`, `port`),
  CONSTRAINT `fk_host_line_emby_info` FOREIGN KEY (`emby_info_id`) REFERENCES `emby_info` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='主机多线路配置表';
