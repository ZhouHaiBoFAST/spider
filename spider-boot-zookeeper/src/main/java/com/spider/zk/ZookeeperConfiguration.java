package com.spider.zk;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 *
 * @author liuzhongkai
 */
@Configuration
@ConditionalOnProperty(name = "spider.zookeeper.servers")
@EnableConfigurationProperties({ZookeeperProperties.class})
public class ZookeeperConfiguration {

    @Bean(initMethod = "init", destroyMethod = "close")
    public WrapCuratorFramework wrapZkClient(ZookeeperProperties properties) {
        return new WrapCuratorFramework(properties);
    }
}
