package com.una.embyhub.model.dto.response.sysnotice;

import java.io.Serializable;
import java.util.Date;
import lombok.Generated;

public class SysNoticeResponse implements Serializable {
   private Long id;
   private String noticeTitle;
   private String noticeContent;
   private Integer noticeStatus;
   private Integer noticeScope;
   private Integer topFlag;
   private Integer sort;
   private Date createDatetime;
   private Date updateDatetime;
   private String createUserName;
   private String updateUserName;
   private Boolean readFlag;
   private Date readDatetime;

   @Generated
   public Long getId() {
      return this.id;
   }

   @Generated
   public String getNoticeTitle() {
      return this.noticeTitle;
   }

   @Generated
   public String getNoticeContent() {
      return this.noticeContent;
   }

   @Generated
   public Integer getNoticeStatus() {
      return this.noticeStatus;
   }

   @Generated
   public Integer getNoticeScope() {
      return this.noticeScope;
   }

   @Generated
   public Integer getTopFlag() {
      return this.topFlag;
   }

   @Generated
   public Integer getSort() {
      return this.sort;
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
   public Boolean getReadFlag() {
      return this.readFlag;
   }

   @Generated
   public Date getReadDatetime() {
      return this.readDatetime;
   }

   @Generated
   public void setId(final Long id) {
      this.id = id;
   }

   @Generated
   public void setNoticeTitle(final String noticeTitle) {
      this.noticeTitle = noticeTitle;
   }

   @Generated
   public void setNoticeContent(final String noticeContent) {
      this.noticeContent = noticeContent;
   }

   @Generated
   public void setNoticeStatus(final Integer noticeStatus) {
      this.noticeStatus = noticeStatus;
   }

   @Generated
   public void setNoticeScope(final Integer noticeScope) {
      this.noticeScope = noticeScope;
   }

   @Generated
   public void setTopFlag(final Integer topFlag) {
      this.topFlag = topFlag;
   }

   @Generated
   public void setSort(final Integer sort) {
      this.sort = sort;
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
   public void setReadFlag(final Boolean readFlag) {
      this.readFlag = readFlag;
   }

   @Generated
   public void setReadDatetime(final Date readDatetime) {
      this.readDatetime = readDatetime;
   }

   @Generated
   @Override
   public boolean equals(final Object o) {
      if (o == this) {
         return true;
      } else if (!(o instanceof SysNoticeResponse other)) {
         return false;
      } else if (!other.canEqual(this)) {
         return false;
      } else {
         Object this$id = this.getId();
         Object other$id = other.getId();
         if (this$id == null ? other$id == null : this$id.equals(other$id)) {
            Object this$noticeStatus = this.getNoticeStatus();
            Object other$noticeStatus = other.getNoticeStatus();
            if (this$noticeStatus == null ? other$noticeStatus == null : this$noticeStatus.equals(other$noticeStatus)) {
               Object this$noticeScope = this.getNoticeScope();
               Object other$noticeScope = other.getNoticeScope();
               if (this$noticeScope == null ? other$noticeScope == null : this$noticeScope.equals(other$noticeScope)) {
                  Object this$topFlag = this.getTopFlag();
                  Object other$topFlag = other.getTopFlag();
                  if (this$topFlag == null ? other$topFlag == null : this$topFlag.equals(other$topFlag)) {
                     Object this$sort = this.getSort();
                     Object other$sort = other.getSort();
                     if (this$sort == null ? other$sort == null : this$sort.equals(other$sort)) {
                        Object this$readFlag = this.getReadFlag();
                        Object other$readFlag = other.getReadFlag();
                        if (this$readFlag == null ? other$readFlag == null : this$readFlag.equals(other$readFlag)) {
                           Object this$noticeTitle = this.getNoticeTitle();
                           Object other$noticeTitle = other.getNoticeTitle();
                           if (this$noticeTitle == null ? other$noticeTitle == null : this$noticeTitle.equals(other$noticeTitle)) {
                              Object this$noticeContent = this.getNoticeContent();
                              Object other$noticeContent = other.getNoticeContent();
                              if (this$noticeContent == null ? other$noticeContent == null : this$noticeContent.equals(other$noticeContent)) {
                                 Object this$createDatetime = this.getCreateDatetime();
                                 Object other$createDatetime = other.getCreateDatetime();
                                 if (this$createDatetime == null ? other$createDatetime == null : this$createDatetime.equals(other$createDatetime)) {
                                    Object this$updateDatetime = this.getUpdateDatetime();
                                    Object other$updateDatetime = other.getUpdateDatetime();
                                    if (this$updateDatetime == null ? other$updateDatetime == null : this$updateDatetime.equals(other$updateDatetime)) {
                                       Object this$createUserName = this.getCreateUserName();
                                       Object other$createUserName = other.getCreateUserName();
                                       if (this$createUserName == null ? other$createUserName == null : this$createUserName.equals(other$createUserName)) {
                                          Object this$updateUserName = this.getUpdateUserName();
                                          Object other$updateUserName = other.getUpdateUserName();
                                          if (this$updateUserName == null ? other$updateUserName == null : this$updateUserName.equals(other$updateUserName)) {
                                             Object this$readDatetime = this.getReadDatetime();
                                             Object other$readDatetime = other.getReadDatetime();
                                             return this$readDatetime == null ? other$readDatetime == null : this$readDatetime.equals(other$readDatetime);
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
      return other instanceof SysNoticeResponse;
   }

   @Generated
   @Override
   public int hashCode() {
      int PRIME = 59;
      int result = 1;
      Object $id = this.getId();
      result = result * 59 + ($id == null ? 43 : $id.hashCode());
      Object $noticeStatus = this.getNoticeStatus();
      result = result * 59 + ($noticeStatus == null ? 43 : $noticeStatus.hashCode());
      Object $noticeScope = this.getNoticeScope();
      result = result * 59 + ($noticeScope == null ? 43 : $noticeScope.hashCode());
      Object $topFlag = this.getTopFlag();
      result = result * 59 + ($topFlag == null ? 43 : $topFlag.hashCode());
      Object $sort = this.getSort();
      result = result * 59 + ($sort == null ? 43 : $sort.hashCode());
      Object $readFlag = this.getReadFlag();
      result = result * 59 + ($readFlag == null ? 43 : $readFlag.hashCode());
      Object $noticeTitle = this.getNoticeTitle();
      result = result * 59 + ($noticeTitle == null ? 43 : $noticeTitle.hashCode());
      Object $noticeContent = this.getNoticeContent();
      result = result * 59 + ($noticeContent == null ? 43 : $noticeContent.hashCode());
      Object $createDatetime = this.getCreateDatetime();
      result = result * 59 + ($createDatetime == null ? 43 : $createDatetime.hashCode());
      Object $updateDatetime = this.getUpdateDatetime();
      result = result * 59 + ($updateDatetime == null ? 43 : $updateDatetime.hashCode());
      Object $createUserName = this.getCreateUserName();
      result = result * 59 + ($createUserName == null ? 43 : $createUserName.hashCode());
      Object $updateUserName = this.getUpdateUserName();
      result = result * 59 + ($updateUserName == null ? 43 : $updateUserName.hashCode());
      Object $readDatetime = this.getReadDatetime();
      return result * 59 + ($readDatetime == null ? 43 : $readDatetime.hashCode());
   }

   @Generated
   @Override
   public String toString() {
      return "SysNoticeResponse(id="
         + this.getId()
         + ", noticeTitle="
         + this.getNoticeTitle()
         + ", noticeContent="
         + this.getNoticeContent()
         + ", noticeStatus="
         + this.getNoticeStatus()
         + ", noticeScope="
         + this.getNoticeScope()
         + ", topFlag="
         + this.getTopFlag()
         + ", sort="
         + this.getSort()
         + ", createDatetime="
         + this.getCreateDatetime()
         + ", updateDatetime="
         + this.getUpdateDatetime()
         + ", createUserName="
         + this.getCreateUserName()
         + ", updateUserName="
         + this.getUpdateUserName()
         + ", readFlag="
         + this.getReadFlag()
         + ", readDatetime="
         + this.getReadDatetime()
         + ")";
   }
}
