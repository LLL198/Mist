ALTER TABLE `request_list`
    ADD COLUMN `audit_status` int DEFAULT 0 COMMENT '审核状态 0 待审核 1 已通过 2 已拒绝' AFTER `status`;
