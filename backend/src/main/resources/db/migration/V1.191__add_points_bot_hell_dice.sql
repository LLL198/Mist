CREATE TABLE `points_bot_hell_vault` (
    `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键id',
    `chat_id` bigint NOT NULL COMMENT '积分群聊ID',
    `vault_points` int NOT NULL DEFAULT 0 COMMENT '当前地狱金库积分',
    `total_subsidy_points` int NOT NULL DEFAULT 0 COMMENT '累计系统初始化及管理员补充积分',
    `total_inflow_points` bigint NOT NULL DEFAULT 0 COMMENT '累计玩家损失与观众手续费流入',
    `total_outflow_points` bigint NOT NULL DEFAULT 0 COMMENT '累计玩家盈利支出',
    `daily_outflow_points` int NOT NULL DEFAULT 0 COMMENT '当日玩家盈利支出',
    `daily_outflow_date` date DEFAULT NULL COMMENT '每日支出统计日期',
    `initialized` tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否已应用一次性初始金库',
    `create_datetime` datetime DEFAULT NULL COMMENT '创建时间',
    `update_datetime` datetime DEFAULT NULL COMMENT '修改时间',
    `create_user_name` varchar(50) DEFAULT NULL COMMENT '创建人名称',
    `update_user_name` varchar(50) DEFAULT NULL COMMENT '修改人名称',
    `update_user_id` bigint DEFAULT NULL COMMENT '修改人id',
    `create_user_id` bigint DEFAULT NULL COMMENT '创建人id',
    `del_flag` int DEFAULT 0 COMMENT '是否删除 0 未删除 1 已删除',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uniq_hell_vault_chat` (`chat_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='积分机器人地狱骰群金库';

CREATE TABLE `points_bot_hell_round` (
    `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键id',
    `chat_id` bigint NOT NULL COMMENT '积分群聊ID',
    `message_id` bigint DEFAULT NULL COMMENT '游戏面板消息ID',
    `player_user_id` bigint NOT NULL COMMENT '发起玩家Telegram ID',
    `player_username` varchar(100) DEFAULT NULL COMMENT 'Telegram用户名',
    `player_display_name` varchar(200) DEFAULT NULL COMMENT '展示名称',
    `status` varchar(24) NOT NULL COMMENT 'BETTING/ROLLING/WAITING_DECISION/SETTLED/LOST/REFUNDED',
    `active_guard` tinyint DEFAULT NULL COMMENT '活动局唯一占位，结束时必须置NULL',
    `config_json` text NOT NULL COMMENT '本轮地狱骰配置快照',
    `bet_points` int NOT NULL COMMENT '玩家下注积分',
    `max_profit_liability` int NOT NULL COMMENT '本局已锁定的最大盈利责任',
    `current_depth` int NOT NULL DEFAULT 0 COMMENT '已通过的最深层数',
    `target_depth` int NOT NULL DEFAULT 1 COMMENT '当前准备掷骰的目标层数',
    `current_payout` int NOT NULL DEFAULT 0 COMMENT '当前收手返还积分',
    `last_dice_value` int DEFAULT NULL COMMENT '最近一次Telegram原生骰值',
    `betting_ends_at` datetime DEFAULT NULL COMMENT '当前层观众下注截止时间',
    `decision_ends_at` datetime DEFAULT NULL COMMENT '玩家收手或继续选择截止时间',
    `payout_points` int NOT NULL DEFAULT 0 COMMENT '最终返还玩家积分',
    `vault_contribution_points` int NOT NULL DEFAULT 0 COMMENT '失败后实际进入金库积分',
    `settled_at` datetime DEFAULT NULL COMMENT '结算时间',
    `create_datetime` datetime DEFAULT NULL COMMENT '创建时间',
    `update_datetime` datetime DEFAULT NULL COMMENT '修改时间',
    `create_user_name` varchar(50) DEFAULT NULL COMMENT '创建人名称',
    `update_user_name` varchar(50) DEFAULT NULL COMMENT '修改人名称',
    `update_user_id` bigint DEFAULT NULL COMMENT '修改人id',
    `create_user_id` bigint DEFAULT NULL COMMENT '创建人id',
    `del_flag` int DEFAULT 0 COMMENT '是否删除 0 未删除 1 已删除',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uniq_hell_round_chat_active` (`chat_id`, `active_guard`),
    KEY `idx_hell_round_status_betting` (`status`, `betting_ends_at`),
    KEY `idx_hell_round_status_decision` (`status`, `decision_ends_at`),
    KEY `idx_hell_round_chat_user_time` (`chat_id`, `player_user_id`, `create_datetime`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='积分机器人地狱骰轮次';

CREATE TABLE `points_bot_hell_bet` (
    `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键id',
    `round_id` bigint NOT NULL COMMENT '地狱骰轮次ID',
    `chat_id` bigint NOT NULL COMMENT '积分群聊ID',
    `depth` int NOT NULL COMMENT '下注层数',
    `user_id` bigint NOT NULL COMMENT '观众Telegram ID',
    `side` varchar(12) NOT NULL COMMENT 'SURVIVE/DIE',
    `bet_points` int NOT NULL COMMENT '下注积分',
    `status` varchar(16) NOT NULL COMMENT 'PENDING/WON/LOST/REFUNDED',
    `payout_points` int NOT NULL DEFAULT 0 COMMENT '结算返还积分',
    `settled_at` datetime DEFAULT NULL COMMENT '结算时间',
    `create_datetime` datetime DEFAULT NULL COMMENT '创建时间',
    `update_datetime` datetime DEFAULT NULL COMMENT '修改时间',
    `create_user_name` varchar(50) DEFAULT NULL COMMENT '创建人名称',
    `update_user_name` varchar(50) DEFAULT NULL COMMENT '修改人名称',
    `update_user_id` bigint DEFAULT NULL COMMENT '修改人id',
    `create_user_id` bigint DEFAULT NULL COMMENT '创建人id',
    `del_flag` int DEFAULT 0 COMMENT '是否删除 0 未删除 1 已删除',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uniq_hell_bet_round_depth_user` (`round_id`, `depth`, `user_id`),
    KEY `idx_hell_bet_chat_user_time` (`chat_id`, `user_id`, `create_datetime`),
    KEY `idx_hell_bet_round_depth_status` (`round_id`, `depth`, `status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='积分机器人地狱骰观众下注';

CREATE TABLE `points_bot_hell_vault_ledger` (
    `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键id',
    `chat_id` bigint NOT NULL COMMENT '积分群聊ID',
    `change_type` varchar(32) NOT NULL COMMENT 'INITIAL/TOP_UP/PLAYER_LOSS/SPECTATOR_FEE/PLAYER_PROFIT',
    `delta_points` int NOT NULL COMMENT '金库变化积分',
    `balance_after` int NOT NULL COMMENT '变化后金库余额',
    `ref_type` varchar(24) DEFAULT NULL COMMENT '关联业务类型',
    `ref_id` varchar(64) DEFAULT NULL COMMENT '关联业务ID',
    `create_datetime` datetime DEFAULT NULL COMMENT '创建时间',
    `update_datetime` datetime DEFAULT NULL COMMENT '修改时间',
    `create_user_name` varchar(50) DEFAULT NULL COMMENT '创建人名称',
    `update_user_name` varchar(50) DEFAULT NULL COMMENT '修改人名称',
    `update_user_id` bigint DEFAULT NULL COMMENT '修改人id',
    `create_user_id` bigint DEFAULT NULL COMMENT '创建人id',
    `del_flag` int DEFAULT 0 COMMENT '是否删除 0 未删除 1 已删除',
    PRIMARY KEY (`id`),
    KEY `idx_hell_vault_ledger_chat_time` (`chat_id`, `create_datetime`),
    KEY `idx_hell_vault_ledger_ref` (`ref_type`, `ref_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='积分机器人地狱骰金库流水';

INSERT INTO `points_bot_game_config` (`game_code`, `enabled`, `config_json`, `sort_order`)
VALUES ('hell_dice', 1,
        '{"minBet":5,"maxBet":30,"dailyPlayLimit":3,"decisionSeconds":15,"bettingSeconds":8,"singlePayoutCap":300,"dailyPlayerProfitCap":300,"dailyGroupProfitCap":1000,"initialVaultPoints":300,"vaultCapacity":3000,"adminTopUpLifetimeCap":3000,"lossVaultPercent":90,"spectatorBetEnabled":true,"spectatorMinBet":1,"spectatorMaxBetPerLayer":5,"spectatorMaxBetPerRound":20,"spectatorMaxBetPerDay":50,"spectatorPoolCapPerLayer":100,"spectatorFeePercent":5,"leaderboardLimit":10,"layerPayoutPercents":[110,125,175,245,465,880]}',
        45)
ON DUPLICATE KEY UPDATE `sort_order` = VALUES(`sort_order`);

UPDATE `notify_channel`
SET `params` = JSON_SET(
        COALESCE(NULLIF(`params`, ''), '{}'),
        '$.enabledGameCommands',
        CASE
            WHEN JSON_TYPE(JSON_EXTRACT(COALESCE(NULLIF(`params`, ''), '{}'), '$.enabledGameCommands')) = 'ARRAY'
                THEN CASE
                    WHEN JSON_CONTAINS(
                            JSON_EXTRACT(COALESCE(NULLIF(`params`, ''), '{}'), '$.enabledGameCommands'),
                            JSON_QUOTE('hell_dice'))
                        THEN JSON_EXTRACT(COALESCE(NULLIF(`params`, ''), '{}'), '$.enabledGameCommands')
                    ELSE JSON_ARRAY_APPEND(
                            JSON_EXTRACT(COALESCE(NULLIF(`params`, ''), '{}'), '$.enabledGameCommands'),
                            '$', 'hell_dice')
                END
            ELSE JSON_ARRAY('sgs', 'blackjack', 'dice', 'hell_dice', 'slots', 'scratch', 'brain')
        END,
        '$.gameCommandsVersion', 4
    )
WHERE `icon_type` IN ('telegram', 'pointsBot')
  AND `del_flag` = 0
  AND JSON_VALID(COALESCE(NULLIF(`params`, ''), '{}'))
  AND JSON_TYPE(COALESCE(NULLIF(`params`, ''), '{}')) = 'OBJECT';
