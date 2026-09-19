package com.una.embyhub.util;

import com.una.embyhub.model.entity.TmdbDailyRelease;
import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.LinearGradientPaint;
import java.awt.RadialGradientPaint;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.MultipleGradientPaint.CycleMethod;
import java.awt.geom.RoundRectangle2D;
import java.awt.geom.Ellipse2D.Double;
import java.awt.geom.Point2D.Float;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import javax.imageio.ImageIO;
import org.springframework.util.StringUtils;

public final class TmdbDailyReleasePosterRenderer {
   private static final int W = 1200;
   private static final int H = 1600;
   private static final int P = 48;
   private static final int POSTER_ITEM_LIMIT = 10;
   private static final Color GOLD = new Color(255, 213, 117);
   private static final Color SILVER = new Color(220, 230, 255);
   private static final Color BRONZE = new Color(230, 157, 96);
   private static final Color BLUE = new Color(142, 176, 255);

   private TmdbDailyReleasePosterRenderer() {
   }

   public static byte[] render(LocalDate date, List<TmdbDailyRelease> rows) throws IOException {
      List<TmdbDailyRelease> safeRows = rows == null
         ? List.of()
         : rows.stream().sorted(Comparator.comparing(TmdbDailyRelease::getRankNo, Comparator.nullsLast(Integer::compareTo))).toList();
      List<TmdbDailyRelease> posterRows = posterRows(safeRows);
      BufferedImage img = new BufferedImage(1200, 1600, 2);
      Graphics2D g = img.createGraphics();
      enableAA(g);
      drawBackground(g, safeRows);
      drawDreamLight(g);
      drawHeader(g, date, posterRows);
      drawFeatured(g, safeRows);
      drawColumns(g, safeRows);
      g.dispose();
      ByteArrayOutputStream out = new ByteArrayOutputStream();
      ImageIO.write(img, "png", out);
      return out.toByteArray();
   }

   private static List<TmdbDailyRelease> posterRows(List<TmdbDailyRelease> rows) {
      List<TmdbDailyRelease> movies = rows.stream().filter(row -> "movie".equals(row.getMediaType())).limit(10L).toList();
      List<TmdbDailyRelease> tvs = rows.stream().filter(row -> "tv".equals(row.getMediaType())).limit(10L).toList();
      ArrayList<TmdbDailyRelease> result = new ArrayList<>(movies.size() + tvs.size());
      result.addAll(movies);
      result.addAll(tvs);
      return result;
   }

   private static void drawBackground(Graphics2D g, List<TmdbDailyRelease> rows) {
      BufferedImage bg = loadBackground(rows);
      if (bg != null) {
         drawCover(g, bg, 1200, 1600);
      } else {
         GradientPaint gp = new GradientPaint(0.0F, 0.0F, new Color(22, 20, 45), 1200.0F, 1600.0F, new Color(34, 58, 91));
         g.setPaint(gp);
         g.fillRect(0, 0, 1200, 1600);
      }

      g.setColor(new Color(4, 6, 16, 165));
      g.fillRect(0, 0, 1200, 1600);
      g.setPaint(
         new LinearGradientPaint(
            0.0F,
            0.0F,
            0.0F,
            1600.0F,
            new float[]{0.0F, 0.35F, 1.0F},
            new Color[]{new Color(255, 255, 255, 18), new Color(30, 40, 80, 90), new Color(2, 4, 12, 220)},
            CycleMethod.NO_CYCLE
         )
      );
      g.fillRect(0, 0, 1200, 1600);
   }

   private static BufferedImage loadBackground(List<TmdbDailyRelease> rows) {
      for (TmdbDailyRelease row : rows) {
         String url = StringUtils.hasText(row.getBackdropPath()) ? row.getBackdropPath() : row.getPosterPath();
         if (StringUtils.hasText(url)) {
            try {
               return MovieCardRenderer.downloadPosterFromUrl(url);
            } catch (Exception var5) {
            }
         }
      }

      return null;
   }

