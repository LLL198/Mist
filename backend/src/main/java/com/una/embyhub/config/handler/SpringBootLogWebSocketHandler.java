package com.una.embyhub.config.handler;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.classic.spi.ThrowableProxyUtil;
import ch.qos.logback.core.AppenderBase;
import java.io.BufferedReader;
import java.io.IOException;
import java.net.URI;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayDeque;
import java.util.Comparator;
import java.util.Deque;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Stream;
import lombok.Generated;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

@Component
public class SpringBootLogWebSocketHandler extends TextWebSocketHandler {
   @Generated
   private static final Logger log = LoggerFactory.getLogger(SpringBootLogWebSocketHandler.class);
   private static final ZoneId DEFAULT_ZONE = ZoneId.systemDefault();
   private static final DateTimeFormatter LOG_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");
   private static final DateTimeFormatter LOG_FILE_DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
   private static final int HISTORY_RETENTION_DAYS = 3;
   private static final int DEFAULT_HISTORY_LIMIT = 500;
   private static final int MAX_HISTORY_LIMIT = 5000;
   private final Path historyDirectory;
   private final Object historyWriteLock = new Object();
   private volatile LocalDate lastCleanupDate;
   private final Map<String, WebSocketSession> sessions = new ConcurrentHashMap<>();
   private final Map<String, Level> sessionLogLevels = new ConcurrentHashMap<>();
   private final Map<String, SseEmitter> sseEmitters = new ConcurrentHashMap<>();
   private final Map<String, Level> sseLogLevels = new ConcurrentHashMap<>();
   private final Map<String, Object> sseSendLocks = new ConcurrentHashMap<>();
   private final SpringBootLogWebSocketHandler.LogWebSocketAppender logAppender;

   public SpringBootLogWebSocketHandler(@Value("${foam.log.history-dir:}") String configuredHistoryDir) {
      this.historyDirectory = this.resolveHistoryDirectory(configuredHistoryDir);
      this.logAppender = new SpringBootLogWebSocketHandler.LogWebSocketAppender();
      LoggerContext loggerContext = (LoggerContext)LoggerFactory.getILoggerFactory();
      this.logAppender.setContext(loggerContext);
      this.logAppender.start();
      ch.qos.logback.classic.Logger rootLogger = loggerContext.getLogger("ROOT");
      rootLogger.addAppender(this.logAppender);
      log.info("Spring Boot 日志历史目录: {}", this.historyDirectory.toAbsolutePath());
   }

   @Override
   public void afterConnectionEstablished(WebSocketSession session) {
      String sessionId = session.getId();
      this.sessions.put(sessionId, session);
      Level logLevel = this.getLogLevelFromSession(session);
      this.sessionLogLevels.put(sessionId, logLevel);
      log.info("新的WebSocket连接建立，会话ID: {}, 日志级别过滤: {}", sessionId, logLevel);
      this.sendMessage(sessionId, "已连接到日志服务，当前日志级别: " + logLevel);
   }

