package com.virspit.virspituser;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class VirspitUserApplication {

    public static void main(String[] args) {
        SpringApplication.run(VirspitUserApplication.class, args);
    }

}
