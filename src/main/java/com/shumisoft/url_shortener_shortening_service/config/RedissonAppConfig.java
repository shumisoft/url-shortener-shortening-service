package com.shumisoft.url_shortener_shortening_service.config;

import lombok.Data;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "redisson")
@Data // Generates the setter for redisUrl so Spring can bind the YAML value
public class RedissonAppConfig {

    private String redisUrl;

    @Bean(destroyMethod = "shutdown")
    public RedissonClient redissonClient() {
        Config config = new Config();

        config.useSingleServer()
                .setAddress(redisUrl)
                .setConnectionMinimumIdleSize(5)
                .setConnectionPoolSize(50);

        return Redisson.create(config);
    }
}