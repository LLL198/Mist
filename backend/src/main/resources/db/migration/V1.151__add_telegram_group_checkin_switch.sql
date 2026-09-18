UPDATE `notify_channel`
SET `params` = JSON_SET(
        COALESCE(NULLIF(`params`, ''), '{}'),
        '$.groupCheckinEnabled',
        IFNULL(JSON_EXTRACT(COALESCE(NULLIF(`params`, ''), '{}'), '$.groupCheckinEnabled'), JSON_EXTRACT('true', '$'))
    )
WHERE `icon_type` IN ('telegram', 'pointsBot')
  AND JSON_VALID(COALESCE(NULLIF(`params`, ''), '{}'));
