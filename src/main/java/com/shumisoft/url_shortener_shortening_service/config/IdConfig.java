package com.shumisoft.url_shortener_shortening_service.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.ecwid.consul.v1.ConsulClient;
import com.shumisoft.url_shortener_shortening_service.utils.IdGenerator;
import com.shumisoft.url_shortener_shortening_service.utils.IdRangeAllocator;

@Configuration
public class IdConfig {

    @Value("${spring.cloud.consul.host}")
    private String consulhost;

    @Bean
    public IdGenerator idGenerator(IdRangeAllocator allocator) {
        return allocator.allocateRange();
    }

    @Bean
    public IdRangeAllocator idRangeAllocator() {
        return new IdRangeAllocator(new ConsulClient(consulhost));
    }
}
