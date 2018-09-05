package com.spider.redis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPoolConfig;

import java.util.HashSet;
import java.util.Set;

/**
 * redis cluster 工厂
 *
 * @author liuzhongkai
 */
public class JedisClusterFactory {

    private static final Logger LOGGER = LoggerFactory.getLogger(JedisClusterFactory.class);


    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private RedisProperties properties;

        public Builder redisProperties(RedisProperties properties) {
            this.properties = properties;
            return this;
        }

        public JedisCluster build() {
            Set<String> nodes = properties.getNodes();
            if (nodes == null || nodes.isEmpty())
                throw new IllegalArgumentException("the redis parameter lacks proxy or nodes");
            LOGGER.info("redis create accomplish {}", properties.getNodes());
            return wholeCreateJedisCluster(analysisStringHostAndPort(properties.getNodes()));
        }

        private JedisCluster createJedisCluster(Set<HostAndPort> addrSet, int timeout, int maxRedirections, JedisPoolConfig config) {
            return new JedisCluster(addrSet, timeout, maxRedirections, config);
        }


        private JedisCluster wholeCreateJedisCluster(Set<HostAndPort> addrSet) {
            JedisPoolConfig config = createJedisPoolConfig(properties.getPool().getMaxWaitMillis(), properties.getPool().getMaxTotal(), properties.getPool().getMinIdle(), properties.getPool().getMaxIdle());
            return createJedisCluster(addrSet, properties.getTimeout(), properties.getMaxAttempts(), config);
        }

        private JedisPoolConfig createJedisPoolConfig(int maxWaitMillis, int maxTotal, int minIdle, int maxIdle) {
            JedisPoolConfig config = new JedisPoolConfig();
            config.setMaxWaitMillis(maxWaitMillis);
            config.setMaxTotal(maxTotal);
            config.setMinIdle(minIdle);
            config.setMaxIdle(maxIdle);
            return config;
        }

        private Set<HostAndPort> analysisStringHostAndPort(Set<String> nodes) {
            Set<HostAndPort> hostAndPortList = new HashSet<>(nodes.size());
            for (String item : nodes) {
                String[] hostAndPost = item.split(":");
                hostAndPortList.add(new HostAndPort(hostAndPost[0], Integer.parseInt(hostAndPost[1])));
            }
            return hostAndPortList;
        }
    }
}
