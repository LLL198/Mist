-- 加速独立开户注册时的跨服务器同名同密码碰撞检查。
-- 密码摘要仅保留在既有业务列中，不新增明文或额外凭证副本。
CREATE INDEX `idx_emby_user_active_credential`
    ON `emby_user` (`emby_user_name`, `emby_user_password`, `del_flag`);
