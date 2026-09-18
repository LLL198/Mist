package com.una.embyhub.config.common.exception;

import com.una.embyhub.config.common.enums.ResponseStatusEnum;
import lombok.Generated;

public class RateLimitException extends BizException {
   private final long retryAfterSeconds;

   public RateLimitException(String message, long retryAfterSeconds) {
      super(ResponseStatusEnum.TOO_MANY_REQUESTS.getCode(), message);
      this.retryAfterSeconds = Math.max(1L, retryAfterSeconds);
   }

   @Generated
   public long getRetryAfterSeconds() {
      return this.retryAfterSeconds;
   }
}
