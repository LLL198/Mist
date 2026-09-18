-- 求片列表添加片长和制片国家字段
ALTER TABLE request_list ADD COLUMN runtime INT NULL COMMENT '片长（分钟）';
ALTER TABLE request_list ADD COLUMN production_countries VARCHAR(500) NULL COMMENT '制片国家（逗号分隔）';
