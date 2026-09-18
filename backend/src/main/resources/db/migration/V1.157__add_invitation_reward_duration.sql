ALTER TABLE `invitation_code`
    ADD COLUMN `reward_duration` INT NOT NULL DEFAULT 0 COMMENT '邀请码注册新人奖励时长，0不奖励' AFTER `validity_days`,
    ADD COLUMN `reward_duration_unit` VARCHAR(10) NOT NULL DEFAULT 'DAY' COMMENT '邀请码注册新人奖励单位 HOUR小时 DAY天' AFTER `reward_duration`;

ALTER TABLE `emby_user_register_record`
    ADD COLUMN `reward_duration` INT NOT NULL DEFAULT 0 COMMENT '邀请码注册实际获得的新人奖励时长，0无奖励' AFTER `expiration_date`,
    ADD COLUMN `reward_duration_unit` VARCHAR(10) DEFAULT NULL COMMENT '邀请码注册实际获得的新人奖励单位 HOUR小时 DAY天' AFTER `reward_duration`;
