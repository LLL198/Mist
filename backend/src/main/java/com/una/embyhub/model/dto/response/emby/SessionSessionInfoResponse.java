package com.una.embyhub.model.dto.response.emby;

import com.google.gson.annotations.SerializedName;
import embyclient.model.BaseItemDto;
import embyclient.model.PlayerStateInfo;
import embyclient.model.SessionUserInfo;
import embyclient.model.TranscodingInfo;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.Generated;

public class SessionSessionInfoResponse {
   @SerializedName("PlayState")
   private PlayerStateInfo playState;
   @SerializedName("AdditionalUsers")
   private List<SessionUserInfo> additionalUsers = new ArrayList<>();
   @SerializedName("RemoteEndPoint")
   private String remoteEndPoint;
   private String remoteAddress;
   @SerializedName("Protocol")
   private String protocol;
   @SerializedName("PlayableMediaTypes")
   private List<String> playableMediaTypes = new ArrayList<>();
   @SerializedName("PlaylistItemId")
   private String playlistItemId;
   @SerializedName("PlaylistIndex")
   private Integer playlistIndex;
   @SerializedName("PlaylistLength")
   private Integer playlistLength;
   @SerializedName("Id")
   private String id;
   @SerializedName("ServerId")
   private String serverId;
   @SerializedName("UserId")
   private String userId;
   @SerializedName("UserName")
   private String userName;
   @SerializedName("UserPrimaryImageTag")
   private String userPrimaryImageTag;
   @SerializedName("Client")
   private String client;
   @SerializedName("LastActivityDate")
   private OffsetDateTime lastActivityDate;
   @SerializedName("DeviceName")
   private String deviceName;
   @SerializedName("DeviceType")
   private String deviceType;
   @SerializedName("NowPlayingItem")
   private BaseItemDto nowPlayingItem;
   @SerializedName("InternalDeviceId")
   private Long internalDeviceId;
   @SerializedName("DeviceId")
   private String deviceId;
   @SerializedName("ApplicationVersion")
   private String applicationVersion;
   @SerializedName("AppIconUrl")
   private String appIconUrl;
   @SerializedName("SupportedCommands")
   private List<String> supportedCommands = new ArrayList<>();
   @SerializedName("TranscodingInfo")
   private TranscodingInfo transcodingInfo;
   @SerializedName("SupportsRemoteControl")
   private Boolean supportsRemoteControl;
   private String coverImage;
   private String UserAvatar;

   @Generated
   public PlayerStateInfo getPlayState() {
      return this.playState;
   }

   @Generated
   public List<SessionUserInfo> getAdditionalUsers() {
      return this.additionalUsers;
   }

   @Generated
   public String getRemoteEndPoint() {
      return this.remoteEndPoint;
   }

   @Generated
   public String getRemoteAddress() {
      return this.remoteAddress;
   }

   @Generated
   public String getProtocol() {
      return this.protocol;
   }

   @Generated
   public List<String> getPlayableMediaTypes() {
      return this.playableMediaTypes;
   }

   @Generated
   public String getPlaylistItemId() {
      return this.playlistItemId;
   }

   @Generated
   public Integer getPlaylistIndex() {
      return this.playlistIndex;
   }

   @Generated
   public Integer getPlaylistLength() {
      return this.playlistLength;
   }

   @Generated
   public String getId() {
      return this.id;
   }

   @Generated
   public String getServerId() {
      return this.serverId;
   }

   @Generated
   public String getUserId() {
      return this.userId;
   }

   @Generated
   public String getUserName() {
      return this.userName;
   }

   @Generated
   public String getUserPrimaryImageTag() {
      return this.userPrimaryImageTag;
   }

   @Generated
   public String getClient() {
      return this.client;
   }

   @Generated
   public OffsetDateTime getLastActivityDate() {
      return this.lastActivityDate;
   }

   @Generated
   public String getDeviceName() {
      return this.deviceName;
   }

