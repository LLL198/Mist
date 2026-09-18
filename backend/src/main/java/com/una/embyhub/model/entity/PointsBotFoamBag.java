package com.una.embyhub.model.entity;

import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Generated;

@TableName("points_bot_mist_bag")
public class PointsBotFoamBag extends BaseEntity implements Serializable {
   private static final long serialVersionUID = 1L;
   public static final String STATUS_ACTIVE = "ACTIVE";
   public static final String STATUS_REPAID = "REPAID";
   public static final String STATUS_PENALIZED = "PENALIZED";
   @TableId(
      value = "id",
      type = IdType.AUTO
   )
   private Long id;
   @TableField("chat_id")
   private Long chatId;
   @TableField("user_id")
   private Long userId;
   @TableField("username")
   private String username;
   @TableField("display_name")
   private String displayName;
   @TableField("principal_points")
   private Integer principalPoints;
   @TableField("repayment_points")
   private Integer repaymentPoints;
   @TableField("repayment_multiplier")
   private Integer repaymentMultiplier;
   @TableField("repayment_hours")
   private Integer repaymentHours;
   @TableField("penalty_days")
   private Integer penaltyDays;
   @TableField("status")
   private String status;
   @TableField(
      value = "active_guard",
      updateStrategy = FieldStrategy.ALWAYS
   )
   private Integer activeGuard;
   @TableField("borrowed_at")
   private LocalDateTime borrowedAt;
   @TableField("due_at")
   private LocalDateTime dueAt;
   @TableField("repaid_at")
   private LocalDateTime repaidAt;
   @TableField("penalized_at")
   private LocalDateTime penalizedAt;
   @TableField("penalty_until")
   private LocalDateTime penaltyUntil;
   @TableField("wiped_points")
   private Long wipedPoints;

   @Generated
   public Long getId() {
      return this.id;
   }

   @Generated
   public Long getChatId() {
      return this.chatId;
   }

   @Generated
   public Long getUserId() {
      return this.userId;
   }

   @Generated
   public String getUsername() {
      return this.username;
   }

   @Generated
   public String getDisplayName() {
      return this.displayName;
   }

   @Generated
   public Integer getPrincipalPoints() {
      return this.principalPoints;
   }

   @Generated
   public Integer getRepaymentPoints() {
      return this.repaymentPoints;
   }

   @Generated
   public Integer getRepaymentMultiplier() {
      return this.repaymentMultiplier;
   }

   @Generated
   public Integer getRepaymentHours() {
      return this.repaymentHours;
   }

   @Generated
   public Integer getPenaltyDays() {
      return this.penaltyDays;
   }

   @Generated
   public String getStatus() {
      return this.status;
   }

   @Generated
   public Integer getActiveGuard() {
      return this.activeGuard;
   }

   @Generated
   public LocalDateTime getBorrowedAt() {
      return this.borrowedAt;
   }

   @Generated
   public LocalDateTime getDueAt() {
      return this.dueAt;
   }

   @Generated
   public LocalDateTime getRepaidAt() {
      return this.repaidAt;
   }

   @Generated
   public LocalDateTime getPenalizedAt() {
      return this.penalizedAt;
   }

   @Generated
   public LocalDateTime getPenaltyUntil() {
      return this.penaltyUntil;
   }

