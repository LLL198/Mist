ALTER TABLE movie_scrape_path_config 
ADD COLUMN overwrite TINYINT(1) DEFAULT 0 COMMENT '是否覆盖 (0:否, 1:是)',
ADD COLUMN coexist TINYINT(1) DEFAULT 0 COMMENT '是否并存 (0:否, 1:是)',
ADD COLUMN quality_priority TINYINT(1) DEFAULT 0 COMMENT '是否画质优先 (0:否, 1:是)',
ADD COLUMN hardlink_mode TINYINT(1) DEFAULT 1 COMMENT '是否硬链接 (0:复制, 1:硬链接)';

ALTER TABLE movie_download_record 
ADD COLUMN overwrite TINYINT(1) DEFAULT 0 COMMENT '是否覆盖',
ADD COLUMN coexist TINYINT(1) DEFAULT 0 COMMENT '是否并存',
ADD COLUMN quality_priority TINYINT(1) DEFAULT 0 COMMENT '是否画质优先',
ADD COLUMN hardlink_mode TINYINT(1) DEFAULT 1 COMMENT '是否硬链接';
