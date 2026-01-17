package br.org.ministerioatos.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/public")
public class HealthController {

    @Autowired
    private ApplicationContext applicationContext;

    @GetMapping("/health")
    public ResponseEntity getHealth(){
       return ResponseEntity.ok().body("app: " + applicationContext.getApplicationName() + " is running");
    }
}
