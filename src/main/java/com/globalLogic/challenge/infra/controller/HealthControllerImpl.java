package com.globalLogic.challenge.infra.controller;

import com.globalLogic.challenge.adapter.controller.HealthController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthControllerImpl implements HealthController {

    @GetMapping("/health")
    public String healthCheck(){
        return "ok";
    }

}
