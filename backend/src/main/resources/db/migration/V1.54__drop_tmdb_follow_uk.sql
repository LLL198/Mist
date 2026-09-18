-- 删除 tmdb_follow 表的唯一键约束 uk_tmdb_follow_tmdb
ALTER TABLE `tmdb_follow` DROP INDEX `uk_tmdb_follow_tmdb`;
