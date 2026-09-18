package com.una.embyhub.service.impl;

import com.alibaba.fastjson2.JSONObject;
import com.una.embyhub.config.common.enums.ResponseStatusEnum;
import com.una.embyhub.config.common.exception.BizException;
import com.una.embyhub.config.common.utils.ConfigCacheLoaderUtils;
import com.una.embyhub.config.common.utils.EmbyInfoCacheManagerUtils;
import com.una.embyhub.model.dto.request.foammigration.FoamDataMigrationRequest;
import com.una.embyhub.model.dto.response.foammigration.FoamDataMigrationConnectionResponse;
import com.una.embyhub.model.dto.response.foammigration.FoamDataMigrationPlanResponse;
import com.una.embyhub.model.dto.response.foammigration.FoamDataMigrationProgressResponse;
import com.una.embyhub.model.dto.response.foammigration.FoamDataMigrationResultResponse;
import com.una.embyhub.model.dto.response.foammigration.FoamDataMigrationTableResultResponse;
import com.una.embyhub.service.FoamDataMigrationService;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.function.Consumer;
import java.util.regex.Pattern;
import javax.sql.DataSource;
import lombok.Generated;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class FoamDataMigrationServiceImpl implements FoamDataMigrationService {
   @Generated
   private static final Logger log = LoggerFactory.getLogger(FoamDataMigrationServiceImpl.class);
   private static final int DEFAULT_BATCH_SIZE = 1000;
   private static final int MIN_BATCH_SIZE = 50;
   private static final int MAX_BATCH_SIZE = 5000;
   private static final String DEFAULT_DATABASE_NAME = "foam-api";
   private static final long PROGRESS_EMIT_INTERVAL_MILLIS = 300L;
   private static final Pattern SAFE_IDENTIFIER = Pattern.compile("[A-Za-z0-9_]+");
   private static final List<String> SUPPORTED_TABLES = List.of(
      "emby_info",
      "emby_user",
      "host_line",
      "emby_user_register_record",
      "emby_user_renew_record",
      "user_oauth_binding",
      "user_points",
      "request_list",
      "sys_notice",
      "system_config"
   );
   private static final String SYSTEM_CONFIG_TABLE = "system_config";
   private static final String KEY_REGISTERED_USER = "registered_user";
   private static final String KEY_SELF_SERVICE_REGISTER_EMBY_INFO_ID = "self_service_register_emby_info_id";
   private static final String KEY_TELEGRAM_BOT_REGISTER_ENABLED = "telegram_bot_register_enabled";
   private static final String KEY_TELEGRAM_BOT_REGISTER_MAX_COUNT = "telegram_bot_register_max_count";
   private static final String KEY_TELEGRAM_BOT_REGISTER_USED_COUNT = "telegram_bot_register_used_count";
   private static final String KEY_TELEGRAM_BOT_REGISTER_DEFAULT_DAYS = "telegram_bot_register_default_days";
   private static final String KEY_TELEGRAM_REQUEST_POINTS_CONFIG = "telegram_request_points_config";
   private static final String KEY_EMBY_ACTIVE_ACCOUNT_PROTECTION = "emby_active_account_protection";
   private static final Set<String> REMOVED_SYSTEM_CONFIG_KEYS = Set.of(
      "telegram_bot_register_max_count", "telegram_bot_register_used_count", "telegram_bot_register_default_days", "license_admin_license_code"
   );
   private static final Set<String> SENSITIVE_SYSTEM_CONFIG_KEYS = Set.of("rose_admin_password");
   private final DataSource dataSource;
   private final ConfigCacheLoaderUtils configCacheLoaderUtils;
   private final EmbyInfoCacheManagerUtils embyInfoCacheManagerUtils;

   public FoamDataMigrationServiceImpl(
      DataSource dataSource, ConfigCacheLoaderUtils configCacheLoaderUtils, EmbyInfoCacheManagerUtils embyInfoCacheManagerUtils
   ) {
      this.dataSource = dataSource;
      this.configCacheLoaderUtils = configCacheLoaderUtils;
      this.embyInfoCacheManagerUtils = embyInfoCacheManagerUtils;
   }

   @Override
   public FoamDataMigrationPlanResponse plan() {
      FoamDataMigrationPlanResponse response = new FoamDataMigrationPlanResponse();
      response.setScope("Mist 2.0.7 兼容核心表补写迁移（不清空当前库，不包含 flyway_schema_history）");
      response.setTableCount(SUPPORTED_TABLES.size());
      response.setTables(SUPPORTED_TABLES);
      return response;
   }

   @Override
   public FoamDataMigrationConnectionResponse testConnection(FoamDataMigrationRequest request) {
      List<String> migrationTables = this.resolveMigrationTables(request);

      try {
         FoamDataMigrationConnectionResponse var13;
         try (Connection sourceConnection = this.openSourceConnection(request)) {
            DatabaseMetaData metaData = sourceConnection.getMetaData();
            List<String> availableTables = new ArrayList<>();
            List<String> missingTables = new ArrayList<>();

            for (String tableName : migrationTables) {
               if (this.tableExists(sourceConnection, tableName)) {
                  availableTables.add(tableName);
               } else {
                  missingTables.add(tableName);
               }
            }

            FoamDataMigrationConnectionResponse response = new FoamDataMigrationConnectionResponse();
            response.setProductName(metaData.getDatabaseProductName());
            response.setProductVersion(metaData.getDatabaseProductVersion());
            response.setCatalog(sourceConnection.getCatalog());
            response.setSupportedTableCount(migrationTables.size());
            response.setAvailableTables(availableTables);
            response.setMissingTables(missingTables);
            response.setAvailableTableCount(availableTables.size());
            response.setMissingTableCount(missingTables.size());
            var13 = response;
         }

         return var13;
      } catch (SQLException var11) {
         throw new BizException(ResponseStatusEnum.BAD_REQUEST.getCode(), this.safeSqlMessage("旧库连接失败", var11));
      }
   }

   @Override
   public FoamDataMigrationResultResponse sync(FoamDataMigrationRequest request) {
      return this.sync(request, null);
   }

   @Override
   public FoamDataMigrationResultResponse sync(FoamDataMigrationRequest request, Consumer<FoamDataMigrationProgressResponse> progressConsumer) {
      long startedAt = System.currentTimeMillis();
      int batchSize = this.resolveBatchSize(request.getBatchSize());
      List<String> migrationTables = this.resolveMigrationTables(request);
      FoamDataMigrationServiceImpl.ProgressEmitter progressEmitter = new FoamDataMigrationServiceImpl.ProgressEmitter(progressConsumer);
      FoamDataMigrationServiceImpl.MigrationProgressTracker progressTracker = new FoamDataMigrationServiceImpl.MigrationProgressTracker(
         startedAt, progressEmitter, migrationTables
      );
      progressEmitter.send(
         this.buildProgress(migrationTables, "RUNNING", "CONNECTING", 0, 0, 0, 0, 0, 0, null, 0L, 0L, 0L, startedAt, "正在连接旧 Mist 数据库...", null), true
      );

      try (
         Connection sourceConnection = this.openSourceConnection(request);
         Connection ignored = this.dataSource.getConnection();
      ) {
         this.trySetReadOnly(sourceConnection);
         progressEmitter.send(
            this.buildProgress(migrationTables, "RUNNING", "PREPARING", 0, 0, 0, 0, 0, 0, null, 0L, 0L, 0L, startedAt, "连接成功，正在按兼容顺序准备核心表补写迁移...", null), true
         );
      } catch (SQLException var17) {
         progressTracker.sendFailed(this.safeSqlMessage("数据库迁移失败", var17));
         throw new BizException(ResponseStatusEnum.BAD_REQUEST.getCode(), this.safeSqlMessage("数据库迁移失败", var17));
      }

      this.runSequentialMigration(request, batchSize, migrationTables, progressTracker);
      boolean cacheRefreshed = this.refreshRuntimeCaches(progressEmitter, migrationTables, startedAt, progressTracker);
      List<FoamDataMigrationTableResultResponse> tableResults = progressTracker.orderedResults();
      FoamDataMigrationResultResponse result = this.buildResult(tableResults, migrationTables.size(), System.currentTimeMillis() - startedAt);
      progressEmitter.send(
         this.buildProgress(
            migrationTables,
            "COMPLETED",
            "COMPLETED",
            100,
            result.getTableCount(),
            0,
            result.getSyncedTableCount(),
            result.getSkippedTableCount(),
            result.getFailedTableCount(),
            null,
            0L,
            0L,
            result.getTotalSyncedRows(),
            startedAt,
            cacheRefreshed ? "旧 Mist 核心数据迁移完成，运行缓存已刷新" : "旧 Mist 核心数据迁移完成，但运行缓存刷新失败，请稍后手动刷新或重启服务",
            result
         ),
         true
      );
      return result;
   }

   private void runSequentialMigration(
      FoamDataMigrationRequest request, int batchSize, List<String> migrationTables, FoamDataMigrationServiceImpl.MigrationProgressTracker progressTracker
   ) {
      for (int index = 0; index < migrationTables.size(); index++) {
         String tableName = migrationTables.get(index);
         String tableProgressText = "第 " + (index + 1) + "/" + migrationTables.size() + " 张表";
         int tableIndex = index;
         progressTracker.sendRunning(tableIndex, 0L, 0L, "正在处理" + tableProgressText + "，按兼容顺序补写...", true);
         FoamDataMigrationTableResultResponse result = this.syncTableSafely(
            request,
            tableName,
            tableProgressText,
            batchSize,
            (sourceRows, syncedRows, message, force) -> progressTracker.sendRunning(tableIndex, sourceRows, syncedRows, message, force)
         );
         progressTracker.finish(tableIndex, result, tableProgressText);
         if ("FAILED".equals(result.getStatus())) {
            progressTracker.sendFailed(result.getMessage());
            throw new BizException(ResponseStatusEnum.BAD_REQUEST.getCode(), result.getMessage());
         }
      }
   }

   private boolean refreshRuntimeCaches(
      FoamDataMigrationServiceImpl.ProgressEmitter progressEmitter,
      List<String> migrationTables,
      long startedAt,
      FoamDataMigrationServiceImpl.MigrationProgressTracker progressTracker
   ) {
      progressEmitter.send(
         this.buildProgress(
            migrationTables,
            "RUNNING",
            "REFRESHING_CACHE",
            98,
            progressTracker.processedTablesSnapshot(),
            0,
            progressTracker.countStatusSnapshot("SYNCED"),
            progressTracker.countSkippedSnapshot(),
            progressTracker.countStatusSnapshot("FAILED"),
            null,
            0L,
            0L,
            progressTracker.totalSyncedRowsSnapshot(),
            startedAt,
            "正在刷新 system_config 与 Emby 服务器运行缓存...",
            null
         ),
         true
      );

      try {
         this.configCacheLoaderUtils.refreshCache();
         this.embyInfoCacheManagerUtils.refresh();
         return true;
      } catch (RuntimeException var7) {
         log.warn("Mist 旧库迁移完成后刷新运行缓存失败：{}", var7.getMessage());
         progressEmitter.send(
            this.buildProgress(
               migrationTables,
               "RUNNING",
               "REFRESHING_CACHE",
               98,
               progressTracker.processedTablesSnapshot(),
               0,
               progressTracker.countStatusSnapshot("SYNCED"),
               progressTracker.countSkippedSnapshot(),
               progressTracker.countStatusSnapshot("FAILED"),
               null,
               0L,
               0L,
               progressTracker.totalSyncedRowsSnapshot(),
               startedAt,
               "数据已写入，但运行缓存刷新失败，请稍后手动刷新或重启服务",
               null
            ),
            true
         );
         return false;
      }
   }

   private FoamDataMigrationTableResultResponse syncTableSafely(
      FoamDataMigrationRequest request,
      String tableName,
      String tableProgressText,
      int batchSize,
      FoamDataMigrationServiceImpl.TableProgressConsumer progressConsumer
   ) {
      Connection targetConnection = null;

      FoamDataMigrationTableResultResponse var9;
      try (Connection sourceConnection = this.openSourceConnection(request)) {
         this.trySetReadOnly(sourceConnection);
         targetConnection = this.dataSource.getConnection();
         targetConnection.setAutoCommit(false);
         this.setForeignKeyChecks(targetConnection, false);
         FoamDataMigrationTableResultResponse result = this.syncTable(
            sourceConnection, targetConnection, tableName, tableProgressText, batchSize, progressConsumer
         );
         targetConnection.commit();
         return result;
      } catch (RuntimeException | SQLException var17) {
         this.rollbackQuietly(targetConnection);
         log.warn("Mist 旧库表 {} 同步失败：{}", tableName, var17.getMessage());
         FoamDataMigrationTableResultResponse resultx = this.newTableResult(tableName);
         resultx.setStatus("FAILED");
         resultx.setMessage(this.safeSqlMessage("表同步失败", var17));
         if (progressConsumer != null) {
            progressConsumer.accept(0L, 0L, resultx.getMessage(), true);
         }

         var9 = resultx;
      } finally {
         this.restoreForeignKeyChecks(targetConnection);
         this.resetAutoCommit(targetConnection);
         this.closeQuietly(targetConnection);
      }

      return var9;
   }

   private FoamDataMigrationTableResultResponse syncTable(
      Connection sourceConnection,
      Connection targetConnection,
      String tableName,
      String tableProgressText,
      int batchSize,
      FoamDataMigrationServiceImpl.TableProgressConsumer progressConsumer
   ) throws SQLException {
      FoamDataMigrationTableResultResponse result = this.newTableResult(tableName);
      if (!this.tableExists(sourceConnection, tableName)) {
         result.setStatus("SKIPPED");
         result.setMessage("旧库缺少该表");
         return result;
      } else if (!this.tableExists(targetConnection, tableName)) {
         result.setStatus("SKIPPED");
         result.setMessage("当前库缺少该表");
         return result;
      } else if ("system_config".equals(tableName)) {
         return this.syncSystemConfigTable(sourceConnection, targetConnection, tableProgressText, progressConsumer);
      } else {
         List<String> columns = this.commonColumns(sourceConnection, targetConnection, tableName);
         if (columns.isEmpty()) {
            result.setStatus("SKIPPED");
            result.setMessage("旧库与当前库没有可同步的共同字段");
            return result;
         } else {
            long sourceRows = this.countRows(sourceConnection, tableName);
            result.setSourceRows(sourceRows);
            if (progressConsumer != null) {
               progressConsumer.accept(sourceRows, 0L, "正在读取" + tableProgressText + "，共 " + sourceRows + " 行", true);
            }

            if (sourceRows == 0L) {
               result.setStatus("EMPTY");
               result.setMessage("旧库无数据，当前库未变更");
               return result;
            } else {
               long syncedRows = this.copyRows(
                  sourceConnection, targetConnection, tableName, tableProgressText, columns, batchSize, sourceRows, progressConsumer
               );
               this.afterTableSynced(targetConnection, tableName);
               result.setStatus("SYNCED");
               result.setSyncedRows(syncedRows);
               result.setMessage("补写完成");
               if (progressConsumer != null) {
                  progressConsumer.accept(sourceRows, syncedRows, tableProgressText + "补写完成，新插入 " + syncedRows + " 行", true);
               }

               return result;
            }
         }
      }
   }

   private long copyRows(
      Connection sourceConnection,
      Connection targetConnection,
      String tableName,
      String tableProgressText,
      List<String> columns,
      int batchSize,
      long sourceRows,
      FoamDataMigrationServiceImpl.TableProgressConsumer progressConsumer
   ) throws SQLException {
      String selectSql = this.buildSelectSql(tableName, columns);
      String insertSql = this.buildInsertSql(tableName, columns);
      long syncedRows = 0L;
      long readRows = 0L;
      int pendingRows = 0;

      try (
         PreparedStatement selectStatement = sourceConnection.prepareStatement(selectSql, 1003, 1007);
         PreparedStatement insertStatement = targetConnection.prepareStatement(insertSql);
      ) {
         selectStatement.setFetchSize(batchSize);

         try (ResultSet resultSet = selectStatement.executeQuery()) {
            while (resultSet.next()) {
               for (int index = 0; index < columns.size(); index++) {
                  insertStatement.setObject(index + 1, resultSet.getObject(index + 1));
               }

               insertStatement.addBatch();
               readRows++;
               if (++pendingRows >= batchSize) {
                  syncedRows += this.countAffectedRows(insertStatement.executeBatch());
                  if (progressConsumer != null) {
                     progressConsumer.accept(
                        sourceRows, syncedRows, "正在补写" + tableProgressText + "，已读取 " + readRows + "/" + sourceRows + " 行，新插入 " + syncedRows + " 行", false
                     );
                  }

                  pendingRows = 0;
               }
            }
         }

         if (pendingRows > 0) {
            syncedRows += this.countAffectedRows(insertStatement.executeBatch());
            if (progressConsumer != null) {
               progressConsumer.accept(
                  sourceRows, syncedRows, "正在补写" + tableProgressText + "，已读取 " + readRows + "/" + sourceRows + " 行，新插入 " + syncedRows + " 行", true
               );
            }
         }
      }

      return syncedRows;
   }

   private FoamDataMigrationTableResultResponse syncSystemConfigTable(
      Connection sourceConnection, Connection targetConnection, String tableProgressText, FoamDataMigrationServiceImpl.TableProgressConsumer progressConsumer
   ) throws SQLException {
      FoamDataMigrationTableResultResponse result = this.newTableResult("system_config");
      List<String> columns = this.commonColumns(sourceConnection, targetConnection, "system_config");
      if (!this.containsColumn(columns, "config_key")) {
         result.setStatus("SKIPPED");
         result.setMessage("system_config 缺少 config_key 字段，无法按配置键合并");
         return result;
      } else {
         List<FoamDataMigrationServiceImpl.SystemConfigRow> sourceRows = this.loadSystemConfigRows(sourceConnection, columns);
         Map<String, FoamDataMigrationServiceImpl.SystemConfigRow> sourceByKey = this.rowsByKey(sourceRows, false);
         List<String> targetColumns = this.queryColumns(targetConnection, "system_config");
         Map<String, FoamDataMigrationServiceImpl.SystemConfigRow> targetByKey = this.rowsByKey(
            this.loadSystemConfigRows(targetConnection, targetColumns), true
         );
         long sourceRowCount = (long)sourceRows.size();
         result.setSourceRows(sourceRowCount);
         if (progressConsumer != null) {
            progressConsumer.accept(sourceRowCount, 0L, "正在按 config_key 合并" + tableProgressText + "，共 " + sourceRowCount + " 项配置", true);
         }

         if (sourceRows.isEmpty()) {
            result.setStatus("EMPTY");
            result.setMessage("旧库无配置数据，当前库未变更");
            return result;
         } else {
            String selfServiceServerId = this.resolveSelfServiceServerId(sourceByKey, targetConnection);
            long changedRows = 0L;
            long processedRows = 0L;

            for (FoamDataMigrationServiceImpl.SystemConfigRow sourceRow : sourceByKey.values()) {
               String configKey = sourceRow.configKey();
               if (!REMOVED_SYSTEM_CONFIG_KEYS.contains(configKey)) {
                  Map<String, Object> values = new HashMap<>(sourceRow.values());
                  this.normalizeSystemConfigValues(values, sourceByKey, selfServiceServerId);
                  changedRows += this.upsertSystemConfigRow(targetConnection, targetColumns, targetByKey, values);
                  processedRows++;
                  if (progressConsumer != null) {
                     progressConsumer.accept(
                        sourceRowCount,
                        changedRows,
                        "正在合并" + tableProgressText + "，已处理 " + processedRows + "/" + sourceByKey.size() + " 项配置，变更 " + changedRows + " 行",
                        false
                     );
                  }
               }
            }

            changedRows += this.ensureCompatibleSystemConfigRows(targetConnection, targetColumns, targetByKey, sourceByKey, selfServiceServerId);
            result.setStatus("SYNCED");
            result.setSyncedRows(changedRows);
            result.setMessage("system_config 已按 config_key 兼容合并");
            if (progressConsumer != null) {
               progressConsumer.accept(sourceRowCount, changedRows, tableProgressText + "合并完成，变更 " + changedRows + " 行", true);
            }

            return result;
         }
      }
   }

   private List<FoamDataMigrationServiceImpl.SystemConfigRow> loadSystemConfigRows(Connection connection, List<String> columns) throws SQLException {
      List<FoamDataMigrationServiceImpl.SystemConfigRow> rows = new ArrayList<>();
      String selectSql = this.buildSelectSql("system_config", columns);

      try (
         PreparedStatement statement = connection.prepareStatement(selectSql);
         ResultSet resultSet = statement.executeQuery();
      ) {
         while (resultSet.next()) {
            Map<String, Object> values = new HashMap<>();

            for (int index = 0; index < columns.size(); index++) {
               values.put(this.normalizeColumn(columns.get(index)), resultSet.getObject(index + 1));
            }

            String configKey = this.textValue(values.get("config_key"));
            if (StringUtils.hasText(configKey)) {
               values.put("config_key", configKey);
               rows.add(new FoamDataMigrationServiceImpl.SystemConfigRow(configKey, values));
            }
         }
      }

      return rows;
   }

   private Map<String, FoamDataMigrationServiceImpl.SystemConfigRow> rowsByKey(List<FoamDataMigrationServiceImpl.SystemConfigRow> rows, boolean keepFirst) {
      Map<String, FoamDataMigrationServiceImpl.SystemConfigRow> result = new LinkedHashMap<>();

      for (FoamDataMigrationServiceImpl.SystemConfigRow row : rows) {
         if (keepFirst) {
            result.putIfAbsent(row.configKey(), row);
         } else {
            result.put(row.configKey(), row);
         }
      }

      return result;
   }

   private void normalizeSystemConfigValues(
      Map<String, Object> values, Map<String, FoamDataMigrationServiceImpl.SystemConfigRow> sourceByKey, String selfServiceServerId
   ) {
      String configKey = this.textValue(values.get("config_key"));
      if ("registered_user".equals(configKey)) {
         boolean disabled = this.isLegacyFalse(this.textValue(values.get("config_value")));
         values.put("config_value", "true");
         values.put("name", "开启页面注册");
         values.put("description", "是否在登录页开放普通账号注册入口");
         values.put("is_update", 1);
         if (disabled) {
            values.put("is_enabled", 0);
         } else if (values.get("is_enabled") == null) {
            values.put("is_enabled", 1);
         }
      } else if ("self_service_register_emby_info_id".equals(configKey)) {
         if (!StringUtils.hasText(this.textValue(values.get("config_value")))) {
            values.put("config_value", selfServiceServerId);
         }

         values.put("name", "自助注册绑定服务器");
         values.put("description", "页面自助注册默认写入的 Emby 服务器 ID");
         values.put("is_update", 1);
         values.putIfAbsent("is_enabled", 1);
      } else if ("telegram_bot_register_enabled".equals(configKey)) {
         values.put("config_value", this.normalizeTelegramBotRegisterConfig(sourceByKey));
         values.put("description", "Telegram 机器人注册配置，JSON 格式保存默认天数、最大次数和已用次数");
         values.put("is_update", 1);
         values.putIfAbsent("is_enabled", 0);
      } else if ("telegram_request_points_config".equals(configKey)) {
         values.put("config_value", this.normalizeTelegramRequestPointsConfig(this.textValue(values.get("config_value")), false));
         values.put("name", "Telegram求片积分配置");
         values.put("description", "Telegram 群内求片扣积分配置，JSON 格式保存开关、免费次数和扣分规则");
         values.put("is_update", 1);
         values.putIfAbsent("is_enabled", 1);
      } else if ("emby_active_account_protection".equals(configKey)) {
         values.put("config_value", this.normalizeEmbyActiveAccountProtectionConfig(this.textValue(values.get("config_value"))));
         values.put("name", "Emby活跃账号保护配置");
         values.put("description", "JSON格式：指定服务器ID、活跃检测天数、封禁保留天数和是否自动删除");
         values.put("is_update", 1);
         values.putIfAbsent("is_enabled", 0);
      }
   }

   private long ensureCompatibleSystemConfigRows(
      Connection targetConnection,
      List<String> targetColumns,
      Map<String, FoamDataMigrationServiceImpl.SystemConfigRow> targetByKey,
      Map<String, FoamDataMigrationServiceImpl.SystemConfigRow> sourceByKey,
      String selfServiceServerId
   ) throws SQLException {
      long changedRows = 0L;
      if (StringUtils.hasText(selfServiceServerId)) {
         changedRows += this.upsertSystemConfigValue(
            targetConnection, targetColumns, targetByKey, "self_service_register_emby_info_id", "自助注册绑定服务器", "页面自助注册默认写入的 Emby 服务器 ID", selfServiceServerId, 1
         );
      }

      if (sourceByKey.containsKey("telegram_bot_register_enabled")
         || sourceByKey.containsKey("telegram_bot_register_max_count")
         || sourceByKey.containsKey("telegram_bot_register_used_count")
         || sourceByKey.containsKey("telegram_bot_register_default_days")) {
         changedRows += this.upsertSystemConfigValue(
            targetConnection,
            targetColumns,
            targetByKey,
            "telegram_bot_register_enabled",
            "Telegram机器人注册配置",
            "Telegram 机器人注册配置，JSON 格式保存默认天数、最大次数和已用次数",
            this.normalizeTelegramBotRegisterConfig(sourceByKey),
            this.sourceConfigEnabled(sourceByKey, "telegram_bot_register_enabled", 0)
         );
      }

      if (!sourceByKey.containsKey("telegram_request_points_config")) {
         changedRows += this.upsertSystemConfigValue(
            targetConnection,
            targetColumns,
            targetByKey,
            "telegram_request_points_config",
            "Telegram求片积分配置",
            "Telegram 群内求片扣积分配置，JSON 格式保存开关、免费次数和扣分规则",
            this.normalizeTelegramRequestPointsConfig("", true),
            1
         );
      }

      if (!sourceByKey.containsKey("emby_active_account_protection") && !targetByKey.containsKey("emby_active_account_protection")) {
         changedRows += this.upsertSystemConfigValue(
            targetConnection,
            targetColumns,
            targetByKey,
            "emby_active_account_protection",
            "Emby活跃账号保护配置",
            "JSON格式：指定服务器ID、活跃检测天数、封禁保留天数和是否自动删除",
            this.normalizeEmbyActiveAccountProtectionConfig(""),
            0
         );
      }

      return changedRows;
   }

   private long upsertSystemConfigValue(
      Connection targetConnection,
      List<String> targetColumns,
      Map<String, FoamDataMigrationServiceImpl.SystemConfigRow> targetByKey,
      String configKey,
      String name,
      String description,
      String configValue,
      int isEnabled
   ) throws SQLException {
      Map<String, Object> values = new HashMap<>();
      FoamDataMigrationServiceImpl.SystemConfigRow existing = targetByKey.get(configKey);
      if (existing != null) {
         values.putAll(existing.values());
      }

      values.put("config_key", configKey);
      values.put("name", name);
      values.put("description", description);
      values.put("config_value", configValue);
      values.put("is_enabled", isEnabled);
      values.put("is_update", 1);
      return this.upsertSystemConfigRow(targetConnection, targetColumns, targetByKey, values);
   }

   private long upsertSystemConfigRow(
      Connection targetConnection,
      List<String> targetColumns,
      Map<String, FoamDataMigrationServiceImpl.SystemConfigRow> targetByKey,
      Map<String, Object> values
   ) throws SQLException {
      String configKey = this.textValue(values.get("config_key"));
      if (!StringUtils.hasText(configKey)) {
         return 0L;
      } else {
         FoamDataMigrationServiceImpl.SystemConfigRow existing = targetByKey.get(configKey);
         return existing == null
            ? this.insertSystemConfigRow(targetConnection, targetColumns, targetByKey, configKey, values)
            : this.updateSystemConfigRow(targetConnection, targetColumns, targetByKey, configKey, existing, values);
      }
   }

   private long insertSystemConfigRow(
      Connection targetConnection,
      List<String> targetColumns,
      Map<String, FoamDataMigrationServiceImpl.SystemConfigRow> targetByKey,
      String configKey,
      Map<String, Object> values
   ) throws SQLException {
      List<String> insertColumns = new ArrayList<>();

      for (String column : targetColumns) {
         String normalized = this.normalizeColumn(column);
         if (!"id".equals(normalized) && values.containsKey(normalized)) {
            insertColumns.add(column);
         }
      }

      if (!this.containsColumn(insertColumns, "config_key")) {
         insertColumns.add("config_key");
      }

      String placeholders = String.join(", ", insertColumns.stream().map(item -> "?").toList());
      String sql = "INSERT INTO " + this.quoteIdentifier("system_config") + " (" + this.joinIdentifiers(insertColumns) + ") VALUES (" + placeholders + ")";

      long var23;
      try (PreparedStatement statement = targetConnection.prepareStatement(sql, 1)) {
         for (int index = 0; index < insertColumns.size(); index++) {
            statement.setObject(index + 1, values.get(this.normalizeColumn(insertColumns.get(index))));
         }

         long affectedRows = (long)Math.max(0, statement.executeUpdate());

         try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
            if (generatedKeys.next()) {
               values.put("id", generatedKeys.getObject(1));
            }
         }

         targetByKey.put(configKey, new FoamDataMigrationServiceImpl.SystemConfigRow(configKey, new HashMap<>(values)));
         var23 = affectedRows;
      }

      return var23;
   }

   private long updateSystemConfigRow(
      Connection targetConnection,
      List<String> targetColumns,
      Map<String, FoamDataMigrationServiceImpl.SystemConfigRow> targetByKey,
      String configKey,
      FoamDataMigrationServiceImpl.SystemConfigRow existing,
      Map<String, Object> values
   ) throws SQLException {
      List<String> updateColumns = new ArrayList<>();

      for (String column : targetColumns) {
         String normalized = this.normalizeColumn(column);
         if (!"id".equals(normalized)
            && !"config_key".equals(normalized)
            && values.containsKey(normalized)
            && (
               !"config_value".equals(normalized)
                  || !SENSITIVE_SYSTEM_CONFIG_KEYS.contains(configKey)
                  || !StringUtils.hasText(this.textValue(existing.values().get("config_value")))
            )) {
            updateColumns.add(column);
         }
      }

      if (updateColumns.isEmpty()) {
         return 0L;
      } else {
         String assignments = String.join(", ", updateColumns.stream().map(columnx -> this.quoteIdentifier(columnx) + " = ?").toList());
         String sql = "UPDATE " + this.quoteIdentifier("system_config") + " SET " + assignments + " WHERE `id` = ?";

         long var22;
         try (PreparedStatement statement = targetConnection.prepareStatement(sql)) {
            for (int index = 0; index < updateColumns.size(); index++) {
               statement.setObject(index + 1, values.get(this.normalizeColumn(updateColumns.get(index))));
            }

            statement.setObject(updateColumns.size() + 1, existing.values().get("id"));
            long affectedRows = (long)Math.max(0, statement.executeUpdate());
            Map<String, Object> updatedValues = new HashMap<>(existing.values());

            for (String columnx : updateColumns) {
               updatedValues.put(this.normalizeColumn(columnx), values.get(this.normalizeColumn(columnx)));
            }

            targetByKey.put(configKey, new FoamDataMigrationServiceImpl.SystemConfigRow(configKey, updatedValues));
            var22 = affectedRows;
         }

         return var22;
      }
   }

   private String resolveSelfServiceServerId(Map<String, FoamDataMigrationServiceImpl.SystemConfigRow> sourceByKey, Connection targetConnection) throws SQLException {
      String currentValue = this.configValue(sourceByKey, "self_service_register_emby_info_id");
      if (StringUtils.hasText(currentValue)) {
         return currentValue;
      } else {
         String registeredValue = this.configValue(sourceByKey, "registered_user");
         return this.isLongText(registeredValue) ? registeredValue : this.firstEnabledEmbyInfoId(targetConnection);
      }
   }

   private String firstEnabledEmbyInfoId(Connection targetConnection) throws SQLException {
      if (!this.tableExists(targetConnection, "emby_info")) {
         return "";
      } else {
         String sql = "SELECT `id`\nFROM `emby_info`\nWHERE COALESCE(`del_flag`, 0) = 0\n  AND COALESCE(`enabled`, 1) = 1\nORDER BY CASE WHEN COALESCE(`spread`, 0) = 1 THEN 0 ELSE 1 END, `id`\nLIMIT 1\n";

         String var5;
         try (
            PreparedStatement statement = targetConnection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();
         ) {
            var5 = resultSet.next() ? this.textValue(resultSet.getObject(1)) : "";
         }

         return var5;
      }
   }

   private String normalizeTelegramBotRegisterConfig(Map<String, FoamDataMigrationServiceImpl.SystemConfigRow> sourceByKey) {
      String raw = this.configValue(sourceByKey, "telegram_bot_register_enabled");
      JSONObject json = this.parseJsonObject(raw);
      int defaultDays = this.parseInteger(
         json == null ? null : json.get("defaultDays"),
         this.firstInteger(this.configValue(sourceByKey, "telegram_bot_register_default_days"), this.parseInteger(raw, 0))
      );
      int maxCount = this.parseInteger(
         json == null ? null : json.get("maxCount"), this.parseInteger(this.configValue(sourceByKey, "telegram_bot_register_max_count"), 0)
      );
      int usedCount = this.parseInteger(
         json == null ? null : json.get("usedCount"), this.parseInteger(this.configValue(sourceByKey, "telegram_bot_register_used_count"), 0)
      );
      JSONObject normalized = new JSONObject();
      normalized.put("defaultDays", Integer.valueOf(Math.max(0, defaultDays)));
      normalized.put("maxCount", Integer.valueOf(Math.max(0, maxCount)));
      normalized.put("usedCount", Integer.valueOf(Math.max(0, usedCount)));
      return normalized.toJSONString();
   }

   private String normalizeTelegramRequestPointsConfig(String rawValue, boolean disableWhenMissing) {
      JSONObject source = this.parseJsonObject(rawValue);
      JSONObject normalized = new JSONObject();
      normalized.put(
         "enabled",
         Boolean.valueOf(source == null ? !disableWhenMissing && this.parseBoolean(rawValue, false) : this.parseBoolean(source.get("enabled"), false))
      );
      normalized.put("dailyFreeCount", Integer.valueOf(this.parseInteger(source == null ? null : source.get("dailyFreeCount"), 0)));
      normalized.put("pointsPerRequest", Integer.valueOf(this.parseInteger(source == null ? null : source.get("pointsPerRequest"), 10)));
      normalized.put("refundOnReject", Boolean.valueOf(source == null || this.parseBoolean(source.get("refundOnReject"), true)));
      return normalized.toJSONString();
   }

   private String normalizeEmbyActiveAccountProtectionConfig(String rawValue) {
      JSONObject source = this.parseJsonObject(rawValue);
      JSONObject normalized = new JSONObject();
      if (source != null) {
         normalized.putAll(source);
      }

      if (!normalized.containsKey("serverIds")) {
         normalized.put("serverIds", List.of());
      }

      normalized.put("activityCheckDays", Integer.valueOf(this.parseInteger(normalized.get("activityCheckDays"), 21)));
      normalized.put("disabledRetentionDays", Integer.valueOf(this.parseInteger(normalized.get("disabledRetentionDays"), 15)));
      normalized.put("deleteAfterDisabled", Boolean.valueOf(this.parseBoolean(normalized.get("deleteAfterDisabled"), true)));
      return normalized.toJSONString();
   }

   private void afterTableSynced(Connection targetConnection, String tableName) throws SQLException {
      switch (tableName) {
         case "emby_info":
            this.executeUpdate(
               targetConnection,
               "UPDATE `emby_info`\nSET `admin_query_userid` = `copyfromuserid`\nWHERE (`admin_query_userid` IS NULL OR TRIM(`admin_query_userid`) = '')\n  AND `copyfromuserid` IS NOT NULL\n  AND TRIM(`copyfromuserid`) <> ''\n"
            );
            this.executeUpdate(
               targetConnection,
               "UPDATE `emby_info`\nSET `emby_url` = CONCAT(TRIM(TRAILING '/' FROM TRIM(`emby_url`)), '/')\nWHERE `emby_url` IS NOT NULL\n  AND TRIM(`emby_url`) <> ''\n  AND LOWER(TRIM(TRAILING '/' FROM TRIM(`emby_url`))) LIKE '%/emby'\n"
            );
            this.executeUpdate(
               targetConnection,
               "UPDATE `emby_info`\nSET `emby_url` = CONCAT(TRIM(TRAILING '/' FROM TRIM(`emby_url`)), '/emby/')\nWHERE `emby_url` IS NOT NULL\n  AND TRIM(`emby_url`) <> ''\n  AND LOWER(TRIM(TRAILING '/' FROM TRIM(`emby_url`))) NOT LIKE '%/emby'\n"
            );
            break;
         case "emby_user":
            this.executeUpdate(
               targetConnection,
               "UPDATE `emby_user`\nSET `expiration_date` = NULL\nWHERE COALESCE(`host_line_type`, 0) = 1\n  AND `expiration_date` IS NOT NULL\n"
            );
            break;
         case "emby_user_register_record":
            this.executeUpdate(
               targetConnection,
               "UPDATE `emby_user_register_record`\nSET `register_channel_detail` = `register_channel`\nWHERE (`register_channel_detail` IS NULL OR TRIM(`register_channel_detail`) = '')\n  AND `register_channel` IS NOT NULL\n  AND TRIM(`register_channel`) <> ''\n"
            );
            break;
         case "emby_user_renew_record":
            this.executeUpdate(
               targetConnection,
               "UPDATE `emby_user_renew_record`\nSET `renew_channel_detail` = `renew_channel`\nWHERE (`renew_channel_detail` IS NULL OR TRIM(`renew_channel_detail`) = '')\n  AND `renew_channel` IS NOT NULL\n  AND TRIM(`renew_channel`) <> ''\n"
            );
      }
   }

   private void executeUpdate(Connection connection, String sql) throws SQLException {
      try (PreparedStatement statement = connection.prepareStatement(sql)) {
         statement.executeUpdate();
      }
   }

   private long countAffectedRows(int[] counts) {
      long affectedRows = 0L;

      for (int count : counts) {
         if (count == -2) {
            affectedRows++;
         } else if (count > 0) {
            affectedRows += (long)count;
         }
      }

      return affectedRows;
   }

   private FoamDataMigrationResultResponse buildResult(List<FoamDataMigrationTableResultResponse> tableResults, int tableCount, long durationMs) {
      FoamDataMigrationResultResponse response = new FoamDataMigrationResultResponse();
      response.setTableCount(tableCount);
      response.setTables(tableResults);
      response.setDurationMs(durationMs);
      response.setSyncedTableCount(this.countStatus(tableResults, "SYNCED"));
      response.setSkippedTableCount(this.countStatus(tableResults, "SKIPPED") + this.countStatus(tableResults, "EMPTY"));
      response.setFailedTableCount(this.countStatus(tableResults, "FAILED"));
      response.setTotalSourceRows(tableResults.stream().mapToLong(item -> item.getSourceRows() == null ? 0L : item.getSourceRows()).sum());
      response.setTotalSyncedRows(tableResults.stream().mapToLong(item -> item.getSyncedRows() == null ? 0L : item.getSyncedRows()).sum());
      return response;
   }

   private int countStatus(List<FoamDataMigrationTableResultResponse> tableResults, String status) {
      return (int)tableResults.stream().filter(item -> status.equals(item.getStatus())).count();
   }

   private int countSkipped(List<FoamDataMigrationTableResultResponse> tableResults) {
      return this.countStatus(tableResults, "SKIPPED") + this.countStatus(tableResults, "EMPTY");
   }

   private long totalSyncedRows(List<FoamDataMigrationTableResultResponse> tableResults) {
      return tableResults.stream().mapToLong(item -> this.valueOrZero(item.getSyncedRows())).sum();
   }

   private long valueOrZero(Long value) {
      return value == null ? 0L : value;
   }

   private FoamDataMigrationProgressResponse buildProgress(
      List<String> migrationTables,
      String status,
      String stage,
      int percent,
      int processedTables,
      int currentTableIndex,
      int syncedTableCount,
      int skippedTableCount,
      int failedTableCount,
      String currentTable,
      long currentTableSourceRows,
      long currentTableSyncedRows,
      long totalSyncedRows,
      long startedAt,
      String message,
      FoamDataMigrationResultResponse result
   ) {
      FoamDataMigrationProgressResponse progress = new FoamDataMigrationProgressResponse();
      progress.setStatus(status);
      progress.setStage(stage);
      progress.setPercent(Math.max(0, Math.min(100, percent)));
      progress.setTotalTables(migrationTables.size());
      progress.setProcessedTables(Math.max(0, processedTables));
      progress.setCurrentTableIndex(currentTableIndex <= 0 ? null : currentTableIndex);
      progress.setSyncedTableCount(Math.max(0, syncedTableCount));
      progress.setSkippedTableCount(Math.max(0, skippedTableCount));
      progress.setFailedTableCount(Math.max(0, failedTableCount));
      progress.setCurrentTable(currentTable);
      progress.setCurrentTableSourceRows(currentTableSourceRows);
      progress.setCurrentTableSyncedRows(currentTableSyncedRows);
      progress.setTotalSyncedRows(totalSyncedRows);
      progress.setDurationMs(System.currentTimeMillis() - startedAt);
      progress.setMessage(message);
      progress.setResult(result);
      return progress;
   }

   private FoamDataMigrationTableResultResponse newTableResult(String tableName) {
      FoamDataMigrationTableResultResponse result = new FoamDataMigrationTableResultResponse();
      result.setTableName(tableName);
      result.setStatus("PENDING");
      result.setSourceRows(0L);
      result.setSyncedRows(0L);
      result.setMessage("");
      return result;
   }

   private List<String> resolveMigrationTables(FoamDataMigrationRequest request) {
      return SUPPORTED_TABLES;
   }

   private String configValue(Map<String, FoamDataMigrationServiceImpl.SystemConfigRow> rows, String key) {
      FoamDataMigrationServiceImpl.SystemConfigRow row = rows.get(key);
      return row == null ? "" : this.textValue(row.values().get("config_value"));
   }

   private int sourceConfigEnabled(Map<String, FoamDataMigrationServiceImpl.SystemConfigRow> rows, String key, int defaultValue) {
      FoamDataMigrationServiceImpl.SystemConfigRow row = rows.get(key);
      if (row == null) {
         return defaultValue;
      } else {
         return this.parseInteger(row.values().get("is_enabled"), defaultValue) > 0 ? 1 : 0;
      }
   }

   private boolean containsColumn(List<String> columns, String columnName) {
      String normalizedColumnName = this.normalizeColumn(columnName);

      for (String column : columns) {
         if (normalizedColumnName.equals(this.normalizeColumn(column))) {
            return true;
         }
      }

      return false;
   }

   private String normalizeColumn(String columnName) {
      return columnName == null ? "" : columnName.toLowerCase(Locale.ROOT);
   }

   private String textValue(Object value) {
      return value == null ? "" : String.valueOf(value).trim();
   }

   private boolean isLegacyFalse(String value) {
      String normalized = value == null ? "" : value.trim().toLowerCase(Locale.ROOT);
      return "false".equals(normalized) || "0".equals(normalized) || "off".equals(normalized) || "no".equals(normalized);
   }

   private boolean isLongText(String value) {
      if (!StringUtils.hasText(value)) {
         return false;
      } else {
         try {
            Long.parseLong(value.trim());
            return true;
         } catch (NumberFormatException var3) {
            return false;
         }
      }
   }

   private JSONObject parseJsonObject(String value) {
      if (StringUtils.hasText(value) && value.trim().startsWith("{")) {
         try {
            return JSONObject.parseObject(value);
         } catch (RuntimeException var3) {
            return null;
         }
      } else {
         return null;
      }
   }

   private int parseInteger(Object value, int defaultValue) {
      if (value == null) {
         return defaultValue;
      } else if (value instanceof Number number) {
         return number.intValue();
      } else {
         String text = String.valueOf(value).trim();
         if (!StringUtils.hasText(text)) {
            return defaultValue;
         } else {
            try {
               return Integer.parseInt(text);
            } catch (NumberFormatException var5) {
               return defaultValue;
            }
         }
      }
   }

   private int firstInteger(String value, int defaultValue) {
      return this.parseInteger(value, defaultValue);
   }

   private boolean parseBoolean(Object value, boolean defaultValue) {
      if (value == null) {
         return defaultValue;
      } else if (value instanceof Boolean bool) {
         return bool;
      } else if (value instanceof Number number) {
         return number.intValue() != 0;
      } else {
         String text = String.valueOf(value).trim().toLowerCase(Locale.ROOT);
         if (!StringUtils.hasText(text)) {
            return defaultValue;
         } else if ("true".equals(text) || "1".equals(text) || "yes".equals(text) || "on".equals(text)) {
            return true;
         } else {
            return !"false".equals(text) && !"0".equals(text) && !"no".equals(text) && !"off".equals(text) ? defaultValue : false;
         }
      }
   }

   private List<String> commonColumns(Connection sourceConnection, Connection targetConnection, String tableName) throws SQLException {
      List<String> sourceColumns = this.queryColumns(sourceConnection, tableName);
      Set<String> targetColumns = new HashSet<>();

      for (String column : this.queryColumns(targetConnection, tableName)) {
         targetColumns.add(column.toLowerCase(Locale.ROOT));
      }

      List<String> columns = new ArrayList<>();

      for (String sourceColumn : sourceColumns) {
         if (targetColumns.contains(sourceColumn.toLowerCase(Locale.ROOT))) {
            columns.add(sourceColumn);
         }
      }

      return columns;
   }

   private List<String> queryColumns(Connection connection, String tableName) throws SQLException {
      String sql = "SELECT COLUMN_NAME\nFROM information_schema.COLUMNS\nWHERE TABLE_SCHEMA = DATABASE()\n  AND TABLE_NAME = ?\nORDER BY ORDINAL_POSITION\n";
      List<String> columns = new ArrayList<>();

      try (PreparedStatement statement = connection.prepareStatement(sql)) {
         statement.setString(1, tableName);

         try (ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
               String columnName = resultSet.getString(1);
               this.validateIdentifier(columnName);
               columns.add(columnName);
            }
         }
      }

      return columns;
   }

   private boolean tableExists(Connection connection, String tableName) throws SQLException {
      this.validateIdentifier(tableName);
      String sql = "SELECT COUNT(1)\nFROM information_schema.TABLES\nWHERE TABLE_SCHEMA = DATABASE()\n  AND TABLE_NAME = ?\n";

      boolean var6;
      try (PreparedStatement statement = connection.prepareStatement(sql)) {
         statement.setString(1, tableName);

         try (ResultSet resultSet = statement.executeQuery()) {
            var6 = resultSet.next() && resultSet.getLong(1) > 0L;
         }
      }

      return var6;
   }

   private long countRows(Connection connection, String tableName) throws SQLException {
      this.validateIdentifier(tableName);

      long var5;
      try (
         PreparedStatement statement = connection.prepareStatement("SELECT COUNT(1) FROM " + this.quoteIdentifier(tableName));
         ResultSet resultSet = statement.executeQuery();
      ) {
         var5 = resultSet.next() ? resultSet.getLong(1) : 0L;
      }

      return var5;
   }

   private String buildSelectSql(String tableName, List<String> columns) {
      this.validateIdentifier(tableName);
      String sql = "SELECT " + this.joinIdentifiers(columns) + " FROM " + this.quoteIdentifier(tableName);
      if ("user_oauth_binding".equals(tableName)
         && this.containsColumn(columns, "update_datetime")
         && this.containsColumn(columns, "create_datetime")
         && this.containsColumn(columns, "id")) {
         return sql + " ORDER BY COALESCE(`update_datetime`, `create_datetime`) DESC, `id` DESC";
      } else {
         return this.containsColumn(columns, "id") ? sql + " ORDER BY `id`" : sql;
      }
   }

   private String buildInsertSql(String tableName, List<String> columns) {
      this.validateIdentifier(tableName);
      String placeholders = String.join(", ", columns.stream().map(item -> "?").toList());
      return "INSERT IGNORE INTO " + this.quoteIdentifier(tableName) + " (" + this.joinIdentifiers(columns) + ") VALUES (" + placeholders + ")";
   }

   private String joinIdentifiers(List<String> identifiers) {
      return String.join(", ", identifiers.stream().map(this::quoteIdentifier).toList());
   }

   private String quoteIdentifier(String identifier) {
      this.validateIdentifier(identifier);
      return "`" + identifier + "`";
   }

   private void validateIdentifier(String identifier) {
      if (!StringUtils.hasText(identifier) || !SAFE_IDENTIFIER.matcher(identifier).matches()) {
         throw new IllegalArgumentException("非法数据库标识符：" + identifier);
      }
   }

   private Connection openSourceConnection(FoamDataMigrationRequest request) throws SQLException {
      Properties properties = new Properties();
      properties.setProperty("user", request.getUsername().trim());
      properties.setProperty("password", request.getPassword() == null ? "" : request.getPassword());
      properties.setProperty("connectTimeout", "10000");
      properties.setProperty("socketTimeout", "3600000");
      properties.setProperty("useCursorFetch", "true");
      properties.setProperty("useServerPrepStmts", "true");
      properties.setProperty("rewriteBatchedStatements", "true");
      return DriverManager.getConnection(this.resolveJdbcUrl(request), properties);
   }

   private String resolveJdbcUrl(FoamDataMigrationRequest request) {
      String jdbcUrl = request.getJdbcUrl();
      if (!StringUtils.hasText(jdbcUrl)) {
         String hostPort = request.getHostPort() == null ? "" : request.getHostPort().trim();
         String databaseName = request.getDatabaseName() == null ? "" : request.getDatabaseName().trim();
         if (!StringUtils.hasText(databaseName)) {
            databaseName = "foam-api";
         }

         if (!StringUtils.hasText(hostPort)) {
            throw new BizException(ResponseStatusEnum.BAD_REQUEST.getCode(), "旧库 IP:端口不能为空");
         }

         if (!hostPort.startsWith("jdbc:") && !hostPort.startsWith("mysql://") && !hostPort.contains("/")) {
            jdbcUrl = hostPort + "/" + databaseName;
         } else {
            jdbcUrl = hostPort;
         }
      }

      return this.normalizeJdbcUrl(jdbcUrl);
   }

   private String normalizeJdbcUrl(String jdbcUrl) {
      String value = jdbcUrl == null ? "" : jdbcUrl.trim();
      if (!StringUtils.hasText(value)) {
         throw new BizException(ResponseStatusEnum.BAD_REQUEST.getCode(), "旧库 IP:端口不能为空");
      } else {
         if (value.startsWith("mysql://")) {
            value = "jdbc:" + value;
         } else if (value.startsWith("//")) {
            value = "jdbc:mysql:" + value;
         } else if (!value.startsWith("jdbc:")) {
            value = "jdbc:mysql://" + value;
         }

         if (!value.startsWith("jdbc:mysql://")) {
            throw new BizException(ResponseStatusEnum.BAD_REQUEST.getCode(), "仅支持 MySQL 数据库地址");
         } else {
            value = this.appendMissingOption(value, "useUnicode", "true");
            value = this.appendMissingOption(value, "characterEncoding", "utf8");
            value = this.appendMissingOption(value, "useSSL", "false");
            value = this.appendMissingOption(value, "serverTimezone", "GMT%2B8");
            value = this.appendMissingOption(value, "allowPublicKeyRetrieval", "true");
            value = this.appendMissingOption(value, "rewriteBatchedStatements", "true");
            return this.appendMissingOption(value, "useServerPrepStmts", "true");
         }
      }
   }

   private String appendMissingOption(String url, String key, String value) {
      String lowerUrl = url.toLowerCase(Locale.ROOT);
      String lowerKey = key.toLowerCase(Locale.ROOT) + "=";
      return !lowerUrl.contains("?" + lowerKey) && !lowerUrl.contains("&" + lowerKey) ? url + (url.contains("?") ? "&" : "?") + key + "=" + value : url;
   }

   private int resolveBatchSize(Integer batchSize) {
      return batchSize == null ? 1000 : Math.min(5000, Math.max(50, batchSize));
   }

   private void setForeignKeyChecks(Connection connection, boolean enabled) throws SQLException {
      try (PreparedStatement statement = connection.prepareStatement("SET FOREIGN_KEY_CHECKS = " + (enabled ? "1" : "0"))) {
         statement.execute();
      }
   }

   private void rollbackQuietly(Connection connection) {
      if (connection != null) {
         try {
            connection.rollback();
         } catch (SQLException var3) {
            log.debug("Mist 数据迁移表事务回滚失败：{}", var3.getMessage());
         }
      }
   }

   private void restoreForeignKeyChecks(Connection connection) {
      if (connection != null) {
         try {
            this.setForeignKeyChecks(connection, true);
         } catch (SQLException var3) {
            log.debug("Mist 数据迁移恢复外键检查失败：{}", var3.getMessage());
         }
      }
   }

   private void resetAutoCommit(Connection connection) {
      if (connection != null) {
         try {
            if (!connection.getAutoCommit()) {
               connection.setAutoCommit(true);
            }
         } catch (SQLException var3) {
            log.debug("Mist 数据迁移恢复自动提交失败：{}", var3.getMessage());
         }
      }
   }

   private void closeQuietly(Connection connection) {
      if (connection != null) {
         try {
            connection.close();
         } catch (SQLException var3) {
            log.debug("Mist 数据迁移关闭连接失败：{}", var3.getMessage());
         }
      }
   }

   private void trySetReadOnly(Connection connection) {
      try {
         connection.setReadOnly(true);
      } catch (SQLException var3) {
         log.debug("旧库连接不支持只读标记：{}", var3.getMessage());
      }
   }

   private String safeSqlMessage(String prefix, Throwable throwable) {
      String message = throwable == null ? "" : throwable.getMessage();
      if (!StringUtils.hasText(message)) {
         return prefix;
      } else {
         String sanitized = message.replaceAll("(?i)(password=)[^&\\s]+", "$1******").replaceAll("(?i)(password: )[^,\\s]+", "$1******");
         return prefix + "：" + sanitized;
      }
   }

   private class MigrationProgressTracker {
      private final long startedAt;
      private final FoamDataMigrationServiceImpl.ProgressEmitter progressEmitter;
      private final List<String> migrationTables;
      private final FoamDataMigrationTableResultResponse[] tableResults;
      private final long[] tableSourceRows;
      private final long[] tableSyncedRows;
      private final Object lock = new Object();

      private MigrationProgressTracker(long startedAt, FoamDataMigrationServiceImpl.ProgressEmitter progressEmitter, List<String> migrationTables) {
         this.startedAt = startedAt;
         this.progressEmitter = progressEmitter;
         this.migrationTables = migrationTables;
         this.tableResults = new FoamDataMigrationTableResultResponse[migrationTables.size()];
         this.tableSourceRows = new long[migrationTables.size()];
         this.tableSyncedRows = new long[migrationTables.size()];
      }

      private void sendRunning(int tableIndex, long sourceRows, long syncedRows, String message, boolean force) {
         synchronized (this.lock) {
            this.tableSourceRows[tableIndex] = Math.max(this.tableSourceRows[tableIndex], sourceRows);
            this.tableSyncedRows[tableIndex] = Math.max(this.tableSyncedRows[tableIndex], syncedRows);
            this.progressEmitter
               .send(
                  FoamDataMigrationServiceImpl.this.buildProgress(
                     this.migrationTables,
                     "RUNNING",
                     "TABLE",
                     this.percentSnapshot(),
                     this.processedTablesSnapshot(),
                     tableIndex + 1,
                     this.countStatusSnapshot("SYNCED"),
                     this.countSkippedSnapshot(),
                     this.countStatusSnapshot("FAILED"),
                     this.tableProgressText(tableIndex),
                     this.tableSourceRows[tableIndex],
                     this.tableSyncedRows[tableIndex],
                     this.totalSyncedRowsSnapshot(),
                     this.startedAt,
                     message,
                     null
                  ),
                  force
               );
         }
      }

      private void finish(int tableIndex, FoamDataMigrationTableResultResponse result, String tableProgressText) {
         synchronized (this.lock) {
            this.tableResults[tableIndex] = result;
            this.tableSourceRows[tableIndex] = FoamDataMigrationServiceImpl.this.valueOrZero(result.getSourceRows());
            this.tableSyncedRows[tableIndex] = FoamDataMigrationServiceImpl.this.valueOrZero(result.getSyncedRows());
            this.progressEmitter
               .send(
                  FoamDataMigrationServiceImpl.this.buildProgress(
                     this.migrationTables,
                     "RUNNING",
                     "TABLE",
                     this.percentSnapshot(),
                     this.processedTablesSnapshot(),
                     tableIndex + 1,
                     this.countStatusSnapshot("SYNCED"),
                     this.countSkippedSnapshot(),
                     this.countStatusSnapshot("FAILED"),
                     tableProgressText,
                     this.tableSourceRows[tableIndex],
                     this.tableSyncedRows[tableIndex],
                     this.totalSyncedRowsSnapshot(),
                     this.startedAt,
                     this.tableResultMessage(tableProgressText, result),
                     null
                  ),
                  true
               );
         }
      }

      private void sendFailed(String message) {
         synchronized (this.lock) {
            this.progressEmitter
               .send(
                  FoamDataMigrationServiceImpl.this.buildProgress(
                     this.migrationTables,
                     "FAILED",
                     "FAILED",
                     this.percentSnapshot(),
                     this.processedTablesSnapshot(),
                     0,
                     this.countStatusSnapshot("SYNCED"),
                     this.countSkippedSnapshot(),
                     this.countStatusSnapshot("FAILED"),
                     null,
                     0L,
                     0L,
                     this.totalSyncedRowsSnapshot(),
                     this.startedAt,
                     message,
                     null
                  ),
                  true
               );
         }
      }

      private List<FoamDataMigrationTableResultResponse> orderedResults() {
         synchronized (this.lock) {
            List<FoamDataMigrationTableResultResponse> results = new ArrayList<>(this.migrationTables.size());

            for (int index = 0; index < this.migrationTables.size(); index++) {
               FoamDataMigrationTableResultResponse result = this.tableResults[index];
               results.add(result == null ? FoamDataMigrationServiceImpl.this.newTableResult(this.migrationTables.get(index)) : result);
            }

            return results;
         }
      }

      private int processedTablesSnapshot() {
         int count = 0;

         for (FoamDataMigrationTableResultResponse result : this.tableResults) {
            if (result != null) {
               count++;
            }
         }

         return count;
      }

      private int countStatusSnapshot(String status) {
         int count = 0;

         for (FoamDataMigrationTableResultResponse result : this.tableResults) {
            if (result != null && status.equals(result.getStatus())) {
               count++;
            }
         }

         return count;
      }

      private int countSkippedSnapshot() {
         return this.countStatusSnapshot("SKIPPED") + this.countStatusSnapshot("EMPTY");
      }

      private long totalSyncedRowsSnapshot() {
         long total = 0L;

         for (int index = 0; index < this.tableResults.length; index++) {
            FoamDataMigrationTableResultResponse result = this.tableResults[index];
            total += result == null ? this.tableSyncedRows[index] : FoamDataMigrationServiceImpl.this.valueOrZero(result.getSyncedRows());
         }

         return total;
      }

      private int percentSnapshot() {
         if (this.migrationTables.isEmpty()) {
            return 100;
         } else {
            double progressedTables = 0.0;

            for (int index = 0; index < this.tableResults.length; index++) {
               if (this.tableResults[index] != null) {
                  progressedTables++;
               } else if (this.tableSourceRows[index] > 0L) {
                  double partial = (double)this.tableSyncedRows[index] / (double)this.tableSourceRows[index];
                  progressedTables += Math.min(0.99, Math.max(0.0, partial));
               }
            }

            int value = (int)Math.floor(progressedTables / (double)this.migrationTables.size() * 100.0);
            return Math.max(0, Math.min(98, value));
         }
      }

      private String tableProgressText(int tableIndex) {
         return "第 " + (tableIndex + 1) + "/" + this.migrationTables.size() + " 张表";
      }

      private String tableResultMessage(String tableProgressText, FoamDataMigrationTableResultResponse result) {
         String status = result.getStatus();
         if ("SYNCED".equals(status)) {
            return tableProgressText + "补写完成，新插入 " + FoamDataMigrationServiceImpl.this.valueOrZero(result.getSyncedRows()) + " 行";
         } else if ("EMPTY".equals(status)) {
            return tableProgressText + "旧库无数据，当前库未变更";
         } else if ("SKIPPED".equals(status)) {
            return tableProgressText + "已跳过";
         } else {
            return "FAILED".equals(status) ? tableProgressText + "同步失败" : result.getMessage();
         }
      }
   }

   private static class ProgressEmitter {
      private final Consumer<FoamDataMigrationProgressResponse> consumer;
      private long lastSentAt;

      private ProgressEmitter(Consumer<FoamDataMigrationProgressResponse> consumer) {
         this.consumer = consumer;
      }

      private synchronized void send(FoamDataMigrationProgressResponse progress, boolean force) {
         if (this.consumer != null && progress != null) {
            long now = System.currentTimeMillis();
            if (force || now - this.lastSentAt >= 300L) {
               this.lastSentAt = now;
               this.consumer.accept(progress);
            }
         }
      }
   }

   private static record SystemConfigRow(String configKey, Map<String, Object> values) {
   }

   private interface TableProgressConsumer {
      void accept(long sourceRows, long syncedRows, String message, boolean force);
   }
}
