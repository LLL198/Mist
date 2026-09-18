-- movie_pt_subscribe.site_id 支持多站点（逗号分隔）
ALTER TABLE `movie_pt_subscribe`
    MODIFY COLUMN `site_id` varchar(500) DEFAULT NULL COMMENT '指定站点ID列表（逗号分隔，为空时搜索全部启用站点）';