   private static void drawDreamLight(Graphics2D g) {
      drawGlow(g, 180, 170, 360, new Color(138, 198, 255, 70));
      drawGlow(g, 1040, 220, 460, new Color(255, 179, 221, 58));
      drawGlow(g, 760, 1180, 520, new Color(160, 127, 255, 48));
      g.setStroke(new BasicStroke(2.0F));
      g.setColor(new Color(255, 255, 255, 38));

      for (int i = 0; i < 26; i++) {
         int x = (i * 181 + 73) % 1200;
         int y = 120 + i * 277 % 1260;
         g.drawLine(x, y, x + 34, y - 18);
      }
   }

   private static void drawGlow(Graphics2D g, int cx, int cy, int radius, Color color) {
      RadialGradientPaint paint = new RadialGradientPaint(
         new Float((float)cx, (float)cy),
         (float)radius,
         new float[]{0.0F, 1.0F},
         new Color[]{color, new Color(color.getRed(), color.getGreen(), color.getBlue(), 0)}
      );
      g.setPaint(paint);
      g.fill(new Double((double)(cx - radius), (double)(cy - radius), (double)radius * 2.0, (double)radius * 2.0));
   }

   private static void drawHeader(Graphics2D g, LocalDate date, List<TmdbDailyRelease> rows) {
      Font base = font(22.0F, 0);
      Font title = font(54.0F, 1);
      Font sub = font(24.0F, 1);
      int movieCount = (int)rows.stream().filter(row -> "movie".equals(row.getMediaType())).count();
      int tvCount = (int)rows.stream().filter(row -> "tv".equals(row.getMediaType())).count();
      g.setFont(title);
      g.setColor(Color.WHITE);
      g.drawString("Mist · 每日上映 / 播出榜", 72, 96);
      String dateText = date.format(DateTimeFormatter.ofPattern("yyyy年MM月dd日", Locale.CHINA));
      g.setFont(sub);
      g.setColor(new Color(232, 240, 255));
      g.drawString(dateText + "  Asia/Shanghai", 76, 144);
      int x = 76;
      int y = 176;
      x = drawPill(g, "TOP " + Math.min(10, rows.size()), x, y, base, new Color(255, 255, 255, 32));
      x = drawPill(g, "电影 " + movieCount, x + 14, y, base, new Color(255, 207, 112, 38));
      drawPill(g, "剧集 " + tvCount, x + 14, y, base, new Color(120, 178, 255, 40));
   }

   private static void drawFeatured(Graphics2D g, List<TmdbDailyRelease> rows) {
      if (rows.isEmpty()) {
         drawGlass(g, 48, 250, 1104, 240, 30, new Color(255, 255, 255, 24));
         g.setFont(font(36.0F, 1));
         g.setColor(Color.WHITE);
         g.drawString("今天暂时没有抓取到上映/播出数据", 100, 355);
         g.setFont(font(24.0F, 0));
         g.setColor(new Color(220, 230, 255, 200));
         g.drawString("可以稍后手动刷新，或检查地区、语种和渠道配置。", 100, 402);
      } else {
         TmdbDailyRelease top = rows.get(0);
         int x = 48;
         int y = 246;
         int w = 1104;
         int h = 250;
         drawGlass(g, x, y, w, h, 34, new Color(255, 255, 255, 28));
         BufferedImage poster = loadImage(top.getPosterPath());
         if (poster != null) {
            Shape oldClip = g.getClip();
            RoundRectangle2D clip = new java.awt.geom.RoundRectangle2D.Double((double)(x + 28), (double)(y + 28), 140.0, 194.0, 22.0, 22.0);
            g.setClip(clip);
            drawCover(g, poster, x + 28, y + 28, 140, 194);
            g.setClip(oldClip);
         }

         int tx = x + 198;
         g.setFont(font(26.0F, 1));
         g.setColor(rankColor(0));
         g.drawString("#1 今日焦点", tx, y + 64);
         g.setFont(font(42.0F, 1));
         g.setColor(Color.WHITE);
         String title = titleWithYear(top);
         g.drawString(ellipsis(title, g.getFontMetrics(), w - 250), tx, y + 116);
         g.setFont(font(25.0F, 0));
         g.setColor(new Color(225, 236, 255, 220));
         g.drawString(metaLine(top), tx, y + 158);
         g.setFont(font(22.0F, 0));
         g.setColor(new Color(230, 236, 255, 190));
         List<String> lines = wrapText(g, defaultText(top.getOverview(), "暂无简介"), w - 250, 2);
         int yy = y + 198;

         for (String line : lines) {
            g.drawString(line, tx, yy);
            yy += 30;
         }
      }
   }

