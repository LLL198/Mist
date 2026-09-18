package com.una.embyhub.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Generated;

@TableName("emby_ip_locations")
public class EmbyIpLocations extends BaseEntity implements Serializable {
   public static final String COL_COUNT = "count";
   public static final String COL_ABNORMAL = "abnormal";
   private static final long serialVersionUID = 1L;
   @TableId(
      value = "id",
      type = IdType.AUTO
   )
   private Long id;
   @TableField("ip_address")
   private String ipAddress;
   @TableField("country")
   private String country;
   @TableField("region")
   private String region;
   @TableField("city")
   private String city;
   @TableField("isp")
   private String isp;
   @TableField("emby_user_name")
   private String embyUserName;
   @TableField("client")
   private String client;
   @TableField("emby_info_id")
   private Long embyInfoId;
   public static final String COL_ID = "id";
   public static final String COL_IP_ADDRESS = "ip_address";
   public static final String COL_COUNTRY = "country";
   public static final String COL_REGION = "region";
   public static final String COL_CITY = "city";
   public static final String COL_ISP = "isp";
   public static final String COL_EMBY_USER_NAME = "emby_user_name";
   public static final String COL_CLIENT = "client";
   public static final String COL_EMBY_INFO_ID = "emby_info_id";
   public static final String COL_CREATE_DATETIME = "create_datetime";
   public static final String COL_UPDATE_DATETIME = "update_datetime";
   public static final String COL_CREATE_USER_NAME = "create_user_name";
   public static final String COL_UPDATE_USER_NAME = "update_user_name";
   public static final String COL_UPDATE_USER_ID = "update_user_id";
   public static final String COL_CREATE_USER_ID = "create_user_id";
   public static final String COL_DEL_FLAG = "del_flag";

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
   public Long getEmbyInfoId() {
      return this.embyInfoId;
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
   public void setEmbyInfoId(final Long embyInfoId) {
      this.embyInfoId = embyInfoId;
   }

   @Generated
   @Override
   public String toString() {
      return "EmbyIpLocations(id="
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
         + ", embyInfoId="
         + this.getEmbyInfoId()
         + ")";
   }

   @Generated
   @Override
   public boolean equals(final Object o) {
      if (o == this) {
         return true;
      } else if (!(o instanceof EmbyIpLocations other)) {
         return false;
      } else if (!other.canEqual(this)) {
         return false;
      } else if (!super.equals(o)) {
         return false;
      } else {
         Object this$id = this.getId();
         Object other$id = other.getId();
         if (this$id == null ? other$id == null : this$id.equals(other$id)) {
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
                              Object this$embyUserName = this.getEmbyUserName();
                              Object other$embyUserName = other.getEmbyUserName();
                              if (this$embyUserName == null ? other$embyUserName == null : this$embyUserName.equals(other$embyUserName)) {
                                 Object this$client = this.getClient();
                                 Object other$client = other.getClient();
                                 return this$client == null ? other$client == null : this$client.equals(other$client);
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
      return other instanceof EmbyIpLocations;
   }

   @Generated
   @Override
   public int hashCode() {
      int PRIME = 59;
      int result = super.hashCode();
      Object $id = this.getId();
      result = result * 59 + ($id == null ? 43 : $id.hashCode());
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
      Object $embyUserName = this.getEmbyUserName();
      result = result * 59 + ($embyUserName == null ? 43 : $embyUserName.hashCode());
      Object $client = this.getClient();
      return result * 59 + ($client == null ? 43 : $client.hashCode());
   }
}
