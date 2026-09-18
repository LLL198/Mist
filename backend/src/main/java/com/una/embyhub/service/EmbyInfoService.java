package com.una.embyhub.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.una.embyhub.config.mybatis.MybatisPlusPage;
import com.una.embyhub.model.dto.request.embyinfo.EmbyInfoRequest;
import com.una.embyhub.model.dto.request.embyinfo.EmbyInfoSave;
import com.una.embyhub.model.dto.request.embyinfo.EmbyInfoUpdate;
import com.una.embyhub.model.dto.request.embyinfo.EmbyInfoUserOptionsRequest;
import com.una.embyhub.model.dto.response.embyinfo.EmbyInfoResponse;
import com.una.embyhub.model.dto.response.embyinfo.EmbyInfoUserOptionResponse;
import com.una.embyhub.model.entity.EmbyInfo;
import java.util.List;

public interface EmbyInfoService extends IService<EmbyInfo> {
   Page<EmbyInfoResponse> select(MybatisPlusPage<EmbyInfoRequest> page);

   void deleteByUserId(List<Long> embyInfoIds);

   void insertEmbyInfo(EmbyInfoSave embyInfoSave);

   void updateEmbyInfo(EmbyInfoUpdate embyUserUpdate);

   void enableEmbyServer(Long embyInfoId);

   void updateEmbyServerEnabled(Long embyInfoId, Integer enabled);

   List<EmbyInfoUserOptionResponse> listSelectableUsers(EmbyInfoUserOptionsRequest request);

   EmbyInfo getEmbyInfoEnabled();

   EmbyInfo getByApiKey(String apiKey);

   EmbyInfo getByServerId(String serverId);
}
