package com.una.embyhub.model.dto.response.pointsbot;

import java.io.Serializable;
import java.time.LocalDateTime;

public record PointsBotRedPacketCancelResponse(
   Long id,
   String status,
   Integer remainingPoints,
   Integer remainingCount,
   Integer claimedCount,
   Integer refundedPoints,
   LocalDateTime refundedAt,
   LocalDateTime finishedAt
) implements Serializable {
}
