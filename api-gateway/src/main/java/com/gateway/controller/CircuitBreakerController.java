package com.gateway.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CircuitBreakerController {

    @GetMapping("/rentalProcessorFallBack")
    public String rentalProcessorFallbackMethod(){

        return "Rental Service is down! Tray back later";
    }

    @GetMapping("/rentalSearchFallBack")
    public String rentalSearchFallbackMethod(){

        return "Search Service is down! Tray back later";
    }
}
