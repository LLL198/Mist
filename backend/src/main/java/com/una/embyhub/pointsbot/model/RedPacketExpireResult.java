package com.una.embyhub.pointsbot.model;

import com.una.embyhub.model.entity.PointsBotRedPacket;

public record RedPacketExpireResult(boolean changed, PointsBotRedPacket redPacket, int refundedPoints) {
}
