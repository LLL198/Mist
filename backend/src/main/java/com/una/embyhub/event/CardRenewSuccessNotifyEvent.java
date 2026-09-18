package com.una.embyhub.event;

import java.util.Date;

public record CardRenewSuccessNotifyEvent(String userName, Long embyInfoId, Integer validityDays, String cardPassword, Date expirationDate) {
}
