CREATE TABLE `admin_menu_permission` (
    `id` BIGINT NOT NULL AUTO_INCREMENT,
    `admin_user_id` BIGINT NOT NULL COMMENT '普通管理员用户 ID',
    `menu_key` VARCHAR(80) NOT NULL COMMENT '后台菜单权限键',
    `create_datetime` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_admin_menu_permission` (`admin_user_id`, `menu_key`),
    KEY `idx_admin_menu_permission_user` (`admin_user_id`),
    CONSTRAINT `fk_admin_menu_permission_user`
        FOREIGN KEY (`admin_user_id`) REFERENCES `emby_user` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='普通管理员后台菜单权限';

-- 升级时保留现有普通管理员的全部后台菜单；后续新晋管理员默认无菜单，由超管分配。
INSERT INTO `admin_menu_permission` (`admin_user_id`, `menu_key`)
SELECT existing_admin.id, menu_catalog.menu_key
FROM `emby_user` existing_admin
CROSS JOIN (
    SELECT 'tickets' AS menu_key
    UNION ALL SELECT 'request-records'
    UNION ALL SELECT 'request-subscribe'
    UNION ALL SELECT 'users'
    UNION ALL SELECT 'telegram-binding-reviews'
    UNION ALL SELECT 'user-analysis'
    UNION ALL SELECT 'user-renew-records'
    UNION ALL SELECT 'cards'
    UNION ALL SELECT 'invitations'
    UNION ALL SELECT 'user-register-records'
    UNION ALL SELECT 'user-sync'
    UNION ALL SELECT 'distribution-admin-reviews'
    UNION ALL SELECT 'distribution-admin-custom-reviews'
    UNION ALL SELECT 'distribution-admin-products'
    UNION ALL SELECT 'games'
    UNION ALL SELECT 'points-bot-redeem-configs'
    UNION ALL SELECT 'points-bot-levels'
    UNION ALL SELECT 'points-bot-prizes'
    UNION ALL SELECT 'points-bot-users'
    UNION ALL SELECT 'points-bot-ledgers'
    UNION ALL SELECT 'points-bot-lotteries'
    UNION ALL SELECT 'points-bot-lottery-entries'
    UNION ALL SELECT 'points-bot-red-packets'
    UNION ALL SELECT 'points-bot-foam-bags'
    UNION ALL SELECT 'now-playing'
    UNION ALL SELECT 'playback-summary'
    UNION ALL SELECT 'playback-records'
    UNION ALL SELECT 'simultaneous-playback'
    UNION ALL SELECT 'servers'
    UNION ALL SELECT 'library-access'
    UNION ALL SELECT 'cover-designer'
    UNION ALL SELECT 'tmdb-daily-release'
    UNION ALL SELECT 'notices'
    UNION ALL SELECT 'emby-block-keywords'
    UNION ALL SELECT 'notify-channels'
    UNION ALL SELECT 'notify-templates'
    UNION ALL SELECT 'realtime-logs'
    UNION ALL SELECT 'migration'
    UNION ALL SELECT 'tasks'
    UNION ALL SELECT 'license'
    UNION ALL SELECT 'settings'
) menu_catalog
WHERE existing_admin.`del_flag` = 0
  AND existing_admin.`is_admin` = 1
  AND COALESCE(existing_admin.`is_primary_admin`, 0) = 0;
