package com.una.embyhub.model.dto.response.playrecords;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.io.Serializable;
import java.util.Date;
import lombok.Generated;

public class PlayRecordsResponse implements Serializable {
   private Long id;
   private String embyUserId;
   @JsonFormat(
      pattern = "yyyy-MM-dd",
      timezone = "GMT+8"
   )
   private Date playDate;
   private String recordType;
   private String device;
   private String content;
   private String embyUserName;
   private Long embyInfoId;
   private String serverName;
   private Date createDatetime;
   private Date updateDatetime;
   private String createUserName;
   private String updateUserName;
   private Long updateUserId;
   private Long createUserId;
   private Integer delFlag;
   private Date firstPlayStartTime;
   private Date lastPlayEndTime;
   private Integer totalSeconds;
   private String totalPlayTime;

   @Generated
   public static PlayRecordsResponse.PlayRecordsResponseBuilder builder() {
      return new PlayRecordsResponse.PlayRecordsResponseBuilder();
   }

   @Generated
   public Long getId() {
      return this.id;
   }

   @Generated
   public String getEmbyUserId() {
      return this.embyUserId;
   }

   @Generated
   public Date getPlayDate() {
      return this.playDate;
   }

   @Generated
   public String getRecordType() {
      return this.recordType;
   }

   @Generated
   public String getDevice() {
      return this.device;
   }

   @Generated
   public String getContent() {
      return this.content;
   }

   @Generated
   public String getEmbyUserName() {
      return this.embyUserName;
   }

   @Generated
   public Long getEmbyInfoId() {
      return this.embyInfoId;
   }

