package com.una.embyhub.model.dto.response.pointsbot;

import java.io.Serializable;
import java.util.Date;
import lombok.Generated;

public final class PointsBotPortalRedeemResponse implements Serializable {
   private final Long recordId;
   private final String status;
   private final String configName;
   private final String redeemType;
   private final Integer pointsSpent;
   private final Long remainingPoints;
   private final String targetUserName;
   private final String password;
   private final Date expirationDate;
   private final String message;

   @Generated
   PointsBotPortalRedeemResponse(
      final Long recordId,
      final String status,
      final String configName,
      final String redeemType,
      final Integer pointsSpent,
      final Long remainingPoints,
      final String targetUserName,
      final String password,
      final Date expirationDate,
      final String message
   ) {
      this.recordId = recordId;
      this.status = status;
      this.configName = configName;
      this.redeemType = redeemType;
      this.pointsSpent = pointsSpent;
      this.remainingPoints = remainingPoints;
      this.targetUserName = targetUserName;
      this.password = password;
      this.expirationDate = expirationDate;
      this.message = message;
   }

   @Generated
   public static PointsBotPortalRedeemResponse.PointsBotPortalRedeemResponseBuilder builder() {
      return new PointsBotPortalRedeemResponse.PointsBotPortalRedeemResponseBuilder();
   }

   @Generated
   public Long getRecordId() {
      return this.recordId;
   }

   @Generated
   public String getStatus() {
      return this.status;
   }

   @Generated
   public String getConfigName() {
      return this.configName;
   }

   @Generated
   public String getRedeemType() {
      return this.redeemType;
   }

   @Generated
   public Integer getPointsSpent() {
      return this.pointsSpent;
   }

   @Generated
   public Long getRemainingPoints() {
      return this.remainingPoints;
   }

   @Generated
   public String getTargetUserName() {
      return this.targetUserName;
   }

   @Generated
   public String getPassword() {
      return this.password;
   }

   @Generated
   public Date getExpirationDate() {
      return this.expirationDate;
   }

   @Generated
   public String getMessage() {
      return this.message;
   }

