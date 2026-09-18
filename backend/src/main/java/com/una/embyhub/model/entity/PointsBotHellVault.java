package com.una.embyhub.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDate;
import lombok.Generated;

@TableName("points_bot_hell_vault")
public class PointsBotHellVault extends BaseEntity implements Serializable {
   private static final long serialVersionUID = 1L;
   @TableId(
      value = "id",
      type = IdType.AUTO
   )
   private Long id;
   @TableField("chat_id")
   private Long chatId;
   @TableField("vault_points")
   private Integer vaultPoints;
   @TableField("total_subsidy_points")
   private Integer totalSubsidyPoints;
   @TableField("total_inflow_points")
   private Long totalInflowPoints;
   @TableField("total_outflow_points")
   private Long totalOutflowPoints;
   @TableField("daily_outflow_points")
   private Integer dailyOutflowPoints;
   @TableField("daily_outflow_date")
   private LocalDate dailyOutflowDate;
   @TableField("initialized")
   private Boolean initialized;

   @Generated
   public Long getId() {
      return this.id;
   }

   @Generated
   public Long getChatId() {
      return this.chatId;
   }

   @Generated
   public Integer getVaultPoints() {
      return this.vaultPoints;
   }

   @Generated
   public Integer getTotalSubsidyPoints() {
      return this.totalSubsidyPoints;
   }

   @Generated
   public Long getTotalInflowPoints() {
      return this.totalInflowPoints;
   }

   @Generated
   public Long getTotalOutflowPoints() {
      return this.totalOutflowPoints;
   }

   @Generated
   public Integer getDailyOutflowPoints() {
      return this.dailyOutflowPoints;
   }

   @Generated
   public LocalDate getDailyOutflowDate() {
      return this.dailyOutflowDate;
   }

   @Generated
   public Boolean getInitialized() {
      return this.initialized;
   }

   @Generated
   public void setId(final Long id) {
      this.id = id;
   }

   @Generated
   public void setChatId(final Long chatId) {
      this.chatId = chatId;
   }

   @Generated
   public void setVaultPoints(final Integer vaultPoints) {
      this.vaultPoints = vaultPoints;
   }

   @Generated
   public void setTotalSubsidyPoints(final Integer totalSubsidyPoints) {
      this.totalSubsidyPoints = totalSubsidyPoints;
   }

   @Generated
   public void setTotalInflowPoints(final Long totalInflowPoints) {
      this.totalInflowPoints = totalInflowPoints;
   }

   @Generated
   public void setTotalOutflowPoints(final Long totalOutflowPoints) {
      this.totalOutflowPoints = totalOutflowPoints;
   }

   @Generated
   public void setDailyOutflowPoints(final Integer dailyOutflowPoints) {
      this.dailyOutflowPoints = dailyOutflowPoints;
   }

   @Generated
   public void setDailyOutflowDate(final LocalDate dailyOutflowDate) {
      this.dailyOutflowDate = dailyOutflowDate;
   }

   @Generated
   public void setInitialized(final Boolean initialized) {
      this.initialized = initialized;
   }

   @Generated
   @Override
   public String toString() {
      return "PointsBotHellVault(id="
         + this.getId()
         + ", chatId="
         + this.getChatId()
         + ", vaultPoints="
         + this.getVaultPoints()
         + ", totalSubsidyPoints="
         + this.getTotalSubsidyPoints()
         + ", totalInflowPoints="
         + this.getTotalInflowPoints()
         + ", totalOutflowPoints="
         + this.getTotalOutflowPoints()
         + ", dailyOutflowPoints="
         + this.getDailyOutflowPoints()
         + ", dailyOutflowDate="
         + this.getDailyOutflowDate()
         + ", initialized="
         + this.getInitialized()
         + ")";
   }

