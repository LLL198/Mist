ALTER TABLE `telegram_binding_review`
    ADD COLUMN `review_uuid` CHAR(36) NULL COMMENT '对外审批UUID' AFTER `id`;

UPDATE `telegram_binding_review`
SET `review_uuid` = UUID()
WHERE `review_uuid` IS NULL OR `review_uuid` = '';

ALTER TABLE `telegram_binding_review`
    MODIFY COLUMN `review_uuid` CHAR(36) NOT NULL COMMENT '对外审批UUID',
    ADD UNIQUE KEY `uk_tg_binding_review_uuid` (`review_uuid`),
    MODIFY COLUMN `status` TINYINT NOT NULL DEFAULT 0
        COMMENT '状态 0待审批 1已通过 2已拒绝 3用户自助取消';
