package com.una.embyhub.event;

public record CardRegisterSuccessNotifyEvent(String userName, Long embyInfoId, Integer validityDays, String cardPassword) {
}
