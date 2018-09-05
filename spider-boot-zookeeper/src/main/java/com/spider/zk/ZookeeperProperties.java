package com.spider.zk;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * zookeeper配置
 *
 * @author liuzhongkai
 */
@ConfigurationProperties(prefix = "spider.zookeeper")
public class ZookeeperProperties {

    private String servers;

    private int sessionTimeout = 30000;

    private int connectionTimeoutMs = 30000;

    private int baseSleepTimeMs = 3000;

    private int maxRetries = 6;

    public String getServers() {
        return servers;
    }

    public void setServers(String servers) {
        this.servers = servers;
    }

    public int getSessionTimeout() {
        return sessionTimeout;
    }

    public void setSessionTimeout(int sessionTimeout) {
        this.sessionTimeout = sessionTimeout;
    }

    public int getConnectionTimeoutMs() {
        return connectionTimeoutMs;
    }

    public void setConnectionTimeoutMs(int connectionTimeoutMs) {
        this.connectionTimeoutMs = connectionTimeoutMs;
    }

    public int getBaseSleepTimeMs() {
        return baseSleepTimeMs;
    }

    public void setBaseSleepTimeMs(int baseSleepTimeMs) {
        this.baseSleepTimeMs = baseSleepTimeMs;
    }

    public int getMaxRetries() {
        return maxRetries;
    }

    public void setMaxRetries(int maxRetries) {
        this.maxRetries = maxRetries;
    }
}
