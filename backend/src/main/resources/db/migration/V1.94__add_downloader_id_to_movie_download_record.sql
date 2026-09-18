-- 为下载记录增加下载器字段
-- 兼容低版本 MySQL（不支持 ADD COLUMN IF NOT EXISTS / ADD INDEX IF NOT EXISTS）

DELIMITER //

DROP PROCEDURE IF EXISTS add_downloader_id_to_movie_download_record//
CREATE PROCEDURE add_downloader_id_to_movie_download_record()
BEGIN
    IF NOT EXISTS (
        SELECT 1
        FROM information_schema.columns
        WHERE table_schema = DATABASE()
          AND table_name = 'movie_download_record'
          AND column_name = 'downloader_id'
    ) THEN
        ALTER TABLE movie_download_record
            ADD COLUMN downloader_id BIGINT DEFAULT NULL COMMENT '下载器ID' AFTER subscribe_id;
    END IF;

    IF NOT EXISTS (
        SELECT 1
        FROM information_schema.statistics
        WHERE table_schema = DATABASE()
          AND table_name = 'movie_download_record'
          AND index_name = 'idx_movie_download_record_downloader_id'
    ) THEN
        ALTER TABLE movie_download_record
            ADD INDEX idx_movie_download_record_downloader_id (downloader_id);
    END IF;
END//

CALL add_downloader_id_to_movie_download_record()//
DROP PROCEDURE IF EXISTS add_downloader_id_to_movie_download_record//

DELIMITER ;
