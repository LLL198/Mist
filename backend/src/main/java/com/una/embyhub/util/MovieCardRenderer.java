package com.una.embyhub.util;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.font.FontRenderContext;
import java.awt.geom.Path2D;
import java.awt.geom.RoundRectangle2D;
import java.awt.geom.Path2D.Double;
import java.awt.geom.RoundRectangle2D.Float;
import java.awt.image.BufferedImage;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.MemoryCacheImageOutputStream;

public class MovieCardRenderer {
   private static final String CH_FONT_RES = "fonts/NotoSansCJKsc-Regular.otf";
   private static final String EN_FONT_RES = "fonts/NotoSans-Regular.ttf";
   private static final String LOGO_RES = "img/mist.png";
   private static final String POSTER_RES = "img/default.jpg";
   private static final float JPEG_QUALITY = 0.78F;
   private static final HttpClient HTTP = HttpClient.newBuilder().connectTimeout(Duration.ofSeconds(10L)).build();
   private static final Map<String, Font> FONT_CACHE = new ConcurrentHashMap<>();

   private static Font loadFont(Path path, float size, boolean fallbackSansSerif) {
      try {
         return Font.createFont(0, Files.newInputStream(path)).deriveFont(size);
      } catch (Exception var4) {
         return new Font(fallbackSansSerif ? "SansSerif" : "Serif", 0, Math.round(size));
      }
   }

   private static Font loadFontFromResource(String res, boolean fallbackSansSerif) {
      return FONT_CACHE.computeIfAbsent(res, k -> {
         try {
            Font var6;
            try (InputStream in = getResourceStream(res)) {
               if (in == null) {
                  throw new IOException("resource not found: " + res);
               }

               Font font = Font.createFont(0, in);
               GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
               ge.registerFont(font);
               var6 = font;
            }

            return var6;
         } catch (Exception var9) {
            return new Font(fallbackSansSerif ? "SansSerif" : "Serif", 0, 12);
         }
      });
   }

   private static InputStream getResourceStream(String res) {
      InputStream in = null;

      try {
         ClassLoader cl = Thread.currentThread().getContextClassLoader();
         if (cl != null) {
            in = cl.getResourceAsStream(res);
         }
      } catch (Throwable var3) {
      }

      if (in == null) {
         in = MovieCardRenderer.class.getResourceAsStream(res.startsWith("/") ? res : "/" + res);
      }

      return in;
   }

   private static Font derive(Font base, float size) {
      return base.deriveFont(size);
   }

   private static MovieCardRenderer.RenderFonts renderFonts(Font chBase, Font enBase) {
      return new MovieCardRenderer.RenderFonts(
         derive(chBase, 28.0F), derive(chBase, 17.0F), derive(chBase, 14.0F), derive(chBase, 11.0F), derive(enBase, 32.0F), derive(chBase, 13.0F)
      );
   }

   private static MovieCardRenderer.RenderFonts resourceFonts() {
      Font chBase = loadFontFromResource("fonts/NotoSansCJKsc-Regular.otf", true);
      Font enBase = loadFontFromResource("fonts/NotoSans-Regular.ttf", true);
      return renderFonts(chBase, enBase);
   }

   private static BufferedImage gradientBg(int w, int h) {
      BufferedImage img = new BufferedImage(w, h, 1);
      Graphics2D g = img.createGraphics();

      for (int y = 0; y < h; y++) {
         float ratio = (float)y / (float)h;
         int r = (int)(20.0F + 40.0F * ratio);
         int gg = (int)(20.0F + 30.0F * ratio);
         int b = (int)(30.0F + 40.0F * ratio);
         g.setColor(new Color(r, gg, b));
         g.drawLine(0, y, w, y);
      }

      g.dispose();
      return img;
   }

   private static BufferedImage blur(BufferedImage src, int radius) {
      if (radius < 1) {
         return src;
      } else {
         int size = radius * 2 + 1;
         float[] data = new float[size * size];
         float sigma = (float)radius / 3.0F;
         float sum = 0.0F;
         int idx = 0;

         for (int y = -radius; y <= radius; y++) {
            for (int x = -radius; x <= radius; x++) {
               float val = (float)Math.exp((double)((float)(-(x * x + y * y)) / (2.0F * sigma * sigma)));
               data[idx++] = val;
               sum += val;
            }
         }

         for (int i = 0; i < data.length; i++) {
            data[i] /= sum;
         }

         Kernel kernel = new Kernel(size, size, data);
         return new ConvolveOp(kernel, 1, null).filter(src, null);
      }
   }

   private static Graphics2D gfx(BufferedImage img) {
      Graphics2D g = img.createGraphics();
      g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
      g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
      return g;
   }

   private static List<String> wrapText(Graphics2D g, String text, Font font, int maxWidth) {
      List<String> lines = new ArrayList<>();
      if (text != null && !text.isEmpty()) {
         FontRenderContext frc = g.getFontRenderContext();
         String[] blocks = text.split("\\n", -1);

         for (String block : blocks) {
            StringBuilder line = new StringBuilder();

            for (char ch : block.toCharArray()) {
               String test = line.toString() + ch;
               int w = (int)font.getStringBounds(test, frc).getWidth();
               if (w <= maxWidth) {
                  line.append(ch);
               } else {
                  if (!line.isEmpty()) {
                     lines.add(line.toString());
                  }

                  line = new StringBuilder(String.valueOf(ch));
               }
            }

            lines.add(line.toString());
         }

         return lines;
      } else {
         return lines;
      }
   }

   private static void drawStatusIcon(Graphics2D g, int x, int y, int size, boolean success) {
      g.setStroke(new BasicStroke((float)size / 6.0F, 1, 1));
      if (success) {
         g.setColor(new Color(40, 180, 100));
         int x1 = x + size / 4;
         int y1 = y + size / 2;
         int x2 = x + size / 2;
         int y2 = y + size * 3 / 4;
         int x3 = x + size * 7 / 8;
         int y3 = y + size / 4;
         g.drawLine(x1, y1, x2, y2);
         g.drawLine(x2, y2, x3, y3);
      } else {
         g.setColor(new Color(220, 60, 60));
         int padding = size / 4;
         g.drawLine(x + padding, y + padding, x + size - padding, y + size - padding);
         g.drawLine(x + size - padding, y + padding, x + padding, y + size - padding);
      }
   }

   private static int drawWrappedText(Graphics2D g, String text, Font font, Color color, int x, int y, int maxWidth, int maxLines, int extraSpacing) {
      if (text != null && !text.isBlank()) {
         g.setFont(font);
         g.setColor(color);
         List<String> lines = wrapText(g, text, font, maxWidth);
         if (lines.isEmpty()) {
            return y;
         } else {
            FontRenderContext frc = g.getFontRenderContext();
            if (lines.size() > maxLines) {
               lines = new ArrayList<>(lines.subList(0, maxLines));
               String last = lines.get(maxLines - 1);
               lines.set(maxLines - 1, shrinkWithEllipsis(last, font, frc, maxWidth));
            }

            for (String line : lines) {
               g.drawString(line, x, y + g.getFontMetrics().getAscent());
               y += g.getFontMetrics().getHeight() + 2;
            }

            return y + extraSpacing;
         }
      } else {
         return y;
      }
   }

   private static void drawStar(Graphics2D g, double cx, double cy, double outer, double inner, Color color) {
      Path2D star = new Double();

      for (int i = 0; i < 10; i++) {
         double angle = Math.toRadians((double)(90 + i * 36));
         double r = i % 2 == 0 ? outer : inner;
         double x = cx + r * Math.cos(angle);
         double y = cy - r * Math.sin(angle);
         if (i == 0) {
            star.moveTo(x, y);
         } else {
            star.lineTo(x, y);
         }
      }

      star.closePath();
      g.setColor(color);
      g.fill(star);
   }

   private static void pasteImage(BufferedImage base, BufferedImage overlay, int x, int y) {
      Graphics2D g = gfx(base);
      g.drawImage(overlay, x, y, null);
      g.dispose();
   }

