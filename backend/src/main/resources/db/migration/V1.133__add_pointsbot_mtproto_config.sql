UPDATE `notify_channel`
SET `params` = JSON_SET(
        COALESCE(NULLIF(`params`, ''), '{}'),
        '$.apiId', IFNULL(JSON_EXTRACT(COALESCE(NULLIF(`params`, ''), '{}'), '$.apiId'), 0),
        '$.apiHash', IFNULL(JSON_EXTRACT(COALESCE(NULLIF(`params`, ''), '{}'), '$.apiHash'), '')
    )
WHERE `icon_type` = 'pointsBot'
  AND JSON_VALID(COALESCE(NULLIF(`params`, ''), '{}'));