   @Generated
   @Override
   public boolean equals(final Object o) {
      if (o == this) {
         return true;
      } else if (!(o instanceof PointsBotHellVault other)) {
         return false;
      } else if (!other.canEqual(this)) {
         return false;
      } else if (!super.equals(o)) {
         return false;
      } else {
         Object this$id = this.getId();
         Object other$id = other.getId();
         if (this$id == null ? other$id == null : this$id.equals(other$id)) {
            Object this$chatId = this.getChatId();
            Object other$chatId = other.getChatId();
            if (this$chatId == null ? other$chatId == null : this$chatId.equals(other$chatId)) {
               Object this$vaultPoints = this.getVaultPoints();
               Object other$vaultPoints = other.getVaultPoints();
               if (this$vaultPoints == null ? other$vaultPoints == null : this$vaultPoints.equals(other$vaultPoints)) {
                  Object this$totalSubsidyPoints = this.getTotalSubsidyPoints();
                  Object other$totalSubsidyPoints = other.getTotalSubsidyPoints();
                  if (this$totalSubsidyPoints == null ? other$totalSubsidyPoints == null : this$totalSubsidyPoints.equals(other$totalSubsidyPoints)) {
                     Object this$totalInflowPoints = this.getTotalInflowPoints();
                     Object other$totalInflowPoints = other.getTotalInflowPoints();
                     if (this$totalInflowPoints == null ? other$totalInflowPoints == null : this$totalInflowPoints.equals(other$totalInflowPoints)) {
                        Object this$totalOutflowPoints = this.getTotalOutflowPoints();
                        Object other$totalOutflowPoints = other.getTotalOutflowPoints();
                        if (this$totalOutflowPoints == null ? other$totalOutflowPoints == null : this$totalOutflowPoints.equals(other$totalOutflowPoints)) {
                           Object this$dailyOutflowPoints = this.getDailyOutflowPoints();
                           Object other$dailyOutflowPoints = other.getDailyOutflowPoints();
                           if (this$dailyOutflowPoints == null ? other$dailyOutflowPoints == null : this$dailyOutflowPoints.equals(other$dailyOutflowPoints)) {
                              Object this$initialized = this.getInitialized();
                              Object other$initialized = other.getInitialized();
                              if (this$initialized == null ? other$initialized == null : this$initialized.equals(other$initialized)) {
                                 Object this$dailyOutflowDate = this.getDailyOutflowDate();
                                 Object other$dailyOutflowDate = other.getDailyOutflowDate();
                                 return this$dailyOutflowDate == null ? other$dailyOutflowDate == null : this$dailyOutflowDate.equals(other$dailyOutflowDate);
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
   @Override
   protected boolean canEqual(final Object other) {
      return other instanceof PointsBotHellVault;
   }

   @Generated
   @Override
   public int hashCode() {
      int PRIME = 59;
      int result = super.hashCode();
      Object $id = this.getId();
      result = result * 59 + ($id == null ? 43 : $id.hashCode());
      Object $chatId = this.getChatId();
      result = result * 59 + ($chatId == null ? 43 : $chatId.hashCode());
      Object $vaultPoints = this.getVaultPoints();
      result = result * 59 + ($vaultPoints == null ? 43 : $vaultPoints.hashCode());
      Object $totalSubsidyPoints = this.getTotalSubsidyPoints();
      result = result * 59 + ($totalSubsidyPoints == null ? 43 : $totalSubsidyPoints.hashCode());
      Object $totalInflowPoints = this.getTotalInflowPoints();
      result = result * 59 + ($totalInflowPoints == null ? 43 : $totalInflowPoints.hashCode());
      Object $totalOutflowPoints = this.getTotalOutflowPoints();
      result = result * 59 + ($totalOutflowPoints == null ? 43 : $totalOutflowPoints.hashCode());
      Object $dailyOutflowPoints = this.getDailyOutflowPoints();
      result = result * 59 + ($dailyOutflowPoints == null ? 43 : $dailyOutflowPoints.hashCode());
      Object $initialized = this.getInitialized();
      result = result * 59 + ($initialized == null ? 43 : $initialized.hashCode());
      Object $dailyOutflowDate = this.getDailyOutflowDate();
      return result * 59 + ($dailyOutflowDate == null ? 43 : $dailyOutflowDate.hashCode());
   }
}