   private static Dimension logoScaledSize(int left, int right, int maxSize) {
      BufferedImage logoImg = readResourceImage(LOGO_RES);
      if (logoImg == null) {
         return new Dimension(0, 0);
      } else {
         int lw = logoImg.getWidth();
         int lh = logoImg.getHeight();
         double limitBySize = 1.0 * (double)maxSize / (double)Math.max(lw, lh);
         double limitByWidth = (double)Math.max(1, right - left) / (double)lw;
         double scale = Math.min(Math.min(limitBySize, limitByWidth), 1.0);
         int w = (int)((double)lw * scale);
         int h = (int)((double)lh * scale);
         return new Dimension(w, h);
      }
   }

   private static Rectangle pasteLogo(BufferedImage base, int left, int right, int y, int maxSize, boolean alignCenter) {
      BufferedImage logoImg = readResourceImage(LOGO_RES);
      if (logoImg == null) {
         return new Rectangle();
      } else {
         int lw = logoImg.getWidth();
         int lh = logoImg.getHeight();
         double limitBySize = 1.0 * (double)maxSize / (double)Math.max(lw, lh);
         double limitByWidth = (double)Math.max(1, right - left) / (double)lw;
         double scale = Math.min(Math.min(limitBySize, limitByWidth), 1.0);
         int w = (int)((double)lw * scale);
         int h = (int)((double)lh * scale);
         Image scaled = logoImg.getScaledInstance(w, h, 4);
         BufferedImage copy = new BufferedImage(w, h, 2);
         Graphics2D g = gfx(copy);
         g.drawImage(scaled, 0, 0, null);
         g.dispose();
         int cx = alignCenter ? (left + right) / 2 - w / 2 : left;
         pasteImage(base, copy, cx, y);
         return new Rectangle(cx, y, w, h);
      }
   }

   private static Rectangle pasteLogo(BufferedImage base, int left, int right, int y, int maxSize) {
      return pasteLogo(base, left, right, y, maxSize, true);
   }

   private static void writeJpeg(BufferedImage img, Path out, float quality) throws IOException {
      try (OutputStream fos = Files.newOutputStream(out)) {
         writeJpeg(img, fos, quality);
      }
   }

   private static void writeJpeg(BufferedImage img, OutputStream out, float quality) throws IOException {
      BufferedImage rgb = new BufferedImage(img.getWidth(), img.getHeight(), 1);
      Graphics2D g = rgb.createGraphics();
      g.drawImage(img, 0, 0, null);
      g.dispose();
      ImageWriter writer = ImageIO.getImageWritersByFormatName("jpg").next();
      ImageWriteParam param = writer.getDefaultWriteParam();
      param.setCompressionMode(2);
      param.setCompressionQuality(quality);

      try (MemoryCacheImageOutputStream ios = new MemoryCacheImageOutputStream(out)) {
         writer.setOutput(ios);
         writer.write(null, new IIOImage(rgb, null, null), param);
         ios.flush();
      } finally {
         writer.dispose();
      }
   }

   private static BufferedImage readResourceImage(String res) {
      try {
         BufferedImage var2;
         try (InputStream in = getResourceStream(res)) {
            if (in == null) {
               return null;
            }

            var2 = ImageIO.read(in);
         }

         return var2;
      } catch (IOException var6) {
         return null;
      }
   }

   public static void generateHorizontalCard(MovieCardRenderer.MovieDetail detail, BufferedImage poster, Path outPath) throws IOException {
      generateHorizontalCard(detail, poster, outPath, resourceFonts());
   }

   public static void generateHorizontalCard(MovieCardRenderer.MovieDetail detail, BufferedImage poster, Path outPath, Path chFontPath, Path enFontPath) throws IOException {
      Font chBase = loadFont(chFontPath, 12.0F, true);
      Font enBase = loadFont(enFontPath, 12.0F, true);
      generateHorizontalCard(detail, poster, outPath, renderFonts(chBase, enBase));
   }

   public static byte[] generateHorizontalCardToBytes(MovieCardRenderer.MovieDetail detail, BufferedImage poster) throws IOException {
      return generateHorizontalCardToBytes(detail, poster, resourceFonts());
   }

   public static byte[] generateHorizontalCardToBytes(MovieCardRenderer.MovieDetail detail, BufferedImage poster, Path chFontPath, Path enFontPath) throws IOException {
      Font chBase = loadFont(chFontPath, 12.0F, true);
      Font enBase = loadFont(enFontPath, 12.0F, true);
      return generateHorizontalCardToBytes(detail, poster, renderFonts(chBase, enBase));
   }

   private static byte[] generateHorizontalCardToBytes(MovieCardRenderer.MovieDetail detail, BufferedImage poster, MovieCardRenderer.RenderFonts fonts) throws IOException {
      ByteArrayOutputStream baos = new ByteArrayOutputStream();
      generateHorizontalCardInternal(detail, poster, baos, fonts);
      return baos.toByteArray();
   }

   private static void generateHorizontalCard(MovieCardRenderer.MovieDetail detail, BufferedImage poster, Path outPath, MovieCardRenderer.RenderFonts fonts) throws IOException {
      Objects.requireNonNull(detail, "detail");
      Objects.requireNonNull(outPath, "outPath");

      try (OutputStream fos = Files.newOutputStream(outPath)) {
         generateHorizontalCardInternal(detail, poster, fos, fonts);
      }
   }

