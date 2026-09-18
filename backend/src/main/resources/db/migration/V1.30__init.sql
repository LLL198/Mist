-- 邀请码表
CREATE TABLE IF NOT EXISTS `invitation_code` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键id',
    `code` VARCHAR(64) NOT NULL COMMENT '邀请码',
    `emby_info_id` BIGINT NOT NULL COMMENT '关联的Emby服务器ID',
    `status` TINYINT NOT NULL DEFAULT 0 COMMENT '使用状态 0未使用 1已使用',
    `usage_limit` INT NOT NULL DEFAULT 1 COMMENT '可使用次数，默认1',
    `used_count` INT NOT NULL DEFAULT 0 COMMENT '已使用次数',
    `used_by` VARCHAR(64) DEFAULT NULL COMMENT '使用人',
    `used_datetime` DATETIME DEFAULT NULL COMMENT '使用时间',
    `expire_datetime` DATETIME DEFAULT NULL COMMENT '过期时间',
    `create_datetime` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_datetime` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `create_user_name` VARCHAR(50) DEFAULT NULL COMMENT '创建人名称',
    `update_user_name` VARCHAR(50) DEFAULT NULL COMMENT '更新人名称',
    `update_user_id` BIGINT DEFAULT NULL COMMENT '修改人id',
    `create_user_id` BIGINT DEFAULT NULL COMMENT '创建人id',
    `del_flag` INT DEFAULT 0 COMMENT '是否删除 0 未删除 1 已删除',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_invitation_code_code` (`code`),
    KEY `idx_invitation_code_emby_info_id` (`emby_info_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='邀请码信息表';