   @Generated
   public String getDeviceType() {
      return this.deviceType;
   }

   @Generated
   public BaseItemDto getNowPlayingItem() {
      return this.nowPlayingItem;
   }

   @Generated
   public Long getInternalDeviceId() {
      return this.internalDeviceId;
   }

   @Generated
   public String getDeviceId() {
      return this.deviceId;
   }

   @Generated
   public String getApplicationVersion() {
      return this.applicationVersion;
   }

   @Generated
   public String getAppIconUrl() {
      return this.appIconUrl;
   }

   @Generated
   public List<String> getSupportedCommands() {
      return this.supportedCommands;
   }

   @Generated
   public TranscodingInfo getTranscodingInfo() {
      return this.transcodingInfo;
   }

   @Generated
   public Boolean getSupportsRemoteControl() {
      return this.supportsRemoteControl;
   }

   @Generated
   public String getCoverImage() {
      return this.coverImage;
   }

   @Generated
   public String getUserAvatar() {
      return this.UserAvatar;
   }

   @Generated
   public SessionSessionInfoResponse setPlayState(final PlayerStateInfo playState) {
      this.playState = playState;
      return this;
   }

   @Generated
   public SessionSessionInfoResponse setAdditionalUsers(final List<SessionUserInfo> additionalUsers) {
      this.additionalUsers = additionalUsers;
      return this;
   }

   @Generated
   public SessionSessionInfoResponse setRemoteEndPoint(final String remoteEndPoint) {
      this.remoteEndPoint = remoteEndPoint;
      return this;
   }

   @Generated
   public SessionSessionInfoResponse setRemoteAddress(final String remoteAddress) {
      this.remoteAddress = remoteAddress;
      return this;
   }

   @Generated
   public SessionSessionInfoResponse setProtocol(final String protocol) {
      this.protocol = protocol;
      return this;
   }

   @Generated
   public SessionSessionInfoResponse setPlayableMediaTypes(final List<String> playableMediaTypes) {
      this.playableMediaTypes = playableMediaTypes;
      return this;
   }

   @Generated
   public SessionSessionInfoResponse setPlaylistItemId(final String playlistItemId) {
      this.playlistItemId = playlistItemId;
      return this;
   }

   @Generated
   public SessionSessionInfoResponse setPlaylistIndex(final Integer playlistIndex) {
      this.playlistIndex = playlistIndex;
      return this;
   }

   @Generated
   public SessionSessionInfoResponse setPlaylistLength(final Integer playlistLength) {
      this.playlistLength = playlistLength;
      return this;
   }

   @Generated
   public SessionSessionInfoResponse setId(final String id) {
      this.id = id;
      return this;
   }

   @Generated
   public SessionSessionInfoResponse setServerId(final String serverId) {
      this.serverId = serverId;
      return this;
   }

   @Generated
   public SessionSessionInfoResponse setUserId(final String userId) {
      this.userId = userId;
      return this;
   }

   @Generated
   public SessionSessionInfoResponse setUserName(final String userName) {
      this.userName = userName;
      return this;
   }

   @Generated
   public SessionSessionInfoResponse setUserPrimaryImageTag(final String userPrimaryImageTag) {
      this.userPrimaryImageTag = userPrimaryImageTag;
      return this;
   }

   @Generated
   public SessionSessionInfoResponse setClient(final String client) {
      this.client = client;
      return this;
   }

   @Generated
   public SessionSessionInfoResponse setLastActivityDate(final OffsetDateTime lastActivityDate) {
      this.lastActivityDate = lastActivityDate;
      return this;
   }

   @Generated
   public SessionSessionInfoResponse setDeviceName(final String deviceName) {
      this.deviceName = deviceName;
      return this;
   }

   @Generated
   public SessionSessionInfoResponse setDeviceType(final String deviceType) {
      this.deviceType = deviceType;
      return this;
   }

