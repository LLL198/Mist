package com.una.embyhub.model.dto.response.embynotifydatadetails;

import com.diboot.core.binding.annotation.BindField;
import com.una.embyhub.model.entity.EmbyInfo;
import java.io.Serializable;
import lombok.Generated;

public class EmbyNotifyDataDetailsResponseData implements Serializable {
   private Long embyNotifyDataId;
   private Long embyInfoId;
   @BindField(
      entity = EmbyInfo.class,
      field = "serverName",
      condition = "this.embyInfoId=id"
   )
   private String serverName;
   private String idList;
   private String episodeList;
   private Long totalSize;
   private Long count;

   @Generated
   public Long getEmbyNotifyDataId() {
      return this.embyNotifyDataId;
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
   public String getIdList() {
      return this.idList;
   }

   @Generated
   public String getEpisodeList() {
      return this.episodeList;
   }

   @Generated
   public Long getTotalSize() {
      return this.totalSize;
   }

   @Generated
   public Long getCount() {
      return this.count;
   }

   @Generated
   public void setEmbyNotifyDataId(final Long embyNotifyDataId) {
      this.embyNotifyDataId = embyNotifyDataId;
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
   public void setIdList(final String idList) {
      this.idList = idList;
   }

   @Generated
   public void setEpisodeList(final String episodeList) {
      this.episodeList = episodeList;
   }

   @Generated
   public void setTotalSize(final Long totalSize) {
      this.totalSize = totalSize;
   }

   @Generated
   public void setCount(final Long count) {
      this.count = count;
   }

   @Generated
   @Override
   public boolean equals(final Object o) {
      if (o == this) {
         return true;
      } else if (!(o instanceof EmbyNotifyDataDetailsResponseData other)) {
         return false;
      } else if (!other.canEqual(this)) {
         return false;
      } else {
         Object this$embyNotifyDataId = this.getEmbyNotifyDataId();
         Object other$embyNotifyDataId = other.getEmbyNotifyDataId();
         if (this$embyNotifyDataId == null ? other$embyNotifyDataId == null : this$embyNotifyDataId.equals(other$embyNotifyDataId)) {
            Object this$embyInfoId = this.getEmbyInfoId();
            Object other$embyInfoId = other.getEmbyInfoId();
            if (this$embyInfoId == null ? other$embyInfoId == null : this$embyInfoId.equals(other$embyInfoId)) {
               Object this$totalSize = this.getTotalSize();
               Object other$totalSize = other.getTotalSize();
               if (this$totalSize == null ? other$totalSize == null : this$totalSize.equals(other$totalSize)) {
                  Object this$count = this.getCount();
                  Object other$count = other.getCount();
                  if (this$count == null ? other$count == null : this$count.equals(other$count)) {
                     Object this$serverName = this.getServerName();
                     Object other$serverName = other.getServerName();
                     if (this$serverName == null ? other$serverName == null : this$serverName.equals(other$serverName)) {
                        Object this$idList = this.getIdList();
                        Object other$idList = other.getIdList();
                        if (this$idList == null ? other$idList == null : this$idList.equals(other$idList)) {
                           Object this$episodeList = this.getEpisodeList();
                           Object other$episodeList = other.getEpisodeList();
                           return this$episodeList == null ? other$episodeList == null : this$episodeList.equals(other$episodeList);
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
      return other instanceof EmbyNotifyDataDetailsResponseData;
   }

   @Generated
   @Override
   public int hashCode() {
      int PRIME = 59;
      int result = 1;
      Object $embyNotifyDataId = this.getEmbyNotifyDataId();
      result = result * 59 + ($embyNotifyDataId == null ? 43 : $embyNotifyDataId.hashCode());
      Object $embyInfoId = this.getEmbyInfoId();
      result = result * 59 + ($embyInfoId == null ? 43 : $embyInfoId.hashCode());
      Object $totalSize = this.getTotalSize();
      result = result * 59 + ($totalSize == null ? 43 : $totalSize.hashCode());
      Object $count = this.getCount();
      result = result * 59 + ($count == null ? 43 : $count.hashCode());
      Object $serverName = this.getServerName();
      result = result * 59 + ($serverName == null ? 43 : $serverName.hashCode());
      Object $idList = this.getIdList();
      result = result * 59 + ($idList == null ? 43 : $idList.hashCode());
      Object $episodeList = this.getEpisodeList();
      return result * 59 + ($episodeList == null ? 43 : $episodeList.hashCode());
   }

   @Generated
   @Override
   public String toString() {
      return "EmbyNotifyDataDetailsResponseData(embyNotifyDataId="
         + this.getEmbyNotifyDataId()
         + ", embyInfoId="
         + this.getEmbyInfoId()
         + ", serverName="
         + this.getServerName()
         + ", idList="
         + this.getIdList()
         + ", episodeList="
         + this.getEpisodeList()
         + ", totalSize="
         + this.getTotalSize()
         + ", count="
         + this.getCount()
         + ")";
   }
}
