package com.una.embyhub.service.impl;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.una.embyhub.config.common.exception.BizException;
import com.una.embyhub.config.common.utils.ConfigCacheLoaderUtils;
import com.una.embyhub.mapper.EmbyUserMapper;
import com.una.embyhub.mapper.EmbyUserRegisterRecordMapper;
import com.una.embyhub.mapper.EmbyUserRenewRecordMapper;
import com.una.embyhub.mapper.SystemConfigMapper;
import com.una.embyhub.model.dto.logintransition.LoginTransitionDtos;
import com.una.embyhub.model.entity.BaseEntity;
import com.una.embyhub.model.entity.EmbyUser;
import com.una.embyhub.model.entity.EmbyUserRegisterRecord;
import com.una.embyhub.model.entity.EmbyUserRenewRecord;
import com.una.embyhub.model.entity.SystemConfig;
import com.una.embyhub.service.LoginTransitionService;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import lombok.Generated;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Service
public class LoginTransitionServiceImpl implements LoginTransitionService {
   @Generated
   private static final Logger log = LoggerFactory.getLogger(LoginTransitionServiceImpl.class);
   private static final int MAX_RULES = 12;
   private static final int MAX_TITLE_LENGTH = 40;
   private static final int MAX_SUBTITLE_LENGTH = 60;
   private static final int MIN_DURATION_MS = 800;
   private static final int MAX_DURATION_MS = 3000;
   private static final Set<String> PRESET_KEYS = Set.of("stardust", "aurora", "golden");
   private static final List<LoginTransitionDtos.Preset> PRESETS = List.of(
      new LoginTransitionDtos.Preset("stardust", "星港点火", "青蓝星港与玻璃舱启动，轻盈推镜入场"),
      new LoginTransitionDtos.Preset("aurora", "霓虹跃迁", "紫青极光穿城，三维镜头重击锁定称号"),
      new LoginTransitionDtos.Preset("golden", "曜金主城", "曜金能量汇聚未来主城，仪式感最强")
   );
   private static final List<LoginTransitionDtos.Rule> DEFAULT_RULES = List.of(
      new LoginTransitionDtos.Rule("monthly", 1, 30, "尊贵的月度 VIP", "30 天专属礼遇", "stardust", 1400, true),
      new LoginTransitionDtos.Rule("quarterly", 31, 90, "尊贵的季度 VIP", "90 天专属礼遇", "aurora", 1600, true),
      new LoginTransitionDtos.Rule("annual", 91, null, "尊贵的年度 VIP", "365 天专属礼遇", "golden", 1800, true)
   );
   private final SystemConfigMapper systemConfigMapper;
   private final EmbyUserMapper embyUserMapper;
   private final EmbyUserRegisterRecordMapper registerRecordMapper;
   private final EmbyUserRenewRecordMapper renewRecordMapper;
   private final ConfigCacheLoaderUtils configCacheLoaderUtils;

   public LoginTransitionServiceImpl(
      SystemConfigMapper systemConfigMapper,
      EmbyUserMapper embyUserMapper,
      EmbyUserRegisterRecordMapper registerRecordMapper,
      EmbyUserRenewRecordMapper renewRecordMapper,
      ConfigCacheLoaderUtils configCacheLoaderUtils
   ) {
      this.systemConfigMapper = systemConfigMapper;
      this.embyUserMapper = embyUserMapper;
      this.registerRecordMapper = registerRecordMapper;
      this.renewRecordMapper = renewRecordMapper;
      this.configCacheLoaderUtils = configCacheLoaderUtils;
   }

   @Override
   public LoginTransitionDtos.SettingsResponse getSettings() {
      SystemConfig config = this.findConfig();
      boolean enabled = config != null && Integer.valueOf(1).equals(config.getIsEnabled());
      return new LoginTransitionDtos.SettingsResponse(enabled, this.readRules(config), PRESETS);
   }

