package com.spider.elasticjob;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * elaticjob 配置
 *
 * @author liuzhongkai
 */
@ConfigurationProperties(prefix = "spider.elaticjob")
public class ElasticJobProperties {

    private String zookeeperNamespace;

    private String zookeeperServerLists;

    private int zookeeperBaseSleepTimeMilliseconds = 8000;

    private int zookeeperMaxSleepTimeMilliseconds = 10000;

    private int zookeeperMaxRetries = 3;

    private int zookeeperSessionTimeoutMilliseconds = 30000;

    private int zookeeperConnectionTimeoutMilliseconds = 10000;

    private String digest;


    public String getZookeeperNamespace() {
        return zookeeperNamespace;
    }

    public void setZookeeperNamespace(String zookeeperNamespace) {
        this.zookeeperNamespace = zookeeperNamespace;
    }

    public String getZookeeperServerLists() {
        return zookeeperServerLists;
    }

    public void setZookeeperServerLists(String zookeeperServerLists) {
        this.zookeeperServerLists = zookeeperServerLists;
    }

    public int getZookeeperBaseSleepTimeMilliseconds() {
        return zookeeperBaseSleepTimeMilliseconds;
    }

    public void setZookeeperBaseSleepTimeMilliseconds(int zookeeperBaseSleepTimeMilliseconds) {
        this.zookeeperBaseSleepTimeMilliseconds = zookeeperBaseSleepTimeMilliseconds;
    }

    public int getZookeeperMaxSleepTimeMilliseconds() {
        return zookeeperMaxSleepTimeMilliseconds;
    }

    public void setZookeeperMaxSleepTimeMilliseconds(int zookeeperMaxSleepTimeMilliseconds) {
        this.zookeeperMaxSleepTimeMilliseconds = zookeeperMaxSleepTimeMilliseconds;
    }

    public int getZookeeperMaxRetries() {
        return zookeeperMaxRetries;
    }

    public void setZookeeperMaxRetries(int zookeeperMaxRetries) {
        this.zookeeperMaxRetries = zookeeperMaxRetries;
    }

    public int getZookeeperSessionTimeoutMilliseconds() {
        return zookeeperSessionTimeoutMilliseconds;
    }

    public void setZookeeperSessionTimeoutMilliseconds(int zookeeperSessionTimeoutMilliseconds) {
        this.zookeeperSessionTimeoutMilliseconds = zookeeperSessionTimeoutMilliseconds;
    }

    public int getZookeeperConnectionTimeoutMilliseconds() {
        return zookeeperConnectionTimeoutMilliseconds;
    }

    public void setZookeeperConnectionTimeoutMilliseconds(int zookeeperConnectionTimeoutMilliseconds) {
        this.zookeeperConnectionTimeoutMilliseconds = zookeeperConnectionTimeoutMilliseconds;
    }

    public String getDigest() {
        return digest;
    }

    public void setDigest(String digest) {
        this.digest = digest;
    }
}
