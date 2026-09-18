-- V1.158 已执行后不再修改；这里单独补充歌曲模板每行的音乐元素。

UPDATE `notify_template`
SET
    `template_content` = '🎼 <b>新歌 · 已完成创作</b>\n\n♪━━━━━━━━━━━━━━♪\n《${songTitleHtml}》\n\n【主歌】\n🎹 发行年份：${songYearHtml}\n🎸 曲风标签：${songGenreHtml}\n🎧 画面版本：${songQualityHtml}\n💿 文件大小：${songSizeHtml}\n🥁 播放时长：${songRuntimeHtml}\n\n【副歌】\n🎙️ 专辑/剧集：${songAlbumHtml}\n🎚️ 曲序/集数：${songTrackHtml}\n🎛️ 媒体类型：${songMediaTypeHtml}\n📻 发布站点：${songServerHtml}\n\n<blockquote expandable>【创作手记】\n${songNoteHtml}</blockquote>\n♪━━━━━━━━━━━━━━♪\n\n✅ 已入库，开播这首新歌。',
    `update_datetime` = NOW(),
    `update_user_name` = 'system'
WHERE `template_code` = 'media_photo_detail_song'
  AND `channel_type` = 'telegram'
  AND (`del_flag` = 0 OR `del_flag` IS NULL)
  AND `template_content` IN (
      '🎼 <b>新歌 · 已完成创作</b>\n\n♪━━━━━━━━━━━━━━♪\n《${songTitleHtml}》\n\n【主歌】\n发行年份：${songYearHtml}\n曲风标签：${songGenreHtml}\n画面版本：${songQualityHtml}\n文件大小：${songSizeHtml}\n播放时长：${songRuntimeHtml}\n\n【副歌】\n专辑/剧集：${songAlbumHtml}\n曲序/集数：${songTrackHtml}\n媒体类型：${songMediaTypeHtml}\n发布站点：${songServerHtml}\n\n<blockquote expandable>【创作手记】\n${songNoteHtml}</blockquote>\n♪━━━━━━━━━━━━━━♪\n\n✅ 已入库，开播这首新歌。',
      '🎼 <b>新歌 · 已完成创作</b>\n\n《${songTitleHtml}》\n\n【主歌】\n发行年份：${songYearHtml}\n曲风标签：${songGenreHtml}\n画面版本：${songQualityHtml}\n文件大小：${songSizeHtml}\n播放时长：${songRuntimeHtml}\n\n【副歌】\n专辑/剧集：${songAlbumHtml}\n曲序/集数：${songTrackHtml}\n媒体类型：${songMediaTypeHtml}\n发布站点：${songServerHtml}\n\n<blockquote expandable>【创作手记】\n${songNoteHtml}</blockquote>\n\n✅ 已入库，开播这首新歌。'
  );