   private static void generateHorizontalCardInternal(
      MovieCardRenderer.MovieDetail detail, BufferedImage poster, OutputStream out, MovieCardRenderer.RenderFonts fonts
   ) throws IOException {
      Objects.requireNonNull(out, "out");
      boolean isLandscape = poster != null && poster.getWidth() >= poster.getHeight();
      if (isLandscape) {
         generateHorizontalCardLandscapeInternal(detail, poster, out, fonts);
      } else {
         int W = 900;
         int H = 400;
         BufferedImage base = new BufferedImage(W, H, 2);
         BufferedImage bg = poster != null ? resizeCrop(poster, W, H) : gradientBg(W, H);
         bg = blur(bg, 10);
         pasteImage(base, toRgba(bg), 0, 0);
         Graphics2D g = gfx(base);
         g.setColor(new Color(5, 5, 15, 90));
         g.fillRect(0, 0, W, H);
         int margin = 28;
         RoundRectangle2D glass = new Float((float)margin, (float)margin, (float)(W - 2 * margin), (float)(H - 2 * margin), 26.0F, 26.0F);
         g.setColor(new Color(255, 255, 255, 190));
         g.fill(glass);
         g.setColor(new Color(255, 255, 255, 70));
         g.setStroke(new BasicStroke(2.0F));
         g.draw(glass);
         Font titleFont = fonts.title();
         Font subTitleFont = fonts.subTitle();
         Font infoFont = fonts.info();
         Font smallFont = fonts.small();
         Font ratingBig = fonts.ratingBig();
         Font ratingSmall = fonts.ratingSmall();
         Color text = new Color(40, 40, 45);
         Color sub = new Color(110, 110, 120);
         Color accent = new Color(0, 170, 125);
         Color barBg = new Color(230, 230, 230);
         int innerH = H - margin * 2;
         int posterH = innerH - 50;
         int posterW = poster != null ? (int)((double)posterH * ((double)poster.getWidth() / (double)poster.getHeight())) : (int)((double)posterH * 0.7);
         BufferedImage posterRender = poster != null ? scale(poster, posterW, posterH) : plain(posterW, posterH, new Color(220, 220, 230));
         int posterX = margin + 35;
         int posterY = margin + (innerH - posterH) / 2;
         BufferedImage shadow = roundedShadow(posterW, posterH, 18);
         pasteImage(base, shadow, posterX - 11, posterY - 8);
         pasteRounded(base, posterRender, posterX, posterY, 18);
         int ratingBoxW = 210;
         int ratingBoxX = W - margin - ratingBoxW - 24;
         int textLeft = posterX + posterW + 32;
         int textRight = ratingBoxX - 24;
         int textWidth = Math.max(220, textRight - textLeft);
         int y = margin + 40;
         String titleCn = withEpisodeCode(pick(detail.title(), detail.originalTitle(), "未知标题"), seasonEpisodeLabel(detail));
         String titleEn = detail.originalTitle();
         FontRenderContext frc = g.getFontRenderContext();
         List<String> titleLines = wrapText(g, titleCn, titleFont, textWidth);
         int firstLineW = 0;
         if (!titleLines.isEmpty()) {
            firstLineW = (int)titleFont.getStringBounds(titleLines.get(0), frc).getWidth();
         }

         y = drawWrappedText(g, titleCn, titleFont, text, textLeft, y, textWidth, 3, 4);
         if (detail.success() != null) {
            int iconSize = 24;
            int iconX = textLeft + firstLineW + 10;
            int iconY = y + (g.getFontMetrics(titleFont).getAscent() - iconSize) / 2 + 4;
            drawStatusIcon(g, iconX, iconY, iconSize, detail.success());
         }

         if (titleEn != null && !titleEn.isBlank() && !titleEn.equals(titleCn)) {
            y = drawWrappedText(g, titleEn, subTitleFont, sub, textLeft, y, textWidth, 3, 6);
         } else {
            y += 10;
         }

         g.setFont(infoFont);
         g.setColor(sub);

         for (String line : baseInfo(detail)) {
            g.drawString(line, textLeft, y + g.getFontMetrics().getAscent());
            y += g.getFontMetrics().getHeight() + 4;
         }

         y += 14;
         List<String> lines = wrapText(g, defaultString(detail.overview(), "暂无简介。"), infoFont, textWidth);
         List<String> drawLines = lines.size() <= 3 ? lines : lines.subList(0, 3);
         boolean ellipsis = lines.size() > 3;
         g.setFont(infoFont);
         g.setColor(text);

         for (int i = 0; i < drawLines.size(); i++) {
            String line = drawLines.get(i);
            if (ellipsis && i == drawLines.size() - 1) {
               line = shrinkWithEllipsis(line, infoFont, frc, textWidth);
            }

            g.drawString(line, textLeft, y + g.getFontMetrics().getAscent());
            y += g.getFontMetrics().getHeight() + 2;
         }

         int brandingBottomPadding = 12;
         int logoYOffset = 6;
         Dimension logoSize = logoScaledSize(textLeft, textRight, 36);
         FontMetrics serverFm = g.getFontMetrics(smallFont);
         int brandingBaseY = (int)glass.getBounds2D().getMaxY() - brandingBottomPadding;
         int brandingBlockHeight = Math.max(logoSize.height, serverFm.getHeight());
         int brandingTop = brandingBaseY - brandingBlockHeight;
         int logoY = brandingBaseY - logoSize.height + logoYOffset;
         Rectangle logoArea = pasteLogo(base, textLeft, textRight, logoY, 36, false);
         if (detail.serverName() != null && !detail.serverName().isBlank()) {
            g.setFont(smallFont);
            g.setColor(sub);
            String serverLabel = "服务器名称：" + detail.serverName();
            int textX = logoArea.x + logoArea.width + 10;
            int maxWidth = Math.max(0, textRight - textX);
            if (maxWidth > 20) {
               String labelToDraw = serverLabel;
               if (serverFm.stringWidth(serverLabel) > maxWidth) {
                  labelToDraw = shrinkWithEllipsis(serverLabel, smallFont, frc, maxWidth);
               }

               int textY = brandingTop + brandingBlockHeight - serverFm.getDescent();
               g.drawString(labelToDraw, textX, textY);
            }
         }

         y += 10;
         RoundRectangle2D ratingGlass = new Float((float)ratingBoxX, (float)(margin + 32), (float)ratingBoxW, (float)(H - margin * 2 - 64), 20.0F, 20.0F);
         g.setColor(new Color(255, 255, 255, 200));
         g.fill(ratingGlass);
         g.setColor(new Color(220, 220, 230));
         g.draw(ratingGlass);
         int rx = ratingBoxX + 14;
         int ry = margin + 32 + 14;
         g.setFont(ratingSmall);
         g.setColor(accent);
         g.drawString("TMDB 评分", rx, ry + g.getFontMetrics().getAscent());
         ry += g.getFontMetrics().getHeight() + 6;
         g.setFont(ratingBig);
         g.setColor(text);
         String score = String.format("%.1f", detail.voteAverage());
         g.drawString(score, rx, ry + g.getFontMetrics().getAscent());
         ry += g.getFontMetrics().getHeight() + 8;
         double starVal = Math.max(0.0, Math.min(5.0, detail.voteAverage() / 2.0));
         int fullStars = (int)Math.round(starVal);

         for (int i = 0; i < 5; i++) {
            Color c = i < fullStars ? new Color(255, 170, 0) : new Color(210, 210, 210);
            drawStar(g, (double)(rx + i * 25 + 9), (double)(ry + 14), 9.0, 4.0, c);
         }

         ry += 27;
         g.setFont(smallFont);
         g.setColor(sub);
         g.drawString(detail.voteCount() + " 人评价", rx, ry + g.getFontMetrics().getAscent());
         ry += g.getFontMetrics().getHeight() + 8;
         String[] labels = new String[]{"好评", "一般", "差评"};
         double good = Math.min(Math.max((detail.voteAverage() - 5.0) / 5.0, 0.0), 1.0);
         double normal = (1.0 - good) * 0.6;
         double bad = 1.0 - good - normal;
         double[] ratios = new double[]{good, normal, bad};
         int barTotal = ratingBoxW - 28 - 40;

         for (int i = 0; i < labels.length; i++) {
            g.setFont(smallFont);
            g.setColor(sub);
            String lbl = labels[i];
            g.drawString(lbl, rx, ry + g.getFontMetrics().getAscent());
            int lw = g.getFontMetrics().stringWidth(lbl);
            int barX = rx + lw + 6;
            int barY = ry + g.getFontMetrics().getAscent() / 2 - 3;
            g.setColor(barBg);
            g.fillRoundRect(barX, barY, barTotal, 7, 6, 6);
            g.setColor(accent);
            g.fillRoundRect(barX, barY, (int)((double)barTotal * ratios[i]), 7, 6, 6);
            ry += g.getFontMetrics().getHeight() + 4;
         }

         g.setFont(smallFont);
         g.setColor(sub);
         String tag = year(detail.releaseDate()) + " · " + mainGenre(detail);
         int tagW = g.getFontMetrics().stringWidth(tag);
         int tagX = ratingBoxX + ratingBoxW - 14 - tagW;
         int tagY = (int)(ratingGlass.getBounds2D().getMaxY() - 14.0 - (double)g.getFontMetrics().getDescent());
         g.drawString(tag, tagX, tagY);
         g.dispose();
         writeJpeg(base, out, 0.78F);
      }
   }

