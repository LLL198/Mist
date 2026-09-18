INSERT INTO `system_config` (`name`, `config_key`, `config_value`, `is_enabled`, `description`, `is_update`, `create_datetime`, `update_datetime`, `create_user_name`, `update_user_name`, `update_user_id`, `create_user_id`, `del_flag`)
SELECT '登录转场',
       'login_transition_config',
       '{"rules":[{"id":"monthly","minDays":1,"maxDays":30,"title":"尊贵的月度 VIP","subtitle":"30 天专属礼遇","preset":"stardust","durationMs":1400,"enabled":true},{"id":"quarterly","minDays":31,"maxDays":90,"title":"尊贵的季度 VIP","subtitle":"90 天专属礼遇","preset":"aurora","durationMs":1600,"enabled":true},{"id":"annual","minDays":91,"maxDays":null,"title":"尊贵的年度 VIP","subtitle":"365 天专属礼遇","preset":"golden","durationMs":1800,"enabled":true}]}',
       0,
       '按用户最近一次注册或续费获赠时长，在登录成功后播放专属称号转场。默认关闭。',
       1,
       NOW(),
       NOW(),
       'system',
       'system',
       NULL,
       NULL,
       0
WHERE NOT EXISTS (SELECT 1 FROM `system_config` WHERE `config_key` = 'login_transition_config');

-- 仅给仍保留此前完整 50 项菜单权限的普通管理员补上新菜单；手动收窄过权限的账号保持最小权限。
INSERT IGNORE INTO `admin_menu_permission` (`admin_user_id`, `menu_key`)
SELECT existing_admin.id, 'login-transition'
FROM `emby_user` existing_admin
INNER JOIN (
    SELECT permission.admin_user_id
    FROM `admin_menu_permission` permission
    GROUP BY permission.admin_user_id
    HAVING COUNT(DISTINCT permission.menu_key) = 50
) full_assignment ON full_assignment.admin_user_id = existing_admin.id
WHERE existing_admin.`del_flag` = 0
  AND existing_admin.`is_admin` = 1
  AND COALESCE(existing_admin.`is_primary_admin`, 0) = 0;
