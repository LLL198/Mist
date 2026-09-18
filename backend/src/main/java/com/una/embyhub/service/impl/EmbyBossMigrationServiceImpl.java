package com.una.embyhub.service.impl;

import cn.dev33.satoken.secure.SaSecureUtil;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.una.embyhub.config.common.enums.RegisterChannelEnum;
import com.una.embyhub.config.common.enums.ResponseStatusEnum;
import com.una.embyhub.config.common.exception.BizException;
import com.una.embyhub.mapper.EmbyUserMapper;
import com.una.embyhub.mapper.HostLineMapper;
import com.una.embyhub.mapper.NotifyChannelMapper;
import com.una.embyhub.mapper.PointsBotLedgerMapper;
import com.una.embyhub.mapper.PointsBotLevelConfigMapper;
import com.una.embyhub.mapper.PointsBotUserMapper;
import com.una.embyhub.mapper.UserOauthBindingMapper;
import com.una.embyhub.mapper.UserPointsMapper;
import com.una.embyhub.model.dto.request.embybossmigration.EmbyBossMigrationRequest;
import com.una.embyhub.model.dto.response.embybossmigration.EmbyBossMigrationResultResponse;
import com.una.embyhub.model.entity.BaseEntity;
import com.una.embyhub.model.entity.EmbyInfo;
import com.una.embyhub.model.entity.EmbyUser;
import com.una.embyhub.model.entity.HostLine;
import com.una.embyhub.model.entity.NotifyChannel;
import com.una.embyhub.model.entity.PointsBotLedger;
import com.una.embyhub.model.entity.PointsBotLevelConfig;
import com.una.embyhub.model.entity.PointsBotUser;
import com.una.embyhub.model.entity.UserOauthBinding;
import com.una.embyhub.model.entity.UserPoints;
import com.una.embyhub.pointsbot.service.PointsBotConfigService;
import com.una.embyhub.service.EmbyBossMigrationService;
import com.una.embyhub.service.EmbyInfoService;
import com.una.embyhub.service.EmbyUserIdentityUtils;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import lombok.Generated;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Service
public class EmbyBossMigrationServiceImpl implements EmbyBossMigrationService {
   @Generated
   private static final Logger log = LoggerFactory.getLogger(EmbyBossMigrationServiceImpl.class);
   private static final String DEFAULT_DATABASE_NAME = "embyboss";
   private static final String CONFIRMATION_TEXT = "IMPORT_EMBYBOSS";
   private static final String SOURCE_TAG = "EmbyBoss迁移";
   private static final String FIXED_LEVEL_REMARK_PREFIX = "embyboss:fixed:";
   private static final String TELEGRAM_PROVIDER = "telegram";
   private static final Pattern URL_PATTERN = Pattern.compile("(?i)https?://[^\\s`*_>)\\]}]+");
   private static final Pattern HOST_PATTERN = Pattern.compile(
      "(?i)(?:\\b\\d{1,3}(?:\\.\\d{1,3}){3}|\\b[a-z0-9][a-z0-9.-]*\\.[a-z]{2,})(?::\\d{1,5})?(?:/[\\w./%?=&+#-]*)?"
   );
   private final EmbyInfoService embyInfoService;
   private final EmbyUserMapper embyUserMapper;
   private final HostLineMapper hostLineMapper;
   private final NotifyChannelMapper notifyChannelMapper;
   private final PointsBotUserMapper pointsBotUserMapper;
   private final PointsBotLedgerMapper pointsBotLedgerMapper;
   private final PointsBotLevelConfigMapper pointsBotLevelConfigMapper;
   private final UserOauthBindingMapper userOauthBindingMapper;
   private final UserPointsMapper userPointsMapper;
   private final PointsBotConfigService pointsBotConfigService;

   @Override
   public EmbyBossMigrationResultResponse preview(EmbyBossMigrationRequest request) {
      request.setDryRun(true);
      return this.migrate(request, true);
   }

   @Transactional(
      rollbackFor = {Exception.class}
   )
   @Override
   public EmbyBossMigrationResultResponse sync(EmbyBossMigrationRequest request) {
      if (Boolean.TRUE.equals(request.getDryRun())) {
         return this.preview(request);
      } else if (!"IMPORT_EMBYBOSS".equals(request.getConfirmation())) {
         throw new BizException(ResponseStatusEnum.BAD_REQUEST.getCode(), "真实迁移需要 confirmation=IMPORT_EMBYBOSS");
      } else {
         return this.migrate(request, false);
      }
   }

   private EmbyBossMigrationResultResponse migrate(EmbyBossMigrationRequest request, boolean dryRun) {
      EmbyBossMigrationResultResponse result = new EmbyBossMigrationResultResponse();
      result.setDryRun(dryRun);
      result.setEmbyInfoId(request.getEmbyInfoId());
      EmbyInfo embyInfo = this.embyInfoService.getById(request.getEmbyInfoId());
      if (embyInfo == null) {
         throw new BizException(ResponseStatusEnum.BAD_REQUEST.getCode(), "目标 Emby 服务器不存在");
      } else {
         EmbyBossMigrationServiceImpl.EmbyBossConfigSnapshot sourceConfig = this.parseSourceConfig(request);
         this.syncConfigFileData(sourceConfig, request, result, embyInfo, dryRun);
         Long chatId = this.resolveChatId(request, result, sourceConfig);
         result.setChatId(chatId);

         try {
            EmbyBossMigrationResultResponse var10;
            try (Connection sourceConnection = this.openSourceConnection(request)) {
               sourceConnection.setReadOnly(true);
               DatabaseMetaData metaData = sourceConnection.getMetaData();
               result.setProductName(metaData.getDatabaseProductName());
               result.setProductVersion(metaData.getDatabaseProductVersion());
               result.setCatalog(sourceConnection.getCatalog());
               if (!this.tableExists(sourceConnection, "emby")) {
                  throw new BizException(ResponseStatusEnum.BAD_REQUEST.getCode(), "源库缺少 EmbyBoss 用户表 emby");
               }

               result.setSourceEmbyRows(this.countRows(sourceConnection, "emby"));
               if (this.tableExists(sourceConnection, "emby2")) {
                  result.setSourceEmby2Rows(this.countRows(sourceConnection, "emby2"));
               } else {
                  result.addWarning("源库缺少 emby2 表，已跳过非 TG 用户迁移");
               }

               Map<String, EmbyBossMigrationServiceImpl.LevelSnapshot> levels = this.resolveLevels(request, result, dryRun);
               this.migrateEmbyTable(sourceConnection, request, result, levels, chatId, dryRun);
               if (this.tableExists(sourceConnection, "emby2")) {
                  this.migrateEmby2Table(sourceConnection, request, result, levels, dryRun);
               }

               result.setMessage(dryRun ? "EmbyBoss 迁移预览完成，未写入任何数据" : "EmbyBoss 数据迁移完成");
               var10 = result;
            }

            return var10;
         } catch (SQLException var13) {
            throw new BizException(ResponseStatusEnum.BAD_REQUEST.getCode(), this.safeSqlMessage("EmbyBoss 源库读取失败", var13));
         }
      }
   }

