package com.una.embyhub.job;

import cn.hutool.core.io.resource.ResourceUtil;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.font.GlyphVector;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.geom.Path2D;
import java.awt.geom.RoundRectangle2D;
import java.awt.geom.Path2D.Float;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;
import javax.imageio.ImageIO;

public final class PlaybackRankingPosterRenderer {
   private static final int LAYOUT_WIDTH = 1600;
   private static final int LAYOUT_HEIGHT = 900;
   public static final int OUTPUT_SCALE = 2;
   public static final int WIDTH = 3200;
   public static final int HEIGHT = 1800;
   private static final TimeZone ZONE = TimeZone.getTimeZone("Asia/Shanghai");
   private static final Color WHITE = new Color(245, 247, 252);
   private static final Color MUTED = new Color(196, 205, 220);
   private static final Color MINT = new Color(86, 225, 169);
   private static final Color LINE = new Color(151, 166, 190, 88);
   private static final Color GLASS = new Color(12, 25, 35, 116);
   private static final Color GLASS_STRONG = new Color(10, 21, 30, 150);
   private static final Color GLASS_BORDER = new Color(236, 244, 255, 82);
   private static final Font BASE_FONT = loadBaseFont();
   private static final Font ICON_FONT = loadIconFont();
   private static final Font SERIF_FONT = new Font("Serif", 0, 20);

   public byte[] render(
      PlaybackRankingPosterStyle style,
      List<PlaybackRankingPosterRenderer.MediaRank> movies,
      List<PlaybackRankingPosterRenderer.MediaRank> series,
      List<PlaybackRankingPosterRenderer.ViewerRank> viewers,
      Date targetDate,
      BufferedImage backdrop,
      String serverLabel
   ) throws Exception {
      PlaybackRankingPosterStyle resolvedStyle = style == null ? PlaybackRankingPosterStyle.DEFAULT : style;
      BufferedImage image = new BufferedImage(3200, 1800, 1);
      Graphics2D g = image.createGraphics();
      this.enableAA(g);
      g.scale(2.0, 2.0);
      this.drawBase(g, backdrop, resolvedStyle);
      switch (resolvedStyle) {
         case POSTER_RAIL:
            this.drawPosterRail(g, movies, series, viewers, targetDate, serverLabel);
            break;
         case DUAL_SPOTLIGHT:
            this.drawDualSpotlight(g, movies, series, viewers, targetDate, serverLabel);
            break;
         case HERO_SPOTLIGHT:
            this.drawHeroSpotlight(g, movies, series, viewers, targetDate, serverLabel);
            break;
         case MIDNIGHT_TICKET:
            this.drawMidnightTicket(g, movies, series, viewers, targetDate, serverLabel);
            break;
         case STREAMING_MAGAZINE:
            this.drawStreamingMagazine(g, movies, series, viewers, targetDate, serverLabel);
            break;
         case CLASSIC_PORTRAIT:
            throw new IllegalArgumentException("经典长图由原排行榜渲染器处理");
      }

      g.dispose();
      ByteArrayOutputStream output = new ByteArrayOutputStream();
      ImageIO.write(image, "png", output);
      return output.toByteArray();
   }

   private void drawBase(Graphics2D g, BufferedImage backdrop, PlaybackRankingPosterStyle style) {
      if (backdrop != null) {
         this.drawCover(g, backdrop, 0, 0, 1600, 900);
      } else {
         GradientPaint background = new GradientPaint(
            0.0F,
            0.0F,
            style == PlaybackRankingPosterStyle.MIDNIGHT_TICKET ? new Color(139, 121, 96) : new Color(91, 119, 143),
            1600.0F,
            900.0F,
            style == PlaybackRankingPosterStyle.MIDNIGHT_TICKET ? new Color(92, 78, 62) : new Color(57, 83, 108)
         );
         g.setPaint(background);
         g.fillRect(0, 0, 1600, 900);
      }
   }

   private void drawPosterRail(
      Graphics2D g,
      List<PlaybackRankingPosterRenderer.MediaRank> movies,
      List<PlaybackRankingPosterRenderer.MediaRank> series,
      List<PlaybackRankingPosterRenderer.ViewerRank> viewers,
      Date targetDate,
      String serverLabel
   ) {
      int railW = 299;
      int footerTop = 594;
      int posterW = (1600 - railW) / 4;

      for (int index = 0; index < 4; index++) {
         int x = railW + index * posterW;
         int width = index == 3 ? 1600 - x : posterW;
         PlaybackRankingPosterRenderer.MediaRank item = this.itemAt(series, index);
         this.drawPosterShaded(g, item, x, 0, width, footerTop, 0);
         this.drawSolidDisplayRank(g, index, x + 8, 512, 130.0F);
         this.drawMediaCaption(g, item, x + 28, 550, width - 56, 24.0F, 18.0F);
      }

      this.fillGlassPanel(g, 0, 0, railW, footerTop, 0, true);
      this.drawRailWatermark(g, 46, 430, 88);
      g.setColor(new Color(235, 242, 251, 92));
      g.fillRect(railW, 0, 1, footerTop);
      g.setFont(this.font(1, 55.0F));
      g.setColor(WHITE);
      g.drawString("Emby", 34, 124);
      g.setFont(this.font(1, 48.0F));
      g.drawString("今日排行榜", 34, 190);
      g.setColor(MINT);
      g.fillRoundRect(34, 224, 48, 4, 4, 4);
      this.drawDate(g, targetDate, 34, 280, 23.0F);
      this.drawSectionHeading(g, PlaybackRankingPosterRenderer.IconKind.TV, "剧集 Top 4", 34, 376, 30.0F, WHITE, 235);
      this.drawIcon(g, PlaybackRankingPosterRenderer.IconKind.TV, 34, 349, 30, MINT);
      this.drawServerBadge(g, serverLabel, 1278, 24, 298);
      this.fillGlassPanel(g, 0, footerTop, 1600, 900 - footerTop, 0, true);
      g.setColor(GLASS_BORDER);
      g.fillRect(0, footerTop, 1600, 1);
      g.fillRect(506, footerTop + 18, 1, 272);
      g.fillRect(990, footerTop + 18, 1, 272);
      this.drawMovieList(g, movies, 46, 640, 420, 5, 40);
      this.drawActivityList(g, viewers, 546, 640, 414, 6, 1);
      this.drawViewerBars(g, viewers, 1032, 640, 520, 4, 1);
   }

   private void drawDualSpotlight(
      Graphics2D g,
      List<PlaybackRankingPosterRenderer.MediaRank> movies,
      List<PlaybackRankingPosterRenderer.MediaRank> series,
      List<PlaybackRankingPosterRenderer.ViewerRank> viewers,
      Date targetDate,
      String serverLabel
   ) {
      this.drawHeader(g, targetDate, serverLabel, true);
      int splitX = 829;
      this.fillGlassPanel(g, splitX, 72, 1600 - splitX, 828, 0, true);
      this.drawCenteredSectionHeading(g, PlaybackRankingPosterRenderer.IconKind.TV, "剧集 Top 4", 198, 121, 380, 31.0F, WHITE);
      PlaybackRankingPosterRenderer.MediaRank champion = this.itemAt(series, 0);
      this.drawPosterShaded(g, champion, 20, 132, 362, 724, 10);
      this.drawDisplayOutlineRank(g, 0, 43, 339, 230.0F, 18, true);
      this.drawCrown(g, 58, 144, 43, this.rankColor(0));
      this.drawDualChampionCaption(g, champion, 48, 716, 310);
      this.drawPosterShaded(g, this.itemAt(series, 1), 388, 132, 220, 390, 8);
      this.drawDisplayOutlineRank(g, 1, 390, 222, 95.0F, 24, false);
      this.drawMediaCaption(g, this.itemAt(series, 1), 410, 455, 182, 22.0F, 18.0F);
      this.drawPosterShaded(g, this.itemAt(series, 2), 614, 132, 188, 390, 8);
      this.drawDisplayOutlineRank(g, 2, 620, 223, 95.0F, 24, false);
      this.drawMediaCaption(g, this.itemAt(series, 2), 636, 455, 150, 22.0F, 18.0F);
      this.drawBackdropShaded(g, this.itemAt(series, 3), 388, 529, 414, 327, 8);
      this.drawDisplayOutlineRank(g, 3, 404, 603, 90.0F, 24, false);
      this.drawMediaCaption(g, this.itemAt(series, 3), 416, 773, 354, 25.0F, 18.0F);
      this.drawDualMovieCards(g, movies, 848, 121, 726, 5, 60);
      g.setColor(new Color(142, 160, 192, 85));
      g.fillRect(splitX, 441, 1600 - splitX, 1);
      this.drawDualActivityCards(g, viewers, 848, 485, 726, 6);
      g.fillRect(splitX, 676, 1600 - splitX, 1);
      this.drawDualViewerCards(g, viewers, 848, 719, 726, 4);
   }

   private void drawHeroSpotlight(
      Graphics2D g,
      List<PlaybackRankingPosterRenderer.MediaRank> movies,
      List<PlaybackRankingPosterRenderer.MediaRank> series,
      List<PlaybackRankingPosterRenderer.ViewerRank> viewers,
      Date targetDate,
      String serverLabel
   ) {
      this.drawHeader(g, targetDate, serverLabel, false);
      PlaybackRankingPosterRenderer.MediaRank champion = this.itemAt(series, 0);
      this.drawBackdrop(g, champion, 0, 76, 610, 522, 0);
      this.drawHeroImageShade(g, 0, 76, 610, 522);
      this.drawDisplayOutlineRank(g, 0, 31, 374, 292.0F, 0, true);
      this.drawHeroChampionCaption(g, champion, 60, 468, 500);
      this.fillGlassPanel(g, 610, 76, 448, 522, 0, true);
      this.drawSectionHeading(g, PlaybackRankingPosterRenderer.IconKind.TV, "剧集 Top 4", 636, 130, 30.0F, WHITE, 404);
      int[] rowTops = new int[]{153, 290, 426};
      int[] rankBaselines = new int[]{237, 375, 510};

      for (int index = 1; index < 4; index++) {
         int rowY = rowTops[index - 1];
         this.drawRankNumber(g, index, 648, rankBaselines[index - 1], 58.0F, false);
         this.drawPoster(g, this.itemAt(series, index), 707, rowY, 94, 132, 6);
         this.drawMediaCaption(g, this.itemAt(series, index), 820, rowY + 55, 214, 22.0F, 17.0F);
         g.setColor(LINE);
         g.fillRect(636, rowY + 137, 404, 1);
      }

      this.fillGlassPanel(g, 1058, 76, 542, 522, 0, true);
      this.drawMovieListWithBackdrops(g, movies, 1088, 130, 480, 5, 81);
      this.fillGlassPanel(g, 34, 598, 1532, 250, 20, true);
      g.setColor(new Color(142, 160, 192, 90));
      g.fillRect(727, 616, 1, 214);
      this.drawHeroActivityRows(g, viewers, 68, 648, 620, 6);
      this.drawHeroViewerRows(g, viewers, 770, 648, 752, 4);
   }

