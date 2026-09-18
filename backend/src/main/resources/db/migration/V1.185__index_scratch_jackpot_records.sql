ALTER TABLE `points_bot_scratch_entry`
    ADD KEY `idx_scratch_chat_jackpot_settled`
        (`chat_id`, `is_jackpot`, `settled_at`, `id`);
