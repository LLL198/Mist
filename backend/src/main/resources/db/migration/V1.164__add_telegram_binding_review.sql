CREATE TABLE IF NOT EXISTS `telegram_binding_review` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `user_id` BIGINT NOT NULL COMMENT 'Emby用户ID',
    `emby_user_name` VARCHAR(255) NOT NULL COMMENT 'Emby用户名快照',
    `telegram_user_id` VARCHAR(64) NOT NULL COMMENT 'Telegram用户ID',
    `telegram_username` VARCHAR(255) DEFAULT NULL COMMENT 'Telegram用户名',
    `telegram_avatar` VARCHAR(1024) DEFAULT NULL COMMENT 'Telegram头像',
    `action_type` VARCHAR(16) NOT NULL COMMENT '操作类型 BIND/UNBIND',
    `request_source` VARCHAR(32) NOT NULL COMMENT '申请来源',
    `replace_existing` TINYINT NOT NULL DEFAULT 0 COMMENT '绑定通过时是否允许替换原绑定',
    `status` TINYINT NOT NULL DEFAULT 0 COMMENT '状态 0待审批 1已通过 2已拒绝',
    `reviewer_user_id` BIGINT DEFAULT NULL COMMENT '审批人用户ID',
    `reviewer_user_name` VARCHAR(255) DEFAULT NULL COMMENT '审批人用户名',
    `review_remark` VARCHAR(500) DEFAULT NULL COMMENT '审批备注',
    `review_datetime` DATETIME DEFAULT NULL COMMENT '审批时间',
    `create_datetime` DATETIME DEFAULT NULL,
    `update_datetime` DATETIME DEFAULT NULL,
    `create_user_name` VARCHAR(255) DEFAULT NULL,
    `update_user_name` VARCHAR(255) DEFAULT NULL,
    `update_user_id` BIGINT DEFAULT NULL,
    `create_user_id` BIGINT DEFAULT NULL,
    `del_flag` TINYINT NOT NULL DEFAULT 0,
    `pending_user_action_key` VARCHAR(96)
        GENERATED ALWAYS AS (
            CASE
                WHEN `status` = 0 AND `del_flag` = 0
                    THEN CAST(`user_id` AS CHAR)
                ELSE NULL
            END
        ) STORED,
    `pending_telegram_action_key` VARCHAR(128)
        GENERATED ALWAYS AS (
            CASE
                WHEN `status` = 0 AND `del_flag` = 0
                    THEN `telegram_user_id`
                ELSE NULL
            END
        ) STORED,
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_tg_binding_review_pending_user_action` (`pending_user_action_key`),
    UNIQUE KEY `uk_tg_binding_review_pending_tg_action` (`pending_telegram_action_key`),
    KEY `idx_tg_binding_review_status_created` (`status`, `create_datetime`),
    KEY `idx_tg_binding_review_user_created` (`user_id`, `create_datetime`),
    KEY `idx_tg_binding_review_tg_created` (`telegram_user_id`, `create_datetime`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='Telegram绑定解绑审批记录';

INSERT INTO `system_config`
(`name`, `config_key`, `config_value`, `is_enabled`, `description`, `is_update`,
 `create_datetime`, `update_datetime`, `create_user_name`, `update_user_name`,
 `update_user_id`, `create_user_id`, `del_flag`)
SELECT
    'TG绑定解绑审批',
    'telegram_binding_review_enabled',
    'true',
    0,
    '开启后，用户主动绑定或解绑 Telegram 都需要管理员审批；默认关闭。',
    1,
    NOW(),
    NOW(),
    'admin',
    'admin',
    1,
    1,
    0
WHERE NOT EXISTS (
    SELECT 1 FROM `system_config`
    WHERE `config_key` = 'telegram_binding_review_enabled'
);

INSERT INTO `system_config`
(`name`, `config_key`, `config_value`, `is_enabled`, `description`, `is_update`,
 `create_datetime`, `update_datetime`, `create_user_name`, `update_user_name`,
 `update_user_id`, `create_user_id`, `del_flag`)
SELECT
    'TG绑定审批机器人通知',
    'telegram_binding_review_notify_enabled',
    'true',
    0,
    '开启后，将待审批申请发送给 Telegram 管理员，并提供同意和拒绝内联按钮；默认关闭。',
    1,
    NOW(),
    NOW(),
    'admin',
    'admin',
    1,
    1,
    0
WHERE NOT EXISTS (
    SELECT 1 FROM `system_config`
    WHERE `config_key` = 'telegram_binding_review_notify_enabled'
);
