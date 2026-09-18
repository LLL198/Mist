-- 为下载记录表增加错误信息字段
ALTER TABLE `movie_download_record`
ADD COLUMN `error_message` varchar(512) DEFAULT NULL COMMENT '整理失败的原因' AFTER `hardlink_mode`;