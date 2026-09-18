UPDATE `points_bot_game_config`
SET `config_json` = JSON_SET(
        COALESCE(NULLIF(`config_json`, ''), '{}'),
        '$.dailyPlayLimit',
        300
    )
WHERE `game_code` = 'hell_dice'
  AND JSON_VALID(COALESCE(NULLIF(`config_json`, ''), '{}'))
  AND (
      JSON_EXTRACT(`config_json`, '$.dailyPlayLimit') IS NULL
      OR JSON_EXTRACT(`config_json`, '$.dailyPlayLimit') = 3
  );
