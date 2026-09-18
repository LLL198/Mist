package com.una.embyhub.model.dto.response.telegram;

import com.una.embyhub.model.dto.response.embyuser.EmbyUserCustomResponse;
import java.io.Serializable;
import java.util.List;
import lombok.Generated;

public class TelegramBindingActionResponse implements Serializable {
   public static final String STATUS_COMPLETED = "COMPLETED";
   public static final String STATUS_PENDING = "PENDING";
   public static final String STATUS_SERVER_SELECTION_REQUIRED = "SERVER_SELECTION_REQUIRED";
   private String status;
   private Long reviewId;
   private String reviewUuid;
   private String message;
   private EmbyUserCustomResponse user;
   private String selectionToken;
   private List<EmbyUserCustomResponse.ServerOption> servers;

   public boolean isPending() {
      return "PENDING".equals(this.status);
   }

   public boolean isServerSelectionRequired() {
      return "SERVER_SELECTION_REQUIRED".equals(this.status);
   }

   public static TelegramBindingActionResponse completed(String message, EmbyUserCustomResponse user) {
      return new TelegramBindingActionResponse("COMPLETED", null, null, message, user, null, null);
   }

   public static TelegramBindingActionResponse pending(Long reviewId, String reviewUuid, String message, EmbyUserCustomResponse user) {
      return new TelegramBindingActionResponse("PENDING", reviewId, reviewUuid, message, user, null, null);
   }

   public static TelegramBindingActionResponse serverSelection(String selectionToken, List<EmbyUserCustomResponse.ServerOption> servers, String message) {
      return new TelegramBindingActionResponse("SERVER_SELECTION_REQUIRED", null, null, message, null, selectionToken, servers);
   }

   @Generated
   public String getStatus() {
      return this.status;
   }

   @Generated
   public Long getReviewId() {
      return this.reviewId;
   }

   @Generated
   public String getReviewUuid() {
      return this.reviewUuid;
   }

   @Generated
   public String getMessage() {
      return this.message;
   }

   @Generated
   public EmbyUserCustomResponse getUser() {
      return this.user;
   }

   @Generated
   public String getSelectionToken() {
      return this.selectionToken;
   }

   @Generated
   public List<EmbyUserCustomResponse.ServerOption> getServers() {
      return this.servers;
   }

   @Generated
   public void setStatus(final String status) {
      this.status = status;
   }

   @Generated
   public void setReviewId(final Long reviewId) {
      this.reviewId = reviewId;
   }

   @Generated
   public void setReviewUuid(final String reviewUuid) {
      this.reviewUuid = reviewUuid;
   }

   @Generated
   public void setMessage(final String message) {
      this.message = message;
   }

   @Generated
   public void setUser(final EmbyUserCustomResponse user) {
      this.user = user;
   }

   @Generated
   public void setSelectionToken(final String selectionToken) {
      this.selectionToken = selectionToken;
   }

   @Generated
   public void setServers(final List<EmbyUserCustomResponse.ServerOption> servers) {
      this.servers = servers;
   }

   @Generated
   @Override
   public boolean equals(final Object o) {
      if (o == this) {
         return true;
      } else if (!(o instanceof TelegramBindingActionResponse other)) {
         return false;
      } else if (!other.canEqual(this)) {
         return false;
      } else {
         Object this$reviewId = this.getReviewId();
         Object other$reviewId = other.getReviewId();
         if (this$reviewId == null ? other$reviewId == null : this$reviewId.equals(other$reviewId)) {
            Object this$status = this.getStatus();
            Object other$status = other.getStatus();
            if (this$status == null ? other$status == null : this$status.equals(other$status)) {
               Object this$reviewUuid = this.getReviewUuid();
               Object other$reviewUuid = other.getReviewUuid();
               if (this$reviewUuid == null ? other$reviewUuid == null : this$reviewUuid.equals(other$reviewUuid)) {
                  Object this$message = this.getMessage();
                  Object other$message = other.getMessage();
                  if (this$message == null ? other$message == null : this$message.equals(other$message)) {
                     Object this$user = this.getUser();
                     Object other$user = other.getUser();
                     if (this$user == null ? other$user == null : this$user.equals(other$user)) {
                        Object this$selectionToken = this.getSelectionToken();
                        Object other$selectionToken = other.getSelectionToken();
                        if (this$selectionToken == null ? other$selectionToken == null : this$selectionToken.equals(other$selectionToken)) {
                           Object this$servers = this.getServers();
                           Object other$servers = other.getServers();
                           return this$servers == null ? other$servers == null : this$servers.equals(other$servers);
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
      return other instanceof TelegramBindingActionResponse;
   }

   @Generated
   @Override
   public int hashCode() {
      int PRIME = 59;
      int result = 1;
      Object $reviewId = this.getReviewId();
      result = result * 59 + ($reviewId == null ? 43 : $reviewId.hashCode());
      Object $status = this.getStatus();
      result = result * 59 + ($status == null ? 43 : $status.hashCode());
      Object $reviewUuid = this.getReviewUuid();
      result = result * 59 + ($reviewUuid == null ? 43 : $reviewUuid.hashCode());
      Object $message = this.getMessage();
      result = result * 59 + ($message == null ? 43 : $message.hashCode());
      Object $user = this.getUser();
      result = result * 59 + ($user == null ? 43 : $user.hashCode());
      Object $selectionToken = this.getSelectionToken();
      result = result * 59 + ($selectionToken == null ? 43 : $selectionToken.hashCode());
      Object $servers = this.getServers();
      return result * 59 + ($servers == null ? 43 : $servers.hashCode());
   }

   @Generated
   @Override
   public String toString() {
      return "TelegramBindingActionResponse(status="
         + this.getStatus()
         + ", reviewId="
         + this.getReviewId()
         + ", reviewUuid="
         + this.getReviewUuid()
         + ", message="
         + this.getMessage()
         + ", user="
         + this.getUser()
         + ", selectionToken="
         + this.getSelectionToken()
         + ", servers="
         + this.getServers()
         + ")";
   }

   @Generated
   public TelegramBindingActionResponse() {
   }

   @Generated
   public TelegramBindingActionResponse(
      final String status,
      final Long reviewId,
      final String reviewUuid,
      final String message,
      final EmbyUserCustomResponse user,
      final String selectionToken,
      final List<EmbyUserCustomResponse.ServerOption> servers
   ) {
      this.status = status;
      this.reviewId = reviewId;
      this.reviewUuid = reviewUuid;
      this.message = message;
      this.user = user;
      this.selectionToken = selectionToken;
      this.servers = servers;
   }
}
