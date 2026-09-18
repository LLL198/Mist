-- 添加管理员查询用户ID字段，用于管理员查询媒体库数据
ALTER TABLE emby_info ADD COLUMN admin_query_userid VARCHAR(100) NULL COMMENT '管理员查询用户ID，用于管理员查询媒体库数据';

-- 将现有数据的 copyfromuserid 复制到新字段
UPDATE emby_info SET admin_query_userid = copyfromuserid WHERE admin_query_userid IS NULL;
