package com.lm.apigateway.filters;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import reactor.netty.http.server.HttpServerResponse;

import java.util.Set;

/**
 * @author el_le
 * @since 18/03/2022 21:54
 */

@Component
public class PostFilter implements GlobalFilter {

    final Logger logger = LoggerFactory.getLogger(PostFilter.class);

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {



        return chain.filter(exchange).then(Mono.fromRunnable(()-> {
            logger.info("Post filter executed");
            ServerHttpResponse response = exchange.getResponse();
            Set<String> responseHeaders = response.getHeaders().keySet();

            responseHeaders.forEach(header -> {
                logger.info(header + " " + response.getHeaders().containsKey(header));
            });
        }));
    }
}
