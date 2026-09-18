CREATE TABLE `points_bot_game_config` (
    `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键id',
    `game_code` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '游戏编码',
    `enabled` tinyint(1) NOT NULL DEFAULT 1 COMMENT '全局是否启用',
    `config_json` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci COMMENT '玩法配置JSON',
    `sort_order` int NOT NULL DEFAULT 0 COMMENT '排序',
    `create_datetime` datetime DEFAULT NULL COMMENT '创建时间',
    `update_datetime` datetime DEFAULT NULL COMMENT '修改时间',
    `create_user_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '创建人名称',
    `update_user_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '修改人名称',
    `update_user_id` bigint DEFAULT NULL COMMENT '修改人id',
    `create_user_id` bigint DEFAULT NULL COMMENT '创建人id',
    `del_flag` int DEFAULT '0' COMMENT '是否删除 0 未删除 1 已删除',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uniq_points_bot_game_code` (`game_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='积分机器人游戏中心配置';

INSERT INTO `points_bot_game_config` (`game_code`, `enabled`, `config_json`, `sort_order`)
VALUES
    ('brain', 0, '{"entryCost":10,"dailyPlayLimit":3,"dailyChampionLimit":2,"registrationSeconds":15,"answerSeconds":60,"minPlayers":5,"maxPlayers":30,"peakMinPlayers":10,"peakEveryRounds":10,"jackpotCap":1000,"championPercent":70,"followerPercent":15,"jackpotPercent":10,"sinkPercent":5,"peakChampionPercent":70,"peakFollowerPercent":20,"targetArithmeticWeight":30,"codeLockWeight":25,"bullsAndCowsWeight":20,"lightsOutWeight":15,"flashMemoryWeight":10}', 10),
    ('sgs', 1, '{}', 20),
    ('blackjack', 1, '{}', 30),
    ('dice', 1, '{}', 40),
    ('slots', 1, '{}', 50),
    ('scratch', 1, '{}', 60);
