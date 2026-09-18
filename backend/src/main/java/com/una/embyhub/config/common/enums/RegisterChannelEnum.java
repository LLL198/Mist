package com.una.embyhub.config.common.enums;

import java.util.Arrays;

public enum RegisterChannelEnum {
   ADMIN_REGISTER(1, "管理员注册"),
   CARD_REGISTER(2, "卡密注册"),
   INVITATION(3, "邀请码注册"),
   POINTS_REDEEM(4, "积分兑换"),
   USER_REGISTER(5, "用户注册");

   private final int code;
   private final String label;

   private RegisterChannelEnum(int code, String label) {
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
      return code == null ? null : Arrays.stream(values()).filter(item -> item.code == code).map(RegisterChannelEnum::getLabel).findFirst().orElse("未知");
   }
}
