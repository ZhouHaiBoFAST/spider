//package com.spider.redis;
//
//import com.alibaba.fastjson.JSON;
//import com.spider.spring.SpringContextHolder;
//import com.spider.zk.WrapZkClient;
//import org.apache.curator.framework.recipes.cache.ChildData;
//import org.apache.curator.framework.recipes.cache.PathChildrenCache;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import redis.clients.jedis.HostAndPort;
//import redis.clients.jedis.JedisCluster;
//import redis.clients.jedis.JedisPoolConfig;
//
//import java.io.IOException;
//import java.util.HashSet;
//import java.util.Iterator;
//import java.util.List;
//import java.util.Set;
//import java.util.concurrent.locks.Lock;
//import java.util.concurrent.locks.ReentrantLock;
//
///**
// * redis连接管理
// * <p>
// * Created by spider-bigdata-boot on 2017/11/16.
// *
// * @author liuzhongkai
// */
//public class JedisClusterFactory {
//
//    private static final Logger LOGGER = LoggerFactory.getLogger(JedisClusterFactory.class);
//
//    private JedisCluster jedisCluster;
//
//    private Set<HostAndPort> addrSet;
//
//    private RedisProperties properties;
//
//    private PathChildrenCache watcher;
//
//    private Lock clusterLock;
//
//    public JedisClusterFactory(RedisProperties properties) {
//        this.properties = properties;
//    }
//
//    public void init() {
//        String proxy = properties.getProxy();
//        Set<String> nodes = properties.getNodes();
//        if (nodes != null && !nodes.isEmpty()) {
//            wholeCreateJedisCluster(analysisStringHostAndPort(properties.getNodes()));
//        } else if (proxy != null && !"".equals(proxy)) {
//            clusterLock = new ReentrantLock();
//            WrapZkClient wrapZkClient = SpringContextHolder.getBean(WrapZkClient.class);
//            this.watcher = new PathChildrenCache(wrapZkClient.getZkClient(), proxy, true);
//            watcher.getListenable().addListener((client, event) -> reset());
//            try {
//                watcher.start(PathChildrenCache.StartMode.BUILD_INITIAL_CACHE);
//            } catch (Exception e) {
//                throw new Error(e);
//            }
//            reset();
//        } else {
//            throw new IllegalArgumentException("the redis parameter lacks proxy or nodes");
//        }
//        LOGGER.info("redis create accomplish");
//    }
//
//    private Set<HostAndPort> analysisStringHostAndPort(Set<String> nodes) {
//        Set<HostAndPort> hostAndPortList = new HashSet<HostAndPort>(nodes.size());
//        for (String item : nodes) {
//            String[] hostAndPost = item.split(":");
//            hostAndPortList.add(new HostAndPort(hostAndPost[0], Integer.parseInt(hostAndPost[1])));
//        }
//        return hostAndPortList;
//    }
//
//    private void reset() {
//        List<ChildData> childDataList = watcher.getCurrentData();
//        if (childDataList.isEmpty())
//            return;
//        Iterator<ChildData> iterator = childDataList.iterator();
//        Set<HostAndPort> addrSet = new HashSet<HostAndPort>(childDataList.size());
//        while (iterator.hasNext()) {
//            ChildData childData = iterator.next();
//            RedisProxyInfo codisProxyInfo = JSON.parseObject(childData.getData(), RedisProxyInfo.class);
//            String addr = codisProxyInfo.getAddr();
//            String[] hostAndPost = addr.split(":");
//            addrSet.add(new HostAndPort(hostAndPost[0], Integer.parseInt(hostAndPost[1])));
//        }
//        clusterLock.lock();
//        try {
//            if (this.addrSet != null && !this.addrSet.containsAll(addrSet)) {
//                jedisCluster = wholeCreateJedisCluster(addrSet);
//            } else {
//                jedisCluster = wholeCreateJedisCluster(addrSet);
//            }
//            this.addrSet = addrSet;
//        } catch (Exception e) {
//            LOGGER.error("create a redis connection error", e);
//
//        } finally {
//            clusterLock.unlock();
//        }
//
//    }
//
//    private JedisCluster wholeCreateJedisCluster(Set<HostAndPort> addrSet) {
//        JedisPoolConfig config = createJedisPoolConfig(properties.getPool().getMaxWaitMillis(), properties.getPool().getMaxTotal(), properties.getPool().getMinIdle(), properties.getPool().getMaxIdle());
//        return createJedisCluster(addrSet, properties.getTimeout(), properties.getMaxAttempts(), config);
//    }
//
//    private JedisCluster createJedisCluster(Set<HostAndPort> addrSet, int timeout, int maxRedirections, JedisPoolConfig config) {
//        return new JedisCluster(addrSet, timeout, maxRedirections, config);
//    }
//
//    private JedisPoolConfig createJedisPoolConfig(int maxWaitMillis, int maxTotal, int minIdle, int maxIdle) {
//        JedisPoolConfig config = new JedisPoolConfig();
//        config.setMaxWaitMillis(maxWaitMillis);
//        config.setMaxTotal(maxTotal);
//        config.setMinIdle(minIdle);
//        config.setMaxIdle(maxIdle);
//        return config;
//    }
//
//    public JedisCluster getResource() {
//        return this.jedisCluster;
//    }
//
//    public void close() {
//        try {
//            if (jedisCluster != null)
//                jedisCluster.close();
//        } catch (IOException e) {
//            LOGGER.error("redis cluster close failure", e);
//        }
//        try {
//            if (watcher != null) {
//                watcher.close();
//            }
//        } catch (IOException e) {
//            LOGGER.error("zookeeper watcher close failure", e);
//        }
//
//    }
//
//    public static class RedisProxyInfo {
//        private String addr;
//        private String state;
//
//        public RedisProxyInfo() {
//        }
//
//        public String getAddr() {
//            return this.addr;
//        }
//
//        public void setAddr(String addr) {
//            this.addr = addr;
//        }
//
//        public String getState() {
//            return this.state;
//        }
//
//        public void setState(String state) {
//            this.state = state;
//        }
//
//        @Override
//        public String toString() {
//            return "RedisProxyInfo{" +
//                    "addr='" + addr + '\'' +
//                    ", state='" + state + '\'' +
//                    '}';
//        }
//    }
//
//}
