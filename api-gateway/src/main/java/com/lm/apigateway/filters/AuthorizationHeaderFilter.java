package com.lm.apigateway.filters;

import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Optional;

/**
 * @author el_le
 * @since 16/03/2022 19:33
 */

@Component
public class AuthorizationHeaderFilter extends AbstractGatewayFilterFactory<AuthorizationHeaderFilter.Config> {

    @Autowired
    private Environment env;

    public AuthorizationHeaderFilter() {
        super(Config.class);

    }

    public static class Config {

    }

    @Override
    public GatewayFilter apply(Config config) {
        return ((exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();

            if (!request.getHeaders().containsKey(HttpHeaders.AUTHORIZATION)){
                return onError(exchange, "No Authorization Header", HttpStatus.UNAUTHORIZED);

            }
            String authorizationHeader = request.getHeaders().get(HttpHeaders.AUTHORIZATION).get(0);
            String jwt = authorizationHeader.replace("Bearer", "");

            if(!isJwtValid(jwt)){
                onError(exchange, "JWT not valid", HttpStatus.UNAUTHORIZED);
            }

            return chain.filter(exchange);
        });
    }

    private Mono<Void> onError(ServerWebExchange exchange, String error, HttpStatus statusCode) {
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(statusCode);
        return response.setComplete();
    }

    private boolean isJwtValid(String jwt) {
        boolean returnValue = true;

        String secret = env.getProperty("token.secret");

        try {
            String subject = Jwts.parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(jwt) // parseJW signature
                    .getBody()
                    .getSubject();

            if ( subject == null || subject.isEmpty()){
                returnValue = false;
            }
        } catch (Exception e) {
            returnValue = false;
        }
        return returnValue;
    }


}
