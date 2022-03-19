package com.lm.apigateway.filters;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Mono;

/**
 * @author el_le
 * @since 18/03/2022 22:14
 */

@Configuration
public class GlobalFiltersConfiguration {

    Logger logger = LoggerFactory.getLogger(GlobalFiltersConfiguration.class);

    @Bean
    public GlobalFilter secondFilter() {

        logger.info("This is the pre filter");

        return ((exchange, chain) -> chain.filter(exchange).then(Mono.fromRunnable(() -> {
            logger.info("This is the post filter");
        })));
    }
}
