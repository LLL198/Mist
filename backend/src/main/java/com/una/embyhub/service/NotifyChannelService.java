package com.una.embyhub.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.una.embyhub.model.dto.request.notifychannel.NotifyChannelSave;
import com.una.embyhub.model.dto.request.notifychannel.NotifyChannelUpdate;
import com.una.embyhub.model.dto.response.notifychannel.NotifyChannelResponse;
import com.una.embyhub.model.entity.NotifyChannel;
import java.util.List;
import org.springframework.web.multipart.MultipartFile;

public interface NotifyChannelService extends IService<NotifyChannel> {
   List<NotifyChannelResponse> select();

   NotifyChannelResponse add(NotifyChannelSave notifyChannelSave);

   void update(NotifyChannelUpdate notifyChannelUpdate);

   void delete(Long id);

   NotifyChannelResponse uploadTelegramStartPanelImage(Long channelId, MultipartFile file);

   byte[] getTelegramStartPanelImage(Long channelId);

   NotifyChannelResponse resetTelegramStartPanelImage(Long channelId);
}
