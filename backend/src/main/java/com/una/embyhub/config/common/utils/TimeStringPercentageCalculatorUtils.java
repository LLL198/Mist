package com.una.embyhub.config.common.utils;

import java.text.DecimalFormat;

public class TimeStringPercentageCalculatorUtils {
   public static String calculatePercentage(String currentTime, String totalTime) {
      int currentSeconds = parseTimeToSeconds(currentTime);
      int totalSeconds = parseTimeToSeconds(totalTime);
      if (totalSeconds <= 0) {
         throw new IllegalArgumentException("总时长不能为0或负数");
      } else if (currentSeconds > totalSeconds) {
         throw new IllegalArgumentException("当前时间不能超过总时长");
      } else {
         double doublePercentage = (double)currentSeconds / (double)totalSeconds * 100.0;
         return formatPercentage(doublePercentage);
      }
   }

   private static int parseTimeToSeconds(String timeString) {
      if (timeString != null && !timeString.trim().isEmpty()) {
         String[] parts = timeString.split(":");

         try {
            int seconds = 0;
            int minutes = 0;
            int hours = 0;
            if (parts.length == 2) {
               minutes = Integer.parseInt(parts[0]);
               seconds = Integer.parseInt(parts[1]);
            } else {
               hours = Integer.parseInt(parts[0]);
               minutes = Integer.parseInt(parts[1]);
               seconds = Integer.parseInt(parts[2]);
            }

            if (hours < 0 || hours > 23) {
               throw new IllegalArgumentException("小时必须在0-23之间");
            } else if (minutes < 0 || minutes > 59) {
               throw new IllegalArgumentException("分钟必须在0-59之间");
            } else if (seconds >= 0 && seconds <= 59) {
               return hours * 3600 + minutes * 60 + seconds;
            } else {
               throw new IllegalArgumentException("秒必须在0-59之间");
            }
         } catch (NumberFormatException var5) {
            throw new IllegalArgumentException("时间格式无效，必须包含数字");
         }
      } else {
         throw new IllegalArgumentException("时间字符串不能为空");
      }
   }

   public static String formatPercentage(double percentage) {
      DecimalFormat df = new DecimalFormat("#.##");
      return df.format(percentage) + "%";
   }
}
