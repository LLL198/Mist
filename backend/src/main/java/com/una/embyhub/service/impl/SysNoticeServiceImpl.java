package com.una.embyhub.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.diboot.core.binding.QueryBuilder;
import com.diboot.core.util.BeanUtils;
import com.una.embyhub.config.common.MpConvert;
import com.una.embyhub.config.common.enums.ResponseStatusEnum;
import com.una.embyhub.config.common.exception.BizException;
import com.una.embyhub.config.mybatis.MybatisPlusPage;
import com.una.embyhub.mapper.SysNoticeMapper;
import com.una.embyhub.mapper.SysNoticeReadMapper;
import com.una.embyhub.model.dto.request.sysnotice.SysNoticePageRequest;
import com.una.embyhub.model.dto.response.sysnotice.SysNoticeResponse;
import com.una.embyhub.model.entity.BaseEntity;
import com.una.embyhub.model.entity.EmbyUser;
import com.una.embyhub.model.entity.SysNotice;
import com.una.embyhub.model.entity.SysNoticeRead;
import com.una.embyhub.service.SysNoticeService;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.Generated;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(
   rollbackFor = {Exception.class}
)
public class SysNoticeServiceImpl extends ServiceImpl<SysNoticeMapper, SysNotice> implements SysNoticeService {
   private final SysNoticeReadMapper sysNoticeReadMapper;

   @Override
   public Page<SysNoticeResponse> select(MybatisPlusPage<SysNoticePageRequest> page) {
      QueryWrapper<SysNotice> queryWrapper = QueryBuilder.toQueryWrapper(page.getObject());
      queryWrapper.orderByDesc("top_flag").orderByDesc("sort").orderByDesc("create_datetime").orderByDesc("id");
      return MpConvert.page(queryWrapper, this.getBaseMapper(), SysNoticeResponse.class, page.getCurrent(), page.getSize(), page.getOrders());
   }

   @Override
   public Boolean insert(SysNotice sysNotice) {
      if (sysNotice.getNoticeStatus() == null) {
         sysNotice.setNoticeStatus(1);
      }

      if (sysNotice.getNoticeScope() == null) {
         sysNotice.setNoticeScope(0);
      }

      if (sysNotice.getTopFlag() == null) {
         sysNotice.setTopFlag(0);
      }

      if (sysNotice.getSort() == null) {
         sysNotice.setSort(0);
      }

      return this.save(sysNotice);
   }

   @Override
   public Boolean update(SysNotice sysNotice) {
      return this.updateById(sysNotice);
   }

   @Override
   public Boolean delete(Long id) {
      boolean removed = this.removeById(id);
      if (removed) {
         this.removeReadRecords(List.of(id));
      }

      return removed;
   }

   @Override
   public Boolean deleteBatch(List<Long> ids) {
      if (ids != null && !ids.isEmpty()) {
         boolean removed = this.removeByIds(ids);
         if (removed) {
            this.removeReadRecords(ids);
         }

         return removed;
      } else {
         return false;
      }
   }

   @Override
   public List<SysNoticeResponse> publicExternalList() {
      List<SysNotice> notices = new LambdaQueryChainWrapper<>(this.getBaseMapper())
         .eq(SysNotice::getNoticeStatus, Integer.valueOf(1))
         .eq(SysNotice::getNoticeScope, Integer.valueOf(1))
         .eq(SysNotice::getTopFlag, Integer.valueOf(1))
         .orderByDesc(SysNotice::getTopFlag)
         .orderByDesc(SysNotice::getSort)
         .orderByDesc(BaseEntity::getCreateDatetime)
         .orderByDesc(SysNotice::getId)
         .list();
      return BeanUtils.convertList(notices, SysNoticeResponse.class);
   }

   @Override
   public List<SysNoticeResponse> siteList() {
      EmbyUser user = this.currentUser();
      List<SysNotice> notices = this.siteNoticeQuery().list();
      if (notices.isEmpty()) {
         return List.of();
      } else {
         List<Long> noticeIds = notices.stream().map(SysNotice::getId).toList();
         Map<Long, Date> readTimeMap = new LambdaQueryChainWrapper<>(this.sysNoticeReadMapper)
            .eq(SysNoticeRead::getUserId, user.getId())
            .in(SysNoticeRead::getNoticeId, noticeIds)
            .list()
            .stream()
            .collect(Collectors.toMap(SysNoticeRead::getNoticeId, SysNoticeRead::getReadDatetime, (left, right) -> left));
         List<SysNoticeResponse> responses = BeanUtils.convertList(notices, SysNoticeResponse.class);
         responses.forEach(response -> {
            Date readDatetime = readTimeMap.get(response.getId());
            response.setReadFlag(readDatetime != null);
            response.setReadDatetime(readDatetime);
         });
         return responses;
      }
   }

   @Override
   public Long unreadCount() {
      EmbyUser user = this.currentUser();
      long total = this.siteNoticeQuery().count();
      if (total <= 0L) {
         return 0L;
      } else {
         long readCount = new LambdaQueryChainWrapper<>(this.sysNoticeReadMapper)
            .eq(SysNoticeRead::getUserId, user.getId())
            .inSql(SysNoticeRead::getNoticeId, "select id from sys_notice where del_flag = 0 and notice_status = 1 and notice_scope = 0")
            .count();
         return Math.max(0L, total - readCount);
      }
   }

   @Override
   public Boolean markRead(Long noticeId) {
      if (noticeId == null) {
         throw new BizException(ResponseStatusEnum.BAD_REQUEST.getCode(), "公告ID不能为空");
      } else {
         EmbyUser user = this.currentUser();
         long exists = this.lambdaQuery()
            .eq(SysNotice::getId, noticeId)
            .eq(SysNotice::getNoticeStatus, Integer.valueOf(1))
            .eq(SysNotice::getNoticeScope, Integer.valueOf(0))
            .count();
         if (exists <= 0L) {
            throw new BizException(ResponseStatusEnum.NOT_EXIST.getCode(), "公告不存在或未启用");
         } else {
            this.sysNoticeReadMapper.insertIgnore(noticeId, user.getId());
            return true;
         }
      }
   }

   private LambdaQueryChainWrapper<SysNotice> siteNoticeQuery() {
      return new LambdaQueryChainWrapper<>(this.getBaseMapper())
         .eq(SysNotice::getNoticeStatus, Integer.valueOf(1))
         .eq(SysNotice::getNoticeScope, Integer.valueOf(0))
         .orderByDesc(SysNotice::getTopFlag)
         .orderByDesc(SysNotice::getSort)
         .orderByDesc(BaseEntity::getCreateDatetime)
         .orderByDesc(SysNotice::getId);
   }

   private void removeReadRecords(List<Long> noticeIds) {
      if (noticeIds != null && !noticeIds.isEmpty()) {
         Set<Long> safeNoticeIds = noticeIds.stream().filter(id -> id != null).collect(Collectors.toSet());
         if (!safeNoticeIds.isEmpty()) {
            this.sysNoticeReadMapper.delete(new LambdaQueryWrapper<SysNoticeRead>().in(SysNoticeRead::getNoticeId, safeNoticeIds));
         }
      }
   }

   private EmbyUser currentUser() {
      Object userObj = StpUtil.getSession().get("user");
      if (userObj instanceof EmbyUser) {
         return (EmbyUser)userObj;
      } else {
         throw new BizException(ResponseStatusEnum.UNAUTHORIZED);
      }
   }

   @Generated
   public SysNoticeServiceImpl(final SysNoticeReadMapper sysNoticeReadMapper) {
      this.sysNoticeReadMapper = sysNoticeReadMapper;
   }
}
