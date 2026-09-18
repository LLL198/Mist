package com.una.embyhub.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.diboot.core.binding.QueryBuilder;
import com.una.embyhub.config.common.MpConvert;
import com.una.embyhub.config.mybatis.MybatisPlusPage;
import com.una.embyhub.mapper.PointsBotLedgerMapper;
import com.una.embyhub.mapper.PointsBotUserMapper;
import com.una.embyhub.model.dto.request.pointsbot.PointsBotLedgerRequest;
import com.una.embyhub.model.dto.response.pointsbot.PointsBotLedgerResponse;
import com.una.embyhub.model.dto.response.pointsbot.PointsBotLedgerStatsResponse;
import com.una.embyhub.model.entity.PointsBotLedger;
import com.una.embyhub.model.entity.PointsBotUser;
import com.una.embyhub.service.PointsBotLedgerManageService;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

@Service
@Transactional(
   rollbackFor = {Exception.class}
)
public class PointsBotLedgerManageServiceImpl extends ServiceImpl<PointsBotLedgerMapper, PointsBotLedger> implements PointsBotLedgerManageService {
   private final PointsBotUserMapper pointsBotUserMapper;

   public PointsBotLedgerManageServiceImpl(PointsBotUserMapper pointsBotUserMapper) {
      this.pointsBotUserMapper = pointsBotUserMapper;
   }

   @Override
   public Page<PointsBotLedgerResponse> select(MybatisPlusPage<PointsBotLedgerRequest> page) {
      QueryWrapper queryWrapper = QueryBuilder.toQueryWrapper(page.getObject());
      queryWrapper.orderByDesc("id");
      Page<PointsBotLedgerResponse> result = MpConvert.page(
         queryWrapper, this.getBaseMapper(), PointsBotLedgerResponse.class, page.getCurrent(), page.getSize(), page.getOrders()
      );
      this.attachTransferParties(result.getRecords());
      result.getRecords().forEach(record -> {
         if (record.getReason() != null) {
            record.setReason(this.translateReason(record.getReason()));
         }
      });
      return result;
   }

   void attachTransferParties(List<PointsBotLedgerResponse> records) {
      if (!CollectionUtils.isEmpty(records)) {
         List<PointsBotLedgerResponse> transferRecords = records.stream()
            .filter(this::isTransferRecord)
            .filter(record -> record.getChatId() != null && record.getUserId() != null)
            .filter(record -> this.transferUserId(record.getRefId()) != null)
            .toList();
         if (!transferRecords.isEmpty()) {
            transferRecords.forEach(record -> {
               Long relatedUserId = this.transferUserId(record.getRefId());
               boolean subjectIsSender = record.getDelta() != null ? record.getDelta() < 0 : this.isTransferOutReason(record.getReason());
               if (subjectIsSender) {
                  record.setTransferFromUserId(record.getUserId());
                  record.setTransferToUserId(relatedUserId);
               } else {
                  record.setTransferFromUserId(relatedUserId);
                  record.setTransferToUserId(record.getUserId());
               }
            });
            Set<Long> chatIds = transferRecords.stream().map(PointsBotLedgerResponse::getChatId).filter(Objects::nonNull).collect(Collectors.toSet());
            Set<Long> userIds = transferRecords.stream()
               .flatMap(record -> Stream.of(record.getTransferFromUserId(), record.getTransferToUserId()))
               .filter(Objects::nonNull)
               .collect(Collectors.toSet());
            if (!chatIds.isEmpty() && !userIds.isEmpty()) {
               QueryWrapper<PointsBotUser> userQuery = new QueryWrapper<>();
               userQuery.select(new String[]{"chat_id", "user_id", "username", "display_name"})
                  .in("chat_id", chatIds)
                  .in("user_id", userIds)
                  .eq("del_flag", Integer.valueOf(0));
               List<PointsBotUser> users = this.pointsBotUserMapper.selectList(userQuery);
               Map<PointsBotLedgerManageServiceImpl.TelegramUserKey, PointsBotUser> userMap = users.stream()
                  .filter(user -> user.getChatId() != null && user.getUserId() != null)
                  .collect(
                     Collectors.toMap(
                        user -> new PointsBotLedgerManageServiceImpl.TelegramUserKey(user.getChatId(), user.getUserId()),
                        user -> (PointsBotUser)user,
                        (first, second) -> first
                     )
                  );
               transferRecords.forEach(
                  record -> {
                     PointsBotUser fromUser = userMap.get(
                        new PointsBotLedgerManageServiceImpl.TelegramUserKey(record.getChatId(), record.getTransferFromUserId())
                     );
                     if (fromUser != null) {
                        record.setTransferFromUsername(fromUser.getUsername());
                        record.setTransferFromDisplayName(fromUser.getDisplayName());
                     }

                     PointsBotUser toUser = userMap.get(new PointsBotLedgerManageServiceImpl.TelegramUserKey(record.getChatId(), record.getTransferToUserId()));
                     if (toUser != null) {
                        record.setTransferToUsername(toUser.getUsername());
                        record.setTransferToDisplayName(toUser.getDisplayName());
                     }
                  }
               );
            }
         }
      }
   }

   private boolean isTransferRecord(PointsBotLedgerResponse record) {
      if (record != null && record.getReason() != null) {
         String reason = record.getReason().trim();
         return "transfer_in".equalsIgnoreCase(reason) || "transfer_out".equalsIgnoreCase(reason) || "转入".equals(reason) || "转出".equals(reason);
      } else {
         return false;
      }
   }

