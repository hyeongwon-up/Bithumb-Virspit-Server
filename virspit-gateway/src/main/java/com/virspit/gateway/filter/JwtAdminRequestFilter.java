package com.virspit.gateway.filter;

import com.virspit.gateway.error.ErrorCode;
import com.virspit.gateway.error.exception.InvalidValueException;
import com.virspit.gateway.error.exception.TokenException;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.reactive.error.ErrorWebExceptionHandler;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Map;

@Component
public class JwtAdminRequestFilter extends
        AbstractGatewayFilterFactory<JwtAdminRequestFilter.Config> implements Ordered {

    final Logger logger =
            LoggerFactory.getLogger(JwtAdminRequestFilter.class);

    @Autowired
    private JwtValidator jwtValidator;

    @Override
    public int getOrder() {
        return -2; // -1 is response write filter, must be called before that
    }

    public static class Config {
        private String role;

        public Config(String role) {
            this.role = role;
        }

        public String getRole() {
            return Role.ADMIN.getCode();
        }
    }

    @Bean
    public ErrorWebExceptionHandler gatewayExceptionHandler() {
        return new GatewayExceptionHandler();
    }

    public class GatewayExceptionHandler implements ErrorWebExceptionHandler {

        private String errorCodeMaker(String errorMessage) {
            return "{\"errorMessage\":" + errorMessage + "}";
        }

        @Override
        public Mono<Void> handle(
                ServerWebExchange exchange, Throwable ex) {
            logger.warn("in GATEWAY Exeptionhandler : " + ex);
            String errorMessage = "Gateway Exception 입니다. 백엔드 파트에 문의해주세요.";
            if (ex.getClass() == NullPointerException.class) {
                errorMessage = "GatewayException : NullPointerException";
            } else if (ex.getClass() == ExpiredJwtException.class) {
                errorMessage = "GatewayException : 만료된 AccessToken 입니다.";
            } else if (ex.getClass() == MalformedJwtException.class || ex.getClass() == SignatureException.class || ex.getClass() == UnsupportedJwtException.class) {
                errorMessage = "GatewayException : 올바르지 않은 형식의 토큰입니다.";
            } else if (ex.getClass() == IllegalArgumentException.class) {
                errorMessage = "GatewayException : 부적절한 요청입니다.";
            }

            byte[] bytes = errorCodeMaker(errorMessage).getBytes(StandardCharsets.UTF_8);
            DataBuffer buffer = exchange.getResponse().bufferFactory().wrap(bytes);
            return exchange.getResponse().writeWith(Flux.just(buffer));
        }
    }


    public JwtAdminRequestFilter() {
        super(Config.class);
    }

    // public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            String token = exchange.getRequest().getHeaders().get("Authorization").get(0).substring(7);
            Map<String, Object> userInfo = jwtValidator.getUserParseInfo(token);
            ArrayList<String> arr = (ArrayList<String>) userInfo.get("role");
            if (!arr.contains(config.getRole())) {
                throw new InvalidValueException(token, ErrorCode.TOKEN_NOT_VALID);
            }
            return chain.filter(exchange);
        };
    }
}