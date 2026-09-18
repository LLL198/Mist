package com.una.embyhub.controller;

import com.una.embyhub.service.EmbyNotifyDataDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping({"embyNotifyDataDetails"})
public class EmbyNotifyDataDetailsController {
   @Autowired
   private EmbyNotifyDataDetailsService embyNotifyDataDetailsService;
}
