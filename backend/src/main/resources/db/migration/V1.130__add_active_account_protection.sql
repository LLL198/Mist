ALTER TABLE `emby_user`
  ADD COLUMN `disable_reason` varchar(64) DEFAULT NULL COMMENT '禁用原因' AFTER `user_status`,
  ADD COLUMN `disabled_datetime` datetime DEFAULT NULL COMMENT '禁用时间' AFTER `disable_reason`;

CREATE INDEX `idx_emby_user_active_protection`
  ON `emby_user` (`emby_info_id`, `user_status`, `disable_reason`, `disabled_datetime`);

INSERT INTO `system_config` (`name`, `config_key`, `config_value`, `is_enabled`, `description`, `is_update`, `create_datetime`, `update_datetime`, `create_user_name`, `update_user_name`, `update_user_id`, `create_user_id`, `del_flag`)
SELECT '活跃保号',
       'emby_active_account_protection',
       '{"serverIds":[],"activityCheckDays":21,"disabledRetentionDays":15,"deleteAfterDisabled":true}',
       0,
       '按 EmbyBoss 活跃保号逻辑执行：仅对配置的服务器检测普通用户，超过活跃天数未观看则禁用；禁用保留期后可自动删除。默认关闭。',
       1,
       NOW(),
       NOW(),
       'admin',
       'admin',
       1,
       1,
       0
WHERE NOT EXISTS (SELECT 1 FROM `system_config` WHERE `config_key` = 'emby_active_account_protection');
