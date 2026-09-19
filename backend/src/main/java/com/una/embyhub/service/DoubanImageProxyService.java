package com.una.embyhub.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.Duration;
import java.util.HexFormat;
import java.util.Locale;
import java.util.Set;

/**
 * Shared, bounded proxy for Douban image URLs.
 *
 * <p>The proxy deliberately does not forward any Douban Cookie.  It also
 * validates every redirect target instead of allowing the upstream to choose
 * an arbitrary final host.</p>
 */
@Service
public class DoubanImageProxyService {

    private static final int MAX_URL_LENGTH = 2048;
    private static final int MAX_IMAGE_BYTES = 10 * 1024 * 1024;
    private static final int MAX_REDIRECTS = 3;
    private static final Duration CONNECT_TIMEOUT = Duration.ofSeconds(10);
    private static final Duration REQUEST_TIMEOUT = Duration.ofSeconds(15);
    private static final Duration CACHE_TTL = Duration.ofHours(6);
    private static final String CACHE_KEY_PREFIX = "douban:image:v2:";

    private static final Set<String> ALLOWED_HOSTS = Set.of(
            "img1.doubanio.com",
            "img2.doubanio.com",
            "img3.doubanio.com",
            "img9.doubanio.com"
    );

    private static final Set<String> ALLOWED_CONTENT_TYPES = Set.of(
            "image/jpeg",
            "image/png",
            "image/gif",
            "image/webp",
            "image/avif"
    );

    private final HttpClient httpClient = HttpClient.newBuilder()
            .connectTimeout(CONNECT_TIMEOUT)
            .followRedirects(HttpClient.Redirect.NEVER)
            .build();

    @Autowired
    private RedisTemplate<String, byte[]> binaryRedisTemplate;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    public ResponseEntity<byte[]> proxy(String rawUrl) {
        URI requestedUri = parseSafeDoubanImageUri(rawUrl);
        if (requestedUri == null) {
            return textResponse(HttpStatus.BAD_REQUEST, "不支持的图片地址");
        }

        String cacheKey = CACHE_KEY_PREFIX + sha256(requestedUri.toASCIIString());
        byte[] cached = binaryRedisTemplate.opsForValue().get(cacheKey);
        String cachedType = cachedContentType(cacheKey);
        if (cached != null && cached.length <= MAX_IMAGE_BYTES && matchesImageSignature(cached, cachedType)) {
            return imageResponse(cached, cachedType);
        }
        if (cached != null) {
            binaryRedisTemplate.delete(cacheKey);
            stringRedisTemplate.delete(cacheKey + ":content-type");
        }

        try {
            URI currentUri = requestedUri;
            for (int redirectCount = 0; redirectCount <= MAX_REDIRECTS; redirectCount++) {
                HttpRequest request = HttpRequest.newBuilder(currentUri)
                        .timeout(REQUEST_TIMEOUT)
                        .header("Accept", "image/avif,image/webp,image/apng,image/*;q=0.8")
                        .header("User-Agent", "Mist-DoubanImageProxy/1.0")
                        .GET()
                        .build();

                HttpResponse<InputStream> response = httpClient.send(
                        request,
                        HttpResponse.BodyHandlers.ofInputStream()
                );

                try (InputStream body = response.body()) {
                    int status = response.statusCode();
                    if (isRedirect(status)) {
                        if (redirectCount == MAX_REDIRECTS) {
                            return textResponse(HttpStatus.BAD_GATEWAY, "图片跳转次数过多");
                        }
                        String location = response.headers().firstValue("Location").orElse(null);
                        if (!StringUtils.hasText(location)) {
                            return textResponse(HttpStatus.BAD_GATEWAY, "图片地址无效");
                        }
                        URI nextUri = currentUri.resolve(location);
                        if (!isSafeDoubanImageUri(nextUri)) {
                            return textResponse(HttpStatus.BAD_GATEWAY, "图片跳转地址不受支持");
                        }
                        currentUri = nextUri;
                        continue;
                    }

                    if (status < 200 || status >= 300) {
                        return textResponse(HttpStatus.BAD_GATEWAY, "图片源站请求失败");
                    }

                    String contentType = safeImageContentType(
                            response.headers().firstValue("Content-Type").orElse(null)
                    );
                    if (contentType == null) {
                        return textResponse(HttpStatus.BAD_GATEWAY, "图片类型不受支持");
                    }

                    long contentLength = response.headers().firstValueAsLong("Content-Length").orElse(-1L);
                    if (contentLength > MAX_IMAGE_BYTES) {
                        return textResponse(HttpStatus.PAYLOAD_TOO_LARGE, "图片文件过大");
                    }

                    byte[] bytes = readLimited(body);
                    if (!matchesImageSignature(bytes, contentType)) {
                        return textResponse(HttpStatus.BAD_GATEWAY, "图片内容无效");
                    }

                    binaryRedisTemplate.opsForValue().set(cacheKey, bytes, CACHE_TTL);
                    stringRedisTemplate.opsForValue().set(cacheKey + ":content-type", contentType, CACHE_TTL);
                    return imageResponse(bytes, contentType);
                }
            }
        } catch (ImageTooLargeException e) {
            return textResponse(HttpStatus.PAYLOAD_TOO_LARGE, "图片文件过大");
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return textResponse(HttpStatus.BAD_GATEWAY, "图片代理请求中断");
        } catch (IOException | RuntimeException e) {
            return textResponse(HttpStatus.BAD_GATEWAY, "图片代理失败");
        }

        return textResponse(HttpStatus.BAD_GATEWAY, "图片代理失败");
    }

