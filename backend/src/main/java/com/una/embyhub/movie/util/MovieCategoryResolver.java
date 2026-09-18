package com.una.embyhub.movie.util;

import cn.hutool.core.io.resource.ResourceUtil;
import info.movito.themoviedbapi.model.core.Genre;
import info.movito.themoviedbapi.model.movies.MovieDb;
import info.movito.themoviedbapi.model.tv.series.TvSeriesDb;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import lombok.Generated;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.yaml.snakeyaml.Yaml;

@Component
public class MovieCategoryResolver {
   @Generated
   private static final Logger log = LoggerFactory.getLogger(MovieCategoryResolver.class);
   private static final String CATEGORY_RESOURCE = "movie/category.yaml";
   private final Map<String, LinkedHashMap<String, Map<String, String>>> categories = this.loadCategories();

   public String resolveMovie(MovieDb movie) {
      if (movie == null) {
         return "";
      } else {
         Map<String, Object> info = new LinkedHashMap<>();
         info.put("original_language", movie.getOriginalLanguage());
         info.put("production_countries", movie.getProductionCountries());
         info.put("genre_ids", this.extractGenreIds(movie.getGenres()));
         info.put("release_date", movie.getReleaseDate());
         return this.getCategory("movie", info);
      }
   }

   public String resolveTv(TvSeriesDb tv) {
      if (tv == null) {
         return "";
      } else {
         Map<String, Object> info = new LinkedHashMap<>();
         info.put("original_language", tv.getOriginalLanguage());
         info.put("origin_country", tv.getOriginCountry());
         info.put("genre_ids", this.extractGenreIds(tv.getGenres()));
         info.put("first_air_date", tv.getFirstAirDate());
         return this.getCategory("tv", info);
      }
   }

   private Map<String, LinkedHashMap<String, Map<String, String>>> loadCategories() {
      try {
         Map<String, LinkedHashMap<String, Map<String, String>>> result;
         try (InputStream in = ResourceUtil.getStream("movie/category.yaml")) {
            if (in == null) {
               log.warn("未找到分类配置: {}", "movie/category.yaml");
               return Map.of();
            }

            Yaml yaml = new Yaml();
            if (yaml.load(in) instanceof Map<?, ?> map) {
               result = new LinkedHashMap<>();

               for (Entry<?, ?> entry : map.entrySet()) {
                  if (entry.getKey() != null) {
                     Object rawValue = entry.getValue();
                     if (rawValue instanceof Map<?, ?> child) {
                        String key = String.valueOf(entry.getKey());
                        LinkedHashMap<String, Map<String, String>> value = new LinkedHashMap<>();

                        for (Entry<?, ?> c : child.entrySet()) {
                           String name = String.valueOf(c.getKey());
                           Map<String, String> conditionMap = (Map<String, String>)c.getValue();
                           if (!(conditionMap instanceof Map)) {
                              value.put(name, null);
                           } else {
                              Map<?, ?> conds = conditionMap;
                              conditionMap = new LinkedHashMap<>();

                              for (Entry<?, ?> cond : conds.entrySet()) {
                                 if (cond.getKey() != null && cond.getValue() != null) {
                                    conditionMap.put(String.valueOf(cond.getKey()), String.valueOf(cond.getValue()));
                                 }
                              }

                              value.put(name, conditionMap);
                           }
                        }

                        result.put(key, value);
                     }
                  }
               }

               return result;
            }

            result = Map.of();
         }

         return result;
      } catch (Exception var20) {
         log.warn("加载分类配置失败: {}", var20.getMessage());
         return Map.of();
      }
   }

