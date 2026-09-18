ALTER TABLE `request_list`
    ADD COLUMN `remark` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '备注' AFTER `parent_tmdb_id`;

ALTER TABLE `request_list`
    MODIFY COLUMN `status` int DEFAULT '0' COMMENT '求片状态 0 已提交 1 已入库 2 已拒绝';
