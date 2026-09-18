CREATE TABLE IF NOT EXISTS tmdb_season
(
    id             BIGINT AUTO_INCREMENT PRIMARY KEY,
    follow_id      BIGINT NOT NULL COMMENT '订阅表主键',
    tmdb_id        INT    NOT NULL COMMENT 'TMDB 剧集 ID',
    season_number  INT    NOT NULL COMMENT '季度编号',
    name           VARCHAR(255) NULL COMMENT '季名称',
    overview       TEXT NULL COMMENT '季简介',
    poster_path    VARCHAR(512) NULL COMMENT '季海报',
    air_date       DATETIME NULL COMMENT '首播日期',
    episode_count  INT NULL COMMENT '季的集数',
    create_datetime DATETIME DEFAULT CURRENT_TIMESTAMP NULL COMMENT '创建时间',
    update_datetime DATETIME DEFAULT CURRENT_TIMESTAMP NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    CONSTRAINT uq_tmdb_season_follow_season UNIQUE (follow_id, season_number)
);
