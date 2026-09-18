package com.una.embyhub.movie.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.una.embyhub.config.common.exception.BizException;
import com.una.embyhub.movie.entity.MovieScrapePathConfigEntity;
import com.una.embyhub.movie.mapper.MovieScrapePathConfigMapper;
import com.una.embyhub.movie.model.MovieScrapePathConfig;
import com.una.embyhub.movie.model.MovieScrapePathConfigRequest;
import com.una.embyhub.movie.service.MovieScrapePathConfigService;
import java.util.List;
import lombok.Generated;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class MovieScrapePathConfigServiceImpl implements MovieScrapePathConfigService {
   private final MovieScrapePathConfigMapper configMapper;

   @Override
   public List<MovieScrapePathConfig> list() {
      QueryWrapper<MovieScrapePathConfigEntity> wrapper = new QueryWrapper<>();
      wrapper.eq("del_flag", Integer.valueOf(0));
      wrapper.orderByAsc("id");
      return this.configMapper.selectList(wrapper).stream().map(this::toModel).toList();
   }

   @Override
   public MovieScrapePathConfig getById(Long id) {
      MovieScrapePathConfigEntity entity = this.configMapper.selectById(id);
      if (entity != null && entity.getDelFlag() != 1) {
         return this.toModel(entity);
      } else {
         throw new BizException("刮削目录配置不存在");
      }
   }

   @Override
   public MovieScrapePathConfig save(MovieScrapePathConfigRequest request) {
      String name = this.trimToNull(request.getName());
      String qbDownloadPath = this.trimToNull(request.getQbDownloadPath());
      String hardlinkPath = this.trimToNull(request.getHardlinkPath());
      if (StringUtils.hasText(qbDownloadPath) && StringUtils.hasText(hardlinkPath) && StringUtils.hasText(name)) {
         MovieScrapePathConfigEntity entity;
         if (request.getId() != null) {
            entity = this.configMapper.selectById(request.getId());
            if (entity == null) {
               throw new BizException("刮削目录配置不存在，无法修改");
            }
         } else {
            entity = new MovieScrapePathConfigEntity();
         }

         entity.setName(name);
         entity.setQbDownloadPath(qbDownloadPath);
         entity.setHardlinkPath(hardlinkPath);
         entity.setOverwrite(request.getOverwrite() != null ? request.getOverwrite() : 0);
         entity.setCoexist(request.getCoexist() != null ? request.getCoexist() : 0);
         entity.setQualityPriority(request.getQualityPriority() != null ? request.getQualityPriority() : 0);
         entity.setSizePriority(request.getSizePriority() != null ? request.getSizePriority() : 0);
         entity.setHardlinkMode(request.getHardlinkMode() != null ? request.getHardlinkMode() : 1);
         if (entity.getId() == null) {
            this.configMapper.insert(entity);
         } else {
            this.configMapper.updateById(entity);
         }

         return this.toModel(entity);
      } else {
         throw new BizException("路径名称、下载路径和硬链接路径不能为空");
      }
   }

   @Override
   public void delete(Long id) {
      MovieScrapePathConfigEntity movieScrapePathConfigEntity = new MovieScrapePathConfigEntity();
      movieScrapePathConfigEntity.setId(id);
      this.configMapper.deleteById(movieScrapePathConfigEntity);
   }

   private String trimToNull(String value) {
      if (value == null) {
         return null;
      } else {
         String trimmed = value.trim();
         return trimmed.isEmpty() ? null : trimmed;
      }
   }

   private MovieScrapePathConfig toModel(MovieScrapePathConfigEntity entity) {
      return entity == null
         ? null
         : MovieScrapePathConfig.builder()
            .id(entity.getId())
            .name(entity.getName())
            .qbDownloadPath(entity.getQbDownloadPath())
            .hardlinkPath(entity.getHardlinkPath())
            .overwrite(entity.getOverwrite())
            .coexist(entity.getCoexist())
            .qualityPriority(entity.getQualityPriority())
            .sizePriority(entity.getSizePriority())
            .hardlinkMode(entity.getHardlinkMode())
            .build();
   }

   @Generated
   public MovieScrapePathConfigServiceImpl(final MovieScrapePathConfigMapper configMapper) {
      this.configMapper = configMapper;
   }
}
