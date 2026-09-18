package com.una.embyhub.service;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import net.dreamlu.mica.ip2region.core.Header;
import net.dreamlu.mica.ip2region.core.IpInfo;
import net.dreamlu.mica.ip2region.core.Searcher;
import net.dreamlu.mica.ip2region.utils.IpInfoUtil;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class EmbyRegionCatalogLoader {
   private static final String XDB_RESOURCE = "ip2region/ip2region.xdb";
   private final EmbyRegionCatalogLoader.InputStreamSource inputStreamSource;
   private volatile List<EmbyRegionCatalogLoader.CatalogRule> cachedRules;

   public EmbyRegionCatalogLoader() {
      this(() -> new ClassPathResource("ip2region/ip2region.xdb").getInputStream());
   }

   EmbyRegionCatalogLoader(EmbyRegionCatalogLoader.InputStreamSource inputStreamSource) {
      this.inputStreamSource = inputStreamSource;
   }

   public List<EmbyRegionCatalogLoader.CatalogRule> rules() {
      List<EmbyRegionCatalogLoader.CatalogRule> snapshot = this.cachedRules;
      if (snapshot != null) {
         return snapshot;
      } else {
         synchronized (this) {
            if (this.cachedRules == null) {
               this.cachedRules = this.loadRules();
            }

            return this.cachedRules;
         }
      }
   }

   private List<EmbyRegionCatalogLoader.CatalogRule> loadRules() {
      try {
         List var13;
         try (InputStream inputStream = this.inputStreamSource.open()) {
            byte[] content = inputStream.readAllBytes();
            if (content.length < 256) {
               throw new IllegalStateException("ip2region xdb 文件头不完整");
            }

            Header header = new Header(content);
            this.validateIndexBounds(content, header);
            Map<String, EmbyRegionCatalogLoader.CatalogRule> deduplicated = new LinkedHashMap<>();

            for (int offset = header.startIndexPtr; offset <= header.endIndexPtr && offset + 14 <= content.length; offset += 14) {
               int dataLength = Searcher.getInt2(content, offset + 8);
               int dataPtr = Searcher.getInt(content, offset + 10);
               if (dataLength > 0 && dataPtr >= 0 && dataPtr + dataLength <= content.length) {
                  String regionText = new String(content, dataPtr, dataLength, StandardCharsets.UTF_8);
                  this.addLocationRules(deduplicated, IpInfoUtil.toIpInfo(regionText));
               }
            }

            List<EmbyRegionCatalogLoader.CatalogRule> result = new ArrayList<>(deduplicated.values());
            result.sort(
               Comparator.<EmbyRegionCatalogLoader.CatalogRule>comparingInt(rule -> "COUNTRY".equals(rule.ruleType()) ? 0 : 1)
                  .thenComparing(EmbyRegionCatalogLoader.CatalogRule::country)
                  .thenComparing(rule -> rule.province() == null ? "" : rule.province())
            );
            var13 = List.copyOf(result);
         }

         return var13;
      } catch (IOException var11) {
         throw new IllegalStateException("读取内置 ip2region xdb 失败", var11);
      }
   }

   private void validateIndexBounds(byte[] content, Header header) {
      if (header.startIndexPtr < 256 || header.endIndexPtr < header.startIndexPtr || header.endIndexPtr + 14 > content.length) {
         throw new IllegalStateException("ip2region xdb 索引范围无效");
      }
   }

   private void addLocationRules(Map<String, EmbyRegionCatalogLoader.CatalogRule> rules, IpInfo ipInfo) {
      if (ipInfo != null) {
         String country = normalize(ipInfo.getCountry());
         if (country != null) {
            String countryCode = countryRuleCode(country);
            rules.putIfAbsent(countryCode, new EmbyRegionCatalogLoader.CatalogRule(countryCode, "COUNTRY", country, null, country));
            String province = normalize(ipInfo.getProvince());
            if (province != null) {
               String provinceCode = provinceRuleCode(country, province);
               rules.putIfAbsent(provinceCode, new EmbyRegionCatalogLoader.CatalogRule(provinceCode, "PROVINCE", country, province, country + " / " + province));
            }
         }
      }
   }

   public static String countryRuleCode(String country) {
      return "COUNTRY:" + country;
   }

   public static String provinceRuleCode(String country, String province) {
      return "PROVINCE:" + country + ":" + province;
   }

   public static String normalize(String value) {
      if (!StringUtils.hasText(value)) {
         return null;
      } else {
         String normalized = value.trim();
         return "0".equals(normalized) ? null : normalized;
      }
   }

   public static record CatalogRule(String ruleCode, String ruleType, String country, String province, String displayName) {
   }

   @FunctionalInterface
   interface InputStreamSource {
      InputStream open() throws IOException;
   }
}
