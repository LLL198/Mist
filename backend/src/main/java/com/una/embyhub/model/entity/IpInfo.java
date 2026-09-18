package com.una.embyhub.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Generated;

@TableName("ip_info")
public class IpInfo extends BaseEntity implements Serializable {
   private static final long serialVersionUID = 1L;
   @TableId(
      value = "id",
      type = IdType.AUTO
   )
   private Long id;
   @TableField("ip")
   private String ip;
   @TableField("pro")
   private String pro;
   @TableField("pro_code")
   private String proCode;
   @TableField("city")
   private String city;
   @TableField("city_code")
   private String cityCode;
   @TableField("region")
   private String region;
   @TableField("region_code")
   private String regionCode;
   @TableField("addr")
   private String addr;
   @TableField("region_names")
   private String regionNames;
   @TableField("err")
   private String err;
   @TableField("isp")
   private String isp;
   public static final String COL_ID = "id";
   public static final String COL_IP = "ip";
   public static final String COL_PRO = "pro";
   public static final String COL_PRO_CODE = "pro_code";
   public static final String COL_CITY = "city";
   public static final String COL_CITY_CODE = "city_code";
   public static final String COL_REGION = "region";
   public static final String COL_REGION_CODE = "region_code";
   public static final String COL_ADDR = "addr";
   public static final String COL_REGION_NAMES = "region_names";
   public static final String COL_ERR = "err";
   public static final String COL_ISP = "isp";
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
   public String getIp() {
      return this.ip;
   }

   @Generated
   public String getPro() {
      return this.pro;
   }

   @Generated
   public String getProCode() {
      return this.proCode;
   }

   @Generated
   public String getCity() {
      return this.city;
   }

   @Generated
   public String getCityCode() {
      return this.cityCode;
   }

   @Generated
   public String getRegion() {
      return this.region;
   }

   @Generated
   public String getRegionCode() {
      return this.regionCode;
   }

   @Generated
   public String getAddr() {
      return this.addr;
   }

   @Generated
   public String getRegionNames() {
      return this.regionNames;
   }

   @Generated
   public String getErr() {
      return this.err;
   }

   @Generated
   public String getIsp() {
      return this.isp;
   }

   @Generated
   public void setId(final Long id) {
      this.id = id;
   }

   @Generated
   public void setIp(final String ip) {
      this.ip = ip;
   }

   @Generated
   public void setPro(final String pro) {
      this.pro = pro;
   }

   @Generated
   public void setProCode(final String proCode) {
      this.proCode = proCode;
   }

   @Generated
   public void setCity(final String city) {
      this.city = city;
   }

   @Generated
   public void setCityCode(final String cityCode) {
      this.cityCode = cityCode;
   }

   @Generated
   public void setRegion(final String region) {
      this.region = region;
   }

   @Generated
   public void setRegionCode(final String regionCode) {
      this.regionCode = regionCode;
   }

   @Generated
   public void setAddr(final String addr) {
      this.addr = addr;
   }

   @Generated
   public void setRegionNames(final String regionNames) {
      this.regionNames = regionNames;
   }

   @Generated
   public void setErr(final String err) {
      this.err = err;
   }

   @Generated
   public void setIsp(final String isp) {
      this.isp = isp;
   }

   @Generated
   @Override
   public String toString() {
      return "IpInfo(id="
         + this.getId()
         + ", ip="
         + this.getIp()
         + ", pro="
         + this.getPro()
         + ", proCode="
         + this.getProCode()
         + ", city="
         + this.getCity()
         + ", cityCode="
         + this.getCityCode()
         + ", region="
         + this.getRegion()
         + ", regionCode="
         + this.getRegionCode()
         + ", addr="
         + this.getAddr()
         + ", regionNames="
         + this.getRegionNames()
         + ", err="
         + this.getErr()
         + ", isp="
         + this.getIsp()
         + ")";
   }

