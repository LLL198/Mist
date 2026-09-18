package com.una.embyhub.model.dto.response.embyiplocations;

import com.diboot.core.binding.annotation.BindField;
import com.una.embyhub.model.entity.EmbyInfo;
import java.io.Serializable;
import java.util.Date;
import lombok.Generated;

public class EmbyIpLocationsResponse implements Serializable {
   private String ipAddress;
   private String country;
   private String region;
   private String city;
   private String isp;
   private String location;
   private String embyUserName;
   private String client;
   private Date lastPlayDate;
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
   public String getCountry() {
      return this.country;
   }

   @Generated
   public String getRegion() {
      return this.region;
   }

   @Generated
   public String getCity() {
      return this.city;
   }

   @Generated
   public String getIsp() {
      return this.isp;
   }

   @Generated
   public String getLocation() {
      return this.location;
   }

   @Generated
   public String getEmbyUserName() {
      return this.embyUserName;
   }

   @Generated
   public String getClient() {
      return this.client;
   }

   @Generated
   public Date getLastPlayDate() {
      return this.lastPlayDate;
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
   public void setCountry(final String country) {
      this.country = country;
   }

   @Generated
   public void setRegion(final String region) {
      this.region = region;
   }

   @Generated
   public void setCity(final String city) {
      this.city = city;
   }

   @Generated
   public void setIsp(final String isp) {
      this.isp = isp;
   }

   @Generated
   public void setLocation(final String location) {
      this.location = location;
   }

   @Generated
   public void setEmbyUserName(final String embyUserName) {
      this.embyUserName = embyUserName;
   }

   @Generated
   public void setClient(final String client) {
      this.client = client;
   }

   @Generated
   public void setLastPlayDate(final Date lastPlayDate) {
      this.lastPlayDate = lastPlayDate;
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
      } else if (!(o instanceof EmbyIpLocationsResponse other)) {
         return false;
      } else if (!other.canEqual(this)) {
         return false;
      } else {
         Object this$embyInfoId = this.getEmbyInfoId();
         Object other$embyInfoId = other.getEmbyInfoId();
         if (this$embyInfoId == null ? other$embyInfoId == null : this$embyInfoId.equals(other$embyInfoId)) {
            Object this$ipAddress = this.getIpAddress();
            Object other$ipAddress = other.getIpAddress();
            if (this$ipAddress == null ? other$ipAddress == null : this$ipAddress.equals(other$ipAddress)) {
               Object this$country = this.getCountry();
               Object other$country = other.getCountry();
               if (this$country == null ? other$country == null : this$country.equals(other$country)) {
                  Object this$region = this.getRegion();
                  Object other$region = other.getRegion();
                  if (this$region == null ? other$region == null : this$region.equals(other$region)) {
                     Object this$city = this.getCity();
                     Object other$city = other.getCity();
                     if (this$city == null ? other$city == null : this$city.equals(other$city)) {
                        Object this$isp = this.getIsp();
                        Object other$isp = other.getIsp();
                        if (this$isp == null ? other$isp == null : this$isp.equals(other$isp)) {
                           Object this$location = this.getLocation();
                           Object other$location = other.getLocation();
                           if (this$location == null ? other$location == null : this$location.equals(other$location)) {
                              Object this$embyUserName = this.getEmbyUserName();
                              Object other$embyUserName = other.getEmbyUserName();
                              if (this$embyUserName == null ? other$embyUserName == null : this$embyUserName.equals(other$embyUserName)) {
                                 Object this$client = this.getClient();
                                 Object other$client = other.getClient();
                                 if (this$client == null ? other$client == null : this$client.equals(other$client)) {
                                    Object this$lastPlayDate = this.getLastPlayDate();
                                    Object other$lastPlayDate = other.getLastPlayDate();
                                    if (this$lastPlayDate == null ? other$lastPlayDate == null : this$lastPlayDate.equals(other$lastPlayDate)) {
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
      return other instanceof EmbyIpLocationsResponse;
   }

   @Generated
   @Override
   public int hashCode() {
      int PRIME = 59;
      int result = 1;
      Object $embyInfoId = this.getEmbyInfoId();
      result = result * 59 + ($embyInfoId == null ? 43 : $embyInfoId.hashCode());
      Object $ipAddress = this.getIpAddress();
      result = result * 59 + ($ipAddress == null ? 43 : $ipAddress.hashCode());
      Object $country = this.getCountry();
      result = result * 59 + ($country == null ? 43 : $country.hashCode());
      Object $region = this.getRegion();
      result = result * 59 + ($region == null ? 43 : $region.hashCode());
      Object $city = this.getCity();
      result = result * 59 + ($city == null ? 43 : $city.hashCode());
      Object $isp = this.getIsp();
      result = result * 59 + ($isp == null ? 43 : $isp.hashCode());
      Object $location = this.getLocation();
      result = result * 59 + ($location == null ? 43 : $location.hashCode());
      Object $embyUserName = this.getEmbyUserName();
      result = result * 59 + ($embyUserName == null ? 43 : $embyUserName.hashCode());
      Object $client = this.getClient();
      result = result * 59 + ($client == null ? 43 : $client.hashCode());
      Object $lastPlayDate = this.getLastPlayDate();
      result = result * 59 + ($lastPlayDate == null ? 43 : $lastPlayDate.hashCode());
      Object $serverName = this.getServerName();
      return result * 59 + ($serverName == null ? 43 : $serverName.hashCode());
   }

   @Generated
   @Override
   public String toString() {
      return "EmbyIpLocationsResponse(ipAddress="
         + this.getIpAddress()
         + ", country="
         + this.getCountry()
         + ", region="
         + this.getRegion()
         + ", city="
         + this.getCity()
         + ", isp="
         + this.getIsp()
         + ", location="
         + this.getLocation()
         + ", embyUserName="
         + this.getEmbyUserName()
         + ", client="
         + this.getClient()
         + ", lastPlayDate="
         + this.getLastPlayDate()
         + ", embyInfoId="
         + this.getEmbyInfoId()
         + ", serverName="
         + this.getServerName()
         + ")";
   }
}
