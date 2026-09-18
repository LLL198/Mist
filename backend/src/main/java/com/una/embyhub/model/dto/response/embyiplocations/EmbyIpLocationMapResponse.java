package com.una.embyhub.model.dto.response.embyiplocations;

import java.io.Serializable;
import lombok.Generated;

public class EmbyIpLocationMapResponse implements Serializable {
   private static final long serialVersionUID = 1L;
   private String name;
   private String country;
   private String region;
   private String city;
   private Long value;
   private String geocoding;

   @Generated
   public String getName() {
      return this.name;
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
   public Long getValue() {
      return this.value;
   }

   @Generated
   public String getGeocoding() {
      return this.geocoding;
   }

   @Generated
   public void setName(final String name) {
      this.name = name;
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
   public void setValue(final Long value) {
      this.value = value;
   }

   @Generated
   public void setGeocoding(final String geocoding) {
      this.geocoding = geocoding;
   }

   @Generated
   @Override
   public boolean equals(final Object o) {
      if (o == this) {
         return true;
      } else if (!(o instanceof EmbyIpLocationMapResponse other)) {
         return false;
      } else if (!other.canEqual(this)) {
         return false;
      } else {
         Object this$value = this.getValue();
         Object other$value = other.getValue();
         if (this$value == null ? other$value == null : this$value.equals(other$value)) {
            Object this$name = this.getName();
            Object other$name = other.getName();
            if (this$name == null ? other$name == null : this$name.equals(other$name)) {
               Object this$country = this.getCountry();
               Object other$country = other.getCountry();
               if (this$country == null ? other$country == null : this$country.equals(other$country)) {
                  Object this$region = this.getRegion();
                  Object other$region = other.getRegion();
                  if (this$region == null ? other$region == null : this$region.equals(other$region)) {
                     Object this$city = this.getCity();
                     Object other$city = other.getCity();
                     if (this$city == null ? other$city == null : this$city.equals(other$city)) {
                        Object this$geocoding = this.getGeocoding();
                        Object other$geocoding = other.getGeocoding();
                        return this$geocoding == null ? other$geocoding == null : this$geocoding.equals(other$geocoding);
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
      return other instanceof EmbyIpLocationMapResponse;
   }

   @Generated
   @Override
   public int hashCode() {
      int PRIME = 59;
      int result = 1;
      Object $value = this.getValue();
      result = result * 59 + ($value == null ? 43 : $value.hashCode());
      Object $name = this.getName();
      result = result * 59 + ($name == null ? 43 : $name.hashCode());
      Object $country = this.getCountry();
      result = result * 59 + ($country == null ? 43 : $country.hashCode());
      Object $region = this.getRegion();
      result = result * 59 + ($region == null ? 43 : $region.hashCode());
      Object $city = this.getCity();
      result = result * 59 + ($city == null ? 43 : $city.hashCode());
      Object $geocoding = this.getGeocoding();
      return result * 59 + ($geocoding == null ? 43 : $geocoding.hashCode());
   }

   @Generated
   @Override
   public String toString() {
      return "EmbyIpLocationMapResponse(name="
         + this.getName()
         + ", country="
         + this.getCountry()
         + ", region="
         + this.getRegion()
         + ", city="
         + this.getCity()
         + ", value="
         + this.getValue()
         + ", geocoding="
         + this.getGeocoding()
         + ")";
   }
}
