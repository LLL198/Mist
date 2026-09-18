package com.una.embyhub.model.dto.response.useranalysis;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import lombok.Generated;

public class UserAnalysisOverviewResponse implements Serializable {
   private static final long serialVersionUID = 1L;
   private Boolean adminView;
   private Long selectedUserId;
   private String emptyMessage;
   private List<UserAnalysisUserResponse> users = new ArrayList<>();

   @Generated
   public Boolean getAdminView() {
      return this.adminView;
   }

   @Generated
   public Long getSelectedUserId() {
      return this.selectedUserId;
   }

   @Generated
   public String getEmptyMessage() {
      return this.emptyMessage;
   }

   @Generated
   public List<UserAnalysisUserResponse> getUsers() {
      return this.users;
   }

   @Generated
   public void setAdminView(final Boolean adminView) {
      this.adminView = adminView;
   }

   @Generated
   public void setSelectedUserId(final Long selectedUserId) {
      this.selectedUserId = selectedUserId;
   }

   @Generated
   public void setEmptyMessage(final String emptyMessage) {
      this.emptyMessage = emptyMessage;
   }

   @Generated
   public void setUsers(final List<UserAnalysisUserResponse> users) {
      this.users = users;
   }

   @Generated
   @Override
   public boolean equals(final Object o) {
      if (o == this) {
         return true;
      } else if (!(o instanceof UserAnalysisOverviewResponse other)) {
         return false;
      } else if (!other.canEqual(this)) {
         return false;
      } else {
         Object this$adminView = this.getAdminView();
         Object other$adminView = other.getAdminView();
         if (this$adminView == null ? other$adminView == null : this$adminView.equals(other$adminView)) {
            Object this$selectedUserId = this.getSelectedUserId();
            Object other$selectedUserId = other.getSelectedUserId();
            if (this$selectedUserId == null ? other$selectedUserId == null : this$selectedUserId.equals(other$selectedUserId)) {
               Object this$emptyMessage = this.getEmptyMessage();
               Object other$emptyMessage = other.getEmptyMessage();
               if (this$emptyMessage == null ? other$emptyMessage == null : this$emptyMessage.equals(other$emptyMessage)) {
                  Object this$users = this.getUsers();
                  Object other$users = other.getUsers();
                  return this$users == null ? other$users == null : this$users.equals(other$users);
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
      return other instanceof UserAnalysisOverviewResponse;
   }

   @Generated
   @Override
   public int hashCode() {
      int PRIME = 59;
      int result = 1;
      Object $adminView = this.getAdminView();
      result = result * 59 + ($adminView == null ? 43 : $adminView.hashCode());
      Object $selectedUserId = this.getSelectedUserId();
      result = result * 59 + ($selectedUserId == null ? 43 : $selectedUserId.hashCode());
      Object $emptyMessage = this.getEmptyMessage();
      result = result * 59 + ($emptyMessage == null ? 43 : $emptyMessage.hashCode());
      Object $users = this.getUsers();
      return result * 59 + ($users == null ? 43 : $users.hashCode());
   }

   @Generated
   @Override
   public String toString() {
      return "UserAnalysisOverviewResponse(adminView="
         + this.getAdminView()
         + ", selectedUserId="
         + this.getSelectedUserId()
         + ", emptyMessage="
         + this.getEmptyMessage()
         + ", users="
         + this.getUsers()
         + ")";
   }
}
