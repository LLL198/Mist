package com.una.embyhub.model.dto.response.pointsbot;

import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Generated;

public class PointsBotRedPacketClaimResponse implements Serializable {
   private Long id;
   private Long redPacketId;
   private Long userId;
   private String username;
   private String displayName;
   private Integer points;
   private LocalDateTime claimedAt;

   @Generated
   public Long getId() {
      return this.id;
   }

   @Generated
   public Long getRedPacketId() {
      return this.redPacketId;
   }

   @Generated
   public Long getUserId() {
      return this.userId;
   }

   @Generated
   public String getUsername() {
      return this.username;
   }

   @Generated
   public String getDisplayName() {
      return this.displayName;
   }

   @Generated
   public Integer getPoints() {
      return this.points;
   }

   @Generated
   public LocalDateTime getClaimedAt() {
      return this.claimedAt;
   }

   @Generated
   public void setId(final Long id) {
      this.id = id;
   }

   @Generated
   public void setRedPacketId(final Long redPacketId) {
      this.redPacketId = redPacketId;
   }

   @Generated
   public void setUserId(final Long userId) {
      this.userId = userId;
   }

   @Generated
   public void setUsername(final String username) {
      this.username = username;
   }

   @Generated
   public void setDisplayName(final String displayName) {
      this.displayName = displayName;
   }

   @Generated
   public void setPoints(final Integer points) {
      this.points = points;
   }

   @Generated
   public void setClaimedAt(final LocalDateTime claimedAt) {
      this.claimedAt = claimedAt;
   }

   @Generated
   @Override
   public boolean equals(final Object o) {
      if (o == this) {
         return true;
      } else if (!(o instanceof PointsBotRedPacketClaimResponse other)) {
         return false;
      } else if (!other.canEqual(this)) {
         return false;
      } else {
         Object this$id = this.getId();
         Object other$id = other.getId();
         if (this$id == null ? other$id == null : this$id.equals(other$id)) {
            Object this$redPacketId = this.getRedPacketId();
            Object other$redPacketId = other.getRedPacketId();
            if (this$redPacketId == null ? other$redPacketId == null : this$redPacketId.equals(other$redPacketId)) {
               Object this$userId = this.getUserId();
               Object other$userId = other.getUserId();
               if (this$userId == null ? other$userId == null : this$userId.equals(other$userId)) {
                  Object this$points = this.getPoints();
                  Object other$points = other.getPoints();
                  if (this$points == null ? other$points == null : this$points.equals(other$points)) {
                     Object this$username = this.getUsername();
                     Object other$username = other.getUsername();
                     if (this$username == null ? other$username == null : this$username.equals(other$username)) {
                        Object this$displayName = this.getDisplayName();
                        Object other$displayName = other.getDisplayName();
                        if (this$displayName == null ? other$displayName == null : this$displayName.equals(other$displayName)) {
                           Object this$claimedAt = this.getClaimedAt();
                           Object other$claimedAt = other.getClaimedAt();
                           return this$claimedAt == null ? other$claimedAt == null : this$claimedAt.equals(other$claimedAt);
                        } else {
                           return false;
                        }
                     } else {
                        return false;
                     }
                  } else {
                     return false;
                  }
               } else {
                  return false;
               }
            } else {
               return false;
            }
         } else {
            return false;
         }
      }
   }

   @Generated
   protected boolean canEqual(final Object other) {
      return other instanceof PointsBotRedPacketClaimResponse;
   }

   @Generated
   @Override
   public int hashCode() {
      int PRIME = 59;
      int result = 1;
      Object $id = this.getId();
      result = result * 59 + ($id == null ? 43 : $id.hashCode());
      Object $redPacketId = this.getRedPacketId();
      result = result * 59 + ($redPacketId == null ? 43 : $redPacketId.hashCode());
      Object $userId = this.getUserId();
      result = result * 59 + ($userId == null ? 43 : $userId.hashCode());
      Object $points = this.getPoints();
      result = result * 59 + ($points == null ? 43 : $points.hashCode());
      Object $username = this.getUsername();
      result = result * 59 + ($username == null ? 43 : $username.hashCode());
      Object $displayName = this.getDisplayName();
      result = result * 59 + ($displayName == null ? 43 : $displayName.hashCode());
      Object $claimedAt = this.getClaimedAt();
      return result * 59 + ($claimedAt == null ? 43 : $claimedAt.hashCode());
   }

   @Generated
   @Override
   public String toString() {
      return "PointsBotRedPacketClaimResponse(id="
         + this.getId()
         + ", redPacketId="
         + this.getRedPacketId()
         + ", userId="
         + this.getUserId()
         + ", username="
         + this.getUsername()
         + ", displayName="
         + this.getDisplayName()
         + ", points="
         + this.getPoints()
         + ", claimedAt="
         + this.getClaimedAt()
         + ")";
   }
}
