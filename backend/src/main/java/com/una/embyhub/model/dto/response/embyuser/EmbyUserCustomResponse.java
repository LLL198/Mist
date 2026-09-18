package com.una.embyhub.model.dto.response.embyuser;

import com.una.embyhub.config.common.enums.HostLineTypeEnum;
import com.una.embyhub.config.common.enums.RegisterChannelEnum;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import lombok.Generated;

public class EmbyUserCustomResponse implements Serializable {
   private Long id;
   private Integer isAdmin;
   private Integer isPrimaryAdmin;
   private List<String> menuPermissions;
   private Integer userStatus;
   private String userStatusName;
   private Date expirationDate;
   private String embyUserName;
   private String remarks;
   private Integer requestPackagesCount;
   private String email;
   private String mobile;
   private String gender;
   private Date birthday;
   private String interests;
   private String avatar;
   private Integer registerChannel;
   private Integer hostLineType;
   private String hostLineTypeName;
   private String registerChannelName;
   private Integer isDistributor;
   private List<UserOauthBindingResponse> oauthBindings;
   private List<EmbyUserCustomResponse.ServerOption> servers;
   private String token;
   private String theme;

   public void setUserStatus(Integer userStatus) {
      this.userStatus = userStatus;
      if (userStatus == 0) {
         this.userStatusName = "启用";
      }

      if (userStatus == 1) {
         this.userStatusName = "禁用";
      }
   }

   public void setHostLineType(Integer hostLineType) {
      this.hostLineType = HostLineTypeEnum.normalize(hostLineType);
      this.hostLineTypeName = HostLineTypeEnum.resolveUserRoleLabel(hostLineType);
   }

   public void setRegisterChannel(Integer registerChannel) {
      this.registerChannel = registerChannel;
      this.registerChannelName = RegisterChannelEnum.resolveLabel(registerChannel);
   }

   @Generated
   public Long getId() {
      return this.id;
   }

   @Generated
   public Integer getIsAdmin() {
      return this.isAdmin;
   }

   @Generated
   public Integer getIsPrimaryAdmin() {
      return this.isPrimaryAdmin;
   }

   @Generated
   public List<String> getMenuPermissions() {
      return this.menuPermissions;
   }

   @Generated
   public Integer getUserStatus() {
      return this.userStatus;
   }

   @Generated
   public String getUserStatusName() {
      return this.userStatusName;
   }

   @Generated
   public Date getExpirationDate() {
      return this.expirationDate;
   }

   @Generated
   public String getEmbyUserName() {
      return this.embyUserName;
   }

   @Generated
   public String getRemarks() {
      return this.remarks;
   }

   @Generated
   public Integer getRequestPackagesCount() {
      return this.requestPackagesCount;
   }

   @Generated
   public String getEmail() {
      return this.email;
   }

   @Generated
   public String getMobile() {
      return this.mobile;
   }

   @Generated
   public String getGender() {
      return this.gender;
   }

   @Generated
   public Date getBirthday() {
      return this.birthday;
   }

   @Generated
   public String getInterests() {
      return this.interests;
   }

   @Generated
   public String getAvatar() {
      return this.avatar;
   }

   @Generated
   public Integer getRegisterChannel() {
      return this.registerChannel;
   }

   @Generated
   public Integer getHostLineType() {
      return this.hostLineType;
   }

   @Generated
   public String getHostLineTypeName() {
      return this.hostLineTypeName;
   }

   @Generated
   public String getRegisterChannelName() {
      return this.registerChannelName;
   }

   @Generated
   public Integer getIsDistributor() {
      return this.isDistributor;
   }

   @Generated
   public List<UserOauthBindingResponse> getOauthBindings() {
      return this.oauthBindings;
   }

   @Generated
   public List<EmbyUserCustomResponse.ServerOption> getServers() {
      return this.servers;
   }

   @Generated
   public String getToken() {
      return this.token;
   }

   @Generated
   public String getTheme() {
      return this.theme;
   }

   @Generated
   public void setId(final Long id) {
      this.id = id;
   }

   @Generated
   public void setIsAdmin(final Integer isAdmin) {
      this.isAdmin = isAdmin;
   }

   @Generated
   public void setIsPrimaryAdmin(final Integer isPrimaryAdmin) {
      this.isPrimaryAdmin = isPrimaryAdmin;
   }

