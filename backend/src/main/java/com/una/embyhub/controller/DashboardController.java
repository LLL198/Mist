package com.una.embyhub.controller;

import cn.dev33.satoken.stp.StpUtil;
import com.una.embyhub.config.common.enums.ResponseStatusEnum;
import com.una.embyhub.config.common.exception.BizException;
import com.una.embyhub.config.common.utils.EmbyInfoCacheManagerUtils;
import com.una.embyhub.model.dto.response.dashboard.DashboardPopularMovieResponse;
import com.una.embyhub.model.entity.EmbyUser;
import com.una.embyhub.service.DashboardPopularMovieService;
import com.una.embyhub.service.EmbyUserService;
import java.util.List;
import lombok.Generated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping({"dashboard"})
public class DashboardController {
   private final DashboardPopularMovieService popularMovieService;
   private final EmbyInfoCacheManagerUtils embyInfoCacheManager;
   private final EmbyUserService embyUserService;

   @GetMapping({"popular-movies"})
   public List<DashboardPopularMovieResponse> popularMovies(@RequestParam(value = "embyInfoId",required = false) Long embyInfoId) {
      return this.popularMovieService.getPopularMovies(this.resolveAccessibleServerId(embyInfoId));
   }

   private Long resolveAccessibleServerId(Long embyInfoId) {
      EmbyUser user = (EmbyUser)StpUtil.getSession().get("user");
      if (user == null) {
         throw new BizException(ResponseStatusEnum.PERMISSION_DENIED);
      } else {
         List<Long> accessibleServerIds = this.embyUserService.listAccessibleEmbyInfoIds(user);
         if (embyInfoId == null) {
            return !accessibleServerIds.isEmpty() ? accessibleServerIds.get(0) : this.embyInfoCacheManager.getRequiredConfig(user).id();
         } else if (this.canAccessServer(user, accessibleServerIds, embyInfoId)) {
            return embyInfoId;
         } else {
            throw new BizException(ResponseStatusEnum.PERMISSION_DENIED);
         }
      }
   }

   private boolean canAccessServer(EmbyUser user, List<Long> accessibleServerIds, Long embyInfoId) {
      if (user != null && embyInfoId != null) {
         return Integer.valueOf(1).equals(user.getIsAdmin()) ? true : accessibleServerIds.contains(embyInfoId);
      } else {
         return false;
      }
   }

   @Generated
   public DashboardController(
      final DashboardPopularMovieService popularMovieService, final EmbyInfoCacheManagerUtils embyInfoCacheManager, final EmbyUserService embyUserService
   ) {
      this.popularMovieService = popularMovieService;
      this.embyInfoCacheManager = embyInfoCacheManager;
      this.embyUserService = embyUserService;
   }
}
