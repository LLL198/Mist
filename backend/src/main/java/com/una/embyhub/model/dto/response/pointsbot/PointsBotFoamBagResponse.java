package com.una.embyhub.model.dto.response.pointsbot;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;
import lombok.Generated;

public class PointsBotFoamBagResponse implements Serializable {
   private Long id;
   private Long chatId;
   private Long userId;
   private String username;
   private String displayName;
   private Boolean telegramBound;
   private String embyUserName;
   private Integer principalPoints;
   private Integer repaymentPoints;
   private Integer repaymentMultiplier;
   private Integer repaymentHours;
   private Integer penaltyDays;
   private String status;
   private LocalDateTime borrowedAt;
   private LocalDateTime dueAt;
   private LocalDateTime repaidAt;
   private LocalDateTime penalizedAt;
   private LocalDateTime penaltyUntil;
   private Long wipedPoints;
   private Date createDatetime;
   private Date updateDatetime;

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
   public Boolean getTelegramBound() {
      return this.telegramBound;
   }

   @Generated
   public String getEmbyUserName() {
      return this.embyUserName;
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
   public Date getCreateDatetime() {
      return this.createDatetime;
   }

   @Generated
   public Date getUpdateDatetime() {
      return this.updateDatetime;
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
   public void setTelegramBound(final Boolean telegramBound) {
      this.telegramBound = telegramBound;
   }

   @Generated
   public void setEmbyUserName(final String embyUserName) {
      this.embyUserName = embyUserName;
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
   public void setCreateDatetime(final Date createDatetime) {
      this.createDatetime = createDatetime;
   }

   @Generated
   public void setUpdateDatetime(final Date updateDatetime) {
      this.updateDatetime = updateDatetime;
   }

   @Generated
   @Override
   public boolean equals(final Object o) {
      if (o == this) {
         return true;
      } else if (!(o instanceof PointsBotFoamBagResponse other)) {
         return false;
      } else if (!other.canEqual(this)) {
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
                  Object this$telegramBound = this.getTelegramBound();
                  Object other$telegramBound = other.getTelegramBound();
                  if (this$telegramBound == null ? other$telegramBound == null : this$telegramBound.equals(other$telegramBound)) {
                     Object this$principalPoints = this.getPrincipalPoints();
                     Object other$principalPoints = other.getPrincipalPoints();
                     if (this$principalPoints == null ? other$principalPoints == null : this$principalPoints.equals(other$principalPoints)) {
                        Object this$repaymentPoints = this.getRepaymentPoints();
                        Object other$repaymentPoints = other.getRepaymentPoints();
                        if (this$repaymentPoints == null ? other$repaymentPoints == null : this$repaymentPoints.equals(other$repaymentPoints)) {
                           Object this$repaymentMultiplier = this.getRepaymentMultiplier();
                           Object other$repaymentMultiplier = other.getRepaymentMultiplier();
                           if (this$repaymentMultiplier == null
                              ? other$repaymentMultiplier == null
                              : this$repaymentMultiplier.equals(other$repaymentMultiplier)) {
                              Object this$repaymentHours = this.getRepaymentHours();
                              Object other$repaymentHours = other.getRepaymentHours();
                              if (this$repaymentHours == null ? other$repaymentHours == null : this$repaymentHours.equals(other$repaymentHours)) {
                                 Object this$penaltyDays = this.getPenaltyDays();
                                 Object other$penaltyDays = other.getPenaltyDays();
                                 if (this$penaltyDays == null ? other$penaltyDays == null : this$penaltyDays.equals(other$penaltyDays)) {
                                    Object this$wipedPoints = this.getWipedPoints();
                                    Object other$wipedPoints = other.getWipedPoints();
                                    if (this$wipedPoints == null ? other$wipedPoints == null : this$wipedPoints.equals(other$wipedPoints)) {
                                       Object this$username = this.getUsername();
                                       Object other$username = other.getUsername();
                                       if (this$username == null ? other$username == null : this$username.equals(other$username)) {
                                          Object this$displayName = this.getDisplayName();
                                          Object other$displayName = other.getDisplayName();
                                          if (this$displayName == null ? other$displayName == null : this$displayName.equals(other$displayName)) {
                                             Object this$embyUserName = this.getEmbyUserName();
                                             Object other$embyUserName = other.getEmbyUserName();
                                             if (this$embyUserName == null ? other$embyUserName == null : this$embyUserName.equals(other$embyUserName)) {
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
                                                            if (this$penalizedAt == null
                                                               ? other$penalizedAt == null
                                                               : this$penalizedAt.equals(other$penalizedAt)) {
                                                               Object this$penaltyUntil = this.getPenaltyUntil();
                                                               Object other$penaltyUntil = other.getPenaltyUntil();
                                                               if (this$penaltyUntil == null
                                                                  ? other$penaltyUntil == null
                                                                  : this$penaltyUntil.equals(other$penaltyUntil)) {
                                                                  Object this$createDatetime = this.getCreateDatetime();
                                                                  Object other$createDatetime = other.getCreateDatetime();
                                                                  if (this$createDatetime == null
                                                                     ? other$createDatetime == null
                                                                     : this$createDatetime.equals(other$createDatetime)) {
                                                                     Object this$updateDatetime = this.getUpdateDatetime();
                                                                     Object other$updateDatetime = other.getUpdateDatetime();
                                                                     return this$updateDatetime == null
                                                                        ? other$updateDatetime == null
                                                                        : this$updateDatetime.equals(other$updateDatetime);
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
      return other instanceof PointsBotFoamBagResponse;
   }

   @Generated
   @Override
   public int hashCode() {
      int PRIME = 59;
      int result = 1;
      Object $id = this.getId();
      result = result * 59 + ($id == null ? 43 : $id.hashCode());
      Object $chatId = this.getChatId();
      result = result * 59 + ($chatId == null ? 43 : $chatId.hashCode());
      Object $userId = this.getUserId();
      result = result * 59 + ($userId == null ? 43 : $userId.hashCode());
      Object $telegramBound = this.getTelegramBound();
      result = result * 59 + ($telegramBound == null ? 43 : $telegramBound.hashCode());
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
      Object $wipedPoints = this.getWipedPoints();
      result = result * 59 + ($wipedPoints == null ? 43 : $wipedPoints.hashCode());
      Object $username = this.getUsername();
      result = result * 59 + ($username == null ? 43 : $username.hashCode());
      Object $displayName = this.getDisplayName();
      result = result * 59 + ($displayName == null ? 43 : $displayName.hashCode());
      Object $embyUserName = this.getEmbyUserName();
      result = result * 59 + ($embyUserName == null ? 43 : $embyUserName.hashCode());
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
      result = result * 59 + ($penaltyUntil == null ? 43 : $penaltyUntil.hashCode());
      Object $createDatetime = this.getCreateDatetime();
      result = result * 59 + ($createDatetime == null ? 43 : $createDatetime.hashCode());
      Object $updateDatetime = this.getUpdateDatetime();
      return result * 59 + ($updateDatetime == null ? 43 : $updateDatetime.hashCode());
   }

   @Generated
   @Override
   public String toString() {
      return "PointsBotFoamBagResponse(id="
         + this.getId()
         + ", chatId="
         + this.getChatId()
         + ", userId="
         + this.getUserId()
         + ", username="
         + this.getUsername()
         + ", displayName="
         + this.getDisplayName()
         + ", telegramBound="
         + this.getTelegramBound()
         + ", embyUserName="
         + this.getEmbyUserName()
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
         + ", createDatetime="
         + this.getCreateDatetime()
         + ", updateDatetime="
         + this.getUpdateDatetime()
         + ")";
   }
}
