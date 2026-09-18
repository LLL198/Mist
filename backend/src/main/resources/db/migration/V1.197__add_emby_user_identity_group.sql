-- 历史账号默认各自属于独立身份组，避免把不同用户仅因同名同密码而误合并。
ALTER TABLE `emby_user`
    ADD COLUMN `identity_group_id` BIGINT NULL COMMENT '可信身份组ID，仅由系统创建流程分配' AFTER `emby_info_id`;

UPDATE `emby_user`
SET `identity_group_id` = `id`
WHERE `identity_group_id` IS NULL;

ALTER TABLE `emby_user`
    MODIFY COLUMN `identity_group_id` BIGINT NOT NULL COMMENT '可信身份组ID，仅由系统创建流程分配';

CREATE INDEX `idx_emby_user_identity_group`
    ON `emby_user` (`identity_group_id`, `del_flag`, `user_status`, `emby_info_id`);
