-- Emby 入库 Telegram 默认使用歌曲模板；旧模板内容在页面作为参考/替换文案保留。

INSERT INTO `notify_template` (
    `template_code`,
    `template_name`,
    `channel_type`,
    `template_content`,
    `variable_comment`,
    `enabled`,
    `remark`,
    `create_datetime`,
    `update_datetime`,
    `create_user_name`,
    `update_user_name`,
    `update_user_id`,
    `create_user_id`,
    `del_flag`
)
SELECT
    'media_photo_detail_song',
    'Emby入库歌曲-telegram',
    'telegram',
    '🎼 <b>新歌 · 已完成创作</b>\n\n♪━━━━━━━━━━━━━━♪\n《${songTitleHtml}》\n\n【主歌】\n发行年份：${songYearHtml}\n曲风标签：${songGenreHtml}\n画面版本：${songQualityHtml}\n文件大小：${songSizeHtml}\n播放时长：${songRuntimeHtml}\n\n【副歌】\n专辑/剧集：${songAlbumHtml}\n曲序/集数：${songTrackHtml}\n媒体类型：${songMediaTypeHtml}\n发布站点：${songServerHtml}\n\n<blockquote expandable>【创作手记】\n${songNoteHtml}</blockquote>\n♪━━━━━━━━━━━━━━♪\n\n✅ 已入库，开播这首新歌。',
    'songTitleHtml, songAlbumHtml, songTrackHtml, songMediaTypeHtml, songYearHtml, songGenreHtml, songQualityHtml, songSizeHtml, songRuntimeHtml, songServerHtml, songNoteHtml',
    1,
    'Emby入库 Telegram 歌曲模板，旧模板内容可在页面弹窗中查看并手动替换',
    NOW(),
    NOW(),
    'system',
    'system',
    NULL,
    NULL,
    0
WHERE NOT EXISTS (
    SELECT 1
    FROM `notify_template`
    WHERE `template_code` = 'media_photo_detail_song'
      AND `channel_type` = 'telegram'
);
