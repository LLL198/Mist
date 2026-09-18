package com.una.embyhub.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.una.embyhub.config.mybatis.MybatisPlusPage;
import com.una.embyhub.model.dto.request.hostline.HostLineRequest;
import com.una.embyhub.model.dto.request.hostline.HostLineSave;
import com.una.embyhub.model.dto.request.hostline.HostLineUpdate;
import com.una.embyhub.model.dto.response.hostline.HostLineResponse;
import com.una.embyhub.model.entity.EmbyUser;
import com.una.embyhub.model.entity.HostLine;
import java.util.List;

public interface HostLineService extends IService<HostLine> {
   Page<HostLineResponse> select(MybatisPlusPage<HostLineRequest> page);

   void insertHostLine(HostLineSave save);

   void updateHostLine(HostLineUpdate update);

   void deleteHostLine(List<Long> ids);

   List<HostLineResponse> listAvailableLines(Long embyInfoId);

   List<HostLineResponse> listUserAvailableLines(EmbyUser user);

   List<HostLineResponse> listCurrentUserLines();
}
