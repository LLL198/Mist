package com.una.embyhub.service;

import com.una.embyhub.config.common.utils.EmbyInfoCacheManagerUtils;

/**
 * Server identity established by the webhook validation layer.
 * Payload fields are never used as proof of server configuration; the flag
 * records whether the deployment secret was also present and valid.
 */
public record VerifiedEmbyWebhook(
        Long embyInfoId,
        String serverId,
        EmbyInfoCacheManagerUtils.EmbyServerConfig serverConfig,
        boolean authenticatedBySecret
) {
}
