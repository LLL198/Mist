package com.una.embyhub.config.common.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

public final class EmbyStudioAliasUtils {
   private static final Map<String, List<String>> FEATURED_STUDIO_ALIASES;

   private EmbyStudioAliasUtils() {
   }

   public static Map<String, List<String>> featuredStudioAliases() {
      return FEATURED_STUDIO_ALIASES;
   }

   public static boolean matchesAlias(String studioName, List<String> aliases) {
      if (StringUtils.hasText(studioName) && !CollectionUtils.isEmpty(aliases)) {
         String normalized = normalize(studioName);
         if (!StringUtils.hasText(normalized)) {
            return false;
         } else {
            for (String alias : aliases) {
               String aliasNormalized = normalize(alias);
               if (StringUtils.hasText(aliasNormalized) && (containsPhrase(normalized, aliasNormalized) || containsPhrase(aliasNormalized, normalized))) {
                  return true;
               }
            }

            return false;
         }
      } else {
         return false;
      }
   }

   public static Set<String> expandAliasesForStudioNames(Collection<String> studioNames) {
      Set<String> expanded = new LinkedHashSet<>();
      if (CollectionUtils.isEmpty(studioNames)) {
         return expanded;
      } else {
         for (String studioName : studioNames) {
            if (StringUtils.hasText(studioName)) {
               expanded.add(studioName);
               expanded.addAll(findAliasGroup(studioName));
            }
         }

         return expanded;
      }
   }

   private static List<String> findAliasGroup(String studioName) {
      List<String> matched = new ArrayList<>();

      for (Entry<String, List<String>> entry : FEATURED_STUDIO_ALIASES.entrySet()) {
         List<String> aliases = new ArrayList<>();
         aliases.add(entry.getKey());
         aliases.addAll(entry.getValue());
         if (matchesAlias(studioName, aliases)) {
            matched.add(entry.getKey());
            matched.addAll(entry.getValue());
         }
      }

      return matched;
   }

   private static boolean containsPhrase(String source, String phrase) {
      return source.equals(phrase) ? true : source.startsWith(phrase + " ") || source.endsWith(" " + phrase) || source.contains(" " + phrase + " ");
   }

   private static String normalize(String value) {
      return !StringUtils.hasText(value)
         ? ""
         : value.toLowerCase(Locale.ROOT).replace("&", " and ").replace("+", " plus ").replaceAll("[^a-z0-9]+", " ").trim().replaceAll("\\s+", " ");
   }

   static {
      Map<String, List<String>> aliases = new LinkedHashMap<>();
      aliases.put("Netflix", List.of("Netflix", "Netflix Studios", "Netflix Animation"));
      aliases.put("HBO", List.of("HBO", "Home Box Office", "HBO Films", "HBO Documentary Films"));
      aliases.put("HBO Max", List.of("HBO Max", "Max"));
      aliases.put(
         "Disney+",
         List.of(
            "Disney+",
            "Disney Plus",
            "Disney",
            "Walt Disney Pictures",
            "Walt Disney Animation Studios",
            "Disney Television Animation",
            "Disney Channel",
            "Disney Branded Television",
            "Pixar"
         )
      );
      aliases.put("Disney Television Animation", List.of("Disney Television Animation"));
      aliases.put("Marvel Studios", List.of("Marvel Studios", "Marvel Television", "Marvel Animation"));
      aliases.put(
         "Warner Bros.",
         List.of(
            "Warner Bros.",
            "Warner Bros",
            "Warner Brothers",
            "Warner Bros. Pictures",
            "Warner Bros. Television",
            "Warner Bros. Animation",
            "New Line Cinema",
            "DC Studios",
            "DC Entertainment"
         )
      );
      aliases.put("Universal Pictures", List.of("Universal Pictures", "Universal", "Universal Pictures Corporation", "DreamWorks Animation", "Illumination"));
      aliases.put(
         "Paramount Pictures",
         List.of(
            "Paramount Pictures",
            "Paramount",
            "Paramount Animation",
            "Paramount Players",
            "Paramount Television Studios",
            "Paramount Network",
            "Paramount+",
            "Paramount Plus",
            "Paramount+ with Showtime"
         )
      );
      aliases.put(
         "20th Century Fox / 20th Century Studios",
         List.of("20th Century Fox", "20th Century Studios", "20th Century", "Fox 2000 Pictures", "Fox Searchlight Pictures", "Searchlight Pictures")
      );
      aliases.put("Columbia Pictures", List.of("Columbia Pictures", "Columbia"));
      aliases.put(
         "Sony Pictures",
         List.of("Sony Pictures", "Sony Pictures Television", "Sony Pictures Animation", "Sony Pictures Entertainment", "TriStar Pictures", "Screen Gems")
      );
      aliases.put("Amazon Studios", List.of("Amazon Studios", "Amazon Prime Video", "Prime Video", "Amazon MGM Studios"));
      aliases.put("Apple TV+", List.of("Apple TV+", "Apple TV Plus", "Apple Studios", "Apple Original Films"));
      aliases.put("BBC / BBC One / BBC Two", List.of("BBC", "BBC One", "BBC Two", "BBC Studios", "BBC Film", "BBC Four"));
      aliases.put("NHK", List.of("NHK", "NHK G", "NHK BSP4K"));
      FEATURED_STUDIO_ALIASES = Collections.unmodifiableMap(aliases);
   }
}
