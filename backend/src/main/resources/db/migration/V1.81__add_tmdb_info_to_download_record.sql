-- 添加 tmdb_id 和 media_type 字段
ALTER TABLE `movie_download_record`
ADD COLUMN `tmdb_id` bigint DEFAULT NULL COMMENT 'TMDB ID',
ADD COLUMN `media_type` varchar(20) DEFAULT NULL COMMENT '媒体类型 movie/tv';
