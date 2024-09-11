package com.wangliang.lepao.config;

import lombok.Data;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Redisson 配置类
 *
 * @author wangliang
 */
@Configuration
@ConfigurationProperties(prefix = "spring.redis")
@Data
public class RedissonConfig {

    private String host;
    private String password;
    private Integer port;

    @Bean
    public RedissonClient redissonClient() {
        // 1. 创建配置
        Config config = new Config();
        String redisHost = String.format("redis://%s:%d", host, port);
        config.useSingleServer()
                .setAddress(redisHost)
                .setPassword(password)
                .setDatabase(2);
        // 2. 根据 Config 创建出 RedissonClient 实例
        RedissonClient redisson = Redisson.create(config);
        return redisson;
    }
}
