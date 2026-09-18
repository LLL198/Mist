package com.una.embyhub.config.common.enums;

import java.util.Arrays;

public enum PointsBotRedeemTypeEnum {
   CREATE_ACCOUNT("CREATE_ACCOUNT", "注册账号"),
   RENEW("RENEW", "续费");

   private final String code;
   private final String label;

   private PointsBotRedeemTypeEnum(String code, String label) {
      this.code = code;
      this.label = label;
   }

   public String getCode() {
      return this.code;
   }

   public String getLabel() {
      return this.label;
   }

   public boolean matches(String value) {
      return this.code.equals(value);
   }

   public static boolean isSupported(String value) {
      return Arrays.stream(values()).anyMatch(item -> item.matches(value));
   }

   public static String resolveLabel(String value) {
      return Arrays.stream(values()).filter(item -> item.matches(value)).map(PointsBotRedeemTypeEnum::getLabel).findFirst().orElse("未知类型");
   }
}
