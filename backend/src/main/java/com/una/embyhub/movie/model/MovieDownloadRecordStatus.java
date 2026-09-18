package com.una.embyhub.movie.model;

public final class MovieDownloadRecordStatus {
   public static final String PENDING = "PENDING";
   public static final String DOWNLOADING = "DOWNLOADING";
   public static final String COMPLETED = "COMPLETED";
   public static final String LINKED = "LINKED";
   public static final String LINK_FAILED = "LINK_FAILED";
   public static final String FAILED = "FAILED";

   private MovieDownloadRecordStatus() {
   }
}
