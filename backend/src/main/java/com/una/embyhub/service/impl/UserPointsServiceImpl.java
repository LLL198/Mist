package com.una.embyhub.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.una.embyhub.config.common.enums.ResponseStatusEnum;
import com.una.embyhub.config.common.exception.BizException;
import com.una.embyhub.mapper.UserPointsMapper;
import com.una.embyhub.model.entity.PointsRecord;
import com.una.embyhub.model.entity.UserPoints;
import com.una.embyhub.service.PointsRecordService;
import com.una.embyhub.service.UserPointsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserPointsServiceImpl extends ServiceImpl<UserPointsMapper, UserPoints> implements UserPointsService {
   @Autowired
   private PointsRecordService pointsRecordService;

   @Transactional(
      rollbackFor = {Exception.class}
   )
   @Override
   public void addPoints(Long userId, Integer points, String recordType, String description) {
      if (points != null && points > 0) {
         UserPoints userPoints = this.getOne(new LambdaQueryWrapper<UserPoints>().eq(UserPoints::getUserId, userId));
         if (userPoints == null) {
            userPoints = new UserPoints();
            userPoints.setUserId(userId);
            userPoints.setPointsBalance(0);
            userPoints.setTotalEarned(0);
            userPoints.setTotalSpent(0);
            this.save(userPoints);
         }

         userPoints.setPointsBalance(userPoints.getPointsBalance() + points);
         userPoints.setTotalEarned(userPoints.getTotalEarned() + points);
         this.updateById(userPoints);
         PointsRecord record = new PointsRecord();
         record.setUserId(userId);
         record.setRecordType(recordType);
         record.setAmount(points);
         record.setBalanceAfter(userPoints.getPointsBalance());
         record.setDescription(description);
         this.pointsRecordService.save(record);
      }
   }

   @Transactional(
      rollbackFor = {Exception.class}
   )
   @Override
   public void deductPoints(Long userId, Integer points, String recordType, String description) {
      if (points != null && points > 0) {
         UserPoints userPoints = this.getOne(new LambdaQueryWrapper<UserPoints>().eq(UserPoints::getUserId, userId));
         if (userPoints != null && userPoints.getPointsBalance() >= points) {
            userPoints.setPointsBalance(userPoints.getPointsBalance() - points);
            userPoints.setTotalSpent(userPoints.getTotalSpent() + points);
            this.updateById(userPoints);
            PointsRecord record = new PointsRecord();
            record.setUserId(userId);
            record.setRecordType(recordType);
            record.setAmount(-points);
            record.setBalanceAfter(userPoints.getPointsBalance());
            record.setDescription(description);
            this.pointsRecordService.save(record);
         } else {
            throw new BizException(ResponseStatusEnum.POINTS_NOT_ENOUGH);
         }
      }
   }

   @Override
   public UserPoints getMyPoints() {
      Long userId = StpUtil.getLoginIdAsLong();
      UserPoints userPoints = this.getOne(new LambdaQueryWrapper<UserPoints>().eq(UserPoints::getUserId, userId));
      if (userPoints == null) {
         userPoints = new UserPoints();
         userPoints.setUserId(userId);
         userPoints.setPointsBalance(0);
      }

      return userPoints;
   }
}
