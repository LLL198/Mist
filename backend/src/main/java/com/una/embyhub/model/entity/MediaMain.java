package com.una.embyhub.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import lombok.Generated;

@TableName("media_main")
public class MediaMain extends BaseEntity implements Serializable {
   public static final String COL_CREATE_TIME = "create_time";
   public static final String COL_UPDATE_TIME = "update_time";
   private static final long serialVersionUID = 1L;
   @TableId(
      value = "id",
      type = IdType.AUTO
   )
   private Long id;
   @TableField("title")
   private String title;
   @TableField("emby_info_id")
   private Long embyInfoId;
   @TableField("`type`")
   private String type;
   @TableField("poster_path")
   private String posterPath;
   @TableField("release_date")
   private Date releaseDate;
   @TableField("play_count")
   private Integer playCount;
   @TableField("rating")
   private BigDecimal rating;
   @TableField("duration")
   private Integer duration;
   public static final String COL_ID = "id";
   public static final String COL_TITLE = "title";
   public static final String COL_EMBY_INFO_ID = "emby_info_id";
   public static final String COL_TYPE = "type";
   public static final String COL_POSTER_PATH = "poster_path";
   public static final String COL_RELEASE_DATE = "release_date";
   public static final String COL_PLAY_COUNT = "play_count";
   public static final String COL_RATING = "rating";
   public static final String COL_DURATION = "duration";
   public static final String COL_CREATE_DATETIME = "create_datetime";
   public static final String COL_UPDATE_DATETIME = "update_datetime";
   public static final String COL_CREATE_USER_NAME = "create_user_name";
   public static final String COL_UPDATE_USER_NAME = "update_user_name";
   public static final String COL_UPDATE_USER_ID = "update_user_id";
   public static final String COL_CREATE_USER_ID = "create_user_id";
   public static final String COL_DEL_FLAG = "del_flag";

   @Generated
   public Long getId() {
      return this.id;
   }

   @Generated
   public String getTitle() {
      return this.title;
   }

   @Generated
   public Long getEmbyInfoId() {
      return this.embyInfoId;
   }

   @Generated
   public String getType() {
      return this.type;
   }

   @Generated
   public String getPosterPath() {
      return this.posterPath;
   }

   @Generated
   public Date getReleaseDate() {
      return this.releaseDate;
   }

   @Generated
   public Integer getPlayCount() {
      return this.playCount;
   }

   @Generated
   public BigDecimal getRating() {
      return this.rating;
   }

   @Generated
   public Integer getDuration() {
      return this.duration;
   }

   @Generated
   public void setId(final Long id) {
      this.id = id;
   }

   @Generated
   public void setTitle(final String title) {
      this.title = title;
   }

   @Generated
   public void setEmbyInfoId(final Long embyInfoId) {
      this.embyInfoId = embyInfoId;
   }

   @Generated
   public void setType(final String type) {
      this.type = type;
   }

   @Generated
   public void setPosterPath(final String posterPath) {
      this.posterPath = posterPath;
   }

   @Generated
   public void setReleaseDate(final Date releaseDate) {
      this.releaseDate = releaseDate;
   }

   @Generated
   public void setPlayCount(final Integer playCount) {
      this.playCount = playCount;
   }

   @Generated
   public void setRating(final BigDecimal rating) {
      this.rating = rating;
   }

   @Generated
   public void setDuration(final Integer duration) {
      this.duration = duration;
   }

   @Generated
   @Override
   public String toString() {
      return "MediaMain(id="
         + this.getId()
         + ", title="
         + this.getTitle()
         + ", embyInfoId="
         + this.getEmbyInfoId()
         + ", type="
         + this.getType()
         + ", posterPath="
         + this.getPosterPath()
         + ", releaseDate="
         + this.getReleaseDate()
         + ", playCount="
         + this.getPlayCount()
         + ", rating="
         + this.getRating()
         + ", duration="
         + this.getDuration()
         + ")";
   }

   @Generated
   @Override
   public boolean equals(final Object o) {
      if (o == this) {
         return true;
      } else if (!(o instanceof MediaMain other)) {
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
               Object this$playCount = this.getPlayCount();
               Object other$playCount = other.getPlayCount();
               if (this$playCount == null ? other$playCount == null : this$playCount.equals(other$playCount)) {
                  Object this$duration = this.getDuration();
                  Object other$duration = other.getDuration();
                  if (this$duration == null ? other$duration == null : this$duration.equals(other$duration)) {
                     Object this$title = this.getTitle();
                     Object other$title = other.getTitle();
                     if (this$title == null ? other$title == null : this$title.equals(other$title)) {
                        Object this$type = this.getType();
                        Object other$type = other.getType();
                        if (this$type == null ? other$type == null : this$type.equals(other$type)) {
                           Object this$posterPath = this.getPosterPath();
                           Object other$posterPath = other.getPosterPath();
                           if (this$posterPath == null ? other$posterPath == null : this$posterPath.equals(other$posterPath)) {
                              Object this$releaseDate = this.getReleaseDate();
                              Object other$releaseDate = other.getReleaseDate();
                              if (this$releaseDate == null ? other$releaseDate == null : this$releaseDate.equals(other$releaseDate)) {
                                 Object this$rating = this.getRating();
                                 Object other$rating = other.getRating();
                                 return this$rating == null ? other$rating == null : this$rating.equals(other$rating);
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
      return other instanceof MediaMain;
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
      Object $playCount = this.getPlayCount();
      result = result * 59 + ($playCount == null ? 43 : $playCount.hashCode());
      Object $duration = this.getDuration();
      result = result * 59 + ($duration == null ? 43 : $duration.hashCode());
      Object $title = this.getTitle();
      result = result * 59 + ($title == null ? 43 : $title.hashCode());
      Object $type = this.getType();
      result = result * 59 + ($type == null ? 43 : $type.hashCode());
      Object $posterPath = this.getPosterPath();
      result = result * 59 + ($posterPath == null ? 43 : $posterPath.hashCode());
      Object $releaseDate = this.getReleaseDate();
      result = result * 59 + ($releaseDate == null ? 43 : $releaseDate.hashCode());
      Object $rating = this.getRating();
      return result * 59 + ($rating == null ? 43 : $rating.hashCode());
   }
}
