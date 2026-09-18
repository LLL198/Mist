CREATE TABLE IF NOT EXISTS `rose_user_binding` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `user_id` bigint NOT NULL COMMENT 'foam用户id',
  `emby_user_id` varchar(128) DEFAULT NULL COMMENT 'emby用户id',
  `emby_user_name` varchar(128) DEFAULT NULL COMMENT 'emby用户名',
  `emby_info_id` bigint DEFAULT NULL COMMENT 'emby服务器id',
  `rose_account_id` varchar(128) DEFAULT NULL COMMENT 'Rose账号id',
  `rose_account_name` varchar(255) DEFAULT NULL COMMENT 'Rose托管账号名称',
  `rose_user_id` varchar(128) DEFAULT NULL COMMENT '115用户id',
  `rose_username` varchar(255) DEFAULT NULL COMMENT '115用户名',
  `rose_mobile` varchar(64) DEFAULT NULL COMMENT '115手机号',
  `rose_avatar_url` varchar(1024) DEFAULT NULL COMMENT '115头像',
  `device_app` varchar(64) DEFAULT NULL COMMENT '扫码设备app',
  `cookie_text` longtext COMMENT 'Rose用户cookie密文原文，仅后端使用',
  `cookie_text_masked` varchar(255) DEFAULT NULL COMMENT '脱敏cookie',
  `target_root` varchar(255) DEFAULT NULL COMMENT '兼容模式目标目录',
  `libraries_json` longtext COMMENT '库目录绑定JSON',
  `binding_status` varchar(32) NOT NULL DEFAULT 'UNBOUND' COMMENT '绑定状态',
  `qr_session_id` varchar(128) DEFAULT NULL COMMENT '二维码会话id',
  `qr_status` varchar(64) DEFAULT NULL COMMENT '二维码状态',
  `qr_scan_url` varchar(1024) DEFAULT NULL COMMENT '二维码扫码地址',
  `last_error` varchar(1024) DEFAULT NULL COMMENT '最近一次错误',
  `rose_profile_json` longtext COMMENT 'Rose用户画像JSON',
  `account_summary_json` longtext COMMENT 'Rose账号摘要JSON',
  `cookie_updated_at` datetime DEFAULT NULL COMMENT 'cookie更新时间',
  `bound_at` datetime DEFAULT NULL COMMENT '绑定时间',
  `last_sync_at` datetime DEFAULT NULL COMMENT '最近同步时间',
  `create_datetime` datetime DEFAULT NULL COMMENT '创建时间',
  `update_datetime` datetime DEFAULT NULL COMMENT '修改时间',
  `create_user_name` varchar(255) DEFAULT NULL COMMENT '创建人名称',
  `update_user_name` varchar(255) DEFAULT NULL COMMENT '修改人名称',
  `update_user_id` bigint DEFAULT NULL COMMENT '修改人id',
  `create_user_id` bigint DEFAULT NULL COMMENT '创建人id',
  `del_flag` int NOT NULL DEFAULT 0 COMMENT '是否删除 0 未删除 1 已删除',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_rose_user_binding_user_id` (`user_id`),
  KEY `idx_rose_user_binding_emby_user_id` (`emby_user_id`),
  KEY `idx_rose_user_binding_emby_user_name` (`emby_user_name`),
  KEY `idx_rose_user_binding_status` (`binding_status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='Rose用户绑定表';

INSERT INTO `system_config` (`name`, `config_key`, `config_value`, `is_enabled`, `description`, `is_update`, `create_datetime`, `update_datetime`, `create_user_name`, `update_user_name`, `update_user_id`, `create_user_id`, `del_flag`)
SELECT 'Rose绑定入口', 'rose_binding_enabled', 'true', 0, '开启后，用户中心展示 Rose 绑定按钮，并允许用户通过 Foam 完成 Rose 扫码绑定。', 1, NOW(), NOW(), 'admin', 'admin', 1, 1, 0
WHERE NOT EXISTS (SELECT 1 FROM `system_config` WHERE `config_key` = 'rose_binding_enabled');

INSERT INTO `system_config` (`name`, `config_key`, `config_value`, `is_enabled`, `description`, `is_update`, `create_datetime`, `update_datetime`, `create_user_name`, `update_user_name`, `update_user_id`, `create_user_id`, `del_flag`)
SELECT 'Rose接口地址', 'rose_api_base_url', '', 1, 'Rose 服务基础地址，例如 http://127.0.0.1:8787。Foam 后端会通过该地址调用 Rose 接口。', 1, NOW(), NOW(), 'admin', 'admin', 1, 1, 0
WHERE NOT EXISTS (SELECT 1 FROM `system_config` WHERE `config_key` = 'rose_api_base_url');
