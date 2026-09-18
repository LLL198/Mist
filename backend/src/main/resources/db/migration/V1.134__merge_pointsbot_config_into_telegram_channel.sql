UPDATE `notify_channel`
SET `params` = JSON_SET(
        COALESCE(NULLIF(`params`, ''), '{}'),
        '$.apiId', IFNULL(JSON_EXTRACT(COALESCE(NULLIF(`params`, ''), '{}'), '$.apiId'), 0),
        '$.apiHash', IFNULL(JSON_EXTRACT(COALESCE(NULLIF(`params`, ''), '{}'), '$.apiHash'), '')
    )
WHERE `icon_type` = 'telegram'
  AND JSON_VALID(COALESCE(NULLIF(`params`, ''), '{}'));

UPDATE `notify_channel` telegram_channel
JOIN `notify_channel` points_channel
  ON points_channel.`icon_type` = 'pointsBot'
 AND points_channel.`del_flag` = 0
SET telegram_channel.`params` = JSON_SET(
        COALESCE(NULLIF(telegram_channel.`params`, ''), '{}'),
        '$.groupChatId', COALESCE(JSON_EXTRACT(COALESCE(NULLIF(telegram_channel.`params`, ''), '{}'), '$.groupChatId'), JSON_EXTRACT(COALESCE(NULLIF(points_channel.`params`, ''), '{}'), '$.groupChatId'), ''),
        '$.dmChatId', COALESCE(JSON_EXTRACT(COALESCE(NULLIF(telegram_channel.`params`, ''), '{}'), '$.dmChatId'), JSON_EXTRACT(COALESCE(NULLIF(points_channel.`params`, ''), '{}'), '$.dmChatId'), ''),
        '$.dailyMessagePointsLimit', COALESCE(JSON_EXTRACT(COALESCE(NULLIF(telegram_channel.`params`, ''), '{}'), '$.dailyMessagePointsLimit'), JSON_EXTRACT(COALESCE(NULLIF(points_channel.`params`, ''), '{}'), '$.dailyMessagePointsLimit'), 20),
        '$.checkinBaseMin', COALESCE(JSON_EXTRACT(COALESCE(NULLIF(telegram_channel.`params`, ''), '{}'), '$.checkinBaseMin'), JSON_EXTRACT(COALESCE(NULLIF(points_channel.`params`, ''), '{}'), '$.checkinBaseMin'), 1),
        '$.checkinBaseMax', COALESCE(JSON_EXTRACT(COALESCE(NULLIF(telegram_channel.`params`, ''), '{}'), '$.checkinBaseMax'), JSON_EXTRACT(COALESCE(NULLIF(points_channel.`params`, ''), '{}'), '$.checkinBaseMax'), 3),
        '$.checkinPenaltyChance', COALESCE(JSON_EXTRACT(COALESCE(NULLIF(telegram_channel.`params`, ''), '{}'), '$.checkinPenaltyChance'), JSON_EXTRACT(COALESCE(NULLIF(points_channel.`params`, ''), '{}'), '$.checkinPenaltyChance'), 15),
        '$.checkinPenaltyMin', COALESCE(JSON_EXTRACT(COALESCE(NULLIF(telegram_channel.`params`, ''), '{}'), '$.checkinPenaltyMin'), JSON_EXTRACT(COALESCE(NULLIF(points_channel.`params`, ''), '{}'), '$.checkinPenaltyMin'), 1),
        '$.checkinPenaltyMax', COALESCE(JSON_EXTRACT(COALESCE(NULLIF(telegram_channel.`params`, ''), '{}'), '$.checkinPenaltyMax'), JSON_EXTRACT(COALESCE(NULLIF(points_channel.`params`, ''), '{}'), '$.checkinPenaltyMax'), 3),
        '$.streakBonusEvery', COALESCE(JSON_EXTRACT(COALESCE(NULLIF(telegram_channel.`params`, ''), '{}'), '$.streakBonusEvery'), JSON_EXTRACT(COALESCE(NULLIF(points_channel.`params`, ''), '{}'), '$.streakBonusEvery'), 7),
        '$.streakBonusPoints', COALESCE(JSON_EXTRACT(COALESCE(NULLIF(telegram_channel.`params`, ''), '{}'), '$.streakBonusPoints'), JSON_EXTRACT(COALESCE(NULLIF(points_channel.`params`, ''), '{}'), '$.streakBonusPoints'), 2),
        '$.lotteryDrawIntervalMinutes', COALESCE(JSON_EXTRACT(COALESCE(NULLIF(telegram_channel.`params`, ''), '{}'), '$.lotteryDrawIntervalMinutes'), JSON_EXTRACT(COALESCE(NULLIF(points_channel.`params`, ''), '{}'), '$.lotteryDrawIntervalMinutes'), 60),
        '$.leaderboardLimit', COALESCE(JSON_EXTRACT(COALESCE(NULLIF(telegram_channel.`params`, ''), '{}'), '$.leaderboardLimit'), JSON_EXTRACT(COALESCE(NULLIF(points_channel.`params`, ''), '{}'), '$.leaderboardLimit'), 10),
        '$.transferMinPoints', COALESCE(JSON_EXTRACT(COALESCE(NULLIF(telegram_channel.`params`, ''), '{}'), '$.transferMinPoints'), JSON_EXTRACT(COALESCE(NULLIF(points_channel.`params`, ''), '{}'), '$.transferMinPoints'), 1),
        '$.transferMaxPoints', COALESCE(JSON_EXTRACT(COALESCE(NULLIF(telegram_channel.`params`, ''), '{}'), '$.transferMaxPoints'), JSON_EXTRACT(COALESCE(NULLIF(points_channel.`params`, ''), '{}'), '$.transferMaxPoints'), 500)
    )
WHERE telegram_channel.`icon_type` = 'telegram'
  AND telegram_channel.`del_flag` = 0
  AND JSON_VALID(COALESCE(NULLIF(telegram_channel.`params`, ''), '{}'))
  AND JSON_VALID(COALESCE(NULLIF(points_channel.`params`, ''), '{}'));
