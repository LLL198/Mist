package com.una.embyhub.service;

import com.una.embyhub.model.dto.response.docker.DockerImagesVersionResponse;

public interface DockerImageService {
   DockerImagesVersionResponse getDockerImagesVersion();
}
