package com.una.embyhub.model.dto.response.pointsbot;

import java.io.Serializable;
import java.util.Date;
import lombok.Generated;

public class PointsBotPortalLedgerResponse implements Serializable {
   private Long id;
   private Integer delta;
   private String reason;
   private String refId;
   private String serverName;
   private Date createDatetime;

   @Generated
   public Long getId() {
      return this.id;
   }

   @Generated
   public Integer getDelta() {
      return this.delta;
   }

   @Generated
   public String getReason() {
      return this.reason;
   }

   @Generated
   public String getRefId() {
      return this.refId;
   }

   @Generated
   public String getServerName() {
      return this.serverName;
   }

   @Generated
   public Date getCreateDatetime() {
      return this.createDatetime;
   }

   @Generated
   public void setId(final Long id) {
      this.id = id;
   }

   @Generated
   public void setDelta(final Integer delta) {
      this.delta = delta;
   }

   @Generated
   public void setReason(final String reason) {
      this.reason = reason;
   }

   @Generated
   public void setRefId(final String refId) {
      this.refId = refId;
   }

   @Generated
   public void setServerName(final String serverName) {
      this.serverName = serverName;
   }

   @Generated
   public void setCreateDatetime(final Date createDatetime) {
      this.createDatetime = createDatetime;
   }

   @Generated
   @Override
   public boolean equals(final Object o) {
      if (o == this) {
         return true;
      } else if (!(o instanceof PointsBotPortalLedgerResponse other)) {
         return false;
      } else if (!other.canEqual(this)) {
         return false;
      } else {
         Object this$id = this.getId();
         Object other$id = other.getId();
         if (this$id == null ? other$id == null : this$id.equals(other$id)) {
            Object this$delta = this.getDelta();
            Object other$delta = other.getDelta();
            if (this$delta == null ? other$delta == null : this$delta.equals(other$delta)) {
               Object this$reason = this.getReason();
               Object other$reason = other.getReason();
               if (this$reason == null ? other$reason == null : this$reason.equals(other$reason)) {
                  Object this$refId = this.getRefId();
                  Object other$refId = other.getRefId();
                  if (this$refId == null ? other$refId == null : this$refId.equals(other$refId)) {
                     Object this$serverName = this.getServerName();
                     Object other$serverName = other.getServerName();
                     if (this$serverName == null ? other$serverName == null : this$serverName.equals(other$serverName)) {
                        Object this$createDatetime = this.getCreateDatetime();
                        Object other$createDatetime = other.getCreateDatetime();
                        return this$createDatetime == null ? other$createDatetime == null : this$createDatetime.equals(other$createDatetime);
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
      return other instanceof PointsBotPortalLedgerResponse;
   }

   @Generated
   @Override
   public int hashCode() {
      int PRIME = 59;
      int result = 1;
      Object $id = this.getId();
      result = result * 59 + ($id == null ? 43 : $id.hashCode());
      Object $delta = this.getDelta();
      result = result * 59 + ($delta == null ? 43 : $delta.hashCode());
      Object $reason = this.getReason();
      result = result * 59 + ($reason == null ? 43 : $reason.hashCode());
      Object $refId = this.getRefId();
      result = result * 59 + ($refId == null ? 43 : $refId.hashCode());
      Object $serverName = this.getServerName();
      result = result * 59 + ($serverName == null ? 43 : $serverName.hashCode());
      Object $createDatetime = this.getCreateDatetime();
      return result * 59 + ($createDatetime == null ? 43 : $createDatetime.hashCode());
   }

   @Generated
   @Override
   public String toString() {
      return "PointsBotPortalLedgerResponse(id="
         + this.getId()
         + ", delta="
         + this.getDelta()
         + ", reason="
         + this.getReason()
         + ", refId="
         + this.getRefId()
         + ", serverName="
         + this.getServerName()
         + ", createDatetime="
         + this.getCreateDatetime()
         + ")";
   }
}
