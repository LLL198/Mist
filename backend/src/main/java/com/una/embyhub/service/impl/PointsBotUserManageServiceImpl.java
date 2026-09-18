package com.una.embyhub.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.diboot.core.binding.QueryBuilder;
import com.una.embyhub.config.common.MpConvert;
import com.una.embyhub.config.common.exception.BizException;
import com.una.embyhub.config.mybatis.MybatisPlusPage;
import com.una.embyhub.mapper.EmbyUserMapper;
import com.una.embyhub.mapper.PointsBotUserMapper;
import com.una.embyhub.mapper.UserOauthBindingMapper;
import com.una.embyhub.model.dto.request.pointsbot.PointsBotUserAdjustRequest;
import com.una.embyhub.model.dto.request.pointsbot.PointsBotUserRequest;
import com.una.embyhub.model.dto.response.pointsbot.PointsBotUserAdjustResponse;
import com.una.embyhub.model.dto.response.pointsbot.PointsBotUserResponse;
import com.una.embyhub.model.dto.response.pointsbot.PointsBotUserStatsResponse;
import com.una.embyhub.model.entity.BaseEntity;
import com.una.embyhub.model.entity.EmbyUser;
import com.una.embyhub.model.entity.PointsBotUser;
import com.una.embyhub.model.entity.UserOauthBinding;
import com.una.embyhub.pointsbot.model.PointsProfile;
import com.una.embyhub.pointsbot.service.PointsStore;
import com.una.embyhub.service.PointsBotUserManageService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

@Service
@Transactional(
   rollbackFor = {Exception.class}
)
public class PointsBotUserManageServiceImpl extends ServiceImpl<PointsBotUserMapper, PointsBotUser> implements PointsBotUserManageService {
   private final UserOauthBindingMapper userOauthBindingMapper;
   private final EmbyUserMapper embyUserMapper;
   private final PointsStore pointsStore;

   public PointsBotUserManageServiceImpl(UserOauthBindingMapper userOauthBindingMapper, EmbyUserMapper embyUserMapper, PointsStore pointsStore) {
      this.userOauthBindingMapper = userOauthBindingMapper;
      this.embyUserMapper = embyUserMapper;
      this.pointsStore = pointsStore;
   }

   @Override
   public Page<PointsBotUserResponse> select(MybatisPlusPage<PointsBotUserRequest> page) {
      QueryWrapper queryWrapper = QueryBuilder.toQueryWrapper(page.getObject());
      this.applyEmbyUserNameFilter(queryWrapper, page.getObject());
      Page<PointsBotUserResponse> result = MpConvert.page(
         queryWrapper, this.getBaseMapper(), PointsBotUserResponse.class, page.getCurrent(), page.getSize(), page.getOrders()
      );
      this.attachEmbyUserNames(result.getRecords());
      return result;
   }

   @Override
   public PointsBotUserAdjustResponse adjustPoints(PointsBotUserAdjustRequest request) {
      if (request == null || request.getId() == null) {
         throw new BizException("积分用户ID不能为空");
      } else if (request.getDelta() == null || request.getDelta() == 0) {
         throw new BizException("积分调整值不能为0");
      } else if (!StringUtils.hasText(request.getRemark())) {
         throw new BizException("请填写调整说明");
      } else {
         PointsBotUser user = this.getBaseMapper()
            .selectOne(
               Wrappers.<PointsBotUser>lambdaQuery().eq(PointsBotUser::getId, request.getId())
                  .eq(BaseEntity::getDelFlag, Integer.valueOf(0))
                  .last("FOR UPDATE")
            );
         if (user == null) {
            throw new BizException("积分用户不存在或已删除");
         } else {
            long beforePoints = user.getPoints() == null ? 0L : user.getPoints();
            long afterPoints = beforePoints + (long)request.getDelta().intValue();
            if (afterPoints < 0L) {
               throw new BizException("扣除积分不能超过用户当前积分");
            } else {
               PointsProfile profile = this.pointsStore.findByUserId(user.getChatId(), user.getUserId());
               if (profile == null) {
                  throw new BizException("积分用户不存在或已删除");
               } else {
                  String remark = request.getRemark().trim();
                  int applied = this.pointsStore.addPoints(profile, request.getDelta(), "admin_adjust", remark);
                  if (applied != request.getDelta()) {
                     throw new BizException("积分调整失败，请刷新后重试");
                  } else {
                     return new PointsBotUserAdjustResponse(user.getId(), user.getUserId(), beforePoints, applied, profile.getPoints(), profile.getLevelName());
                  }
               }
            }
         }
      }
   }

   private void applyEmbyUserNameFilter(QueryWrapper queryWrapper, PointsBotUserRequest request) {
      if (request != null && StringUtils.hasText(request.getEmbyUserName())) {
         queryWrapper.apply(
            "user_id IN (\n    SELECT CAST(uob.provider_user_id AS UNSIGNED)\n    FROM user_oauth_binding uob\n    INNER JOIN emby_user eu ON eu.id = uob.user_id\n    WHERE uob.provider = 'telegram'\n      AND uob.del_flag = 0\n      AND eu.del_flag = 0\n      AND eu.emby_user_name LIKE CONCAT({0}, '%') ESCAPE '\\\\'\n      AND uob.provider_user_id REGEXP '^[0-9]+$'\n)\n",
            new Object[]{this.escapeLikePrefix(request.getEmbyUserName().trim())}
         );
      }
   }

