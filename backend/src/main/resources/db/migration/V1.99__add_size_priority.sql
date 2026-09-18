ALTER TABLE movie_scrape_path_config ADD COLUMN size_priority TINYINT DEFAULT 0 COMMENT '是否大小优先(0否1是)';
ALTER TABLE movie_download_record ADD COLUMN size_priority TINYINT COMMENT '是否大小优先(0否1是)';
