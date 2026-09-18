package com.una.embyhub.model.dto.request.telegram;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import java.util.List;
import java.util.Map;
import lombok.Generated;

public class SearchRequest {
   @NotBlank
   private String kw;
   private List<String> channels;
   private List<String> plugins;
   @JsonProperty("cloud_types")
   private List<String> cloudTypes;
   private Integer conc;
   private Boolean refresh;
   private String res;
   private String src;
   private Map<String, Object> ext;

   @Generated
   public static SearchRequest.SearchRequestBuilder builder() {
      return new SearchRequest.SearchRequestBuilder();
   }

   @Generated
   public String getKw() {
      return this.kw;
   }

   @Generated
   public List<String> getChannels() {
      return this.channels;
   }

   @Generated
   public List<String> getPlugins() {
      return this.plugins;
   }

   @Generated
   public List<String> getCloudTypes() {
      return this.cloudTypes;
   }

   @Generated
   public Integer getConc() {
      return this.conc;
   }

   @Generated
   public Boolean getRefresh() {
      return this.refresh;
   }

   @Generated
   public String getRes() {
      return this.res;
   }

   @Generated
   public String getSrc() {
      return this.src;
   }

   @Generated
   public Map<String, Object> getExt() {
      return this.ext;
   }

   @Generated
   public void setKw(final String kw) {
      this.kw = kw;
   }

   @Generated
   public void setChannels(final List<String> channels) {
      this.channels = channels;
   }

   @Generated
   public void setPlugins(final List<String> plugins) {
      this.plugins = plugins;
   }

   @JsonProperty("cloud_types")
   @Generated
   public void setCloudTypes(final List<String> cloudTypes) {
      this.cloudTypes = cloudTypes;
   }

   @Generated
   public void setConc(final Integer conc) {
      this.conc = conc;
   }

   @Generated
   public void setRefresh(final Boolean refresh) {
      this.refresh = refresh;
   }

   @Generated
   public void setRes(final String res) {
      this.res = res;
   }

   @Generated
   public void setSrc(final String src) {
      this.src = src;
   }

   @Generated
   public void setExt(final Map<String, Object> ext) {
      this.ext = ext;
   }