   private static void generateHorizontalCardLandscapeInternal(
      MovieCardRenderer.MovieDetail detail, BufferedImage poster, OutputStream out, MovieCardRenderer.RenderFonts fonts
   ) throws IOException {
      int W = 900;
      int H = 500;
      BufferedImage base = new BufferedImage(W, H, 2);
      BufferedImage bg = resizeCrop(poster, W, H);
      bg = blur(bg, 10);
      pasteImage(base, toRgba(bg), 0, 0);
      Graphics2D g = gfx(base);
      g.setColor(new Color(5, 5, 15, 90));
      g.fillRect(0, 0, W, H);
      int margin = 28;
      RoundRectangle2D glass = new Float((float)margin, (float)margin, (float)(W - 2 * margin), (float)(H - 2 * margin), 26.0F, 26.0F);
      g.setColor(new Color(255, 255, 255, 190));
      g.fill(glass);
      g.setColor(new Color(255, 255, 255, 70));
      g.setStroke(new BasicStroke(2.0F));
      g.draw(glass);
      Font titleFont = fonts.title();
      Font subTitleFont = fonts.subTitle();
      Font infoFont = fonts.info();
      Font smallFont = fonts.small();
      Font ratingBig = fonts.ratingBig();
      Font ratingSmall = fonts.ratingSmall();
      Color text = new Color(40, 40, 45);
      Color sub = new Color(110, 110, 120);
      Color accent = new Color(0, 170, 125);
      Color barBg = new Color(230, 230, 230);
      int imgPadding = 16;
      int innerLeft = margin + imgPadding;
      int innerRight = W - margin - imgPadding;
      int innerWidth = innerRight - innerLeft;
      int posterTopY = margin + imgPadding;
      int maxPosterH = (int)((double)(H - 2 * margin) * 0.42);
      double imgRatio = (double)poster.getWidth() / (double)poster.getHeight();
      int posterH = (int)((double)innerWidth / imgRatio);
      if (posterH > maxPosterH) {
         posterH = maxPosterH;
      }

      BufferedImage posterRender = resizeCrop(poster, innerWidth, posterH);
      BufferedImage shadow = roundedShadow(innerWidth, posterH, 18);
      pasteImage(base, shadow, innerLeft - 11, posterTopY - 8);
      pasteRounded(base, posterRender, innerLeft, posterTopY, 18);
      int logoGapTop = posterTopY + posterH + 4;
      pasteLogo(base, innerLeft, innerRight, logoGapTop, 28, true);
      int logoGapBottom = logoGapTop + 32;
      int textLeft = innerLeft + 10;
      int ratingBoxW = 180;
      int ratingBoxX = innerRight - ratingBoxW;
      int textRight = ratingBoxX - 20;
      int textWidth = Math.max(200, textRight - textLeft);
      int y = logoGapBottom + 2;
      String titleCn = withEpisodeCode(pick(detail.title(), detail.originalTitle(), "未知标题"), seasonEpisodeLabel(detail));
      String titleEn = detail.originalTitle();
      FontRenderContext frc = g.getFontRenderContext();
      List<String> titleLines = wrapText(g, titleCn, titleFont, textWidth);
      int firstLineW = 0;
      if (!titleLines.isEmpty()) {
         firstLineW = (int)titleFont.getStringBounds(titleLines.get(0), frc).getWidth();
      }

      y = drawWrappedText(g, titleCn, titleFont, text, textLeft, y, textWidth, 3, 4);
      if (detail.success() != null) {
         int iconSize = 22;
         int iconX = textLeft + firstLineW + 10;
         int iconY = y + (g.getFontMetrics(titleFont).getAscent() - iconSize) / 2 + 4;
         drawStatusIcon(g, iconX, iconY, iconSize, detail.success());
      }

      if (titleEn != null && !titleEn.isBlank() && !titleEn.equals(titleCn)) {
         y = drawWrappedText(g, titleEn, subTitleFont, sub, textLeft, y, textWidth, 3, 4);
      } else {
         y += 6;
      }

      g.setFont(infoFont);
      g.setColor(sub);

      for (String line : baseInfo(detail)) {
         g.drawString(line, textLeft, y + g.getFontMetrics().getAscent());
         y += g.getFontMetrics().getHeight() + 2;
      }

      y += 8;
      List<String> lines = wrapText(g, defaultString(detail.overview(), "暂无简介。"), infoFont, textWidth);
      int maxOverviewLines = 3;
      List<String> drawLines = lines.size() <= maxOverviewLines ? lines : lines.subList(0, maxOverviewLines);
      boolean ellipsis = lines.size() > maxOverviewLines;
      g.setFont(infoFont);
      g.setColor(text);

      for (int i = 0; i < drawLines.size(); i++) {
         String line = drawLines.get(i);
         if (ellipsis && i == drawLines.size() - 1) {
            line = shrinkWithEllipsis(line, infoFont, frc, textWidth);
         }

         g.drawString(line, textLeft, y + g.getFontMetrics().getAscent());
         y += g.getFontMetrics().getHeight() + 2;
      }

      int ry = logoGapBottom + 2;
      g.setFont(ratingSmall);
      g.setColor(accent);
      g.drawString("TMDB 评分", ratingBoxX, ry + g.getFontMetrics().getAscent());
      ry += g.getFontMetrics().getHeight() + 4;
      g.setFont(ratingBig);
      g.setColor(text);
      String score = String.format("%.1f", detail.voteAverage());
      g.drawString(score, ratingBoxX, ry + g.getFontMetrics().getAscent());
      ry += g.getFontMetrics().getHeight() + 6;
      double starVal = Math.max(0.0, Math.min(5.0, detail.voteAverage() / 2.0));
      int fullStars = (int)Math.round(starVal);

      for (int i = 0; i < 5; i++) {
         Color c = i < fullStars ? new Color(255, 170, 0) : new Color(210, 210, 210);
         drawStar(g, (double)(ratingBoxX + i * 22 + 8), (double)(ry + 12), 8.0, 3.5, c);
      }

      ry += 24;
      g.setFont(smallFont);
      g.setColor(sub);
      g.drawString(detail.voteCount() + " 人评价", ratingBoxX, ry + g.getFontMetrics().getAscent());
      ry += g.getFontMetrics().getHeight() + 6;
      String[] labels = new String[]{"好评", "一般", "差评"};
      double good = Math.min(Math.max((detail.voteAverage() - 5.0) / 5.0, 0.0), 1.0);
      double normal = (1.0 - good) * 0.6;
      double bad = 1.0 - good - normal;
      double[] ratios = new double[]{good, normal, bad};
      int barTotal = ratingBoxW - 50;

      for (int i = 0; i < labels.length; i++) {
         g.setFont(smallFont);
         g.setColor(sub);
         String lbl = labels[i];
         g.drawString(lbl, ratingBoxX, ry + g.getFontMetrics().getAscent());
         int lw = g.getFontMetrics().stringWidth(lbl);
         int barX = ratingBoxX + lw + 6;
         int barY = ry + g.getFontMetrics().getAscent() / 2 - 3;
         g.setColor(barBg);
         g.fillRoundRect(barX, barY, barTotal, 6, 6, 6);
         g.setColor(accent);
         g.fillRoundRect(barX, barY, (int)((double)barTotal * ratios[i]), 6, 6, 6);
         ry += g.getFontMetrics().getHeight() + 3;
      }

      g.setFont(smallFont);
      g.setColor(sub);
      String tag = year(detail.releaseDate()) + " · " + mainGenre(detail);
      int tagW = g.getFontMetrics().stringWidth(tag);
      int tagX = innerRight - tagW;
      int tagY = (int)(glass.getBounds2D().getMaxY() - 14.0 - (double)g.getFontMetrics().getDescent());
      g.drawString(tag, tagX, tagY);
      g.dispose();
      writeJpeg(base, out, 0.78F);
   }

   public static void generateVerticalCard(MovieCardRenderer.MovieDetail detail, BufferedImage poster, Path outPath) throws IOException {
      generateVerticalCard(detail, poster, outPath, resourceFonts());
   }

   public static void generateVerticalCard(MovieCardRenderer.MovieDetail detail, BufferedImage poster, Path outPath, Path chFontPath, Path enFontPath) throws IOException {
      Font chBase = loadFont(chFontPath, 12.0F, true);
      Font enBase = loadFont(enFontPath, 12.0F, true);
      generateVerticalCard(detail, poster, outPath, renderFonts(chBase, enBase));
   }

   public static byte[] generateVerticalCardToBytes(MovieCardRenderer.MovieDetail detail, BufferedImage poster) throws IOException {
      return generateVerticalCardToBytes(detail, poster, resourceFonts());
   }

   public static byte[] generateVerticalCardToBytes(MovieCardRenderer.MovieDetail detail, BufferedImage poster, Path chFontPath, Path enFontPath) throws IOException {
      Font chBase = loadFont(chFontPath, 12.0F, true);
      Font enBase = loadFont(enFontPath, 12.0F, true);
      return generateVerticalCardToBytes(detail, poster, renderFonts(chBase, enBase));
   }

