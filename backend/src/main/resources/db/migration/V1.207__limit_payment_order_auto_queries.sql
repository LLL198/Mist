ALTER TABLE `payment_purchase_setting`
  ADD COLUMN `auto_query_limit` int NOT NULL DEFAULT 30 COMMENT '单个订单自动向易支付查单次数上限' AFTER `order_timeout_minutes`;

ALTER TABLE `payment_account_order`
  ADD COLUMN `provider_query_count` int NOT NULL DEFAULT 0 COMMENT '已向易支付发起的自动查单次数' AFTER `last_query_datetime`;
