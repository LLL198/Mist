package com.una.embyhub.model.dto.request.embyiplocations;

import java.io.Serializable;
import java.util.Date;
import lombok.Generated;

public class EmbyIpLocationsSave implements Serializable {
   private Long id;
   private String ipAddress;
   private String country;
   private String region;
   private String city;
   private String isp;
   private String embyUserName;
   private String client;
   private Date createDatetime;
   private Date updateDatetime;
   private String createUserName;
   private String updateUserName;
   private Long updateUserId;
   private Long createUserId;
   private Integer delFlag;

   @Generated
   public Long getId() {
      return this.id;
   }

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
   public String getEmbyUserName() {
      return this.embyUserName;
   }

   @Generated
   public String getClient() {
      return this.client;
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
   public void setId(final Long id) {
      this.id = id;
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
   public void setEmbyUserName(final String embyUserName) {
      this.embyUserName = embyUserName;
   }

   @Generated
   public void setClient(final String client) {
      this.client = client;
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
   @Override
   public boolean equals(final Object o) {
      if (o == this) {
         return true;
      } else if (!(o instanceof EmbyIpLocationsSave other)) {
         return false;
      } else if (!other.canEqual(this)) {
         return false;
      } else {
         Object this$id = this.getId();
         Object other$id = other.getId();
         if (this$id == null ? other$id == null : this$id.equals(other$id)) {
            Object this$updateUserId = this.getUpdateUserId();
            Object other$updateUserId = other.getUpdateUserId();
            if (this$updateUserId == null ? other$updateUserId == null : this$updateUserId.equals(other$updateUserId)) {
               Object this$createUserId = this.getCreateUserId();
               Object other$createUserId = other.getCreateUserId();
               if (this$createUserId == null ? other$createUserId == null : this$createUserId.equals(other$createUserId)) {
                  Object this$delFlag = this.getDelFlag();
                  Object other$delFlag = other.getDelFlag();
                  if (this$delFlag == null ? other$delFlag == null : this$delFlag.equals(other$delFlag)) {
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
                                    Object this$embyUserName = this.getEmbyUserName();
                                    Object other$embyUserName = other.getEmbyUserName();
                                    if (this$embyUserName == null ? other$embyUserName == null : this$embyUserName.equals(other$embyUserName)) {
                                       Object this$client = this.getClient();
                                       Object other$client = other.getClient();
                                       if (this$client == null ? other$client == null : this$client.equals(other$client)) {
                                          Object this$createDatetime = this.getCreateDatetime();
                                          Object other$createDatetime = other.getCreateDatetime();
                                          if (this$createDatetime == null ? other$createDatetime == null : this$createDatetime.equals(other$createDatetime)) {
                                             Object this$updateDatetime = this.getUpdateDatetime();
                                             Object other$updateDatetime = other.getUpdateDatetime();
                                             if (this$updateDatetime == null ? other$updateDatetime == null : this$updateDatetime.equals(other$updateDatetime)) {
                                                Object this$createUserName = this.getCreateUserName();
                                                Object other$createUserName = other.getCreateUserName();
                                                if (this$createUserName == null
                                                   ? other$createUserName == null
                                                   : this$createUserName.equals(other$createUserName)) {
                                                   Object this$updateUserName = this.getUpdateUserName();
                                                   Object other$updateUserName = other.getUpdateUserName();
                                                   return this$updateUserName == null
                                                      ? other$updateUserName == null
                                                      : this$updateUserName.equals(other$updateUserName);
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
      return other instanceof EmbyIpLocationsSave;
   }

   @Generated
   @Override
   public int hashCode() {
      int PRIME = 59;
      int result = 1;
      Object $id = this.getId();
      result = result * 59 + ($id == null ? 43 : $id.hashCode());
      Object $updateUserId = this.getUpdateUserId();
      result = result * 59 + ($updateUserId == null ? 43 : $updateUserId.hashCode());
      Object $createUserId = this.getCreateUserId();
      result = result * 59 + ($createUserId == null ? 43 : $createUserId.hashCode());
      Object $delFlag = this.getDelFlag();
      result = result * 59 + ($delFlag == null ? 43 : $delFlag.hashCode());
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
      Object $embyUserName = this.getEmbyUserName();
      result = result * 59 + ($embyUserName == null ? 43 : $embyUserName.hashCode());
      Object $client = this.getClient();
      result = result * 59 + ($client == null ? 43 : $client.hashCode());
      Object $createDatetime = this.getCreateDatetime();
      result = result * 59 + ($createDatetime == null ? 43 : $createDatetime.hashCode());
      Object $updateDatetime = this.getUpdateDatetime();
      result = result * 59 + ($updateDatetime == null ? 43 : $updateDatetime.hashCode());
      Object $createUserName = this.getCreateUserName();
      result = result * 59 + ($createUserName == null ? 43 : $createUserName.hashCode());
      Object $updateUserName = this.getUpdateUserName();
      return result * 59 + ($updateUserName == null ? 43 : $updateUserName.hashCode());
   }

   @Generated
   @Override
   public String toString() {
      return "EmbyIpLocationsSave(id="
         + this.getId()
         + ", ipAddress="
         + this.getIpAddress()
         + ", country="
         + this.getCountry()
         + ", region="
         + this.getRegion()
         + ", city="
         + this.getCity()
         + ", isp="
         + this.getIsp()
         + ", embyUserName="
         + this.getEmbyUserName()
         + ", client="
         + this.getClient()
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
         + ")";
   }
}
