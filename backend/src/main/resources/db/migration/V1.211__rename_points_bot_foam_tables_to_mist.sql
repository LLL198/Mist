-- Rename the physical tables while preserving the old names as updatable compatibility views.
RENAME TABLE
    `points_bot_foam_bag_config` TO `points_bot_mist_bag_config`,
    `points_bot_foam_bag` TO `points_bot_mist_bag`;

ALTER TABLE `points_bot_mist_bag_config`
    COMMENT = '积分机器人雾袋配置';

ALTER TABLE `points_bot_mist_bag`
    COMMENT = '积分机器人雾袋记录',
    RENAME INDEX `uniq_foam_bag_active_user` TO `uniq_mist_bag_active_user`,
    RENAME INDEX `idx_foam_bag_due` TO `idx_mist_bag_due`,
    RENAME INDEX `idx_foam_bag_user_history` TO `idx_mist_bag_user_history`,
    RENAME INDEX `idx_foam_bag_chat_user_history` TO `idx_mist_bag_chat_user_history`;

CREATE OR REPLACE ALGORITHM = UNDEFINED SQL SECURITY INVOKER VIEW `points_bot_foam_bag_config` AS
SELECT * FROM `points_bot_mist_bag_config`;

CREATE OR REPLACE ALGORITHM = UNDEFINED SQL SECURITY INVOKER VIEW `points_bot_foam_bag` AS
SELECT * FROM `points_bot_mist_bag`;
