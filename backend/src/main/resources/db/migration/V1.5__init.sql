alter table emby_notify_data
    add emby_info_id bigint null comment 'emby服务器地址id';

alter table emby_notify_data_details
    add emby_info_id bigint null comment 'emby服务器地址id';

