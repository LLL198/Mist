package com.una.embyhub.config.common.utils;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class TemplateRenderUtils {
   private static final Pattern PATTERN = Pattern.compile("\\$\\{(\\w+)}");

   private TemplateRenderUtils() {
   }

   public static String render(String template, Map<String, String> variables) {
      if (template != null && !template.isEmpty() && variables != null) {
         Matcher matcher = PATTERN.matcher(template);
         StringBuffer sb = new StringBuffer();

         while (matcher.find()) {
            String key = matcher.group(1);
            String value = variables.getOrDefault(key, "");
            matcher.appendReplacement(sb, Matcher.quoteReplacement(value));
         }

         matcher.appendTail(sb);
         return sb.toString();
      } else {
         return template;
      }
   }
}
