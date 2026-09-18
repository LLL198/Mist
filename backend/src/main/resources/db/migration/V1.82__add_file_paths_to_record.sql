-- 添加源文件路径和链接文件路径字段
ALTER TABLE `movie_download_record`
ADD COLUMN `source_file_path` varchar(1000) DEFAULT NULL COMMENT '原始文件路径',
ADD COLUMN `link_file_path` varchar(1000) DEFAULT NULL COMMENT '链接文件路径';
