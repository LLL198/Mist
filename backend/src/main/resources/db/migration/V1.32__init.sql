-- 添加邀请码注册用户有效天数字段
ALTER TABLE `invitation_code` ADD COLUMN `validity_days` INT DEFAULT NULL COMMENT '注册用户的账户有效天数' AFTER `used_datetime`;
