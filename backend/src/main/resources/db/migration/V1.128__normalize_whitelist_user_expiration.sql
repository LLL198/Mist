UPDATE `emby_user`
SET `expiration_date` = NULL
WHERE `host_line_type` = 1
  AND `expiration_date` IS NOT NULL;
