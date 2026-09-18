package com.una.embyhub.service.impl;

import com.una.embyhub.event.SimultaneousPlaybackUserConfigCleanupEvent;
import lombok.Generated;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
public class SimultaneousPlaybackUserConfigCleanupListener {
   @Generated
   private static final Logger log = LoggerFactory.getLogger(SimultaneousPlaybackUserConfigCleanupListener.class);
   @Autowired
   private SimultaneousPlaybackUserConfigCleanupService cleanupService;

   @TransactionalEventListener(
      phase = TransactionPhase.AFTER_COMMIT,
      fallbackExecution = true
   )
   public void onUserConfigCleanup(SimultaneousPlaybackUserConfigCleanupEvent event) {
      if (event != null) {
         try {
            this.cleanupService.removeUserRule(event.embyInfoId(), event.embyUserId(), event.embyUserName());
         } catch (Exception var3) {
            log.warn("清理用户同播配置失败，不影响用户主操作: serverId={}, embyUserId={}, userName={}", event.embyInfoId(), event.embyUserId(), event.embyUserName(), var3);
         }
      }
   }
}
