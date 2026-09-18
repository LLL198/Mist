CREATE TABLE `points_bot_redeem_config` (
    `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `config_name` varchar(100) NOT NULL COMMENT '配置名称',
    `redeem_days` int NOT NULL COMMENT '兑换天数',
    `emby_info_id` bigint NOT NULL COMMENT '关联的Emby服务器ID',
    `enabled` tinyint NOT NULL DEFAULT '1' COMMENT '启用状态 1=启用 0=停用',
    `sort` int DEFAULT '0' COMMENT '排序值（越大越靠前）',
    `remark` varchar(255) DEFAULT NULL COMMENT '备注说明',
    `create_datetime` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_datetime` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `create_user_name` varchar(64) DEFAULT NULL COMMENT '创建人名称',
    `update_user_name` varchar(64) DEFAULT NULL COMMENT '修改人名称',
    `update_user_id` bigint DEFAULT NULL COMMENT '修改人id',
    `create_user_id` bigint DEFAULT NULL COMMENT '创建人id',
    `del_flag` tinyint NOT NULL DEFAULT '0' COMMENT '是否删除 0 未删除 1 已删除',
    PRIMARY KEY (`id`),
    KEY `idx_points_bot_redeem_enabled` (`enabled`),
    KEY `idx_points_bot_redeem_emby` (`emby_info_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='积分兑换配置表';
