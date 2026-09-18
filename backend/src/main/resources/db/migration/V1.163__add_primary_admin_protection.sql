ALTER TABLE `emby_user`
  ADD COLUMN `is_primary_admin` tinyint NOT NULL DEFAULT 0 COMMENT '是否首次注册的最高管理员 0 否 1 是' AFTER `is_admin`;

UPDATE `emby_user`
SET `is_primary_admin` = 1
WHERE `id` = (
  SELECT `primary_admin`.`id`
  FROM (
    SELECT `id`
    FROM `emby_user`
    WHERE `del_flag` = 0
      AND `is_admin` = 1
    ORDER BY CASE WHEN `emby_info_id` IS NULL THEN 0 ELSE 1 END, `id` ASC
    LIMIT 1
  ) AS `primary_admin`
);