    static URI parseSafeDoubanImageUri(String rawUrl) {
        if (!StringUtils.hasText(rawUrl) || rawUrl.length() > MAX_URL_LENGTH) {
            return null;
        }
        try {
            URI uri = URI.create(rawUrl.trim());
            return isSafeDoubanImageUri(uri) ? uri : null;
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

    static boolean isSafeDoubanImageUri(URI uri) {
        if (uri == null
                || !"https".equalsIgnoreCase(uri.getScheme())
                || uri.getUserInfo() != null
                || uri.getHost() == null
                || uri.getPort() != -1) {
            return false;
        }
        return ALLOWED_HOSTS.contains(uri.getHost().toLowerCase(Locale.ROOT));
    }

    private String cachedContentType(String cacheKey) {
        String contentType = stringRedisTemplate.opsForValue().get(cacheKey + ":content-type");
        return safeImageContentType(contentType);
    }

    private byte[] readLimited(InputStream body) throws IOException, ImageTooLargeException {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        byte[] buffer = new byte[8192];
        int total = 0;
        int read;
        while ((read = body.read(buffer)) != -1) {
            total += read;
            if (total > MAX_IMAGE_BYTES) {
                throw new ImageTooLargeException();
            }
            output.write(buffer, 0, read);
        }
        return output.toByteArray();
    }

    private static boolean isRedirect(int status) {
        return status == 301 || status == 302 || status == 303 || status == 307 || status == 308;
    }

    private static String safeImageContentType(String contentType) {
        if (!StringUtils.hasText(contentType)) {
            return null;
        }
        String normalized = contentType.split(";", 2)[0].trim().toLowerCase(Locale.ROOT);
        return ALLOWED_CONTENT_TYPES.contains(normalized) ? normalized : null;
    }

    private static boolean matchesImageSignature(byte[] bytes, String contentType) {
        if (bytes == null || bytes.length < 4 || !StringUtils.hasText(contentType)) {
            return false;
        }
        return switch (contentType) {
            case "image/jpeg" -> (bytes[0] & 0xff) == 0xff
                    && (bytes[1] & 0xff) == 0xd8
                    && (bytes[2] & 0xff) == 0xff;
            case "image/png" -> bytes.length >= 8
                    && (bytes[0] & 0xff) == 0x89
                    && bytes[1] == 0x50
                    && bytes[2] == 0x4e
                    && bytes[3] == 0x47
                    && bytes[4] == 0x0d
                    && bytes[5] == 0x0a
                    && bytes[6] == 0x1a
                    && bytes[7] == 0x0a;
            case "image/gif" -> bytes.length >= 6
                    && (bytes[0] == 'G' && bytes[1] == 'I' && bytes[2] == 'F')
                    && (bytes[3] == '8' && (bytes[4] == '7' || bytes[4] == '9') && bytes[5] == 'a');
            case "image/webp" -> bytes.length >= 12
                    && bytes[0] == 'R' && bytes[1] == 'I' && bytes[2] == 'F' && bytes[3] == 'F'
                    && bytes[8] == 'W' && bytes[9] == 'E' && bytes[10] == 'B' && bytes[11] == 'P';
            case "image/avif" -> isAvif(bytes);
            default -> false;
        };
    }

    private static boolean isAvif(byte[] bytes) {
        if (bytes.length < 12
                || bytes[4] != 'f' || bytes[5] != 't' || bytes[6] != 'y' || bytes[7] != 'p') {
            return false;
        }
        String brand = new String(bytes, 8, 4, StandardCharsets.US_ASCII).toLowerCase(Locale.ROOT);
        return brand.equals("avif") || brand.equals("avis") || brand.equals("mif1") || brand.equals("msf1");
    }

    private static ResponseEntity<byte[]> imageResponse(byte[] bytes, String contentType) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType(
                StringUtils.hasText(contentType) ? contentType : "application/octet-stream"
        ));
        headers.setContentLength(bytes.length);
        headers.setCacheControl(CacheControl.maxAge(CACHE_TTL).cachePublic());
        headers.set("X-Content-Type-Options", "nosniff");
        headers.set("Content-Security-Policy", "default-src 'none'; sandbox");
        headers.set("Cross-Origin-Resource-Policy", "same-origin");
        return new ResponseEntity<>(bytes, headers, HttpStatus.OK);
    }

    private static ResponseEntity<byte[]> textResponse(HttpStatus status, String message) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.TEXT_PLAIN);
        headers.set("X-Content-Type-Options", "nosniff");
        return new ResponseEntity<>(message.getBytes(StandardCharsets.UTF_8), headers, status);
    }

    private static String sha256(String value) {
        try {
            byte[] digest = MessageDigest.getInstance("SHA-256")
                    .digest(value.getBytes(StandardCharsets.UTF_8));
            return HexFormat.of().formatHex(digest);
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("SHA-256 is unavailable", e);
        }
    }

    private static final class ImageTooLargeException extends Exception {
    }
}