   @Generated
   @Override
   public boolean equals(final Object o) {
      if (o == this) {
         return true;
      } else if (!(o instanceof SearchRequest other)) {
         return false;
      } else if (!other.canEqual(this)) {
         return false;
      } else {
         Object this$conc = this.getConc();
         Object other$conc = other.getConc();
         if (this$conc == null ? other$conc == null : this$conc.equals(other$conc)) {
            Object this$refresh = this.getRefresh();
            Object other$refresh = other.getRefresh();
            if (this$refresh == null ? other$refresh == null : this$refresh.equals(other$refresh)) {
               Object this$kw = this.getKw();
               Object other$kw = other.getKw();
               if (this$kw == null ? other$kw == null : this$kw.equals(other$kw)) {
                  Object this$channels = this.getChannels();
                  Object other$channels = other.getChannels();
                  if (this$channels == null ? other$channels == null : this$channels.equals(other$channels)) {
                     Object this$plugins = this.getPlugins();
                     Object other$plugins = other.getPlugins();
                     if (this$plugins == null ? other$plugins == null : this$plugins.equals(other$plugins)) {
                        Object this$cloudTypes = this.getCloudTypes();
                        Object other$cloudTypes = other.getCloudTypes();
                        if (this$cloudTypes == null ? other$cloudTypes == null : this$cloudTypes.equals(other$cloudTypes)) {
                           Object this$res = this.getRes();
                           Object other$res = other.getRes();
                           if (this$res == null ? other$res == null : this$res.equals(other$res)) {
                              Object this$src = this.getSrc();
                              Object other$src = other.getSrc();
                              if (this$src == null ? other$src == null : this$src.equals(other$src)) {
                                 Object this$ext = this.getExt();
                                 Object other$ext = other.getExt();
                                 return this$ext == null ? other$ext == null : this$ext.equals(other$ext);
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
      return other instanceof SearchRequest;
   }

   @Generated
   @Override
   public int hashCode() {
      int PRIME = 59;
      int result = 1;
      Object $conc = this.getConc();
      result = result * 59 + ($conc == null ? 43 : $conc.hashCode());
      Object $refresh = this.getRefresh();
      result = result * 59 + ($refresh == null ? 43 : $refresh.hashCode());
      Object $kw = this.getKw();
      result = result * 59 + ($kw == null ? 43 : $kw.hashCode());
      Object $channels = this.getChannels();
      result = result * 59 + ($channels == null ? 43 : $channels.hashCode());
      Object $plugins = this.getPlugins();
      result = result * 59 + ($plugins == null ? 43 : $plugins.hashCode());
      Object $cloudTypes = this.getCloudTypes();
      result = result * 59 + ($cloudTypes == null ? 43 : $cloudTypes.hashCode());
      Object $res = this.getRes();
      result = result * 59 + ($res == null ? 43 : $res.hashCode());
      Object $src = this.getSrc();
      result = result * 59 + ($src == null ? 43 : $src.hashCode());
      Object $ext = this.getExt();
      return result * 59 + ($ext == null ? 43 : $ext.hashCode());
   }

   @Generated
   @Override
   public String toString() {
      return "SearchRequest(kw="
         + this.getKw()
         + ", channels="
         + this.getChannels()
         + ", plugins="
         + this.getPlugins()
         + ", cloudTypes="
         + this.getCloudTypes()
         + ", conc="
         + this.getConc()
         + ", refresh="
         + this.getRefresh()
         + ", res="
         + this.getRes()
         + ", src="
         + this.getSrc()
         + ", ext="
         + this.getExt()
         + ")";
   }

   @Generated
   public SearchRequest() {
   }

   @Generated
   public SearchRequest(
      final String kw,
      final List<String> channels,
      final List<String> plugins,
      final List<String> cloudTypes,
      final Integer conc,
      final Boolean refresh,
      final String res,
      final String src,
      final Map<String, Object> ext
   ) {
      this.kw = kw;
      this.channels = channels;
      this.plugins = plugins;
      this.cloudTypes = cloudTypes;
      this.conc = conc;
      this.refresh = refresh;
      this.res = res;
      this.src = src;
      this.ext = ext;
   }

   @Generated
   public static class SearchRequestBuilder {
      @Generated
      private String kw;
      @Generated
      private List<String> channels;
      @Generated
      private List<String> plugins;
      @Generated
      private List<String> cloudTypes;
      @Generated
      private Integer conc;
      @Generated
      private Boolean refresh;
      @Generated
      private String res;
      @Generated
      private String src;
      @Generated
      private Map<String, Object> ext;

      @Generated
      SearchRequestBuilder() {
      }

      @Generated
      public SearchRequest.SearchRequestBuilder kw(final String kw) {
         this.kw = kw;
         return this;
      }

      @Generated
      public SearchRequest.SearchRequestBuilder channels(final List<String> channels) {
         this.channels = channels;
         return this;
      }

      @Generated
      public SearchRequest.SearchRequestBuilder plugins(final List<String> plugins) {
         this.plugins = plugins;
         return this;
      }

      @JsonProperty("cloud_types")
      @Generated
      public SearchRequest.SearchRequestBuilder cloudTypes(final List<String> cloudTypes) {
         this.cloudTypes = cloudTypes;
         return this;
      }

      @Generated
      public SearchRequest.SearchRequestBuilder conc(final Integer conc) {
         this.conc = conc;
         return this;
      }

      @Generated
      public SearchRequest.SearchRequestBuilder refresh(final Boolean refresh) {
         this.refresh = refresh;
         return this;
      }

      @Generated
      public SearchRequest.SearchRequestBuilder res(final String res) {
         this.res = res;
         return this;
      }

      @Generated
      public SearchRequest.SearchRequestBuilder src(final String src) {
         this.src = src;
         return this;
      }

      @Generated
      public SearchRequest.SearchRequestBuilder ext(final Map<String, Object> ext) {
         this.ext = ext;
         return this;
      }

      @Generated
      public SearchRequest build() {
         return new SearchRequest(this.kw, this.channels, this.plugins, this.cloudTypes, this.conc, this.refresh, this.res, this.src, this.ext);
      }

      @Generated
      @Override
      public String toString() {
         return "SearchRequest.SearchRequestBuilder(kw="
            + this.kw
            + ", channels="
            + this.channels
            + ", plugins="
            + this.plugins
            + ", cloudTypes="
            + this.cloudTypes
            + ", conc="
            + this.conc
            + ", refresh="
            + this.refresh
            + ", res="
            + this.res
            + ", src="
            + this.src
            + ", ext="
            + this.ext
            + ")";
      }
   }
}
