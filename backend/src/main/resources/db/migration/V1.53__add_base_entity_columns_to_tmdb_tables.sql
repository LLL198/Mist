-- 为 tmdb_season 表添加 BaseEntity 所需的缺失字段
-- 该表在 V1.51 创建时缺少了这些通用字段

ALTER TABLE tmdb_season
    ADD COLUMN create_user_name VARCHAR(64) NULL COMMENT '创建人名称',
    ADD COLUMN update_user_name VARCHAR(64) NULL COMMENT '修改人名称',
    ADD COLUMN update_user_id BIGINT NULL COMMENT '修改人id',
    ADD COLUMN create_user_id BIGINT NULL COMMENT '创建人id',
    ADD COLUMN del_flag TINYINT(1) DEFAULT 0 NOT NULL COMMENT '是否删除 0 未删除 1 已删除';

-- 添加表注释
ALTER TABLE tmdb_season COMMENT = 'TMDB 剧集季度信息';
