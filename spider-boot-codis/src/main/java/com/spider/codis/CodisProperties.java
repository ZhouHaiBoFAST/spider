package com.spider.codis;

import org.springframework.boot.context.properties.ConfigurationProperties;
import redis.clients.jedis.JedisPoolConfig;

/**
 * codis配置类
 * <p>
 * Created by spider-bigdata-boot on 2017/11/16.
 *
 * @author liuzhongkai
 */
@ConfigurationProperties(prefix = "spider.codis")
public class CodisProperties {
    private String proxy;

    private JedisPoolConfig poolConfig;

    private int timeoutMs = 12000;

    private int soTimeoutMs = 12000;

    private int connectionTimeoutMs = 12000;

    public JedisPoolConfig getPoolConfig() {
        return poolConfig;
    }

    public void setPoolConfig(JedisPoolConfig poolConfig) {
        this.poolConfig = poolConfig;
    }

    public String getProxy() {
        return proxy;
    }

    public void setProxy(String proxy) {
        this.proxy = proxy;
    }

    public int getTimeoutMs() {
        return timeoutMs;
    }

    public void setTimeoutMs(int timeoutMs) {
        this.timeoutMs = timeoutMs;
    }

    public int getSoTimeoutMs() {
        return soTimeoutMs;
    }

    public void setSoTimeoutMs(int soTimeoutMs) {
        this.soTimeoutMs = soTimeoutMs;
    }

    public int getConnectionTimeoutMs() {
        return connectionTimeoutMs;
    }

    public void setConnectionTimeoutMs(int connectionTimeoutMs) {
        this.connectionTimeoutMs = connectionTimeoutMs;
    }
}
