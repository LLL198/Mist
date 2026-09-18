package com.una.embyhub.service;

import com.una.embyhub.model.dto.logintransition.LoginTransitionDtos;

public interface LoginTransitionService {
   LoginTransitionDtos.SettingsResponse getSettings();

   LoginTransitionDtos.SettingsResponse updateSettings(LoginTransitionDtos.UpdateRequest request);

   LoginTransitionDtos.CurrentResponse resolveForUser(long userId);
}
