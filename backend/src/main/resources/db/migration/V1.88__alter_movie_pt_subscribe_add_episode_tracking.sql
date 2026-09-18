-- 为 PT 订阅增加剧集追更字段
-- 兼容低版本 MySQL（不支持 ADD COLUMN IF NOT EXISTS）

DELIMITER //

DROP PROCEDURE IF EXISTS add_movie_pt_subscribe_episode_columns//
CREATE PROCEDURE add_movie_pt_subscribe_episode_columns()
BEGIN
    IF NOT EXISTS (
        SELECT 1
        FROM information_schema.columns
        WHERE table_schema = DATABASE()
          AND table_name = 'movie_pt_subscribe'
          AND column_name = 'start_episode'
    ) THEN
        ALTER TABLE movie_pt_subscribe
            ADD COLUMN start_episode INT DEFAULT 1 COMMENT '开始追更集数（剧集可用）' AFTER season;
    END IF;

    IF NOT EXISTS (
        SELECT 1
        FROM information_schema.columns
        WHERE table_schema = DATABASE()
          AND table_name = 'movie_pt_subscribe'
          AND column_name = 'tmdb_latest_episode'
    ) THEN
        ALTER TABLE movie_pt_subscribe
            ADD COLUMN tmdb_latest_episode INT DEFAULT NULL COMMENT 'TMDB当前季最新已播集数' AFTER state;
    END IF;

    IF NOT EXISTS (
        SELECT 1
        FROM information_schema.columns
        WHERE table_schema = DATABASE()
          AND table_name = 'movie_pt_subscribe'
          AND column_name = 'last_downloaded_episode'
    ) THEN
        ALTER TABLE movie_pt_subscribe
            ADD COLUMN last_downloaded_episode INT NOT NULL DEFAULT 0 COMMENT '最近统计的已下载最大集数' AFTER tmdb_latest_episode;
    END IF;
END//

CALL add_movie_pt_subscribe_episode_columns()//
DROP PROCEDURE IF EXISTS add_movie_pt_subscribe_episode_columns//

DELIMITER ;
