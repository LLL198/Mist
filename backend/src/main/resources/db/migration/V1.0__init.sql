/*
 Navicat Premium Dump SQL

 Source Server         : 123.207.59.154 15004
 Source Server Type    : MySQL
 Source Server Version : 80406 (8.4.6)
 Source Host           : 123.207.59.154:15004
 Source Schema         : foam-api

 Target Server Type    : MySQL
 Target Server Version : 80406 (8.4.6)
 File Encoding         : 65001

 Date: 15/11/2025 17:29:38
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for card_security_management
-- ----------------------------
DROP TABLE IF EXISTS `card_security_management`;
CREATE TABLE `card_security_management` (
                                            `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键id',
                                            `card_password` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '卡密',
                                            `card_validity` int DEFAULT NULL COMMENT '卡密有效期',
                                            `card_status` int DEFAULT '0' COMMENT '卡密状态 0 未使用 1 已使用',
                                            `user_id` bigint DEFAULT NULL COMMENT '用户id',
                                            `emby_user_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT 'emby用户名',
                                            `create_datetime` datetime DEFAULT NULL COMMENT '创建时间',
                                            `update_datetime` datetime DEFAULT NULL COMMENT '修改时间',
                                            `create_user_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '创建人名称',
                                            `update_user_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '修改人名称',
                                            `update_user_id` bigint DEFAULT NULL COMMENT '修改人id',
                                            `create_user_id` bigint DEFAULT NULL COMMENT '创建人id',
                                            `del_flag` int DEFAULT '0' COMMENT '是否删除 0 未删除 1 已删除',
                                            PRIMARY KEY (`id`) USING BTREE,
                                            KEY `idx_card_status_user_id` (`card_status`,`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=470 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='卡密管理';

-- ----------------------------
-- Records of card_security_management
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for emby_info
-- ----------------------------
DROP TABLE IF EXISTS `emby_info`;
CREATE TABLE `emby_info` (
                             `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键id',
                             `emby_url` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT 'emby服务器url',
                             `emby_port` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT 'emby服务器端口',
                             `emby_agreement` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT 'emby服务器协议',
                             `status` int DEFAULT '0' COMMENT '运行状态 0 正常 1 禁用',
                             `user_id` bigint DEFAULT NULL COMMENT '用户id',
                             `copyfromuserid` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '用于查询最新入库的 Emby 用户 ID',
                             `create_datetime` datetime DEFAULT NULL COMMENT '创建时间',
                             `update_datetime` datetime DEFAULT NULL COMMENT '修改时间',
                             `create_user_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '创建人名称',
                             `update_user_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '修改人名称',
                             `update_user_id` bigint DEFAULT NULL COMMENT '修改人id',
                             `create_user_id` bigint DEFAULT NULL COMMENT '创建人id',
                             `del_flag` int DEFAULT '0' COMMENT '是否删除 0 未删除 1 已删除',
                             `emby_apikey` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT 'emby秘钥',
                             `enabled` int DEFAULT NULL COMMENT '启用中 展示emby数据（0 不启用 1 启用）',
                             PRIMARY KEY (`id`) USING BTREE,
                             KEY `idx_status` (`status`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='emby服务器信息';

-- ----------------------------
-- Records of emby_info
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for emby_ip_locations
-- ----------------------------
DROP TABLE IF EXISTS `emby_ip_locations`;
CREATE TABLE `emby_ip_locations` (
                                     `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键id',
                                     `ip_address` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT '' COMMENT 'IP地址（支持IPv4和IPv6）',
                                     `country` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT '' COMMENT '国家',
                                     `region` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT '' COMMENT '地区/省份',
                                     `city` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT '' COMMENT '城市',
                                     `isp` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT '' COMMENT '互联网服务提供商',
                                     `emby_user_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT '' COMMENT 'emby用户名',
                                     `client` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT '' COMMENT '客户端',
                                     `create_datetime` datetime DEFAULT NULL COMMENT '创建时间',
                                     `update_datetime` datetime DEFAULT NULL COMMENT '修改时间',
                                     `create_user_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '创建人名称',
                                     `update_user_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '修改人名称',
                                     `update_user_id` bigint DEFAULT NULL COMMENT '修改人id',
                                     `create_user_id` bigint DEFAULT NULL COMMENT '创建人id',
                                     `del_flag` int DEFAULT '0' COMMENT '是否删除 0 未删除 1 已删除',
                                     PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=378 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='用户播放记录ip对应表';

-- ----------------------------
-- Records of emby_ip_locations
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for emby_notify_data
-- ----------------------------
DROP TABLE IF EXISTS `emby_notify_data`;
CREATE TABLE `emby_notify_data` (
                                    `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键id',
                                    `name` varchar(300) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '电影名称',
                                    `overview` varchar(4000) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '剧情简介',
                                    `production_year` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '年份',
                                    `type` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT 'Episode 剧集 Movie 电影',
                                    `status` int DEFAULT NULL COMMENT '状态 0 发送消息失败 1 发送成功 2 待入库',
                                    `create_datetime` datetime DEFAULT NULL COMMENT '创建时间',
                                    `update_datetime` datetime DEFAULT NULL COMMENT '修改时间',
                                    `create_user_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '创建人名称',
                                    `update_user_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '修改人名称',
                                    `update_user_id` bigint DEFAULT NULL COMMENT '修改人id',
                                    `create_user_id` bigint DEFAULT NULL COMMENT '创建人id',
                                    `del_flag` int DEFAULT '0' COMMENT '是否删除 0 未删除 1 已删除',
                                    `img_url` varchar(300) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '海报链接',
                                    `tmdb_url` varchar(300) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT 'tmdb链接',
                                    `display_title` varchar(300) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '分辨率',
                                    `genres` varchar(300) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '电影类型',
                                    `size` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '影片文件大小',
                                    `backdrop_path` varchar(300) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '横幅图片路径',
                                    PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='入库通知详情数据';

-- ----------------------------
-- Records of emby_notify_data
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for emby_notify_data_details
-- ----------------------------
DROP TABLE IF EXISTS `emby_notify_data_details`;
CREATE TABLE `emby_notify_data_details` (
                                            `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键id',
                                            `emby_notify_data_id` bigint DEFAULT NULL COMMENT '入库通知详情数据表id',
                                            `episode_details` varchar(3000) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '集数详情',
                                            `episode_info` varchar(3000) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '集数介绍',
                                            `create_datetime` datetime DEFAULT NULL COMMENT '创建时间',
                                            `update_datetime` datetime DEFAULT NULL COMMENT '修改时间',
                                            `size` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '影片文件大小',
                                            `create_user_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '创建人名称',
                                            `update_user_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '修改人名称',
                                            `update_user_id` bigint DEFAULT NULL COMMENT '修改人id',
                                            `create_user_id` bigint DEFAULT NULL COMMENT '创建人id',
                                            `del_flag` int DEFAULT '0' COMMENT '是否删除 0 未删除 1 已删除',
                                            `status` int DEFAULT NULL COMMENT '状态 0 发送消息失败 1 发送成功 2 待入库',
                                            PRIMARY KEY (`id`) USING BTREE,
                                            KEY `idx_emby_notify_data_details_filter_group` (`del_flag`,`status`,`emby_notify_data_id`,`episode_details`(100)),
                                            KEY `idx_emby_notify_data_details_covering` (`del_flag`,`status`,`emby_notify_data_id`,`id`,`episode_details`(100),`size`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='入库通知详情数据明细表';

-- ----------------------------
-- Records of emby_notify_data_details
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for emby_user
-- ----------------------------
DROP TABLE IF EXISTS `emby_user`;
CREATE TABLE `emby_user` (
                             `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键id',
                             `emby_user_id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT 'emby用户id',
                             `emby_user_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT 'emby用户名',
                             `emby_user_password` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT 'emby用户密码',
                             `is_admin` int DEFAULT '0' COMMENT '是否管理员 0 否 1 是',
                             `user_status` int DEFAULT '0' COMMENT '用户状态 0 启用 1 禁用',
                             `expiration_date` datetime DEFAULT NULL COMMENT '到期时间',
                             `expire_date_count` bigint DEFAULT NULL COMMENT '还有多少天到期',
                             `create_datetime` datetime DEFAULT NULL COMMENT '创建时间',
                             `update_datetime` datetime DEFAULT NULL COMMENT '修改时间',
                             `create_user_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '创建人名称',
                             `update_user_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '修改人名称',
                             `update_user_id` bigint DEFAULT NULL COMMENT '修改人id',
                             `create_user_id` bigint DEFAULT NULL COMMENT '创建人id',
                             `del_flag` int DEFAULT '0' COMMENT '是否删除 0 未删除 1 已删除',
                             `remarks` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '备注',
                             `request_packages_count` int DEFAULT '0' COMMENT '求片次数',
                             `email` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '电子邮箱',
                             `mobile` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '手机号码',
                             `gender` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '性别',
                             `birthday` datetime DEFAULT NULL COMMENT '出生日期',
                             `interests` varchar(300) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '影片爱好',
                             PRIMARY KEY (`id`) USING BTREE,
                             KEY `idx_emby_user_name_user_status` (`emby_user_name`,`user_status`)
) ENGINE=InnoDB AUTO_INCREMENT=214 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='emby用户表';

-- ----------------------------
-- Records of emby_user
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for flyway_schema_history
-- ----------------------------
DROP TABLE IF EXISTS `flyway_schema_history`;
CREATE TABLE `flyway_schema_history` (
                                         `installed_rank` int NOT NULL,
                                         `version` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
                                         `description` varchar(200) COLLATE utf8mb4_unicode_ci NOT NULL,
                                         `type` varchar(20) COLLATE utf8mb4_unicode_ci NOT NULL,
                                         `script` varchar(1000) COLLATE utf8mb4_unicode_ci NOT NULL,
                                         `checksum` int DEFAULT NULL,
                                         `installed_by` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL,
                                         `installed_on` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
                                         `execution_time` int NOT NULL,
                                         `success` tinyint(1) NOT NULL,
                                         PRIMARY KEY (`installed_rank`),
                                         KEY `flyway_schema_history_s_idx` (`success`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ----------------------------
-- Records of flyway_schema_history
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for media_main
-- ----------------------------
DROP TABLE IF EXISTS `media_main`;
CREATE TABLE `media_main` (
                              `id` bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '影视ID（主键）',
                              `title` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '影视标题',
                              `type` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '影视类型 movie ,tv',
                              `poster_path` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT '' COMMENT '海报路径（URL或本地路径）',
                              `release_date` date DEFAULT NULL COMMENT '上映/首播日期（电影：上映日；电视剧：首播日）',
                              `play_count` int unsigned DEFAULT '0' COMMENT '总播放次数',
                              `rating` decimal(3,1) DEFAULT NULL COMMENT '评分（如8.4、9.3）',
                              `duration` int unsigned DEFAULT NULL COMMENT '时长（电影：总分钟数；电视剧：单集分钟数）',
                              `create_datetime` datetime DEFAULT NULL COMMENT '创建时间',
                              `update_datetime` datetime DEFAULT NULL COMMENT '修改时间',
                              `create_user_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '创建人名称',
                              `update_user_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '修改人名称',
                              `update_user_id` bigint DEFAULT NULL COMMENT '修改人id',
                              `create_user_id` bigint DEFAULT NULL COMMENT '创建人id',
                              `del_flag` int DEFAULT '0' COMMENT '是否删除 0 未删除 1 已删除',
                              PRIMARY KEY (`id`) USING BTREE,
                              KEY `idx_type` (`type`) USING BTREE,
                              KEY `idx_rating` (`rating`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=835 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='影视基础信息主表';

-- ----------------------------
-- Records of media_main
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for media_view_detail
-- ----------------------------
DROP TABLE IF EXISTS `media_view_detail`;
CREATE TABLE `media_view_detail` (
                                     `id` bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '观看记录ID（主键）',
                                     `media_id` bigint DEFAULT NULL COMMENT '关联的影视ID（外键，关联media_main.media_id）',
                                     `user_id` bigint DEFAULT NULL COMMENT '用户ID',
                                     `emby_user_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '用户名',
                                     `view_time` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '观看开始时间',
                                     `device` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '观看设备（如：手机端、电脑端、电视端、平板端）',
                                     `create_datetime` datetime DEFAULT NULL COMMENT '创建时间',
                                     `update_datetime` datetime DEFAULT NULL COMMENT '修改时间',
                                     `create_user_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '创建人名称',
                                     `update_user_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '修改人名称',
                                     `update_user_id` bigint DEFAULT NULL COMMENT '修改人id',
                                     `create_user_id` bigint DEFAULT NULL COMMENT '创建人id',
                                     `del_flag` int DEFAULT '0' COMMENT '是否删除 0 未删除 1 已删除',
                                     PRIMARY KEY (`id`) USING BTREE,
                                     KEY `idx_media_id` (`media_id`) USING BTREE,
                                     KEY `idx_user_id` (`user_id`) USING BTREE,
                                     KEY `idx_view_time` (`view_time`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1661 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='用户观看记录明细表';

-- ----------------------------
-- Records of media_view_detail
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for notify_channel
-- ----------------------------
DROP TABLE IF EXISTS `notify_channel`;
CREATE TABLE `notify_channel` (
                                  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键id',
                                  `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '渠道名称',
                                  `desc` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '渠道描述',
                                  `icon_type` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT 'icon类型 telegram wechat email sms custom dingding',
                                  `enabled` int DEFAULT '1' COMMENT '是否启用 0 未启用 1 启用',
                                  `custom_icon` varchar(600) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '自定义icon',
                                  `params` varchar(4000) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '配置参数',
                                  `create_datetime` datetime DEFAULT NULL COMMENT '创建时间',
                                  `update_datetime` datetime DEFAULT NULL COMMENT '修改时间',
                                  `create_user_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '创建人名称',
                                  `update_user_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '修改人名称',
                                  `update_user_id` bigint DEFAULT NULL COMMENT '修改人id',
                                  `create_user_id` bigint DEFAULT NULL COMMENT '创建人id',
                                  `del_flag` int DEFAULT '0' COMMENT '是否删除 0 未删除 1 已删除',
                                  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='通知渠道表';

-- ----------------------------
-- Records of notify_channel
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for notify_template
-- ----------------------------
DROP TABLE IF EXISTS `notify_template`;
CREATE TABLE `notify_template` (
                                   `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键id',
                                   `template_code` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '模板编码',
                                   `template_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '模板名称',
                                   `channel_type` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT 'common' COMMENT '渠道类型',
                                   `template_content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci COMMENT '模板内容',
                                   `variable_comment` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '变量说明',
                                   `enabled` int DEFAULT '1' COMMENT '是否启用 0未启用 1启用',
                                   `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '备注',
                                   `create_datetime` datetime DEFAULT NULL COMMENT '创建时间',
                                   `update_datetime` datetime DEFAULT NULL COMMENT '修改时间',
                                   `create_user_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '创建人名称',
                                   `update_user_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '修改人名称',
                                   `update_user_id` bigint DEFAULT NULL COMMENT '修改人id',
                                   `create_user_id` bigint DEFAULT NULL COMMENT '创建人id',
                                   `del_flag` int DEFAULT '0' COMMENT '是否删除 0未删除 1已删除',
                                   PRIMARY KEY (`id`),
                                   UNIQUE KEY `uniq_template_channel` (`template_code`,`channel_type`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='通知模板表';

-- ----------------------------
-- Records of notify_template
-- ----------------------------
BEGIN;
INSERT INTO `notify_template` (`id`, `template_code`, `template_name`, `channel_type`, `template_content`, `variable_comment`, `enabled`, `remark`, `create_datetime`, `update_datetime`, `create_user_name`, `update_user_name`, `update_user_id`, `create_user_id`, `del_flag`) VALUES (1, 'media_photo_detail', '资源详情-telegram', 'telegram', '名称：${name}\n\n${tvInfoBlock}${displayTitleBlock}${genresBlock}🗂 类型：${typeTag}\n${sizeBlock}简介：${overview}', 'name、overview、tvInfoBlock、displayTitleBlock、genresBlock、typeTag、sizeBlock', 1, '', NULL, '2025-11-15 14:49:18', NULL, '2', 133, NULL, 0);
INSERT INTO `notify_template` (`id`, `template_code`, `template_name`, `channel_type`, `template_content`, `variable_comment`, `enabled`, `remark`, `create_datetime`, `update_datetime`, `create_user_name`, `update_user_name`, `update_user_id`, `create_user_id`, `del_flag`) VALUES (2, 'media_photo_detail', '资源详情-钉钉', 'dingding', '${backdropImageBlock}名称：${name}\n\n${tvInfoBlock}${displayTitleBlock}${genresBlock}🗂 类型：${typeTag}\n${sizeBlock}简介：${overview}', 'backdropImageBlock、name、overview 等变量', 1, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0);
INSERT INTO `notify_template` (`id`, `template_code`, `template_name`, `channel_type`, `template_content`, `variable_comment`, `enabled`, `remark`, `create_datetime`, `update_datetime`, `create_user_name`, `update_user_name`, `update_user_id`, `create_user_id`, `del_flag`) VALUES (3, 'media_photo_message', '图文消息-telegram', 'telegram', '${overview}', 'overview', 1, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0);
INSERT INTO `notify_template` (`id`, `template_code`, `template_name`, `channel_type`, `template_content`, `variable_comment`, `enabled`, `remark`, `create_datetime`, `update_datetime`, `create_user_name`, `update_user_name`, `update_user_id`, `create_user_id`, `del_flag`) VALUES (4, 'media_photo_message', '图文消息-钉钉', 'dingding', '${backdropImageBlock}${overview}', 'backdropImageBlock、overview', 1, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0);
INSERT INTO `notify_template` (`id`, `template_code`, `template_name`, `channel_type`, `template_content`, `variable_comment`, `enabled`, `remark`, `create_datetime`, `update_datetime`, `create_user_name`, `update_user_name`, `update_user_id`, `create_user_id`, `del_flag`) VALUES (5, 'media_text_message', '文本消息-telegram', 'telegram', '${overview}', 'overview', 1, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0);
INSERT INTO `notify_template` (`id`, `template_code`, `template_name`, `channel_type`, `template_content`, `variable_comment`, `enabled`, `remark`, `create_datetime`, `update_datetime`, `create_user_name`, `update_user_name`, `update_user_id`, `create_user_id`, `del_flag`) VALUES (6, 'media_text_message', '文本消息-钉钉', 'dingding', '${overview}', 'overview', 1, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0);
COMMIT;

-- ----------------------------
-- Table structure for play_records
-- ----------------------------
DROP TABLE IF EXISTS `play_records`;
CREATE TABLE `play_records` (
                                `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键id',
                                `emby_user_id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT 'emby用户id',
                                `play_date` datetime DEFAULT NULL COMMENT '播放时间',
                                `record_type` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '播放类型 播放 VideoPlayback 暂停 VideoPlaybackStopped',
                                `device` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '设备',
                                `content` varchar(600) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '内容',
                                `emby_user_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT 'emby用户名',
                                `create_datetime` datetime DEFAULT NULL COMMENT '创建时间',
                                `update_datetime` datetime DEFAULT NULL COMMENT '修改时间',
                                `create_user_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '创建人名称',
                                `update_user_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '修改人名称',
                                `update_user_id` bigint DEFAULT NULL COMMENT '修改人id',
                                `create_user_id` bigint DEFAULT NULL COMMENT '创建人id',
                                `del_flag` int DEFAULT '0' COMMENT '是否删除 0 未删除 1 已删除',
                                PRIMARY KEY (`id`) USING BTREE,
                                KEY `idx_play_date_emby_user_name` (`play_date`,`emby_user_name`) USING BTREE,
                                KEY `idx_user_content_device` (`emby_user_id`,`content`,`device`) USING BTREE,
                                KEY `idx_play_date` (`play_date`) USING BTREE,
                                KEY `idx_record_type` (`record_type`) USING BTREE,
                                KEY `idx_username` (`emby_user_name`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=5923 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='播放记录表';

-- ----------------------------
-- Records of play_records
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for request_list
-- ----------------------------
DROP TABLE IF EXISTS `request_list`;
CREATE TABLE `request_list` (
                                `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键id',
                                `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '片名',
                                `type` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '类型',
                                `image_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '图片地址',
                                `score` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '评分',
                                `tmdb_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT 'tmdb地址',
                                `release_date` datetime DEFAULT NULL COMMENT '上映日期',
                                `tmdb_id` int DEFAULT NULL COMMENT 'tmdb id',
                                `overview` varchar(4000) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '简介',
                                `user_id` bigint DEFAULT NULL COMMENT '用户id',
                                `status` int DEFAULT '0' COMMENT '求片状态 0 已提交 1 已入库',
                                `emby_user_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT 'emby用户名',
                                `create_datetime` datetime DEFAULT NULL COMMENT '创建时间',
                                `update_datetime` datetime DEFAULT NULL COMMENT '修改时间',
                                `create_user_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '创建人名称',
                                `update_user_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '修改人名称',
                                `update_user_id` bigint DEFAULT NULL COMMENT '修改人id',
                                `create_user_id` bigint DEFAULT NULL COMMENT '创建人id',
                                `del_flag` int DEFAULT '0' COMMENT '是否删除 0 未删除 1 已删除',
                                `backdrop_path` varchar(300) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '横幅图片路径',
                                `season` int DEFAULT NULL COMMENT '季',
                                `episode` int DEFAULT NULL COMMENT '剧集',
                                `parent_tmdb_id` int DEFAULT NULL COMMENT '剧集顶级tmdb id',
                                PRIMARY KEY (`id`) USING BTREE,
                                KEY `idx_name_type_tmdb_id` (`name`,`type`,`tmdb_id`)
) ENGINE=InnoDB AUTO_INCREMENT=110 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='求片列表';

-- ----------------------------
-- Records of request_list
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for request_packages
-- ----------------------------
DROP TABLE IF EXISTS `request_packages`;
CREATE TABLE `request_packages` (
                                    `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键id',
                                    `title` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '套餐名称',
                                    `description` varchar(200) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '套餐描述',
                                    `count` int DEFAULT NULL COMMENT '套餐包含数量',
                                    `icon` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '套餐图标标识',
                                    `amount` decimal(18,2) DEFAULT NULL COMMENT '金额',
                                    `create_datetime` datetime DEFAULT NULL COMMENT '创建时间',
                                    `update_datetime` datetime DEFAULT NULL COMMENT '修改时间',
                                    `create_user_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '创建人名称',
                                    `update_user_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '修改人名称',
                                    `update_user_id` bigint DEFAULT NULL COMMENT '修改人id',
                                    `create_user_id` bigint DEFAULT NULL COMMENT '创建人id',
                                    `del_flag` int DEFAULT '0' COMMENT '是否删除 0 未删除 1 已删除',
                                    PRIMARY KEY (`id`),
                                    KEY `idx_request_packages_del_flag` (`del_flag`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='求片套餐信息表';

-- ----------------------------
-- Records of request_packages
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for request_packages_card_security_management
-- ----------------------------
DROP TABLE IF EXISTS `request_packages_card_security_management`;
CREATE TABLE `request_packages_card_security_management` (
                                                             `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键id',
                                                             `card_password` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '卡密',
                                                             `card_count` int DEFAULT NULL COMMENT '求片次数',
                                                             `card_status` int DEFAULT '0' COMMENT '卡密状态 0 未使用 1 已使用',
                                                             `user_id` bigint DEFAULT NULL COMMENT '用户id',
                                                             `emby_user_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT 'emby用户名',
                                                             `create_datetime` datetime DEFAULT NULL COMMENT '创建时间',
                                                             `update_datetime` datetime DEFAULT NULL COMMENT '修改时间',
                                                             `create_user_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '创建人名称',
                                                             `update_user_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '修改人名称',
                                                             `update_user_id` bigint DEFAULT NULL COMMENT '修改人id',
                                                             `create_user_id` bigint DEFAULT NULL COMMENT '创建人id',
                                                             `del_flag` int DEFAULT '0' COMMENT '是否删除 0 未删除 1 已删除',
                                                             PRIMARY KEY (`id`) USING BTREE,
                                                             KEY `idx_del_card_status` (`del_flag`,`card_status`),
                                                             KEY `idx_del_flag_create_datetime` (`del_flag`,`create_datetime`),
                                                             KEY `idx_security_management_main` (`del_flag`,`card_status`,`card_count`),
                                                             KEY `idx_security_management_password` (`card_password`),
                                                             KEY `idx_security_management_emby` (`emby_user_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='求片套餐信息卡密管理';

-- ----------------------------
-- Records of request_packages_card_security_management
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for sys_notice
-- ----------------------------
DROP TABLE IF EXISTS `sys_notice`;
CREATE TABLE `sys_notice` (
                              `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键ID',
                              `notice_title` varchar(128) NOT NULL COMMENT '公告标题',
                              `notice_content` text NOT NULL COMMENT '公告内容',
                              `notice_status` int NOT NULL DEFAULT '1' COMMENT '状态 1=启用 0=停用',
                              `top_flag` int NOT NULL DEFAULT '0' COMMENT '是否置顶 1=是 0=否',
                              `sort` int NOT NULL DEFAULT '0' COMMENT '排序 越大越前',
                              `create_datetime` datetime DEFAULT NULL COMMENT '创建时间(即发布时间)',
                              `update_datetime` datetime DEFAULT NULL COMMENT '修改时间',
                              `create_user_name` varchar(50) DEFAULT NULL COMMENT '创建人名称',
                              `update_user_name` varchar(50) DEFAULT NULL COMMENT '修改人名称',
                              `update_user_id` bigint DEFAULT NULL COMMENT '修改人id',
                              `create_user_id` bigint DEFAULT NULL COMMENT '创建人id',
                              `del_flag` int NOT NULL DEFAULT '0' COMMENT '是否删除 0 未删除 1 已删除',
                              PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='系统公告';

-- ----------------------------
-- Records of sys_notice
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for sys_operation_log
-- ----------------------------
DROP TABLE IF EXISTS `sys_operation_log`;
CREATE TABLE `sys_operation_log` (
                                     `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键id',
                                     `operation_content` varchar(255) DEFAULT NULL COMMENT '操作内容',
                                     `operation_type` varchar(50) DEFAULT NULL COMMENT '操作类型',
                                     `operation_time` datetime DEFAULT NULL COMMENT '操作时间',
                                     `operator` varchar(50) DEFAULT NULL COMMENT '操作人',
                                     `request_url` varchar(255) DEFAULT NULL COMMENT '请求URL',
                                     `request_method` varchar(20) DEFAULT NULL COMMENT '请求方法',
                                     `request_params` varchar(4000) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '请求参数',
                                     `response_result` varchar(4000) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '响应结果',
                                     `ip_address` varchar(50) DEFAULT NULL COMMENT 'IP地址',
                                     `create_datetime` datetime DEFAULT NULL COMMENT '创建时间',
                                     `update_datetime` datetime DEFAULT NULL COMMENT '修改时间',
                                     `create_user_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '创建人名称',
                                     `update_user_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '修改人名称',
                                     `update_user_id` bigint DEFAULT NULL COMMENT '修改人id',
                                     `create_user_id` bigint DEFAULT NULL COMMENT '创建人id',
                                     `del_flag` int DEFAULT '0' COMMENT '是否删除 0 未删除 1 已删除',
                                     PRIMARY KEY (`id`),
                                     KEY `idx_operation` (`del_flag`,`operation_type`,`operator`,`operation_time`)
) ENGINE=InnoDB AUTO_INCREMENT=1735 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='系统操作日志表';

-- ----------------------------
-- Records of sys_operation_log
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for system_config
-- ----------------------------
DROP TABLE IF EXISTS `system_config`;
CREATE TABLE `system_config` (
                                 `id` bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '主键id',
                                 `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '配置项名称',
                                 `config_key` varchar(100) NOT NULL COMMENT '配置项键名',
                                 `config_value` varchar(600) DEFAULT NULL COMMENT '配置项值',
                                 `is_enabled` int NOT NULL DEFAULT '1' COMMENT '是否启用（1=启用，0=禁用）',
                                 `description` varchar(255) DEFAULT NULL COMMENT '配置项描述',
                                 `is_update` int DEFAULT NULL COMMENT '是否修改 0 不能修改 1 能修改',
                                 `create_datetime` datetime DEFAULT NULL COMMENT '创建时间',
                                 `update_datetime` datetime DEFAULT NULL COMMENT '修改时间',
                                 `create_user_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '创建人名称',
                                 `update_user_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '修改人名称',
                                 `update_user_id` bigint DEFAULT NULL COMMENT '修改人id',
                                 `create_user_id` bigint DEFAULT NULL COMMENT '创建人id',
                                 `del_flag` int DEFAULT '0' COMMENT '是否删除 0 未删除 1 已删除',
                                 PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='系统配置管理表';

-- ----------------------------
-- Records of system_config
-- ----------------------------
BEGIN;
INSERT INTO `system_config` (`id`, `name`, `config_key`, `config_value`, `is_enabled`, `description`, `is_update`, `create_datetime`, `update_datetime`, `create_user_name`, `update_user_name`, `update_user_id`, `create_user_id`, `del_flag`) VALUES (8, 'emby入库直达合并通知', 'emby_merge', 'true', 0, '开启此配置 🛠️\n需要开启 emby 通知的合并 📥 和神医助手的合并 🧑⚕️\n否则通知会很混乱哦 😵\n此配置能够合并剧集直接发送 📤\n不然就不会合并啦 ❌', 0, '2025-07-17 16:18:10', '2025-11-08 17:13:20', 'admin', 'admin', 133, 1, 0);
INSERT INTO `system_config` (`id`, `name`, `config_key`, `config_value`, `is_enabled`, `description`, `is_update`, `create_datetime`, `update_datetime`, `create_user_name`, `update_user_name`, `update_user_id`, `create_user_id`, `del_flag`) VALUES (9, '开启用户注册', 'registered_user', 'true', 0, '是否开启用户注册', 0, '2025-08-10 23:04:54', '2025-11-08 16:58:35', 'admin', 'admin', 133, 1, 0);
INSERT INTO `system_config` (`id`, `name`, `config_key`, `config_value`, `is_enabled`, `description`, `is_update`, `create_datetime`, `update_datetime`, `create_user_name`, `update_user_name`, `update_user_id`, `create_user_id`, `del_flag`) VALUES (10, 'emby登录通知', 'emby_user_authentication_notify', 'true', 0, '是否开启emby用户验证通知 需要勾选emby用户 已验证用户身份 无法验证用户身份', 0, '2025-08-10 23:04:54', '2025-08-11 22:28:16', 'admin', 'admin', 133, 1, 0);
INSERT INTO `system_config` (`id`, `name`, `config_key`, `config_value`, `is_enabled`, `description`, `is_update`, `create_datetime`, `update_datetime`, `create_user_name`, `update_user_name`, `update_user_id`, `create_user_id`, `del_flag`) VALUES (11, 'emby播放通知', 'emby_playback_notify', 'true', 0, '是否开启emby用户播放通知 需要勾选emby播放 开始 暂停 取消暂停 停止 不支持钉钉', 0, '2025-08-10 23:04:54', '2025-11-08 22:41:00', 'admin', 'admin', 133, 1, 0);
INSERT INTO `system_config` (`id`, `name`, `config_key`, `config_value`, `is_enabled`, `description`, `is_update`, `create_datetime`, `update_datetime`, `create_user_name`, `update_user_name`, `update_user_id`, `create_user_id`, `del_flag`) VALUES (12, '求片次数', 'request_count', '0', 0, '是否开始求片次数默认值', 0, '2025-08-10 23:04:54', '2025-11-09 23:05:35', 'admin', 'admin', 133, 1, 0);
INSERT INTO `system_config` (`id`, `name`, `config_key`, `config_value`, `is_enabled`, `description`, `is_update`, `create_datetime`, `update_datetime`, `create_user_name`, `update_user_name`, `update_user_id`, `create_user_id`, `del_flag`) VALUES (13, '用户保留天数', 'days_expired', '0', 0, '过期多久删除用户（天数）', 0, '2025-08-10 23:04:54', '2025-09-14 21:20:33', 'admin', 'admin', 133, 1, 0);
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
