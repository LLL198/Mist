ALTER TABLE `media_main`
    ADD COLUMN `emby_info_id` bigint DEFAULT NULL COMMENT '服务器ID' AFTER `title`,
    ADD KEY `idx_emby_info_id` (`emby_info_id`);

UPDATE `media_main` mm
JOIN (
    SELECT
        mvd.`media_id`,
        MIN(eu.`emby_info_id`) AS `emby_info_id`
    FROM `media_view_detail` mvd
    JOIN `emby_user` eu ON eu.`id` = mvd.`user_id`
    WHERE mvd.`media_id` IS NOT NULL
      AND eu.`emby_info_id` IS NOT NULL
    GROUP BY mvd.`media_id`
) source ON source.`media_id` = mm.`id`
SET mm.`emby_info_id` = source.`emby_info_id`
WHERE mm.`emby_info_id` IS NULL;
