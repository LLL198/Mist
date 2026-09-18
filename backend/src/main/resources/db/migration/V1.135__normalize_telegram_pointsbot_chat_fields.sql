UPDATE `notify_channel`
SET `params` = JSON_SET(
        COALESCE(NULLIF(`params`, ''), '{}'),
        '$.botChatGroupId', COALESCE(
                NULLIF(JSON_UNQUOTE(JSON_EXTRACT(COALESCE(NULLIF(`params`, ''), '{}'), '$.botChatGroupId')), ''),
                NULLIF(JSON_UNQUOTE(JSON_EXTRACT(COALESCE(NULLIF(`params`, ''), '{}'), '$.groupChatId')), ''),
                ''
            ),
        '$.botChatId', COALESCE(
                NULLIF(JSON_UNQUOTE(JSON_EXTRACT(COALESCE(NULLIF(`params`, ''), '{}'), '$.botChatId')), ''),
                NULLIF(JSON_UNQUOTE(JSON_EXTRACT(COALESCE(NULLIF(`params`, ''), '{}'), '$.dmChatId')), ''),
                ''
            )
    )
WHERE `icon_type` = 'telegram'
  AND JSON_VALID(COALESCE(NULLIF(`params`, ''), '{}'));

UPDATE `notify_channel`
SET `params` = JSON_REMOVE(
        COALESCE(NULLIF(`params`, ''), '{}'),
        '$.groupChatId',
        '$.dmChatId'
    )
WHERE `icon_type` = 'telegram'
  AND JSON_VALID(COALESCE(NULLIF(`params`, ''), '{}'));
