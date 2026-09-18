package com.una.embyhub.config.common.utils;

import com.alibaba.fastjson2.JSONObject;
import net.dreamlu.mica.ip2region.core.Ip2regionSearcher;
import org.springframework.util.StringUtils;

public class EmbyEventLogParserUtils {
   public static String success(String json, Ip2regionSearcher searchSearcher) {
      JSONObject root = JSONObject.parseObject(json);
      String title = root.getString("Title");
      String description = root.getString("Description").split("\n")[0];
      String serverName = root.getJSONObject("Server").getString("Name");
      String userName = root.getJSONObject("User").getString("Name");
      String deviceName = root.getJSONObject("Session").getString("DeviceName");
      String clientApp = root.getJSONObject("Session").getString("Client");
      String remoteIp = root.getJSONObject("Session").getString("RemoteEndPoint");
      String ipAddress = "";
      ipAddress = IpAddressUtils.safeAddressAndIsp(searchSearcher, remoteIp);
      return String.format(
         "* ✅ %s%n%n\ud83d\udc64 用户名: %s%n%n\ud83d\udda5️ 服务器: %s%n%n\ud83d\udcf1 设备: %s%n%n\ud83d\udcf2 客户端: %s%n%n\ud83c\udf10 远程IP: %s %s%n%n\ud83d\udcdd 详情: %s *",
         title,
         userName,
         serverName,
         deviceName,
         clientApp,
         remoteIp,
         ipAddress,
         description
      );
   }

   public static String failed(String json, Ip2regionSearcher searchSearcher) {
      JSONObject root = JSONObject.parseObject(json);
      String serverName = root.getJSONObject("Server").getString("Name");
      String deviceName = root.getJSONObject("DeviceInfo").getString("Name");
      String appName = root.getJSONObject("DeviceInfo").getString("AppName");
      String title = root.getString("Title");
      String description = root.getString("Description");
      String data = description.split("\n\n")[0];
      String ip = description.split("\n\n")[1];
      String ipAddress = "";
      if (StringUtils.hasText(ip)) {
         ipAddress = IpAddressUtils.safeAddressAndIsp(searchSearcher, ip);
      }

      return String.format(
         "* \ud83d\udd12 %s%n%n\ud83d\udda5️ 服务器: %s%n%n\ud83d\udcf1 设备: %s%n%n\ud83d\udcf2 应用: %s%n%n\ud83d\udcdd 详情: %s%n%n\ud83c\udf10 远程IP: %s %s *",
         title,
         serverName,
         deviceName,
         appName,
         data,
         ip,
         ipAddress
      );
   }
}
