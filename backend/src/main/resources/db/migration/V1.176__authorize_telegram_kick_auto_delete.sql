UPDATE `system_config`
SET `config_value` = CASE
        WHEN NOT JSON_VALID(COALESCE(NULLIF(`config_value`, ''), '{}'))
            THEN '{"pointsGroupEnabled":true,"libraryNotifyGroupEnabled":false,"authorizedKickDeleteEnabled":false}'
        WHEN JSON_TYPE(COALESCE(NULLIF(`config_value`, ''), '{}')) <> 'OBJECT'
            THEN '{"pointsGroupEnabled":true,"libraryNotifyGroupEnabled":false,"authorizedKickDeleteEnabled":false}'
        ELSE JSON_SET(
                COALESCE(NULLIF(`config_value`, ''), '{}'),
                '$.authorizedKickDeleteEnabled',
                IFNULL(
                    JSON_EXTRACT(
                        COALESCE(NULLIF(`config_value`, ''), '{}'),
                        '$.authorizedKickDeleteEnabled'
                    ),
                    JSON_EXTRACT('false', '$')
                )
            )
    END,
    `description` = '总开关默认关闭；可分别设置积分群聊、入库通知群退群删号；授权管理员踢人删号需单独开启，且管理员必须拥有专用权限。',
    `update_datetime` = NOW()
WHERE `config_key` = 'telegram_leave_auto_delete_enabled'
  AND `del_flag` = 0;
