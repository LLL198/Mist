UPDATE `points_bot_game_config`
SET `config_json` = JSON_SET(
        COALESCE(NULLIF(`config_json`, ''), '{}'),
        '$.layerDeathMaxes',
        JSON_ARRAY(2, 2, 3, 3, 4, 4)
    )
WHERE `game_code` = 'hell_dice'
  AND JSON_VALID(COALESCE(NULLIF(`config_json`, ''), '{}'));

-- 已创建轮次继续使用开局时的旧难度，避免部署重启后中途改变本局胜负规则。
UPDATE `points_bot_hell_round`
SET `config_json` = JSON_SET(
        `config_json`,
        '$.layerDeathMaxes',
        JSON_ARRAY(1, 1, 2, 2, 3, 3)
    )
WHERE JSON_VALID(`config_json`)
  AND JSON_EXTRACT(`config_json`, '$.layerDeathMaxes') IS NULL;
