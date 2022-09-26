package com.ssimoesfelipe.spaceprobecontrol.domain.config;

import org.springframework.boot.autoconfigure.cache.RedisCacheManagerBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class RedisConfig {

  @Bean
  RedisCacheManagerBuilderCustomizer redisCacheManagerBuilderCustomizer() {
    return (builder) -> {
      Map<String, RedisCacheConfiguration> configurationMap = new HashMap<>();
      configurationMap.put("planet", RedisCacheConfiguration.defaultCacheConfig().entryTtl(Duration.ofMinutes(5)));
      builder.withInitialCacheConfigurations(configurationMap);
    };
  }
}
