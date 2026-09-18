-- Only administrators who still hold the complete original 41-menu catalog receive
-- the newly catalogued legacy entries automatically. Manually restricted accounts
-- keep their current least-privilege assignment and see the new entries unchecked.
INSERT INTO `admin_menu_permission` (`admin_user_id`, `menu_key`)
SELECT existing_admin.id, added_menu.menu_key
FROM `emby_user` existing_admin
INNER JOIN (
    SELECT permission.admin_user_id
    FROM `admin_menu_permission` permission
    WHERE permission.menu_key IN (
        'tickets',
        'request-records',
        'request-subscribe',
        'users',
        'telegram-binding-reviews',
        'user-analysis',
        'user-renew-records',
        'cards',
        'invitations',
        'user-register-records',
        'user-sync',
        'distribution-admin-reviews',
        'distribution-admin-custom-reviews',
        'distribution-admin-products',
        'games',
        'points-bot-redeem-configs',
        'points-bot-levels',
        'points-bot-prizes',
        'points-bot-users',
        'points-bot-ledgers',
        'points-bot-lotteries',
        'points-bot-lottery-entries',
        'points-bot-red-packets',
        'points-bot-foam-bags',
        'now-playing',
        'playback-summary',
        'playback-records',
        'simultaneous-playback',
        'servers',
        'library-access',
        'cover-designer',
        'tmdb-daily-release',
        'notices',
        'emby-block-keywords',
        'notify-channels',
        'notify-templates',
        'realtime-logs',
        'migration',
        'tasks',
        'license',
        'settings'
    )
    GROUP BY permission.admin_user_id
    HAVING COUNT(DISTINCT permission.menu_key) = 41
) full_assignment ON full_assignment.admin_user_id = existing_admin.id
CROSS JOIN (
    SELECT 'dashboard' AS menu_key
    UNION ALL SELECT 'library'
    UNION ALL SELECT 'requests'
    UNION ALL SELECT 'distribution-dashboard'
    UNION ALL SELECT 'distribution-application'
    UNION ALL SELECT 'distribution-products'
    UNION ALL SELECT 'distribution-invite-records'
    UNION ALL SELECT 'distribution-exchange-history'
    UNION ALL SELECT 'distribution-cards'
) added_menu
WHERE existing_admin.`del_flag` = 0
  AND existing_admin.`is_admin` = 1
  AND COALESCE(existing_admin.`is_primary_admin`, 0) = 0;
