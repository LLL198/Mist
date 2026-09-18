package com.una.embyhub.config.common.utils;

public class CheckPasswordUtils {
   public static boolean isContinuousChar(String password) {
      char[] chars = password.toCharArray();

      for (int i = 0; i < chars.length - 2; i++) {
         int n1 = chars[i];
         int n2 = chars[i + 1];
         int n3 = chars[i + 2];
         if (n1 == n2 && n1 == n3) {
            return true;
         }

         if (n1 + 1 == n2 && n1 + 2 == n3 || n1 - 1 == n2 && n1 - 2 == n3) {
            return true;
         }
      }

      return false;
   }
}
