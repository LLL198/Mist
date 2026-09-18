package com.una.embyhub.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.diboot.core.util.BeanUtils;
import com.una.embyhub.config.common.MpConvert;
import com.una.embyhub.config.common.enums.HostLineTypeEnum;
import com.una.embyhub.config.common.enums.ResponseStatusEnum;
import com.una.embyhub.config.common.exception.BizException;
import com.una.embyhub.config.mybatis.MybatisPlusPage;
import com.una.embyhub.mapper.HostLineMapper;
import com.una.embyhub.model.dto.request.hostline.HostLineRequest;
import com.una.embyhub.model.dto.request.hostline.HostLineSave;
import com.una.embyhub.model.dto.request.hostline.HostLineUpdate;
import com.una.embyhub.model.dto.response.hostline.HostLineResponse;
import com.una.embyhub.model.entity.EmbyInfo;
import com.una.embyhub.model.entity.EmbyUser;
import com.una.embyhub.model.entity.HostLine;
import com.una.embyhub.service.EmbyInfoService;
import com.una.embyhub.service.HostLineService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Service
@Transactional(
   rollbackFor = {Exception.class}
)
public class HostLineServiceImpl extends ServiceImpl<HostLineMapper, HostLine> implements HostLineService {
   @Autowired
   private EmbyInfoService embyInfoService;

   @Override
   public Page<HostLineResponse> select(MybatisPlusPage<HostLineRequest> page) {
      QueryWrapper<HostLine> queryWrapper = new QueryWrapper<>();
      HostLineRequest request = page.getObject();
      if (request != null) {
         if (request.getEmbyInfoId() != null) {
            queryWrapper.eq("emby_info_id", request.getEmbyInfoId());
         }

         if (StringUtils.hasText(request.getLineName())) {
            queryWrapper.like("line_name", request.getLineName());
         }

         if (request.getLineType() != null) {
            queryWrapper.eq("line_type", Integer.valueOf(HostLineTypeEnum.normalize(request.getLineType())));
         }

         if (request.getIsDisplay() != null) {
            queryWrapper.eq("is_display", request.getIsDisplay());
         }

         if (request.getEnabled() != null) {
            queryWrapper.eq("enabled", request.getEnabled());
         }
      }

      queryWrapper.orderByAsc("sort_no", new String[]{"id"});
      return MpConvert.page(queryWrapper, this.getBaseMapper(), HostLineResponse.class, page.getCurrent(), page.getSize(), page.getOrders());
   }

   @Override
   public void insertHostLine(HostLineSave save) {
      this.validateRequiredFields(save.getEmbyInfoId(), save.getLineName(), save.getProtocol(), save.getDomain(), save.getPort());
      Integer lineType = HostLineTypeEnum.normalize(save.getLineType());
      this.assertUniqueLine(save.getEmbyInfoId(), save.getLineName(), save.getProtocol(), save.getDomain(), save.getPort(), lineType, null);
      HostLine hostLine = BeanUtils.convert(save, HostLine.class);
      hostLine.setLineType(lineType);
      this.save(hostLine);
   }

   @Override
   public void updateHostLine(HostLineUpdate update) {
      if (update.getId() == null) {
         throw new BizException(ResponseStatusEnum.HOST_LINE_NOT_FOUND);
      } else {
         HostLine exist = this.getById(update.getId());
         if (exist == null) {
            throw new BizException(ResponseStatusEnum.HOST_LINE_NOT_FOUND);
         } else {
            this.validateRequiredFields(update.getEmbyInfoId(), update.getLineName(), update.getProtocol(), update.getDomain(), update.getPort());
            Integer lineType = HostLineTypeEnum.normalize(update.getLineType());
            this.assertUniqueLine(
               update.getEmbyInfoId(), update.getLineName(), update.getProtocol(), update.getDomain(), update.getPort(), lineType, update.getId()
            );
            HostLine hostLine = BeanUtils.convert(update, HostLine.class);
            hostLine.setLineType(lineType);
            this.updateById(hostLine);
         }
      }
   }

   @Override
   public void deleteHostLine(List<Long> ids) {
      if (ids != null && !ids.isEmpty()) {
         this.removeByIds(ids);
      } else {
         throw new BizException(ResponseStatusEnum.HOST_LINE_NOT_FOUND);
      }
   }

   @Override
   public List<HostLineResponse> listAvailableLines(Long embyInfoId) {
      return this.listAvailableLines(embyInfoId, false);
   }

