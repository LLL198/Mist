package com.una.embyhub.config.job;

import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class AppBootInfo {
   private static volatile long appStartedAtMs = System.currentTimeMillis();

   @EventListener({ApplicationStartedEvent.class})
   public void onStarted(ApplicationStartedEvent e) {
      appStartedAtMs = System.currentTimeMillis();
   }

   public static long getAppStartedAtMs() {
      return appStartedAtMs;
   }
}
