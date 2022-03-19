package com.lm.apigateway.filters;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import reactor.netty.http.server.HttpServerRequest;

import java.util.Set;

/**
 * @author el_le
 * @since 18/03/2022 19:31
 */

@Component
public class PreFilter implements GlobalFilter, Ordered {

    final Logger logger = LoggerFactory.getLogger(PreFilter.class);

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        logger.info("Pre filter executed");
        logger.info("Path: {}", exchange.getRequest().getPath());
        HttpHeaders headers = exchange.getRequest().getHeaders();
        Set<String> headerKeys = headers.keySet();

        headerKeys.forEach((header) -> {
            System.out.println(headers.getFirst(header));
        });

        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
