UPDATE emby_user
SET register_channel = 1
WHERE register_channel = 1 AND is_admin = 1;

UPDATE emby_user
SET register_channel = 2
WHERE register_channel = 1 AND (is_admin IS NULL OR is_admin = 0);

UPDATE emby_user
SET register_channel = 3
WHERE register_channel = 2;

UPDATE emby_user
SET register_channel = 4
WHERE register_channel = 3;
