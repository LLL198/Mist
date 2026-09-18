package com.una.embyhub.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.una.embyhub.model.entity.TmdbEpisode;
import java.util.List;

public interface TmdbEpisodeService extends IService<TmdbEpisode> {
   void saveOrUpdateEpisode(TmdbEpisode episode);

   List<TmdbEpisode> listByFollowAndSeason(Long followId, Integer seasonNumber);

   List<TmdbEpisode> listByFollow(Long followId);

   void removeOtherSeasons(Long followId, Integer keepSeasonNumber);

   Integer findLatestSeasonNumber(Long followId);
}
