ALTER TABLE `payment_account_order`
  ADD COLUMN `buyer_name` varchar(64) DEFAULT NULL COMMENT '购买人名称' AFTER `order_no`;