   @Generated
   public Long getWipedPoints() {
      return this.wipedPoints;
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
   public void setUserId(final Long userId) {
      this.userId = userId;
   }

   @Generated
   public void setUsername(final String username) {
      this.username = username;
   }

   @Generated
   public void setDisplayName(final String displayName) {
      this.displayName = displayName;
   }

   @Generated
   public void setPrincipalPoints(final Integer principalPoints) {
      this.principalPoints = principalPoints;
   }

   @Generated
   public void setRepaymentPoints(final Integer repaymentPoints) {
      this.repaymentPoints = repaymentPoints;
   }

   @Generated
   public void setRepaymentMultiplier(final Integer repaymentMultiplier) {
      this.repaymentMultiplier = repaymentMultiplier;
   }

   @Generated
   public void setRepaymentHours(final Integer repaymentHours) {
      this.repaymentHours = repaymentHours;
   }

   @Generated
   public void setPenaltyDays(final Integer penaltyDays) {
      this.penaltyDays = penaltyDays;
   }

   @Generated
   public void setStatus(final String status) {
      this.status = status;
   }

   @Generated
   public void setActiveGuard(final Integer activeGuard) {
      this.activeGuard = activeGuard;
   }

   @Generated
   public void setBorrowedAt(final LocalDateTime borrowedAt) {
      this.borrowedAt = borrowedAt;
   }

   @Generated
   public void setDueAt(final LocalDateTime dueAt) {
      this.dueAt = dueAt;
   }

   @Generated
   public void setRepaidAt(final LocalDateTime repaidAt) {
      this.repaidAt = repaidAt;
   }

   @Generated
   public void setPenalizedAt(final LocalDateTime penalizedAt) {
      this.penalizedAt = penalizedAt;
   }

   @Generated
   public void setPenaltyUntil(final LocalDateTime penaltyUntil) {
      this.penaltyUntil = penaltyUntil;
   }

   @Generated
   public void setWipedPoints(final Long wipedPoints) {
      this.wipedPoints = wipedPoints;
   }

   @Generated
   @Override
   public String toString() {
      return "PointsBotFoamBag(id="
         + this.getId()
         + ", chatId="
         + this.getChatId()
         + ", userId="
         + this.getUserId()
         + ", username="
         + this.getUsername()
         + ", displayName="
         + this.getDisplayName()
         + ", principalPoints="
         + this.getPrincipalPoints()
         + ", repaymentPoints="
         + this.getRepaymentPoints()
         + ", repaymentMultiplier="
         + this.getRepaymentMultiplier()
         + ", repaymentHours="
         + this.getRepaymentHours()
         + ", penaltyDays="
         + this.getPenaltyDays()
         + ", status="
         + this.getStatus()
         + ", activeGuard="
         + this.getActiveGuard()
         + ", borrowedAt="
         + this.getBorrowedAt()
         + ", dueAt="
         + this.getDueAt()
         + ", repaidAt="
         + this.getRepaidAt()
         + ", penalizedAt="
         + this.getPenalizedAt()
         + ", penaltyUntil="
         + this.getPenaltyUntil()
         + ", wipedPoints="
         + this.getWipedPoints()
         + ")";
   }

   @Generated
   @Override
   public boolean equals(final Object o) {
      if (o == this) {
         return true;
      } else if (!(o instanceof PointsBotFoamBag other)) {
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
               Object this$userId = this.getUserId();
               Object other$userId = other.getUserId();
               if (this$userId == null ? other$userId == null : this$userId.equals(other$userId)) {
                  Object this$principalPoints = this.getPrincipalPoints();
                  Object other$principalPoints = other.getPrincipalPoints();
                  if (this$principalPoints == null ? other$principalPoints == null : this$principalPoints.equals(other$principalPoints)) {
                     Object this$repaymentPoints = this.getRepaymentPoints();
                     Object other$repaymentPoints = other.getRepaymentPoints();
                     if (this$repaymentPoints == null ? other$repaymentPoints == null : this$repaymentPoints.equals(other$repaymentPoints)) {
                        Object this$repaymentMultiplier = this.getRepaymentMultiplier();
                        Object other$repaymentMultiplier = other.getRepaymentMultiplier();
                        if (this$repaymentMultiplier == null ? other$repaymentMultiplier == null : this$repaymentMultiplier.equals(other$repaymentMultiplier)) {
                           Object this$repaymentHours = this.getRepaymentHours();
                           Object other$repaymentHours = other.getRepaymentHours();
                           if (this$repaymentHours == null ? other$repaymentHours == null : this$repaymentHours.equals(other$repaymentHours)) {
                              Object this$penaltyDays = this.getPenaltyDays();
                              Object other$penaltyDays = other.getPenaltyDays();
                              if (this$penaltyDays == null ? other$penaltyDays == null : this$penaltyDays.equals(other$penaltyDays)) {
                                 Object this$activeGuard = this.getActiveGuard();
                                 Object other$activeGuard = other.getActiveGuard();
                                 if (this$activeGuard == null ? other$activeGuard == null : this$activeGuard.equals(other$activeGuard)) {
                                    Object this$wipedPoints = this.getWipedPoints();
                                    Object other$wipedPoints = other.getWipedPoints();
                                    if (this$wipedPoints == null ? other$wipedPoints == null : this$wipedPoints.equals(other$wipedPoints)) {
                                       Object this$username = this.getUsername();
                                       Object other$username = other.getUsername();
                                       if (this$username == null ? other$username == null : this$username.equals(other$username)) {
                                          Object this$displayName = this.getDisplayName();
                                          Object other$displayName = other.getDisplayName();
                                          if (this$displayName == null ? other$displayName == null : this$displayName.equals(other$displayName)) {
                                             Object this$status = this.getStatus();
                                             Object other$status = other.getStatus();
                                             if (this$status == null ? other$status == null : this$status.equals(other$status)) {
                                                Object this$borrowedAt = this.getBorrowedAt();
                                                Object other$borrowedAt = other.getBorrowedAt();
                                                if (this$borrowedAt == null ? other$borrowedAt == null : this$borrowedAt.equals(other$borrowedAt)) {
                                                   Object this$dueAt = this.getDueAt();
                                                   Object other$dueAt = other.getDueAt();
                                                   if (this$dueAt == null ? other$dueAt == null : this$dueAt.equals(other$dueAt)) {
                                                      Object this$repaidAt = this.getRepaidAt();
                                                      Object other$repaidAt = other.getRepaidAt();
                                                      if (this$repaidAt == null ? other$repaidAt == null : this$repaidAt.equals(other$repaidAt)) {
                                                         Object this$penalizedAt = this.getPenalizedAt();
                                                         Object other$penalizedAt = other.getPenalizedAt();
                                                         if (this$penalizedAt == null ? other$penalizedAt == null : this$penalizedAt.equals(other$penalizedAt)) {
                                                            Object this$penaltyUntil = this.getPenaltyUntil();
                                                            Object other$penaltyUntil = other.getPenaltyUntil();
                                                            return this$penaltyUntil == null
                                                               ? other$penaltyUntil == null
                                                               : this$penaltyUntil.equals(other$penaltyUntil);
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
         } else {
            return false;
         }
      }
   }

   @Generated
   @Override
   protected boolean canEqual(final Object other) {
      return other instanceof PointsBotFoamBag;
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
      Object $userId = this.getUserId();
      result = result * 59 + ($userId == null ? 43 : $userId.hashCode());
      Object $principalPoints = this.getPrincipalPoints();
      result = result * 59 + ($principalPoints == null ? 43 : $principalPoints.hashCode());
      Object $repaymentPoints = this.getRepaymentPoints();
      result = result * 59 + ($repaymentPoints == null ? 43 : $repaymentPoints.hashCode());
      Object $repaymentMultiplier = this.getRepaymentMultiplier();
      result = result * 59 + ($repaymentMultiplier == null ? 43 : $repaymentMultiplier.hashCode());
      Object $repaymentHours = this.getRepaymentHours();
      result = result * 59 + ($repaymentHours == null ? 43 : $repaymentHours.hashCode());
      Object $penaltyDays = this.getPenaltyDays();
      result = result * 59 + ($penaltyDays == null ? 43 : $penaltyDays.hashCode());
      Object $activeGuard = this.getActiveGuard();
      result = result * 59 + ($activeGuard == null ? 43 : $activeGuard.hashCode());
      Object $wipedPoints = this.getWipedPoints();
      result = result * 59 + ($wipedPoints == null ? 43 : $wipedPoints.hashCode());
      Object $username = this.getUsername();
      result = result * 59 + ($username == null ? 43 : $username.hashCode());
      Object $displayName = this.getDisplayName();
      result = result * 59 + ($displayName == null ? 43 : $displayName.hashCode());
      Object $status = this.getStatus();
      result = result * 59 + ($status == null ? 43 : $status.hashCode());
      Object $borrowedAt = this.getBorrowedAt();
      result = result * 59 + ($borrowedAt == null ? 43 : $borrowedAt.hashCode());
      Object $dueAt = this.getDueAt();
      result = result * 59 + ($dueAt == null ? 43 : $dueAt.hashCode());
      Object $repaidAt = this.getRepaidAt();
      result = result * 59 + ($repaidAt == null ? 43 : $repaidAt.hashCode());
      Object $penalizedAt = this.getPenalizedAt();
      result = result * 59 + ($penalizedAt == null ? 43 : $penalizedAt.hashCode());
      Object $penaltyUntil = this.getPenaltyUntil();
      return result * 59 + ($penaltyUntil == null ? 43 : $penaltyUntil.hashCode());
   }
}
