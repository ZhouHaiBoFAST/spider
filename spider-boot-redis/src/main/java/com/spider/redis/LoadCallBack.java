package com.spider.redis;

/**
 * 抽象数据库操作
 *
 * @author liuzhongkai
 */
@FunctionalInterface
public interface LoadCallBack<T> {

    /**
     * 数据库具体实现
     *
     * @return 数据库调用返回值
     * @author liuzhongkai
     */
    public T callBack();
}