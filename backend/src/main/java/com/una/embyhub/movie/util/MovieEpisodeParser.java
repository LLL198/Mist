package com.una.embyhub.movie.util;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.springframework.util.StringUtils;

public final class MovieEpisodeParser {
   private static final Pattern TOKEN_SPLIT_PATTERN = Pattern.compile("[\\.\\s\\(\\)\\[\\]\\-【】/～;&\\|#_「」~]+");
   private static final Pattern METAINFO_PATTERN = Pattern.compile("(?<=\\{\\[)[\\W\\w]+(?=]})");
   private static final Pattern METAINFO_SEASON_BEGIN_PATTERN = Pattern.compile("(?<=s=)\\d+");
   private static final Pattern METAINFO_SEASON_END_PATTERN = Pattern.compile("(?<=s=\\d+-)\\d+");
   private static final Pattern METAINFO_EPISODE_BEGIN_PATTERN = Pattern.compile("(?<=e=)\\d+");
   private static final Pattern METAINFO_EPISODE_END_PATTERN = Pattern.compile("(?<=e=\\d+-)\\d+");
   private static final Pattern METAINFO_TYPE_PATTERN = Pattern.compile("(?<=type=)\\w+");
   private static final Pattern SEASON_TOKEN_PATTERN = Pattern.compile("(?i)S(\\d{3})|^S(\\d{1,3})$|S(\\d{1,3})E");
   private static final Pattern EPISODE_TOKEN_PATTERN = Pattern.compile("(?i)EP?(\\d{2,4})$|^EP?(\\d{1,4})$|^S\\d{1,2}EP?(\\d{1,4})$|S\\d{2}EP?(\\d{2,4})");
   private static final Pattern TITLE_EPISODE_PATTERN = Pattern.compile("(?i)Episode\\s+(\\d{1,4})");
   private static final Pattern SUBTITLE_SEASON_PATTERN = Pattern.compile("(?<![全共]\\s*)[第\\s]+([0-9一二三四五六七八九十S\\-]+)\\s*季(?!\\s*[全共])");
   private static final Pattern SUBTITLE_SEASON_ALL_PATTERN = Pattern.compile("[全共]\\s*([0-9一二三四五六七八九十]+)\\s*季");
   private static final Pattern SUBTITLE_EPISODE_BETWEEN_PATTERN = Pattern.compile(
      "[第]*\\s*([0-9一二三四五六七八九十百零]+)\\s*[集话話期幕]?\\s*-\\s*第*\\s*([0-9一二三四五六七八九十百零]+)\\s*[集话話期幕]"
   );
   private static final Pattern SUBTITLE_EPISODE_PATTERN = Pattern.compile("(?<![全共]\\s*)[第\\s]+([0-9一二三四五六七八九十百零EP]+)\\s*[集话話期幕](?!\\s*[全共])");
   private static final Pattern SUBTITLE_EPISODE_ALL_PATTERN = Pattern.compile("([0-9一二三四五六七八九十百零]+)\\s*集\\s*全|[全共]\\s*([0-9一二三四五六七八九十百零]+)\\s*[集话話期幕]");
   private static final Pattern RESOLUTION_PATTERN = Pattern.compile("(?i)^[SBUHD]*(\\d{3,4}[PI]+)|\\d{3,4}X(\\d{3,4})");
   private static final Pattern RESOLUTION_K_PATTERN = Pattern.compile("(?i)^[248]+K$");
   private static final Pattern VIDEO_ENCODE_PATTERN = Pattern.compile(
      "(?i)^(H26[45])$|^(X26[45])$|^AVC$|^HEVC$|^VC\\d?$|^MPEG\\d?$|^XVID$|^DIVX$|^AV1$|^HDR\\d*$|^AVS(\\+|[23])$"
   );
   private static final Pattern SUBTITLE_HINT_PATTERN = Pattern.compile("[全第季集话話期幕]");
   private static final Set<String> VIDEO_EXTS = Set.of("mkv", "mp4", "avi", "mov", "wmv", "m4v", "ts", "m2ts", "flv", "webm", "mpg", "mpeg", "rmvb", "iso");

