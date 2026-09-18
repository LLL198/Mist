CREATE TABLE `payment_account_order` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `order_no` varchar(64) NOT NULL COMMENT 'Foam 订单号',
  `lookup_token_hash` char(64) NOT NULL COMMENT '订单查询码 SHA-256',
  `provider_trade_no` varchar(128) DEFAULT NULL COMMENT '易支付交易号',
  `payment_type` varchar(32) NOT NULL COMMENT '支付方式',
  `status` varchar(24) NOT NULL COMMENT 'PENDING/PAID/PROVISIONING/COMPLETED/EXPIRED/REVIEW',
  `amount` decimal(10,2) NOT NULL COMMENT '实付金额快照',
  `product_name` varchar(128) NOT NULL COMMENT '商品名称快照',
  `validity_days` int NOT NULL COMMENT '账号有效期天数快照',
  `emby_info_id` bigint NOT NULL COMMENT '目标 Emby 服务器 ID 快照',
  `host_line_type` int NOT NULL DEFAULT 0 COMMENT '线路类型快照',
  `account_user_name` varchar(128) NOT NULL COMMENT '预生成的 Emby 用户名',
  `encrypted_password` text DEFAULT NULL COMMENT 'AES-GCM 加密的待交付密码',
  `emby_user_id` bigint DEFAULT NULL COMMENT '创建后的 Foam 用户 ID',
  `qr_code` text DEFAULT NULL COMMENT '易支付二维码内容',
  `pay_url` text DEFAULT NULL COMMENT '易支付付款地址',
  `client_ip_hash` char(64) DEFAULT NULL COMMENT '创建端 IP 哈希',
  `last_query_datetime` datetime DEFAULT NULL COMMENT '最近主动查单时间',
  `paid_datetime` datetime DEFAULT NULL COMMENT '确认支付时间',
  `provision_started_datetime` datetime DEFAULT NULL COMMENT '开始开户注册时间',
  `completed_datetime` datetime DEFAULT NULL COMMENT '开户注册完成时间',
  `expires_datetime` datetime NOT NULL COMMENT '支付订单过期时间',
  `credential_revealed_datetime` datetime DEFAULT NULL COMMENT '密码首次交付时间',
  `failure_reason` varchar(512) DEFAULT NULL COMMENT '最近失败原因',
  `create_datetime` datetime DEFAULT NULL,
  `update_datetime` datetime DEFAULT NULL,
  `create_user_name` varchar(128) DEFAULT NULL,
  `update_user_name` varchar(128) DEFAULT NULL,
  `create_user_id` bigint DEFAULT NULL,
  `update_user_id` bigint DEFAULT NULL,
  `del_flag` int NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_payment_account_order_no` (`order_no`),
  UNIQUE KEY `uk_payment_account_provider_trade_no` (`provider_trade_no`),
  KEY `idx_payment_account_status_created` (`status`, `create_datetime`),
  KEY `idx_payment_account_emby_user` (`emby_user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='易支付扫码开户注册订单';

INSERT INTO `system_config`
  (`config_key`, `name`, `config_value`, `is_enabled`, `is_update`, `description`, `del_flag`)
SELECT
  'payment_account_purchase_enabled',
  '登录页扫码开户注册',
  '',
  0,
  1,
  '开启后登录页显示易支付扫码开户注册入口；商户和商品参数通过 FOAM_PAYMENT_* 环境变量配置',
  0
WHERE NOT EXISTS (
  SELECT 1 FROM `system_config` WHERE `config_key` = 'payment_account_purchase_enabled' AND `del_flag` = 0
);
