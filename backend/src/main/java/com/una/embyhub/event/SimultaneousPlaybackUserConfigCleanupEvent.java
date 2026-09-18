package com.una.embyhub.event;

public record SimultaneousPlaybackUserConfigCleanupEvent(Long embyInfoId, String embyUserId, String embyUserName) {
}
