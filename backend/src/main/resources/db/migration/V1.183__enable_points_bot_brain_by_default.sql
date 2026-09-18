UPDATE `points_bot_game_config`
SET `enabled` = 1
WHERE `game_code` = 'brain'
  AND `enabled` = 0
  AND `update_datetime` IS NULL;
