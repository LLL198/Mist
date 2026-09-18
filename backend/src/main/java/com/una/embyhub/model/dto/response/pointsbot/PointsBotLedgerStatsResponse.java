package com.una.embyhub.model.dto.response.pointsbot;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.Map;
import lombok.Generated;

@Schema(
   description = "积分机器人积分流水统计信息"
)
public class PointsBotLedgerStatsResponse {
   @Schema(
      description = "总流水记录数"
   )
   private Long totalRecords;
   @Schema(
      description = "总收入积分数（delta > 0）"
   )
   private Long totalIncome;
   @Schema(
      description = "总支出积分数（delta < 0，绝对值）"
   )
   private Long totalExpense;
   @Schema(
      description = "净积分变化"
   )
   private Long netChange;
   @Schema(
      description = "各类型流水统计（reason -> count）"
   )
   private Map<String, Long> reasonStats;

   @Generated
   public Long getTotalRecords() {
      return this.totalRecords;
   }

   @Generated
   public Long getTotalIncome() {
      return this.totalIncome;
   }

   @Generated
   public Long getTotalExpense() {
      return this.totalExpense;
   }

   @Generated
   public Long getNetChange() {
      return this.netChange;
   }

   @Generated
   public Map<String, Long> getReasonStats() {
      return this.reasonStats;
   }

   @Generated
   public void setTotalRecords(final Long totalRecords) {
      this.totalRecords = totalRecords;
   }

   @Generated
   public void setTotalIncome(final Long totalIncome) {
      this.totalIncome = totalIncome;
   }

   @Generated
   public void setTotalExpense(final Long totalExpense) {
      this.totalExpense = totalExpense;
   }

   @Generated
   public void setNetChange(final Long netChange) {
      this.netChange = netChange;
   }

   @Generated
   public void setReasonStats(final Map<String, Long> reasonStats) {
      this.reasonStats = reasonStats;
   }

   @Generated
   @Override
   public boolean equals(final Object o) {
      if (o == this) {
         return true;
      } else if (!(o instanceof PointsBotLedgerStatsResponse other)) {
         return false;
      } else if (!other.canEqual(this)) {
         return false;
      } else {
         Object this$totalRecords = this.getTotalRecords();
         Object other$totalRecords = other.getTotalRecords();
         if (this$totalRecords == null ? other$totalRecords == null : this$totalRecords.equals(other$totalRecords)) {
            Object this$totalIncome = this.getTotalIncome();
            Object other$totalIncome = other.getTotalIncome();
            if (this$totalIncome == null ? other$totalIncome == null : this$totalIncome.equals(other$totalIncome)) {
               Object this$totalExpense = this.getTotalExpense();
               Object other$totalExpense = other.getTotalExpense();
               if (this$totalExpense == null ? other$totalExpense == null : this$totalExpense.equals(other$totalExpense)) {
                  Object this$netChange = this.getNetChange();
                  Object other$netChange = other.getNetChange();
                  if (this$netChange == null ? other$netChange == null : this$netChange.equals(other$netChange)) {
                     Object this$reasonStats = this.getReasonStats();
                     Object other$reasonStats = other.getReasonStats();
                     return this$reasonStats == null ? other$reasonStats == null : this$reasonStats.equals(other$reasonStats);
                  } else {
                     return false;
                  }
               } else {
                  return false;
               }
            } else {
               return false;
            }
         } else {
            return false;
         }
      }
   }

   @Generated
   protected boolean canEqual(final Object other) {
      return other instanceof PointsBotLedgerStatsResponse;
   }

   @Generated
   @Override
   public int hashCode() {
      int PRIME = 59;
      int result = 1;
      Object $totalRecords = this.getTotalRecords();
      result = result * 59 + ($totalRecords == null ? 43 : $totalRecords.hashCode());
      Object $totalIncome = this.getTotalIncome();
      result = result * 59 + ($totalIncome == null ? 43 : $totalIncome.hashCode());
      Object $totalExpense = this.getTotalExpense();
      result = result * 59 + ($totalExpense == null ? 43 : $totalExpense.hashCode());
      Object $netChange = this.getNetChange();
      result = result * 59 + ($netChange == null ? 43 : $netChange.hashCode());
      Object $reasonStats = this.getReasonStats();
      return result * 59 + ($reasonStats == null ? 43 : $reasonStats.hashCode());
   }

   @Generated
   @Override
   public String toString() {
      return "PointsBotLedgerStatsResponse(totalRecords="
         + this.getTotalRecords()
         + ", totalIncome="
         + this.getTotalIncome()
         + ", totalExpense="
         + this.getTotalExpense()
         + ", netChange="
         + this.getNetChange()
         + ", reasonStats="
         + this.getReasonStats()
         + ")";
   }
}