   @Generated
   public SessionSessionInfoResponse setNowPlayingItem(final BaseItemDto nowPlayingItem) {
      this.nowPlayingItem = nowPlayingItem;
      return this;
   }

   @Generated
   public SessionSessionInfoResponse setInternalDeviceId(final Long internalDeviceId) {
      this.internalDeviceId = internalDeviceId;
      return this;
   }

   @Generated
   public SessionSessionInfoResponse setDeviceId(final String deviceId) {
      this.deviceId = deviceId;
      return this;
   }

   @Generated
   public SessionSessionInfoResponse setApplicationVersion(final String applicationVersion) {
      this.applicationVersion = applicationVersion;
      return this;
   }

   @Generated
   public SessionSessionInfoResponse setAppIconUrl(final String appIconUrl) {
      this.appIconUrl = appIconUrl;
      return this;
   }

   @Generated
   public SessionSessionInfoResponse setSupportedCommands(final List<String> supportedCommands) {
      this.supportedCommands = supportedCommands;
      return this;
   }

   @Generated
   public SessionSessionInfoResponse setTranscodingInfo(final TranscodingInfo transcodingInfo) {
      this.transcodingInfo = transcodingInfo;
      return this;
   }

   @Generated
   public SessionSessionInfoResponse setSupportsRemoteControl(final Boolean supportsRemoteControl) {
      this.supportsRemoteControl = supportsRemoteControl;
      return this;
   }

   @Generated
   public SessionSessionInfoResponse setCoverImage(final String coverImage) {
      this.coverImage = coverImage;
      return this;
   }

   @Generated
   public SessionSessionInfoResponse setUserAvatar(final String UserAvatar) {
      this.UserAvatar = UserAvatar;
      return this;
   }

