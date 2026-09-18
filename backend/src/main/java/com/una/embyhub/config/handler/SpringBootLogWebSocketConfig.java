package com.una.embyhub.config.handler;

import com.una.embyhub.config.common.config.CorsProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
public class SpringBootLogWebSocketConfig implements WebSocketConfigurer {
   private final SpringBootLogWebSocketHandler springBootLogWebSocketHandler;
   private final CorsProperties corsProperties;

   public SpringBootLogWebSocketConfig(SpringBootLogWebSocketHandler springBootLogWebSocketHandler, CorsProperties corsProperties) {
      this.springBootLogWebSocketHandler = springBootLogWebSocketHandler;
      this.corsProperties = corsProperties;
   }

   @Override
   public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
      String[] allowedOrigins = this.corsProperties.getAllowedOriginsArray();
      registry.addHandler(this.springBootLogWebSocketHandler, "/ws/docker/logs")
         .addInterceptors(new SaWebSocketInterceptor())
         .setAllowedOrigins(allowedOrigins);
   }
}
