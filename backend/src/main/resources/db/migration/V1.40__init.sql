-- 积分兑换配置表添加所需积分字段
ALTER TABLE `points_bot_redeem_config` 
ADD COLUMN `required_points` INT COMMENT '兑换所需积分' AFTER `remark`;
