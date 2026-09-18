package com.una.embyhub.event;

public record InvitationRegisterSuccessNotifyEvent(String userName, Long embyInfoId, Integer validityDays, String invitationCode) {
}
