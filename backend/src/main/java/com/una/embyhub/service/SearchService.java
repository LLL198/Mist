package com.una.embyhub.service;

import com.una.embyhub.model.dto.request.telegram.SearchRequest;
import com.una.embyhub.model.dto.response.telegram.SearchResponse;

public interface SearchService {
   SearchResponse search(SearchRequest request);
}
