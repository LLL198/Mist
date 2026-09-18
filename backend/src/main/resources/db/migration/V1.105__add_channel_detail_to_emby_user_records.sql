ALTER TABLE `emby_user_register_record`
    ADD COLUMN `register_channel_detail` VARCHAR(100) DEFAULT NULL COMMENT '注册渠道明细' AFTER `register_channel`;

ALTER TABLE `emby_user_renew_record`
    ADD COLUMN `renew_channel_detail` VARCHAR(100) DEFAULT NULL COMMENT '续费渠道明细' AFTER `renew_channel`;

UPDATE `emby_user_register_record`
SET `register_channel_detail` = CASE `register_channel`
    WHEN 1 THEN '管理员注册'
    WHEN 2 THEN '卡密注册'
    WHEN 3 THEN '邀请码注册'
    WHEN 4 THEN '积分兑换'
    WHEN 5 THEN '用户注册'
    ELSE `register_channel_detail`
END
WHERE `register_channel_detail` IS NULL;

UPDATE `emby_user_renew_record`
SET `renew_channel_detail` = CASE `renew_channel`
    WHEN 1 THEN '管理员续费'
    WHEN 2 THEN '卡密续费'
    ELSE `renew_channel_detail`
END
WHERE `renew_channel_detail` IS NULL;
