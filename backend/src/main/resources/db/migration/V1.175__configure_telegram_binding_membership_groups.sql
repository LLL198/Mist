UPDATE `system_config`
SET `name` = 'TG绑定需加入群聊',
    `config_value` = '{"pointsGroupRequired":true,"libraryNotifyGroupRequired":false}',
    `description` = '开启后，可分别要求 Telegram 用户加入积分群/频道、入库通知群/频道后才能绑定或换绑 Emby 账号；默认仅要求加入积分群/频道，两项可同时开启。',
    `is_update` = 1,
    `update_datetime` = NOW()
WHERE `config_key` = 'telegram_binding_points_group_required'
  AND `del_flag` = 0;
