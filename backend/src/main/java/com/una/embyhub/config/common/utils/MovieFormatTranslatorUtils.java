package com.una.embyhub.config.common.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MovieFormatTranslatorUtils {
   private static final Map<String, String> TRANSLATION_MAP = new HashMap<>();
   private static final Pattern TRANSLATION_PATTERN;

   public static String translate(String format) {
      if (format != null && !format.trim().isEmpty()) {
         String input = format.trim();
         StringBuilder result = new StringBuilder();
         Matcher matcher = TRANSLATION_PATTERN.matcher(input);
         int lastEnd = 0;

         while (matcher.find()) {
            int start = matcher.start();
            int end = matcher.end();
            if (start > lastEnd) {
               result.append(input, lastEnd, start);
            }

            String matchedKey = input.substring(start, end);
            String translatedValue = TRANSLATION_MAP.get(matchedKey);
            result.append(translatedValue);
            lastEnd = end;
         }

         if (lastEnd < input.length()) {
            result.append(input.substring(lastEnd));
         }

         return result.toString();
      } else {
         return "";
      }
   }

   public static void main(String[] args) {
      System.out.println(translate("1080p H264"));
      System.out.println(translate("2160p HDR10+ Dolby Vision"));
      System.out.println(translate("Bluray Remux HDR10 Adaptive"));
      System.out.println(translate("H265 DTS-HD MA"));
      System.out.println(translate("WEB-DL AAC"));
      System.out.println(translate("AV1 Dolby Vision HDR10+"));
      System.out.println(translate("4K UHD Blu-ray HDR10+ Atmos"));
      System.out.println(translate("10-bit HLG BT.2020"));
      System.out.println(translate("12-bit HDR10+ 4:4:4"));
      System.out.println(translate("Directors Cut Dual Audio"));
      System.out.println(translate("IMAX Enhanced 1080p"));
   }

   static {
      TRANSLATION_MAP.put("1080p", "1080p全高清");
      TRANSLATION_MAP.put("720p", "720p高清");
      TRANSLATION_MAP.put("2160p", "2160p超高清(4K)");
      TRANSLATION_MAP.put("4K", "4K超高清");
      TRANSLATION_MAP.put("8K", "8K超高清");
      TRANSLATION_MAP.put("1440p", "1440p准4K");
      TRANSLATION_MAP.put("576p", "576p标清");
      TRANSLATION_MAP.put("480p", "480p标清");
      TRANSLATION_MAP.put("H264", "H.264");
      TRANSLATION_MAP.put("H265", "H.265/HEVC");
      TRANSLATION_MAP.put("HEVC", "H.265/HEVC");
      TRANSLATION_MAP.put("x264", "x264");
      TRANSLATION_MAP.put("x265", "x265");
      TRANSLATION_MAP.put("VP9", "VP9");
      TRANSLATION_MAP.put("AV1", "AV1");
      TRANSLATION_MAP.put("MPEG-2", "MPEG-2");
      TRANSLATION_MAP.put("MPEG-4", "MPEG-4");
      TRANSLATION_MAP.put("VC-1", "VC-1");
      TRANSLATION_MAP.put("ProRes", "ProRes");
      TRANSLATION_MAP.put("DNxHR", "DNxHR");
      TRANSLATION_MAP.put("AAC", "AAC");
      TRANSLATION_MAP.put("AC3", "杜比数字(AC-3)");
      TRANSLATION_MAP.put("EAC3", "杜比数字+(E-AC-3)");
      TRANSLATION_MAP.put("DTS", "DTS");
      TRANSLATION_MAP.put("DTS-HD MA", "DTS-HD 主音轨");
      TRANSLATION_MAP.put("DTS:X", "DTS:X");
      TRANSLATION_MAP.put("TrueHD", "杜比TrueHD");
      TRANSLATION_MAP.put("FLAC", "FLAC无损音频");
      TRANSLATION_MAP.put("PCM", "PCM");
      TRANSLATION_MAP.put("Opus", "Opus");
      TRANSLATION_MAP.put("MP3", "MP3");
      TRANSLATION_MAP.put("HDR", "高动态范围");
      TRANSLATION_MAP.put("SDR", "标准动态范围");
      TRANSLATION_MAP.put("HDR10", "HDR10");
      TRANSLATION_MAP.put("HDR10+", "HDR10+");
      TRANSLATION_MAP.put("Dolby Vision", "杜比视界");
      TRANSLATION_MAP.put("HDR10 Adaptive", "HDR10自适应");
      TRANSLATION_MAP.put("HLG", "混合对数伽马(HLG)");
      TRANSLATION_MAP.put("Technicolor HDR", "特艺彩色HDR");
      TRANSLATION_MAP.put("BT.2020", "BT.2020");
      TRANSLATION_MAP.put("Rec.2020", "Rec.2020");
      TRANSLATION_MAP.put("P3", "DCI-P3");
      TRANSLATION_MAP.put("Wide Color Gamut", "广色域");
      TRANSLATION_MAP.put("Remux", "重封装");
      TRANSLATION_MAP.put("Bluray", "蓝光");
      TRANSLATION_MAP.put("WEB-DL", "网络下载");
      TRANSLATION_MAP.put("WEBRip", "网络rips");
      TRANSLATION_MAP.put("HDTV", "高清电视");
      TRANSLATION_MAP.put("DVDRip", "DVDrips");
      TRANSLATION_MAP.put("Ultra HD", "超高清");
      TRANSLATION_MAP.put("HD", "高清");
      TRANSLATION_MAP.put("SD", "标清");
      TRANSLATION_MAP.put("UHD", "超高清");
      TRANSLATION_MAP.put("HFR", "高帧率");
      TRANSLATION_MAP.put("SDR", "标准动态范围");
      TRANSLATION_MAP.put("Atmos", "杜比全景声");
      TRANSLATION_MAP.put("DTS:X", "DTS:X");
      TRANSLATION_MAP.put("Multi-Audio", "多音频");
      TRANSLATION_MAP.put("Dual Audio", "双音频");
      TRANSLATION_MAP.put("Subbed", "带字幕");
      TRANSLATION_MAP.put("Unrated", "未分级");
      TRANSLATION_MAP.put("Extended", "加长版");
      TRANSLATION_MAP.put("Directors Cut", "导演剪辑版");
      TRANSLATION_MAP.put("Limited", "限量版");
      TRANSLATION_MAP.put("Theatrical", "院线版");
      TRANSLATION_MAP.put("IMAX", "IMAX");
      TRANSLATION_MAP.put("IMAX Enhanced", "IMAX增强版");
      TRANSLATION_MAP.put("4K UHD Blu-ray", "4K超高清蓝光");
      TRANSLATION_MAP.put("HDR10+ Adaptive", "HDR10+自适应");
      TRANSLATION_MAP.put("Dolby Atmos", "杜比全景声");
      TRANSLATION_MAP.put("Hi-Res Audio", "高解析度音频");
      TRANSLATION_MAP.put("Lossless", "无损");
      TRANSLATION_MAP.put("Lossy", "有损");
      TRANSLATION_MAP.put("Dual Layer", "双层");
      TRANSLATION_MAP.put("Triple Layer", "三层");
      TRANSLATION_MAP.put("Hybrid SDR/HDR", "混合SDR/HDR");
      TRANSLATION_MAP.put("10-bit", "10位");
      TRANSLATION_MAP.put("12-bit", "12位");
      TRANSLATION_MAP.put("8-bit", "8位");
      TRANSLATION_MAP.put("4:2:0", "4:2:0");
      TRANSLATION_MAP.put("4:2:2", "4:2:2");
      TRANSLATION_MAP.put("4:4:4", "4:4:4");
      TRANSLATION_MAP.put("High Bitrate", "高码率");
      TRANSLATION_MAP.put("Low Bitrate", "低码率");
      TRANSLATION_MAP.put("Variable Bitrate", "可变码率");
      TRANSLATION_MAP.put("Constant Bitrate", "固定码率");
      List<String> keys = new ArrayList<>(TRANSLATION_MAP.keySet());
      keys.sort((a, b) -> b.length() - a.length());
      StringBuilder patternBuilder = new StringBuilder();
      patternBuilder.append("(");

      for (int i = 0; i < keys.size(); i++) {
         String key = keys.get(i);
         String escapedKey = Pattern.quote(key);
         patternBuilder.append(escapedKey);
         if (i < keys.size() - 1) {
            patternBuilder.append("|");
         }
      }

      patternBuilder.append(")");
      TRANSLATION_PATTERN = Pattern.compile(patternBuilder.toString());
   }
}
