package com.spider.redis;

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.JedisCluster;

/**
 * redis工厂注册
 * <p>
 * Created by spider-boot on 2017/11/29.
 *
 * @author liuzhongkai
 */
@Configuration
@ConditionalOnClass(JedisCluster.class)
@ConditionalOnProperty("spider.redis.nodes")
@EnableConfigurationProperties({RedisProperties.class})
public class RedisConfiguration {

    @Bean(destroyMethod = "close")
    @ConditionalOnMissingBean(name = "jedisCluster")
    public JedisCluster jedisCluster(RedisProperties properties) {
        return JedisClusterFactory.builder().redisProperties(properties).build();
    }

    @Bean
    public RedisCacheTemplate redisCacheTemplate(JedisCluster jedisCluster) {
        return new RedisCacheTemplate(jedisCluster);
    }

}
