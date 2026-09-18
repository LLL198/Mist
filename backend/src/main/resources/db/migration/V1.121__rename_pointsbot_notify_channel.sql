DELETE FROM `notify_channel`
WHERE `icon_type` = 'nullbr';

UPDATE `notify_channel`
SET `name` = '娱乐机器人',
    `desc` = '签到/兑换/抽奖和游戏机器人配置',
    `update_datetime` = NOW(),
    `update_user_name` = 'system'
WHERE `icon_type` = 'pointsBot'
  AND (`name` = '积分机器人' OR `desc` = '积分/签到/抽奖机器人配置');
