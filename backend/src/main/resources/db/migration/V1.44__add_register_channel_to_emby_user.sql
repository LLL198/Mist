ALTER TABLE `emby_user`
    ADD COLUMN `register_channel` TINYINT DEFAULT NULL COMMENT '注册渠道 1 管理员注册 2 卡密注册 3 邀请码注册 4 积分兑换 5 用户注册'
    AFTER `invitation_code`;
