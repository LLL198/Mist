package com.una.embyhub.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.una.embyhub.model.dto.request.wechatipconfig.WeChatIpConfigSave;
import com.una.embyhub.model.dto.request.wechatipconfig.WeChatIpConfigUpdate;
import com.una.embyhub.model.dto.response.wechatipconfig.WeChatIpConfigResponse;
import com.una.embyhub.model.entity.WeChatIpConfig;
import java.util.List;

public interface WeChatIpConfigService extends IService<WeChatIpConfig> {
   List<WeChatIpConfigResponse> select();

   void add(WeChatIpConfigSave save);

   void update(WeChatIpConfigUpdate update);

   void delete(Long id);

   WeChatIpConfig getFirstEnabled();

   List<WeChatIpConfig> getAllEnabled();

   void updateLastIp(Long id, String ip);
}
