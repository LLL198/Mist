package com.una.embyhub.config.common.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FilmFilenameProcessorUtils {
   public static void main(String[] args) {
      String[] testFilenames = new String[]{
         "Friends.S01E01.The.One.Where.Monica.Gets.A.Roommate.720p.BluRay.x264-[YSX].mp4",
         "Breaking.Bad.S05E16.Felina.1080p.BluRay.x264-[YSX].mp4",
         "Inception.2010.1080p.BluRay.x264-[YSX].mp4",
         "Avengers.Endgame.2019.2160p.UHD.BluRay.x265-[YSX].mkv",
         "The.Prestige.2006.BluRay.1080p.DTS.x264-CHD【电影天堂】.mp4",
         "John Wick: Chapter 3 - Parabellum (2019) [1080p] [BluRay].mkv",
         "Alita.Battle.Angel.2019.2160p.UHD.BluRay.x265.HDR.DTS-HD.MA.7.1.mp4",
         "Blade Runner 2049 (2017) [1080p] [YTS.MX].mp4",
         "Toy Story 4 (2019) [1080p] [Dual Audio].mkv",
         "Star Wars: The Rise of Skywalker (2019) [UHD] [HDR10].mkv",
         "1917 (2020) [BluRay] [DTS-HD MA 5.1].mkv",
         "Tenet (2020) [IMAX] [1080p].mp4",
         "The Mandalorian S01E01 Chapter 1 [1080p] Disney+.mkv",
         "Better Call Saul S06E09 Nippy [1080p] AMZN.WEB-DL.mkv",
         "Money.Heist.S05.Part2.Complete.720p.NF.[WEB-DL].mkv",
         "【科幻】Dune.2021.1080p.BluRay.x264-[YSX].mp4"
      };

      for (String filename : testFilenames) {
         System.out.println("原始文件名: " + filename);
         System.out.println("处理后（TMDb搜索格式）: " + processFilenameForTMDb(filename));
         System.out.println();
      }
   }

   public static String processFilenameForTMDb(String filename) {
      int dotIndex = filename.lastIndexOf(46);
      String baseName = dotIndex != -1 ? filename.substring(0, dotIndex) : filename;
      baseName = baseName.replaceAll("\\[.*?\\]", "");
      baseName = baseName.replaceAll("【.*?】", "");
      String year = "";
      Matcher yearInParenthesesMatcher = Pattern.compile(".*\\((19|20)\\d{2}\\).*").matcher(baseName);
      if (yearInParenthesesMatcher.find()) {
         year = yearInParenthesesMatcher.group().replaceAll(".*\\((\\d{4})\\).*", "$1");
         baseName = baseName.replaceAll("\\(\\d{4}\\)", "").trim();
      } else {
         Matcher standaloneYearMatcher = Pattern.compile("(?<!\\d)(19|20)\\d{2}(?!\\d)").matcher(baseName);
         if (standaloneYearMatcher.find()) {
            year = standaloneYearMatcher.group();
            baseName = baseName.replace(year, "").trim();
         }
      }

      baseName = baseName.replaceAll("(?i)(S|Season|Series|Part)\\s*\\d+\\s*(E|Episode)?\\s*\\d*", "");
      baseName = baseName.replaceAll("(?i)\\d+p|UHD|HD|BluRay|WEB-DL|HDR|HDR10|DV|x264|x265|HEVC|AVC|DTS|DD5.1|DD\\+5.1|Atmos", "");
      baseName = baseName.replaceAll(
         "(?i)BRRip|BDRip|AMZN|NF|YIFY|YTS|WEB|H.264|H.265|Dual Audio|Multi Audio|REPACK|PROPER|UNRATED|EXTENDED|CUT|DIRECTORS CUT|IMAX|4K", ""
      );
      baseName = baseName.replaceAll("[._-]+", " ");
      baseName = baseName.replaceAll("\\s+", " ").trim();
      return formatForTMDb(baseName, year);
   }

   private static String formatForTMDb(String title, String year) {
      StringBuilder result = new StringBuilder();
      if (!title.isEmpty()) {
         result.append(title);
      } else if (!year.isEmpty()) {
         result.append(year);
      }

      return result.toString();
   }
}
