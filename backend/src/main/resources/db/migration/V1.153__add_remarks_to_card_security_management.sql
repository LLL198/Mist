ALTER TABLE `card_security_management`
  ADD COLUMN `remarks` varchar(255) DEFAULT NULL COMMENT '备注' AFTER `emby_info_id`;
