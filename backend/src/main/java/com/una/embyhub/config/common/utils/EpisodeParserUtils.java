package com.una.embyhub.config.common.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import lombok.Generated;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EpisodeParserUtils {
   @Generated
   private static final Logger log = LoggerFactory.getLogger(EpisodeParserUtils.class);

   public static void main(String[] args) {
      String input1 = "新 柯南非走不可 - S1, Ep1 - 第1话 在 c32cb9537bfe";
      String input2 = "动漫名称 - S2, Ep3 - 精彩片段 在 abc123";
      String input3 = "某电视剧 - S5, Ep10 - 大结局";
      parseEpisodeInfo(input1);
      parseEpisodeInfo(input2);
      parseEpisodeInfo(input3);
   }

   public static String parseEpisodeInfo(String input) {
      String patternStr = "S(\\d+),\\s*Ep(\\d+)";
      Pattern pattern = Pattern.compile(patternStr);
      Matcher matcher = pattern.matcher(input);
      if (matcher.find()) {
         int season = Integer.parseInt(matcher.group(1));
         int episode = Integer.parseInt(matcher.group(2));
         String seasonDesc = "第" + season + "季";
         String episodeDesc = "第" + episode + "集";
         log.info("原始字符串: " + input);
         log.info("解析结果: " + seasonDesc + " " + episodeDesc);
         log.info("------------------------");
         return seasonDesc + " " + episodeDesc;
      } else {
         log.info("未找到匹配的集数信息: " + input);
         return "";
      }
   }

   public static EpisodeParserUtils.EpisodeInfo parseEpisodeInfoWithReturn(String input) {
      String patternStr = "S(\\d+),\\s*Ep(\\d+)";
      Pattern pattern = Pattern.compile(patternStr);
      Matcher matcher = pattern.matcher(input);
      if (matcher.find()) {
         int season = Integer.parseInt(matcher.group(1));
         int episode = Integer.parseInt(matcher.group(2));
         return new EpisodeParserUtils.EpisodeInfo(season, episode);
      } else {
         return null;
      }
   }

   public static class EpisodeInfo {
      private int season;
      private int episode;

      public EpisodeInfo(int season, int episode) {
         this.season = season;
         this.episode = episode;
      }

      public int getSeason() {
         return this.season;
      }

      public int getEpisode() {
         return this.episode;
      }

      @Override
      public String toString() {
         return this.season + "季 第" + this.episode + "集";
      }
   }
}