   private void drawMidnightTicket(
      Graphics2D g,
      List<PlaybackRankingPosterRenderer.MediaRank> movies,
      List<PlaybackRankingPosterRenderer.MediaRank> series,
      List<PlaybackRankingPosterRenderer.ViewerRank> viewers,
      Date targetDate,
      String serverLabel
   ) {
      Color ticketGold = new Color(199, 162, 108);
      this.drawTicketGlassStructure(g, ticketGold);
      this.drawTicketTexture(g, ticketGold);
      this.drawPerforation(g, 826, 148, 644, ticketGold);
      this.drawBarcode(g, 620, 186, 170, 22, ticketGold);
      this.drawBarcode(g, 38, 720, 18, 118, ticketGold);
      this.drawBarcode(g, 1544, 720, 18, 118, ticketGold);
      this.drawTicketGutters(g, ticketGold);
      this.drawTicketLogo(g, 72, 48, 48);
      g.setFont(this.serifFont(1, 49.0F));
      g.setColor(ticketGold);
      g.drawString("Emby 今日排行榜", 132, 96);
      this.drawStar(g, 678, 73, 9, ticketGold);
      this.drawStar(g, 709, 73, 9, ticketGold);
      this.drawStar(g, 1128, 73, 9, ticketGold);
      this.drawStar(g, 1159, 73, 9, ticketGold);
      Stroke previousStroke = g.getStroke();
      g.setStroke(new BasicStroke(1.2F, 0, 0, 1.0F, new float[]{5.0F, 5.0F}, 0.0F));
      g.setColor(new Color(ticketGold.getRed(), ticketGold.getGreen(), ticketGold.getBlue(), 115));
      g.drawRoundRect(742, 48, 414, 54, 3, 3);
      g.drawLine(642, 42, 642, 104);
      g.drawLine(1194, 42, 1194, 104);
      g.setStroke(previousStroke);
      this.drawDate(g, targetDate, 772, 86, 24.0F);
      this.drawPlainServerLabel(g, serverLabel, 1250, 84, 290, ticketGold);
      this.drawSectionHeading(g, PlaybackRankingPosterRenderer.IconKind.TV, "剧集 Top 4", 84, 204, 32.0F, ticketGold, 690);
      int[] widths = new int[]{144, 139, 122, 107};
      int[] heights = new int[]{316, 287, 264, 230};
      int[] tops = new int[]{235, 270, 297, 321};
      int[] xs = new int[]{100, 304, 498, 674};
      int[] rankXs = new int[]{64, 270, 463, 646};
      int[] rankBaselines = new int[]{358, 372, 406, 422};
      float[] rankSizes = new float[]{104.0F, 90.0F, 84.0F, 78.0F};

      for (int index = 0; index < 4; index++) {
         int x = xs[index];
         int posterW = widths[index];
         this.drawStampPoster(g, this.itemAt(series, index), x, tops[index], posterW, heights[index], ticketGold);
         this.drawTicketRank(g, index, rankXs[index], rankBaselines[index], rankSizes[index]);
         this.drawTicketCaption(g, this.itemAt(series, index), x - 8, 594, posterW + 16);
      }

      this.drawTicketMovieList(g, movies, 872, 204, 648, ticketGold);
      this.drawActivityList(g, viewers, 122, 708, 660, 6, 2, ticketGold);
      this.drawTicketViewerCards(g, viewers, 856, 708, 670, 4, ticketGold);
   }

   private void drawStreamingMagazine(
      Graphics2D g,
      List<PlaybackRankingPosterRenderer.MediaRank> movies,
      List<PlaybackRankingPosterRenderer.MediaRank> series,
      List<PlaybackRankingPosterRenderer.ViewerRank> viewers,
      Date targetDate,
      String serverLabel
   ) {
      int footerTop = 690;
      this.fillGlassPanel(g, 0, 0, 1020, footerTop, 0, false);
      this.drawOutlinedDisplayText(g, "DAILY " + this.formatMonthDayDash(targetDate), 52, 132, 160.0F, new Color(187, 133, 75, 54));
      this.drawScaledFilledText(g, "Emby 今日排行榜", 56, 157, 82.0F, 0.76, WHITE);
      this.drawDate(g, targetDate, 58, 190, 25.0F);
      this.drawMixedSectionHeading(g, PlaybackRankingPosterRenderer.IconKind.TV, "剧集", "Top 4", 58, 260, 30.0F, WHITE, MINT, 246);
      int[] xs = new int[]{84, 346, 588, 807};
      int[] ys = new int[]{291, 267, 227, 169};
      int[] widths = new int[]{212, 181, 168, 196};
      int[] heights = new int[]{332, 338, 359, 393};
      double[] angles = new double[]{-0.014, -0.009, 0.0075, 0.014};
      int[] rankXs = new int[]{48, 306, 536, 766};
      int[] rankBaselines = new int[]{453, 405, 359, 313};
      float[] rankSizes = new float[]{214.0F, 210.0F, 205.0F, 194.0F};

      for (int index = 0; index < 4; index++) {
         this.drawRotatedPoster(g, this.itemAt(series, index), xs[index], ys[index], widths[index], heights[index], 6, angles[index]);
         this.drawMagazineOutlineRank(g, index, rankXs[index], rankBaselines[index], rankSizes[index]);
         this.drawMagazineCaption(g, this.itemAt(series, index), index, xs[index] - 5, ys[index] + heights[index] + 27, widths[index] + 10);
      }

      this.fillGlassPanel(g, 1020, 0, 580, footerTop, 0, true);
      this.drawMagazineMovieList(g, movies, 1054, 124, 500, 5);
      this.drawPlainServerLabel(g, serverLabel, 1304, 50, 250, new Color(70, 205, 228));
      this.fillGlassPanel(g, 0, footerTop, 1600, 900 - footerTop, 0, true);
      g.setColor(LINE);
      g.fillRect(0, footerTop, 1600, 1);
      g.fillRect(894, footerTop + 16, 1, 182);
      this.drawStreamingActivityStrip(g, viewers, 44, 736, 810, 6);
      this.drawStreamingViewerStrip(g, viewers, 930, 736, 630, 4);
   }

   private void fillGlassPanel(Graphics2D g, int x, int y, int width, int height, int radius, boolean strong) {
      g.setColor(strong ? GLASS_STRONG : GLASS);
      if (radius > 0) {
         g.fillRoundRect(x, y, width, height, radius, radius);
      } else {
         g.fillRect(x, y, width, height);
      }

      g.setColor(GLASS_BORDER);
      if (radius > 0) {
         g.drawRoundRect(x, y, width, height, radius, radius);
      } else {
         g.fillRect(x, y, width, 1);
      }
   }

   private void fillWarmGlassPanel(Graphics2D g, int x, int y, int width, int height, int radius, boolean strong) {
      g.setColor(strong ? new Color(64, 49, 34, 148) : new Color(87, 67, 47, 116));
      if (radius > 0) {
         g.fillRoundRect(x, y, width, height, radius, radius);
      } else {
         g.fillRect(x, y, width, height);
      }

      g.setColor(new Color(255, 240, 216, 82));
      if (radius > 0) {
         g.drawRoundRect(x, y, width, height, radius, radius);
      } else {
         g.fillRect(x, y, width, 1);
      }
   }

   private void fillGlassCard(Graphics2D g, int x, int y, int width, int height, int radius) {
      g.setColor(new Color(8, 18, 27, 142));
      g.fillRoundRect(x, y, width, height, radius, radius);
      g.setColor(new Color(229, 238, 250, 92));
      g.setStroke(new BasicStroke(1.2F));
      g.drawRoundRect(x, y, width, height, radius, radius);
   }

   private void drawSolidDisplayRank(Graphics2D g, int index, int x, int baseline, float size) {
      String value = String.valueOf(index + 1);
      Font displayFont = this.font(1, size);
      GlyphVector glyph = displayFont.createGlyphVector(g.getFontRenderContext(), value);
      Shape shape = glyph.getOutline((float)x, (float)baseline);
      g.setColor(new Color(0, 0, 0, 150));
      g.translate(3, 4);
      g.fill(shape);
      g.translate(-3, -4);
      g.setColor(this.rankColor(index));
      g.fill(shape);
   }

   private void drawDisplayOutlineRank(Graphics2D g, int index, int x, int baseline, float size, int fillAlpha, boolean glow) {
      String value = String.valueOf(index + 1);
      Font displayFont = this.font(1, size);
      GlyphVector glyph = displayFont.createGlyphVector(g.getFontRenderContext(), value);
      Shape shape = glyph.getOutline((float)x, (float)baseline);
      Stroke previousStroke = g.getStroke();
      Color rank = this.rankColor(index);
      if (glow) {
         g.setStroke(new BasicStroke(Math.max(7.0F, size / 30.0F), 1, 1));
         g.setColor(new Color(rank.getRed(), rank.getGreen(), rank.getBlue(), 34));
         g.draw(shape);
      }

      if (fillAlpha > 0) {
         g.setColor(new Color(3, 8, 14, fillAlpha));
         g.fill(shape);
      }

      g.setStroke(new BasicStroke(Math.max(2.4F, size / 65.0F), 1, 1));
      g.setColor(rank);
      g.draw(shape);
      g.setStroke(previousStroke);
   }

   private void drawOutlinedDisplayText(Graphics2D g, String value, int x, int baseline, float size, Color color) {
      Font displayFont = this.font(1, size);
      GlyphVector glyph = displayFont.createGlyphVector(g.getFontRenderContext(), value);
      Shape shape = glyph.getOutline((float)x, (float)baseline);
      Stroke previousStroke = g.getStroke();
      g.setStroke(new BasicStroke(1.4F));
      g.setColor(color);
      g.draw(shape);
      g.setStroke(previousStroke);
   }

   private void drawScaledFilledText(Graphics2D g, String value, int x, int baseline, float size, double scaleX, Color color) {
      Font displayFont = this.font(1, size);
      GlyphVector glyph = displayFont.createGlyphVector(g.getFontRenderContext(), value);
      AffineTransform transform = new AffineTransform();
      transform.translate((double)x, (double)baseline);
      transform.scale(scaleX, 1.0);
      g.setColor(color);
      g.fill(transform.createTransformedShape(glyph.getOutline()));
   }

