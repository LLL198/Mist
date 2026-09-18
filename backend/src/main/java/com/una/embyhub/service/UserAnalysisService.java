package com.una.embyhub.service;

import com.una.embyhub.model.dto.response.useranalysis.UserAnalysisOverviewResponse;
import com.una.embyhub.model.dto.response.useranalysis.UserAnalysisUserOptionResponse;
import java.time.LocalDate;
import java.util.List;

public interface UserAnalysisService {
   UserAnalysisOverviewResponse adminOverview(Long userId, Long embyInfoId, String embyUserId, String embyUserName, LocalDate startDate, LocalDate endDate);

   List<UserAnalysisUserOptionResponse> activeUserOptions(Long embyInfoId);

   UserAnalysisOverviewResponse myOverview(LocalDate startDate, LocalDate endDate);
}
