package com.example.healthylife.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class healthcheckController {

    @GetMapping("/health-check")
    public ResponseEntity<Void> checkhealth(){
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