   private void drawCrown(Graphics2D g, int x, int y, int size, Color color) {
      Path2D crown = new Float();
      crown.moveTo((double)x, (double)y + (double)size * 0.72);
      crown.lineTo((double)x + (double)size * 0.12, (double)y + (double)size * 0.24);
      crown.lineTo((double)x + (double)size * 0.38, (double)y + (double)size * 0.52);
      crown.lineTo((double)x + (double)size * 0.52, (double)y + (double)size * 0.12);
      crown.lineTo((double)x + (double)size * 0.72, (double)y + (double)size * 0.5);
      crown.lineTo((double)(x + size), (double)y + (double)size * 0.22);
      crown.lineTo((double)x + (double)size * 0.88, (double)y + (double)size * 0.72);
      crown.closePath();
      Stroke previousStroke = g.getStroke();
      g.setStroke(new BasicStroke(2.4F, 1, 1));
      g.setColor(color);
      g.draw(crown);
      g.drawLine(x + 3, y + (int)((double)size * 0.82), x + size - 3, y + (int)((double)size * 0.82));
      g.setStroke(previousStroke);
   }

   private void drawRailWatermark(Graphics2D g, int x, int y, int size) {
      Polygon back = new Polygon(new int[]{x + size / 2, x + size, x + size / 2, x}, new int[]{y, y + size / 2, y + size, y + size / 2}, 4);
      Polygon front = new Polygon(new int[]{x + size, x + size * 3 / 2, x + size, x + size / 2}, new int[]{y, y + size / 2, y + size, y + size / 2}, 4);
      g.setColor(new Color(130, 152, 171, 24));
      g.fillPolygon(back);
      g.fillPolygon(front);
      Polygon play = new Polygon(new int[]{x + size * 3 / 4, x + size * 3 / 4, x + size * 11 / 10}, new int[]{y + size / 3, y + size * 2 / 3, y + size / 2}, 3);
      g.setColor(new Color(190, 210, 223, 28));
      g.fillPolygon(play);
   }

   private void drawSectionHeading(
      Graphics2D g, PlaybackRankingPosterRenderer.IconKind iconKind, String text, int x, int baseline, float size, Color color, int width
   ) {
      int iconSize = Math.max(20, Math.round(size));
      this.drawIcon(g, iconKind, x, baseline - iconSize + 3, iconSize, color);
      int textX = x + iconSize + 12;
      g.setFont(this.font(1, size));
      g.setColor(color);
      g.drawString(text, textX, baseline);
      g.setColor(new Color(color.getRed(), color.getGreen(), color.getBlue(), 105));
      int lineWidth = Math.max(92, width);
      g.fillRect(x, baseline + 10, lineWidth, 2);
   }

   private void drawCenteredSectionHeading(
      Graphics2D g, PlaybackRankingPosterRenderer.IconKind iconKind, String text, int x, int baseline, int width, float size, Color color
   ) {
      g.setFont(this.font(1, size));
      int iconSize = Math.max(20, Math.round(size));
      int contentWidth = iconSize + 12 + g.getFontMetrics().stringWidth(text);
      int contentX = x + Math.max(0, (width - contentWidth) / 2);
      this.drawSectionHeading(g, iconKind, text, contentX, baseline, size, color, contentWidth);
      int lineY = baseline - Math.round(size * 0.42F);
      g.setColor(new Color(80, 183, 224, 145));
      int side = Math.max(22, (width - contentWidth) / 2 - 24);
      g.fillRect(x, lineY, side, 2);
      g.fillRect(x + width - side, lineY, side, 2);
   }

   private void drawMixedSectionHeading(
      Graphics2D g,
      PlaybackRankingPosterRenderer.IconKind iconKind,
      String prefix,
      String suffix,
      int x,
      int baseline,
      float size,
      Color prefixColor,
      Color suffixColor,
      int width
   ) {
      int iconSize = Math.max(20, Math.round(size));
      this.drawIcon(g, iconKind, x, baseline - iconSize + 3, iconSize, prefixColor);
      int textX = x + iconSize + 12;
      g.setFont(this.font(1, size));
      g.setColor(prefixColor);
      g.drawString(prefix, textX, baseline);
      int suffixX = textX + g.getFontMetrics().stringWidth(prefix + " ");
      g.setColor(suffixColor);
      g.drawString(suffix, suffixX, baseline);
      g.setColor(new Color(prefixColor.getRed(), prefixColor.getGreen(), prefixColor.getBlue(), 105));
      g.fillRect(x, baseline + 10, width, 2);
   }

   private void drawIcon(Graphics2D g, PlaybackRankingPosterRenderer.IconKind kind, int x, int y, int size, Color color) {
      int codepoint = switch (kind) {
         case TV -> 985076;
         case MOVIE -> 987087;
         case CLOCK -> 984347;
         case USER -> 985941;
         case SERVER -> 984205;
         case ACTIVITY -> 984112;
      };
      if (ICON_FONT.canDisplay(codepoint)) {
         Graphics2D icon = (Graphics2D)g.create();
         String glyph = new String(Character.toChars(codepoint));
         icon.setFont(ICON_FONT.deriveFont((float)size));
         FontMetrics metrics = icon.getFontMetrics();
         icon.setColor(color);
         int drawX = x + (size - metrics.stringWidth(glyph)) / 2;
         int drawY = y + (size + metrics.getAscent() - metrics.getDescent()) / 2;
         icon.drawString(glyph, drawX, drawY);
         icon.dispose();
      } else {
         Graphics2D icon = (Graphics2D)g.create();
         icon.setColor(color);
         icon.setStroke(new BasicStroke(Math.max(1.6F, (float)size / 13.0F), 1, 1));
         int inset = Math.max(2, size / 8);
         switch (kind) {
            case TV:
               icon.drawRoundRect(x + inset, y + size / 5, size - inset * 2, size * 3 / 5, 5, 5);
               icon.drawLine(x + size / 2, y + size / 5, x + size / 3, y);
               icon.drawLine(x + size / 2, y + size / 5, x + size * 2 / 3, y);
               icon.drawLine(x + size / 3, y + size * 4 / 5, x + size / 4, y + size - 1);
               icon.drawLine(x + size * 2 / 3, y + size * 4 / 5, x + size * 3 / 4, y + size - 1);
               break;
            case MOVIE:
               int reel = size * 3 / 4;
               icon.drawOval(x, y, reel, reel);
               int hole = Math.max(3, size / 7);
               int centerX = x + reel / 2;
               int centerY = y + reel / 2;
               icon.fillOval(centerX - hole / 2, y + size / 8, hole, hole);
               icon.fillOval(x + size / 8, centerY - hole / 2, hole, hole);
               icon.fillOval(centerX - hole / 2, y + reel - size / 8 - hole, hole, hole);
               icon.fillOval(x + reel - size / 8 - hole, centerY - hole / 2, hole, hole);
               icon.drawLine(x + reel * 2 / 3, y + reel * 2 / 3, x + size, y + size);
               icon.drawLine(x + reel / 2, y + reel, x + size - 2, y + reel);
               break;
            case CLOCK:
               icon.drawOval(x + 1, y + 1, size - 3, size - 3);
               icon.drawLine(x + size / 2, y + size / 2, x + size / 2, y + size / 5);
               icon.drawLine(x + size / 2, y + size / 2, x + size * 3 / 4, y + size * 2 / 3);
               break;
            case USER:
               icon.drawOval(x + size * 3 / 8, y + 1, size / 3, size / 3);
               icon.drawArc(x + size / 6, y + size / 3, size * 2 / 3, size * 2 / 3, 18, 144);
               icon.drawLine(x + size / 6, y + size * 4 / 5, x + size * 5 / 6, y + size * 4 / 5);
               break;
            case SERVER:
               for (int row = 0; row < 3; row++) {
                  int rowY = y + row * size / 3;
                  icon.drawRoundRect(x + 1, rowY + 1, size - 3, size / 4, 4, 4);
                  icon.fillOval(x + size / 6, rowY + size / 10, Math.max(2, size / 10), Math.max(2, size / 10));
               }
               break;
            case ACTIVITY:
               icon.drawRoundRect(x + 1, y + 2, size - 3, size - 5, 4, 4);
               Path2D pulse = new Float();
               pulse.moveTo((double)x + (double)size / 7.0, (double)y + (double)size * 0.56);
               pulse.lineTo((double)x + (double)size * 0.34, (double)y + (double)size * 0.56);
               pulse.lineTo((double)x + (double)size * 0.44, (double)y + (double)size * 0.28);
               pulse.lineTo((double)x + (double)size * 0.57, (double)y + (double)size * 0.74);
               pulse.lineTo((double)x + (double)size * 0.68, (double)y + (double)size * 0.5);
               pulse.lineTo((double)x + (double)size * 0.86, (double)y + (double)size * 0.5);
               icon.draw(pulse);
         }

         icon.dispose();
      }
   }

   private void drawRankMedallion(Graphics2D g, int index, int centerX, int centerY, int radius) {
      Color rank = this.rankColor(index);
      g.setColor(new Color(7, 13, 20, 190));
      g.fillOval(centerX - radius, centerY - radius, radius * 2, radius * 2);
      g.setColor(new Color(rank.getRed(), rank.getGreen(), rank.getBlue(), 220));
      g.setStroke(new BasicStroke(1.6F));
      g.drawOval(centerX - radius, centerY - radius, radius * 2, radius * 2);
      String value = String.valueOf(index + 1);
      g.setFont(this.font(1, (float)radius * 1.2F));
      FontMetrics metrics = g.getFontMetrics();
      g.setColor(rank);
      g.drawString(value, centerX - metrics.stringWidth(value) / 2, centerY + (metrics.getAscent() - metrics.getDescent()) / 2);
   }

   private void drawSquareRankBadge(Graphics2D g, int index, int centerX, int centerY, int size) {
      Color rank = this.rankColor(index);
      int x = centerX - size / 2;
      int y = centerY - size / 2;
      g.setColor(new Color(rank.getRed(), rank.getGreen(), rank.getBlue(), 225));
      g.fillRoundRect(x, y, size, size, 5, 5);
      String value = String.valueOf(index + 1);
      g.setFont(this.font(1, (float)size * 0.66F));
      FontMetrics metrics = g.getFontMetrics();
      g.setColor(index == 0 ? new Color(22, 27, 29) : Color.WHITE);
      g.drawString(value, centerX - metrics.stringWidth(value) / 2, centerY + (metrics.getAscent() - metrics.getDescent()) / 2);
   }

