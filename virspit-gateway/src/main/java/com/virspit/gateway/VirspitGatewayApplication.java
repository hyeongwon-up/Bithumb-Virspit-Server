package com.virspit.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class VirspitGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(VirspitGatewayApplication.class, args);
    }

    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {

        return builder.routes()
                .route("path_route",  r-> r.path("/new")
                        .filters(f -> f.addRequestHeader("Hello", "World")
                                .rewritePath("/new", "/"))
                        .uri("http://localhost:8083/"))
                .build();
    }

}
