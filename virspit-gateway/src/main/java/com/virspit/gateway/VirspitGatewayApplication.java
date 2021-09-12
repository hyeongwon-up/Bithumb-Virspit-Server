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

//    @Bean
//    public CorsConfiguration corsConfiguration(RoutePredicateHandlerMapping routePredicateHandlerMapping) {
//        CorsConfiguration corsConfiguration = new CorsConfiguration().applyPermitDefaultValues();
//        Arrays.asList(HttpMethod.OPTIONS, HttpMethod.PUT, HttpMethod.GET, HttpMethod.DELETE, HttpMethod.POST) .forEach(m -> corsConfiguration.addAllowedMethod(m));
//        corsConfiguration.addAllowedOrigin("*");
//        routePredicateHandlerMapping.setCorsConfigurations(new HashMap<String, CorsConfiguration>() {{ put("/**", corsConfiguration); }});
//        return corsConfiguration;
//    }

    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("new_user",  r-> r.path("/new")
                        .filters(f -> f
                                .rewritePath("/new", "/auth/signup"))

                        .uri("http://localhost:8083/"))
                .route("new_user",  r-> r.path("/login")
                        .filters(f -> f
                                .rewritePath("/login", "/auth/signin"))

                        .uri("http://localhost:8083/"))

                .route("new_user",  r-> r.path("/register")
                        .filters(f -> f
                                .rewritePath("/register", "/auth/register"))

                        .uri("http://localhost:8083/"))
                .route("new_user",  r-> r.path("/login2")
                        .filters(f -> f
                                .rewritePath("/login2", "/auth/login"))

                        .uri("http://localhost:8083/"))
                .build();
    }

}
