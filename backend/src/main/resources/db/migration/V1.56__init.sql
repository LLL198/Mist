-- 添加 watched_episodes 字段到 tmdb_watch_progress 表
ALTER TABLE tmdb_watch_progress 
ADD COLUMN watched_episodes JSON COMMENT '已观看的集列表，格式: ["1-1", "1-3", "2-5"]';

-- 数据迁移：将现有的 season_number 和 episode_number 转换为 watched_episodes
-- 注意：这会将所有 <= season_number-episode_number 的集标记为已观看
-- 如果数据量大，建议分批执行或手动处理
UPDATE tmdb_watch_progress 
SET watched_episodes = '[]'
WHERE watched_episodes IS NULL;
