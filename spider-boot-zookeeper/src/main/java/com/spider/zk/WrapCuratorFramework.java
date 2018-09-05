package com.spider.zk;

import com.google.common.base.Charsets;
import com.google.common.base.Objects;
import com.google.common.base.Strings;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.data.Stat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WrapCuratorFramework {

    private static final Logger logger = LoggerFactory.getLogger(WrapCuratorFramework.class);

    private CuratorFramework curatorFramework;

    public WrapCuratorFramework(ZookeeperProperties properties) {
        curatorFramework = CuratorFrameworkFactory.builder()
                .connectString(properties.getServers())
                .sessionTimeoutMs(properties.getSessionTimeout())
                .connectionTimeoutMs(properties.getConnectionTimeoutMs())
                .retryPolicy(new ExponentialBackoffRetry(properties.getBaseSleepTimeMs(), properties.getMaxRetries())).build();
    }

    public void init() {
        logger.info("curatorFramework start");
        curatorFramework.start();
    }

    public void close() {
        curatorFramework.close();
    }

    /**
     * 创建node
     *
     * @param nodeName   节点名称
     * @param value      节点数据
     * @param createMode 创建类型
     * @return 是否创建成功
     */
    public boolean createNode(String nodeName, String value, CreateMode createMode) {
        boolean suc = false;
        try {
            Stat stat = curatorFramework.checkExists().forPath(nodeName);
            if (stat == null) {
                String opResult;
                if (Strings.isNullOrEmpty(value)) {
                    opResult = curatorFramework.create().creatingParentsIfNeeded().withMode(createMode).forPath(nodeName);
                } else {
                    opResult = curatorFramework.create().creatingParentsIfNeeded().withMode(createMode).forPath(nodeName,
                            value.getBytes(Charsets.UTF_8));
                }
                suc = Objects.equal(nodeName, opResult);
            }
        } catch (Exception e) {
            logger.error("createNode fail,path:" + nodeName, e);
        }
        return suc;
    }

    /**
     * 删除节点
     *
     * @param path 地址
     */
    public void deleteNode(String path) {
        try {
            Stat stat = curatorFramework.checkExists().forPath(path);
            if (stat != null) {
                curatorFramework.delete().deletingChildrenIfNeeded().forPath(path);
            }
        } catch (Exception e) {
            logger.error("deleteNode fail", e);
        }
    }

    public CuratorFramework getCuratorFramework() {
        return curatorFramework;
    }
}