   @Transactional(
      rollbackFor = {Exception.class}
   )
   @Override
   public LoginTransitionDtos.SettingsResponse updateSettings(LoginTransitionDtos.UpdateRequest request) {
      if (request != null && request.enabled() != null) {
         List<LoginTransitionDtos.Rule> rules = this.normalizeRules(request.rules());
         SystemConfig config = this.findConfig();
         if (config == null) {
            config = new SystemConfig();
            config.setName("登录转场");
            config.setConfigKey("login_transition_config");
            config.setDescription("按用户最近一次注册或续费获赠时长，在登录成功后播放专属称号转场");
            config.setIsUpdate(1);
            config.setDelFlag(0);
         }

         config.setIsEnabled(Boolean.TRUE.equals(request.enabled()) ? 1 : 0);
         config.setConfigValue(this.serializeRules(rules));
         if (config.getId() == null) {
            this.systemConfigMapper.insert(config);
         } else {
            this.systemConfigMapper.updateById(config);
         }

         this.configCacheLoaderUtils.refreshCache();
         return new LoginTransitionDtos.SettingsResponse(Boolean.TRUE.equals(request.enabled()), rules, PRESETS);
      } else {
         throw new BizException("登录转场开关不能为空");
      }
   }

   @Override
   public LoginTransitionDtos.CurrentResponse resolveForUser(long userId) {
      SystemConfig config = this.findConfig();
      if (config != null && Integer.valueOf(1).equals(config.getIsEnabled())) {
         Integer grantedDays = this.resolveGrantedDays(userId);
         if (grantedDays != null && grantedDays >= 1) {
            LoginTransitionDtos.Rule matched = this.readRules(config)
               .stream()
               .filter(rule -> Boolean.TRUE.equals(rule.enabled()))
               .filter(rule -> grantedDays >= rule.minDays())
               .filter(rule -> rule.maxDays() == null || grantedDays <= rule.maxDays())
               .findFirst()
               .orElse(null);
            return matched == null
               ? LoginTransitionDtos.CurrentResponse.disabled(grantedDays)
               : new LoginTransitionDtos.CurrentResponse(true, grantedDays, matched);
         } else {
            return LoginTransitionDtos.CurrentResponse.disabled(grantedDays);
         }
      } else {
         return LoginTransitionDtos.CurrentResponse.disabled(null);
      }
   }

   private SystemConfig findConfig() {
      return this.systemConfigMapper
         .selectOne(
            new LambdaQueryWrapper<SystemConfig>().eq(SystemConfig::getConfigKey, "login_transition_config").last("LIMIT 1")
         );
   }

   private List<LoginTransitionDtos.Rule> readRules(SystemConfig config) {
      if (config != null && StringUtils.hasText(config.getConfigValue())) {
         try {
            JSONObject root = JSON.parseObject(config.getConfigValue());
            JSONArray values = root == null ? null : root.getJSONArray("rules");
            if (values != null && !values.isEmpty()) {
               List<LoginTransitionDtos.Rule> rules = new ArrayList<>();

               for (int index = 0; index < values.size(); index++) {
                  JSONObject value = values.getJSONObject(index);
                  if (value != null) {
                     rules.add(
                        new LoginTransitionDtos.Rule(
                           value.getString("id"),
                           value.getInteger("minDays"),
                           value.getInteger("maxDays"),
                           value.getString("title"),
                           value.getString("subtitle"),
                           value.getString("preset"),
                           value.getInteger("durationMs"),
                           value.getBoolean("enabled")
                        )
                     );
                  }
               }

               return this.normalizeRules(rules);
            } else {
               return DEFAULT_RULES;
            }
         } catch (Exception var7) {
            log.warn("登录转场配置无效，使用默认规则", (Throwable)var7);
            return DEFAULT_RULES;
         }
      } else {
         return DEFAULT_RULES;
      }
   }

