package com.una.embyhub.foam.controller;

import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Base64;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

public class DoubanDiscoverMain {
   private static final String BASE = "https://frodo.douban.com/api/v2";
   private static final String API_KEY = "0dad551ec0f84ed02907ff5c42e8ec70";
   private static final String API_SECRET = "bf7dddc7c9cfe6f7";
   private static final HttpClient HTTP = HttpClient.newHttpClient();

   public static void main(String[] args) throws Exception {
      String moviesJson = discoverDoubanMovies(1, 30, "U", "");
      System.out.println("=== Douban Movies Discover ===");
      System.out.println(moviesJson);
      String tvsJson = discoverDoubanTvs(1, 30, "U", "");
      System.out.println("=== Douban TVs Discover ===");
      System.out.println(tvsJson);
   }

   public static String discoverDoubanMovies(int page, int count, String sort, String tags) throws Exception {
      return frodoRecommend("/movie/recommend", page, count, sort, tags);
   }

   public static String discoverDoubanTvs(int page, int count, String sort, String tags) throws Exception {
      return frodoRecommend("/tv/recommend", page, count, sort, tags);
   }

   private static String frodoRecommend(String path, int page, int count, String sort, String tags) throws Exception {
      if (page < 1) {
         page = 1;
      }

      if (count < 1) {
         count = 30;
      }

      if (count > 100) {
         count = 100;
      }

      if (sort == null || sort.isBlank()) {
         sort = "R";
      }

      if (tags == null) {
         tags = "";
      }

      int start = (page - 1) * count;
      String url = "https://frodo.douban.com/api/v2" + path;
      String ts = todayTs();
      Map<String, String> params = new LinkedHashMap<>();
      params.put("start", String.valueOf(start));
      params.put("count", String.valueOf(count));
      params.put("sort", sort);
      params.put("tags", tags);
      params.put("os_rom", "android");
      params.put("apiKey", "0dad551ec0f84ed02907ff5c42e8ec70");
      params.put("_ts", ts);
      params.put("_sig", sign("GET", url, ts));
      URI uri = URI.create(url + "?" + toQuery(params));
      HttpRequest req = HttpRequest.newBuilder(uri)
         .header("Accept", "application/json")
         .header("User-Agent", "api-client/1 com.douban.frodo/7.18.0(230) Android/22")
         .GET()
         .build();
      HttpResponse<String> resp = HTTP.send(req, BodyHandlers.ofString(StandardCharsets.UTF_8));
      if (resp.statusCode() / 100 != 2) {
         throw new RuntimeException("HTTP " + resp.statusCode() + " body=" + resp.body());
      } else {
         return resp.body();
      }
   }

   private static String sign(String method, String fullUrl, String ts) {
      try {
         String path = URI.create(fullUrl).getPath();
         String encodedPath = URLEncoder.encode(path, StandardCharsets.UTF_8).replace("+", "%20");
         String raw = method.toUpperCase() + "&" + encodedPath + "&" + ts;
         Mac mac = Mac.getInstance("HmacSHA1");
         mac.init(new SecretKeySpec("bf7dddc7c9cfe6f7".getBytes(StandardCharsets.UTF_8), "HmacSHA1"));
         byte[] digest = mac.doFinal(raw.getBytes(StandardCharsets.UTF_8));
         return Base64.getEncoder().encodeToString(digest);
      } catch (Exception var8) {
         throw new RuntimeException("sign failed", var8);
      }
   }

   private static String todayTs() {
      return LocalDate.now().format(DateTimeFormatter.BASIC_ISO_DATE);
   }

   private static String toQuery(Map<String, String> params) {
      return params.entrySet().stream().map(e -> enc(e.getKey()) + "=" + enc(e.getValue())).collect(Collectors.joining("&"));
   }

   private static String enc(String s) {
      return URLEncoder.encode(s == null ? "" : s, StandardCharsets.UTF_8);
   }
}
