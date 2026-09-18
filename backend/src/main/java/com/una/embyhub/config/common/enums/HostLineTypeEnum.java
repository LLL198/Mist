package com.una.embyhub.config.common.enums;

import java.util.Arrays;

public enum HostLineTypeEnum {
   COMMON(0, "通用线路"),
   WHITELIST(1, "白名单线路");

   private final int code;
   private final String label;

   private HostLineTypeEnum(int code, String label) {
      this.code = code;
      this.label = label;
   }

   public int getCode() {
      return this.code;
   }

   public String getLabel() {
      return this.label;
   }

   public static int normalize(Integer code) {
      return WHITELIST.code == (code == null ? COMMON.code : code) ? WHITELIST.code : COMMON.code;
   }

   public static String resolveLabel(Integer code) {
      int normalizedCode = normalize(code);
      return Arrays.stream(values()).filter(item -> item.code == normalizedCode).map(HostLineTypeEnum::getLabel).findFirst().orElse(COMMON.label);
   }

   public static String resolveUserRoleLabel(Integer code) {
      return normalize(code) == WHITELIST.code ? "所有线路" : "普通线路";
   }
}