   private static byte[] generateVerticalCardToBytes(MovieCardRenderer.MovieDetail detail, BufferedImage poster, MovieCardRenderer.RenderFonts fonts) throws IOException {
      ByteArrayOutputStream baos = new ByteArrayOutputStream();
      generateVerticalCardInternal(detail, poster, baos, fonts);
      return baos.toByteArray();
   }

   private static void generateVerticalCard(MovieCardRenderer.MovieDetail detail, BufferedImage poster, Path outPath, MovieCardRenderer.RenderFonts fonts) throws IOException {
      Objects.requireNonNull(outPath, "outPath");

      try (OutputStream fos = Files.newOutputStream(outPath)) {
         generateVerticalCardInternal(detail, poster, fos, fonts);
      }
   }

   private static void generateVerticalCardInternal(
      MovieCardRenderer.MovieDetail detail, BufferedImage poster, OutputStream out, MovieCardRenderer.RenderFonts fonts
   ) throws IOException {
      Objects.requireNonNull(detail, "detail");
      int W = 720;
      int H = 1080;
      BufferedImage base = new BufferedImage(W, H, 2);
      BufferedImage bg = poster != null ? resizeCrop(poster, W, H) : gradientBg(W, H);
      bg = blur(bg, 10);
      pasteImage(base, toRgba(bg), 0, 0);
      Graphics2D g = gfx(base);
      g.setColor(new Color(5, 5, 15, 90));
      g.fillRect(0, 0, W, H);
      int margin = 32;
      RoundRectangle2D glass = new Float((float)margin, (float)margin, (float)(W - 2 * margin), (float)(H - 2 * margin), 26.0F, 26.0F);
      g.setColor(new Color(255, 255, 255, 190));
      g.fill(glass);
      g.setColor(new Color(255, 255, 255, 70));
      g.setStroke(new BasicStroke(2.0F));
      g.draw(glass);
      int innerLeft = margin + 40;
      int innerRight = W - margin - 40;
      int innerWidth = innerRight - innerLeft;
      Font titleFont = fonts.title();
      Font subTitleFont = fonts.subTitle();
      Font infoFont = fonts.info();
      Font smallFont = fonts.small();
      Font ratingBig = fonts.ratingBig();
      Font ratingSmall = fonts.ratingSmall();
      Color text = new Color(40, 40, 45);
      Color sub = new Color(110, 110, 120);
      Color accent = new Color(0, 170, 125);
      Color barBg = new Color(230, 230, 230);
      double ratio = poster != null ? (double)poster.getWidth() / (double)poster.getHeight() : 0.7;
      int posterW = innerWidth;
      int posterH = (int)((double)innerWidth / ratio);
      int maxPosterH = (int)((double)H * 0.45);
      if (posterH > maxPosterH) {
         posterH = maxPosterH;
         posterW = (int)((double)maxPosterH * ratio);
      }

      BufferedImage posterRender = poster != null ? scale(poster, posterW, posterH) : plain(posterW, posterH, new Color(220, 220, 230));
      int posterX = (W - posterW) / 2;
      int posterY = margin + 26;
      pasteImage(base, roundedShadow(posterW, posterH, 22), posterX - 11, posterY - 8);
      pasteRounded(base, posterRender, posterX, posterY, 22);
      int y = posterY + posterH + 28;
      y = Math.max(y, margin + 80);
      int textLeft = innerLeft;
      int textWidth = innerWidth;
      String title = pick(detail.title(), detail.originalTitle(), "未知标题");
      FontRenderContext frc = g.getFontRenderContext();
      List<String> titleLines = wrapText(g, title, titleFont, innerWidth);
      int firstLineW = 0;
      if (!titleLines.isEmpty()) {
         firstLineW = (int)titleFont.getStringBounds(titleLines.get(0), frc).getWidth();
      }

      y = drawWrappedText(g, title, titleFont, text, innerLeft, y, innerWidth, 3, 6);
      if (detail.success() != null) {
         int iconSize = 24;
         int iconX = innerLeft + firstLineW + 10;
         int iconY = y + (g.getFontMetrics(titleFont).getAscent() - iconSize) / 2 + 4;
         drawStatusIcon(g, iconX, iconY, iconSize, detail.success());
      }

      if (detail.originalTitle() != null && !detail.originalTitle().isBlank() && !detail.originalTitle().equals(detail.title())) {
         y = drawWrappedText(g, detail.originalTitle(), subTitleFont, sub, innerLeft, y, innerWidth, 3, 8);
      } else {
         y += 6;
      }

      g.setFont(infoFont);
      g.setColor(sub);

      for (String line : baseInfo(detail)) {
         g.drawString(line, textLeft, y + g.getFontMetrics().getAscent());
         y += g.getFontMetrics().getHeight() + 2;
      }

      y += 10;
      List<String> lines = wrapText(g, defaultString(detail.overview(), "暂无简介。"), infoFont, innerWidth);
      List<String> drawLines = lines.size() <= 3 ? lines : lines.subList(0, 3);
      boolean ellipsis = lines.size() > 3;
      g.setFont(infoFont);
      g.setColor(text);

      for (int i = 0; i < drawLines.size(); i++) {
         String line = drawLines.get(i);
         if (ellipsis && i == drawLines.size() - 1) {
            line = shrinkWithEllipsis(line, infoFont, frc, textWidth);
         }

         g.drawString(line, textLeft, y + g.getFontMetrics().getAscent());
         y += g.getFontMetrics().getHeight() + 2;
      }

      y += 10;
      pasteLogo(base, textLeft, innerRight, y, 32);
      y += 44;
      String[] labels = new String[]{"好评", "一般", "差评"};
      FontMetrics ratingSmallMetrics = g.getFontMetrics(ratingSmall);
      FontMetrics ratingBigMetrics = g.getFontMetrics(ratingBig);
      FontMetrics smallMetrics = g.getFontMetrics(smallFont);
      int ratingBottomLimit = H - margin - 40;
      int ratingSectionHeight = ratingSmallMetrics.getHeight()
         + 6
         + ratingBigMetrics.getHeight()
         + 10
         + 9
         + 16
         + 6
         + smallMetrics.getHeight()
         + 4
         + labels.length * (smallMetrics.getHeight() + 2);
      int maxStartY = ratingBottomLimit - ratingSectionHeight;
      if (y > maxStartY) {
         y = Math.max(maxStartY, margin + 80);
      }

      g.setFont(ratingSmall);
      g.setColor(accent);
      g.drawString("TMDB 评分", textLeft, y + g.getFontMetrics().getAscent());
      y += g.getFontMetrics().getHeight() + 6;
      g.setFont(ratingBig);
      g.setColor(text);
      String score = String.format("%.1f", detail.voteAverage());
      g.drawString(score, textLeft, y + g.getFontMetrics().getAscent());
      y += g.getFontMetrics().getHeight() + 10;
      double starVal = Math.max(0.0, Math.min(5.0, detail.voteAverage() / 2.0));
      int fullStars = (int)Math.round(starVal);

      for (int i = 0; i < 5; i++) {
         Color c = i < fullStars ? new Color(255, 140, 0) : new Color(210, 210, 210);
         drawStar(g, (double)(textLeft + i * 26 + 9), (double)(y + 16), 9.0, 4.0, c);
      }

      y += 31;
      g.setFont(smallFont);
      g.setColor(sub);
      g.drawString(detail.voteCount() + " 人评价", textLeft, y + g.getFontMetrics().getAscent());
      y += g.getFontMetrics().getHeight() + 4;
      double good = Math.min(Math.max((detail.voteAverage() - 5.0) / 5.0, 0.0), 1.0);
      double normal = (1.0 - good) * 0.6;
      double bad = 1.0 - good - normal;
      double[] ratios = new double[]{good, normal, bad};
      int barTotal = textWidth - 80;

      for (int i = 0; i < labels.length && y + 12 <= ratingBottomLimit; i++) {
         g.setFont(smallFont);
         g.setColor(sub);
         String lbl = labels[i];
         g.drawString(lbl, textLeft, y + g.getFontMetrics().getAscent());
         int lw = g.getFontMetrics().stringWidth(lbl);
         int barX = textLeft + lw + 6;
         int barY = y + g.getFontMetrics().getAscent() / 2 - 3;
         g.setColor(barBg);
         g.fillRoundRect(barX, barY, barTotal, 6, 6, 6);
         g.setColor(accent);
         g.fillRoundRect(barX, barY, (int)((double)barTotal * ratios[i]), 6, 6, 6);
         y += g.getFontMetrics().getHeight() + 2;
      }

      g.setFont(smallFont);
      g.setColor(sub);
      String tag = year(detail.releaseDate()) + " · " + mainGenre(detail);
      int tagW = g.getFontMetrics().stringWidth(tag);
      g.drawString(tag, innerRight - tagW, H - margin - 20);
      g.dispose();
      writeJpeg(base, out, 0.78F);
   }

