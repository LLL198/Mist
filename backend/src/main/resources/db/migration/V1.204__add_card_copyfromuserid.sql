ALTER TABLE `card_security_management`
  ADD COLUMN `copyfromuserid` varchar(255) DEFAULT NULL
  COMMENT '卡密指定的 Emby 模板用户 ID，为空时使用服务器默认模板用户'
  AFTER `emby_info_id`;
