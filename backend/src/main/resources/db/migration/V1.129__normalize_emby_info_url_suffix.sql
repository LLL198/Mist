UPDATE `emby_info`
SET `emby_url` = CONCAT(TRIM(TRAILING '/' FROM TRIM(`emby_url`)), '/emby/')
WHERE `emby_url` IS NOT NULL
  AND TRIM(`emby_url`) <> ''
  AND LOWER(TRIM(TRAILING '/' FROM TRIM(`emby_url`))) NOT LIKE '%/emby';
