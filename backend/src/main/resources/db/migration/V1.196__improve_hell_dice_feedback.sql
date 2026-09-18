ALTER TABLE `points_bot_hell_bet`
    ADD COLUMN `username` varchar(100) DEFAULT NULL COMMENT '下注观众Telegram用户名' AFTER `user_id`,
    ADD COLUMN `display_name` varchar(200) DEFAULT NULL COMMENT '下注观众展示名称' AFTER `username`;

UPDATE `points_bot_game_config`
SET `config_json` = JSON_SET(
        COALESCE(NULLIF(`config_json`, ''), '{}'),
        '$.panelRetentionSeconds',
        120
    )
WHERE `game_code` = 'hell_dice'
  AND JSON_VALID(COALESCE(NULLIF(`config_json`, ''), '{}'))
  AND JSON_EXTRACT(`config_json`, '$.panelRetentionSeconds') IS NULL;

-- 只升级仍在使用旧默认返还的配置；管理员已经自定义的倍率保持不变。
UPDATE `points_bot_game_config`
SET `config_json` = JSON_SET(
        `config_json`,
        '$.layerPayoutPercents',
        JSON_ARRAY(120, 160, 240, 360, 700, 1000)
    )
WHERE `game_code` = 'hell_dice'
  AND JSON_VALID(COALESCE(NULLIF(`config_json`, ''), '{}'))
  AND JSON_LENGTH(JSON_EXTRACT(`config_json`, '$.layerPayoutPercents')) = 6
  AND JSON_EXTRACT(`config_json`, '$.layerPayoutPercents[0]') = 110
  AND JSON_EXTRACT(`config_json`, '$.layerPayoutPercents[1]') = 125
  AND JSON_EXTRACT(`config_json`, '$.layerPayoutPercents[2]') = 175
  AND JSON_EXTRACT(`config_json`, '$.layerPayoutPercents[3]') = 245
  AND JSON_EXTRACT(`config_json`, '$.layerPayoutPercents[4]') = 465
  AND JSON_EXTRACT(`config_json`, '$.layerPayoutPercents[5]') = 880;

-- 已经开局的轮次继续使用自己的配置快照，避免发布中途改变该局返还。
