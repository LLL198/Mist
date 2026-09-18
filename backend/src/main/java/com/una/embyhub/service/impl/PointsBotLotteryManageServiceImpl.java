package com.una.embyhub.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.diboot.core.binding.QueryBuilder;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.una.embyhub.config.common.MpConvert;
import com.una.embyhub.config.mybatis.MybatisPlusPage;
import com.una.embyhub.mapper.PointsBotLotteryEntryMapper;
import com.una.embyhub.mapper.PointsBotLotteryMapper;
import com.una.embyhub.model.dto.request.pointsbot.PointsBotLotteryRequest;
import com.una.embyhub.model.dto.response.pointsbot.PointsBotLotteryResponse;
import com.una.embyhub.model.dto.response.pointsbot.PointsBotLotteryStatsResponse;
import com.una.embyhub.model.entity.BaseEntity;
import com.una.embyhub.model.entity.PointsBotLottery;
import com.una.embyhub.model.entity.PointsBotLotteryEntry;
import com.una.embyhub.service.PointsBotLotteryManageService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Service
@Transactional(
   rollbackFor = {Exception.class}
)
public class PointsBotLotteryManageServiceImpl extends ServiceImpl<PointsBotLotteryMapper, PointsBotLottery> implements PointsBotLotteryManageService {
   @Autowired
   private ObjectMapper objectMapper;
   @Autowired
   private PointsBotLotteryEntryMapper pointsBotLotteryEntryMapper;

   @Override
   public Page<PointsBotLotteryResponse> select(MybatisPlusPage<PointsBotLotteryRequest> page) {
      QueryWrapper queryWrapper = QueryBuilder.toQueryWrapper(page.getObject());
      queryWrapper.orderByDesc("id");
      Page<PointsBotLotteryResponse> result = MpConvert.page(
         queryWrapper, this.getBaseMapper(), PointsBotLotteryResponse.class, page.getCurrent(), page.getSize(), page.getOrders()
      );
      if (result.getRecords() != null) {
         for (PointsBotLotteryResponse resp : result.getRecords()) {
            if (StringUtils.hasText(resp.getWinnersJson())) {
               try {
                  List<PointsBotLotteryResponse.WinnerInfo> winners = this.objectMapper
                     .readValue(resp.getWinnersJson(), new TypeReference<List<PointsBotLotteryResponse.WinnerInfo>>() {
                     });
                  resp.setWinners(winners);
                  resp.setWinnersJson(null);
               } catch (Exception var7) {
               }
            }
         }
      }

      return result;
   }

   @Override
   public PointsBotLotteryStatsResponse getStats() {
      PointsBotLotteryStatsResponse stats = new PointsBotLotteryStatsResponse();
      long totalLotteries = this.lambdaQuery().eq(BaseEntity::getDelFlag, Integer.valueOf(0)).count();
      stats.setTotalLotteries(totalLotteries);
      long openLotteries = this.lambdaQuery().eq(BaseEntity::getDelFlag, Integer.valueOf(0)).eq(PointsBotLottery::getStatus, "OPEN").count();
      stats.setOpenLotteries(openLotteries);
      long closedLotteries = this.lambdaQuery().eq(BaseEntity::getDelFlag, Integer.valueOf(0)).eq(PointsBotLottery::getStatus, "CLOSED").count();
      stats.setClosedLotteries(closedLotteries);
      long totalParticipants = this.pointsBotLotteryEntryMapper
         .selectCount((Wrapper<PointsBotLotteryEntry>)new QueryWrapper().eq("del_flag", Integer.valueOf(0)));
      stats.setTotalParticipants(totalParticipants);
      long lotteriesWithParticipants = this.lambdaQuery()
         .eq(BaseEntity::getDelFlag, Integer.valueOf(0))
         .inSql(PointsBotLottery::getId, "SELECT DISTINCT lottery_id FROM points_bot_lottery_entry WHERE del_flag = 0")
         .count();
      if (totalLotteries > 0L) {
         double rate = (double)lotteriesWithParticipants / (double)totalLotteries * 100.0;
         stats.setParticipationRate(String.format("%.0f%%", rate));
      } else {
         stats.setParticipationRate("0%");
      }

      return stats;
   }
}