   private static BufferedImage resizeCrop(BufferedImage src, int w, int h) {
      double rSrc = (double)src.getWidth() / (double)src.getHeight();
      double rCanvas = (double)w / (double)h;
      int newW;
      int newH;
      if (rSrc > rCanvas) {
         newH = h;
         newW = (int)((double)h * rSrc);
      } else {
         newW = w;
         newH = (int)((double)w / rSrc);
      }

      BufferedImage scaled = scale(src, newW, newH);
      int left = (newW - w) / 2;
      int top = (newH - h) / 2;
      return scaled.getSubimage(left, top, w, h);
   }

   private static BufferedImage scale(BufferedImage src, int w, int h) {
      Image tmp = src.getScaledInstance(w, h, 4);
      BufferedImage img = new BufferedImage(w, h, 1);
      Graphics2D g = gfx(img);
      g.drawImage(tmp, 0, 0, null);
      g.dispose();
      return img;
   }

   private static BufferedImage plain(int w, int h, Color c) {
      BufferedImage img = new BufferedImage(w, h, 1);
      Graphics2D g = gfx(img);
      g.setColor(c);
      g.fillRect(0, 0, w, h);
      g.dispose();
      return img;
   }

   private static BufferedImage roundedShadow(int w, int h, int radius) {
      int sw = w + 22;
      int sh = h + 22;
      BufferedImage shadow = new BufferedImage(sw, sh, 2);
      Graphics2D g = gfx(shadow);
      g.setColor(new Color(0, 0, 0, 180));
      g.fill(new Float(11.0F, 11.0F, (float)w, (float)h, (float)radius * 2.0F, (float)radius * 2.0F));
      g.dispose();
      return blur(shadow, 10);
   }

   private static void pasteRounded(BufferedImage base, BufferedImage img, int x, int y, int radius) {
      BufferedImage mask = makeRoundedCorner(img, radius);
      pasteImage(base, mask, x, y);
   }

   private static BufferedImage makeRoundedCorner(BufferedImage img, int radius) {
      BufferedImage output = new BufferedImage(img.getWidth(), img.getHeight(), 2);
      Graphics2D g = gfx(output);
      g.setComposite(AlphaComposite.Src);
      g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
      g.setColor(Color.WHITE);
      g.fill(new Float(0.0F, 0.0F, (float)img.getWidth(), (float)img.getHeight(), (float)radius * 2.0F, (float)radius * 2.0F));
      g.setComposite(AlphaComposite.SrcIn);
      g.drawImage(img, 0, 0, null);
      g.dispose();
      return output;
   }

   private static BufferedImage toRgba(BufferedImage img) {
      BufferedImage out = new BufferedImage(img.getWidth(), img.getHeight(), 2);
      Graphics2D g = gfx(out);
      g.drawImage(img, 0, 0, null);
      g.dispose();
      return out;
   }

   private static String shrinkWithEllipsis(String line, Font font, FontRenderContext frc, int maxWidth) {
      String ell = "…";
      if (font.getStringBounds(line, frc).getWidth() <= (double)maxWidth) {
         return line;
      } else if (font.getStringBounds(line + ell, frc).getWidth() <= (double)maxWidth) {
         return line + ell;
      } else {
         StringBuilder sb = new StringBuilder(line);

         while (sb.length() > 0 && font.getStringBounds("" + sb + ell, frc).getWidth() > (double)maxWidth) {
            sb.deleteCharAt(sb.length() - 1);
         }

         return sb.isEmpty() ? ell : sb + ell;
      }
   }

   private static List<String> wrapText(String text, Font font, FontRenderContext frc, int maxWidth) {
      List<String> lines = new ArrayList<>();
      if (text != null && !text.isEmpty()) {
         String[] blocks = text.split("\\n", -1);

         for (String block : blocks) {
            StringBuilder currentLine = new StringBuilder();

            for (int i = 0; i < block.length(); i++) {
               char c = block.charAt(i);
               String test = currentLine.toString() + c;
               if (font.getStringBounds(test, frc).getWidth() > (double)maxWidth) {
                  if (currentLine.length() > 0) {
                     lines.add(currentLine.toString());
                     currentLine = new StringBuilder();
                     currentLine.append(c);
                  } else {
                     lines.add(String.valueOf(c));
                  }
               } else {
                  currentLine.append(c);
               }
            }

            lines.add(currentLine.toString());
         }

         return lines;
      } else {
         return lines;
      }
   }

   private static String pick(String... vals) {
      for (String v : vals) {
         if (v != null && !v.isBlank()) {
            return v;
         }
      }

      return "";
   }

   private static String defaultString(String s, String def) {
      return s != null && !s.isBlank() ? s : def;
   }

   private static String seasonEpisodeLabel(MovieCardRenderer.MovieDetail detail) {
      if (!detail.tvSeries()) {
         return "";
      } else {
         StringBuilder sb = new StringBuilder();
         if (detail.seasonNumber() > 0) {
            sb.append(String.format("S%02d", detail.seasonNumber()));
         }

         if (detail.episodeNumber() > 0) {
            sb.append(String.format("E%02d", detail.episodeNumber()));
         }

         return sb.toString();
      }
   }

   private static String withEpisodeCode(String title, String episodeCode) {
      return episodeCode != null && !episodeCode.isBlank() ? title + " " + episodeCode : title;
   }

   private static List<String> baseInfo(MovieCardRenderer.MovieDetail d) {
      String runtime = d.runtime() > 0 ? d.runtime() + " 分钟" : "片长未知";
      String genres = d.genres() != null && !d.genres().isEmpty() ? String.join(" / ", d.genres().subList(0, Math.min(3, d.genres().size()))) : "类型未知";
      String date = d.releaseDate() != null ? d.releaseDate() : "未知";
      String countries = d.productionCountries() != null && !d.productionCountries().isEmpty()
         ? String.join(" / ", d.productionCountries().subList(0, Math.min(2, d.productionCountries().size())))
         : "国家/地区未知";
      List<String> list = new ArrayList<>();
      list.add(runtime + " / " + genres);
      list.add("上映日期：" + date);
      list.add("制片国家/地区：" + countries);
      String resourceLine = null;
      if (d.downloadCurrent() != null && !d.downloadCurrent().isBlank()) {
         resourceLine = "资源：" + d.downloadCurrent();
      }

      if (Boolean.FALSE.equals(d.success()) && d.downloadError() != null && !d.downloadError().isBlank()) {
         if (resourceLine != null) {
            resourceLine = resourceLine + " (原因：" + d.downloadError() + ")";
         } else {
            resourceLine = "原因：" + d.downloadError();
         }
      }

      if (resourceLine != null) {
         list.add(resourceLine);
      }

      return list;
   }

   private static String year(String date) {
      return date != null && date.length() >= 4 ? date.substring(0, 4) : "未知年份";
   }

   private static String mainGenre(MovieCardRenderer.MovieDetail d) {
      return d.genres() != null && !d.genres().isEmpty() ? d.genres().get(0) : "电影";
   }

