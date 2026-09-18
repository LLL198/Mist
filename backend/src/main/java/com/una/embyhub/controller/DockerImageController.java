package com.una.embyhub.controller;
import com.una.embyhub.model.dto.response.docker.DockerImagesVersionResponse;
import com.una.embyhub.service.DockerImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping({"/docker"})
public class DockerImageController {
   @Autowired
   private DockerImageService dockerImageService;

   @GetMapping({"/version"})
   public DockerImagesVersionResponse getDockerImagesVersion() {
      return this.dockerImageService.getDockerImagesVersion();
   }
}
