package com.yhh.xuanke.config;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

@Configuration
public class CaffeineConfig {

    @Bean
    public Cache<Integer, Boolean> caffeineCache() {
        return Caffeine.newBuilder()
                .expireAfterWrite(1, TimeUnit.DAYS)
                .initialCapacity(100)
                //缓存最大条数
                .maximumSize(1000)
                .build();
    }
}
