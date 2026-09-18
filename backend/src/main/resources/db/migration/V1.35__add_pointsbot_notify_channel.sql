INSERT INTO `notify_channel` (`name`, `desc`, `icon_type`, `enabled`, `custom_icon`, `params`, `create_datetime`, `update_datetime`, `create_user_name`, `update_user_name`, `update_user_id`, `create_user_id`, `del_flag`)
VALUES ('积分机器人', '积分/签到/抽奖机器人配置', 'pointsBot', 0, NULL,
        '{\"botToken\":\"\",\"groupChatId\":\"\",\"dmChatId\":\"\",\"dailyMessagePointsLimit\":20,\"checkinBaseMin\":1,\"checkinBaseMax\":3,\"checkinPenaltyChance\":15,\"checkinPenaltyMin\":1,\"checkinPenaltyMax\":3,\"streakBonusEvery\":7,\"streakBonusPoints\":2,\"lotteryDrawIntervalMinutes\":60,\"leaderboardLimit\":10,\"transferMinPoints\":1,\"transferMaxPoints\":500}',
        NOW(), NOW(), 'system', 'system', NULL, NULL, 0);
