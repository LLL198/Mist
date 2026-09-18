package com.una.embyhub.model.dto.response.moviepilot;

import lombok.Generated;

public class MoviePilotSubscribeResponse {
   private Boolean success;
   private String message;
   private MoviePilotSubscribeResponse.SubscribeData data;

   @Generated
   public Boolean getSuccess() {
      return this.success;
   }

   @Generated
   public String getMessage() {
      return this.message;
   }

   @Generated
   public MoviePilotSubscribeResponse.SubscribeData getData() {
      return this.data;
   }

   @Generated
   public void setSuccess(final Boolean success) {
      this.success = success;
   }

   @Generated
   public void setMessage(final String message) {
      this.message = message;
   }

   @Generated
   public void setData(final MoviePilotSubscribeResponse.SubscribeData data) {
      this.data = data;
   }

   @Generated
   @Override
   public boolean equals(final Object o) {
      if (o == this) {
         return true;
      } else if (!(o instanceof MoviePilotSubscribeResponse other)) {
         return false;
      } else if (!other.canEqual(this)) {
         return false;
      } else {
         Object this$success = this.getSuccess();
         Object other$success = other.getSuccess();
         if (this$success == null ? other$success == null : this$success.equals(other$success)) {
            Object this$message = this.getMessage();
            Object other$message = other.getMessage();
            if (this$message == null ? other$message == null : this$message.equals(other$message)) {
               Object this$data = this.getData();
               Object other$data = other.getData();
               return this$data == null ? other$data == null : this$data.equals(other$data);
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
      return other instanceof MoviePilotSubscribeResponse;
   }

   @Generated
   @Override
   public int hashCode() {
      int PRIME = 59;
      int result = 1;
      Object $success = this.getSuccess();
      result = result * 59 + ($success == null ? 43 : $success.hashCode());
      Object $message = this.getMessage();
      result = result * 59 + ($message == null ? 43 : $message.hashCode());
      Object $data = this.getData();
      return result * 59 + ($data == null ? 43 : $data.hashCode());
   }

   @Generated
   @Override
   public String toString() {
      return "MoviePilotSubscribeResponse(success=" + this.getSuccess() + ", message=" + this.getMessage() + ", data=" + this.getData() + ")";
   }

   public static class SubscribeData {
      private Long id;

      @Generated
      public Long getId() {
         return this.id;
      }

      @Generated
      public void setId(final Long id) {
         this.id = id;
      }

      @Generated
      @Override
      public boolean equals(final Object o) {
         if (o == this) {
            return true;
         } else if (!(o instanceof MoviePilotSubscribeResponse.SubscribeData other)) {
            return false;
         } else if (!other.canEqual(this)) {
            return false;
         } else {
            Object this$id = this.getId();
            Object other$id = other.getId();
            return this$id == null ? other$id == null : this$id.equals(other$id);
         }
      }

      @Generated
      protected boolean canEqual(final Object other) {
         return other instanceof MoviePilotSubscribeResponse.SubscribeData;
      }

      @Generated
      @Override
      public int hashCode() {
         int PRIME = 59;
         int result = 1;
         Object $id = this.getId();
         return result * 59 + ($id == null ? 43 : $id.hashCode());
      }

      @Generated
      @Override
      public String toString() {
         return "MoviePilotSubscribeResponse.SubscribeData(id=" + this.getId() + ")";
      }
   }
}
