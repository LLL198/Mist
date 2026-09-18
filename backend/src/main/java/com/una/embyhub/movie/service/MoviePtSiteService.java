package com.una.embyhub.movie.service;

import com.una.embyhub.movie.model.MoviePtSite;
import com.una.embyhub.movie.model.MoviePtSiteSaveRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;

public interface MoviePtSiteService {
   List<MoviePtSite> list();

   List<MoviePtSite> list(boolean includeStats);

   List<MoviePtSite> list(Integer enabled, boolean includeStats);

   MoviePtSite getById(Long id);

   MoviePtSite getById(Long id, boolean includeStats);

   MoviePtSite save(MoviePtSiteSaveRequest request);

   void getFavicon(Long id, HttpServletResponse response);

   void refreshAllUserStats();

   void deleteById(Long id);
}
