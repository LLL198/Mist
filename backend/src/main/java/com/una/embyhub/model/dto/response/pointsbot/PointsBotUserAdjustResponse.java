package com.una.embyhub.model.dto.response.pointsbot;

import java.io.Serializable;
import lombok.Generated;

public class PointsBotUserAdjustResponse implements Serializable {
   private Long id;
   private Long userId;
   private long beforePoints;
   private int delta;
   private long afterPoints;
   private String levelName;

   @Generated
   public Long getId() {
      return this.id;
   }

   @Generated
   public Long getUserId() {
      return this.userId;
   }

   @Generated
   public long getBeforePoints() {
      return this.beforePoints;
   }

   @Generated
   public int getDelta() {
      return this.delta;
   }

   @Generated
   public long getAfterPoints() {
      return this.afterPoints;
   }

   @Generated
   public String getLevelName() {
      return this.levelName;
   }

   @Generated
   public void setId(final Long id) {
      this.id = id;
   }

   @Generated
   public void setUserId(final Long userId) {
      this.userId = userId;
   }

   @Generated
   public void setBeforePoints(final long beforePoints) {
      this.beforePoints = beforePoints;
   }

   @Generated
   public void setDelta(final int delta) {
      this.delta = delta;
   }

   @Generated
   public void setAfterPoints(final long afterPoints) {
      this.afterPoints = afterPoints;
   }

   @Generated
   public void setLevelName(final String levelName) {
      this.levelName = levelName;
   }

   @Generated
   @Override
   public boolean equals(final Object o) {
      if (o == this) {
         return true;
      } else if (!(o instanceof PointsBotUserAdjustResponse other)) {
         return false;
      } else if (!other.canEqual(this)) {
         return false;
      } else if (this.getBeforePoints() != other.getBeforePoints()) {
         return false;
      } else if (this.getDelta() != other.getDelta()) {
         return false;
      } else if (this.getAfterPoints() != other.getAfterPoints()) {
         return false;
      } else {
         Object this$id = this.getId();
         Object other$id = other.getId();
         if (this$id == null ? other$id == null : this$id.equals(other$id)) {
            Object this$userId = this.getUserId();
            Object other$userId = other.getUserId();
            if (this$userId == null ? other$userId == null : this$userId.equals(other$userId)) {
               Object this$levelName = this.getLevelName();
               Object other$levelName = other.getLevelName();
               return this$levelName == null ? other$levelName == null : this$levelName.equals(other$levelName);
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
      return other instanceof PointsBotUserAdjustResponse;
   }

   @Generated
   @Override
   public int hashCode() {
      int PRIME = 59;
      int result = 1;
      long $beforePoints = this.getBeforePoints();
      result = result * 59 + (int)($beforePoints >>> 32 ^ $beforePoints);
      result = result * 59 + this.getDelta();
      long $afterPoints = this.getAfterPoints();
      result = result * 59 + (int)($afterPoints >>> 32 ^ $afterPoints);
      Object $id = this.getId();
      result = result * 59 + ($id == null ? 43 : $id.hashCode());
      Object $userId = this.getUserId();
      result = result * 59 + ($userId == null ? 43 : $userId.hashCode());
      Object $levelName = this.getLevelName();
      return result * 59 + ($levelName == null ? 43 : $levelName.hashCode());
   }

   @Generated
   @Override
   public String toString() {
      return "PointsBotUserAdjustResponse(id="
         + this.getId()
         + ", userId="
         + this.getUserId()
         + ", beforePoints="
         + this.getBeforePoints()
         + ", delta="
         + this.getDelta()
         + ", afterPoints="
         + this.getAfterPoints()
         + ", levelName="
         + this.getLevelName()
         + ")";
   }

   @Generated
   public PointsBotUserAdjustResponse(
      final Long id, final Long userId, final long beforePoints, final int delta, final long afterPoints, final String levelName
   ) {
      this.id = id;
      this.userId = userId;
      this.beforePoints = beforePoints;
      this.delta = delta;
      this.afterPoints = afterPoints;
      this.levelName = levelName;
   }
}
