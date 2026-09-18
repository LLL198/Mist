UPDATE `telegram_binding_review`
SET `review_uuid` = CONCAT('FOAM', UPPER(REPLACE(`review_uuid`, '-', '')))
WHERE UPPER(`review_uuid`) NOT LIKE 'FOAM%';

UPDATE `telegram_binding_review`
SET `review_uuid` = UPPER(`review_uuid`)
WHERE `review_uuid` <> UPPER(`review_uuid`);

ALTER TABLE `telegram_binding_review`
    MODIFY COLUMN `review_uuid` CHAR(36) NOT NULL COMMENT '对外审批指纹，FOAM前缀加32位大写字符';
