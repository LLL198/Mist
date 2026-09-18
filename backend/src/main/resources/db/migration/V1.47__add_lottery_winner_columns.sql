-- Add winner_count to points_bot_lottery
ALTER TABLE `points_bot_lottery` ADD COLUMN `winner_count` INT DEFAULT 1 COMMENT '中奖人数';

-- Add is_winner to points_bot_lottery_entry
ALTER TABLE `points_bot_lottery_entry` ADD COLUMN `is_winner` TINYINT(1) DEFAULT 0 COMMENT '是否中奖';