   private Long resolveChatId(
      EmbyBossMigrationRequest request, EmbyBossMigrationResultResponse result, EmbyBossMigrationServiceImpl.EmbyBossConfigSnapshot sourceConfig
   ) {
      if (request.getChatId() != null) {
         return request.getChatId();
      } else {
         if (sourceConfig != null && StringUtils.hasText(sourceConfig.groupChatId())) {
            try {
               return Long.parseLong(sourceConfig.groupChatId().trim());
            } catch (NumberFormatException var6) {
               result.addWarning("config.json 中 group 不是有效的 Telegram 群聊 ID，已尝试读取当前积分机器人配置");
            }
         }

         try {
            String groupChatId = this.pointsBotConfigService.loadConfig().getConfig().getGroupChatId();
            if (StringUtils.hasText(groupChatId)) {
               return Long.parseLong(groupChatId.trim());
            }
         } catch (Exception var5) {
            result.addWarning("Telegram 机器人积分群/频道 Chat ID 读取失败: " + var5.getMessage());
         }

         if (Boolean.TRUE.equals(request.getSyncPointsBotUsers()) || Boolean.TRUE.equals(request.getSyncPointLedger())) {
            result.addWarning("未提供积分群聊 ID，已跳过 points_bot_user 与积分流水迁移");
         }

         return null;
      }
   }

   private EmbyBossMigrationServiceImpl.EmbyBossConfigSnapshot parseSourceConfig(EmbyBossMigrationRequest request) {
      if (!StringUtils.hasText(request.getConfigJson())) {
         return null;
      } else {
         try {
            JSONObject root = JSONObject.parseObject(request.getConfigJson());
            JSONObject open = root.getJSONObject("open");
            Integer checkinMin = null;
            Integer checkinMax = null;
            if (open != null) {
               JSONArray reward = open.getJSONArray("checkin_reward");
               if (reward != null && !reward.isEmpty()) {
                  checkinMin = reward.getInteger(0);
                  checkinMax = reward.size() > 1 ? reward.getInteger(1) : checkinMin;
               }
            }

            return new EmbyBossMigrationServiceImpl.EmbyBossConfigSnapshot(
               this.trimToNull(root.getString("emby_url")),
               this.trimToNull(root.getString("emby_api")),
               this.trimToNull(root.getString("emby_line")),
               this.trimToNull(root.getString("emby_whitelist_line")),
               this.trimToNull(root.getString("bot_token")),
               this.trimToNull(this.firstJsonId(root.get("group"))),
               this.trimToNull(this.firstJsonId(root.get("owner"))),
               checkinMin,
               checkinMax
            );
         } catch (Exception var7) {
            throw new BizException(ResponseStatusEnum.BAD_REQUEST.getCode(), "EmbyBoss config.json 解析失败，请检查是否为完整 JSON");
         }
      }
   }

   private String firstJsonId(Object value) {
      if (value == null) {
         return null;
      } else if (value instanceof JSONArray array) {
         return array.isEmpty() ? null : String.valueOf(array.get(0));
      } else {
         return String.valueOf(value);
      }
   }

   private void syncConfigFileData(
      EmbyBossMigrationServiceImpl.EmbyBossConfigSnapshot sourceConfig,
      EmbyBossMigrationRequest request,
      EmbyBossMigrationResultResponse result,
      EmbyInfo embyInfo,
      boolean dryRun
   ) {
      if (sourceConfig != null) {
         this.syncEmbyInfoConfig(sourceConfig, request, result, embyInfo, dryRun);
         this.syncHostLines(sourceConfig, request, result, dryRun);
         this.syncTelegramConfig(sourceConfig, request, result, dryRun);
      }
   }

   private void syncEmbyInfoConfig(
      EmbyBossMigrationServiceImpl.EmbyBossConfigSnapshot sourceConfig,
      EmbyBossMigrationRequest request,
      EmbyBossMigrationResultResponse result,
      EmbyInfo embyInfo,
      boolean dryRun
   ) {
      if (Boolean.TRUE.equals(request.getSyncEmbyServerConfig())) {
         boolean changed = false;
         String embyUrl = this.normalizeEmbyServerUrl(sourceConfig.embyUrl());
         if (this.shouldSyncEmbyUrl(embyInfo.getEmbyUrl(), embyUrl, request)) {
            embyInfo.setEmbyUrl(embyUrl);
            changed = true;
            EmbyBossMigrationServiceImpl.UrlParts parts = this.parseUrlParts(embyUrl);
            if (parts != null) {
               embyInfo.setEmbyAgreement(parts.protocol());
               embyInfo.setEmbyPort(String.valueOf(parts.port()));
            }
         }

         if (StringUtils.hasText(sourceConfig.embyApi())
            && this.shouldReplace(embyInfo.getEmbyApikey(), request)
            && !sourceConfig.embyApi().equals(embyInfo.getEmbyApikey())) {
            embyInfo.setEmbyApikey(sourceConfig.embyApi());
            changed = true;
         }

         if (changed) {
            result.setEmbyInfoConfigUpdated(result.getEmbyInfoConfigUpdated() + 1L);
            if (!dryRun) {
               embyInfo.setUpdateDatetime(new Date());
               this.embyInfoService.updateById(embyInfo);
            }
         }
      }
   }