   private List<LoginTransitionDtos.Rule> normalizeRules(List<LoginTransitionDtos.Rule> source) {
      if (source != null && !source.isEmpty()) {
         if (source.size() > 12) {
            throw new BizException("登录转场规则最多配置 12 条");
         } else {
            List<LoginTransitionDtos.Rule> rules = new ArrayList<>();
            Set<String> ids = new HashSet<>();
            int index = 0;

            while (index < source.size()) {
               LoginTransitionDtos.Rule rule = source.get(index);
               if (rule == null) {
                  throw new BizException("登录转场规则不能为空");
               }

               int minDays = rule.minDays() == null ? 0 : rule.minDays();
               Integer maxDays = rule.maxDays();
               if (minDays >= 1 && (maxDays == null || maxDays >= minDays)) {
                  String title = this.trim(rule.title());
                  String subtitle = this.trim(rule.subtitle());
                  if (StringUtils.hasText(title) && title.length() <= 40) {
                     if (subtitle.length() > 60) {
                        throw new BizException("副标题文案不能超过 60 个字符");
                     }

                     String preset = this.trim(rule.preset());
                     if (!PRESET_KEYS.contains(preset)) {
                        throw new BizException("登录转场动画预设无效");
                     }

                     int durationMs = rule.durationMs() == null ? 0 : rule.durationMs();
                     if (durationMs >= 800 && durationMs <= 3000) {
                        String id = this.trim(rule.id());
                        if (!StringUtils.hasText(id)) {
                           id = "rule-" + (index + 1);
                        }

                        if (!ids.add(id)) {
                           throw new BizException("登录转场规则标识不能重复");
                        }

                        rules.add(
                           new LoginTransitionDtos.Rule(id, minDays, maxDays, title, subtitle, preset, durationMs, rule.enabled() == null || rule.enabled())
                        );
                        index++;
                        continue;
                     }

                     throw new BizException("登录转场播放时长必须在 0.8 到 3 秒之间");
                  }

                  throw new BizException("称号文案不能为空且不能超过 40 个字符");
               }

               throw new BizException("登录转场时长范围无效");
            }

            rules.sort(Comparator.comparingInt(LoginTransitionDtos.Rule::minDays));

            for (int indexx = 1; indexx < rules.size(); indexx++) {
               LoginTransitionDtos.Rule previous = rules.get(indexx - 1);
               LoginTransitionDtos.Rule current = rules.get(indexx);
               if (previous.maxDays() == null || current.minDays() <= previous.maxDays()) {
                  throw new BizException("登录转场时长范围不能重叠");
               }
            }

            return List.copyOf(rules);
         }
      } else {
         throw new BizException("至少需要一条登录转场规则");
      }
   }

   private String serializeRules(List<LoginTransitionDtos.Rule> rules) {
      Map<String, Object> value = new LinkedHashMap<>();
      value.put("rules", rules);
      return JSON.toJSONString(value);
   }

   private Integer resolveGrantedDays(long userId) {
      EmbyUserRenewRecord renew = this.renewRecordMapper
         .selectOne(
            new LambdaQueryWrapper<EmbyUserRenewRecord>()
               .eq(EmbyUserRenewRecord::getUserId, Long.valueOf(userId))
               .isNotNull(EmbyUserRenewRecord::getRenewDays)
               .gt(EmbyUserRenewRecord::getRenewDays, Integer.valueOf(0))
               .orderByDesc(EmbyUserRenewRecord::getCreateDatetime)
               .orderByDesc(EmbyUserRenewRecord::getId)
               .last("LIMIT 1"));
      EmbyUserRegisterRecord registration = this.registerRecordMapper
         .selectOne(
            new LambdaQueryWrapper<EmbyUserRegisterRecord>()
               .eq(EmbyUserRegisterRecord::getUserId, Long.valueOf(userId))
               .orderByDesc(EmbyUserRegisterRecord::getCreateDatetime)
               .orderByDesc(EmbyUserRegisterRecord::getId)
               .last("LIMIT 1"));
      if (renew != null && this.isSameOrAfter(renew.getCreateDatetime(), registration == null ? null : registration.getCreateDatetime())) {
         return renew.getRenewDays();
      } else {
         Integer registrationDays = registration == null ? null : registration.getRegisterDays();
         if (registrationDays == null || registrationDays < 1) {
            registrationDays = this.daysBetween(
               registration == null ? null : registration.getCreateDatetime(), registration == null ? null : registration.getExpirationDate()
            );
         }

         if (registrationDays != null) {
            return registrationDays;
         } else {
            EmbyUser user = this.embyUserMapper.selectById(Long.valueOf(userId));
            return this.daysBetween(user == null ? null : user.getCreateDatetime(), user == null ? null : user.getExpirationDate());
         }
      }
   }

   private boolean isSameOrAfter(Date value, Date reference) {
      return value != null && (reference == null || !value.before(reference));
   }

   private Integer daysBetween(Date start, Date end) {
      if (start != null && end != null && end.after(start)) {
         long days = ChronoUnit.DAYS
            .between(start.toInstant().atZone(ZoneId.systemDefault()).toLocalDate(), end.toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
         return days >= 1L && days <= 2147483647L ? (int)days : null;
      } else {
         return null;
      }
   }

   private String trim(String value) {
      return value == null ? "" : value.trim();
   }
}
