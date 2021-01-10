package com.lucutovidiu.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication(scanBasePackages = {"com.lucutovidiu"})
@ComponentScan(basePackages = {"com.lucutovidiu"})
@EnableMongoRepositories(basePackages = {"com.lucutovidiu"})
public class WebApplication {

    public static void main(String[] args) {
        SpringApplication.run(WebApplication.class, args);
    }

}
