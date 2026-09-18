package com.una.embyhub.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Generated;

@TableName("host_line")
public class HostLine extends BaseEntity implements Serializable {
   private static final long serialVersionUID = 1L;
   @TableId(
      value = "id",
      type = IdType.AUTO
   )
   private Long id;
   @TableField("emby_info_id")
   private Long embyInfoId;
   @TableField("line_name")
   private String lineName;
   @TableField("line_type")
   private Integer lineType;
   @TableField("protocol")
   private String protocol;
   @TableField("domain")
   private String domain;
   @TableField("port")
   private Integer port;
   @TableField("is_display")
   private Integer isDisplay;
   @TableField("enabled")
   private Integer enabled;
   @TableField("sort_no")
   private Integer sortNo;
   @TableField("remark")
   private String remark;
   public static final String COL_ID = "id";
   public static final String COL_EMBY_INFO_ID = "emby_info_id";
   public static final String COL_LINE_NAME = "line_name";
   public static final String COL_LINE_TYPE = "line_type";
   public static final String COL_PROTOCOL = "protocol";
   public static final String COL_DOMAIN = "domain";
   public static final String COL_PORT = "port";
   public static final String COL_IS_DISPLAY = "is_display";
   public static final String COL_ENABLED = "enabled";
   public static final String COL_SORT_NO = "sort_no";
   public static final String COL_REMARK = "remark";

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
   public void setLineType(final Integer lineType) {
      this.lineType = lineType;
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
   public String toString() {
      return "HostLine(id="
         + this.getId()
         + ", embyInfoId="
         + this.getEmbyInfoId()
         + ", lineName="
         + this.getLineName()
         + ", lineType="
         + this.getLineType()
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

   @Generated
   @Override
   public boolean equals(final Object o) {
      if (o == this) {
         return true;
      } else if (!(o instanceof HostLine other)) {
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
      }
   }

   @Generated
   @Override
   protected boolean canEqual(final Object other) {
      return other instanceof HostLine;
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
      Object $protocol = this.getProtocol();
      result = result * 59 + ($protocol == null ? 43 : $protocol.hashCode());
      Object $domain = this.getDomain();
      result = result * 59 + ($domain == null ? 43 : $domain.hashCode());
      Object $remark = this.getRemark();
      return result * 59 + ($remark == null ? 43 : $remark.hashCode());
   }
}
