package com.una.embyhub.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.una.embyhub.model.entity.UserPoints;

public interface UserPointsService extends IService<UserPoints> {
   void addPoints(Long userId, Integer points, String recordType, String description);

   void deductPoints(Long userId, Integer points, String recordType, String description);

   UserPoints getMyPoints();
}
