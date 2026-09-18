package com.una.embyhub.service;

import com.una.embyhub.model.dto.request.foammigration.FoamDataMigrationRequest;
import com.una.embyhub.model.dto.response.foammigration.FoamDataMigrationConnectionResponse;
import com.una.embyhub.model.dto.response.foammigration.FoamDataMigrationPlanResponse;
import com.una.embyhub.model.dto.response.foammigration.FoamDataMigrationProgressResponse;
import com.una.embyhub.model.dto.response.foammigration.FoamDataMigrationResultResponse;
import java.util.function.Consumer;

public interface FoamDataMigrationService {
   FoamDataMigrationPlanResponse plan();

   FoamDataMigrationConnectionResponse testConnection(FoamDataMigrationRequest request);

   FoamDataMigrationResultResponse sync(FoamDataMigrationRequest request);

   FoamDataMigrationResultResponse sync(FoamDataMigrationRequest request, Consumer<FoamDataMigrationProgressResponse> progressConsumer);
}
