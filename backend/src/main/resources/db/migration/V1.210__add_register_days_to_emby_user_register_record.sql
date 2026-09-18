ALTER TABLE `emby_user_register_record`
    ADD COLUMN `register_days` INT NULL COMMENT '本次注册实际获得的有效天数，永久用户为空' AFTER `expiration_date`;

-- 兼容历史注册记录：按注册时间与当时写入的到期时间回填，不使用当前剩余天数。
UPDATE `emby_user_register_record`
SET `register_days` = DATEDIFF(`expiration_date`, `create_datetime`)
WHERE `register_days` IS NULL
  AND `create_datetime` IS NOT NULL
  AND `expiration_date` IS NOT NULL
  AND DATEDIFF(`expiration_date`, `create_datetime`) > 0;
