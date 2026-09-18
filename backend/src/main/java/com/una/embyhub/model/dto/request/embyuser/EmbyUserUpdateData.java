package com.una.embyhub.model.dto.request.embyuser;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import java.util.Date;
import lombok.Generated;

public class EmbyUserUpdateData implements ProtectedUserMutationRequest, Serializable {
   private Long id;
   private Date expirationDate;
   @JsonIgnore
   private boolean expirationDateSet;
   private String remarks;
   private Integer requestPackagesCount;
   private Integer hostLineType;
   private String avatar;

   public void setExpirationDate(Date expirationDate) {
      this.expirationDate = expirationDate;
      this.expirationDateSet = true;
   }

   @Generated
   public Long getId() {
      return this.id;
   }

   @Generated
   public Date getExpirationDate() {
      return this.expirationDate;
   }

   @Generated
   public boolean isExpirationDateSet() {
      return this.expirationDateSet;
   }

   @Generated
   public String getRemarks() {
      return this.remarks;
   }

   @Generated
   public Integer getRequestPackagesCount() {
      return this.requestPackagesCount;
   }

   @Generated
   public Integer getHostLineType() {
      return this.hostLineType;
   }

   @Generated
   public String getAvatar() {
      return this.avatar;
   }

   @Generated
   public void setId(final Long id) {
      this.id = id;
   }

   @Generated
   public void setRemarks(final String remarks) {
      this.remarks = remarks;
   }

   @Generated
   public void setRequestPackagesCount(final Integer requestPackagesCount) {
      this.requestPackagesCount = requestPackagesCount;
   }

   @Generated
   public void setHostLineType(final Integer hostLineType) {
      this.hostLineType = hostLineType;
   }

   @Generated
   public void setAvatar(final String avatar) {
      this.avatar = avatar;
   }

   @Generated
   @Override
   public boolean equals(final Object o) {
      if (o == this) {
         return true;
      } else if (!(o instanceof EmbyUserUpdateData other)) {
         return false;
      } else if (!other.canEqual(this)) {
         return false;
      } else if (this.isExpirationDateSet() != other.isExpirationDateSet()) {
         return false;
      } else {
         Object this$id = this.getId();
         Object other$id = other.getId();
         if (this$id == null ? other$id == null : this$id.equals(other$id)) {
            Object this$requestPackagesCount = this.getRequestPackagesCount();
            Object other$requestPackagesCount = other.getRequestPackagesCount();
            if (this$requestPackagesCount == null ? other$requestPackagesCount == null : this$requestPackagesCount.equals(other$requestPackagesCount)) {
               Object this$hostLineType = this.getHostLineType();
               Object other$hostLineType = other.getHostLineType();
               if (this$hostLineType == null ? other$hostLineType == null : this$hostLineType.equals(other$hostLineType)) {
                  Object this$expirationDate = this.getExpirationDate();
                  Object other$expirationDate = other.getExpirationDate();
                  if (this$expirationDate == null ? other$expirationDate == null : this$expirationDate.equals(other$expirationDate)) {
                     Object this$remarks = this.getRemarks();
                     Object other$remarks = other.getRemarks();
                     if (this$remarks == null ? other$remarks == null : this$remarks.equals(other$remarks)) {
                        Object this$avatar = this.getAvatar();
                        Object other$avatar = other.getAvatar();
                        return this$avatar == null ? other$avatar == null : this$avatar.equals(other$avatar);
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
      return other instanceof EmbyUserUpdateData;
   }

   @Generated
   @Override
   public int hashCode() {
      int PRIME = 59;
      int result = 1;
      result = result * 59 + (this.isExpirationDateSet() ? 79 : 97);
      Object $id = this.getId();
      result = result * 59 + ($id == null ? 43 : $id.hashCode());
      Object $requestPackagesCount = this.getRequestPackagesCount();
      result = result * 59 + ($requestPackagesCount == null ? 43 : $requestPackagesCount.hashCode());
      Object $hostLineType = this.getHostLineType();
      result = result * 59 + ($hostLineType == null ? 43 : $hostLineType.hashCode());
      Object $expirationDate = this.getExpirationDate();
      result = result * 59 + ($expirationDate == null ? 43 : $expirationDate.hashCode());
      Object $remarks = this.getRemarks();
      result = result * 59 + ($remarks == null ? 43 : $remarks.hashCode());
      Object $avatar = this.getAvatar();
      return result * 59 + ($avatar == null ? 43 : $avatar.hashCode());
   }

   @Generated
   @Override
   public String toString() {
      return "EmbyUserUpdateData(id="
         + this.getId()
         + ", expirationDate="
         + this.getExpirationDate()
         + ", expirationDateSet="
         + this.isExpirationDateSet()
         + ", remarks="
         + this.getRemarks()
         + ", requestPackagesCount="
         + this.getRequestPackagesCount()
         + ", hostLineType="
         + this.getHostLineType()
         + ", avatar="
         + this.getAvatar()
         + ")";
   }
}