   private void drawViewerAvatar(Graphics2D g, String userName, int index, int centerX, int centerY, int radius) {
      Color[] palette = new Color[]{new Color(226, 87, 121), new Color(235, 143, 65), new Color(179, 80, 214), new Color(78, 137, 224)};
      Color avatar = palette[index % palette.length];
      g.setColor(avatar);
      g.fillOval(centerX - radius, centerY - radius, radius * 2, radius * 2);
      String initial = userName != null && !userName.isBlank() ? userName.substring(0, 1).toUpperCase(Locale.ROOT) : "?";
      g.setFont(this.font(1, (float)radius * 1.15F));
      FontMetrics metrics = g.getFontMetrics();
      g.setColor(Color.WHITE);
      g.drawString(initial, centerX - metrics.stringWidth(initial) / 2, centerY + (metrics.getAscent() - metrics.getDescent()) / 2);
   }

   private void drawHeroViewerRows(Graphics2D g, List<PlaybackRankingPosterRenderer.ViewerRank> viewers, int x, int y, int width, int count) {
      this.drawSectionHeading(g, PlaybackRankingPosterRenderer.IconKind.USER, "用户观看排行", x, y, 25.0F, WHITE, width);
      int shown = Math.min(count, viewers.size());
      long max = viewers.stream().limit((long)shown).mapToLong(PlaybackRankingPosterRenderer.ViewerRank::seconds).max().orElse(1L);

      for (int index = 0; index < shown; index++) {
         PlaybackRankingPosterRenderer.ViewerRank viewer = viewers.get(index);
         int baseline = y + 45 + index * 39;
         this.drawRankMedallion(g, index, x + 12, baseline - 6, 11);
         g.setFont(this.font(1, 16.0F));
         g.setColor(WHITE);
         g.drawString(this.ellipsis(g, viewer.userName(), 130), x + 34, baseline);
         int barX = x + 164;
         int barW = width - 300;
         g.setColor(new Color(255, 255, 255, 38));
         g.fillRoundRect(barX, baseline - 8, barW, 8, 8, 8);
         g.setColor(MINT);
         g.fillRoundRect(barX, baseline - 8, Math.max(6, (int)Math.round((double)((long)barW * viewer.seconds()) / (double)max)), 8, 8, 8);
         g.setFont(this.font(0, 14.0F));
         g.setColor(MUTED);
         String duration = this.formatDuration(viewer.seconds());
         g.drawString(duration, x + width - 106, baseline);
         this.drawCountPill(g, "×" + viewer.playCount(), x + width - 48, baseline - 18, 42, 24);
      }
   }

   private void drawHeroChampionCaption(Graphics2D g, PlaybackRankingPosterRenderer.MediaRank item, int x, int baseline, int width) {
      if (item != null) {
         g.setFont(this.font(1, 60.0F));
         g.setColor(WHITE);
         g.drawString(this.ellipsis(g, item.title(), width), x, baseline);
         this.drawIcon(g, PlaybackRankingPosterRenderer.IconKind.CLOCK, x, baseline + 27, 25, MUTED);
         g.setFont(this.font(0, 25.0F));
         g.setColor(MUTED);
         g.drawString(this.formatDuration(item.seconds()) + "   ×" + item.playCount(), x + 34, baseline + 59);
      }
   }

   private void drawHeroActivityRows(Graphics2D g, List<PlaybackRankingPosterRenderer.ViewerRank> viewers, int x, int y, int width, int count) {
      this.drawSectionHeading(g, PlaybackRankingPosterRenderer.IconKind.ACTIVITY, "活跃时间范围排行", x, y, 25.0F, WHITE, width);
      List<PlaybackRankingPosterRenderer.ActivityRank> activities = this.activityRows(viewers);
      int shown = Math.min(count, activities.size());
      int colW = width / 2;

      for (int index = 0; index < shown; index++) {
         PlaybackRankingPosterRenderer.ActivityRank activity = activities.get(index);
         int col = index / 3;
         int row = index % 3;
         int itemX = x + col * colW;
         int baseline = y + 47 + row * 49;
         this.drawRankMedallion(g, index, itemX + 12, baseline - 6, 12);
         g.setFont(this.font(0, 17.0F));
         g.setColor(WHITE);
         g.drawString(this.formatHM(activity.start()) + "–" + this.formatHM(activity.end()), itemX + 34, baseline);
         g.setFont(this.font(0, 15.0F));
         g.setColor(MUTED);
         String duration = this.formatDuration(activity.seconds());
         int durationW = g.getFontMetrics().stringWidth(duration);
         g.drawString(duration, itemX + colW - durationW - 20, baseline);
      }
   }

   private void drawTicketLogo(Graphics2D g, int x, int y, int size) {
      Polygon diamond = new Polygon(new int[]{x + size / 2, x + size, x + size / 2, x}, new int[]{y, y + size / 2, y + size, y + size / 2}, 4);
      g.setColor(new Color(73, 190, 117));
      g.fillPolygon(diamond);
      Polygon play = new Polygon(new int[]{x + size * 2 / 5, x + size * 2 / 5, x + size * 3 / 4}, new int[]{y + size / 4, y + size * 3 / 4, y + size / 2}, 3);
      g.setColor(new Color(228, 244, 232));
      g.fillPolygon(play);
   }

   private void drawTicketGlassStructure(Graphics2D g, Color gold) {
      Area upper = new Area(new java.awt.geom.RoundRectangle2D.Float(24.0F, 20.0F, 1552.0F, 634.0F, 22.0F, 22.0F));
      this.subtractCircle(upper, 24, 136, 22);
      this.subtractCircle(upper, 1576, 136, 22);
      this.subtractCircle(upper, 24, 654, 22);
      this.subtractCircle(upper, 1576, 654, 22);
      this.subtractCircle(upper, 800, 654, 18);

      for (int y = 170; y < 632; y += 28) {
         this.subtractCircle(upper, 24, y, 8);
         this.subtractCircle(upper, 1576, y, 8);
      }

      Area lower = new Area(new java.awt.geom.RoundRectangle2D.Float(20.0F, 674.0F, 1562.0F, 200.0F, 22.0F, 22.0F));
      this.subtractCircle(lower, 800, 674, 18);
      this.subtractCircle(lower, 800, 874, 18);
      g.setColor(new Color(64, 49, 34, 148));
      g.fill(upper);
      g.fill(lower);
      g.setStroke(new BasicStroke(2.0F));
      g.setColor(new Color(gold.getRed(), gold.getGreen(), gold.getBlue(), 160));
      g.draw(upper);
      g.draw(lower);
      g.drawLine(24, 136, 1576, 136);
      g.setStroke(new BasicStroke(1.2F, 0, 0, 1.0F, new float[]{5.0F, 6.0F}, 0.0F));
      g.drawLine(802, 690, 802, 858);
      g.setStroke(new BasicStroke(1.0F));
   }

   private void subtractCircle(Area area, int centerX, int centerY, int radius) {
      area.subtract(
         new Area(new java.awt.geom.Ellipse2D.Float((float)(centerX - radius), (float)(centerY - radius), (float)radius * 2.0F, (float)radius * 2.0F))
      );
   }

   private void drawStar(Graphics2D g, int centerX, int centerY, int radius, Color color) {
      Polygon star = new Polygon();

      for (int point = 0; point < 10; point++) {
         double angle = (-Math.PI / 2) + (double)point * Math.PI / 5.0;
         double distance = point % 2 == 0 ? (double)radius : (double)radius * 0.43;
         star.addPoint((int)Math.round((double)centerX + Math.cos(angle) * distance), (int)Math.round((double)centerY + Math.sin(angle) * distance));
      }

      g.setColor(color);
      g.fillPolygon(star);
   }

   private void drawTicketCaption(Graphics2D g, PlaybackRankingPosterRenderer.MediaRank item, int x, int baseline, int width) {
      if (item != null) {
         String title = "《" + item.title() + "》";
         g.setFont(this.serifFont(1, 18.0F));
         FontMetrics titleMetrics = g.getFontMetrics();
         g.setColor(new Color(255, 239, 213));
         g.drawString(this.ellipsis(g, title, width), x + Math.max(0, (width - titleMetrics.stringWidth(title)) / 2), baseline);
         String meta = this.formatDuration(item.seconds()) + "   ×" + item.playCount();
         g.setFont(this.font(0, 14.0F));
         FontMetrics metaMetrics = g.getFontMetrics();
         int metaX = x + Math.max(0, (width - metaMetrics.stringWidth(meta) - 20) / 2);
         this.drawIcon(g, PlaybackRankingPosterRenderer.IconKind.CLOCK, metaX, baseline + 9, 16, new Color(229, 207, 176));
         g.setColor(new Color(229, 207, 176));
         g.drawString(meta, metaX + 21, baseline + 24);
      }
   }

   private void drawTicketGutters(Graphics2D g, Color gold) {
      this.drawStar(g, 76, 704, 7, gold);
      this.drawStar(g, 76, 846, 7, gold);
      this.drawStar(g, 1524, 704, 7, gold);
      this.drawStar(g, 1524, 846, 7, gold);
      Graphics2D left = (Graphics2D)g.create();
      left.rotate(-Math.PI / 2, 84.0, 790.0);
      left.setFont(this.font(0, 13.0F));
      left.setColor(new Color(gold.getRed(), gold.getGreen(), gold.getBlue(), 185));
      left.drawString("082005", 58, 795);
      left.dispose();
      Graphics2D right = (Graphics2D)g.create();
      right.rotate(Math.PI / 2, 1516.0, 790.0);
      right.setFont(this.font(0, 13.0F));
      right.setColor(new Color(gold.getRed(), gold.getGreen(), gold.getBlue(), 185));
      right.drawString("082205", 1490, 795);
      right.dispose();
   }

