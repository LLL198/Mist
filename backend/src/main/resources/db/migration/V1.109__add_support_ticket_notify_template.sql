INSERT INTO `notify_template` (`template_code`, `template_name`, `channel_type`, `template_content`, `variable_comment`, `enabled`, `remark`, `create_datetime`, `update_datetime`, `create_user_name`, `update_user_name`, `update_user_id`, `create_user_id`, `del_flag`)
SELECT 'support_ticket_submitted', '工单提交-通用', 'common', '新工单提醒\n\n工单ID：${ticketId}\n标题：${ticketTitle}\n提交人：${ticketSubmitter}\n提交时间：${ticketSubmitTime}\n\n内容：\n${ticketContent}', 'ticketId, ticketTitle, ticketSubmitter, ticketSubmitTime, ticketContent', 1, '工单提交通知', NOW(), NOW(), 'system', 'system', NULL, NULL, 0
WHERE NOT EXISTS (SELECT 1 FROM `notify_template` WHERE `template_code` = 'support_ticket_submitted' AND `channel_type` = 'common');

INSERT INTO `notify_template` (`template_code`, `template_name`, `channel_type`, `template_content`, `variable_comment`, `enabled`, `remark`, `create_datetime`, `update_datetime`, `create_user_name`, `update_user_name`, `update_user_id`, `create_user_id`, `del_flag`)
SELECT 'support_ticket_submitted', '工单提交-telegram', 'telegram', '新工单提醒\n\n工单ID：${ticketId}\n标题：${ticketTitle}\n提交人：${ticketSubmitter}\n提交时间：${ticketSubmitTime}\n\n内容：\n${ticketContent}', 'ticketId, ticketTitle, ticketSubmitter, ticketSubmitTime, ticketContent', 1, '工单提交通知', NOW(), NOW(), 'system', 'system', NULL, NULL, 0
WHERE NOT EXISTS (SELECT 1 FROM `notify_template` WHERE `template_code` = 'support_ticket_submitted' AND `channel_type` = 'telegram');

INSERT INTO `notify_template` (`template_code`, `template_name`, `channel_type`, `template_content`, `variable_comment`, `enabled`, `remark`, `create_datetime`, `update_datetime`, `create_user_name`, `update_user_name`, `update_user_id`, `create_user_id`, `del_flag`)
SELECT 'support_ticket_submitted', '工单提交-企业微信', 'wechat', '新工单提醒\n工单ID：${ticketId}\n标题：${ticketTitle}\n提交人：${ticketSubmitter}\n提交时间：${ticketSubmitTime}\n内容：${ticketContent}', 'ticketId, ticketTitle, ticketSubmitter, ticketSubmitTime, ticketContent', 1, '工单提交通知', NOW(), NOW(), 'system', 'system', NULL, NULL, 0
WHERE NOT EXISTS (SELECT 1 FROM `notify_template` WHERE `template_code` = 'support_ticket_submitted' AND `channel_type` = 'wechat');

INSERT INTO `notify_template` (`template_code`, `template_name`, `channel_type`, `template_content`, `variable_comment`, `enabled`, `remark`, `create_datetime`, `update_datetime`, `create_user_name`, `update_user_name`, `update_user_id`, `create_user_id`, `del_flag`)
SELECT 'support_ticket_submitted', '工单提交-钉钉', 'dingding', '新工单提醒\n\n工单ID：${ticketId}\n标题：${ticketTitle}\n提交人：${ticketSubmitter}\n提交时间：${ticketSubmitTime}\n\n内容：\n${ticketContent}', 'ticketId, ticketTitle, ticketSubmitter, ticketSubmitTime, ticketContent', 1, '工单提交通知', NOW(), NOW(), 'system', 'system', NULL, NULL, 0
WHERE NOT EXISTS (SELECT 1 FROM `notify_template` WHERE `template_code` = 'support_ticket_submitted' AND `channel_type` = 'dingding');

INSERT INTO `notify_template` (`template_code`, `template_name`, `channel_type`, `template_content`, `variable_comment`, `enabled`, `remark`, `create_datetime`, `update_datetime`, `create_user_name`, `update_user_name`, `update_user_id`, `create_user_id`, `del_flag`)
SELECT 'support_ticket_submitted', '工单提交-消息推送', 'messagepush', '新工单提醒\n工单ID：${ticketId}\n标题：${ticketTitle}\n提交人：${ticketSubmitter}\n提交时间：${ticketSubmitTime}\n内容：${ticketContent}', 'ticketId, ticketTitle, ticketSubmitter, ticketSubmitTime, ticketContent', 1, '工单提交通知', NOW(), NOW(), 'system', 'system', NULL, NULL, 0
WHERE NOT EXISTS (SELECT 1 FROM `notify_template` WHERE `template_code` = 'support_ticket_submitted' AND `channel_type` = 'messagepush');
