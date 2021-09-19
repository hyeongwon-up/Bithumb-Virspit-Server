package com.virspit.virspitservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;

@EnableReactiveMongoRepositories
@SpringBootApplication
public class VirspitServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(VirspitServiceApplication.class, args);
    }

}