   private boolean isTransferOutReason(String reason) {
      return reason != null && ("transfer_out".equalsIgnoreCase(reason.trim()) || "转出".equals(reason.trim()));
   }

   private Long transferUserId(String refId) {
      if (refId != null && !refId.isBlank()) {
         try {
            return Long.valueOf(refId.trim());
         } catch (NumberFormatException var3) {
            return null;
         }
      } else {
         return null;
      }
   }

   @Override
   public PointsBotLedgerStatsResponse getStats() {
      PointsBotLedgerStatsResponse stats = new PointsBotLedgerStatsResponse();
      QueryWrapper<PointsBotLedger> summaryWrapper = new QueryWrapper<>();
      summaryWrapper.select(
         new String[]{
            "COUNT(*) AS totalRecords",
            "COALESCE(SUM(CASE WHEN delta > 0 THEN delta ELSE 0 END), 0) AS totalIncome",
            "COALESCE(SUM(CASE WHEN delta < 0 THEN -delta ELSE 0 END), 0) AS totalExpense"
         }
      );
      summaryWrapper.eq("del_flag", Integer.valueOf(0));
      Map<String, Object> summaryRow = this.getBaseMapper().selectMaps(summaryWrapper).stream().findFirst().orElseGet(HashMap::new);
      long totalRecords = this.toLong(summaryRow.get("totalRecords"));
      long totalIncome = this.toLong(summaryRow.get("totalIncome"));
      long totalExpense = this.toLong(summaryRow.get("totalExpense"));
      stats.setTotalRecords(totalRecords);
      stats.setTotalIncome(totalIncome);
      stats.setTotalExpense(totalExpense);
      stats.setNetChange(totalIncome - totalExpense);
      QueryWrapper<PointsBotLedger> reasonWrapper = new QueryWrapper<>();
      reasonWrapper.select(new String[]{"reason", "COUNT(*) AS reasonCount"});
      reasonWrapper.eq("del_flag", Integer.valueOf(0));
      reasonWrapper.isNotNull("reason");
      reasonWrapper.groupBy("reason");
      List<Map<String, Object>> reasonRows = this.getBaseMapper().selectMaps(reasonWrapper);
      Map<String, Long> reasonStats = reasonRows.stream()
         .filter(row -> row.get("reason") != null)
         .collect(Collectors.toMap(row -> String.valueOf(row.get("reason")), row -> this.toLong(row.get("reasonCount")), Long::sum, HashMap::new));
      stats.setReasonStats(reasonStats);
      return stats;
   }

   @Override
   public String translateReason(String reason) {
      if (reason == null) {
         return "";
      } else {
         Map<String, String> mappings = this.getAllReasonMappings();
         String trimmed = reason.trim();
         String translated = mappings.get(trimmed);
         if (translated != null) {
            return translated;
         } else {
            translated = mappings.get(trimmed.toLowerCase());
            return translated != null ? translated : mappings.getOrDefault(trimmed.toUpperCase(), reason);
         }
      }
   }

   @Override
   public Map<String, String> getAllReasonMappings() {
      Map<String, String> mappings = new LinkedHashMap<>();
      mappings.put("checkin", "每日签到");
      mappings.put("message", "发言积分");
      mappings.put("redeem", "积分兑换");
      mappings.put("redeem_refund", "兑换退款");
      mappings.put("redeem_register", "积分注册");
      mappings.put("redeem_register_refund", "积分注册退款");
      mappings.put("redeem_renew", "积分续费");
      mappings.put("transfer_in", "转入");
      mappings.put("transfer_out", "转出");
      mappings.put("lottery_join", "参与抽奖");
      mappings.put("lottery_win", "抽奖中奖");
      mappings.put("renew", "续期账号");
      mappings.put("recharge", "积分续费");
      mappings.put("recharge_refund", "积分续费退款");
      mappings.put("web_redeem", "Web积分兑换");
      mappings.put("web_redeem_refund", "Web兑换退款");
      mappings.put("admin_adjust", "管理员调整");
      mappings.put("telegram_request_submit", "Telegram 求片扣分");
      mappings.put("telegram_request_refund", "Telegram 求片退款");
      mappings.put("foam_bag_grant", "雾袋到账");
      mappings.put("foam_bag_repay", "雾袋归还");
      mappings.put("foam_bag_overdue_wipe", "雾袋逾期归零");
      mappings.put("red_packet_hold", "红包积分托管");
      mappings.put("red_packet_claim", "领取积分红包");
      mappings.put("red_packet_refund", "红包到期退款");
      mappings.put("red_packet_cancel_refund", "红包取消退款");
      mappings.put("embyboss_migration", "EmbyBoss 迁移");
      mappings.put("DICE_BET", "骰子-下注");
      mappings.put("DICE_WIN", "骰子-盈利");
      mappings.put("DICE_DRAW", "骰子-平局");
      mappings.put("DICE_REFUND", "骰子-退款");
      mappings.put("21点-下注", "21点-下注");
      mappings.put("21点-盈利", "21点-盈利");
      mappings.put("21点-退款", "21点-退款");
      mappings.put("21点-加倍", "21点-加倍");
      mappings.put("21点-平局", "21点-平局");
      mappings.put("老虎机-下注", "老虎机-下注");
      mappings.put("老虎机-盈利", "老虎机-盈利");
      mappings.put("老虎机-大奖", "老虎机-大奖");
      mappings.put("老虎机-退款", "老虎机-退款");
      return mappings;
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

   private static record TelegramUserKey(Long chatId, Long userId) {
   }
}
