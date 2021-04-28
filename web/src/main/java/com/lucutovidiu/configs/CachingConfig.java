package com.lucutovidiu.configs;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static com.lucutovidiu.cache.CacheNames.*;

@Configuration
@EnableCaching
public class CachingConfig {

    @Bean
    public CacheManager cacheManager() {
        ConcurrentMapCacheManager cacheManager = new ConcurrentMapCacheManager(PORTFOLIOS_BASIC,
                PORTFOLIOS_FULL, PORTFOLIO_BY_ID, USER_GET_BY_ID_DTO, USER_GET_BY_ID, GET_ALL_USERS,
                GET_ALL_UK_BANK_HOLIDAYS);
        cacheManager.setAllowNullValues(false);
        return cacheManager;
    }
}
