-- 每月求片次数自动增加配置
-- 配置值为JSON格式：{"count": 5, "day": 1, "mode": "ADD"}
-- count: 增加/重置的次数
-- day: 每月执行日期（1-28）
-- mode: ADD(累加) 或 RESET(重置)
INSERT INTO `system_config` (`name`, `config_key`, `config_value`, `is_enabled`, `description`, `is_update`, `create_datetime`, `update_datetime`, `create_user_name`, `update_user_name`, `update_user_id`, `create_user_id`, `del_flag`)
VALUES ('每月求片次数自动增加', 'monthly_request_config', '{"count": 5, "day": 1, "mode": "ADD"}', 0, '每月自动为未禁用且未过期的用户增加求片次数，count为次数，day为执行日期(1-28)，mode为ADD(累加)或RESET(重置)', 1, NOW(), NOW(), 'admin', 'admin', 1, 1, 0);
