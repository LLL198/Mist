-- 为 PT 订阅增加 original_title 字段（英文原始标题）
-- 兼容低版本 MySQL（不支持 ADD COLUMN IF NOT EXISTS）

DELIMITER //

DROP PROCEDURE IF EXISTS add_movie_pt_subscribe_original_title_column//
CREATE PROCEDURE add_movie_pt_subscribe_original_title_column()
BEGIN
    IF NOT EXISTS (
        SELECT 1
        FROM information_schema.columns
        WHERE table_schema = DATABASE()
          AND table_name = 'movie_pt_subscribe'
          AND column_name = 'original_title'
    ) THEN
        ALTER TABLE movie_pt_subscribe
            ADD COLUMN original_title VARCHAR(255) DEFAULT NULL COMMENT '原始标题（英文名）' AFTER name;
    END IF;
END//

CALL add_movie_pt_subscribe_original_title_column()//
DROP PROCEDURE IF EXISTS add_movie_pt_subscribe_original_title_column//

DELIMITER ;