   private void syncHostLines(
      EmbyBossMigrationServiceImpl.EmbyBossConfigSnapshot sourceConfig,
      EmbyBossMigrationRequest request,
      EmbyBossMigrationResultResponse result,
      boolean dryRun
   ) {
      if (Boolean.TRUE.equals(request.getSyncHostLines())) {
         EmbyBossMigrationServiceImpl.HostLineEndpoint commonLine = this.resolveHostLineEndpoint(
            sourceConfig.embyLine(), sourceConfig.embyUrl(), "EmbyBoss 普通线路", 0
         );
         if (commonLine != null) {
            this.upsertHostLine(commonLine, request, result, dryRun);
         } else if (StringUtils.hasText(sourceConfig.embyLine()) || StringUtils.hasText(sourceConfig.embyUrl())) {
            result.addWarning("未能从 emby_line / emby_url 解析普通线路地址，已跳过普通线路同步");
         }

         EmbyBossMigrationServiceImpl.HostLineEndpoint whitelistLine = this.resolveHostLineEndpoint(sourceConfig.embyWhitelistLine(), null, "EmbyBoss 白名单线路", 1);
         if (whitelistLine != null) {
            this.upsertHostLine(whitelistLine, request, result, dryRun);
         } else if (StringUtils.hasText(sourceConfig.embyWhitelistLine())) {
            result.addWarning("未能从 emby_whitelist_line 解析白名单线路地址，已跳过白名单线路同步");
         }
      }
   }

   private EmbyBossMigrationServiceImpl.HostLineEndpoint resolveHostLineEndpoint(String lineText, String fallbackUrl, String lineName, int lineType) {
      String candidate = this.extractAddressCandidate(lineText);
      if (!StringUtils.hasText(candidate)) {
         candidate = fallbackUrl;
      }

      if (!StringUtils.hasText(candidate)) {
         return null;
      } else {
         EmbyBossMigrationServiceImpl.UrlParts parts = this.parseUrlParts(candidate);
         if (parts == null) {
            return null;
         } else {
            String remark = StringUtils.hasText(lineText) ? lineText : fallbackUrl;
            return new EmbyBossMigrationServiceImpl.HostLineEndpoint(
               lineName, lineType, parts.protocol(), parts.host(), parts.port(), this.truncate("EmbyBoss config.json: " + remark, 500)
            );
         }
      }
   }

   private String extractAddressCandidate(String text) {
      if (!StringUtils.hasText(text)) {
         return null;
      } else {
         Matcher urlMatcher = URL_PATTERN.matcher(text);
         if (urlMatcher.find()) {
            return this.cleanAddressCandidate(urlMatcher.group());
         } else {
            Matcher hostMatcher = HOST_PATTERN.matcher(text);
            return hostMatcher.find() ? this.cleanAddressCandidate(hostMatcher.group()) : null;
         }
      }
   }

   private String cleanAddressCandidate(String candidate) {
      return !StringUtils.hasText(candidate) ? null : candidate.trim().replaceAll("[,，。；;)）\\]>]+$", "");
   }

   private void upsertHostLine(
      EmbyBossMigrationServiceImpl.HostLineEndpoint endpoint, EmbyBossMigrationRequest request, EmbyBossMigrationResultResponse result, boolean dryRun
   ) {
      HostLine existing = this.hostLineMapper
         .selectOne(
            new LambdaQueryWrapper<HostLine>()
               .eq(HostLine::getEmbyInfoId, request.getEmbyInfoId())
               .eq(HostLine::getLineType, Integer.valueOf(endpoint.lineType()))
               .eq(HostLine::getLineName, endpoint.lineName())
               .last("limit 1")
         );
      if (existing == null) {
         result.setHostLineInserted(result.getHostLineInserted() + 1L);
         if (!dryRun) {
            HostLine line = new HostLine();
            this.fillHostLine(line, endpoint, request.getEmbyInfoId(), endpoint.lineType() == 0 ? 10 : 20);
            this.hostLineMapper.insert(line);
         }
      } else {
         if (Boolean.TRUE.equals(request.getOverwriteExisting()) && this.hostLineChanged(existing, endpoint)) {
            result.setHostLineUpdated(result.getHostLineUpdated() + 1L);
            if (!dryRun) {
               this.fillHostLine(existing, endpoint, request.getEmbyInfoId(), existing.getSortNo() == null ? 10 : existing.getSortNo());
               this.hostLineMapper.updateById(existing);
            }
         }
      }
   }

   private void fillHostLine(HostLine line, EmbyBossMigrationServiceImpl.HostLineEndpoint endpoint, Long embyInfoId, Integer sortNo) {
      line.setEmbyInfoId(embyInfoId);
      line.setLineName(endpoint.lineName());
      line.setLineType(endpoint.lineType());
      line.setProtocol(endpoint.protocol());
      line.setDomain(endpoint.host());
      line.setPort(endpoint.port());
      line.setIsDisplay(1);
      line.setEnabled(1);
      line.setSortNo(sortNo);
      line.setRemark(endpoint.remark());
      line.setDelFlag(0);
      if (line.getCreateDatetime() == null) {
         line.setCreateDatetime(new Date());
      }

      line.setUpdateDatetime(new Date());
   }

   private boolean hostLineChanged(HostLine line, EmbyBossMigrationServiceImpl.HostLineEndpoint endpoint) {
      return !endpoint.protocol().equals(line.getProtocol())
         || !endpoint.host().equals(line.getDomain())
         || line.getPort() == null
         || line.getPort() != endpoint.port()
         || line.getLineType() == null
         || line.getLineType() != endpoint.lineType()
         || line.getEnabled() == null
         || line.getEnabled() != 1
         || line.getIsDisplay() == null
         || line.getIsDisplay() != 1
         || !endpoint.remark().equals(line.getRemark());
   }

