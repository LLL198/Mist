package com.una.embyhub.config.handler;

import cn.dev33.satoken.stp.StpUtil;
import java.util.Map;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

public class SaWebSocketInterceptor implements HandshakeInterceptor {
   @Override
   public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Map<String, Object> attributes) {
      StpUtil.checkLogin();

      try {
         if (!StpUtil.hasPermission("admin")) {
            throw new RuntimeException("没有WebSocket连接权限");
         } else {
            return true;
         }
      } catch (Exception var6) {
         return false;
      }
   }

   @Override
   public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Exception exception) {
   }
}
