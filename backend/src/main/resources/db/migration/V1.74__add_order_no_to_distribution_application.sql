ALTER TABLE `distribution_application` 
ADD COLUMN `order_no` VARCHAR(64) NULL COMMENT '分销单号' AFTER `id`;
