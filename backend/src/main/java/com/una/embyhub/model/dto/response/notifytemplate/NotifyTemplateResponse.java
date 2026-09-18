package com.una.embyhub.model.dto.response.notifytemplate;

import java.io.Serializable;
import java.util.Date;
import lombok.Generated;

public class NotifyTemplateResponse implements Serializable {
   private Long id;
   private String templateCode;
   private String templateName;
   private String channelType;
   private String templateContent;
   private String variableComment;
   private Integer enabled;
   private String remark;
   private Date createDatetime;
   private Date updateDatetime;

   @Generated
   public Long getId() {
      return this.id;
   }

   @Generated
   public String getTemplateCode() {
      return this.templateCode;
   }

   @Generated
   public String getTemplateName() {
      return this.templateName;
   }

   @Generated
   public String getChannelType() {
      return this.channelType;
   }

   @Generated
   public String getTemplateContent() {
      return this.templateContent;
   }

   @Generated
   public String getVariableComment() {
      return this.variableComment;
   }

   @Generated
   public Integer getEnabled() {
      return this.enabled;
   }

   @Generated
   public String getRemark() {
      return this.remark;
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
   public void setId(final Long id) {
      this.id = id;
   }

   @Generated
   public void setTemplateCode(final String templateCode) {
      this.templateCode = templateCode;
   }

   @Generated
   public void setTemplateName(final String templateName) {
      this.templateName = templateName;
   }

   @Generated
   public void setChannelType(final String channelType) {
      this.channelType = channelType;
   }

   @Generated
   public void setTemplateContent(final String templateContent) {
      this.templateContent = templateContent;
   }

   @Generated
   public void setVariableComment(final String variableComment) {
      this.variableComment = variableComment;
   }

   @Generated
   public void setEnabled(final Integer enabled) {
      this.enabled = enabled;
   }

   @Generated
   public void setRemark(final String remark) {
      this.remark = remark;
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
   @Override
   public boolean equals(final Object o) {
      if (o == this) {
         return true;
      } else if (!(o instanceof NotifyTemplateResponse other)) {
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
               Object this$templateCode = this.getTemplateCode();
               Object other$templateCode = other.getTemplateCode();
               if (this$templateCode == null ? other$templateCode == null : this$templateCode.equals(other$templateCode)) {
                  Object this$templateName = this.getTemplateName();
                  Object other$templateName = other.getTemplateName();
                  if (this$templateName == null ? other$templateName == null : this$templateName.equals(other$templateName)) {
                     Object this$channelType = this.getChannelType();
                     Object other$channelType = other.getChannelType();
                     if (this$channelType == null ? other$channelType == null : this$channelType.equals(other$channelType)) {
                        Object this$templateContent = this.getTemplateContent();
                        Object other$templateContent = other.getTemplateContent();
                        if (this$templateContent == null ? other$templateContent == null : this$templateContent.equals(other$templateContent)) {
                           Object this$variableComment = this.getVariableComment();
                           Object other$variableComment = other.getVariableComment();
                           if (this$variableComment == null ? other$variableComment == null : this$variableComment.equals(other$variableComment)) {
                              Object this$remark = this.getRemark();
                              Object other$remark = other.getRemark();
                              if (this$remark == null ? other$remark == null : this$remark.equals(other$remark)) {
                                 Object this$createDatetime = this.getCreateDatetime();
                                 Object other$createDatetime = other.getCreateDatetime();
                                 if (this$createDatetime == null ? other$createDatetime == null : this$createDatetime.equals(other$createDatetime)) {
                                    Object this$updateDatetime = this.getUpdateDatetime();
                                    Object other$updateDatetime = other.getUpdateDatetime();
                                    return this$updateDatetime == null ? other$updateDatetime == null : this$updateDatetime.equals(other$updateDatetime);
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
      return other instanceof NotifyTemplateResponse;
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
      Object $templateCode = this.getTemplateCode();
      result = result * 59 + ($templateCode == null ? 43 : $templateCode.hashCode());
      Object $templateName = this.getTemplateName();
      result = result * 59 + ($templateName == null ? 43 : $templateName.hashCode());
      Object $channelType = this.getChannelType();
      result = result * 59 + ($channelType == null ? 43 : $channelType.hashCode());
      Object $templateContent = this.getTemplateContent();
      result = result * 59 + ($templateContent == null ? 43 : $templateContent.hashCode());
      Object $variableComment = this.getVariableComment();
      result = result * 59 + ($variableComment == null ? 43 : $variableComment.hashCode());
      Object $remark = this.getRemark();
      result = result * 59 + ($remark == null ? 43 : $remark.hashCode());
      Object $createDatetime = this.getCreateDatetime();
      result = result * 59 + ($createDatetime == null ? 43 : $createDatetime.hashCode());
      Object $updateDatetime = this.getUpdateDatetime();
      return result * 59 + ($updateDatetime == null ? 43 : $updateDatetime.hashCode());
   }

   @Generated
   @Override
   public String toString() {
      return "NotifyTemplateResponse(id="
         + this.getId()
         + ", templateCode="
         + this.getTemplateCode()
         + ", templateName="
         + this.getTemplateName()
         + ", channelType="
         + this.getChannelType()
         + ", templateContent="
         + this.getTemplateContent()
         + ", variableComment="
         + this.getVariableComment()
         + ", enabled="
         + this.getEnabled()
         + ", remark="
         + this.getRemark()
         + ", createDatetime="
         + this.getCreateDatetime()
         + ", updateDatetime="
         + this.getUpdateDatetime()
         + ")";
   }
}
