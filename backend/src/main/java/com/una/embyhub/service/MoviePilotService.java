package com.una.embyhub.service;

import com.una.embyhub.model.dto.request.moviepilot.MoviePilotLoginRequest;
import com.una.embyhub.model.dto.request.moviepilot.MoviePilotSubscribeRequest;
import com.una.embyhub.model.dto.response.moviepilot.MoviePilotLoginResponse;
import com.una.embyhub.model.dto.response.moviepilot.MoviePilotSubscribeResponse;
import com.una.embyhub.model.dto.response.moviepilot.MoviePilotSubscriptionItemResponse;
import java.util.List;

public interface MoviePilotService {
   MoviePilotLoginResponse login(MoviePilotLoginRequest request);

   MoviePilotSubscribeResponse subscribe(MoviePilotSubscribeRequest request);

   MoviePilotSubscribeResponse cancelSubscribe(Long id);

   List<MoviePilotSubscriptionItemResponse> subscribeList();
}
