package com.una.embyhub.pointsbot.service;

import com.alibaba.fastjson2.JSONObject;
import com.una.embyhub.config.common.exception.BizException;
import com.una.embyhub.pointsbot.model.PointsBotConfig;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

public final class PointsBotConfigRules {
   private static final int DEFAULT_TRANSFER_MIN_POINTS = 1;
   private static final int DEFAULT_TRANSFER_MAX_POINTS = 500;
   private static final int DEFAULT_RED_PACKET_EXPIRE_MINUTES = 1440;
   private static final int MAX_RED_PACKET_EXPIRE_MINUTES = 10080;
   private static final Set<String> LEGACY_GAME_COMMANDS = Set.of("sgs", "blackjack", "dice", "slots");
   private static final Set<String> SUPPORTED_GAME_COMMANDS = new LinkedHashSet<>(PointsBotConfig.ALL_GAME_COMMANDS);

   private PointsBotConfigRules() {
   }

   public static void validateTransferRange(JSONObject params) {
      if (params != null) {
         int minPoints = positiveInteger(params, "transferMinPoints", 1, "积分转账最小值");
         int maxPoints = positiveInteger(params, "transferMaxPoints", 500, "积分转账最大值");
         if (maxPoints < minPoints) {
            throw new BizException("积分转账最大值不能小于最小值");
         }
      }
   }

   public static void validateGameConfiguration(JSONObject params) {
      if (params != null) {
         validateEnabledGameCommands(params);
      }
   }

   public static void validateRedPacketConfiguration(JSONObject params) {
      if (params != null) {
         boundedPositiveInteger(params, "redPacketExpireMinutes", 1440, 10080, "红包过期时间");
      }
   }

   public static PointsBotConfig normalizeTransferRange(PointsBotConfig config) {
      if (config == null) {
         return new PointsBotConfig();
      } else {
         if (config.getTransferMinPoints() <= 0) {
            config.setTransferMinPoints(1);
         }

         if (config.getTransferMaxPoints() <= 0) {
            config.setTransferMaxPoints(500);
         }

         if (config.getTransferMaxPoints() < config.getTransferMinPoints()) {
            config.setTransferMaxPoints(config.getTransferMinPoints());
         }

         return config;
      }
   }

   public static PointsBotConfig normalizeGameConfiguration(PointsBotConfig config) {
      if (config == null) {
         return new PointsBotConfig();
      } else {
         if (config.getGameCommandsEnabled() == null) {
            config.setGameCommandsEnabled(true);
         }

         if (config.getFoamBagEnabled() == null) {
            config.setFoamBagEnabled(true);
         }

         if (config.getRedPacketEnabled() == null) {
            config.setRedPacketEnabled(true);
         }

         if (config.getRedPacketExpireMinutes() <= 0 || config.getRedPacketExpireMinutes() > 10080) {
            config.setRedPacketExpireMinutes(1440);
         }

         List<String> configuredCommands = config.getEnabledGameCommands();
         if (configuredCommands == null) {
            config.setEnabledGameCommands(new ArrayList<>(PointsBotConfig.DEFAULT_GAME_COMMANDS));
         } else {
            LinkedHashSet<String> normalized = new LinkedHashSet<>();

            for (String command : configuredCommands) {
               String value = normalizeGameCommand(command);
               if (SUPPORTED_GAME_COMMANDS.contains(value)) {
                  normalized.add(value);
               }
            }

            if ((config.getGameCommandsVersion() == null || config.getGameCommandsVersion() < 2) && normalized.containsAll(LEGACY_GAME_COMMANDS)) {
               normalized.add("scratch");
            }

            if ((config.getGameCommandsVersion() == null || config.getGameCommandsVersion() < 4) && normalized.containsAll(LEGACY_GAME_COMMANDS)) {
               normalized.add("hell_dice");
            }

            config.setEnabledGameCommands(new ArrayList<>(normalized));
         }

         config.setGameCommandsVersion(4);
         return config;
      }
   }

   public static boolean isGameCommandEnabled(PointsBotConfig config, String command) {
      PointsBotConfig normalized = normalizeGameConfiguration(config);
      return !Boolean.FALSE.equals(normalized.getGameCommandsEnabled()) && normalized.getEnabledGameCommands().contains(normalizeGameCommand(command));
   }

   private static void validateEnabledGameCommands(JSONObject params) {
      if (params.containsKey("enabledGameCommands") && params.get("enabledGameCommands") != null) {
         Object rawCommands = params.get("enabledGameCommands");
         if (rawCommands instanceof List) {
            for (Object rawCommand : (List)rawCommands) {
               String command = normalizeGameCommand(rawCommand == null ? null : String.valueOf(rawCommand));
               if (!SUPPORTED_GAME_COMMANDS.contains(command)) {
                  throw new BizException("存在不受支持的游戏命令");
               }
            }
         } else {
            throw new BizException("游戏命令必须使用数组格式");
         }
      }
   }

   private static String normalizeGameCommand(String command) {
      if (command == null) {
         return "";
      } else {
         String normalized = command.trim().toLowerCase(Locale.ROOT);
         if (normalized.startsWith("/")) {
            normalized = normalized.substring(1);
         }

         return "bj".equals(normalized) ? "blackjack" : normalized;
      }
   }

   private static int positiveInteger(JSONObject params, String key, int defaultValue, String label) {
      Object rawValue = params.get(key);
      if (rawValue != null && !String.valueOf(rawValue).isBlank()) {
         try {
            BigDecimal value = new BigDecimal(String.valueOf(rawValue).trim()).stripTrailingZeros();
            if (value.scale() > 0) {
               throw new ArithmeticException("not an integer");
            } else {
               int parsed = value.intValueExact();
               if (parsed <= 0) {
                  throw new ArithmeticException("not positive");
               } else {
                  return parsed;
               }
            }
         } catch (RuntimeException var7) {
            throw new BizException(label + "必须为大于0的整数");
         }
      } else {
         return defaultValue;
      }
   }

   private static int boundedPositiveInteger(JSONObject params, String key, int defaultValue, int maximum, String label) {
      int value = positiveInteger(params, key, defaultValue, label);
      if (value > maximum) {
         throw new BizException(label + "不能超过 " + maximum + " 分钟");
      } else {
         return value;
      }
   }
}
