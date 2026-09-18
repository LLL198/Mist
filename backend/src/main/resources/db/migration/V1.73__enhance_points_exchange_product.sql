-- Enhance points_exchange_product table
-- Add emby_info_id column for server association (required for CREATE_ACCOUNT/RENEW types)
ALTER TABLE `points_exchange_product` 
ADD COLUMN `emby_info_id` bigint DEFAULT NULL COMMENT '服务器ID(创建账号/续费类型必填)';

-- Update product_type comment to reflect new types: CREATE_ACCOUNT(创建账号), RENEW(续费), CUSTOM(自定义), CARD_KEY(卡密), DURATION_EXTENSION(时长)
ALTER TABLE `points_exchange_product` 
MODIFY COLUMN `product_type` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '商品类型 CREATE_ACCOUNT 创建账号, RENEW 续费, CUSTOM 自定义, CARD_KEY 卡密, DURATION_EXTENSION 时长';

-- Update product_value comment to reflect generic value (not just days)
ALTER TABLE `points_exchange_product` 
MODIFY COLUMN `product_value` int NOT NULL COMMENT '商品值(用于开号/续费时为天数,其他类型为通用值)';
