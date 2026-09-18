-- 同时播放记录主表
CREATE TABLE IF NOT EXISTS `simultaneous_playback_record` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键id',
    `emby_info_id` BIGINT DEFAULT NULL COMMENT '服务器ID',
    `emby_user_id` VARCHAR(64) DEFAULT NULL COMMENT 'emby用户id',
    `emby_user_name` VARCHAR(500) DEFAULT NULL COMMENT 'emby用户名',
    `detection_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '检测时间',
    `session_count` INT DEFAULT NULL COMMENT '同时播放会话数量',
    `create_datetime` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_datetime` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
    `create_user_name` VARCHAR(50) DEFAULT NULL COMMENT '创建人名称',
    `update_user_name` VARCHAR(50) DEFAULT NULL COMMENT '修改人名称',
    `create_user_id` BIGINT DEFAULT NULL COMMENT '创建人id',
    `update_user_id` BIGINT DEFAULT NULL COMMENT '修改人id',
    `del_flag` INT DEFAULT 0 COMMENT '是否删除 0 未删除 1 已删除',
    PRIMARY KEY (`id`),
    KEY `idx_detection_time` (`detection_time`),
    KEY `idx_emby_user_name` (`emby_user_name`),
    KEY `idx_emby_info_id` (`emby_info_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='同时播放记录';

-- 同时播放记录明细表
CREATE TABLE IF NOT EXISTS `simultaneous_playback_record_detail` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键id',
    `record_id` BIGINT NOT NULL COMMENT '同时播放记录id',
    `item_id` VARCHAR(100) DEFAULT NULL COMMENT '播放项目ID',
    `item_name` VARCHAR(500) DEFAULT NULL COMMENT '播放内容',
    `item_type` VARCHAR(50) DEFAULT NULL COMMENT '内容类型',
    `poster_url` VARCHAR(512) DEFAULT NULL COMMENT '海报链接',
    `playback_time` DATETIME DEFAULT NULL COMMENT '播放时间',
    `client` VARCHAR(200) DEFAULT NULL COMMENT '客户端',
    `device_name` VARCHAR(200) DEFAULT NULL COMMENT '设备名称',
    `remote_endpoint` VARCHAR(100) DEFAULT NULL COMMENT '播放地址',
    `remote_address` VARCHAR(255) DEFAULT NULL COMMENT '地址信息',
    `create_datetime` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_datetime` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
    `create_user_name` VARCHAR(50) DEFAULT NULL COMMENT '创建人名称',
    `update_user_name` VARCHAR(50) DEFAULT NULL COMMENT '修改人名称',
    `create_user_id` BIGINT DEFAULT NULL COMMENT '创建人id',
    `update_user_id` BIGINT DEFAULT NULL COMMENT '修改人id',
    `del_flag` INT DEFAULT 0 COMMENT '是否删除 0 未删除 1 已删除',
    PRIMARY KEY (`id`),
    KEY `idx_record_id` (`record_id`),
    KEY `idx_playback_time` (`playback_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='同时播放记录明细';
