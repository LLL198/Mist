-- 为 Telegram 绑定增加有效数据唯一约束，防止高并发下重复绑定。
-- 不直接把 del_flag 放进唯一索引；删除数据的唯一列为 NULL，允许保留多条历史删除记录。

UPDATE user_oauth_binding b
JOIN (
    SELECT id
    FROM (
        SELECT id,
               ROW_NUMBER() OVER (
                   PARTITION BY provider, provider_user_id
                   ORDER BY COALESCE(update_datetime, create_datetime) DESC, id DESC
               ) AS row_num
        FROM user_oauth_binding
        WHERE del_flag = 0
    ) duplicated_provider_user
    WHERE row_num > 1
) d ON b.id = d.id
SET b.del_flag = 1,
    b.update_datetime = NOW(),
    b.update_user_name = COALESCE(b.update_user_name, 'system'),
    b.update_user_id = COALESCE(b.update_user_id, 0);

UPDATE user_oauth_binding b
JOIN (
    SELECT id
    FROM (
        SELECT id,
               ROW_NUMBER() OVER (
                   PARTITION BY provider, user_id
                   ORDER BY COALESCE(update_datetime, create_datetime) DESC, id DESC
               ) AS row_num
        FROM user_oauth_binding
        WHERE del_flag = 0
    ) duplicated_user_provider
    WHERE row_num > 1
) d ON b.id = d.id
SET b.del_flag = 1,
    b.update_datetime = NOW(),
    b.update_user_name = COALESCE(b.update_user_name, 'system'),
    b.update_user_id = COALESCE(b.update_user_id, 0);

DELIMITER //

DROP PROCEDURE IF EXISTS add_user_oauth_binding_active_unique_columns//
CREATE PROCEDURE add_user_oauth_binding_active_unique_columns()
BEGIN
    IF EXISTS (
        SELECT 1
        FROM information_schema.columns
        WHERE table_schema = DATABASE()
          AND table_name = 'user_oauth_binding'
          AND column_name = 'active_provider_user_id'
          AND extra LIKE '%GENERATED%'
    ) THEN
        ALTER TABLE user_oauth_binding
            DROP COLUMN active_provider_user_id;
    END IF;

    IF EXISTS (
        SELECT 1
        FROM information_schema.columns
        WHERE table_schema = DATABASE()
          AND table_name = 'user_oauth_binding'
          AND column_name = 'active_user_id'
          AND extra LIKE '%GENERATED%'
    ) THEN
        ALTER TABLE user_oauth_binding
            DROP COLUMN active_user_id;
    END IF;

    IF NOT EXISTS (
        SELECT 1
        FROM information_schema.columns
        WHERE table_schema = DATABASE()
          AND table_name = 'user_oauth_binding'
          AND column_name = 'active_provider_user_id'
    ) THEN
        ALTER TABLE user_oauth_binding
            ADD COLUMN active_provider_user_id VARCHAR(128) DEFAULT NULL COMMENT '有效绑定第三方用户ID，删除数据为NULL';
    END IF;

    IF NOT EXISTS (
        SELECT 1
        FROM information_schema.columns
        WHERE table_schema = DATABASE()
          AND table_name = 'user_oauth_binding'
          AND column_name = 'active_user_id'
    ) THEN
        ALTER TABLE user_oauth_binding
            ADD COLUMN active_user_id BIGINT DEFAULT NULL COMMENT '有效绑定系统用户ID，删除数据为NULL';
    END IF;
END//

CALL add_user_oauth_binding_active_unique_columns()//
DROP PROCEDURE IF EXISTS add_user_oauth_binding_active_unique_columns//

UPDATE user_oauth_binding
SET active_provider_user_id = CASE WHEN del_flag = 0 THEN provider_user_id ELSE NULL END,
    active_user_id = CASE WHEN del_flag = 0 THEN user_id ELSE NULL END//

DROP TRIGGER IF EXISTS bi_user_oauth_binding_active_unique//
CREATE TRIGGER bi_user_oauth_binding_active_unique
BEFORE INSERT ON user_oauth_binding
FOR EACH ROW
BEGIN
    IF NEW.del_flag = 0 THEN
        SET NEW.active_provider_user_id = NEW.provider_user_id;
        SET NEW.active_user_id = NEW.user_id;
    ELSE
        SET NEW.active_provider_user_id = NULL;
        SET NEW.active_user_id = NULL;
    END IF;
END//

DROP TRIGGER IF EXISTS bu_user_oauth_binding_active_unique//
CREATE TRIGGER bu_user_oauth_binding_active_unique
BEFORE UPDATE ON user_oauth_binding
FOR EACH ROW
BEGIN
    IF NEW.del_flag = 0 THEN
        SET NEW.active_provider_user_id = NEW.provider_user_id;
        SET NEW.active_user_id = NEW.user_id;
    ELSE
        SET NEW.active_provider_user_id = NULL;
        SET NEW.active_user_id = NULL;
    END IF;
END//

DROP PROCEDURE IF EXISTS add_user_oauth_binding_active_unique_indexes//
CREATE PROCEDURE add_user_oauth_binding_active_unique_indexes()
BEGIN
    IF NOT EXISTS (
        SELECT 1
        FROM information_schema.statistics
        WHERE table_schema = DATABASE()
          AND table_name = 'user_oauth_binding'
          AND index_name = 'uk_oauth_provider_active_provider_user'
    ) THEN
        ALTER TABLE user_oauth_binding
            ADD UNIQUE KEY uk_oauth_provider_active_provider_user (provider, active_provider_user_id);
    END IF;

    IF NOT EXISTS (
        SELECT 1
        FROM information_schema.statistics
        WHERE table_schema = DATABASE()
          AND table_name = 'user_oauth_binding'
          AND index_name = 'uk_oauth_provider_active_user'
    ) THEN
        ALTER TABLE user_oauth_binding
            ADD UNIQUE KEY uk_oauth_provider_active_user (provider, active_user_id);
    END IF;
END//

CALL add_user_oauth_binding_active_unique_indexes()//
DROP PROCEDURE IF EXISTS add_user_oauth_binding_active_unique_indexes//

DELIMITER ;
