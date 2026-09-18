package com.una.embyhub.model.dto.response.telegram;

import com.alibaba.fastjson2.annotation.JSONField;
import java.util.List;
import java.util.Map;
import lombok.Generated;

public class SearchResponse {
   private Integer code;
   private String message;
   private SearchResponse.SearchDataResponse data = new SearchResponse.SearchDataResponse();

   @Generated
   public static SearchResponse.SearchResponseBuilder builder() {
      return new SearchResponse.SearchResponseBuilder();
   }

   @Generated
   public Integer getCode() {
      return this.code;
   }

   @Generated
   public String getMessage() {
      return this.message;
   }

   @Generated
   public SearchResponse.SearchDataResponse getData() {
      return this.data;
   }

   @Generated
   public void setCode(final Integer code) {
      this.code = code;
   }

   @Generated
   public void setMessage(final String message) {
      this.message = message;
   }

   @Generated
   public void setData(final SearchResponse.SearchDataResponse data) {
      this.data = data;
   }

   @Generated
   @Override
   public boolean equals(final Object o) {
      if (o == this) {
         return true;
      } else if (!(o instanceof SearchResponse other)) {
         return false;
      } else if (!other.canEqual(this)) {
         return false;
      } else {
         Object this$code = this.getCode();
         Object other$code = other.getCode();
         if (this$code == null ? other$code == null : this$code.equals(other$code)) {
            Object this$message = this.getMessage();
            Object other$message = other.getMessage();
            if (this$message == null ? other$message == null : this$message.equals(other$message)) {
               Object this$data = this.getData();
               Object other$data = other.getData();
               return this$data == null ? other$data == null : this$data.equals(other$data);
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
      return other instanceof SearchResponse;
   }

   @Generated
   @Override
   public int hashCode() {
      int PRIME = 59;
      int result = 1;
      Object $code = this.getCode();
      result = result * 59 + ($code == null ? 43 : $code.hashCode());
      Object $message = this.getMessage();
      result = result * 59 + ($message == null ? 43 : $message.hashCode());
      Object $data = this.getData();
      return result * 59 + ($data == null ? 43 : $data.hashCode());
   }

   @Generated
   @Override
   public String toString() {
      return "SearchResponse(code=" + this.getCode() + ", message=" + this.getMessage() + ", data=" + this.getData() + ")";
   }

   @Generated
   public SearchResponse() {
   }

   @Generated
   public SearchResponse(final Integer code, final String message, final SearchResponse.SearchDataResponse data) {
      this.code = code;
      this.message = message;
      this.data = data;
   }

   public static class SearchDataResponse {
      private Integer total;
      @JSONField(
         name = "merged_by_type"
      )
      private Map<String, List<SearchResponse.SearchDataResponse.LinkItem>> mergedByType;
      private List<SearchResponse.SearchDataResponse.ResultItem> results;

      @Generated
      public static SearchResponse.SearchDataResponse.SearchDataResponseBuilder builder() {
         return new SearchResponse.SearchDataResponse.SearchDataResponseBuilder();
      }

      @Generated
      public Integer getTotal() {
         return this.total;
      }

      @Generated
      public Map<String, List<SearchResponse.SearchDataResponse.LinkItem>> getMergedByType() {
         return this.mergedByType;
      }

      @Generated
      public List<SearchResponse.SearchDataResponse.ResultItem> getResults() {
         return this.results;
      }

      @Generated
      public void setTotal(final Integer total) {
         this.total = total;
      }

      @Generated
      public void setMergedByType(final Map<String, List<SearchResponse.SearchDataResponse.LinkItem>> mergedByType) {
         this.mergedByType = mergedByType;
      }

      @Generated
      public void setResults(final List<SearchResponse.SearchDataResponse.ResultItem> results) {
         this.results = results;
      }

      @Generated
      @Override
      public boolean equals(final Object o) {
         if (o == this) {
            return true;
         } else if (!(o instanceof SearchResponse.SearchDataResponse other)) {
            return false;
         } else if (!other.canEqual(this)) {
            return false;
         } else {
            Object this$total = this.getTotal();
            Object other$total = other.getTotal();
            if (this$total == null ? other$total == null : this$total.equals(other$total)) {
               Object this$mergedByType = this.getMergedByType();
               Object other$mergedByType = other.getMergedByType();
               if (this$mergedByType == null ? other$mergedByType == null : this$mergedByType.equals(other$mergedByType)) {
                  Object this$results = this.getResults();
                  Object other$results = other.getResults();
                  return this$results == null ? other$results == null : this$results.equals(other$results);
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
         return other instanceof SearchResponse.SearchDataResponse;
      }

      @Generated
      @Override
      public int hashCode() {
         int PRIME = 59;
         int result = 1;
         Object $total = this.getTotal();
         result = result * 59 + ($total == null ? 43 : $total.hashCode());
         Object $mergedByType = this.getMergedByType();
         result = result * 59 + ($mergedByType == null ? 43 : $mergedByType.hashCode());
         Object $results = this.getResults();
         return result * 59 + ($results == null ? 43 : $results.hashCode());
      }

      @Generated
      @Override
      public String toString() {
         return "SearchResponse.SearchDataResponse(total="
            + this.getTotal()
            + ", mergedByType="
            + this.getMergedByType()
            + ", results="
            + this.getResults()
            + ")";
      }

      @Generated
      public SearchDataResponse() {
      }

      @Generated
      public SearchDataResponse(
         final Integer total,
         final Map<String, List<SearchResponse.SearchDataResponse.LinkItem>> mergedByType,
         final List<SearchResponse.SearchDataResponse.ResultItem> results
      ) {
         this.total = total;
         this.mergedByType = mergedByType;
         this.results = results;
      }

      public static class LinkBrief {
         private String type;
         private String url;
         private String password;

         @Generated
         public static SearchResponse.SearchDataResponse.LinkBrief.LinkBriefBuilder builder() {
            return new SearchResponse.SearchDataResponse.LinkBrief.LinkBriefBuilder();
         }

         @Generated
         public String getType() {
            return this.type;
         }

         @Generated
         public String getUrl() {
            return this.url;
         }

         @Generated
         public String getPassword() {
            return this.password;
         }

         @Generated
         public void setType(final String type) {
            this.type = type;
         }

         @Generated
         public void setUrl(final String url) {
            this.url = url;
         }

         @Generated
         public void setPassword(final String password) {
            this.password = password;
         }

         @Generated
         @Override
         public boolean equals(final Object o) {
            if (o == this) {
               return true;
            } else if (!(o instanceof SearchResponse.SearchDataResponse.LinkBrief other)) {
               return false;
            } else if (!other.canEqual(this)) {
               return false;
            } else {
               Object this$type = this.getType();
               Object other$type = other.getType();
               if (this$type == null ? other$type == null : this$type.equals(other$type)) {
                  Object this$url = this.getUrl();
                  Object other$url = other.getUrl();
                  if (this$url == null ? other$url == null : this$url.equals(other$url)) {
                     Object this$password = this.getPassword();
                     Object other$password = other.getPassword();
                     return this$password == null ? other$password == null : this$password.equals(other$password);
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
            return other instanceof SearchResponse.SearchDataResponse.LinkBrief;
         }

         @Generated
         @Override
         public int hashCode() {
            int PRIME = 59;
            int result = 1;
            Object $type = this.getType();
            result = result * 59 + ($type == null ? 43 : $type.hashCode());
            Object $url = this.getUrl();
            result = result * 59 + ($url == null ? 43 : $url.hashCode());
            Object $password = this.getPassword();
            return result * 59 + ($password == null ? 43 : $password.hashCode());
         }

         @Generated
         @Override
         public String toString() {
            return "SearchResponse.SearchDataResponse.LinkBrief(type=" + this.getType() + ", url=" + this.getUrl() + ", password=" + this.getPassword() + ")";
         }

         @Generated
         public LinkBrief() {
         }

         @Generated
         public LinkBrief(final String type, final String url, final String password) {
            this.type = type;
            this.url = url;
            this.password = password;
         }

         @Generated
         public static class LinkBriefBuilder {
            @Generated
            private String type;
            @Generated
            private String url;
            @Generated
            private String password;

            @Generated
            LinkBriefBuilder() {
            }

            @Generated
            public SearchResponse.SearchDataResponse.LinkBrief.LinkBriefBuilder type(final String type) {
               this.type = type;
               return this;
            }

            @Generated
            public SearchResponse.SearchDataResponse.LinkBrief.LinkBriefBuilder url(final String url) {
               this.url = url;
               return this;
            }

            @Generated
            public SearchResponse.SearchDataResponse.LinkBrief.LinkBriefBuilder password(final String password) {
               this.password = password;
               return this;
            }

            @Generated
            public SearchResponse.SearchDataResponse.LinkBrief build() {
               return new SearchResponse.SearchDataResponse.LinkBrief(this.type, this.url, this.password);
            }

            @Generated
            @Override
            public String toString() {
               return "SearchResponse.SearchDataResponse.LinkBrief.LinkBriefBuilder(type="
                  + this.type
                  + ", url="
                  + this.url
                  + ", password="
                  + this.password
                  + ")";
            }
         }
      }

      public static class LinkItem {
         private String url;
         private String password;
         private String note;
         private String datetime;
         private String source;
         private List<String> images;
         private String type;

         @Generated
         public static SearchResponse.SearchDataResponse.LinkItem.LinkItemBuilder builder() {
            return new SearchResponse.SearchDataResponse.LinkItem.LinkItemBuilder();
         }

         @Generated
         public String getUrl() {
            return this.url;
         }

         @Generated
         public String getPassword() {
            return this.password;
         }

         @Generated
         public String getNote() {
            return this.note;
         }

         @Generated
         public String getDatetime() {
            return this.datetime;
         }

         @Generated
         public String getSource() {
            return this.source;
         }

         @Generated
         public List<String> getImages() {
            return this.images;
         }

         @Generated
         public String getType() {
            return this.type;
         }

         @Generated
         public void setUrl(final String url) {
            this.url = url;
         }

         @Generated
         public void setPassword(final String password) {
            this.password = password;
         }

         @Generated
         public void setNote(final String note) {
            this.note = note;
         }

         @Generated
         public void setDatetime(final String datetime) {
            this.datetime = datetime;
         }

         @Generated
         public void setSource(final String source) {
            this.source = source;
         }

         @Generated
         public void setImages(final List<String> images) {
            this.images = images;
         }

         @Generated
         public void setType(final String type) {
            this.type = type;
         }

         @Generated
         @Override
         public boolean equals(final Object o) {
            if (o == this) {
               return true;
            } else if (!(o instanceof SearchResponse.SearchDataResponse.LinkItem other)) {
               return false;
            } else if (!other.canEqual(this)) {
               return false;
            } else {
               Object this$url = this.getUrl();
               Object other$url = other.getUrl();
               if (this$url == null ? other$url == null : this$url.equals(other$url)) {
                  Object this$password = this.getPassword();
                  Object other$password = other.getPassword();
                  if (this$password == null ? other$password == null : this$password.equals(other$password)) {
                     Object this$note = this.getNote();
                     Object other$note = other.getNote();
                     if (this$note == null ? other$note == null : this$note.equals(other$note)) {
                        Object this$datetime = this.getDatetime();
                        Object other$datetime = other.getDatetime();
                        if (this$datetime == null ? other$datetime == null : this$datetime.equals(other$datetime)) {
                           Object this$source = this.getSource();
                           Object other$source = other.getSource();
                           if (this$source == null ? other$source == null : this$source.equals(other$source)) {
                              Object this$images = this.getImages();
                              Object other$images = other.getImages();
                              if (this$images == null ? other$images == null : this$images.equals(other$images)) {
                                 Object this$type = this.getType();
                                 Object other$type = other.getType();
                                 return this$type == null ? other$type == null : this$type.equals(other$type);
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
            return other instanceof SearchResponse.SearchDataResponse.LinkItem;
         }

         @Generated
         @Override
         public int hashCode() {
            int PRIME = 59;
            int result = 1;
            Object $url = this.getUrl();
            result = result * 59 + ($url == null ? 43 : $url.hashCode());
            Object $password = this.getPassword();
            result = result * 59 + ($password == null ? 43 : $password.hashCode());
            Object $note = this.getNote();
            result = result * 59 + ($note == null ? 43 : $note.hashCode());
            Object $datetime = this.getDatetime();
            result = result * 59 + ($datetime == null ? 43 : $datetime.hashCode());
            Object $source = this.getSource();
            result = result * 59 + ($source == null ? 43 : $source.hashCode());
            Object $images = this.getImages();
            result = result * 59 + ($images == null ? 43 : $images.hashCode());
            Object $type = this.getType();
            return result * 59 + ($type == null ? 43 : $type.hashCode());
         }

         @Generated
         @Override
         public String toString() {
            return "SearchResponse.SearchDataResponse.LinkItem(url="
               + this.getUrl()
               + ", password="
               + this.getPassword()
               + ", note="
               + this.getNote()
               + ", datetime="
               + this.getDatetime()
               + ", source="
               + this.getSource()
               + ", images="
               + this.getImages()
               + ", type="
               + this.getType()
               + ")";
         }

         @Generated
         public LinkItem() {
         }

         @Generated
         public LinkItem(
            final String url,
            final String password,
            final String note,
            final String datetime,
            final String source,
            final List<String> images,
            final String type
         ) {
            this.url = url;
            this.password = password;
            this.note = note;
            this.datetime = datetime;
            this.source = source;
            this.images = images;
            this.type = type;
         }

         @Generated
         public static class LinkItemBuilder {
            @Generated
            private String url;
            @Generated
            private String password;
            @Generated
            private String note;
            @Generated
            private String datetime;
            @Generated
            private String source;
            @Generated
            private List<String> images;
            @Generated
            private String type;

            @Generated
            LinkItemBuilder() {
            }

            @Generated
            public SearchResponse.SearchDataResponse.LinkItem.LinkItemBuilder url(final String url) {
               this.url = url;
               return this;
            }

            @Generated
            public SearchResponse.SearchDataResponse.LinkItem.LinkItemBuilder password(final String password) {
               this.password = password;
               return this;
            }

            @Generated
            public SearchResponse.SearchDataResponse.LinkItem.LinkItemBuilder note(final String note) {
               this.note = note;
               return this;
            }

            @Generated
            public SearchResponse.SearchDataResponse.LinkItem.LinkItemBuilder datetime(final String datetime) {
               this.datetime = datetime;
               return this;
            }

            @Generated
            public SearchResponse.SearchDataResponse.LinkItem.LinkItemBuilder source(final String source) {
               this.source = source;
               return this;
            }

            @Generated
            public SearchResponse.SearchDataResponse.LinkItem.LinkItemBuilder images(final List<String> images) {
               this.images = images;
               return this;
            }

            @Generated
            public SearchResponse.SearchDataResponse.LinkItem.LinkItemBuilder type(final String type) {
               this.type = type;
               return this;
            }

            @Generated
            public SearchResponse.SearchDataResponse.LinkItem build() {
               return new SearchResponse.SearchDataResponse.LinkItem(this.url, this.password, this.note, this.datetime, this.source, this.images, this.type);
            }

            @Generated
            @Override
            public String toString() {
               return "SearchResponse.SearchDataResponse.LinkItem.LinkItemBuilder(url="
                  + this.url
                  + ", password="
                  + this.password
                  + ", note="
                  + this.note
                  + ", datetime="
                  + this.datetime
                  + ", source="
                  + this.source
                  + ", images="
                  + this.images
                  + ", type="
                  + this.type
                  + ")";
            }
         }
      }

      public static class ResultItem {
         private String messageId;
         private String uniqueId;
         private String channel;
         private String datetime;
         private String title;
         private String content;
         private List<String> tags;
         private List<String> images;
         private List<SearchResponse.SearchDataResponse.LinkBrief> links;

         @Generated
         public static SearchResponse.SearchDataResponse.ResultItem.ResultItemBuilder builder() {
            return new SearchResponse.SearchDataResponse.ResultItem.ResultItemBuilder();
         }

         @Generated
         public String getMessageId() {
            return this.messageId;
         }

         @Generated
         public String getUniqueId() {
            return this.uniqueId;
         }

         @Generated
         public String getChannel() {
            return this.channel;
         }

         @Generated
         public String getDatetime() {
            return this.datetime;
         }

         @Generated
         public String getTitle() {
            return this.title;
         }

         @Generated
         public String getContent() {
            return this.content;
         }

         @Generated
         public List<String> getTags() {
            return this.tags;
         }

         @Generated
         public List<String> getImages() {
            return this.images;
         }

         @Generated
         public List<SearchResponse.SearchDataResponse.LinkBrief> getLinks() {
            return this.links;
         }

         @Generated
         public void setMessageId(final String messageId) {
            this.messageId = messageId;
         }

         @Generated
         public void setUniqueId(final String uniqueId) {
            this.uniqueId = uniqueId;
         }

         @Generated
         public void setChannel(final String channel) {
            this.channel = channel;
         }

         @Generated
         public void setDatetime(final String datetime) {
            this.datetime = datetime;
         }

         @Generated
         public void setTitle(final String title) {
            this.title = title;
         }

         @Generated
         public void setContent(final String content) {
            this.content = content;
         }

         @Generated
         public void setTags(final List<String> tags) {
            this.tags = tags;
         }

         @Generated
         public void setImages(final List<String> images) {
            this.images = images;
         }

         @Generated
         public void setLinks(final List<SearchResponse.SearchDataResponse.LinkBrief> links) {
            this.links = links;
         }

         @Generated
         @Override
         public boolean equals(final Object o) {
            if (o == this) {
               return true;
            } else if (!(o instanceof SearchResponse.SearchDataResponse.ResultItem other)) {
               return false;
            } else if (!other.canEqual(this)) {
               return false;
            } else {
               Object this$messageId = this.getMessageId();
               Object other$messageId = other.getMessageId();
               if (this$messageId == null ? other$messageId == null : this$messageId.equals(other$messageId)) {
                  Object this$uniqueId = this.getUniqueId();
                  Object other$uniqueId = other.getUniqueId();
                  if (this$uniqueId == null ? other$uniqueId == null : this$uniqueId.equals(other$uniqueId)) {
                     Object this$channel = this.getChannel();
                     Object other$channel = other.getChannel();
                     if (this$channel == null ? other$channel == null : this$channel.equals(other$channel)) {
                        Object this$datetime = this.getDatetime();
                        Object other$datetime = other.getDatetime();
                        if (this$datetime == null ? other$datetime == null : this$datetime.equals(other$datetime)) {
                           Object this$title = this.getTitle();
                           Object other$title = other.getTitle();
                           if (this$title == null ? other$title == null : this$title.equals(other$title)) {
                              Object this$content = this.getContent();
                              Object other$content = other.getContent();
                              if (this$content == null ? other$content == null : this$content.equals(other$content)) {
                                 Object this$tags = this.getTags();
                                 Object other$tags = other.getTags();
                                 if (this$tags == null ? other$tags == null : this$tags.equals(other$tags)) {
                                    Object this$images = this.getImages();
                                    Object other$images = other.getImages();
                                    if (this$images == null ? other$images == null : this$images.equals(other$images)) {
                                       Object this$links = this.getLinks();
                                       Object other$links = other.getLinks();
                                       return this$links == null ? other$links == null : this$links.equals(other$links);
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
            return other instanceof SearchResponse.SearchDataResponse.ResultItem;
         }

         @Generated
         @Override
         public int hashCode() {
            int PRIME = 59;
            int result = 1;
            Object $messageId = this.getMessageId();
            result = result * 59 + ($messageId == null ? 43 : $messageId.hashCode());
            Object $uniqueId = this.getUniqueId();
            result = result * 59 + ($uniqueId == null ? 43 : $uniqueId.hashCode());
            Object $channel = this.getChannel();
            result = result * 59 + ($channel == null ? 43 : $channel.hashCode());
            Object $datetime = this.getDatetime();
            result = result * 59 + ($datetime == null ? 43 : $datetime.hashCode());
            Object $title = this.getTitle();
            result = result * 59 + ($title == null ? 43 : $title.hashCode());
            Object $content = this.getContent();
            result = result * 59 + ($content == null ? 43 : $content.hashCode());
            Object $tags = this.getTags();
            result = result * 59 + ($tags == null ? 43 : $tags.hashCode());
            Object $images = this.getImages();
            result = result * 59 + ($images == null ? 43 : $images.hashCode());
            Object $links = this.getLinks();
            return result * 59 + ($links == null ? 43 : $links.hashCode());
         }

         @Generated
         @Override
         public String toString() {
            return "SearchResponse.SearchDataResponse.ResultItem(messageId="
               + this.getMessageId()
               + ", uniqueId="
               + this.getUniqueId()
               + ", channel="
               + this.getChannel()
               + ", datetime="
               + this.getDatetime()
               + ", title="
               + this.getTitle()
               + ", content="
               + this.getContent()
               + ", tags="
               + this.getTags()
               + ", images="
               + this.getImages()
               + ", links="
               + this.getLinks()
               + ")";
         }

         @Generated
         public ResultItem() {
         }

         @Generated
         public ResultItem(
            final String messageId,
            final String uniqueId,
            final String channel,
            final String datetime,
            final String title,
            final String content,
            final List<String> tags,
            final List<String> images,
            final List<SearchResponse.SearchDataResponse.LinkBrief> links
         ) {
            this.messageId = messageId;
            this.uniqueId = uniqueId;
            this.channel = channel;
            this.datetime = datetime;
            this.title = title;
            this.content = content;
            this.tags = tags;
            this.images = images;
            this.links = links;
         }

         @Generated
         public static class ResultItemBuilder {
            @Generated
            private String messageId;
            @Generated
            private String uniqueId;
            @Generated
            private String channel;
            @Generated
            private String datetime;
            @Generated
            private String title;
            @Generated
            private String content;
            @Generated
            private List<String> tags;
            @Generated
            private List<String> images;
            @Generated
            private List<SearchResponse.SearchDataResponse.LinkBrief> links;

            @Generated
            ResultItemBuilder() {
            }

            @Generated
            public SearchResponse.SearchDataResponse.ResultItem.ResultItemBuilder messageId(final String messageId) {
               this.messageId = messageId;
               return this;
            }

            @Generated
            public SearchResponse.SearchDataResponse.ResultItem.ResultItemBuilder uniqueId(final String uniqueId) {
               this.uniqueId = uniqueId;
               return this;
            }

            @Generated
            public SearchResponse.SearchDataResponse.ResultItem.ResultItemBuilder channel(final String channel) {
               this.channel = channel;
               return this;
            }

            @Generated
            public SearchResponse.SearchDataResponse.ResultItem.ResultItemBuilder datetime(final String datetime) {
               this.datetime = datetime;
               return this;
            }

            @Generated
            public SearchResponse.SearchDataResponse.ResultItem.ResultItemBuilder title(final String title) {
               this.title = title;
               return this;
            }

            @Generated
            public SearchResponse.SearchDataResponse.ResultItem.ResultItemBuilder content(final String content) {
               this.content = content;
               return this;
            }

            @Generated
            public SearchResponse.SearchDataResponse.ResultItem.ResultItemBuilder tags(final List<String> tags) {
               this.tags = tags;
               return this;
            }

            @Generated
            public SearchResponse.SearchDataResponse.ResultItem.ResultItemBuilder images(final List<String> images) {
               this.images = images;
               return this;
            }

            @Generated
            public SearchResponse.SearchDataResponse.ResultItem.ResultItemBuilder links(final List<SearchResponse.SearchDataResponse.LinkBrief> links) {
               this.links = links;
               return this;
            }

            @Generated
            public SearchResponse.SearchDataResponse.ResultItem build() {
               return new SearchResponse.SearchDataResponse.ResultItem(
                  this.messageId, this.uniqueId, this.channel, this.datetime, this.title, this.content, this.tags, this.images, this.links
               );
            }

            @Generated
            @Override
            public String toString() {
               return "SearchResponse.SearchDataResponse.ResultItem.ResultItemBuilder(messageId="
                  + this.messageId
                  + ", uniqueId="
                  + this.uniqueId
                  + ", channel="
                  + this.channel
                  + ", datetime="
                  + this.datetime
                  + ", title="
                  + this.title
                  + ", content="
                  + this.content
                  + ", tags="
                  + this.tags
                  + ", images="
                  + this.images
                  + ", links="
                  + this.links
                  + ")";
            }
         }
      }

      @Generated
      public static class SearchDataResponseBuilder {
         @Generated
         private Integer total;
         @Generated
         private Map<String, List<SearchResponse.SearchDataResponse.LinkItem>> mergedByType;
         @Generated
         private List<SearchResponse.SearchDataResponse.ResultItem> results;

         @Generated
         SearchDataResponseBuilder() {
         }

         @Generated
         public SearchResponse.SearchDataResponse.SearchDataResponseBuilder total(final Integer total) {
            this.total = total;
            return this;
         }

         @Generated
         public SearchResponse.SearchDataResponse.SearchDataResponseBuilder mergedByType(
            final Map<String, List<SearchResponse.SearchDataResponse.LinkItem>> mergedByType
         ) {
            this.mergedByType = mergedByType;
            return this;
         }

         @Generated
         public SearchResponse.SearchDataResponse.SearchDataResponseBuilder results(final List<SearchResponse.SearchDataResponse.ResultItem> results) {
            this.results = results;
            return this;
         }

         @Generated
         public SearchResponse.SearchDataResponse build() {
            return new SearchResponse.SearchDataResponse(this.total, this.mergedByType, this.results);
         }

         @Generated
         @Override
         public String toString() {
            return "SearchResponse.SearchDataResponse.SearchDataResponseBuilder(total="
               + this.total
               + ", mergedByType="
               + this.mergedByType
               + ", results="
               + this.results
               + ")";
         }
      }
   }

   @Generated
   public static class SearchResponseBuilder {
      @Generated
      private Integer code;
      @Generated
      private String message;
      @Generated
      private SearchResponse.SearchDataResponse data;

      @Generated
      SearchResponseBuilder() {
      }

      @Generated
      public SearchResponse.SearchResponseBuilder code(final Integer code) {
         this.code = code;
         return this;
      }

      @Generated
      public SearchResponse.SearchResponseBuilder message(final String message) {
         this.message = message;
         return this;
      }

      @Generated
      public SearchResponse.SearchResponseBuilder data(final SearchResponse.SearchDataResponse data) {
         this.data = data;
         return this;
      }

      @Generated
      public SearchResponse build() {
         return new SearchResponse(this.code, this.message, this.data);
      }

      @Generated
      @Override
      public String toString() {
         return "SearchResponse.SearchResponseBuilder(code=" + this.code + ", message=" + this.message + ", data=" + this.data + ")";
      }
   }
}
