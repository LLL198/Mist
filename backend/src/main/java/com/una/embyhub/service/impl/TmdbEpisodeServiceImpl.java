package com.una.embyhub.service.impl;

import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.una.embyhub.mapper.TmdbEpisodeMapper;
import com.una.embyhub.model.entity.TmdbEpisode;
import com.una.embyhub.service.TmdbEpisodeService;
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
public class TmdbEpisodeServiceImpl extends ServiceImpl<TmdbEpisodeMapper, TmdbEpisode> implements TmdbEpisodeService {
   @Generated
   private static final Logger log = LoggerFactory.getLogger(TmdbEpisodeServiceImpl.class);

   @Override
   public void saveOrUpdateEpisode(TmdbEpisode episode) {
      if (episode != null && episode.getFollowId() != null && episode.getSeasonNumber() != null && episode.getEpisodeNumber() != null) {
         TmdbEpisode exist = new LambdaQueryChainWrapper<>(this.baseMapper)
            .eq(TmdbEpisode::getFollowId, episode.getFollowId())
            .eq(TmdbEpisode::getSeasonNumber, episode.getSeasonNumber())
            .eq(TmdbEpisode::getEpisodeNumber, episode.getEpisodeNumber())
            .one();
         if (exist != null) {
            episode.setId(exist.getId());
            this.updateById(episode);
         } else {
            this.save(episode);
         }
      } else {
         log.warn("分集信息不完整，跳过写入：{}", episode);
      }
   }

   @Override
   public List<TmdbEpisode> listByFollowAndSeason(Long followId, Integer seasonNumber) {
      return new LambdaQueryChainWrapper<>(this.baseMapper)
         .eq(TmdbEpisode::getFollowId, followId)
         .eq(TmdbEpisode::getSeasonNumber, seasonNumber)
         .orderByAsc(TmdbEpisode::getEpisodeNumber)
         .list();
   }

   @Override
   public List<TmdbEpisode> listByFollow(Long followId) {
      return new LambdaQueryChainWrapper<>(this.baseMapper)
         .eq(TmdbEpisode::getFollowId, followId)
         .orderByAsc(TmdbEpisode::getSeasonNumber)
         .orderByAsc(TmdbEpisode::getEpisodeNumber)
         .list();
   }

   @Override
   public void removeOtherSeasons(Long followId, Integer keepSeasonNumber) {
      this.lambdaUpdate().eq(TmdbEpisode::getFollowId, followId).ne(TmdbEpisode::getSeasonNumber, keepSeasonNumber).remove();
   }

   @Override
   public Integer findLatestSeasonNumber(Long followId) {
      TmdbEpisode episode = new LambdaQueryChainWrapper<>(this.baseMapper)
         .eq(TmdbEpisode::getFollowId, followId)
         .orderByDesc(TmdbEpisode::getSeasonNumber)
         .orderByDesc(TmdbEpisode::getEpisodeNumber)
         .last("limit 1")
         .one();
      return episode != null ? episode.getSeasonNumber() : null;
   }
}
