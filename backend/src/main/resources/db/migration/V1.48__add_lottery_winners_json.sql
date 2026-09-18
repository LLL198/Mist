-- Add winners_json to points_bot_lottery
ALTER TABLE `points_bot_lottery` ADD COLUMN `winners_json` TEXT COMMENT '中奖人列表JSON';
