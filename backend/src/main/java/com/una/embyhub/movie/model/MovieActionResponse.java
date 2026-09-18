package com.una.embyhub.movie.model;

import java.io.Serializable;
import lombok.Generated;

public class MovieActionResponse implements Serializable {
   private static final long serialVersionUID = 1L;
   private boolean success;
   private String message;

   @Generated
   MovieActionResponse(final boolean success, final String message) {
      this.success = success;
      this.message = message;
   }

   @Generated
   public static MovieActionResponse.MovieActionResponseBuilder builder() {
      return new MovieActionResponse.MovieActionResponseBuilder();
   }

   @Generated
   public boolean isSuccess() {
      return this.success;
   }

   @Generated
   public String getMessage() {
      return this.message;
   }

   @Generated
   public void setSuccess(final boolean success) {
      this.success = success;
   }

   @Generated
   public void setMessage(final String message) {
      this.message = message;
   }

   @Generated
   @Override
   public boolean equals(final Object o) {
      if (o == this) {
         return true;
      } else if (!(o instanceof MovieActionResponse other)) {
         return false;
      } else if (!other.canEqual(this)) {
         return false;
      } else if (this.isSuccess() != other.isSuccess()) {
         return false;
      } else {
         Object this$message = this.getMessage();
         Object other$message = other.getMessage();
         return this$message == null ? other$message == null : this$message.equals(other$message);
      }
   }

   @Generated
   protected boolean canEqual(final Object other) {
      return other instanceof MovieActionResponse;
   }

   @Generated
   @Override
   public int hashCode() {
      int PRIME = 59;
      int result = 1;
      result = result * 59 + (this.isSuccess() ? 79 : 97);
      Object $message = this.getMessage();
      return result * 59 + ($message == null ? 43 : $message.hashCode());
   }

   @Generated
   @Override
   public String toString() {
      return "MovieActionResponse(success=" + this.isSuccess() + ", message=" + this.getMessage() + ")";
   }

   @Generated
   public static class MovieActionResponseBuilder {
      @Generated
      private boolean success;
      @Generated
      private String message;

      @Generated
      MovieActionResponseBuilder() {
      }

      @Generated
      public MovieActionResponse.MovieActionResponseBuilder success(final boolean success) {
         this.success = success;
         return this;
      }

      @Generated
      public MovieActionResponse.MovieActionResponseBuilder message(final String message) {
         this.message = message;
         return this;
      }

      @Generated
      public MovieActionResponse build() {
         return new MovieActionResponse(this.success, this.message);
      }

      @Generated
      @Override
      public String toString() {
         return "MovieActionResponse.MovieActionResponseBuilder(success=" + this.success + ", message=" + this.message + ")";
      }
   }
}
