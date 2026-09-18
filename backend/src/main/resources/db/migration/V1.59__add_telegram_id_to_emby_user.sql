-- 创建用户第三方登录绑定表
-- 支持多种登录方式：telegram, wechat, google 等
-- 支持逻辑删除（del_flag）

CREATE TABLE `user_oauth_binding`
(
    `id`                BIGINT       NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `user_id`           BIGINT       NOT NULL COMMENT '关联的 emby_user.id',
    `provider`          VARCHAR(32)  NOT NULL COMMENT '登录提供商：telegram, wechat, google 等',
    `provider_user_id`  VARCHAR(128) NOT NULL COMMENT '第三方平台用户ID',
    `provider_username` VARCHAR(128) DEFAULT NULL COMMENT '第三方平台用户名',
    `provider_avatar`   VARCHAR(512) DEFAULT NULL COMMENT '第三方平台头像URL',
    `extra_data`        JSON         DEFAULT NULL COMMENT '额外数据（JSON格式）',
    `del_flag`          TINYINT(1)   DEFAULT 0 COMMENT '删除标志（0代表存在 1代表删除）',
    `create_datetime`   DATETIME     DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_datetime`   DATETIME     DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`),
    KEY `idx_user_id` (`user_id`),
    KEY `idx_provider_user` (`provider`, `provider_user_id`),
    CONSTRAINT `fk_oauth_user` FOREIGN KEY (`user_id`) REFERENCES `emby_user` (`id`) ON DELETE CASCADE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci COMMENT ='用户第三方登录绑定表';
