package com.una.embyhub.service.impl;

import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.una.embyhub.mapper.TmdbWatchProgressMapper;
import com.una.embyhub.model.entity.TmdbWatchProgress;
import com.una.embyhub.service.TmdbWatchProgressService;
import lombok.Generated;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Service
@Transactional(
   rollbackFor = {Exception.class}
)
public class TmdbWatchProgressServiceImpl extends ServiceImpl<TmdbWatchProgressMapper, TmdbWatchProgress> implements TmdbWatchProgressService {
   @Generated
   private static final Logger log = LoggerFactory.getLogger(TmdbWatchProgressServiceImpl.class);

   @Override
   public TmdbWatchProgress saveProgress(TmdbWatchProgress progress) {
      if (progress != null && progress.getFollowId() != null) {
         LambdaQueryChainWrapper<TmdbWatchProgress> wrapper = new LambdaQueryChainWrapper<>(this.baseMapper)
            .eq(TmdbWatchProgress::getFollowId, progress.getFollowId());
         if (StringUtils.hasText(progress.getWatcherName())) {
            wrapper.eq(TmdbWatchProgress::getWatcherName, progress.getWatcherName());
         }

         TmdbWatchProgress exist = wrapper.one();
         if (exist != null) {
            progress.setId(exist.getId());
            this.updateById(progress);
         } else {
            this.save(progress);
         }

         return progress;
      } else {
         throw new IllegalArgumentException("进度参数不能为空");
      }
   }
}
