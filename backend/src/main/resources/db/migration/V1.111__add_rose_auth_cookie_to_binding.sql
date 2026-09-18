-- 为 Rose 绑定增加用户登录态 Cookie 字段
-- 兼容低版本 MySQL（不支持 ADD COLUMN IF NOT EXISTS）

DELIMITER //

DROP PROCEDURE IF EXISTS add_rose_auth_cookie_text_column//
CREATE PROCEDURE add_rose_auth_cookie_text_column()
BEGIN
    IF NOT EXISTS (
        SELECT 1
        FROM information_schema.columns
        WHERE table_schema = DATABASE()
          AND table_name = 'rose_user_binding'
          AND column_name = 'rose_auth_cookie_text'
    ) THEN
        ALTER TABLE rose_user_binding
            ADD COLUMN rose_auth_cookie_text LONGTEXT COMMENT 'Rose用户登录态Cookie，仅后端代理使用' AFTER cookie_text_masked;
    END IF;
END//

CALL add_rose_auth_cookie_text_column()//
DROP PROCEDURE IF EXISTS add_rose_auth_cookie_text_column//

DELIMITER ;
