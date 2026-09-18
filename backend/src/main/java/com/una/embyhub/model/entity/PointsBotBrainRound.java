package com.una.embyhub.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Generated;

@TableName("points_bot_brain_round")
public class PointsBotBrainRound extends BaseEntity implements Serializable {
   private static final long serialVersionUID = 1L;
   @TableId(
      value = "id",
      type = IdType.AUTO
   )
   private Long id;
   @TableField("chat_id")
   private Long chatId;
   @TableField("message_id")
   private Long messageId;
   @TableField("status")
   private String status;
   @TableField("is_peak")
   private Boolean peak;
   @TableField("config_json")
   private String configJson;
   @TableField("question_type")
   private String questionType;
   @TableField("question_prompt")
   private String questionPrompt;
   @TableField("hidden_prompt")
   private String hiddenPrompt;
   @TableField("answer_data")
   private String answerData;
   @TableField("explanation")
   private String explanation;
   @TableField("entry_cost")
   private Integer entryCost;
   @TableField("min_players")
   private Integer minPlayers;
   @TableField("max_players")
   private Integer maxPlayers;
   @TableField("registration_ends_at")
   private LocalDateTime registrationEndsAt;
   @TableField("started_at")
   private LocalDateTime startedAt;
   @TableField("question_hides_at")
   private LocalDateTime questionHidesAt;
   @TableField("answer_ends_at")
   private LocalDateTime answerEndsAt;
   @TableField("settled_at")
   private LocalDateTime settledAt;
   @TableField("total_pot")
   private Integer totalPot;
   @TableField("champion_user_id")
   private Long championUserId;
   @TableField("jackpot_before")
   private Integer jackpotBefore;
   @TableField("jackpot_after")
   private Integer jackpotAfter;

   @Generated
   public Long getId() {
      return this.id;
   }

   @Generated
   public Long getChatId() {
      return this.chatId;
   }

   @Generated
   public Long getMessageId() {
      return this.messageId;
   }

   @Generated
   public String getStatus() {
      return this.status;
   }

   @Generated
   public Boolean getPeak() {
      return this.peak;
   }

   @Generated
   public String getConfigJson() {
      return this.configJson;
   }

   @Generated
   public String getQuestionType() {
      return this.questionType;
   }

   @Generated
   public String getQuestionPrompt() {
      return this.questionPrompt;
   }

   @Generated
   public String getHiddenPrompt() {
      return this.hiddenPrompt;
   }

   @Generated
   public String getAnswerData() {
      return this.answerData;
   }

   @Generated
   public String getExplanation() {
      return this.explanation;
   }

   @Generated
   public Integer getEntryCost() {
      return this.entryCost;
   }

   @Generated
   public Integer getMinPlayers() {
      return this.minPlayers;
   }

   @Generated
   public Integer getMaxPlayers() {
      return this.maxPlayers;
   }

   @Generated
   public LocalDateTime getRegistrationEndsAt() {
      return this.registrationEndsAt;
   }

   @Generated
   public LocalDateTime getStartedAt() {
      return this.startedAt;
   }

   @Generated
   public LocalDateTime getQuestionHidesAt() {
      return this.questionHidesAt;
   }

   @Generated
   public LocalDateTime getAnswerEndsAt() {
      return this.answerEndsAt;
   }

   @Generated
   public LocalDateTime getSettledAt() {
      return this.settledAt;
   }

   @Generated
   public Integer getTotalPot() {
      return this.totalPot;
   }

   @Generated
   public Long getChampionUserId() {
      return this.championUserId;
   }

   @Generated
   public Integer getJackpotBefore() {
      return this.jackpotBefore;
   }