   @Override
   public List<HostLineResponse> listUserAvailableLines(EmbyUser user) {
      if (user == null) {
         throw new BizException(ResponseStatusEnum.FORBIDDEN);
      } else {
         Long embyInfoId = user.getEmbyInfoId();
         return embyInfoId == null ? List.of() : this.listAvailableLines(embyInfoId, this.hasWhitelistLineAccess(user));
      }
   }

   private List<HostLineResponse> listAvailableLines(Long embyInfoId, boolean includeWhitelistLines) {
      this.assertEmbyInfoExists(embyInfoId);
      QueryWrapper<HostLine> queryWrapper = new QueryWrapper<>();
      queryWrapper.eq("emby_info_id", embyInfoId)
         .eq("enabled", Integer.valueOf(1))
         .eq("is_display", Integer.valueOf(1))
         .orderByAsc("sort_no", new String[]{"id"});
      if (includeWhitelistLines) {
         queryWrapper.in("line_type", new Object[]{HostLineTypeEnum.COMMON.getCode(), HostLineTypeEnum.WHITELIST.getCode()});
      } else {
         queryWrapper.eq("line_type", Integer.valueOf(HostLineTypeEnum.COMMON.getCode()));
      }

      List<HostLine> records = this.list(queryWrapper);
      return BeanUtils.convertList(records, HostLineResponse.class);
   }

   @Override
   public List<HostLineResponse> listCurrentUserLines() {
      EmbyUser currentUser = (EmbyUser)StpUtil.getSession().get("user");
      if (currentUser == null) {
         throw new BizException(ResponseStatusEnum.FORBIDDEN);
      } else {
         Long embyInfoId = currentUser.getEmbyInfoId();
         return embyInfoId == null ? List.of() : this.listUserAvailableLines(currentUser);
      }
   }

   private boolean hasWhitelistLineAccess(EmbyUser user) {
      if (user == null) {
         return false;
      } else {
         return Integer.valueOf(1).equals(user.getIsAdmin())
            ? true
            : HostLineTypeEnum.normalize(user.getHostLineType()) == HostLineTypeEnum.WHITELIST.getCode();
      }
   }

   private void validateRequiredFields(Long embyInfoId, String lineName, String protocol, String domain, Integer port) {
      this.assertEmbyInfoExists(embyInfoId);
      if (!StringUtils.hasText(lineName)) {
         throw new BizException(ResponseStatusEnum.HOST_LINE_LINE_NAME_EMPTY);
      } else if (!StringUtils.hasText(protocol)) {
         throw new BizException(ResponseStatusEnum.HOST_LINE_PROTOCOL_EMPTY);
      } else if (!StringUtils.hasText(domain)) {
         throw new BizException(ResponseStatusEnum.HOST_LINE_DOMAIN_EMPTY);
      } else if (port == null) {
         throw new BizException(ResponseStatusEnum.HOST_LINE_PORT_EMPTY);
      } else if (port < 1 || port > 65535) {
         throw new BizException(ResponseStatusEnum.HOST_LINE_PORT_INVALID);
      }
   }

   private EmbyInfo assertEmbyInfoExists(Long embyInfoId) {
      if (embyInfoId == null) {
         throw new BizException(ResponseStatusEnum.HOST_LINE_EMBY_INFO_ID_EMPTY);
      } else {
         EmbyInfo embyInfo = this.embyInfoService.getById(embyInfoId);
         if (embyInfo == null) {
            throw new BizException(ResponseStatusEnum.EMBY_SERVER_NOT_FOUND);
         } else {
            return embyInfo;
         }
      }
   }

   private void assertUniqueLine(Long embyInfoId, String lineName, String protocol, String domain, Integer port, Integer lineType, Long ignoreId) {
      QueryWrapper<HostLine> wrapper = new QueryWrapper<>();
      wrapper.eq("emby_info_id", embyInfoId)
         .eq("line_type", Integer.valueOf(HostLineTypeEnum.normalize(lineType)))
         .and(w -> w.eq("line_name", lineName).or(w2 -> w2.eq("protocol", protocol).eq("domain", domain).eq("port", port)));
      if (ignoreId != null) {
         wrapper.ne("id", ignoreId);
      }

      long count = this.count(wrapper);
      if (count > 0L) {
         throw new BizException(ResponseStatusEnum.HOST_LINE_DUPLICATE);
      }
   }
}
