-- 删除 host_line 表的约束（如果存在）
-- 使用存储过程来安全地删除约束

DELIMITER //

-- 删除外键约束（如果存在）
DROP PROCEDURE IF EXISTS drop_fk_if_exists//
CREATE PROCEDURE drop_fk_if_exists()
BEGIN
    IF EXISTS (
        SELECT 1 FROM INFORMATION_SCHEMA.TABLE_CONSTRAINTS
        WHERE TABLE_SCHEMA = DATABASE()
        AND TABLE_NAME = 'host_line'
        AND CONSTRAINT_NAME = 'fk_host_line_emby_info'
        AND CONSTRAINT_TYPE = 'FOREIGN KEY'
    ) THEN
        ALTER TABLE `host_line` DROP FOREIGN KEY `fk_host_line_emby_info`;
    END IF;
END//
CALL drop_fk_if_exists()//
DROP PROCEDURE IF EXISTS drop_fk_if_exists//

-- 删除唯一索引 uk_host_line_name（如果存在）
DROP PROCEDURE IF EXISTS drop_uk_name_if_exists//
CREATE PROCEDURE drop_uk_name_if_exists()
BEGIN
    IF EXISTS (
        SELECT 1 FROM INFORMATION_SCHEMA.STATISTICS
        WHERE TABLE_SCHEMA = DATABASE()
        AND TABLE_NAME = 'host_line'
        AND INDEX_NAME = 'uk_host_line_name'
    ) THEN
        ALTER TABLE `host_line` DROP INDEX `uk_host_line_name`;
    END IF;
END//
CALL drop_uk_name_if_exists()//
DROP PROCEDURE IF EXISTS drop_uk_name_if_exists//

-- 删除唯一索引 uk_host_line_endpoint（如果存在）
DROP PROCEDURE IF EXISTS drop_uk_endpoint_if_exists//
CREATE PROCEDURE drop_uk_endpoint_if_exists()
BEGIN
    IF EXISTS (
        SELECT 1 FROM INFORMATION_SCHEMA.STATISTICS
        WHERE TABLE_SCHEMA = DATABASE()
        AND TABLE_NAME = 'host_line'
        AND INDEX_NAME = 'uk_host_line_endpoint'
    ) THEN
        ALTER TABLE `host_line` DROP INDEX `uk_host_line_endpoint`;
    END IF;
END//
CALL drop_uk_endpoint_if_exists()//
DROP PROCEDURE IF EXISTS drop_uk_endpoint_if_exists//

DELIMITER ;