   private static void drawColumns(Graphics2D g, List<TmdbDailyRelease> rows) {
      List<TmdbDailyRelease> movies = rows.stream().filter(row -> "movie".equals(row.getMediaType())).limit(10L).toList();
      List<TmdbDailyRelease> tvs = rows.stream().filter(row -> "tv".equals(row.getMediaType())).limit(10L).toList();
      int top = 540;
      int panelW = 537;
      drawListPanel(g, "电影上映", movies, 48, top, panelW, 1600 - top - 48);
      drawListPanel(g, "剧集更新", tvs, 48 + panelW + 30, top, panelW, 1600 - top - 48);
   }

   private static void drawListPanel(Graphics2D g, String title, List<TmdbDailyRelease> rows, int x, int y, int w, int h) {
      drawGlass(g, x, y, w, h, 28, new Color(255, 255, 255, 22));
      g.setFont(font(32.0F, 1));
      g.setColor(Color.WHITE);
      g.drawString(title + "  " + rows.size(), x + 30, y + 54);
      if (rows.isEmpty()) {
         drawEmptyListState(g, title, x, y, w, h);
      } else {
         int rowY = y + 96;
         int rowH = Math.min(88, (h - 120) / Math.max(1, Math.min(rows.size(), 8)));
         Font rankFont = font(25.0F, 1);
         Font itemFont = font(24.0F, 1);
         Font metaFont = font(19.0F, 0);

         for (int i = 0; i < rows.size() && rowY + rowH <= y + h - 24; i++) {
            TmdbDailyRelease row = rows.get(i);
            int rank = i + 1;
            g.setComposite(AlphaComposite.SrcOver);
            g.setColor(new Color(255, 255, 255, i % 2 == 0 ? 22 : 12));
            g.fillRoundRect(x + 22, rowY - 8, w - 44, rowH - 8, 20, 20);
            g.setFont(rankFont);
            g.setColor(rankColor(i));
            g.drawString(String.format(Locale.ROOT, "%02d", rank), x + 42, rowY + 28);
            g.setFont(itemFont);
            g.setColor(Color.WHITE);
            g.drawString(ellipsis(row.getTitle(), g.getFontMetrics(), w - 150), x + 94, rowY + 20);
            g.setFont(metaFont);
            g.setColor(new Color(222, 232, 255, 190));
            g.drawString(ellipsis(metaLine(row), g.getFontMetrics(), w - 150), x + 94, rowY + 52);
            rowY += rowH;
         }
      }
   }

   private static void drawEmptyListState(Graphics2D g, String title, int x, int y, int w, int h) {
      boolean movie = title.contains("电影");
      String main = movie ? "今日暂无电影" : "今日暂无剧集";
      int centerX = x + w / 2;
      int centerY = y + Math.max(220, h / 2);
      g.setColor(new Color(255, 255, 255, 18));
      g.fillRoundRect(x + 44, centerY - 58, w - 88, 116, 26, 26);
      g.setFont(font(30.0F, 1));
      FontMetrics mainMetrics = g.getFontMetrics();
      g.setColor(new Color(255, 255, 255, 220));
      g.drawString(main, centerX - mainMetrics.stringWidth(main) / 2, centerY + 10);
   }

   private static int drawPill(Graphics2D g, String text, int x, int y, Font font, Color bg) {
      g.setFont(font);
      FontMetrics fm = g.getFontMetrics();
      int w = fm.stringWidth(text) + 34;
      int h = 44;
      g.setColor(bg);
      g.fillRoundRect(x, y, w, h, 22, 22);
      g.setColor(new Color(255, 255, 255, 210));
      g.drawString(text, x + 17, y + 29);
      return x + w;
   }

   private static void drawGlass(Graphics2D g, int x, int y, int w, int h, int radius, Color fill) {
      g.setColor(new Color(0, 0, 0, 66));
      g.fillRoundRect(x + 8, y + 10, w, h, radius, radius);
      g.setColor(fill);
      g.fillRoundRect(x, y, w, h, radius, radius);
      g.setStroke(new BasicStroke(1.4F));
      g.setColor(new Color(255, 255, 255, 58));
      g.drawRoundRect(x, y, w, h, radius, radius);
   }

