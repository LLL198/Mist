package com.una.embyhub.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.una.embyhub.config.mybatis.MybatisPlusPage;
import com.una.embyhub.model.dto.request.sysnotice.SysNoticePageRequest;
import com.una.embyhub.model.dto.response.sysnotice.SysNoticeResponse;
import com.una.embyhub.model.entity.SysNotice;
import java.util.List;

public interface SysNoticeService extends IService<SysNotice> {
   Page<SysNoticeResponse> select(MybatisPlusPage<SysNoticePageRequest> page);

   List<SysNoticeResponse> publicExternalList();

   List<SysNoticeResponse> siteList();

   Long unreadCount();

   Boolean markRead(Long noticeId);

   Boolean insert(SysNotice sysNotice);

   Boolean update(SysNotice sysNotice);

   Boolean delete(Long id);

   Boolean deleteBatch(List<Long> ids);
}
