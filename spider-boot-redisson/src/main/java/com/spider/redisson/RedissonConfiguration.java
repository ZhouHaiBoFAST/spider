package com.spider.redisson;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

/**
 * redisson注册
 *
 * @author liuzhongkai
 */
@Configuration
@ConditionalOnProperty(name = "spider.redisson.yaml-file")
@EnableConfigurationProperties({RedissonProperties.class})
public class RedissonConfiguration {

    @Bean(destroyMethod = "shutdown")
    public RedissonClient redissonClient(RedissonProperties properties) throws IOException {
        return Redisson.create(Config.fromYAML(properties.getYamlFile().getInputStream()));
    }

}
