package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
// configure the entity scan to your group id
@EntityScan("com.example.**")
public class Main {
    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }
}