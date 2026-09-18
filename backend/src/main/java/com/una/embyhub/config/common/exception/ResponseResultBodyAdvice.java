package com.una.embyhub.config.common.exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.una.embyhub.model.dto.response.ResponseData;
import jakarta.annotation.Resource;
import java.util.Arrays;
import java.util.Objects;
import lombok.Generated;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@RestControllerAdvice
public class ResponseResultBodyAdvice implements ResponseBodyAdvice<Object> {
   @Generated
   private static final Logger log = LoggerFactory.getLogger(ResponseResultBodyAdvice.class);
   @Resource
   private ObjectMapper objectMapper;
   private static final String[] EXCLUDED_PATHS = new String[]{
      "/wechat/bot",
      "/paymentAccount/easypay/notify",
      "/wechat-ip/qrcode",
      "/douban/image",
      "/embyUser/uploadAvatar",
      "/embyUser/userDiff",
      "/embyUser/exportUserDiffExcel"
   };

   @Override
   public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
      return true;
   }

   @Override
   public Object beforeBodyWrite(
      Object body,
      MethodParameter returnType,
      MediaType selectedContentType,
      Class<? extends HttpMessageConverter<?>> selectedConverterType,
      ServerHttpRequest request,
      ServerHttpResponse response
   ) {
      try {
         Class<?> returnClass = returnType.getParameterType();
         if (!(body instanceof SseEmitter)
            && !SseEmitter.class.isAssignableFrom(returnClass)
            && (selectedContentType == null || !MediaType.TEXT_EVENT_STREAM.includes(selectedContentType))) {
            String path = request.getURI().getPath();
            if (path != null && path.contains("/rose/qr/") && path.endsWith("/image")) {
               return body;
            } else if (path != null && path.endsWith("/notifyChannel/telegram/startPanelImage/content")) {
               return body;
            } else if (path != null && Arrays.stream(EXCLUDED_PATHS).anyMatch(path::contains)) {
               return body;
            } else if (body instanceof String || Objects.equals(returnClass, String.class)) {
               return this.objectMapper.writeValueAsString(ResponseData.success(body));
            } else {
               return body instanceof ResponseData ? body : ResponseData.success(body);
            }
         } else {
            return body;
         }
      } catch (Throwable e) {
         ResponseResultBodyAdvice.<RuntimeException>sneakyThrow(e);
         throw new AssertionError("unreachable");
      }
   }

   private static <T extends Throwable> void sneakyThrow(Throwable throwable) throws T {
      throw (T)throwable;
   }
}
