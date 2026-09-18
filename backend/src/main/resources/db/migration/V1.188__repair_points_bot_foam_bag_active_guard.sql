-- MyBatis-Plus 默认忽略更新对象中的 NULL 字段，早期归还和逾期记录可能仍占用活动唯一键。
UPDATE `points_bot_foam_bag`
SET `active_guard` = NULL
WHERE `status` IN ('REPAID', 'PENALIZED')
  AND `active_guard` IS NOT NULL;
