CREATE TABLE `payment_purchase_setting` (
  `id` bigint NOT NULL COMMENT '单例主键，固定为 1',
  `enabled` tinyint NOT NULL DEFAULT 0 COMMENT '登录页售卡入口是否开启',
  `api_base_url` varchar(512) DEFAULT NULL COMMENT '易支付接口根地址',
  `merchant_pid` varchar(128) DEFAULT NULL COMMENT '易支付商户号',
  `merchant_key_cipher` text DEFAULT NULL COMMENT 'AES-GCM 加密后的易支付商户密钥',
  `public_api_base_url` varchar(512) DEFAULT NULL COMMENT 'Foam 外网 API 根地址',
  `web_base_url` varchar(512) DEFAULT NULL COMMENT 'Foam Web 根地址',
  `supported_types` varchar(128) NOT NULL DEFAULT 'alipay,wxpay' COMMENT '启用的支付方式，逗号分隔',
  `order_timeout_minutes` int NOT NULL DEFAULT 15 COMMENT '支付订单超时分钟数',
  `connect_timeout_seconds` int NOT NULL DEFAULT 5 COMMENT '上游连接超时秒数',
  `request_timeout_seconds` int NOT NULL DEFAULT 10 COMMENT '上游请求超时秒数',
  `create_datetime` datetime DEFAULT NULL,
  `update_datetime` datetime DEFAULT NULL,
  `create_user_name` varchar(128) DEFAULT NULL,
  `update_user_name` varchar(128) DEFAULT NULL,
  `create_user_id` bigint DEFAULT NULL,
  `update_user_id` bigint DEFAULT NULL,
  `del_flag` int NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='易支付售卡设置';

INSERT INTO `payment_purchase_setting`
  (`id`, `enabled`, `supported_types`, `order_timeout_minutes`, `connect_timeout_seconds`, `request_timeout_seconds`, `create_datetime`, `update_datetime`, `del_flag`)
VALUES
  (1, 0, 'alipay,wxpay', 15, 5, 10, NOW(), NOW(), 0);

CREATE TABLE `payment_purchase_package` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(128) NOT NULL COMMENT '套餐名称',
  `validity_days` int NOT NULL COMMENT '卡密有效天数',
  `price` decimal(10,2) NOT NULL COMMENT '售价',
  `emby_info_id` bigint NOT NULL COMMENT '卡密目标 Emby 服务器',
  `host_line_type` int NOT NULL DEFAULT 0 COMMENT '线路类型',
  `enabled` tinyint NOT NULL DEFAULT 1 COMMENT '是否在登录页展示',
  `sort_order` int NOT NULL DEFAULT 0 COMMENT '展示顺序',
  `remarks` varchar(255) DEFAULT NULL COMMENT '后台备注',
  `create_datetime` datetime DEFAULT NULL,
  `update_datetime` datetime DEFAULT NULL,
  `create_user_name` varchar(128) DEFAULT NULL,
  `update_user_name` varchar(128) DEFAULT NULL,
  `create_user_id` bigint DEFAULT NULL,
  `update_user_id` bigint DEFAULT NULL,
  `del_flag` int NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`),
  KEY `idx_payment_purchase_package_enabled_sort` (`enabled`, `sort_order`, `id`),
  KEY `idx_payment_purchase_package_emby_info` (`emby_info_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='易支付售卡套餐';

ALTER TABLE `payment_account_order`
  ADD COLUMN `package_id` bigint DEFAULT NULL COMMENT '支付套餐 ID 快照来源' AFTER `status`,
  ADD COLUMN `card_security_id` bigint DEFAULT NULL COMMENT '交付的卡密 ID' AFTER `emby_user_id`,
  ADD COLUMN `manual_confirmed_by` bigint DEFAULT NULL COMMENT '人工确认管理员 ID' AFTER `credential_revealed_datetime`,
  ADD COLUMN `manual_confirmed_datetime` datetime DEFAULT NULL COMMENT '人工确认时间' AFTER `manual_confirmed_by`,
  ADD COLUMN `manual_confirm_reason` varchar(512) DEFAULT NULL COMMENT '人工确认原因' AFTER `manual_confirmed_datetime`,
  ADD COLUMN `provider_check_result` varchar(512) DEFAULT NULL COMMENT '最近人工查单结果' AFTER `manual_confirm_reason`,
  MODIFY COLUMN `status` varchar(24) NOT NULL COMMENT 'PENDING/PAID/PROVISIONING/COMPLETED/EXPIRED/REVIEW/FAILED',
  MODIFY COLUMN `product_name` varchar(128) NOT NULL COMMENT '套餐名称快照',
  MODIFY COLUMN `validity_days` int NOT NULL COMMENT '卡密有效天数快照',
  MODIFY COLUMN `account_user_name` varchar(128) DEFAULT NULL COMMENT '历史开户注册字段，不再使用',
  ADD UNIQUE KEY `uk_payment_account_card_security` (`card_security_id`),
  ADD KEY `idx_payment_account_package` (`package_id`);

ALTER TABLE `card_security_management`
  ADD COLUMN `payment_order_id` bigint DEFAULT NULL COMMENT '来源支付订单 ID' AFTER `distributor_id`,
  ADD UNIQUE KEY `uk_card_security_payment_order` (`payment_order_id`);

UPDATE `system_config`
SET `del_flag` = 1,
    `description` = '已迁移到支付管理页面',
    `update_datetime` = NOW()
WHERE `config_key` = 'payment_account_purchase_enabled'
  AND `del_flag` = 0;
