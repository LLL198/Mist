package com.una.embyhub.payment.model;

public final class PaymentAccountOrderStatus {
   public static final String PENDING = "PENDING";
   public static final String PAID = "PAID";
   public static final String PROVISIONING = "PROVISIONING";
   public static final String COMPLETED = "COMPLETED";
   public static final String EXPIRED = "EXPIRED";
   public static final String REVIEW = "REVIEW";
   public static final String FAILED = "FAILED";

   private PaymentAccountOrderStatus() {
   }
}
