-- 下载记录单表化：
-- 1) 在 movie_download_record 增加季集字段；
-- 2) 将旧明细表首条记录回填到主表（兼容历史数据）；
-- 3) 保留旧明细表，仅标记为历史兼容，不再用于新逻辑。

DELIMITER //

DROP PROCEDURE IF EXISTS flatten_movie_download_record_to_single_table//
CREATE PROCEDURE flatten_movie_download_record_to_single_table()
BEGIN
    -- 追加季集字段（若不存在）
    IF NOT EXISTS (
        SELECT 1
        FROM information_schema.columns
        WHERE table_schema = DATABASE()
          AND table_name = 'movie_download_record'
          AND column_name = 'episode_codes'
    ) THEN
        ALTER TABLE movie_download_record
            ADD COLUMN episode_codes VARCHAR(255) DEFAULT NULL COMMENT '季集编码（如S01E01,S01E02）';
    END IF;

    IF NOT EXISTS (
        SELECT 1
        FROM information_schema.columns
        WHERE table_schema = DATABASE()
          AND table_name = 'movie_download_record'
          AND column_name = 'episode_seqs'
    ) THEN
        ALTER TABLE movie_download_record
            ADD COLUMN episode_seqs VARCHAR(100) DEFAULT NULL COMMENT '集数展示（如1-2）';
    END IF;

    -- 回填历史明细数据（仅当明细表存在时）
    IF EXISTS (
        SELECT 1
        FROM information_schema.tables
        WHERE table_schema = DATABASE()
          AND table_name = 'movie_download_record_file'
    ) THEN
        UPDATE movie_download_record r
        SET r.episode_codes = COALESCE(
                NULLIF(r.episode_codes, ''),
                (SELECT f.episode_codes
                 FROM movie_download_record_file f
                 WHERE f.record_id = r.id
                   AND f.del_flag = 0
                 ORDER BY f.id
                 LIMIT 1)
            ),
            r.episode_seqs = COALESCE(
                NULLIF(r.episode_seqs, ''),
                (SELECT f.episode_seqs
                 FROM movie_download_record_file f
                 WHERE f.record_id = r.id
                   AND f.del_flag = 0
                 ORDER BY f.id
                 LIMIT 1)
            ),
            r.source_file_path = COALESCE(
                NULLIF(r.source_file_path, ''),
                (SELECT f.source_file_path
                 FROM movie_download_record_file f
                 WHERE f.record_id = r.id
                   AND f.del_flag = 0
                 ORDER BY f.id
                 LIMIT 1)
            ),
            r.link_file_path = COALESCE(
                NULLIF(r.link_file_path, ''),
                (SELECT f.link_file_path
                 FROM movie_download_record_file f
                 WHERE f.record_id = r.id
                   AND f.del_flag = 0
                 ORDER BY f.id
                 LIMIT 1)
            )
        WHERE r.del_flag = 0;

        ALTER TABLE movie_download_record_file
            COMMENT = '下载记录文件明细表（历史兼容，已废弃，不再写入）';
    END IF;
END//

CALL flatten_movie_download_record_to_single_table()//
DROP PROCEDURE IF EXISTS flatten_movie_download_record_to_single_table//

DELIMITER ;
