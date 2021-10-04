package com.virspit.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableDiscoveryClient
public class VirspitGatewayApplication {

//    private JwtRequestFilter jwtRequestFilter;

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

//    @Bean
//    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
//
//        return builder.routes()
//                .route("path_route",  r-> r.path("/new")
//                        .filters(f -> f.filter(GatewayFilter)
//                                .rewritePath("/new", "/"))
//                        .uri("http://localhost:8081/"))
//                .build();
//    }

}
