CREATE TABLE IF NOT EXISTS `sys_notice_read` (
    `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `notice_id` bigint NOT NULL COMMENT '公告ID',
    `user_id` bigint NOT NULL COMMENT '用户ID',
    `read_datetime` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '阅读时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_sys_notice_read_notice_user` (`notice_id`, `user_id`),
    KEY `idx_sys_notice_read_user` (`user_id`),
    KEY `idx_sys_notice_read_notice` (`notice_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='系统公告已读记录';
