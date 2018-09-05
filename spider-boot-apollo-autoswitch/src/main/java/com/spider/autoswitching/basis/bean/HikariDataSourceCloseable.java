package com.spider.autoswitching.basis.bean;

import com.spider.autoswitching.AsyncTerminateStrategy;
import com.zaxxer.hikari.HikariDataSource;
import com.zaxxer.hikari.HikariPoolMXBean;

/**
 * @author liuzhongkai
 */
public class HikariDataSourceCloseable implements AsyncTerminateStrategy.Closeable<HikariDataSource> {


    @Override
    public boolean close(HikariDataSource destroyObj) {
        HikariPoolMXBean poolMXBean = destroyObj.getHikariPoolMXBean();
        poolMXBean.softEvictConnections();
        return poolMXBean.getActiveConnections() <= 0;
    }


}
