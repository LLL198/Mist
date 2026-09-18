package com.una.embyhub.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.una.embyhub.config.mybatis.MybatisPlusPage;
import com.una.embyhub.model.dto.request.mediamain.MediaMainRequest;
import com.una.embyhub.model.dto.response.mediamain.MediaMainResponse;
import com.una.embyhub.model.entity.MediaMain;

public interface MediaMainService extends IService<MediaMain> {
   Page<MediaMainResponse> select(MybatisPlusPage<MediaMainRequest> page);
}
