alter table request_list
    add emby_info_id bigint DEFAULT NULL COMMENT 'emby服务器id';

alter table request_list
    add emby_server_id varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT 'emby服务器唯一ID';