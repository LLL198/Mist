CREATE TABLE IF NOT EXISTS `telegram_binding_review_message` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `review_id` BIGINT NOT NULL COMMENT 'Telegram绑定审批记录ID',
    `chat_id` BIGINT NOT NULL COMMENT '管理员Telegram Chat ID',
    `message_id` INT NOT NULL COMMENT '审批卡片Message ID',
    `create_datetime` DATETIME DEFAULT NULL,
    `update_datetime` DATETIME DEFAULT NULL,
    `create_user_name` VARCHAR(255) DEFAULT NULL,
    `update_user_name` VARCHAR(255) DEFAULT NULL,
    `update_user_id` BIGINT DEFAULT NULL,
    `create_user_id` BIGINT DEFAULT NULL,
    `del_flag` TINYINT NOT NULL DEFAULT 0,
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_tg_binding_review_message_chat` (`review_id`, `chat_id`),
    KEY `idx_tg_binding_review_message_review` (`review_id`, `del_flag`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci
  COMMENT='Telegram绑定审批管理员消息坐标';
