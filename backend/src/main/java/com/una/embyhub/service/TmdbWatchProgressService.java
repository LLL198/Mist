package com.una.embyhub.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.una.embyhub.model.entity.TmdbWatchProgress;

public interface TmdbWatchProgressService extends IService<TmdbWatchProgress> {
   TmdbWatchProgress saveProgress(TmdbWatchProgress progress);
}
