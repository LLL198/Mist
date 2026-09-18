ALTER TABLE `points_bot_user`
    ADD COLUMN `level_id` bigint DEFAULT NULL COMMENT '等级ID' AFTER `display_name`,
    ADD COLUMN `level_name` varchar(50) DEFAULT NULL COMMENT '等级名称' AFTER `level_id`;

CREATE TABLE `points_bot_level_config` (
    `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `level_name` varchar(50) NOT NULL COMMENT '等级名称',
    `min_points` int NOT NULL COMMENT '达成等级的最低积分',
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
    KEY `idx_points_bot_level_enabled` (`enabled`),
    KEY `idx_points_bot_level_points` (`min_points`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='积分等级配置表';

CREATE TABLE `points_bot_prize_config` (
    `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `prize_name` varchar(100) NOT NULL COMMENT '奖品名称',
    `required_points` int DEFAULT NULL COMMENT '兑换所需积分',
    `level_id` bigint DEFAULT NULL COMMENT '关联的等级ID',
    `total_quantity` int NOT NULL COMMENT '总数量',
    `remaining_quantity` int NOT NULL COMMENT '剩余数量',
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
    KEY `idx_points_bot_prize_enabled` (`enabled`),
    KEY `idx_points_bot_prize_level` (`level_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='积分奖品配置表';

INSERT INTO `points_bot_level_config` (`level_name`, `min_points`, `enabled`, `sort`, `remark`)
VALUES ('青铜', 50, 1, 10, '默认等级'),
       ('白银', 200, 1, 20, '默认等级'),
       ('黄金', 500, 1, 30, '默认等级');
