-- 抽奖表添加奖品关联字段
ALTER TABLE `points_bot_lottery` 
ADD COLUMN `prize_config_id` BIGINT COMMENT '关联的奖品配置ID' AFTER `title`;

-- 添加索引
ALTER TABLE `points_bot_lottery`
ADD INDEX `idx_prize_config` (`prize_config_id`);
