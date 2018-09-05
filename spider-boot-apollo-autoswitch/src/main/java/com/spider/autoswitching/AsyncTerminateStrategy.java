package com.spider.autoswitching;

/**
 * 异步终止策略
 *
 * @author liuzhongkai
 */
@FunctionalInterface
public interface AsyncTerminateStrategy<T> {

    void terminate(T destroyObj);

    @FunctionalInterface
    public interface Closeable<T> {

        boolean close(T destroyObj);

    }
}
