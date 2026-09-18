-- 企业微信IP配置表
CREATE TABLE IF NOT EXISTS `wechat_ip_config` (
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `corp_id` VARCHAR(64) NOT NULL COMMENT '企业ID',
  `corp_name` VARCHAR(128) DEFAULT NULL COMMENT '企业名称',
  `app_ids` VARCHAR(512) DEFAULT NULL COMMENT '应用ID列表，逗号分隔',
  `last_ip` VARCHAR(64) DEFAULT NULL COMMENT '最后更新的公网IP',
  `last_ip_update_time` DATETIME DEFAULT NULL COMMENT 'IP最后更新时间',
  `enabled` TINYINT DEFAULT 1 COMMENT '是否启用 0-禁用 1-启用',
  `remark` VARCHAR(255) DEFAULT NULL COMMENT '备注',
  `create_datetime` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_datetime` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `create_user_name` VARCHAR(64) DEFAULT NULL COMMENT '创建人名称',
  `update_user_name` VARCHAR(64) DEFAULT NULL COMMENT '更新人名称',
  `create_user_id` BIGINT DEFAULT NULL COMMENT '创建人ID',
  `update_user_id` BIGINT DEFAULT NULL COMMENT '更新人ID',
  `del_flag` TINYINT DEFAULT 0 COMMENT '删除标志 0-未删除 1-已删除',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_corp_id` (`corp_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='企业微信IP配置表';