   @Generated
   public Integer getJackpotAfter() {
      return this.jackpotAfter;
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
   public void setMessageId(final Long messageId) {
      this.messageId = messageId;
   }

   @Generated
   public void setStatus(final String status) {
      this.status = status;
   }

   @Generated
   public void setPeak(final Boolean peak) {
      this.peak = peak;
   }

   @Generated
   public void setConfigJson(final String configJson) {
      this.configJson = configJson;
   }

   @Generated
   public void setQuestionType(final String questionType) {
      this.questionType = questionType;
   }

   @Generated
   public void setQuestionPrompt(final String questionPrompt) {
      this.questionPrompt = questionPrompt;
   }

   @Generated
   public void setHiddenPrompt(final String hiddenPrompt) {
      this.hiddenPrompt = hiddenPrompt;
   }

   @Generated
   public void setAnswerData(final String answerData) {
      this.answerData = answerData;
   }

   @Generated
   public void setExplanation(final String explanation) {
      this.explanation = explanation;
   }

   @Generated
   public void setEntryCost(final Integer entryCost) {
      this.entryCost = entryCost;
   }

   @Generated
   public void setMinPlayers(final Integer minPlayers) {
      this.minPlayers = minPlayers;
   }

   @Generated
   public void setMaxPlayers(final Integer maxPlayers) {
      this.maxPlayers = maxPlayers;
   }

   @Generated
   public void setRegistrationEndsAt(final LocalDateTime registrationEndsAt) {
      this.registrationEndsAt = registrationEndsAt;
   }

   @Generated
   public void setStartedAt(final LocalDateTime startedAt) {
      this.startedAt = startedAt;
   }

   @Generated
   public void setQuestionHidesAt(final LocalDateTime questionHidesAt) {
      this.questionHidesAt = questionHidesAt;
   }

   @Generated
   public void setAnswerEndsAt(final LocalDateTime answerEndsAt) {
      this.answerEndsAt = answerEndsAt;
   }

   @Generated
   public void setSettledAt(final LocalDateTime settledAt) {
      this.settledAt = settledAt;
   }

   @Generated
   public void setTotalPot(final Integer totalPot) {
      this.totalPot = totalPot;
   }

   @Generated
   public void setChampionUserId(final Long championUserId) {
      this.championUserId = championUserId;
   }

   @Generated
   public void setJackpotBefore(final Integer jackpotBefore) {
      this.jackpotBefore = jackpotBefore;
   }

   @Generated
   public void setJackpotAfter(final Integer jackpotAfter) {
      this.jackpotAfter = jackpotAfter;
   }

   @Generated
   @Override
   public String toString() {
      return "PointsBotBrainRound(id="
         + this.getId()
         + ", chatId="
         + this.getChatId()
         + ", messageId="
         + this.getMessageId()
         + ", status="
         + this.getStatus()
         + ", peak="
         + this.getPeak()
         + ", configJson="
         + this.getConfigJson()
         + ", questionType="
         + this.getQuestionType()
         + ", questionPrompt="
         + this.getQuestionPrompt()
         + ", hiddenPrompt="
         + this.getHiddenPrompt()
         + ", answerData="
         + this.getAnswerData()
         + ", explanation="
         + this.getExplanation()
         + ", entryCost="
         + this.getEntryCost()
         + ", minPlayers="
         + this.getMinPlayers()
         + ", maxPlayers="
         + this.getMaxPlayers()
         + ", registrationEndsAt="
         + this.getRegistrationEndsAt()
         + ", startedAt="
         + this.getStartedAt()
         + ", questionHidesAt="
         + this.getQuestionHidesAt()
         + ", answerEndsAt="
         + this.getAnswerEndsAt()
         + ", settledAt="
         + this.getSettledAt()
         + ", totalPot="
         + this.getTotalPot()
         + ", championUserId="
         + this.getChampionUserId()
         + ", jackpotBefore="
         + this.getJackpotBefore()
         + ", jackpotAfter="
         + this.getJackpotAfter()
         + ")";
   }

   @Generated
   @Override
   public boolean equals(final Object o) {
      if (o == this) {
         return true;
      } else if (!(o instanceof PointsBotBrainRound other)) {
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
               Object this$messageId = this.getMessageId();
               Object other$messageId = other.getMessageId();
               if (this$messageId == null ? other$messageId == null : this$messageId.equals(other$messageId)) {
                  Object this$peak = this.getPeak();
                  Object other$peak = other.getPeak();
                  if (this$peak == null ? other$peak == null : this$peak.equals(other$peak)) {
                     Object this$entryCost = this.getEntryCost();
                     Object other$entryCost = other.getEntryCost();
                     if (this$entryCost == null ? other$entryCost == null : this$entryCost.equals(other$entryCost)) {
                        Object this$minPlayers = this.getMinPlayers();
                        Object other$minPlayers = other.getMinPlayers();
                        if (this$minPlayers == null ? other$minPlayers == null : this$minPlayers.equals(other$minPlayers)) {
                           Object this$maxPlayers = this.getMaxPlayers();
                           Object other$maxPlayers = other.getMaxPlayers();
                           if (this$maxPlayers == null ? other$maxPlayers == null : this$maxPlayers.equals(other$maxPlayers)) {
                              Object this$totalPot = this.getTotalPot();
                              Object other$totalPot = other.getTotalPot();
                              if (this$totalPot == null ? other$totalPot == null : this$totalPot.equals(other$totalPot)) {
                                 Object this$championUserId = this.getChampionUserId();
                                 Object other$championUserId = other.getChampionUserId();
                                 if (this$championUserId == null ? other$championUserId == null : this$championUserId.equals(other$championUserId)) {
                                    Object this$jackpotBefore = this.getJackpotBefore();
                                    Object other$jackpotBefore = other.getJackpotBefore();
                                    if (this$jackpotBefore == null ? other$jackpotBefore == null : this$jackpotBefore.equals(other$jackpotBefore)) {
                                       Object this$jackpotAfter = this.getJackpotAfter();
                                       Object other$jackpotAfter = other.getJackpotAfter();
                                       if (this$jackpotAfter == null ? other$jackpotAfter == null : this$jackpotAfter.equals(other$jackpotAfter)) {
                                          Object this$status = this.getStatus();
                                          Object other$status = other.getStatus();
                                          if (this$status == null ? other$status == null : this$status.equals(other$status)) {
                                             Object this$configJson = this.getConfigJson();
                                             Object other$configJson = other.getConfigJson();
                                             if (this$configJson == null ? other$configJson == null : this$configJson.equals(other$configJson)) {
                                                Object this$questionType = this.getQuestionType();
                                                Object other$questionType = other.getQuestionType();
                                                if (this$questionType == null ? other$questionType == null : this$questionType.equals(other$questionType)) {
                                                   Object this$questionPrompt = this.getQuestionPrompt();
                                                   Object other$questionPrompt = other.getQuestionPrompt();
                                                   if (this$questionPrompt == null
                                                      ? other$questionPrompt == null
                                                      : this$questionPrompt.equals(other$questionPrompt)) {
                                                      Object this$hiddenPrompt = this.getHiddenPrompt();
                                                      Object other$hiddenPrompt = other.getHiddenPrompt();
                                                      if (this$hiddenPrompt == null ? other$hiddenPrompt == null : this$hiddenPrompt.equals(other$hiddenPrompt)
                                                         )
                                                       {
                                                         Object this$answerData = this.getAnswerData();
                                                         Object other$answerData = other.getAnswerData();
                                                         if (this$answerData == null ? other$answerData == null : this$answerData.equals(other$answerData)) {
                                                            Object this$explanation = this.getExplanation();
                                                            Object other$explanation = other.getExplanation();
                                                            if (this$explanation == null
                                                               ? other$explanation == null
                                                               : this$explanation.equals(other$explanation)) {
                                                               Object this$registrationEndsAt = this.getRegistrationEndsAt();
                                                               Object other$registrationEndsAt = other.getRegistrationEndsAt();
                                                               if (this$registrationEndsAt == null
                                                                  ? other$registrationEndsAt == null
                                                                  : this$registrationEndsAt.equals(other$registrationEndsAt)) {
                                                                  Object this$startedAt = this.getStartedAt();
                                                                  Object other$startedAt = other.getStartedAt();
                                                                  if (this$startedAt == null ? other$startedAt == null : this$startedAt.equals(other$startedAt)
                                                                     )
                                                                   {
                                                                     Object this$questionHidesAt = this.getQuestionHidesAt();
                                                                     Object other$questionHidesAt = other.getQuestionHidesAt();
                                                                     if (this$questionHidesAt == null
                                                                        ? other$questionHidesAt == null
                                                                        : this$questionHidesAt.equals(other$questionHidesAt)) {
                                                                        Object this$answerEndsAt = this.getAnswerEndsAt();
                                                                        Object other$answerEndsAt = other.getAnswerEndsAt();
                                                                        if (this$answerEndsAt == null
                                                                           ? other$answerEndsAt == null
                                                                           : this$answerEndsAt.equals(other$answerEndsAt)) {
                                                                           Object this$settledAt = this.getSettledAt();
                                                                           Object other$settledAt = other.getSettledAt();
                                                                           return this$settledAt == null
                                                                              ? other$settledAt == null
                                                                              : this$settledAt.equals(other$settledAt);
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
      return other instanceof PointsBotBrainRound;
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
      Object $messageId = this.getMessageId();
      result = result * 59 + ($messageId == null ? 43 : $messageId.hashCode());
      Object $peak = this.getPeak();
      result = result * 59 + ($peak == null ? 43 : $peak.hashCode());
      Object $entryCost = this.getEntryCost();
      result = result * 59 + ($entryCost == null ? 43 : $entryCost.hashCode());
      Object $minPlayers = this.getMinPlayers();
      result = result * 59 + ($minPlayers == null ? 43 : $minPlayers.hashCode());
      Object $maxPlayers = this.getMaxPlayers();
      result = result * 59 + ($maxPlayers == null ? 43 : $maxPlayers.hashCode());
      Object $totalPot = this.getTotalPot();
      result = result * 59 + ($totalPot == null ? 43 : $totalPot.hashCode());
      Object $championUserId = this.getChampionUserId();
      result = result * 59 + ($championUserId == null ? 43 : $championUserId.hashCode());
      Object $jackpotBefore = this.getJackpotBefore();
      result = result * 59 + ($jackpotBefore == null ? 43 : $jackpotBefore.hashCode());
      Object $jackpotAfter = this.getJackpotAfter();
      result = result * 59 + ($jackpotAfter == null ? 43 : $jackpotAfter.hashCode());
      Object $status = this.getStatus();
      result = result * 59 + ($status == null ? 43 : $status.hashCode());
      Object $configJson = this.getConfigJson();
      result = result * 59 + ($configJson == null ? 43 : $configJson.hashCode());
      Object $questionType = this.getQuestionType();
      result = result * 59 + ($questionType == null ? 43 : $questionType.hashCode());
      Object $questionPrompt = this.getQuestionPrompt();
      result = result * 59 + ($questionPrompt == null ? 43 : $questionPrompt.hashCode());
      Object $hiddenPrompt = this.getHiddenPrompt();
      result = result * 59 + ($hiddenPrompt == null ? 43 : $hiddenPrompt.hashCode());
      Object $answerData = this.getAnswerData();
      result = result * 59 + ($answerData == null ? 43 : $answerData.hashCode());
      Object $explanation = this.getExplanation();
      result = result * 59 + ($explanation == null ? 43 : $explanation.hashCode());
      Object $registrationEndsAt = this.getRegistrationEndsAt();
      result = result * 59 + ($registrationEndsAt == null ? 43 : $registrationEndsAt.hashCode());
      Object $startedAt = this.getStartedAt();
      result = result * 59 + ($startedAt == null ? 43 : $startedAt.hashCode());
      Object $questionHidesAt = this.getQuestionHidesAt();
      result = result * 59 + ($questionHidesAt == null ? 43 : $questionHidesAt.hashCode());
      Object $answerEndsAt = this.getAnswerEndsAt();
      result = result * 59 + ($answerEndsAt == null ? 43 : $answerEndsAt.hashCode());
      Object $settledAt = this.getSettledAt();
      return result * 59 + ($settledAt == null ? 43 : $settledAt.hashCode());
   }
}
