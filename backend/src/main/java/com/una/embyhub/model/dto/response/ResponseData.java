package com.una.embyhub.model.dto.response;

import com.una.embyhub.config.common.enums.ResponseStatusEnum;
import java.io.Serializable;
import lombok.Generated;

public class ResponseData<T> implements Serializable {
   private Integer code;
   private String msg;
   private T data;

   public ResponseData() {
   }

   public ResponseData(Integer code, String msg) {
      this.code = code;
      this.msg = msg;
   }

   public ResponseData(Integer code, T data) {
      this.code = code;
      this.data = data;
   }

   public ResponseData(Integer code, String msg, T data) {
      this.code = code;
      this.msg = msg;
      this.data = data;
   }

   private ResponseData(ResponseStatusEnum resultStatus, T data) {
      this.code = resultStatus.getCode();
      this.msg = resultStatus.getMsg();
      this.data = data;
   }

   public static ResponseData<Void> success() {
      return new ResponseData<>(ResponseStatusEnum.SUCCESS, null);
   }

   public static <T> ResponseData<T> success(T data) {
      return new ResponseData<>(ResponseStatusEnum.SUCCESS, data);
   }

   public static <T> ResponseData<T> success(ResponseStatusEnum resultStatus, T data) {
      return resultStatus == null ? success(data) : new ResponseData<>(resultStatus, data);
   }

   public static <T> ResponseData<T> failure() {
      return new ResponseData<>(ResponseStatusEnum.SYSTEM_ERROR, null);
   }

   public static <T> ResponseData<T> failure(ResponseStatusEnum resultStatus) {
      return failure(resultStatus, null);
   }

   public static <T> ResponseData<T> failure(ResponseStatusEnum resultStatus, T data) {
      return resultStatus == null ? new ResponseData<>(ResponseStatusEnum.SYSTEM_ERROR, null) : new ResponseData<>(resultStatus, data);
   }

   public static <T> ResponseData<T> failure(Integer code, String msg) {
      return new ResponseData<>(code, msg);
   }

   public static <T> ResponseData<T> failure(Integer code, String msg, T data) {
      return new ResponseData<>(code, msg, data);
   }

   @Generated
   public Integer getCode() {
      return this.code;
   }

   @Generated
   public String getMsg() {
      return this.msg;
   }

   @Generated
   public T getData() {
      return this.data;
   }

   @Generated
   public void setCode(final Integer code) {
      this.code = code;
   }

   @Generated
   public void setMsg(final String msg) {
      this.msg = msg;
   }

   @Generated
   public void setData(final T data) {
      this.data = data;
   }

   @Generated
   @Override
   public boolean equals(final Object o) {
      if (o == this) {
         return true;
      } else if (!(o instanceof ResponseData<?> other)) {
         return false;
      } else if (!other.canEqual(this)) {
         return false;
      } else {
         Object this$code = this.getCode();
         Object other$code = other.getCode();
         if (this$code == null ? other$code == null : this$code.equals(other$code)) {
            Object this$msg = this.getMsg();
            Object other$msg = other.getMsg();
            if (this$msg == null ? other$msg == null : this$msg.equals(other$msg)) {
               Object this$data = this.getData();
               Object other$data = other.getData();
               return this$data == null ? other$data == null : this$data.equals(other$data);
            } else {
               return false;
            }
         } else {
            return false;
         }
      }
   }

   @Generated
   protected boolean canEqual(final Object other) {
      return other instanceof ResponseData;
   }

   @Generated
   @Override
   public int hashCode() {
      int PRIME = 59;
      int result = 1;
      Object $code = this.getCode();
      result = result * 59 + ($code == null ? 43 : $code.hashCode());
      Object $msg = this.getMsg();
      result = result * 59 + ($msg == null ? 43 : $msg.hashCode());
      Object $data = this.getData();
      return result * 59 + ($data == null ? 43 : $data.hashCode());
   }

   @Generated
   @Override
   public String toString() {
      return "ResponseData(code=" + this.getCode() + ", msg=" + this.getMsg() + ", data=" + this.getData() + ")";
   }
}
