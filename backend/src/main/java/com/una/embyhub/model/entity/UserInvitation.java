package com.una.embyhub.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Generated;

@TableName("user_invitation")
public class UserInvitation extends BaseEntity implements Serializable {
   private static final long serialVersionUID = 1L;
   @TableId(
      value = "id",
      type = IdType.AUTO
   )
   private Long id;
   @TableField("inviter_id")
   private Long inviterId;
   @TableField("invitee_id")
   private Long inviteeId;
   @TableField("invitation_source")
   private String invitationSource;

   @Generated
   public Long getId() {
      return this.id;
   }

   @Generated
   public Long getInviterId() {
      return this.inviterId;
   }

   @Generated
   public Long getInviteeId() {
      return this.inviteeId;
   }

   @Generated
   public String getInvitationSource() {
      return this.invitationSource;
   }

   @Generated
   public void setId(final Long id) {
      this.id = id;
   }

   @Generated
   public void setInviterId(final Long inviterId) {
      this.inviterId = inviterId;
   }

   @Generated
   public void setInviteeId(final Long inviteeId) {
      this.inviteeId = inviteeId;
   }

   @Generated
   public void setInvitationSource(final String invitationSource) {
      this.invitationSource = invitationSource;
   }

   @Generated
   @Override
   public String toString() {
      return "UserInvitation(id="
         + this.getId()
         + ", inviterId="
         + this.getInviterId()
         + ", inviteeId="
         + this.getInviteeId()
         + ", invitationSource="
         + this.getInvitationSource()
         + ")";
   }

   @Generated
   @Override
   public boolean equals(final Object o) {
      if (o == this) {
         return true;
      } else if (!(o instanceof UserInvitation other)) {
         return false;
      } else if (!other.canEqual(this)) {
         return false;
      } else if (!super.equals(o)) {
         return false;
      } else {
         Object this$id = this.getId();
         Object other$id = other.getId();
         if (this$id == null ? other$id == null : this$id.equals(other$id)) {
            Object this$inviterId = this.getInviterId();
            Object other$inviterId = other.getInviterId();
            if (this$inviterId == null ? other$inviterId == null : this$inviterId.equals(other$inviterId)) {
               Object this$inviteeId = this.getInviteeId();
               Object other$inviteeId = other.getInviteeId();
               if (this$inviteeId == null ? other$inviteeId == null : this$inviteeId.equals(other$inviteeId)) {
                  Object this$invitationSource = this.getInvitationSource();
                  Object other$invitationSource = other.getInvitationSource();
                  return this$invitationSource == null ? other$invitationSource == null : this$invitationSource.equals(other$invitationSource);
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
   protected boolean canEqual(final Object other) {
      return other instanceof UserInvitation;
   }

   @Generated
   @Override
   public int hashCode() {
      int PRIME = 59;
      int result = super.hashCode();
      Object $id = this.getId();
      result = result * 59 + ($id == null ? 43 : $id.hashCode());
      Object $inviterId = this.getInviterId();
      result = result * 59 + ($inviterId == null ? 43 : $inviterId.hashCode());
      Object $inviteeId = this.getInviteeId();
      result = result * 59 + ($inviteeId == null ? 43 : $inviteeId.hashCode());
      Object $invitationSource = this.getInvitationSource();
      return result * 59 + ($invitationSource == null ? 43 : $invitationSource.hashCode());
   }
}