   @Generated
   @Override
   public boolean equals(final Object o) {
      if (o == this) {
         return true;
      } else if (!(o instanceof PointsBotPortalRedeemResponse other)) {
         return false;
      } else {
         Object this$recordId = this.getRecordId();
         Object other$recordId = other.getRecordId();
         if (this$recordId == null ? other$recordId == null : this$recordId.equals(other$recordId)) {
            Object this$pointsSpent = this.getPointsSpent();
            Object other$pointsSpent = other.getPointsSpent();
            if (this$pointsSpent == null ? other$pointsSpent == null : this$pointsSpent.equals(other$pointsSpent)) {
               Object this$remainingPoints = this.getRemainingPoints();
               Object other$remainingPoints = other.getRemainingPoints();
               if (this$remainingPoints == null ? other$remainingPoints == null : this$remainingPoints.equals(other$remainingPoints)) {
                  Object this$status = this.getStatus();
                  Object other$status = other.getStatus();
                  if (this$status == null ? other$status == null : this$status.equals(other$status)) {
                     Object this$configName = this.getConfigName();
                     Object other$configName = other.getConfigName();
                     if (this$configName == null ? other$configName == null : this$configName.equals(other$configName)) {
                        Object this$redeemType = this.getRedeemType();
                        Object other$redeemType = other.getRedeemType();
                        if (this$redeemType == null ? other$redeemType == null : this$redeemType.equals(other$redeemType)) {
                           Object this$targetUserName = this.getTargetUserName();
                           Object other$targetUserName = other.getTargetUserName();
                           if (this$targetUserName == null ? other$targetUserName == null : this$targetUserName.equals(other$targetUserName)) {
                              Object this$password = this.getPassword();
                              Object other$password = other.getPassword();
                              if (this$password == null ? other$password == null : this$password.equals(other$password)) {
                                 Object this$expirationDate = this.getExpirationDate();
                                 Object other$expirationDate = other.getExpirationDate();
                                 if (this$expirationDate == null ? other$expirationDate == null : this$expirationDate.equals(other$expirationDate)) {
                                    Object this$message = this.getMessage();
                                    Object other$message = other.getMessage();
                                    return this$message == null ? other$message == null : this$message.equals(other$message);
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
   @Override
   public int hashCode() {
      int PRIME = 59;
      int result = 1;
      Object $recordId = this.getRecordId();
      result = result * 59 + ($recordId == null ? 43 : $recordId.hashCode());
      Object $pointsSpent = this.getPointsSpent();
      result = result * 59 + ($pointsSpent == null ? 43 : $pointsSpent.hashCode());
      Object $remainingPoints = this.getRemainingPoints();
      result = result * 59 + ($remainingPoints == null ? 43 : $remainingPoints.hashCode());
      Object $status = this.getStatus();
      result = result * 59 + ($status == null ? 43 : $status.hashCode());
      Object $configName = this.getConfigName();
      result = result * 59 + ($configName == null ? 43 : $configName.hashCode());
      Object $redeemType = this.getRedeemType();
      result = result * 59 + ($redeemType == null ? 43 : $redeemType.hashCode());
      Object $targetUserName = this.getTargetUserName();
      result = result * 59 + ($targetUserName == null ? 43 : $targetUserName.hashCode());
      Object $password = this.getPassword();
      result = result * 59 + ($password == null ? 43 : $password.hashCode());
      Object $expirationDate = this.getExpirationDate();
      result = result * 59 + ($expirationDate == null ? 43 : $expirationDate.hashCode());
      Object $message = this.getMessage();
      return result * 59 + ($message == null ? 43 : $message.hashCode());
   }

   @Generated
   @Override
   public String toString() {
      return "PointsBotPortalRedeemResponse(recordId="
         + this.getRecordId()
         + ", status="
         + this.getStatus()
         + ", configName="
         + this.getConfigName()
         + ", redeemType="
         + this.getRedeemType()
         + ", pointsSpent="
         + this.getPointsSpent()
         + ", remainingPoints="
         + this.getRemainingPoints()
         + ", targetUserName="
         + this.getTargetUserName()
         + ", password="
         + this.getPassword()
         + ", expirationDate="
         + this.getExpirationDate()
         + ", message="
         + this.getMessage()
         + ")";
   }

   @Generated
   public static class PointsBotPortalRedeemResponseBuilder {
      @Generated
      private Long recordId;
      @Generated
      private String status;
      @Generated
      private String configName;
      @Generated
      private String redeemType;
      @Generated
      private Integer pointsSpent;
      @Generated
      private Long remainingPoints;
      @Generated
      private String targetUserName;
      @Generated
      private String password;
      @Generated
      private Date expirationDate;
      @Generated
      private String message;

      @Generated
      PointsBotPortalRedeemResponseBuilder() {
      }

      @Generated
      public PointsBotPortalRedeemResponse.PointsBotPortalRedeemResponseBuilder recordId(final Long recordId) {
         this.recordId = recordId;
         return this;
      }

      @Generated
      public PointsBotPortalRedeemResponse.PointsBotPortalRedeemResponseBuilder status(final String status) {
         this.status = status;
         return this;
      }

      @Generated
      public PointsBotPortalRedeemResponse.PointsBotPortalRedeemResponseBuilder configName(final String configName) {
         this.configName = configName;
         return this;
      }

      @Generated
      public PointsBotPortalRedeemResponse.PointsBotPortalRedeemResponseBuilder redeemType(final String redeemType) {
         this.redeemType = redeemType;
         return this;
      }

      @Generated
      public PointsBotPortalRedeemResponse.PointsBotPortalRedeemResponseBuilder pointsSpent(final Integer pointsSpent) {
         this.pointsSpent = pointsSpent;
         return this;
      }

      @Generated
      public PointsBotPortalRedeemResponse.PointsBotPortalRedeemResponseBuilder remainingPoints(final Long remainingPoints) {
         this.remainingPoints = remainingPoints;
         return this;
      }

      @Generated
      public PointsBotPortalRedeemResponse.PointsBotPortalRedeemResponseBuilder targetUserName(final String targetUserName) {
         this.targetUserName = targetUserName;
         return this;
      }

      @Generated
      public PointsBotPortalRedeemResponse.PointsBotPortalRedeemResponseBuilder password(final String password) {
         this.password = password;
         return this;
      }

      @Generated
      public PointsBotPortalRedeemResponse.PointsBotPortalRedeemResponseBuilder expirationDate(final Date expirationDate) {
         this.expirationDate = expirationDate;
         return this;
      }

      @Generated
      public PointsBotPortalRedeemResponse.PointsBotPortalRedeemResponseBuilder message(final String message) {
         this.message = message;
         return this;
      }

      @Generated
      public PointsBotPortalRedeemResponse build() {
         return new PointsBotPortalRedeemResponse(
            this.recordId,
            this.status,
            this.configName,
            this.redeemType,
            this.pointsSpent,
            this.remainingPoints,
            this.targetUserName,
            this.password,
            this.expirationDate,
            this.message
         );
      }

      @Generated
      @Override
      public String toString() {
         return "PointsBotPortalRedeemResponse.PointsBotPortalRedeemResponseBuilder(recordId="
            + this.recordId
            + ", status="
            + this.status
            + ", configName="
            + this.configName
            + ", redeemType="
            + this.redeemType
            + ", pointsSpent="
            + this.pointsSpent
            + ", remainingPoints="
            + this.remainingPoints
            + ", targetUserName="
            + this.targetUserName
            + ", password="
            + this.password
            + ", expirationDate="
            + this.expirationDate
            + ", message="
            + this.message
            + ")";
      }
   }
}
