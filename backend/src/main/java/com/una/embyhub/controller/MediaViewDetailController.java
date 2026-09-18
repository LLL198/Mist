package com.una.embyhub.controller;

import com.una.embyhub.service.MediaViewDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping({"mediaViewDetail"})
public class MediaViewDetailController {
   @Autowired
   private MediaViewDetailService mediaViewDetailService;
}