   private void syncTelegramConfig(
      EmbyBossMigrationServiceImpl.EmbyBossConfigSnapshot sourceConfig,
      EmbyBossMigrationRequest request,
      EmbyBossMigrationResultResponse result,
      boolean dryRun
   ) {
      if (Boolean.TRUE.equals(request.getSyncTelegramConfig())) {
         boolean hasTelegramConfig = StringUtils.hasText(sourceConfig.botToken())
            || StringUtils.hasText(sourceConfig.groupChatId())
            || StringUtils.hasText(sourceConfig.ownerId())
            || sourceConfig.checkinMin() != null
            || sourceConfig.checkinMax() != null;
         if (hasTelegramConfig) {
            NotifyChannel channel = this.notifyChannelMapper
               .selectOne(new LambdaQueryWrapper<NotifyChannel>().eq(NotifyChannel::getIconType, "telegram").last("limit 1"));
            JSONObject params = this.parseNotifyChannelParams(channel);
            boolean changed = channel == null;
            if (StringUtils.hasText(sourceConfig.groupChatId())
               && this.shouldReplace(params.getString("botChatGroupId"), request)
               && !sourceConfig.groupChatId().equals(params.getString("botChatGroupId"))) {
               params.put("botChatGroupId", sourceConfig.groupChatId());
               changed = true;
            }

            if (StringUtils.hasText(sourceConfig.ownerId())
               && this.shouldReplace(params.getString("botChatId"), request)
               && !sourceConfig.ownerId().equals(params.getString("botChatId"))) {
               params.put("botChatId", sourceConfig.ownerId());
               changed = true;
            }

            if (sourceConfig.checkinMin() != null && !sourceConfig.checkinMin().equals(params.getInteger("checkinBaseMin"))) {
               params.put("checkinBaseMin", sourceConfig.checkinMin());
               changed = true;
            }

            if (sourceConfig.checkinMax() != null && !sourceConfig.checkinMax().equals(params.getInteger("checkinBaseMax"))) {
               params.put("checkinBaseMax", sourceConfig.checkinMax());
               changed = true;
            }

            boolean enableBot = StringUtils.hasText(params.getString("botChatGroupId"));
            if (channel != null && enableBot && (channel.getEnabled() == null || channel.getEnabled() != 1)) {
               changed = true;
            }

            if (changed) {
               result.setPointsBotConfigUpdated(result.getPointsBotConfigUpdated() + 1L);
               if (!dryRun) {
                  if (channel == null) {
                     channel = new NotifyChannel();
                     channel.setName("Telegram 机器人");
                     channel.setDesc("Telegram MTProto 统一机器人配置");
                     channel.setIconType("telegram");
                     channel.setCustomIcon(null);
                     channel.setCreateDatetime(new Date());
                     channel.setDelFlag(0);
                  }

                  channel.setEnabled(enableBot ? 1 : 0);
                  channel.setParams(params.toJSONString());
                  channel.setUpdateDatetime(new Date());
                  if (channel.getId() == null) {
                     this.notifyChannelMapper.insert(channel);
                  } else {
                     this.notifyChannelMapper.updateById(channel);
                  }
               }
            }
         }
      }
   }

   private JSONObject parseNotifyChannelParams(NotifyChannel channel) {
      if (channel != null && StringUtils.hasText(channel.getParams())) {
         try {
            JSONObject parsed = JSONObject.parseObject(channel.getParams());
            return parsed == null ? new JSONObject() : parsed;
         } catch (Exception var3) {
            return new JSONObject();
         }
      } else {
         return new JSONObject();
      }
   }

   private boolean shouldReplace(String existing, EmbyBossMigrationRequest request) {
      return Boolean.TRUE.equals(request.getOverwriteExisting()) || !StringUtils.hasText(existing);
   }

   private boolean shouldSyncEmbyUrl(String existing, String next, EmbyBossMigrationRequest request) {
      if (!StringUtils.hasText(next) || next.equals(existing)) {
         return false;
      } else {
         return this.shouldReplace(existing, request) ? true : next.equals(this.normalizeEmbyServerUrl(existing));
      }
   }

   private String normalizeEmbyServerUrl(String url) {
      EmbyBossMigrationServiceImpl.UrlParts parts = this.parseUrlParts(url);
      if (parts == null) {
         return this.trimToNull(url);
      } else {
         String port = this.isDefaultPort(parts.protocol(), parts.port()) ? "" : ":" + parts.port();
         return parts.protocol() + "://" + parts.host() + port + "/emby/";
      }
   }

   private EmbyBossMigrationServiceImpl.UrlParts parseUrlParts(String rawUrl) {
      if (!StringUtils.hasText(rawUrl)) {
         return null;
      } else {
         String value = this.cleanAddressCandidate(rawUrl);
         if (!StringUtils.hasText(value)) {
            return null;
         } else {
            if (!value.matches("(?i)^https?://.*")) {
               value = "https://" + value;
            }

            try {
               URI uri = new URI(value);
               String protocol = uri.getScheme() == null ? "" : uri.getScheme().toLowerCase(Locale.ROOT);
               if (!"http".equals(protocol) && !"https".equals(protocol)) {
                  return null;
               } else {
                  String host = uri.getHost();
                  if (!StringUtils.hasText(host)) {
                     return null;
                  } else {
                     int port = uri.getPort() > 0 ? uri.getPort() : ("https".equals(protocol) ? 443 : 80);
                     return new EmbyBossMigrationServiceImpl.UrlParts(protocol, host, port);
                  }
               }
            } catch (IllegalArgumentException | URISyntaxException var7) {
               return null;
            }
         }
      }
   }

   private boolean isDefaultPort(String protocol, int port) {
      return "https".equals(protocol) && port == 443 || "http".equals(protocol) && port == 80;
   }

   private Map<String, EmbyBossMigrationServiceImpl.LevelSnapshot> resolveLevels(
      EmbyBossMigrationRequest request, EmbyBossMigrationResultResponse result, boolean dryRun
   ) {
      Map<String, EmbyBossMigrationServiceImpl.LevelSnapshot> levels = new HashMap<>();
      levels.put("a", this.ensureFixedLevel("a", this.levelName(request.getLevelNameA(), "白名单用户"), 400, request, result, dryRun));
      levels.put("b", this.ensureFixedLevel("b", this.levelName(request.getLevelNameB(), "普通用户"), 300, request, result, dryRun));
      levels.put("c", this.ensureFixedLevel("c", this.levelName(request.getLevelNameC(), "封禁用户"), 200, request, result, dryRun));
      levels.put("d", this.ensureFixedLevel("d", this.levelName(request.getLevelNameD(), "未注册用户"), 100, request, result, dryRun));
      return levels;
   }

