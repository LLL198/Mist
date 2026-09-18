package com.una.embyhub.config.common.exception;

import com.una.embyhub.config.common.enums.ResponseStatusEnum;
import lombok.Generated;

public class BizException extends RuntimeException {
   private Integer code;

   public BizException() {
   }

   public BizException(String message) {
      super(message);
   }

   public BizException(Integer code, String message) {
      super(message);
      this.code = code;
   }

   public BizException(ResponseStatusEnum responseStatusEnum) {
      super(responseStatusEnum.getMsg());
      this.code = responseStatusEnum.getCode();
   }

   @Generated
   public Integer getCode() {
      return this.code;
   }

   @Generated
   public void setCode(final Integer code) {
      this.code = code;
   }

   @Generated
   @Override
   public boolean equals(final Object o) {
      if (o == this) {
         return true;
      } else if (!(o instanceof BizException other)) {
         return false;
      } else if (!other.canEqual(this)) {
         return false;
      } else {
         Object this$code = this.getCode();
         Object other$code = other.getCode();
         return this$code == null ? other$code == null : this$code.equals(other$code);
      }
   }

   @Generated
   protected boolean canEqual(final Object other) {
      return other instanceof BizException;
   }

   @Generated
   @Override
   public int hashCode() {
      int PRIME = 59;
      int result = 1;
      Object $code = this.getCode();
      return result * 59 + ($code == null ? 43 : $code.hashCode());
   }

   @Generated
   @Override
   public String toString() {
      return "BizException(code=" + this.getCode() + ")";
   }
}
