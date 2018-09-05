package com.spider.redis;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Set;

/**
 * redis集群配置类
 * <p>
 *
 * @author liuzhongkai
 */
@ConfigurationProperties(prefix = "spider.redis")
public class RedisProperties {

    private String proxy;

    private int timeout = 30000;

    private int maxAttempts = 3;

    private Set<String> nodes;

    private Pool pool = new Pool();

    public static class Pool {

        private int maxWaitMillis = -1, maxTotal = 8, minIdle = 0, maxIdle = 8;

        public Pool() {

        }

        public int getMaxWaitMillis() {
            return maxWaitMillis;
        }

        public void setMaxWaitMillis(int maxWaitMillis) {
            this.maxWaitMillis = maxWaitMillis;
        }

        public int getMaxTotal() {
            return maxTotal;
        }

        public void setMaxTotal(int maxTotal) {
            this.maxTotal = maxTotal;
        }

        public int getMinIdle() {
            return minIdle;
        }

        public void setMinIdle(int minIdle) {
            this.minIdle = minIdle;
        }

        public int getMaxIdle() {
            return maxIdle;
        }

        public void setMaxIdle(int maxIdle) {
            this.maxIdle = maxIdle;
        }
    }

    public String getProxy() {
        return proxy;
    }

    public void setProxy(String proxy) {
        this.proxy = proxy;
    }

    public int getTimeout() {
        return timeout;
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }

    public Pool getPool() {
        return pool;
    }

    public void setPool(Pool pool) {
        this.pool = pool;
    }

    public int getMaxAttempts() {
        return maxAttempts;
    }

    public void setMaxAttempts(int maxAttempts) {
        this.maxAttempts = maxAttempts;
    }

    public Set<String> getNodes() {
        return nodes;
    }

    public void setNodes(Set<String> nodes) {
        this.nodes = nodes;
    }
}