   public static MovieCardRenderer.MovieCardData fetchMovieByQuery(String apiKey, String query) throws Exception {
      return fetchMovieByQuery(apiKey, query, "zh-CN");
   }

   public static MovieCardRenderer.MovieCardData fetchMovieByQuery(String apiKey, String query, String language) throws Exception {
      JSONObject search = tmdbGet(
         apiKey, "/search/movie", Map.of("query", query, "language", language == null ? "zh-CN" : language, "page", "1", "include_adult", "false")
      );
      JSONArray results = search.getJSONArray("results");
      if (results != null && !results.isEmpty()) {
         JSONObject first = results.getJSONObject(0);
         long id = first.getLongValue("id");
         return fetchMovieById(apiKey, id, language);
      } else {
         throw new IllegalStateException("TMDB 未找到影片: " + query);
      }
   }

   public static MovieCardRenderer.MovieCardData fetchMovieById(String apiKey, long movieId) throws Exception {
      return fetchMovieById(apiKey, movieId, "zh-CN");
   }

   public static MovieCardRenderer.MovieCardData fetchMovieById(String apiKey, long movieId, String language) throws Exception {
      JSONObject detail = tmdbGet(apiKey, "/movie/" + movieId, Map.of("language", language == null ? "zh-CN" : language));
      String title = detail.getString("title");
      String originalTitle = detail.getString("original_title");
      String overview = detail.getString("overview");
      int runtime = detail.getIntValue("runtime");
      List<String> genres = new ArrayList<>();
      JSONArray gArr = detail.getJSONArray("genres");
      if (gArr != null) {
         gArr.forEach(o -> {
            JSONObject jo = (JSONObject)o;
            genres.add(jo.getString("name"));
         });
      }

      String releaseDate = detail.getString("release_date");
      List<String> countries = new ArrayList<>();
      JSONArray cArr = detail.getJSONArray("production_countries");
      if (cArr != null) {
         cArr.forEach(o -> {
            JSONObject jo = (JSONObject)o;
            countries.add(jo.getString("name"));
         });
      }

      double voteAverage = detail.getDoubleValue("vote_average");
      int voteCount = detail.getIntValue("vote_count");
      String posterPath = detail.getString("poster_path");
      MovieCardRenderer.MovieDetail md = new MovieCardRenderer.MovieDetail(
         title, originalTitle, null, overview, runtime, genres, releaseDate, countries, voteAverage, voteCount, null, false, 0, 0, null, null, null, null
      );
      return new MovieCardRenderer.MovieCardData(md, posterPath);
   }

   private static JSONObject tmdbGet(String apiKey, String path, Map<String, String> params) throws Exception {
      if (apiKey != null && !apiKey.isBlank()) {
         Map<String, String> query = new LinkedHashMap<>();
         if (params != null) {
            query.putAll(params);
         }

         query.put("api_key", apiKey);
         StringBuilder url = new StringBuilder("https://api.themoviedb.org/3").append(path).append("?");
         boolean first = true;

         for (Entry<String, String> e : query.entrySet()) {
            if (!first) {
               url.append("&");
            }

            first = false;
            url.append(URLEncoder.encode(e.getKey(), StandardCharsets.UTF_8))
               .append("=")
               .append(URLEncoder.encode(e.getValue() == null ? "" : e.getValue(), StandardCharsets.UTF_8));
         }

         HttpRequest req = HttpRequest.newBuilder().uri(URI.create(url.toString())).timeout(Duration.ofSeconds(15L)).GET().build();
         HttpResponse<byte[]> resp = HTTP.send(req, BodyHandlers.ofByteArray());
         if (resp.statusCode() / 100 != 2) {
            throw new IOException("TMDB 请求失败: " + resp.statusCode() + " " + url);
         } else {
            return JSON.parseObject(resp.body());
         }
      } else {
         throw new IllegalArgumentException("TMDB apiKey 不能为空");
      }
   }

   public static BufferedImage downloadPoster(String posterPath) throws Exception {
      return posterPath != null && !posterPath.isBlank() ? downloadPosterFromUrl("https://image.tmdb.org/t/p/w500" + posterPath) : null;
   }

   public static BufferedImage downloadPosterFromUrl(String url) throws Exception {
      if (url == null || url.isBlank()) {
         return null;
      } else if (!url.startsWith("http://") && !url.startsWith("https://")) {
         return readResourceImage("img/default.jpg");
      } else {
         HttpRequest req = HttpRequest.newBuilder().uri(URI.create(url)).timeout(Duration.ofSeconds(20L)).GET().build();
         HttpResponse<byte[]> resp = HTTP.send(req, BodyHandlers.ofByteArray());
         if (resp.statusCode() != 200) {
            throw new IOException("poster download failed: " + resp.statusCode());
         } else {
            return ImageIO.read(new ByteArrayInputStream(resp.body()));
         }
      }
   }

   public static void generateHorizontalCardFromUrl(MovieCardRenderer.MovieDetail detail, String posterUrl, Path outPath) throws Exception {
      BufferedImage poster = downloadPosterFromUrl(posterUrl);
      generateHorizontalCard(detail, poster, outPath);
   }

   public static void generateHorizontalCardFromUrl(MovieCardRenderer.MovieDetail detail, String posterUrl, Path outPath, Path chFontPath, Path enFontPath) throws Exception {
      BufferedImage poster = downloadPosterFromUrl(posterUrl);
      generateHorizontalCard(detail, poster, outPath, chFontPath, enFontPath);
   }

   public static byte[] generateHorizontalCardBytesFromUrl(MovieCardRenderer.MovieDetail detail, String posterUrl) throws Exception {
      BufferedImage poster = downloadPosterFromUrl(posterUrl);
      return generateHorizontalCardToBytes(detail, poster);
   }

   public static void generateVerticalCardFromUrl(MovieCardRenderer.MovieDetail detail, String posterUrl, Path outPath) throws Exception {
      BufferedImage poster = downloadPosterFromUrl(posterUrl);
      generateVerticalCard(detail, poster, outPath);
   }

   public static void generateVerticalCardFromUrl(MovieCardRenderer.MovieDetail detail, String posterUrl, Path outPath, Path chFontPath, Path enFontPath) throws Exception {
      BufferedImage poster = downloadPosterFromUrl(posterUrl);
      generateVerticalCard(detail, poster, outPath, chFontPath, enFontPath);
   }

   public static byte[] generateVerticalCardBytesFromUrl(MovieCardRenderer.MovieDetail detail, String posterUrl) throws Exception {
      BufferedImage poster = downloadPosterFromUrl(posterUrl);
      return generateVerticalCardToBytes(detail, poster);
   }

   public static byte[] generatePlaybackCardToBytes(MovieCardRenderer.PlaybackDetail detail, BufferedImage backdrop, BufferedImage poster) throws IOException {
      ByteArrayOutputStream baos = new ByteArrayOutputStream();
      generatePlaybackCardInternal(detail, backdrop, poster, baos, resourceFonts());
      return baos.toByteArray();
   }

   public static byte[] generatePlaybackCardToBytes(MovieCardRenderer.PlaybackDetail detail, BufferedImage backdrop) throws IOException {
      return generatePlaybackCardToBytes(detail, backdrop, null);
   }

   public static byte[] generatePlaybackCardBytesFromUrl(MovieCardRenderer.PlaybackDetail detail, String backdropUrl) throws Exception {
      BufferedImage backdrop = downloadPosterFromUrl(backdropUrl);
      return generatePlaybackCardToBytes(detail, backdrop, null);
   }

