ALTER TABLE `points_bot_redeem_config`
    ADD COLUMN `redeem_type` varchar(32) NOT NULL DEFAULT 'CREATE_ACCOUNT'
        COMMENT '兑换类型 CREATE_ACCOUNT 注册账号, RENEW 续费'
        AFTER `config_name`,
    ADD KEY `idx_points_bot_redeem_type_enabled` (`redeem_type`, `enabled`, `del_flag`);