   @Generated
   public void setMenuPermissions(final List<String> menuPermissions) {
      this.menuPermissions = menuPermissions;
   }

   @Generated
   public void setUserStatusName(final String userStatusName) {
      this.userStatusName = userStatusName;
   }

   @Generated
   public void setExpirationDate(final Date expirationDate) {
      this.expirationDate = expirationDate;
   }

   @Generated
   public void setEmbyUserName(final String embyUserName) {
      this.embyUserName = embyUserName;
   }

   @Generated
   public void setRemarks(final String remarks) {
      this.remarks = remarks;
   }

   @Generated
   public void setRequestPackagesCount(final Integer requestPackagesCount) {
      this.requestPackagesCount = requestPackagesCount;
   }

   @Generated
   public void setEmail(final String email) {
      this.email = email;
   }

   @Generated
   public void setMobile(final String mobile) {
      this.mobile = mobile;
   }

   @Generated
   public void setGender(final String gender) {
      this.gender = gender;
   }

   @Generated
   public void setBirthday(final Date birthday) {
      this.birthday = birthday;
   }

   @Generated
   public void setInterests(final String interests) {
      this.interests = interests;
   }

   @Generated
   public void setAvatar(final String avatar) {
      this.avatar = avatar;
   }

   @Generated
   public void setHostLineTypeName(final String hostLineTypeName) {
      this.hostLineTypeName = hostLineTypeName;
   }

   @Generated
   public void setRegisterChannelName(final String registerChannelName) {
      this.registerChannelName = registerChannelName;
   }

   @Generated
   public void setIsDistributor(final Integer isDistributor) {
      this.isDistributor = isDistributor;
   }

   @Generated
   public void setOauthBindings(final List<UserOauthBindingResponse> oauthBindings) {
      this.oauthBindings = oauthBindings;
   }

   @Generated
   public void setServers(final List<EmbyUserCustomResponse.ServerOption> servers) {
      this.servers = servers;
   }

   @Generated
   public void setToken(final String token) {
      this.token = token;
   }

   @Generated
   public void setTheme(final String theme) {
      this.theme = theme;
   }

