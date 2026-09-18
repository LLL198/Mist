package com.una.embyhub.model.dto.response.embyiplocations;

import com.diboot.core.binding.annotation.BindField;
import com.diboot.core.binding.annotation.BindFieldList;
import com.una.embyhub.model.entity.EmbyInfo;
import com.una.embyhub.model.entity.EmbyIpLocations;
import java.io.Serializable;
import lombok.Generated;

public class ThresholdUserResponse implements Serializable {
   private String ipAddress;
   private String city;
   @BindFieldList(
      entity = EmbyIpLocations.class,
      field = "city",
      condition = "this.emby_user_name=emby_user_name",
      splitBy = ","
   )
   private String cityList;
   private String embyUserName;
   private Long count;
   @BindFieldList(
      entity = EmbyIpLocations.class,
      field = "ipAddress",
      condition = "this.emby_user_name=emby_user_name",
      splitBy = ","
   )
   private String ipAddressList;
   private Long embyInfoId;
   @BindField(
      entity = EmbyInfo.class,
      field = "serverName",
      condition = "this.embyInfoId=id"
   )
   private String serverName;

   @Generated
   public String getIpAddress() {
      return this.ipAddress;
   }

   @Generated
   public String getCity() {
      return this.city;
   }

   @Generated
   public String getCityList() {
      return this.cityList;
   }

   @Generated
   public String getEmbyUserName() {
      return this.embyUserName;
   }

   @Generated
   public Long getCount() {
      return this.count;
   }

   @Generated
   public String getIpAddressList() {
      return this.ipAddressList;
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
   public void setIpAddress(final String ipAddress) {
      this.ipAddress = ipAddress;
   }

   @Generated
   public void setCity(final String city) {
      this.city = city;
   }

   @Generated
   public void setCityList(final String cityList) {
      this.cityList = cityList;
   }

   @Generated
   public void setEmbyUserName(final String embyUserName) {
      this.embyUserName = embyUserName;
   }

   @Generated
   public void setCount(final Long count) {
      this.count = count;
   }

   @Generated
   public void setIpAddressList(final String ipAddressList) {
      this.ipAddressList = ipAddressList;
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
   @Override
   public boolean equals(final Object o) {
      if (o == this) {
         return true;
      } else if (!(o instanceof ThresholdUserResponse other)) {
         return false;
      } else if (!other.canEqual(this)) {
         return false;
      } else {
         Object this$count = this.getCount();
         Object other$count = other.getCount();
         if (this$count == null ? other$count == null : this$count.equals(other$count)) {
            Object this$embyInfoId = this.getEmbyInfoId();
            Object other$embyInfoId = other.getEmbyInfoId();
            if (this$embyInfoId == null ? other$embyInfoId == null : this$embyInfoId.equals(other$embyInfoId)) {
               Object this$ipAddress = this.getIpAddress();
               Object other$ipAddress = other.getIpAddress();
               if (this$ipAddress == null ? other$ipAddress == null : this$ipAddress.equals(other$ipAddress)) {
                  Object this$city = this.getCity();
                  Object other$city = other.getCity();
                  if (this$city == null ? other$city == null : this$city.equals(other$city)) {
                     Object this$cityList = this.getCityList();
                     Object other$cityList = other.getCityList();
                     if (this$cityList == null ? other$cityList == null : this$cityList.equals(other$cityList)) {
                        Object this$embyUserName = this.getEmbyUserName();
                        Object other$embyUserName = other.getEmbyUserName();
                        if (this$embyUserName == null ? other$embyUserName == null : this$embyUserName.equals(other$embyUserName)) {
                           Object this$ipAddressList = this.getIpAddressList();
                           Object other$ipAddressList = other.getIpAddressList();
                           if (this$ipAddressList == null ? other$ipAddressList == null : this$ipAddressList.equals(other$ipAddressList)) {
                              Object this$serverName = this.getServerName();
                              Object other$serverName = other.getServerName();
                              return this$serverName == null ? other$serverName == null : this$serverName.equals(other$serverName);
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
      return other instanceof ThresholdUserResponse;
   }

   @Generated
   @Override
   public int hashCode() {
      int PRIME = 59;
      int result = 1;
      Object $count = this.getCount();
      result = result * 59 + ($count == null ? 43 : $count.hashCode());
      Object $embyInfoId = this.getEmbyInfoId();
      result = result * 59 + ($embyInfoId == null ? 43 : $embyInfoId.hashCode());
      Object $ipAddress = this.getIpAddress();
      result = result * 59 + ($ipAddress == null ? 43 : $ipAddress.hashCode());
      Object $city = this.getCity();
      result = result * 59 + ($city == null ? 43 : $city.hashCode());
      Object $cityList = this.getCityList();
      result = result * 59 + ($cityList == null ? 43 : $cityList.hashCode());
      Object $embyUserName = this.getEmbyUserName();
      result = result * 59 + ($embyUserName == null ? 43 : $embyUserName.hashCode());
      Object $ipAddressList = this.getIpAddressList();
      result = result * 59 + ($ipAddressList == null ? 43 : $ipAddressList.hashCode());
      Object $serverName = this.getServerName();
      return result * 59 + ($serverName == null ? 43 : $serverName.hashCode());
   }

   @Generated
   @Override
   public String toString() {
      return "ThresholdUserResponse(ipAddress="
         + this.getIpAddress()
         + ", city="
         + this.getCity()
         + ", cityList="
         + this.getCityList()
         + ", embyUserName="
         + this.getEmbyUserName()
         + ", count="
         + this.getCount()
         + ", ipAddressList="
         + this.getIpAddressList()
         + ", embyInfoId="
         + this.getEmbyInfoId()
         + ", serverName="
         + this.getServerName()
         + ")";
   }
}