   private void drawStampPoster(Graphics2D g, PlaybackRankingPosterRenderer.MediaRank item, int x, int y, int width, int height, Color paper) {
      Area stamp = new Area(new java.awt.geom.Rectangle2D.Float((float)x, (float)y, (float)width, (float)height));
      int notch = Math.max(7, width / 16);
      int step = notch + 3;

      for (int px = x; px <= x + width; px += step) {
         stamp.subtract(
            new Area(new java.awt.geom.Ellipse2D.Float((float)px - (float)notch / 2.0F, (float)y - (float)notch / 2.0F, (float)notch, (float)notch))
         );
         stamp.subtract(
            new Area(new java.awt.geom.Ellipse2D.Float((float)px - (float)notch / 2.0F, (float)(y + height) - (float)notch / 2.0F, (float)notch, (float)notch))
         );
      }

      for (int py = y; py <= y + height; py += step) {
         stamp.subtract(
            new Area(new java.awt.geom.Ellipse2D.Float((float)x - (float)notch / 2.0F, (float)py - (float)notch / 2.0F, (float)notch, (float)notch))
         );
         stamp.subtract(
            new Area(new java.awt.geom.Ellipse2D.Float((float)(x + width) - (float)notch / 2.0F, (float)py - (float)notch / 2.0F, (float)notch, (float)notch))
         );
      }

      g.setColor(new Color(220, 205, 177, 225));
      g.fill(stamp);
      int margin = Math.max(8, width / 14);
      this.drawPoster(g, item, x + margin, y + margin, width - margin * 2, height - margin * 2, 2);
      g.setColor(new Color(paper.getRed(), paper.getGreen(), paper.getBlue(), 150));
      g.setStroke(new BasicStroke(1.2F));
      g.drawRect(x + margin - 2, y + margin - 2, width - margin * 2 + 4, height - margin * 2 + 4);
   }

   private void drawTicketRank(Graphics2D g, int index, int x, int baseline, float size) {
      String value = String.valueOf(index + 1);
      Font displayFont = this.serifFont(1, size);
      GlyphVector glyph = displayFont.createGlyphVector(g.getFontRenderContext(), value);
      AffineTransform transform = new AffineTransform();
      transform.translate((double)x, (double)baseline);
      transform.scale(0.68, 1.0);
      Shape shape = transform.createTransformedShape(glyph.getOutline());
      g.setColor(new Color(0, 0, 0, 120));
      g.translate(2, 3);
      g.fill(shape);
      g.translate(-2, -3);
      Color[] ticketRanks = new Color[]{new Color(178, 143, 82), new Color(148, 145, 142), new Color(163, 116, 82), new Color(103, 117, 148)};
      g.setColor(ticketRanks[Math.min(index, ticketRanks.length - 1)]);
      g.fill(shape);
   }

   private void drawTicketViewerCards(Graphics2D g, List<PlaybackRankingPosterRenderer.ViewerRank> viewers, int x, int y, int width, int count, Color gold) {
      this.drawSectionHeading(g, PlaybackRankingPosterRenderer.IconKind.USER, "用户观看排行", x, y, 25.0F, gold, width);
      int shown = Math.min(count, viewers.size());
      long max = viewers.stream().limit((long)shown).mapToLong(PlaybackRankingPosterRenderer.ViewerRank::seconds).max().orElse(1L);
      int gap = 30;
      int cardW = (width - gap) / 2;

      for (int index = 0; index < shown; index++) {
         PlaybackRankingPosterRenderer.ViewerRank viewer = viewers.get(index);
         int col = index % 2;
         int row = index / 2;
         int cardX = x + col * (cardW + gap);
         int cardY = y + 30 + row * 67;
         this.drawRankMedallion(g, index, cardX + 12, cardY + 17, 11);
         this.drawViewerAvatar(g, viewer.userName(), index, cardX + 46, cardY + 17, 14);
         g.setFont(this.font(1, 15.0F));
         g.setColor(new Color(255, 244, 226));
         g.drawString(this.ellipsis(g, viewer.userName(), cardW - 145), cardX + 67, cardY + 22);
         g.setFont(this.font(0, 13.0F));
         g.setColor(new Color(240, 222, 194));
         g.drawString(this.formatDuration(viewer.seconds()), cardX + 67, cardY + 47);
         this.drawCountPill(g, "×" + viewer.playCount(), cardX + cardW - 49, cardY + 3, 44, 24);
         int barX = cardX + 2;
         int barY = cardY + 54;
         int barW = cardW - 10;
         g.setColor(new Color(255, 255, 255, 38));
         g.fillRoundRect(barX, barY, barW, 7, 7, 7);
         g.setColor(MINT);
         g.fillRoundRect(barX, barY, Math.max(5, (int)Math.round((double)((long)barW * viewer.seconds()) / (double)max)), 7, 7, 7);
      }
   }

   private void drawMagazineMovieList(Graphics2D g, List<PlaybackRankingPosterRenderer.MediaRank> movies, int x, int y, int width, int count) {
      this.drawMixedSectionHeading(
         g, PlaybackRankingPosterRenderer.IconKind.MOVIE, "电影", "Top " + Math.min(count, movies.size()), x, y, 30.0F, WHITE, MINT, width
      );
      int shown = Math.min(count, movies.size());
      int firstBaseline = y + 88;
      int rowH = 105;

      for (int index = 0; index < shown; index++) {
         PlaybackRankingPosterRenderer.MediaRank item = movies.get(index);
         int baseline = firstBaseline + index * rowH;
         this.drawRankNumber(g, index, x, baseline, 68.0F, false);
         g.setFont(this.font(1, 22.0F));
         g.setColor(WHITE);
         g.drawString(this.ellipsis(g, item.title(), width - 230), x + 68, baseline - 6);
         g.setFont(this.font(0, 16.0F));
         g.setColor(MUTED);
         String meta = this.formatDuration(item.seconds()) + "  |  ×" + item.playCount();
         int metaW = g.getFontMetrics().stringWidth(meta);
         g.drawString(meta, x + width - metaW, baseline - 6);
         g.setColor(LINE);
         g.fillRect(x, baseline + 28, width, 1);
      }
   }

   private void drawMagazineOutlineRank(Graphics2D g, int index, int x, int baseline, float size) {
      String value = String.valueOf(index + 1);
      Font displayFont = this.font(0, size);
      GlyphVector glyph = displayFont.createGlyphVector(g.getFontRenderContext(), value);
      Shape raw = glyph.getOutline();
      AffineTransform transform = new AffineTransform();
      transform.translate((double)x, (double)baseline);
      transform.scale(0.64, 1.0);
      Shape shape = transform.createTransformedShape(raw);
      Stroke previousStroke = g.getStroke();
      g.setStroke(new BasicStroke(4.8F, 1, 1));
      g.setColor(new Color(0, 0, 0, 125));
      g.draw(shape);
      g.setStroke(new BasicStroke(2.2F, 1, 1));
      g.setColor(this.rankColor(index));
      g.draw(shape);
      g.setStroke(previousStroke);
   }

   private void drawMagazineCaption(Graphics2D g, PlaybackRankingPosterRenderer.MediaRank item, int index, int x, int baseline, int width) {
      if (item != null) {
         g.setFont(this.font(1, 20.0F));
         String title = this.ellipsis(g, item.title(), width);
         FontMetrics titleMetrics = g.getFontMetrics();
         g.setColor(this.rankColor(index));
         g.drawString(title, x + Math.max(0, (width - titleMetrics.stringWidth(title)) / 2), baseline);
         String meta = this.formatDuration(item.seconds()) + "  |  ×" + item.playCount();
         g.setFont(this.font(0, 15.0F));
         FontMetrics metaMetrics = g.getFontMetrics();
         int metaX = x + Math.max(0, (width - metaMetrics.stringWidth(meta) - 20) / 2);
         this.drawIcon(g, PlaybackRankingPosterRenderer.IconKind.CLOCK, metaX, baseline + 10, 16, MUTED);
         g.setColor(MUTED);
         g.drawString(meta, metaX + 20, baseline + 25);
      }
   }

   private void drawCountPill(Graphics2D g, String value, int x, int y, int width, int height) {
      g.setColor(new Color(31, 111, 77, 155));
      g.fillRoundRect(x, y, width, height, height, height);
      g.setColor(new Color(109, 239, 178, 180));
      g.drawRoundRect(x, y, width, height, height, height);
      g.setFont(this.font(1, 13.0F));
      FontMetrics metrics = g.getFontMetrics();
      g.setColor(new Color(130, 246, 191));
      g.drawString(value, x + (width - metrics.stringWidth(value)) / 2, y + (height + metrics.getAscent() - metrics.getDescent()) / 2);
   }

   private void drawDualChampionCaption(Graphics2D g, PlaybackRankingPosterRenderer.MediaRank item, int x, int baseline, int width) {
      if (item != null) {
         g.setFont(this.font(1, 42.0F));
         g.setColor(WHITE);
         g.drawString(this.ellipsis(g, item.title(), width), x, baseline);
         this.drawIcon(g, PlaybackRankingPosterRenderer.IconKind.CLOCK, x, baseline + 22, 25, MUTED);
         g.setFont(this.font(0, 25.0F));
         g.setColor(MUTED);
         g.drawString(this.formatDuration(item.seconds()), x + 34, baseline + 45);
         g.setFont(this.font(1, 27.0F));
         g.setColor(this.rankColor(0));
         g.drawString("× " + item.playCount(), x, baseline + 96);
      }
   }

   private void drawHeader(Graphics2D g, Date targetDate, String serverLabel, boolean compact) {
      int height = compact ? 72 : 76;
      this.fillGlassPanel(g, 0, 0, 1600, height, 0, true);
      g.setColor(LINE);
      g.fillRect(0, height - 1, 1600, 1);
      g.setFont(this.font(1, compact ? 36.0F : 38.0F));
      g.setColor(WHITE);
      g.drawString("Emby 今日排行榜", 30, compact ? 51 : 54);
      this.drawDate(g, targetDate, compact ? 588 : 630, compact ? 53 : 54, compact ? 29.0F : 28.0F);
      this.drawServerBadge(g, serverLabel, compact ? 1234 : 1285, compact ? 14 : 20, compact ? 328 : 276);
   }

   private void drawMovieList(Graphics2D g, List<PlaybackRankingPosterRenderer.MediaRank> movies, int x, int y, int width, int count, int rowH) {
      this.drawMovieList(g, movies, x, y, width, count, rowH, WHITE);
   }

   private void drawMovieList(
      Graphics2D g, List<PlaybackRankingPosterRenderer.MediaRank> movies, int x, int y, int width, int count, int rowH, Color titleColor
   ) {
      this.drawSectionHeading(g, PlaybackRankingPosterRenderer.IconKind.MOVIE, "电影 Top " + Math.min(count, movies.size()), x, y, 28.0F, titleColor, width);
      int top = y + 8;

      for (int index = 0; index < Math.min(count, movies.size()); index++) {
         PlaybackRankingPosterRenderer.MediaRank item = movies.get(index);
         int baseline = top + (index + 1) * rowH;
         this.drawRankNumber(g, index, x, baseline, 29.0F, false);
         g.setFont(this.font(1, 20.0F));
         g.setColor(WHITE);
         g.drawString(this.ellipsis(g, item.title(), width - 205), x + 48, baseline);
         g.setFont(this.font(0, 16.0F));
         g.setColor(MUTED);
         String meta = this.formatDuration(item.seconds()) + "  ×" + item.playCount();
         int metaW = g.getFontMetrics().stringWidth(meta);
         g.drawString(meta, x + width - metaW, baseline);
         g.setColor(LINE);
         g.fillRect(x, baseline + 15, width, 1);
      }
   }