   @Generated
   @Override
   public boolean equals(final Object o) {
      if (o == this) {
         return true;
      } else if (!(o instanceof SessionSessionInfoResponse other)) {
         return false;
      } else if (!other.canEqual(this)) {
         return false;
      } else {
         Object this$playlistIndex = this.getPlaylistIndex();
         Object other$playlistIndex = other.getPlaylistIndex();
         if (this$playlistIndex == null ? other$playlistIndex == null : this$playlistIndex.equals(other$playlistIndex)) {
            Object this$playlistLength = this.getPlaylistLength();
            Object other$playlistLength = other.getPlaylistLength();
            if (this$playlistLength == null ? other$playlistLength == null : this$playlistLength.equals(other$playlistLength)) {
               Object this$internalDeviceId = this.getInternalDeviceId();
               Object other$internalDeviceId = other.getInternalDeviceId();
               if (this$internalDeviceId == null ? other$internalDeviceId == null : this$internalDeviceId.equals(other$internalDeviceId)) {
                  Object this$supportsRemoteControl = this.getSupportsRemoteControl();
                  Object other$supportsRemoteControl = other.getSupportsRemoteControl();
                  if (this$supportsRemoteControl == null ? other$supportsRemoteControl == null : this$supportsRemoteControl.equals(other$supportsRemoteControl)
                     )
                   {
                     Object this$playState = this.getPlayState();
                     Object other$playState = other.getPlayState();
                     if (this$playState == null ? other$playState == null : this$playState.equals(other$playState)) {
                        Object this$additionalUsers = this.getAdditionalUsers();
                        Object other$additionalUsers = other.getAdditionalUsers();
                        if (this$additionalUsers == null ? other$additionalUsers == null : this$additionalUsers.equals(other$additionalUsers)) {
                           Object this$remoteEndPoint = this.getRemoteEndPoint();
                           Object other$remoteEndPoint = other.getRemoteEndPoint();
                           if (this$remoteEndPoint == null ? other$remoteEndPoint == null : this$remoteEndPoint.equals(other$remoteEndPoint)) {
                              Object this$remoteAddress = this.getRemoteAddress();
                              Object other$remoteAddress = other.getRemoteAddress();
                              if (this$remoteAddress == null ? other$remoteAddress == null : this$remoteAddress.equals(other$remoteAddress)) {
                                 Object this$protocol = this.getProtocol();
                                 Object other$protocol = other.getProtocol();
                                 if (this$protocol == null ? other$protocol == null : this$protocol.equals(other$protocol)) {
                                    Object this$playableMediaTypes = this.getPlayableMediaTypes();
                                    Object other$playableMediaTypes = other.getPlayableMediaTypes();
                                    if (this$playableMediaTypes == null
                                       ? other$playableMediaTypes == null
                                       : this$playableMediaTypes.equals(other$playableMediaTypes)) {
                                       Object this$playlistItemId = this.getPlaylistItemId();
                                       Object other$playlistItemId = other.getPlaylistItemId();
                                       if (this$playlistItemId == null ? other$playlistItemId == null : this$playlistItemId.equals(other$playlistItemId)) {
                                          Object this$id = this.getId();
                                          Object other$id = other.getId();
                                          if (this$id == null ? other$id == null : this$id.equals(other$id)) {
                                             Object this$serverId = this.getServerId();
                                             Object other$serverId = other.getServerId();
                                             if (this$serverId == null ? other$serverId == null : this$serverId.equals(other$serverId)) {
                                                Object this$userId = this.getUserId();
                                                Object other$userId = other.getUserId();
                                                if (this$userId == null ? other$userId == null : this$userId.equals(other$userId)) {
                                                   Object this$userName = this.getUserName();
                                                   Object other$userName = other.getUserName();
                                                   if (this$userName == null ? other$userName == null : this$userName.equals(other$userName)) {
                                                      Object this$userPrimaryImageTag = this.getUserPrimaryImageTag();
                                                      Object other$userPrimaryImageTag = other.getUserPrimaryImageTag();
                                                      if (this$userPrimaryImageTag == null
                                                         ? other$userPrimaryImageTag == null
                                                         : this$userPrimaryImageTag.equals(other$userPrimaryImageTag)) {
                                                         Object this$client = this.getClient();
                                                         Object other$client = other.getClient();
                                                         if (this$client == null ? other$client == null : this$client.equals(other$client)) {
                                                            Object this$lastActivityDate = this.getLastActivityDate();
                                                            Object other$lastActivityDate = other.getLastActivityDate();
                                                            if (this$lastActivityDate == null
                                                               ? other$lastActivityDate == null
                                                               : this$lastActivityDate.equals(other$lastActivityDate)) {
                                                               Object this$deviceName = this.getDeviceName();
                                                               Object other$deviceName = other.getDeviceName();
                                                               if (this$deviceName == null
                                                                  ? other$deviceName == null
                                                                  : this$deviceName.equals(other$deviceName)) {
                                                                  Object this$deviceType = this.getDeviceType();
                                                                  Object other$deviceType = other.getDeviceType();
                                                                  if (this$deviceType == null
                                                                     ? other$deviceType == null
                                                                     : this$deviceType.equals(other$deviceType)) {
                                                                     Object this$nowPlayingItem = this.getNowPlayingItem();
                                                                     Object other$nowPlayingItem = other.getNowPlayingItem();
                                                                     if (this$nowPlayingItem == null
                                                                        ? other$nowPlayingItem == null
                                                                        : this$nowPlayingItem.equals(other$nowPlayingItem)) {
                                                                        Object this$deviceId = this.getDeviceId();
                                                                        Object other$deviceId = other.getDeviceId();
                                                                        if (this$deviceId == null
                                                                           ? other$deviceId == null
                                                                           : this$deviceId.equals(other$deviceId)) {
                                                                           Object this$applicationVersion = this.getApplicationVersion();
                                                                           Object other$applicationVersion = other.getApplicationVersion();
                                                                           if (this$applicationVersion == null
                                                                              ? other$applicationVersion == null
                                                                              : this$applicationVersion.equals(other$applicationVersion)) {
                                                                              Object this$appIconUrl = this.getAppIconUrl();
                                                                              Object other$appIconUrl = other.getAppIconUrl();
                                                                              if (this$appIconUrl == null
                                                                                 ? other$appIconUrl == null
                                                                                 : this$appIconUrl.equals(other$appIconUrl)) {
                                                                                 Object this$supportedCommands = this.getSupportedCommands();
                                                                                 Object other$supportedCommands = other.getSupportedCommands();
                                                                                 if (this$supportedCommands == null
                                                                                    ? other$supportedCommands == null
                                                                                    : this$supportedCommands.equals(other$supportedCommands)) {
                                                                                    Object this$transcodingInfo = this.getTranscodingInfo();
                                                                                    Object other$transcodingInfo = other.getTranscodingInfo();
                                                                                    if (this$transcodingInfo == null
                                                                                       ? other$transcodingInfo == null
                                                                                       : this$transcodingInfo.equals(other$transcodingInfo)) {
                                                                                       Object this$coverImage = this.getCoverImage();
                                                                                       Object other$coverImage = other.getCoverImage();
                                                                                       if (this$coverImage == null
                                                                                          ? other$coverImage == null
                                                                                          : this$coverImage.equals(other$coverImage)) {
                                                                                          Object this$UserAvatar = this.getUserAvatar();
                                                                                          Object other$UserAvatar = other.getUserAvatar();
                                                                                          return this$UserAvatar == null
                                                                                             ? other$UserAvatar == null
                                                                                             : this$UserAvatar.equals(other$UserAvatar);
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
      return other instanceof SessionSessionInfoResponse;
   }

   @Generated
   @Override
   public int hashCode() {
      int PRIME = 59;
      int result = 1;
      Object $playlistIndex = this.getPlaylistIndex();
      result = result * 59 + ($playlistIndex == null ? 43 : $playlistIndex.hashCode());
      Object $playlistLength = this.getPlaylistLength();
      result = result * 59 + ($playlistLength == null ? 43 : $playlistLength.hashCode());
      Object $internalDeviceId = this.getInternalDeviceId();
      result = result * 59 + ($internalDeviceId == null ? 43 : $internalDeviceId.hashCode());
      Object $supportsRemoteControl = this.getSupportsRemoteControl();
      result = result * 59 + ($supportsRemoteControl == null ? 43 : $supportsRemoteControl.hashCode());
      Object $playState = this.getPlayState();
      result = result * 59 + ($playState == null ? 43 : $playState.hashCode());
      Object $additionalUsers = this.getAdditionalUsers();
      result = result * 59 + ($additionalUsers == null ? 43 : $additionalUsers.hashCode());
      Object $remoteEndPoint = this.getRemoteEndPoint();
      result = result * 59 + ($remoteEndPoint == null ? 43 : $remoteEndPoint.hashCode());
      Object $remoteAddress = this.getRemoteAddress();
      result = result * 59 + ($remoteAddress == null ? 43 : $remoteAddress.hashCode());
      Object $protocol = this.getProtocol();
      result = result * 59 + ($protocol == null ? 43 : $protocol.hashCode());
      Object $playableMediaTypes = this.getPlayableMediaTypes();
      result = result * 59 + ($playableMediaTypes == null ? 43 : $playableMediaTypes.hashCode());
      Object $playlistItemId = this.getPlaylistItemId();
      result = result * 59 + ($playlistItemId == null ? 43 : $playlistItemId.hashCode());
      Object $id = this.getId();
      result = result * 59 + ($id == null ? 43 : $id.hashCode());
      Object $serverId = this.getServerId();
      result = result * 59 + ($serverId == null ? 43 : $serverId.hashCode());
      Object $userId = this.getUserId();
      result = result * 59 + ($userId == null ? 43 : $userId.hashCode());
      Object $userName = this.getUserName();
      result = result * 59 + ($userName == null ? 43 : $userName.hashCode());
      Object $userPrimaryImageTag = this.getUserPrimaryImageTag();
      result = result * 59 + ($userPrimaryImageTag == null ? 43 : $userPrimaryImageTag.hashCode());
      Object $client = this.getClient();
      result = result * 59 + ($client == null ? 43 : $client.hashCode());
      Object $lastActivityDate = this.getLastActivityDate();
      result = result * 59 + ($lastActivityDate == null ? 43 : $lastActivityDate.hashCode());
      Object $deviceName = this.getDeviceName();
      result = result * 59 + ($deviceName == null ? 43 : $deviceName.hashCode());
      Object $deviceType = this.getDeviceType();
      result = result * 59 + ($deviceType == null ? 43 : $deviceType.hashCode());
      Object $nowPlayingItem = this.getNowPlayingItem();
      result = result * 59 + ($nowPlayingItem == null ? 43 : $nowPlayingItem.hashCode());
      Object $deviceId = this.getDeviceId();
      result = result * 59 + ($deviceId == null ? 43 : $deviceId.hashCode());
      Object $applicationVersion = this.getApplicationVersion();
      result = result * 59 + ($applicationVersion == null ? 43 : $applicationVersion.hashCode());
      Object $appIconUrl = this.getAppIconUrl();
      result = result * 59 + ($appIconUrl == null ? 43 : $appIconUrl.hashCode());
      Object $supportedCommands = this.getSupportedCommands();
      result = result * 59 + ($supportedCommands == null ? 43 : $supportedCommands.hashCode());
      Object $transcodingInfo = this.getTranscodingInfo();
      result = result * 59 + ($transcodingInfo == null ? 43 : $transcodingInfo.hashCode());
      Object $coverImage = this.getCoverImage();
      result = result * 59 + ($coverImage == null ? 43 : $coverImage.hashCode());
      Object $UserAvatar = this.getUserAvatar();
      return result * 59 + ($UserAvatar == null ? 43 : $UserAvatar.hashCode());
   }

   @Generated
   @Override
   public String toString() {
      return "SessionSessionInfoResponse(playState="
         + this.getPlayState()
         + ", additionalUsers="
         + this.getAdditionalUsers()
         + ", remoteEndPoint="
         + this.getRemoteEndPoint()
         + ", remoteAddress="
         + this.getRemoteAddress()
         + ", protocol="
         + this.getProtocol()
         + ", playableMediaTypes="
         + this.getPlayableMediaTypes()
         + ", playlistItemId="
         + this.getPlaylistItemId()
         + ", playlistIndex="
         + this.getPlaylistIndex()
         + ", playlistLength="
         + this.getPlaylistLength()
         + ", id="
         + this.getId()
         + ", serverId="
         + this.getServerId()
         + ", userId="
         + this.getUserId()
         + ", userName="
         + this.getUserName()
         + ", userPrimaryImageTag="
         + this.getUserPrimaryImageTag()
         + ", client="
         + this.getClient()
         + ", lastActivityDate="
         + this.getLastActivityDate()
         + ", deviceName="
         + this.getDeviceName()
         + ", deviceType="
         + this.getDeviceType()
         + ", nowPlayingItem="
         + this.getNowPlayingItem()
         + ", internalDeviceId="
         + this.getInternalDeviceId()
         + ", deviceId="
         + this.getDeviceId()
         + ", applicationVersion="
         + this.getApplicationVersion()
         + ", appIconUrl="
         + this.getAppIconUrl()
         + ", supportedCommands="
         + this.getSupportedCommands()
         + ", transcodingInfo="
         + this.getTranscodingInfo()
         + ", supportsRemoteControl="
         + this.getSupportsRemoteControl()
         + ", coverImage="
         + this.getCoverImage()
         + ", UserAvatar="
         + this.getUserAvatar()
         + ")";
   }
}
