ALTER TABLE `playback_reporting_record`
    ADD COLUMN `client_name` varchar(255) DEFAULT NULL COMMENT '客户端名称' AFTER `duration`,
    ADD KEY `idx_prr_client_name` (`client_name`);
