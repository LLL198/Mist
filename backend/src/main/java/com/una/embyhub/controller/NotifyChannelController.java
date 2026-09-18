package com.una.embyhub.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.una.embyhub.model.dto.request.notifychannel.NotifyChannelSave;
import com.una.embyhub.model.dto.request.notifychannel.NotifyChannelUpdate;
import com.una.embyhub.model.dto.response.notifychannel.NotifyChannelResponse;
import com.una.embyhub.service.NotifyChannelService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.CacheControl;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping({"notifyChannel"})
public class NotifyChannelController {
   @Autowired
   private NotifyChannelService notifyChannelService;

   @PostMapping({"select"})
   @SaCheckPermission({"admin"})
   public List<NotifyChannelResponse> select() {
      return this.notifyChannelService.select();
   }

   @PostMapping({"add"})
   @SaCheckPermission({"admin"})
   public NotifyChannelResponse add(@RequestBody NotifyChannelSave notifyChannelSave) {
      return this.notifyChannelService.add(notifyChannelSave);
   }

   @PostMapping({"update"})
   @SaCheckPermission({"admin"})
   public void update(@RequestBody NotifyChannelUpdate notifyChannelUpdate) {
      this.notifyChannelService.update(notifyChannelUpdate);
   }

   @PostMapping({"delete"})
   @SaCheckPermission({"admin"})
   public void delete(@RequestParam Long id) {
      this.notifyChannelService.delete(id);
   }

   @PostMapping({"telegram/startPanelImage"})
   @SaCheckPermission({"admin"})
   public NotifyChannelResponse uploadTelegramStartPanelImage(@RequestParam Long channelId, @RequestParam("file") MultipartFile file) {
      return this.notifyChannelService.uploadTelegramStartPanelImage(channelId, file);
   }

   @GetMapping({"telegram/startPanelImage/content"})
   @SaCheckPermission({"admin"})
   public ResponseEntity<byte[]> getTelegramStartPanelImage(@RequestParam Long channelId) {
      byte[] image = this.notifyChannelService.getTelegramStartPanelImage(channelId);
      return ResponseEntity.ok().contentType(startPanelImageContentType(image)).cacheControl(CacheControl.noStore()).body(image);
   }

   @PostMapping({"telegram/startPanelImage/reset"})
   @SaCheckPermission({"admin"})
   public NotifyChannelResponse resetTelegramStartPanelImage(@RequestParam Long channelId) {
      return this.notifyChannelService.resetTelegramStartPanelImage(channelId);
   }

   static MediaType startPanelImageContentType(byte[] image) {
      return image != null && image.length >= 3 && (image[0] & 255) == 255 && (image[1] & 255) == 216 && (image[2] & 255) == 255
         ? MediaType.IMAGE_JPEG
         : MediaType.IMAGE_PNG;
   }
}
