package com.example.sklopi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.example.sklopi.repository")
public class SklopiApplication {

    public static void main(String[] args) {
        SpringApplication.run(SklopiApplication.class, args);
    }

}
