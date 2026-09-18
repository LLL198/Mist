package com.una.embyhub.foam.service;

import com.una.embyhub.foam.response.douban.FoamDoubanDiscoverResponse;

public interface FoamDoubanService {
   FoamDoubanDiscoverResponse discoverMovies(int page, int count, String sort, String tags);

   FoamDoubanDiscoverResponse discoverTvs(int page, int count, String sort, String tags);
}
