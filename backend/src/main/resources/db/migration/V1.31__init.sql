-- 添加邀请码字段到 emby_user 表
ALTER TABLE `emby_user` ADD COLUMN `invitation_code` VARCHAR(64) DEFAULT NULL COMMENT '注册时使用的邀请码' AFTER `avatar`;