   private void attachEmbyUserNames(List<PointsBotUserResponse> records) {
      if (!CollectionUtils.isEmpty(records)) {
         List<String> telegramIds = records.stream()
            .map(PointsBotUserResponse::getUserId)
            .filter(Objects::nonNull)
            .map(String::valueOf)
            .distinct()
            .collect(Collectors.toList());
         if (!CollectionUtils.isEmpty(telegramIds)) {
            List<UserOauthBinding> bindings = new LambdaQueryChainWrapper<>(this.userOauthBindingMapper)
               .select(UserOauthBinding::getUserId, UserOauthBinding::getProviderUserId)
               .eq(UserOauthBinding::getProvider, "telegram")
               .eq(BaseEntity::getDelFlag, Integer.valueOf(0))
               .in(UserOauthBinding::getProviderUserId, telegramIds)
               .list();
            if (!CollectionUtils.isEmpty(bindings)) {
               List<Long> embyUserIds = bindings.stream().map(UserOauthBinding::getUserId).filter(Objects::nonNull).distinct().collect(Collectors.toList());
               if (!CollectionUtils.isEmpty(embyUserIds)) {
                  List<EmbyUser> embyUsers = new LambdaQueryChainWrapper<>(this.embyUserMapper)
                     .select(EmbyUser::getId, EmbyUser::getEmbyUserName)
                     .eq(BaseEntity::getDelFlag, Integer.valueOf(0))
                     .in(EmbyUser::getId, embyUserIds)
                     .list();
                  Map<Long, String> embyUserNameMap = embyUsers.stream()
                     .filter(user -> user.getId() != null)
                     .collect(Collectors.toMap(EmbyUser::getId, EmbyUser::getEmbyUserName, (first, second) -> first));
                  Map<String, String> telegramEmbyNameMap = bindings.stream()
                     .filter(binding -> StringUtils.hasText(binding.getProviderUserId()))
                     .filter(binding -> embyUserNameMap.containsKey(binding.getUserId()))
                     .collect(
                        Collectors.toMap(UserOauthBinding::getProviderUserId, binding -> embyUserNameMap.get(binding.getUserId()), (first, second) -> first)
                     );
                  records.forEach(record -> {
                     if (record.getUserId() != null) {
                        record.setEmbyUserName(telegramEmbyNameMap.get(String.valueOf(record.getUserId())));
                     }
                  });
               }
            }
         }
      }
   }

   private String escapeLikePrefix(String value) {
      return value.replace("\\", "\\\\").replace("%", "\\%").replace("_", "\\_");
   }

   @Override
   public PointsBotUserStatsResponse getStats() {
      PointsBotUserStatsResponse stats = new PointsBotUserStatsResponse();
      QueryWrapper<PointsBotUser> summaryWrapper = new QueryWrapper<>();
      summaryWrapper.select(
         new String[]{
            "COUNT(*) AS totalUsers",
            "COALESCE(SUM(points), 0) AS totalPoints",
            "COALESCE(AVG(points), 0) AS avgPoints",
            "COALESCE(MAX(points), 0) AS maxPoints",
            "COALESCE(MIN(points), 0) AS minPoints",
            "COALESCE(SUM(CASE WHEN COALESCE(last_message_date, last_checkin_date) > DATE_SUB(CURDATE(), INTERVAL 7 DAY) THEN 1 ELSE 0 END), 0) AS activeUsers",
            "COALESCE(SUM(CASE WHEN IFNULL(checkin_streak, 0) > 0 THEN 1 ELSE 0 END), 0) AS streakUsers"
         }
      );
      summaryWrapper.eq("del_flag", Integer.valueOf(0));
      Map<String, Object> row = this.getBaseMapper().selectMaps(summaryWrapper).stream().findFirst().orElseGet(HashMap::new);
      stats.setTotalUsers(this.toLong(row.get("totalUsers")));
      stats.setTotalPoints(this.toLong(row.get("totalPoints")));
      stats.setAvgPoints(this.toDouble(row.get("avgPoints")));
      stats.setMaxPoints(this.toLong(row.get("maxPoints")));
      stats.setMinPoints(this.toLong(row.get("minPoints")));
      stats.setActiveUsers(this.toLong(row.get("activeUsers")));
      stats.setStreakUsers(this.toLong(row.get("streakUsers")));
      return stats;
   }

   private long toLong(Object value) {
      if (value == null) {
         return 0L;
      } else if (value instanceof Number number) {
         return number.longValue();
      } else {
         try {
            return Long.parseLong(value.toString());
         } catch (Exception var3) {
            return 0L;
         }
      }
   }

   private double toDouble(Object value) {
      if (value == null) {
         return 0.0;
      } else if (value instanceof Number number) {
         return number.doubleValue();
      } else {
         try {
            return Double.parseDouble(value.toString());
         } catch (Exception var3) {
            return 0.0;
         }
      }
   }
}
