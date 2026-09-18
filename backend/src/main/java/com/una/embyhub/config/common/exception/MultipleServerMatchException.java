package com.una.embyhub.config.common.exception;

import com.una.embyhub.model.dto.response.embyuser.LoginMultipleServerResponse;
import lombok.Generated;

public class MultipleServerMatchException extends RuntimeException {
   private final LoginMultipleServerResponse response;

   public MultipleServerMatchException(LoginMultipleServerResponse response) {
      super("用户存在于多个服务器，请选择服务器登录");
      this.response = response;
   }

   @Generated
   public LoginMultipleServerResponse getResponse() {
      return this.response;
   }
}
