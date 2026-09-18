-- 修改 points_exchange_product 表 product_type 字段注释
ALTER TABLE `points_exchange_product` 
MODIFY COLUMN `product_type` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '商品类型 1:CARD (创建/续费卡密) 2:CUSTOM (自定义)';
