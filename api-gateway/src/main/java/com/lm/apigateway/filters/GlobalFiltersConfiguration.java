package com.lm.apigateway.filters;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import reactor.core.publisher.Mono;

/**
 * @author el_le
 * @since 18/03/2022 22:14
 */

@Configuration
public class GlobalFiltersConfiguration {

    Logger logger = LoggerFactory.getLogger(GlobalFiltersConfiguration.class);

    /**
     * note: on the way back, the last post filter will be executed first, and so on;
     * should business logic be different then annotate class using @Order
     * @return
     */
    @Order(1)
    @Bean
    public GlobalFilter secondFilter() {

        logger.info("This is the pre filter");

        return ((exchange, chain) -> chain.filter(exchange).then(Mono.fromRunnable(() -> {
            logger.info("This is the post filter");
        })));
    }

    @Order(2)
    @Bean
    public GlobalFilter thirdFilter() {

        logger.info("This is the third pre filter");

        return ((exchange, chain) -> chain.filter(exchange).then(Mono.fromRunnable(() -> {
            logger.info("This is the third post filter");
        })));
    }

    @Order(3)
    @Bean
    public GlobalFilter fourthFilter() {

        logger.info("This is the fourth pre filter");

        return ((exchange, chain) -> chain.filter(exchange).then(Mono.fromRunnable(() -> {
            logger.info("This is the fourth post filter");
        })));
    }
}
