package com.virspit.gateway.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

public class JwtRequestFilter implements GlobalFilter {
    final Logger logger = LoggerFactory.getLogger(JwtRequestFilter.class);

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        try {
            String token = exchange.getRequest().getHeaders().get("Authorization").get(0).substring(7);
        } catch (NullPointerException e) {
            logger.warn("no token.");
            exchange.getResponse().setStatusCode(HttpStatus.valueOf(401));
            logger.info("status code :" + exchange.getResponse().getStatusCode());
            return chain.filter(exchange);
        }
        return chain.filter(exchange);
    }
}
