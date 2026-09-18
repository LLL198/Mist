package com.una.embyhub.model.dto.request.embyinfo;

import java.io.Serializable;
import lombok.Generated;

public class EmbyInfoUpdate implements Serializable {
   private Long id;
   private String embyUrl;
   private String embyOpenUrl;
   private String embyPort;
   private String embyAgreement;
   private String serverName;
   private String embyServerId;
   private Integer status;
   private Long userId;
   private String embyApikey;
   private Integer enabled;
   private String copyfromuserid;
   private Integer spread;
   private String adminQueryUserid;

   @Generated
   public Long getId() {
      return this.id;
   }

   @Generated
   public String getEmbyUrl() {
      return this.embyUrl;
   }

   @Generated
   public String getEmbyOpenUrl() {
      return this.embyOpenUrl;
   }

   @Generated
   public String getEmbyPort() {
      return this.embyPort;
   }

   @Generated
   public String getEmbyAgreement() {
      return this.embyAgreement;
   }

   @Generated
   public String getServerName() {
      return this.serverName;
   }

   @Generated
   public String getEmbyServerId() {
      return this.embyServerId;
   }

   @Generated
   public Integer getStatus() {
      return this.status;
   }

   @Generated
   public Long getUserId() {
      return this.userId;
   }

   @Generated
   public String getEmbyApikey() {
      return this.embyApikey;
   }

   @Generated
   public Integer getEnabled() {
      return this.enabled;
   }

   @Generated
   public String getCopyfromuserid() {
      return this.copyfromuserid;
   }

   @Generated
   public Integer getSpread() {
      return this.spread;
   }

   @Generated
   public String getAdminQueryUserid() {
      return this.adminQueryUserid;
   }

   @Generated
   public void setId(final Long id) {
      this.id = id;
   }

   @Generated
   public void setEmbyUrl(final String embyUrl) {
      this.embyUrl = embyUrl;
   }

   @Generated
   public void setEmbyOpenUrl(final String embyOpenUrl) {
      this.embyOpenUrl = embyOpenUrl;
   }

   @Generated
   public void setEmbyPort(final String embyPort) {
      this.embyPort = embyPort;
   }

   @Generated
   public void setEmbyAgreement(final String embyAgreement) {
      this.embyAgreement = embyAgreement;
   }

   @Generated
   public void setServerName(final String serverName) {
      this.serverName = serverName;
   }

   @Generated
   public void setEmbyServerId(final String embyServerId) {
      this.embyServerId = embyServerId;
   }

   @Generated
   public void setStatus(final Integer status) {
      this.status = status;
   }

   @Generated
   public void setUserId(final Long userId) {
      this.userId = userId;
   }

   @Generated
   public void setEmbyApikey(final String embyApikey) {
      this.embyApikey = embyApikey;
   }

   @Generated
   public void setEnabled(final Integer enabled) {
      this.enabled = enabled;
   }

   @Generated
   public void setCopyfromuserid(final String copyfromuserid) {
      this.copyfromuserid = copyfromuserid;
   }

   @Generated
   public void setSpread(final Integer spread) {
      this.spread = spread;
   }

   @Generated
   public void setAdminQueryUserid(final String adminQueryUserid) {
      this.adminQueryUserid = adminQueryUserid;
   }

