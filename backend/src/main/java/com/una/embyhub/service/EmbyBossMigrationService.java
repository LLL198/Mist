package com.una.embyhub.service;

import com.una.embyhub.model.dto.request.embybossmigration.EmbyBossMigrationRequest;
import com.una.embyhub.model.dto.response.embybossmigration.EmbyBossMigrationResultResponse;

public interface EmbyBossMigrationService {
   EmbyBossMigrationResultResponse preview(EmbyBossMigrationRequest request);

   EmbyBossMigrationResultResponse sync(EmbyBossMigrationRequest request);
}
