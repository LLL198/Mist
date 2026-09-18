alter table emby_notify_data
    add vote_average double null comment '评分',
    add vote_count int null comment '评分人数';
