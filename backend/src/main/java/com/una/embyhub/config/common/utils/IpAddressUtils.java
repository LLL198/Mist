package com.una.embyhub.config.common.utils;

import java.net.Inet4Address;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.util.Optional;
import net.dreamlu.mica.ip2region.core.Ip2regionSearcher;
import net.dreamlu.mica.ip2region.core.IpInfo;
import org.springframework.util.StringUtils;

public final class IpAddressUtils {
   private IpAddressUtils() {
   }

   public static Optional<IpAddressUtils.ParsedIp> parseLiteral(String endpoint) {
      String candidate = extractLiteral(endpoint);
      if (!StringUtils.hasText(candidate)) {
         return Optional.empty();
      } else {
         try {
            InetAddress address;
            if (candidate.contains(":")) {
               address = InetAddress.getByName(candidate);
            } else {
               if (!isIpv4Literal(candidate)) {
                  return Optional.empty();
               }

               address = InetAddress.getByName(candidate);
            }

            IpAddressUtils.AddressFamily family;
            if (address instanceof Inet4Address) {
               family = IpAddressUtils.AddressFamily.IPV4;
            } else {
               if (!(address instanceof Inet6Address)) {
                  return Optional.empty();
               }

               family = IpAddressUtils.AddressFamily.IPV6;
            }

            return Optional.of(new IpAddressUtils.ParsedIp(address.getHostAddress(), family, isPrivateOrLocal(address)));
         } catch (Exception var4) {
            return Optional.empty();
         }
      }
   }

   public static Optional<IpInfo> safeLookup(Ip2regionSearcher searcher, String endpoint) {
      if (searcher == null) {
         return Optional.empty();
      } else {
         Optional<IpAddressUtils.ParsedIp> parsed = parseLiteral(endpoint);
         if (!parsed.isEmpty() && !parsed.get().privateOrLocal()) {
            try {
               return Optional.ofNullable(searcher.memorySearch(parsed.get().address()));
            } catch (Exception var4) {
               return Optional.empty();
            }
         } else {
            return Optional.empty();
         }
      }
   }

   public static String safeAddressAndIsp(Ip2regionSearcher searcher, String endpoint) {
      return safeLookup(searcher, endpoint).map(IpInfo::getAddressAndIsp).filter(StringUtils::hasText).orElse("");
   }

   public static String normalizeHostForUrl(String host) {
      if (!StringUtils.hasText(host)) {
         return host;
      } else {
         String value = host.trim();
         if (value.startsWith("[") && value.endsWith("]")) {
            return value;
         } else {
            Optional<IpAddressUtils.ParsedIp> parsed = parseLiteral(value);
            return parsed.isPresent() && parsed.get().family() == IpAddressUtils.AddressFamily.IPV6 ? "[" + value + "]" : value;
         }
      }
   }

   private static String extractLiteral(String endpoint) {
      if (!StringUtils.hasText(endpoint)) {
         return null;
      } else {
         String value = endpoint.trim();
         if (value.startsWith("[")) {
            int close = value.indexOf(93);
            if (close <= 1) {
               return null;
            } else {
               String suffix = value.substring(close + 1);
               return !StringUtils.hasText(suffix) || suffix.startsWith(":") && isValidPort(suffix.substring(1)) ? value.substring(1, close) : null;
            }
         } else {
            int firstColon = value.indexOf(58);
            if (firstColon > 0 && firstColon == value.lastIndexOf(58) && value.contains(".")) {
               String port = value.substring(firstColon + 1);
               return isValidPort(port) ? value.substring(0, firstColon) : null;
            } else {
               return value;
            }
         }
      }
   }

   private static boolean isValidPort(String value) {
      if (!StringUtils.hasText(value)) {
         return false;
      } else {
         for (int index = 0; index < value.length(); index++) {
            if (!Character.isDigit(value.charAt(index))) {
               return false;
            }
         }

         try {
            int port = Integer.parseInt(value);
            return port >= 0 && port <= 65535;
         } catch (NumberFormatException var2) {
            return false;
         }
      }
   }

   private static boolean isIpv4Literal(String value) {
      String[] parts = value.split("\\.", -1);
      if (parts.length != 4) {
         return false;
      } else {
         String[] var2 = parts;
         int var3 = parts.length;
         int var4 = 0;

         while (var4 < var3) {
            String part = var2[var4];
            if (!part.isEmpty() && part.length() <= 3) {
               for (int index = 0; index < part.length(); index++) {
                  if (!Character.isDigit(part.charAt(index))) {
                     return false;
                  }
               }

               int octet = Integer.parseInt(part);
               if (octet >= 0 && octet <= 255) {
                  var4++;
                  continue;
               }

               return false;
            }

            return false;
         }

         return true;
      }
   }

   private static boolean isPrivateOrLocal(InetAddress address) {
      if (!address.isAnyLocalAddress()
         && !address.isLoopbackAddress()
         && !address.isLinkLocalAddress()
         && !address.isSiteLocalAddress()
         && !address.isMulticastAddress()) {
         byte[] bytes = address.getAddress();
         if (address instanceof Inet4Address && bytes.length == 4) {
            int first = Byte.toUnsignedInt(bytes[0]);
            int second = Byte.toUnsignedInt(bytes[1]);
            int third = Byte.toUnsignedInt(bytes[2]);
            return first == 0
               || first >= 224
               || first == 100 && second >= 64 && second <= 127
               || first == 192 && second == 0
               || first == 198 && (second == 18 || second == 19)
               || first == 198 && second == 51 && third == 100
               || first == 203 && second == 0 && third == 113;
         } else if (address instanceof Inet6Address && bytes.length == 16) {
            boolean uniqueLocal = (bytes[0] & 254) == 252;
            boolean documentation = bytes[0] == 32 && bytes[1] == 1 && bytes[2] == 13 && (bytes[3] & 255) == 184;
            return uniqueLocal || documentation;
         } else {
            return true;
         }
      } else {
         return true;
      }
   }

   public static enum AddressFamily {
      IPV4,
      IPV6;
   }

   public static record ParsedIp(String address, IpAddressUtils.AddressFamily family, boolean privateOrLocal) {
   }
}
