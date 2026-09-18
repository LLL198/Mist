package com.una.embyhub.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.diboot.core.binding.QueryBuilder;
import com.una.embyhub.config.common.MpConvert;
import com.una.embyhub.config.mybatis.MybatisPlusPage;
import com.una.embyhub.mapper.PointsBotLotteryEntryMapper;
import com.una.embyhub.mapper.PointsBotLotteryMapper;
import com.una.embyhub.model.dto.request.pointsbot.PointsBotLotteryEntryRequest;
import com.una.embyhub.model.dto.response.pointsbot.PointsBotLotteryEntryResponse;
import com.una.embyhub.model.dto.response.pointsbot.PointsBotLotteryEntryStatsResponse;
import com.una.embyhub.model.entity.BaseEntity;
import com.una.embyhub.model.entity.PointsBotLottery;
import com.una.embyhub.model.entity.PointsBotLotteryEntry;
import com.una.embyhub.service.PointsBotLotteryEntryManageService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(
   rollbackFor = {Exception.class}
)
public class PointsBotLotteryEntryManageServiceImpl
   extends ServiceImpl<PointsBotLotteryEntryMapper, PointsBotLotteryEntry>
   implements PointsBotLotteryEntryManageService {
   @Autowired
   private PointsBotLotteryMapper pointsBotLotteryMapper;

   @Override
   public Page<PointsBotLotteryEntryResponse> select(MybatisPlusPage<PointsBotLotteryEntryRequest> page) {
      QueryWrapper queryWrapper = QueryBuilder.toQueryWrapper(page.getObject());
      return MpConvert.page(queryWrapper, this.getBaseMapper(), PointsBotLotteryEntryResponse.class, page.getCurrent(), page.getSize(), page.getOrders());
   }

   @Override
   public PointsBotLotteryEntryStatsResponse getStats() {
      PointsBotLotteryEntryStatsResponse stats = new PointsBotLotteryEntryStatsResponse();
      long totalEntries = this.lambdaQuery().eq(BaseEntity::getDelFlag, Integer.valueOf(0)).count();
      stats.setTotalEntries(totalEntries);
      QueryWrapper<PointsBotLotteryEntry> uniqueUserWrapper = new QueryWrapper<>();
      uniqueUserWrapper.select(new String[]{"COUNT(DISTINCT user_id) AS uniqueUsers"});
      uniqueUserWrapper.eq("del_flag", Integer.valueOf(0));
      Map<String, Object> uniqueUserRow = this.getBaseMapper().selectMaps(uniqueUserWrapper).stream().findFirst().orElseGet(HashMap::new);
      stats.setUniqueUsers(this.toLong(uniqueUserRow.get("uniqueUsers")));
      QueryWrapper<PointsBotLotteryEntry> topUserWrapper = new QueryWrapper<>();
      topUserWrapper.select(new String[]{"user_id AS userId", "MAX(username) AS username", "MAX(display_name) AS displayName", "COUNT(*) AS entryCount"});
      topUserWrapper.eq("del_flag", Integer.valueOf(0));
      topUserWrapper.groupBy("user_id");
      topUserWrapper.orderByDesc("entryCount");
      topUserWrapper.last("LIMIT 5");
      List<Map<String, Object>> topUserRows = this.getBaseMapper().selectMaps(topUserWrapper);
      List<Map<String, Object>> topActiveUsers = topUserRows.stream().map(row -> {
         Map<String, Object> userInfo = new HashMap<>();
         userInfo.put("userId", this.toLong(row.get("userId")));
         userInfo.put("username", row.get("username"));
         userInfo.put("displayName", row.get("displayName"));
         userInfo.put("entryCount", this.toLong(row.get("entryCount")));
         return userInfo;
      }).collect(Collectors.toList());
      stats.setTopActiveUsers(topActiveUsers);
      long totalLotteries = this.pointsBotLotteryMapper.selectCount((Wrapper<PointsBotLottery>)new QueryWrapper().eq("del_flag", Integer.valueOf(0)));
      long lotteriesWithParticipants = this.pointsBotLotteryMapper
         .selectCount(
            (Wrapper<PointsBotLottery>)((QueryWrapper)new QueryWrapper().eq("del_flag", Integer.valueOf(0)))
               .inSql("id", "SELECT DISTINCT lottery_id FROM points_bot_lottery_entry WHERE del_flag = 0")
         );
      if (totalLotteries > 0L) {
         double rate = (double)lotteriesWithParticipants / (double)totalLotteries * 100.0;
         stats.setParticipationRate(String.format("%.0f%%", rate));
      } else {
         stats.setParticipationRate("0%");
      }

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
}
