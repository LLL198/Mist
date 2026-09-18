CREATE TABLE IF NOT EXISTS `emby_region_block_rule`
(
    `id`               BIGINT       NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `rule_code`        VARCHAR(320) NOT NULL COMMENT '规则编码 COUNTRY:<country> 或 PROVINCE:<country>:<province>',
    `rule_type`        VARCHAR(16)  NOT NULL COMMENT '规则类型 COUNTRY/PROVINCE',
    `country`          VARCHAR(128) NOT NULL COMMENT 'IP库国家名称',
    `province`         VARCHAR(128)          DEFAULT NULL COMMENT 'IP库省级地区名称',
    `display_name`     VARCHAR(320) NOT NULL COMMENT '规则展示名称',
    `enabled`          TINYINT      NOT NULL DEFAULT 0 COMMENT '是否启用 0-禁用 1-启用',
    `create_datetime`  DATETIME              DEFAULT NULL COMMENT '创建时间',
    `update_datetime`  DATETIME              DEFAULT NULL COMMENT '修改时间',
    `create_user_name` VARCHAR(50)            DEFAULT NULL COMMENT '创建人名称',
    `update_user_name` VARCHAR(50)            DEFAULT NULL COMMENT '修改人名称',
    `update_user_id`   BIGINT                 DEFAULT NULL COMMENT '修改人ID',
    `create_user_id`   BIGINT                 DEFAULT NULL COMMENT '创建人ID',
    `del_flag`         INT          NOT NULL DEFAULT 0 COMMENT '是否删除 0-未删除 1-已删除',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_emby_region_block_rule_code` (`rule_code`),
    KEY `idx_emby_region_block_rule_enabled` (`enabled`, `rule_type`),
    KEY `idx_emby_region_block_rule_location` (`country`, `province`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='Emby地区拦截规则';

ALTER TABLE `emby_client_filter_record`
    ADD COLUMN `filter_type` VARCHAR(16) NOT NULL DEFAULT 'UA' COMMENT '拦截类型 UA/REGION' AFTER `event_name`,
    ADD COLUMN `resolved_ip` VARCHAR(64) DEFAULT NULL COMMENT '解析后的客户端IP' AFTER `remote_endpoint`,
    ADD COLUMN `country` VARCHAR(128) DEFAULT NULL COMMENT 'IP归属国家' AFTER `resolved_ip`,
    ADD COLUMN `province` VARCHAR(128) DEFAULT NULL COMMENT 'IP归属省级地区' AFTER `country`,
    ADD COLUMN `city` VARCHAR(128) DEFAULT NULL COMMENT 'IP归属城市' AFTER `province`,
    ADD KEY `idx_filter_type_trigger_time` (`filter_type`, `trigger_time`),
    ADD KEY `idx_region_location` (`country`, `province`);