   private String getCategory(String type, Map<String, Object> info) {
      if (StringUtils.hasText(type) && info != null && !info.isEmpty()) {
         LinkedHashMap<String, Map<String, String>> rules = this.categories.get(type);
         if (rules != null && !rules.isEmpty()) {
            for (Entry<String, Map<String, String>> entry : rules.entrySet()) {
               String category = entry.getKey();
               Map<String, String> conditions = entry.getValue();
               if (conditions == null || conditions.isEmpty()) {
                  return category;
               }

               boolean match = true;

               for (Entry<String, String> cond : conditions.entrySet()) {
                  String attr = cond.getKey();
                  String value = cond.getValue();
                  if (StringUtils.hasText(value)) {
                     String infoValue = null;
                     Object infoRaw = null;
                     if ("release_year".equals(attr)) {
                        infoValue = this.stringValue(info.get("release_date"));
                        if (!StringUtils.hasText(infoValue)) {
                           infoValue = this.stringValue(info.get("first_air_date"));
                        }

                        if (StringUtils.hasText(infoValue) && infoValue.length() >= 4) {
                           infoValue = infoValue.substring(0, 4);
                        }
                     } else {
                        infoRaw = info.get(attr);
                     }

                     List<String> infoValues = new ArrayList<>();
                     if (StringUtils.hasText(infoValue)) {
                        infoValues.add(infoValue.toUpperCase(Locale.ROOT));
                     } else if (infoRaw != null) {
                        infoValues.addAll(this.toStringList(attr, infoRaw));
                     }

                     if (infoValues.isEmpty()) {
                        match = false;
                     } else {
                        List<String> values = new ArrayList<>();

                        for (String v : value.split(",")) {
                           String trimmed = v.trim();
                           if (StringUtils.hasText(trimmed)) {
                              if (trimmed.contains("-")) {
                                 String[] parts = trimmed.split("-", 2);
                                 String begin = parts[0];
                                 String end = parts.length > 1 ? parts[1] : "";
                                 String prefix = "";
                                 if (begin.startsWith("!")) {
                                    prefix = "!";
                                    begin = begin.substring(1);
                                 }

                                 if (this.isDigits(begin) && this.isDigits(end)) {
                                    int b = Integer.parseInt(begin);
                                    int e = Integer.parseInt(end);

                                    for (int i = b; i <= e; i++) {
                                       values.add(prefix + i);
                                    }
                                 } else {
                                    values.add(prefix + begin);
                                    if (StringUtils.hasText(end)) {
                                       values.add(prefix + end);
                                    }
                                 }
                              } else {
                                 values.add(trimmed);
                              }
                           }
                        }

                        List<String> normalized = new ArrayList<>();
                        List<String> invertValues = new ArrayList<>();

                        for (String vx : values) {
                           String upper = vx.toUpperCase(Locale.ROOT);
                           if (upper.startsWith("!")) {
                              invertValues.add(upper.substring(1));
                           } else {
                              normalized.add(upper);
                           }
                        }

                        Set<String> infoSet = Set.copyOf(infoValues);
                        if (!normalized.isEmpty() && normalized.stream().noneMatch(infoSet::contains)) {
                           match = false;
                        }

                        if (!invertValues.isEmpty() && invertValues.stream().anyMatch(infoSet::contains)) {
                           match = false;
                        }
                     }
                  }
               }

               if (match) {
                  return category;
               }
            }

            return "";
         } else {
            return "";
         }
      } else {
         return "";
      }
   }

   private List<String> extractGenreIds(List<Genre> genres) {
      if (genres != null && !genres.isEmpty()) {
         List<String> ids = new ArrayList<>();

         for (Genre genre : genres) {
            if (genre != null && genre.getId() > 0) {
               ids.add(String.valueOf(genre.getId()));
            }
         }

         return ids;
      } else {
         return List.of();
      }
   }

   private List<String> toStringList(String attr, Object infoRaw) {
      List<String> values = new ArrayList<>();
      if ("production_countries".equals(attr) && infoRaw instanceof List) {
         for (Object item : (List)infoRaw) {
            String code = this.extractCountryCode(item);
            if (StringUtils.hasText(code)) {
               values.add(code.toUpperCase(Locale.ROOT));
            }
         }

         return values;
      } else {
         if (infoRaw instanceof List) {
            for (Object itemx : (List)infoRaw) {
               if (itemx != null) {
                  values.add(String.valueOf(itemx).toUpperCase(Locale.ROOT));
               }
            }
         } else if (infoRaw != null) {
            values.add(String.valueOf(infoRaw).toUpperCase(Locale.ROOT));
         }

         return values;
      }
   }

   private boolean isDigits(String value) {
      if (!StringUtils.hasText(value)) {
         return false;
      } else {
         for (int i = 0; i < value.length(); i++) {
            if (!Character.isDigit(value.charAt(i))) {
               return false;
            }
         }

         return true;
      }
   }

   private String stringValue(Object value) {
      return value == null ? "" : String.valueOf(value).trim();
   }

   private String extractCountryCode(Object item) {
      if (item == null) {
         return "";
      } else if (item instanceof Map<?, ?> map) {
         Object value = map.get("iso_3166_1");
         return value == null ? "" : String.valueOf(value).trim();
      } else {
         try {
            Method method = item.getClass().getMethod("getIso_3166_1");
            Object value = method.invoke(item);
            return value == null ? "" : String.valueOf(value).trim();
         } catch (Exception var4) {
            return "";
         }
      }
   }
}
