package com.una.embyhub.service;

import com.alibaba.fastjson2.JSONObject;
import com.una.embyhub.config.common.utils.EmbyInfoCacheManagerUtils;
import com.una.embyhub.model.entity.EmbyInfo;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

/** Authentication and server binding for the public Emby webhook endpoint. */
@Component
public class EmbyWebhookAuthenticator {

    public static final String REQUEST_ATTRIBUTE = EmbyWebhookAuthenticator.class.getName() + ".verified";
    private static final int MIN_SECRET_LENGTH = 32;

    private final String webhookSecret;
    private final boolean allowUnsigned;
    private final EmbyInfoService embyInfoService;
    private final EmbyInfoCacheManagerUtils embyInfoCacheManager;

    @Autowired
    public EmbyWebhookAuthenticator(
            @Value("${MIST_EMBY_WEBHOOK_SECRET:}") String webhookSecret,
            @Value("${MIST_EMBY_WEBHOOK_ALLOW_UNSIGNED:true}") boolean allowUnsigned,
            EmbyInfoService embyInfoService,
            EmbyInfoCacheManagerUtils embyInfoCacheManager
    ) {
        this.webhookSecret = webhookSecret == null ? "" : webhookSecret.trim();
        this.allowUnsigned = allowUnsigned;
        this.embyInfoService = embyInfoService;
        this.embyInfoCacheManager = embyInfoCacheManager;
    }

    public boolean isAvailable() {
        return isSecretConfigured() || (!StringUtils.hasText(webhookSecret) && allowUnsigned);
    }

    public boolean isSecretConfigured() {
        return webhookSecret.length() >= MIN_SECRET_LENGTH;
    }

    public boolean matches(HttpServletRequest request) {
        if (request == null || !isAvailable()) {
            return false;
        }
        if (!StringUtils.hasText(webhookSecret)) {
            return true;
        }
        if (!isSecretConfigured()) {
            return false;
        }

        String suppliedSecret = request.getHeader("X-Mist-Webhook-Secret");
        if (!StringUtils.hasText(suppliedSecret)) {
            suppliedSecret = queryParameter(request.getQueryString(), "secret");
        }
        if (!StringUtils.hasText(suppliedSecret)) {
            return false;
        }

        return MessageDigest.isEqual(
                webhookSecret.getBytes(StandardCharsets.UTF_8),
                suppliedSecret.getBytes(StandardCharsets.UTF_8)
        );
    }

    public VerifiedEmbyWebhook resolve(JSONObject payload, boolean authenticatedBySecret) {
        if (payload == null || payload.isEmpty()) {
            return null;
        }

        JSONObject server = payload.getJSONObject("Server");
        String serverId = server == null ? null : server.getString("Id");
        if (!StringUtils.hasText(serverId)) {
            return null;
        }

        EmbyInfo embyInfo = embyInfoService.getByServerId(serverId);
        EmbyInfoCacheManagerUtils.EmbyServerConfig serverConfig = embyInfoCacheManager.getConfigByServerId(serverId);
        if (embyInfo == null
                || embyInfo.getId() == null
                || serverConfig == null
                || !embyInfo.getId().equals(serverConfig.id())
                || !StringUtils.hasText(serverConfig.url())
                || !StringUtils.hasText(serverConfig.apiKey())) {
            return null;
        }

        return new VerifiedEmbyWebhook(embyInfo.getId(), serverId, serverConfig, authenticatedBySecret);
    }

    private static String queryParameter(String query, String name) {
        if (!StringUtils.hasText(query)) {
            return null;
        }

        for (String part : query.split("&")) {
            int separator = part.indexOf('=');
            String rawName = separator >= 0 ? part.substring(0, separator) : part;
            if (!name.equals(decode(rawName))) {
                continue;
            }
            return separator >= 0 ? decode(part.substring(separator + 1)) : "";
        }
        return null;
    }

    private static String decode(String value) {
        try {
            return URLDecoder.decode(value, StandardCharsets.UTF_8);
        } catch (IllegalArgumentException e) {
            return "";
        }
    }
}