   @Override
   public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
      String sessionId = session.getId();
      this.sessions.remove(sessionId);
      this.sessionLogLevels.remove(sessionId);
      log.info("WebSocket连接关闭，会话ID: {}", sessionId);
   }

   private Level getLogLevelFromSession(WebSocketSession session) {
      try {
         URI uri = session.getUri();
         if (uri == null) {
            return Level.INFO;
         }

         String query = uri.getQuery();
         if (query == null || query.isEmpty()) {
            return Level.INFO;
         }

         Map<String, String> parameters = new ConcurrentHashMap<>();
         String[] pairs = query.split("&");

         for (String pair : pairs) {
            int idx = pair.indexOf("=");
            String key = idx > 0 ? URLDecoder.decode(pair.substring(0, idx), StandardCharsets.UTF_8.name()) : pair;
            String value = idx > 0 && pair.length() > idx + 1 ? URLDecoder.decode(pair.substring(idx + 1), StandardCharsets.UTF_8.name()) : "";
            parameters.put(key, value);
         }

         String levelParam = parameters.get("level");
         if (levelParam != null && !levelParam.isEmpty()) {
            return Level.toLevel(levelParam.toUpperCase(), Level.INFO);
         }
      } catch (Exception var13) {
         log.error("解析日志级别参数失败: {}", var13.getMessage());
      }

      return Level.INFO;
   }

   private void sendMessage(String sessionId, String message) {
      WebSocketSession session = this.sessions.get(sessionId);
      if (session != null && session.isOpen()) {
         try {
            session.sendMessage(new TextMessage(message));
         } catch (IOException var5) {
            log.error("发送消息失败: {}", var5.getMessage());
         }
      }
   }

   public SseEmitter createSseEmitter(Level level, Integer historyLimit) {
      Level resolvedLevel = level == null ? Level.INFO : level;
      int resolvedHistoryLimit = this.normalizeHistoryLimit(historyLimit);
      SseEmitter emitter = new SseEmitter(0L);
      String emitterId = UUID.randomUUID().toString();
      this.sseEmitters.put(emitterId, emitter);
      this.sseLogLevels.put(emitterId, resolvedLevel);
      this.sseSendLocks.put(emitterId, new Object());
      emitter.onCompletion(() -> this.removeSseEmitter(emitterId));
      emitter.onTimeout(() -> this.removeSseEmitter(emitterId));
      emitter.onError(ex -> this.removeSseEmitter(emitterId));
      boolean connected = this.sendSseEvent(emitterId, "connected", "已连接到日志服务，当前日志级别: " + resolvedLevel + "，历史保留天数: 3");
      if (!connected) {
         this.removeSseEmitter(emitterId);
         return emitter;
      } else {
         this.replayRecentHistoryToSse(emitterId, resolvedLevel, resolvedHistoryLimit);
         return emitter;
      }
   }

   private void removeSseEmitter(String emitterId) {
      this.sseEmitters.remove(emitterId);
      this.sseLogLevels.remove(emitterId);
      this.sseSendLocks.remove(emitterId);
   }

   private int normalizeHistoryLimit(Integer historyLimit) {
      return historyLimit != null && historyLimit > 0 ? Math.min(historyLimit, 5000) : 500;
   }

   private Path resolveHistoryDirectory(String configuredHistoryDir) {
      return configuredHistoryDir != null && !configuredHistoryDir.isBlank() ? Paths.get(configuredHistoryDir.trim()) : Paths.get("data", "log");
   }

   private boolean sendSseEvent(String emitterId, String eventName, String data) {
      SseEmitter emitter = this.sseEmitters.get(emitterId);
      Object lock = this.sseSendLocks.get(emitterId);
      if (emitter != null && lock != null) {
         try {
            synchronized (lock) {
               emitter.send(SseEmitter.event().name(eventName).data(data));
            }

            return true;
         } catch (Exception var9) {
            this.removeSseEmitter(emitterId);
            return false;
         }
      } else {
         return false;
      }
   }

   private void replayRecentHistoryToSse(String emitterId, Level minLevel, int historyLimit) {
      try {
         this.cleanupExpiredHistoryFiles(LocalDate.now(DEFAULT_ZONE));
         List<Path> historyFiles = this.listHistoryFilesWithinRetention();
         if (historyFiles.isEmpty()) {
            return;
         }

         Deque<String> recentLines = new ArrayDeque<>(historyLimit);

         for (Path historyFile : historyFiles) {
            this.collectRecentHistoryLines(historyFile, minLevel, historyLimit, recentLines);
         }

         for (String line : recentLines) {
            if (!this.sendSseEvent(emitterId, "log", line)) {
               return;
            }
         }
      } catch (Exception var8) {
      }
   }

   private void collectRecentHistoryLines(Path historyFile, Level minLevel, int historyLimit, Deque<String> recentLines) throws IOException {
      String line;
      try (BufferedReader reader = Files.newBufferedReader(historyFile, StandardCharsets.UTF_8)) {
         while ((line = reader.readLine()) != null) {
            SpringBootLogWebSocketHandler.ParsedHistoryLine parsed = this.parseHistoryLine(line);
            if (parsed != null && parsed.level().isGreaterOrEqual(minLevel)) {
               recentLines.addLast(parsed.message());
               if (recentLines.size() > historyLimit) {
                  recentLines.removeFirst();
               }
            }
         }
      }
   }

   private SpringBootLogWebSocketHandler.ParsedHistoryLine parseHistoryLine(String line) {
      if (line != null && !line.isEmpty()) {
         int firstTab = line.indexOf(9);
         int secondTab = firstTab < 0 ? -1 : line.indexOf(9, firstTab + 1);
         if (firstTab > 0 && secondTab > firstTab) {
            String levelString = line.substring(firstTab + 1, secondTab);
            String message = this.unescapeHistoryLog(line.substring(secondTab + 1));
            return new SpringBootLogWebSocketHandler.ParsedHistoryLine(Level.toLevel(levelString, Level.INFO), message);
         } else {
            return null;
         }
      } else {
         return null;
      }
   }

   private List<Path> listHistoryFilesWithinRetention() throws IOException {
      if (!Files.exists(this.historyDirectory)) {
         return List.of();
      } else {
         LocalDate minDate = LocalDate.now(DEFAULT_ZONE).minusDays(2L);

         List var3;
         try (Stream<Path> stream = Files.list(this.historyDirectory)) {
            var3 = stream.filter(x$0 -> Files.isRegularFile(x$0))
               .filter(path -> this.isWithinRetention(path, minDate))
               .sorted(Comparator.comparing(path -> path.getFileName().toString()))
               .toList();
         }

         return var3;
      }
   }

   private boolean isWithinRetention(Path path, LocalDate minDate) {
      LocalDate fileDate = this.parseHistoryFileDate(path);
      return fileDate != null && !fileDate.isBefore(minDate);
   }

   private LocalDate parseHistoryFileDate(Path path) {
      String filename = path.getFileName().toString();
      if (!filename.endsWith(".log")) {
         return null;
      } else {
         String datePart = filename.substring(0, filename.length() - 4);

         try {
            return LocalDate.parse(datePart, LOG_FILE_DATE_FORMATTER);
         } catch (Exception var5) {
            return null;
         }
      }
   }

   private void persistHistoryLog(ILoggingEvent event, String formattedLog) {
      try {
         long timestamp = event.getTimeStamp();
         LocalDate eventDate = Instant.ofEpochMilli(timestamp).atZone(DEFAULT_ZONE).toLocalDate();
         String fileName = eventDate.format(LOG_FILE_DATE_FORMATTER) + ".log";
         String cleanLog = this.escapeHistoryLog(formattedLog);
         String line = timestamp + "\t" + event.getLevel() + "\t" + cleanLog + System.lineSeparator();
         synchronized (this.historyWriteLock) {
            Files.createDirectories(this.historyDirectory);
            Files.writeString(
               this.historyDirectory.resolve(fileName),
               line,
               StandardCharsets.UTF_8,
               StandardOpenOption.CREATE,
               StandardOpenOption.WRITE,
               StandardOpenOption.APPEND
            );
            this.cleanupExpiredHistoryFiles(eventDate);
         }
      } catch (Exception var12) {
      }
   }

   private void cleanupExpiredHistoryFiles(LocalDate nowDate) throws IOException {
      if (nowDate != null) {
         LocalDate lastDate = this.lastCleanupDate;
         if (!Objects.equals(lastDate, nowDate)) {
            synchronized (this.historyWriteLock) {
               if (!Objects.equals(this.lastCleanupDate, nowDate)) {
                  if (Files.exists(this.historyDirectory)) {
                     LocalDate minDate = nowDate.minusDays(2L);

                     try (Stream<Path> stream = Files.list(this.historyDirectory)) {
                        stream.filter(x$0 -> Files.isRegularFile(x$0)).forEach(path -> {
                           LocalDate fileDate = this.parseHistoryFileDate(path);
                           if (fileDate != null && fileDate.isBefore(minDate)) {
                              try {
                                 Files.deleteIfExists(path);
                              } catch (IOException var5x) {
                              }
                           }
                        });
                     }
                  }

                  this.lastCleanupDate = nowDate;
               }
            }
         }
      }
   }

   private String formatLogLine(ILoggingEvent event) {
      String timestamp = Instant.ofEpochMilli(event.getTimeStamp()).atZone(DEFAULT_ZONE).toLocalDateTime().format(LOG_TIME_FORMATTER);
      StringBuilder builder = new StringBuilder(
         String.format("[%s] [%s] [%s] %s", timestamp, event.getLevel(), event.getLoggerName(), event.getFormattedMessage())
      );
      if (event.getThrowableProxy() != null) {
         String stackTrace = ThrowableProxyUtil.asString(event.getThrowableProxy());
         if (StringUtils.hasText(stackTrace)) {
            builder.append(System.lineSeparator()).append(stackTrace.stripTrailing());
         }
      }

      return builder.toString();
   }

   private String escapeHistoryLog(String message) {
      if (message != null && !message.isEmpty()) {
         StringBuilder escaped = new StringBuilder(message.length() + 16);

         for (int i = 0; i < message.length(); i++) {
            char ch = message.charAt(i);
            if (ch == '\\') {
               escaped.append("\\\\");
            } else if (ch == '\r') {
               escaped.append("\\r");
            } else if (ch == '\n') {
               escaped.append("\\n");
            } else {
               escaped.append(ch);
            }
         }

         return escaped.toString();
      } else {
         return "";
      }
   }

   private String unescapeHistoryLog(String message) {
      if (message != null && !message.isEmpty()) {
         StringBuilder unescaped = new StringBuilder(message.length());

         for (int i = 0; i < message.length(); i++) {
            char ch = message.charAt(i);
            if (ch == '\\' && i + 1 < message.length()) {
               char next = message.charAt(++i);
               if (next == '\\') {
                  unescaped.append('\\');
               } else if (next == 'n') {
                  unescaped.append('\n');
               } else if (next == 'r') {
                  unescaped.append('\r');
               } else {
                  unescaped.append('\\').append(next);
               }
            } else {
               unescaped.append(ch);
            }
         }

         return unescaped.toString();
      } else {
         return "";
      }
   }

   private class LogWebSocketAppender extends AppenderBase<ILoggingEvent> {
      protected void append(ILoggingEvent event) {
         String formattedLog = SpringBootLogWebSocketHandler.this.formatLogLine(event);
         SpringBootLogWebSocketHandler.this.persistHistoryLog(event, formattedLog);
         SpringBootLogWebSocketHandler.this.sessions.keySet().forEach(sessionId -> {
            Level sessionLevel = SpringBootLogWebSocketHandler.this.sessionLogLevels.getOrDefault(sessionId, Level.INFO);
            if (event.getLevel().isGreaterOrEqual(sessionLevel)) {
               SpringBootLogWebSocketHandler.this.sendMessage(sessionId, formattedLog);
            }
         });
         SpringBootLogWebSocketHandler.this.sseEmitters.forEach((emitterId, ignored) -> {
            Level sseLevel = SpringBootLogWebSocketHandler.this.sseLogLevels.getOrDefault(emitterId, Level.INFO);
            if (event.getLevel().isGreaterOrEqual(sseLevel)) {
               SpringBootLogWebSocketHandler.this.sendSseEvent(emitterId, "log", formattedLog);
            }
         });
      }
   }

   private static record ParsedHistoryLine(Level level, String message) {
   }
}
