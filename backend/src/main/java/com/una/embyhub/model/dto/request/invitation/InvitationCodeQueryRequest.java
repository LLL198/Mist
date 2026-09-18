package com.una.embyhub.model.dto.request.invitation;

import java.io.Serializable;
import lombok.Generated;

public class InvitationCodeQueryRequest implements Serializable {
   private String code;
   private Long embyInfoId;
   private Integer hostLineType;
   private Integer status;
   private long current = 1L;
   private long size = 10L;

   @Generated
   public String getCode() {
      return this.code;
   }

   @Generated
   public Long getEmbyInfoId() {
      return this.embyInfoId;
   }

   @Generated
   public Integer getHostLineType() {
      return this.hostLineType;
   }

   @Generated
   public Integer getStatus() {
      return this.status;
   }

   @Generated
   public long getCurrent() {
      return this.current;
   }

   @Generated
   public long getSize() {
      return this.size;
   }

   @Generated
   public void setCode(final String code) {
      this.code = code;
   }

   @Generated
   public void setEmbyInfoId(final Long embyInfoId) {
      this.embyInfoId = embyInfoId;
   }

   @Generated
   public void setHostLineType(final Integer hostLineType) {
      this.hostLineType = hostLineType;
   }

   @Generated
   public void setStatus(final Integer status) {
      this.status = status;
   }

   @Generated
   public void setCurrent(final long current) {
      this.current = current;
   }

   @Generated
   public void setSize(final long size) {
      this.size = size;
   }

   @Generated
   @Override
   public boolean equals(final Object o) {
      if (o == this) {
         return true;
      } else if (!(o instanceof InvitationCodeQueryRequest other)) {
         return false;
      } else if (!other.canEqual(this)) {
         return false;
      } else if (this.getCurrent() != other.getCurrent()) {
         return false;
      } else if (this.getSize() != other.getSize()) {
         return false;
      } else {
         Object this$embyInfoId = this.getEmbyInfoId();
         Object other$embyInfoId = other.getEmbyInfoId();
         if (this$embyInfoId == null ? other$embyInfoId == null : this$embyInfoId.equals(other$embyInfoId)) {
            Object this$hostLineType = this.getHostLineType();
            Object other$hostLineType = other.getHostLineType();
            if (this$hostLineType == null ? other$hostLineType == null : this$hostLineType.equals(other$hostLineType)) {
               Object this$status = this.getStatus();
               Object other$status = other.getStatus();
               if (this$status == null ? other$status == null : this$status.equals(other$status)) {
                  Object this$code = this.getCode();
                  Object other$code = other.getCode();
                  return this$code == null ? other$code == null : this$code.equals(other$code);
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
      return other instanceof InvitationCodeQueryRequest;
   }

   @Generated
   @Override
   public int hashCode() {
      int PRIME = 59;
      int result = 1;
      long $current = this.getCurrent();
      result = result * 59 + (int)($current >>> 32 ^ $current);
      long $size = this.getSize();
      result = result * 59 + (int)($size >>> 32 ^ $size);
      Object $embyInfoId = this.getEmbyInfoId();
      result = result * 59 + ($embyInfoId == null ? 43 : $embyInfoId.hashCode());
      Object $hostLineType = this.getHostLineType();
      result = result * 59 + ($hostLineType == null ? 43 : $hostLineType.hashCode());
      Object $status = this.getStatus();
      result = result * 59 + ($status == null ? 43 : $status.hashCode());
      Object $code = this.getCode();
      return result * 59 + ($code == null ? 43 : $code.hashCode());
   }

   @Generated
   @Override
   public String toString() {
      return "InvitationCodeQueryRequest(code="
         + this.getCode()
         + ", embyInfoId="
         + this.getEmbyInfoId()
         + ", hostLineType="
         + this.getHostLineType()
         + ", status="
         + this.getStatus()
         + ", current="
         + this.getCurrent()
         + ", size="
         + this.getSize()
         + ")";
   }
}
