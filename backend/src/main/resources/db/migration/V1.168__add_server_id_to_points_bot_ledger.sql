ALTER TABLE `points_bot_ledger`
    ADD COLUMN `server_id` BIGINT NULL COMMENT '关联 Emby 服务器 ID' AFTER `ref_id`;

UPDATE `points_bot_ledger`
SET `server_id` = CAST(`ref_id` AS UNSIGNED)
WHERE `server_id` IS NULL
  AND `reason` IN ('redeem', 'redeem_refund', 'redeem_renew', 'recharge')
  AND `ref_id` REGEXP '^[0-9]+$';

CREATE INDEX `idx_points_bot_ledger_server_id`
    ON `points_bot_ledger` (`server_id`);
