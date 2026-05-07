package com.example;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @GetMapping("/")
    public String hello() {
        return "Hello, World! (Spring Boot)";
    }

    @GetMapping("/health")
    public String health() {
        return "OK";
    }
}
