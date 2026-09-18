-- 为 PT 订阅增加海报/壁纸字段
-- 兼容低版本 MySQL（不支持 ADD COLUMN IF NOT EXISTS）

DELIMITER //

DROP PROCEDURE IF EXISTS add_movie_pt_subscribe_image_columns//
CREATE PROCEDURE add_movie_pt_subscribe_image_columns()
BEGIN
    IF NOT EXISTS (
        SELECT 1
        FROM information_schema.columns
        WHERE table_schema = DATABASE()
          AND table_name = 'movie_pt_subscribe'
          AND column_name = 'poster_path'
    ) THEN
        ALTER TABLE movie_pt_subscribe
            ADD COLUMN poster_path VARCHAR(1000) DEFAULT NULL COMMENT '海报图片URL' AFTER tmdb_id;
    END IF;

    IF NOT EXISTS (
        SELECT 1
        FROM information_schema.columns
        WHERE table_schema = DATABASE()
          AND table_name = 'movie_pt_subscribe'
          AND column_name = 'backdrop_path'
    ) THEN
        ALTER TABLE movie_pt_subscribe
            ADD COLUMN backdrop_path VARCHAR(1000) DEFAULT NULL COMMENT '壁纸图片URL' AFTER poster_path;
    END IF;
END//

CALL add_movie_pt_subscribe_image_columns()//
DROP PROCEDURE IF EXISTS add_movie_pt_subscribe_image_columns//

DELIMITER ;
