package com.una.embyhub.service.impl;

import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpRequest;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.una.embyhub.FoamApiApplication;
import com.una.embyhub.model.dto.response.docker.DockerImageVersionResponse;
import com.una.embyhub.model.dto.response.docker.DockerImagesVersionResponse;
import com.una.embyhub.service.DockerImageService;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import lombok.Generated;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

@Service
public class DockerImageServiceImpl implements DockerImageService {
   @Generated
   private static final Logger log = LoggerFactory.getLogger(DockerImageServiceImpl.class);
   private static final String BACKEND_IMAGE = "ciwei123321/foam-api-v2";
   private static final String FRONTEND_IMAGE = "ciwei123321/foam-web";
   private static final String UNKNOWN_VERSION = "未知";
   @Value("${foam.current-backend-version:}")
   private String configuredBackendVersion;
   @Value("${foam.current-frontend-version:}")
   private String configuredFrontendVersion;

   @Override
   public DockerImagesVersionResponse getDockerImagesVersion() {
      String backendCurrentVersion = this.resolveBackendVersion();
      String frontendCurrentVersion = this.resolveFrontendVersion();
      DockerImageVersionResponse backend = this.buildResponse("ciwei123321/foam-api-v2", backendCurrentVersion);
      DockerImageVersionResponse frontend = this.buildResponse("ciwei123321/foam-web", frontendCurrentVersion);
      return new DockerImagesVersionResponse(backend, frontend);
   }

   private DockerImageVersionResponse buildResponse(String image, String currentVersion) {
      DockerImageVersionResponse response = new DockerImageVersionResponse();
      response.setImage(image);
      response.setCurrentVersion(StrUtil.blankToDefault(currentVersion, "未知"));
      response.setLatestVersion(this.fetchLatestTag(image));
      return response;
   }

   private String resolveBackendVersion() {
      if (StrUtil.isNotBlank(this.configuredBackendVersion)) {
         return this.configuredBackendVersion;
      } else {
         String implementationVersion = FoamApiApplication.class.getPackage().getImplementationVersion();
         if (StrUtil.isNotBlank(implementationVersion)) {
            return implementationVersion;
         } else {
            String pomVersion = this.readPomPropertiesVersion();
            return StrUtil.isNotBlank(pomVersion) ? pomVersion : "未知";
         }
      }
   }

   private String resolveFrontendVersion() {
      return StrUtil.isNotBlank(this.configuredFrontendVersion) ? this.configuredFrontendVersion : "未知";
   }

   private String readPomPropertiesVersion() {
      ClassPathResource resource = new ClassPathResource("META-INF/maven/com.una/foam-api-v2/pom.properties");
      if (!resource.exists()) {
         return "";
      } else {
         try {
            String var4;
            try (InputStream inputStream = resource.getInputStream()) {
               Properties properties = new Properties();
               properties.load(inputStream);
               var4 = properties.getProperty("version", "");
            }

            return var4;
         } catch (IOException var7) {
            log.warn("读取 pom.properties 失败: {}", var7.getMessage());
            return "";
         }
      }
   }

   private String fetchLatestTag(String image) {
      String url = String.format("https://hub.docker.com/v2/repositories/%s/tags?page_size=20&page=1&ordering=last_updated", image);

      try {
         String body = HttpRequest.get(url)
            .header("Accept", "application/json")
            .header("User-Agent", "foam-api-v2/version-check")
            .timeout(5000)
            .execute()
            .body();
         JSONObject jsonObject = JSONObject.parseObject(body);
         JSONArray results = jsonObject.getJSONArray("results");
         if (results != null && !results.isEmpty()) {
            for (int i = 0; i < results.size(); i++) {
               JSONObject tag = results.getJSONObject(i);
               String tagName = tag.getString("name");
               if (!StrUtil.isBlank(tagName) && !StrUtil.equalsIgnoreCase(tagName, "latest")) {
                  return tagName;
               }
            }

            JSONObject fallback = results.getJSONObject(0);
            String fallbackName = fallback.getString("name");
            if (StrUtil.isNotBlank(fallbackName)) {
               return fallbackName;
            }
         }

         log.warn("{} 没有返回可用的 tags 结果", image);
      } catch (Exception var9) {
         log.warn("获取 {} 最新版本失败: {}", image, var9.getMessage());
      }

      return "未知";
   }
}
