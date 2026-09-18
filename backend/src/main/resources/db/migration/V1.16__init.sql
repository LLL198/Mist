ALTER TABLE `play_records`
    MODIFY COLUMN `emby_user_name` varchar(500)
    CHARACTER SET utf8mb4
    COLLATE utf8mb4_unicode_ci
    DEFAULT NULL
    COMMENT 'emby用户名';