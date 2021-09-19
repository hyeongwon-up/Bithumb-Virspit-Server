package com.virspit.virspitorder;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class VirspitOrderApplication {

	public static void main(String[] args) {
		SpringApplication.run(VirspitOrderApplication.class, args);
	}

}
