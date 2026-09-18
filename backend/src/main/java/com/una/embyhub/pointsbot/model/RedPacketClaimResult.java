package com.una.embyhub.pointsbot.model;

import com.una.embyhub.model.entity.PointsBotRedPacket;

public record RedPacketClaimResult(RedPacketClaimResult.Status status, PointsBotRedPacket redPacket, int points, int refundedPoints) {
   public static enum Status {
      CLAIMED,
      ALREADY_CLAIMED,
      FINISHED,
      EXPIRED,
      CANCELLED,
      NOT_OPEN,
      WRONG_MESSAGE,
      NOT_FOUND;
   }
}
