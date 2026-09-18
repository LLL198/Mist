package com.una.embyhub.model.dto.response.requestlist;

public record RequestListStatusResponse(boolean isSubmitted, boolean isCurrentUserSubmitted, boolean isCurrentUserRejected, boolean isPendingImport) {
}