   private void drawDualMovieCards(Graphics2D g, List<PlaybackRankingPosterRenderer.MediaRank> movies, int x, int y, int width, int count, int rowH) {
      this.drawCenteredSectionHeading(g, PlaybackRankingPosterRenderer.IconKind.MOVIE, "电影 Top " + Math.min(count, movies.size()), x, y, width, 29.0F, WHITE);
      int top = y + 22;

      for (int index = 0; index < Math.min(count, movies.size()); index++) {
         PlaybackRankingPosterRenderer.MediaRank item = movies.get(index);
         int rowY = top + index * rowH;
         this.fillGlassCard(g, x, rowY + 5, width, rowH - 7, 9);
         int baseline = rowY + 39;
         this.drawRankMedallion(g, index, x + 28, baseline - 11, 22);
         g.setColor(new Color(142, 160, 192, 80));
         g.fillRect(x + 68, rowY + 13, 1, rowH - 23);
         g.setFont(this.font(1, 20.0F));
         g.setColor(WHITE);
         g.drawString(this.ellipsis(g, item.title(), width - 250), x + 88, baseline);
         g.setFont(this.font(0, 16.0F));
         g.setColor(index == 0 ? new Color(236, 190, 65) : MUTED);
         String meta = this.formatDuration(item.seconds()) + "  ×" + item.playCount();
         int metaW = g.getFontMetrics().stringWidth(meta);
         g.drawString(meta, x + width - metaW - 18, baseline);
      }
   }

   private void drawTicketMovieList(Graphics2D g, List<PlaybackRankingPosterRenderer.MediaRank> movies, int x, int y, int width, Color gold) {
      this.drawSectionHeading(g, PlaybackRankingPosterRenderer.IconKind.MOVIE, "电影 Top " + Math.min(5, movies.size()), x, y, 29.0F, gold, width);
      g.setFont(this.font(0, 14.0F));
      g.setColor(new Color(gold.getRed(), gold.getGreen(), gold.getBlue(), 155));
      g.drawString("时长", x + width - 142, y + 5);
      g.drawString("播放", x + width - 54, y + 5);
      int rowH = 75;
      int top = y + 24;

      for (int index = 0; index < Math.min(5, movies.size()); index++) {
         PlaybackRankingPosterRenderer.MediaRank item = movies.get(index);
         int baseline = top + (index + 1) * rowH - 28;
         this.drawRankNumber(g, index, x + 8, baseline, 36.0F, false);
         g.setFont(this.serifFont(1, 20.0F));
         g.setColor(new Color(255, 244, 226));
         String title = "《" + (item.title() == null ? "未知" : item.title()) + "》";
         g.drawString(this.ellipsis(g, title, width - 245), x + 74, baseline);
         g.setFont(this.font(0, 16.0F));
         g.setColor(new Color(245, 219, 177));
         String duration = this.formatDuration(item.seconds());
         int durationW = g.getFontMetrics().stringWidth(duration);
         g.drawString(duration, x + width - 105 - durationW, baseline);
         g.drawString("×" + item.playCount(), x + width - 44, baseline);
         Stroke previousStroke = g.getStroke();
         g.setStroke(new BasicStroke(1.0F, 0, 0, 1.0F, new float[]{6.0F, 6.0F}, 0.0F));
         g.setColor(new Color(gold.getRed(), gold.getGreen(), gold.getBlue(), 95));
         g.drawLine(x, baseline + 24, x + width, baseline + 24);
         g.setStroke(previousStroke);
      }
   }

   private void drawMovieListWithBackdrops(Graphics2D g, List<PlaybackRankingPosterRenderer.MediaRank> movies, int x, int y, int width, int count, int rowH) {
      this.drawSectionHeading(g, PlaybackRankingPosterRenderer.IconKind.MOVIE, "电影 Top " + Math.min(count, movies.size()), x, y, 30.0F, WHITE, width);
      int top = y + 22;

      for (int index = 0; index < Math.min(count, movies.size()); index++) {
         PlaybackRankingPosterRenderer.MediaRank item = movies.get(index);
         int rowY = top + index * rowH;
         int baseline = rowY + 54;
         this.drawRankNumber(g, index, x, baseline, 34.0F, false);
         this.drawBackdrop(g, item, x + 53, rowY + 5, 94, 71, 5);
         g.setFont(this.font(1, 19.0F));
         g.setColor(WHITE);
         g.drawString(this.ellipsis(g, item.title(), width - 305), x + 165, baseline - 8);
         g.setFont(this.font(0, 15.0F));
         g.setColor(MUTED);
         String meta = this.formatDuration(item.seconds()) + "  ×" + item.playCount();
         int metaW = g.getFontMetrics().stringWidth(meta);
         g.drawString(meta, x + width - metaW, baseline - 8);
         g.setColor(LINE);
         g.fillRect(x, rowY + rowH - 2, width, 1);
      }
   }

   private void drawActivityList(Graphics2D g, List<PlaybackRankingPosterRenderer.ViewerRank> viewers, int x, int y, int width, int count, int columns) {
      this.drawActivityList(g, viewers, x, y, width, count, columns, WHITE);
   }

   private void drawActivityList(
      Graphics2D g, List<PlaybackRankingPosterRenderer.ViewerRank> viewers, int x, int y, int width, int count, int columns, Color titleColor
   ) {
      this.drawSectionHeading(g, PlaybackRankingPosterRenderer.IconKind.CLOCK, "活跃时间范围排行", x, y, 25.0F, titleColor, width);
      List<PlaybackRankingPosterRenderer.ActivityRank> activities = this.activityRows(viewers);
      int shown = Math.min(count, activities.size());
      int rows = Math.max(1, (int)Math.ceil((double)shown / (double)columns));
      int colW = width / columns;
      int rowH = 34;

      for (int index = 0; index < shown; index++) {
         PlaybackRankingPosterRenderer.ActivityRank activity = activities.get(index);
         int col = index % columns;
         int row = index / columns;
         int itemX = x + col * colW;
         int baseline = y + 47 + row * rowH;
         this.drawRankNumber(g, index, itemX, baseline, 18.0F, false);
         g.setFont(this.font(1, 16.0F));
         g.setColor(WHITE);
         String name = this.ellipsis(g, activity.userName(), Math.max(64, colW - 188));
         g.drawString(name, itemX + 28, baseline);
         g.setFont(this.font(0, 14.0F));
         g.setColor(MUTED);
         String range = this.formatHM(activity.start()) + "–" + this.formatHM(activity.end()) + " · " + this.formatDuration(activity.seconds());
         int rangeW = g.getFontMetrics().stringWidth(range);
         g.drawString(range, itemX + colW - rangeW - 12, baseline);
      }

      if (rows == 0) {
         g.setColor(MUTED);
         g.drawString("暂无活跃范围", x, y + 48);
      }
   }

   private void drawDualActivityCards(Graphics2D g, List<PlaybackRankingPosterRenderer.ViewerRank> viewers, int x, int y, int width, int count) {
      this.drawSectionHeading(g, PlaybackRankingPosterRenderer.IconKind.CLOCK, "活跃时间范围排行", x, y, 26.0F, MINT, width);
      List<PlaybackRankingPosterRenderer.ActivityRank> activities = this.activityRows(viewers);
      int shown = Math.min(count, activities.size());
      int gap = 22;
      int cardW = (width - gap) / 2;
      int cardH = 53;

      for (int index = 0; index < shown; index++) {
         PlaybackRankingPosterRenderer.ActivityRank activity = activities.get(index);
         int col = index / 3;
         int row = index % 3;
         int cardX = x + col * (cardW + gap);
         int cardY = y + 14 + row * 55;
         this.fillGlassCard(g, cardX, cardY, cardW, cardH, 10);
         this.drawRankMedallion(g, index, cardX + 22, cardY + 26, 13);
         g.setFont(this.font(1, 16.0F));
         g.setColor(WHITE);
         g.drawString(this.ellipsis(g, activity.userName(), cardW - 158), cardX + 46, cardY + 32);
         g.setFont(this.font(0, 14.0F));
         g.setColor(MUTED);
         String range = this.formatHM(activity.start()) + "–" + this.formatHM(activity.end());
         int rangeW = g.getFontMetrics().stringWidth(range);
         g.drawString(range, cardX + cardW - rangeW - 12, cardY + 32);
      }
   }

   private void drawDualViewerCards(Graphics2D g, List<PlaybackRankingPosterRenderer.ViewerRank> viewers, int x, int y, int width, int count) {
      this.drawSectionHeading(g, PlaybackRankingPosterRenderer.IconKind.USER, "用户观看排行", x, y, 26.0F, MINT, width);
      int shown = Math.min(count, viewers.size());
      long max = viewers.stream().limit((long)shown).mapToLong(PlaybackRankingPosterRenderer.ViewerRank::seconds).max().orElse(1L);
      int gap = 10;
      int cardW = (width - gap * 3) / 4;

      for (int index = 0; index < shown; index++) {
         PlaybackRankingPosterRenderer.ViewerRank viewer = viewers.get(index);
         int cardX = x + index * (cardW + gap);
         int cardY = y + 14;
         this.fillGlassCard(g, cardX, cardY, cardW, 126, 10);
         this.drawRankNumber(g, index, cardX + 10, cardY + 36, 29.0F, false);
         this.drawViewerAvatar(g, viewer.userName(), index, cardX + 54, cardY + 27, 17);
         g.setFont(this.font(1, 16.0F));
         g.setColor(WHITE);
         g.drawString(this.ellipsis(g, viewer.userName(), cardW - 118), cardX + 78, cardY + 33);
         this.drawCountPill(g, "×" + viewer.playCount(), cardX + cardW - 49, cardY + 14, 42, 24);
         g.setFont(this.font(0, 13.0F));
         g.setColor(MUTED);
         this.drawIcon(g, PlaybackRankingPosterRenderer.IconKind.CLOCK, cardX + 15, cardY + 56, 17, MUTED);
         g.drawString(this.formatDuration(viewer.seconds()), cardX + 38, cardY + 70);
         int barX = cardX + 12;
         int barW = cardW - 24;
         g.setColor(new Color(255, 255, 255, 42));
         g.fillRoundRect(barX, cardY + 99, barW, 9, 9, 9);
         g.setColor(MINT);
         g.fillRoundRect(barX, cardY + 99, Math.max(5, (int)Math.round((double)((long)barW * viewer.seconds()) / (double)max)), 9, 9, 9);
      }
   }

