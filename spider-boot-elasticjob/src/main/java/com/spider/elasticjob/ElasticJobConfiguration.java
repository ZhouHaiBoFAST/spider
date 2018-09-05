package com.spider.elasticjob;

import com.dangdang.ddframe.job.reg.zookeeper.ZookeeperConfiguration;
import com.dangdang.ddframe.job.reg.zookeeper.ZookeeperRegistryCenter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * elaticjob 注册
 *
 * @author liuzhongkai
 */
@Configuration
@ConditionalOnProperty(name = "spider.elaticjob.zookeeper-server-lists")
@EnableConfigurationProperties({ElasticJobProperties.class})
public class ElasticJobConfiguration {

    @Bean(initMethod = "init", destroyMethod = "close")
    public ZookeeperRegistryCenter zookeeperRegistryCenter(ElasticJobProperties properties) {
        ZookeeperConfiguration zookeeperConfiguration = new ZookeeperConfiguration(properties.getZookeeperServerLists(), properties.getZookeeperNamespace());
        zookeeperConfiguration.setBaseSleepTimeMilliseconds(properties.getZookeeperBaseSleepTimeMilliseconds());
        zookeeperConfiguration.setConnectionTimeoutMilliseconds(properties.getZookeeperConnectionTimeoutMilliseconds());
        zookeeperConfiguration.setDigest(properties.getDigest());
        zookeeperConfiguration.setMaxRetries(properties.getZookeeperMaxRetries());
        zookeeperConfiguration.setMaxSleepTimeMilliseconds(properties.getZookeeperMaxSleepTimeMilliseconds());
        zookeeperConfiguration.setSessionTimeoutMilliseconds(properties.getZookeeperSessionTimeoutMilliseconds());
        return new ZookeeperRegistryCenter(zookeeperConfiguration);
    }

    @Bean
    public ElasticJobSpiderBeanProcessor elasticJobBeanProcessor(ZookeeperRegistryCenter registryCenter) {
        return new ElasticJobSpiderBeanProcessor(registryCenter);
    }

}
