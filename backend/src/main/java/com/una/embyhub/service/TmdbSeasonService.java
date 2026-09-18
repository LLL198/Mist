package com.una.embyhub.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.una.embyhub.model.entity.TmdbSeason;
import java.util.List;

public interface TmdbSeasonService extends IService<TmdbSeason> {
   void saveOrUpdateSeason(TmdbSeason season);

   List<TmdbSeason> listByFollow(Long followId);
}
