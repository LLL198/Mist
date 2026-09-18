-- 添加抽奖公告消息ID字段
ALTER TABLE points_bot_lottery ADD COLUMN announcement_message_id BIGINT COMMENT '抽奖公告消息ID（用于置顶和取消置顶）';
