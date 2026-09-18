package com.una.embyhub.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.una.embyhub.model.entity.TmdbDailyRelease;
import java.util.Date;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;

public interface TmdbDailyReleaseMapper extends BaseMapper<TmdbDailyRelease> {
   @Delete({"DELETE FROM tmdb_daily_release WHERE publish_date = #{publishDate}"})
   int hardDeleteByPublishDate(@Param("publishDate") Date publishDate);
}