   private void drawStreamingActivityStrip(Graphics2D g, List<PlaybackRankingPosterRenderer.ViewerRank> viewers, int x, int y, int width, int count) {
      this.drawSectionHeading(g, PlaybackRankingPosterRenderer.IconKind.ACTIVITY, "活跃时间范围排行", x, y, 25.0F, WHITE, width);
      List<PlaybackRankingPosterRenderer.ActivityRank> activities = this.activityRows(viewers);
      int shown = Math.min(count, activities.size());
      long max = activities.stream().limit((long)shown).mapToLong(PlaybackRankingPosterRenderer.ActivityRank::seconds).max().orElse(1L);
      int colW = width / Math.max(1, count);

      for (int index = 0; index < shown; index++) {
         PlaybackRankingPosterRenderer.ActivityRank activity = activities.get(index);
         int itemX = x + index * colW;
         this.drawSquareRankBadge(g, index, itemX + 10, y + 37, 20);
         g.setFont(this.font(1, 14.0F));
         g.setColor(WHITE);
         g.drawString(this.ellipsis(g, activity.userName(), colW - 40), itemX + 28, y + 43);
         g.setFont(this.font(0, 12.0F));
         g.setColor(MUTED);
         g.drawString(this.formatHM(activity.start()) + "–" + this.formatHM(activity.end()), itemX, y + 68);
         g.drawString(this.formatDuration(activity.seconds()), itemX, y + 88);
         int barW = Math.max(34, colW - 18);
         g.setColor(new Color(255, 255, 255, 42));
         g.fillRoundRect(itemX, y + 100, barW, 6, 6, 6);
         g.setColor(MINT);
         g.fillRoundRect(itemX, y + 100, Math.max(5, (int)Math.round((double)((long)barW * activity.seconds()) / (double)max)), 6, 6, 6);
         if (index < shown - 1) {
            g.setColor(new Color(151, 166, 190, 52));
            g.fillRect(itemX + colW - 7, y + 34, 1, 105);
         }
      }
   }

   private void drawStreamingViewerStrip(Graphics2D g, List<PlaybackRankingPosterRenderer.ViewerRank> viewers, int x, int y, int width, int count) {
      this.drawMixedSectionHeading(
         g, PlaybackRankingPosterRenderer.IconKind.USER, "用户观看排行", "Top " + Math.min(count, viewers.size()), x, y, 25.0F, WHITE, MINT, width
      );
      int shown = Math.min(count, viewers.size());
      long max = viewers.stream().limit((long)shown).mapToLong(PlaybackRankingPosterRenderer.ViewerRank::seconds).max().orElse(1L);
      int colW = width / Math.max(1, count);

      for (int index = 0; index < shown; index++) {
         PlaybackRankingPosterRenderer.ViewerRank viewer = viewers.get(index);
         int itemX = x + index * colW;
         this.drawRankMedallion(g, index, itemX + 10, y + 37, 11);
         this.drawViewerAvatar(g, viewer.userName(), index, itemX + 38, y + 37, 14);
         g.setFont(this.font(1, 14.0F));
         g.setColor(WHITE);
         g.drawString(this.ellipsis(g, viewer.userName(), colW - 76), itemX + 58, y + 43);
         g.setFont(this.font(0, 12.0F));
         g.setColor(MUTED);
         g.drawString(this.formatDuration(viewer.seconds()) + "  ×" + viewer.playCount(), itemX + 58, y + 70);
         int barW = Math.max(36, colW - 34);
         g.setColor(new Color(255, 255, 255, 42));
         g.fillRoundRect(itemX + 58, y + 88, Math.max(30, barW - 34), 6, 6, 6);
         g.setColor(MINT);
         g.fillRoundRect(itemX + 58, y + 88, Math.max(5, (int)Math.round((double)((long)(barW - 34) * viewer.seconds()) / (double)max)), 6, 6, 6);
      }
   }

   private void drawViewerBars(Graphics2D g, List<PlaybackRankingPosterRenderer.ViewerRank> viewers, int x, int y, int width, int count, int columns) {
      this.drawViewerBars(g, viewers, x, y, width, count, columns, WHITE);
   }

   private void drawViewerBars(
      Graphics2D g, List<PlaybackRankingPosterRenderer.ViewerRank> viewers, int x, int y, int width, int count, int columns, Color titleColor
   ) {
      this.drawSectionHeading(g, PlaybackRankingPosterRenderer.IconKind.USER, "用户观看排行", x, y, 25.0F, titleColor, width);
      int shown = Math.min(count, viewers.size());
      long max = 1L;

      for (int index = 0; index < shown; index++) {
         max = Math.max(max, viewers.get(index).seconds());
      }

      int colW = width / columns;
      int rowH = columns >= 4 ? 72 : (columns == 1 ? 55 : 58);

      for (int index = 0; index < shown; index++) {
         PlaybackRankingPosterRenderer.ViewerRank viewer = viewers.get(index);
         int col = index % columns;
         int row = index / columns;
         int itemX = x + col * colW;
         int top = y + 31 + row * rowH;
         this.drawRankMedallion(g, index, itemX + 11, top + 14, 11);
         g.setFont(this.font(1, 16.0F));
         g.setColor(WHITE);
         g.drawString(this.ellipsis(g, viewer.userName(), Math.max(54, colW - 105)), itemX + 28, top + 20);
         g.setFont(this.font(0, 14.0F));
         g.setColor(MUTED);
         String duration = this.formatDuration(viewer.seconds());
         int durationW = g.getFontMetrics().stringWidth(duration);
         g.drawString(duration, itemX + colW - durationW - 12, top + 20);
         int barX = itemX + 28;
         int barY = top + 31;
         int barW = Math.max(40, colW - 42);
         g.setColor(new Color(255, 255, 255, 42));
         g.fillRoundRect(barX, barY, barW, 7, 7, 7);
         int fillW = Math.max(5, (int)Math.round((double)barW * ((double)viewer.seconds() / (double)max)));
         g.setColor(MINT);
         g.fillRoundRect(barX, barY, fillW, 7, 7, 7);
      }
   }

   private void drawPoster(Graphics2D g, PlaybackRankingPosterRenderer.MediaRank item, int x, int y, int width, int height, int radius) {
      this.drawArtwork(g, item, item == null ? null : item.poster(), x, y, width, height, radius, false);
   }

   private void drawPosterShaded(Graphics2D g, PlaybackRankingPosterRenderer.MediaRank item, int x, int y, int width, int height, int radius) {
      this.drawArtwork(g, item, item == null ? null : item.poster(), x, y, width, height, radius, true);
   }

   private void drawBackdrop(Graphics2D g, PlaybackRankingPosterRenderer.MediaRank item, int x, int y, int width, int height, int radius) {
      this.drawArtwork(g, item, item == null ? null : item.backdrop(), x, y, width, height, radius, false);
   }

   private void drawBackdropShaded(Graphics2D g, PlaybackRankingPosterRenderer.MediaRank item, int x, int y, int width, int height, int radius) {
      this.drawArtwork(g, item, item == null ? null : item.backdrop(), x, y, width, height, radius, true);
   }

   private void drawArtwork(
      Graphics2D g, PlaybackRankingPosterRenderer.MediaRank item, BufferedImage artwork, int x, int y, int width, int height, int radius, boolean shadedCaption
   ) {
      Shape previousClip = g.getClip();
      RoundRectangle2D clip = new java.awt.geom.RoundRectangle2D.Float((float)x, (float)y, (float)width, (float)height, (float)radius, (float)radius);
      g.setClip(clip);
      if (artwork != null) {
         this.drawCover(g, artwork, x, y, width, height);
      } else {
         GradientPaint fallback = new GradientPaint(
            (float)x, (float)y, new Color(112, 139, 158), (float)(x + width), (float)(y + height), new Color(62, 91, 114)
         );
         g.setPaint(fallback);
         g.fillRect(x, y, width, height);
         if (item != null) {
            g.setFont(this.font(1, Math.max(15.0F, Math.min(28.0F, (float)width / 6.0F))));
            g.setColor(new Color(255, 255, 255, 170));
            String fallbackTitle = this.ellipsis(g, item.title(), width - 24);
            g.drawString(fallbackTitle, x + 12, y + height / 2);
         }
      }

      if (shadedCaption) {
         int shadeHeight = Math.min(92, height);
         GradientPaint captionShade = new GradientPaint(
            (float)x, (float)(y + height - shadeHeight), new Color(4, 10, 17, 0), (float)x, (float)(y + height), new Color(4, 10, 17, 205)
         );
         g.setPaint(captionShade);
         g.fillRect(x, y + height - shadeHeight, width, shadeHeight);
      }

      g.setClip(previousClip);
      g.setColor(new Color(218, 226, 238, 62));
      g.setStroke(new BasicStroke(1.0F));
      g.draw(clip);
   }

   private void drawHeroImageShade(Graphics2D g, int x, int y, int width, int height) {
      GradientPaint bottomShade = new GradientPaint(
         (float)x, (float)y + (float)height * 0.48F, new Color(0, 0, 0, 0), (float)x, (float)(y + height), new Color(20, 40, 54, 165)
      );
      g.setPaint(bottomShade);
      g.fillRect(x, y, width, height);
      GradientPaint sideShade = new GradientPaint(
         (float)x + (float)width * 0.62F, (float)y, new Color(0, 0, 0, 0), (float)(x + width), (float)y, new Color(20, 40, 54, 175)
      );
      g.setPaint(sideShade);
      g.fillRect(x, y, width, height);
   }

   private void drawRotatedPoster(Graphics2D g, PlaybackRankingPosterRenderer.MediaRank item, int x, int y, int width, int height, int radius, double radians) {
      Graphics2D rotated = (Graphics2D)g.create();
      rotated.rotate(radians, (double)x + (double)width / 2.0, (double)y + (double)height / 2.0);
      this.drawPoster(rotated, item, x, y, width, height, radius);
      rotated.dispose();
   }

   private void drawMediaCaption(Graphics2D g, PlaybackRankingPosterRenderer.MediaRank item, int x, int baseline, int width, float titleSize, float metaSize) {
      if (item != null) {
         g.setFont(this.font(1, titleSize));
         g.setColor(WHITE);
         g.drawString(this.ellipsis(g, item.title(), width), x, baseline);
         g.setFont(this.font(0, metaSize));
         g.setColor(MUTED);
         g.drawString(this.formatDuration(item.seconds()) + "  ×" + item.playCount(), x, baseline + Math.round(metaSize + 10.0F));
      }
   }