   private MovieEpisodeParser() {
   }

   public static MovieEpisodeParser.EpisodeMeta parse(String source) {
      if (!StringUtils.hasText(source)) {
         return null;
      } else {
         MovieEpisodeParser.ParsedSource parsed = extractMetaInfo(source);
         MovieEpisodeParser.EpisodeMeta meta = new MovieEpisodeParser.EpisodeMeta();
         if (StringUtils.hasText(parsed.cleaned)) {
            parseTokens(meta, parsed.cleaned);
            parseSubtitle(meta, parsed.cleaned);
         }

         applyMetaOverride(meta, parsed.meta);
         normalizeMeta(meta);
         return meta;
      }
   }

   public static MovieEpisodeParser.EpisodeMeta parseFromPath(String contentPath) {
      return !StringUtils.hasText(contentPath) ? null : parseFromPath(Paths.get(contentPath));
   }

   public static MovieEpisodeParser.EpisodeMeta parseFromPath(Path path) {
      if (path == null) {
         return null;
      } else {
         MovieEpisodeParser.EpisodeMeta fileMeta = parse(stripExtension(path.getFileName().toString()));
         if (fileMeta == null) {
            fileMeta = new MovieEpisodeParser.EpisodeMeta();
         }

         Path parent = path.getParent();
         MovieEpisodeParser.EpisodeMeta dirMeta = parent != null ? parse(parent.getFileName().toString()) : null;
         if (shouldMerge(fileMeta, dirMeta)) {
            mergeEpisodeMeta(fileMeta, dirMeta);
         }

         Path root = parent != null ? parent.getParent() : null;
         MovieEpisodeParser.EpisodeMeta rootMeta = root != null ? parse(root.getFileName().toString()) : null;
         if (shouldMerge(fileMeta, rootMeta)) {
            mergeEpisodeMeta(fileMeta, rootMeta);
         }

         normalizeMeta(fileMeta);
         return fileMeta;
      }
   }

   public static List<String> buildEpisodeCodes(MovieEpisodeParser.EpisodeMeta meta, int maxEpisodes) {
      if (meta != null && meta.beginEpisode != null) {
         int start = meta.beginEpisode;
         int end = meta.endEpisode != null ? meta.endEpisode : meta.beginEpisode;
         if (end < start) {
            int tmp = start;
            start = end;
            end = tmp;
         }

         Integer season = meta.beginSeason;
         if (season == null && meta.isTv) {
            season = 1;
         }

         int limit = Math.min(end, start + Math.max(maxEpisodes, 1) - 1);
         List<String> episodes = new ArrayList<>();

         for (int i = start; i <= limit; i++) {
            episodes.add(formatEpisode(season, i));
         }

         return episodes;
      } else {
         return List.of();
      }
   }

   public static String buildSeasonEpisode(MovieEpisodeParser.EpisodeMeta meta) {
      if (meta == null) {
         return "";
      } else {
         String season = buildSeason(meta);
         String episode = buildEpisode(meta);
         if (StringUtils.hasText(season) && StringUtils.hasText(episode)) {
            return season + " " + episode;
         } else {
            return StringUtils.hasText(season) ? season : episode;
         }
      }
   }

   public static String buildSeasonEpisodeTag(MovieEpisodeParser.EpisodeMeta meta) {
      if (meta == null) {
         return "";
      } else {
         String season = buildSeason(meta);
         String episode = buildEpisode(meta);
         if (StringUtils.hasText(season) && StringUtils.hasText(episode)) {
            return season + episode;
         } else {
            return StringUtils.hasText(season) ? season : episode;
         }
      }
   }

   public static String buildEpisodeSeqs(MovieEpisodeParser.EpisodeMeta meta) {
      if (meta != null && meta.beginEpisode != null) {
         int start = meta.beginEpisode;
         int end = meta.endEpisode != null ? meta.endEpisode : meta.beginEpisode;
         if (end < start) {
            int tmp = start;
            start = end;
            end = tmp;
         }

         return start == end ? String.valueOf(start) : start + "-" + end;
      } else {
         return "";
      }
   }

