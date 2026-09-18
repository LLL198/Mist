package com.una.embyhub.config.common.enums;

import java.util.Arrays;

public enum RenewChannelEnum {
   ADMIN_RENEW(1, "管理员续费"),
   CARD_RENEW(2, "卡密续费"),
   POINTS_REDEEM(3, "积分兑换");

   private final int code;
   private final String label;

   private RenewChannelEnum(int code, String label) {
      this.code = code;
      this.label = label;
   }

   public int getCode() {
      return this.code;
   }

   public String getLabel() {
      return this.label;
   }

   public static String resolveLabel(Integer code) {
      return code == null ? null : Arrays.stream(values()).filter(item -> item.code == code).map(RenewChannelEnum::getLabel).findFirst().orElse("未知");
   }
}
