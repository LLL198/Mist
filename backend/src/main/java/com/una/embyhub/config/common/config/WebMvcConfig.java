package com.una.embyhub.config.common.config;

import java.nio.file.Paths;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
   @Autowired
   private CorsProperties corsProperties;
   @Value("${foam.avatar.base-path:data/avatars}")
   private String avatarBasePath;
   @Value("${foam.avatar.public-path:/avatars}")
   private String avatarPublicPath;

   @Override
   public void addCorsMappings(CorsRegistry registry) {
      String[] allowedOrigins = this.corsProperties.getAllowedOriginsArray();
      if (allowedOrigins.length != 0) {
         registry.addMapping("/**")
            .allowedOrigins(allowedOrigins)
            .allowedMethods("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS")
            .allowCredentials(true)
            .allowedHeaders("*")
            .maxAge(3600L);
      }
   }

   @Override
   public void addResourceHandlers(ResourceHandlerRegistry registry) {
      String handlerPath = this.avatarPublicPath.startsWith("/") ? this.avatarPublicPath : "/" + this.avatarPublicPath;
      if (!handlerPath.endsWith("/")) {
         handlerPath = handlerPath + "/";
      }

      String resourceLocation = Paths.get(this.avatarBasePath).toAbsolutePath().toUri().toString();
      if (!resourceLocation.endsWith("/")) {
         resourceLocation = resourceLocation + "/";
      }

      registry.addResourceHandler(handlerPath + "**").addResourceLocations(resourceLocation);
   }
}