   public static List<String> collectEpisodesFromContentPath(String contentPath, int maxEpisodes) {
      if (!StringUtils.hasText(contentPath)) {
         return List.of();
      } else {
         Path path = Paths.get(contentPath);
         if (!Files.exists(path)) {
            return List.of();
         } else {
            final Set<String> episodes = new LinkedHashSet<>();
            if (Files.isRegularFile(path)) {
               MovieEpisodeParser.EpisodeMeta meta = parseFromPath(path);
               episodes.addAll(buildEpisodeCodes(meta, maxEpisodes));
               return new ArrayList<>(episodes);
            } else {
               final int limit = Math.max(maxEpisodes, 1);

               try {
                  Files.walkFileTree(path, new FileVisitor<Path>() {
                     public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) {
                        return episodes.size() >= limit ? FileVisitResult.TERMINATE : FileVisitResult.CONTINUE;
                     }

                     public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {
                        if (episodes.size() >= limit) {
                           return FileVisitResult.TERMINATE;
                        } else if (!MovieEpisodeParser.isVideoFile(file)) {
                           return FileVisitResult.CONTINUE;
                        } else {
                           MovieEpisodeParser.EpisodeMeta meta = MovieEpisodeParser.parseFromPath(file);
                           episodes.addAll(MovieEpisodeParser.buildEpisodeCodes(meta, limit - episodes.size()));
                           return episodes.size() >= limit ? FileVisitResult.TERMINATE : FileVisitResult.CONTINUE;
                        }
                     }

                     public FileVisitResult visitFileFailed(Path file, IOException exc) {
                        return FileVisitResult.CONTINUE;
                     }

                     public FileVisitResult postVisitDirectory(Path dir, IOException exc) {
                        return episodes.size() >= limit ? FileVisitResult.TERMINATE : FileVisitResult.CONTINUE;
                     }
                  });
               } catch (IOException var6) {
                  return List.of();
               }

               return new ArrayList<>(episodes);
            }
         }
      }
   }

   private static boolean isVideoFile(Path file) {
      if (file == null) {
         return false;
      } else {
         String name = file.getFileName().toString().toLowerCase(Locale.ROOT);
         int dot = name.lastIndexOf(46);
         if (dot > 0 && dot != name.length() - 1) {
            String ext = name.substring(dot + 1);
            return VIDEO_EXTS.contains(ext);
         } else {
            return false;
         }
      }
   }

   private static MovieEpisodeParser.ParsedSource extractMetaInfo(String source) {
      MovieEpisodeParser.EpisodeMeta meta = new MovieEpisodeParser.EpisodeMeta();
      if (!StringUtils.hasText(source)) {
         return new MovieEpisodeParser.ParsedSource("", meta);
      } else {
         String cleaned = source;
         Matcher matcher = METAINFO_PATTERN.matcher(source);

         while (matcher.find()) {
            String result = matcher.group();
            MovieEpisodeParser.EpisodeMeta fragment = parseMetaFragment(result);
            boolean hasAny = fragment.hasEpisode() || fragment.hasSeason() || fragment.isTv;
            if (fragment.hasSeason()) {
               meta.beginSeason = fragment.beginSeason;
               meta.endSeason = fragment.endSeason;
               meta.isTv = true;
            }

            if (fragment.hasEpisode()) {
               meta.beginEpisode = fragment.beginEpisode;
               meta.endEpisode = fragment.endEpisode;
               meta.isTv = true;
            }

            if (fragment.isTv) {
               meta.isTv = true;
            }

            if (hasAny) {
               cleaned = cleaned.replace("{[" + result + "]}", "");
            }
         }

         return new MovieEpisodeParser.ParsedSource(cleaned, meta);
      }
   }

   private static MovieEpisodeParser.EpisodeMeta parseMetaFragment(String fragment) {
      MovieEpisodeParser.EpisodeMeta meta = new MovieEpisodeParser.EpisodeMeta();
      if (!StringUtils.hasText(fragment)) {
         return meta;
      } else {
         String type = findFirstGroup(METAINFO_TYPE_PATTERN, fragment);
         if (StringUtils.hasText(type) && "tv".equalsIgnoreCase(type)) {
            meta.isTv = true;
         }

         String beginSeason = findFirstGroup(METAINFO_SEASON_BEGIN_PATTERN, fragment);
         String endSeason = findFirstGroup(METAINFO_SEASON_END_PATTERN, fragment);
         meta.beginSeason = parseIntSafe(beginSeason);
         meta.endSeason = parseIntSafe(endSeason);
         if (meta.beginSeason != null && meta.endSeason != null && meta.beginSeason > meta.endSeason) {
            int tmp = meta.beginSeason;
            meta.beginSeason = meta.endSeason;
            meta.endSeason = tmp;
         }

         String beginEpisode = findFirstGroup(METAINFO_EPISODE_BEGIN_PATTERN, fragment);
         String endEpisode = findFirstGroup(METAINFO_EPISODE_END_PATTERN, fragment);
         meta.beginEpisode = parseIntSafe(beginEpisode);
         meta.endEpisode = parseIntSafe(endEpisode);
         if (meta.beginEpisode != null && meta.endEpisode != null && meta.beginEpisode > meta.endEpisode) {
            int tmp = meta.beginEpisode;
            meta.beginEpisode = meta.endEpisode;
            meta.endEpisode = tmp;
         }

         if (meta.beginSeason != null || meta.beginEpisode != null) {
            meta.isTv = true;
         }

         return meta;
      }
   }

   private static void applyMetaOverride(MovieEpisodeParser.EpisodeMeta target, MovieEpisodeParser.EpisodeMeta override) {
      if (target != null && override != null) {
         if (override.beginSeason != null) {
            target.beginSeason = override.beginSeason;
            target.endSeason = override.endSeason;
         }

         if (override.beginEpisode != null) {
            target.beginEpisode = override.beginEpisode;
            target.endEpisode = override.endEpisode;
         }

         if (override.isTv) {
            target.isTv = true;
         }
      }
   }

   private static void parseTokens(MovieEpisodeParser.EpisodeMeta meta, String title) {
      if (meta != null && StringUtils.hasText(title)) {
         String[] tokens = TOKEN_SPLIT_PATTERN.split(title);
         String lastTokenType = "";

         for (int i = 0; i < tokens.length; i++) {
            String token = tokens[i];
            if (StringUtils.hasText(token)) {
               String upper = token.toUpperCase(Locale.ROOT);
               String nextToken = i + 1 < tokens.length ? tokens[i + 1] : "";
               if (isVideoEncodePrefix(upper, nextToken)) {
                  lastTokenType = "videoencode";
               } else if ("EPISODE".equals(upper)) {
                  lastTokenType = "EPISODE";
               } else if ("SEASON".equals(upper)) {
                  lastTokenType = "SEASON";
               } else if (isYearToken(token)) {
                  lastTokenType = "year";
               } else if (isResolutionToken(token)) {
                  lastTokenType = "pix";
               } else if (isVideoEncodeToken(upper)) {
                  lastTokenType = "videoencode";
               } else {
                  Integer season = parseSeasonToken(token);
                  if (season != null) {
                     if (meta.beginSeason == null) {
                        meta.beginSeason = season;
                        meta.totalSeason = 1;
                     }

                     meta.isTv = true;
                     lastTokenType = "season";
                  }

                  if ("SEASON".equals(lastTokenType) && isShortNumber(token, 3)) {
                     Integer value = parseIntSafe(token);
                     if (value != null && meta.beginSeason == null) {
                        meta.beginSeason = value;
                        meta.totalSeason = 1;
                        meta.isTv = true;
                        lastTokenType = "season";
                        continue;
                     }
                  }

                  Integer episode = parseEpisodeToken(token);
                  if (episode != null) {
                     meta.isTv = true;
                     if (meta.beginEpisode == null) {
                        meta.beginEpisode = episode;
                        meta.totalEpisode = 1;
                     } else if (episode > meta.beginEpisode) {
                        meta.endEpisode = episode;
                        meta.totalEpisode = meta.endEpisode - meta.beginEpisode + 1;
                     }

                     lastTokenType = "episode";
                  } else if (isDigits(token)) {
                     Integer value = parseIntSafe(token);
                     if (value != null) {
                        if (meta.beginEpisode != null
                           && meta.endEpisode == null
                           && token.length() < 5
                           && value > meta.beginEpisode
                           && "episode".equals(lastTokenType)) {
                           meta.endEpisode = value;
                           meta.totalEpisode = meta.endEpisode - meta.beginEpisode + 1;
                           meta.isTv = true;
                        } else if (meta.beginEpisode == null
                           && token.length() > 1
                           && token.length() < 4
                           && !"year".equals(lastTokenType)
                           && !"videoencode".equals(lastTokenType)) {
                           meta.beginEpisode = value;
                           meta.totalEpisode = 1;
                           meta.isTv = true;
                           lastTokenType = "episode";
                        } else if ("EPISODE".equals(lastTokenType) && meta.beginEpisode == null && token.length() < 5) {
                           meta.beginEpisode = value;
                           meta.totalEpisode = 1;
                           meta.isTv = true;
                           lastTokenType = "episode";
                        }
                     }
                  }
               }
            }
         }
      }
   }

   private static void parseSubtitle(MovieEpisodeParser.EpisodeMeta meta, String titleText) {
      if (meta != null && StringUtils.hasText(titleText)) {
         String text = " " + titleText + " ";
         Matcher titleMatcher = TITLE_EPISODE_PATTERN.matcher(text);
         if (titleMatcher.find()) {
            Integer episode = parseIntSafe(titleMatcher.group(1));
            if (episode != null && meta.beginEpisode == null) {
               meta.beginEpisode = episode;
               meta.totalEpisode = 1;
               meta.isTv = true;
            }
         } else if (SUBTITLE_HINT_PATTERN.matcher(text).find()) {
            Matcher seasonAllMatcher = SUBTITLE_SEASON_ALL_PATTERN.matcher(text);
            if (seasonAllMatcher.find()) {
               String value = seasonAllMatcher.group(1);
               if (!StringUtils.hasText(value)) {
                  value = seasonAllMatcher.group(2);
               }

               Integer totalSeason = parseSmartNumber(value);
               if (totalSeason != null && meta.beginSeason == null && meta.beginEpisode == null) {
                  meta.totalSeason = totalSeason;
                  meta.beginSeason = 1;
                  meta.endSeason = totalSeason;
                  meta.isTv = true;
               }
            } else {
               Matcher seasonMatcher = SUBTITLE_SEASON_PATTERN.matcher(text);
               if (seasonMatcher.find()) {
                  String seasons = seasonMatcher.group(1);
                  if (StringUtils.hasText(seasons)) {
                     seasons = seasons.toUpperCase(Locale.ROOT).replace("S", "").trim();
                  }

                  Integer beginSeason = null;
                  Integer endSeason = null;
                  if (StringUtils.hasText(seasons)) {
                     String[] parts = seasons.split("-");
                     beginSeason = parseSmartNumber(parts[0].trim());
                     if (parts.length > 1) {
                        endSeason = parseSmartNumber(parts[1].trim());
                     }
                  }

                  if (beginSeason != null && beginSeason > 100) {
                     beginSeason = null;
                  }

                  if (endSeason != null && endSeason > 100) {
                     endSeason = null;
                  }

                  if (meta.beginSeason == null && beginSeason != null) {
                     meta.beginSeason = beginSeason;
                     meta.totalSeason = 1;
                  }

                  if (meta.beginSeason != null && meta.endSeason == null && endSeason != null && !endSeason.equals(meta.beginSeason)) {
                     meta.endSeason = endSeason;
                     meta.totalSeason = meta.endSeason - meta.beginSeason + 1;
                  }

                  meta.isTv = true;
               }

               Matcher episodeBetweenMatcher = SUBTITLE_EPISODE_BETWEEN_PATTERN.matcher(text);
               if (episodeBetweenMatcher.find()) {
                  Integer beginEpisode = parseSmartNumber(episodeBetweenMatcher.group(1));
                  Integer endEpisode = parseSmartNumber(episodeBetweenMatcher.group(2));
                  if (beginEpisode != null && beginEpisode >= 10000) {
                     beginEpisode = null;
                  }

                  if (endEpisode != null && endEpisode >= 10000) {
                     endEpisode = null;
                  }

                  if (meta.beginEpisode == null && beginEpisode != null) {
                     meta.beginEpisode = beginEpisode;
                     meta.totalEpisode = 1;
                  }

                  if (meta.beginEpisode != null && meta.endEpisode == null && endEpisode != null && !endEpisode.equals(meta.beginEpisode)) {
                     meta.endEpisode = endEpisode;
                     meta.totalEpisode = meta.endEpisode - meta.beginEpisode + 1;
                  }

                  meta.isTv = true;
               } else {
                  Matcher episodeMatcher = SUBTITLE_EPISODE_PATTERN.matcher(text);
                  if (episodeMatcher.find()) {
                     String episodes = episodeMatcher.group(1);
                     if (StringUtils.hasText(episodes)) {
                        episodes = episodes.toUpperCase(Locale.ROOT).replace("E", "").replace("P", "").trim();
                     }

                     Integer beginEpisodex = null;
                     Integer endEpisodex = null;
                     if (StringUtils.hasText(episodes)) {
                        if (episodes.contains("-")) {
                           String[] parts = episodes.split("-");
                           beginEpisodex = parseSmartNumber(parts[0].trim());
                           if (parts.length > 1) {
                              endEpisodex = parseSmartNumber(parts[1].trim());
                           }
                        } else {
                           beginEpisodex = parseSmartNumber(episodes);
                        }
                     }

                     if (beginEpisodex != null && beginEpisodex >= 10000) {
                        beginEpisodex = null;
                     }

                     if (endEpisodex != null && endEpisodex >= 10000) {
                        endEpisodex = null;
                     }

                     if (meta.beginEpisode == null && beginEpisodex != null) {
                        meta.beginEpisode = beginEpisodex;
                        meta.totalEpisode = 1;
                     }

                     if (meta.beginEpisode != null && meta.endEpisode == null && endEpisodex != null && !endEpisodex.equals(meta.beginEpisode)) {
                        meta.endEpisode = endEpisodex;
                        meta.totalEpisode = meta.endEpisode - meta.beginEpisode + 1;
                     }

                     meta.isTv = true;
                  } else {
                     Matcher episodeAllMatcher = SUBTITLE_EPISODE_ALL_PATTERN.matcher(text);
                     if (episodeAllMatcher.find()) {
                        String valuex = episodeAllMatcher.group(1);
                        if (!StringUtils.hasText(valuex)) {
                           valuex = episodeAllMatcher.group(2);
                        }

                        Integer totalEpisode = parseSmartNumber(valuex);
                        if (totalEpisode != null && meta.beginEpisode == null) {
                           meta.totalEpisode = totalEpisode;
                           meta.isTv = true;
                        }
                     }
                  }
               }
            }
         }
      }
   }

   private static Integer parseSeasonToken(String token) {
      Matcher matcher = SEASON_TOKEN_PATTERN.matcher(token);
      if (!matcher.find()) {
         return null;
      } else {
         for (int i = 1; i <= matcher.groupCount(); i++) {
            String group = matcher.group(i);
            if (StringUtils.hasText(group) && isDigits(group)) {
               return parseIntSafe(group);
            }
         }

         return null;
      }
   }

   private static Integer parseEpisodeToken(String token) {
      Matcher matcher = EPISODE_TOKEN_PATTERN.matcher(token);
      if (!matcher.find()) {
         return null;
      } else {
         for (int i = 1; i <= matcher.groupCount(); i++) {
            String group = matcher.group(i);
            if (StringUtils.hasText(group) && isDigits(group)) {
               return parseIntSafe(group);
            }
         }

         return null;
      }
   }

   private static boolean isYearToken(String token) {
      if (isDigits(token) && token.length() == 4) {
         Integer value = parseIntSafe(token);
         return value != null && value >= 1900 && value <= 2099;
      } else {
         return false;
      }
   }

   private static boolean isResolutionToken(String token) {
      return !StringUtils.hasText(token) ? false : RESOLUTION_PATTERN.matcher(token).find() || RESOLUTION_K_PATTERN.matcher(token).find();
   }

   private static boolean isVideoEncodeToken(String token) {
      return !StringUtils.hasText(token) ? false : VIDEO_ENCODE_PATTERN.matcher(token).matches();
   }

   private static boolean isVideoEncodePrefix(String token, String nextToken) {
      if (!StringUtils.hasText(token) || !StringUtils.hasText(nextToken)) {
         return false;
      } else if (!"H".equals(token) && !"X".equals(token)) {
         return false;
      } else {
         String next = nextToken.toUpperCase(Locale.ROOT);
         return isDigits(next) && next.length() == 3 ? next.startsWith("26") && (next.endsWith("4") || next.endsWith("5") || next.endsWith("6")) : false;
      }
   }

   private static boolean isDigits(String value) {
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

   private static boolean isShortNumber(String value, int maxLen) {
      return StringUtils.hasText(value) && isDigits(value) && value.length() < maxLen;
   }

   private static Integer parseIntSafe(String value) {
      if (!isDigits(value)) {
         return null;
      } else {
         try {
            return Integer.parseInt(value);
         } catch (NumberFormatException var2) {
            return null;
         }
      }
   }

   private static String findFirstGroup(Pattern pattern, String text) {
      if (pattern != null && StringUtils.hasText(text)) {
         Matcher matcher = pattern.matcher(text);
         return matcher.find() ? matcher.group() : "";
      } else {
         return "";
      }
   }

   private static Integer parseSmartNumber(String value) {
      if (!StringUtils.hasText(value)) {
         return null;
      } else {
         String trimmed = value.trim();
         if (isDigits(trimmed)) {
            return parseIntSafe(trimmed);
         } else {
            int parsed = parseChineseNumber(trimmed);
            return parsed <= 0 ? null : parsed;
         }
      }
   }

   private static int parseChineseNumber(String value) {
      if (!StringUtils.hasText(value)) {
         return 0;
      } else {
         int result = 0;
         int section = 0;
         int number = 0;

         for (int i = 0; i < value.length(); i++) {
            char ch = value.charAt(i);
            Integer digit = chineseDigit(ch);
            if (digit != null) {
               number = digit;
            } else {
               int unit = chineseUnit(ch);
               if (unit > 0) {
                  if (number == 0) {
                     number = 1;
                  }

                  if (unit == 10000) {
                     section += number;
                     result += section * unit;
                     section = 0;
                  } else {
                     section += number * unit;
                  }

                  number = 0;
               }
            }
         }

         return result + section + number;
      }
   }

   private static Integer chineseDigit(char ch) {
      return switch (ch) {
         case '一' -> 1;
         case '七' -> 7;
         case '三' -> 3;
         case '两', '二' -> 2;
         case '九' -> 9;
         case '五' -> 5;
         case '八' -> 8;
         case '六' -> 6;
         case '四' -> 4;
         case '零' -> 0;
         default -> null;
      };
   }

   private static int chineseUnit(char ch) {
      return switch (ch) {
         case '万' -> 10000;
         case '十' -> 10;
         case '千' -> 1000;
         case '百' -> 100;
         default -> 0;
      };
   }

   private static void normalizeMeta(MovieEpisodeParser.EpisodeMeta meta) {
      if (meta != null) {
         if (meta.beginEpisode != null && meta.endEpisode != null && meta.beginEpisode > meta.endEpisode) {
            int tmp = meta.beginEpisode;
            meta.beginEpisode = meta.endEpisode;
            meta.endEpisode = tmp;
         }

         if (meta.beginSeason != null && meta.endSeason != null && meta.beginSeason > meta.endSeason) {
            int tmp = meta.beginSeason;
            meta.beginSeason = meta.endSeason;
            meta.endSeason = tmp;
         }

         if ((meta.beginEpisode != null || meta.beginSeason != null) && !meta.isTv) {
            meta.isTv = true;
         }
      }
   }

   private static boolean shouldMerge(MovieEpisodeParser.EpisodeMeta target, MovieEpisodeParser.EpisodeMeta other) {
      return target != null && other != null ? target.isTv || !other.isTv : false;
   }

   private static void mergeEpisodeMeta(MovieEpisodeParser.EpisodeMeta target, MovieEpisodeParser.EpisodeMeta other) {
      if (target != null && other != null) {
         if (!target.isTv && other.isTv) {
            target.isTv = true;
         }

         if (target.isTv && target.beginSeason == null) {
            target.beginSeason = other.beginSeason;
            target.endSeason = other.endSeason;
            target.totalSeason = other.totalSeason;
         }

         if (target.isTv && target.beginEpisode == null) {
            target.beginEpisode = other.beginEpisode;
            target.endEpisode = other.endEpisode;
            target.totalEpisode = other.totalEpisode;
         }
      }
   }

   private static String buildSeason(MovieEpisodeParser.EpisodeMeta meta) {
      if (meta == null) {
         return "";
      } else {
         Integer season = meta.beginSeason;
         if (season == null && meta.isTv) {
            season = 1;
         }

         if (season == null) {
            return "";
         } else {
            Integer end = meta.endSeason;
            return end != null && !end.equals(season) ? String.format("S%02d-S%02d", season, end) : String.format("S%02d", season);
         }
      }
   }

   private static String buildEpisode(MovieEpisodeParser.EpisodeMeta meta) {
      if (meta != null && meta.beginEpisode != null) {
         int start = meta.beginEpisode;
         int end = meta.endEpisode != null ? meta.endEpisode : meta.beginEpisode;
         if (end < start) {
            int tmp = start;
            start = end;
            end = tmp;
         }

         String startTag = String.format("E%02d", start);
         return start == end ? startTag : startTag + "-" + String.format("E%02d", end);
      } else {
         return "";
      }
   }

   private static String formatEpisode(Integer season, int episode) {
      return season == null ? String.format("E%02d", episode) : String.format("S%02dE%02d", season, episode);
   }

   private static String stripExtension(String segment) {
      if (!StringUtils.hasText(segment)) {
         return segment;
      } else {
         int idx = segment.lastIndexOf(46);
         if (idx > 0 && idx != segment.length() - 1) {
            String ext = segment.substring(idx + 1);
            return ext.length() > 0 && ext.length() <= 5 ? segment.substring(0, idx) : segment;
         } else {
            return segment;
         }
      }
   }

   public static final class EpisodeMeta {
      private Integer beginSeason;
      private Integer endSeason;
      private Integer totalSeason;
      private Integer beginEpisode;
      private Integer endEpisode;
      private Integer totalEpisode;
      private boolean isTv;

      public Integer getBeginSeason() {
         return this.beginSeason;
      }

      public Integer getEndSeason() {
         return this.endSeason;
      }

      public Integer getBeginEpisode() {
         return this.beginEpisode;
      }

      public Integer getEndEpisode() {
         return this.endEpisode;
      }

      public boolean isTv() {
         return this.isTv;
      }

      public boolean hasEpisode() {
         return this.beginEpisode != null;
      }

      public boolean hasSeason() {
         return this.beginSeason != null;
      }
   }

   private static final class ParsedSource {
      private final String cleaned;
      private final MovieEpisodeParser.EpisodeMeta meta;

      private ParsedSource(String cleaned, MovieEpisodeParser.EpisodeMeta meta) {
         this.cleaned = cleaned;
         this.meta = meta;
      }
   }
}
