package com.yeolsimee.roumo.healthcheck.ui;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthCheckController {

    @GetMapping("/healthcheck")
    ResponseEntity<String> healthCheck() {
        return ResponseEntity.ok("ok");
    }
}