   @Generated
   @Override
   public boolean equals(final Object o) {
      if (o == this) {
         return true;
      } else if (!(o instanceof EmbyUserCustomResponse other)) {
         return false;
      } else if (!other.canEqual(this)) {
         return false;
      } else {
         Object this$id = this.getId();
         Object other$id = other.getId();
         if (this$id == null ? other$id == null : this$id.equals(other$id)) {
            Object this$isAdmin = this.getIsAdmin();
            Object other$isAdmin = other.getIsAdmin();
            if (this$isAdmin == null ? other$isAdmin == null : this$isAdmin.equals(other$isAdmin)) {
               Object this$isPrimaryAdmin = this.getIsPrimaryAdmin();
               Object other$isPrimaryAdmin = other.getIsPrimaryAdmin();
               if (this$isPrimaryAdmin == null ? other$isPrimaryAdmin == null : this$isPrimaryAdmin.equals(other$isPrimaryAdmin)) {
                  Object this$userStatus = this.getUserStatus();
                  Object other$userStatus = other.getUserStatus();
                  if (this$userStatus == null ? other$userStatus == null : this$userStatus.equals(other$userStatus)) {
                     Object this$requestPackagesCount = this.getRequestPackagesCount();
                     Object other$requestPackagesCount = other.getRequestPackagesCount();
                     if (this$requestPackagesCount == null ? other$requestPackagesCount == null : this$requestPackagesCount.equals(other$requestPackagesCount)) {
                        Object this$registerChannel = this.getRegisterChannel();
                        Object other$registerChannel = other.getRegisterChannel();
                        if (this$registerChannel == null ? other$registerChannel == null : this$registerChannel.equals(other$registerChannel)) {
                           Object this$hostLineType = this.getHostLineType();
                           Object other$hostLineType = other.getHostLineType();
                           if (this$hostLineType == null ? other$hostLineType == null : this$hostLineType.equals(other$hostLineType)) {
                              Object this$isDistributor = this.getIsDistributor();
                              Object other$isDistributor = other.getIsDistributor();
                              if (this$isDistributor == null ? other$isDistributor == null : this$isDistributor.equals(other$isDistributor)) {
                                 Object this$menuPermissions = this.getMenuPermissions();
                                 Object other$menuPermissions = other.getMenuPermissions();
                                 if (this$menuPermissions == null ? other$menuPermissions == null : this$menuPermissions.equals(other$menuPermissions)) {
                                    Object this$userStatusName = this.getUserStatusName();
                                    Object other$userStatusName = other.getUserStatusName();
                                    if (this$userStatusName == null ? other$userStatusName == null : this$userStatusName.equals(other$userStatusName)) {
                                       Object this$expirationDate = this.getExpirationDate();
                                       Object other$expirationDate = other.getExpirationDate();
                                       if (this$expirationDate == null ? other$expirationDate == null : this$expirationDate.equals(other$expirationDate)) {
                                          Object this$embyUserName = this.getEmbyUserName();
                                          Object other$embyUserName = other.getEmbyUserName();
                                          if (this$embyUserName == null ? other$embyUserName == null : this$embyUserName.equals(other$embyUserName)) {
                                             Object this$remarks = this.getRemarks();
                                             Object other$remarks = other.getRemarks();
                                             if (this$remarks == null ? other$remarks == null : this$remarks.equals(other$remarks)) {
                                                Object this$email = this.getEmail();
                                                Object other$email = other.getEmail();
                                                if (this$email == null ? other$email == null : this$email.equals(other$email)) {
                                                   Object this$mobile = this.getMobile();
                                                   Object other$mobile = other.getMobile();
                                                   if (this$mobile == null ? other$mobile == null : this$mobile.equals(other$mobile)) {
                                                      Object this$gender = this.getGender();
                                                      Object other$gender = other.getGender();
                                                      if (this$gender == null ? other$gender == null : this$gender.equals(other$gender)) {
                                                         Object this$birthday = this.getBirthday();
                                                         Object other$birthday = other.getBirthday();
                                                         if (this$birthday == null ? other$birthday == null : this$birthday.equals(other$birthday)) {
                                                            Object this$interests = this.getInterests();
                                                            Object other$interests = other.getInterests();
                                                            if (this$interests == null ? other$interests == null : this$interests.equals(other$interests)) {
                                                               Object this$avatar = this.getAvatar();
                                                               Object other$avatar = other.getAvatar();
                                                               if (this$avatar == null ? other$avatar == null : this$avatar.equals(other$avatar)) {
                                                                  Object this$hostLineTypeName = this.getHostLineTypeName();
                                                                  Object other$hostLineTypeName = other.getHostLineTypeName();
                                                                  if (this$hostLineTypeName == null
                                                                     ? other$hostLineTypeName == null
                                                                     : this$hostLineTypeName.equals(other$hostLineTypeName)) {
                                                                     Object this$registerChannelName = this.getRegisterChannelName();
                                                                     Object other$registerChannelName = other.getRegisterChannelName();
                                                                     if (this$registerChannelName == null
                                                                        ? other$registerChannelName == null
                                                                        : this$registerChannelName.equals(other$registerChannelName)) {
                                                                        Object this$oauthBindings = this.getOauthBindings();
                                                                        Object other$oauthBindings = other.getOauthBindings();
                                                                        if (this$oauthBindings == null
                                                                           ? other$oauthBindings == null
                                                                           : this$oauthBindings.equals(other$oauthBindings)) {
                                                                           Object this$servers = this.getServers();
                                                                           Object other$servers = other.getServers();
                                                                           if (this$servers == null
                                                                              ? other$servers == null
                                                                              : this$servers.equals(other$servers)) {
                                                                              Object this$token = this.getToken();
                                                                              Object other$token = other.getToken();
                                                                              if (this$token == null ? other$token == null : this$token.equals(other$token)) {
                                                                                 Object this$theme = this.getTheme();
                                                                                 Object other$theme = other.getTheme();
                                                                                 return this$theme == null
                                                                                    ? other$theme == null
                                                                                    : this$theme.equals(other$theme);
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
      return other instanceof EmbyUserCustomResponse;
   }

   @Generated
   @Override
   public int hashCode() {
      int PRIME = 59;
      int result = 1;
      Object $id = this.getId();
      result = result * 59 + ($id == null ? 43 : $id.hashCode());
      Object $isAdmin = this.getIsAdmin();
      result = result * 59 + ($isAdmin == null ? 43 : $isAdmin.hashCode());
      Object $isPrimaryAdmin = this.getIsPrimaryAdmin();
      result = result * 59 + ($isPrimaryAdmin == null ? 43 : $isPrimaryAdmin.hashCode());
      Object $userStatus = this.getUserStatus();
      result = result * 59 + ($userStatus == null ? 43 : $userStatus.hashCode());
      Object $requestPackagesCount = this.getRequestPackagesCount();
      result = result * 59 + ($requestPackagesCount == null ? 43 : $requestPackagesCount.hashCode());
      Object $registerChannel = this.getRegisterChannel();
      result = result * 59 + ($registerChannel == null ? 43 : $registerChannel.hashCode());
      Object $hostLineType = this.getHostLineType();
      result = result * 59 + ($hostLineType == null ? 43 : $hostLineType.hashCode());
      Object $isDistributor = this.getIsDistributor();
      result = result * 59 + ($isDistributor == null ? 43 : $isDistributor.hashCode());
      Object $menuPermissions = this.getMenuPermissions();
      result = result * 59 + ($menuPermissions == null ? 43 : $menuPermissions.hashCode());
      Object $userStatusName = this.getUserStatusName();
      result = result * 59 + ($userStatusName == null ? 43 : $userStatusName.hashCode());
      Object $expirationDate = this.getExpirationDate();
      result = result * 59 + ($expirationDate == null ? 43 : $expirationDate.hashCode());
      Object $embyUserName = this.getEmbyUserName();
      result = result * 59 + ($embyUserName == null ? 43 : $embyUserName.hashCode());
      Object $remarks = this.getRemarks();
      result = result * 59 + ($remarks == null ? 43 : $remarks.hashCode());
      Object $email = this.getEmail();
      result = result * 59 + ($email == null ? 43 : $email.hashCode());
      Object $mobile = this.getMobile();
      result = result * 59 + ($mobile == null ? 43 : $mobile.hashCode());
      Object $gender = this.getGender();
      result = result * 59 + ($gender == null ? 43 : $gender.hashCode());
      Object $birthday = this.getBirthday();
      result = result * 59 + ($birthday == null ? 43 : $birthday.hashCode());
      Object $interests = this.getInterests();
      result = result * 59 + ($interests == null ? 43 : $interests.hashCode());
      Object $avatar = this.getAvatar();
      result = result * 59 + ($avatar == null ? 43 : $avatar.hashCode());
      Object $hostLineTypeName = this.getHostLineTypeName();
      result = result * 59 + ($hostLineTypeName == null ? 43 : $hostLineTypeName.hashCode());
      Object $registerChannelName = this.getRegisterChannelName();
      result = result * 59 + ($registerChannelName == null ? 43 : $registerChannelName.hashCode());
      Object $oauthBindings = this.getOauthBindings();
      result = result * 59 + ($oauthBindings == null ? 43 : $oauthBindings.hashCode());
      Object $servers = this.getServers();
      result = result * 59 + ($servers == null ? 43 : $servers.hashCode());
      Object $token = this.getToken();
      result = result * 59 + ($token == null ? 43 : $token.hashCode());
      Object $theme = this.getTheme();
      return result * 59 + ($theme == null ? 43 : $theme.hashCode());
   }

   @Generated
   @Override
   public String toString() {
      return "EmbyUserCustomResponse(id="
         + this.getId()
         + ", isAdmin="
         + this.getIsAdmin()
         + ", isPrimaryAdmin="
         + this.getIsPrimaryAdmin()
         + ", menuPermissions="
         + this.getMenuPermissions()
         + ", userStatus="
         + this.getUserStatus()
         + ", userStatusName="
         + this.getUserStatusName()
         + ", expirationDate="
         + this.getExpirationDate()
         + ", embyUserName="
         + this.getEmbyUserName()
         + ", remarks="
         + this.getRemarks()
         + ", requestPackagesCount="
         + this.getRequestPackagesCount()
         + ", email="
         + this.getEmail()
         + ", mobile="
         + this.getMobile()
         + ", gender="
         + this.getGender()
         + ", birthday="
         + this.getBirthday()
         + ", interests="
         + this.getInterests()
         + ", avatar="
         + this.getAvatar()
         + ", registerChannel="
         + this.getRegisterChannel()
         + ", hostLineType="
         + this.getHostLineType()
         + ", hostLineTypeName="
         + this.getHostLineTypeName()
         + ", registerChannelName="
         + this.getRegisterChannelName()
         + ", isDistributor="
         + this.getIsDistributor()
         + ", oauthBindings="
         + this.getOauthBindings()
         + ", servers="
         + this.getServers()
         + ", token="
         + this.getToken()
         + ", theme="
         + this.getTheme()
         + ")";
   }

   public static class ServerOption implements Serializable {
      private Long id;
      private Long embyInfoId;
      private String serverName;
      private String embyUrl;
      private String embyOpenUrl;
      private String embyServerId;

      @Generated
      public Long getId() {
         return this.id;
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
      public String getEmbyUrl() {
         return this.embyUrl;
      }

      @Generated
      public String getEmbyOpenUrl() {
         return this.embyOpenUrl;
      }

      @Generated
      public String getEmbyServerId() {
         return this.embyServerId;
      }

      @Generated
      public void setId(final Long id) {
         this.id = id;
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
      public void setEmbyUrl(final String embyUrl) {
         this.embyUrl = embyUrl;
      }

      @Generated
      public void setEmbyOpenUrl(final String embyOpenUrl) {
         this.embyOpenUrl = embyOpenUrl;
      }

      @Generated
      public void setEmbyServerId(final String embyServerId) {
         this.embyServerId = embyServerId;
      }

      @Generated
      @Override
      public boolean equals(final Object o) {
         if (o == this) {
            return true;
         } else if (!(o instanceof EmbyUserCustomResponse.ServerOption other)) {
            return false;
         } else if (!other.canEqual(this)) {
            return false;
         } else {
            Object this$id = this.getId();
            Object other$id = other.getId();
            if (this$id == null ? other$id == null : this$id.equals(other$id)) {
               Object this$embyInfoId = this.getEmbyInfoId();
               Object other$embyInfoId = other.getEmbyInfoId();
               if (this$embyInfoId == null ? other$embyInfoId == null : this$embyInfoId.equals(other$embyInfoId)) {
                  Object this$serverName = this.getServerName();
                  Object other$serverName = other.getServerName();
                  if (this$serverName == null ? other$serverName == null : this$serverName.equals(other$serverName)) {
                     Object this$embyUrl = this.getEmbyUrl();
                     Object other$embyUrl = other.getEmbyUrl();
                     if (this$embyUrl == null ? other$embyUrl == null : this$embyUrl.equals(other$embyUrl)) {
                        Object this$embyOpenUrl = this.getEmbyOpenUrl();
                        Object other$embyOpenUrl = other.getEmbyOpenUrl();
                        if (this$embyOpenUrl == null ? other$embyOpenUrl == null : this$embyOpenUrl.equals(other$embyOpenUrl)) {
                           Object this$embyServerId = this.getEmbyServerId();
                           Object other$embyServerId = other.getEmbyServerId();
                           return this$embyServerId == null ? other$embyServerId == null : this$embyServerId.equals(other$embyServerId);
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
         return other instanceof EmbyUserCustomResponse.ServerOption;
      }

      @Generated
      @Override
      public int hashCode() {
         int PRIME = 59;
         int result = 1;
         Object $id = this.getId();
         result = result * 59 + ($id == null ? 43 : $id.hashCode());
         Object $embyInfoId = this.getEmbyInfoId();
         result = result * 59 + ($embyInfoId == null ? 43 : $embyInfoId.hashCode());
         Object $serverName = this.getServerName();
         result = result * 59 + ($serverName == null ? 43 : $serverName.hashCode());
         Object $embyUrl = this.getEmbyUrl();
         result = result * 59 + ($embyUrl == null ? 43 : $embyUrl.hashCode());
         Object $embyOpenUrl = this.getEmbyOpenUrl();
         result = result * 59 + ($embyOpenUrl == null ? 43 : $embyOpenUrl.hashCode());
         Object $embyServerId = this.getEmbyServerId();
         return result * 59 + ($embyServerId == null ? 43 : $embyServerId.hashCode());
      }

      @Generated
      @Override
      public String toString() {
         return "EmbyUserCustomResponse.ServerOption(id="
            + this.getId()
            + ", embyInfoId="
            + this.getEmbyInfoId()
            + ", serverName="
            + this.getServerName()
            + ", embyUrl="
            + this.getEmbyUrl()
            + ", embyOpenUrl="
            + this.getEmbyOpenUrl()
            + ", embyServerId="
            + this.getEmbyServerId()
            + ")";
      }
   }
}
