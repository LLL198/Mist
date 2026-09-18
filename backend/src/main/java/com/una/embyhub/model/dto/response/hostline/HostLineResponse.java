package com.una.embyhub.model.dto.response.hostline;

import com.una.embyhub.config.common.enums.HostLineTypeEnum;
import com.una.embyhub.config.common.utils.IpAddressUtils;
import java.io.Serializable;
import lombok.Generated;

public class HostLineResponse implements Serializable {
   private Long id;
   private Long embyInfoId;
   private String lineName;
   private Integer lineType;
   private String lineTypeName;
   private String protocol;
   private String domain;
   private Integer port;
   private Integer isDisplay;
   private Integer enabled;
   private Integer sortNo;
   private String remark;

   public void setLineType(Integer lineType) {
      this.lineType = HostLineTypeEnum.normalize(lineType);
      this.lineTypeName = HostLineTypeEnum.resolveLabel(lineType);
   }

   public String getLineAddress() {
      return this.protocol != null && this.domain != null && this.port != null
         ? String.format("%s://%s:%d", this.protocol, IpAddressUtils.normalizeHostForUrl(this.domain), this.port)
         : null;
   }

   @Generated
   public Long getId() {
      return this.id;
   }

   @Generated
   public Long getEmbyInfoId() {
      return this.embyInfoId;
   }

   @Generated
   public String getLineName() {
      return this.lineName;
   }

   @Generated
   public Integer getLineType() {
      return this.lineType;
   }

   @Generated
   public String getLineTypeName() {
      return this.lineTypeName;
   }

   @Generated
   public String getProtocol() {
      return this.protocol;
   }

   @Generated
   public String getDomain() {
      return this.domain;
   }

   @Generated
   public Integer getPort() {
      return this.port;
   }

   @Generated
   public Integer getIsDisplay() {
      return this.isDisplay;
   }

   @Generated
   public Integer getEnabled() {
      return this.enabled;
   }

   @Generated
   public Integer getSortNo() {
      return this.sortNo;
   }

   @Generated
   public String getRemark() {
      return this.remark;
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
   public void setLineName(final String lineName) {
      this.lineName = lineName;
   }

   @Generated
   public void setLineTypeName(final String lineTypeName) {
      this.lineTypeName = lineTypeName;
   }

   @Generated
   public void setProtocol(final String protocol) {
      this.protocol = protocol;
   }

   @Generated
   public void setDomain(final String domain) {
      this.domain = domain;
   }

   @Generated
   public void setPort(final Integer port) {
      this.port = port;
   }

   @Generated
   public void setIsDisplay(final Integer isDisplay) {
      this.isDisplay = isDisplay;
   }

   @Generated
   public void setEnabled(final Integer enabled) {
      this.enabled = enabled;
   }

   @Generated
   public void setSortNo(final Integer sortNo) {
      this.sortNo = sortNo;
   }

   @Generated
   public void setRemark(final String remark) {
      this.remark = remark;
   }

   @Generated
   @Override
   public boolean equals(final Object o) {
      if (o == this) {
         return true;
      } else if (!(o instanceof HostLineResponse other)) {
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
               Object this$lineType = this.getLineType();
               Object other$lineType = other.getLineType();
               if (this$lineType == null ? other$lineType == null : this$lineType.equals(other$lineType)) {
                  Object this$port = this.getPort();
                  Object other$port = other.getPort();
                  if (this$port == null ? other$port == null : this$port.equals(other$port)) {
                     Object this$isDisplay = this.getIsDisplay();
                     Object other$isDisplay = other.getIsDisplay();
                     if (this$isDisplay == null ? other$isDisplay == null : this$isDisplay.equals(other$isDisplay)) {
                        Object this$enabled = this.getEnabled();
                        Object other$enabled = other.getEnabled();
                        if (this$enabled == null ? other$enabled == null : this$enabled.equals(other$enabled)) {
                           Object this$sortNo = this.getSortNo();
                           Object other$sortNo = other.getSortNo();
                           if (this$sortNo == null ? other$sortNo == null : this$sortNo.equals(other$sortNo)) {
                              Object this$lineName = this.getLineName();
                              Object other$lineName = other.getLineName();
                              if (this$lineName == null ? other$lineName == null : this$lineName.equals(other$lineName)) {
                                 Object this$lineTypeName = this.getLineTypeName();
                                 Object other$lineTypeName = other.getLineTypeName();
                                 if (this$lineTypeName == null ? other$lineTypeName == null : this$lineTypeName.equals(other$lineTypeName)) {
                                    Object this$protocol = this.getProtocol();
                                    Object other$protocol = other.getProtocol();
                                    if (this$protocol == null ? other$protocol == null : this$protocol.equals(other$protocol)) {
                                       Object this$domain = this.getDomain();
                                       Object other$domain = other.getDomain();
                                       if (this$domain == null ? other$domain == null : this$domain.equals(other$domain)) {
                                          Object this$remark = this.getRemark();
                                          Object other$remark = other.getRemark();
                                          return this$remark == null ? other$remark == null : this$remark.equals(other$remark);
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
      return other instanceof HostLineResponse;
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
      Object $lineType = this.getLineType();
      result = result * 59 + ($lineType == null ? 43 : $lineType.hashCode());
      Object $port = this.getPort();
      result = result * 59 + ($port == null ? 43 : $port.hashCode());
      Object $isDisplay = this.getIsDisplay();
      result = result * 59 + ($isDisplay == null ? 43 : $isDisplay.hashCode());
      Object $enabled = this.getEnabled();
      result = result * 59 + ($enabled == null ? 43 : $enabled.hashCode());
      Object $sortNo = this.getSortNo();
      result = result * 59 + ($sortNo == null ? 43 : $sortNo.hashCode());
      Object $lineName = this.getLineName();
      result = result * 59 + ($lineName == null ? 43 : $lineName.hashCode());
      Object $lineTypeName = this.getLineTypeName();
      result = result * 59 + ($lineTypeName == null ? 43 : $lineTypeName.hashCode());
      Object $protocol = this.getProtocol();
      result = result * 59 + ($protocol == null ? 43 : $protocol.hashCode());
      Object $domain = this.getDomain();
      result = result * 59 + ($domain == null ? 43 : $domain.hashCode());
      Object $remark = this.getRemark();
      return result * 59 + ($remark == null ? 43 : $remark.hashCode());
   }

   @Generated
   @Override
   public String toString() {
      return "HostLineResponse(id="
         + this.getId()
         + ", embyInfoId="
         + this.getEmbyInfoId()
         + ", lineName="
         + this.getLineName()
         + ", lineType="
         + this.getLineType()
         + ", lineTypeName="
         + this.getLineTypeName()
         + ", protocol="
         + this.getProtocol()
         + ", domain="
         + this.getDomain()
         + ", port="
         + this.getPort()
         + ", isDisplay="
         + this.getIsDisplay()
         + ", enabled="
         + this.getEnabled()
         + ", sortNo="
         + this.getSortNo()
         + ", remark="
         + this.getRemark()
         + ")";
   }
}
