-- Add is_distributor column to emby_user table
ALTER TABLE `emby_user` ADD COLUMN `is_distributor` int DEFAULT '0' COMMENT '是否为分销商 0 否 1 是';

-- Add distributor_id column to card_security_management table
ALTER TABLE `card_security_management` ADD COLUMN `distributor_id` bigint DEFAULT NULL COMMENT '分销商ID';
CREATE INDEX `idx_card_distributor_id` ON `card_security_management` (`distributor_id`);

-- Create points_exchange_product table
CREATE TABLE `points_exchange_product` (
    `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键id',
    `name` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '商品名称',
    `product_type` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '商品类型 CARD_KEY 卡密, DURATION_EXTENSION 时长',
    `points_cost` int NOT NULL COMMENT '消耗积分',
    `product_value` int NOT NULL COMMENT '商品值(天数)',
    `is_enabled` int DEFAULT '1' COMMENT '是否上架 0 下架 1 上架',
    `sort_order` int DEFAULT '0' COMMENT '排序',
    `create_datetime` datetime DEFAULT NULL COMMENT '创建时间',
    `update_datetime` datetime DEFAULT NULL COMMENT '修改时间',
    `create_user_name` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '创建人名称',
    `update_user_name` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '修改人名称',
    `update_user_id` bigint DEFAULT NULL COMMENT '修改人id',
    `create_user_id` bigint DEFAULT NULL COMMENT '创建人id',
    `del_flag` int DEFAULT '0' COMMENT '是否删除 0 未删除 1 已删除',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='积分兑换商品表';

-- Create points_record table
CREATE TABLE `points_record` (
    `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键id',
    `user_id` bigint NOT NULL COMMENT '用户id',
    `record_type` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '记录类型 INVITE_REWARD 邀请奖励, EXCHANGE_SPEND 兑换消耗, ADMIN_ADJUST 管理员调整',
    `amount` int NOT NULL COMMENT '积分变动数量',
    `balance_after` int DEFAULT NULL COMMENT '变动后余额(选填快照)',
    `description` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '描述',
    `create_datetime` datetime DEFAULT NULL COMMENT '创建时间',
    `update_datetime` datetime DEFAULT NULL COMMENT '修改时间',
    `create_user_name` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '创建人名称',
    `update_user_name` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '修改人名称',
    `update_user_id` bigint DEFAULT NULL COMMENT '修改人id',
    `create_user_id` bigint DEFAULT NULL COMMENT '创建人id',
    `del_flag` int DEFAULT '0' COMMENT '是否删除 0 未删除 1 已删除',
    PRIMARY KEY (`id`),
    KEY `idx_points_user_id` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='积分变动记录表';

-- Create distribution_application table
CREATE TABLE `distribution_application` (
    `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键id',
    `user_id` bigint NOT NULL COMMENT '申请人id',
    `card_count` int NOT NULL COMMENT '申请卡密数量',
    `card_days` int NOT NULL COMMENT '卡密天数',
    `status` int DEFAULT '0' COMMENT '状态 0 待审批 1 已通过 2 已拒绝',
    `review_comment` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '审批备注',
    `create_datetime` datetime DEFAULT NULL COMMENT '创建时间',
    `update_datetime` datetime DEFAULT NULL COMMENT '修改时间',
    `create_user_name` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '创建人名称',
    `update_user_name` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '修改人名称',
    `update_user_id` bigint DEFAULT NULL COMMENT '修改人id',
    `create_user_id` bigint DEFAULT NULL COMMENT '创建人id',
    `del_flag` int DEFAULT '0' COMMENT '是否删除 0 未删除 1 已删除',
    PRIMARY KEY (`id`),
    KEY `idx_application_user_id` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='分销卡密申请表';

-- Create user_invitation table
CREATE TABLE `user_invitation` (
    `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键id',
    `inviter_id` bigint NOT NULL COMMENT '邀请人id',
    `invitee_id` bigint NOT NULL COMMENT '被邀请人id',
    `invitation_source` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT 'CARD_KEY' COMMENT '邀请来源 CARD_KEY 卡密, LINK 链接',
    `create_datetime` datetime DEFAULT NULL COMMENT '创建时间',
    `update_datetime` datetime DEFAULT NULL COMMENT '修改时间',
    `create_user_name` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '创建人名称',
    `update_user_name` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '修改人名称',
    `update_user_id` bigint DEFAULT NULL COMMENT '修改人id',
    `create_user_id` bigint DEFAULT NULL COMMENT '创建人id',
    `del_flag` int DEFAULT '0' COMMENT '是否删除 0 未删除 1 已删除',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uniq_invitee` (`invitee_id`),
    KEY `idx_inviter` (`inviter_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='用户邀请关系表';

-- Add points column to emby_user table if not exists (assuming it might be needed, or we use a separate table for wallet balance?)
-- Using a separate table approach for wallet might be cleaner or just adding to user table.
-- Let's check the plan. Plan said "UserPoints" entity. But simpler to add points to emby_user or maintain in a separate table?
-- The plan mentioned `user_points` table stores balance. Let's create it.

CREATE TABLE `user_points` (
    `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键id',
    `user_id` bigint NOT NULL COMMENT '用户id',
    `points_balance` int DEFAULT '0' COMMENT '当前积分余额',
    `total_earned` int DEFAULT '0' COMMENT '累计获得积分',
    `total_spent` int DEFAULT '0' COMMENT '累计消耗积分',
    `create_datetime` datetime DEFAULT NULL COMMENT '创建时间',
    `update_datetime` datetime DEFAULT NULL COMMENT '修改时间',
    `create_user_name` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '创建人名称',
    `update_user_name` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '修改人名称',
    `update_user_id` bigint DEFAULT NULL COMMENT '修改人id',
    `create_user_id` bigint DEFAULT NULL COMMENT '创建人id',
    `del_flag` int DEFAULT '0' COMMENT '是否删除 0 未删除 1 已删除',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uniq_user_points` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='用户积分钱包表';
