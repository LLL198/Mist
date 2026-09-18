package com.una.embyhub.config.common.exception;

import cn.dev33.satoken.exception.NotLoginException;
import cn.dev33.satoken.exception.NotPermissionException;
import com.una.embyhub.config.common.enums.ResponseStatusEnum;
import com.una.embyhub.model.dto.response.ResponseData;
import embyclient.ApiException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;
import lombok.Generated;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;

@ControllerAdvice
public class GlobalExceptionHandler {
   @Generated
   private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

   @ResponseBody
   @ResponseStatus(HttpStatus.FORBIDDEN)
   @ExceptionHandler({NotPermissionException.class})
   public Object notPermissionExceptionHandler(NotPermissionException e) {
      log.warn("无权限访问：{}", e.getMessage());
      return ResponseData.failure(ResponseStatusEnum.FORBIDDEN.getCode(), "无权限访问");
   }

   @ResponseBody
   @ResponseStatus(HttpStatus.TOO_MANY_REQUESTS)
   @ExceptionHandler({RateLimitException.class})
   public Object rateLimitExceptionHandler(RateLimitException e, HttpServletResponse response) {
      response.setHeader("Retry-After", String.valueOf(e.getRetryAfterSeconds()));
      return ResponseData.failure(e.getCode(), e.getMessage());
   }

   @ResponseBody
   @ResponseStatus(HttpStatus.BAD_REQUEST)
   @ExceptionHandler({Exception.class})
   public Object exceptionHandler(Exception e, HttpServletRequest request) {
      if (this.isSseRequest(request)) {
         return this.buildSseErrorEvent(e);
      } else if (e instanceof BizException bizException) {
         if (bizException.getCode() == null) {
            bizException.setCode(ResponseStatusEnum.BAD_REQUEST.getCode());
         }

         return ResponseData.failure(bizException.getCode(), bizException.getMessage());
      } else if (e instanceof MethodArgumentNotValidException methodArgumentNotValidException) {
         Map<String, String> map = new HashMap<>();
         BindingResult result = methodArgumentNotValidException.getBindingResult();
         result.getFieldErrors().forEach(item -> {
            String message = item.getDefaultMessage();
            String field = item.getField();
            map.put(field, message);
         });
         log.error("数据校验出现错误：", (Throwable)e);
         return ResponseData.failure(ResponseStatusEnum.BAD_REQUEST, map);
      } else if (e instanceof HttpRequestMethodNotSupportedException) {
         log.error("请求方法错误：", (Throwable)e);
         return ResponseData.failure(ResponseStatusEnum.BAD_REQUEST.getCode(), "请求方法不正确");
      } else if (e instanceof MissingServletRequestParameterException) {
         log.error("请求参数缺失：", (Throwable)e);
         MissingServletRequestParameterException ex = (MissingServletRequestParameterException)e;
         return ResponseData.failure(ResponseStatusEnum.BAD_REQUEST.getCode(), "请求参数缺少: " + ex.getParameterName());
      } else if (e instanceof MethodArgumentTypeMismatchException) {
         log.error("请求参数类型错误：", (Throwable)e);
         MethodArgumentTypeMismatchException ex = (MethodArgumentTypeMismatchException)e;
         return ResponseData.failure(ResponseStatusEnum.BAD_REQUEST.getCode(), "请求参数类型不正确：" + ex.getName());
      } else if (e instanceof HttpMessageNotReadableException) {
         log.warn("请求体无法解析：{}", e.getMessage());
         return ResponseData.failure(ResponseStatusEnum.BAD_REQUEST.getCode(), this.resolveRequestBodyError(e));
      } else if (e instanceof NoHandlerFoundException ex) {
         log.error("请求地址不存在：", (Throwable)e);
         return ResponseData.failure(ResponseStatusEnum.NOT_EXIST, ex.getRequestURL());
      } else if (e instanceof NotLoginException) {
         log.error("用户未登录：{}", e.getMessage());
         return ResponseData.failure(ResponseStatusEnum.UNAUTHORIZED, e.getMessage());
      } else if (e instanceof ApiException) {
         e.printStackTrace();
         log.error("emby接口异常：{}", ((ApiException)e).getResponseBody());
         return ResponseData.failure(ResponseStatusEnum.SYSTEM_ERROR.getCode(), ((ApiException)e).getResponseBody());
      } else if (e instanceof IllegalArgumentException) {
         log.error("参数错误：{}", (Throwable)e);
         return ResponseData.failure(ResponseStatusEnum.BAD_REQUEST.getCode(), e.getMessage());
      } else if (e.getCause() instanceof NotPermissionException) {
         log.error("无权限访问：{}", e.getMessage());
         return ResponseData.failure(ResponseStatusEnum.FORBIDDEN.getCode(), "无权限访问");
      } else if (e instanceof MultipleServerMatchException ex) {
         return ResponseData.failure(ResponseStatusEnum.MULTIPLE_SERVER_MATCH.getCode(), ResponseStatusEnum.MULTIPLE_SERVER_MATCH.getMsg(), ex.getResponse());
      } else {
         log.error("【系统异常】", (Throwable)e);
         return ResponseData.failure(ResponseStatusEnum.SYSTEM_ERROR.getCode(), ResponseStatusEnum.SYSTEM_ERROR.getMsg());
      }
   }

   private boolean isSseRequest(HttpServletRequest request) {
      if (request == null) {
         return false;
      } else {
         String accept = request.getHeader("Accept");
         if (accept != null && accept.contains("text/event-stream")) {
            return true;
         } else {
            String contentType = request.getContentType();
            return contentType != null && contentType.contains("text/event-stream");
         }
      }
   }

   private String resolveRequestBodyError(Throwable throwable) {
      for (Throwable current = throwable; current != null; current = current.getCause()) {
         String message = current.getMessage();
         if (message != null && message.startsWith("不允许提交受保护的用户字段:")) {
            return message;
         }
      }

      return "请求体格式不正确";
   }

   private String buildSseErrorEvent(Exception e) {
      String message = e == null ? "unknown error" : e.getMessage();
      if (message == null || message.isBlank()) {
         message = ResponseStatusEnum.SYSTEM_ERROR.getMsg();
      }

      String sanitized = message.replace("\r", " ").replace("\n", " ");
      return "event: error\ndata: {\"message\":\"" + sanitized.replace("\"", "\\\"") + "\"}\n\n";
   }
}