   @Generated
   @Override
   public boolean equals(final Object o) {
      if (o == this) {
         return true;
      } else if (!(o instanceof IpInfo other)) {
         return false;
      } else if (!other.canEqual(this)) {
         return false;
      } else if (!super.equals(o)) {
         return false;
      } else {
         Object this$id = this.getId();
         Object other$id = other.getId();
         if (this$id == null ? other$id == null : this$id.equals(other$id)) {
            Object this$ip = this.getIp();
            Object other$ip = other.getIp();
            if (this$ip == null ? other$ip == null : this$ip.equals(other$ip)) {
               Object this$pro = this.getPro();
               Object other$pro = other.getPro();
               if (this$pro == null ? other$pro == null : this$pro.equals(other$pro)) {
                  Object this$proCode = this.getProCode();
                  Object other$proCode = other.getProCode();
                  if (this$proCode == null ? other$proCode == null : this$proCode.equals(other$proCode)) {
                     Object this$city = this.getCity();
                     Object other$city = other.getCity();
                     if (this$city == null ? other$city == null : this$city.equals(other$city)) {
                        Object this$cityCode = this.getCityCode();
                        Object other$cityCode = other.getCityCode();
                        if (this$cityCode == null ? other$cityCode == null : this$cityCode.equals(other$cityCode)) {
                           Object this$region = this.getRegion();
                           Object other$region = other.getRegion();
                           if (this$region == null ? other$region == null : this$region.equals(other$region)) {
                              Object this$regionCode = this.getRegionCode();
                              Object other$regionCode = other.getRegionCode();
                              if (this$regionCode == null ? other$regionCode == null : this$regionCode.equals(other$regionCode)) {
                                 Object this$addr = this.getAddr();
                                 Object other$addr = other.getAddr();
                                 if (this$addr == null ? other$addr == null : this$addr.equals(other$addr)) {
                                    Object this$regionNames = this.getRegionNames();
                                    Object other$regionNames = other.getRegionNames();
                                    if (this$regionNames == null ? other$regionNames == null : this$regionNames.equals(other$regionNames)) {
                                       Object this$err = this.getErr();
                                       Object other$err = other.getErr();
                                       if (this$err == null ? other$err == null : this$err.equals(other$err)) {
                                          Object this$isp = this.getIsp();
                                          Object other$isp = other.getIsp();
                                          return this$isp == null ? other$isp == null : this$isp.equals(other$isp);
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
      return other instanceof IpInfo;
   }

   @Generated
   @Override
   public int hashCode() {
      int PRIME = 59;
      int result = super.hashCode();
      Object $id = this.getId();
      result = result * 59 + ($id == null ? 43 : $id.hashCode());
      Object $ip = this.getIp();
      result = result * 59 + ($ip == null ? 43 : $ip.hashCode());
      Object $pro = this.getPro();
      result = result * 59 + ($pro == null ? 43 : $pro.hashCode());
      Object $proCode = this.getProCode();
      result = result * 59 + ($proCode == null ? 43 : $proCode.hashCode());
      Object $city = this.getCity();
      result = result * 59 + ($city == null ? 43 : $city.hashCode());
      Object $cityCode = this.getCityCode();
      result = result * 59 + ($cityCode == null ? 43 : $cityCode.hashCode());
      Object $region = this.getRegion();
      result = result * 59 + ($region == null ? 43 : $region.hashCode());
      Object $regionCode = this.getRegionCode();
      result = result * 59 + ($regionCode == null ? 43 : $regionCode.hashCode());
      Object $addr = this.getAddr();
      result = result * 59 + ($addr == null ? 43 : $addr.hashCode());
      Object $regionNames = this.getRegionNames();
      result = result * 59 + ($regionNames == null ? 43 : $regionNames.hashCode());
      Object $err = this.getErr();
      result = result * 59 + ($err == null ? 43 : $err.hashCode());
      Object $isp = this.getIsp();
      return result * 59 + ($isp == null ? 43 : $isp.hashCode());
   }
}
