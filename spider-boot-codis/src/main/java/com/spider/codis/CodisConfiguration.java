package com.spider.codis;


import com.spider.zk.WrapCuratorFramework;
import io.codis.jodis.RoundRobinJedisPool;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;

/**
 * codis工厂注册
 *
 * @author liuzhongkai
 */
@Configuration
@ConditionalOnProperty(name = "spider.codis.proxy")
@EnableConfigurationProperties({CodisProperties.class})
public class CodisConfiguration {

    @Bean(destroyMethod = "close")
    public RoundRobinJedisPool roundRobinJedisPool(CodisProperties properties, WrapCuratorFramework wrapCuratorFramework) {
        return RoundRobinJedisPool.create().curatorClient(wrapCuratorFramework.getCuratorFramework(), false).zkProxyDir(properties.getProxy()).poolConfig(properties.getPoolConfig()).timeoutMs(properties.getTimeoutMs()).soTimeoutMs(properties.getSoTimeoutMs()).connectionTimeoutMs(properties.getConnectionTimeoutMs()).build();
    }

}
