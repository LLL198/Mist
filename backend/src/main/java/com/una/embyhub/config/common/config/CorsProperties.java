package com.una.embyhub.config.common.config;

import java.net.URI;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
@ConfigurationProperties(
   prefix = "foam.security.cors"
)
public class CorsProperties {
   private List<String> allowedOrigins = List.of(
      "http://localhost:5174",
      "http://127.0.0.1:5174",
      "http://localhost:8081",
      "http://127.0.0.1:8081",
      "http://localhost:8082",
      "http://127.0.0.1:8082"
   );

   public List<String> getAllowedOrigins() {
      return this.allowedOrigins;
   }

   public void setAllowedOrigins(List<String> allowedOrigins) {
      this.allowedOrigins = allowedOrigins;
   }

   public String[] getAllowedOriginsArray() {
      return this.allowedOrigins == null
         ? new String[0]
         : this.allowedOrigins
            .stream()
            .filter(StringUtils::hasText)
            .flatMap(value -> Arrays.stream(value.split(",")))
            .map(String::trim)
            .filter(StringUtils::hasText)
            .map(CorsProperties::normalizeOrigin)
            .filter(StringUtils::hasText)
            .distinct()
            .toArray(String[]::new);
   }

   static String normalizeOrigin(String value) {
      if (!StringUtils.hasText(value)) {
         return "";
      } else {
         String origin = value.trim();

         while (origin.endsWith("/")) {
            origin = origin.substring(0, origin.length() - 1);
         }

         if (origin.contains("*")) {
            return "";
         } else {
            try {
               URI uri = URI.create(origin);
               String scheme = uri.getScheme();
               if (!"http".equalsIgnoreCase(scheme) && !"https".equalsIgnoreCase(scheme)) {
                  return "";
               } else if (!StringUtils.hasText(uri.getHost())) {
                  return "";
               } else if (!StringUtils.hasText(uri.getRawPath())
                  && !StringUtils.hasText(uri.getRawQuery())
                  && !StringUtils.hasText(uri.getRawFragment())
                  && !StringUtils.hasText(uri.getUserInfo())) {
                  String normalizedScheme = scheme.toLowerCase(Locale.ROOT);
                  String host = uri.getHost().toLowerCase(Locale.ROOT);
                  String hostPart = host.contains(":") ? "[" + host + "]" : host;
                  return uri.getPort() >= 0 ? normalizedScheme + "://" + hostPart + ":" + uri.getPort() : normalizedScheme + "://" + hostPart;
               } else {
                  return "";
               }
            } catch (IllegalArgumentException var7) {
               return "";
            }
         }
      }
   }
}
