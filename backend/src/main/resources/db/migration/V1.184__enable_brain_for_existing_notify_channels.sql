UPDATE `notify_channel`
SET `params` = JSON_SET(
        COALESCE(NULLIF(`params`, ''), '{}'),
        '$.enabledGameCommands',
        CASE
            WHEN JSON_TYPE(
                    JSON_EXTRACT(
                        COALESCE(NULLIF(`params`, ''), '{}'),
                        '$.enabledGameCommands'
                    )
                 ) = 'ARRAY'
                THEN CASE
                    WHEN JSON_CONTAINS(
                            JSON_EXTRACT(
                                COALESCE(NULLIF(`params`, ''), '{}'),
                                '$.enabledGameCommands'
                            ),
                            JSON_QUOTE('brain')
                         )
                        THEN JSON_EXTRACT(
                            COALESCE(NULLIF(`params`, ''), '{}'),
                            '$.enabledGameCommands'
                        )
                    ELSE JSON_ARRAY_APPEND(
                            JSON_EXTRACT(
                                COALESCE(NULLIF(`params`, ''), '{}'),
                                '$.enabledGameCommands'
                            ),
                            '$',
                            'brain'
                         )
                END
            ELSE JSON_ARRAY('sgs', 'blackjack', 'dice', 'slots', 'scratch', 'brain')
        END,
        '$.gameCommandsVersion',
        3
    )
WHERE `icon_type` IN ('telegram', 'pointsBot')
  AND `del_flag` = 0
  AND JSON_VALID(COALESCE(NULLIF(`params`, ''), '{}'))
  AND JSON_TYPE(COALESCE(NULLIF(`params`, ''), '{}')) = 'OBJECT';