   private static BufferedImage loadImage(String url) {
      if (!StringUtils.hasText(url)) {
         return null;
      } else {
         try {
            return MovieCardRenderer.downloadPosterFromUrl(url);
         } catch (Exception var2) {
            return null;
         }
      }
   }

   private static String titleWithYear(TmdbDailyRelease row) {
      String title = defaultText(row.getTitle(), "未命名");
      return row.getYear() != null && !title.contains(String.valueOf(row.getYear())) ? title + " (" + row.getYear() + ")" : title;
   }

   private static String metaLine(TmdbDailyRelease row) {
      if ("movie".equals(row.getMediaType())) {
         return "电影 / 评分 " + score(row);
      } else {
         String episode = StringUtils.hasText(row.getEpisodeDisplay()) ? row.getEpisodeDisplay() : "今日更新";
         return "剧集 / " + episode + " / 评分 " + score(row);
      }
   }

   private static String score(TmdbDailyRelease row) {
      return row.getVoteAverage() == null ? "--" : row.getVoteAverage().setScale(1, RoundingMode.HALF_UP).toPlainString();
   }

   private static String dateText(Date date) {
      return date == null ? "待定" : new SimpleDateFormat("yyyy-MM-dd").format(date);
   }

   private static Color rankColor(int index) {
      if (index == 0) {
         return GOLD;
      } else if (index == 1) {
         return SILVER;
      } else {
         return index == 2 ? BRONZE : BLUE;
      }
   }

   private static void drawCover(Graphics2D g, BufferedImage src, int w, int h) {
      drawCover(g, src, 0, 0, w, h);
   }

   private static void drawCover(Graphics2D g, BufferedImage src, int x, int y, int w, int h) {
      double scale = Math.max((double)w / (double)src.getWidth(), (double)h / (double)src.getHeight());
      int nw = (int)Math.round((double)src.getWidth() * scale);
      int nh = (int)Math.round((double)src.getHeight() * scale);
      int dx = x + (w - nw) / 2;
      int dy = y + (h - nh) / 2;
      g.drawImage(src, dx, dy, nw, nh, null);
   }

   private static List<String> wrapText(Graphics2D g, String text, int maxWidth, int maxLines) {
      FontMetrics fm = g.getFontMetrics();
      String source = defaultText(text, "");
      ArrayList<String> lines = new ArrayList<>();
      StringBuilder line = new StringBuilder();

      for (int i = 0; i < source.length(); i++) {
         char c = source.charAt(i);
         if (fm.stringWidth(line.toString() + c) > maxWidth && !line.isEmpty()) {
            lines.add(line.toString());
            line.setLength(0);
            if (lines.size() >= maxLines) {
               break;
            }
         }

         line.append(c);
      }

      if (!line.isEmpty() && lines.size() < maxLines) {
         lines.add(line.toString());
      }

      if (lines.size() == maxLines && fm.stringWidth(lines.get(maxLines - 1)) > maxWidth - fm.stringWidth("...")) {
         lines.set(maxLines - 1, ellipsis(lines.get(maxLines - 1), fm, maxWidth));
      }

      return lines;
   }

   private static String ellipsis(String text, FontMetrics fm, int maxWidth) {
      String safe = defaultText(text, "");
      if (fm.stringWidth(safe) <= maxWidth) {
         return safe;
      } else {
         String ell = "...";
         StringBuilder sb = new StringBuilder();

         for (int i = 0; i < safe.length(); i++) {
            char c = safe.charAt(i);
            if (fm.stringWidth(sb.toString() + c + ell) > maxWidth) {
               break;
            }

            sb.append(c);
         }

         return sb + ell;
      }
   }

   private static String defaultText(String value, String fallback) {
      return StringUtils.hasText(value) ? value.trim() : fallback;
   }

   private static Font font(float size, int style) {
      return new Font("SansSerif", style, Math.round(size));
   }

   private static void enableAA(Graphics2D g) {
      g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
      g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
      g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
   }
}
