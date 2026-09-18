ALTER TABLE `points_bot_scratch_round`
    ADD COLUMN `pity_target` int DEFAULT NULL COMMENT '大奖保底目标轮次' AFTER `jackpot_cell`,
    ADD COLUMN `pity_progress` int DEFAULT NULL COMMENT '当前保底周期进度' AFTER `pity_target`;
