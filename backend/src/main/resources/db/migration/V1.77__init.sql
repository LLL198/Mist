-- 创建 rocketmq_failed_message 表
CREATE TABLE IF NOT EXISTS `rocketmq_failed_message` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `topic` varchar(255) DEFAULT NULL COMMENT '消息主题',
  `tag` varchar(255) DEFAULT NULL COMMENT '消息标签',
  `msg_key` varchar(255) DEFAULT NULL COMMENT '消息Key',
  `message_body` longtext COMMENT '消息内容',
  `business_type` varchar(50) DEFAULT NULL COMMENT '业务类型',
  `failure_type` varchar(50) DEFAULT NULL COMMENT '失败类型',
  `exception_message` text COMMENT '异常信息',
  `status` int(11) DEFAULT '0' COMMENT '状态 (0: 待处理, 1: 已重试成功, 2: 已放弃)',
  `create_datetime` datetime DEFAULT NULL COMMENT '创建时间',
  `update_datetime` datetime DEFAULT NULL COMMENT '修改时间',
  `create_user_name` varchar(50) DEFAULT NULL COMMENT '创建人名称',
  `update_user_name` varchar(50) DEFAULT NULL COMMENT '修改人名称',
  `create_user_id` bigint(20) DEFAULT NULL COMMENT '创建人id',
  `update_user_id` bigint(20) DEFAULT NULL COMMENT '修改人id',
  `del_flag` int(11) DEFAULT '0' COMMENT '是否删除 0 未删除 1 已删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='RocketMQ 失败消息备份表';
