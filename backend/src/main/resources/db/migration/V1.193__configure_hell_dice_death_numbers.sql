UPDATE `points_bot_game_config`
SET `config_json` = JSON_REMOVE(
        JSON_SET(
            COALESCE(NULLIF(`config_json`, ''), '{}'),
            '$.layerDeathNumbers',
            JSON_ARRAY(
                JSON_ARRAY(1, 3, 5),
                JSON_ARRAY(1, 3, 5),
                JSON_ARRAY(1, 3, 5, 6),
                JSON_ARRAY(1, 3, 5, 6),
                JSON_ARRAY(1, 2, 3, 4, 5),
                JSON_ARRAY(1, 2, 3, 4, 5)
            ),
            '$.layerPayoutPercents',
            JSON_ARRAY(110, 125, 175, 245, 465, 880)
        ),
        '$.layerDeathMaxes',
        '$.layerWinningNumbers'
    )
WHERE `game_code` = 'hell_dice'
  AND JSON_VALID(COALESCE(NULLIF(`config_json`, ''), '{}'));

-- 旧轮次继续由应用按其 layerDeathMaxes 快照判定，避免发布后改变进行中轮次的结果。
