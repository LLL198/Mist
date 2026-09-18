package com.una.embyhub.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.una.embyhub.config.mybatis.MybatisPlusPage;
import com.una.embyhub.mapper.PointsRecordMapper;
import com.una.embyhub.model.dto.request.pointsrecord.PointsRecordRequest;
import com.una.embyhub.model.dto.response.pointsrecord.PointsRecordResponse;
import com.una.embyhub.model.entity.PointsRecord;
import com.una.embyhub.service.PointsRecordService;
import org.springframework.stereotype.Service;

@Service
public class PointsRecordServiceImpl extends ServiceImpl<PointsRecordMapper, PointsRecord> implements PointsRecordService {
   @Override
   public Page<PointsRecordResponse> queryPageWithUserInfo(MybatisPlusPage<PointsRecordRequest> pageParam) {
      Page<PointsRecordResponse> page = new Page<>(pageParam.getCurrent(), pageParam.getSize());
      PointsRecordRequest request = pageParam.getObject();
      boolean isAdmin = StpUtil.hasPermission("admin");
      Long userId = null;
      String username = null;
      String recordType = null;
      if (request != null) {
         recordType = request.getRecordType();
         if (isAdmin) {
            username = request.getUsername();
         }
      }

      if (!isAdmin) {
         userId = StpUtil.getLoginIdAsLong();
      }

      Page<PointsRecordResponse> responsePage = this.baseMapper.selectPageWithUserInfo(page, userId, username, recordType);
      if (responsePage.getRecords() != null) {
         for (PointsRecordResponse record : responsePage.getRecords()) {
            if (record.getRecordType() != null) {
               String var11 = record.getRecordType();
               switch (var11) {
                  case "INVITE_REWARD":
                     record.setRecordTypeCn("邀请奖励");
                     break;
                  case "EXCHANGE_SPEND":
                     record.setRecordTypeCn("兑换消耗");
                     break;
                  case "ADMIN_ADJUST":
                     record.setRecordTypeCn("管理员调整");
                     break;
                  case "EXCHANGE_REFUND":
                     record.setRecordTypeCn("退还积分");
                     break;
                  default:
                     record.setRecordTypeCn("未知类型");
               }
            }
         }
      }

      return responsePage;
   }
}