   @Generated
   @Override
   public boolean equals(final Object o) {
      if (o == this) {
         return true;
      } else if (!(o instanceof EmbyInfoUpdate other)) {
         return false;
      } else if (!other.canEqual(this)) {
         return false;
      } else {
         Object this$id = this.getId();
         Object other$id = other.getId();
         if (this$id == null ? other$id == null : this$id.equals(other$id)) {
            Object this$status = this.getStatus();
            Object other$status = other.getStatus();
            if (this$status == null ? other$status == null : this$status.equals(other$status)) {
               Object this$userId = this.getUserId();
               Object other$userId = other.getUserId();
               if (this$userId == null ? other$userId == null : this$userId.equals(other$userId)) {
                  Object this$enabled = this.getEnabled();
                  Object other$enabled = other.getEnabled();
                  if (this$enabled == null ? other$enabled == null : this$enabled.equals(other$enabled)) {
                     Object this$spread = this.getSpread();
                     Object other$spread = other.getSpread();
                     if (this$spread == null ? other$spread == null : this$spread.equals(other$spread)) {
                        Object this$embyUrl = this.getEmbyUrl();
                        Object other$embyUrl = other.getEmbyUrl();
                        if (this$embyUrl == null ? other$embyUrl == null : this$embyUrl.equals(other$embyUrl)) {
                           Object this$embyOpenUrl = this.getEmbyOpenUrl();
                           Object other$embyOpenUrl = other.getEmbyOpenUrl();
                           if (this$embyOpenUrl == null ? other$embyOpenUrl == null : this$embyOpenUrl.equals(other$embyOpenUrl)) {
                              Object this$embyPort = this.getEmbyPort();
                              Object other$embyPort = other.getEmbyPort();
                              if (this$embyPort == null ? other$embyPort == null : this$embyPort.equals(other$embyPort)) {
                                 Object this$embyAgreement = this.getEmbyAgreement();
                                 Object other$embyAgreement = other.getEmbyAgreement();
                                 if (this$embyAgreement == null ? other$embyAgreement == null : this$embyAgreement.equals(other$embyAgreement)) {
                                    Object this$serverName = this.getServerName();
                                    Object other$serverName = other.getServerName();
                                    if (this$serverName == null ? other$serverName == null : this$serverName.equals(other$serverName)) {
                                       Object this$embyServerId = this.getEmbyServerId();
                                       Object other$embyServerId = other.getEmbyServerId();
                                       if (this$embyServerId == null ? other$embyServerId == null : this$embyServerId.equals(other$embyServerId)) {
                                          Object this$embyApikey = this.getEmbyApikey();
                                          Object other$embyApikey = other.getEmbyApikey();
                                          if (this$embyApikey == null ? other$embyApikey == null : this$embyApikey.equals(other$embyApikey)) {
                                             Object this$copyfromuserid = this.getCopyfromuserid();
                                             Object other$copyfromuserid = other.getCopyfromuserid();
                                             if (this$copyfromuserid == null ? other$copyfromuserid == null : this$copyfromuserid.equals(other$copyfromuserid)) {
                                                Object this$adminQueryUserid = this.getAdminQueryUserid();
                                                Object other$adminQueryUserid = other.getAdminQueryUserid();
                                                return this$adminQueryUserid == null
                                                   ? other$adminQueryUserid == null
                                                   : this$adminQueryUserid.equals(other$adminQueryUserid);
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
      return other instanceof EmbyInfoUpdate;
   }

   @Generated
   @Override
   public int hashCode() {
      int PRIME = 59;
      int result = 1;
      Object $id = this.getId();
      result = result * 59 + ($id == null ? 43 : $id.hashCode());
      Object $status = this.getStatus();
      result = result * 59 + ($status == null ? 43 : $status.hashCode());
      Object $userId = this.getUserId();
      result = result * 59 + ($userId == null ? 43 : $userId.hashCode());
      Object $enabled = this.getEnabled();
      result = result * 59 + ($enabled == null ? 43 : $enabled.hashCode());
      Object $spread = this.getSpread();
      result = result * 59 + ($spread == null ? 43 : $spread.hashCode());
      Object $embyUrl = this.getEmbyUrl();
      result = result * 59 + ($embyUrl == null ? 43 : $embyUrl.hashCode());
      Object $embyOpenUrl = this.getEmbyOpenUrl();
      result = result * 59 + ($embyOpenUrl == null ? 43 : $embyOpenUrl.hashCode());
      Object $embyPort = this.getEmbyPort();
      result = result * 59 + ($embyPort == null ? 43 : $embyPort.hashCode());
      Object $embyAgreement = this.getEmbyAgreement();
      result = result * 59 + ($embyAgreement == null ? 43 : $embyAgreement.hashCode());
      Object $serverName = this.getServerName();
      result = result * 59 + ($serverName == null ? 43 : $serverName.hashCode());
      Object $embyServerId = this.getEmbyServerId();
      result = result * 59 + ($embyServerId == null ? 43 : $embyServerId.hashCode());
      Object $embyApikey = this.getEmbyApikey();
      result = result * 59 + ($embyApikey == null ? 43 : $embyApikey.hashCode());
      Object $copyfromuserid = this.getCopyfromuserid();
      result = result * 59 + ($copyfromuserid == null ? 43 : $copyfromuserid.hashCode());
      Object $adminQueryUserid = this.getAdminQueryUserid();
      return result * 59 + ($adminQueryUserid == null ? 43 : $adminQueryUserid.hashCode());
   }

   @Generated
   @Override
   public String toString() {
      return "EmbyInfoUpdate(id="
         + this.getId()
         + ", embyUrl="
         + this.getEmbyUrl()
         + ", embyOpenUrl="
         + this.getEmbyOpenUrl()
         + ", embyPort="
         + this.getEmbyPort()
         + ", embyAgreement="
         + this.getEmbyAgreement()
         + ", serverName="
         + this.getServerName()
         + ", embyServerId="
         + this.getEmbyServerId()
         + ", status="
         + this.getStatus()
         + ", userId="
         + this.getUserId()
         + ", embyApikey="
         + this.getEmbyApikey()
         + ", enabled="
         + this.getEnabled()
         + ", copyfromuserid="
         + this.getCopyfromuserid()
         + ", spread="
         + this.getSpread()
         + ", adminQueryUserid="
         + this.getAdminQueryUserid()
         + ")";
   }
}