   private EmbyBossMigrationServiceImpl.LevelSnapshot ensureFixedLevel(
      String code, String levelName, int sort, EmbyBossMigrationRequest request, EmbyBossMigrationResultResponse result, boolean dryRun
   ) {
      if (!Boolean.TRUE.equals(request.getSyncLevelConfigs())) {
         return new EmbyBossMigrationServiceImpl.LevelSnapshot(null, levelName);
      } else {
         String marker = "embyboss:fixed:" + code;
         PointsBotLevelConfig existing = this.pointsBotLevelConfigMapper
            .selectOne(
               new LambdaQueryWrapper<PointsBotLevelConfig>().eq(PointsBotLevelConfig::getRemark, marker).last("limit 1")
            );
         if (existing == null) {
            result.setLevelConfigInserted(result.getLevelConfigInserted() + 1L);
            if (dryRun) {
               return new EmbyBossMigrationServiceImpl.LevelSnapshot(null, levelName);
            } else {
               PointsBotLevelConfig config = new PointsBotLevelConfig();
               config.setLevelName(levelName);
               config.setMinPoints(0);
               config.setEnabled(0);
               config.setSort(sort);
               config.setRemark(marker);
               this.pointsBotLevelConfigMapper.insert(config);
               return new EmbyBossMigrationServiceImpl.LevelSnapshot(config.getId(), levelName);
            }
         } else {
            boolean changed = !levelName.equals(existing.getLevelName())
               || existing.getEnabled() == null
               || existing.getEnabled() != 0
               || existing.getSort() == null
               || existing.getSort() != sort;
            if (changed && Boolean.TRUE.equals(request.getOverwriteExisting())) {
               result.setLevelConfigUpdated(result.getLevelConfigUpdated() + 1L);
               if (!dryRun) {
                  existing.setLevelName(levelName);
                  existing.setEnabled(0);
                  existing.setSort(sort);
                  existing.setRemark(marker);
                  this.pointsBotLevelConfigMapper.updateById(existing);
               }
            }

            return new EmbyBossMigrationServiceImpl.LevelSnapshot(existing.getId(), levelName);
         }
      }
   }

   private void migrateEmbyTable(
      Connection sourceConnection,
      EmbyBossMigrationRequest request,
      EmbyBossMigrationResultResponse result,
      Map<String, EmbyBossMigrationServiceImpl.LevelSnapshot> levels,
      Long chatId,
      boolean dryRun
   ) throws SQLException {
      String sql = "SELECT tg, embyid, name, pwd, pwd2, lv, cr, ex, us, iv, ch\nFROM emby\n";

      try (
         PreparedStatement statement = sourceConnection.prepareStatement(sql);
         ResultSet rs = statement.executeQuery();
      ) {
         while (rs.next()) {
            EmbyBossMigrationServiceImpl.EmbyBossRow row = this.readEmbyRow(rs);
            this.migrateEmbyBossRow(row, request, result, levels, chatId, dryRun, true);
         }
      }
   }

   private void migrateEmby2Table(
      Connection sourceConnection,
      EmbyBossMigrationRequest request,
      EmbyBossMigrationResultResponse result,
      Map<String, EmbyBossMigrationServiceImpl.LevelSnapshot> levels,
      boolean dryRun
   ) throws SQLException {
      String sql = "SELECT embyid, name, pwd, pwd2, lv, cr, ex, expired\nFROM emby2\n";

      try (
         PreparedStatement statement = sourceConnection.prepareStatement(sql);
         ResultSet rs = statement.executeQuery();
      ) {
         while (rs.next()) {
            EmbyBossMigrationServiceImpl.EmbyBossRow row = new EmbyBossMigrationServiceImpl.EmbyBossRow();
            row.embyId = this.trimToNull(rs.getString("embyid"));
            row.name = this.trimToNull(rs.getString("name"));
            row.password = this.trimToNull(rs.getString("pwd"));
            row.safeCode = this.trimToNull(rs.getString("pwd2"));
            row.levelCode = this.normalizeLevel(rs.getString("lv"));
            row.createdAt = rs.getTimestamp("cr");
            row.expiresAt = rs.getTimestamp("ex");
            row.expired = this.getInteger(rs, "expired");
            this.migrateEmbyBossRow(row, request, result, levels, null, dryRun, false);
         }
      }
   }

   private void migrateEmbyBossRow(
      EmbyBossMigrationServiceImpl.EmbyBossRow row,
      EmbyBossMigrationRequest request,
      EmbyBossMigrationResultResponse result,
      Map<String, EmbyBossMigrationServiceImpl.LevelSnapshot> levels,
      Long chatId,
      boolean dryRun,
      boolean withTelegram
   ) {
      result.incrementLevel(row.levelCode);
      EmbyBossMigrationServiceImpl.LevelSnapshot level = levels.getOrDefault(row.levelCode, new EmbyBossMigrationServiceImpl.LevelSnapshot(null, row.levelCode));
      Long embyUserId = null;
      if (Boolean.TRUE.equals(request.getSyncEmbyUsers())) {
         embyUserId = this.upsertEmbyUser(row, request, result, level, dryRun);
      } else if (this.hasEmbyAccount(row)) {
         EmbyUser existing = this.findExistingEmbyUser(row, request.getEmbyInfoId());
         embyUserId = existing == null ? null : existing.getId();
      }

      if (withTelegram && row.tg != null && row.tg > 0L) {
         if (Boolean.TRUE.equals(request.getSyncPointsBotUsers()) && chatId != null) {
            this.upsertPointsBotUser(row, chatId, request, result, level, dryRun);
         }

         if (embyUserId != null && Boolean.TRUE.equals(request.getSyncOauthBindings())) {
            this.upsertTelegramBinding(embyUserId, row, request, result, dryRun);
         }
      }

      if (embyUserId != null && Boolean.TRUE.equals(request.getSyncUserPoints())) {
         this.upsertUserPoints(embyUserId, row.points(), request, result, dryRun);
      }
   }

