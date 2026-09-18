package com.una.embyhub.service;

import com.una.embyhub.model.dto.request.embyuser.EmbyUserMultiCreateRequest;
import com.una.embyhub.model.dto.response.embyuser.EmbyUserMultiCreateResponse;

public interface EmbyUserMultiCreateService {
   EmbyUserMultiCreateResponse createMultiServerUser(EmbyUserMultiCreateRequest request);
}
