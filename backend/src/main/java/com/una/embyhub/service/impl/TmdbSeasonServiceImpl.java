package com.una.embyhub.service.impl;

import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.una.embyhub.mapper.TmdbSeasonMapper;
import com.una.embyhub.model.entity.TmdbSeason;
import com.una.embyhub.service.TmdbSeasonService;
import java.util.List;
import lombok.Generated;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(
   rollbackFor = {Exception.class}
)
public class TmdbSeasonServiceImpl extends ServiceImpl<TmdbSeasonMapper, TmdbSeason> implements TmdbSeasonService {
   @Generated
   private static final Logger log = LoggerFactory.getLogger(TmdbSeasonServiceImpl.class);

   @Override
   public void saveOrUpdateSeason(TmdbSeason season) {
      if (season != null && season.getFollowId() != null && season.getSeasonNumber() != null) {
         TmdbSeason exist = new LambdaQueryChainWrapper<>(this.baseMapper)
            .eq(TmdbSeason::getFollowId, season.getFollowId())
            .eq(TmdbSeason::getSeasonNumber, season.getSeasonNumber())
            .one();
         if (exist != null) {
            season.setId(exist.getId());
            this.updateById(season);
         } else {
            this.save(season);
         }
      } else {
         log.warn("季度信息不完整，跳过写入：{}", season);
      }
   }

   @Override
   public List<TmdbSeason> listByFollow(Long followId) {
      return new LambdaQueryChainWrapper<>(this.baseMapper).eq(TmdbSeason::getFollowId, followId).orderByAsc(TmdbSeason::getSeasonNumber).list();
   }
}