   private Long upsertEmbyUser(
      EmbyBossMigrationServiceImpl.EmbyBossRow row,
      EmbyBossMigrationRequest request,
      EmbyBossMigrationResultResponse result,
      EmbyBossMigrationServiceImpl.LevelSnapshot level,
      boolean dryRun
   ) {
      if (!this.hasEmbyAccount(row)) {
         result.setSkippedNoAccountRows(result.getSkippedNoAccountRows() + 1L);
         return null;
      } else {
         result.setSourceMigratableUsers(result.getSourceMigratableUsers() + 1L);
         EmbyUser existing = this.findExistingEmbyUser(row, request.getEmbyInfoId());
         if (existing == null) {
            result.setEmbyUserInserted(result.getEmbyUserInserted() + 1L);
            if (dryRun) {
               return null;
            } else {
               EmbyUser user = new EmbyUser();
               user.setIdentityGroupId(EmbyUserIdentityUtils.newIdentityGroupId());
               this.fillEmbyUser(user, row, request.getEmbyInfoId(), level);
               this.embyUserMapper.insert(user);
               return user.getId();
            }
         } else {
            if (Boolean.TRUE.equals(request.getOverwriteExisting())) {
               if (Integer.valueOf(1).equals(existing.getIsPrimaryAdmin())) {
                  throw new BizException(ResponseStatusEnum.PRIMARY_ADMIN_PROTECTED);
               }

               result.setEmbyUserUpdated(result.getEmbyUserUpdated() + 1L);
               if (!dryRun) {
                  this.fillEmbyUser(existing, row, request.getEmbyInfoId(), level);
                  this.embyUserMapper.updateById(existing);
               }
            }

            return existing.getId();
         }
      }
   }

   private void fillEmbyUser(EmbyUser user, EmbyBossMigrationServiceImpl.EmbyBossRow row, Long embyInfoId, EmbyBossMigrationServiceImpl.LevelSnapshot level) {
      boolean whitelistUser = "a".equals(row.levelCode);
      user.setEmbyUserId(row.embyId);
      user.setEmbyUserName(row.name);
      user.setEmbyUserPassword(this.embyBossPasswordHash(row));
      user.setIsAdmin(0);
      user.setUserStatus(whitelistUser ? 0 : (this.disabled(row) ? 1 : 0));
      user.setExpirationDate(whitelistUser ? null : this.toDate(row.expiresAt));
      user.setExpireDateCount(whitelistUser ? null : this.expireDateCount(row.expiresAt));
      user.setRemarks(this.buildRemark(row, level));
      user.setRequestPackagesCount(0);
      user.setRegisterChannel(RegisterChannelEnum.USER_REGISTER.getCode());
      user.setEmbyInfoId(embyInfoId);
      user.setHostLineType(whitelistUser ? 1 : 0);
      user.setIsDistributor(0);
      if (user.getCreateDatetime() == null) {
         user.setCreateDatetime(this.toDate(row.createdAt));
      }

      user.setUpdateDatetime(new Date());
      user.setDelFlag(0);
   }

   private EmbyUser findExistingEmbyUser(EmbyBossMigrationServiceImpl.EmbyBossRow row, Long embyInfoId) {
      LambdaQueryWrapper<EmbyUser> wrapper = new LambdaQueryWrapper<EmbyUser>()
         .eq(EmbyUser::getEmbyInfoId, embyInfoId)
         .eq(BaseEntity::getDelFlag, Integer.valueOf(0))
         .and(query -> {
               if (StringUtils.hasText(row.embyId)) {
                  query.eq(EmbyUser::getEmbyUserId, row.embyId);
                  if (StringUtils.hasText(row.name)) {
                     query.or().eq(EmbyUser::getEmbyUserName, row.name);
                  }
               } else {
                  query.eq(EmbyUser::getEmbyUserName, row.name);
               }
            })
         .last("limit 1");
      return this.embyUserMapper.selectOne(wrapper);
   }

   private void upsertPointsBotUser(
      EmbyBossMigrationServiceImpl.EmbyBossRow row,
      Long chatId,
      EmbyBossMigrationRequest request,
      EmbyBossMigrationResultResponse result,
      EmbyBossMigrationServiceImpl.LevelSnapshot level,
      boolean dryRun
   ) {
      PointsBotUser existing = this.pointsBotUserMapper
         .selectOne(
            new LambdaQueryWrapper<PointsBotUser>()
               .eq(PointsBotUser::getChatId, chatId)
               .eq(PointsBotUser::getUserId, row.tg)
               .last("limit 1")
         );
      long nextPoints = row.points();
      if (existing == null) {
         result.setPointsBotUserInserted(result.getPointsBotUserInserted() + 1L);
         if (!dryRun) {
            PointsBotUser user = new PointsBotUser();
            this.fillPointsBotUser(user, row, chatId, level, nextPoints);
            this.pointsBotUserMapper.insert(user);
            this.insertPointLedgerIfNeeded(row, chatId, nextPoints, request, result, dryRun);
         }
      } else {
         if (Boolean.TRUE.equals(request.getOverwriteExisting())) {
            result.setPointsBotUserUpdated(result.getPointsBotUserUpdated() + 1L);
            long oldPoints = existing.getPoints() == null ? 0L : existing.getPoints();
            if (!dryRun) {
               this.fillPointsBotUser(existing, row, chatId, level, nextPoints);
               this.pointsBotUserMapper.updateById(existing);
               this.insertPointLedgerIfNeeded(row, chatId, nextPoints - oldPoints, request, result, dryRun);
            }
         }
      }
   }

   private void fillPointsBotUser(
      PointsBotUser user, EmbyBossMigrationServiceImpl.EmbyBossRow row, Long chatId, EmbyBossMigrationServiceImpl.LevelSnapshot level, long points
   ) {
      user.setChatId(chatId);
      user.setUserId(row.tg);
      user.setUsername(this.displayText(row));
      user.setDisplayName(this.displayText(row));
      user.setLevelId(level.id());
      user.setLevelName(level.name());
      user.setPoints(points);
      user.setCheckinStreak(user.getCheckinStreak() == null ? 0 : user.getCheckinStreak());
      user.setLastCheckinDate(this.toLocalDate(row.checkinAt));
      user.setDailyMessagePoints(0);
      user.setDailyMessageCount(0);
      user.setUpdateDatetime(new Date());
      user.setDelFlag(0);
   }