   private void drawRankNumber(Graphics2D g, int index, int x, int baseline, float size, boolean outlined) {
      String rank = String.valueOf(index + 1);
      Font rankFont = this.font(1, size);
      g.setFont(rankFont);
      if (outlined) {
         g.setColor(new Color(0, 0, 0, 190));
         g.drawString(rank, x + 2, baseline + 2);
      }

      g.setColor(this.rankColor(index));
      g.drawString(rank, x, baseline);
   }

   private void drawOutlinedRankNumber(Graphics2D g, int index, int x, int baseline, float size) {
      String rank = String.valueOf(index + 1);
      Font rankFont = this.font(1, size);
      GlyphVector glyph = rankFont.createGlyphVector(g.getFontRenderContext(), rank);
      Shape outline = glyph.getOutline((float)x, (float)baseline);
      Stroke previousStroke = g.getStroke();
      g.setColor(new Color(3, 7, 12, 165));
      g.fill(outline);
      g.setStroke(new BasicStroke(Math.max(2.0F, size / 24.0F), 1, 1));
      g.setColor(this.rankColor(index));
      g.draw(outline);
      g.setStroke(previousStroke);
   }

   private void drawSectionTitle(Graphics2D g, String text, int x, int baseline, float size, Color color) {
      g.setFont(this.font(1, size));
      g.setColor(color);
      g.drawString(text, x, baseline);
      g.setColor(LINE);
      g.fillRect(x, baseline + 11, Math.max(100, Math.min(340, g.getFontMetrics().stringWidth(text))), 2);
   }

   private void drawDate(Graphics2D g, Date targetDate, int x, int baseline, float size) {
      SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日（EEE）", Locale.CHINA);
      format.setTimeZone(ZONE);
      g.setFont(this.font(1, size));
      g.setColor(MINT);
      g.drawString(format.format(targetDate), x, baseline);
   }

   private void drawServerBadge(Graphics2D g, String serverLabel, int x, int y, int maxWidth) {
      String label = "推送服务器  " + (serverLabel != null && !serverLabel.isBlank() ? serverLabel : "未知服务器");
      Font badgeFont = this.font(1, 18.0F);
      g.setFont(badgeFont);
      String visible = this.ellipsis(g, label, maxWidth - 58);
      int height = 46;
      GradientPaint badge = new GradientPaint((float)x, (float)y, new Color(67, 95, 230), (float)(x + maxWidth), (float)(y + height), new Color(71, 206, 220));
      g.setPaint(badge);
      g.fillRoundRect(x, y, maxWidth, height, height, height);
      g.setColor(new Color(255, 255, 255, 140));
      g.drawRoundRect(x, y, maxWidth, height, height, height);
      g.setColor(Color.WHITE);
      this.drawIcon(g, PlaybackRankingPosterRenderer.IconKind.SERVER, x + 14, y + 12, 22, Color.WHITE);
      g.drawString(visible, x + 44, y + 30);
   }

   private void drawPlainServerLabel(Graphics2D g, String serverLabel, int x, int baseline, int maxWidth, Color color) {
      String label = "推送服务器  " + (serverLabel != null && !serverLabel.isBlank() ? serverLabel : "未知服务器");
      g.setFont(this.font(1, 19.0F));
      g.setColor(color);
      this.drawIcon(g, PlaybackRankingPosterRenderer.IconKind.SERVER, x, baseline - 21, 20, color);
      g.drawString(this.ellipsis(g, label, maxWidth - 30), x + 30, baseline);
   }

   private void drawPerforation(Graphics2D g, int x, int top, int bottom, Color color) {
      g.setColor(new Color(color.getRed(), color.getGreen(), color.getBlue(), 115));

      for (int y = top; y < bottom; y += 18) {
         g.fillOval(x - 3, y, 6, 6);
      }
   }

   private void drawTicketTexture(Graphics2D g, Color gold) {
      g.setColor(new Color(gold.getRed(), gold.getGreen(), gold.getBlue(), 17));

      for (int y = 28; y < 872; y += 8) {
         int offset = y * 37 % 17;

         for (int x = 32 + offset; x < 1568; x += 23) {
            g.fillRect(x, y, 1, 1);
         }
      }
   }

   private void drawTicketNotches(Graphics2D g, int headerBottom, int bodyBottom, int footerTop, Color gold) {
      g.setColor(new Color(0, 0, 0, 255));
      int[] notchY = new int[]{headerBottom, bodyBottom, footerTop};

      for (int y : notchY) {
         g.fillOval(8, y - 16, 32, 32);
         g.fillOval(1560, y - 16, 32, 32);
      }

      g.fillOval(783, bodyBottom - 17, 34, 34);
      g.fillOval(783, footerTop - 17, 34, 34);
      g.fillOval(783, 858, 34, 34);
      g.setColor(new Color(gold.getRed(), gold.getGreen(), gold.getBlue(), 145));

      for (int y = 164; y < bodyBottom - 20; y += 28) {
         g.drawArc(14, y, 18, 18, -90, 180);
         g.drawArc(1568, y, 18, 18, 90, 180);
      }
   }

   private void drawBarcode(Graphics2D g, int x, int y, int width, int height, Color color) {
      g.setColor(new Color(color.getRed(), color.getGreen(), color.getBlue(), 110));
      int cursor = x;

      for (int seed = 7; cursor < x + width; seed = seed * 31 + cursor) {
         int lineWidth = 1 + Math.abs(seed % 3);
         g.fillRect(cursor, y, lineWidth, height);
         cursor += lineWidth + 2 + Math.abs(seed % 4);
      }
   }

   private List<PlaybackRankingPosterRenderer.ActivityRank> activityRows(List<PlaybackRankingPosterRenderer.ViewerRank> viewers) {
      List<PlaybackRankingPosterRenderer.ActivityRank> result = new ArrayList<>();

      for (PlaybackRankingPosterRenderer.ViewerRank viewer : viewers) {
         if (viewer.firstSeen() != null && viewer.lastSeen() != null && !viewer.firstSeen().after(viewer.lastSeen())) {
            long seconds = Math.max(1L, (viewer.lastSeen().getTime() - viewer.firstSeen().getTime()) / 1000L);
            result.add(new PlaybackRankingPosterRenderer.ActivityRank(viewer.userName(), viewer.firstSeen(), viewer.lastSeen(), seconds));
         }
      }

      result.sort(Comparator.comparingLong(PlaybackRankingPosterRenderer.ActivityRank::seconds).reversed());
      return result;
   }

   private PlaybackRankingPosterRenderer.MediaRank itemAt(List<PlaybackRankingPosterRenderer.MediaRank> items, int index) {
      return index >= 0 && index < items.size() ? items.get(index) : null;
   }

   private void drawCover(Graphics2D g, BufferedImage source, int x, int y, int width, int height) {
      double scale = Math.max((double)width / (double)source.getWidth(), (double)height / (double)source.getHeight());
      int scaledWidth = (int)Math.round((double)source.getWidth() * scale);
      int scaledHeight = (int)Math.round((double)source.getHeight() * scale);
      int drawX = x + (width - scaledWidth) / 2;
      int drawY = y + (height - scaledHeight) / 2;
      g.drawImage(source, drawX, drawY, scaledWidth, scaledHeight, null);
   }

   private Font font(int style, float size) {
      return BASE_FONT.deriveFont(style, size);
   }

   private Font serifFont(int style, float size) {
      return SERIF_FONT.deriveFont(style, size);
   }

   private static Font loadBaseFont() {
      try (InputStream input = ResourceUtil.getStream("fonts/NotoSansCJKsc-Regular.otf")) {
         return input != null ? Font.createFont(0, input) : new Font("SansSerif", 0, 20);
      } catch (Exception var5) {
         return new Font("SansSerif", 0, 20);
      }
   }

   private static Font loadIconFont() {
      try (InputStream input = ResourceUtil.getStream("fonts/materialdesignicons-webfont.ttf")) {
         return input != null ? Font.createFont(0, input) : new Font("SansSerif", 0, 20);
      } catch (Exception var5) {
         return new Font("SansSerif", 0, 20);
      }
   }

   private String ellipsis(Graphics2D g, String value, int maxWidth) {
      String text = value != null && !value.isBlank() ? value : "未知";
      FontMetrics metrics = g.getFontMetrics();
      if (metrics.stringWidth(text) <= maxWidth) {
         return text;
      } else {
         String suffix = "…";
         StringBuilder result = new StringBuilder();

         for (int index = 0; index < text.length() && metrics.stringWidth(result.toString() + text.charAt(index) + suffix) <= maxWidth; index++) {
            result.append(text.charAt(index));
         }

         return result + suffix;
      }
   }

   private Color rankColor(int index) {
      return switch (index) {
         case 0 -> new Color(235, 188, 48);
         case 1 -> new Color(193, 195, 202);
         case 2 -> new Color(194, 115, 56);
         default -> new Color(74, 119, 210);
      };
   }

   private String formatDuration(long totalSeconds) {
      long hours = totalSeconds / 3600L;
      long minutes = totalSeconds % 3600L / 60L;
      long seconds = totalSeconds % 60L;
      return hours > 0L ? String.format("%d:%02d:%02d", hours, minutes, seconds) : String.format("%02d:%02d", minutes, seconds);
   }

   private String formatHM(Date date) {
      SimpleDateFormat format = new SimpleDateFormat("HH:mm");
      format.setTimeZone(ZONE);
      return format.format(date);
   }

   private String formatMonthDay(Date date) {
      SimpleDateFormat format = new SimpleDateFormat("MM·dd");
      format.setTimeZone(ZONE);
      return format.format(date);
   }

   private String formatMonthDayDash(Date date) {
      SimpleDateFormat format = new SimpleDateFormat("MM-dd");
      format.setTimeZone(ZONE);
      return format.format(date);
   }

   private void enableAA(Graphics2D g) {
      g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
      g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
      g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
      g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
   }

   private static record ActivityRank(String userName, Date start, Date end, long seconds) {
   }

   private static enum IconKind {
      TV,
      MOVIE,
      CLOCK,
      USER,
      SERVER,
      ACTIVITY;
   }

   public static record MediaRank(String title, long seconds, int playCount, BufferedImage poster, BufferedImage backdrop) {
      public MediaRank(String title, long seconds, int playCount, BufferedImage poster) {
         this(title, seconds, playCount, poster, null);
      }
   }

   public static record ViewerRank(String userName, long seconds, int playCount, Date firstSeen, Date lastSeen) {
   }
}