   @Generated
   public String getServerName() {
      return this.serverName;
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
   public String getCreateUserName() {
      return this.createUserName;
   }

   @Generated
   public String getUpdateUserName() {
      return this.updateUserName;
   }

   @Generated
   public Long getUpdateUserId() {
      return this.updateUserId;
   }

   @Generated
   public Long getCreateUserId() {
      return this.createUserId;
   }

   @Generated
   public Integer getDelFlag() {
      return this.delFlag;
   }

   @Generated
   public Date getFirstPlayStartTime() {
      return this.firstPlayStartTime;
   }

   @Generated
   public Date getLastPlayEndTime() {
      return this.lastPlayEndTime;
   }

   @Generated
   public Integer getTotalSeconds() {
      return this.totalSeconds;
   }

   @Generated
   public String getTotalPlayTime() {
      return this.totalPlayTime;
   }

   @Generated
   public void setId(final Long id) {
      this.id = id;
   }

   @Generated
   public void setEmbyUserId(final String embyUserId) {
      this.embyUserId = embyUserId;
   }

   @JsonFormat(
      pattern = "yyyy-MM-dd",
      timezone = "GMT+8"
   )
   @Generated
   public void setPlayDate(final Date playDate) {
      this.playDate = playDate;
   }

   @Generated
   public void setRecordType(final String recordType) {
      this.recordType = recordType;
   }

   @Generated
   public void setDevice(final String device) {
      this.device = device;
   }

   @Generated
   public void setContent(final String content) {
      this.content = content;
   }

   @Generated
   public void setEmbyUserName(final String embyUserName) {
      this.embyUserName = embyUserName;
   }

   @Generated
   public void setEmbyInfoId(final Long embyInfoId) {
      this.embyInfoId = embyInfoId;
   }

   @Generated
   public void setServerName(final String serverName) {
      this.serverName = serverName;
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
   public void setCreateUserName(final String createUserName) {
      this.createUserName = createUserName;
   }

   @Generated
   public void setUpdateUserName(final String updateUserName) {
      this.updateUserName = updateUserName;
   }

   @Generated
   public void setUpdateUserId(final Long updateUserId) {
      this.updateUserId = updateUserId;
   }

   @Generated
   public void setCreateUserId(final Long createUserId) {
      this.createUserId = createUserId;
   }

   @Generated
   public void setDelFlag(final Integer delFlag) {
      this.delFlag = delFlag;
   }

   @Generated
   public void setFirstPlayStartTime(final Date firstPlayStartTime) {
      this.firstPlayStartTime = firstPlayStartTime;
   }

   @Generated
   public void setLastPlayEndTime(final Date lastPlayEndTime) {
      this.lastPlayEndTime = lastPlayEndTime;
   }

   @Generated
   public void setTotalSeconds(final Integer totalSeconds) {
      this.totalSeconds = totalSeconds;
   }

   @Generated
   public void setTotalPlayTime(final String totalPlayTime) {
      this.totalPlayTime = totalPlayTime;
   }

   @Generated
   @Override
   public boolean equals(final Object o) {
      if (o == this) {
         return true;
      } else if (!(o instanceof PlayRecordsResponse other)) {
         return false;
      } else if (!other.canEqual(this)) {
         return false;
      } else {
         Object this$id = this.getId();
         Object other$id = other.getId();
         if (this$id == null ? other$id == null : this$id.equals(other$id)) {
            Object this$embyInfoId = this.getEmbyInfoId();
            Object other$embyInfoId = other.getEmbyInfoId();
            if (this$embyInfoId == null ? other$embyInfoId == null : this$embyInfoId.equals(other$embyInfoId)) {
               Object this$updateUserId = this.getUpdateUserId();
               Object other$updateUserId = other.getUpdateUserId();
               if (this$updateUserId == null ? other$updateUserId == null : this$updateUserId.equals(other$updateUserId)) {
                  Object this$createUserId = this.getCreateUserId();
                  Object other$createUserId = other.getCreateUserId();
                  if (this$createUserId == null ? other$createUserId == null : this$createUserId.equals(other$createUserId)) {
                     Object this$delFlag = this.getDelFlag();
                     Object other$delFlag = other.getDelFlag();
                     if (this$delFlag == null ? other$delFlag == null : this$delFlag.equals(other$delFlag)) {
                        Object this$totalSeconds = this.getTotalSeconds();
                        Object other$totalSeconds = other.getTotalSeconds();
                        if (this$totalSeconds == null ? other$totalSeconds == null : this$totalSeconds.equals(other$totalSeconds)) {
                           Object this$embyUserId = this.getEmbyUserId();
                           Object other$embyUserId = other.getEmbyUserId();
                           if (this$embyUserId == null ? other$embyUserId == null : this$embyUserId.equals(other$embyUserId)) {
                              Object this$playDate = this.getPlayDate();
                              Object other$playDate = other.getPlayDate();
                              if (this$playDate == null ? other$playDate == null : this$playDate.equals(other$playDate)) {
                                 Object this$recordType = this.getRecordType();
                                 Object other$recordType = other.getRecordType();
                                 if (this$recordType == null ? other$recordType == null : this$recordType.equals(other$recordType)) {
                                    Object this$device = this.getDevice();
                                    Object other$device = other.getDevice();
                                    if (this$device == null ? other$device == null : this$device.equals(other$device)) {
                                       Object this$content = this.getContent();
                                       Object other$content = other.getContent();
                                       if (this$content == null ? other$content == null : this$content.equals(other$content)) {
                                          Object this$embyUserName = this.getEmbyUserName();
                                          Object other$embyUserName = other.getEmbyUserName();
                                          if (this$embyUserName == null ? other$embyUserName == null : this$embyUserName.equals(other$embyUserName)) {
                                             Object this$serverName = this.getServerName();
                                             Object other$serverName = other.getServerName();
                                             if (this$serverName == null ? other$serverName == null : this$serverName.equals(other$serverName)) {
                                                Object this$createDatetime = this.getCreateDatetime();
                                                Object other$createDatetime = other.getCreateDatetime();
                                                if (this$createDatetime == null
                                                   ? other$createDatetime == null
                                                   : this$createDatetime.equals(other$createDatetime)) {
                                                   Object this$updateDatetime = this.getUpdateDatetime();
                                                   Object other$updateDatetime = other.getUpdateDatetime();
                                                   if (this$updateDatetime == null
                                                      ? other$updateDatetime == null
                                                      : this$updateDatetime.equals(other$updateDatetime)) {
                                                      Object this$createUserName = this.getCreateUserName();
                                                      Object other$createUserName = other.getCreateUserName();
                                                      if (this$createUserName == null
                                                         ? other$createUserName == null
                                                         : this$createUserName.equals(other$createUserName)) {
                                                         Object this$updateUserName = this.getUpdateUserName();
                                                         Object other$updateUserName = other.getUpdateUserName();
                                                         if (this$updateUserName == null
                                                            ? other$updateUserName == null
                                                            : this$updateUserName.equals(other$updateUserName)) {
                                                            Object this$firstPlayStartTime = this.getFirstPlayStartTime();
                                                            Object other$firstPlayStartTime = other.getFirstPlayStartTime();
                                                            if (this$firstPlayStartTime == null
                                                               ? other$firstPlayStartTime == null
                                                               : this$firstPlayStartTime.equals(other$firstPlayStartTime)) {
                                                               Object this$lastPlayEndTime = this.getLastPlayEndTime();
                                                               Object other$lastPlayEndTime = other.getLastPlayEndTime();
                                                               if (this$lastPlayEndTime == null
                                                                  ? other$lastPlayEndTime == null
                                                                  : this$lastPlayEndTime.equals(other$lastPlayEndTime)) {
                                                                  Object this$totalPlayTime = this.getTotalPlayTime();
                                                                  Object other$totalPlayTime = other.getTotalPlayTime();
                                                                  return this$totalPlayTime == null
                                                                     ? other$totalPlayTime == null
                                                                     : this$totalPlayTime.equals(other$totalPlayTime);
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
      return other instanceof PlayRecordsResponse;
   }

   @Generated
   @Override
   public int hashCode() {
      int PRIME = 59;
      int result = 1;
      Object $id = this.getId();
      result = result * 59 + ($id == null ? 43 : $id.hashCode());
      Object $embyInfoId = this.getEmbyInfoId();
      result = result * 59 + ($embyInfoId == null ? 43 : $embyInfoId.hashCode());
      Object $updateUserId = this.getUpdateUserId();
      result = result * 59 + ($updateUserId == null ? 43 : $updateUserId.hashCode());
      Object $createUserId = this.getCreateUserId();
      result = result * 59 + ($createUserId == null ? 43 : $createUserId.hashCode());
      Object $delFlag = this.getDelFlag();
      result = result * 59 + ($delFlag == null ? 43 : $delFlag.hashCode());
      Object $totalSeconds = this.getTotalSeconds();
      result = result * 59 + ($totalSeconds == null ? 43 : $totalSeconds.hashCode());
      Object $embyUserId = this.getEmbyUserId();
      result = result * 59 + ($embyUserId == null ? 43 : $embyUserId.hashCode());
      Object $playDate = this.getPlayDate();
      result = result * 59 + ($playDate == null ? 43 : $playDate.hashCode());
      Object $recordType = this.getRecordType();
      result = result * 59 + ($recordType == null ? 43 : $recordType.hashCode());
      Object $device = this.getDevice();
      result = result * 59 + ($device == null ? 43 : $device.hashCode());
      Object $content = this.getContent();
      result = result * 59 + ($content == null ? 43 : $content.hashCode());
      Object $embyUserName = this.getEmbyUserName();
      result = result * 59 + ($embyUserName == null ? 43 : $embyUserName.hashCode());
      Object $serverName = this.getServerName();
      result = result * 59 + ($serverName == null ? 43 : $serverName.hashCode());
      Object $createDatetime = this.getCreateDatetime();
      result = result * 59 + ($createDatetime == null ? 43 : $createDatetime.hashCode());
      Object $updateDatetime = this.getUpdateDatetime();
      result = result * 59 + ($updateDatetime == null ? 43 : $updateDatetime.hashCode());
      Object $createUserName = this.getCreateUserName();
      result = result * 59 + ($createUserName == null ? 43 : $createUserName.hashCode());
      Object $updateUserName = this.getUpdateUserName();
      result = result * 59 + ($updateUserName == null ? 43 : $updateUserName.hashCode());
      Object $firstPlayStartTime = this.getFirstPlayStartTime();
      result = result * 59 + ($firstPlayStartTime == null ? 43 : $firstPlayStartTime.hashCode());
      Object $lastPlayEndTime = this.getLastPlayEndTime();
      result = result * 59 + ($lastPlayEndTime == null ? 43 : $lastPlayEndTime.hashCode());
      Object $totalPlayTime = this.getTotalPlayTime();
      return result * 59 + ($totalPlayTime == null ? 43 : $totalPlayTime.hashCode());
   }

   @Generated
   @Override
   public String toString() {
      return "PlayRecordsResponse(id="
         + this.getId()
         + ", embyUserId="
         + this.getEmbyUserId()
         + ", playDate="
         + this.getPlayDate()
         + ", recordType="
         + this.getRecordType()
         + ", device="
         + this.getDevice()
         + ", content="
         + this.getContent()
         + ", embyUserName="
         + this.getEmbyUserName()
         + ", embyInfoId="
         + this.getEmbyInfoId()
         + ", serverName="
         + this.getServerName()
         + ", createDatetime="
         + this.getCreateDatetime()
         + ", updateDatetime="
         + this.getUpdateDatetime()
         + ", createUserName="
         + this.getCreateUserName()
         + ", updateUserName="
         + this.getUpdateUserName()
         + ", updateUserId="
         + this.getUpdateUserId()
         + ", createUserId="
         + this.getCreateUserId()
         + ", delFlag="
         + this.getDelFlag()
         + ", firstPlayStartTime="
         + this.getFirstPlayStartTime()
         + ", lastPlayEndTime="
         + this.getLastPlayEndTime()
         + ", totalSeconds="
         + this.getTotalSeconds()
         + ", totalPlayTime="
         + this.getTotalPlayTime()
         + ")";
   }

   @Generated
   public PlayRecordsResponse() {
   }

   @Generated
   public PlayRecordsResponse(
      final Long id,
      final String embyUserId,
      final Date playDate,
      final String recordType,
      final String device,
      final String content,
      final String embyUserName,
      final Long embyInfoId,
      final String serverName,
      final Date createDatetime,
      final Date updateDatetime,
      final String createUserName,
      final String updateUserName,
      final Long updateUserId,
      final Long createUserId,
      final Integer delFlag,
      final Date firstPlayStartTime,
      final Date lastPlayEndTime,
      final Integer totalSeconds,
      final String totalPlayTime
   ) {
      this.id = id;
      this.embyUserId = embyUserId;
      this.playDate = playDate;
      this.recordType = recordType;
      this.device = device;
      this.content = content;
      this.embyUserName = embyUserName;
      this.embyInfoId = embyInfoId;
      this.serverName = serverName;
      this.createDatetime = createDatetime;
      this.updateDatetime = updateDatetime;
      this.createUserName = createUserName;
      this.updateUserName = updateUserName;
      this.updateUserId = updateUserId;
      this.createUserId = createUserId;
      this.delFlag = delFlag;
      this.firstPlayStartTime = firstPlayStartTime;
      this.lastPlayEndTime = lastPlayEndTime;
      this.totalSeconds = totalSeconds;
      this.totalPlayTime = totalPlayTime;
   }

   @Generated
   public static class PlayRecordsResponseBuilder {
      @Generated
      private Long id;
      @Generated
      private String embyUserId;
      @Generated
      private Date playDate;
      @Generated
      private String recordType;
      @Generated
      private String device;
      @Generated
      private String content;
      @Generated
      private String embyUserName;
      @Generated
      private Long embyInfoId;
      @Generated
      private String serverName;
      @Generated
      private Date createDatetime;
      @Generated
      private Date updateDatetime;
      @Generated
      private String createUserName;
      @Generated
      private String updateUserName;
      @Generated
      private Long updateUserId;
      @Generated
      private Long createUserId;
      @Generated
      private Integer delFlag;
      @Generated
      private Date firstPlayStartTime;
      @Generated
      private Date lastPlayEndTime;
      @Generated
      private Integer totalSeconds;
      @Generated
      private String totalPlayTime;

      @Generated
      PlayRecordsResponseBuilder() {
      }

      @Generated
      public PlayRecordsResponse.PlayRecordsResponseBuilder id(final Long id) {
         this.id = id;
         return this;
      }

      @Generated
      public PlayRecordsResponse.PlayRecordsResponseBuilder embyUserId(final String embyUserId) {
         this.embyUserId = embyUserId;
         return this;
      }

      @JsonFormat(
         pattern = "yyyy-MM-dd",
         timezone = "GMT+8"
      )
      @Generated
      public PlayRecordsResponse.PlayRecordsResponseBuilder playDate(final Date playDate) {
         this.playDate = playDate;
         return this;
      }

      @Generated
      public PlayRecordsResponse.PlayRecordsResponseBuilder recordType(final String recordType) {
         this.recordType = recordType;
         return this;
      }

      @Generated
      public PlayRecordsResponse.PlayRecordsResponseBuilder device(final String device) {
         this.device = device;
         return this;
      }

      @Generated
      public PlayRecordsResponse.PlayRecordsResponseBuilder content(final String content) {
         this.content = content;
         return this;
      }

      @Generated
      public PlayRecordsResponse.PlayRecordsResponseBuilder embyUserName(final String embyUserName) {
         this.embyUserName = embyUserName;
         return this;
      }

      @Generated
      public PlayRecordsResponse.PlayRecordsResponseBuilder embyInfoId(final Long embyInfoId) {
         this.embyInfoId = embyInfoId;
         return this;
      }

      @Generated
      public PlayRecordsResponse.PlayRecordsResponseBuilder serverName(final String serverName) {
         this.serverName = serverName;
         return this;
      }

      @Generated
      public PlayRecordsResponse.PlayRecordsResponseBuilder createDatetime(final Date createDatetime) {
         this.createDatetime = createDatetime;
         return this;
      }

      @Generated
      public PlayRecordsResponse.PlayRecordsResponseBuilder updateDatetime(final Date updateDatetime) {
         this.updateDatetime = updateDatetime;
         return this;
      }

      @Generated
      public PlayRecordsResponse.PlayRecordsResponseBuilder createUserName(final String createUserName) {
         this.createUserName = createUserName;
         return this;
      }

      @Generated
      public PlayRecordsResponse.PlayRecordsResponseBuilder updateUserName(final String updateUserName) {
         this.updateUserName = updateUserName;
         return this;
      }

      @Generated
      public PlayRecordsResponse.PlayRecordsResponseBuilder updateUserId(final Long updateUserId) {
         this.updateUserId = updateUserId;
         return this;
      }

      @Generated
      public PlayRecordsResponse.PlayRecordsResponseBuilder createUserId(final Long createUserId) {
         this.createUserId = createUserId;
         return this;
      }

      @Generated
      public PlayRecordsResponse.PlayRecordsResponseBuilder delFlag(final Integer delFlag) {
         this.delFlag = delFlag;
         return this;
      }

      @Generated
      public PlayRecordsResponse.PlayRecordsResponseBuilder firstPlayStartTime(final Date firstPlayStartTime) {
         this.firstPlayStartTime = firstPlayStartTime;
         return this;
      }

      @Generated
      public PlayRecordsResponse.PlayRecordsResponseBuilder lastPlayEndTime(final Date lastPlayEndTime) {
         this.lastPlayEndTime = lastPlayEndTime;
         return this;
      }

      @Generated
      public PlayRecordsResponse.PlayRecordsResponseBuilder totalSeconds(final Integer totalSeconds) {
         this.totalSeconds = totalSeconds;
         return this;
      }

      @Generated
      public PlayRecordsResponse.PlayRecordsResponseBuilder totalPlayTime(final String totalPlayTime) {
         this.totalPlayTime = totalPlayTime;
         return this;
      }

      @Generated
      public PlayRecordsResponse build() {
         return new PlayRecordsResponse(
            this.id,
            this.embyUserId,
            this.playDate,
            this.recordType,
            this.device,
            this.content,
            this.embyUserName,
            this.embyInfoId,
            this.serverName,
            this.createDatetime,
            this.updateDatetime,
            this.createUserName,
            this.updateUserName,
            this.updateUserId,
            this.createUserId,
            this.delFlag,
            this.firstPlayStartTime,
            this.lastPlayEndTime,
            this.totalSeconds,
            this.totalPlayTime
         );
      }

      @Generated
      @Override
      public String toString() {
         return "PlayRecordsResponse.PlayRecordsResponseBuilder(id="
            + this.id
            + ", embyUserId="
            + this.embyUserId
            + ", playDate="
            + this.playDate
            + ", recordType="
            + this.recordType
            + ", device="
            + this.device
            + ", content="
            + this.content
            + ", embyUserName="
            + this.embyUserName
            + ", embyInfoId="
            + this.embyInfoId
            + ", serverName="
            + this.serverName
            + ", createDatetime="
            + this.createDatetime
            + ", updateDatetime="
            + this.updateDatetime
            + ", createUserName="
            + this.createUserName
            + ", updateUserName="
            + this.updateUserName
            + ", updateUserId="
            + this.updateUserId
            + ", createUserId="
            + this.createUserId
            + ", delFlag="
            + this.delFlag
            + ", firstPlayStartTime="
            + this.firstPlayStartTime
            + ", lastPlayEndTime="
            + this.lastPlayEndTime
            + ", totalSeconds="
            + this.totalSeconds
            + ", totalPlayTime="
            + this.totalPlayTime
            + ")";
      }
   }
}
