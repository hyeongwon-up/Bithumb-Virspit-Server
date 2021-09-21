package com.virspit.virspitauth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class VirspitAuthApplication {

    public static void main(String[] args) {
        SpringApplication.run(VirspitAuthApplication.class, args);
    }

}