   private void upsertTelegramBinding(
      Long embyUserId, EmbyBossMigrationServiceImpl.EmbyBossRow row, EmbyBossMigrationRequest request, EmbyBossMigrationResultResponse result, boolean dryRun
   ) {
      UserOauthBinding existing = this.userOauthBindingMapper
         .selectOne(
            new LambdaQueryWrapper<UserOauthBinding>()
               .eq(UserOauthBinding::getProvider, "telegram")
               .eq(UserOauthBinding::getProviderUserId, String.valueOf(row.tg))
               .last("limit 1")
         );
      if (existing == null) {
         result.setOauthBindingInserted(result.getOauthBindingInserted() + 1L);
         if (!dryRun) {
            UserOauthBinding binding = new UserOauthBinding();
            this.fillTelegramBinding(binding, embyUserId, row);
            this.userOauthBindingMapper.insert(binding);
         }
      } else {
         if (Boolean.TRUE.equals(request.getOverwriteExisting())) {
            result.setOauthBindingUpdated(result.getOauthBindingUpdated() + 1L);
            if (!dryRun) {
               this.fillTelegramBinding(existing, embyUserId, row);
               this.userOauthBindingMapper.updateById(existing);
            }
         }
      }
   }

   private void fillTelegramBinding(UserOauthBinding binding, Long embyUserId, EmbyBossMigrationServiceImpl.EmbyBossRow row) {
      binding.setUserId(embyUserId);
      binding.setProvider("telegram");
      binding.setProviderUserId(String.valueOf(row.tg));
      binding.setProviderUsername(this.displayText(row));
      binding.setExtraData("{\"source\":\"embyboss\"}");
      binding.setDelFlag(0);
      if (binding.getCreateDatetime() == null) {
         binding.setCreateDatetime(new Date());
         binding.setCreateUserId(embyUserId);
         binding.setCreateUserName("EmbyBoss迁移");
      }

      binding.setUpdateDatetime(new Date());
      binding.setUpdateUserId(embyUserId);
      binding.setUpdateUserName("EmbyBoss迁移");
   }

   private void upsertUserPoints(Long userId, long points, EmbyBossMigrationRequest request, EmbyBossMigrationResultResponse result, boolean dryRun) {
      UserPoints existing = this.userPointsMapper
         .selectOne(new LambdaQueryWrapper<UserPoints>().eq(UserPoints::getUserId, userId).last("limit 1"));
      int balance = this.safeInt(points);
      if (existing == null) {
         result.setUserPointsInserted(result.getUserPointsInserted() + 1L);
         if (!dryRun) {
            UserPoints userPoints = new UserPoints();
            userPoints.setUserId(userId);
            userPoints.setPointsBalance(balance);
            userPoints.setTotalEarned(Math.max(balance, 0));
            userPoints.setTotalSpent(0);
            userPoints.setCreateDatetime(new Date());
            userPoints.setDelFlag(0);
            this.userPointsMapper.insert(userPoints);
         }
      } else {
         if (Boolean.TRUE.equals(request.getOverwriteExisting())) {
            result.setUserPointsUpdated(result.getUserPointsUpdated() + 1L);
            if (!dryRun) {
               existing.setPointsBalance(balance);
               existing.setTotalEarned(Math.max(existing.getTotalEarned() == null ? 0 : existing.getTotalEarned(), balance));
               if (existing.getTotalSpent() == null) {
                  existing.setTotalSpent(0);
               }

               existing.setDelFlag(0);
               this.userPointsMapper.updateById(existing);
            }
         }
      }
   }

   private void insertPointLedgerIfNeeded(
      EmbyBossMigrationServiceImpl.EmbyBossRow row,
      Long chatId,
      long delta,
      EmbyBossMigrationRequest request,
      EmbyBossMigrationResultResponse result,
      boolean dryRun
   ) {
      if (Boolean.TRUE.equals(request.getSyncPointLedger()) && chatId != null && row.tg != null && delta != 0L) {
         result.setPointLedgerInserted(result.getPointLedgerInserted() + 1L);
         if (!dryRun) {
            PointsBotLedger ledger = new PointsBotLedger();
            ledger.setChatId(chatId);
            ledger.setUserId(row.tg);
            ledger.setDelta(this.safeInt(delta));
            ledger.setReason("embyboss_migration");
            ledger.setRefId(row.embyId);
            this.pointsBotLedgerMapper.insert(ledger);
         }
      }
   }

   private EmbyBossMigrationServiceImpl.EmbyBossRow readEmbyRow(ResultSet rs) throws SQLException {
      EmbyBossMigrationServiceImpl.EmbyBossRow row = new EmbyBossMigrationServiceImpl.EmbyBossRow();
      row.tg = this.getLong(rs, "tg");
      row.embyId = this.trimToNull(rs.getString("embyid"));
      row.name = this.trimToNull(rs.getString("name"));
      row.password = this.trimToNull(rs.getString("pwd"));
      row.safeCode = this.trimToNull(rs.getString("pwd2"));
      row.levelCode = this.normalizeLevel(rs.getString("lv"));
      row.createdAt = rs.getTimestamp("cr");
      row.expiresAt = rs.getTimestamp("ex");
      row.us = this.getInteger(rs, "us");
      row.iv = this.getInteger(rs, "iv");
      row.checkinAt = rs.getTimestamp("ch");
      return row;
   }

