ALTER TABLE `host_line`
  ADD COLUMN `line_type` TINYINT DEFAULT 0 COMMENT '线路类型 0通用线路 1白名单线路' AFTER `line_name`;

ALTER TABLE `emby_user`
  ADD COLUMN `host_line_type` TINYINT DEFAULT 0 COMMENT '线路类型 0通用线路 1白名单线路' AFTER `emby_info_id`;

ALTER TABLE `card_security_management`
  ADD COLUMN `host_line_type` TINYINT DEFAULT 0 COMMENT '线路类型 0通用线路 1白名单线路' AFTER `emby_info_id`;

ALTER TABLE `invitation_code`
  ADD COLUMN `host_line_type` TINYINT DEFAULT 0 COMMENT '线路类型 0通用线路 1白名单线路' AFTER `emby_info_id`;

CREATE INDEX `idx_host_line_type` ON `host_line` (`emby_info_id`, `line_type`, `enabled`, `is_display`);
