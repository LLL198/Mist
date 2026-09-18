alter table play_records
    add emby_info_id bigint null comment '服务器地址id';

alter table emby_ip_locations
    add emby_info_id bigint null comment '服务器地址id';