   private Connection openSourceConnection(EmbyBossMigrationRequest request) throws SQLException {
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

   private String resolveJdbcUrl(EmbyBossMigrationRequest request) {
      String jdbcUrl = request.getJdbcUrl();
      if (!StringUtils.hasText(jdbcUrl)) {
         String hostPort = request.getHostPort() == null ? "" : request.getHostPort().trim();
         String databaseName = request.getDatabaseName() == null ? "" : request.getDatabaseName().trim();
         if (!StringUtils.hasText(databaseName)) {
            databaseName = "embyboss";
         }

         if (!StringUtils.hasText(hostPort)) {
            throw new BizException(ResponseStatusEnum.BAD_REQUEST.getCode(), "EmbyBoss 源库 IP:端口不能为空");
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
         throw new BizException(ResponseStatusEnum.BAD_REQUEST.getCode(), "EmbyBoss 源库 IP:端口不能为空");
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

   private boolean tableExists(Connection connection, String tableName) throws SQLException {
      boolean var5;
      try (PreparedStatement statement = connection.prepareStatement(
            "SELECT COUNT(1)\nFROM information_schema.TABLES\nWHERE TABLE_SCHEMA = DATABASE()\n  AND TABLE_NAME = ?\n"
         )) {
         statement.setString(1, tableName);

         try (ResultSet rs = statement.executeQuery()) {
            var5 = rs.next() && rs.getLong(1) > 0L;
         }
      }

      return var5;
   }

   private long countRows(Connection connection, String tableName) throws SQLException {
      long var5;
      try (
         PreparedStatement statement = connection.prepareStatement("SELECT COUNT(1) FROM `" + tableName + "`");
         ResultSet rs = statement.executeQuery();
      ) {
         var5 = rs.next() ? rs.getLong(1) : 0L;
      }

      return var5;
   }

   private boolean hasEmbyAccount(EmbyBossMigrationServiceImpl.EmbyBossRow row) {
      return StringUtils.hasText(row.embyId) && StringUtils.hasText(row.name);
   }

   private String buildRemark(EmbyBossMigrationServiceImpl.EmbyBossRow row, EmbyBossMigrationServiceImpl.LevelSnapshot level) {
      StringBuilder remark = new StringBuilder("EmbyBoss迁移");
      remark.append(" lv=").append(row.levelCode);
      if (level != null && StringUtils.hasText(level.name())) {
         remark.append("(").append(level.name()).append(")");
      }

      if (row.tg != null) {
         remark.append(" tg=").append(row.tg);
      }

      if (row.us != null) {
         remark.append(" us=").append(row.us);
      }

      return this.truncate(remark.toString(), 50);
   }

   private boolean disabled(EmbyBossMigrationServiceImpl.EmbyBossRow row) {
      if (!"c".equals(row.levelCode) && (row.expired == null || row.expired <= 0)) {
         Date expireDate = this.toDate(row.expiresAt);
         return expireDate != null && expireDate.before(new Date());
      } else {
         return true;
      }
   }

   private Long expireDateCount(Timestamp expiresAt) {
      LocalDate date = this.toLocalDate(expiresAt);
      return date == null ? null : ChronoUnit.DAYS.between(LocalDate.now(), date);
   }

   private Date toDate(Timestamp timestamp) {
      return timestamp == null ? null : new Date(timestamp.getTime());
   }

   private LocalDate toLocalDate(Timestamp timestamp) {
      return timestamp == null ? null : timestamp.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
   }

   private Long getLong(ResultSet rs, String column) throws SQLException {
      long value = rs.getLong(column);
      return rs.wasNull() ? null : value;
   }

   private Integer getInteger(ResultSet rs, String column) throws SQLException {
      int value = rs.getInt(column);
      return rs.wasNull() ? null : value;
   }

   private long safePoints(Integer value) {
      return Math.max(0L, value == null ? 0L : value.longValue());
   }

   private int safeInt(long value) {
      if (value > 2147483647L) {
         return Integer.MAX_VALUE;
      } else {
         return value < -2147483648L ? Integer.MIN_VALUE : (int)value;
      }
   }

   private String embyBossPasswordHash(EmbyBossMigrationServiceImpl.EmbyBossRow row) {
      if (row != null && StringUtils.hasText(row.safeCode)) {
         return SaSecureUtil.md5(row.safeCode.trim());
      } else {
         return row == null ? null : this.passwordHash(row.password);
      }
   }

   private String passwordHash(String password) {
      if (!StringUtils.hasText(password)) {
         return null;
      } else {
         String value = password.trim();
         return value.matches("(?i)^[0-9a-f]{32}$") ? value.toLowerCase(Locale.ROOT) : SaSecureUtil.md5(value);
      }
   }

   private String displayText(EmbyBossMigrationServiceImpl.EmbyBossRow row) {
      if (StringUtils.hasText(row.name)) {
         return this.truncate(row.name, 100);
      } else {
         return row.tg == null ? "EmbyBoss用户" : "TG" + row.tg;
      }
   }

   private String normalizeLevel(String value) {
      String level = value == null ? "" : value.trim().toLowerCase(Locale.ROOT);
      return !"a".equals(level) && !"b".equals(level) && !"c".equals(level) && !"d".equals(level) ? "d" : level;
   }

   private String levelName(String configured, String fallback) {
      return this.truncate(StringUtils.hasText(configured) ? configured.trim() : fallback, 50);
   }

   private String trimToNull(String value) {
      return !StringUtils.hasText(value) ? null : value.trim();
   }

   private String truncate(String value, int maxLength) {
      return value != null && value.length() > maxLength ? value.substring(0, maxLength) : value;
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

   @Generated
   public EmbyBossMigrationServiceImpl(
      final EmbyInfoService embyInfoService,
      final EmbyUserMapper embyUserMapper,
      final HostLineMapper hostLineMapper,
      final NotifyChannelMapper notifyChannelMapper,
      final PointsBotUserMapper pointsBotUserMapper,
      final PointsBotLedgerMapper pointsBotLedgerMapper,
      final PointsBotLevelConfigMapper pointsBotLevelConfigMapper,
      final UserOauthBindingMapper userOauthBindingMapper,
      final UserPointsMapper userPointsMapper,
      final PointsBotConfigService pointsBotConfigService
   ) {
      this.embyInfoService = embyInfoService;
      this.embyUserMapper = embyUserMapper;
      this.hostLineMapper = hostLineMapper;
      this.notifyChannelMapper = notifyChannelMapper;
      this.pointsBotUserMapper = pointsBotUserMapper;
      this.pointsBotLedgerMapper = pointsBotLedgerMapper;
      this.pointsBotLevelConfigMapper = pointsBotLevelConfigMapper;
      this.userOauthBindingMapper = userOauthBindingMapper;
      this.userPointsMapper = userPointsMapper;
      this.pointsBotConfigService = pointsBotConfigService;
   }

   private static record EmbyBossConfigSnapshot(
      String embyUrl,
      String embyApi,
      String embyLine,
      String embyWhitelistLine,
      String botToken,
      String groupChatId,
      String ownerId,
      Integer checkinMin,
      Integer checkinMax
   ) {
   }

   private class EmbyBossRow {
      private Long tg;
      private String embyId;
      private String name;
      private String password;
      private String safeCode;
      private String levelCode = "d";
      private Timestamp createdAt;
      private Timestamp expiresAt;
      private Timestamp checkinAt;
      private Integer us;
      private Integer iv;
      private Integer expired;

      private long points() {
         return EmbyBossMigrationServiceImpl.this.safePoints(this.iv);
      }
   }

   private static record HostLineEndpoint(String lineName, int lineType, String protocol, String host, int port, String remark) {
   }

   private static record LevelSnapshot(Long id, String name) {
   }

   private static record UrlParts(String protocol, String host, int port) {
   }
}
