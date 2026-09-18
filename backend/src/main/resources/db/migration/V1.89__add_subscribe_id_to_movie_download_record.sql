-- 为下载记录增加订阅关联字段
-- 兼容低版本 MySQL（不支持 ADD COLUMN IF NOT EXISTS / ADD INDEX IF NOT EXISTS）

DELIMITER //

DROP PROCEDURE IF EXISTS add_subscribe_id_to_movie_download_record//
CREATE PROCEDURE add_subscribe_id_to_movie_download_record()
BEGIN
    IF NOT EXISTS (
        SELECT 1
        FROM information_schema.columns
        WHERE table_schema = DATABASE()
          AND table_name = 'movie_download_record'
          AND column_name = 'subscribe_id'
    ) THEN
        ALTER TABLE movie_download_record
            ADD COLUMN subscribe_id BIGINT DEFAULT NULL COMMENT '关联订阅ID' AFTER media_type;
    END IF;

    IF NOT EXISTS (
        SELECT 1
        FROM information_schema.statistics
        WHERE table_schema = DATABASE()
          AND table_name = 'movie_download_record'
          AND index_name = 'idx_movie_download_record_subscribe_id'
    ) THEN
        ALTER TABLE movie_download_record
            ADD INDEX idx_movie_download_record_subscribe_id (subscribe_id);
    END IF;
END//

CALL add_subscribe_id_to_movie_download_record()//
DROP PROCEDURE IF EXISTS add_subscribe_id_to_movie_download_record//

DELIMITER ;
