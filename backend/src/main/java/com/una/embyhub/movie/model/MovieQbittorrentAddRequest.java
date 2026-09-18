package com.una.embyhub.movie.model;

import jakarta.validation.constraints.NotBlank;
import java.io.Serializable;
import lombok.Generated;

public class MovieQbittorrentAddRequest implements Serializable {
   private static final long serialVersionUID = 1L;
   @NotBlank(
      message = "磁力链接不能为空"
   )
   private String magnet;
   private String savePath;

   @Generated
   public String getMagnet() {
      return this.magnet;
   }

   @Generated
   public String getSavePath() {
      return this.savePath;
   }

   @Generated
   public void setMagnet(final String magnet) {
      this.magnet = magnet;
   }

   @Generated
   public void setSavePath(final String savePath) {
      this.savePath = savePath;
   }

   @Generated
   @Override
   public boolean equals(final Object o) {
      if (o == this) {
         return true;
      } else if (!(o instanceof MovieQbittorrentAddRequest other)) {
         return false;
      } else if (!other.canEqual(this)) {
         return false;
      } else {
         Object this$magnet = this.getMagnet();
         Object other$magnet = other.getMagnet();
         if (this$magnet == null ? other$magnet == null : this$magnet.equals(other$magnet)) {
            Object this$savePath = this.getSavePath();
            Object other$savePath = other.getSavePath();
            return this$savePath == null ? other$savePath == null : this$savePath.equals(other$savePath);
         } else {
            return false;
         }
      }
   }

   @Generated
   protected boolean canEqual(final Object other) {
      return other instanceof MovieQbittorrentAddRequest;
   }

   @Generated
   @Override
   public int hashCode() {
      int PRIME = 59;
      int result = 1;
      Object $magnet = this.getMagnet();
      result = result * 59 + ($magnet == null ? 43 : $magnet.hashCode());
      Object $savePath = this.getSavePath();
      return result * 59 + ($savePath == null ? 43 : $savePath.hashCode());
   }

   @Generated
   @Override
   public String toString() {
      return "MovieQbittorrentAddRequest(magnet=" + this.getMagnet() + ", savePath=" + this.getSavePath() + ")";
   }
}
