-- 审批结果只展示管理员角色，不再暴露管理员绑定的 Emby 账号名称。
UPDATE `telegram_binding_review`
SET `reviewer_user_name` = '管理员',
    `update_user_name` = '管理员'
WHERE `status` IN (1, 2)
  AND `del_flag` = 0
  AND (`reviewer_user_name` <> '管理员'
       OR `update_user_name` <> '管理员'
       OR `reviewer_user_name` IS NULL
       OR `update_user_name` IS NULL);
