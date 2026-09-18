-- 更新 movie_pt_subscribe.state 字段备注，补充 C=订阅完结
ALTER TABLE `movie_pt_subscribe`
    MODIFY COLUMN `state` varchar(1) NOT NULL DEFAULT 'N' COMMENT '订阅状态 N新建 R订阅中 S暂停 C订阅完结';
