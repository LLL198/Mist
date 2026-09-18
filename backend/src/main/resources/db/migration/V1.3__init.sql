alter table card_security_management
    add emby_info_id bigint null comment 'emby服务器地址id';

alter table emby_user
    add emby_info_id bigint null comment 'emby服务器地址id';

