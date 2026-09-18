package com.una.embyhub.movie.model;

import jakarta.validation.constraints.NotBlank;
import java.io.Serializable;
import lombok.Generated;

public class MoviePtSiteSaveRequest implements Serializable {
   private static final long serialVersionUID = 1L;
   private Long id;
   @NotBlank(
      message = "站点名称不能为空"
   )
   private String name;
   @NotBlank(
      message = "站点地址不能为空"
   )
   private String baseUrl;
   private String cookies;
   private String token;
   private String siteType;
   private Integer enabled;

   @Generated
   public Long getId() {
      return this.id;
   }

   @Generated
   public String getName() {
      return this.name;
   }

   @Generated
   public String getBaseUrl() {
      return this.baseUrl;
   }

   @Generated
   public String getCookies() {
      return this.cookies;
   }

   @Generated
   public String getToken() {
      return this.token;
   }

   @Generated
   public String getSiteType() {
      return this.siteType;
   }

   @Generated
   public Integer getEnabled() {
      return this.enabled;
   }

   @Generated
   public void setId(final Long id) {
      this.id = id;
   }

   @Generated
   public void setName(final String name) {
      this.name = name;
   }

   @Generated
   public void setBaseUrl(final String baseUrl) {
      this.baseUrl = baseUrl;
   }

   @Generated
   public void setCookies(final String cookies) {
      this.cookies = cookies;
   }

   @Generated
   public void setToken(final String token) {
      this.token = token;
   }

   @Generated
   public void setSiteType(final String siteType) {
      this.siteType = siteType;
   }

   @Generated
   public void setEnabled(final Integer enabled) {
      this.enabled = enabled;
   }

   @Generated
   @Override
   public boolean equals(final Object o) {
      if (o == this) {
         return true;
      } else if (!(o instanceof MoviePtSiteSaveRequest other)) {
         return false;
      } else if (!other.canEqual(this)) {
         return false;
      } else {
         Object this$id = this.getId();
         Object other$id = other.getId();
         if (this$id == null ? other$id == null : this$id.equals(other$id)) {
            Object this$enabled = this.getEnabled();
            Object other$enabled = other.getEnabled();
            if (this$enabled == null ? other$enabled == null : this$enabled.equals(other$enabled)) {
               Object this$name = this.getName();
               Object other$name = other.getName();
               if (this$name == null ? other$name == null : this$name.equals(other$name)) {
                  Object this$baseUrl = this.getBaseUrl();
                  Object other$baseUrl = other.getBaseUrl();
                  if (this$baseUrl == null ? other$baseUrl == null : this$baseUrl.equals(other$baseUrl)) {
                     Object this$cookies = this.getCookies();
                     Object other$cookies = other.getCookies();
                     if (this$cookies == null ? other$cookies == null : this$cookies.equals(other$cookies)) {
                        Object this$token = this.getToken();
                        Object other$token = other.getToken();
                        if (this$token == null ? other$token == null : this$token.equals(other$token)) {
                           Object this$siteType = this.getSiteType();
                           Object other$siteType = other.getSiteType();
                           return this$siteType == null ? other$siteType == null : this$siteType.equals(other$siteType);
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
      return other instanceof MoviePtSiteSaveRequest;
   }

   @Generated
   @Override
   public int hashCode() {
      int PRIME = 59;
      int result = 1;
      Object $id = this.getId();
      result = result * 59 + ($id == null ? 43 : $id.hashCode());
      Object $enabled = this.getEnabled();
      result = result * 59 + ($enabled == null ? 43 : $enabled.hashCode());
      Object $name = this.getName();
      result = result * 59 + ($name == null ? 43 : $name.hashCode());
      Object $baseUrl = this.getBaseUrl();
      result = result * 59 + ($baseUrl == null ? 43 : $baseUrl.hashCode());
      Object $cookies = this.getCookies();
      result = result * 59 + ($cookies == null ? 43 : $cookies.hashCode());
      Object $token = this.getToken();
      result = result * 59 + ($token == null ? 43 : $token.hashCode());
      Object $siteType = this.getSiteType();
      return result * 59 + ($siteType == null ? 43 : $siteType.hashCode());
   }

   @Generated
   @Override
   public String toString() {
      return "MoviePtSiteSaveRequest(id="
         + this.getId()
         + ", name="
         + this.getName()
         + ", baseUrl="
         + this.getBaseUrl()
         + ", cookies="
         + this.getCookies()
         + ", token="
         + this.getToken()
         + ", siteType="
         + this.getSiteType()
         + ", enabled="
         + this.getEnabled()
         + ")";
   }
}
