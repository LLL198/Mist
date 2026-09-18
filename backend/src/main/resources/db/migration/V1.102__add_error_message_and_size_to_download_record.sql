-- 为下载记录表增加错误信息和资源大小字段
ALTER TABLE `movie_download_record` 
ADD COLUMN `size` varchar(64) DEFAULT NULL COMMENT '资源大小' AFTER `error_message`;
