-- 为第三方登录绑定表补齐公共审计字段
-- 兼容低版本 MySQL（不支持 ADD COLUMN IF NOT EXISTS）

DELIMITER //

DROP PROCEDURE IF EXISTS add_user_oauth_binding_base_columns//
CREATE PROCEDURE add_user_oauth_binding_base_columns()
BEGIN
    IF NOT EXISTS (
        SELECT 1
        FROM information_schema.columns
        WHERE table_schema = DATABASE()
          AND table_name = 'user_oauth_binding'
          AND column_name = 'create_user_name'
    ) THEN
        ALTER TABLE user_oauth_binding
            ADD COLUMN create_user_name VARCHAR(64) DEFAULT NULL COMMENT '创建人名称' AFTER update_datetime;
    END IF;

    IF NOT EXISTS (
        SELECT 1
        FROM information_schema.columns
        WHERE table_schema = DATABASE()
          AND table_name = 'user_oauth_binding'
          AND column_name = 'update_user_name'
    ) THEN
        ALTER TABLE user_oauth_binding
            ADD COLUMN update_user_name VARCHAR(64) DEFAULT NULL COMMENT '修改人名称' AFTER create_user_name;
    END IF;

    IF NOT EXISTS (
        SELECT 1
        FROM information_schema.columns
        WHERE table_schema = DATABASE()
          AND table_name = 'user_oauth_binding'
          AND column_name = 'update_user_id'
    ) THEN
        ALTER TABLE user_oauth_binding
            ADD COLUMN update_user_id BIGINT DEFAULT NULL COMMENT '修改人id' AFTER update_user_name;
    END IF;

    IF NOT EXISTS (
        SELECT 1
        FROM information_schema.columns
        WHERE table_schema = DATABASE()
          AND table_name = 'user_oauth_binding'
          AND column_name = 'create_user_id'
    ) THEN
        ALTER TABLE user_oauth_binding
            ADD COLUMN create_user_id BIGINT DEFAULT NULL COMMENT '创建人id' AFTER update_user_id;
    END IF;
END//

CALL add_user_oauth_binding_base_columns()//
DROP PROCEDURE IF EXISTS add_user_oauth_binding_base_columns//

DELIMITER ;
