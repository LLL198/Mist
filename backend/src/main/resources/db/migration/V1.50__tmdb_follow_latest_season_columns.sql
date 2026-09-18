-- 为 TMDB 追剧订阅增加最新季信息，方便列表展示最新季分集与海报
ALTER TABLE tmdb_follow
    ADD COLUMN latest_season_number INT NULL,
    ADD COLUMN latest_season_name VARCHAR(255) NULL,
    ADD COLUMN latest_season_overview TEXT NULL,
    ADD COLUMN latest_season_poster_path VARCHAR(255) NULL;
