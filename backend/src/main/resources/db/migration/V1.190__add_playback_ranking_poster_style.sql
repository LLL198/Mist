INSERT INTO `system_config` (`name`, `config_key`, `config_value`, `is_enabled`, `description`, `is_update`, `create_datetime`, `update_datetime`, `create_user_name`, `update_user_name`, `update_user_id`, `create_user_id`, `del_flag`)
SELECT '排行榜通知海报样式', 'playback_ranking_poster_style', 'streaming_magazine', 1, '选择每日播放排行榜通知海报样式；默认使用流媒体杂志，设置页仅展示布局骨架预览。', 1, NOW(), NOW(), 'admin', 'admin', 1, 1, 0
WHERE NOT EXISTS (SELECT 1 FROM `system_config` WHERE `config_key` = 'playback_ranking_poster_style');
