package com.una.embyhub.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.una.embyhub.model.dto.request.moviepilot.MoviePilotLoginRequest;
import com.una.embyhub.model.dto.request.moviepilot.MoviePilotSubscribeRequest;
import com.una.embyhub.model.dto.response.moviepilot.MoviePilotLoginResponse;
import com.una.embyhub.model.dto.response.moviepilot.MoviePilotSubscribeResponse;
import com.una.embyhub.model.dto.response.moviepilot.MoviePilotSubscriptionItemResponse;
import com.una.embyhub.service.MoviePilotService;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping({"moviepilot"})
public class MoviePilotController {
   @Autowired
   private MoviePilotService moviePilotService;

   @PostMapping({"/login"})
   @SaCheckPermission({"admin"})
   public MoviePilotLoginResponse login(@Valid @RequestBody MoviePilotLoginRequest request) {
      return this.moviePilotService.login(request);
   }

   @PostMapping({"/subscribe"})
   @SaCheckPermission({"admin"})
   public MoviePilotSubscribeResponse subscribe(@Valid @RequestBody MoviePilotSubscribeRequest request) {
      return this.moviePilotService.subscribe(request);
   }

   @PostMapping({"/subscribe/{id}"})
   @SaCheckPermission({"admin"})
   public MoviePilotSubscribeResponse cancelSubscribe(@PathVariable("id") Long id) {
      return this.moviePilotService.cancelSubscribe(id);
   }

   @PostMapping({"/subscribe/list"})
   @SaCheckPermission({"admin"})
   public List<MoviePilotSubscriptionItemResponse> subscribeList() {
      return this.moviePilotService.subscribeList();
   }
}