   private static void generatePlaybackCardInternal(
      MovieCardRenderer.PlaybackDetail detail, BufferedImage backdrop, BufferedImage poster, OutputStream out, MovieCardRenderer.RenderFonts fonts
   ) throws IOException {
      Objects.requireNonNull(detail, "detail");
      int W = 900;
      int H = 500;
      BufferedImage base = new BufferedImage(W, H, 2);
      BufferedImage bgSource = backdrop != null ? backdrop : poster;
      BufferedImage bg = bgSource != null ? resizeCrop(bgSource, W, H) : gradientBg(W, H);
      if (backdrop == null && poster != null) {
         bg = blur(bg, 15);
      }

      pasteImage(base, toRgba(bg), 0, 0);
      Graphics2D g = gfx(base);
      GradientPaint gradient = new GradientPaint(0.0F, 0.0F, new Color(0, 0, 0, 60), 0.0F, (float)H, new Color(0, 0, 0, 220));
      g.setPaint(gradient);
      g.fillRect(0, 0, W, H);
      Font titleFont = derive(fonts.title().deriveFont(1), 48.0F);
      Font subtitleFont = derive(fonts.subTitle(), 15.0F);
      Font infoFont = derive(fonts.info(), 15.0F);
      Font buttonFont = derive(fonts.subTitle().deriveFont(1), 15.0F);
      Color textWhite = new Color(255, 255, 255);
      Color textGray = new Color(245, 245, 245);
      Color glassBg = new Color(255, 255, 255, 40);
      Color glassBorder = new Color(255, 255, 255, 90);
      int margin = 50;
      int y = H - 200;
      int posterW = 0;
      if (poster != null) {
         int posterTargetH = 280;
         int posterTargetW = (int)((double)posterTargetH * 2.0 / 3.0);
         posterW = posterTargetW;
         int posterX = W - margin - posterTargetW;
         int posterY = (H - posterTargetH) / 2;
         g.setColor(new Color(0, 0, 0, 100));
         g.fillRoundRect(posterX + 6, posterY + 6, posterTargetW, posterTargetH, 12, 12);
         BufferedImage croppedPoster = resizeCrop(poster, posterTargetW, posterTargetH);
         BufferedImage roundedPoster = makeRoundedCorner(croppedPoster, 12);
         g.drawImage(roundedPoster, posterX, posterY, null);
      }

      int contentMaxWidth = W - margin * 2 - (poster != null ? posterW + 40 : 0);
      g.setFont(titleFont);
      String title = detail.title() != null ? detail.title() : "未知标题";
      FontRenderContext frc = g.getFontRenderContext();
      List<String> titleLines = wrapText(title, titleFont, frc, contentMaxWidth);
      FontMetrics titleMetrics = g.getFontMetrics();
      int lineHeight = titleMetrics.getHeight() - 10;
      if (titleLines.size() > 1) {
         y = H - 250;
      } else {
         y = H - 220;
      }

      for (String line : titleLines) {
         g.setColor(new Color(255, 200, 100, 60));
         g.drawString(line, margin - 2, y - 2);
         g.drawString(line, margin + 2, y - 2);
         g.drawString(line, margin - 2, y + 2);
         g.drawString(line, margin + 2, y + 2);
         g.setColor(new Color(255, 220, 150, 40));
         g.drawString(line, margin - 3, y - 3);
         g.drawString(line, margin + 3, y + 3);
         GradientPaint titleGradient = new GradientPaint(
            (float)margin,
            (float)(y - titleMetrics.getHeight()),
            new Color(255, 215, 120),
            (float)(margin + titleMetrics.stringWidth(line)),
            (float)y,
            new Color(255, 255, 255)
         );
         g.setPaint(titleGradient);
         g.drawString(line, margin, y);
         y += 50;
      }

      y -= 10;
      if (detail.subtitle() != null && !detail.subtitle().isBlank()) {
         g.setFont(subtitleFont);
         g.setColor(textGray);
         String displaySubtitle = shrinkWithEllipsis(detail.subtitle(), subtitleFont, frc, contentMaxWidth);
         g.drawString(displaySubtitle, margin, y);
         y += 22;
      }

      if (detail.resolution() != null && !detail.resolution().isBlank()) {
         g.setFont(infoFont);
         g.setColor(textGray);
         g.drawString("规格：" + detail.resolution(), margin, y);
         y += 22;
      }

      if (detail.duration() != null && !detail.duration().isBlank()) {
         g.setFont(infoFont);
         g.setColor(textGray);
         g.drawString("影片时长：" + detail.duration(), margin, y);
         y += 22;
      }

      if (detail.date() != null && !detail.date().isBlank()) {
         g.setFont(infoFont);
         g.setColor(textGray);
         g.drawString("播放时间：" + detail.date(), margin, y);
         y += 22;
      }

      if (detail.genres() != null && !detail.genres().isEmpty()) {
         g.setFont(infoFont);
         g.setColor(textGray);
         String genresStr = String.join(", ", detail.genres());
         g.drawString(genresStr, margin, y);
         y += 22;
      }

      if (detail.userName() != null && !detail.userName().isBlank()) {
         g.setFont(infoFont);
         g.setColor(textGray);
         g.drawString("用户名称：" + detail.userName(), margin, y);
         y += 22;
      }

      if (detail.serverName() != null && !detail.serverName().isBlank()) {
         g.setFont(infoFont);
         g.setColor(textGray);
         g.drawString("服务器名称：" + detail.serverName(), margin, y);
      }

      String buttonText = detail.isPlaying() ? "▶ 播放" : "|| 暂停";
      g.setFont(buttonFont);
      FontMetrics fm = g.getFontMetrics();
      int btnTextWidth = fm.stringWidth(buttonText);
      int buttonPaddingX = 24;
      int buttonPaddingY = 10;
      int buttonW = btnTextWidth + buttonPaddingX * 2;
      int buttonH = fm.getHeight() + buttonPaddingY * 2;
      int buttonY = H - 65;
      g.setColor(glassBg);
      g.fillRoundRect(margin, buttonY, buttonW, buttonH, buttonH, buttonH);
      g.setColor(glassBorder);
      g.setStroke(new BasicStroke(1.5F));
      g.drawRoundRect(margin, buttonY, buttonW, buttonH, buttonH, buttonH);
      g.setColor(textWhite);
      int textX = margin + buttonPaddingX;
      int textY = buttonY + buttonPaddingY + fm.getAscent();
      g.drawString(buttonText, textX, textY);
      pasteLogo(base, W - margin - 100, W - margin, H - 55, 28, false);
      g.dispose();
      writeJpeg(base, out, 0.78F);
   }

   public static void main(String[] args) throws Exception {
      String apiKey = "your_tmdb_key";
      MovieCardRenderer.MovieCardData data = fetchMovieByQuery(apiKey, "流浪地球2");
      BufferedImage poster = downloadPosterFromUrl("https://image.tmdb.org/t/p/w500" + data.posterPath());
      generateHorizontalCard(data.detail(), poster, Path.of("out-horizontal.jpg"));
      generateVerticalCard(data.detail(), poster, Path.of("out-vertical.jpg"));
      byte[] horizontal = generateHorizontalCardBytesFromUrl(data.detail(), "https://image.tmdb.org/t/p/w500" + data.posterPath());
      byte[] vertical = generateVerticalCardBytesFromUrl(data.detail(), "https://image.tmdb.org/t/p/w500" + data.posterPath());
   }

   public static record MovieCardData(MovieCardRenderer.MovieDetail detail, String posterPath) {
   }

   public static record MovieDetail(
      String title,
      String originalTitle,
      String displayTitle,
      String overview,
      int runtime,
      List<String> genres,
      String releaseDate,
      List<String> productionCountries,
      double voteAverage,
      int voteCount,
      String serverName,
      boolean tvSeries,
      int seasonNumber,
      int episodeNumber,
      String downloadCurrent,
      String downloadStatus,
      String downloadError,
      Boolean success
   ) {
   }

   public static record PlaybackDetail(
      String title,
      String subtitle,
      String duration,
      String date,
      String resolution,
      List<String> genres,
      boolean isPlaying,
      String userName,
      String serverName
   ) {
   }

   private static record RenderFonts(Font title, Font subTitle, Font info, Font small, Font ratingBig, Font ratingSmall) {
   }
}
