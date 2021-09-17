package com.virspit.virspitproduct;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class VirspitProductApplication {

	public static void main(String[] args) {
		SpringApplication.run(VirspitProductApplication.class, args);
	}

}
