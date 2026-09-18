-- 新增 TMDB 追踪的媒体类型与电影上映字段
-- 使用存储过程方式避免列已存在时报错

DELIMITER //

DROP PROCEDURE IF EXISTS add_tmdb_follow_columns//
CREATE PROCEDURE add_tmdb_follow_columns()
BEGIN
    IF NOT EXISTS (SELECT 1 FROM information_schema.columns WHERE table_schema = DATABASE() AND table_name = 'tmdb_follow' AND column_name = 'media_type') THEN
        ALTER TABLE tmdb_follow ADD COLUMN media_type VARCHAR(16) DEFAULT 'tv';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM information_schema.columns WHERE table_schema = DATABASE() AND table_name = 'tmdb_follow' AND column_name = 'release_date') THEN
        ALTER TABLE tmdb_follow ADD COLUMN release_date DATETIME NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM information_schema.columns WHERE table_schema = DATABASE() AND table_name = 'tmdb_follow' AND column_name = 'runtime_minutes') THEN
        ALTER TABLE tmdb_follow ADD COLUMN runtime_minutes INT NULL;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM information_schema.columns WHERE table_schema = DATABASE() AND table_name = 'tmdb_follow' AND column_name = 'release_notified') THEN
        ALTER TABLE tmdb_follow ADD COLUMN release_notified TINYINT(1) DEFAULT 0;
    END IF;
END//

CALL add_tmdb_follow_columns()//
DROP PROCEDURE IF EXISTS add_tmdb_follow_columns//

-- 季度增加演员信息
DROP PROCEDURE IF EXISTS add_tmdb_season_casts//
CREATE PROCEDURE add_tmdb_season_casts()
BEGIN
    IF NOT EXISTS (SELECT 1 FROM information_schema.columns WHERE table_schema = DATABASE() AND table_name = 'tmdb_season' AND column_name = 'casts') THEN
        ALTER TABLE tmdb_season ADD COLUMN casts TEXT NULL;
    END IF;
END//

CALL add_tmdb_season_casts()//
DROP PROCEDURE IF EXISTS add_tmdb_season_casts//

DELIMITER ;
