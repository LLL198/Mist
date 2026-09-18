CREATE TABLE `points_bot_foam_bag_config` (
    `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键id',
    `amount_tiers_json` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '可选积分档位JSON',
    `daily_limit` int NOT NULL DEFAULT 3 COMMENT '每日成功使用次数上限',
    `repayment_multiplier` int NOT NULL DEFAULT 2 COMMENT '归还倍数',
    `repayment_hours` int NOT NULL DEFAULT 24 COMMENT '归还期限小时数',
    `penalty_days` int NOT NULL DEFAULT 7 COMMENT '逾期限制天数',
    `allow_unbound_users` tinyint(1) NOT NULL DEFAULT 1 COMMENT '是否允许未绑定用户使用',
    `create_datetime` datetime DEFAULT NULL COMMENT '创建时间',
    `update_datetime` datetime DEFAULT NULL COMMENT '修改时间',
    `create_user_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '创建人名称',
    `update_user_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '修改人名称',
    `update_user_id` bigint DEFAULT NULL COMMENT '修改人id',
    `create_user_id` bigint DEFAULT NULL COMMENT '创建人id',
    `del_flag` int DEFAULT '0' COMMENT '是否删除 0 未删除 1 已删除',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='积分机器人泡沫袋配置';

INSERT INTO `points_bot_foam_bag_config`
    (`id`, `amount_tiers_json`, `daily_limit`, `repayment_multiplier`, `repayment_hours`,
     `penalty_days`, `allow_unbound_users`)
VALUES
    (1, '[10,300,500]', 3, 2, 24, 7, 1);

CREATE TABLE `points_bot_foam_bag` (
    `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键id',
    `chat_id` bigint NOT NULL COMMENT '积分群/频道ID',
    `user_id` bigint NOT NULL COMMENT 'Telegram用户ID',
    `username` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT 'Telegram用户名快照',
    `display_name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '展示名称快照',
    `principal_points` int NOT NULL COMMENT '获得积分',
    `repayment_points` int NOT NULL COMMENT '应归还积分',
    `repayment_multiplier` int NOT NULL COMMENT '归还倍数快照',
    `repayment_hours` int NOT NULL COMMENT '归还期限快照',
    `penalty_days` int NOT NULL COMMENT '逾期限制天数快照',
    `status` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '状态 ACTIVE/REPAID/PENALIZED',
    `active_guard` tinyint DEFAULT NULL COMMENT '活动记录唯一约束标记',
    `borrowed_at` datetime NOT NULL COMMENT '获得时间',
    `due_at` datetime NOT NULL COMMENT '最晚归还时间',
    `repaid_at` datetime DEFAULT NULL COMMENT '实际归还时间',
    `penalized_at` datetime DEFAULT NULL COMMENT '逾期处理时间',
    `penalty_until` datetime DEFAULT NULL COMMENT '限制结束时间',
    `wiped_points` bigint NOT NULL DEFAULT 0 COMMENT '逾期归零积分数',
    `create_datetime` datetime DEFAULT NULL COMMENT '创建时间',
    `update_datetime` datetime DEFAULT NULL COMMENT '修改时间',
    `create_user_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '创建人名称',
    `update_user_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '修改人名称',
    `update_user_id` bigint DEFAULT NULL COMMENT '修改人id',
    `create_user_id` bigint DEFAULT NULL COMMENT '创建人id',
    `del_flag` int DEFAULT '0' COMMENT '是否删除 0 未删除 1 已删除',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uniq_foam_bag_active_user` (`chat_id`, `user_id`, `active_guard`),
    KEY `idx_foam_bag_due` (`status`, `due_at`, `id`),
    KEY `idx_foam_bag_user_history` (`user_id`, `borrowed_at`, `id`),
    KEY `idx_foam_bag_chat_user_history` (`chat_id`, `user_id`, `borrowed_at`, `id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='积分机器人泡沫袋记录';

UPDATE `notify_channel`
SET `params` = JSON_SET(
        COALESCE(NULLIF(`params`, ''), '{}'),
        '$.foamBagEnabled',
        COALESCE(
            JSON_EXTRACT(COALESCE(NULLIF(`params`, ''), '{}'), '$.foamBagEnabled'),
            CAST('true' AS JSON)
        )
    )
WHERE `icon_type` IN ('telegram', 'pointsBot')
  AND `del_flag` = 0
  AND JSON_VALID(COALESCE(NULLIF(`params`, ''), '{}'))
  AND JSON_TYPE(COALESCE(NULLIF(`params`, ''), '{}')) = 'OBJECT';
