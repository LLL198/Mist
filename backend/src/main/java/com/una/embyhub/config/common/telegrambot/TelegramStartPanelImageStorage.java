package com.una.embyhub.config.common.telegrambot;

import com.una.embyhub.config.common.enums.ResponseStatusEnum;
import com.una.embyhub.config.common.exception.BizException;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.AtomicMoveNotSupportedException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.nio.file.StandardOpenOption;
import java.util.Iterator;
import java.util.UUID;
import java.util.regex.Pattern;
import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageInputStream;
import javax.imageio.stream.ImageOutputStream;
import lombok.Generated;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class TelegramStartPanelImageStorage {
   @Generated
   private static final Logger log = LoggerFactory.getLogger(TelegramStartPanelImageStorage.class);
   static final long MAX_FILE_SIZE = 5242880L;
   static final long MAX_STORED_FILE_SIZE = 10485760L;
   static final long MAX_IMAGE_PIXELS = 16000000L;
   static final int MAX_DIMENSION_SUM = 10000;
   static final double MAX_ASPECT_RATIO = 20.0;
   private static final float[] JPEG_QUALITIES = new float[]{0.92F, 0.85F, 0.78F, 0.7F, 0.6F, 0.5F};
   private static final Pattern MANAGED_FILE_NAME = Pattern.compile("^start-panel-\\d+-[0-9a-f]{32}\\.(?:png|jpg)$");
   @Value("${foam.telegram.start-panel-image-dir:data/telegram/start-panel}")
   private String baseDirectory;

   public String store(Long channelId, MultipartFile file) {
      if (channelId == null || channelId <= 0L) {
         throw this.badRequest("Telegram 渠道 ID 无效");
      } else if (file == null || file.isEmpty()) {
         throw this.badRequest("请选择要上传的机器人首页图片");
      } else if (file.getSize() > 5242880L) {
         throw this.badRequest("机器人首页图片不能超过 5MB");
      } else {
         TelegramStartPanelImageStorage.DecodedImage decodedImage = this.decodeValidatedImage(file);
         BufferedImage image = decodedImage.image;
         Path directory = this.managedDirectory();
         Path temporary = null;

         String var10;
         try {
            Files.createDirectories(directory);
            temporary = Files.createTempFile(directory, ".start-panel-", ".tmp");
            String extension = this.writeCanonicalImage(decodedImage, temporary);
            String fileName = "start-panel-" + channelId + "-" + UUID.randomUUID().toString().replace("-", "") + "." + extension;
            Path target = directory.resolve(fileName).normalize();
            this.moveReplacing(temporary, target);
            var10 = fileName;
         } catch (IOException var14) {
            log.error("保存 Telegram 首页图片失败: channelId={}", channelId, var14);
            throw this.badRequest("机器人首页图片保存失败");
         } finally {
            image.flush();
            if (temporary != null) {
               this.deletePathQuietly(temporary);
            }
         }

         return var10;
      }
   }

   private TelegramStartPanelImageStorage.DecodedImage decodeValidatedImage(MultipartFile file) {
      try {
         TelegramStartPanelImageStorage.DecodedImage var14;
         try (
            InputStream input = file.getInputStream();
            ImageInputStream imageInput = ImageIO.createImageInputStream(input);
         ) {
            if (imageInput == null) {
               throw this.badRequest("仅支持有效的 PNG 或 JPEG 图片");
            }

            Iterator<ImageReader> readers = ImageIO.getImageReaders(imageInput);
            if (!readers.hasNext()) {
               throw this.badRequest("仅支持有效的 PNG 或 JPEG 图片");
            }

            ImageReader reader = readers.next();

            try {
               String format = reader.getFormatName();
               if (!"png".equalsIgnoreCase(format) && !"jpeg".equalsIgnoreCase(format) && !"jpg".equalsIgnoreCase(format)) {
                  throw this.badRequest("仅支持有效的 PNG 或 JPEG 图片");
               }

               reader.setInput(imageInput, true, true);
               int width = reader.getWidth(0);
               int height = reader.getHeight(0);
               long pixels = (long)width * (long)height;
               double aspectRatio = (double)Math.max(width, height) / (double)Math.min(width, height);
               if (width <= 0 || height <= 0 || pixels > 16000000L || width + height > 10000 || aspectRatio > 20.0) {
                  throw this.badRequest("机器人首页图片尺寸不符合 Telegram 要求");
               }

               BufferedImage image = reader.read(0);
               if (image == null) {
                  throw this.badRequest("机器人首页图片读取失败");
               }

               var14 = new TelegramStartPanelImageStorage.DecodedImage(image, "jpeg".equalsIgnoreCase(format) || "jpg".equalsIgnoreCase(format));
            } finally {
               reader.dispose();
            }
         }

         return var14;
      } catch (BizException var28) {
         throw var28;
      } catch (RuntimeException | IOException var29) {
         throw this.badRequest("机器人首页图片读取失败");
      }
   }

   private String writeCanonicalImage(TelegramStartPanelImageStorage.DecodedImage decodedImage, Path target) throws IOException {
      if (!decodedImage.jpegSource) {
         if (!ImageIO.write(decodedImage.image, "png", target.toFile())) {
            throw this.badRequest("机器人首页图片格式转换失败");
         }

         if (Files.size(target) <= 10485760L) {
            return "png";
         }
      }

      this.writeJpegWithinLimit(decodedImage.image, target);
      return "jpg";
   }

   private void writeJpegWithinLimit(BufferedImage image, Path target) throws IOException {
      BufferedImage rgbImage = new BufferedImage(image.getWidth(), image.getHeight(), 1);
      Graphics2D graphics = rgbImage.createGraphics();

      try {
         graphics.setColor(Color.WHITE);
         graphics.fillRect(0, 0, rgbImage.getWidth(), rgbImage.getHeight());
         graphics.drawImage(image, 0, 0, null);
      } finally {
         graphics.dispose();
      }

      try {
         for (float quality : JPEG_QUALITIES) {
            this.writeJpeg(rgbImage, target, quality);
            if (Files.size(target) <= 10485760L) {
               return;
            }
         }

         throw this.badRequest("机器人首页图片转换后超过 Telegram 的 10MB 限制");
      } finally {
         rgbImage.flush();
      }
   }

   private void writeJpeg(BufferedImage image, Path target, float quality) throws IOException {
      Iterator<ImageWriter> writers = ImageIO.getImageWritersByFormatName("jpeg");
      if (!writers.hasNext()) {
         throw this.badRequest("机器人首页图片格式转换失败");
      } else {
         ImageWriter writer = writers.next();

         try (
            OutputStream output = Files.newOutputStream(target, StandardOpenOption.WRITE, StandardOpenOption.TRUNCATE_EXISTING);
            ImageOutputStream imageOutput = ImageIO.createImageOutputStream(output);
         ) {
            if (imageOutput == null) {
               throw new IOException("无法创建 JPEG 输出流");
            }

            ImageWriteParam parameter = writer.getDefaultWriteParam();
            parameter.setCompressionMode(2);
            parameter.setCompressionQuality(quality);
            writer.setOutput(imageOutput);
            writer.write(null, new IIOImage(image, null, null), parameter);
         } finally {
            writer.dispose();
         }
      }
   }

   public File resolve(String configuredFileName) {
      Path path = this.resolveManagedPath(configuredFileName);
      if (path != null && Files.isRegularFile(path)) {
         try {
            return Files.size(path) > 0L ? path.toFile() : null;
         } catch (IOException var4) {
            return null;
         }
      } else {
         return null;
      }
   }

   public byte[] read(String configuredFileName) {
      Path path = this.resolveManagedPath(configuredFileName);
      if (path != null && Files.isRegularFile(path)) {
         try {
            return Files.readAllBytes(path);
         } catch (IOException var4) {
            log.error("读取 Telegram 首页图片失败: file={}", configuredFileName, var4);
            throw this.badRequest("机器人首页图片读取失败");
         }
      } else {
         throw this.badRequest("当前 Telegram 渠道未配置自定义首页图片");
      }
   }

   public void delete(String configuredFileName) {
      Path path = this.resolveManagedPath(configuredFileName);
      if (path != null) {
         this.deletePathQuietly(path);
      }
   }

   private Path managedDirectory() {
      return Path.of(this.baseDirectory).toAbsolutePath().normalize();
   }

   private Path resolveManagedPath(String configuredFileName) {
      if (configuredFileName != null && MANAGED_FILE_NAME.matcher(configuredFileName).matches()) {
         Path directory = this.managedDirectory();
         Path path = directory.resolve(configuredFileName).normalize();
         return directory.equals(path.getParent()) ? path : null;
      } else {
         return null;
      }
   }

   private void moveReplacing(Path source, Path target) throws IOException {
      try {
         Files.move(source, target, StandardCopyOption.ATOMIC_MOVE, StandardCopyOption.REPLACE_EXISTING);
      } catch (AtomicMoveNotSupportedException var4) {
         Files.move(source, target, StandardCopyOption.REPLACE_EXISTING);
      }
   }

   private void deletePathQuietly(Path path) {
      try {
         Files.deleteIfExists(path);
      } catch (IOException var3) {
         log.warn("删除 Telegram 首页图片失败: path={}, error={}", path, var3.getMessage());
      }
   }

   private BizException badRequest(String message) {
      return new BizException(ResponseStatusEnum.BAD_REQUEST.getCode(), message);
   }

   private static final class DecodedImage {
      private final BufferedImage image;
      private final boolean jpegSource;

      private DecodedImage(BufferedImage image, boolean jpegSource) {
         this.image = image;
         this.jpegSource = jpegSource;
      }
   }
}
