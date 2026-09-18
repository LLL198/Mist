ALTER TABLE `sys_notice`
    ADD COLUMN `notice_scope` int NOT NULL DEFAULT '0' COMMENT '公告范围 0=站内 1=站外' AFTER `notice_status`;

CREATE TABLE IF NOT EXISTS `support_ticket` (
    `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `title` varchar(128) NOT NULL COMMENT '工单标题',
    `content` text NOT NULL COMMENT '工单内容',
    `status` int NOT NULL DEFAULT '0' COMMENT '状态 0=待处理 1=已批准 2=已拒绝',
    `user_id` bigint DEFAULT NULL COMMENT '提交用户ID',
    `emby_user_name` varchar(50) DEFAULT NULL COMMENT '提交用户名称',
    `reply_count` int NOT NULL DEFAULT '0' COMMENT '回复数量',
    `last_reply_content` varchar(500) DEFAULT NULL COMMENT '最后回复内容',
    `last_reply_user_name` varchar(50) DEFAULT NULL COMMENT '最后回复人',
    `last_reply_datetime` datetime DEFAULT NULL COMMENT '最后回复时间',
    `create_datetime` datetime DEFAULT NULL COMMENT '创建时间',
    `update_datetime` datetime DEFAULT NULL COMMENT '修改时间',
    `create_user_name` varchar(50) DEFAULT NULL COMMENT '创建人名称',
    `update_user_name` varchar(50) DEFAULT NULL COMMENT '修改人名称',
    `update_user_id` bigint DEFAULT NULL COMMENT '修改人id',
    `create_user_id` bigint DEFAULT NULL COMMENT '创建人id',
    `del_flag` int NOT NULL DEFAULT '0' COMMENT '是否删除 0 未删除 1 已删除',
    PRIMARY KEY (`id`),
    KEY `idx_support_ticket_user_status` (`user_id`, `status`),
    KEY `idx_support_ticket_status` (`status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='工单';

CREATE TABLE IF NOT EXISTS `support_ticket_reply` (
    `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `ticket_id` bigint NOT NULL COMMENT '工单ID',
    `reply_content` text NOT NULL COMMENT '回复内容',
    `user_id` bigint DEFAULT NULL COMMENT '回复用户ID',
    `emby_user_name` varchar(50) DEFAULT NULL COMMENT '回复用户名称',
    `reply_role` int NOT NULL DEFAULT '0' COMMENT '回复角色 0=用户 1=管理员',
    `create_datetime` datetime DEFAULT NULL COMMENT '创建时间',
    `update_datetime` datetime DEFAULT NULL COMMENT '修改时间',
    `create_user_name` varchar(50) DEFAULT NULL COMMENT '创建人名称',
    `update_user_name` varchar(50) DEFAULT NULL COMMENT '修改人名称',
    `update_user_id` bigint DEFAULT NULL COMMENT '修改人id',
    `create_user_id` bigint DEFAULT NULL COMMENT '创建人id',
    `del_flag` int NOT NULL DEFAULT '0' COMMENT '是否删除 0 未删除 1 已删除',
    PRIMARY KEY (`id`),
    KEY `idx_support_ticket_reply_ticket` (`ticket_id`, `id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='工单回复';
